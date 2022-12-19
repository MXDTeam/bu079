package gui;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import client.MapleCharacterUtil;
import handling.world.World.Broadcast;
import handling.channel.handler.DamageParse;
import client.inventory.ItemFlag;
import client.inventory.Equip;
import constants.GameConstants;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import server.Timer.EventTimer;
import server.ShutdownServer;
import tools.MaplePacketCreator;
import server.MapleInventoryManipulator;
import client.inventory.MapleInventoryType;
import client.MapleStat;
import scripting.NPCConversationManager;
import scripting.PortalScriptManager;
import server.life.MapleMonsterInformationProvider;
import scripting.ReactorScriptManager;
import server.MapleShopFactory;
import provider.MapleDataProvider;
import provider.MapleDataTool;
import provider.MapleData;
import provider.MapleDataProviderFactory;
import java.io.File;
import server.MapleCarnivalChallenge;
import handling.world.World.Find;
import java.util.Iterator;
import client.MapleCharacter;
import handling.channel.ChannelServer;
import handling.login.handler.AutoRegister;
import client.LoginCrypto;
import server.CashItemFactory;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import server.MapleItemInformationProvider;
import database.DBConPool;
import javax.swing.JOptionPane;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import javax.swing.Icon;
import java.awt.Font;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.GroupLayout.Group;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout.Alignment;
import java.awt.Container;
import javax.swing.GroupLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.Component;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
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
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.util.concurrent.ScheduledFuture;
import java.lang.management.MemoryMXBean;
import client.inventory.IItem;
import javax.swing.JFrame;

public class 玩家控制台 extends JFrame{
    private static 玩家控制台 instance = new 玩家控制台();
    private int 账号ID;
    private int 角色ID;
    private int 怪物爆物序号;
    private int 全局爆物序号;
    private int 商店序号;
    private int 商店物品序号;
    private IItem 读取装备;
    private static MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    private static long startRunTime = System.currentTimeMillis();
    private static ScheduledFuture<?> ts = null;
    private int minutesLeft;
    private static Thread t = null;
    private JTextField MAXHP;
    private JTextField MAXMP;
    private JTextField QQ;
    private JButton jButton1;
    private JButton jButton10;
    private JButton jButton100;
    private JButton jButton101;
    private JButton jButton102;
    private JButton jButton103;
    private JButton jButton104;
    private JButton jButton105;
    private JButton jButton109;
    private JButton jButton11;
    private JButton jButton110;
    private JButton jButton111;
    private JButton jButton113;
    private JButton jButton12;
    private JButton jButton120;
    private JButton jButton121;
    private JButton jButton122;
    private JButton jButton123;
    private JButton jButton124;
    private JButton jButton13;
    private JButton jButton14;
    private JButton jButton15;
    private JButton jButton16;
    private JButton jButton2;
    private JButton jButton20;
    private JButton jButton21;
    private JButton jButton22;
    private JButton jButton23;
    private JButton jButton24;
    private JButton jButton25;
    private JButton jButton26;
    private JButton jButton28;
    private JButton jButton29;
    private JButton jButton3;
    private JButton jButton30;
    private JButton jButton31;
    private JButton jButton32;
    private JButton jButton33;
    private JButton jButton34;
    private JButton jButton35;
    private JButton jButton36;
    private JButton jButton37;
    private JButton jButton38;
    private JButton jButton39;
    private JButton jButton4;
    private JButton jButton41;
    private JButton jButton43;
    private JButton jButton44;
    private JButton jButton46;
    private JButton jButton5;
    private JButton jButton57;
    private JButton jButton58;
    private JButton jButton59;
    private JButton jButton6;
    private JButton jButton60;
    private JButton jButton62;
    private JButton jButton63;
    private JButton jButton64;
    private JButton jButton65;
    private JButton jButton66;
    private JButton jButton67;
    private JButton jButton68;
    private JButton jButton69;
    private JButton jButton7;
    private JButton jButton70;
    private JButton jButton71;
    private JButton jButton72;
    private JButton jButton73;
    private JButton jButton74;
    private JButton jButton75;
    private JButton jButton76;
    private JButton jButton77;
    private JButton jButton78;
    private JButton jButton79;
    private JButton jButton8;
    private JButton jButton80;
    private JButton jButton89;
    private JButton jButton9;
    private JButton jButton90;
    private JButton jButton91;
    private JButton jButton92;
    private JButton jButton93;
    private JButton jButton94;
    private JButton jButton95;
    private JButton jButton96;
    private JButton jButton97;
    private JButton jButton98;
    private JButton jButton99;
    private JLabel jLabel10;
    private JLabel jLabel108;
    private JLabel jLabel11;
    private JLabel jLabel110;
    private JLabel jLabel114;
    private JLabel jLabel119;
    private JLabel jLabel12;
    private JLabel jLabel120;
    private JLabel jLabel121;
    private JLabel jLabel122;
    private JLabel jLabel123;
    private JLabel jLabel128;
    private JLabel jLabel129;
    private JLabel jLabel13;
    private JLabel jLabel130;
    private JLabel jLabel131;
    private JLabel jLabel132;
    private JLabel jLabel133;
    private JLabel jLabel134;
    private JLabel jLabel135;
    private JLabel jLabel136;
    private JLabel jLabel137;
    private JLabel jLabel138;
    private JLabel jLabel139;
    private JLabel jLabel14;
    private JLabel jLabel140;
    private JLabel jLabel143;
    private JLabel jLabel144;
    private JLabel jLabel146;
    private JLabel jLabel147;
    private JLabel jLabel148;
    private JLabel jLabel149;
    private JLabel jLabel15;
    private JLabel jLabel150;
    private JLabel jLabel151;
    private JLabel jLabel152;
    private JLabel jLabel153;
    private JLabel jLabel164;
    private JLabel jLabel165;
    private JLabel jLabel25;
    private JLabel jLabel26;
    private JLabel jLabel27;
    private JLabel jLabel28;
    private JLabel jLabel29;
    private JLabel jLabel30;
    private JLabel jLabel31;
    private JLabel jLabel32;
    private JLabel jLabel39;
    private JLabel jLabel4;
    private JLabel jLabel40;
    private JLabel jLabel41;
    private JLabel jLabel42;
    private JLabel jLabel43;
    private JLabel jLabel44;
    private JLabel jLabel45;
    private JLabel jLabel46;
    private JLabel jLabel47;
    private JLabel jLabel48;
    private JLabel jLabel49;
    private JLabel jLabel5;
    private JLabel jLabel50;
    private JLabel jLabel51;
    private JLabel jLabel52;
    private JLabel jLabel53;
    private JLabel jLabel54;
    private JLabel jLabel55;
    private JLabel jLabel56;
    private JLabel jLabel57;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JLabel jLabel91;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel104;
    private JPanel jPanel105;
    private JPanel jPanel106;
    private JPanel jPanel107;
    private JPanel jPanel108;
    private JPanel jPanel11;
    private JPanel jPanel110;
    private JPanel jPanel111;
    private JPanel jPanel112;
    private JPanel jPanel113;
    private JPanel jPanel114;
    private JPanel jPanel115;
    private JPanel jPanel116;
    private JPanel jPanel117;
    private JPanel jPanel118;
    private JPanel jPanel119;
    private JPanel jPanel120;
    private JPanel jPanel121;
    private JPanel jPanel122;
    private JPanel jPanel124;
    private JPanel jPanel125;
    private JPanel jPanel126;
    private JPanel jPanel127;
    private JPanel jPanel128;
    private JPanel jPanel129;
    private JPanel jPanel130;
    private JPanel jPanel131;
    private JPanel jPanel132;
    private JPanel jPanel133;
    private JPanel jPanel134;
    private JPanel jPanel135;
    private JPanel jPanel136;
    private JPanel jPanel137;
    private JPanel jPanel138;
    private JPanel jPanel139;
    private JPanel jPanel14;
    private JPanel jPanel140;
    private JPanel jPanel141;
    private JPanel jPanel142;
    private JPanel jPanel143;
    private JPanel jPanel144;
    private JPanel jPanel145;
    private JPanel jPanel146;
    private JPanel jPanel147;
    private JPanel jPanel148;
    private JPanel jPanel16;
    private JPanel jPanel17;
    private JPanel jPanel18;
    private JPanel jPanel19;
    private JPanel jPanel2;
    private JPanel jPanel24;
    private JPanel jPanel28;
    private JPanel jPanel29;
    private JPanel jPanel3;
    private JPanel jPanel31;
    private JPanel jPanel35;
    private JPanel jPanel36;
    private JPanel jPanel37;
    private JPanel jPanel38;
    private JPanel jPanel39;
    private JPanel jPanel40;
    private JPanel jPanel41;
    private JPanel jPanel42;
    private JPanel jPanel43;
    private JPanel jPanel45;
    private JPanel jPanel47;
    private JPanel jPanel50;
    private JPanel jPanel51;
    private JPanel jPanel52;
    private JPanel jPanel55;
    private JPanel jPanel56;
    private JPanel jPanel57;
    private JPanel jPanel58;
    private JPanel jPanel59;
    private JPanel jPanel6;
    private JPanel jPanel60;
    private JPanel jPanel61;
    private JPanel jPanel7;
    private JPanel jPanel75;
    private JPanel jPanel8;
    private JPanel jPanel83;
    private JPanel jPanel84;
    private JPanel jPanel85;
    private JPanel jPanel86;
    private JPanel jPanel87;
    private JPanel jPanel88;
    private JPanel jPanel90;
    private JPanel jPanel91;
    private JPanel jPanel92;
    private JScrollPane jScrollPane10;
    private JScrollPane jScrollPane11;
    private JScrollPane jScrollPane12;
    private JScrollPane jScrollPane13;
    private JScrollPane jScrollPane14;
    private JScrollPane jScrollPane16;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane21;
    private JScrollPane jScrollPane22;
    private JScrollPane jScrollPane23;
    private JScrollPane jScrollPane24;
    private JScrollPane jScrollPane25;
    private JScrollPane jScrollPane26;
    private JScrollPane jScrollPane27;
    private JScrollPane jScrollPane28;
    private JScrollPane jScrollPane29;
    private JScrollPane jScrollPane60;
    private JScrollPane jScrollPane7;
    private JScrollPane jScrollPane8;
    private JScrollPane jScrollPane9;
    private JTabbedPane jTabbedPane1;
    private JTabbedPane jTabbedPane4;
    private JTabbedPane jTabbedPane7;
    private JTextField 上卷次数;
    private JTextArea 临时公告;
    public static JTabbedPane 主界面菜单;
    private JTable 仓库;
    private JButton 修改商品;
    private JTable 全局爆物;
    private JTextField 全局爆物物品ID;
    private JTextField 全局爆物物品名字;
    private JTextField 全局爆物物品爆率;
    private JTable 其他;
    private JTabbedPane 其他分支;
    private JButton 删除商品;
    private JTextField 力量;
    private JButton 加载商城;
    private JTextField 发型;
    private JTextField 发放奖励数量;
    private JTextField 发放物品ID;
    private JTextField 发放物品数量;
    private JTable 商人列表;
    private JComboBox<String> 商品上下架;
    private JTextField 商品代码;
    private JTextField 商品价格;
    private JComboBox<String> 商品出售状态;
    private JTable 商品列表;
    private JTextField 商品名称;
    private JComboBox<String> 商品性别;
    private JTextField 商品排列顺序;
    private JTextField 商品排序;
    private JTextField 商品数量;
    private JTextField 商品编码;
    private JTextField 商品限时;
    private JTabbedPane 商城分支;
    private JTextField 商城列表代码;
    private static JTextField 商城扩充价格;
    private JTable 商城物品表;
    private JTextField 商店物品ID;
    private JTextField 商店物品价格;
    private JTextField 商店物品名称;
    private JRadioButton 在线玩家A;
    private JRadioButton 在线玩家B;
    private JTabbedPane 宠物分支;
    private JTable 家族信息;
    private JTextField 密码;
    private JLabel 当前商店ID;
    private JButton 怪物ID查询;
    private JTextField 怪物ID查询掉落;
    private JTable 怪物爆物;
    private JTextField 怪物爆物怪物ID;
    private JTextField 怪物爆物物品ID;
    private JTextField 怪物爆物物品名字;
    private JTextField 怪物爆物物品爆率;
    private JTextField 所在地图;
    private JTable 技能信息;
    private JTextField 技能点;
    private JTextField 抵用;
    private JLabel 收取装备状态;
    private JTextField 敏捷;
    private JTable 时仓;
    private JTextField 智力;
    private JTable 消耗;
    private JTabbedPane 消耗分支;
    private JButton 添加商品;
    private JTextField 清空怪物ID;
    private JButton 清空未领取邮件;
    private JTextField 清空物品ID;
    private JTextField 点券;
    private JTabbedPane 热销分支;
    private JButton 物品ID查询;
    private JTextField 物品ID查询掉落;
    private JTable 特殊;
    private JTextField 等级;
    private JTextField 职业ID;
    private JTextField 能力点;
    private JTextField 脸型;
    private JTable 装备;
    private JTextField 装备HP;
    private JTextField 装备ID;
    private JTextField 装备MP;
    private JRadioButton 装备不可交易;
    private JTabbedPane 装备分支;
    private JTextField 装备力量;
    private JRadioButton 装备可以交易;
    private JTextField 装备命中;
    private JTextField 装备回避;
    private JTextField 装备攻击力;
    private JTextField 装备敏捷;
    private JTextField 装备星数;
    private JTextField 装备智力;
    private JTextField 装备潜能;
    private JTextField 装备物理防御;
    private JTextField 装备砸卷上限;
    private JTextField 装备移动速度;
    private JTextField 装备跳跃力;
    private JTextField 装备运气;
    private JCheckBox 装备限时开关;
    private JTextField 装备限时时间;
    private JTextField 装备魔法力;
    private JTextField 装备魔法防御;
    private JTable 角色信息;
    private JTabbedPane 角色操作;
    private JTextField 角色昵称;
    private JTable 设置;
    private JTabbedPane 设置分支;
    private JTextField 豆豆;
    private JTextField 账号;
    private JTable 账号信息;
    private JTabbedPane 资源页签;
    private JTable 身上;
    private JTextField 运气;
    private JRadioButton 选中玩家A;
    private JRadioButton 选中玩家B;
    private JLabel 选中角色名;
    private JButton 邮件全部玩家;
    private JButton 邮件发放全部;
    private JButton 邮件发放在线;
    private JButton 邮件发放选中;
    private JButton 邮件在线玩家;
    private JTextField 邮件标题;
    private JTextArea 邮件正文;
    private JButton 邮件离线玩家;
    private static JTable 邮件角色列表;
    private JTextField 金币;
    private JTextField 附件A代码;
    private JTextField 附件A数量;
    private JTextField 附件B代码;
    private JTextField 附件B数量;
    private JTextField 附件C代码;
    private JTextField 附件C数量;
    private JTabbedPane 首页分支;
    
