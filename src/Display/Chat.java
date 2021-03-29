package Display;

import Function.InteractWithServer;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Chat {

    SimpleDateFormat time_format;

    {
        time_format = new SimpleDateFormat(("MM-dd HH:mm:ss"));
    }

    private final InteractWithServer handler;

    public Chat(InteractWithServer handler) {
        this.handler = handler;
    }

    public void Open(String Opposite_account) {
        JFrame chat_frame = new JFrame("好友");
        chat_frame.setSize(800, 700);
        chat_frame.setLocationRelativeTo(null);
        chat_frame.setLayout(null);
        chat_frame.setResizable(false);

        //短期聊天记录
        JPanel context = new JPanel();
        context.setBounds(0, 0, 650, 480);
        context.setLayout(null);
        chat_frame.add(context);

        JTextPane context_jtp = new JTextPane();
        Document doc = context_jtp.getDocument();
        //聊天记录不可编辑
        context_jtp.setEditable(false);
        context_jtp.setOpaque(false);

        JScrollPane context_jsp = new JScrollPane(context_jtp);
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

        //按钮要放置在JScrollPane前，否则没效果(原因暂时未知)
        JButton close = new JButton("关  闭");
        close.setBounds(490, 112, 80, 30);
        close.addActionListener(e -> System.exit(0));
        send_context.add(close);

        JTextPane send_context_jtp = new JTextPane();
        send_context_jtp.setOpaque(false);
        //回车键发送消息
        send_context_jtp.addKeyListener(new KeyAdapter() {
            String str;
            boolean press_ctrl = false;
            boolean press_enter = false;

            @Override
            public void keyPressed(KeyEvent e) {
                if (!press_enter && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!send_context_jtp.getText().equals("") && !send_context_jtp.getText().equals("\n")) {
                        str = "                                              "
                                + "                                           "
                                + time_format.format(new Date()) + "\n"
                                + send_context_jtp.getText() + "\n";
                        try {
                            doc.insertString(doc.getLength(), str, new SimpleAttributeSet());
                        } catch (BadLocationException ef) {
                            ef.printStackTrace();
                        }
                    }
                    send_context_jtp.setText("");
                    press_enter = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    press_ctrl = true;
                    System.out.println("press");
                }
                if (press_ctrl && e.getKeyCode() == KeyEvent.VK_V) {
                    send_context_jtp.setText("====");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (press_enter && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    send_context_jtp.setText("");
                    press_enter = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    press_ctrl = false;
                    System.out.println("release");
                }
            }
        });

        //发送消息按钮
        JButton send = new JButton("发  送");
        send.setBounds(570, 112, 79, 30);
        send.addActionListener(e -> handler.Clink_Send_Operation(Opposite_account));//对方的账号为123456
        send_context.add(send);

        JScrollPane send_context_jsp = new JScrollPane(send_context_jtp);
        send_context_jsp.setOpaque(false);
        send_context_jsp.getViewport().setOpaque(false);
        send_context_jsp.setBounds(0, 0, 650, 180);
        send_context.add(send_context_jsp);

        chat_frame.add(send_context);
        chat_frame.setVisible(true);

        send_context_jtp.requestFocus();

        new Thread() {
            public void run() {
                DataInputStream din = handler.getDin();

                while (true) {
                    try {
                        int action = din.readInt();
                        if (action == 21) {
                            String message = din.readUTF();
                            try {
                                doc.insertString(doc.getLength(), message, new SimpleAttributeSet());
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
