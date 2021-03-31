package Display;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class PeopleNode extends DefaultMutableTreeNode {

    private String kind;
    private String account;
    private String name;
    private String says;
    private ImageIcon head;

    private boolean OnLineState = false;

    public PeopleNode(String kind, String account, String name, String says, ImageIcon head) {
        super(name);
        this.kind = kind;
        this.account = account;
        this.name = name;
        this.says = says;
        this.head = head;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public String getSays() {
        return says;
    }

    public ImageIcon getImageIcon() {
        return new ImageIcon("好友.png");
    }

    public boolean getOnLineState() {
        return OnLineState;
    }


    // 更改在线状态
    public void changeState(boolean OnLineState) {
        this.OnLineState = OnLineState;
    }

    public String getKind() {
        return kind;
    }
}
