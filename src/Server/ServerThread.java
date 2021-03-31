package Server;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

public class ServerThread extends Thread {

    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;
    private String account;
    private ConcurrentHashMap<String, DataInputStream> DataInputHashMap;
    private ConcurrentHashMap<String, DataOutputStream> DataOutHashMap;

    private Connection connection;//һ��statement��Ӧһ��ResultSet

    /**
     * @param socket
     * @param DataInputHashMap ��������Ϸ������Ŀͻ��˵������ֽ����������˺Ŵ洢��
     * @param DataOutHashMap   ��������Ϸ������Ŀͻ��˵�����ֽ����������˺Ŵ洢��
     */
    public ServerThread(Socket socket, ConcurrentHashMap<String, DataInputStream> DataInputHashMap, ConcurrentHashMap<String, DataOutputStream> DataOutHashMap, Connection connection) {
        this.socket = socket;
        this.DataInputHashMap = DataInputHashMap;
        this.DataOutHashMap = DataOutHashMap;
        this.connection = connection;
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            din = new DataInputStream(in);
            dout = new DataOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            int action = 0;
            try {
                action = din.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //1��ʾ�������ӣ����ֽ������������ӵ�HashMap�������˺Ŵ洢��
            if (action == 1) {
                try {
                    account = din.readUTF();//��ȡ�ַ���
                    try {
                        //�����ݿ��ѯ�˺��ж��Ƿ����
                        ResultSet re = connection.createStatement().executeQuery("select *from everyone where account=" + account);
                        //����˺Ŵ����Ҵ��ڲ�����״̬
                        if (re.next() && !re.getBoolean("state")) {
                            dout.writeInt(1);
                            dout.writeUTF(re.getString("name"));
                            dout.writeUTF(re.getString("says"));
                            dout.writeInt(re.getInt("image"));
                            //�޸�����״̬
//                            statement.execute("");
                            //���Һ�����Ϣ
                            ResultSet re1 = connection.createStatement().executeQuery("select *from friendship where me=" + account);
                            //����к�����Ϣ����з���
                            while (re1.next()) {
                                dout.writeBoolean(true);
                                dout.writeUTF(re1.getString("friend"));
                                dout.writeUTF(re1.getString("kind"));
                                ResultSet re2 = connection.createStatement().executeQuery("select *from everyone where account=" + re1.getString("friend"));
                                if (re2.next()) {
                                    dout.writeUTF(re2.getString("name"));
                                    dout.writeUTF(re2.getString("says"));
                                    dout.writeInt(re2.getInt("image"));
                                    dout.writeBoolean(re2.getBoolean("state"));
                                }
                            }
                            //��������
                            dout.writeBoolean(false);

                            System.out.println("�˺� " + account + " ��������");
                            DataInputHashMap.put(account, din);
                            DataOutHashMap.put(account, dout);
                            System.out.println("�ֽ������˺ųɹ�");
                        } else {
                            dout.writeInt(0);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
