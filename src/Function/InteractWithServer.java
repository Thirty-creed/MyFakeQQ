package Function;

import Display.Client;

import java.io.*;
import java.net.Socket;

public class InteractWithServer implements Operation {

    private DataInputStream din;
    private DataOutputStream dout;
    private boolean IsConnected = false;

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
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Client(this).UI();

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
    public void Clink_Send_Operation(String receiver,String message) {
        try {
            dout.writeInt(2);
            dout.writeUTF(receiver);//写入字符串，receiver为接收方
            dout.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
