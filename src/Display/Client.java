package Display;

import Function.InteractWithServer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Client {

    private final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
    private final DefaultMutableTreeNode friend = new DefaultMutableTreeNode("�ҵĺ���");
    private final DefaultMutableTreeNode stranger = new DefaultMutableTreeNode("İ����");
    private final DefaultMutableTreeNode blacklist = new DefaultMutableTreeNode("������");
    private JTree contacts_tree = new JTree(root);

    private PeopleNode myself;
    private ArrayList<PeopleNode> people_list;
    private ConcurrentHashMap<String, PeopleNode> peopleMap = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Chat> chatMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> messageMap = new ConcurrentHashMap<>();

    private final InteractWithServer handler;


    public Client(InteractWithServer handler, ArrayList<PeopleNode> people_list, PeopleNode myself) {
        this.handler = handler;
        this.people_list = people_list;
        this.myself = myself;
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
        head.setIcon(new ImageIcon(myself.getImageIcon().getImage().getScaledInstance(100, 100,
                JFrame.DO_NOTHING_ON_CLOSE)));
        head.setBounds(15, 15, 100, 100);
        panel.add(head);
        head.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handler.Clink_ChangeInformation_Operation();
                }
            }

        });
        jf.setIconImage(myself.getImageIcon().getImage());

        // �س�
        JLabel name = new JLabel(myself.getName());
        name.setForeground(Color.YELLOW);
        name.setBounds(150, 15, 60, 30);
        panel.add(name);

        // ����״̬
        String[] item = new String[]{"����", "����", "����", "�������"};
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
        JLabel says = new JLabel(myself.getSays());
        says.setForeground(Color.YELLOW);
        says.setBounds(150, 70, 100, 30);
        panel.add(says);

        // ����QQ����
        JTextField findNumber = new JTextField();
        findNumber.setBounds(5, 135, 230, 30);
        panel.add(findNumber);

        // ��Ӻ��ѹ���
        JButton jbu = new JButton("��Ӻ���");
        jbu.addActionListener(e -> {
            handler.Clink_AddFriend_Operation();
        });
        jbu.setBounds(240, 135, 90, 30);
        jbu.setOpaque(false);
        panel.add(jbu);

        // ����ѡ�͸����������ڴ���֮ǰ��
        UIManager.put("TabbedPane.contentOpaque", false);
        // ����ѡ�
        JTabbedPane tab = new JTabbedPane();
        // �����������
        JScrollPane jsp1 = new JScrollPane(setContactsTree());
        this.AddJSPaneToTab(tab, jsp1, "��ϵ��");

        JScrollPane jsp2 = new JScrollPane(SetGroupTree());
        this.AddJSPaneToTab(tab, jsp2, "Ⱥ��");

        tab.setBounds(0, 180, 330, 500);

        panel.add(tab);

        jf.setVisible(true);

        // �����������������Ϣ
        new Thread() {
            @Override
            public void run() {

            }
        }.start();
    }

    // ʹ�ڵ�չ��
    public void expandTree(JTree jtree) {
        TreeNode root = (TreeNode) jtree.getModel().getRoot();
        jtree.expandPath(new TreePath(root));
    }

    // ����ϵ�˵����������Ժ͹�������
    public JTree setContactsTree() {
        // ���һ��չ��
        contacts_tree.setToggleClickCount(1);
        root.add(friend);
        root.add(stranger);
        root.add(blacklist);
        for (int i = 0; i < people_list.size(); i++) {
            PeopleNode node = people_list.get(i);
            if (node.getKind().equals("�ҵĺ���")) {
                friend.add(node);
            }
            peopleMap.put(node.getAccount(), node);
        }
        // ���ظ��ڵ�
        contacts_tree.setRootVisible(false);
        // չ����
        this.expandTree(contacts_tree);
        // ����͸��
        contacts_tree.setOpaque(false);
        // ���ظ���
        contacts_tree.setShowsRootHandles(false);
        // �Ը����ڵ���и��Ի�����
        contacts_tree.setCellRenderer(new DefaultTreeCellRenderer() {

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
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    PeopleNode people = (PeopleNode) node;
                    setIcon(new ImageIcon(people.getImageIcon().getImage().getScaledInstance(60, 60,
                            JFrame.DO_NOTHING_ON_CLOSE)));
                    setFont(new Font("����", Font.BOLD, 15));
                } else {
                    setFont(new Font("����", Font.BOLD, 20));
                }

                setBackgroundNonSelectionColor(new Color(255, 255, 255, 175));
                setBackgroundSelectionColor(new Color(255, 255, 255, 175));
                setTextSelectionColor(Color.RED);
                setTextNonSelectionColor(Color.BLACK);

                return this;
            }

        });

        // ʹ�ڵ�����Ӧ��Ӧ����
        contacts_tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object node = contacts_tree.getLastSelectedPathComponent();
                String str = node.toString();
                if (!str.equals("�ҵĺ���") && !str.equals("������") && !str.equals("İ����") && e.getClickCount() == 2) {
                    //������κ��ѣ������Ի���
                    PeopleNode people = (PeopleNode) node;
                    Chat chat = new Chat(handler, people.getAccount());
                    chat.Open();
                    chatMap.put(people.getAccount(), chat);

                }
            }

        });

        return contacts_tree;
    }

    //Ⱥ���������ʱδ�༭
    public JTree SetGroupTree() {
        return new JTree();
    }

    // �����������ӵ�ѡ���
    public void AddJSPaneToTab(JTabbedPane tab, JScrollPane jsp, String str) {
        // �������������͸��
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
        jsp.setName(str);
        // ��ʾ������
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tab.add(jsp);
    }

}
