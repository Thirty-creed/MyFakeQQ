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

    //�����ݿ���в���
    private static Connection connection;

    public static void main(String[] args) {

        QQServer qqserver = new QQServer();
        qqserver.createServer();
        // ���������ݿ������
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/people_data", "root", null);
            System.out.println("���������ݿ������");
        } catch (Exception e) {
            e.printStackTrace();
        }
        qqserver.waitConnect();

    }

    //����������
    public void createServer() {
        try {
            server = new ServerSocket(8800);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //�ȴ�����
    public void waitConnect() {
        while (true) {
            try {
                Socket socket = server.accept();
                System.out.println("�и��ͻ������ӽ����ˣ�");
                //һ���ͻ��˽���һ���̴߳���
                new ServerThread(socket, DataInputHashMap, DataOutHashMap, connection).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
