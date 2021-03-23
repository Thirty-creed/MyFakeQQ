package Display;

import Function.OperationWithServer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Client {

    DefaultMutableTreeNode root = new DefaultMutableTreeNode();
    DefaultMutableTreeNode friend = new DefaultMutableTreeNode("�ҵĺ���");
    DefaultMutableTreeNode stranger = new DefaultMutableTreeNode("İ����");
    DefaultMutableTreeNode blackname = new DefaultMutableTreeNode("������");
    DefaultMutableTreeNode bl = new DefaultMutableTreeNode("����");

    JTree jtree = new JTree(root);

    OperationWithServer handler;

    public Client(OperationWithServer handler) {
        this.handler = handler;
    }

    public void expandTree(JTree jtree) {
        TreeNode root = (TreeNode) jtree.getModel().getRoot();
        jtree.expandPath(new TreePath(root));
    }

    public JTree setContactsTree() {
        // ���һ��չ��
        jtree.setToggleClickCount(1);
        jtree.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //������κ��ѣ������Ի���
                    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();
                    if (treeNode.toString().equals("����")) {
                        new Chat(handler).Open();
                    }
                }
                //�Ҽ��������
                if (e.getButton() == MouseEvent.BUTTON3) {
//                    System.out.println("===");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        root.add(friend);
        friend.add(bl);
        root.add(stranger);
        root.add(blackname);
        // ���ظ��ڵ�
        jtree.setRootVisible(false);
        // չ����
        expandTree(jtree);
        // ����͸��
        jtree.setOpaque(false);
        // ���ظ���
        jtree.setShowsRootHandles(false);
        jtree.setCellRenderer(new DefaultTreeCellRenderer() {

            // �����չ��ͼƬ����Ϊ������
            final ImageIcon icon1 = new ImageIcon("����.png");
            final ImageIcon icon2 = new ImageIcon("չ��.png");

            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if (!expanded) {
                    setIcon(icon1);
                } else {
                    setIcon(icon2);
                }
                String str = value.toString();
                if (!str.equals("�ҵĺ���") && !str.equals("������") && !str.equals("İ����")) {
                    setIcon(new ImageIcon("����.png"));
                }
                setBackgroundNonSelectionColor(new Color(255, 255, 255, 150));
                setTextSelectionColor(Color.RED);
                setTextNonSelectionColor(Color.BLACK);
                setFont(new Font("����", Font.BOLD, 20));
                return this;
            }

        });
        return jtree;
    }

    public void AddJSPane(JTabbedPane table, JScrollPane jsp, String str) {
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
        jsp.setName(str);
        // ��ʾ������
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        table.add(jsp);
    }

    public void UI() {
        JFrame jf = new JFrame();
        jf.setTitle("QQ����");
        jf.setSize(350, 750);
        jf.setLocation(1500, 100);
        // ���ڴ�С���ɸı�
        jf.setResizable(false);
        // �Զ��岼��
        jf.setLayout(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ����ͼƬ
        JLabel back = new JLabel();
        Image background = new ImageIcon("background.jpg").getImage()
                .getScaledInstance(350, 750, JFrame.DO_NOTHING_ON_CLOSE);
        back.setIcon(new ImageIcon(background));
        // ���ò���λ��
        back.setBounds(0, 0, 350, 750);
        // JFrame��������壬������ͼƬ������ײ�
        jf.getLayeredPane().add(back, Integer.valueOf(Integer.MIN_VALUE));

        JPanel panel = (JPanel) jf.getContentPane();
        // ����������������������͸��
        panel.setOpaque(false);

        // ͷ��
        JLabel head = new JLabel();
        Image img = new ImageIcon("ͷ��.jpg").getImage().getScaledInstance(100, 100,
                JFrame.DO_NOTHING_ON_CLOSE);
        head.setIcon(new ImageIcon(img));
        head.setBounds(15, 15, 100, 100);
        panel.add(head);
        head.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handler.Clink_ChangeInformation_Operation();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        jf.setIconImage(img);
        // �س�
        JLabel name = new JLabel("�ǳ�");
        name.setForeground(Color.YELLOW);
        name.setBounds(150, 15, 60, 30);
        panel.add(name);

        // ����״̬
        String[] item= new String[]{"����", "����", "����", "�������"};
        var box = new JComboBox(item);

        box.setBounds(150, 45, 80, 20);
        box.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                handler.Clink_OnlineState_Operation();
                System.out.println(e.getItem());
            }
        });
        panel.add(box);

        // ����ǩ��
        JLabel says = new JLabel("����ǩ��");
        says.setForeground(Color.YELLOW);
        says.setBounds(150, 70, 100, 30);
        panel.add(says);

        // ����QQ����
        JTextField findNumber = new JTextField();
        findNumber.setBounds(5, 135, 230, 30);
        panel.add(findNumber);

        // ���Ӻ��ѹ���
        JButton jbu = new JButton("���Ӻ���");
        jbu.addActionListener(e -> {
            handler.Clink_AddFriend_Operation();
//				friend.add(bl1);
//				jtree.updateUI();
        });
        jbu.setBounds(240, 135, 90, 30);
        jbu.setOpaque(false);
        panel.add(jbu);

        // ����ѡ�͸����������ڴ���֮ǰ��
        UIManager.put("TabbedPane.contentOpaque", false);
        // ����ѡ�
        JTabbedPane table = new JTabbedPane();
        // �����������
        JScrollPane jsp1 = new JScrollPane(setContactsTree());
        AddJSPane(table, jsp1, "��ϵ��");

        JScrollPane jsp2 = new JScrollPane();
        AddJSPane(table, jsp2, "Ⱥ��");

        table.setBounds(0, 180, 330, 500);

        panel.add(table);

        jf.setVisible(true);

    }
}