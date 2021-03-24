package Function;

import Display.Client;

import java.io.*;
import java.net.Socket;

public class OperationWithServer implements Operation {

    private DataInputStream din;
    private DataOutputStream dout;
    /**
     * ���������������
     */
    public void connect() {
        try {
            Socket soc = new Socket("127.0.0.1", 8800);
            InputStream in = soc.getInputStream();
            OutputStream out = soc.getOutputStream();
            din = new DataInputStream(in);
            dout = new DataOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
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
    public boolean Clink_Sign_In_Operation(String account,String password) {
        new Client(this).UI();
        try {
            dout.writeInt(1);
            dout.writeUTF(account);//д���ַ���
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
    public void Clink_Send_Operation(String receiver) {
        try {
            dout.writeInt(2);
            dout.writeUTF(receiver);//д���ַ�����receiverΪ���շ�
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
