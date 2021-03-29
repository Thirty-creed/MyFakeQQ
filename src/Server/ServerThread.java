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
     * @param DataInputHashMap 存放连接上服务器的客户端的输入字节流（根据账号存储）
     * @param DataOutHashMap   存放连接上服务器的客户端的输出字节流（根据账号存储）
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
            //1表示建立连接，将字节输入输出流添加到HashMap（根据账号存储）
            if (action == 1) {
                try {
                    account = din.readUTF();//读取字符串
                    System.out.println("账号 "+account+" 建立连接");
                    DataInputHashMap.put(account, din);
                    DataOutHashMap.put(account, dout);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("字节流绑定账号成功");
            } else if (action == 2) {
                try {
                    String receiver = din.readUTF();//读取字符串
                    DataOutputStream message_to = DataOutHashMap.get(receiver);
                    String message= din.readUTF();
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
