package gui;

import java.lang.management.ManagementFactory;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import database.DBConPool;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Icon;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.Alignment;
import java.awt.Container;
import javax.swing.GroupLayout;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import java.awt.Component;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.LayoutManager;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.Color;
import javax.swing.ImageIcon;
import LzjSetting.Game;
import java.awt.EventQueue;
import tools.FileoutputUtil;
import javax.swing.UIManager;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;
import javax.swing.JDialog;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.lang.management.MemoryMXBean;
import client.inventory.IItem;
import javax.swing.JFrame;

public class 特殊控制台 extends JFrame{
    private static 特殊控制台 instance = new 特殊控制台();
    private int 账号ID;
    private int 角色ID;
    private int 怪物爆物序号;
    private int 全局爆物序号;
    private int 商店序号;
    private int 商店物品序号;
    private IItem 读取装备;
    private static MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    private static long startRunTime = System.currentTimeMillis();
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel2;
    private JLabel jLabel221;
    private JLabel jLabel222;
    private JLabel jLabel223;
    private JLabel jLabel224;
    private JLabel jLabel225;
    private JLabel jLabel226;
    private JLabel jLabel227;
    private JLabel jLabel228;
    private JLabel jLabel229;
    private JLabel jLabel230;
    private JLabel jLabel231;
    private JLabel jLabel3;
    private JLabel jLabel38;
    private JLabel jLabel39;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel11;
    private JPanel jPanel16;
    private JPanel jPanel17;
    private JPanel jPanel18;
    private JPanel jPanel19;
    private JPanel jPanel2;
    private JPanel jPanel29;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private JScrollPane jScrollPane81;
    private JTabbedPane jTabbedPane1;
    private JTabbedPane jTabbedPane4;
    public static JTabbedPane 主界面菜单;
    private JTextField 交易币个数;
    private static JTextField 交易币金额;
    private static JTextField 伤害高于次数值;
    private JPanel 充值卡;
    private JTable 充值卡信息;
    private JTextField 充值赞助卡个数;
    private JTextField 充值赞助金额;
    private JButton 初始化功能;
    private JButton 刷新充值卡信息;
    private static JToggleButton 扣除21E伤害;
    private JTextField 抵用充值卡个数;
    private JTextField 抵用券充值卡金额;
    private JTextField 点券充值卡个数;
    private JTextField 点券充值卡金额1;
    private JButton 生成交易币;
    private JButton 生成充值赞助卡;
    private JButton 生成抵用券充值卡1;
    private JButton 生成点券充值卡1;
    private JButton 生成礼包1;
    private JTextField 礼包个数;
    private JTextField 礼包编号;
    private static JToggleButton 自定义伤害加成开关;
    private static JTextField 自定义伤害加成道具代码;
    private static JToggleButton 自定义伤害段数显示;
    private static JToggleButton 自定义伤害气泡显示;
    private static JToggleButton 自定义伤害黄字喇叭显示;
    private static JTextField 自定义力量加成比例;
    private static JTextField 自定义敏捷加成比例;
    private static JTextField 自定义智力加成比例;
    private static JTextField 自定义物攻加成比例;
    private static JTextField 自定义物防加成比例;
    private static JTextField 自定义血量加成比例;
    private static JTextField 自定义运气加成比例;
    private static JTextField 自定义魔攻加成比例;
    private static JTextField 自定义魔量加成比例;
    private static JTextField 自定义魔防加成比例;
    private static JToggleButton 道具加成自定义伤害开关;
    private static JTextField 邀请人百分比获取;
    
    public static final 特殊控制台 getInstance() {
        return 特殊控制台.instance;
    }
    
