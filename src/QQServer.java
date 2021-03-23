import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class QQServer {

    private ServerSocket server;
    private HashMap<String, DataInputStream> DataInputHashMap = new HashMap<String, DataInputStream>();
    private HashMap<String, DataOutputStream> DataOutHashMap = new HashMap<String, DataOutputStream>();

    public static void main(String[] args) {
        QQServer qqserver = new QQServer();
        qqserver.createServer();
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
                new ServerThread(socket, DataInputHashMap, DataOutHashMap).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
