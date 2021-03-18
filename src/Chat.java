import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Chat {

    boolean first = true;

    public static void main(String[] args) {
        Chat chat = new Chat();
        chat.Open();
    }

    public void Open() {
        JFrame chat_frame = new JFrame("好友");
        chat_frame.setSize(800, 700);
        chat_frame.setLocationRelativeTo(null);
        chat_frame.setLayout(null);
        chat_frame.setResizable(false);
//        chat.setUndecorated(true);
        chat_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //短期聊天记录
        JPanel context = new JPanel();
//        context.setBackground(Color.BLACK);
        context.setBounds(0, 0, 650, 480);
        context.setLayout(null);
        chat_frame.add(context);

        JTextArea context_jta = new JTextArea();
        //聊天记录不可编辑
        context_jta.setEditable(false);
        context_jta.setOpaque(false);

        JScrollPane context_jsp = new JScrollPane(context_jta);
        context_jsp.setOpaque(false);
        context_jsp.getViewport().setOpaque(false);
        context_jsp.setBounds(0, 0, 650, 480);
        context.add(context_jsp);

        //工具栏
        JPanel tool = new JPanel();
        tool.setBounds(0, 480, 650, 40);

        JButton emotion = new JButton("表情");
        tool.add(emotion);
        chat_frame.add(tool);

        Image people = new ImageIcon("man.png").getImage().getScaledInstance(150, 700, JFrame.DO_NOTHING_ON_CLOSE);
        JLabel QQ_show = new JLabel(new ImageIcon(people));
        QQ_show.setBounds(650, 0, 150, 700);
        chat_frame.add(QQ_show);

        //将要发送的信息
        JPanel send_context = new JPanel();
        send_context.setLayout(null);
        send_context.setBounds(0, 520, 650, 180);

        //按钮要放置在JTexArea前，否则没效果(原因暂时未知)
        JButton close = new JButton("关  闭");
        close.setBounds(490, 112, 80, 30);
        close.addActionListener(e -> System.exit(0));
        send_context.add(close);

        JTextArea send_context_jta = new JTextArea();
        send_context_jta.setOpaque(false);
        //回车键发送消息
        send_context_jta.addKeyListener(new KeyListener() {
            String str;

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (!send_context_jta.getText().equals("")) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if (first) {
                            first = false;
                            str = send_context_jta.getText();
                        } else {
                            str = context_jta.getText() + "\n" + send_context_jta.getText();
                        }
                        context_jta.setText(str);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    send_context_jta.setText("");
                }
            }
        });

        //发送消息按钮
        JButton send = new JButton("发  送");
        send.setBounds(570, 112, 79, 30);
        send_context.add(send);
        send.addActionListener(new ActionListener() {
            String str;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!send_context_jta.getText().equals("")) {
                    if (first) {
                        first = false;
                        str = send_context_jta.getText();
                    } else {
                        str = context_jta.getText() + "\n" + send_context_jta.getText();
                    }
                    send_context_jta.setText("");
                    context_jta.setText(str);
                }
                send_context_jta.requestFocus();
            }
        });

        JScrollPane send_context_jsp = new JScrollPane(send_context_jta);
        send_context_jsp.setOpaque(false);
        send_context_jsp.getViewport().setOpaque(false);
        send_context_jsp.setBounds(0, 0, 650, 180);
        send_context.add(send_context_jsp);

        chat_frame.add(send_context);
        chat_frame.setVisible(true);
        send_context_jta.requestFocus();
    }
}
