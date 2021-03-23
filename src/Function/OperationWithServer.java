package Function;

import Display.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class OperationWithServer implements Operation {

    private DataInputStream din;
    private DataOutputStream dout;

    /**
     * 与服务器建立连接
     */
    public void connect() {

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
    public void Clink_Send_Operation() {

    }
}
