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

    private Connection connection;//一个statement对应一个ResultSet

    /**
     * @param socket
     * @param DataInputHashMap 存放连接上服务器的客户端的输入字节流（根据账号存储）
     * @param DataOutHashMap   存放连接上服务器的客户端的输出字节流（根据账号存储）
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
            //1表示建立连接，将字节输入输出流添加到HashMap（根据账号存储）
            if (action == 1) {
                try {
                    account = din.readUTF();//读取字符串
                    try {
                        //在数据库查询账号判断是否存在
                        ResultSet re = connection.createStatement().executeQuery("select *from everyone where account=" + account);
                        //如果账号存在且处于不在线状态
                        if (re.next() && !re.getBoolean("state")) {
                            dout.writeInt(1);
                            dout.writeUTF(re.getString("name"));
                            dout.writeUTF(re.getString("says"));
                            dout.writeInt(re.getInt("image"));
                            //修改在线状态
//                            statement.execute("");
                            //查找好友信息
                            ResultSet re1 = connection.createStatement().executeQuery("select *from friendship where me=" + account);
                            //如果有好友信息则进行发送
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
                            //结束发送
                            dout.writeBoolean(false);

                            System.out.println("账号 " + account + " 建立连接");
                            DataInputHashMap.put(account, din);
                            DataOutHashMap.put(account, dout);
                            System.out.println("字节流绑定账号成功");
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