    public static final 玩家控制台 getInstance() {
        return 玩家控制台.instance;
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
                new 玩家控制台().setVisible(true);
            }
        });
    }
    
    public 玩家控制台() {
        this.账号ID = -1;
        this.角色ID = -1;
        this.怪物爆物序号 = -1;
        this.全局爆物序号 = -1;
        this.商店序号 = -1;
        this.商店物品序号 = -1;
        this.读取装备 = null;
        this.minutesLeft = 0;
        this.setTitle("【" + Game.服务端名称 + "玩家控制台】 - 新春贺岁版[可关闭]");
        this.initComponents();
        final ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("image/冒险岛.png"));
        this.setIconImage(icon.getImage());
        this.初始化界面配置();
    }
    
    private void initComponents() {
        玩家控制台.主界面菜单 = new JTabbedPane();
        this.jPanel1 = new JPanel();
        this.jTabbedPane1 = new JTabbedPane();
        this.jPanel6 = new JPanel();
        this.jScrollPane8 = new JScrollPane();
        this.账号信息 = new JTable();
        this.jTabbedPane7 = new JTabbedPane();
        this.jPanel35 = new JPanel();
        this.jPanel36 = new JPanel();
        this.jButton2 = new JButton();
        this.jButton3 = new JButton();
        this.jButton4 = new JButton();
        this.jButton8 = new JButton();
        this.jPanel37 = new JPanel();
        this.jButton5 = new JButton();
        this.jButton6 = new JButton();
        this.jButton7 = new JButton();
        this.jButton9 = new JButton();
        this.jPanel38 = new JPanel();
        this.jButton10 = new JButton();
        this.账号 = new JTextField();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.抵用 = new JTextField();
        this.jLabel6 = new JLabel();
        this.点券 = new JTextField();
        this.jLabel8 = new JLabel();
        this.QQ = new JTextField();
        this.密码 = new JTextField();
        this.jLabel10 = new JLabel();
        this.jButton11 = new JButton();
        this.jButton12 = new JButton();
        this.jButton16 = new JButton();
        this.jPanel104 = new JPanel();
        this.jButton102 = new JButton();
        this.jButton120 = new JButton();
        this.jPanel7 = new JPanel();
        this.jScrollPane2 = new JScrollPane();
        this.角色信息 = new JTable();
        this.角色操作 = new JTabbedPane();
        this.jPanel39 = new JPanel();
        this.jPanel42 = new JPanel();
        this.jButton21 = new JButton();
        this.jLabel12 = new JLabel();
        this.等级 = new JTextField();
        this.jLabel14 = new JLabel();
        this.力量 = new JTextField();
        this.敏捷 = new JTextField();
        this.jLabel39 = new JLabel();
        this.智力 = new JTextField();
        this.jLabel40 = new JLabel();
        this.运气 = new JTextField();
        this.jLabel41 = new JLabel();
        this.MAXHP = new JTextField();
        this.jLabel42 = new JLabel();
        this.MAXMP = new JTextField();
        this.jLabel43 = new JLabel();
        this.脸型 = new JTextField();
        this.jLabel44 = new JLabel();
        this.发型 = new JTextField();
        this.jLabel45 = new JLabel();
        this.jLabel122 = new JLabel();
        this.能力点 = new JTextField();
        this.jLabel123 = new JLabel();
        this.技能点 = new JTextField();
        this.jPanel55 = new JPanel();
        this.jLabel46 = new JLabel();
        this.金币 = new JTextField();
        this.jButton22 = new JButton();
        this.jLabel47 = new JLabel();
        this.豆豆 = new JTextField();
        this.jPanel107 = new JPanel();
        this.jLabel164 = new JLabel();
        this.所在地图 = new JTextField();
        this.jButton60 = new JButton();
        this.jLabel165 = new JLabel();
        this.职业ID = new JTextField();
        this.jPanel56 = new JPanel();
        this.jLabel50 = new JLabel();
        this.角色昵称 = new JTextField();
        this.jButton57 = new JButton();
        this.jButton64 = new JButton();
        this.jPanel57 = new JPanel();
        this.jButton58 = new JButton();
        this.jButton59 = new JButton();
        this.jButton62 = new JButton();
        this.jButton63 = new JButton();
        this.jPanel60 = new JPanel();
        this.jButton67 = new JButton();
        this.jButton70 = new JButton();
        this.jPanel59 = new JPanel();
        this.jButton66 = new JButton();
        this.jButton69 = new JButton();
        this.jPanel105 = new JPanel();
        this.jButton103 = new JButton();
        this.jButton121 = new JButton();
        this.jButton124 = new JButton();
        this.jPanel106 = new JPanel();
        this.jButton104 = new JButton();
        this.jButton122 = new JButton();
        this.jButton105 = new JButton();
        this.jButton123 = new JButton();
        this.jPanel43 = new JPanel();
        this.jPanel40 = new JPanel();
        this.jButton35 = new JButton();
        this.选中玩家A = new JRadioButton();
        this.发放奖励数量 = new JTextField();
        this.在线玩家A = new JRadioButton();
        this.jLabel11 = new JLabel();
        this.jButton36 = new JButton();
        this.jButton37 = new JButton();
        this.jButton38 = new JButton();
        this.jButton39 = new JButton();
        this.jPanel45 = new JPanel();
        this.jButton28 = new JButton();
        this.jButton29 = new JButton();
        this.jButton30 = new JButton();
        this.jButton31 = new JButton();
        this.jButton32 = new JButton();
        this.jButton33 = new JButton();
        this.jButton34 = new JButton();
        this.jButton68 = new JButton();
        this.jButton71 = new JButton();
        this.jButton72 = new JButton();
        this.jButton73 = new JButton();
        this.jButton74 = new JButton();
        this.jButton75 = new JButton();
        this.jButton76 = new JButton();
        this.jButton77 = new JButton();
        this.jButton78 = new JButton();
        this.jPanel41 = new JPanel();
        this.选中玩家B = new JRadioButton();
        this.发放物品ID = new JTextField();
        this.在线玩家B = new JRadioButton();
        this.jLabel13 = new JLabel();
        this.jButton41 = new JButton();
        this.发放物品数量 = new JTextField();
        this.jLabel15 = new JLabel();
        this.jPanel47 = new JPanel();
        this.jPanel50 = new JPanel();
        this.jButton43 = new JButton();
        this.装备ID = new JTextField();
        this.jLabel25 = new JLabel();
        this.jLabel28 = new JLabel();
        this.装备力量 = new JTextField();
        this.装备智力 = new JTextField();
        this.装备敏捷 = new JTextField();
        this.装备运气 = new JTextField();
        this.jLabel29 = new JLabel();
        this.装备HP = new JTextField();
        this.装备MP = new JTextField();
        this.jLabel30 = new JLabel();
        this.装备攻击力 = new JTextField();
        this.装备魔法力 = new JTextField();
        this.装备物理防御 = new JTextField();
        this.装备可以交易 = new JRadioButton();
        this.装备不可交易 = new JRadioButton();
        this.装备魔法防御 = new JTextField();
        this.jLabel55 = new JLabel();
        this.装备潜能 = new JTextField();
        this.jLabel56 = new JLabel();
        this.装备星数 = new JTextField();
        this.装备命中 = new JTextField();
        this.jLabel54 = new JLabel();
        this.装备回避 = new JTextField();
        this.装备移动速度 = new JTextField();
        this.装备跳跃力 = new JTextField();
        this.装备砸卷上限 = new JTextField();
        this.jLabel31 = new JLabel();
        this.装备限时开关 = new JCheckBox();
        this.装备限时时间 = new JTextField();
        this.jButton44 = new JButton();
        this.jButton46 = new JButton();
        this.上卷次数 = new JTextField();
        this.jLabel32 = new JLabel();
        this.jLabel9 = new JLabel();
        this.收取装备状态 = new JLabel();
        this.jPanel51 = new JPanel();
        this.选中角色名 = new JLabel();
        this.jPanel8 = new JPanel();
        this.资源页签 = new JTabbedPane();
        this.jScrollPane21 = new JScrollPane();
        this.身上 = new JTable();
        this.jScrollPane22 = new JScrollPane();
        this.装备 = new JTable();
        this.jScrollPane23 = new JScrollPane();
        this.消耗 = new JTable();
        this.jScrollPane24 = new JScrollPane();
        this.设置 = new JTable();
        this.jScrollPane25 = new JScrollPane();
        this.其他 = new JTable();
        this.jScrollPane26 = new JScrollPane();
        this.特殊 = new JTable();
        this.jScrollPane27 = new JScrollPane();
        this.仓库 = new JTable();
        this.jScrollPane28 = new JScrollPane();
        this.时仓 = new JTable();
        this.jPanel58 = new JPanel();
        this.jButton23 = new JButton();
        this.jButton65 = new JButton();
        this.jLabel51 = new JLabel();
        this.jScrollPane13 = new JScrollPane();
        this.技能信息 = new JTable();
        this.jPanel61 = new JPanel();
        this.jButton24 = new JButton();
        this.jButton79 = new JButton();
        this.jLabel52 = new JLabel();
        this.jPanel10 = new JPanel();
        this.jScrollPane29 = new JScrollPane();
        this.家族信息 = new JTable();
        this.jButton80 = new JButton();
        this.jPanel3 = new JPanel();
        this.jTabbedPane4 = new JTabbedPane();
        this.jPanel16 = new JPanel();
        this.jPanel75 = new JPanel();
        this.jLabel128 = new JLabel();
        this.邮件标题 = new JTextField();
        this.jLabel129 = new JLabel();
        this.jScrollPane10 = new JScrollPane();
        this.邮件正文 = new JTextArea();
        this.jLabel130 = new JLabel();
        this.附件A代码 = new JTextField();
        this.jLabel131 = new JLabel();
        this.附件A数量 = new JTextField();
        this.jLabel132 = new JLabel();
        this.附件B代码 = new JTextField();
        this.jLabel133 = new JLabel();
        this.附件B数量 = new JTextField();
        this.jLabel134 = new JLabel();
        this.附件C代码 = new JTextField();
        this.jLabel135 = new JLabel();
        this.附件C数量 = new JTextField();
        this.jLabel137 = new JLabel();
        this.jPanel83 = new JPanel();
        this.jScrollPane11 = new JScrollPane();
        玩家控制台.邮件角色列表 = new JTable();
        this.jPanel11 = new JPanel();
        this.邮件在线玩家 = new JButton();
        this.邮件离线玩家 = new JButton();
        this.邮件全部玩家 = new JButton();
        this.jPanel14 = new JPanel();
        this.邮件发放选中 = new JButton();
        this.邮件发放在线 = new JButton();
        this.邮件发放全部 = new JButton();
        this.jPanel24 = new JPanel();
        this.清空未领取邮件 = new JButton();
        this.jPanel17 = new JPanel();
        this.jPanel31 = new JPanel();
        this.jScrollPane9 = new JScrollPane();
        this.怪物爆物 = new JTable();
        this.jLabel136 = new JLabel();
        this.jLabel138 = new JLabel();
        this.jLabel139 = new JLabel();
        this.怪物爆物物品ID = new JTextField();
        this.怪物爆物物品爆率 = new JTextField();
        this.jLabel140 = new JLabel();
        this.jButton89 = new JButton();
        this.jButton90 = new JButton();
        this.jButton91 = new JButton();
        this.jButton92 = new JButton();
        this.怪物爆物物品名字 = new JTextField();
        this.怪物爆物怪物ID = new JTextField();
        this.jPanel84 = new JPanel();
        this.jButton93 = new JButton();
        this.jButton94 = new JButton();
        this.jButton95 = new JButton();
        this.jButton96 = new JButton();
        this.jScrollPane7 = new JScrollPane();
        this.全局爆物 = new JTable();
        this.全局爆物物品名字 = new JTextField();
        this.jLabel146 = new JLabel();
        this.jLabel147 = new JLabel();
        this.全局爆物物品ID = new JTextField();
        this.全局爆物物品爆率 = new JTextField();
        this.jLabel148 = new JLabel();
        this.jPanel87 = new JPanel();
        this.jLabel150 = new JLabel();
        this.jLabel151 = new JLabel();
        this.物品ID查询掉落 = new JTextField();
        this.物品ID查询 = new JButton();
        this.怪物ID查询掉落 = new JTextField();
        this.怪物ID查询 = new JButton();
        this.jPanel88 = new JPanel();
        this.jLabel152 = new JLabel();
        this.jLabel153 = new JLabel();
        this.清空物品ID = new JTextField();
        this.jButton109 = new JButton();
        this.清空怪物ID = new JTextField();
        this.jButton110 = new JButton();
        this.jPanel90 = new JPanel();
        this.jButton113 = new JButton();
        this.jPanel18 = new JPanel();
        this.jPanel86 = new JPanel();
        this.jScrollPane14 = new JScrollPane();
        this.商人列表 = new JTable();
        this.jButton97 = new JButton();
        this.jButton98 = new JButton();
        this.jButton111 = new JButton();
        this.jPanel85 = new JPanel();
        this.jScrollPane12 = new JScrollPane();
        this.商品列表 = new JTable();
        this.jLabel143 = new JLabel();
        this.商店物品名称 = new JTextField();
        this.jLabel144 = new JLabel();
        this.商店物品ID = new JTextField();
        this.jLabel149 = new JLabel();
        this.商店物品价格 = new JTextField();
        this.jButton99 = new JButton();
        this.jButton100 = new JButton();
        this.jButton101 = new JButton();
        this.当前商店ID = new JLabel();
        this.jLabel7 = new JLabel();
        this.商品排列顺序 = new JTextField();
        this.jPanel19 = new JPanel();
        this.jPanel108 = new JPanel();
        this.jScrollPane16 = new JScrollPane();
        this.临时公告 = new JTextArea();
        this.jButton1 = new JButton();
        this.jButton13 = new JButton();
        this.jButton14 = new JButton();
        this.jButton15 = new JButton();
        this.jButton20 = new JButton();
        this.jPanel29 = new JPanel();
        this.商城分支 = new JTabbedPane();
        this.jPanel111 = new JPanel();
        this.首页分支 = new JTabbedPane();
        this.jPanel118 = new JPanel();
        this.jPanel119 = new JPanel();
        this.jPanel120 = new JPanel();
        this.jPanel121 = new JPanel();
        this.jPanel110 = new JPanel();
        this.装备分支 = new JTabbedPane();
        this.jPanel2 = new JPanel();
        this.jPanel124 = new JPanel();
        this.jPanel125 = new JPanel();
        this.jPanel126 = new JPanel();
        this.jPanel127 = new JPanel();
        this.jPanel128 = new JPanel();
        this.jPanel129 = new JPanel();
        this.jPanel130 = new JPanel();
        this.jPanel131 = new JPanel();
        this.jPanel132 = new JPanel();
        this.jPanel133 = new JPanel();
        this.jPanel134 = new JPanel();
        this.jPanel135 = new JPanel();
        this.jPanel112 = new JPanel();
        this.消耗分支 = new JTabbedPane();
        this.jPanel122 = new JPanel();
        this.jPanel136 = new JPanel();
        this.jPanel137 = new JPanel();
        this.jPanel113 = new JPanel();
        this.设置分支 = new JTabbedPane();
        this.jPanel147 = new JPanel();
        this.jPanel114 = new JPanel();
        this.其他分支 = new JTabbedPane();
        this.jPanel138 = new JPanel();
        this.jPanel139 = new JPanel();
        this.jPanel140 = new JPanel();
        this.jPanel141 = new JPanel();
        this.jPanel142 = new JPanel();
        this.jPanel143 = new JPanel();
        this.jPanel115 = new JPanel();
        this.宠物分支 = new JTabbedPane();
        this.jPanel144 = new JPanel();
        this.jPanel145 = new JPanel();
        this.jPanel146 = new JPanel();
        this.jPanel116 = new JPanel();
        this.jPanel117 = new JPanel();
        this.热销分支 = new JTabbedPane();
        this.jPanel148 = new JPanel();
        this.jScrollPane60 = new JScrollPane();
        this.商城物品表 = new JTable();
        this.jPanel28 = new JPanel();
        this.jLabel26 = new JLabel();
        this.商城列表代码 = new JTextField();
        this.加载商城 = new JButton();
        this.jLabel27 = new JLabel();
        this.jPanel52 = new JPanel();
        this.jLabel53 = new JLabel();
        玩家控制台.商城扩充价格 = new JTextField();
        this.jButton25 = new JButton();
        this.jButton26 = new JButton();
        this.jPanel91 = new JPanel();
        this.jLabel48 = new JLabel();
        this.商品编码 = new JTextField();
        this.jLabel49 = new JLabel();
        this.商品代码 = new JTextField();
        this.jLabel57 = new JLabel();
        this.商品名称 = new JTextField();
        this.jLabel91 = new JLabel();
        this.商品数量 = new JTextField();
        this.jLabel108 = new JLabel();
        this.商品价格 = new JTextField();
        this.jLabel110 = new JLabel();
        this.商品限时 = new JTextField();
        this.jLabel114 = new JLabel();
        this.jLabel119 = new JLabel();
        this.商品上下架 = (JComboBox<String>)new JComboBox();
        this.jLabel120 = new JLabel();
        this.商品性别 = (JComboBox<String>)new JComboBox();
        this.jLabel121 = new JLabel();
        this.商品排序 = new JTextField();
        this.商品出售状态 = (JComboBox<String>)new JComboBox();
        this.jPanel92 = new JPanel();
        this.添加商品 = new JButton();
        this.删除商品 = new JButton();
        this.修改商品 = new JButton();
        this.setBackground(new Color(255, 255, 255));
        this.setCursor(new Cursor(0));
        this.setIconImages(null);
        this.setMinimumSize(new Dimension(1366, 768));
        this.setResizable(false);
        this.getContentPane().setLayout((LayoutManager)new AbsoluteLayout());
        this.账号信息.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "账号ID", "账号", "在线", "抵用", "点券", "最近上线", "QQ", "封号", "IP地址", "MAC地址", "GM" }) {
            boolean[] canEdit = { false, false, false, false, false, false, false, false, false, false, false };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.账号信息.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane8.setViewportView((Component)this.账号信息);
        if (this.账号信息.getColumnModel().getColumnCount() > 0) {
            this.账号信息.getColumnModel().getColumn(0).setPreferredWidth(20);
            this.账号信息.getColumnModel().getColumn(5).setPreferredWidth(120);
            this.账号信息.getColumnModel().getColumn(10).setMaxWidth(50);
        }
        this.jPanel35.setMinimumSize(new Dimension(200, 106));
        this.jPanel36.setBorder((Border)BorderFactory.createTitledBorder("帐号功能"));
        this.jButton2.setText("封停");
        this.jButton2.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton2ActionPerformed(evt);
            }
        });
        this.jButton3.setText("解封");
        this.jButton3.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton3ActionPerformed(evt);
            }
        });
        this.jButton4.setText("踢号");
        this.jButton4.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton4ActionPerformed(evt);
            }
        });
        this.jButton8.setText("删号");
        this.jButton8.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton8ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel36Layout = new GroupLayout((Container)this.jPanel36);
        this.jPanel36.setLayout((LayoutManager)jPanel36Layout);
        jPanel36Layout.setHorizontalGroup((Group)jPanel36Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel36Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel36Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel36Layout.createSequentialGroup().addComponent((Component)this.jButton2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton3)).addGroup((Group)jPanel36Layout.createSequentialGroup().addComponent((Component)this.jButton4).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton8))).addContainerGap(-1, 32767)));
        jPanel36Layout.setVerticalGroup((Group)jPanel36Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel36Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel36Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton3).addComponent((Component)this.jButton2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel36Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton4).addComponent((Component)this.jButton8)).addContainerGap(-1, 32767)));
        this.jPanel37.setBorder((Border)BorderFactory.createTitledBorder("帐号筛选"));
        this.jButton5.setForeground(new Color(255, 0, 0));
        this.jButton5.setText("全部");
        this.jButton5.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton5ActionPerformed(evt);
            }
        });
        this.jButton6.setText("在线");
        this.jButton6.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton6ActionPerformed(evt);
            }
        });
        this.jButton7.setText("离线");
        this.jButton7.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton7ActionPerformed(evt);
            }
        });
        this.jButton9.setText("封停");
        this.jButton9.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton9ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel37Layout = new GroupLayout((Container)this.jPanel37);
        this.jPanel37.setLayout((LayoutManager)jPanel37Layout);
        jPanel37Layout.setHorizontalGroup((Group)jPanel37Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel37Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group)jPanel37Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group)jPanel37Layout.createSequentialGroup().addComponent((Component)this.jButton9).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton7)).addGroup((Group)jPanel37Layout.createSequentialGroup().addComponent((Component)this.jButton5).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton6))).addGap(29, 29, 29)));
        jPanel37Layout.setVerticalGroup((Group)jPanel37Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel37Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel37Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton6).addComponent((Component)this.jButton5)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel37Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton7).addComponent((Component)this.jButton9)).addContainerGap(-1, 32767)));
        this.jPanel38.setBorder((Border)BorderFactory.createTitledBorder("帐号处理"));
        this.jPanel38.setPreferredSize(new Dimension(1000, 69));
        this.jButton10.setText("修改");
        this.jButton10.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton10ActionPerformed(evt);
            }
        });
        this.jLabel4.setText("账号:");
        this.jLabel5.setText("抵用:");
        this.jLabel6.setText("点券:");
        this.jLabel8.setText("QQ:");
        this.jLabel10.setText("密码:");
        this.jButton11.setText("修改");
        this.jButton11.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton11ActionPerformed(evt);
            }
        });
        this.jButton12.setText("查找");
        this.jButton12.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton12ActionPerformed(evt);
            }
        });
        this.jButton16.setText("注册");
        this.jButton16.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton16ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel38Layout = new GroupLayout((Container)this.jPanel38);
        this.jPanel38.setLayout((LayoutManager)jPanel38Layout);
        jPanel38Layout.setHorizontalGroup((Group)jPanel38Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel38Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel38Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel38Layout.createSequentialGroup().addComponent((Component)this.账号, -2, 137, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton12)).addComponent((Component)this.jLabel4)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group)jPanel38Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel5).addComponent((Component)this.抵用, -2, 114, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel38Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.点券, -2, 114, -2).addComponent((Component)this.jLabel6)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel38Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel8).addGroup((Group)jPanel38Layout.createSequentialGroup().addComponent((Component)this.QQ, -2, 137, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton10))).addGap(6, 6, 6).addGroup((Group)jPanel38Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel38Layout.createSequentialGroup().addComponent((Component)this.jLabel10).addGap(0, 0, 32767)).addGroup((Group)jPanel38Layout.createSequentialGroup().addComponent((Component)this.密码, -2, 137, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jButton11, -2, 67, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.jButton16, -2, 66, -2))).addContainerGap()));
        jPanel38Layout.setVerticalGroup((Group)jPanel38Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel38Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel38Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel4).addGroup((Group)jPanel38Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel5).addComponent((Component)this.jLabel6).addComponent((Component)this.jLabel8).addComponent((Component)this.jLabel10))).addGap(1, 1, 1).addGroup((Group)jPanel38Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel38Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.账号, -2, -1, -2).addComponent((Component)this.jButton12)).addGroup(Alignment.TRAILING, (Group)jPanel38Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.抵用, -2, -1, -2).addComponent((Component)this.点券, -2, -1, -2).addComponent((Component)this.QQ, -2, -1, -2).addComponent((Component)this.jButton10).addComponent((Component)this.密码, -2, 23, -2).addComponent((Component)this.jButton11).addComponent((Component)this.jButton16))).addContainerGap(-1, 32767)));
        jPanel38Layout.linkSize(1, this.QQ, this.jButton10, this.jButton11, this.jButton12, this.jLabel10, this.jLabel4, this.jLabel5, this.jLabel6, this.jLabel8, this.密码, this.抵用, this.点券, this.账号);
        this.jPanel104.setBorder((Border)BorderFactory.createTitledBorder("GM设置"));
        this.jButton102.setText("变成GM");
        this.jButton102.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton102ActionPerformed(evt);
            }
        });
        this.jButton120.setText("变成玩家");
        this.jButton120.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton120ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel104Layout = new GroupLayout((Container)this.jPanel104);
        this.jPanel104.setLayout((LayoutManager)jPanel104Layout);
        jPanel104Layout.setHorizontalGroup((Group)jPanel104Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel104Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel104Layout.createParallelGroup(Alignment.TRAILING, false).addComponent((Component)this.jButton120, -1, 91, 32767).addComponent((Component)this.jButton102, -1, -1, 32767)).addContainerGap(-1, 32767)));
        jPanel104Layout.setVerticalGroup((Group)jPanel104Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel104Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jButton102).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.jButton120).addContainerGap()));
        final GroupLayout jPanel35Layout = new GroupLayout((Container)this.jPanel35);
        this.jPanel35.setLayout((LayoutManager)jPanel35Layout);
        jPanel35Layout.setHorizontalGroup((Group)jPanel35Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel35Layout.createSequentialGroup().addComponent((Component)this.jPanel37, -2, 145, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel36, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel104, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel38, -2, 961, -2).addContainerGap(-1, 32767)));
        jPanel35Layout.setVerticalGroup((Group)jPanel35Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel35Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group)jPanel35Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jPanel36, -1, -1, 32767).addComponent((Component)this.jPanel37, -1, -1, 32767).addComponent((Component)this.jPanel38, -1, 96, 32767).addComponent((Component)this.jPanel104, -1, -1, 32767)).addGap(104, 104, 104)));
        this.jPanel36.getAccessibleContext().setAccessibleName("");
        this.jTabbedPane7.addTab("常用功能", (Component)this.jPanel35);
        final GroupLayout jPanel6Layout = new GroupLayout((Container)this.jPanel6);
        this.jPanel6.setLayout((LayoutManager)jPanel6Layout);
        jPanel6Layout.setHorizontalGroup((Group)jPanel6Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel6Layout.createSequentialGroup().addGroup((Group)jPanel6Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel6Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jTabbedPane7, -1, 1423, 32767)).addComponent((Component)this.jScrollPane8)).addContainerGap()));
        jPanel6Layout.setVerticalGroup((Group)jPanel6Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel6Layout.createSequentialGroup().addComponent((Component)this.jScrollPane8, -2, 434, -2).addGap(3, 3, 3).addComponent((Component)this.jTabbedPane7, -2, 214, -2).addContainerGap(42, 32767)));
        this.jTabbedPane1.addTab("帐号信息", (Component)this.jPanel6);
        this.jScrollPane2.setHorizontalScrollBarPolicy(32);
        this.角色信息.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.角色信息.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "状态", "账号ID", "角色ID", "角色昵称", "等级", "职业", "所在地图", "金币", "豆豆", "力量", "敏捷", "智力", "运气", "MaxHP", "MaxMP", "发型", "脸型", "GM", "能力点", "技能点" }) {
            boolean[] canEdit = { false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.角色信息.setName("");
        this.角色信息.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane2.setViewportView((Component)this.角色信息);
        if (this.角色信息.getColumnModel().getColumnCount() > 0) {
            this.角色信息.getColumnModel().getColumn(0).setPreferredWidth(50);
            this.角色信息.getColumnModel().getColumn(6).setPreferredWidth(80);
        }
        this.角色操作.addChangeListener((ChangeListener)new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                玩家控制台.this.角色操作StateChanged(evt);
            }
        });
        this.jPanel39.setMinimumSize(new Dimension(200, 106));
        this.jPanel42.setBorder((Border)BorderFactory.createTitledBorder("能力修改"));
        this.jButton21.setText("修改");
        this.jButton21.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton21ActionPerformed(evt);
            }
        });
        this.jLabel12.setText("等级:");
        this.jLabel14.setText("力量:");
        this.jLabel39.setText("敏捷:");
        this.智力.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.智力ActionPerformed(evt);
            }
        });
        this.jLabel40.setText("智力:");
        this.jLabel41.setText("运气:");
        this.MAXHP.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.MAXHPActionPerformed(evt);
            }
        });
        this.jLabel42.setText("MAXHP:");
        this.jLabel43.setText("MAXMP:");
        this.jLabel44.setText("脸型:");
        this.jLabel45.setText("发型:");
        this.jLabel122.setText("技能点:");
        this.jLabel123.setText("能力点:");
        final GroupLayout jPanel42Layout = new GroupLayout((Container)this.jPanel42);
        this.jPanel42.setLayout((LayoutManager)jPanel42Layout);
        jPanel42Layout.setHorizontalGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel42Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel12).addComponent((Component)this.等级, -2, 51, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel14).addComponent((Component)this.力量, -2, 60, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel39).addComponent((Component)this.敏捷, -2, 60, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel40).addComponent((Component)this.智力, -2, 60, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel41).addComponent((Component)this.运气, -2, 60, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.MAXHP).addComponent((Component)this.jLabel43, -1, 60, 32767)).addGap(6, 6, 6).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.MAXMP, -2, 60, -2).addComponent((Component)this.jLabel42, Alignment.TRAILING, -2, 60, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel42Layout.createSequentialGroup().addComponent((Component)this.发型, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.脸型, -2, 60, -2)).addGroup((Group)jPanel42Layout.createSequentialGroup().addComponent((Component)this.jLabel45, -2, 52, -2).addGap(18, 18, 18).addComponent((Component)this.jLabel44, -2, 52, -2))).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel123).addComponent((Component)this.能力点, -2, 60, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel42Layout.createSequentialGroup().addComponent((Component)this.技能点, -2, 60, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jButton21)).addComponent((Component)this.jLabel122)).addGap(0, 0, 32767)));
        jPanel42Layout.setVerticalGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel42Layout.createSequentialGroup().addGap(14, 14, 14).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel12, Alignment.TRAILING).addComponent((Component)this.jLabel14, Alignment.TRAILING).addComponent((Component)this.jLabel39, Alignment.TRAILING).addComponent((Component)this.jLabel40, Alignment.TRAILING).addComponent((Component)this.jLabel41, Alignment.TRAILING).addGroup(Alignment.TRAILING, (Group)jPanel42Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel42).addComponent((Component)this.jLabel43).addComponent((Component)this.jLabel44).addComponent((Component)this.jLabel45).addComponent((Component)this.jLabel122).addComponent((Component)this.jLabel123))).addGap(2, 2, 2).addGroup((Group)jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.等级, Alignment.TRAILING, -2, -1, -2).addComponent((Component)this.力量, Alignment.TRAILING, -2, -1, -2).addComponent((Component)this.敏捷, Alignment.TRAILING, -2, -1, -2).addComponent((Component)this.智力, Alignment.TRAILING, -2, -1, -2).addComponent((Component)this.运气, Alignment.TRAILING, -2, -1, -2).addGroup(Alignment.TRAILING, (Group)jPanel42Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.MAXHP, -2, -1, -2).addComponent((Component)this.MAXMP, -2, -1, -2).addComponent((Component)this.脸型, -2, -1, -2).addComponent((Component)this.发型, -2, -1, -2).addComponent((Component)this.能力点, -2, -1, -2).addComponent((Component)this.技能点, -2, -1, -2).addComponent((Component)this.jButton21))).addContainerGap(-1, 32767)));
        this.jPanel55.setBorder((Border)BorderFactory.createTitledBorder("资产修改"));
        this.jLabel46.setText("金币:");
        this.jButton22.setText("修改");
        this.jButton22.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton22ActionPerformed(evt);
            }
        });
        this.jLabel47.setText("豆豆:");
        this.jPanel107.setBorder((Border)BorderFactory.createTitledBorder("离线调整"));
        this.jLabel164.setText("所在地图:");
        this.jButton60.setText("修改");
        this.jButton60.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton60ActionPerformed(evt);
            }
        });
        this.jLabel165.setText("职业ID:");
        this.职业ID.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.职业IDActionPerformed(evt);
            }
        });
        final GroupLayout jPanel107Layout = new GroupLayout((Container)this.jPanel107);
        this.jPanel107.setLayout((LayoutManager)jPanel107Layout);
        jPanel107Layout.setHorizontalGroup((Group)jPanel107Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel107Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel107Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.所在地图).addGroup((Group)jPanel107Layout.createSequentialGroup().addComponent((Component)this.jLabel164, -2, 99, -2).addGap(0, 56, 32767))).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel107Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel107Layout.createSequentialGroup().addComponent((Component)this.职业ID, -2, 85, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton60)).addComponent((Component)this.jLabel165, -2, 52, -2)).addContainerGap()));
        jPanel107Layout.setVerticalGroup((Group)jPanel107Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel107Layout.createSequentialGroup().addGap(14, 14, 14).addGroup((Group)jPanel107Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel165).addComponent((Component)this.jLabel164)).addGap(1, 1, 1).addGroup((Group)jPanel107Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.所在地图, -2, -1, -2).addComponent((Component)this.职业ID, -2, -1, -2).addComponent((Component)this.jButton60)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel55Layout = new GroupLayout((Container)this.jPanel55);
        this.jPanel55.setLayout((LayoutManager)jPanel55Layout);
        jPanel55Layout.setHorizontalGroup((Group)jPanel55Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel55Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel55Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.金币, -2, 97, -2).addComponent((Component)this.jLabel46, -2, 52, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel55Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel47, -2, 52, -2).addGroup((Group)jPanel55Layout.createSequentialGroup().addComponent((Component)this.豆豆, -2, 85, -2).addGap(18, 18, 18).addComponent((Component)this.jButton22, -2, 96, -2))).addPreferredGap(ComponentPlacement.RELATED, 48, 32767).addComponent((Component)this.jPanel107, -2, -1, -2).addContainerGap()));
        jPanel55Layout.setVerticalGroup((Group)jPanel55Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel55Layout.createSequentialGroup().addGap(14, 14, 14).addGroup((Group)jPanel55Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel47).addComponent((Component)this.jLabel46)).addGap(2, 2, 2).addGroup((Group)jPanel55Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.金币, -2, -1, -2).addComponent((Component)this.豆豆, -2, -1, -2).addComponent((Component)this.jButton22)).addContainerGap(-1, 32767)).addComponent((Component)this.jPanel107, Alignment.TRAILING, -1, -1, 32767));
        this.jPanel56.setBorder((Border)BorderFactory.createTitledBorder("角色筛选"));
        this.jLabel50.setText("角色昵称:");
        this.角色昵称.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.角色昵称ActionPerformed(evt);
            }
        });
        this.jButton57.setText("查找");
        this.jButton57.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton57ActionPerformed(evt);
            }
        });
        this.jButton64.setText("删除");
        this.jButton64.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton64ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel56Layout = new GroupLayout((Container)this.jPanel56);
        this.jPanel56.setLayout((LayoutManager)jPanel56Layout);
        jPanel56Layout.setHorizontalGroup((Group)jPanel56Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel56Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel56Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel56Layout.createSequentialGroup().addComponent((Component)this.jLabel50).addGap(0, 0, 32767)).addComponent((Component)this.角色昵称)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel56Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jButton64, Alignment.TRAILING).addComponent((Component)this.jButton57, Alignment.TRAILING))));
        jPanel56Layout.setVerticalGroup((Group)jPanel56Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel56Layout.createSequentialGroup().addGroup((Group)jPanel56Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel56Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent((Component)this.jButton57).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton64)).addGroup((Group)jPanel56Layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent((Component)this.jLabel50).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.角色昵称, -2, -1, -2).addGap(0, 0, 32767))).addContainerGap()));
        this.jPanel57.setBorder((Border)BorderFactory.createTitledBorder("角色解救"));
        this.jButton58.setText("卡物品");
        this.jButton58.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton58ActionPerformed(evt);
            }
        });
        this.jButton59.setText("卡家族");
        this.jButton59.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton59ActionPerformed(evt);
            }
        });
        this.jButton62.setText("卡登录");
        this.jButton62.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton62ActionPerformed(evt);
            }
        });
        this.jButton63.setText("卡头脸");
        this.jButton63.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton63ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel57Layout = new GroupLayout((Container)this.jPanel57);
        this.jPanel57.setLayout((LayoutManager)jPanel57Layout);
        jPanel57Layout.setHorizontalGroup((Group)jPanel57Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel57Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group)jPanel57Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group)jPanel57Layout.createSequentialGroup().addComponent((Component)this.jButton63).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton62)).addGroup((Group)jPanel57Layout.createSequentialGroup().addComponent((Component)this.jButton58).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton59))).addContainerGap()));
        jPanel57Layout.setVerticalGroup((Group)jPanel57Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel57Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel57Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton59).addComponent((Component)this.jButton58)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel57Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton62).addComponent((Component)this.jButton63)).addContainerGap(-1, 32767)));
        this.jPanel60.setBorder((Border)BorderFactory.createTitledBorder("小甜点"));
        this.jButton67.setText("还原键盘技能");
        this.jButton67.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton67ActionPerformed(evt);
            }
        });
        this.jButton70.setText("备份键盘技能");
        this.jButton70.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton70ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel60Layout = new GroupLayout((Container)this.jPanel60);
        this.jPanel60.setLayout((LayoutManager)jPanel60Layout);
        jPanel60Layout.setHorizontalGroup((Group)jPanel60Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel60Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group)jPanel60Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component)this.jButton70).addComponent((Component)this.jButton67)).addContainerGap()));
        jPanel60Layout.setVerticalGroup((Group)jPanel60Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel60Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jButton67).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton70).addContainerGap(-1, 32767)));
        this.jPanel59.setBorder((Border)BorderFactory.createTitledBorder("进阶数据"));
        this.jButton66.setText("读取角色背包");
        this.jButton66.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton66ActionPerformed(evt);
            }
        });
        this.jButton69.setText("读取角色技能");
        this.jButton69.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton69ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel59Layout = new GroupLayout((Container)this.jPanel59);
        this.jPanel59.setLayout((LayoutManager)jPanel59Layout);
        jPanel59Layout.setHorizontalGroup((Group)jPanel59Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel59Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group)jPanel59Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component)this.jButton69).addComponent((Component)this.jButton66)).addContainerGap(-1, 32767)));
        jPanel59Layout.setVerticalGroup((Group)jPanel59Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel59Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jButton66).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton69).addContainerGap(-1, 32767)));
        this.jPanel105.setBorder((Border)BorderFactory.createTitledBorder("GM设置"));
        this.jButton103.setText("变成GM");
        this.jButton103.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton103ActionPerformed(evt);
            }
        });
        this.jButton121.setText("变成玩家");
        this.jButton121.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton121ActionPerformed(evt);
            }
        });
        this.jButton124.setText("变成巡查者");
        this.jButton124.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton124ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel105Layout = new GroupLayout((Container)this.jPanel105);
        this.jPanel105.setLayout((LayoutManager)jPanel105Layout);
        jPanel105Layout.setHorizontalGroup((Group)jPanel105Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel105Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel105Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jButton124, -1, -1, 32767).addComponent((Component)this.jButton121, -1, -1, 32767).addComponent((Component)this.jButton103, -1, -1, 32767)).addContainerGap(18, 32767)));
        jPanel105Layout.setVerticalGroup((Group)jPanel105Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel105Layout.createSequentialGroup().addComponent((Component)this.jButton103).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton121).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton124).addContainerGap(14, 32767)));
        this.jPanel106.setBorder((Border)BorderFactory.createTitledBorder("角色查看"));
        this.jButton104.setForeground(new Color(255, 0, 0));
        this.jButton104.setText("全部角色");
        this.jButton104.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton104ActionPerformed(evt);
            }
        });
        this.jButton122.setText("管理角色");
        this.jButton122.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton122ActionPerformed(evt);
            }
        });
        this.jButton105.setText("在线角色");
        this.jButton105.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton105ActionPerformed(evt);
            }
        });
        this.jButton123.setText("离线角色");
        this.jButton123.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton123ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel106Layout = new GroupLayout((Container)this.jPanel106);
        this.jPanel106.setLayout((LayoutManager)jPanel106Layout);
        jPanel106Layout.setHorizontalGroup((Group)jPanel106Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel106Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel106Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jButton104, -1, -1, 32767).addComponent((Component)this.jButton122, -1, -1, 32767)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group)jPanel106Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jButton105, -1, -1, 32767).addComponent((Component)this.jButton123)).addContainerGap(-1, 32767)));
        jPanel106Layout.setVerticalGroup((Group)jPanel106Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel106Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel106Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group)jPanel106Layout.createSequentialGroup().addComponent((Component)this.jButton105).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.jButton123)).addGroup((Group)jPanel106Layout.createSequentialGroup().addComponent((Component)this.jButton104).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.jButton122))).addContainerGap()));
        final GroupLayout jPanel39Layout = new GroupLayout((Container)this.jPanel39);
        this.jPanel39.setLayout((LayoutManager)jPanel39Layout);
        jPanel39Layout.setHorizontalGroup((Group)jPanel39Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel39Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel39Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel39Layout.createSequentialGroup().addComponent((Component)this.jPanel57, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel59, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel60, -2, -1, -2)).addGroup((Group)jPanel39Layout.createSequentialGroup().addComponent((Component)this.jPanel106, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jPanel56, -1, -1, 32767))).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group)jPanel39Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group)jPanel39Layout.createSequentialGroup().addComponent((Component)this.jPanel105, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel55, -2, -1, -2)).addComponent((Component)this.jPanel42, -1, -1, 32767)).addGap(330, 330, 330)));
        jPanel39Layout.setVerticalGroup((Group)jPanel39Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel39Layout.createSequentialGroup().addGroup((Group)jPanel39Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jPanel42, -1, -1, 32767).addComponent((Component)this.jPanel56, -1, -1, 32767).addComponent((Component)this.jPanel106, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED, 12, 32767).addGroup((Group)jPanel39Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jPanel60, -1, -1, 32767).addComponent((Component)this.jPanel55, -1, -1, 32767).addComponent((Component)this.jPanel59, -1, -1, 32767).addComponent((Component)this.jPanel105, -1, -1, 32767).addComponent((Component)this.jPanel57, -1, -1, 32767)).addContainerGap(62, 32767)));
        this.jPanel55.setBorder((Border)BorderFactory.createTitledBorder("资产修改"));
        this.jPanel56.setBorder((Border)BorderFactory.createTitledBorder("角色筛选"));
        this.jPanel59.setBorder((Border)BorderFactory.createTitledBorder("进阶数据"));
        this.角色操作.addTab("角色属性操作", (Component)this.jPanel39);
        this.jPanel43.setMinimumSize(new Dimension(200, 106));
        this.jPanel40.setBorder((Border)BorderFactory.createTitledBorder("天降福利"));
        this.jButton35.setText("发放金币");
        this.jButton35.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton35ActionPerformed(evt);
            }
        });
        this.选中玩家A.setText("选中玩家");
        this.发放奖励数量.setText("10000");
        this.在线玩家A.setSelected(true);
        this.在线玩家A.setText("在线玩家");
        this.jLabel11.setText("发放奖励数量:");
        this.jButton36.setText("发放经验");
        this.jButton36.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton36ActionPerformed(evt);
            }
        });
        this.jButton37.setText("发放豆豆");
        this.jButton37.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton37ActionPerformed(evt);
            }
        });
        this.jButton38.setText("发放抵用");
        this.jButton38.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton38ActionPerformed(evt);
            }
        });
        this.jButton39.setText("发放点券");
        this.jButton39.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton39ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel40Layout = new GroupLayout((Container)this.jPanel40);
        this.jPanel40.setLayout((LayoutManager)jPanel40Layout);
        jPanel40Layout.setHorizontalGroup((Group)jPanel40Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel40Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel40Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel40Layout.createSequentialGroup().addComponent((Component)this.选中玩家A).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.在线玩家A).addPreferredGap(ComponentPlacement.RELATED, 35, 32767).addComponent((Component)this.jButton37, -2, 115, -2)).addGroup((Group)jPanel40Layout.createSequentialGroup().addComponent((Component)this.发放奖励数量).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton36, -2, 115, -2)).addGroup((Group)jPanel40Layout.createSequentialGroup().addComponent((Component)this.jLabel11, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton35, -2, 115, -2)).addGroup(Alignment.TRAILING, (Group)jPanel40Layout.createSequentialGroup().addGap(0, 0, 32767).addGroup((Group)jPanel40Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jButton38, Alignment.TRAILING, -2, 125, -2).addComponent((Component)this.jButton39, Alignment.TRAILING, -2, 125, -2)))).addContainerGap()));
        jPanel40Layout.linkSize(0, this.jButton35, this.jButton36, this.jButton37, this.jButton38, this.jButton39);
        jPanel40Layout.setVerticalGroup((Group)jPanel40Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel40Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel40Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton35).addComponent((Component)this.jLabel11)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel40Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel40Layout.createSequentialGroup().addComponent((Component)this.发放奖励数量, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel40Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.选中玩家A).addComponent((Component)this.在线玩家A))).addGroup((Group)jPanel40Layout.createSequentialGroup().addComponent((Component)this.jButton36).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton37, -2, 28, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton38, -2, 28, -2))).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton39, -2, 28, -2).addContainerGap()));
        jPanel40Layout.linkSize(1, this.jButton35, this.jButton36, this.jButton37, this.jButton38, this.jButton39);
        this.jPanel45.setBorder((Border)BorderFactory.createTitledBorder("便捷功能"));
        this.jButton28.setText("强制下线");
        this.jButton28.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton28ActionPerformed(evt);
            }
        });
        this.jButton29.setText("封号+强制下线");
        this.jButton29.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton29ActionPerformed(evt);
            }
        });
        this.jButton30.setText("还原新手删技能");
        this.jButton30.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton30ActionPerformed(evt);
            }
        });
        this.jButton31.setText("全员下线");
        this.jButton31.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton31ActionPerformed(evt);
            }
        });
        this.jButton32.setText("传送到自由市场");
        this.jButton32.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton32ActionPerformed(evt);
            }
        });
        this.jButton33.setText("传送到特定地图");
        this.jButton33.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton33ActionPerformed(evt);
            }
        });
        this.jButton34.setText("清空所有背包");
        this.jButton34.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton34ActionPerformed(evt);
            }
        });
        this.jButton68.setText("清空全身装备");
        this.jButton68.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton68ActionPerformed(evt);
            }
        });
        this.jButton71.setText("修改脸型");
        this.jButton71.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton71ActionPerformed(evt);
            }
        });
        this.jButton72.setText("修改发型");
        this.jButton72.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton72ActionPerformed(evt);
            }
        });
        this.jButton73.setText("一键满级");
        this.jButton73.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton73ActionPerformed(evt);
            }
        });
        this.jButton74.setText("一键满技能");
        this.jButton74.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton74ActionPerformed(evt);
            }
        });
        this.jButton75.setText("一键满属性");
        this.jButton75.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton75ActionPerformed(evt);
            }
        });
        this.jButton76.setText("开启技能槽");
        this.jButton76.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton76ActionPerformed(evt);
            }
        });
        this.jButton77.setText("击杀玩家");
        this.jButton77.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton77ActionPerformed(evt);
            }
        });
        this.jButton78.setText("关小黑屋");
        this.jButton78.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton78ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel45Layout = new GroupLayout((Container)this.jPanel45);
        this.jPanel45.setLayout((LayoutManager)jPanel45Layout);
        jPanel45Layout.setHorizontalGroup((Group)jPanel45Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel45Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel45Layout.createSequentialGroup().addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jButton28, -2, 128, -2).addComponent((Component)this.jButton30, -2, 128, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel45Layout.createSequentialGroup().addComponent((Component)this.jButton33, -2, 125, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton75, -2, 125, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton68, -2, 125, -2)).addGroup((Group)jPanel45Layout.createSequentialGroup().addComponent((Component)this.jButton32, -2, 125, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton73, -2, 125, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton34, -2, 125, -2)))).addGroup((Group)jPanel45Layout.createSequentialGroup().addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jButton29, -2, 112, -2).addComponent((Component)this.jButton31, -2, 112, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jButton72, -2, 112, -2).addComponent((Component)this.jButton71, -2, 112, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jButton74, -2, 112, -2).addComponent((Component)this.jButton76, -2, 112, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jButton77, -2, 112, -2).addComponent((Component)this.jButton78, -2, 112, -2)))).addContainerGap(-1, 32767)));
        jPanel45Layout.linkSize(0, this.jButton28, this.jButton29, this.jButton30, this.jButton31, this.jButton32, this.jButton33, this.jButton34, this.jButton68, this.jButton71, this.jButton72, this.jButton73, this.jButton74, this.jButton75, this.jButton76, this.jButton77, this.jButton78);
        jPanel45Layout.setVerticalGroup((Group)jPanel45Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel45Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton28, -2, 38, -2).addComponent((Component)this.jButton32).addComponent((Component)this.jButton34).addComponent((Component)this.jButton73)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton30).addComponent((Component)this.jButton33).addComponent((Component)this.jButton68).addComponent((Component)this.jButton75)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton29).addComponent((Component)this.jButton71).addComponent((Component)this.jButton74).addComponent((Component)this.jButton77)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel45Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton31).addComponent((Component)this.jButton72).addComponent((Component)this.jButton76).addComponent((Component)this.jButton78)).addContainerGap(-1, 32767)));
        jPanel45Layout.linkSize(1, this.jButton28, this.jButton29, this.jButton30, this.jButton31, this.jButton32, this.jButton33, this.jButton34, this.jButton68, this.jButton71, this.jButton72, this.jButton73, this.jButton74, this.jButton75, this.jButton76, this.jButton77, this.jButton78);
        this.jPanel41.setBorder((Border)BorderFactory.createTitledBorder("天降物品"));
        this.选中玩家B.setSelected(true);
        this.选中玩家B.setText("选中玩家");
        this.在线玩家B.setText("在线玩家");
        this.jLabel13.setText("发放物品ID:");
        this.jButton41.setText("发放物品");
        this.jButton41.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton41ActionPerformed(evt);
            }
        });
        this.jLabel15.setText("发放物品数量:");
        final GroupLayout jPanel41Layout = new GroupLayout((Container)this.jPanel41);
        this.jPanel41.setLayout((LayoutManager)jPanel41Layout);
        jPanel41Layout.setHorizontalGroup((Group)jPanel41Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel41Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel41Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel41Layout.createSequentialGroup().addGroup((Group)jPanel41Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel13, -1, -1, 32767).addComponent((Component)this.发放物品ID, -2, 134, -2)).addGap(127, 127, 127)).addGroup((Group)jPanel41Layout.createSequentialGroup().addComponent((Component)this.jLabel15, -1, -1, 32767).addGap(32, 32, 32)).addGroup((Group)jPanel41Layout.createSequentialGroup().addGroup((Group)jPanel41Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel41Layout.createSequentialGroup().addComponent((Component)this.选中玩家B).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.在线玩家B).addGap(0, 0, 32767)).addGroup((Group)jPanel41Layout.createSequentialGroup().addComponent((Component)this.发放物品数量, -2, 134, -2).addPreferredGap(ComponentPlacement.RELATED, 92, 32767).addComponent((Component)this.jButton41, -2, 115, -2))).addContainerGap()))));
        jPanel41Layout.setVerticalGroup((Group)jPanel41Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel41Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jLabel13).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.发放物品ID, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jLabel15).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel41Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.发放物品数量, -2, -1, -2).addComponent((Component)this.jButton41)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel41Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.选中玩家B).addComponent((Component)this.在线玩家B)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel43Layout = new GroupLayout((Container)this.jPanel43);
        this.jPanel43.setLayout((LayoutManager)jPanel43Layout);
        jPanel43Layout.setHorizontalGroup((Group)jPanel43Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel43Layout.createSequentialGroup().addComponent((Component)this.jPanel45, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel40, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel41, -2, -1, -2).addGap(0, 133, 32767)));
        jPanel43Layout.setVerticalGroup((Group)jPanel43Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel43Layout.createSequentialGroup().addGroup((Group)jPanel43Layout.createParallelGroup(Alignment.TRAILING, false).addComponent((Component)this.jPanel41, -1, -1, 32767).addComponent((Component)this.jPanel45, Alignment.LEADING, -1, -1, 32767).addComponent((Component)this.jPanel40, Alignment.LEADING, -1, -1, 32767)).addGap(0, 75, 32767)));
        this.jPanel40.setBorder((Border)BorderFactory.createTitledBorder("天降福利"));
        this.jPanel45.setBorder((Border)BorderFactory.createTitledBorder("便捷功能"));
        this.角色操作.addTab("在线角色操作", (Component)this.jPanel43);
        this.jPanel47.setMinimumSize(new Dimension(200, 106));
        this.jPanel50.setBorder((Border)BorderFactory.createTitledBorder("基础属性定制"));
        this.jButton43.setText("发放全新装备");
        this.jButton43.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton43ActionPerformed(evt);
            }
        });
        this.jLabel25.setText("装备ID:");
        this.jLabel28.setText("主属性(力量/智力/敏捷/运气):");
        this.装备力量.setText("0");
        this.装备智力.setText("0");
        this.装备敏捷.setText("0");
        this.装备运气.setText("0");
        this.jLabel29.setText("生存属性(HP/MP):");
        this.装备HP.setText("0");
        this.装备MP.setText("0");
        this.jLabel30.setText("攻防属性(攻击力/魔法力/物理防御/魔法防御):");
        this.装备攻击力.setText("0");
        this.装备魔法力.setText("0");
        this.装备物理防御.setText("0");
        this.装备可以交易.setSelected(true);
        this.装备可以交易.setText("可以交易");
        this.装备可以交易.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.装备可以交易ActionPerformed(evt);
            }
        });
        this.装备不可交易.setText("不可交易");
        this.装备魔法防御.setText("0");
        this.jLabel55.setText("基础潜能:");
        this.装备潜能.setText("0");
        this.jLabel56.setText("基础星数:");
        this.装备星数.setText("0");
        this.装备命中.setText("0");
        this.jLabel54.setText("杂项属性(命中/回避/移动速度/跳跃力):");
        this.装备回避.setText("0");
        this.装备移动速度.setText("0");
        this.装备跳跃力.setText("0");
        this.装备砸卷上限.setText("0");
        this.装备砸卷上限.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.装备砸卷上限ActionPerformed(evt);
            }
        });
        this.jLabel31.setText("砸卷上限:");
        this.装备限时开关.setText("限时时间(H)");
        this.装备限时开关.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.装备限时开关ActionPerformed(evt);
            }
        });
        this.装备限时时间.setText("0");
        this.装备限时时间.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.装备限时时间ActionPerformed(evt);
            }
        });
        this.jButton44.setText("发放第一格装备");
        this.jButton44.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton44ActionPerformed(evt);
            }
        });
        this.jButton46.setText("收取第一格装备");
        this.jButton46.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton46ActionPerformed(evt);
            }
        });
        this.上卷次数.setText("0");
        this.jLabel32.setText("上卷次数:");
        final GroupLayout jPanel50Layout = new GroupLayout((Container)this.jPanel50);
        this.jPanel50.setLayout((LayoutManager)jPanel50Layout);
        jPanel50Layout.setHorizontalGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel50Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel25).addComponent((Component)this.jLabel29, -2, 169, -2).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.装备HP, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备MP, -2, 60, -2)).addComponent((Component)this.装备ID, -2, 126, -2).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.装备潜能, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备星数, -2, 60, -2)).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.jLabel55, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jLabel56, -2, 60, -2))).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group)jPanel50Layout.createSequentialGroup().addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.装备攻击力, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备魔法力, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备物理防御, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备魔法防御, -2, 60, -2).addGap(18, 18, 18).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.上卷次数, -2, 60, -2).addComponent((Component)this.jLabel32, -2, 60, -2))).addGroup((Group)jPanel50Layout.createSequentialGroup().addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.装备命中, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备回避, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备移动速度, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备跳跃力, -2, 60, -2)).addComponent((Component)this.jLabel54, -2, 273, -2)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.装备可以交易).addComponent((Component)this.装备不可交易)))).addGap(145, 145, 145)).addGroup((Group)jPanel50Layout.createSequentialGroup().addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel50Layout.createSequentialGroup().addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.装备力量, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备智力, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备敏捷, -2, 60, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.装备运气, -2, 60, -2)).addComponent((Component)this.jLabel28, -2, 169, -2)).addGap(18, 18, 18).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.装备砸卷上限, -2, 60, -2).addComponent((Component)this.jLabel31, -2, 60, -2)).addGap(38, 38, 38).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.装备限时时间).addComponent((Component)this.装备限时开关, -1, -1, 32767))).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.jLabel30, -2, 273, -2).addGap(103, 103, 103).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.CENTER, false).addComponent((Component)this.jButton44, -1, -1, 32767).addComponent((Component)this.jButton43, -1, -1, 32767).addComponent((Component)this.jButton46, -1, -1, 32767)))).addGap(12, 12, 12)))));
        jPanel50Layout.linkSize(0, this.jButton43, this.jButton44, this.jButton46, this.装备限时时间);
        jPanel50Layout.setVerticalGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel50Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel50Layout.createSequentialGroup().addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.jLabel25, -2, 23, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.装备ID, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel29).addComponent((Component)this.jLabel30)).addGap(2, 2, 2).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.装备HP, -2, -1, -2).addComponent((Component)this.装备MP, -2, -1, -2).addComponent((Component)this.装备攻击力, -2, -1, -2).addComponent((Component)this.装备魔法力, -2, -1, -2).addComponent((Component)this.装备物理防御, -2, -1, -2).addComponent((Component)this.装备魔法防御, -2, -1, -2))).addGroup((Group)jPanel50Layout.createSequentialGroup().addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.jLabel28, -2, 23, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.装备力量, -2, -1, -2).addComponent((Component)this.装备智力, -2, -1, -2).addComponent((Component)this.装备敏捷, -2, -1, -2).addComponent((Component)this.装备运气, -2, -1, -2).addComponent((Component)this.装备砸卷上限, -2, -1, -2))).addGroup((Group)jPanel50Layout.createSequentialGroup().addGap(4, 4, 4).addComponent((Component)this.jLabel31, -2, 19, -2))).addGap(2, 2, 2).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel50Layout.createSequentialGroup().addGap(25, 25, 25).addComponent((Component)this.上卷次数, -2, -1, -2)).addComponent((Component)this.jLabel32, -2, 19, -2)))).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.jLabel54).addGap(2, 2, 2).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.装备命中, -2, -1, -2).addComponent((Component)this.装备回避, -2, -1, -2).addComponent((Component)this.装备移动速度, -2, -1, -2).addComponent((Component)this.装备跳跃力, -2, -1, -2))).addGroup((Group)jPanel50Layout.createSequentialGroup().addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel55).addComponent((Component)this.jLabel56)).addGap(1, 1, 1).addGroup((Group)jPanel50Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.装备潜能, -2, -1, -2).addComponent((Component)this.装备星数, -2, -1, -2))).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.装备可以交易).addGap(6, 6, 6).addComponent((Component)this.装备不可交易)))).addGroup((Group)jPanel50Layout.createSequentialGroup().addComponent((Component)this.装备限时开关).addGap(1, 1, 1).addComponent((Component)this.装备限时时间, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jButton46).addGap(4, 4, 4).addComponent((Component)this.jButton44).addGap(6, 6, 6).addComponent((Component)this.jButton43))).addContainerGap(22, 32767)));
        jPanel50Layout.linkSize(1, this.jButton43, this.jButton44, this.jButton46, this.装备限时时间);
        this.jLabel9.setText("<html><body>定制提示：<br>1、面板属性最低为0，最高为32767，不可不填或者超出范围，否则可能发放失败！<br>2、砸卷次数上限是127，超出范围也会发放失败！<br>3、读取装备会先将玩家装备收取，点击发放才会重新发给玩家。</body></html>");
        this.收取装备状态.setText("收取装备状态：[无]");
        final GroupLayout jPanel47Layout = new GroupLayout((Container)this.jPanel47);
        this.jPanel47.setLayout((LayoutManager)jPanel47Layout);
        jPanel47Layout.setHorizontalGroup((Group)jPanel47Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel47Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jPanel50, -2, 695, -2).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group)jPanel47Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.收取装备状态).addComponent((Component)this.jLabel9, -2, 448, -2)).addContainerGap(255, 32767)));
        jPanel47Layout.setVerticalGroup((Group)jPanel47Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel47Layout.createSequentialGroup().addComponent((Component)this.jLabel9, -2, 139, -2).addGap(11, 11, 11).addComponent((Component)this.收取装备状态).addGap(0, 124, 32767)).addGroup((Group)jPanel47Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jPanel50, -2, -1, -2).addContainerGap(-1, 32767)));
        this.角色操作.addTab("在线定制装备", (Component)this.jPanel47);
        this.jPanel51.setMinimumSize(new Dimension(200, 106));
        final GroupLayout jPanel51Layout = new GroupLayout((Container)this.jPanel51);
        this.jPanel51.setLayout((LayoutManager)jPanel51Layout);
        jPanel51Layout.setHorizontalGroup((Group)jPanel51Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1418, 32767));
        jPanel51Layout.setVerticalGroup((Group)jPanel51Layout.createParallelGroup(Alignment.LEADING).addGap(0, 289, 32767));
        this.角色操作.addTab("杂项功能", (Component)this.jPanel51);
        this.选中角色名.setFont(new Font("sansserif", 0, 14));
        this.选中角色名.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/模拟玩家.png")));
        this.选中角色名.setText("当前选中角色:[无]");
        final GroupLayout jPanel7Layout = new GroupLayout((Container)this.jPanel7);
        this.jPanel7.setLayout((LayoutManager)jPanel7Layout);
        jPanel7Layout.setHorizontalGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel7Layout.createSequentialGroup().addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jScrollPane2, -1, 1433, 32767).addGroup(Alignment.TRAILING, (Group)jPanel7Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent((Component)this.选中角色名, -2, 232, -2))).addContainerGap()).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel7Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.角色操作, -1, 1423, 32767).addContainerGap())));
        jPanel7Layout.setVerticalGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel7Layout.createSequentialGroup().addComponent((Component)this.jScrollPane2, -2, 332, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.选中角色名).addContainerGap(331, 32767)).addGroup((Group)jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel7Layout.createSequentialGroup().addContainerGap(365, 32767).addComponent((Component)this.角色操作, -2, 318, -2).addContainerGap())));
        this.jTabbedPane1.addTab("角色信息", (Component)this.jPanel7);
        this.身上.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "物品名字", "物品ID", "物品数量" }) {
            boolean[] canEdit = { false, false, false, true };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.身上.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane21.setViewportView((Component)this.身上);
        this.资源页签.addTab("身上", (Component)this.jScrollPane21);
        this.装备.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "物品名字", "物品ID", "物品数量" }) {
            boolean[] canEdit = { false, false, false, false };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.装备.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane22.setViewportView((Component)this.装备);
        this.资源页签.addTab("装备", (Component)this.jScrollPane22);
        this.消耗.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "物品名字", "物品ID", "物品数量" }) {
            boolean[] canEdit = { false, false, false, true };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.消耗.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane23.setViewportView((Component)this.消耗);
        this.资源页签.addTab("消耗", (Component)this.jScrollPane23);
        this.设置.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "物品名字", "物品ID", "物品数量" }) {
            boolean[] canEdit = { false, false, false, true };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.设置.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane24.setViewportView((Component)this.设置);
        this.资源页签.addTab("设置", (Component)this.jScrollPane24);
        this.其他.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "物品名字", "物品ID", "物品数量" }) {
            boolean[] canEdit = { false, false, false, true };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.其他.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane25.setViewportView((Component)this.其他);
        this.资源页签.addTab("其他", (Component)this.jScrollPane25);
        this.特殊.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "物品名字", "物品ID", "物品数量" }) {
            boolean[] canEdit = { false, false, false, true };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.特殊.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane26.setViewportView((Component)this.特殊);
        this.资源页签.addTab("特殊", (Component)this.jScrollPane26);
        this.仓库.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "物品名字", "物品ID", "物品数量" }) {
            boolean[] canEdit = { false, false, false, true };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.仓库.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane27.setViewportView((Component)this.仓库);
        this.资源页签.addTab("仓库", (Component)this.jScrollPane27);
        this.时仓.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "物品名字", "物品ID", "物品数量" }) {
            boolean[] canEdit = { false, false, false, true };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.时仓.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane28.setViewportView((Component)this.时仓);
        this.资源页签.addTab("时仓", (Component)this.jScrollPane28);
        this.jPanel58.setBorder((Border)BorderFactory.createTitledBorder("物品属性"));
        this.jButton23.setText("修改选中行");
        this.jButton23.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton23ActionPerformed(evt);
            }
        });
        this.jButton65.setText("删除选中行");
        this.jButton65.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton65ActionPerformed(evt);
            }
        });
        this.jLabel51.setText("提示：只可以修改物品数量，不可以修改装备数量。");
        final GroupLayout jPanel58Layout = new GroupLayout((Container)this.jPanel58);
        this.jPanel58.setLayout((LayoutManager)jPanel58Layout);
        jPanel58Layout.setHorizontalGroup((Group)jPanel58Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel58Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jLabel51).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.jButton23).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton65).addContainerGap()));
        jPanel58Layout.setVerticalGroup((Group)jPanel58Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel58Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group)jPanel58Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton23).addComponent((Component)this.jButton65).addComponent((Component)this.jLabel51)).addContainerGap()));
        this.技能信息.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "技能名字", "技能ID", "目前等级", "最高等级" }) {
            boolean[] canEdit = { false, false, false, true, true };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.技能信息.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane13.setViewportView((Component)this.技能信息);
        this.jPanel61.setBorder((Border)BorderFactory.createTitledBorder("技能属性"));
        this.jButton24.setText("修改选中行");
        this.jButton24.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton24ActionPerformed(evt);
            }
        });
        this.jButton79.setText("删除选中行");
        this.jButton79.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton79ActionPerformed(evt);
            }
        });
        this.jLabel52.setText("提示：只可以修改技能目前等级和最大等级。");
        final GroupLayout jPanel61Layout = new GroupLayout((Container)this.jPanel61);
        this.jPanel61.setLayout((LayoutManager)jPanel61Layout);
        jPanel61Layout.setHorizontalGroup((Group)jPanel61Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel61Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jLabel52).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.jButton24).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton79).addContainerGap()));
        jPanel61Layout.setVerticalGroup((Group)jPanel61Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel61Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group)jPanel61Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton24).addComponent((Component)this.jButton79).addComponent((Component)this.jLabel52)).addContainerGap()));
        final GroupLayout jPanel8Layout = new GroupLayout((Container)this.jPanel8);
        this.jPanel8.setLayout((LayoutManager)jPanel8Layout);
        jPanel8Layout.setHorizontalGroup((Group)jPanel8Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel8Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel8Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jPanel58, -2, 500, 32767).addComponent((Component)this.资源页签, -1, 500, 32767)).addPreferredGap(ComponentPlacement.RELATED, 345, 32767).addGroup((Group)jPanel8Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jPanel61, -1, -1, 32767).addComponent((Component)this.jScrollPane13, -1, 578, 32767)).addContainerGap()));
        jPanel8Layout.setVerticalGroup((Group)jPanel8Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel8Layout.createSequentialGroup().addGroup((Group)jPanel8Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.资源页签, -1, 526, 32767).addComponent((Component)this.jScrollPane13)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel8Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jPanel58, -1, -1, 32767).addComponent((Component)this.jPanel61, -1, -1, 32767)).addContainerGap(94, 32767)));
        this.jTabbedPane1.addTab("道具/技能信息", (Component)this.jPanel8);
        this.家族信息.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "家族ID", "家族名称", "族长/角色ID", "成员1称呼", "成员2称呼", "成员3称呼", "成员4称呼", "成员5称呼", "家族GP" }) {
            boolean[] canEdit = { false, false, false, false, false, false, false, false, false };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.家族信息.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane29.setViewportView((Component)this.家族信息);
        this.jButton80.setText("读取所有家族信息");
        this.jButton80.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton80ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel10Layout = new GroupLayout((Container)this.jPanel10);
        this.jPanel10.setLayout((LayoutManager)jPanel10Layout);
        jPanel10Layout.setHorizontalGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jScrollPane29, Alignment.TRAILING, -1, 1443, 32767).addGroup((Group)jPanel10Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jButton80, -1, -1, 32767).addContainerGap()));
        jPanel10Layout.setVerticalGroup((Group)jPanel10Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel10Layout.createSequentialGroup().addComponent((Component)this.jScrollPane29, -1, 602, 32767).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton80).addGap(62, 62, 62)));
        this.jTabbedPane1.addTab("家族信息", (Component)this.jPanel10);
        final GroupLayout jPanel1Layout = new GroupLayout((Container)this.jPanel1);
        this.jPanel1.setLayout((LayoutManager)jPanel1Layout);
        jPanel1Layout.setHorizontalGroup((Group)jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel1Layout.createSequentialGroup().addComponent((Component)this.jTabbedPane1, -2, 1448, -2).addGap(0, 0, 32767)));
        jPanel1Layout.setVerticalGroup((Group)jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jTabbedPane1));
        玩家控制台.主界面菜单.addTab("玩家管理", (Icon)new ImageIcon(this.getClass().getResource("/image/管理.png")), (Component)this.jPanel1);
        this.jPanel75.setBorder((Border)BorderFactory.createTitledBorder("邮件内容"));
        this.jLabel128.setText("邮件标题:");
        this.邮件标题.setText("管理员邮件");
        this.jLabel129.setText("邮件正文:");
        this.邮件正文.setColumns(20);
        this.邮件正文.setRows(5);
        this.邮件正文.setText("这是来自管理员发放的物品。");
        this.jScrollPane10.setViewportView((Component)this.邮件正文);
        this.jLabel130.setText("附件A代码:");
        this.附件A代码.setText("0");
        this.jLabel131.setText("附件A数量:");
        this.附件A数量.setText("0");
        this.jLabel132.setText("附件B代码:");
        this.附件B代码.setText("0");
        this.jLabel133.setText("附件B数量:");
        this.附件B数量.setText("0");
        this.jLabel134.setText("附件C代码:");
        this.附件C代码.setText("0");
        this.jLabel135.setText("附件C数量:");
        this.附件C数量.setText("0");
        this.jLabel137.setText("<html><body>附件代码规则:<br>0为不发送任何道具<br>1为发送金币<br>2为发送经验<br>3为发送抵用<br>4为发送点券<br>填入物品ID则发送具体的物品<br><font color=\"#FF0000\">Tips:发送数量小于1时，视为无效！</font></body></html>");
        this.jLabel137.setVerticalAlignment(1);
        final GroupLayout jPanel75Layout = new GroupLayout((Container)this.jPanel75);
        this.jPanel75.setLayout((LayoutManager)jPanel75Layout);
        jPanel75Layout.setHorizontalGroup((Group)jPanel75Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel75Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel75Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jScrollPane10, -1, 258, 32767).addGroup((Group)jPanel75Layout.createSequentialGroup().addComponent((Component)this.jLabel128, -2, 83, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.邮件标题, -2, 132, -2)).addGroup(Alignment.TRAILING, (Group)jPanel75Layout.createSequentialGroup().addComponent((Component)this.jLabel130, -2, 80, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.附件A代码, -2, 132, -2)).addGroup((Group)jPanel75Layout.createSequentialGroup().addComponent((Component)this.jLabel131, -2, 80, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.附件A数量, -2, 132, -2)).addGroup(Alignment.TRAILING, (Group)jPanel75Layout.createSequentialGroup().addComponent((Component)this.jLabel132, -2, 80, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.附件B代码, -2, 132, -2)).addGroup((Group)jPanel75Layout.createSequentialGroup().addComponent((Component)this.jLabel133, -2, 80, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.附件B数量, -2, 132, -2)).addGroup((Group)jPanel75Layout.createSequentialGroup().addComponent((Component)this.jLabel134, -2, 80, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.附件C代码, -2, 132, -2)).addGroup((Group)jPanel75Layout.createSequentialGroup().addComponent((Component)this.jLabel135, -2, 80, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.附件C数量, -2, 132, -2)).addGroup((Group)jPanel75Layout.createSequentialGroup().addGroup((Group)jPanel75Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel129, -2, 83, -2).addComponent((Component)this.jLabel137, -2, -1, -2)).addGap(0, 0, 32767))).addContainerGap()));
        jPanel75Layout.setVerticalGroup((Group)jPanel75Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel75Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel75Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel128).addComponent((Component)this.邮件标题, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jLabel129).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jScrollPane10, -2, 142, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel75Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel130).addComponent((Component)this.附件A代码, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel75Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel131).addComponent((Component)this.附件A数量, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel75Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel132).addComponent((Component)this.附件B代码, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel75Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel133).addComponent((Component)this.附件B数量, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel75Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel134).addComponent((Component)this.附件C代码, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel75Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel135).addComponent((Component)this.附件C数量, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED, 28, 32767).addComponent((Component)this.jLabel137, -2, 142, -2).addContainerGap()));
        this.jPanel83.setBorder((Border)BorderFactory.createTitledBorder("发放目标"));
        玩家控制台.邮件角色列表.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "角色ID", "角色名字", "角色等级" }) {
            boolean[] canEdit = { false, false, false };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        玩家控制台.邮件角色列表.setAutoResizeMode(3);
        玩家控制台.邮件角色列表.setName("");
        玩家控制台.邮件角色列表.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane11.setViewportView((Component)玩家控制台.邮件角色列表);
        this.邮件在线玩家.setText("在线玩家");
        this.邮件在线玩家.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.邮件在线玩家ActionPerformed(evt);
            }
        });
        this.邮件离线玩家.setText("离线玩家");
        this.邮件离线玩家.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.邮件离线玩家ActionPerformed(evt);
            }
        });
        this.邮件全部玩家.setText("全部玩家");
        this.邮件全部玩家.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.邮件全部玩家ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel11Layout = new GroupLayout((Container)this.jPanel11);
        this.jPanel11.setLayout((LayoutManager)jPanel11Layout);
        jPanel11Layout.setHorizontalGroup((Group)jPanel11Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel11Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel11Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.邮件在线玩家, -1, 124, 32767).addComponent((Component)this.邮件离线玩家, -1, -1, 32767).addComponent((Component)this.邮件全部玩家, -1, -1, 32767)).addContainerGap(-1, 32767)));
        jPanel11Layout.setVerticalGroup((Group)jPanel11Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel11Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.邮件在线玩家).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.邮件离线玩家).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.邮件全部玩家).addContainerGap(-1, 32767)));
        this.邮件发放选中.setText("发放给选中玩家");
        this.邮件发放选中.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.邮件发放选中ActionPerformed(evt);
            }
        });
        this.邮件发放在线.setText("发放给在线玩家");
        this.邮件发放在线.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.邮件发放在线ActionPerformed(evt);
            }
        });
        this.邮件发放全部.setText("发放给全部玩家");
        this.邮件发放全部.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.邮件发放全部ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel14Layout = new GroupLayout((Container)this.jPanel14);
        this.jPanel14.setLayout((LayoutManager)jPanel14Layout);
        jPanel14Layout.setHorizontalGroup((Group)jPanel14Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel14Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel14Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel14Layout.createSequentialGroup().addGroup((Group)jPanel14Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.邮件发放选中, -1, 124, 32767).addComponent((Component)this.邮件发放在线, -1, -1, 32767)).addGap(0, 0, 32767)).addComponent((Component)this.邮件发放全部, -1, -1, 32767)).addContainerGap()));
        jPanel14Layout.setVerticalGroup((Group)jPanel14Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel14Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.邮件发放选中).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.邮件发放在线).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.邮件发放全部).addContainerGap(-1, 32767)));
        this.清空未领取邮件.setText("清空未领取邮件");
        this.清空未领取邮件.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.清空未领取邮件ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel24Layout = new GroupLayout((Container)this.jPanel24);
        this.jPanel24.setLayout((LayoutManager)jPanel24Layout);
        jPanel24Layout.setHorizontalGroup((Group)jPanel24Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel24Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.清空未领取邮件, -2, 124, -2).addContainerGap(-1, 32767)));
        jPanel24Layout.setVerticalGroup((Group)jPanel24Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel24Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.清空未领取邮件).addContainerGap(-1, 32767)));
        final GroupLayout jPanel83Layout = new GroupLayout((Container)this.jPanel83);
        this.jPanel83.setLayout((LayoutManager)jPanel83Layout);
        jPanel83Layout.setHorizontalGroup((Group)jPanel83Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel83Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jScrollPane11, -2, 296, -2).addPreferredGap(ComponentPlacement.RELATED, 28, 32767).addGroup((Group)jPanel83Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jPanel11, Alignment.TRAILING, -2, -1, -2).addComponent((Component)this.jPanel14, Alignment.TRAILING, -2, -1, -2).addComponent((Component)this.jPanel24, Alignment.TRAILING, -2, -1, -2)).addContainerGap()));
        jPanel83Layout.setVerticalGroup((Group)jPanel83Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel83Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jPanel11, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel14, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel24, -2, -1, -2).addContainerGap(-1, 32767)).addGroup((Group)jPanel83Layout.createSequentialGroup().addComponent((Component)this.jScrollPane11).addContainerGap()));
        this.jPanel11.setBorder((Border)BorderFactory.createTitledBorder("玩家筛选"));
        this.jPanel14.setBorder((Border)BorderFactory.createTitledBorder("发放范围"));
        this.jPanel24.setBorder((Border)BorderFactory.createTitledBorder("邮件清理"));
        final GroupLayout jPanel16Layout = new GroupLayout((Container)this.jPanel16);
        this.jPanel16.setLayout((LayoutManager)jPanel16Layout);
        jPanel16Layout.setHorizontalGroup((Group)jPanel16Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel16Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jPanel75, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel83, -2, -1, -2).addGap(0, 0, 32767)));
        jPanel16Layout.setVerticalGroup((Group)jPanel16Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel16Layout.createSequentialGroup().addGroup((Group)jPanel16Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jPanel75, -1, -1, 32767).addComponent((Component)this.jPanel83, -1, -1, 32767)).addGap(0, 123, 32767)));
        this.jTabbedPane4.addTab("邮件发放", (Component)this.jPanel16);
        this.jPanel31.setBorder((Border)BorderFactory.createTitledBorder("怪物爆物[10000=1%爆率] [爆率低于100不受倍率加成]"));
        this.jPanel31.setPreferredSize(new Dimension(661, 580));
        this.怪物爆物.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "怪物", "物品名字", "物品ID", "爆率" }) {
            boolean[] canEdit = { false, false, false, false, false };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.怪物爆物.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane9.setViewportView((Component)this.怪物爆物);
        if (this.怪物爆物.getColumnModel().getColumnCount() > 0) {
            this.怪物爆物.getColumnModel().getColumn(0).setResizable(false);
            this.怪物爆物.getColumnModel().getColumn(0).setPreferredWidth(20);
            this.怪物爆物.getColumnModel().getColumn(1).setResizable(false);
            this.怪物爆物.getColumnModel().getColumn(1).setPreferredWidth(0);
        }
        this.jLabel136.setText("掉落怪物ID:");
        this.jLabel138.setText("物品名字:");
        this.jLabel139.setText("物品ID:");
        this.jLabel140.setText("爆率:");
        this.jButton89.setForeground(new Color(255, 0, 0));
        this.jButton89.setText("刷新怪物爆物数据");
        this.jButton89.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton89ActionPerformed(evt);
            }
        });
        this.jButton90.setText("修改怪物掉落");
        this.jButton90.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton90ActionPerformed(evt);
            }
        });
        this.jButton91.setText("删除怪物掉落");
        this.jButton91.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton91ActionPerformed(evt);
            }
        });
        this.jButton92.setText("增加怪物掉落");
        this.jButton92.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton92ActionPerformed(evt);
            }
        });
        this.怪物爆物物品名字.setEditable(false);
        this.怪物爆物物品名字.setForeground(new Color(255, 0, 0));
        final GroupLayout jPanel31Layout = new GroupLayout((Container)this.jPanel31);
        this.jPanel31.setLayout((LayoutManager)jPanel31Layout);
        jPanel31Layout.setHorizontalGroup((Group)jPanel31Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel31Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel31Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel31Layout.createSequentialGroup().addGroup((Group)jPanel31Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.怪物爆物怪物ID, -2, 121, -2).addComponent((Component)this.jLabel136)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel31Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel138).addComponent((Component)this.怪物爆物物品名字, -2, 132, -2)).addGap(9, 9, 9).addGroup((Group)jPanel31Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group)jPanel31Layout.createSequentialGroup().addComponent((Component)this.jLabel139).addGap(107, 107, 107)).addGroup((Group)jPanel31Layout.createSequentialGroup().addComponent((Component)this.怪物爆物物品ID).addPreferredGap(ComponentPlacement.RELATED))).addGroup((Group)jPanel31Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.怪物爆物物品爆率).addGroup((Group)jPanel31Layout.createSequentialGroup().addComponent((Component)this.jLabel140, -2, 54, -2).addGap(0, 64, 32767)))).addGroup((Group)jPanel31Layout.createSequentialGroup().addComponent((Component)this.jButton89).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.jButton92, -2, 100, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton91, -2, 70, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton90, -2, 70, -2)).addComponent((Component)this.jScrollPane9)).addContainerGap()));
        jPanel31Layout.linkSize(0, this.jButton90, this.jButton91, this.jButton92);
        jPanel31Layout.setVerticalGroup((Group)jPanel31Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel31Layout.createSequentialGroup().addComponent((Component)this.jScrollPane9, -2, 428, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel31Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group)jPanel31Layout.createSequentialGroup().addComponent((Component)this.jLabel140).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.怪物爆物物品爆率, -2, -1, -2)).addGroup((Group)jPanel31Layout.createSequentialGroup().addGroup((Group)jPanel31Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel136).addComponent((Component)this.jLabel138).addComponent((Component)this.jLabel139)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel31Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.怪物爆物怪物ID, -2, -1, -2).addComponent((Component)this.怪物爆物物品名字, -2, -1, -2).addComponent((Component)this.怪物爆物物品ID, -2, -1, -2)))).addPreferredGap(ComponentPlacement.RELATED, 57, 32767).addGroup((Group)jPanel31Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton90).addComponent((Component)this.jButton91).addComponent((Component)this.jButton92).addComponent((Component)this.jButton89)).addContainerGap()));
        this.jPanel84.setBorder((Border)BorderFactory.createTitledBorder("全局爆物[10000=1%爆率] "));
        this.jButton93.setForeground(new Color(255, 0, 0));
        this.jButton93.setText("刷新全局爆物数据");
        this.jButton93.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton93ActionPerformed(evt);
            }
        });
        this.jButton94.setText("修改全局掉落");
        this.jButton94.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton94ActionPerformed(evt);
            }
        });
        this.jButton95.setText("删除全局掉落");
        this.jButton95.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton95ActionPerformed(evt);
            }
        });
        this.jButton96.setText("增加全局掉落");
        this.jButton96.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton96ActionPerformed(evt);
            }
        });
        this.全局爆物.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "物品名字", "物品ID", "爆率" }) {
            boolean[] canEdit = { false, false, false, false };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.全局爆物.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane7.setViewportView((Component)this.全局爆物);
        if (this.全局爆物.getColumnModel().getColumnCount() > 0) {
            this.全局爆物.getColumnModel().getColumn(0).setResizable(false);
            this.全局爆物.getColumnModel().getColumn(0).setPreferredWidth(0);
        }
        this.全局爆物物品名字.setEditable(false);
        this.全局爆物物品名字.setForeground(new Color(255, 0, 0));
        this.jLabel146.setText("物品名字");
        this.jLabel147.setText("物品ID");
        this.jLabel148.setText("物品爆率:");
        final GroupLayout jPanel84Layout = new GroupLayout((Container)this.jPanel84);
        this.jPanel84.setLayout((LayoutManager)jPanel84Layout);
        jPanel84Layout.setHorizontalGroup((Group)jPanel84Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel84Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel84Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel84Layout.createSequentialGroup().addComponent((Component)this.jButton93).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.jButton96, -2, 100, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton95, -2, 70, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton94, -2, 70, -2)).addComponent((Component)this.jScrollPane7, -1, 541, 32767).addGroup((Group)jPanel84Layout.createSequentialGroup().addGroup((Group)jPanel84Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel84Layout.createSequentialGroup().addComponent((Component)this.jLabel146).addGap(0, 0, 32767)).addComponent((Component)this.全局爆物物品名字)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel84Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel147).addComponent((Component)this.全局爆物物品ID, -2, 181, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel84Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel148, -2, 54, -2).addComponent((Component)this.全局爆物物品爆率, -2, 193, -2)))).addContainerGap()));
        jPanel84Layout.linkSize(0, this.jButton94, this.jButton95, this.jButton96);
        jPanel84Layout.setVerticalGroup((Group)jPanel84Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel84Layout.createSequentialGroup().addComponent((Component)this.jScrollPane7, -2, 428, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel84Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component)this.全局爆物物品爆率, -2, -1, -2).addGroup((Group)jPanel84Layout.createSequentialGroup().addGroup((Group)jPanel84Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel146).addComponent((Component)this.jLabel147).addComponent((Component)this.jLabel148)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel84Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.全局爆物物品名字, -2, -1, -2).addComponent((Component)this.全局爆物物品ID, -2, -1, -2)))).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group)jPanel84Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton93).addComponent((Component)this.jButton94).addComponent((Component)this.jButton95).addComponent((Component)this.jButton96)).addContainerGap()));
        this.jPanel87.setBorder((Border)BorderFactory.createTitledBorder("快捷查询"));
        this.jLabel150.setText("物品ID查询掉落:");
        this.jLabel151.setText("怪物ID查询掉落:");
        this.物品ID查询.setText("查询");
        this.物品ID查询.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.物品ID查询ActionPerformed(evt);
            }
        });
        this.怪物ID查询.setText("查询");
        this.怪物ID查询.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.怪物ID查询ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel87Layout = new GroupLayout((Container)this.jPanel87);
        this.jPanel87.setLayout((LayoutManager)jPanel87Layout);
        jPanel87Layout.setHorizontalGroup((Group)jPanel87Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel87Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel87Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel87Layout.createSequentialGroup().addComponent((Component)this.怪物ID查询掉落, -2, 118, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.怪物ID查询)).addComponent((Component)this.jLabel150).addComponent((Component)this.jLabel151).addGroup((Group)jPanel87Layout.createSequentialGroup().addComponent((Component)this.物品ID查询掉落, -2, 118, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.物品ID查询))).addContainerGap(21, 32767)));
        jPanel87Layout.setVerticalGroup((Group)jPanel87Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel87Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jLabel150).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel87Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.物品ID查询掉落, -2, -1, -2).addComponent((Component)this.物品ID查询)).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jLabel151).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel87Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.怪物ID查询掉落, -2, -1, -2).addComponent((Component)this.怪物ID查询)).addContainerGap(-1, 32767)));
        this.jPanel88.setBorder((Border)BorderFactory.createTitledBorder("快捷清理"));
        this.jLabel152.setText("清空掉落物品:");
        this.jLabel153.setText("清空掉落怪物:");
        this.jButton109.setText("清空");
        this.jButton109.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton109ActionPerformed(evt);
            }
        });
        this.jButton110.setText("清空");
        this.jButton110.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton110ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel88Layout = new GroupLayout((Container)this.jPanel88);
        this.jPanel88.setLayout((LayoutManager)jPanel88Layout);
        jPanel88Layout.setHorizontalGroup((Group)jPanel88Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel88Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel88Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel88Layout.createSequentialGroup().addComponent((Component)this.清空怪物ID, -2, 118, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton110)).addComponent((Component)this.jLabel152).addComponent((Component)this.jLabel153).addGroup((Group)jPanel88Layout.createSequentialGroup().addComponent((Component)this.清空物品ID, -2, 118, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton109))).addContainerGap(21, 32767)));
        jPanel88Layout.setVerticalGroup((Group)jPanel88Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel88Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jLabel152).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel88Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.清空物品ID, -2, -1, -2).addComponent((Component)this.jButton109)).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jLabel153).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel88Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.清空怪物ID, -2, -1, -2).addComponent((Component)this.jButton110)).addContainerGap(-1, 32767)));
        this.jPanel90.setBorder((Border)BorderFactory.createTitledBorder("杂项功能"));
        this.jButton113.setText("刷新怪物卡片");
        this.jButton113.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton113ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel90Layout = new GroupLayout((Container)this.jPanel90);
        this.jPanel90.setLayout((LayoutManager)jPanel90Layout);
        jPanel90Layout.setHorizontalGroup((Group)jPanel90Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel90Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jButton113, -1, -1, 32767).addContainerGap()));
        jPanel90Layout.setVerticalGroup((Group)jPanel90Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel90Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jButton113).addContainerGap(-1, 32767)));
        final GroupLayout jPanel17Layout = new GroupLayout((Container)this.jPanel17);
        this.jPanel17.setLayout((LayoutManager)jPanel17Layout);
        jPanel17Layout.setHorizontalGroup((Group)jPanel17Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel17Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel17Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jPanel87, -1, -1, 32767).addComponent((Component)this.jPanel88, -1, -1, 32767).addComponent((Component)this.jPanel90, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel31, -2, 567, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel84, -2, -1, -2).addGap(44, 44, 44)));
        jPanel17Layout.setVerticalGroup((Group)jPanel17Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel17Layout.createSequentialGroup().addGroup((Group)jPanel17Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group)jPanel17Layout.createSequentialGroup().addComponent((Component)this.jPanel87, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel88, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jPanel90, -2, -1, -2)).addComponent((Component)this.jPanel31, -1, 590, 32767).addComponent((Component)this.jPanel84, -1, -1, 32767)).addContainerGap(103, 32767)));
        this.jTabbedPane4.addTab("爆物管理", (Component)this.jPanel17);
        this.jPanel86.setBorder((Border)BorderFactory.createTitledBorder("商店列表"));
        this.商人列表.setModel((TableModel)new DefaultTableModel(new Object[0][], (Object[])new String[] { "商店ID", "NPCID" }));
        this.jScrollPane14.setViewportView((Component)this.商人列表);
        this.jButton97.setText("读取商品>>");
        this.jButton97.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton97ActionPerformed(evt);
            }
        });
        this.jButton98.setForeground(new Color(255, 0, 0));
        this.jButton98.setText("刷新商店列表");
        this.jButton98.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton98ActionPerformed(evt);
            }
        });
        this.jButton111.setText("增加商店");
        this.jButton111.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton111ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel86Layout = new GroupLayout((Container)this.jPanel86);
        this.jPanel86.setLayout((LayoutManager)jPanel86Layout);
        jPanel86Layout.setHorizontalGroup((Group)jPanel86Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel86Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel86Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group)jPanel86Layout.createSequentialGroup().addComponent((Component)this.jButton111).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton98, -2, 114, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton97, -2, 100, -2)).addComponent((Component)this.jScrollPane14, -2, 302, -2)).addContainerGap(-1, 32767)));
        jPanel86Layout.setVerticalGroup((Group)jPanel86Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel86Layout.createSequentialGroup().addComponent((Component)this.jScrollPane14, -2, 541, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel86Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton97).addComponent((Component)this.jButton98).addComponent((Component)this.jButton111)).addContainerGap(16, 32767)));
        this.jPanel85.setBorder((Border)BorderFactory.createTitledBorder("商店货物列表"));
        this.商品列表.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "序号", "物品名称", "物品ID", "销售价格", "排列顺序" }) {
            boolean[] canEdit = { false, false, false, false, true };
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.商品列表.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane12.setViewportView((Component)this.商品列表);
        if (this.商品列表.getColumnModel().getColumnCount() > 0) {
            this.商品列表.getColumnModel().getColumn(0).setResizable(false);
            this.商品列表.getColumnModel().getColumn(0).setPreferredWidth(0);
        }
        this.jLabel143.setText("物品名称:");
        this.商店物品名称.setEditable(false);
        this.商店物品名称.setForeground(new Color(255, 0, 0));
        this.jLabel144.setText("物品ID:");
        this.jLabel149.setText("物品价格:");
        this.商店物品价格.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.商店物品价格ActionPerformed(evt);
            }
        });
        this.jButton99.setText("修改物品");
        this.jButton99.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton99ActionPerformed(evt);
            }
        });
        this.jButton100.setText("删除物品");
        this.jButton100.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton100ActionPerformed(evt);
            }
        });
        this.jButton101.setText("新增物品");
        this.jButton101.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton101ActionPerformed(evt);
            }
        });
        this.当前商店ID.setText("当前商店ID： 【0】");
        this.jLabel7.setText("排列顺序:");
        this.商品排列顺序.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.商品排列顺序ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel85Layout = new GroupLayout((Container)this.jPanel85);
        this.jPanel85.setLayout((LayoutManager)jPanel85Layout);
        jPanel85Layout.setHorizontalGroup((Group)jPanel85Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel85Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel85Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jScrollPane12, Alignment.TRAILING).addGroup(Alignment.TRAILING, (Group)jPanel85Layout.createSequentialGroup().addGap(0, 45, 32767).addGroup((Group)jPanel85Layout.createParallelGroup(Alignment.LEADING, false).addGroup(Alignment.TRAILING, (Group)jPanel85Layout.createSequentialGroup().addComponent((Component)this.jLabel143).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.商店物品名称, -2, 117, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jLabel144).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.商店物品ID, -2, 98, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jLabel149).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.商店物品价格, -2, 93, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jLabel7).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.商品排列顺序, -2, 81, -2).addGap(12, 12, 12)).addGroup(Alignment.TRAILING, (Group)jPanel85Layout.createSequentialGroup().addComponent((Component)this.当前商店ID, -2, 127, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.jButton101).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton100).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton99))))).addContainerGap()));
        jPanel85Layout.setVerticalGroup((Group)jPanel85Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel85Layout.createSequentialGroup().addComponent((Component)this.jScrollPane12, -2, 485, -2).addGap(18, 18, 18).addGroup((Group)jPanel85Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.商店物品名称, -2, -1, -2).addComponent((Component)this.jLabel143).addComponent((Component)this.商店物品ID, -2, -1, -2).addComponent((Component)this.jLabel144).addComponent((Component)this.商店物品价格, -2, -1, -2).addComponent((Component)this.jLabel149).addComponent((Component)this.jLabel7).addComponent((Component)this.商品排列顺序, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group)jPanel85Layout.createParallelGroup(Alignment.CENTER).addComponent((Component)this.当前商店ID).addComponent((Component)this.jButton101).addComponent((Component)this.jButton100).addComponent((Component)this.jButton99)).addContainerGap()));
        final GroupLayout jPanel18Layout = new GroupLayout((Container)this.jPanel18);
        this.jPanel18.setLayout((LayoutManager)jPanel18Layout);
        jPanel18Layout.setHorizontalGroup((Group)jPanel18Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel18Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jPanel86, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel85, -2, -1, -2).addContainerGap(-1, 32767)));
        jPanel18Layout.setVerticalGroup((Group)jPanel18Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel18Layout.createSequentialGroup().addGroup((Group)jPanel18Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jPanel86, -1, -1, 32767).addComponent((Component)this.jPanel85, -1, -1, 32767)).addContainerGap(83, 32767)));
        this.jTabbedPane4.addTab("商店管理", (Component)this.jPanel18);
        this.临时公告.setColumns(20);
        this.临时公告.setRows(5);
        this.临时公告.setText("大家准备下线更新服务器啦~");
        this.jScrollPane16.setViewportView((Component)this.临时公告);
        this.jButton1.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/GM工具.png")));
        this.jButton1.setText("顶端滚动公告");
        this.jButton1.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton1ActionPerformed(evt);
            }
        });
        this.jButton13.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/GM工具.png")));
        this.jButton13.setText("中心弹窗公告");
        this.jButton13.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton13ActionPerformed(evt);
            }
        });
        this.jButton14.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/GM工具.png")));
        this.jButton14.setText("聊天蓝色公告");
        this.jButton14.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton14ActionPerformed(evt);
            }
        });
        this.jButton15.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/GM工具.png")));
        this.jButton15.setText("中心气泡公告");
        this.jButton15.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton15ActionPerformed(evt);
            }
        });
        this.jButton20.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/GM工具.png")));
        this.jButton20.setText("发放全部公告");
        this.jButton20.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton20ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel108Layout = new GroupLayout((Container)this.jPanel108);
        this.jPanel108.setLayout((LayoutManager)jPanel108Layout);
        jPanel108Layout.setHorizontalGroup((Group)jPanel108Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel108Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel108Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jScrollPane16).addGroup((Group)jPanel108Layout.createSequentialGroup().addComponent((Component)this.jButton1).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton13).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton15).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton14).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jButton20).addContainerGap(656, 32767)))));
        jPanel108Layout.setVerticalGroup((Group)jPanel108Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel108Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jScrollPane16, -2, 130, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group)jPanel108Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jButton1).addComponent((Component)this.jButton13).addComponent((Component)this.jButton14).addComponent((Component)this.jButton15).addComponent((Component)this.jButton20)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel19Layout = new GroupLayout((Container)this.jPanel19);
        this.jPanel19.setLayout((LayoutManager)jPanel19Layout);
        jPanel19Layout.setHorizontalGroup((Group)jPanel19Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel19Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jPanel108, -2, -1, -2).addContainerGap(85, 32767)));
        jPanel19Layout.setVerticalGroup((Group)jPanel19Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel19Layout.createSequentialGroup().addComponent((Component)this.jPanel108, -2, -1, -2).addContainerGap(504, 32767)));
        this.jPanel108.setBorder((Border)BorderFactory.createTitledBorder("全服公告"));
        this.jTabbedPane4.addTab("通知/特色功能", (Component)this.jPanel19);
        final GroupLayout jPanel118Layout = new GroupLayout((Container)this.jPanel118);
        this.jPanel118.setLayout((LayoutManager)jPanel118Layout);
        jPanel118Layout.setHorizontalGroup((Group)jPanel118Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1400, 32767));
        jPanel118Layout.setVerticalGroup((Group)jPanel118Layout.createParallelGroup(Alignment.LEADING).addGap(0, 51, 32767));
        this.首页分支.addTab("推荐商品", (Component)this.jPanel118);
        final GroupLayout jPanel119Layout = new GroupLayout((Container)this.jPanel119);
        this.jPanel119.setLayout((LayoutManager)jPanel119Layout);
        jPanel119Layout.setHorizontalGroup((Group)jPanel119Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1400, 32767));
        jPanel119Layout.setVerticalGroup((Group)jPanel119Layout.createParallelGroup(Alignment.LEADING).addGap(0, 51, 32767));
        this.首页分支.addTab("主题馆", (Component)this.jPanel119);
        final GroupLayout jPanel120Layout = new GroupLayout((Container)this.jPanel120);
        this.jPanel120.setLayout((LayoutManager)jPanel120Layout);
        jPanel120Layout.setHorizontalGroup((Group)jPanel120Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1400, 32767));
        jPanel120Layout.setVerticalGroup((Group)jPanel120Layout.createParallelGroup(Alignment.LEADING).addGap(0, 51, 32767));
        this.首页分支.addTab("活动", (Component)this.jPanel120);
        final GroupLayout jPanel121Layout = new GroupLayout((Container)this.jPanel121);
        this.jPanel121.setLayout((LayoutManager)jPanel121Layout);
        jPanel121Layout.setHorizontalGroup((Group)jPanel121Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1400, 32767));
        jPanel121Layout.setVerticalGroup((Group)jPanel121Layout.createParallelGroup(Alignment.LEADING).addGap(0, 51, 32767));
        this.首页分支.addTab("每日特卖", (Component)this.jPanel121);
        final GroupLayout jPanel111Layout = new GroupLayout((Container)this.jPanel111);
        this.jPanel111.setLayout((LayoutManager)jPanel111Layout);
        jPanel111Layout.setHorizontalGroup((Group)jPanel111Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel111Layout.createSequentialGroup().addComponent((Component)this.首页分支).addContainerGap()));
        jPanel111Layout.setVerticalGroup((Group)jPanel111Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.首页分支));
        this.商城分支.addTab("首页", (Component)this.jPanel111);
        final GroupLayout jPanel2Layout = new GroupLayout((Container)this.jPanel2);
        this.jPanel2.setLayout((LayoutManager)jPanel2Layout);
        jPanel2Layout.setHorizontalGroup((Group)jPanel2Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel2Layout.setVerticalGroup((Group)jPanel2Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("帽子", (Component)this.jPanel2);
        final GroupLayout jPanel124Layout = new GroupLayout((Container)this.jPanel124);
        this.jPanel124.setLayout((LayoutManager)jPanel124Layout);
        jPanel124Layout.setHorizontalGroup((Group)jPanel124Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel124Layout.setVerticalGroup((Group)jPanel124Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("脸饰", (Component)this.jPanel124);
        final GroupLayout jPanel125Layout = new GroupLayout((Container)this.jPanel125);
        this.jPanel125.setLayout((LayoutManager)jPanel125Layout);
        jPanel125Layout.setHorizontalGroup((Group)jPanel125Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel125Layout.setVerticalGroup((Group)jPanel125Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("眼饰", (Component)this.jPanel125);
        final GroupLayout jPanel126Layout = new GroupLayout((Container)this.jPanel126);
        this.jPanel126.setLayout((LayoutManager)jPanel126Layout);
        jPanel126Layout.setHorizontalGroup((Group)jPanel126Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel126Layout.setVerticalGroup((Group)jPanel126Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("长袍", (Component)this.jPanel126);
        final GroupLayout jPanel127Layout = new GroupLayout((Container)this.jPanel127);
        this.jPanel127.setLayout((LayoutManager)jPanel127Layout);
        jPanel127Layout.setHorizontalGroup((Group)jPanel127Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel127Layout.setVerticalGroup((Group)jPanel127Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("上衣", (Component)this.jPanel127);
        final GroupLayout jPanel128Layout = new GroupLayout((Container)this.jPanel128);
        this.jPanel128.setLayout((LayoutManager)jPanel128Layout);
        jPanel128Layout.setHorizontalGroup((Group)jPanel128Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel128Layout.setVerticalGroup((Group)jPanel128Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("裤裙", (Component)this.jPanel128);
        final GroupLayout jPanel129Layout = new GroupLayout((Container)this.jPanel129);
        this.jPanel129.setLayout((LayoutManager)jPanel129Layout);
        jPanel129Layout.setHorizontalGroup((Group)jPanel129Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel129Layout.setVerticalGroup((Group)jPanel129Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("鞋子", (Component)this.jPanel129);
        final GroupLayout jPanel130Layout = new GroupLayout((Container)this.jPanel130);
        this.jPanel130.setLayout((LayoutManager)jPanel130Layout);
        jPanel130Layout.setHorizontalGroup((Group)jPanel130Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel130Layout.setVerticalGroup((Group)jPanel130Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("手套", (Component)this.jPanel130);
        final GroupLayout jPanel131Layout = new GroupLayout((Container)this.jPanel131);
        this.jPanel131.setLayout((LayoutManager)jPanel131Layout);
        jPanel131Layout.setHorizontalGroup((Group)jPanel131Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel131Layout.setVerticalGroup((Group)jPanel131Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("武器", (Component)this.jPanel131);
        final GroupLayout jPanel132Layout = new GroupLayout((Container)this.jPanel132);
        this.jPanel132.setLayout((LayoutManager)jPanel132Layout);
        jPanel132Layout.setHorizontalGroup((Group)jPanel132Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel132Layout.setVerticalGroup((Group)jPanel132Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("戒指", (Component)this.jPanel132);
        final GroupLayout jPanel133Layout = new GroupLayout((Container)this.jPanel133);
        this.jPanel133.setLayout((LayoutManager)jPanel133Layout);
        jPanel133Layout.setHorizontalGroup((Group)jPanel133Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel133Layout.setVerticalGroup((Group)jPanel133Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("镖", (Component)this.jPanel133);
        final GroupLayout jPanel134Layout = new GroupLayout((Container)this.jPanel134);
        this.jPanel134.setLayout((LayoutManager)jPanel134Layout);
        jPanel134Layout.setHorizontalGroup((Group)jPanel134Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel134Layout.setVerticalGroup((Group)jPanel134Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("披风", (Component)this.jPanel134);
        final GroupLayout jPanel135Layout = new GroupLayout((Container)this.jPanel135);
        this.jPanel135.setLayout((LayoutManager)jPanel135Layout);
        jPanel135Layout.setHorizontalGroup((Group)jPanel135Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        jPanel135Layout.setVerticalGroup((Group)jPanel135Layout.createParallelGroup(Alignment.LEADING).addGap(0, 0, 32767));
        this.装备分支.addTab("骑宠", (Component)this.jPanel135);
        final GroupLayout jPanel110Layout = new GroupLayout((Container)this.jPanel110);
        this.jPanel110.setLayout((LayoutManager)jPanel110Layout);
        jPanel110Layout.setHorizontalGroup((Group)jPanel110Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.装备分支));
        jPanel110Layout.setVerticalGroup((Group)jPanel110Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel110Layout.createSequentialGroup().addComponent((Component)this.装备分支, -2, 61, -2).addGap(0, 0, 32767)));
        this.商城分支.addTab("装备", (Component)this.jPanel110);
        final GroupLayout jPanel122Layout = new GroupLayout((Container)this.jPanel122);
        this.jPanel122.setLayout((LayoutManager)jPanel122Layout);
        jPanel122Layout.setHorizontalGroup((Group)jPanel122Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel122Layout.setVerticalGroup((Group)jPanel122Layout.createParallelGroup(Alignment.LEADING).addGap(0, 34, 32767));
        this.消耗分支.addTab("喜庆物品", (Component)this.jPanel122);
        final GroupLayout jPanel136Layout = new GroupLayout((Container)this.jPanel136);
        this.jPanel136.setLayout((LayoutManager)jPanel136Layout);
        jPanel136Layout.setHorizontalGroup((Group)jPanel136Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel136Layout.setVerticalGroup((Group)jPanel136Layout.createParallelGroup(Alignment.LEADING).addGap(0, 34, 32767));
        this.消耗分支.addTab("通讯物品", (Component)this.jPanel136);
        final GroupLayout jPanel137Layout = new GroupLayout((Container)this.jPanel137);
        this.jPanel137.setLayout((LayoutManager)jPanel137Layout);
        jPanel137Layout.setHorizontalGroup((Group)jPanel137Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel137Layout.setVerticalGroup((Group)jPanel137Layout.createParallelGroup(Alignment.LEADING).addGap(0, 34, 32767));
        this.消耗分支.addTab("卷轴", (Component)this.jPanel137);
        final GroupLayout jPanel112Layout = new GroupLayout((Container)this.jPanel112);
        this.jPanel112.setLayout((LayoutManager)jPanel112Layout);
        jPanel112Layout.setHorizontalGroup((Group)jPanel112Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.消耗分支));
        jPanel112Layout.setVerticalGroup((Group)jPanel112Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.消耗分支));
        this.商城分支.addTab("消耗", (Component)this.jPanel112);
        final GroupLayout jPanel147Layout = new GroupLayout((Container)this.jPanel147);
        this.jPanel147.setLayout((LayoutManager)jPanel147Layout);
        jPanel147Layout.setHorizontalGroup((Group)jPanel147Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel147Layout.setVerticalGroup((Group)jPanel147Layout.createParallelGroup(Alignment.LEADING).addGap(0, 25, 32767));
        this.设置分支.addTab("所有设置", (Component)this.jPanel147);
        final GroupLayout jPanel113Layout = new GroupLayout((Container)this.jPanel113);
        this.jPanel113.setLayout((LayoutManager)jPanel113Layout);
        jPanel113Layout.setHorizontalGroup((Group)jPanel113Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.设置分支));
        jPanel113Layout.setVerticalGroup((Group)jPanel113Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel113Layout.createSequentialGroup().addComponent((Component)this.设置分支, -2, 54, -2).addGap(0, 1, 32767)));
        this.商城分支.addTab("设置", (Component)this.jPanel113);
        final GroupLayout jPanel138Layout = new GroupLayout((Container)this.jPanel138);
        this.jPanel138.setLayout((LayoutManager)jPanel138Layout);
        jPanel138Layout.setHorizontalGroup((Group)jPanel138Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel138Layout.setVerticalGroup((Group)jPanel138Layout.createParallelGroup(Alignment.LEADING).addGap(0, 85, 32767));
        this.其他分支.addTab("会员卡", (Component)this.jPanel138);
        final GroupLayout jPanel139Layout = new GroupLayout((Container)this.jPanel139);
        this.jPanel139.setLayout((LayoutManager)jPanel139Layout);
        jPanel139Layout.setHorizontalGroup((Group)jPanel139Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel139Layout.setVerticalGroup((Group)jPanel139Layout.createParallelGroup(Alignment.LEADING).addGap(0, 85, 32767));
        this.其他分支.addTab("表情", (Component)this.jPanel139);
        final GroupLayout jPanel140Layout = new GroupLayout((Container)this.jPanel140);
        this.jPanel140.setLayout((LayoutManager)jPanel140Layout);
        jPanel140Layout.setHorizontalGroup((Group)jPanel140Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel140Layout.setVerticalGroup((Group)jPanel140Layout.createParallelGroup(Alignment.LEADING).addGap(0, 85, 32767));
        this.其他分支.addTab("个人商店", (Component)this.jPanel140);
        final GroupLayout jPanel141Layout = new GroupLayout((Container)this.jPanel141);
        this.jPanel141.setLayout((LayoutManager)jPanel141Layout);
        jPanel141Layout.setHorizontalGroup((Group)jPanel141Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel141Layout.setVerticalGroup((Group)jPanel141Layout.createParallelGroup(Alignment.LEADING).addGap(0, 85, 32767));
        this.其他分支.addTab("纪念日", (Component)this.jPanel141);
        final GroupLayout jPanel142Layout = new GroupLayout((Container)this.jPanel142);
        this.jPanel142.setLayout((LayoutManager)jPanel142Layout);
        jPanel142Layout.setHorizontalGroup((Group)jPanel142Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel142Layout.setVerticalGroup((Group)jPanel142Layout.createParallelGroup(Alignment.LEADING).addGap(0, 85, 32767));
        this.其他分支.addTab("游戏", (Component)this.jPanel142);
        final GroupLayout jPanel143Layout = new GroupLayout((Container)this.jPanel143);
        this.jPanel143.setLayout((LayoutManager)jPanel143Layout);
        jPanel143Layout.setHorizontalGroup((Group)jPanel143Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel143Layout.setVerticalGroup((Group)jPanel143Layout.createParallelGroup(Alignment.LEADING).addGap(0, 85, 32767));
        this.其他分支.addTab("效果", (Component)this.jPanel143);
        final GroupLayout jPanel114Layout = new GroupLayout((Container)this.jPanel114);
        this.jPanel114.setLayout((LayoutManager)jPanel114Layout);
        jPanel114Layout.setHorizontalGroup((Group)jPanel114Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.其他分支));
        jPanel114Layout.setVerticalGroup((Group)jPanel114Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.其他分支));
        this.商城分支.addTab("其他", (Component)this.jPanel114);
        final GroupLayout jPanel144Layout = new GroupLayout((Container)this.jPanel144);
        this.jPanel144.setLayout((LayoutManager)jPanel144Layout);
        jPanel144Layout.setHorizontalGroup((Group)jPanel144Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel144Layout.setVerticalGroup((Group)jPanel144Layout.createParallelGroup(Alignment.LEADING).addGap(0, 34, 32767));
        this.宠物分支.addTab("宠物", (Component)this.jPanel144);
        final GroupLayout jPanel145Layout = new GroupLayout((Container)this.jPanel145);
        this.jPanel145.setLayout((LayoutManager)jPanel145Layout);
        jPanel145Layout.setHorizontalGroup((Group)jPanel145Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel145Layout.setVerticalGroup((Group)jPanel145Layout.createParallelGroup(Alignment.LEADING).addGap(0, 34, 32767));
        this.宠物分支.addTab("宠物服饰", (Component)this.jPanel145);
        final GroupLayout jPanel146Layout = new GroupLayout((Container)this.jPanel146);
        this.jPanel146.setLayout((LayoutManager)jPanel146Layout);
        jPanel146Layout.setHorizontalGroup((Group)jPanel146Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel146Layout.setVerticalGroup((Group)jPanel146Layout.createParallelGroup(Alignment.LEADING).addGap(0, 34, 32767));
        this.宠物分支.addTab("其他", (Component)this.jPanel146);
        final GroupLayout jPanel115Layout = new GroupLayout((Container)this.jPanel115);
        this.jPanel115.setLayout((LayoutManager)jPanel115Layout);
        jPanel115Layout.setHorizontalGroup((Group)jPanel115Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.宠物分支));
        jPanel115Layout.setVerticalGroup((Group)jPanel115Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.宠物分支));
        this.商城分支.addTab("宠物", (Component)this.jPanel115);
        final GroupLayout jPanel116Layout = new GroupLayout((Container)this.jPanel116);
        this.jPanel116.setLayout((LayoutManager)jPanel116Layout);
        jPanel116Layout.setHorizontalGroup((Group)jPanel116Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1415, 32767));
        jPanel116Layout.setVerticalGroup((Group)jPanel116Layout.createParallelGroup(Alignment.LEADING).addGap(0, 55, 32767));
        this.商城分支.addTab("礼包", (Component)this.jPanel116);
        final GroupLayout jPanel148Layout = new GroupLayout((Container)this.jPanel148);
        this.jPanel148.setLayout((LayoutManager)jPanel148Layout);
        jPanel148Layout.setHorizontalGroup((Group)jPanel148Layout.createParallelGroup(Alignment.LEADING).addGap(0, 1410, 32767));
        jPanel148Layout.setVerticalGroup((Group)jPanel148Layout.createParallelGroup(Alignment.LEADING).addGap(0, 26, 32767));
        this.热销分支.addTab("热销列表", (Component)this.jPanel148);
        final GroupLayout jPanel117Layout = new GroupLayout((Container)this.jPanel117);
        this.jPanel117.setLayout((LayoutManager)jPanel117Layout);
        jPanel117Layout.setHorizontalGroup((Group)jPanel117Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.热销分支));
        jPanel117Layout.setVerticalGroup((Group)jPanel117Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.热销分支));
        this.商城分支.addTab("热销", (Component)this.jPanel117);
        this.商城物品表.setModel((TableModel)new DefaultTableModel(new Object[0][], new String[] { "商品编码", "物品代码", "道具名称", "数量", "价格", "限时/天", "出售状态", "上/下架", "性别", "排序（数字越大排序越前）" }) {
            Class[] types = { String.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class };
            boolean[] canEdit = { false, false, false, false, false, false, false, false, false, false };
            
            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.商城物品表.setToolTipText("");
        this.商城物品表.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane60.setViewportView((Component)this.商城物品表);
        if (this.商城物品表.getColumnModel().getColumnCount() > 0) {
            this.商城物品表.getColumnModel().getColumn(5).setPreferredWidth(20);
            this.商城物品表.getColumnModel().getColumn(6).setPreferredWidth(20);
            this.商城物品表.getColumnModel().getColumn(7).setPreferredWidth(20);
            this.商城物品表.getColumnModel().getColumn(8).setPreferredWidth(15);
        }
        this.jPanel28.setBorder((Border)BorderFactory.createTitledBorder("加载商城"));
        this.jLabel26.setText("加载第几个商城：");
        this.商城列表代码.setText("0");
        this.商城列表代码.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.商城列表代码ActionPerformed(evt);
            }
        });
        this.加载商城.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/通用设置.png")));
        this.加载商城.setText("点击读取商城物品");
        this.加载商城.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.加载商城ActionPerformed(evt);
            }
        });
        this.jLabel27.setText("输入0:加载默认商城");
        final GroupLayout jPanel28Layout = new GroupLayout((Container)this.jPanel28);
        this.jPanel28.setLayout((LayoutManager)jPanel28Layout);
        jPanel28Layout.setHorizontalGroup((Group)jPanel28Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel28Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel28Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel27).addGroup((Group)jPanel28Layout.createSequentialGroup().addGroup((Group)jPanel28Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel26).addComponent((Component)this.商城列表代码, -2, 103, -2)).addGap(18, 18, 18).addComponent((Component)this.加载商城, -2, 203, -2))).addGap(0, 13, 32767)));
        jPanel28Layout.setVerticalGroup((Group)jPanel28Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel28Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jLabel26).addGap(0, 0, 0).addGroup((Group)jPanel28Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.商城列表代码, -2, -1, -2).addComponent((Component)this.加载商城, -2, 48, -2)).addGap(0, 0, 0).addComponent((Component)this.jLabel27).addContainerGap(-1, 32767)));
        this.jPanel52.setBorder((Border)BorderFactory.createTitledBorder("商城价格"));
        this.jLabel53.setText("商城扩充价格");
        玩家控制台.商城扩充价格.addFocusListener((FocusListener)new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                玩家控制台.this.商城扩充价格FocusLost(evt);
            }
        });
        玩家控制台.商城扩充价格.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.商城扩充价格ActionPerformed(evt);
            }
        });
        this.jButton25.setText("修改");
        this.jButton25.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton25ActionPerformed(evt);
            }
        });
        this.jButton26.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/召唤生物.png")));
        this.jButton26.setText("重载商城所有数据");
        this.jButton26.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.jButton26ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel52Layout = new GroupLayout((Container)this.jPanel52);
        this.jPanel52.setLayout((LayoutManager)jPanel52Layout);
        jPanel52Layout.setHorizontalGroup((Group)jPanel52Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel52Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel52Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jLabel53).addGroup((Group)jPanel52Layout.createSequentialGroup().addGroup((Group)jPanel52Layout.createParallelGroup(Alignment.TRAILING, false).addComponent((Component)this.jButton25, Alignment.LEADING, -1, 97, 32767).addComponent((Component)玩家控制台.商城扩充价格, Alignment.LEADING)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jButton26, -2, 203, -2))).addContainerGap(-1, 32767)));
        jPanel52Layout.setVerticalGroup((Group)jPanel52Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel52Layout.createSequentialGroup().addGap(10, 10, 10).addComponent((Component)this.jLabel53).addGap(0, 0, 0).addGroup((Group)jPanel52Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)玩家控制台.商城扩充价格, -2, -1, -2).addComponent((Component)this.jButton26, -2, 48, -2)).addGap(0, 0, 0).addComponent((Component)this.jButton25).addContainerGap(-1, 32767)));
        this.jPanel91.setBorder((Border)BorderFactory.createTitledBorder("商品属性"));
        this.jLabel48.setText("商品编码:");
        this.商品编码.setEnabled(false);
        this.jLabel49.setText("商品代码:");
        this.jLabel57.setText("商品名称:");
        this.商品名称.setEnabled(false);
        this.jLabel91.setText("数量:");
        this.jLabel108.setText("价格:");
        this.jLabel110.setText("限时/天:");
        this.jLabel114.setText("出售状态:");
        this.jLabel119.setText("上/下架:");
        this.商品上下架.setModel(new DefaultComboBoxModel(new String[] { "已经下架了↓", "已经上架了↑" }));
        this.jLabel120.setText("性别:");
        this.商品性别.setModel(new DefaultComboBoxModel(new String[] { "男", "女", "不限" }));
        this.jLabel121.setText("排序:");
        this.商品出售状态.setModel(new DefaultComboBoxModel(new String[] { "New", "Sale", "Hot", "Event", "None" }));
        final GroupLayout jPanel91Layout = new GroupLayout((Container)this.jPanel91);
        this.jPanel91.setLayout((LayoutManager)jPanel91Layout);
        jPanel91Layout.setHorizontalGroup((Group)jPanel91Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel91Layout.createSequentialGroup().addContainerGap().addComponent((Component)this.jLabel48).addGap(10, 10, 10).addComponent((Component)this.商品编码, -2, 85, -2).addGap(10, 10, 10).addComponent((Component)this.jLabel49).addGap(10, 10, 10).addComponent((Component)this.商品代码, -2, 75, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jLabel57).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.商品名称, -2, 95, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jLabel91).addGap(10, 10, 10).addComponent((Component)this.商品数量, -2, 52, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jLabel108).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.商品价格, -2, 85, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jLabel110).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.商品限时, -2, 85, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jLabel114).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.商品出售状态, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jLabel119).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.商品上下架, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jLabel120).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.商品性别, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jLabel121).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.商品排序, -2, 85, -2).addContainerGap(-1, 32767)));
        jPanel91Layout.setVerticalGroup((Group)jPanel91Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel91Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel91Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component)this.jLabel48).addComponent((Component)this.商品编码, -2, -1, -2).addComponent((Component)this.jLabel49).addComponent((Component)this.商品代码, -2, -1, -2).addComponent((Component)this.jLabel57).addComponent((Component)this.商品名称, -2, -1, -2).addComponent((Component)this.jLabel91).addComponent((Component)this.商品数量, -2, -1, -2).addComponent((Component)this.jLabel108).addComponent((Component)this.商品价格, -2, -1, -2).addComponent((Component)this.jLabel110).addComponent((Component)this.商品限时, -2, -1, -2).addComponent((Component)this.jLabel114).addComponent((Component)this.jLabel119).addComponent((Component)this.商品上下架, -2, -1, -2).addComponent((Component)this.jLabel120).addComponent((Component)this.商品性别, -2, -1, -2).addComponent((Component)this.jLabel121).addComponent((Component)this.商品排序, -2, -1, -2).addComponent((Component)this.商品出售状态, -2, -1, -2)).addContainerGap(21, 32767)));
        this.jPanel92.setBorder((Border)BorderFactory.createTitledBorder("商城操作"));
        this.添加商品.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/常用功能.png")));
        this.添加商品.setText("添加商品");
        this.添加商品.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.添加商品ActionPerformed(evt);
            }
        });
        this.删除商品.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/常用功能.png")));
        this.删除商品.setText("删除商品");
        this.删除商品.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.删除商品ActionPerformed(evt);
            }
        });
        this.修改商品.setIcon((Icon)new ImageIcon(this.getClass().getResource("/image/常用功能.png")));
        this.修改商品.setText("修改商品");
        this.修改商品.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                玩家控制台.this.修改商品ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel92Layout = new GroupLayout((Container)this.jPanel92);
        this.jPanel92.setLayout((LayoutManager)jPanel92Layout);
        jPanel92Layout.setHorizontalGroup((Group)jPanel92Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel92Layout.createSequentialGroup().addContainerGap(34, 32767).addGroup((Group)jPanel92Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.添加商品, -1, 201, 32767).addComponent((Component)this.删除商品, -1, -1, 32767).addComponent((Component)this.修改商品, -1, -1, 32767)).addContainerGap(33, 32767)));
        jPanel92Layout.setVerticalGroup((Group)jPanel92Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel92Layout.createSequentialGroup().addGap(5, 5, 5).addComponent((Component)this.添加商品).addGap(5, 5, 5).addComponent((Component)this.删除商品).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.修改商品).addContainerGap(-1, 32767)));
        final GroupLayout jPanel29Layout = new GroupLayout((Container)this.jPanel29);
        this.jPanel29.setLayout((LayoutManager)jPanel29Layout);
        jPanel29Layout.setHorizontalGroup((Group)jPanel29Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group)jPanel29Layout.createSequentialGroup().addGroup((Group)jPanel29Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component)this.商城分支, Alignment.LEADING, -2, 0, 32767).addGroup((Group)jPanel29Layout.createSequentialGroup().addContainerGap().addGroup((Group)jPanel29Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component)this.jScrollPane60).addGroup(Alignment.LEADING, (Group)jPanel29Layout.createSequentialGroup().addComponent((Component)this.jPanel28, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel52, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component)this.jPanel92, -2, -1, -2)).addComponent((Component)this.jPanel91, Alignment.LEADING, -1, -1, 32767)))).addContainerGap()));
        jPanel29Layout.setVerticalGroup((Group)jPanel29Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel29Layout.createSequentialGroup().addComponent((Component)this.商城分支, -2, 84, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component)this.jScrollPane60, -2, 297, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component)this.jPanel91, -2, -1, -2).addGap(15, 15, 15).addGroup((Group)jPanel29Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component)this.jPanel92, -1, -1, 32767).addComponent((Component)this.jPanel52, -2, 0, 32767).addComponent((Component)this.jPanel28, -1, -1, 32767)).addContainerGap(80, 32767)));
        this.jTabbedPane4.addTab("商城管理", (Component)this.jPanel29);
        final GroupLayout jPanel3Layout = new GroupLayout((Container)this.jPanel3);
        this.jPanel3.setLayout((LayoutManager)jPanel3Layout);
        jPanel3Layout.setHorizontalGroup((Group)jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup((Group)jPanel3Layout.createSequentialGroup().addComponent((Component)this.jTabbedPane4).addContainerGap()));
        jPanel3Layout.setVerticalGroup((Group)jPanel3Layout.createParallelGroup(Alignment.LEADING).addComponent((Component)this.jTabbedPane4));
        玩家控制台.主界面菜单.addTab("后台工具", (Icon)new ImageIcon(this.getClass().getResource("/image/后台.png")), (Component)this.jPanel3);
        this.getContentPane().add((Component)玩家控制台.主界面菜单, new AbsoluteConstraints(0, 0, 1450, 760));
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    private void 修改商品ActionPerformed(final ActionEvent evt) {
        this.调整商城物品(2);
    }
    
    private void 删除商品ActionPerformed(final ActionEvent evt) {
        this.调整商城物品(1);
    }
    
    private void 添加商品ActionPerformed(final ActionEvent evt) {
        this.调整商城物品(0);
    }
    
    private void jButton26ActionPerformed(final ActionEvent evt) {
        this.重载商城();
    }
    
    private void jButton25ActionPerformed(final ActionEvent evt) {
        if (Start.ConfigValuesMap.get("商城扩充价格") != Integer.valueOf(玩家控制台.商城扩充价格.getText())) {
            this.配置更新("商城扩充价格", Integer.valueOf(玩家控制台.商城扩充价格.getText()).intValue());
            JOptionPane.showMessageDialog(null, "扩背包价格更新成功！", "提示", 1);
        }
    }
    
    private void 商城扩充价格ActionPerformed(final ActionEvent evt) {
    }
    
    private void 商城扩充价格FocusLost(final FocusEvent evt) {
    }
    
    private void 加载商城ActionPerformed(final ActionEvent evt) {
        this.加载商城();
    }
    
    private void 商城列表代码ActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton20ActionPerformed(final ActionEvent evt) {
        this.发起公告(4);
    }
    
    private void jButton15ActionPerformed(final ActionEvent evt) {
        this.发起公告(2);
    }
    
    private void jButton14ActionPerformed(final ActionEvent evt) {
        this.发起公告(3);
    }
    
    private void jButton13ActionPerformed(final ActionEvent evt) {
        this.发起公告(1);
    }
    
    private void jButton1ActionPerformed(final ActionEvent evt) {
        this.发起公告(0);
    }
    
    private void 商品排列顺序ActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton101ActionPerformed(final ActionEvent evt) {
        if (this.商店物品ID.getText().length() > 4 && this.商店物品价格.getText().length() > 0) {
            this.调整商店物品(0);
        }
    }
    
    private void jButton100ActionPerformed(final ActionEvent evt) {
        if (this.商店物品ID.getText().length() > 4 && this.商店物品价格.getText().length() > 0) {
            this.调整商店物品(1);
        }
    }
    
    private void jButton99ActionPerformed(final ActionEvent evt) {
        if (this.商店物品ID.getText().length() > 4 && this.商店物品价格.getText().length() > 0) {
            this.调整商店物品(2);
        }
    }
    
    private void 商店物品价格ActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton111ActionPerformed(final ActionEvent evt) {
        this.商店列表(2);
    }
    
    private void jButton98ActionPerformed(final ActionEvent evt) {
        this.商店列表(0);
    }
    
    private void jButton97ActionPerformed(final ActionEvent evt) {
        this.商店列表(1);
    }
    
    private void jButton113ActionPerformed(final ActionEvent evt) {
        this.刷新怪物卡片();
    }
    
    private void jButton110ActionPerformed(final ActionEvent evt) {
        if (this.清空怪物ID.getText().length() > 4 && this.清空怪物ID.getText().matches("[0-9]*")) {
            this.清空怪物爆物(1);
        }
    }
    
    private void jButton109ActionPerformed(final ActionEvent evt) {
        if (this.清空物品ID.getText().length() > 4 && this.清空物品ID.getText().matches("[0-9]*")) {
            this.清空怪物爆物(0);
        }
    }
    
    private void 怪物ID查询ActionPerformed(final ActionEvent evt) {
        if (this.怪物ID查询掉落.getText().length() > 4 && this.怪物ID查询掉落.getText().matches("[0-9]*")) {
            this.刷新怪物爆物(2);
        }
    }
    
    private void 物品ID查询ActionPerformed(final ActionEvent evt) {
        if (this.物品ID查询掉落.getText().length() > 4 && this.物品ID查询掉落.getText().matches("[0-9]*")) {
            this.刷新怪物爆物(1);
        }
    }
    
    private void jButton96ActionPerformed(final ActionEvent evt) {
        if (this.全局爆物物品ID.getText().length() > 4 && this.全局爆物物品爆率.getText().length() > 0) {
            this.调整全局爆率(0);
        }
    }
    
    private void jButton95ActionPerformed(final ActionEvent evt) {
        if (this.全局爆物物品ID.getText().length() > 4 && this.全局爆物物品爆率.getText().length() > 0) {
            this.调整全局爆率(1);
        }
    }
    
    private void jButton94ActionPerformed(final ActionEvent evt) {
        if (this.全局爆物物品ID.getText().length() > 4 && this.全局爆物物品爆率.getText().length() > 0) {
            this.调整全局爆率(2);
        }
    }
    
    private void jButton93ActionPerformed(final ActionEvent evt) {
        this.刷新全局爆物(0);
    }
    
    private void jButton92ActionPerformed(final ActionEvent evt) {
        if (this.怪物爆物物品ID.getText().length() > 4 && this.怪物爆物物品爆率.getText().length() > 0) {
            this.调整怪物爆率(0);
        }
    }
    
    private void jButton91ActionPerformed(final ActionEvent evt) {
        if (this.怪物爆物物品ID.getText().length() > 4 && this.怪物爆物物品爆率.getText().length() > 0) {
            this.调整怪物爆率(1);
        }
    }
    
    private void jButton90ActionPerformed(final ActionEvent evt) {
        if (this.怪物爆物物品ID.getText().length() > 4 && this.怪物爆物物品爆率.getText().length() > 0) {
            this.调整怪物爆率(2);
        }
    }
    
    private void jButton89ActionPerformed(final ActionEvent evt) {
        this.刷新怪物爆物(0);
    }
    
    private void 清空未领取邮件ActionPerformed(final ActionEvent evt) {
        this.清空邮件();
    }
    
    private void 邮件发放全部ActionPerformed(final ActionEvent evt) {
        this.发送邮件(2);
    }
    
    private void 邮件发放在线ActionPerformed(final ActionEvent evt) {
        this.发送邮件(1);
    }
    
    private void 邮件发放选中ActionPerformed(final ActionEvent evt) {
        this.发送邮件(0);
    }
    
    private void 邮件全部玩家ActionPerformed(final ActionEvent evt) {
        this.筛选邮件玩家(2);
    }
    
    private void 邮件离线玩家ActionPerformed(final ActionEvent evt) {
        this.筛选邮件玩家(1);
    }
    
    private void 邮件在线玩家ActionPerformed(final ActionEvent evt) {
        this.筛选邮件玩家(0);
    }
    
    private void jButton80ActionPerformed(final ActionEvent evt) {
        this.读取家族信息();
    }
    
    private void jButton79ActionPerformed(final ActionEvent evt) {
        this.修改角色技能(1);
    }
    
    private void jButton24ActionPerformed(final ActionEvent evt) {
        this.修改角色技能(0);
    }
    
    private void jButton65ActionPerformed(final ActionEvent evt) {
        this.修改角色背包(1);
    }
    
    private void jButton23ActionPerformed(final ActionEvent evt) {
        this.修改角色背包(0);
    }
    
    private void 角色操作StateChanged(final ChangeEvent evt) {
        if (this.角色操作.getSelectedIndex() == 1 || this.角色操作.getSelectedIndex() == 2) {
            this.刷新角色信息(1);
        }
    }
    
    private void jButton46ActionPerformed(final ActionEvent evt) {
        this.第一格装备调整(0);
    }
    
    private void jButton44ActionPerformed(final ActionEvent evt) {
        this.第一格装备调整(1);
    }
    
    private void 装备限时时间ActionPerformed(final ActionEvent evt) {
    }
    
    private void 装备限时开关ActionPerformed(final ActionEvent evt) {
    }
    
    private void 装备可以交易ActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton43ActionPerformed(final ActionEvent evt) {
        this.发放定制装备();
    }
    
    private void jButton41ActionPerformed(final ActionEvent evt) {
        this.发放物品();
    }
    
    private void jButton78ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(14);
    }
    
    private void jButton77ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(13);
    }
    
    private void jButton76ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(10);
    }
    
    private void jButton75ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(8);
    }
    
    private void jButton74ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(9);
    }
    
    private void jButton73ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(7);
    }
    
    private void jButton72ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(6);
    }
    
    private void jButton71ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(5);
    }
    
    private void jButton68ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(12);
    }
    
    private void jButton34ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(11);
    }
    
    private void jButton33ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(4);
    }
    
    private void jButton32ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(3);
    }
    
    private void jButton31ActionPerformed(final ActionEvent evt) {
        this.全员下线();
    }
    
    private void jButton30ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(1);
    }
    
    private void jButton29ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(2);
    }
    
    private void jButton28ActionPerformed(final ActionEvent evt) {
        this.在线角色处理(0);
    }
    
    private void jButton39ActionPerformed(final ActionEvent evt) {
        this.发放福利(4);
    }
    
    private void jButton38ActionPerformed(final ActionEvent evt) {
        this.发放福利(3);
    }
    
    private void jButton37ActionPerformed(final ActionEvent evt) {
        this.发放福利(2);
    }
    
    private void jButton36ActionPerformed(final ActionEvent evt) {
        this.发放福利(1);
    }
    
    private void jButton35ActionPerformed(final ActionEvent evt) {
        this.发放福利(0);
    }
    
    private void jButton123ActionPerformed(final ActionEvent evt) {
        this.刷新角色信息(2);
    }
    
    private void jButton105ActionPerformed(final ActionEvent evt) {
        this.刷新角色信息(1);
    }
    
    private void jButton122ActionPerformed(final ActionEvent evt) {
        this.刷新角色信息(3);
    }
    
    private void jButton104ActionPerformed(final ActionEvent evt) {
        this.刷新角色信息(0);
    }
    
    private void jButton121ActionPerformed(final ActionEvent evt) {
        this.修改角色属性(4);
    }
    
    private void jButton103ActionPerformed(final ActionEvent evt) {
        this.修改角色属性(3);
    }
    
    private void jButton69ActionPerformed(final ActionEvent evt) {
        this.读取角色技能();
    }
    
    private void jButton66ActionPerformed(final ActionEvent evt) {
        this.读取角色背包();
    }
    
    private void jButton70ActionPerformed(final ActionEvent evt) {
        this.键盘技能处理(0);
    }
    
    private void jButton67ActionPerformed(final ActionEvent evt) {
        this.键盘技能处理(1);
    }
    
    private void jButton63ActionPerformed(final ActionEvent evt) {
        this.卡角色清理(1);
    }
    
    private void jButton62ActionPerformed(final ActionEvent evt) {
        this.卡角色清理(3);
    }
    
    private void jButton59ActionPerformed(final ActionEvent evt) {
        this.卡角色清理(2);
    }
    
    private void jButton58ActionPerformed(final ActionEvent evt) {
        this.卡角色清理(0);
    }
    
    private void jButton64ActionPerformed(final ActionEvent evt) {
        this.删除角色();
    }
    
    private void jButton57ActionPerformed(final ActionEvent evt) {
        this.刷新角色信息(4);
    }
    
    private void 角色昵称ActionPerformed(final ActionEvent evt) {
    }
    
    private void 职业IDActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton60ActionPerformed(final ActionEvent evt) {
        this.修改角色属性(2);
    }
    
    private void jButton22ActionPerformed(final ActionEvent evt) {
        this.修改角色属性(1);
    }
    
    private void MAXHPActionPerformed(final ActionEvent evt) {
    }
    
    private void 智力ActionPerformed(final ActionEvent evt) {
    }
    
    private void jButton21ActionPerformed(final ActionEvent evt) {
        this.修改角色属性(0);
    }
    
    private void jButton120ActionPerformed(final ActionEvent evt) {
        this.变身GM(0);
    }
    
    private void jButton102ActionPerformed(final ActionEvent evt) {
        this.变身GM(1);
    }
    
    private void jButton12ActionPerformed(final ActionEvent evt) {
        this.刷新账号信息(4);
    }
    
    private void jButton11ActionPerformed(final ActionEvent evt) {
        this.修改密码();
    }
    
    private void jButton10ActionPerformed(final ActionEvent evt) {
        this.修改账号信息();
    }
    
    private void jButton9ActionPerformed(final ActionEvent evt) {
        this.刷新账号信息(3);
    }
    
    private void jButton7ActionPerformed(final ActionEvent evt) {
        this.刷新账号信息(2);
    }
    
    private void jButton6ActionPerformed(final ActionEvent evt) {
        this.刷新账号信息(1);
    }
    
    private void jButton5ActionPerformed(final ActionEvent evt) {
        this.刷新账号信息(0);
    }
    
    private void jButton8ActionPerformed(final ActionEvent evt) {
        this.删除账号();
    }
    
    private void jButton4ActionPerformed(final ActionEvent evt) {
        this.踢掉账号();
    }
    
    private void jButton3ActionPerformed(final ActionEvent evt) {
        this.封解账号(0);
    }
    
    private void jButton2ActionPerformed(final ActionEvent evt) {
        this.封解账号(1);
    }
    
    private void jButton16ActionPerformed(final ActionEvent evt) {
        this.注册账号();
    }
    
    private void jButton124ActionPerformed(final ActionEvent evt) {
        this.修改角色属性(5);
    }
    
    private void 装备砸卷上限ActionPerformed(final ActionEvent evt) {
    }
    
    public void 加载商城() {
        if (this.商城列表代码.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "请输入你要加载的商城");
            return;
        }
        for (int i = ((DefaultTableModel)this.商城物品表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)this.商城物品表.getModel()).removeRow(i);
        }
        final int 大类型 = (this.商城分支.getSelectedIndex() + 1) * 100;
        int 小类型 = 0;
        switch (大类型) {
            case 100: {
                小类型 = this.首页分支.getSelectedIndex();
                break;
            }
            case 200: {
                小类型 = this.装备分支.getSelectedIndex();
                break;
            }
            case 300: {
                小类型 = this.消耗分支.getSelectedIndex();
                break;
            }
            case 400: {
                小类型 = this.设置分支.getSelectedIndex();
                break;
            }
            case 500: {
                小类型 = this.其他分支.getSelectedIndex();
                break;
            }
            case 600: {
                小类型 = this.宠物分支.getSelectedIndex();
                break;
            }
            case 700: {
                小类型 = 0;
                break;
            }
            case 800: {
                小类型 = this.热销分支.getSelectedIndex();
                break;
            }
            default: {
                小类型 = 0;
                break;
            }
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM cashshop_modified_items order by serial desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("type") == Integer.valueOf(this.商城列表代码.getText()).intValue()) {
                    String 出售状态2 = "";
                    switch (rs.getInt("mark")) {
                        case 0: {
                            出售状态2 = "New";
                            break;
                        }
                        case 1: {
                            出售状态2 = "Sale";
                            break;
                        }
                        case 2: {
                            出售状态2 = "HOT";
                            break;
                        }
                        case 3: {
                            出售状态2 = "Event";
                            break;
                        }
                        default: {
                            出售状态2 = "None";
                            break;
                        }
                    }
                    String 出售状态3 = "";
                    switch (rs.getInt("gender")) {
                        case 0: {
                            出售状态3 = "男";
                            break;
                        }
                        case 1: {
                            出售状态3 = "女";
                            break;
                        }
                        case 2: {
                            出售状态3 = "不限";
                            break;
                        }
                    }
                    final int 类别 = (int)Math.floor((double)(rs.getInt("serial") / 100000));
                    if (类别 != 大类型 + 小类型) {
                        continue;
                    }
                    ((DefaultTableModel)this.商城物品表.getModel()).insertRow(this.商城物品表.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("serial")), Integer.valueOf(rs.getInt("itemid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("count")), Integer.valueOf(rs.getInt("discount_price")), Integer.valueOf(rs.getInt("period")), 出售状态2, (rs.getInt("showUp") > 0) ? "已经上架↑" : "已经下架↓", 出售状态3, Integer.valueOf(rs.getInt("priority")) });
                }
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
    }
    
    public void 调整商城物品(final int type) {
        if (this.商品代码.getText().length() != 7) {
            JOptionPane.showMessageDialog(null, "错误:商品代码是7位数的哦");
            return;
        }
        if (this.商品数量.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:商品数量不能为空");
            return;
        }
        if (this.商品价格.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:商品价格不能为空");
            return;
        }
        if (this.商品限时.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:商品限时不能为空");
            return;
        }
        if (this.商品排序.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:商品排序不能为空");
            return;
        }
        if (this.商城列表代码.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:商城列表不能为空");
            return;
        }
        final int 大类型 = (this.商城分支.getSelectedIndex() + 1) * 100;
        int 小类型 = 0;
        switch (大类型) {
            case 100: {
                小类型 = this.首页分支.getSelectedIndex();
                break;
            }
            case 200: {
                小类型 = this.装备分支.getSelectedIndex();
                break;
            }
            case 300: {
                小类型 = this.消耗分支.getSelectedIndex();
                break;
            }
            case 400: {
                小类型 = this.设置分支.getSelectedIndex();
                break;
            }
            case 500: {
                小类型 = this.其他分支.getSelectedIndex();
                break;
            }
            case 600: {
                小类型 = this.宠物分支.getSelectedIndex();
                break;
            }
            case 700: {
                小类型 = 0;
                break;
            }
            case 800: {
                小类型 = this.热销分支.getSelectedIndex();
                break;
            }
            default: {
                小类型 = 0;
                break;
            }
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO cashshop_modified_items (serial,discount_price,mark,showup,itemid,priority,package,period,gender,count,meso,unk_1,unk_2,unk_3,extra_flags,type) VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    int 随机数;
                    for (随机数 = (大类型 + 小类型) * 100000 + (int)Math.floor(Math.random() * 100000.0); CashItemFactory.getInstance().getModInfoSN(随机数); 随机数 = (大类型 + 小类型) * 100000 + (int)Math.floor(Math.random() * 100000.0)) {}
                    ps.setInt(1, 随机数);
                    ps.setInt(2, Integer.valueOf(this.商品价格.getText()).intValue());
                    ps.setInt(3, Integer.valueOf(this.商品出售状态.getSelectedIndex()).intValue());
                    ps.setInt(4, Integer.valueOf(this.商品上下架.getSelectedIndex()).intValue());
                    ps.setInt(5, Integer.valueOf(this.商品代码.getText()).intValue());
                    ps.setInt(6, Integer.valueOf(this.商品排序.getText()).intValue());
                    ps.setInt(7, 0);
                    ps.setInt(8, Integer.valueOf(this.商品限时.getText()).intValue());
                    ps.setInt(9, Integer.valueOf(this.商品性别.getSelectedIndex()).intValue());
                    ps.setInt(10, Integer.valueOf(this.商品数量.getText()).intValue());
                    ps.setInt(11, 0);
                    ps.setInt(12, 0);
                    ps.setInt(13, 0);
                    ps.setInt(14, 0);
                    ps.setInt(15, 0);
                    ps.setInt(16, Integer.valueOf(this.商城列表代码.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM cashshop_modified_items where serial = ?");
                    ps.setInt(1, Integer.valueOf(this.商品编码.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE cashshop_modified_items set discount_price = ?,mark = ? ,showup = ? ,itemid = ?,priority = ?,package = ?,period = ?,gender = ?,count = ?,meso = ?,unk_1 = ?,unk_2 = ?,unk_3 = ?,extra_flags = ?,type = ? where serial = ?");
                    ps.setInt(1, Integer.valueOf(this.商品价格.getText()).intValue());
                    ps.setInt(2, Integer.valueOf(this.商品出售状态.getSelectedIndex()).intValue());
                    ps.setInt(3, Integer.valueOf(this.商品上下架.getSelectedIndex()).intValue());
                    ps.setInt(4, Integer.valueOf(this.商品代码.getText()).intValue());
                    ps.setInt(5, Integer.valueOf(this.商品排序.getText()).intValue());
                    ps.setInt(6, 0);
                    ps.setInt(7, Integer.valueOf(this.商品限时.getText()).intValue());
                    ps.setInt(8, Integer.valueOf(this.商品性别.getSelectedIndex()).intValue());
                    ps.setInt(9, Integer.valueOf(this.商品数量.getText()).intValue());
                    ps.setInt(10, 0);
                    ps.setInt(11, 0);
                    ps.setInt(12, 0);
                    ps.setInt(13, 0);
                    ps.setInt(14, 0);
                    ps.setInt(15, Integer.valueOf(this.商城列表代码.getText()).intValue());
                    ps.setInt(16, Integer.valueOf(this.商品编码.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.加载商城();
            this.重载商城();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "爆率提示", 1);
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]刷新出错：" + ex.getMessage());
        }
    }
    
    private void 刷新账号信息(final int type) {
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.账号信息.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.账号信息.getModel()).removeRow(i);
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("SELECT * FROM accounts order by id desc");
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("SELECT * FROM accounts where loggedin > 0 order by id desc ");
                    break;
                }
                case 2: {
                    ps = con.prepareStatement("SELECT * FROM accounts where loggedin = 0 order by id desc ");
                    break;
                }
                case 3: {
                    ps = con.prepareStatement("SELECT * FROM accounts where banned = 1 order by id desc ");
                    break;
                }
                case 4: {
                    ps = con.prepareStatement("SELECT * FROM accounts where name = ? order by id desc ");
                    ps.setString(1, this.账号.getText());
                    break;
                }
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel)this.账号信息.getModel()).insertRow(this.账号信息.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("id")), rs.getString("name"), (rs.getInt("loggedin") == 0) ? "不在线" : "在线", Integer.valueOf(rs.getInt("mPoints")), Integer.valueOf(rs.getInt("ACash")), rs.getTimestamp("lastlogin"), rs.getString("qq"), (rs.getInt("banned") == 0) ? "正常" : "封禁", rs.getString("SessionIP"), rs.getString("macs"), (rs.getInt("gm") == 0) ? "玩家" : "管理员" });
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
    }
    
    public void 删除账号() {
        if (this.账号.getText().length() < 4) {
            JOptionPane.showMessageDialog(null, "请选择要删除的账号！", "提示", 0);
        }
        else {
            PreparedStatement ps = null;
            try {
                final int n = JOptionPane.showConfirmDialog((Component)this, "你确定要删除这个账号吗？", "信息", 0);
                if (n == 0) {
                    ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("Delete FROM accounts where name = ?");
                    ps.setString(1, this.账号.getText());
                    ps.execute();
                    ps.close();
                    JOptionPane.showMessageDialog(null, "删除账号成功！", "提示", 1);
                    this.刷新账号信息(0);
                }
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
            }
        }
    }
    
    public void 封解账号(final int type) {
        if (this.账号.getText().length() < 4) {
            JOptionPane.showMessageDialog(null, "请选择要处理的账号！", "提示", 0);
        }
        else {
            try {
                final Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
                PreparedStatement ps = null;
                ps = con.prepareStatement("Update accounts set banned = ? Where name = ?");
                ps.setInt(1, type);
                ps.setString(2, this.账号.getText());
                ps.execute();
                ps.close();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
            }
            JOptionPane.showMessageDialog(null, "处理账号成功！", "提示", 1);
            this.刷新账号信息(0);
        }
    }
    
    public void 变身GM(final int type) {
        if (this.账号.getText().length() < 4) {
            JOptionPane.showMessageDialog(null, "请选择要调整的账号！", "提示", 0);
        }
        else {
            try {
                final Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
                PreparedStatement ps = null;
                ps = con.prepareStatement("Update accounts set gm = ? Where name = ?");
                ps.setInt(1, (type == 0) ? 0 : 100);
                ps.setString(2, this.账号.getText());
                ps.execute();
                ps.close();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
            }
            JOptionPane.showMessageDialog(null, "调整成功！", "提示", 1);
            this.刷新账号信息(0);
        }
    }
    
    public void 修改密码() {
        if (this.账号.getText().length() < 4 || this.密码.getText().length() < 4) {
            JOptionPane.showMessageDialog(null, "请选择要修改的账号或者密码不能为空！", "提示", 0);
            return;
        }
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
            final PreparedStatement ps = con.prepareStatement("Update accounts set password = ? Where name = ?");
            ps.setString(1, LoginCrypto.hexSha1(this.密码.getText()));
            ps.setString(2, this.账号.getText());
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "修改成功！", "提示", 1);
        }
        catch (Exception ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]密码修改错误!\r\n" + ex);
        }
    }
    
    public void 注册账号() {
        if (this.账号.getText().length() < 4 || this.密码.getText().length() < 4) {
            JOptionPane.showMessageDialog(null, "请选择要修改的账号或者密码不能为空！", "提示", 0);
            return;
        }
        if (this.账号.getText().length() > 10) {
            JOptionPane.showMessageDialog(null, "账号过长！", "提示", 0);
            return;
        }
        if (AutoRegister.getAccountExists(this.账号.getText())) {
            JOptionPane.showMessageDialog(null, "你输入的这个账号已经被注册过了", "提示", 0);
            return;
        }
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
            final PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, password) VALUES (?,?)");
            ps.setString(1, this.账号.getText());
            ps.setString(2, LoginCrypto.hexSha1(this.密码.getText()));
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "注册成功！", "提示", 1);
        }
        catch (Exception ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]账号注册失败!\r\n" + ex);
        }
    }
    
    public void 修改账号信息() {
        final boolean result1 = this.抵用.getText().matches("[0-9]+");
        final boolean result2 = this.点券.getText().matches("[0-9]+");
        final boolean result3 = this.QQ.getText().matches("[0-9]+");
        if (result1 && result2 && result3) {
            try {
                final PreparedStatement ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE accounts SET mPoints = ?, ACash = ?, df_tired_point = ?, qq = ? WHERE name = ?");
                ps.setInt(1, Integer.parseInt(this.抵用.getText()));
                ps.setInt(2, Integer.parseInt(this.点券.getText()));
                ps.setInt(4, Integer.parseInt(this.QQ.getText()));
                ps.setString(5, this.账号.getText());
                ps.execute();
                this.刷新账号信息(0);
                ps.close();
                JOptionPane.showMessageDialog(null, "修改成功！", "提示", 1);
            }
            catch (SQLException ex) {
                System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "请选择要修改的账号,数据不能为空，或者数值填写不对!", "提示", 0);
        }
    }
    
    public void 踢掉账号() {
        if (this.账号.getText().length() < 4) {
            JOptionPane.showMessageDialog(null, "请选择要处理的账号！", "提示", 0);
        }
        else {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    if (chr == null) {
                        continue;
                    }
                    if (chr.getAccountID() != this.账号ID) {
                        continue;
                    }
                    chr.getClient().disconnect(true, false);
                    chr.getClient().getSession().close();
                }
            }
        }
    }
    
    public void 刷新角色信息(final int type) {
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.角色信息.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.角色信息.getModel()).removeRow(i);
        }
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters order by id desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                if (type == 1 && Find.findChannel(rs.getString("name")) <= 0) {
                    continue;
                }
                if (type == 2 && Find.findChannel(rs.getString("name")) >= 0) {
                    continue;
                }
                if (type == 3 && rs.getInt("gm") <= 0) {
                    continue;
                }
                if (type == 4 && !rs.getString("name").contains((CharSequence)this.角色昵称.getText())) {
                    continue;
                }
                if (type == 5 && rs.getInt("accountid") != this.账号ID) {
                    continue;
                }
                ((DefaultTableModel)this.角色信息.getModel()).insertRow(this.角色信息.getRowCount(), new Object[] { (Find.findChannel(rs.getString("name")) > 0) ? "在线" : "离线", Integer.valueOf(rs.getInt("accountid")), Integer.valueOf(rs.getInt("id")), rs.getString("name"), Integer.valueOf(rs.getInt("level")), MapleCarnivalChallenge.getJobNameById(rs.getInt("job")), Integer.valueOf(rs.getInt("map")), Integer.valueOf(rs.getInt("meso")), Integer.valueOf(rs.getInt("beans")), Integer.valueOf(rs.getInt("str")), Integer.valueOf(rs.getInt("dex")), Integer.valueOf(rs.getInt("int")), Integer.valueOf(rs.getInt("luk")), Integer.valueOf(rs.getInt("maxhp")), Integer.valueOf(rs.getInt("maxmp")), Integer.valueOf(rs.getInt("hair")), Integer.valueOf(rs.getInt("face")), (rs.getInt("gm") == 0) ? "玩家" : ((rs.getInt("gm") == 1) ? "巡查者" : "管理员"), Integer.valueOf(rs.getInt("ap")), rs.getString("sp").substring(0, rs.getString("sp").indexOf(",")) });
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
    }
    
    public void 删除角色() {
        Connection con = null;
        PreparedStatement ps = null;
        if (this.角色昵称.getText().length() <= 1) {
            JOptionPane.showMessageDialog(null, "请选择要处理的角色！", "提示", 0);
        }
        else {
            final int n = JOptionPane.showConfirmDialog((Component)this, "你确定要删除这个角色吗？", "信息", 0);
            if (n == 0) {
                try {
                    con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
                    ps = con.prepareStatement(" delete from characters where id = ?");
                    ps.setInt(1, this.角色ID);
                    ps.execute();
                    ps = con.prepareStatement(" delete from inventoryitems where characterid = ?");
                    ps.setInt(1, this.角色ID);
                    ps.execute();
                    ps = con.prepareStatement(" delete from skills where characterid = ?");
                    ps.setInt(1, this.角色ID);
                    ps.execute();
                    ps.close();
                    JOptionPane.showMessageDialog(null, "删除角色及其物品、技能数据成功！", "提示", 1);
                    this.刷新角色信息(0);
                }
                catch (SQLException ex) {
                    System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
                }
            }
        }
    }
    
    public void 修改角色属性(final int type) {
        PreparedStatement ps = null;
        if (this.角色昵称.getText().length() <= 1) {
            JOptionPane.showMessageDialog(null, "角色昵称不能留空");
            return;
        }
        if (Find.findChannel(this.角色昵称.getText()) > 0) {
            JOptionPane.showMessageDialog(null, "请先将角色离线后再修改。");
            return;
        }
        final int n = JOptionPane.showConfirmDialog((Component)this, "你确定要修改这个角色吗？", "信息", 0);
        if (n != 0) {
            return;
        }
        try {
            switch (type) {
                case 0: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE characters SET name = ?,level = ?, str = ?, dex = ?, `int` = ?,luk = ?,  maxhp = ?, maxmp = ?, hair = ?, face = ?, ap = ?, sp = ? where id = ?");
                    ps.setString(1, this.角色昵称.getText());
                    ps.setInt(2, Integer.parseInt(this.等级.getText()));
                    ps.setInt(3, Integer.parseInt(this.力量.getText()));
                    ps.setInt(4, Integer.parseInt(this.敏捷.getText()));
                    ps.setInt(5, Integer.parseInt(this.智力.getText()));
                    ps.setInt(6, Integer.parseInt(this.运气.getText()));
                    ps.setInt(7, Integer.parseInt(this.MAXHP.getText()));
                    ps.setInt(8, Integer.parseInt(this.MAXMP.getText()));
                    ps.setInt(9, Integer.parseInt(this.发型.getText()));
                    ps.setInt(10, Integer.parseInt(this.脸型.getText()));
                    ps.setInt(11, Integer.parseInt(this.能力点.getText()));
                    final String 技能点数 = this.技能点.getText() + ",0,0,0,0,0,0,0,0,0";
                    ps.setString(12, 技能点数);
                    ps.setInt(13, this.角色ID);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE characters SET meso = ?,beans = ? where id = ?");
                    ps.setInt(1, Integer.parseInt(this.金币.getText()));
                    ps.setInt(2, Integer.parseInt(this.豆豆.getText()));
                    ps.setInt(3, this.角色ID);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 2: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE characters SET map = ?,job = ? where id = ?");
                    ps.setInt(1, Integer.parseInt(this.所在地图.getText()));
                    ps.setInt(2, Integer.parseInt(this.职业ID.getText()));
                    ps.setInt(3, this.角色ID);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 3: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE characters SET gm = ? where id = ?");
                    ps.setInt(1, 100);
                    ps.setInt(2, this.角色ID);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 4: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE characters SET gm = ? where id = ?");
                    ps.setInt(1, 0);
                    ps.setInt(2, this.角色ID);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 5: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE characters SET gm = ? where id = ?");
                    ps.setInt(1, 1);
                    ps.setInt(2, this.角色ID);
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "修改成功！", "提示", 1);
            this.刷新角色信息(0);
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
    }
    
    public void 卡角色清理(final int type) {
        PreparedStatement ps = null;
        if (this.角色昵称.getText().length() <= 1) {
            JOptionPane.showMessageDialog(null, "请选择你要解救的角色！");
            return;
        }
        final int n = JOptionPane.showConfirmDialog((Component)this, "你确定要解救这个角色吗？\n对应的解救方式是初始化数据，或者强制掉线！\n请谨慎操作！", "信息", 0);
        if (n != 0) {
            return;
        }
        try {
            switch (type) {
                case 0: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("delete from inventoryitems where characterid = ?");
                    ps.setInt(1, this.角色ID);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE characters SET hair = ?,face = ? where id = ?");
                    ps.setInt(1, 30000);
                    ps.setInt(2, 20000);
                    ps.setInt(3, this.角色ID);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 2: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE characters SET guildid = ?,guildrank = ?,allianceRank = ? where id = ?");
                    ps.setInt(1, 0);
                    ps.setInt(2, 5);
                    ps.setInt(3, 5);
                    ps.setInt(4, this.角色ID);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 3: {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                            if (chr == null) {
                                continue;
                            }
                            if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                                continue;
                            }
                            chr.getClient().disconnect(true, false);
                            chr.getClient().getSession().close();
                        }
                    }
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "处理成功！对应卡死已解救完毕！", "提示", 1);
            this.刷新角色信息(0);
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
    }
    
    public void 键盘技能处理(final int type) {
        PreparedStatement ps = null;
        try {
            switch (type) {
                case 0: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement(" delete from keymap_backup where characterid = ?");
                    ps.setInt(1, this.角色ID);
                    ps.execute();
                    ps.close();
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement(" insert into keymap_backup select * from keymap where characterid = ?");
                    ps.setInt(1, this.角色ID);
                    ps.execute();
                    ps.close();
                    JOptionPane.showMessageDialog(null, "保存成功！可以确认数据库数据了！", "提示", 1);
                    break;
                }
                case 1: {
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement(" delete from keymap where characterid = ?");
                    ps.setInt(1, this.角色ID);
                    ps.execute();
                    ps.close();
                    ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement(" insert into keymap select * from keymap_backup where characterid = ?");
                    ps.setInt(1, this.角色ID);
                    ps.execute();
                    ps.close();
                    JOptionPane.showMessageDialog(null, "还原成功！可以确认键盘数据了！", "提示", 1);
                    break;
                }
            }
            this.刷新角色信息(0);
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
    }
    
    public void 读取角色背包() {
        if (this.角色昵称.getText().length() <= 1) {
            JOptionPane.showMessageDialog(null, "请选择你要读取的角色！");
            return;
        }
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.身上.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.身上.getModel()).removeRow(i);
        }
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.装备.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.装备.getModel()).removeRow(i);
        }
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.消耗.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.消耗.getModel()).removeRow(i);
        }
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.设置.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.设置.getModel()).removeRow(i);
        }
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.其他.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.其他.getModel()).removeRow(i);
        }
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.特殊.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.特殊.getModel()).removeRow(i);
        }
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.仓库.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.仓库.getModel()).removeRow(i);
        }
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.时仓.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.时仓.getModel()).removeRow(i);
        }
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid = ?");
            ps.setInt(1, this.角色ID);
            rs = ps.executeQuery();
            while (rs.next()) {
                switch (rs.getInt("inventorytype")) {
                    case -1: {
                        ((DefaultTableModel)this.身上.getModel()).insertRow(this.身上.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("inventoryitemid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("quantity")) });
                        continue;
                    }
                    case 1: {
                        ((DefaultTableModel)this.装备.getModel()).insertRow(this.装备.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("inventoryitemid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("quantity")) });
                        continue;
                    }
                    case 2: {
                        ((DefaultTableModel)this.消耗.getModel()).insertRow(this.消耗.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("inventoryitemid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("quantity")) });
                        continue;
                    }
                    case 3: {
                        ((DefaultTableModel)this.设置.getModel()).insertRow(this.设置.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("inventoryitemid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("quantity")) });
                        continue;
                    }
                    case 4: {
                        ((DefaultTableModel)this.其他.getModel()).insertRow(this.其他.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("inventoryitemid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("quantity")) });
                        continue;
                    }
                    case 5: {
                        ((DefaultTableModel)this.特殊.getModel()).insertRow(this.特殊.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("inventoryitemid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("quantity")) });
                        continue;
                    }
                }
            }
            int accid = -1;
            ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
            ps.setInt(1, this.角色ID);
            rs = ps.executeQuery();
            while (rs.next()) {
                accid = rs.getInt("accountid");
            }
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE accountid = ?");
            ps.setInt(1, accid);
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel)this.仓库.getModel()).insertRow(this.仓库.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("inventoryitemid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("quantity")) });
            }
            ps = con.prepareStatement("SELECT * FROM csitems WHERE accountid = ?");
            ps.setInt(1, accid);
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel)this.时仓.getModel()).insertRow(this.时仓.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("inventoryitemid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("quantity")) });
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
        JOptionPane.showMessageDialog(null, "读取速度因角色数据量而异！\n读取完毕！可前往查看！", "提示", 1);
    }
    
    public void 修改角色背包(final int type) {
        Connection con = null;
        PreparedStatement ps = null;
        int i = -1;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            switch (type) {
            }
            switch (this.资源页签.getSelectedIndex()) {
                case 0: {
                    i = this.身上.getSelectedRow();
                    if (i < 0) {
                        JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
                        return;
                    }
                    if (type == 0) {
                        ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.身上.getValueAt(i, 2).toString()));
                        ps.setInt(2, Integer.parseInt(this.身上.getValueAt(i, 3).toString()));
                        ps.setInt(3, Integer.parseInt(this.身上.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                    }
                    if (type == 1) {
                        ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.身上.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                        ((DefaultTableModel)(DefaultTableModel)this.身上.getModel()).removeRow(i);
                        break;
                    }
                    break;
                }
                case 1: {
                    i = this.装备.getSelectedRow();
                    if (i < 0) {
                        JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
                        return;
                    }
                    if (type == 0) {
                        ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.装备.getValueAt(i, 2).toString()));
                        ps.setInt(2, Integer.parseInt(this.装备.getValueAt(i, 3).toString()));
                        ps.setInt(3, Integer.parseInt(this.装备.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                    }
                    if (type == 1) {
                        ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.装备.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                        ((DefaultTableModel)(DefaultTableModel)this.装备.getModel()).removeRow(i);
                        break;
                    }
                    break;
                }
                case 2: {
                    i = this.消耗.getSelectedRow();
                    if (i < 0) {
                        JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
                        return;
                    }
                    if (type == 0) {
                        ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.消耗.getValueAt(i, 2).toString()));
                        ps.setInt(2, Integer.parseInt(this.消耗.getValueAt(i, 3).toString()));
                        ps.setInt(3, Integer.parseInt(this.消耗.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                    }
                    if (type == 1) {
                        ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.消耗.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                        ((DefaultTableModel)(DefaultTableModel)this.消耗.getModel()).removeRow(i);
                        break;
                    }
                    break;
                }
                case 3: {
                    i = this.设置.getSelectedRow();
                    if (i < 0) {
                        JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
                        return;
                    }
                    if (type == 0) {
                        ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.设置.getValueAt(i, 2).toString()));
                        ps.setInt(2, Integer.parseInt(this.设置.getValueAt(i, 3).toString()));
                        ps.setInt(3, Integer.parseInt(this.设置.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                    }
                    if (type == 1) {
                        ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.设置.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                        ((DefaultTableModel)(DefaultTableModel)this.设置.getModel()).removeRow(i);
                        break;
                    }
                    break;
                }
                case 4: {
                    i = this.其他.getSelectedRow();
                    if (i < 0) {
                        JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
                        return;
                    }
                    if (type == 0) {
                        ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.其他.getValueAt(i, 2).toString()));
                        ps.setInt(2, Integer.parseInt(this.其他.getValueAt(i, 3).toString()));
                        ps.setInt(3, Integer.parseInt(this.其他.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                    }
                    if (type == 1) {
                        ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.其他.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                        ((DefaultTableModel)(DefaultTableModel)this.其他.getModel()).removeRow(i);
                        break;
                    }
                    break;
                }
                case 5: {
                    i = this.特殊.getSelectedRow();
                    if (i < 0) {
                        JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
                        return;
                    }
                    if (type == 0) {
                        ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.特殊.getValueAt(i, 2).toString()));
                        ps.setInt(2, Integer.parseInt(this.特殊.getValueAt(i, 3).toString()));
                        ps.setInt(3, Integer.parseInt(this.特殊.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                    }
                    if (type == 1) {
                        ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.特殊.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                        ((DefaultTableModel)(DefaultTableModel)this.特殊.getModel()).removeRow(i);
                        break;
                    }
                    break;
                }
                case 6: {
                    i = this.仓库.getSelectedRow();
                    if (i < 0) {
                        JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
                        return;
                    }
                    if (type == 0) {
                        ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.仓库.getValueAt(i, 2).toString()));
                        ps.setInt(2, Integer.parseInt(this.仓库.getValueAt(i, 3).toString()));
                        ps.setInt(3, Integer.parseInt(this.仓库.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                    }
                    if (type == 1) {
                        ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.仓库.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                        ((DefaultTableModel)(DefaultTableModel)this.仓库.getModel()).removeRow(i);
                        break;
                    }
                    break;
                }
                case 7: {
                    if (type == 0) {
                        ps = con.prepareStatement("update csitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.时仓.getValueAt(i, 2).toString()));
                        ps.setInt(2, Integer.parseInt(this.时仓.getValueAt(i, 3).toString()));
                        ps.setInt(3, Integer.parseInt(this.时仓.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                    }
                    if (type == 1) {
                        ps = con.prepareStatement("delete from csitems WHERE inventoryitemid = ?");
                        ps.setInt(1, Integer.parseInt(this.时仓.getValueAt(i, 0).toString()));
                        ps.execute();
                        ps.close();
                        ((DefaultTableModel)(DefaultTableModel)this.时仓.getModel()).removeRow(i);
                        break;
                    }
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "处理完毕！\n需要确认最新信息，请手动重新获取！", "提示", 1);
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
    }
    
    public void 读取角色技能() {
        if (this.角色昵称.getText().length() <= 1) {
            JOptionPane.showMessageDialog(null, "请选择你要读取的角色！");
            return;
        }
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.技能信息.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.技能信息.getModel()).removeRow(i);
        }
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM skills WHERE characterid = ?");
            ps.setInt(1, this.角色ID);
            rs = ps.executeQuery();
            while (rs.next()) {
                final MapleDataProvider data = MapleDataProviderFactory.getDataProvider(new File(((System.getProperty("wzpath") != null) ? System.getProperty("wzpath") : "") + "wz/String.wz"));
                String skillName = null;
                final MapleData itemsData = data.getData("Skill.img");
                for (final MapleData itemFolder : itemsData.getChildren()) {
                    final int skillId = Integer.parseInt(itemFolder.getName());
                    if (rs.getInt("skillid") == skillId) {
                        skillName = MapleDataTool.getString("name", itemFolder, "NO-NAME");
                    }
                }
                ((DefaultTableModel)this.技能信息.getModel()).insertRow(this.技能信息.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("id")), skillName, Integer.valueOf(rs.getInt("skillid")), Integer.valueOf(rs.getInt("skilllevel")), Integer.valueOf(rs.getInt("masterlevel")) });
            }
            rs.close();
            ps.close();
            con.close();
            JOptionPane.showMessageDialog(null, "读取速度因角色数据量而异！\n读取完毕！可前往查看！", "提示", 1);
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
    }
    
    public void 修改角色技能(final int type) {
        Connection con = null;
        PreparedStatement ps = null;
        final int i = this.技能信息.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
            return;
        }
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            if (type == 0) {
                ps = con.prepareStatement("update skills set skillid = ?,skilllevel = ?,masterlevel = ? WHERE id = ?");
                ps.setInt(1, Integer.parseInt(this.技能信息.getValueAt(i, 2).toString()));
                ps.setInt(2, Integer.parseInt(this.技能信息.getValueAt(i, 3).toString()));
                ps.setInt(3, Integer.parseInt(this.技能信息.getValueAt(i, 4).toString()));
                ps.setInt(4, Integer.parseInt(this.技能信息.getValueAt(i, 0).toString()));
                ps.execute();
                ps.close();
            }
            if (type == 1) {
                ps = con.prepareStatement("delete from skills WHERE id = ?");
                ps.setInt(1, Integer.parseInt(this.技能信息.getValueAt(i, 0).toString()));
                ps.execute();
                ps.close();
                ((DefaultTableModel)(DefaultTableModel)this.技能信息.getModel()).removeRow(i);
            }
            JOptionPane.showMessageDialog(null, "处理完毕！\n需要确认最新信息，请手动重新获取！", "提示", 1);
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
    }
    
    public void 保存数据() {
        final int n = JOptionPane.showConfirmDialog((Component)this, "需要保存所有在线角色数据和雇佣商人吗？", "信息", 0);
        if (n == 0) {
            int p = 0;
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    ++p;
                    chr.saveToDB(true, true);
                }
                cserv.closeAllMerchant();
            }
            JOptionPane.showMessageDialog(null, "保存" + p + "个在线角色和雇佣商人成功!");
        }
    }
    
    public void 重载全部() {
        final int n = JOptionPane.showConfirmDialog((Component)this, "你需要重载全部配置吗？", "信息", 0);
        if (n == 0) {
            MapleShopFactory.getInstance().clear();
            ReactorScriptManager.getInstance().clearDrops();
            MapleMonsterInformationProvider.getInstance().clearDrops();
            PortalScriptManager.getInstance().clearScripts();
            Start.GetConfigValues();
            JOptionPane.showMessageDialog(null, "重载完成。");
        }
    }
    
    public void 读取家族信息() {
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.家族信息.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.家族信息.getModel()).removeRow(i);
        }
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM guilds");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel)this.家族信息.getModel()).insertRow(this.家族信息.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("guildid")), rs.getString("name"), NPCConversationManager.角色ID取名字(rs.getInt("leader")), rs.getString("rank1title"), rs.getString("rank2title"), rs.getString("rank3title"), rs.getString("rank4title"), rs.getString("rank5title"), rs.getString("GP") });
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
        }
    }
    
    public void 在线角色处理(final int type) {
        boolean success = false;
        if (this.角色昵称.getText().length() <= 1) {
            JOptionPane.showMessageDialog(null, "请选择你要处理的角色！");
            return;
        }
        switch (type) {
            case 0: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要踢[" + this.角色昵称.getText() + "]下线吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.getClient().disconnect(true, false);
                        chr.getClient().getSession().close();
                        success = true;
                    }
                }
                break;
            }
            case 1: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要还原[" + this.角色昵称.getText() + "]成为新人,并且清理他所有的技能跟属性点吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.clearSkills();
                        chr.changeJob(0);
                        chr.getStat().setMaxHp((short)50);
                        chr.getStat().setMaxMp((short)50);
                        chr.getStat().setStr((short)4);
                        chr.getStat().setDex((short)4);
                        chr.getStat().setInt((short)4);
                        chr.getStat().setLuk((short)4);
                        chr.updateSingleStat(MapleStat.MAXHP, 50);
                        chr.updateSingleStat(MapleStat.MAXMP, 50);
                        chr.updateSingleStat(MapleStat.STR, 4);
                        chr.updateSingleStat(MapleStat.DEX, 4);
                        chr.updateSingleStat(MapleStat.INT, 4);
                        chr.updateSingleStat(MapleStat.LUK, 4);
                        chr.setExp(0);
                        chr.updateSingleStat(MapleStat.EXP, 0);
                        chr.setLevel((short)1);
                        chr.updateSingleStat(MapleStat.LEVEL, 1);
                        chr.setRemainingAp((short)0);
                        chr.updateSingleStat(MapleStat.AVAILABLEAP, 0);
                        chr.setRemainingSp(0);
                        chr.updateSingleStat(MapleStat.AVAILABLESP, 0);
                        success = true;
                    }
                }
                break;
            }
            case 2: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要封[" + this.角色昵称.getText() + "]的掉落并踢下线吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.getClient().disconnect(true, false);
                        chr.getClient().getSession().close();
                        success = true;
                    }
                }
                break;
            }
            case 3: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要把[" + this.角色昵称.getText() + "]传送到自由市场吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.changeMap(910000000);
                        success = true;
                    }
                }
                break;
            }
            case 4: {
                final String id = JOptionPane.showInputDialog((Component)this, "你要把[" + this.角色昵称.getText() + "]传送到哪里？(输入地图ID)", "信息", 2);
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.changeMap(Integer.parseInt(id));
                        success = true;
                    }
                }
                break;
            }
            case 5: {
                final String id = JOptionPane.showInputDialog((Component)this, "输入你要更改[" + this.角色昵称.getText() + "]的脸型ID！", "信息", 2);
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.setFace(Integer.parseInt(id));
                        success = true;
                    }
                }
                break;
            }
            case 6: {
                final String id = JOptionPane.showInputDialog((Component)this, "输入你要更改[" + this.角色昵称.getText() + "]的发型ID！", "信息", 2);
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.setHair(Integer.parseInt(id));
                        success = true;
                    }
                }
                break;
            }
            case 7: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要将[" + this.角色昵称.getText() + "]提升到满级吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText()) || chr.getLevel() >= ((Integer)Start.ConfigValuesMap.get("角色最大等级")).intValue()) {
                            continue;
                        }
                        for (int j = ((Integer)Start.ConfigValuesMap.get("角色最大等级")).intValue() - chr.getLevel(), i = 0; i < j; ++i) {
                            chr.levelUp();
                        }
                        chr.setExp(0);
                        chr.updateSingleStat(MapleStat.EXP, 0);
                        success = true;
                    }
                }
                break;
            }
            case 8: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要将[" + this.角色昵称.getText() + "]满属性吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.getStat().setMaxHp((short)30000);
                        chr.getStat().setMaxMp((short)30000);
                        chr.getStat().setStr((short)32767);
                        chr.getStat().setDex((short)32767);
                        chr.getStat().setInt((short)32767);
                        chr.getStat().setLuk((short)32767);
                        chr.updateSingleStat(MapleStat.MAXHP, 30000);
                        chr.updateSingleStat(MapleStat.MAXMP, 30000);
                        chr.updateSingleStat(MapleStat.STR, 32767);
                        chr.updateSingleStat(MapleStat.DEX, 32767);
                        chr.updateSingleStat(MapleStat.INT, 32767);
                        chr.updateSingleStat(MapleStat.LUK, 32767);
                        success = true;
                    }
                }
                break;
            }
            case 9: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要将[" + this.角色昵称.getText() + "]满技能吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.maxSkills();
                        success = true;
                    }
                }
                break;
            }
            case 10: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要将[" + this.角色昵称.getText() + "]满技能吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.openSkill(chr);
                        success = true;
                    }
                }
                break;
            }
            case 11: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要将[" + this.角色昵称.getText() + "]背包清空吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        for (int d = 0; d < 96; ++d) {
                            final IItem target = chr.getInventory(MapleInventoryType.EQUIP).getItem((short)d);
                            if (target != null) {
                                MapleInventoryManipulator.removeFromSlot(chr.getClient(), MapleInventoryType.EQUIP, target.getPosition(), target.getQuantity(), true);
                            }
                        }
                        for (int d = 0; d < 96; ++d) {
                            final IItem target = chr.getInventory(MapleInventoryType.USE).getItem((short)d);
                            if (target != null) {
                                MapleInventoryManipulator.removeFromSlot(chr.getClient(), MapleInventoryType.USE, target.getPosition(), target.getQuantity(), true);
                            }
                        }
                        for (int d = 0; d < 96; ++d) {
                            final IItem target = chr.getInventory(MapleInventoryType.SETUP).getItem((short)d);
                            if (target != null) {
                                MapleInventoryManipulator.removeFromSlot(chr.getClient(), MapleInventoryType.SETUP, target.getPosition(), target.getQuantity(), true);
                            }
                        }
                        for (int d = 0; d < 96; ++d) {
                            final IItem target = chr.getInventory(MapleInventoryType.ETC).getItem((short)d);
                            if (target != null) {
                                MapleInventoryManipulator.removeFromSlot(chr.getClient(), MapleInventoryType.ETC, target.getPosition(), target.getQuantity(), true);
                            }
                        }
                        for (int d = 0; d < 96; ++d) {
                            final IItem target = chr.getInventory(MapleInventoryType.CASH).getItem((short)d);
                            if (target != null) {
                                MapleInventoryManipulator.removeFromSlot(chr.getClient(), MapleInventoryType.CASH, target.getPosition(), target.getQuantity(), true);
                            }
                        }
                        success = true;
                    }
                }
                break;
            }
            case 12: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要将[" + this.角色昵称.getText() + "]身上装备清空吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        for (int d = -1; d > -129; --d) {
                            final IItem target = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short)d);
                            if (target != null) {
                                chr.getInventory(MapleInventoryType.EQUIPPED).removeItem(target.getPosition());
                            }
                        }
                        chr.equipChanged();
                        chr.getClient().sendPacket(MaplePacketCreator.getCharInfo(chr));
                        chr.getMap().removePlayer(chr);
                        chr.getMap().addPlayer(chr);
                        success = true;
                    }
                }
                break;
            }
            case 13: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要击杀[" + this.角色昵称.getText() + "]吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.startMapEffect("不友好之人！承受死亡的痛苦吧！", 5120027);
                        chr.setHp(0);
                        chr.updateSingleStat(MapleStat.HP, 0);
                        success = true;
                    }
                }
                break;
            }
            case 14: {
                final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要把[" + this.角色昵称.getText() + "]传送到小黑屋吗？", "信息", 0);
                if (answer != 0) {
                    return;
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        if (chr == null) {
                            continue;
                        }
                        if (!chr.getName().contains((CharSequence)this.角色昵称.getText())) {
                            continue;
                        }
                        chr.changeMap(180000001);
                        success = true;
                    }
                }
                break;
            }
        }
        if (success) {
            JOptionPane.showMessageDialog(null, "处理成功！", "提示", 1);
        }
        else {
            JOptionPane.showMessageDialog(null, "处理失败！角色没有找到或者不符合要求！", "提示", 1);
        }
    }
    
    public void 全员下线() {
        final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要将所有玩家踢下线吗？", "信息", 0);
        if (answer != 0) {
            return;
        }
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr == null) {
                    continue;
                }
                chr.getClient().disconnect(true, false);
                chr.getClient().getSession().close();
            }
        }
        JOptionPane.showMessageDialog(null, "处理成功！", "提示", 1);
    }
    
    private void 关闭服务端() {
        玩家控制台.主界面菜单.setSelectedIndex(0);
        玩家控制台.主界面菜单.setEnabled(false);
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.closeAllMerchant();
        }
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                chr.saveToDB(true, true);
            }
        }
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.getPlayerStorage().断所有人(true);
        }
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.getPlayerStorage().断所有人(true);
        }
        try {
            this.minutesLeft = 0;
            if (玩家控制台.ts == null && (玩家控制台.t == null || !玩家控制台.t.isAlive())) {
                玩家控制台.t = new Thread((Runnable)ShutdownServer.getInstance());
                玩家控制台.ts = EventTimer.getInstance().register((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (玩家控制台.this.minutesLeft == 0) {
                            ShutdownServer.getInstance();
                            玩家控制台.t.start();
                            玩家控制台.ts.cancel(false);
                            return;
                        }
                        玩家控制台.this.minutesLeft--;
                    }
                }, 60000L);
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + e);
        }
    }
    
    private void 发放福利(final int type) {
        if (this.发放奖励数量.getText().matches("[0-9]+")) {
            int num = Integer.parseInt(this.发放奖励数量.getText());
            boolean solo = false;
            if (num <= 0) {
                return;
            }
            if (num > 99999999) {
                num = 99999999;
            }
            if (this.选中玩家A.isSelected()) {
                if (this.角色昵称.getText().length() <= 1) {
                    JOptionPane.showMessageDialog(null, "请选择你要处理的角色！");
                    return;
                }
                solo = true;
            }
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (solo && !mch.getName().contains((CharSequence)this.角色昵称.getText()) && mch.getId() != this.角色ID) {
                        continue;
                    }
                    switch (type) {
                        case 0: {
                            mch.gainMeso(num, true);
                            mch.startMapEffect("管理员发放了[" + num + "]金币的福利！", 5120027);
                            continue;
                        }
                        case 1: {
                            mch.gainExp(num, false, false, false);
                            mch.startMapEffect("管理员发放了[" + num + "]经验的福利！", 5120027);
                            continue;
                        }
                        case 2: {
                            mch.gainBeans(num);
                            mch.startMapEffect("管理员发放了[" + num + "]豆豆的福利！", 5120027);
                            continue;
                        }
                        case 3: {
                            mch.modifyCSPoints(2, num, true);
                            mch.startMapEffect("管理员发放了[" + num + "]抵用的福利！", 5120027);
                            continue;
                        }
                        case 4: {
                            mch.modifyCSPoints(1, num, true);
                            mch.startMapEffect("管理员发放了[" + num + "]点券的福利！", 5120027);
                            continue;
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "在线奖励发放成功！", "提示", 1);
        }
        else {
            JOptionPane.showMessageDialog(null, "发放失败！请输入有效数量！", "提示", 1);
        }
    }
    
    private void 发放物品() {
        if (this.发放物品数量.getText().matches("[0-9]+")) {
            int num = Integer.parseInt(this.发放物品数量.getText());
            boolean solo = false;
            if (num <= 0) {
                return;
            }
            if (num > 32767) {
                num = 32767;
            }
            if (this.选中玩家B.isSelected()) {
                if (this.角色昵称.getText().length() <= 1) {
                    JOptionPane.showMessageDialog(null, "请选择你要处理的角色！");
                    return;
                }
                solo = true;
            }
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (solo && !mch.getName().contains((CharSequence)this.角色昵称.getText()) && mch.getId() != this.角色ID) {
                        continue;
                    }
                    mch.gainItem(Integer.parseInt(this.发放物品ID.getText()), Integer.parseInt(this.发放物品数量.getText()));
                    mch.startMapEffect("管理员发放了[" + ii.getName(Integer.parseInt(this.发放物品ID.getText())) + "x" + this.发放物品数量.getText() + "]的福利！", 5120027);
                }
            }
            JOptionPane.showMessageDialog(null, "在线奖励物品发放成功！", "提示", 1);
        }
        else {
            JOptionPane.showMessageDialog(null, "发放失败！请输入有效数量！", "提示", 1);
        }
    }
    
    public void 初始化界面配置() {
        this.账号信息.addMouseListener((MouseListener)new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = 玩家控制台.this.账号信息.getSelectedRow();
                玩家控制台.this.账号ID = Integer.parseInt(玩家控制台.this.账号信息.getValueAt(i, 0).toString());
                玩家控制台.this.账号.setText(玩家控制台.this.账号信息.getValueAt(i, 1).toString());
                玩家控制台.this.抵用.setText(玩家控制台.this.账号信息.getValueAt(i, 3).toString());
                玩家控制台.this.点券.setText(玩家控制台.this.账号信息.getValueAt(i, 4).toString());
                玩家控制台.this.QQ.setText((玩家控制台.this.账号信息.getValueAt(i, 6) == null) ? "" : 玩家控制台.this.账号信息.getValueAt(i, 6).toString());
                玩家控制台.this.刷新角色信息(5);
            }
        });
        this.角色信息.addMouseListener((MouseListener)new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = 玩家控制台.this.角色信息.getSelectedRow();
                玩家控制台.this.角色ID = Integer.parseInt(玩家控制台.this.角色信息.getValueAt(i, 2).toString());
                玩家控制台.this.角色昵称.setText(玩家控制台.this.角色信息.getValueAt(i, 3).toString());
                玩家控制台.this.等级.setText(玩家控制台.this.角色信息.getValueAt(i, 4).toString());
                玩家控制台.this.职业ID.setText(String.valueOf(MapleCarnivalChallenge.getIdByJobName(玩家控制台.this.角色信息.getValueAt(i, 5).toString())));
                玩家控制台.this.所在地图.setText(玩家控制台.this.角色信息.getValueAt(i, 6).toString());
                玩家控制台.this.金币.setText(玩家控制台.this.角色信息.getValueAt(i, 7).toString());
                玩家控制台.this.豆豆.setText(玩家控制台.this.角色信息.getValueAt(i, 8).toString());
                玩家控制台.this.力量.setText(玩家控制台.this.角色信息.getValueAt(i, 9).toString());
                玩家控制台.this.智力.setText(玩家控制台.this.角色信息.getValueAt(i, 11).toString());
                玩家控制台.this.敏捷.setText(玩家控制台.this.角色信息.getValueAt(i, 10).toString());
                玩家控制台.this.运气.setText(玩家控制台.this.角色信息.getValueAt(i, 12).toString());
                玩家控制台.this.MAXHP.setText(玩家控制台.this.角色信息.getValueAt(i, 13).toString());
                玩家控制台.this.MAXMP.setText(玩家控制台.this.角色信息.getValueAt(i, 14).toString());
                玩家控制台.this.发型.setText(玩家控制台.this.角色信息.getValueAt(i, 15).toString());
                玩家控制台.this.脸型.setText(玩家控制台.this.角色信息.getValueAt(i, 16).toString());
                玩家控制台.this.能力点.setText(玩家控制台.this.角色信息.getValueAt(i, 18).toString());
                玩家控制台.this.技能点.setText(玩家控制台.this.角色信息.getValueAt(i, 19).toString());
                玩家控制台.this.选中角色名.setText("当前选中角色:[" + 玩家控制台.this.角色信息.getValueAt(i, 3).toString() + "]");
            }
        });
        this.怪物爆物.addMouseListener((MouseListener)new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = 玩家控制台.this.怪物爆物.getSelectedRow();
                玩家控制台.this.怪物爆物序号 = Integer.valueOf(玩家控制台.this.怪物爆物.getValueAt(i, 0).toString()).intValue();
                玩家控制台.this.怪物爆物怪物ID.setText(玩家控制台.this.怪物爆物.getValueAt(i, 1).toString());
                玩家控制台.this.怪物爆物物品名字.setText(玩家控制台.this.怪物爆物.getValueAt(i, 2).toString());
                玩家控制台.this.怪物爆物物品ID.setText(玩家控制台.this.怪物爆物.getValueAt(i, 3).toString());
                玩家控制台.this.怪物爆物物品爆率.setText(玩家控制台.this.怪物爆物.getValueAt(i, 4).toString());
            }
        });
        this.全局爆物.addMouseListener((MouseListener)new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = 玩家控制台.this.全局爆物.getSelectedRow();
                玩家控制台.this.全局爆物序号 = Integer.valueOf(玩家控制台.this.全局爆物.getValueAt(i, 0).toString()).intValue();
                玩家控制台.this.全局爆物物品名字.setText(玩家控制台.this.全局爆物.getValueAt(i, 1).toString());
                玩家控制台.this.全局爆物物品ID.setText(玩家控制台.this.全局爆物.getValueAt(i, 2).toString());
                玩家控制台.this.全局爆物物品爆率.setText(玩家控制台.this.全局爆物.getValueAt(i, 3).toString());
            }
        });
        this.商品列表.addMouseListener((MouseListener)new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = 玩家控制台.this.商品列表.getSelectedRow();
                玩家控制台.this.商店物品序号 = Integer.valueOf(玩家控制台.this.商品列表.getValueAt(i, 0).toString()).intValue();
                玩家控制台.this.商店物品名称.setText(玩家控制台.this.商品列表.getValueAt(i, 1).toString());
                玩家控制台.this.商店物品ID.setText(玩家控制台.this.商品列表.getValueAt(i, 2).toString());
                玩家控制台.this.商店物品价格.setText(玩家控制台.this.商品列表.getValueAt(i, 3).toString());
                玩家控制台.this.商品排列顺序.setText(玩家控制台.this.商品列表.getValueAt(i, 4).toString());
            }
        });
        this.商城物品表.addMouseListener((MouseListener)new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = 玩家控制台.this.商城物品表.getSelectedRow();
                玩家控制台.this.商品编码.setText(玩家控制台.this.商城物品表.getValueAt(i, 0).toString());
                玩家控制台.this.商品代码.setText(玩家控制台.this.商城物品表.getValueAt(i, 1).toString());
                玩家控制台.this.商品名称.setText(玩家控制台.this.商城物品表.getValueAt(i, 2).toString());
                玩家控制台.this.商品数量.setText(玩家控制台.this.商城物品表.getValueAt(i, 3).toString());
                玩家控制台.this.商品价格.setText(玩家控制台.this.商城物品表.getValueAt(i, 4).toString());
                玩家控制台.this.商品限时.setText(玩家控制台.this.商城物品表.getValueAt(i, 5).toString());
                玩家控制台.this.商品出售状态.setSelectedItem(玩家控制台.this.商城物品表.getValueAt(i, 6).toString());
                玩家控制台.this.商品上下架.setSelectedItem(玩家控制台.this.商城物品表.getValueAt(i, 7).toString());
                玩家控制台.this.商品性别.setSelectedItem(玩家控制台.this.商城物品表.getValueAt(i, 8).toString());
                玩家控制台.this.商品排序.setText(玩家控制台.this.商城物品表.getValueAt(i, 9).toString());
            }
        });
        final ButtonGroup g1 = new ButtonGroup();
        g1.add((AbstractButton)this.在线玩家A);
        g1.add((AbstractButton)this.选中玩家A);
        final ButtonGroup g2 = new ButtonGroup();
        g2.add((AbstractButton)this.在线玩家B);
        g2.add((AbstractButton)this.选中玩家B);
        final ButtonGroup g3 = new ButtonGroup();
        g3.add((AbstractButton)this.装备可以交易);
        g3.add((AbstractButton)this.装备不可交易);
        final ButtonGroup g4 = new ButtonGroup();
    }
    
    public void 发放定制装备() {
        boolean success = false;
        final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要发放给[" + this.角色昵称.getText() + "]一个全新的装备吗？", "信息", 0);
        if (answer != 0) {
            return;
        }
        if (this.装备ID.getText().matches("[0-9]+")) {
            final int id = Integer.parseInt(this.装备ID.getText());
            if (this.角色昵称.getText().length() <= 1) {
                JOptionPane.showMessageDialog(null, "请选择你要处理的角色！");
                return;
            }
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (mch.getName().contains((CharSequence)this.角色昵称.getText()) && mch.getId() == this.角色ID) {
                        final MapleInventoryType type = GameConstants.getInventoryType(id);
                        if (type != MapleInventoryType.EQUIP && type != MapleInventoryType.CASH) {
                            JOptionPane.showMessageDialog(null, "似乎不是装备也不是时装！无法发送定制！", "提示", 1);
                            return;
                        }
                        if (!MapleInventoryManipulator.checkSpace(mch.getClient(), id, 1, "")) {
                            JOptionPane.showMessageDialog(null, "玩家背包满了！无法发送定制！", "提示", 1);
                            return;
                        }
                        final Equip item = (Equip)(Equip)ii.getEquipById(id);
                        if (Integer.parseInt(this.装备HP.getText()) > 0 && Integer.parseInt(this.装备HP.getText()) < 32768) {
                            item.setHp((short)Integer.parseInt(this.装备HP.getText()));
                        }
                        if (Integer.parseInt(this.装备MP.getText()) > 0 && Integer.parseInt(this.装备MP.getText()) < 32768) {
                            item.setMp((short)Integer.parseInt(this.装备MP.getText()));
                        }
                        String 名字 = "";
                        if (this.装备潜能.getText().equals("C") || this.装备潜能.getText().equals("B") || this.装备潜能.getText().equals("A") || this.装备潜能.getText().equals("S") || this.装备潜能.getText().equals("M") || this.装备潜能.getText().equals("L")) {
                            名字 = this.装备潜能.getText() + "级";
                        }
                        item.setOwner(名字);
                        if (!this.装备潜能.getText().equals("") && Integer.parseInt(this.装备星数.getText()) > 0 && Integer.parseInt(this.装备星数.getText()) < 32768) {
                            item.setOwner(item.getOwner() + "★x" + this.装备星数.getText());
                        }
                        if (Integer.parseInt(this.装备力量.getText()) > 0 && Integer.parseInt(this.装备力量.getText()) < 32768) {
                            item.setStr((short)Integer.parseInt(this.装备力量.getText()));
                        }
                        if (Integer.parseInt(this.装备智力.getText()) > 0 && Integer.parseInt(this.装备智力.getText()) < 32768) {
                            item.setInt((short)Integer.parseInt(this.装备智力.getText()));
                        }
                        if (Integer.parseInt(this.装备敏捷.getText()) > 0 && Integer.parseInt(this.装备敏捷.getText()) < 32768) {
                            item.setDex((short)Integer.parseInt(this.装备敏捷.getText()));
                        }
                        if (Integer.parseInt(this.装备运气.getText()) > 0 && Integer.parseInt(this.装备运气.getText()) < 32768) {
                            item.setLuk((short)Integer.parseInt(this.装备运气.getText()));
                        }
                        if (Integer.parseInt(this.装备攻击力.getText()) > 0 && Integer.parseInt(this.装备攻击力.getText()) < 32768) {
                            item.setWatk((short)Integer.parseInt(this.装备攻击力.getText()));
                        }
                        if (Integer.parseInt(this.装备魔法力.getText()) > 0 && Integer.parseInt(this.装备魔法力.getText()) < 32768) {
                            item.setMatk((short)Integer.parseInt(this.装备魔法力.getText()));
                        }
                        if (Integer.parseInt(this.装备物理防御.getText()) > 0 && Integer.parseInt(this.装备物理防御.getText()) < 32768) {
                            item.setWdef((short)Integer.parseInt(this.装备物理防御.getText()));
                        }
                        if (Integer.parseInt(this.装备魔法防御.getText()) > 0 && Integer.parseInt(this.装备魔法防御.getText()) < 32768) {
                            item.setMdef((short)Integer.parseInt(this.装备魔法防御.getText()));
                        }
                        if (Integer.parseInt(this.装备命中.getText()) > 0 && Integer.parseInt(this.装备命中.getText()) < 32768) {
                            item.setAcc((short)Integer.parseInt(this.装备命中.getText()));
                        }
                        if (Integer.parseInt(this.装备回避.getText()) > 0 && Integer.parseInt(this.装备回避.getText()) < 32768) {
                            item.setAvoid((short)Integer.parseInt(this.装备回避.getText()));
                        }
                        if (Integer.parseInt(this.装备移动速度.getText()) > 0 && Integer.parseInt(this.装备移动速度.getText()) < 32768) {
                            item.setSpeed((short)Integer.parseInt(this.装备移动速度.getText()));
                        }
                        if (Integer.parseInt(this.装备跳跃力.getText()) > 0 && Integer.parseInt(this.装备跳跃力.getText()) < 32768) {
                            item.setJump((short)Integer.parseInt(this.装备跳跃力.getText()));
                        }
                        if (Integer.parseInt(this.装备砸卷上限.getText()) >= 0 && Integer.parseInt(this.装备砸卷上限.getText()) < 128) {
                            item.setUpgradeSlots((byte)Integer.parseInt(this.装备砸卷上限.getText()));
                        }
                        if (Integer.parseInt(this.上卷次数.getText()) >= 0 && Integer.parseInt(this.上卷次数.getText()) < 128) {
                            item.setLevel((byte)Integer.parseInt(this.上卷次数.getText()));
                        }
                        if (this.装备限时开关.isSelected() && Integer.parseInt(this.装备限时时间.getText()) > 0) {
                            item.setExpiration(System.currentTimeMillis() + (long)(Integer.parseInt(this.装备限时时间.getText()) * 60 * 60 * 1000));
                        }
                        if (this.装备不可交易.isSelected()) {
                            byte flag = item.getFlag();
                            flag |= (byte)ItemFlag.UNTRADEABLE.getValue();
                            item.setFlag(flag);
                        }
                        MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                        mch.startMapEffect("管理员发放了[" + ii.getName(id) + "x1]的定制装备！", 5120027);
                        success = true;
                        break;
                    }
                }
            }
            if (success) {
                JOptionPane.showMessageDialog(null, "定制装备发放成功！", "提示", 1);
            }
            else {
                JOptionPane.showMessageDialog(null, "定制装备发放失败！可能是角色不在线！", "提示", 1);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "定制发放失败！请输入有效装备ID！", "提示", 1);
        }
    }
    
    public void 第一格装备调整(final int type) {
        boolean success = false;
        final int answer = JOptionPane.showConfirmDialog((Component)this, "你确定要对[" + this.角色昵称.getText() + "]的第一个格子的装备进行操作吗？", "信息", 0);
        if (answer != 0) {
            return;
        }
        if (this.角色昵称.getText().length() <= 1) {
            JOptionPane.showMessageDialog(null, "请选择你要处理的角色！");
            return;
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
            for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                if (mch.getName().contains((CharSequence)this.角色昵称.getText()) && mch.getId() == this.角色ID) {
                    switch (type) {
                        case 0: {
                            if (mch.getInventory(MapleInventoryType.EQUIP).getItem((short)1) == null) {
                                JOptionPane.showMessageDialog(null, "该玩家第一格没有装备，无法收取！", "提示", 1);
                                return;
                            }
                            this.读取装备 = mch.getInventory(MapleInventoryType.EQUIP).getItem((short)1);
                            if (this.读取装备 != null) {
                                this.收取装备状态.setText("收取装备状态：" + this.读取装备.getItemId());
                                mch.getInventory(MapleInventoryType.EQUIP).removeSlot((short)1);
                                mch.getClient().sendPacket(MaplePacketCreator.clearInventoryItem(MapleInventoryType.EQUIP, (short)1, true));
                                this.装备HP.setText(String.valueOf((int)((Equip)this.读取装备).getHp()));
                                this.装备MP.setText(String.valueOf((int)((Equip)this.读取装备).getMp()));
                                this.装备潜能.setText(String.valueOf(((Equip)this.读取装备).getOwner()).substring(0, (this.读取装备.getOwner().indexOf("级") > 0) ? this.读取装备.getOwner().indexOf("级") : 0));
                                this.装备星数.setText(String.valueOf(((Equip)this.读取装备).getOwner()).substring(this.读取装备.getOwner().indexOf("x") + 1));
                                this.装备力量.setText(String.valueOf((int)((Equip)this.读取装备).getStr()));
                                this.装备智力.setText(String.valueOf((int)((Equip)this.读取装备).getInt()));
                                this.装备敏捷.setText(String.valueOf((int)((Equip)this.读取装备).getDex()));
                                this.装备运气.setText(String.valueOf((int)((Equip)this.读取装备).getLuk()));
                                this.装备攻击力.setText(String.valueOf((int)((Equip)this.读取装备).getWatk()));
                                this.装备魔法力.setText(String.valueOf((int)((Equip)this.读取装备).getMatk()));
                                this.装备物理防御.setText(String.valueOf((int)((Equip)this.读取装备).getWdef()));
                                this.装备魔法防御.setText(String.valueOf((int)((Equip)this.读取装备).getMdef()));
                                this.装备命中.setText(String.valueOf((int)((Equip)this.读取装备).getAcc()));
                                this.装备回避.setText(String.valueOf((int)((Equip)this.读取装备).getAvoid()));
                                this.装备移动速度.setText(String.valueOf((int)((Equip)this.读取装备).getSpeed()));
                                this.装备跳跃力.setText(String.valueOf((int)((Equip)this.读取装备).getJump()));
                                this.上卷次数.setText(String.valueOf((int)((Equip)this.读取装备).getLevel()));
                                this.装备砸卷上限.setText(String.valueOf((int)((Equip)this.读取装备).getUpgradeSlots()));
                                this.装备限时时间.setText(String.valueOf(((Equip)this.读取装备).getExpiration() / 3600000L));
                                this.装备ID.setText(String.valueOf(((Equip)this.读取装备).getItemId()));
                                success = true;
                                break;
                            }
                            break;
                        }
                        case 1: {
                            if (this.读取装备 == null) {
                                JOptionPane.showMessageDialog(null, "没有收取装备，无法发放！", "提示", 1);
                                return;
                            }
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), this.读取装备.getItemId(), 1, "")) {
                                JOptionPane.showMessageDialog(null, "玩家背包满了！无法发送收取的装备！", "提示", 1);
                                return;
                            }
                            final Equip item = (Equip)this.读取装备;
                            if (Integer.parseInt(this.装备HP.getText()) > 0 && Integer.parseInt(this.装备HP.getText()) < 32768) {
                                item.setHp((short)Integer.parseInt(this.装备HP.getText()));
                            }
                            if (Integer.parseInt(this.装备MP.getText()) > 0 && Integer.parseInt(this.装备MP.getText()) < 32768) {
                                item.setMp((short)Integer.parseInt(this.装备MP.getText()));
                            }
                            String 名字 = "";
                            if (this.装备潜能.getText().equals("C") || this.装备潜能.getText().equals("B") || this.装备潜能.getText().equals("A") || this.装备潜能.getText().equals("S") || this.装备潜能.getText().equals("M") || this.装备潜能.getText().equals("L")) {
                                名字 = this.装备潜能.getText() + "级";
                            }
                            item.setOwner(名字);
                            if (!this.装备潜能.getText().equals("") && Integer.parseInt(this.装备星数.getText()) > 0 && Integer.parseInt(this.装备星数.getText()) < 32768) {
                                item.setOwner(item.getOwner() + "★x" + this.装备星数.getText());
                            }
                            if (Integer.parseInt(this.装备力量.getText()) > 0 && Integer.parseInt(this.装备力量.getText()) < 32768) {
                                item.setStr((short)Integer.parseInt(this.装备力量.getText()));
                            }
                            if (Integer.parseInt(this.装备智力.getText()) > 0 && Integer.parseInt(this.装备智力.getText()) < 32768) {
                                item.setInt((short)Integer.parseInt(this.装备智力.getText()));
                            }
                            if (Integer.parseInt(this.装备敏捷.getText()) > 0 && Integer.parseInt(this.装备敏捷.getText()) < 32768) {
                                item.setDex((short)Integer.parseInt(this.装备敏捷.getText()));
                            }
                            if (Integer.parseInt(this.装备运气.getText()) > 0 && Integer.parseInt(this.装备运气.getText()) < 32768) {
                                item.setLuk((short)Integer.parseInt(this.装备运气.getText()));
                            }
                            if (Integer.parseInt(this.装备攻击力.getText()) > 0 && Integer.parseInt(this.装备攻击力.getText()) < 32768) {
                                item.setWatk((short)Integer.parseInt(this.装备攻击力.getText()));
                            }
                            if (Integer.parseInt(this.装备魔法力.getText()) > 0 && Integer.parseInt(this.装备魔法力.getText()) < 32768) {
                                item.setMatk((short)Integer.parseInt(this.装备魔法力.getText()));
                            }
                            if (Integer.parseInt(this.装备物理防御.getText()) > 0 && Integer.parseInt(this.装备物理防御.getText()) < 32768) {
                                item.setWdef((short)Integer.parseInt(this.装备物理防御.getText()));
                            }
                            if (Integer.parseInt(this.装备魔法防御.getText()) > 0 && Integer.parseInt(this.装备魔法防御.getText()) < 32768) {
                                item.setMdef((short)Integer.parseInt(this.装备魔法防御.getText()));
                            }
                            if (Integer.parseInt(this.装备命中.getText()) > 0 && Integer.parseInt(this.装备命中.getText()) < 32768) {
                                item.setAcc((short)Integer.parseInt(this.装备命中.getText()));
                            }
                            if (Integer.parseInt(this.装备回避.getText()) > 0 && Integer.parseInt(this.装备回避.getText()) < 32768) {
                                item.setAvoid((short)Integer.parseInt(this.装备回避.getText()));
                            }
                            if (Integer.parseInt(this.装备移动速度.getText()) > 0 && Integer.parseInt(this.装备移动速度.getText()) < 32768) {
                                item.setSpeed((short)Integer.parseInt(this.装备移动速度.getText()));
                            }
                            if (Integer.parseInt(this.装备跳跃力.getText()) > 0 && Integer.parseInt(this.装备跳跃力.getText()) < 32768) {
                                item.setJump((short)Integer.parseInt(this.装备跳跃力.getText()));
                            }
                            if (Integer.parseInt(this.装备砸卷上限.getText()) >= 0 && Integer.parseInt(this.装备砸卷上限.getText()) < 128) {
                                item.setUpgradeSlots((byte)Integer.parseInt(this.装备砸卷上限.getText()));
                            }
                            if (Integer.parseInt(this.上卷次数.getText()) >= 0 && Integer.parseInt(this.上卷次数.getText()) < 128) {
                                item.setLevel((byte)Integer.parseInt(this.上卷次数.getText()));
                            }
                            if (this.装备限时开关.isSelected() && Integer.parseInt(this.装备限时时间.getText()) > 0) {
                                item.setExpiration(System.currentTimeMillis() + (long)(Integer.parseInt(this.装备限时时间.getText()) * 60 * 60 * 1000));
                            }
                            if (this.装备不可交易.isSelected()) {
                                byte flag = item.getFlag();
                                flag |= (byte)ItemFlag.UNTRADEABLE.getValue();
                                item.setFlag(flag);
                            }
                            MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            mch.startMapEffect("管理员发放了[" + ii.getName(item.getItemId()) + "x1]的定制装备！", 5120027);
                            this.读取装备 = null;
                            mch.saveToDB(true, true);
                            this.收取装备状态.setText("收取装备状态：[无]");
                            success = true;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        if (success) {
            JOptionPane.showMessageDialog(null, "第一格装备处理成功！", "提示", 1);
        }
        else {
            JOptionPane.showMessageDialog(null, "第一格装备处理失败！可能是角色不在线！", "提示", 1);
        }
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
    
    public void 经验限制配置更新(final String name, final int value) {
        if (Start.ConfigValuesMap.get(name) != null) {
            Start.ConfigValuesMap.put(name, Integer.valueOf(value));
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE explimit SET num = ? WHERE name = ?");
            ps.setInt(1, value);
            ps.setString(2, name);
            ps.execute();
            ps.close();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }
    
    public void 血量倍率配置更新(final int name, final int value) {
        if (DamageParse.MobRedDam.get(Integer.valueOf(name)) != null) {
            DamageParse.MobRedDam.put(Integer.valueOf(name), Integer.valueOf(value));
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE mobreddam SET numb = ? WHERE mobid = ?");
            ps.setInt(1, value);
            ps.setInt(2, name);
            ps.execute();
            ps.close();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }
    
    public void 血量倍率配置更新1(final String name, final int value) {
        if (Start.ConfigValuesMap.get(name) != null) {
            Start.ConfigValuesMap.put(name, Integer.valueOf(value));
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE ConfigValues SET val = ? WHERE name = ?");
            ps.setInt(1, value);
            ps.setString(2, name);
            ps.execute();
            ps.close();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }
    
    public void 公告配置更新(final String nameNew, final String nameOld) {
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE messages SET message = ? WHERE message = ?");
            ps.setString(1, nameNew);
            ps.setString(2, nameOld);
            ps.execute();
            ps.close();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }
    
    public void 重载配置() {
        Start.GetConfigValues();
        Start.公告初始化();
        JOptionPane.showMessageDialog(null, "重载数据库配置成功！", "提示", 1);
    }
    
    private void 筛选邮件玩家(final int type) {
        for (int i = ((DefaultTableModel)(DefaultTableModel)玩家控制台.邮件角色列表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)玩家控制台.邮件角色列表.getModel()).removeRow(i);
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters order by level desc");
            rs = ps.executeQuery();
            while (rs.next()) {
                if (type == 0 && Find.findChannel(rs.getString("name")) <= 0) {
                    continue;
                }
                if (type == 1 && Find.findChannel(rs.getString("name")) > 0) {
                    continue;
                }
                ((DefaultTableModel)玩家控制台.邮件角色列表.getModel()).insertRow(玩家控制台.邮件角色列表.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("id")), rs.getString("name"), rs.getString("level") });
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]获取玩家列表出错：" + ex.getMessage());
        }
    }
    
    private void 发起公告(final int type) {
        for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
            for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                switch (type) {
                    case 0: {
                        Broadcast.broadcastMessage(MaplePacketCreator.serverMessage(this.临时公告.getText()));
                        continue;
                    }
                    case 1: {
                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(1, this.临时公告.getText()));
                        continue;
                    }
                    case 2: {
                        mch.startMapEffect(this.临时公告.getText(), 5120015);
                        continue;
                    }
                    case 3: {
                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, this.临时公告.getText()));
                        continue;
                    }
                    case 4: {
                        Broadcast.broadcastMessage(MaplePacketCreator.serverMessage(this.临时公告.getText()));
                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(1, this.临时公告.getText()));
                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, this.临时公告.getText()));
                        mch.startMapEffect(this.临时公告.getText(), 5120015);
                        continue;
                    }
                }
            }
        }
    }
    
    public void 发送邮件(final int type) {
        switch (type) {
            case 0: {
                final int i = 玩家控制台.邮件角色列表.getSelectedRow();
                if (i < 0) {
                    JOptionPane.showMessageDialog(null, "没有选中角色！操作错误！", "提示", 1);
                    return;
                }
                if (this.附件A数量.getText() == null || this.附件B数量.getText() == null || this.附件C数量.getText() == null) {
                    JOptionPane.showMessageDialog(null, "操作错误！即使不发放的道具数量也要填0！", "提示", 1);
                    return;
                }
                try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
                     final PreparedStatement ps = con.prepareStatement("INSERT INTO mail (title,characterid,itemA,numA,itemB,numB,itemC,numC,body ) VALUES ( ?,?,?,?,?,?,?,?,? )")) {
                    ps.setString(1, this.邮件标题.getText());
                    ps.setInt(2, Integer.parseInt(玩家控制台.邮件角色列表.getValueAt(i, 0).toString()));
                    ps.setInt(3, Integer.parseInt(this.附件A代码.getText()));
                    ps.setInt(4, Integer.parseInt(this.附件A数量.getText()));
                    ps.setInt(5, Integer.parseInt(this.附件B代码.getText()));
                    ps.setInt(6, Integer.parseInt(this.附件B数量.getText()));
                    ps.setInt(7, Integer.parseInt(this.附件C代码.getText()));
                    ps.setInt(8, Integer.parseInt(this.附件C数量.getText()));
                    ps.setString(9, this.邮件正文.getText());
                    ps.execute();
                    MapleCharacterUtil.sendNote(玩家控制台.邮件角色列表.getValueAt(i, 1).toString(), 玩家控制台.邮件角色列表.getValueAt(i, 1).toString(), "[新邮件,请查收]" + this.邮件标题.getText(), 0);
                }
                catch (SQLException ex) {
                    System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]发放邮件出错：" + ex.getMessage());
                }
                break;
            }
            case 1:
            case 2: {
                final ArrayList playerList = new ArrayList();
                try {
                    final Connection con2 = (Connection)DBConPool.getInstance().getDataSource().getConnection();
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    ps = con2.prepareStatement("SELECT * FROM characters order by level desc");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        if (type == 1 && Find.findChannel(rs.getString("name")) <= 0) {
                            continue;
                        }
                        playerList.add(Integer.valueOf(rs.getInt("id")));
                    }
                    rs.close();
                    ps.close();
                    con2.close();
                }
                catch (SQLException ex2) {
                    System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]获取玩家列表出错：" + ex2.getMessage());
                }
                if (playerList.size() <= 0) {
                    return;
                }
                try {
                    final Connection con2 = (Connection)DBConPool.getInstance().getDataSource().getConnection();
                    String insert_command = "INSERT INTO mail (title,characterid,itemA,numA,itemB,numB,itemC,numC,body ) VALUES ";
                    for (int j = 0; j < playerList.size(); ++j) {
                        insert_command = insert_command + "(\"" + this.邮件标题.getText() + "\"," + playerList.get(j).toString() + "," + this.附件A代码.getText() + "," + this.附件A数量.getText() + "," + this.附件B代码.getText() + "," + this.附件B数量.getText() + "," + this.附件C代码.getText() + "," + this.附件C数量.getText() + ",\"" + this.邮件正文.getText() + "\")";
                        if (j + 1 < playerList.size()) {
                            insert_command += ",";
                        }
                    }
                    final PreparedStatement ps2 = con2.prepareStatement(insert_command);
                    ps2.execute();
                    ps2.close();
                    con2.close();
                }
                catch (SQLException ex2) {
                    System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]发放邮件出错：" + ex2.getMessage());
                }
                break;
            }
        }
        JOptionPane.showMessageDialog(null, "邮件发放完毕！", "邮件提示", 1);
    }
    
    public void 清空邮件() {
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("delete from mail")) {
            ps.execute();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]清空邮件出错：" + ex.getMessage());
        }
        JOptionPane.showMessageDialog(null, "邮件清除完毕！", "邮件提示", 1);
    }
    
    public void 重载商城() {
        CashItemFactory.getInstance().clearCashShop();
    }
    
    public void 刷新怪物爆物(final int type) {
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.怪物爆物.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.怪物爆物.getModel()).removeRow(i);
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid !=0");
                    rs = ps.executeQuery();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid = ?");
                    ps.setInt(1, Integer.valueOf(this.物品ID查询掉落.getText()).intValue());
                    rs = ps.executeQuery();
                    break;
                }
                case 2: {
                    ps = con.prepareStatement("SELECT * FROM drop_data WHERE dropperid = ?");
                    ps.setInt(1, Integer.valueOf(this.怪物ID查询掉落.getText()).intValue());
                    rs = ps.executeQuery();
                    break;
                }
            }
            while (rs.next()) {
                ((DefaultTableModel)this.怪物爆物.getModel()).insertRow(this.怪物爆物.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("id")), Integer.valueOf(rs.getInt("dropperid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("chance")) });
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]怪物暴率出错：" + ex.getMessage());
        }
    }
    
    public void 刷新全局爆物(final int type) {
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.全局爆物.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.全局爆物.getModel()).removeRow(i);
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM drop_data_global WHERE itemid !=0");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel)this.全局爆物.getModel()).insertRow(this.全局爆物.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("id")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("chance")) });
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]全局暴率出错：" + ex.getMessage());
        }
    }
    
    public void 清空怪物爆物(final int type) {
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.怪物爆物.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.怪物爆物.getModel()).removeRow(i);
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("DELETE FROM drop_data WHERE itemid = ?");
                    ps.setInt(1, Integer.valueOf(this.清空物品ID.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM drop_data WHERE dropperid = ?");
                    ps.setInt(1, Integer.valueOf(this.清空怪物ID.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "数据清除完毕！", "爆率提示", 1);
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]删除暴率出错：" + ex.getMessage());
        }
    }
    
    public void 刷新怪物卡片() {
        for (int i = ((DefaultTableModel)(DefaultTableModel)this.怪物爆物.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel)(DefaultTableModel)this.怪物爆物.getModel()).removeRow(i);
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid >=2380000&& itemid <2390000");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel)this.怪物爆物.getModel()).insertRow(this.怪物爆物.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("id")), Integer.valueOf(rs.getInt("dropperid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("chance")) });
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]刷新出错：" + ex.getMessage());
        }
    }
    
    public void 调整怪物爆率(final int type) {
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO drop_data (dropperid,itemid,minimum_quantity,maximum_quantity,chance) VALUES( ?,?,1,1,?)");
                    ps.setInt(1, Integer.valueOf(this.怪物爆物怪物ID.getText()).intValue());
                    ps.setInt(2, Integer.valueOf(this.怪物爆物物品ID.getText()).intValue());
                    ps.setInt(3, Integer.valueOf(this.怪物爆物物品爆率.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM drop_data where id = ?");
                    ps.setInt(1, this.怪物爆物序号);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE drop_data set dropperid = ?,itemid = ?,chance = ? where id = ?");
                    ps.setInt(1, Integer.valueOf(this.怪物爆物怪物ID.getText()).intValue());
                    ps.setInt(2, Integer.valueOf(this.怪物爆物物品ID.getText()).intValue());
                    ps.setInt(3, Integer.valueOf(this.怪物爆物物品爆率.getText()).intValue());
                    ps.setInt(4, this.怪物爆物序号);
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            if (this.怪物ID查询掉落.getText().length() > 4 && this.怪物ID查询掉落.getText().matches("[0-9]*")) {
                this.刷新怪物爆物(2);
            }
            else if (this.物品ID查询掉落.getText().length() > 4 && this.物品ID查询掉落.getText().matches("[0-9]*")) {
                this.刷新怪物爆物(1);
            }
            else {
                this.刷新怪物爆物(0);
            }
            MapleMonsterInformationProvider.getInstance().clearDrops();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "爆率提示", 1);
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]刷新出错：" + ex.getMessage());
        }
    }
    
    public void 调整全局爆率(final int type) {
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO drop_data_global (continent,droptype,itemid,minimum_quantity,maximum_quantity,chance) VALUES(1,1,?,1,1,?)");
                    ps.setInt(1, Integer.valueOf(this.全局爆物物品ID.getText()).intValue());
                    ps.setInt(2, Integer.valueOf(this.全局爆物物品爆率.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM drop_data_global where id = ?");
                    ps.setInt(1, this.全局爆物序号);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE drop_data_global set itemid = ?,chance = ? where id = ?");
                    ps.setInt(1, Integer.valueOf(this.全局爆物物品ID.getText()).intValue());
                    ps.setInt(2, Integer.valueOf(this.全局爆物物品爆率.getText()).intValue());
                    ps.setInt(3, this.全局爆物序号);
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.刷新全局爆物(0);
            MapleMonsterInformationProvider.getInstance().clearDrops();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "爆率提示", 1);
        }
        catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]刷新出错：" + ex.getMessage());
        }
    }
    
    public void 商店列表(final int type) {
        switch (type) {
            case 0: {
                for (int i = ((DefaultTableModel)(DefaultTableModel)this.商人列表.getModel()).getRowCount() - 1; i >= 0; --i) {
                    ((DefaultTableModel)(DefaultTableModel)this.商人列表.getModel()).removeRow(i);
                }
                    try {
                        final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
                        PreparedStatement ps = null;
                        ResultSet rs = null;
                        ps = con.prepareStatement("SELECT * FROM shops");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            ((DefaultTableModel)this.商人列表.getModel()).insertRow(this.商人列表.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("shopid")), Integer.valueOf(rs.getInt("npcid")) });
                        }
                        rs.close();
                        ps.close();
                        con.close();
                    }
                    catch (SQLException ex) {
                        System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]刷新商店出错：" + ex.getMessage());
                    }
                
                break;
            }
            case 1: {
                for (int i = ((DefaultTableModel)(DefaultTableModel)this.商品列表.getModel()).getRowCount() - 1; i >= 0; --i) {
                    ((DefaultTableModel)(DefaultTableModel)this.商品列表.getModel()).removeRow(i);
                }
                int i = this.商人列表.getSelectedRow();
                if (i < 0) {
                    JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
                    return;
                }
                this.当前商店ID.setText("当前商店ID: 【" + this.商人列表.getValueAt(i, 0).toString() + "】");
                this.商店序号 = Integer.valueOf(this.商人列表.getValueAt(i, 0).toString()).intValue();
                try (final Connection con2 = (Connection)DBConPool.getInstance().getDataSource().getConnection();
                     final PreparedStatement ps2 = con2.prepareStatement("select * from shopitems where shopid = ?")) {
                    ResultSet rs2 = null;
                    ps2.setInt(1, Integer.valueOf(this.商人列表.getValueAt(i, 0).toString()).intValue());
                    rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        ((DefaultTableModel)this.商品列表.getModel()).insertRow(this.商品列表.getRowCount(), new Object[] { Integer.valueOf(rs2.getInt("shopitemid")), MapleItemInformationProvider.getInstance().getName(rs2.getInt("itemid")), Integer.valueOf(rs2.getInt("itemid")), Integer.valueOf(rs2.getInt("price")), Integer.valueOf(rs2.getInt("position")) });
                    }
                    rs2.close();
                    ps2.close();
                    con2.close();
                }
                catch (SQLException ex2) {
                    System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]刷新商店物品出错：" + ex2.getMessage());
                }
                break;
            }
        }
        JOptionPane.showMessageDialog(null, "处理完毕！", "商店提示", 1);
    }
    
    public void 调整商店物品(final int type) {
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO shopitems (shopid,itemid,price,position) VALUES( ?,?,?,?)");
                    ps.setInt(1, this.商店序号);
                    ps.setInt(2, Integer.valueOf(this.商店物品ID.getText()).intValue());
                    ps.setInt(3, Integer.valueOf(this.商店物品价格.getText()).intValue());
                    ps.setInt(4, Integer.valueOf(this.商品排列顺序.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM shopitems where shopitemid = ?");
                    ps.setInt(1, this.商店物品序号);
                    ps.execute();
                    ps.close();
                    break;
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE shopitems set itemid = ?,price = ? ,position = ? where shopitemid = ?");
                    ps.setInt(1, Integer.valueOf(this.商店物品ID.getText()).intValue());
                    ps.setInt(2, Integer.valueOf(this.商店物品价格.getText()).intValue());
                    ps.setInt(3, Integer.valueOf(this.商品排列顺序.getText()).intValue());
                    ps.setInt(4, this.商店物品序号);
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            for (int j = ((DefaultTableModel)(DefaultTableModel)this.商品列表.getModel()).getRowCount() - 1; j >= 0; --j) {
                ((DefaultTableModel)(DefaultTableModel)this.商品列表.getModel()).removeRow(j);
            }
            final Connection conn = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement pss = conn.prepareStatement("select * from shopitems where shopid = ?")) {
                ResultSet rs = null;
                pss.setInt(1, this.商店序号);
                rs = pss.executeQuery();
                while (rs.next()) {
                    ((DefaultTableModel)this.商品列表.getModel()).insertRow(this.商品列表.getRowCount(), new Object[] { Integer.valueOf(rs.getInt("shopitemid")), MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), Integer.valueOf(rs.getInt("itemid")), Integer.valueOf(rs.getInt("price")), Integer.valueOf(rs.getInt("position")) });
                }
                rs.close();
                pss.close();
                conn.close();
            }
            catch (SQLException ex) {
                System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]刷新商店物品出错：" + ex.getMessage());
            }
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "爆率提示", 1);
        }
        catch (SQLException ex2) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]刷新出错：" + ex2.getMessage());
        }
    }
}
