package Function;

import Display.Client;

import java.io.*;
import java.net.Socket;

public class OperationWithServer implements Operation {

    private DataInputStream din;
    private DataOutputStream dout;

    /**
     * 与服务器建立连接
     */
    public void connect() {
        try {
            Socket soc = new Socket("127.0.0.1", 8800);
            InputStream in = soc.getInputStream();
            OutputStream out = soc.getOutputStream();
            din = new DataInputStream(in);
            dout = new DataOutputStream(out);
            dout.writeInt(1);
            for (int i = 0; i < 5; i++) {
                dout.writeChar('b');
            }
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
    public boolean Clink_Sign_In_Operation() {
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
    public void Clink_Send_Operation(char[] ch) {
        try {
            dout.writeInt(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0;i<5;i++){
            try {
                dout.writeChar(ch[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
