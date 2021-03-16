import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Login {
    public static void main(String[] args) {
        Login login=new Login();
        login.UI();
    }

    public void UI(){
        JFrame frame=new JFrame();
        frame.setSize(430,330);
        //设置不可改变大小
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("图标.png").getImage());
        //去边框
        frame.setUndecorated(true);
        frame.setLayout(null);

        Image image=new ImageIcon("登录背景.jpg").getImage().getScaledInstance(450,350,JFrame.DO_NOTHING_ON_CLOSE);
        JLabel background=new JLabel(new ImageIcon(image));
        background.setBounds(0,0,450,350);
        //将背景标签放置在中间层
        frame.getLayeredPane().add(background, Integer.valueOf(Integer.MIN_VALUE));
        JPanel panel=(JPanel) frame.getContentPane();
        //将最上层设置透明
        panel.setOpaque(false);

        JButton minimize=new JButton(new ImageIcon("最小化.png"));
        //将按钮设置透明
        minimize.setContentAreaFilled(false);
        //将按钮设置为无边框
        minimize.setBorderPainted(false);
        minimize.setBounds(370,0,30,30);
        minimize.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //最小化窗口
                frame.setExtendedState(1);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //使按钮呈现红色
                minimize.setContentAreaFilled(true);
                minimize.setBackground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimize.setContentAreaFilled(false);
            }
        });
        frame.add(minimize);

        JButton close=new JButton(new ImageIcon("关闭.png"));
        //将按钮设置透明
        close.setContentAreaFilled(false);
        //将按钮设置为无边框
        close.setBorderPainted(false);
        close.setBounds(400,0,30,30);
        close.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //关闭窗口
                frame.dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //使按钮呈现红色
                close.setContentAreaFilled(true);
                close.setBackground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                close.setContentAreaFilled(false);
            }
        });
        frame.add(close);

        frame.setVisible(true);

    }
}
