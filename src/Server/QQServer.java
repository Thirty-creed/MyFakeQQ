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

    //对数据库进行操作
    private static Connection connection;

    public static void main(String[] args) {

        QQServer qqserver = new QQServer();
        qqserver.createServer();
        // 建立与数据库的连接
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/people_data", "root", null);
            System.out.println("建立与数据库的连接");
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
                new ServerThread(socket, DataInputHashMap, DataOutHashMap, connection).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
