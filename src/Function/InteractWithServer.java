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
     * ���������������
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
            System.out.println("�����ѽ�����");
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
            dout.writeUTF(account);//д���ַ���
            String kind = din.readUTF();
            System.out.println("�������ͣ�" + kind);
            String opposite_account = din.readUTF();
            System.out.println("�Է��˺�Ϊ��" + opposite_account);
            String opposite_name = din.readUTF();
            System.out.println("�Է��ǳ�Ϊ��" + opposite_name);
            people_list.add(new PeopleNode(kind, opposite_account, opposite_name, null, null));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Client(this, people_list).UI();

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
