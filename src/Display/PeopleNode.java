package Display;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class PeopleNode extends DefaultMutableTreeNode {

    private String account;
    private String name;
    private String says;
    private ImageIcon head;
    private String Kind;
    private boolean OnLineState = false;

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public ImageIcon getImageIcon() {
        return head;
    }

    public PeopleNode(String account, String name, String says, ImageIcon head) {
        super(name);
        this.account = account;
        this.name = name;
        this.says = says;
        this.head = head;
    }
}
