package Function;

import Display.Client;
import Display.PeopleNode;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class InteractWithServer implements Operation {

    private DataInputStream din;
    private DataOutputStream dout;
    private boolean IsConnected = false;
    private ArrayList<PeopleNode> people_list = new ArrayList<>();

    /**
     * 与服务器建立连接
     */
    public void connect() {
        if (!IsConnected) {
            try {
                Socket soc = new Socket("127.0.0.1", 8800);
                InputStream in = soc.getInputStream();
                OutputStream out = soc.getOutputStream();
                din = new DataInputStream(in);
                dout = new DataOutputStream(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            IsConnected = true;
        } else {
            System.out.println("连接已建立！");
        }
    }

    public DataInputStream getDin() {
        return din;
    }

    public DataOutputStream getDout() {
        return dout;
    }

    @Override
    public void Clink_Sign_Up_Operation() {

    }

    @Override
    public void Clink_Forget_Operation() {

    }

    @Override
    public boolean Clink_Sign_In_Operation(String account, String password) {
        if (!IsConnected) {
            connect();
        }

        try {
            dout.writeInt(1);
            dout.writeUTF(account);//写入字符串
            dout.writeUTF(password);
            int result = din.readInt();
            //1表示登录成功
            if (result == 1) {
                //接收自己的信息
                String name = din.readUTF();
                String says = din.readUTF();
                int image = din.readInt();
                PeopleNode myself = new PeopleNode(null, account, name, says, image);
                myself.SetState(true);
                //接收好友信息
                while (din.readBoolean()) {
                    String opposite_account = din.readUTF();
                    System.out.println("对方账号为：" + opposite_account);
                    String kind = din.readUTF();
                    System.out.println("好友类型：" + kind);
                    String opposite_name = din.readUTF();
                    System.out.println("对方昵称为：" + opposite_name);
                    String opposite_says = din.readUTF();
                    int opposite_image = din.readInt();
                    PeopleNode opposite_node = new PeopleNode(kind, opposite_account, opposite_name, opposite_says, opposite_image);
                    boolean is_online=din.readBoolean();
                    opposite_node.SetState(is_online);
                    people_list.add(opposite_node);
                }
                //根据信息创建客户端
                new Client(this, people_list, myself).UI();
            } else {
                System.out.println("登录失败，请重新输入账号密码");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void Clink_ChangeInformation_Operation() {

    }

    @Override
    public void Clink_OnlineState_Operation() {

    }

    @Override
    public void Clink_AddFriend_Operation() {

    }

    @Override
    public void Clink_Send_Operation(String receiver, String message) {

    }
}
