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
     * @param DataInputHashMap 存放连接上服务器的客户端的输入字节流（根据账号存储）
     * @param DataOutHashMap   存放连接上服务器的客户端的输出字节流（根据账号存储）
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
            //1表示建立连接，将字节输入输出流添加到HashMap（根据账号存储）
            if (action == 1) {
                try {
                    account = din.readUTF();//读取字符串
                    System.out.println("账号 " + account + " 建立连接");
                    DataInputHashMap.put(account, din);
                    DataOutHashMap.put(account, dout);
                    try {
                        ResultSet re=statement.executeQuery("select *from everyone where name='库里'");
                        while (re.next()){
                            dout.writeUTF(re.getString("account"));

                            dout.writeUTF(re.getString("name"));
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("字节流绑定账号成功");
            } else if (action == 2) {
                try {
                    String receiver = din.readUTF();//读取字符串
                    DataOutputStream message_to = DataOutHashMap.get(receiver);
                    String message = din.readUTF();
                    message_to.writeInt(21);//21表示发送消息
                    message_to.writeUTF(message);//写入字符串
                    System.out.println("发送成功");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