    public static void main(final String[] args) throws Exception {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.generalNoTranslucencyShadow;
            UIManager.put("RootPane.setupButtonVisible", Boolean.valueOf(false));
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        }
        catch (Exception e) {
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "]" + e);
        }
        EventQueue.invokeLater((Runnable)new Runnable() {
            @Override
            public void run() {
                new 特殊控制台().setVisible(true);
            }
        });
    }
    
    public 特殊控制台() {
        this.账号ID = -1;
        this.角色ID = -1;
        this.怪物爆物序号 = -1;
        this.全局爆物序号 = -1;
        this.商店序号 = -1;
        this.商店物品序号 = -1;
        this.读取装备 = null;
        this.setTitle("【" + Game.服务端名称 + "特殊控制台】 - 新春贺岁版[可关闭]");
        this.initComponents();
        final ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("image/冒险岛.png"));
        this.setIconImage(icon.getImage());
    }
    
    private void initComponents() {
        特殊控制台.主界面菜单 = new JTabbedPane();
        this.jPanel1 = new JPanel();
        this.jTabbedPane1 = new JTabbedPane();
        this.jPanel6 = new JPanel();
        this.充值卡 = new JPanel();
        this.jScrollPane81 = new JScrollPane();
        this.充值卡信息 = new JTable();
        this.jPanel7 = new JPanel();
        this.jLabel224 = new JLabel();
        this.jLabel221 = new JLabel();
        this.点券充值卡个数 = new JTextField();
        this.点券充值卡金额1 = new JTextField();
        this.生成点券充值卡1 = new JButton();
        this.生成抵用券充值卡1 = new JButton();
        this.抵用充值卡个数 = new JTextField();
        this.抵用券充值卡金额 = new JTextField();
        this.jLabel225 = new JLabel();
        this.jLabel222 = new JLabel();
        this.礼包编号 = new JTextField();
        this.礼包个数 = new JTextField();
        this.jLabel223 = new JLabel();
        this.jLabel226 = new JLabel();
        this.生成礼包1 = new JButton();
        this.生成充值赞助卡 = new JButton();
        this.充值赞助卡个数 = new JTextField();
        this.充值赞助金额 = new JTextField();
        this.jLabel229 = new JLabel();
        this.jLabel228 = new JLabel();
        特殊控制台.交易币金额 = new JTextField();
        this.jLabel230 = new JLabel();
        this.jLabel231 = new JLabel();
        this.交易币个数 = new JTextField();
        this.生成交易币 = new JButton();
        this.刷新充值卡信息 = new JButton();
        this.jLabel227 = new JLabel();
        特殊控制台.邀请人百分比获取 = new JTextField();
        this.jPanel2 = new JPanel();
        this.jPanel4 = new JPanel();
        this.jPanel8 = new JPanel();
        this.jPanel9 = new JPanel();
        this.jLabel38 = new JLabel();
        特殊控制台.自定义伤害加成道具代码 = new JTextField();
        特殊控制台.道具加成自定义伤害开关 = new JToggleButton();
        this.jLabel2 = new JLabel();
        this.jLabel1 = new JLabel();
        特殊控制台.自定义伤害加成开关 = new JToggleButton();
        this.jLabel39 = new JLabel();
        特殊控制台.伤害高于次数值 = new JTextField();
        this.jPanel10 = new JPanel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel10 = new JLabel();
        this.jLabel11 = new JLabel();
        this.jLabel12 = new JLabel();
        this.jLabel13 = new JLabel();
        特殊控制台.自定义力量加成比例 = new JTextField();
        特殊控制台.自定义敏捷加成比例 = new JTextField();
        特殊控制台.自定义智力加成比例 = new JTextField();
        特殊控制台.自定义运气加成比例 = new JTextField();
        特殊控制台.自定义物攻加成比例 = new JTextField();
        特殊控制台.自定义魔攻加成比例 = new JTextField();
        特殊控制台.自定义血量加成比例 = new JTextField();
        特殊控制台.自定义魔量加成比例 = new JTextField();
        特殊控制台.自定义物防加成比例 = new JTextField();
        特殊控制台.自定义魔防加成比例 = new JTextField();
        this.初始化功能 = new JButton();
        this.jPanel11 = new JPanel();
        this.jLabel15 = new JLabel();
        特殊控制台.自定义伤害气泡显示 = new JToggleButton();
        this.jLabel16 = new JLabel();
        特殊控制台.自定义伤害段数显示 = new JToggleButton();
        this.jLabel17 = new JLabel();
        特殊控制台.扣除21E伤害 = new JToggleButton();
        this.jLabel18 = new JLabel();
        特殊控制台.自定义伤害黄字喇叭显示 = new JToggleButton();
        this.jPanel5 = new JPanel();
        this.jLabel9 = new JLabel();
        this.jLabel14 = new JLabel();
        this.jPanel3 = new JPanel();
        this.jTabbedPane4 = new JTabbedPane();
        this.jPanel16 = new JPanel();
        this.jPanel17 = new JPanel();
        this.jPanel18 = new JPanel();
        this.jPanel19 = new JPanel();
        this.jPanel29 = new JPanel();
        this.setBackground(new Color(255, 255, 255));
        this.setCursor(new Cursor(0));
        this.setIconImages(null);
        this.setMinimumSize(new Dimension(1366, 768));
        this.setResizable(false);
        this.getContentPane().setLayout((LayoutManager)new AbsoluteLayout());
        this.充值卡.setBorder((Border)BorderFactory.createTitledBorder("卡单列表"));
        this.充值卡.setLayout((LayoutManager)new AbsoluteLayout());
        this.充值卡信息.setBorder((Border)BorderFactory.createCompoundBorder());
        this.充值卡信息.setFont(new Font("宋体", 0, 18));
        this.充值卡信息.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "卡号", "类型", "数额", "礼包" }) {
            boolean[] canEdit = { false, false, false, false };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.充值卡信息.setCursor(new Cursor(0));
        this.jScrollPane81.setViewportView((Component)this.充值卡信息);
        this.充值卡.add((Component)this.jScrollPane81, new AbsoluteConstraints(10, 30, 620, 500));
        this.jPanel7.setBorder((Border)BorderFactory.createTitledBorder("操作功能"));
        this.jLabel224.setFont(new Font("幼圆", 0, 14));
        this.jLabel224.setText("点券充值卡金额；");
        this.jLabel221.setFont(new Font("幼圆", 0, 14));
        this.jLabel221.setText("点券充值卡个数；");
        this.点券充值卡个数.setMaximumSize(new Dimension(137, 27));
        this.点券充值卡个数.setMinimumSize(new Dimension(137, 27));
        this.点券充值卡金额1.setMaximumSize(new Dimension(137, 27));
        this.点券充值卡金额1.setMinimumSize(new Dimension(137, 27));
        this.生成点券充值卡1.setFont(new Font("幼圆", 0, 15));
        this.生成点券充值卡1.setText("生成");
        this.生成点券充值卡1.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.生成点券充值卡1ActionPerformed(evt);
            }
        });
        this.生成抵用券充值卡1.setFont(new Font("幼圆", 0, 15));
        this.生成抵用券充值卡1.setText("生成");
        this.生成抵用券充值卡1.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.生成抵用券充值卡1ActionPerformed(evt);
            }
        });
        this.抵用充值卡个数.setMaximumSize(new Dimension(137, 27));
        this.抵用充值卡个数.setMinimumSize(new Dimension(137, 27));
        this.抵用券充值卡金额.setMaximumSize(new Dimension(137, 27));
        this.抵用券充值卡金额.setMinimumSize(new Dimension(137, 27));
        this.jLabel225.setFont(new Font("幼圆", 0, 14));
        this.jLabel225.setText("抵用充值卡金额；");
        this.jLabel222.setFont(new Font("幼圆", 0, 14));
        this.jLabel222.setText("抵用充值卡个数；");
        this.礼包编号.setMaximumSize(new Dimension(137, 27));
        this.礼包编号.setMinimumSize(new Dimension(137, 27));
        this.礼包个数.setMaximumSize(new Dimension(137, 27));
        this.礼包个数.setMinimumSize(new Dimension(137, 27));
        this.jLabel223.setFont(new Font("幼圆", 0, 14));
        this.jLabel223.setText("生成个数；");
        this.jLabel226.setFont(new Font("幼圆", 0, 14));
        this.jLabel226.setText("生成礼包；");
        this.生成礼包1.setFont(new Font("幼圆", 0, 15));
        this.生成礼包1.setText("生成");
        this.生成礼包1.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.生成礼包1ActionPerformed(evt);
            }
        });
        this.生成充值赞助卡.setFont(new Font("幼圆", 0, 15));
        this.生成充值赞助卡.setText("生成");
        this.生成充值赞助卡.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.生成充值赞助卡ActionPerformed(evt);
            }
        });
        this.充值赞助卡个数.setMaximumSize(new Dimension(137, 27));
        this.充值赞助卡个数.setMinimumSize(new Dimension(137, 27));
        this.充值赞助金额.setMaximumSize(new Dimension(137, 27));
        this.充值赞助金额.setMinimumSize(new Dimension(137, 27));
        this.jLabel229.setFont(new Font("幼圆", 0, 14));
        this.jLabel229.setText("生成余额累计卡金额；");
        this.jLabel228.setFont(new Font("幼圆", 0, 14));
        this.jLabel228.setText("生成余额累计卡个数；");
        特殊控制台.交易币金额.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.交易币金额ActionPerformed(evt);
            }
        });
        this.jLabel230.setFont(new Font("幼圆", 0, 14));
        this.jLabel230.setText("生成交易币金额；");
        this.jLabel231.setFont(new Font("幼圆", 0, 14));
        this.jLabel231.setText("生成交易币个数；");
        this.交易币个数.setMaximumSize(new Dimension(137, 27));
        this.交易币个数.setMinimumSize(new Dimension(137, 27));
        this.生成交易币.setFont(new Font("幼圆", 0, 15));
        this.生成交易币.setText("生成");
        this.生成交易币.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.生成交易币ActionPerformed(evt);
            }
        });
        this.刷新充值卡信息.setFont(new Font("幼圆", 0, 15));
        this.刷新充值卡信息.setText("刷新充值卡信息");
        this.刷新充值卡信息.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.刷新充值卡信息ActionPerformed(evt);
            }
        });
        this.jLabel227.setFont(new Font("幼圆", 0, 14));
        this.jLabel227.setText("邀请人获取百分比数量");
        特殊控制台.邀请人百分比获取.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.邀请人百分比获取FocusLost(evt);
            }
        });
        final GroupLayout jPanel7Layout = new GroupLayout((Container)this.jPanel7);
        this.jPanel7.setLayout((LayoutManager)jPanel7Layout);
        jPanel7Layout.setHorizontalGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel7Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel7Layout.createSequentialGroup().addComponent((Component)this.jLabel224).addGap(38, 38, 38).addComponent((Component)this.jLabel221)).addGroup((Group)jPanel7Layout.createSequentialGroup().addComponent((Component)this.点券充值卡金额1, -2, 130, -2).addGap(20, 20, 20).addComponent((Component)this.点券充值卡个数, -2, 130, -2).addGap(20, 20, 20).addComponent((Component)this.生成点券充值卡1, -2, 130, -2)).addGroup((Group)jPanel7Layout.createSequentialGroup().addComponent((Component)this.jLabel225).addGap(38, 38, 38).addComponent((Component)this.jLabel222)).addGroup((Group)jPanel7Layout.createSequentialGroup().addComponent((Component)this.抵用券充值卡金额, -2, 130, -2).addGap(20, 20, 20).addComponent((Component)this.抵用充值卡个数, -2, 130, -2).addGap(20, 20, 20).addComponent((Component)this.生成抵用券充值卡1, -2, 130, -2)).addGroup((Group)jPanel7Layout.createSequentialGroup().addComponent((Component)this.jLabel226).addGap(80, 80, 80).addComponent((Component)this.jLabel223)).addGroup((Group)jPanel7Layout.createSequentialGroup().addComponent((Component)this.礼包编号, -2, 130, -2).addGap(20, 20, 20).addComponent((Component)this.礼包个数, -2, 130, -2).addGap(20, 20, 20).addComponent((Component)this.生成礼包1, -2, 130, -2)).addGroup((Group)jPanel7Layout.createSequentialGroup().addComponent((Component)this.jLabel229).addGap(10, 10, 10).addComponent((Component)this.jLabel228)).addGroup((Group)jPanel7Layout.createSequentialGroup().addComponent((Component)this.充值赞助金额, -2, 130, -2).addGap(20, 20, 20).addComponent((Component)this.充值赞助卡个数, -2, 130, -2).addGap(20, 20, 20).addComponent((Component)this.生成充值赞助卡, -2, 130, -2)).addGroup((Group)jPanel7Layout.createSequentialGroup().addComponent((Component)this.jLabel230).addGap(38, 38, 38).addComponent((Component)this.jLabel231)).addGroup((Group)jPanel7Layout.createSequentialGroup().addComponent((Component)特殊控制台.交易币金额, -2, 130, -2).addGap(20, 20, 20).addComponent((Component)this.交易币个数, -2, 130, -2).addGap(20, 20, 20).addComponent((Component)this.生成交易币, -2, 130, -2)).addComponent((Component)this.刷新充值卡信息, -2, 430, -2).addComponent((Component)this.jLabel227).addComponent((Component)特殊控制台.邀请人百分比获取, -2, 130, -2)).addContainerGap()));
        jPanel7Layout.setVerticalGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel7Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel224).addComponent((Component)this.jLabel221)).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.点券充值卡金额1, -2, 30, -2).addComponent((Component)this.点券充值卡个数, -2, 30, -2).addComponent((Component)this.生成点券充值卡1, -2, 30, -2)).addGap(10, 10, 10).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel225).addComponent((Component)this.jLabel222)).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.抵用券充值卡金额, -2, 30, -2).addComponent((Component)this.抵用充值卡个数, -2, 30, -2).addComponent((Component)this.生成抵用券充值卡1, -2, 30, -2)).addGap(10, 10, 10).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel226).addComponent((Component)this.jLabel223)).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.礼包编号, -2, 30, -2).addComponent((Component)this.礼包个数, -2, 30, -2).addComponent((Component)this.生成礼包1, -2, 30, -2)).addGap(10, 10, 10).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel229).addComponent((Component)this.jLabel228)).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.充值赞助金额, -2, 30, -2).addComponent((Component)this.充值赞助卡个数, -2, 30, -2).addComponent((Component)this.生成充值赞助卡, -2, 30, -2)).addGap(20, 20, 20).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel230).addComponent((Component)this.jLabel231)).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)特殊控制台.交易币金额, -2, 30, -2).addComponent((Component)this.交易币个数, -2, 30, -2).addComponent((Component)this.生成交易币, -2, 30, -2)).addGap(30, 30, 30).addComponent((Component)this.刷新充值卡信息).addGap(19, 19, 19).addComponent((Component)this.jLabel227).addGap(10, 10, 10).addComponent((Component)特殊控制台.邀请人百分比获取, -2, 30, -2).addContainerGap(-1, 32767)));
        final GroupLayout jPanel6Layout = new GroupLayout((Container)this.jPanel6);
        this.jPanel6.setLayout((LayoutManager)jPanel6Layout);
        jPanel6Layout.setHorizontalGroup((Group)jPanel6Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel6Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.充值卡, -2, 654, -2).addGap(18, 18, 18).addComponent((Component)this.jPanel7, -2, -1, -2).addContainerGap(299, 32767)));
        jPanel6Layout.setVerticalGroup((Group)jPanel6Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel6Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel6Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.充值卡, -1, 553, 32767).addComponent((Component)this.jPanel7, -1, -1, 32767)).addContainerGap(53, 32767)));
        this.jTabbedPane1.addTab("CDK生成器", (Component)this.jPanel6);
        this.jPanel4.setBorder((Border)BorderFactory.createTitledBorder("基础计算"));
        this.jPanel8.setBorder((Border)BorderFactory.createTitledBorder("自定义计算公式"));
        this.jPanel9.setBorder((Border)BorderFactory.createTitledBorder(""));
        this.jLabel38.setText("加成道具代码");
        特殊控制台.自定义伤害加成道具代码.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义伤害加成道具代码FocusLost(evt);
            }
        });
        特殊控制台.自定义伤害加成道具代码.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义伤害加成道具代码ActionPerformed(evt);
            }
        });
        特殊控制台.道具加成自定义伤害开关.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        特殊控制台.道具加成自定义伤害开关.setBorderPainted(false);
        特殊控制台.道具加成自定义伤害开关.setContentAreaFilled(false);
        特殊控制台.道具加成自定义伤害开关.setFocusPainted(false);
        特殊控制台.道具加成自定义伤害开关.setSelectedIcon((Icon)new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        特殊控制台.道具加成自定义伤害开关.addChangeListener((ChangeListener)new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                特殊控制台.this.道具加成自定义伤害开关StateChanged(evt);
            }
        });
        特殊控制台.道具加成自定义伤害开关.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.道具加成自定义伤害开关ActionPerformed(evt);
            }
        });
        this.jLabel2.setText("开启道具直接加成伤害");
        this.jLabel1.setText("开启自定义伤害加成");
        特殊控制台.自定义伤害加成开关.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        特殊控制台.自定义伤害加成开关.setBorderPainted(false);
        特殊控制台.自定义伤害加成开关.setContentAreaFilled(false);
        特殊控制台.自定义伤害加成开关.setFocusPainted(false);
        特殊控制台.自定义伤害加成开关.setSelectedIcon((Icon)new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        特殊控制台.自定义伤害加成开关.addChangeListener((ChangeListener)new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                特殊控制台.this.自定义伤害加成开关StateChanged(evt);
            }
        });
        特殊控制台.自定义伤害加成开关.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义伤害加成开关ActionPerformed(evt);
            }
        });
        this.jLabel39.setText("大于多少伤害开启加成");
        特殊控制台.伤害高于次数值.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.伤害高于次数值FocusLost(evt);
            }
        });
        final GroupLayout jPanel9Layout = new GroupLayout((Container)this.jPanel9);
        this.jPanel9.setLayout((LayoutManager)jPanel9Layout);
        jPanel9Layout.setHorizontalGroup((Group)jPanel9Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel9Layout.createSequentialGroup().addGap(21, 21, 21).addGroup((Group)jPanel9Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jLabel39, -1, -1, 32767).addComponent((Component)this.jLabel38, -1, -1, 32767).addComponent((Component)this.jLabel2, -1, -1, 32767).addComponent((Component)this.jLabel1, -1, -1, 32767)).addGap(31, 31, 31).addGroup((Group)jPanel9Layout.createParallelGroup(Alignment.CENTER).addComponent((Component)特殊控制台.道具加成自定义伤害开关, -2, 77, -2).addComponent((Component)特殊控制台.自定义伤害加成开关, -2, 77, -2).addComponent((Component)特殊控制台.自定义伤害加成道具代码, -2, 90, -2).addComponent((Component)特殊控制台.伤害高于次数值, -2, 90, -2)).addContainerGap(33, 32767)));
        jPanel9Layout.setVerticalGroup((Group)jPanel9Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel9Layout.createSequentialGroup().addGap(25, 25, 25).addGroup((Group)jPanel9Layout.createParallelGroup(Alignment.CENTER).addComponent((Component)特殊控制台.自定义伤害加成开关, -2, 30, -2).addComponent((Component)this.jLabel1)).addGap(18, 18, 18).addGroup((Group)jPanel9Layout.createParallelGroup(Alignment.CENTER).addComponent((Component)特殊控制台.道具加成自定义伤害开关, -2, 30, -2).addComponent((Component)this.jLabel2)).addGap(18, 18, 18).addGroup((Group)jPanel9Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel38).addComponent((Component)特殊控制台.自定义伤害加成道具代码, -2, -1, -2)).addGap(18, 18, 18).addGroup((Group)jPanel9Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel39).addComponent((Component)特殊控制台.伤害高于次数值, -2, -1, -2)).addGap(18, 18, 18)));
        this.jPanel10.setBorder((Border)BorderFactory.createTitledBorder(""));
        this.jLabel3.setText("力量加成比例:");
        this.jLabel4.setText("敏捷加成比例:");
        this.jLabel5.setText("智力加成比例:");
        this.jLabel6.setText("运气加成比例:");
        this.jLabel7.setText("物攻加成比例:");
        this.jLabel8.setText("魔攻加成比例:");
        this.jLabel10.setText("血量加成比例:");
        this.jLabel11.setText("魔量加成比例:");
        this.jLabel12.setText("物防加成比例:");
        this.jLabel13.setText("魔防加成比例:");
        特殊控制台.自定义力量加成比例.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义力量加成比例FocusLost(evt);
            }
        });
        特殊控制台.自定义力量加成比例.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义力量加成比例ActionPerformed(evt);
            }
        });
        特殊控制台.自定义敏捷加成比例.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义敏捷加成比例FocusLost(evt);
            }
        });
        特殊控制台.自定义敏捷加成比例.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义敏捷加成比例ActionPerformed(evt);
            }
        });
        特殊控制台.自定义智力加成比例.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义智力加成比例FocusLost(evt);
            }
        });
        特殊控制台.自定义智力加成比例.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义智力加成比例ActionPerformed(evt);
            }
        });
        特殊控制台.自定义运气加成比例.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义运气加成比例FocusLost(evt);
            }
        });
        特殊控制台.自定义运气加成比例.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义运气加成比例ActionPerformed(evt);
            }
        });
        特殊控制台.自定义物攻加成比例.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义物攻加成比例FocusLost(evt);
            }
        });
        特殊控制台.自定义物攻加成比例.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义物攻加成比例ActionPerformed(evt);
            }
        });
        特殊控制台.自定义魔攻加成比例.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义魔攻加成比例FocusLost(evt);
            }
        });
        特殊控制台.自定义魔攻加成比例.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义魔攻加成比例ActionPerformed(evt);
            }
        });
        特殊控制台.自定义血量加成比例.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义血量加成比例FocusLost(evt);
            }
        });
        特殊控制台.自定义血量加成比例.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义血量加成比例ActionPerformed(evt);
            }
        });
        特殊控制台.自定义魔量加成比例.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义魔量加成比例FocusLost(evt);
            }
        });
        特殊控制台.自定义魔量加成比例.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义魔量加成比例ActionPerformed(evt);
            }
        });
        特殊控制台.自定义物防加成比例.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义物防加成比例FocusLost(evt);
            }
        });
        特殊控制台.自定义物防加成比例.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义物防加成比例ActionPerformed(evt);
            }
        });
        特殊控制台.自定义魔防加成比例.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                特殊控制台.this.自定义魔防加成比例FocusLost(evt);
            }
        });
        特殊控制台.自定义魔防加成比例.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义魔防加成比例ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel10Layout = new GroupLayout((Container)this.jPanel10);
        this.jPanel10.setLayout((LayoutManager)jPanel10Layout);
        jPanel10Layout.setHorizontalGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel10Layout.createSequentialGroup().addGap(18, 18, 18).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component)this.jLabel13).addComponent((Component)this.jLabel12).addComponent((Component)this.jLabel11).addComponent((Component)this.jLabel10).addComponent((Component)this.jLabel8).addComponent((Component)this.jLabel7).addComponent((Component)this.jLabel6).addComponent((Component)this.jLabel5).addComponent((Component)this.jLabel4).addComponent((Component)this.jLabel3)).addPreferredGap(ComponentPlacement.RELATED, 133, 32767).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)特殊控制台.自定义力量加成比例, -2, 109, -2).addComponent((Component)特殊控制台.自定义敏捷加成比例, -2, 109, -2).addComponent((Component)特殊控制台.自定义智力加成比例, -2, 109, -2).addComponent((Component)特殊控制台.自定义运气加成比例, -2, 109, -2).addComponent((Component)特殊控制台.自定义物攻加成比例, -2, 109, -2).addComponent((Component)特殊控制台.自定义魔攻加成比例, -2, 109, -2).addComponent((Component)特殊控制台.自定义血量加成比例, -2, 109, -2).addComponent((Component)特殊控制台.自定义魔量加成比例, -2, 109, -2).addComponent((Component)特殊控制台.自定义物防加成比例, -2, 109, -2).addComponent((Component)特殊控制台.自定义魔防加成比例, -2, 109, -2)).addGap(69, 69, 69)));
        jPanel10Layout.setVerticalGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel10Layout.createSequentialGroup().addGap(34, 34, 34).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel3).addComponent((Component)特殊控制台.自定义力量加成比例, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel4).addComponent((Component)特殊控制台.自定义敏捷加成比例, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel5).addComponent((Component)特殊控制台.自定义智力加成比例, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel6).addComponent((Component)特殊控制台.自定义运气加成比例, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel7).addComponent((Component)特殊控制台.自定义物攻加成比例, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel8).addComponent((Component)特殊控制台.自定义魔攻加成比例, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel10).addComponent((Component)特殊控制台.自定义血量加成比例, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel11).addComponent((Component)特殊控制台.自定义魔量加成比例, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel12).addComponent((Component)特殊控制台.自定义物防加成比例, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group)jPanel10Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel13).addComponent((Component)特殊控制台.自定义魔防加成比例, -2, -1, -2)).addContainerGap(34, 32767)));
        this.初始化功能.setFont(new Font("幼圆", 0, 15));
        this.初始化功能.setText("初始化功能信息");
        this.初始化功能.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.初始化功能ActionPerformed(evt);
            }
        });
        this.jPanel11.setBorder((Border)BorderFactory.createTitledBorder(""));
        this.jLabel15.setText("伤害气泡显示");
        特殊控制台.自定义伤害气泡显示.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        特殊控制台.自定义伤害气泡显示.setBorderPainted(false);
        特殊控制台.自定义伤害气泡显示.setContentAreaFilled(false);
        特殊控制台.自定义伤害气泡显示.setFocusPainted(false);
        特殊控制台.自定义伤害气泡显示.setSelectedIcon((Icon)new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        特殊控制台.自定义伤害气泡显示.addChangeListener((ChangeListener)new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                特殊控制台.this.自定义伤害气泡显示StateChanged(evt);
            }
        });
        特殊控制台.自定义伤害气泡显示.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义伤害气泡显示ActionPerformed(evt);
            }
        });
        this.jLabel16.setText("伤害额外段数显示");
        特殊控制台.自定义伤害段数显示.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        特殊控制台.自定义伤害段数显示.setBorderPainted(false);
        特殊控制台.自定义伤害段数显示.setContentAreaFilled(false);
        特殊控制台.自定义伤害段数显示.setFocusPainted(false);
        特殊控制台.自定义伤害段数显示.setSelectedIcon((Icon)new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        特殊控制台.自定义伤害段数显示.addChangeListener((ChangeListener)new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                特殊控制台.this.自定义伤害段数显示StateChanged(evt);
            }
        });
        特殊控制台.自定义伤害段数显示.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义伤害段数显示ActionPerformed(evt);
            }
        });
        this.jLabel17.setText("是否扣除21个E的伤害");
        特殊控制台.扣除21E伤害.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        特殊控制台.扣除21E伤害.setBorderPainted(false);
        特殊控制台.扣除21E伤害.setContentAreaFilled(false);
        特殊控制台.扣除21E伤害.setFocusPainted(false);
        特殊控制台.扣除21E伤害.setSelectedIcon((Icon)new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        特殊控制台.扣除21E伤害.addChangeListener((ChangeListener)new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                特殊控制台.this.扣除21E伤害StateChanged(evt);
            }
        });
        特殊控制台.扣除21E伤害.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.扣除21E伤害ActionPerformed(evt);
            }
        });
        this.jLabel18.setText("伤害黄字喇叭显示");
        特殊控制台.自定义伤害黄字喇叭显示.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        特殊控制台.自定义伤害黄字喇叭显示.setBorderPainted(false);
        特殊控制台.自定义伤害黄字喇叭显示.setContentAreaFilled(false);
        特殊控制台.自定义伤害黄字喇叭显示.setFocusPainted(false);
        特殊控制台.自定义伤害黄字喇叭显示.setSelectedIcon((Icon)new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        特殊控制台.自定义伤害黄字喇叭显示.addChangeListener((ChangeListener)new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                特殊控制台.this.自定义伤害黄字喇叭显示StateChanged(evt);
            }
        });
        特殊控制台.自定义伤害黄字喇叭显示.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                特殊控制台.this.自定义伤害黄字喇叭显示ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel11Layout = new GroupLayout((Container)this.jPanel11);
        this.jPanel11.setLayout((LayoutManager)jPanel11Layout);
        jPanel11Layout.setHorizontalGroup((Group)jPanel11Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel11Layout.createSequentialGroup().addGap(18, 18, 18).addGroup((Group)jPanel11Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group)jPanel11Layout.createSequentialGroup().addComponent((Component)this.jLabel16, -1, -1, 32767).addGap(23, 23, 23).addComponent((Component)特殊控制台.自定义伤害段数显示, -2, 77, -2)).addGroup((Group)jPanel11Layout.createSequentialGroup().addComponent((Component)this.jLabel15, -1, 126, 32767).addGap(31, 31, 31).addComponent((Component)特殊控制台.自定义伤害气泡显示, -2, 77, -2)).addGroup(Alignment.LEADING, (Group)jPanel11Layout.createSequentialGroup().addGroup((Group)jPanel11Layout.createParallelGroup(Alignment.TRAILING, false).addComponent((Component)this.jLabel18, -1, -1, 32767).addComponent((Component)this.jLabel17, -1, 123, 32767)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group)jPanel11Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)特殊控制台.扣除21E伤害, Alignment.TRAILING, -2, 77, -2).addComponent((Component)特殊控制台.自定义伤害黄字喇叭显示, Alignment.TRAILING, -2, 77, -2)))).addGap(43, 43, 43)));
        jPanel11Layout.setVerticalGroup((Group)jPanel11Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel11Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel11Layout.createParallelGroup(Alignment.CENTER).addComponent((Component)特殊控制台.自定义伤害气泡显示, -2, 30, -2).addComponent((Component)this.jLabel15)).addGap(18, 18, 18).addGroup((Group)jPanel11Layout.createParallelGroup(Alignment.CENTER).addComponent((Component)this.jLabel16).addComponent((Component)特殊控制台.自定义伤害段数显示, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group)jPanel11Layout.createParallelGroup(Alignment.CENTER).addComponent((Component)特殊控制台.自定义伤害黄字喇叭显示, -2, 30, -2).addComponent((Component)this.jLabel18)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group)jPanel11Layout.createParallelGroup(Alignment.CENTER).addComponent((Component)this.jLabel17).addComponent((Component)特殊控制台.扣除21E伤害, -2, 30, -2)).addGap(24, 24, 24)));
        final GroupLayout jPanel8Layout = new GroupLayout((Container)this.jPanel8);
        this.jPanel8.setLayout((LayoutManager)jPanel8Layout);
        jPanel8Layout.setHorizontalGroup((Group)jPanel8Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel8Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel8Layout.createParallelGroup(Alignment.TRAILING, false).addComponent((Component)this.jPanel9, -1, -1, 32767).addComponent((Component)this.jPanel11, -1, -1, 32767)).addGap(18, 18, 18).addGroup((Group)jPanel8Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.初始化功能, -2, 299, -2).addComponent((Component)this.jPanel10, -2, -1, -2)).addContainerGap(18, 32767)));
        jPanel8Layout.setVerticalGroup((Group)jPanel8Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel8Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel8Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel8Layout.createSequentialGroup().addComponent((Component)this.jPanel10, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED, -1, 32767).addComponent((Component)this.初始化功能)).addGroup((Group)jPanel8Layout.createSequentialGroup().addComponent((Component)this.jPanel9, -2, 199, -2).addGap(18, 18, 18).addComponent((Component)this.jPanel11, -1, -1, 32767))).addGap(19, 19, 19)));
        final GroupLayout jPanel4Layout = new GroupLayout((Container)this.jPanel4);
        this.jPanel4.setLayout((LayoutManager)jPanel4Layout);
        jPanel4Layout.setHorizontalGroup((Group)jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel4Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jPanel8, -1, -1, 32767).addContainerGap()));
        jPanel4Layout.setVerticalGroup((Group)jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel4Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jPanel8, -2, -1, -2).addContainerGap(-1, 32767)));
        this.jPanel5.setBorder((Border)BorderFactory.createTitledBorder("说明介绍"));
        this.jLabel9.setText("<html><body>计算公式：<br>1.如果第一控制台开启战力修正,四维伤害提高10倍,双攻加成提高50倍<br>2.总伤=装备属性*对应属性的加成比例总和</body></html>");
        this.jLabel14.setText("<html><body>关于条件：<br>1、使用该功能记得先点初始化<br>2、开启自定义伤害加成才有效果<br>3、开启道具直接加成拥有道具就可以享受加成无需伤害大于</body></html>");
        final GroupLayout jPanel5Layout = new GroupLayout((Container)this.jPanel5);
        this.jPanel5.setLayout((LayoutManager)jPanel5Layout);
        jPanel5Layout.setHorizontalGroup((Group)jPanel5Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel5Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel5Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel14).addComponent((Component)this.jLabel9, Alignment.TRAILING, -1, 552, 32767)).addContainerGap()));
        jPanel5Layout.setVerticalGroup((Group)jPanel5Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel5Layout.createSequentialGroup().addGap(22, 22, 22).addComponent((Component)this.jLabel14, -2, 154, -2).addGap(63, 63, 63).addComponent((Component)this.jLabel9, -2, 154, -2).addContainerGap(-1, 32767)));
        final GroupLayout jPanel2Layout = new GroupLayout((Container)this.jPanel2);
        this.jPanel2.setLayout((LayoutManager)jPanel2Layout);
        jPanel2Layout.setHorizontalGroup((Group)jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel2Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jPanel4, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jPanel5, -2, -1, -2).addContainerGap(39, 32767)));
        jPanel2Layout.setVerticalGroup((Group)jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel2Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel2Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jPanel4, -1, -1, 32767).addComponent((Component)this.jPanel5, -1, -1, 32767)).addContainerGap(95, 32767)));
        this.jTabbedPane1.addTab("自定义伤害面板", (Component)this.jPanel2);
        final GroupLayout jPanel1Layout = new GroupLayout((Container)this.jPanel1);
        this.jPanel1.setLayout((LayoutManager)jPanel1Layout);
        jPanel1Layout.setHorizontalGroup((Group)jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel1Layout.createSequentialGroup().addComponent((Component)this.jTabbedPane1, -2, 1448, -2).addGap(0, 0, 32767)));
        jPanel1Layout.setVerticalGroup((Group)jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel1Layout.createSequentialGroup().addComponent((Component)this.jTabbedPane1, -2, 645, -2).addGap(0, 17, 32767)));
        特殊控制台.主界面菜单.addTab("特殊功能", (Icon)new ImageIcon(this.getClass().getResource("/image/管理.png")), (Component)this.jPanel1);
        this.getContentPane().add((Component)特殊控制台.主界面菜单, new AbsoluteConstraints(0, 0, 1450, 700));
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    private void 生成交易币ActionPerformed(final ActionEvent evt) {
        final boolean result1 = 特殊控制台.交易币金额.getText().matches("[0-9]+");
        if (特殊控制台.交易币金额.getText().equals("") && !result1) {
            return;
        }
        final boolean result2 = this.交易币个数.getText().matches("[0-9]+");
        if (this.交易币个数.getText().equals("") && !result2) {
            return;
        }
        for (int 卡数 = Integer.valueOf(this.交易币个数.getText()).intValue(), i = 0; i < 卡数; ++i) {
            this.生成自定义充值卡(7);
        }
    }
    
    private void 交易币金额ActionPerformed(final ActionEvent evt) {
    }
    
    private void 邀请人百分比获取FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("邀请人百分比获取") != Integer.valueOf(特殊控制台.邀请人百分比获取.getText())) {
            this.配置更新("邀请人百分比获取", Integer.valueOf(特殊控制台.邀请人百分比获取.getText()).intValue());
        }
    }
    
    private void 生成充值赞助卡ActionPerformed(final ActionEvent evt) {
        final boolean result1 = this.充值赞助金额.getText().matches("[0-9]+");
        if (this.充值赞助金额.getText().equals("") && !result1) {
            return;
        }
        final boolean result2 = this.充值赞助卡个数.getText().matches("[0-9]+");
        if (this.充值赞助卡个数.getText().equals("") && !result2) {
            return;
        }
        for (int 卡数 = Integer.valueOf(this.充值赞助卡个数.getText()).intValue(), i = 0; i < 卡数; ++i) {
            this.生成自定义充值卡(6);
        }
    }
    
    private void 生成礼包1ActionPerformed(final ActionEvent evt) {
        final boolean result1 = this.礼包编号.getText().matches("[0-9]+");
        if (this.礼包编号.getText().equals("") && !result1) {
            return;
        }
        final boolean result2 = this.礼包个数.getText().matches("[0-9]+");
        if (this.礼包个数.getText().equals("") && !result2) {
            return;
        }
        for (int 卡数 = Integer.valueOf(this.礼包个数.getText()).intValue(), i = 0; i < 卡数; ++i) {
            this.生成自定义充值卡(5);
        }
    }
    
    private void 生成抵用券充值卡1ActionPerformed(final ActionEvent evt) {
        final boolean result1 = this.抵用券充值卡金额.getText().matches("[0-9]+");
        if (this.抵用券充值卡金额.getText().equals("") && !result1) {
            return;
        }
        final boolean result2 = this.抵用充值卡个数.getText().matches("[0-9]+");
        if (this.抵用充值卡个数.getText().equals("") && !result2) {
            return;
        }
        for (int 卡数 = Integer.valueOf(this.抵用充值卡个数.getText()).intValue(), i = 0; i < 卡数; ++i) {
            this.生成自定义充值卡(2);
        }
    }
    
    private void 生成点券充值卡1ActionPerformed(final ActionEvent evt) {
        final boolean result1 = this.点券充值卡个数.getText().matches("[0-9]+");
        if (this.点券充值卡个数.getText().equals("") && !result1) {
            return;
        }
        final boolean result2 = this.点券充值卡个数.getText().matches("[0-9]+");
        if (this.点券充值卡个数.getText().equals("") && !result2) {
            return;
        }
        for (int 卡数 = Integer.valueOf(this.点券充值卡个数.getText()).intValue(), i = 0; i < 卡数; ++i) {
            this.生成自定义充值卡(1);
        }
    }
    
    private void 刷新充值卡信息ActionPerformed(final ActionEvent evt) {
        this.刷新充值卡信息();
        特殊控制台.邀请人百分比获取.setText(String.valueOf(Start.ConfigValuesMap.get("邀请人百分比获取")));
    }
    
    private void 自定义伤害加成开关StateChanged(final ChangeEvent evt) {
        this.配置更新("自定义伤害加成开关", (int)(特殊控制台.自定义伤害加成开关.isSelected() ? 1 : 0));
    }
    
    private void 自定义伤害加成开关ActionPerformed(final ActionEvent evt) {
    }
    
    private void 道具加成自定义伤害开关StateChanged(final ChangeEvent evt) {
        this.配置更新("道具加成自定义伤害开关", (int)(特殊控制台.道具加成自定义伤害开关.isSelected() ? 1 : 0));
    }
    
    private void 道具加成自定义伤害开关ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义伤害加成道具代码FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义伤害加成道具代码") != Integer.valueOf(特殊控制台.自定义伤害加成道具代码.getText())) {
            this.配置更新("自定义伤害加成道具代码", Integer.valueOf(特殊控制台.自定义伤害加成道具代码.getText()).intValue());
        }
    }
    
    private void 自定义伤害加成道具代码ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义力量加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义力量加成比例") != Integer.valueOf(特殊控制台.自定义力量加成比例.getText())) {
            this.配置更新("自定义力量加成比例", Integer.valueOf(特殊控制台.自定义力量加成比例.getText()).intValue());
        }
    }
    
    private void 自定义力量加成比例ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义敏捷加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义敏捷加成比例") != Integer.valueOf(特殊控制台.自定义敏捷加成比例.getText())) {
            this.配置更新("自定义敏捷加成比例", Integer.valueOf(特殊控制台.自定义敏捷加成比例.getText()).intValue());
        }
    }
    
    private void 自定义敏捷加成比例ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义智力加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义智力加成比例") != Integer.valueOf(特殊控制台.自定义智力加成比例.getText())) {
            this.配置更新("自定义智力加成比例", Integer.valueOf(特殊控制台.自定义智力加成比例.getText()).intValue());
        }
    }
    
    private void 自定义智力加成比例ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义运气加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义运气加成比例") != Integer.valueOf(特殊控制台.自定义运气加成比例.getText())) {
            this.配置更新("自定义运气加成比例", Integer.valueOf(特殊控制台.自定义运气加成比例.getText()).intValue());
        }
    }
    
    private void 自定义运气加成比例ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义物攻加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义物攻加成比例") != Integer.valueOf(特殊控制台.自定义物攻加成比例.getText())) {
            this.配置更新("自定义物攻加成比例", Integer.valueOf(特殊控制台.自定义物攻加成比例.getText()).intValue());
        }
    }
    
    private void 自定义物攻加成比例ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义魔攻加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义魔攻加成比例") != Integer.valueOf(特殊控制台.自定义魔攻加成比例.getText())) {
            this.配置更新("自定义魔攻加成比例", Integer.valueOf(特殊控制台.自定义魔攻加成比例.getText()).intValue());
        }
    }
    
    private void 自定义魔攻加成比例ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义血量加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义血量加成比例") != Integer.valueOf(特殊控制台.自定义血量加成比例.getText())) {
            this.配置更新("自定义血量加成比例", Integer.valueOf(特殊控制台.自定义血量加成比例.getText()).intValue());
        }
    }
    
    private void 自定义血量加成比例ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义魔量加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义魔量加成比例") != Integer.valueOf(特殊控制台.自定义魔量加成比例.getText())) {
            this.配置更新("自定义魔量加成比例", Integer.valueOf(特殊控制台.自定义魔量加成比例.getText()).intValue());
        }
    }
    
    private void 自定义魔量加成比例ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义物防加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义物防加成比例") != Integer.valueOf(特殊控制台.自定义物防加成比例.getText())) {
            this.配置更新("自定义物防加成比例", Integer.valueOf(特殊控制台.自定义物防加成比例.getText()).intValue());
        }
    }
    
    private void 自定义物防加成比例ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义魔防加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自定义魔防加成比例") != Integer.valueOf(特殊控制台.自定义魔防加成比例.getText())) {
            this.配置更新("自定义魔防加成比例", Integer.valueOf(特殊控制台.自定义魔防加成比例.getText()).intValue());
        }
    }
    
    private void 自定义魔防加成比例ActionPerformed(final ActionEvent evt) {
    }
    
    private void 伤害高于次数值FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("伤害高于次数值") != Integer.valueOf(特殊控制台.伤害高于次数值.getText())) {
            this.配置更新("伤害高于次数值", Integer.valueOf(特殊控制台.伤害高于次数值.getText()).intValue());
        }
    }
    
    private void 初始化功能ActionPerformed(final ActionEvent evt) {
        初始化功能信息();
    }
    
    private void 自定义伤害气泡显示StateChanged(final ChangeEvent evt) {
        this.配置更新("自定义伤害气泡显示", (int)(特殊控制台.自定义伤害气泡显示.isSelected() ? 1 : 0));
    }
    
    private void 自定义伤害气泡显示ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义伤害段数显示StateChanged(final ChangeEvent evt) {
        this.配置更新("自定义伤害段数显示", (int)(特殊控制台.自定义伤害段数显示.isSelected() ? 1 : 0));
    }
    
    private void 自定义伤害段数显示ActionPerformed(final ActionEvent evt) {
    }
    
    private void 扣除21E伤害StateChanged(final ChangeEvent evt) {
        this.配置更新("扣除21E伤害", (int)(特殊控制台.扣除21E伤害.isSelected() ? 1 : 0));
    }
    
    private void 扣除21E伤害ActionPerformed(final ActionEvent evt) {
    }
    
    private void 自定义伤害黄字喇叭显示StateChanged(final ChangeEvent evt) {
        this.配置更新("自定义伤害黄字喇叭显示", (int)(特殊控制台.自定义伤害黄字喇叭显示.isSelected() ? 1 : 0));
    }
    
    private void 自定义伤害黄字喇叭显示ActionPerformed(final ActionEvent evt) {
    }
    
    public static void 初始化功能信息() {
        特殊控制台.伤害高于次数值.setText(String.valueOf(Start.ConfigValuesMap.get("伤害高于次数值")));
        特殊控制台.自定义伤害加成道具代码.setText(String.valueOf(Start.ConfigValuesMap.get("自定义伤害加成道具代码")));
        特殊控制台.自定义伤害加成开关.setSelected(((Integer)Start.ConfigValuesMap.get("自定义伤害加成开关")).intValue() > 0);
        特殊控制台.道具加成自定义伤害开关.setSelected(((Integer)Start.ConfigValuesMap.get("道具加成自定义伤害开关")).intValue() > 0);
        特殊控制台.自定义伤害气泡显示.setSelected(((Integer)Start.ConfigValuesMap.get("自定义伤害气泡显示")).intValue() > 0);
        特殊控制台.自定义伤害段数显示.setSelected(((Integer)Start.ConfigValuesMap.get("自定义伤害段数显示")).intValue() > 0);
        特殊控制台.自定义伤害黄字喇叭显示.setSelected(((Integer)Start.ConfigValuesMap.get("自定义伤害黄字喇叭显示")).intValue() > 0);
        特殊控制台.自定义力量加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("自定义力量加成比例")));
        特殊控制台.自定义敏捷加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("自定义敏捷加成比例")));
        特殊控制台.自定义智力加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("自定义智力加成比例")));
        特殊控制台.自定义运气加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("自定义运气加成比例")));
        特殊控制台.自定义物攻加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("自定义物攻加成比例")));
        特殊控制台.自定义魔攻加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("自定义魔攻加成比例")));
        特殊控制台.自定义物防加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("自定义物防加成比例")));
        特殊控制台.自定义魔防加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("自定义魔防加成比例")));
        特殊控制台.自定义血量加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("自定义血量加成比例")));
        特殊控制台.自定义魔量加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("自定义魔量加成比例")));
    }
    
    public void 配置更新(final String name, final int value) {
        if (Start.ConfigValuesMap.get(name) != null) {
            Start.ConfigValuesMap.put(name, Integer.valueOf(value));
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE configvalues SET Val = ? WHERE name = ?");
            ps.setInt(1, value);
            ps.setString(2, name);
            ps.execute();
            ps.close();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }
    
    public void 刷新充值卡信息() {
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.充值卡信息.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.充值卡信息.getModel()).removeRow(i);
        }
        final PreparedStatement ps1 = null;
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps2 = null;
            ResultSet rs = null;
            ps2 = con.prepareStatement("SELECT * FROM nxcode");
            rs = ps2.executeQuery();
            while (rs.next()) {
                String 类型 = "";
                switch (rs.getInt("type")) {
                    case 1: {
                        类型 = "点券";
                        break;
                    }
                    case 2: {
                        类型 = "抵用券";
                        break;
                    }
                    case 3: {
                        类型 = "金币";
                        break;
                    }
                    case 4: {
                        类型 = "经验";
                        break;
                    }
                    case 5: {
                        类型 = "礼包";
                        break;
                    }
                    case 6: {
                        类型 = "累计+余额";
                        break;
                    }
                    case 7: {
                        类型 = "交易币";
                        break;
                    }
                }
                if (rs.getInt("valid") > 0) {
                    ((DefaultTableModel)this.充值卡信息.getModel()).insertRow(this.充值卡信息.getRowCount(), new Object[] { rs.getString("code"), 类型, (rs.getInt("type") == 5) ? "1" : Integer.valueOf(rs.getInt("item")), (rs.getInt("type") == 5) ? Integer.valueOf(rs.getInt("item")) : "无" });
                }
            }
            rs.close();
            ps2.close();
            con.close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void 生成自定义充值卡(final int typea) {
        String 输出 = "";
        final String chars = "1234567890aAbBcCdDeEfFgGhHiIjJkKlLmMNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
        final char 生成1 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成2 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成3 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成4 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成5 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成6 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成7 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成8 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成9 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成10 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成11 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成12 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成13 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成14 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成15 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成16 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成17 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成18 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成19 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成20 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成21 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成22 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成23 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成24 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成25 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成26 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成27 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成28 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成29 = chars.charAt((int)(Math.random() * 62.0));
        final char 生成30 = chars.charAt((int)(Math.random() * 62.0));
        final String 充值卡 = "" + 生成1 + "" + 生成2 + "" + 生成3 + "" + 生成4 + "" + 生成5 + "" + 生成6 + "" + 生成7 + "" + 生成8 + "" + 生成9 + "" + 生成10 + "" + 生成11 + "" + 生成12 + "" + 生成13 + "" + 生成14 + "" + 生成15 + "" + 生成16 + "" + 生成17 + "" + 生成18 + "" + 生成19 + "" + 生成20 + "" + 生成21 + "" + 生成22 + "" + 生成23 + "" + 生成24 + "" + 生成25 + "" + 生成26 + "" + 生成27 + "" + 生成28 + "" + 生成29 + "" + 生成30 + "";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            if (typea == 1) {
                final int 金额 = Integer.parseInt(this.点券充值卡金额1.getText());
                ps = con.prepareStatement("INSERT INTO nxcode ( code,type,item,valid) VALUES ( ?, ?, ?, ?)");
                ps.setString(1, 充值卡);
                ps.setInt(2, 1);
                ps.setInt(3, 金额);
                ps.setInt(4, 1);
                ps.executeUpdate();
                FileoutputUtil.logToFile("充值卡后台库存/[" + FileoutputUtil.CurrentReadable_Date() + "]" + 金额 + "点券充值卡.txt", "" + 充值卡 + "\r\n");
                this.刷新充值卡信息();
                输出 = "" + FileoutputUtil.CurrentReadable_Date() + "/生成兑换卡成功，数额为 " + 金额 + " 点券，已经存放服务端根目录。";
            }
            else if (typea == 2) {
                final int 金额 = Integer.parseInt(this.抵用券充值卡金额.getText());
                ps = con.prepareStatement("INSERT INTO nxcode (code,type,item,valid) VALUES ( ?, ?, ?, ?)");
                ps.setString(1, 充值卡);
                ps.setInt(2, 2);
                ps.setInt(3, 金额);
                ps.setInt(4, 1);
                ps.executeUpdate();
                FileoutputUtil.logToFile("充值卡后台库存/[" + FileoutputUtil.CurrentReadable_Date() + "]" + 金额 + "充抵用券值卡.txt", "" + 充值卡 + "\r\n");
                this.刷新充值卡信息();
                输出 = "" + FileoutputUtil.CurrentReadable_Date() + "/生成兑换卡成功，数额为 " + 金额 + " 抵用券，已经存放服务端根目录。";
            }
            else if (typea == 5) {
                final int 礼包 = Integer.parseInt(this.礼包编号.getText());
                ps = con.prepareStatement("INSERT INTO nxcode ( code,type,valid,item) VALUES ( ?, ?, ?,?)");
                ps.setString(1, 充值卡);
                ps.setInt(2, 5);
                ps.setInt(3, 1);
                ps.setInt(4, 礼包);
                ps.executeUpdate();
                FileoutputUtil.logToFile("充值卡后台库存/[" + FileoutputUtil.CurrentReadable_Date() + "]" + 礼包 + "礼包兑换卡.txt", "" + 充值卡 + "\r\n");
                this.刷新充值卡信息();
                输出 = "" + FileoutputUtil.CurrentReadable_Date() + "/生成兑换卡成功，礼包为 " + 礼包 + " 号，已经存放服务端根目录。";
            }
            else if (typea == 6) {
                final int 金额 = Integer.parseInt(this.充值赞助金额.getText());
                ps = con.prepareStatement("INSERT INTO nxcode (code,type,item,valid) VALUES ( ?, ?, ?, ?)");
                ps.setString(1, 充值卡);
                ps.setInt(2, 6);
                ps.setInt(3, 金额);
                ps.setInt(4, 1);
                ps.executeUpdate();
                FileoutputUtil.logToFile("充值卡后台库存/[" + FileoutputUtil.CurrentReadable_Date() + "]" + 金额 + "充累计赞助加余额值卡.txt", "" + 充值卡 + "\r\n");
                this.刷新充值卡信息();
                输出 = "" + FileoutputUtil.CurrentReadable_Date() + "/生成兑换卡成功，数额为 " + 金额 + " 赞助加余额，已经存放服务端根目录。";
            }
            else if (typea == 7) {
                final int 金额 = Integer.parseInt(特殊控制台.交易币金额.getText());
                ps = con.prepareStatement("INSERT INTO nxcode (code,type,item,valid) VALUES ( ?, ?, ?, ?)");
                ps.setString(1, 充值卡);
                ps.setInt(2, 7);
                ps.setInt(3, 金额);
                ps.setInt(4, 1);
                ps.executeUpdate();
                FileoutputUtil.logToFile("充值卡后台库存/[" + FileoutputUtil.CurrentReadable_Date() + "]" + 金额 + "充交易币卡.txt", "" + 充值卡 + "\r\n");
                this.刷新充值卡信息();
                输出 = "" + FileoutputUtil.CurrentReadable_Date() + "/生成兑换卡成功，数额为 " + 金额 + " 交易币卡，已经存放服务端根目录。";
            }
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
