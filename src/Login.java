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
        //���ò��ɸı��С
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("ͼ��.png").getImage());
        //ȥ�߿�
        frame.setUndecorated(true);
        frame.setLayout(null);

        Image image=new ImageIcon("��¼����.jpg").getImage().getScaledInstance(450,350,JFrame.DO_NOTHING_ON_CLOSE);
        JLabel background=new JLabel(new ImageIcon(image));
        background.setBounds(0,0,450,350);
        //��������ǩ�������м��
        frame.getLayeredPane().add(background, Integer.valueOf(Integer.MIN_VALUE));
        JPanel panel=(JPanel) frame.getContentPane();
        //�����ϲ�����͸��
        panel.setOpaque(false);

        JButton minimize=new JButton(new ImageIcon("��С��.png"));
        //����ť����͸��
        minimize.setContentAreaFilled(false);
        //����ť����Ϊ�ޱ߿�
        minimize.setBorderPainted(false);
        minimize.setBounds(370,0,30,30);
        minimize.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //��С������
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
                //ʹ��ť���ֺ�ɫ
                minimize.setContentAreaFilled(true);
                minimize.setBackground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimize.setContentAreaFilled(false);
            }
        });
        frame.add(minimize);

        JButton close=new JButton(new ImageIcon("�ر�.png"));
        //����ť����͸��
        close.setContentAreaFilled(false);
        //����ť����Ϊ�ޱ߿�
        close.setBorderPainted(false);
        close.setBounds(400,0,30,30);
        close.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //�رմ���
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
                //ʹ��ť���ֺ�ɫ
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
