import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Client {

    DefaultMutableTreeNode root = new DefaultMutableTreeNode();
    DefaultMutableTreeNode friend = new DefaultMutableTreeNode("我的好友");
    DefaultMutableTreeNode stranger = new DefaultMutableTreeNode("陌生人");
    DefaultMutableTreeNode blackname = new DefaultMutableTreeNode("黑名单");
    DefaultMutableTreeNode bl = new DefaultMutableTreeNode("朋友");
    DefaultMutableTreeNode bl1 = new DefaultMutableTreeNode("朋友");
    JTree jtree = new JTree(root);

//    public static void main(String[] args) {
//        Client QQ = new Client();
//        QQ.UI();
//    }

    public void expandTree(JTree jtree) {
        TreeNode root = (TreeNode) jtree.getModel().getRoot();
        jtree.expandPath(new TreePath(root));
    }

    public JTree setContactsTree() {
        // 点击一次展开
        jtree.setToggleClickCount(1);
        jtree.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2){
                    DefaultMutableTreeNode treeNode=(DefaultMutableTreeNode)jtree.getLastSelectedPathComponent();
                    if(treeNode.toString().equals("朋友")){
                        new Chat().Open();
                    }
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
        // 隐藏根节点
        jtree.setRootVisible(false);
        // 展开树
        expandTree(jtree);
        // 设置透明
        jtree.setOpaque(false);
        // 隐藏根柄
        jtree.setShowsRootHandles(false);
        jtree.setCellRenderer(new DefaultTreeCellRenderer() {

            // 收起和展开图片设置为三角形
            ImageIcon icon1 = new ImageIcon("收起.png");
            ImageIcon icon2 = new ImageIcon("展开.png");

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
                    setIcon(new ImageIcon("好友.png"));
                }
                setBackgroundNonSelectionColor(new Color(255, 255, 255, 150));
                setTextSelectionColor(Color.RED);
                setTextNonSelectionColor(Color.BLACK);
                setFont(new Font("宋体", 1, 20));
                return this;
            }

        });
        return jtree;
    }

    public void AddJSPane(JTabbedPane table, JScrollPane jsp, String str) {
        jsp.getViewport().setOpaque(false);
        jsp.setOpaque(false);
        jsp.setName(str);
        // 显示滚动条
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        table.add(jsp);
    }

    public void UI() {

//		JFrame.setDefaultLookAndFeelDecorated(true);
//		try {
////			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
//			String lookAndFeel ="com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//			UIManager.setLookAndFeel(lookAndFeel);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}
        JFrame jf = new JFrame();
        jf.setTitle("QQ界面");
        jf.setSize(350, 750);
        jf.setLocation(1500, 100);
        // 窗口大小不可改变
        jf.setResizable(false);
        // 自定义布局
        jf.setLayout(null);
        jf.setDefaultCloseOperation(3);

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
        Image img = new ImageIcon("头像.jpg").getImage().getScaledInstance(100, 100,
                JFrame.DO_NOTHING_ON_CLOSE);
        head.setIcon(new ImageIcon(img));
        head.setBounds(15, 15, 100, 100);
        panel.add(head);
        jf.setIconImage(img);
        // 呢称
        JLabel name = new JLabel("昵称");
        name.setForeground(Color.YELLOW);
        name.setBounds(150, 15, 60, 30);
        panel.add(name);

        // 在线状态
        JComboBox box = new JComboBox();
        box.addItem("在线");
        box.addItem("离线");
        box.addItem("隐身");
        box.addItem("请勿打扰");
        box.setBounds(150, 45, 80, 20);
        box.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                System.out.println(e.getItem());
            }
        });
        panel.add(box);

        // 个性签名
        JLabel says = new JLabel("个性签名");
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
//				friend.add(bl1);
//				jtree.updateUI();
        });
        jbu.setBounds(240, 135, 90, 30);
        jbu.setOpaque(false);
        panel.add(jbu);

        // 设置选项卡透明（需放置在创建之前）
        UIManager.put("TabbedPane.contentOpaque", false);
        // 创建选项卡
        JTabbedPane table = new JTabbedPane();
        // 创建滚动面板
        JScrollPane jsp1 = new JScrollPane(setContactsTree());
        AddJSPane(table, jsp1, "联系人");

        JScrollPane jsp2 = new JScrollPane();
        AddJSPane(table, jsp2, "群组");

        table.setBounds(0, 180, 330, 500);

        panel.add(table);

        jf.setVisible(true);

    }
}
