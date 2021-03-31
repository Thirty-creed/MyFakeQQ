package Display;

import Function.InteractWithServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login {

    private boolean Can_Drag = false;
    private final InteractWithServer handler = new InteractWithServer();

    public static void main(String[] args) {
        Login login = new Login();
        login.UI();
    }

    public void UI() {
        JFrame frame = new JFrame();
        frame.setSize(450, 330);
        //设置不可改变大小
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        //设置在任务栏显示的图标
        frame.setIconImage(new ImageIcon("图标.jpg").getImage());
        //去边框
        frame.setUndecorated(true);
        frame.setLayout(null);

        Image image = new ImageIcon("登录背景.jpg").getImage().getScaledInstance(450, 330, JFrame.DO_NOTHING_ON_CLOSE);
        JLabel background = new JLabel(new ImageIcon(image));
        background.setBounds(0, 0, 450, 330);
        //将背景标签放置在中间层
        frame.getLayeredPane().add(background, Integer.valueOf(Integer.MIN_VALUE));
        JPanel panel = (JPanel) frame.getContentPane();
        //将最上层设置透明
        panel.setOpaque(false);

        // 设置窗体拖动效果
        background.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                Can_Drag = false;
            }

        });

        background.addMouseMotionListener(new MouseMotionAdapter() {
            int StartX, StartY, EndX, EndY;

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!Can_Drag) {
                    StartX = e.getX();
                    StartY = e.getY();
                    Can_Drag = true;
                }
                EndX = e.getX();
                EndY = e.getY();
                frame.setLocation(frame.getX() + EndX - StartX, frame.getY() + EndY - StartY);
            }

        });

        JButton minimize = new JButton(new ImageIcon("最小化.png"));
        //将按钮设置透明
        minimize.setContentAreaFilled(false);
        //将按钮设置为无边框
        minimize.setBorderPainted(false);
        minimize.setBounds(390, 0, 30, 30);
        minimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //最小化窗口
                frame.setExtendedState(1);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //使按钮呈现灰色
                minimize.setContentAreaFilled(true);
                minimize.setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimize.setContentAreaFilled(false);
            }
        });
        frame.add(minimize);

        JButton close = new JButton(new ImageIcon("关闭.png"));
        //将按钮设置透明
        close.setContentAreaFilled(false);
        //将按钮设置为无边框
        close.setBorderPainted(false);
        close.setBounds(420, 0, 30, 30);
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //关闭窗口
                System.exit(0);
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

        Image QQ_Head = new ImageIcon("头像.jpg").getImage().getScaledInstance(100, 100, JFrame.DO_NOTHING_ON_CLOSE);
        JLabel head = new JLabel(new ImageIcon(QQ_Head));
        head.setBounds(20, 120, 100, 100);
        frame.add(head);

        JTextField account = new JTextField();
        account.setBounds(135, 120, 200, 30);
        frame.add(account);

        JTextField password = new JTextField();
        password.setBounds(135, 160, 200, 30);
        frame.add(password);

        JCheckBox auto_sign_in = new JCheckBox("自动登录");
        auto_sign_in.setBounds(135, 200, 80, 20);
        auto_sign_in.setOpaque(false);
        frame.add(auto_sign_in);

        JCheckBox remember = new JCheckBox("记住密码");
        remember.setBounds(240, 200, 80, 20);
        remember.setOpaque(false);
        frame.add(remember);

        Font font = new Font("宋体", Font.BOLD | Font.ITALIC, 16);

        JButton sign_up = new JButton("注册账号");
        sign_up.setBounds(330, 120, 120, 30);
        sign_up.setForeground(Color.yellow);
        sign_up.setFont(font);
        sign_up.setContentAreaFilled(false);
        sign_up.setBorderPainted(false);
        sign_up.addActionListener(e -> handler.Clink_Sign_Up_Operation());
        frame.add(sign_up);

        JButton forget = new JButton("找回密码");
        forget.setBounds(330, 160, 120, 30);
        forget.setForeground(Color.yellow);
        forget.setFont(font);
        forget.setContentAreaFilled(false);
        forget.setBorderPainted(false);
        forget.addActionListener(e -> handler.Clink_Forget_Operation());
        frame.add(forget);

        JButton sign_in = new JButton("登        录");
        sign_in.setFont(new Font("宋体", Font.BOLD, 15));
        sign_in.setBounds(135, 250, 180, 40);
        sign_in.addActionListener(e -> {
            //判断是否合法
            if (IsLegalLogin(account.getText(), password.getText())) {
                if (handler.Clink_Sign_In_Operation(account.getText(), password.getText())) {
                    System.out.println("登录的账号为" + account.getText());
                    frame.dispose();
                }
            } else {
                //弹出提醒重新输入账号密码
                System.out.println("验证失败");
            }
        });
        frame.add(sign_in);

        frame.setVisible(true);
        account.requestFocus();
    }

    /**
     * 暂时只验证账号
     *
     * @param account  账号
     * @param password 密码
     * @return 账号密码是否合法
     */
    public boolean IsLegalLogin(String account, String password) {
        if (account.equals("")) {
            return false;
        }
        int n = account.length();
        char[] ch;
        ch = account.toCharArray();
        for (int i = 0; i < n; i++) {
            if (ch[i] < '0' || ch[i] > '9') {
                return false;
            }
        }
        return true;
    }
}
