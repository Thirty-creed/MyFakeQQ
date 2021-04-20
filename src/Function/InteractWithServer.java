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
            dout.writeUTF(password);
            int result = din.readInt();
            //1��ʾ��¼�ɹ�
            if (result == 1) {
                //�����Լ�����Ϣ
                String name = din.readUTF();
                String says = din.readUTF();
                int image = din.readInt();
                PeopleNode myself = new PeopleNode(null, account, name, says, image);
                myself.SetState(true);
                //���պ�����Ϣ
                while (din.readBoolean()) {
                    String opposite_account = din.readUTF();
                    System.out.println("�Է��˺�Ϊ��" + opposite_account);
                    String kind = din.readUTF();
                    System.out.println("�������ͣ�" + kind);
                    String opposite_name = din.readUTF();
                    System.out.println("�Է��ǳ�Ϊ��" + opposite_name);
                    String opposite_says = din.readUTF();
                    int opposite_image = din.readInt();
                    PeopleNode opposite_node = new PeopleNode(kind, opposite_account, opposite_name, opposite_says, opposite_image);
                    boolean is_online=din.readBoolean();
                    opposite_node.SetState(is_online);
                    people_list.add(opposite_node);
                }
                //������Ϣ�����ͻ���
                new Client(this, people_list, myself).UI();
            } else {
                System.out.println("��¼ʧ�ܣ������������˺�����");
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
