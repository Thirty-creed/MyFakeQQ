package Server;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ServerThread extends Thread {

    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;
    private String account;
    private HashMap<String, DataInputStream> DataInputHashMap;
    private HashMap<String, DataOutputStream> DataOutHashMap;

    /**
     * @param socket
     * @param DataInputHashMap ��������Ϸ������Ŀͻ��˵������ֽ����������˺Ŵ洢��
     * @param DataOutHashMap   ��������Ϸ������Ŀͻ��˵�����ֽ����������˺Ŵ洢��
     */
    public ServerThread(Socket socket, HashMap<String, DataInputStream> DataInputHashMap, HashMap<String, DataOutputStream> DataOutHashMap) {
        this.socket = socket;
        this.DataInputHashMap = DataInputHashMap;
        this.DataOutHashMap = DataOutHashMap;
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            din = new DataInputStream(in);
            dout = new DataOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    System.out.println("�˺� "+account+" ��������");
                    DataInputHashMap.put(account, din);
                    DataOutHashMap.put(account, dout);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("�ֽ������˺ųɹ�");
            } else if (action == 2) {
                try {
                    String receiver = din.readUTF();//��ȡ�ַ���
                    DataOutputStream message_to = DataOutHashMap.get(receiver);
                    String message= din.readUTF();
                    message_to.writeInt(21);//21��ʾ������Ϣ
                    message_to.writeUTF(message);//д���ַ���
                    System.out.println("���ͳɹ�");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
