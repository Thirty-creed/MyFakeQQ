package Display;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PeopleNode extends DefaultMutableTreeNode {

    private String kind;
    private String account;
    private String name;
    private String says;
    private int head;

    private boolean OnLineState = false;

    public PeopleNode(String kind, String account, String name, String says, int head) {
        super(name + "          ");
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

        if (OnLineState) {
            return switch (head) {
                case 0 -> new ImageIcon("库里.jpg");
                case 1 -> new ImageIcon("克莱.png");
                default -> null;
            };
        } else {
            return switch (head) {
                case 0 -> getGrayImage(new ImageIcon("库里.jpg"));
                case 1 -> getGrayImage(new ImageIcon("克莱.png"));
                default -> null;
            };
        }
    }

    public boolean getOnLineState() {
        return OnLineState;
    }

    public ImageIcon getGrayImage(ImageIcon icon){
        int w=icon.getImage().getWidth(null);
        int h=icon.getImage().getHeight(null);
        BufferedImage buff=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        Graphics g=buff.getGraphics();
        g.drawImage(icon.getImage(),0,0,null);
        for (int i=0;i<w;i++){
            for(int j=0;j<h;j++){
                int red,green,blue;
                int pixel=buff.getRGB(i,j);
                red=(pixel>>16)&0xFF;
                green=(pixel>>8)&0xFF;
                blue=(pixel>>0)&0xFF;
                int sum=(red+green+blue)/3;
                g.setColor(new Color(sum,sum,sum));
              g.fillRect(i,j,1,1);
            }
        }
        return new ImageIcon(buff);
    }

    // 更改在线状态
    public void SetState(boolean OnLineState) {
        this.OnLineState = OnLineState;
    }

    public String getKind() {
        return kind;
    }
}
