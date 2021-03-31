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
    private final DefaultMutableTreeNode friend = new DefaultMutableTreeNode("我的好友");
    private final DefaultMutableTreeNode stranger = new DefaultMutableTreeNode("陌生人");
    private final DefaultMutableTreeNode blacklist = new DefaultMutableTreeNode("黑名单");
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
        jf.setTitle("QQ界面");
        jf.setSize(350, 750);
        jf.setLocation(1500, 100);
        // 窗口大小不可改变
        jf.setResizable(false);
        // 自定义布局
        jf.setLayout(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 背景图片
        JLabel back = new JLabel();
        Image background = new ImageIcon("background.jpg").getImage()
                .getScaledInstance(350, 750, JFrame.DO_NOTHING_ON_CLOSE);
        back.setIcon(new ImageIcon(background));
        // 设置布局位置
        back.setBounds(0, 0, 350, 750);
        // JFrame有三层面板，将背景图片放在最底层
        jf.getLayeredPane().add(back, Integer.valueOf(Integer.MIN_VALUE));

        JPanel panel = (JPanel) jf.getContentPane();
        // 将放置组件的内容面板设置透明
        panel.setOpaque(false);

        // 头像
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

        // 呢称
        JLabel name = new JLabel(myself.getName());
        name.setForeground(Color.YELLOW);
        name.setBounds(150, 15, 60, 30);
        panel.add(name);

        // 在线状态
        String[] item = new String[]{"在线", "离线", "隐身", "请勿打扰"};
        var box = new JComboBox(item);
        box.setBounds(150, 45, 80, 20);
        box.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                handler.Clink_OnlineState_Operation();
                System.out.println(e.getItem());
            }
        });
        panel.add(box);

        // 个性签名
        JLabel says = new JLabel(myself.getSays());
        says.setForeground(Color.YELLOW);
        says.setBounds(150, 70, 100, 30);
        panel.add(says);

        // 查找QQ号码
        JTextField findNumber = new JTextField();
        findNumber.setBounds(5, 135, 230, 30);
        panel.add(findNumber);

        // 添加好友功能
        JButton jbu = new JButton("添加好友");
        jbu.addActionListener(e -> {
            handler.Clink_AddFriend_Operation();
        });
        jbu.setBounds(240, 135, 90, 30);
        jbu.setOpaque(false);
        panel.add(jbu);

        // 设置选项卡透明（需放置在创建之前）
        UIManager.put("TabbedPane.contentOpaque", false);
        // 创建选项卡
        JTabbedPane tab = new JTabbedPane();
        // 创建滚动面板
        JScrollPane jsp1 = new JScrollPane(setContactsTree());
        this.AddJSPaneToTab(tab, jsp1, "联系人");

        JScrollPane jsp2 = new JScrollPane(SetGroupTree());
        this.AddJSPaneToTab(tab, jsp2, "群组");

        tab.setBounds(0, 180, 330, 500);

        panel.add(tab);

        jf.setVisible(true);

        // 处理服务器发来的消息
        new Thread() {
            @Override
            public void run() {

            }
        }.start();
    }

    // 使节点展开
    public void expandTree(JTree jtree) {
        TreeNode root = (TreeNode) jtree.getModel().getRoot();
        jtree.expandPath(new TreePath(root));
    }

    // 对联系人的树进行属性和功能设置
    public JTree setContactsTree() {
        // 点击一次展开
        contacts_tree.setToggleClickCount(1);
        root.add(friend);
        root.add(stranger);
        root.add(blacklist);
        for (int i = 0; i < people_list.size(); i++) {
            PeopleNode node = people_list.get(i);
            if (node.getKind().equals("我的好友")) {
                friend.add(node);
            }
            peopleMap.put(node.getAccount(), node);
        }
        // 隐藏根节点
        contacts_tree.setRootVisible(false);
        // 展开树
        this.expandTree(contacts_tree);
        // 设置透明
        contacts_tree.setOpaque(false);
        // 隐藏根柄
        contacts_tree.setShowsRootHandles(false);
        // 对各个节点进行个性化设置
        contacts_tree.setCellRenderer(new DefaultTreeCellRenderer() {

            // 收起和展开图片设置为三角形
            final ImageIcon icon1 = new ImageIcon("收起.png");
            final ImageIcon icon2 = new ImageIcon("展开.png");

            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

                if (!expanded) {
                    setIcon(icon1);
                } else {
                    setIcon(icon2);
                }

                String str = value.toString();
                if (!str.equals("我的好友") && !str.equals("黑名单") && !str.equals("陌生人")) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    PeopleNode people = (PeopleNode) node;
                    setIcon(new ImageIcon(people.getImageIcon().getImage().getScaledInstance(60, 60,
                            JFrame.DO_NOTHING_ON_CLOSE)));
                    setFont(new Font("宋体", Font.BOLD, 15));
                } else {
                    setFont(new Font("宋体", Font.BOLD, 20));
                }

                setBackgroundNonSelectionColor(new Color(255, 255, 255, 175));
                setBackgroundSelectionColor(new Color(255, 255, 255, 175));
                setTextSelectionColor(Color.RED);
                setTextNonSelectionColor(Color.BLACK);

                return this;
            }

        });

        // 使节点能响应相应操作
        contacts_tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object node = contacts_tree.getLastSelectedPathComponent();
                String str = node.toString();
                if (!str.equals("我的好友") && !str.equals("黑名单") && !str.equals("陌生人") && e.getClickCount() == 2) {
                    //点击两次好友，弹出对话框
                    PeopleNode people = (PeopleNode) node;
                    Chat chat = new Chat(handler, people.getAccount());
                    chat.Open();
                    chatMap.put(people.getAccount(), chat);

                }
            }

        });

        return contacts_tree;
    }

    //群组的树，暂时未编辑
    public JTree SetGroupTree() {
        return new JTree();
    }

    // 将滚动面板添加的选项卡里
    public void AddJSPaneToTab(JTabbedPane tab, JScrollPane jsp, String str) {
        // 将滚动面板设置透明
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
        jsp.setName(str);
        // 显示滚动条
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        tab.add(jsp);
    }

}
