package Server;

import java.io.*;
import java.net.Socket;
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

    private Statement statement;

    /**
     * @param socket
     * @param DataInputHashMap ��������Ϸ������Ŀͻ��˵������ֽ����������˺Ŵ洢��
     * @param DataOutHashMap   ��������Ϸ������Ŀͻ��˵�����ֽ����������˺Ŵ洢��
     */
    public ServerThread(Socket socket, ConcurrentHashMap<String, DataInputStream> DataInputHashMap, ConcurrentHashMap<String, DataOutputStream> DataOutHashMap,Statement statement) {
        this.socket = socket;
        this.DataInputHashMap = DataInputHashMap;
        this.DataOutHashMap = DataOutHashMap;
        this.statement=statement;
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
                    System.out.println("�˺� " + account + " ��������");
                    DataInputHashMap.put(account, din);
                    DataOutHashMap.put(account, dout);
                    System.out.println("�ֽ������˺ųɹ�");
                    try {
                        ResultSet re=statement.executeQuery("select *from friendship where me="+account);
                        while (re.next()){
                            String kind=re.getString("kind");
                            dout.writeUTF(kind);
                            String friend_account=re.getString("friend");
                            dout.writeUTF(friend_account);
                            ResultSet re1=statement.executeQuery("select *from everyone where account="+friend_account);
                            while (re1.next()){
                                String name=re1.getString("name");
                                dout.writeUTF(name);
                            }
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
