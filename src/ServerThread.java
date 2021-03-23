import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread {

    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;
    private HashMap<String, DataInputStream> DataInputHashMap;
    private HashMap<String, DataOutputStream> DataOutHashMap;

    /**
     * @param socket
     * @param DataInputHashMap 存放连接上服务器的客户端的输入字节流（根据客户端的代号String）
     * @param DataOutHashMap   存放连接上服务器的客户端的输出字节流（根据客户端的代号String）
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
            char[] ch1 = new char[5];
            int action = 0;
            try {
                action = din.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //1表示建立连接，将字节输入输出流添加到HashMap（根据String）
            if (action == 1) {
                ch1 = new char[5];
                for (int i = 0; i < 5; i++) {
                    try {
                        ch1[i] = din.readChar();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String str = new String(ch1);
                System.out.println(str);
                DataInputHashMap.put(str, din);
                DataOutHashMap.put(str, dout);
                System.out.println("字节流传入成功");
            } else if (action == 2) {
                char[] ch=new char[5];
                for(int i=0;i<5;i++){
                    try {
                        ch[i]=din.readChar();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String str=new String(ch);
                System.out.println(str+"======");
                DataOutputStream other_out=DataOutHashMap.get(str);
                try {
                    other_out.writeInt(1);
                    for(int i=0;i<5;i++){
                        try {
                            other_out.writeChar(ch1[i]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
