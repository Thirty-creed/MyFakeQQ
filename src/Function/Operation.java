package Function;

public interface Operation {

    /**
     * ���ע�ᰴť
     */
    public void Clink_Sign_Up_Operation();

    /**
     * �����������
     */
    public void Clink_Forget_Operation();

    /**
     * �����¼�������������н���,�����¼�ɹ�����ture
     */
    public boolean Clink_Sign_In_Operation(String account,String password);


    /**
     * �޸ĸ�����Ϣ
     */
    public void Clink_ChangeInformation_Operation();

    /**
     * �޸�����״̬
     */
    public void Clink_OnlineState_Operation();

    /**
     * ��Ӻ���
     */
    public void Clink_AddFriend_Operation();

    /**
     * �����������
     */
    public void Clink_Send_Operation(String receiver);
}
