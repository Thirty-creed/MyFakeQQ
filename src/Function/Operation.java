package Function;

public interface Operation {

    /**
     * 点击注册按钮
     */
    public void Clink_Sign_Up_Operation();

    /**
     * 点击忘记密码
     */
    public void Clink_Forget_Operation();

    /**
     * 点击登录后的与服务器进行交互,如果登录成功返回ture
     */
    public boolean Clink_Sign_In_Operation(String account,String password);


    /**
     * 修改个人信息
     */
    public void Clink_ChangeInformation_Operation();

    /**
     * 修改在线状态
     */
    public void Clink_OnlineState_Operation();

    /**
     * 添加好友
     */
    public void Clink_AddFriend_Operation();

    /**
     * 点击发送文字
     */
    public void Clink_Send_Operation(String receiver);
}
