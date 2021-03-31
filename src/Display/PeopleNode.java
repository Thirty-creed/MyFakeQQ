package Display;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class PeopleNode extends DefaultMutableTreeNode {

    private String kind;
    private String account;
    private String name;
    private String says;
    private int head;

    private boolean OnLineState = false;

    public PeopleNode(String kind, String account, String name, String says, int head) {
        super(name+"                 ");
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

        switch (head){
            case 0:
                return new ImageIcon("库里.jpg");
            case 1:
                return new ImageIcon("克雷.png");
        }

        return null;
    }

    public boolean getOnLineState() {
        return OnLineState;
    }


    // 更改在线状态
    public void SetState(boolean OnLineState) {
        this.OnLineState = OnLineState;
    }

    public String getKind() {
        return kind;
    }
}
