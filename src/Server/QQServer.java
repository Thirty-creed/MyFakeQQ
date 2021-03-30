package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;

public class QQServer {

    private ServerSocket server;
    private ConcurrentHashMap<String, DataInputStream> DataInputHashMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, DataOutputStream> DataOutHashMap = new ConcurrentHashMap<>();

    private static Statement statement;

    public static void main(String[] args) {

        QQServer qqserver = new QQServer();
        qqserver.createServer();
        // 建立与数据库的连接
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/people_data", "root", null);
            statement = connection.createStatement();
            System.out.println("建立与数据库的连接");
//            ResultSet rs=statement.executeQuery("select *from everyone");
//            while (rs.next()){
//                System.out.println(rs.getString("name"));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        qqserver.waitConnect();

    }

    //建立服务器
    public void createServer() {
        try {
            server = new ServerSocket(8800);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //等待连接
    public void waitConnect() {
        while (true) {
            try {
                Socket socket = server.accept();
                System.out.println("有个客户端连接进来了！");
                //一个客户端建立一个线程处理
                new ServerThread(socket, DataInputHashMap, DataOutHashMap, statement).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
