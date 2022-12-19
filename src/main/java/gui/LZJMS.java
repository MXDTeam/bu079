package gui;

import LzjSetting.Game;
import MXDJR.MXDJR;
import client.MapleCharacter;
import client.MapleJob;
import client.Skill;
import client.SkillFactory;
import client.inventory.IItem;
import constants.tzjc;
import database.DBConPool;
import handling.channel.ChannelServer;
import handling.channel.handler.DamageParse;
import handling.world.MapleParty;
import handling.world.World;
import handling.world.World.Broadcast;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import provider.*;
import scripting.NPCConversationManager;
import scripting.PortalScriptManager;
import scripting.ReactorScriptManager;
import server.*;
import server.Timer.EventTimer;
import server.life.MapleLifeFactory;
import server.life.MapleMonsterInformationProvider;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.Pair;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.ScheduledFuture;

public class LZJMS extends JFrame {
    private static LZJMS instance = new LZJMS();
    public static 玩家控制台 玩家控制台;
    public static 特殊控制台 特殊控制台;
    private int 账号ID;
    private int 角色ID;
    private int 怪物爆物序号;
    private int 全局爆物序号;
    private int 商店序号;
    private int 商店物品序号;
    private IItem 读取装备;
    private static MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
    private static long startRunTime = System.currentTimeMillis();
    private static String authCode = "";
    private static ScheduledFuture<?> ts = null;
    private int minutesLeft;
    private static JTextField BOSS出装备概率;
    private static JTextField GM固伤伤害;
    private static JToggleButton GM固伤开关;
    private static JTextField IP多开上限;
    private static JTextField MAC多开上限;
    private static JTextField MAC注册上限;
    private JButton NPC删除;
    private JButton NPC刷新;
    private static JToggleButton boss击杀记录;
    private JButton jButton1;
    private JButton jButton114;
    private JButton jButton115;
    private JButton jButton116;
    private JButton jButton117;
    private JButton jButton118;
    private JButton jButton16;
    private JButton jButton17;
    private JButton jButton18;
    private JButton jButton19;
    private JButton jButton49;
    private JButton jButton50;
    private JButton jButton52;
    private JButton jButton53;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel100;
    private JLabel jLabel101;
    private JLabel jLabel102;
    private JLabel jLabel103;
    private JLabel jLabel104;
    private JLabel jLabel105;
    private JLabel jLabel106;
    private JLabel jLabel107;
    private JLabel jLabel108;
    private JLabel jLabel109;
    private JLabel jLabel11;
    private JLabel jLabel110;
    private JLabel jLabel111;
    private JLabel jLabel112;
    private JLabel jLabel113;
    private JLabel jLabel114;
    private JLabel jLabel115;
    private JLabel jLabel116;
    private JLabel jLabel117;
    private JLabel jLabel118;
    private JLabel jLabel119;
    private JLabel jLabel12;
    private JLabel jLabel120;
    private JLabel jLabel121;
    private JLabel jLabel122;
    private JLabel jLabel123;
    private JLabel jLabel124;
    private JLabel jLabel125;
    private JLabel jLabel126;
    private JLabel jLabel127;
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
    private JLabel jLabel141;
    private JLabel jLabel142;
    private JLabel jLabel143;
    private JLabel jLabel144;
    private JLabel jLabel145;
    private JLabel jLabel146;
    private JLabel jLabel147;
    private JLabel jLabel148;
    private JLabel jLabel149;
    private JLabel jLabel15;
    private JLabel jLabel150;
    private JLabel jLabel151;
    private JLabel jLabel152;
    private JLabel jLabel153;
    private JLabel jLabel154;
    private JLabel jLabel155;
    private JLabel jLabel156;
    private JLabel jLabel157;
    private JLabel jLabel158;
    private JLabel jLabel159;
    private JLabel jLabel16;
    private JLabel jLabel160;
    private JLabel jLabel161;
    private JLabel jLabel162;
    private JLabel jLabel163;
    private JLabel jLabel164;
    private JLabel jLabel165;
    private JLabel jLabel166;
    private JLabel jLabel167;
    private JLabel jLabel168;
    private JLabel jLabel169;
    private JLabel jLabel17;
    private JLabel jLabel170;
    private JLabel jLabel171;
    private JLabel jLabel172;
    private JLabel jLabel173;
    private JLabel jLabel174;
    private JLabel jLabel175;
    private JLabel jLabel176;
    private JLabel jLabel177;
    private JLabel jLabel178;
    private JLabel jLabel179;
    private JLabel jLabel18;
    private JLabel jLabel180;
    private JLabel jLabel181;
    private JLabel jLabel182;
    private JLabel jLabel183;
    private JLabel jLabel184;
    private JLabel jLabel185;
    private JLabel jLabel186;
    private JLabel jLabel187;
    private JLabel jLabel188;
    private JLabel jLabel189;
    private JLabel jLabel19;
    private JLabel jLabel190;
    private JLabel jLabel191;
    private JLabel jLabel192;
    private JLabel jLabel193;
    private JLabel jLabel194;
    private JLabel jLabel195;
    private JLabel jLabel196;
    private JLabel jLabel197;
    private JLabel jLabel198;
    private JLabel jLabel199;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel200;
    private JLabel jLabel201;
    private JLabel jLabel202;
    private JLabel jLabel203;
    private JLabel jLabel204;
    private JLabel jLabel205;
    private JLabel jLabel206;
    private JLabel jLabel207;
    private JLabel jLabel208;
    private JLabel jLabel209;
    private JLabel jLabel21;
    private JLabel jLabel210;
    private JLabel jLabel211;
    private JLabel jLabel212;
    private JLabel jLabel213;
    private JLabel jLabel214;
    private JLabel jLabel215;
    private JLabel jLabel216;
    private JLabel jLabel217;
    private JLabel jLabel218;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel24;
    private JLabel jLabel25;
    private JLabel jLabel26;
    private JLabel jLabel27;
    private JLabel jLabel28;
    private JLabel jLabel29;
    private JLabel jLabel3;
    private JLabel jLabel30;
    private JLabel jLabel31;
    private JLabel jLabel32;
    private JLabel jLabel33;
    private JLabel jLabel34;
    private JLabel jLabel35;
    private JLabel jLabel36;
    private JLabel jLabel37;
    private JLabel jLabel38;
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
    private JLabel jLabel58;
    private JLabel jLabel59;
    private JLabel jLabel6;
    private JLabel jLabel60;
    private JLabel jLabel61;
    private JLabel jLabel62;
    private JLabel jLabel63;
    private JLabel jLabel64;
    private JLabel jLabel65;
    private JLabel jLabel66;
    private JLabel jLabel67;
    private JLabel jLabel68;
    private JLabel jLabel69;
    private JLabel jLabel7;
    private JLabel jLabel70;
    private JLabel jLabel71;
    private JLabel jLabel72;
    private JLabel jLabel73;
    private JLabel jLabel74;
    private JLabel jLabel75;
    private JLabel jLabel76;
    private JLabel jLabel77;
    private JLabel jLabel78;
    private JLabel jLabel79;
    private JLabel jLabel8;
    private JLabel jLabel80;
    private JLabel jLabel81;
    private JLabel jLabel82;
    private JLabel jLabel83;
    private JLabel jLabel84;
    private JLabel jLabel85;
    private JLabel jLabel86;
    private JLabel jLabel87;
    private JLabel jLabel88;
    private JLabel jLabel89;
    private JLabel jLabel9;
    private JLabel jLabel90;
    private JLabel jLabel91;
    private JLabel jLabel92;
    private JLabel jLabel93;
    private JLabel jLabel94;
    private JLabel jLabel95;
    private JLabel jLabel96;
    private JLabel jLabel97;
    private JLabel jLabel98;
    private JLabel jLabel99;
    private JPanel jPanel1;
    private JPanel jPanel10;
    private JPanel jPanel100;
    private JPanel jPanel101;
    private JPanel jPanel11;
    private static JPanel jPanel12;
    private JPanel jPanel13;
    private JPanel jPanel14;
    private JPanel jPanel15;
    private JPanel jPanel16;
    private JPanel jPanel17;
    private JPanel jPanel18;
    private JPanel jPanel19;
    private JPanel jPanel2;
    private JPanel jPanel20;
    private JPanel jPanel21;
    private JPanel jPanel22;
    private JPanel jPanel23;
    private JPanel jPanel24;
    private JPanel jPanel25;
    private JPanel jPanel26;
    private JPanel jPanel27;
    private JPanel jPanel28;
    private JPanel jPanel29;
    private JPanel jPanel3;
    private JPanel jPanel30;
    private JPanel jPanel31;
    private JPanel jPanel32;
    private JPanel jPanel33;
    private JPanel jPanel34;
    private JPanel jPanel35;
    private JPanel jPanel36;
    private JPanel jPanel37;
    private JPanel jPanel38;
    private JPanel jPanel39;
    private JPanel jPanel4;
    private JPanel jPanel40;
    private JPanel jPanel41;
    private JPanel jPanel42;
    private JPanel jPanel43;
    static JPanel jPanel44;
    private JPanel jPanel45;
    private JPanel jPanel46;
    private JPanel jPanel47;
    private JPanel jPanel48;
    private JPanel jPanel49;
    private JPanel jPanel5;
    private JPanel jPanel50;
    private JPanel jPanel51;
    private JPanel jPanel52;
    private JPanel jPanel53;
    private JPanel jPanel54;
    private JPanel jPanel55;
    private JPanel jPanel56;
    private JPanel jPanel57;
    private JPanel jPanel58;
    private JPanel jPanel59;
    private JPanel jPanel6;
    private JPanel jPanel60;
    private JPanel jPanel61;
    private JPanel jPanel62;
    private JPanel jPanel63;
    private JPanel jPanel64;
    private JPanel jPanel65;
    private JPanel jPanel66;
    private JPanel jPanel67;
    private JPanel jPanel68;
    private JPanel jPanel69;
    private JPanel jPanel7;
    private JPanel jPanel70;
    private JPanel jPanel71;
    private JPanel jPanel72;
    private JPanel jPanel73;
    private JPanel jPanel74;
    private JPanel jPanel75;
    private JPanel jPanel76;
    private JPanel jPanel77;
    private JPanel jPanel78;
    private JPanel jPanel79;
    private JPanel jPanel8;
    private JPanel jPanel80;
    private JPanel jPanel81;
    private JPanel jPanel82;
    private JPanel jPanel83;
    private JPanel jPanel84;
    private JPanel jPanel85;
    private JPanel jPanel86;
    private JPanel jPanel87;
    private JPanel jPanel88;
    private JPanel jPanel89;
    private JPanel jPanel9;
    private JPanel jPanel90;
    private JPanel jPanel91;
    private JPanel jPanel92;
    private JPanel jPanel93;
    private JPanel jPanel94;
    private JPanel jPanel95;
    private JPanel jPanel96;
    private JPanel jPanel97;
    private JPanel jPanel98;
    private JPanel jPanel99;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane10;
    private JScrollPane jScrollPane105;
    private JScrollPane jScrollPane106;
    private JScrollPane jScrollPane107;
    private JScrollPane jScrollPane108;
    private JScrollPane jScrollPane11;
    private JScrollPane jScrollPane12;
    private JScrollPane jScrollPane13;
    private JScrollPane jScrollPane14;
    private JScrollPane jScrollPane15;
    private JScrollPane jScrollPane16;
    private JScrollPane jScrollPane17;
    private JScrollPane jScrollPane18;
    private JScrollPane jScrollPane19;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane20;
    private JScrollPane jScrollPane21;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JScrollPane jScrollPane6;
    private JScrollPane jScrollPane7;
    private JScrollPane jScrollPane8;
    private JScrollPane jScrollPane9;
    private JTextArea jTextArea1;
    private JTextField jTextField1;
    public static JTextArea outSee;
    private static JToggleButton 上线喇叭;
    private static JToggleButton 不参与叠加开关;
    private static JTextField 不参与叠加排序;
    private static JTable 不参与叠加道具;
    private JButton 不参与叠加道具删除;
    private JButton 不参与叠加道具删除1;
    private JButton 不参与叠加道具删除2;
    private JButton 不参与叠加道具删除3;
    private JButton 不参与叠加道具增加;
    private JButton 不参与叠加道具增加1;
    private JButton 不参与叠加道具增加2;
    private JButton 不参与叠加道具增加3;
    private static JTextField 不参与道具叠加源代码;
    private static JToggleButton 世界等级开关;
    private static JTextField 世界等级范围;
    private static JToggleButton 丢出物品;
    private static JToggleButton 丢出金币;
    private static JToggleButton 丢失伤害;
    private static JToggleButton 个数检测;
    public static JTabbedPane 主界面菜单;
    private static JProgressBar 人数;
    private static JTextField 伤害上限;
    private JLabel 伤害修正1;
    private static JToggleButton 伤害修正开关;
    private static JToggleButton 伤害检测;
    private static JToggleButton 伤害突破开关;
    public static JButton 保存数据;
    private JButton 保存玩家;
    public static JButton 保存雇佣;
    private JButton 保存雇佣并关闭;
    private JButton 一键清空数据库;
    private JButton 修改地图;
    private JButton 修改地图1;
    private JButton 修改地图2;
    private JButton 修改地图3;
    private JButton 修改技能;
    private static JTextField 修正组队经验加成;
    private static JTextField 修正队员分配经验;
    private JComboBox<String> 倍率活动类型;
    private JTextField 倍率设置;
    private JTextField 充值玩家名字;
    private JTextField 充值角色ID;
    private JTable 充值赞助列表;
    private JTable 充值赞助列表1;
    private static JTextField 克隆基础伤害;
    private static JTextField 全局血量倍率;
    private static JTextField 全局血量等级;
    private static JToggleButton 全屏检测;
    private static JToggleButton 全服通告;
    private JTable 公告信息;
    private static JTextField 公告间隔时间;
    private static JButton 关闭时间显示;
    private static JTextField 关闭时间输入;
    private static JProgressBar 内存;
    private static JToggleButton 冒险家开关;
    private static JTextField 出装备概率;
    private static JToggleButton 击杀boss打开npc;
    private JButton 删除地图;
    private JButton 刷新地图;
    private JButton 刷新职业列表;
    private Checkbox 剩余余额;
    private static JToggleButton 升级提示;
    private static JToggleButton 升级群消息通知;
    private static JTextField 原套装代码;
    private static JTextField 原始组队经验加成;
    private static JTextField 原道具经验加成代码;
    private static JToggleButton 双倍频道开关;
    private JTextField 发放个人玩家名字;
    private JTextField 发放充值数量;
    private JButton 发放其他内容;
    private JTextField 发放其他数量;
    private JTextField 发放其他玩家;
    private JComboBox<String> 发放其他类型;
    private JComboBox<String> 发放其他范围;
    private JButton 发放氪金充值;
    private JButton 发放道具;
    private JTextField 发放道具代码;
    private JComboBox<String> 发放道具发放范围;
    private JTextField 发放道具数量;
    private static JTextField 叠加上线;
    private static JToggleButton 叠加开关;
    private Checkbox 可用点券;
    private static JToggleButton 同IP多开;
    private static JToggleButton 同MAC多开;
    public static JButton 启动服务端;
    private static JToggleButton 吸怪检测;
    private static JToggleButton 吸物检测;
    private static JToggleButton 喷火龙;
    private static JTextField 地图刷新频率;
    private static JToggleButton 地图名称;
    private static JToggleButton 坐骑不饥饿开关;
    private static JToggleButton 坐骑恢复开关;
    private static JTextField 坐骑恢复道具;
    private static JTextField 坐骑恢复频率;
    private JButton 增加地图;
    private static JTextField 多倍地图倍率;
    private static JTextField 多倍地图原地图代码;
    private static JTextField 多倍地图排序;
    private static JTextField 多倍地图现地图代码;
    private static JTable 多倍怪物地图列表;
    private static JToggleButton 大海龟;
    private static JToggleButton 大灰狼;
    private static JTextField 套装个数;
    private static JTable 套装伤害加成表;
    private static JTextField 套装加成比例;
    private static JTextField 套装名字;
    private JTextField 套装名字输入;
    private static JToggleButton 套装属性加成开关;
    private JTextField 套装排序输入;
    private JButton 套装查询;
    private JButton 套装查询1;
    private static JTextField 套装编码;
    private static JTextField 套装道具代码;
    private JButton 套装道具修改;
    private JButton 套装道具删除;
    private JButton 套装道具增加;
    private static JTextField 子弹代码;
    private JButton 子弹删除;
    private JButton 子弹增加;
    private static JTable 子弹扩充列表;
    private static JToggleButton 子弹扩充开关;
    private static JTextField 子弹排序;
    private JTabbedPane 宠吸功能面板;
    private static JTextField 宠吸道具;
    private JButton 宠物不参与地图初始;
    private static JToggleButton 宠物不饥饿开关;
    private static JTextField 宠物吸取不参与地图代码;
    private static JTextField 宠物吸取不参与地图代码1;
    private static JTable 宠物吸取不参与地图列表;
    private JButton 宠物吸取物品初始化;
    private static JTextField 宠物吸取道具不参与排序;
    private JButton 宠物吸取金币初始;
    private static JTextField 宠物吸物代码;
    private static JTextField 宠物吸物代码1;
    private static JTable 宠物吸物列表;
    private static JTextField 宠物吸物排序;
    private static JTextField 宠物吸金代码;
    private static JTextField 宠物吸金代码1;
    private static JTable 宠物吸金列表;
    private static JTextField 宠物吸金排序;
    private static JToggleButton 宠物自动吃药开关;
    private static JToggleButton 封停IP;
    private static JToggleButton 封停MAC;
    private static JToggleButton 封停账号;
    private static JTable 封禁IP地址;
    private static JTable 封禁MAC地址;
    private static JToggleButton 小白兔;
    private static JToggleButton 小青蛇;
    private JButton 开启倍率活动;
    private JLabel 当前职业ID;
    private JLabel 当前职业名称;
    private JTextField 怪物个数;
    private static JToggleButton 怪物减伤开关;
    private static JTextField 怪物刷新频率设定;
    private static JToggleButton 怪物地图多倍怪物开关;
    private static JToggleButton 怪物多倍地图开关;
    private JButton 怪物经验限制修改;
    private JButton 怪物经验限制删除;
    private JButton 怪物经验限制刷新;
    private JButton 怪物经验限制增加;
    private static JTable 怪物经验限制表;
    private static JToggleButton 怪物血量显示开关;
    private JTable 怪物血量表;
    private static JToggleButton 成就上卷加七记录开关;
    private static JToggleButton 成就上卷加三记录开关;
    private static JToggleButton 成就还原上卷记录开关;
    private static JToggleButton 战力修正;
    private static JToggleButton 战神开关;
    private static JToggleButton 所有显示开关;
    private JTextField 技能代码;
    private JLabel 技能名;
    private JTextField 技能名称;
    private JTextField 技能段数;
    private JTextField 持续时间分;
    private JTextField 持续时间时;
    private static JToggleButton 攻速检测;
    static JTextField 数据库IP地址;
    static JTextField 数据库名称;
    static JTextField 数据库密码;
    static JTextField 数据库用户名;
    static JTextField 数据库端口;
    private static JToggleButton 无限BUFF;
    private static JProgressBar 时长;
    private static JToggleButton 星精灵;
    private static JTextField 服务器双倍频道;
    private static JTextField 服务器名字;
    private static JTextField 服务器商城端口;
    private static JTextField 服务器掉落倍率;
    private static JTextField 服务器登录端口;
    private static JTextField 服务器经验倍率;
    private static JTextField 服务器金币倍率;
    private static JTextField 服务器频道数量;
    private static JTextField 服务器频道端口;
    private static JToggleButton 机器人开关;
    private static JToggleButton 欢迎弹窗;
    private static JToggleButton 段数检测;
    private Checkbox 每日充值;
    private JButton 氪金ID查询;
    private JTextField 氪金ID输入;
    private JButton 氪金名字查询;
    private JTextField 氪金名字输入;
    private JButton 氪金机器码查询;
    private JTextField 氪金机器码输入;
    private static JToggleButton 泡点开关;
    private static JToggleButton 泡点等级开关;
    private JButton 泡点配置修改;
    private static JTable 泡点配置表;
    private JButton 泡点配置说明;
    public static JButton 清空日志;
    private static JToggleButton 游戏仓库;
    private static JToggleButton 游戏喇叭;
    private static JToggleButton 漂漂猪;
    private static JToggleButton 火野猪;
    private static JTextField 点券比例;
    private static JToggleButton 爆物上线开关;
    private static JTextField 爆物上线数量;
    private static JToggleButton 特殊全宠物吸物开关;
    private static JToggleButton 特殊全宠物吸金开关;
    private static JToggleButton 特殊宠物吸取开关;
    private static JToggleButton 特殊宠物吸物开关;
    private static JToggleButton 特殊宠物吸物无法使用地图开关;
    private static JToggleButton 特殊宠物吸金开关;
    private JButton 特殊组队删除;
    private JButton 特殊组队增加;
    private static JToggleButton 特殊组队经验加成;
    private static JTable 特殊组队经验加成表;
    private static JTextField 特殊组队经验排序;
    private static JTextField 特殊组队经验职业;
    private static JToggleButton 玩家交易;
    private static JToggleButton 玩家升级喇叭;
    private static JToggleButton 玩家指令;
    private static JToggleButton 玩家登录;
    private static JToggleButton 玩家聊天;
    private static JTextField 现套装代码;
    private JTextField 现有剩余积分;
    private JTextField 现有可用点券;
    private JTextField 现有每日充值;
    private JTextField 现有累计充值;
    private static JToggleButton 登录自动注册开关;
    private static JToggleButton 白雪人;
    private static JToggleButton 石头人;
    private static JTextField 砍爆率;
    private JButton 离线泡点修改;
    private static JToggleButton 离线泡点开关;
    private static JToggleButton 离线泡点等级开关;
    private JButton 离线泡点说明;
    private static JTable 离线泡点配置表;
    private static JToggleButton 离线给在线时间开关;
    private static JTextField 突破上线;
    private static JToggleButton 章鱼怪;
    private static JButton 第三控制台;
    private static JButton 第二控制台;
    private static JTextField 等级上限;
    private static JTextField 等级范围;
    private static JToggleButton 等级连升开关;
    private static JToggleButton 管理员加速;
    private static JToggleButton 管理员独占登录;
    private static JToggleButton 管理员隐身;
    private static JToggleButton 紫色猫;
    private Checkbox 累计充值;
    private static JToggleButton 红螃蟹;
    private static JTextField 经验加成比例;
    private static JTable 经验加成表;
    private JButton 经验加成配置修改1;
    private JButton 经验加成配置说明1;
    private static JToggleButton 绿水灵;
    private JTable 职业列表表单;
    private JTable 职业技能列表表单;
    private JButton 职业技能初始化;
    private static JToggleButton 胖企鹅;
    private static JTextField 自动刷钱道具;
    private static JTextField 自动吃药道具;
    private static JToggleButton 自定义箱子代码;
    private static JTable 自建NPC列表;
    private static JToggleButton 花蘑菇;
    private static JToggleButton 蓝蜗牛;
    private static JToggleButton 蘑菇仔;
    private static JToggleButton 表单卡破功开关;
    private JButton 装备加成伤害列表初始化;
    private static JToggleButton 装备卡破功开关;
    private static JToggleButton 记录登录信息;
    private JButton 读取技能;
    private static JTextField 账号角色上限;
    private static JToggleButton 越级带人开关;
    private static JTextField 越级带人道具;
    private static JToggleButton 越级带人道具开关;
    private static JToggleButton 越级打怪;
    private static JToggleButton 道具强行宠吸开关;
    private static JTextField 道具经验加成代码;
    private JButton 道具经验加成修改删除;
    private JButton 道具经验加成修改按钮;
    private JButton 道具经验加成增加;
    private static JTextField 道具经验加成比例;
    private static JToggleButton 道具经验开关;
    private static JTextField 重置技能总范围;
    private static JToggleButton 重置技能范围开关;
    private JButton 重载BOSS事件;
    private JButton 重载传送门;
    public static JButton 重载全部;
    private JButton 重载公告;
    private JButton 重载副本;
    private JButton 重载反应堆;
    private JButton 重载商城;
    private JButton 重载商店;
    private JButton 重载套装加成;
    private JButton 重载套装加成1;
    private JButton 重载活动事件;
    private JButton 重载爆率;
    private JButton 重载自定义事件;
    private JButton 野外BOSS内容修改;
    private JButton 野外BOSS内容删除;
    private JButton 野外BOSS内容刷新;
    private JButton 野外BOSS内容增加;
    private static JTable 野外BOSS刷新时间;
    private static JTextField 野外BOSS刷新时间boss代码;
    private static JTextField 野外BOSS刷新时间分;
    private static JTextField 野外BOSS刷新时间原地图;
    private static JTextField 野外BOSS刷新时间横坐标;
    private static JTextField 野外BOSS刷新时间现地图;
    private static JTextField 野外BOSS刷新时间纵坐标;
    private static JTextField 野外BOSS刷新时间说明;
    private static JToggleButton 野外boss击杀广播;
    private static JToggleButton 金币全局砍数量;
    private static JTextField 金币砍全局倍率;
    private static JToggleButton 金币重置;
    private static JToggleButton 金锤子使用开关;
    private static JTextField 金锤子使用概率;
    private static JToggleButton 阶段经验开关;
    private static JTable 阶段经验配置表;
    private static JToggleButton 雇佣商人;
    private static JTextField 雇佣持续时间;
    private static JToggleButton 雇佣经验加成开关;
    private static JToggleButton 雇佣自动回收;
    private static JToggleButton 青鳄鱼;
    private static JToggleButton 顽皮猴;
    private static JToggleButton 骑士团开关;
    private static Thread t = null;

    public static final LZJMS getInstance() {
        return instance;
    }

    public static void main(final String[] args) throws Exception {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        try {
            BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.generalNoTranslucencyShadow;
            UIManager.put("RootPane.setupButtonVisible", Boolean.valueOf(false));
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "]" + e);
        }
        EventQueue.invokeLater((Runnable) new Runnable() {
            @Override
            public void run() {
                new LZJMS().setVisible(true);
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][========================================]");
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][每一段时光，都有幸福的理由。 ]");
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][快乐不是因为得到的多而是因为计较的少。]");
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][付出不一定有收获，努力了就值得了。 ]");
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][时间往往不是答案，但答案都在时间里。 ]");
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]控制台已启动，点击右下角[启动服务端]运行。");
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][========================================]");
            }
        });
    }

    public LZJMS() {
        this.账号ID = -1;
        this.角色ID = -1;
        this.怪物爆物序号 = -1;
        this.全局爆物序号 = -1;
        this.商店序号 = -1;
        this.商店物品序号 = -1;
        this.读取装备 = null;
        this.minutesLeft = 0;
        this.setTitle("【" + Game.服务端名称 + "控制台】 - 新春贺岁版，本软件均采集于互联网,仅供学习交流使用，请勿用于商业用途和非法用途，如作他用所造成的一切后果和法律责任一概与我们无关,如侵犯到权益,我们将删除处理.");
        this.initComponents();
        final LzjStream mps = new LzjStream((OutputStream) System.out, LZJMS.outSee);
        System.setOut((PrintStream) mps);
        System.setErr((PrintStream) mps);
        final ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("image/冒险岛.png"));
        this.setIconImage(icon.getImage());
        this.初始化界面配置();
    }

    private void initComponents() {
        LZJMS.主界面菜单 = new JTabbedPane();
        this.jScrollPane1 = new JScrollPane();
        LZJMS.outSee = new JTextArea();
        this.jPanel2 = new JPanel();
        this.宠吸功能面板 = new JTabbedPane();
        LZJMS.jPanel12 = new JPanel();
        LZJMS.jPanel44 = new JPanel();
        this.jLabel16 = new JLabel();
        this.jLabel17 = new JLabel();
        LZJMS.数据库IP地址 = new JTextField();
        LZJMS.数据库端口 = new JTextField();
        this.jLabel18 = new JLabel();
        LZJMS.数据库用户名 = new JTextField();
        this.jLabel19 = new JLabel();
        LZJMS.数据库名称 = new JTextField();
        this.jLabel34 = new JLabel();
        LZJMS.数据库密码 = new JTextField();
        this.jPanel48 = new JPanel();
        LZJMS.登录自动注册开关 = new JToggleButton();
        this.jLabel24 = new JLabel();
        LZJMS.MAC注册上限 = new JTextField();
        this.jLabel32 = new JLabel();
        LZJMS.账号角色上限 = new JTextField();
        this.jLabel33 = new JLabel();
        this.jPanel54 = new JPanel();
        this.jLabel65 = new JLabel();
        this.jLabel66 = new JLabel();
        LZJMS.服务器金币倍率 = new JTextField();
        LZJMS.服务器掉落倍率 = new JTextField();
        this.jLabel67 = new JLabel();
        LZJMS.服务器经验倍率 = new JTextField();
        this.jLabel69 = new JLabel();
        LZJMS.服务器双倍频道 = new JTextField();
        this.jLabel68 = new JLabel();
        LZJMS.双倍频道开关 = new JToggleButton();
        this.jPanel53 = new JPanel();
        this.jLabel61 = new JLabel();
        this.jLabel62 = new JLabel();
        LZJMS.服务器商城端口 = new JTextField();
        LZJMS.服务器频道端口 = new JTextField();
        this.jLabel63 = new JLabel();
        LZJMS.服务器频道数量 = new JTextField();
        this.jLabel64 = new JLabel();
        LZJMS.服务器登录端口 = new JTextField();
        this.jLabel70 = new JLabel();
        LZJMS.服务器名字 = new JTextField();
        this.jPanel49 = new JPanel();
        LZJMS.冒险家开关 = new JToggleButton();
        this.jLabel35 = new JLabel();
        LZJMS.战神开关 = new JToggleButton();
        this.jLabel36 = new JLabel();
        LZJMS.骑士团开关 = new JToggleButton();
        this.jLabel37 = new JLabel();
        this.jLabel38 = new JLabel();
        LZJMS.等级上限 = new JTextField();
        this.jPanel64 = new JPanel();
        this.jLabel73 = new JLabel();
        this.jLabel74 = new JLabel();
        LZJMS.MAC多开上限 = new JTextField();
        LZJMS.IP多开上限 = new JTextField();
        this.jLabel77 = new JLabel();
        LZJMS.同IP多开 = new JToggleButton();
        this.jLabel78 = new JLabel();
        LZJMS.同MAC多开 = new JToggleButton();
        this.jPanel73 = new JPanel();
        this.jLabel109 = new JLabel();
        LZJMS.雇佣持续时间 = new JTextField();
        this.jLabel111 = new JLabel();
        LZJMS.雇佣自动回收 = new JToggleButton();
        this.jLabel4 = new JLabel();
        LZJMS.雇佣经验加成开关 = new JToggleButton();
        this.jLabel5 = new JLabel();
        LZJMS.经验加成比例 = new JTextField();
        this.jPanel6 = new JPanel();
        this.jLabel6 = new JLabel();
        LZJMS.机器人开关 = new JToggleButton();
        this.jLabel7 = new JLabel();
        LZJMS.升级群消息通知 = new JToggleButton();
        this.jLabel9 = new JLabel();
        this.jPanel7 = new JPanel();
        this.jLabel8 = new JLabel();
        LZJMS.爆物上线开关 = new JToggleButton();
        this.jLabel10 = new JLabel();
        LZJMS.爆物上线数量 = new JTextField();
        this.jPanel8 = new JPanel();
        this.jLabel11 = new JLabel();
        LZJMS.自定义箱子代码 = new JToggleButton();
        this.jLabel12 = new JLabel();
        LZJMS.上线喇叭 = new JToggleButton();
        this.jLabel14 = new JLabel();
        LZJMS.玩家升级喇叭 = new JToggleButton();
        this.jLabel211 = new JLabel();
        LZJMS.无限BUFF = new JToggleButton();
        this.jPanel10 = new JPanel();
        this.jLabel13 = new JLabel();
        LZJMS.所有显示开关 = new JToggleButton();
        this.jLabel15 = new JLabel();
        LZJMS.伤害突破开关 = new JToggleButton();
        this.jLabel26 = new JLabel();
        LZJMS.突破上线 = new JTextField();
        this.jLabel27 = new JLabel();
        LZJMS.装备卡破功开关 = new JToggleButton();
        this.jLabel210 = new JLabel();
        LZJMS.表单卡破功开关 = new JToggleButton();
        this.jPanel16 = new JPanel();
        this.jLabel21 = new JLabel();
        LZJMS.怪物血量显示开关 = new JToggleButton();
        this.jLabel22 = new JLabel();
        LZJMS.野外boss击杀广播 = new JToggleButton();
        this.jLabel25 = new JLabel();
        LZJMS.boss击杀记录 = new JToggleButton();
        LZJMS.击杀boss打开npc = new JToggleButton();
        this.jLabel39 = new JLabel();
        this.jPanel4 = new JPanel();
        this.jLabel28 = new JLabel();
        this.jLabel29 = new JLabel();
        LZJMS.砍爆率 = new JTextField();
        LZJMS.出装备概率 = new JTextField();
        this.jLabel31 = new JLabel();
        LZJMS.战力修正 = new JToggleButton();
        LZJMS.BOSS出装备概率 = new JTextField();
        this.jLabel172 = new JLabel();
        this.jLabel173 = new JLabel();
        this.jLabel174 = new JLabel();
        this.jPanel17 = new JPanel();
        this.jLabel40 = new JLabel();
        LZJMS.宠物自动吃药开关 = new JToggleButton();
        this.jLabel41 = new JLabel();
        LZJMS.自动吃药道具 = new JTextField();
        this.jLabel89 = new JLabel();
        LZJMS.宠物不饥饿开关 = new JToggleButton();
        this.jPanel18 = new JPanel();
        this.jLabel45 = new JLabel();
        LZJMS.等级连升开关 = new JToggleButton();
        this.jLabel46 = new JLabel();
        LZJMS.等级范围 = new JTextField();
        this.jLabel47 = new JLabel();
        LZJMS.世界等级开关 = new JToggleButton();
        this.jLabel48 = new JLabel();
        LZJMS.世界等级范围 = new JTextField();
        this.jPanel20 = new JPanel();
        this.jLabel49 = new JLabel();
        LZJMS.成就还原上卷记录开关 = new JToggleButton();
        this.jLabel50 = new JLabel();
        LZJMS.成就上卷加三记录开关 = new JToggleButton();
        this.jLabel51 = new JLabel();
        LZJMS.成就上卷加七记录开关 = new JToggleButton();
        this.jPanel37 = new JPanel();
        this.jLabel83 = new JLabel();
        LZJMS.金锤子使用开关 = new JToggleButton();
        this.jLabel85 = new JLabel();
        LZJMS.金锤子使用概率 = new JTextField();
        this.jPanel52 = new JPanel();
        this.jLabel163 = new JLabel();
        LZJMS.GM固伤开关 = new JToggleButton();
        this.jLabel164 = new JLabel();
        LZJMS.GM固伤伤害 = new JTextField();
        this.jPanel55 = new JPanel();
        this.jLabel30 = new JLabel();
        LZJMS.金币重置 = new JToggleButton();
        this.jLabel146 = new JLabel();
        LZJMS.金币全局砍数量 = new JToggleButton();
        this.jLabel147 = new JLabel();
        LZJMS.金币砍全局倍率 = new JTextField();
        this.jPanel94 = new JPanel();
        this.jLabel200 = new JLabel();
        LZJMS.越级带人开关 = new JToggleButton();
        this.jLabel201 = new JLabel();
        LZJMS.越级带人道具开关 = new JToggleButton();
        this.jLabel202 = new JLabel();
        LZJMS.越级带人道具 = new JTextField();
        this.jPanel95 = new JPanel();
        this.jLabel42 = new JLabel();
        LZJMS.坐骑恢复开关 = new JToggleButton();
        this.jLabel43 = new JLabel();
        LZJMS.坐骑恢复频率 = new JTextField();
        this.jLabel44 = new JLabel();
        LZJMS.坐骑恢复道具 = new JTextField();
        this.jLabel206 = new JLabel();
        LZJMS.坐骑不饥饿开关 = new JToggleButton();
        this.jPanel3 = new JPanel();
        this.jPanel76 = new JPanel();
        this.jScrollPane5 = new JScrollPane();
        LZJMS.泡点配置表 = new JTable();
        this.泡点配置说明 = new JButton();
        this.泡点配置修改 = new JButton();
        this.jPanel77 = new JPanel();
        this.jScrollPane6 = new JScrollPane();
        LZJMS.离线泡点配置表 = new JTable();
        this.离线泡点说明 = new JButton();
        this.离线泡点修改 = new JButton();
        this.jPanel19 = new JPanel();
        this.jLabel52 = new JLabel();
        LZJMS.泡点开关 = new JToggleButton();
        this.jLabel53 = new JLabel();
        LZJMS.泡点等级开关 = new JToggleButton();
        this.jPanel21 = new JPanel();
        this.jLabel54 = new JLabel();
        LZJMS.离线泡点开关 = new JToggleButton();
        this.jLabel55 = new JLabel();
        LZJMS.离线泡点等级开关 = new JToggleButton();
        this.jLabel56 = new JLabel();
        LZJMS.离线给在线时间开关 = new JToggleButton();
        this.jPanel75 = new JPanel();
        this.jScrollPane7 = new JScrollPane();
        LZJMS.阶段经验配置表 = new JTable();
        this.经验加成配置说明1 = new JButton();
        this.经验加成配置修改1 = new JButton();
        this.jPanel25 = new JPanel();
        this.jLabel57 = new JLabel();
        LZJMS.阶段经验开关 = new JToggleButton();
        this.jPanel23 = new JPanel();
        this.jPanel83 = new JPanel();
        this.jScrollPane8 = new JScrollPane();
        LZJMS.不参与叠加道具 = new JTable();
        this.不参与叠加道具增加 = new JButton();
        this.不参与叠加道具删除 = new JButton();
        this.jLabel119 = new JLabel();
        this.jLabel120 = new JLabel();
        LZJMS.不参与叠加排序 = new JTextField();
        LZJMS.不参与道具叠加源代码 = new JTextField();
        this.jPanel26 = new JPanel();
        this.jLabel59 = new JLabel();
        LZJMS.叠加开关 = new JToggleButton();
        this.jLabel60 = new JLabel();
        LZJMS.叠加上线 = new JTextField();
        this.jLabel91 = new JLabel();
        LZJMS.不参与叠加开关 = new JToggleButton();
        this.jPanel63 = new JPanel();
        this.jScrollPane3 = new JScrollPane();
        LZJMS.经验加成表 = new JTable();
        this.道具经验加成增加 = new JButton();
        this.道具经验加成修改按钮 = new JButton();
        this.道具经验加成修改删除 = new JButton();
        this.jLabel108 = new JLabel();
        this.jLabel110 = new JLabel();
        this.jLabel114 = new JLabel();
        LZJMS.原道具经验加成代码 = new JTextField();
        LZJMS.道具经验加成代码 = new JTextField();
        LZJMS.道具经验加成比例 = new JTextField();
        this.jPanel22 = new JPanel();
        this.jLabel58 = new JLabel();
        LZJMS.道具经验开关 = new JToggleButton();
        this.jPanel84 = new JPanel();
        this.jScrollPane9 = new JScrollPane();
        LZJMS.子弹扩充列表 = new JTable();
        this.子弹增加 = new JButton();
        this.子弹删除 = new JButton();
        this.jLabel121 = new JLabel();
        LZJMS.子弹代码 = new JTextField();
        LZJMS.子弹排序 = new JTextField();
        this.jLabel122 = new JLabel();
        this.jPanel27 = new JPanel();
        this.jLabel125 = new JLabel();
        LZJMS.子弹扩充开关 = new JToggleButton();
        this.jPanel85 = new JPanel();
        this.jScrollPane10 = new JScrollPane();
        LZJMS.特殊组队经验加成表 = new JTable();
        this.特殊组队增加 = new JButton();
        this.特殊组队删除 = new JButton();
        this.jLabel123 = new JLabel();
        LZJMS.特殊组队经验排序 = new JTextField();
        LZJMS.特殊组队经验职业 = new JTextField();
        this.jLabel124 = new JLabel();
        this.jPanel28 = new JPanel();
        this.jLabel126 = new JLabel();
        LZJMS.特殊组队经验加成 = new JToggleButton();
        this.jLabel127 = new JLabel();
        this.jLabel128 = new JLabel();
        LZJMS.原始组队经验加成 = new JTextField();
        LZJMS.修正组队经验加成 = new JTextField();
        this.jLabel209 = new JLabel();
        LZJMS.修正队员分配经验 = new JTextField();
        this.jPanel13 = new JPanel();
        this.jPanel65 = new JPanel();
        LZJMS.玩家登录 = new JToggleButton();
        this.jLabel71 = new JLabel();
        LZJMS.管理员独占登录 = new JToggleButton();
        this.jLabel72 = new JLabel();
        LZJMS.记录登录信息 = new JToggleButton();
        this.jLabel75 = new JLabel();
        this.jLabel76 = new JLabel();
        LZJMS.欢迎弹窗 = new JToggleButton();
        this.jPanel66 = new JPanel();
        LZJMS.玩家交易 = new JToggleButton();
        this.jLabel79 = new JLabel();
        LZJMS.玩家聊天 = new JToggleButton();
        this.jLabel80 = new JLabel();
        LZJMS.丢出金币 = new JToggleButton();
        this.jLabel81 = new JLabel();
        this.jLabel82 = new JLabel();
        LZJMS.丢出物品 = new JToggleButton();
        this.jPanel67 = new JPanel();
        LZJMS.升级提示 = new JToggleButton();
        this.jLabel84 = new JLabel();
        this.jLabel86 = new JLabel();
        LZJMS.游戏喇叭 = new JToggleButton();
        this.jPanel68 = new JPanel();
        LZJMS.越级打怪 = new JToggleButton();
        this.jLabel88 = new JLabel();
        this.jLabel90 = new JLabel();
        LZJMS.雇佣商人 = new JToggleButton();
        this.jPanel69 = new JPanel();
        LZJMS.地图名称 = new JToggleButton();
        this.jLabel92 = new JLabel();
        LZJMS.玩家指令 = new JToggleButton();
        this.jLabel93 = new JLabel();
        this.jLabel94 = new JLabel();
        LZJMS.游戏仓库 = new JToggleButton();
        this.jPanel70 = new JPanel();
        LZJMS.管理员隐身 = new JToggleButton();
        this.jLabel96 = new JLabel();
        LZJMS.管理员加速 = new JToggleButton();
        this.jLabel97 = new JLabel();
        this.jPanel100 = new JPanel();
        this.jLabel216 = new JLabel();
        LZJMS.克隆基础伤害 = new JTextField();
        this.jLabel217 = new JLabel();
        LZJMS.自动刷钱道具 = new JTextField();
        this.jPanel15 = new JPanel();
        this.jPanel71 = new JPanel();
        LZJMS.段数检测 = new JToggleButton();
        this.jLabel99 = new JLabel();
        LZJMS.攻速检测 = new JToggleButton();
        this.jLabel100 = new JLabel();
        LZJMS.全屏检测 = new JToggleButton();
        this.jLabel101 = new JLabel();
        this.jLabel102 = new JLabel();
        LZJMS.吸怪检测 = new JToggleButton();
        this.jLabel107 = new JLabel();
        LZJMS.吸物检测 = new JToggleButton();
        this.jLabel145 = new JLabel();
        LZJMS.个数检测 = new JToggleButton();
        this.jPanel72 = new JPanel();
        LZJMS.伤害检测 = new JToggleButton();
        this.jLabel103 = new JLabel();
        this.jLabel104 = new JLabel();
        this.jLabel106 = new JLabel();
        LZJMS.丢失伤害 = new JToggleButton();
        LZJMS.伤害上限 = new JTextField();
        this.伤害修正1 = new JLabel();
        LZJMS.伤害修正开关 = new JToggleButton();
        LZJMS.重置技能总范围 = new JTextField();
        this.jLabel199 = new JLabel();
        this.jLabel113 = new JLabel();
        LZJMS.重置技能范围开关 = new JToggleButton();
        this.jPanel74 = new JPanel();
        LZJMS.全服通告 = new JToggleButton();
        this.jLabel112 = new JLabel();
        this.jLabel116 = new JLabel();
        LZJMS.封停账号 = new JToggleButton();
        this.jLabel117 = new JLabel();
        LZJMS.封停IP = new JToggleButton();
        this.jLabel118 = new JLabel();
        LZJMS.封停MAC = new JToggleButton();
        this.jPanel50 = new JPanel();
        this.jScrollPane2 = new JScrollPane();
        this.职业列表表单 = new JTable();
        this.职业技能初始化 = new JButton();
        this.刷新职业列表 = new JButton();
        this.读取技能 = new JButton();
        this.jPanel51 = new JPanel();
        this.jScrollPane13 = new JScrollPane();
        this.职业技能列表表单 = new JTable();
        this.jLabel160 = new JLabel();
        this.技能代码 = new JTextField();
        this.技能名 = new JLabel();
        this.技能名称 = new JTextField();
        this.jLabel161 = new JLabel();
        this.技能段数 = new JTextField();
        this.jLabel162 = new JLabel();
        this.怪物个数 = new JTextField();
        this.修改技能 = new JButton();
        this.当前职业ID = new JLabel();
        this.当前职业名称 = new JLabel();
        this.jPanel30 = new JPanel();
        this.jScrollPane4 = new JScrollPane();
        this.公告信息 = new JTable();
        this.jButton16 = new JButton();
        this.jButton17 = new JButton();
        this.jButton18 = new JButton();
        this.jButton19 = new JButton();
        LZJMS.公告间隔时间 = new JTextField();
        this.jLabel218 = new JLabel();
        this.重载公告 = new JButton();
        this.jPanel1 = new JPanel();
        this.jPanel62 = new JPanel();
        LZJMS.蓝蜗牛 = new JToggleButton();
        LZJMS.蘑菇仔 = new JToggleButton();
        LZJMS.绿水灵 = new JToggleButton();
        LZJMS.胖企鹅 = new JToggleButton();
        LZJMS.星精灵 = new JToggleButton();
        LZJMS.漂漂猪 = new JToggleButton();
        LZJMS.白雪人 = new JToggleButton();
        LZJMS.大海龟 = new JToggleButton();
        LZJMS.章鱼怪 = new JToggleButton();
        LZJMS.顽皮猴 = new JToggleButton();
        LZJMS.大灰狼 = new JToggleButton();
        LZJMS.紫色猫 = new JToggleButton();
        LZJMS.石头人 = new JToggleButton();
        LZJMS.红螃蟹 = new JToggleButton();
        LZJMS.小青蛇 = new JToggleButton();
        LZJMS.青鳄鱼 = new JToggleButton();
        LZJMS.花蘑菇 = new JToggleButton();
        LZJMS.火野猪 = new JToggleButton();
        LZJMS.小白兔 = new JToggleButton();
        LZJMS.喷火龙 = new JToggleButton();
        this.jPanel11 = new JPanel();
        this.jPanel98 = new JPanel();
        this.jScrollPane15 = new JScrollPane();
        this.怪物血量表 = new JTable();
        this.jPanel46 = new JPanel();
        this.jLabel20 = new JLabel();
        LZJMS.全局血量等级 = new JTextField();
        this.jLabel23 = new JLabel();
        LZJMS.全局血量倍率 = new JTextField();
        this.jButton114 = new JButton();
        this.jPanel99 = new JPanel();
        this.jButton115 = new JButton();
        this.jButton116 = new JButton();
        this.jButton117 = new JButton();
        this.jButton118 = new JButton();
        this.jPanel32 = new JPanel();
        this.jLabel133 = new JLabel();
        LZJMS.怪物减伤开关 = new JToggleButton();
        this.jPanel24 = new JPanel();
        this.jPanel29 = new JPanel();
        this.jScrollPane11 = new JScrollPane();
        LZJMS.多倍怪物地图列表 = new JTable();
        this.jLabel134 = new JLabel();
        LZJMS.多倍地图排序 = new JTextField();
        this.jLabel135 = new JLabel();
        LZJMS.多倍地图原地图代码 = new JTextField();
        LZJMS.多倍地图现地图代码 = new JTextField();
        this.jLabel136 = new JLabel();
        this.增加地图 = new JButton();
        this.修改地图 = new JButton();
        this.删除地图 = new JButton();
        this.刷新地图 = new JButton();
        this.jPanel31 = new JPanel();
        this.jLabel129 = new JLabel();
        LZJMS.怪物多倍地图开关 = new JToggleButton();
        this.jLabel130 = new JLabel();
        LZJMS.怪物地图多倍怪物开关 = new JToggleButton();
        this.jLabel131 = new JLabel();
        LZJMS.多倍地图倍率 = new JTextField();
        this.jLabel132 = new JLabel();
        LZJMS.怪物刷新频率设定 = new JTextField();
        this.jLabel212 = new JLabel();
        LZJMS.地图刷新频率 = new JTextField();
        this.jPanel33 = new JPanel();
        this.jPanel34 = new JPanel();
        this.jScrollPane12 = new JScrollPane();
        LZJMS.野外BOSS刷新时间 = new JTable();
        this.jPanel35 = new JPanel();
        this.jLabel137 = new JLabel();
        LZJMS.野外BOSS刷新时间原地图 = new JTextField();
        this.jLabel138 = new JLabel();
        LZJMS.野外BOSS刷新时间现地图 = new JTextField();
        this.jLabel139 = new JLabel();
        LZJMS.野外BOSS刷新时间boss代码 = new JTextField();
        this.jLabel140 = new JLabel();
        LZJMS.野外BOSS刷新时间分 = new JTextField();
        this.jLabel141 = new JLabel();
        LZJMS.野外BOSS刷新时间横坐标 = new JTextField();
        this.jLabel142 = new JLabel();
        LZJMS.野外BOSS刷新时间纵坐标 = new JTextField();
        this.jLabel143 = new JLabel();
        LZJMS.野外BOSS刷新时间说明 = new JTextField();
        this.野外BOSS内容增加 = new JButton();
        this.野外BOSS内容删除 = new JButton();
        this.野外BOSS内容修改 = new JButton();
        this.野外BOSS内容刷新 = new JButton();
        this.jPanel36 = new JPanel();
        this.jPanel82 = new JPanel();
        this.jScrollPane108 = new JScrollPane();
        LZJMS.自建NPC列表 = new JTable();
        this.NPC刷新 = new JButton();
        this.NPC删除 = new JButton();
        this.jPanel79 = new JPanel();
        this.jScrollPane105 = new JScrollPane();
        LZJMS.怪物经验限制表 = new JTable();
        this.怪物经验限制增加 = new JButton();
        this.怪物经验限制删除 = new JButton();
        this.怪物经验限制修改 = new JButton();
        this.怪物经验限制刷新 = new JButton();
        this.jPanel47 = new JPanel();
        this.jPanel80 = new JPanel();
        this.jScrollPane106 = new JScrollPane();
        LZJMS.封禁IP地址 = new JTable();
        this.jButton49 = new JButton();
        this.jButton50 = new JButton();
        this.jPanel81 = new JPanel();
        this.jScrollPane107 = new JScrollPane();
        LZJMS.封禁MAC地址 = new JTable();
        this.jButton52 = new JButton();
        this.jButton53 = new JButton();
        this.jPanel39 = new JPanel();
        this.jPanel56 = new JPanel();
        this.jScrollPane16 = new JScrollPane();
        LZJMS.宠物吸取不参与地图列表 = new JTable();
        this.jLabel180 = new JLabel();
        LZJMS.宠物吸取道具不参与排序 = new JTextField();
        this.jLabel181 = new JLabel();
        LZJMS.宠物吸取不参与地图代码 = new JTextField();
        this.不参与叠加道具增加1 = new JButton();
        this.不参与叠加道具删除1 = new JButton();
        this.修改地图1 = new JButton();
        this.宠物不参与地图初始 = new JButton();
        this.jLabel189 = new JLabel();
        LZJMS.宠物吸取不参与地图代码1 = new JTextField();
        this.jPanel57 = new JPanel();
        this.jLabel148 = new JLabel();
        LZJMS.特殊宠物吸取开关 = new JToggleButton();
        this.jLabel165 = new JLabel();
        LZJMS.特殊宠物吸物开关 = new JToggleButton();
        LZJMS.特殊宠物吸金开关 = new JToggleButton();
        this.jLabel167 = new JLabel();
        this.jLabel169 = new JLabel();
        LZJMS.特殊宠物吸物无法使用地图开关 = new JToggleButton();
        this.jLabel187 = new JLabel();
        LZJMS.宠吸道具 = new JTextField();
        this.jLabel188 = new JLabel();
        LZJMS.道具强行宠吸开关 = new JToggleButton();
        this.jLabel207 = new JLabel();
        LZJMS.特殊全宠物吸金开关 = new JToggleButton();
        this.jLabel208 = new JLabel();
        LZJMS.特殊全宠物吸物开关 = new JToggleButton();
        this.jPanel88 = new JPanel();
        this.jScrollPane18 = new JScrollPane();
        LZJMS.宠物吸物列表 = new JTable();
        this.jLabel185 = new JLabel();
        LZJMS.宠物吸物排序 = new JTextField();
        this.jLabel186 = new JLabel();
        LZJMS.宠物吸物代码 = new JTextField();
        this.不参与叠加道具增加3 = new JButton();
        this.修改地图3 = new JButton();
        this.不参与叠加道具删除3 = new JButton();
        this.宠物吸取物品初始化 = new JButton();
        this.jLabel191 = new JLabel();
        LZJMS.宠物吸物代码1 = new JTextField();
        this.jPanel89 = new JPanel();
        this.jScrollPane17 = new JScrollPane();
        LZJMS.宠物吸金列表 = new JTable();
        this.jLabel182 = new JLabel();
        LZJMS.宠物吸金排序 = new JTextField();
        this.jLabel184 = new JLabel();
        LZJMS.宠物吸金代码 = new JTextField();
        this.不参与叠加道具增加2 = new JButton();
        this.修改地图2 = new JButton();
        this.不参与叠加道具删除2 = new JButton();
        this.宠物吸取金币初始 = new JButton();
        this.jLabel190 = new JLabel();
        LZJMS.宠物吸金代码1 = new JTextField();
        this.jPanel90 = new JPanel();
        this.jPanel91 = new JPanel();
        this.jScrollPane19 = new JScrollPane();
        LZJMS.套装伤害加成表 = new JTable();
        this.jPanel92 = new JPanel();
        this.jLabel192 = new JLabel();
        LZJMS.原套装代码 = new JTextField();
        this.jLabel193 = new JLabel();
        LZJMS.现套装代码 = new JTextField();
        this.jLabel194 = new JLabel();
        LZJMS.套装加成比例 = new JTextField();
        this.套装道具增加 = new JButton();
        this.套装道具删除 = new JButton();
        this.套装道具修改 = new JButton();
        this.jLabel195 = new JLabel();
        LZJMS.套装道具代码 = new JTextField();
        this.装备加成伤害列表初始化 = new JButton();
        this.jLabel196 = new JLabel();
        LZJMS.套装属性加成开关 = new JToggleButton();
        this.jPanel93 = new JPanel();
        this.jLabel197 = new JLabel();
        this.套装排序输入 = new JTextField();
        this.套装查询 = new JButton();
        this.jLabel204 = new JLabel();
        this.套装名字输入 = new JTextField();
        this.套装查询1 = new JButton();
        this.jLabel198 = new JLabel();
        LZJMS.套装编码 = new JTextField();
        this.jLabel203 = new JLabel();
        LZJMS.套装名字 = new JTextField();
        this.jLabel205 = new JLabel();
        LZJMS.套装个数 = new JTextField();
        this.重载套装加成 = new JButton();
        this.jPanel58 = new JPanel();
        this.jPanel59 = new JPanel();
        this.重载副本 = new JButton();
        this.重载爆率 = new JButton();
        this.重载反应堆 = new JButton();
        this.重载传送门 = new JButton();
        this.重载商店 = new JButton();
        this.重载商城 = new JButton();
        this.重载活动事件 = new JButton();
        this.重载BOSS事件 = new JButton();
        this.重载自定义事件 = new JButton();
        this.重载套装加成1 = new JButton();
        this.jPanel61 = new JPanel();
        this.保存玩家 = new JButton();
        this.保存雇佣并关闭 = new JButton();
        this.一键清空数据库 = new JButton();
        this.jPanel5 = new JPanel();
        this.jPanel38 = new JPanel();
        this.jPanel40 = new JPanel();
        this.jLabel87 = new JLabel();
        this.倍率设置 = new JTextField();
        this.持续时间时 = new JTextField();
        this.jLabel95 = new JLabel();
        this.jLabel144 = new JLabel();
        this.持续时间分 = new JTextField();
        this.jLabel98 = new JLabel();
        this.开启倍率活动 = new JButton();
        this.jLabel157 = new JLabel();
        this.倍率活动类型 = (JComboBox<String>) new JComboBox();
        this.jPanel41 = new JPanel();
        this.jPanel42 = new JPanel();
        this.jLabel149 = new JLabel();
        this.发放道具代码 = new JTextField();
        this.jLabel150 = new JLabel();
        this.发放道具数量 = new JTextField();
        this.jLabel151 = new JLabel();
        this.发放道具发放范围 = (JComboBox<String>) new JComboBox();
        this.jLabel152 = new JLabel();
        this.发放个人玩家名字 = new JTextField();
        this.jLabel153 = new JLabel();
        this.发放道具 = new JButton();
        this.jPanel43 = new JPanel();
        this.jPanel45 = new JPanel();
        this.jLabel154 = new JLabel();
        this.发放其他数量 = new JTextField();
        this.jLabel155 = new JLabel();
        this.发放其他范围 = (JComboBox<String>) new JComboBox();
        this.jLabel156 = new JLabel();
        this.jLabel158 = new JLabel();
        this.发放其他类型 = (JComboBox<String>) new JComboBox();
        this.发放其他玩家 = new JTextField();
        this.jLabel159 = new JLabel();
        this.发放其他内容 = new JButton();
        this.jPanel9 = new JPanel();
        this.jPanel78 = new JPanel();
        this.jScrollPane14 = new JScrollPane();
        this.充值赞助列表 = new JTable();
        this.jPanel86 = new JPanel();
        this.jButton1 = new JButton();
        this.jPanel87 = new JPanel();
        this.jLabel175 = new JLabel();
        this.jLabel176 = new JLabel();
        this.jLabel177 = new JLabel();
        this.jLabel178 = new JLabel();
        this.jLabel179 = new JLabel();
        this.充值角色ID = new JTextField();
        this.充值玩家名字 = new JTextField();
        this.现有每日充值 = new JTextField();
        this.现有累计充值 = new JTextField();
        this.现有剩余积分 = new JTextField();
        this.jLabel183 = new JLabel();
        this.现有可用点券 = new JTextField();
        this.每日充值 = new Checkbox();
        this.jLabel166 = new JLabel();
        this.累计充值 = new Checkbox();
        this.jLabel168 = new JLabel();
        this.剩余余额 = new Checkbox();
        this.jLabel170 = new JLabel();
        this.可用点券 = new Checkbox();
        this.jLabel171 = new JLabel();
        this.jLabel105 = new JLabel();
        LZJMS.点券比例 = new JTextField();
        this.jLabel115 = new JLabel();
        this.发放充值数量 = new JTextField();
        this.发放氪金充值 = new JButton();
        this.jPanel96 = new JPanel();
        this.jLabel213 = new JLabel();
        this.氪金ID输入 = new JTextField();
        this.氪金ID查询 = new JButton();
        this.jLabel214 = new JLabel();
        this.氪金名字输入 = new JTextField();
        this.氪金名字查询 = new JButton();
        this.氪金机器码查询 = new JButton();
        this.氪金机器码输入 = new JTextField();
        this.jLabel215 = new JLabel();
        this.jPanel97 = new JPanel();
        this.jScrollPane21 = new JScrollPane();
        this.充值赞助列表1 = new JTable();
        this.jPanel60 = new JPanel();
        this.jScrollPane20 = new JScrollPane();
        this.jTextArea1 = new JTextArea();
        this.jPanel101 = new JPanel();
        this.jTextField1 = new JTextField();
        LZJMS.清空日志 = new JButton();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        LZJMS.时长 = new JProgressBar();
        LZJMS.内存 = new JProgressBar();
        LZJMS.人数 = new JProgressBar();
        LZJMS.保存数据 = new JButton();
        LZJMS.保存雇佣 = new JButton();
        LZJMS.重载全部 = new JButton();
        LZJMS.启动服务端 = new JButton();
        this.jPanel14 = new JPanel();
        LZJMS.第二控制台 = new JButton();
        LZJMS.第三控制台 = new JButton();
        LZJMS.关闭时间显示 = new JButton();
        LZJMS.关闭时间输入 = new JTextField();
        this.setDefaultCloseOperation(3);
        this.setBackground(new Color(255, 255, 255));
        this.setCursor(new Cursor(0));
        this.setIconImages(null);
        this.setMinimumSize(new Dimension(1366, 768));
        this.setResizable(false);
        this.getContentPane().setLayout((LayoutManager) new AbsoluteLayout());
        LZJMS.主界面菜单.setCursor(new Cursor(0));
        LZJMS.outSee.setEditable(false);
        LZJMS.outSee.setBackground(new Color(25, 25, 25));
        LZJMS.outSee.setColumns(20);
        LZJMS.outSee.setForeground(new Color(10, 255, 221));
        LZJMS.outSee.setRows(5);
        this.jScrollPane1.setViewportView((Component) LZJMS.outSee);
        LZJMS.主界面菜单.addTab("日志记录", (Icon) new ImageIcon(this.getClass().getResource("/image/日志.png")), (Component) this.jScrollPane1);
        LZJMS.jPanel44.setBorder((Border) BorderFactory.createTitledBorder("数据库设置[一般无需调整]"));
        this.jLabel16.setText("数据库名称");
        this.jLabel17.setText("IP地址");
        LZJMS.数据库IP地址.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.数据库IP地址FocusLost(evt);
            }
        });
        LZJMS.数据库端口.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.数据库端口FocusLost(evt);
            }
        });
        this.jLabel18.setText("端口");
        LZJMS.数据库用户名.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.数据库用户名FocusLost(evt);
            }
        });
        this.jLabel19.setText("用户名");
        LZJMS.数据库名称.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.数据库名称FocusLost(evt);
            }
        });
        LZJMS.数据库名称.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.数据库名称ActionPerformed(evt);
            }
        });
        this.jLabel34.setText("密码");
        LZJMS.数据库密码.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.数据库密码FocusLost(evt);
            }
        });
        final GroupLayout jPanel44Layout = new GroupLayout((Container) LZJMS.jPanel44);
        LZJMS.jPanel44.setLayout((LayoutManager) jPanel44Layout);
        jPanel44Layout.setHorizontalGroup((Group) jPanel44Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel44Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel44Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel44Layout.createSequentialGroup().addComponent((Component) this.jLabel34, -1, -1, 32767).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) LZJMS.数据库密码, -2, 69, -2)).addGroup((Group) jPanel44Layout.createSequentialGroup().addComponent((Component) this.jLabel16).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.数据库名称, -2, 69, -2)).addGroup((Group) jPanel44Layout.createSequentialGroup().addGroup((Group) jPanel44Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel44Layout.createSequentialGroup().addComponent((Component) this.jLabel17).addGap(0, 0, 32767)).addComponent((Component) this.jLabel18, -1, -1, 32767).addComponent((Component) this.jLabel19, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel44Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.数据库IP地址, -2, 69, -2).addComponent((Component) LZJMS.数据库端口, -2, 69, -2).addComponent((Component) LZJMS.数据库用户名, -2, 69, -2)))).addContainerGap()));
        jPanel44Layout.setVerticalGroup((Group) jPanel44Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel44Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel44Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) LZJMS.数据库名称, -2, -1, -2).addComponent((Component) this.jLabel16)).addGap(8, 8, 8).addGroup((Group) jPanel44Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel17).addComponent((Component) LZJMS.数据库IP地址, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel44Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel18).addComponent((Component) LZJMS.数据库端口, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel44Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel19).addComponent((Component) LZJMS.数据库用户名, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel44Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel34).addComponent((Component) LZJMS.数据库密码, -2, -1, -2)).addContainerGap(-1, 32767)));
        this.jPanel48.setBorder((Border) BorderFactory.createTitledBorder("帐号设置"));
        LZJMS.登录自动注册开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.登录自动注册开关.setBorderPainted(false);
        LZJMS.登录自动注册开关.setContentAreaFilled(false);
        LZJMS.登录自动注册开关.setFocusPainted(false);
        LZJMS.登录自动注册开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.登录自动注册开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.登录自动注册开关StateChanged(evt);
            }
        });
        LZJMS.登录自动注册开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.登录自动注册开关ActionPerformed(evt);
            }
        });
        this.jLabel24.setText("登录自动注册");
        LZJMS.MAC注册上限.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.MAC注册上限FocusLost(evt);
            }
        });
        this.jLabel32.setText("MAC注册帐号上限");
        LZJMS.账号角色上限.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.账号角色上限FocusLost(evt);
            }
        });
        this.jLabel33.setText("账号创建角色上限");
        final GroupLayout jPanel48Layout = new GroupLayout((Container) this.jPanel48);
        this.jPanel48.setLayout((LayoutManager) jPanel48Layout);
        jPanel48Layout.setHorizontalGroup((Group) jPanel48Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel48Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel48Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel48Layout.createSequentialGroup().addGroup((Group) jPanel48Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel48Layout.createSequentialGroup().addComponent((Component) this.jLabel32, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED)).addGroup((Group) jPanel48Layout.createSequentialGroup().addComponent((Component) this.jLabel33, -1, 113, 32767).addGap(2, 2, 2))).addGroup((Group) jPanel48Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.账号角色上限, -2, 69, -2).addComponent((Component) LZJMS.MAC注册上限, -2, 69, -2))).addGroup((Group) jPanel48Layout.createSequentialGroup().addComponent((Component) this.jLabel24).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.登录自动注册开关, -2, 74, -2))).addContainerGap()));
        jPanel48Layout.setVerticalGroup((Group) jPanel48Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel48Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel48Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.登录自动注册开关, -2, 30, -2).addComponent((Component) this.jLabel24)).addGap(9, 9, 9).addGroup((Group) jPanel48Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel32).addComponent((Component) LZJMS.MAC注册上限, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel48Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel33).addComponent((Component) LZJMS.账号角色上限, -2, -1, -2)).addContainerGap(-1, 32767)));
        this.jPanel54.setBorder((Border) BorderFactory.createTitledBorder("服务器倍率[重启生效]"));
        this.jLabel65.setText("经验倍率");
        this.jLabel66.setText("金币倍率");
        LZJMS.服务器金币倍率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.服务器金币倍率FocusLost(evt);
            }
        });
        LZJMS.服务器掉落倍率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.服务器掉落倍率FocusLost(evt);
            }
        });
        this.jLabel67.setText("掉落倍率");
        LZJMS.服务器经验倍率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.服务器经验倍率FocusLost(evt);
            }
        });
        this.jLabel69.setText("频道ID");
        LZJMS.服务器双倍频道.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.服务器双倍频道FocusLost(evt);
            }
        });
        this.jLabel68.setText("双倍频道");
        LZJMS.双倍频道开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.双倍频道开关.setBorderPainted(false);
        LZJMS.双倍频道开关.setContentAreaFilled(false);
        LZJMS.双倍频道开关.setFocusPainted(false);
        LZJMS.双倍频道开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.双倍频道开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.双倍频道开关StateChanged(evt);
            }
        });
        LZJMS.双倍频道开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.双倍频道开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel54Layout = new GroupLayout((Container) this.jPanel54);
        this.jPanel54.setLayout((LayoutManager) jPanel54Layout);
        jPanel54Layout.setHorizontalGroup((Group) jPanel54Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel54Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel54Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel54Layout.createSequentialGroup().addComponent((Component) this.jLabel65).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.服务器经验倍率, -2, 69, -2)).addGroup((Group) jPanel54Layout.createSequentialGroup().addGroup((Group) jPanel54Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel54Layout.createSequentialGroup().addGroup((Group) jPanel54Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel54Layout.createSequentialGroup().addComponent((Component) this.jLabel66).addGap(0, 0, 32767)).addComponent((Component) this.jLabel67, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED)).addGroup((Group) jPanel54Layout.createSequentialGroup().addComponent((Component) this.jLabel69, -1, -1, 32767).addGap(24, 24, 24))).addGroup((Group) jPanel54Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) LZJMS.服务器双倍频道).addComponent((Component) LZJMS.服务器掉落倍率).addComponent((Component) LZJMS.服务器金币倍率, Alignment.TRAILING, -2, 69, -2))).addGroup((Group) jPanel54Layout.createSequentialGroup().addComponent((Component) this.jLabel68).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.双倍频道开关, -2, 74, -2))).addContainerGap()));
        jPanel54Layout.setVerticalGroup((Group) jPanel54Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel54Layout.createSequentialGroup().addGap(5, 5, 5).addGroup((Group) jPanel54Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) LZJMS.服务器经验倍率, -2, -1, -2).addComponent((Component) this.jLabel65)).addGap(8, 8, 8).addGroup((Group) jPanel54Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel66).addComponent((Component) LZJMS.服务器金币倍率, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel54Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel67).addComponent((Component) LZJMS.服务器掉落倍率, -2, -1, -2)).addGap(4, 4, 4).addGroup((Group) jPanel54Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel68).addComponent((Component) LZJMS.双倍频道开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel54Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel69).addComponent((Component) LZJMS.服务器双倍频道, -2, -1, -2)).addContainerGap(-1, 32767)));
        this.jPanel53.setBorder((Border) BorderFactory.createTitledBorder("服务器设置[重启生效]"));
        this.jLabel61.setText("登录端口");
        this.jLabel62.setText("商城端口");
        LZJMS.服务器商城端口.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.服务器商城端口FocusLost(evt);
            }
        });
        LZJMS.服务器商城端口.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.服务器商城端口ActionPerformed(evt);
            }
        });
        LZJMS.服务器频道端口.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.服务器频道端口FocusLost(evt);
            }
        });
        this.jLabel63.setText("频道端口");
        LZJMS.服务器频道数量.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.服务器频道数量FocusLost(evt);
            }
        });
        this.jLabel64.setText("频道数量");
        LZJMS.服务器登录端口.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.服务器登录端口FocusLost(evt);
            }
        });
        LZJMS.服务器登录端口.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.服务器登录端口ActionPerformed(evt);
            }
        });
        this.jLabel70.setText("服务器名字");
        LZJMS.服务器名字.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.服务器名字FocusLost(evt);
            }
        });
        LZJMS.服务器名字.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.服务器名字ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel53Layout = new GroupLayout((Container) this.jPanel53);
        this.jPanel53.setLayout((LayoutManager) jPanel53Layout);
        jPanel53Layout.setHorizontalGroup((Group) jPanel53Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel53Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel53Layout.createSequentialGroup().addComponent((Component) this.jLabel62).addGap(0, 0, 32767)).addComponent((Component) this.jLabel63, -1, -1, 32767).addComponent((Component) this.jLabel64, -1, -1, 32767).addGroup((Group) jPanel53Layout.createSequentialGroup().addComponent((Component) this.jLabel70, -1, -1, 32767).addGap(18, 18, 18))).addGroup((Group) jPanel53Layout.createSequentialGroup().addComponent((Component) this.jLabel61).addGap(30, 30, 30))).addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) LZJMS.服务器商城端口, Alignment.TRAILING).addGroup((Group) jPanel53Layout.createSequentialGroup().addGap(6, 6, 6).addComponent((Component) LZJMS.服务器名字, -1, 69, 32767))).addComponent((Component) LZJMS.服务器频道数量, Alignment.TRAILING)).addGroup(Alignment.TRAILING, (Group) jPanel53Layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.服务器登录端口, Alignment.TRAILING, -2, 69, -2).addComponent((Component) LZJMS.服务器频道端口, Alignment.TRAILING, -2, 69, -2)))).addContainerGap()));
        jPanel53Layout.linkSize(0, LZJMS.服务器名字, LZJMS.服务器商城端口, LZJMS.服务器频道数量);
        jPanel53Layout.setVerticalGroup((Group) jPanel53Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group) jPanel53Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) LZJMS.服务器登录端口, -2, -1, -2).addComponent((Component) this.jLabel61)).addGap(8, 8, 8).addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel62).addComponent((Component) LZJMS.服务器商城端口, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel63).addComponent((Component) LZJMS.服务器频道端口, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel64).addComponent((Component) LZJMS.服务器频道数量, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel53Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel70).addComponent((Component) LZJMS.服务器名字, -2, -1, -2)).addContainerGap()));
        jPanel53Layout.linkSize(1, LZJMS.服务器名字, LZJMS.服务器商城端口, LZJMS.服务器登录端口, LZJMS.服务器频道数量, LZJMS.服务器频道端口);
        this.jPanel49.setBorder((Border) BorderFactory.createTitledBorder("职业/等级"));
        LZJMS.冒险家开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.冒险家开关.setBorderPainted(false);
        LZJMS.冒险家开关.setContentAreaFilled(false);
        LZJMS.冒险家开关.setFocusPainted(false);
        LZJMS.冒险家开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.冒险家开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.冒险家开关StateChanged(evt);
            }
        });
        LZJMS.冒险家开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.冒险家开关ActionPerformed(evt);
            }
        });
        this.jLabel35.setText("冒险家");
        LZJMS.战神开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.战神开关.setBorderPainted(false);
        LZJMS.战神开关.setContentAreaFilled(false);
        LZJMS.战神开关.setFocusPainted(false);
        LZJMS.战神开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.战神开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.战神开关StateChanged(evt);
            }
        });
        LZJMS.战神开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.战神开关ActionPerformed(evt);
            }
        });
        this.jLabel36.setText("战神");
        LZJMS.骑士团开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.骑士团开关.setBorderPainted(false);
        LZJMS.骑士团开关.setContentAreaFilled(false);
        LZJMS.骑士团开关.setFocusPainted(false);
        LZJMS.骑士团开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.骑士团开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.骑士团开关StateChanged(evt);
            }
        });
        LZJMS.骑士团开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.骑士团开关ActionPerformed(evt);
            }
        });
        this.jLabel37.setText("骑士团");
        this.jLabel38.setText("角色等级上限");
        LZJMS.等级上限.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.等级上限FocusLost(evt);
            }
        });
        LZJMS.等级上限.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.等级上限ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel49Layout = new GroupLayout((Container) this.jPanel49);
        this.jPanel49.setLayout((LayoutManager) jPanel49Layout);
        jPanel49Layout.setHorizontalGroup((Group) jPanel49Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel49Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel49Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel49Layout.createSequentialGroup().addComponent((Component) this.jLabel35).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.冒险家开关, -2, 74, -2)).addGroup((Group) jPanel49Layout.createSequentialGroup().addComponent((Component) this.jLabel36).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.战神开关, -2, 74, -2)).addGroup((Group) jPanel49Layout.createSequentialGroup().addComponent((Component) this.jLabel37).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.骑士团开关, -2, 74, -2)).addGroup((Group) jPanel49Layout.createSequentialGroup().addComponent((Component) this.jLabel38, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.等级上限, -2, 66, -2))).addContainerGap()));
        jPanel49Layout.setVerticalGroup((Group) jPanel49Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel49Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel49Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel35).addComponent((Component) LZJMS.冒险家开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel49Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel36).addComponent((Component) LZJMS.战神开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel49Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel37).addComponent((Component) LZJMS.骑士团开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel49Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel38).addComponent((Component) LZJMS.等级上限, -2, -1, -2)).addContainerGap(-1, 32767)));
        this.jPanel64.setBorder((Border) BorderFactory.createTitledBorder("多开限制"));
        this.jLabel73.setText("IP多开上限");
        this.jLabel74.setText("MAC多开上限");
        LZJMS.MAC多开上限.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.MAC多开上限FocusLost(evt);
            }
        });
        LZJMS.IP多开上限.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.IP多开上限FocusLost(evt);
            }
        });
        this.jLabel77.setText("同IP多开");
        LZJMS.同IP多开.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.同IP多开.setBorderPainted(false);
        LZJMS.同IP多开.setContentAreaFilled(false);
        LZJMS.同IP多开.setFocusPainted(false);
        LZJMS.同IP多开.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.同IP多开.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.同IP多开StateChanged(evt);
            }
        });
        LZJMS.同IP多开.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.同IP多开ActionPerformed(evt);
            }
        });
        this.jLabel78.setText("同MAC多开");
        LZJMS.同MAC多开.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.同MAC多开.setBorderPainted(false);
        LZJMS.同MAC多开.setContentAreaFilled(false);
        LZJMS.同MAC多开.setFocusPainted(false);
        LZJMS.同MAC多开.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.同MAC多开.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.同MAC多开StateChanged(evt);
            }
        });
        LZJMS.同MAC多开.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.同MAC多开ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel64Layout = new GroupLayout((Container) this.jPanel64);
        this.jPanel64.setLayout((LayoutManager) jPanel64Layout);
        jPanel64Layout.setHorizontalGroup((Group) jPanel64Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel64Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel64Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel64Layout.createSequentialGroup().addComponent((Component) this.jLabel73, -1, -1, 32767).addGap(18, 18, 18).addComponent((Component) LZJMS.IP多开上限, -2, 69, -2)).addGroup((Group) jPanel64Layout.createSequentialGroup().addComponent((Component) this.jLabel77).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.同IP多开, -2, 74, -2)).addGroup(Alignment.TRAILING, (Group) jPanel64Layout.createSequentialGroup().addGroup((Group) jPanel64Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component) this.jLabel74, -1, -1, 32767).addComponent((Component) this.jLabel78, -1, -1, 32767)).addGap(18, 18, 18).addGroup((Group) jPanel64Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.同MAC多开, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.MAC多开上限, Alignment.TRAILING, -2, 69, -2)))).addContainerGap()));
        jPanel64Layout.setVerticalGroup((Group) jPanel64Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel64Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel64Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel77).addComponent((Component) LZJMS.同IP多开, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel64Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) LZJMS.IP多开上限, -2, -1, -2).addComponent((Component) this.jLabel73)).addGap(10, 10, 10).addGroup((Group) jPanel64Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel78).addComponent((Component) LZJMS.同MAC多开, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel64Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel74).addComponent((Component) LZJMS.MAC多开上限, -2, -1, -2)).addContainerGap()));
        this.jPanel73.setBorder((Border) BorderFactory.createTitledBorder("雇佣"));
        this.jLabel109.setText("持续时间");
        LZJMS.雇佣持续时间.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.雇佣持续时间FocusLost(evt);
            }
        });
        this.jLabel111.setText("雇佣自动回收");
        LZJMS.雇佣自动回收.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.雇佣自动回收.setBorderPainted(false);
        LZJMS.雇佣自动回收.setContentAreaFilled(false);
        LZJMS.雇佣自动回收.setFocusPainted(false);
        LZJMS.雇佣自动回收.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.雇佣自动回收.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.雇佣自动回收StateChanged(evt);
            }
        });
        LZJMS.雇佣自动回收.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.雇佣自动回收ActionPerformed(evt);
            }
        });
        this.jLabel4.setText("雇佣经验加成");
        LZJMS.雇佣经验加成开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.雇佣经验加成开关.setBorderPainted(false);
        LZJMS.雇佣经验加成开关.setContentAreaFilled(false);
        LZJMS.雇佣经验加成开关.setFocusPainted(false);
        LZJMS.雇佣经验加成开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.雇佣经验加成开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.雇佣经验加成开关StateChanged(evt);
            }
        });
        LZJMS.雇佣经验加成开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.雇佣经验加成开关ActionPerformed(evt);
            }
        });
        this.jLabel5.setText("经验加成比例%");
        LZJMS.经验加成比例.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.经验加成比例FocusLost(evt);
            }
        });
        final GroupLayout jPanel73Layout = new GroupLayout((Container) this.jPanel73);
        this.jPanel73.setLayout((LayoutManager) jPanel73Layout);
        jPanel73Layout.setHorizontalGroup((Group) jPanel73Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel73Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel73Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel73Layout.createSequentialGroup().addComponent((Component) this.jLabel4).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.雇佣经验加成开关, -2, 74, -2)).addGroup((Group) jPanel73Layout.createSequentialGroup().addComponent((Component) this.jLabel5).addGap(18, 18, 18).addComponent((Component) LZJMS.经验加成比例, -2, 69, -2).addGap(0, 0, 32767)).addGroup(Alignment.TRAILING, (Group) jPanel73Layout.createSequentialGroup().addComponent((Component) this.jLabel109, -1, -1, 32767).addGap(23, 23, 23).addComponent((Component) LZJMS.雇佣持续时间, -2, 69, -2)).addGroup(Alignment.TRAILING, (Group) jPanel73Layout.createSequentialGroup().addComponent((Component) this.jLabel111, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.雇佣自动回收, -2, 74, -2))).addContainerGap()));
        jPanel73Layout.setVerticalGroup((Group) jPanel73Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel73Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel73Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel4).addComponent((Component) LZJMS.雇佣经验加成开关, -2, 30, -2)).addGap(6, 6, 6).addGroup((Group) jPanel73Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel5).addComponent((Component) LZJMS.经验加成比例, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel73Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel111).addComponent((Component) LZJMS.雇佣自动回收, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel73Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel109).addComponent((Component) LZJMS.雇佣持续时间, -2, -1, -2)).addContainerGap()));
        this.jPanel6.setBorder((Border) BorderFactory.createTitledBorder("群机器人"));
        this.jLabel6.setText("机器人开关");
        LZJMS.机器人开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.机器人开关.setBorderPainted(false);
        LZJMS.机器人开关.setContentAreaFilled(false);
        LZJMS.机器人开关.setFocusPainted(false);
        LZJMS.机器人开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.机器人开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.机器人开关StateChanged(evt);
            }
        });
        LZJMS.机器人开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.机器人开关ActionPerformed(evt);
            }
        });
        this.jLabel7.setText("升级群消息通知");
        LZJMS.升级群消息通知.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.升级群消息通知.setBorderPainted(false);
        LZJMS.升级群消息通知.setContentAreaFilled(false);
        LZJMS.升级群消息通知.setFocusPainted(false);
        LZJMS.升级群消息通知.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.升级群消息通知.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.升级群消息通知StateChanged(evt);
            }
        });
        LZJMS.升级群消息通知.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.升级群消息通知ActionPerformed(evt);
            }
        });
        this.jLabel9.setText("<html><body>定制提示：<br>1、此功能暂不开放<br>2、请勿点击</body></html>");
        final GroupLayout jPanel6Layout = new GroupLayout((Container) this.jPanel6);
        this.jPanel6.setLayout((LayoutManager) jPanel6Layout);
        jPanel6Layout.setHorizontalGroup((Group) jPanel6Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel6Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel6Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel6Layout.createSequentialGroup().addComponent((Component) this.jLabel7).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.升级群消息通知, -2, 74, -2)).addGroup((Group) jPanel6Layout.createSequentialGroup().addComponent((Component) this.jLabel6).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.机器人开关, -2, 74, -2))).addContainerGap()).addGroup((Group) jPanel6Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel6Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jLabel9, -2, 179, -2).addContainerGap(15, 32767))));
        jPanel6Layout.setVerticalGroup((Group) jPanel6Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel6Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel6Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.机器人开关, -2, 30, -2).addComponent((Component) this.jLabel6)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel6Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.升级群消息通知, -2, 30, -2).addComponent((Component) this.jLabel7)).addContainerGap(-1, 32767)).addGroup((Group) jPanel6Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel6Layout.createSequentialGroup().addGap(77, 77, 77).addComponent((Component) this.jLabel9, -2, 64, -2).addContainerGap(-1, 32767))));
        this.jPanel7.setBorder((Border) BorderFactory.createTitledBorder("爆物上线"));
        this.jLabel8.setText("爆物上线开关");
        LZJMS.爆物上线开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.爆物上线开关.setBorderPainted(false);
        LZJMS.爆物上线开关.setContentAreaFilled(false);
        LZJMS.爆物上线开关.setFocusPainted(false);
        LZJMS.爆物上线开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.爆物上线开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.爆物上线开关StateChanged(evt);
            }
        });
        LZJMS.爆物上线开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.爆物上线开关ActionPerformed(evt);
            }
        });
        this.jLabel10.setText("爆物上线数量");
        LZJMS.爆物上线数量.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.爆物上线数量FocusLost(evt);
            }
        });
        LZJMS.爆物上线数量.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.爆物上线数量ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel7Layout = new GroupLayout((Container) this.jPanel7);
        this.jPanel7.setLayout((LayoutManager) jPanel7Layout);
        jPanel7Layout.setHorizontalGroup((Group) jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel7Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel7Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel8).addComponent((Component) this.jLabel10)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel7Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.爆物上线数量, -2, 66, -2).addComponent((Component) LZJMS.爆物上线开关, -2, 74, -2)).addContainerGap()));
        jPanel7Layout.setVerticalGroup((Group) jPanel7Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel7Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel7Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.爆物上线开关, -2, 30, -2).addComponent((Component) this.jLabel8)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel7Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel10).addComponent((Component) LZJMS.爆物上线数量, -2, -1, -2)).addContainerGap(-1, 32767)));
        this.jPanel8.setBorder((Border) BorderFactory.createTitledBorder("杂项内容"));
        this.jLabel11.setText("箱子变脚本开关");
        LZJMS.自定义箱子代码.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.自定义箱子代码.setBorderPainted(false);
        LZJMS.自定义箱子代码.setContentAreaFilled(false);
        LZJMS.自定义箱子代码.setFocusPainted(false);
        LZJMS.自定义箱子代码.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.自定义箱子代码.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.自定义箱子代码StateChanged(evt);
            }
        });
        LZJMS.自定义箱子代码.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.自定义箱子代码ActionPerformed(evt);
            }
        });
        this.jLabel12.setText("玩家上线喇叭");
        LZJMS.上线喇叭.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.上线喇叭.setBorderPainted(false);
        LZJMS.上线喇叭.setContentAreaFilled(false);
        LZJMS.上线喇叭.setFocusPainted(false);
        LZJMS.上线喇叭.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.上线喇叭.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.上线喇叭StateChanged(evt);
            }
        });
        LZJMS.上线喇叭.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.上线喇叭ActionPerformed(evt);
            }
        });
        this.jLabel14.setText("玩家升级喇叭");
        LZJMS.玩家升级喇叭.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.玩家升级喇叭.setBorderPainted(false);
        LZJMS.玩家升级喇叭.setContentAreaFilled(false);
        LZJMS.玩家升级喇叭.setFocusPainted(false);
        LZJMS.玩家升级喇叭.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.玩家升级喇叭.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.玩家升级喇叭StateChanged(evt);
            }
        });
        LZJMS.玩家升级喇叭.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.玩家升级喇叭ActionPerformed(evt);
            }
        });
        this.jLabel211.setText("无限BUFF");
        LZJMS.无限BUFF.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.无限BUFF.setBorderPainted(false);
        LZJMS.无限BUFF.setContentAreaFilled(false);
        LZJMS.无限BUFF.setFocusPainted(false);
        LZJMS.无限BUFF.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.无限BUFF.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.无限BUFFStateChanged(evt);
            }
        });
        LZJMS.无限BUFF.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.无限BUFFActionPerformed(evt);
            }
        });
        final GroupLayout jPanel8Layout = new GroupLayout((Container) this.jPanel8);
        this.jPanel8.setLayout((LayoutManager) jPanel8Layout);
        jPanel8Layout.setHorizontalGroup((Group) jPanel8Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel8Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel8Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel8Layout.createSequentialGroup().addComponent((Component) this.jLabel11).addGap(18, 18, 18).addComponent((Component) LZJMS.自定义箱子代码, -2, 74, -2).addGap(0, 0, 32767)).addGroup((Group) jPanel8Layout.createSequentialGroup().addComponent((Component) this.jLabel12).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.上线喇叭, -2, 74, -2)).addGroup((Group) jPanel8Layout.createSequentialGroup().addComponent((Component) this.jLabel14).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.玩家升级喇叭, -2, 74, -2)).addGroup((Group) jPanel8Layout.createSequentialGroup().addComponent((Component) this.jLabel211).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.无限BUFF, -2, 74, -2))).addContainerGap()));
        jPanel8Layout.setVerticalGroup((Group) jPanel8Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel8Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel8Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.自定义箱子代码, -2, 30, -2).addComponent((Component) this.jLabel11)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel8Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel12).addComponent((Component) LZJMS.上线喇叭, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel8Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel14).addComponent((Component) LZJMS.玩家升级喇叭, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel8Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel211).addComponent((Component) LZJMS.无限BUFF, -2, 30, -2)).addContainerGap(-1, 32767)));
        this.jPanel10.setBorder((Border) BorderFactory.createTitledBorder("显示伤害"));
        this.jLabel13.setText("所有显示开关");
        LZJMS.所有显示开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.所有显示开关.setBorderPainted(false);
        LZJMS.所有显示开关.setContentAreaFilled(false);
        LZJMS.所有显示开关.setFocusPainted(false);
        LZJMS.所有显示开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.所有显示开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.所有显示开关StateChanged(evt);
            }
        });
        LZJMS.所有显示开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.所有显示开关ActionPerformed(evt);
            }
        });
        this.jLabel15.setText("伤害突破开关");
        LZJMS.伤害突破开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.伤害突破开关.setBorderPainted(false);
        LZJMS.伤害突破开关.setContentAreaFilled(false);
        LZJMS.伤害突破开关.setFocusPainted(false);
        LZJMS.伤害突破开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.伤害突破开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.伤害突破开关StateChanged(evt);
            }
        });
        LZJMS.伤害突破开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.伤害突破开关ActionPerformed(evt);
            }
        });
        this.jLabel26.setText("突破上线");
        LZJMS.突破上线.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.突破上线FocusLost(evt);
            }
        });
        this.jLabel27.setText("装备卡破功开关");
        LZJMS.装备卡破功开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.装备卡破功开关.setBorderPainted(false);
        LZJMS.装备卡破功开关.setContentAreaFilled(false);
        LZJMS.装备卡破功开关.setFocusPainted(false);
        LZJMS.装备卡破功开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.装备卡破功开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.装备卡破功开关StateChanged(evt);
            }
        });
        LZJMS.装备卡破功开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.装备卡破功开关ActionPerformed(evt);
            }
        });
        this.jLabel210.setText("表单卡破功开关");
        LZJMS.表单卡破功开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.表单卡破功开关.setBorderPainted(false);
        LZJMS.表单卡破功开关.setContentAreaFilled(false);
        LZJMS.表单卡破功开关.setFocusPainted(false);
        LZJMS.表单卡破功开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.表单卡破功开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.表单卡破功开关StateChanged(evt);
            }
        });
        LZJMS.表单卡破功开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.表单卡破功开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel10Layout = new GroupLayout((Container) this.jPanel10);
        this.jPanel10.setLayout((LayoutManager) jPanel10Layout);
        jPanel10Layout.setHorizontalGroup((Group) jPanel10Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel10Layout.createSequentialGroup().addGroup((Group) jPanel10Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel10Layout.createSequentialGroup().addComponent((Component) this.jLabel26).addGap(69, 69, 69).addComponent((Component) LZJMS.突破上线)).addGroup((Group) jPanel10Layout.createSequentialGroup().addComponent((Component) this.jLabel27).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.装备卡破功开关, -2, 74, -2)).addGroup(Alignment.TRAILING, (Group) jPanel10Layout.createSequentialGroup().addComponent((Component) this.jLabel13).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.所有显示开关, -2, 74, -2)).addGroup(Alignment.TRAILING, (Group) jPanel10Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent((Component) this.jLabel15).addGap(40, 40, 40).addComponent((Component) LZJMS.伤害突破开关, -2, 74, -2)).addGroup((Group) jPanel10Layout.createSequentialGroup().addComponent((Component) this.jLabel210).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.表单卡破功开关, -2, 74, -2))).addContainerGap()));
        jPanel10Layout.setVerticalGroup((Group) jPanel10Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel10Layout.createSequentialGroup().addGroup((Group) jPanel10Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.所有显示开关, -2, 30, -2).addGroup((Group) jPanel10Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jLabel13))).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel10Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel15).addComponent((Component) LZJMS.伤害突破开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel10Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel26).addComponent((Component) LZJMS.突破上线, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel10Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.装备卡破功开关, -2, 30, -2).addComponent((Component) this.jLabel27)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel10Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.表单卡破功开关, -2, 30, -2).addComponent((Component) this.jLabel210)).addGap(0, 0, 32767)));
        this.jPanel16.setBorder((Border) BorderFactory.createTitledBorder("关于怪物"));
        this.jLabel21.setText("怪物血量显示开关");
        LZJMS.怪物血量显示开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.怪物血量显示开关.setBorderPainted(false);
        LZJMS.怪物血量显示开关.setContentAreaFilled(false);
        LZJMS.怪物血量显示开关.setFocusPainted(false);
        LZJMS.怪物血量显示开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.怪物血量显示开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.怪物血量显示开关StateChanged(evt);
            }
        });
        LZJMS.怪物血量显示开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.怪物血量显示开关ActionPerformed(evt);
            }
        });
        this.jLabel22.setText("野外boss击杀广播");
        LZJMS.野外boss击杀广播.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.野外boss击杀广播.setBorderPainted(false);
        LZJMS.野外boss击杀广播.setContentAreaFilled(false);
        LZJMS.野外boss击杀广播.setFocusPainted(false);
        LZJMS.野外boss击杀广播.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.野外boss击杀广播.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.野外boss击杀广播StateChanged(evt);
            }
        });
        LZJMS.野外boss击杀广播.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.野外boss击杀广播ActionPerformed(evt);
            }
        });
        this.jLabel25.setText("boss击杀记录");
        LZJMS.boss击杀记录.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.boss击杀记录.setBorderPainted(false);
        LZJMS.boss击杀记录.setContentAreaFilled(false);
        LZJMS.boss击杀记录.setFocusPainted(false);
        LZJMS.boss击杀记录.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.boss击杀记录.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.boss击杀记录StateChanged(evt);
            }
        });
        LZJMS.boss击杀记录.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.boss击杀记录ActionPerformed(evt);
            }
        });
        LZJMS.击杀boss打开npc.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.击杀boss打开npc.setBorderPainted(false);
        LZJMS.击杀boss打开npc.setContentAreaFilled(false);
        LZJMS.击杀boss打开npc.setFocusPainted(false);
        LZJMS.击杀boss打开npc.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.击杀boss打开npc.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.击杀boss打开npcStateChanged(evt);
            }
        });
        LZJMS.击杀boss打开npc.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.击杀boss打开npcActionPerformed(evt);
            }
        });
        this.jLabel39.setText("击杀boss打开npc");
        final GroupLayout jPanel16Layout = new GroupLayout((Container) this.jPanel16);
        this.jPanel16.setLayout((LayoutManager) jPanel16Layout);
        jPanel16Layout.setHorizontalGroup((Group) jPanel16Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel16Layout.createSequentialGroup().addGroup((Group) jPanel16Layout.createParallelGroup(Alignment.TRAILING, false).addGroup((Group) jPanel16Layout.createSequentialGroup().addComponent((Component) this.jLabel39).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.击杀boss打开npc, -2, 74, -2)).addGroup(Alignment.LEADING, (Group) jPanel16Layout.createSequentialGroup().addComponent((Component) this.jLabel21).addGap(18, 18, 18).addComponent((Component) LZJMS.怪物血量显示开关, -2, 74, -2)).addGroup(Alignment.LEADING, (Group) jPanel16Layout.createSequentialGroup().addComponent((Component) this.jLabel22).addGap(18, 18, 18).addComponent((Component) LZJMS.野外boss击杀广播, -2, 74, -2)).addGroup(Alignment.LEADING, (Group) jPanel16Layout.createSequentialGroup().addComponent((Component) this.jLabel25).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.boss击杀记录, -2, 74, -2))).addGap(0, 0, 32767)));
        jPanel16Layout.setVerticalGroup((Group) jPanel16Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel16Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel16Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.怪物血量显示开关, -2, 30, -2).addComponent((Component) this.jLabel21)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel16Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel22).addComponent((Component) LZJMS.野外boss击杀广播, -2, 30, -2)).addGap(6, 6, 6).addGroup((Group) jPanel16Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel25).addComponent((Component) LZJMS.boss击杀记录, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel16Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel39).addComponent((Component) LZJMS.击杀boss打开npc, -2, 30, -2)).addContainerGap(30, 32767)));
        this.jPanel4.setBorder((Border) BorderFactory.createTitledBorder("修正内容"));
        this.jLabel28.setText("砍爆率");
        this.jLabel29.setText("小怪出装备概率");
        LZJMS.砍爆率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.砍爆率FocusLost(evt);
            }
        });
        LZJMS.出装备概率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.出装备概率FocusLost(evt);
            }
        });
        this.jLabel31.setText("战力修正");
        LZJMS.战力修正.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.战力修正.setBorderPainted(false);
        LZJMS.战力修正.setContentAreaFilled(false);
        LZJMS.战力修正.setFocusPainted(false);
        LZJMS.战力修正.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.战力修正.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.战力修正StateChanged(evt);
            }
        });
        LZJMS.战力修正.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.战力修正ActionPerformed(evt);
            }
        });
        LZJMS.BOSS出装备概率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.BOSS出装备概率FocusLost(evt);
            }
        });
        this.jLabel172.setText("BOSS出装备概率");
        this.jLabel173.setText("出装备总概率是");
        this.jLabel174.setText("10000为100%");
        final GroupLayout jPanel4Layout = new GroupLayout((Container) this.jPanel4);
        this.jPanel4.setLayout((LayoutManager) jPanel4Layout);
        jPanel4Layout.setHorizontalGroup((Group) jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel4Layout.createSequentialGroup().addGroup((Group) jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel4Layout.createSequentialGroup().addComponent((Component) this.jLabel28).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.砍爆率, -2, 69, -2)).addGroup(Alignment.TRAILING, (Group) jPanel4Layout.createSequentialGroup().addComponent((Component) this.jLabel172).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.BOSS出装备概率, -2, 69, -2)).addGroup(Alignment.TRAILING, (Group) jPanel4Layout.createSequentialGroup().addGroup((Group) jPanel4Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel29).addComponent((Component) this.jLabel173)).addGroup((Group) jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel4Layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED, 41, 32767).addComponent((Component) LZJMS.出装备概率, -2, 69, -2)).addGroup((Group) jPanel4Layout.createSequentialGroup().addGap(40, 40, 40).addComponent((Component) this.jLabel174, -1, 70, 32767)))).addGroup((Group) jPanel4Layout.createSequentialGroup().addComponent((Component) this.jLabel31).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.战力修正, -2, 74, -2))).addContainerGap()));
        jPanel4Layout.setVerticalGroup((Group) jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel4Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel4Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel28).addComponent((Component) LZJMS.砍爆率, -2, -1, -2)).addGap(6, 6, 6).addGroup((Group) jPanel4Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel29).addComponent((Component) LZJMS.出装备概率, -2, -1, -2)).addGap(6, 6, 6).addGroup((Group) jPanel4Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) LZJMS.BOSS出装备概率, -2, -1, -2).addComponent((Component) this.jLabel172)).addGap(10, 10, 10).addGroup((Group) jPanel4Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel173).addComponent((Component) this.jLabel174)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel4Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel31).addComponent((Component) LZJMS.战力修正, -2, 30, -2)).addContainerGap(42, 32767)));
        this.jPanel17.setBorder((Border) BorderFactory.createTitledBorder("宠物功能"));
        this.jLabel40.setText("宠物自动嗑药");
        LZJMS.宠物自动吃药开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.宠物自动吃药开关.setBorderPainted(false);
        LZJMS.宠物自动吃药开关.setContentAreaFilled(false);
        LZJMS.宠物自动吃药开关.setFocusPainted(false);
        LZJMS.宠物自动吃药开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.宠物自动吃药开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.宠物自动吃药开关StateChanged(evt);
            }
        });
        LZJMS.宠物自动吃药开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.宠物自动吃药开关ActionPerformed(evt);
            }
        });
        this.jLabel41.setText("自动吃药道具");
        LZJMS.自动吃药道具.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.自动吃药道具FocusLost(evt);
            }
        });
        this.jLabel89.setText("宠物不饥饿开关");
        LZJMS.宠物不饥饿开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.宠物不饥饿开关.setBorderPainted(false);
        LZJMS.宠物不饥饿开关.setContentAreaFilled(false);
        LZJMS.宠物不饥饿开关.setFocusPainted(false);
        LZJMS.宠物不饥饿开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.宠物不饥饿开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.宠物不饥饿开关StateChanged(evt);
            }
        });
        LZJMS.宠物不饥饿开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.宠物不饥饿开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel17Layout = new GroupLayout((Container) this.jPanel17);
        this.jPanel17.setLayout((LayoutManager) jPanel17Layout);
        jPanel17Layout.setHorizontalGroup((Group) jPanel17Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel17Layout.createSequentialGroup().addGroup((Group) jPanel17Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel17Layout.createSequentialGroup().addComponent((Component) this.jLabel41).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.自动吃药道具, -2, 69, -2)).addGroup((Group) jPanel17Layout.createSequentialGroup().addComponent((Component) this.jLabel40).addPreferredGap(ComponentPlacement.RELATED, 48, 32767).addComponent((Component) LZJMS.宠物自动吃药开关, -2, 74, -2)).addGroup((Group) jPanel17Layout.createSequentialGroup().addComponent((Component) this.jLabel89).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.宠物不饥饿开关, -2, 74, -2))).addContainerGap()));
        jPanel17Layout.setVerticalGroup((Group) jPanel17Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel17Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel17Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.宠物自动吃药开关, -2, 30, -2).addComponent((Component) this.jLabel40)).addGap(6, 6, 6).addGroup((Group) jPanel17Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel41).addComponent((Component) LZJMS.自动吃药道具, -2, -1, -2)).addGap(6, 6, 6).addGroup((Group) jPanel17Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel89).addComponent((Component) LZJMS.宠物不饥饿开关, -2, 30, -2)).addContainerGap(-1, 32767)));
        this.jPanel18.setBorder((Border) BorderFactory.createTitledBorder("等级连升/世界等级"));
        this.jLabel45.setText("等级连升开关");
        LZJMS.等级连升开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.等级连升开关.setBorderPainted(false);
        LZJMS.等级连升开关.setContentAreaFilled(false);
        LZJMS.等级连升开关.setFocusPainted(false);
        LZJMS.等级连升开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.等级连升开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.等级连升开关StateChanged(evt);
            }
        });
        LZJMS.等级连升开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.等级连升开关ActionPerformed(evt);
            }
        });
        this.jLabel46.setText("等级连升范围");
        this.jLabel47.setText("世界等级开关");
        LZJMS.世界等级开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.世界等级开关.setBorderPainted(false);
        LZJMS.世界等级开关.setContentAreaFilled(false);
        LZJMS.世界等级开关.setFocusPainted(false);
        LZJMS.世界等级开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.世界等级开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.世界等级开关StateChanged(evt);
            }
        });
        LZJMS.世界等级开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.世界等级开关ActionPerformed(evt);
            }
        });
        this.jLabel48.setText("世界等级范围");
        LZJMS.世界等级范围.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.世界等级范围FocusLost(evt);
            }
        });
        LZJMS.世界等级范围.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.世界等级范围ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel18Layout = new GroupLayout((Container) this.jPanel18);
        this.jPanel18.setLayout((LayoutManager) jPanel18Layout);
        jPanel18Layout.setHorizontalGroup((Group) jPanel18Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel18Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel18Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel18Layout.createSequentialGroup().addGroup((Group) jPanel18Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel18Layout.createSequentialGroup().addComponent((Component) this.jLabel47).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.世界等级开关, -2, 74, -2)).addGroup((Group) jPanel18Layout.createSequentialGroup().addGroup((Group) jPanel18Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel18Layout.createSequentialGroup().addComponent((Component) this.jLabel45).addGap(18, 18, 18).addComponent((Component) LZJMS.等级连升开关, -2, 74, -2)).addGroup((Group) jPanel18Layout.createSequentialGroup().addComponent((Component) this.jLabel46).addGap(18, 18, 18).addComponent((Component) LZJMS.等级范围, -2, 66, -2))).addGap(0, 0, 32767))).addContainerGap(-1, 32767)).addGroup((Group) jPanel18Layout.createSequentialGroup().addComponent((Component) this.jLabel48).addGap(18, 18, 18).addComponent((Component) LZJMS.世界等级范围, -2, 66, -2).addContainerGap(-1, 32767)))));
        jPanel18Layout.setVerticalGroup((Group) jPanel18Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel18Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel18Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel45).addComponent((Component) LZJMS.等级连升开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel18Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel46).addComponent((Component) LZJMS.等级范围, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel18Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.世界等级开关, -2, 30, -2).addComponent((Component) this.jLabel47)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel18Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel48).addComponent((Component) LZJMS.世界等级范围, -2, -1, -2)).addContainerGap(31, 32767)));
        this.jPanel20.setBorder((Border) BorderFactory.createTitledBorder("成就系统"));
        this.jLabel49.setText("还原上卷记录");
        LZJMS.成就还原上卷记录开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.成就还原上卷记录开关.setBorderPainted(false);
        LZJMS.成就还原上卷记录开关.setContentAreaFilled(false);
        LZJMS.成就还原上卷记录开关.setFocusPainted(false);
        LZJMS.成就还原上卷记录开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.成就还原上卷记录开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.成就还原上卷记录开关StateChanged(evt);
            }
        });
        LZJMS.成就还原上卷记录开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.成就还原上卷记录开关ActionPerformed(evt);
            }
        });
        this.jLabel50.setText("卷轴加三记录");
        LZJMS.成就上卷加三记录开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.成就上卷加三记录开关.setBorderPainted(false);
        LZJMS.成就上卷加三记录开关.setContentAreaFilled(false);
        LZJMS.成就上卷加三记录开关.setFocusPainted(false);
        LZJMS.成就上卷加三记录开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.成就上卷加三记录开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.成就上卷加三记录开关StateChanged(evt);
            }
        });
        LZJMS.成就上卷加三记录开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.成就上卷加三记录开关ActionPerformed(evt);
            }
        });
        this.jLabel51.setText("卷轴加七记录");
        LZJMS.成就上卷加七记录开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.成就上卷加七记录开关.setBorderPainted(false);
        LZJMS.成就上卷加七记录开关.setContentAreaFilled(false);
        LZJMS.成就上卷加七记录开关.setFocusPainted(false);
        LZJMS.成就上卷加七记录开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.成就上卷加七记录开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.成就上卷加七记录开关StateChanged(evt);
            }
        });
        LZJMS.成就上卷加七记录开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.成就上卷加七记录开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel20Layout = new GroupLayout((Container) this.jPanel20);
        this.jPanel20.setLayout((LayoutManager) jPanel20Layout);
        jPanel20Layout.setHorizontalGroup((Group) jPanel20Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel20Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel20Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel20Layout.createSequentialGroup().addComponent((Component) this.jLabel49).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.成就还原上卷记录开关, -2, 74, -2)).addGroup((Group) jPanel20Layout.createSequentialGroup().addComponent((Component) this.jLabel50).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.成就上卷加三记录开关, -2, 74, -2)).addGroup((Group) jPanel20Layout.createSequentialGroup().addComponent((Component) this.jLabel51).addGap(18, 18, 18).addComponent((Component) LZJMS.成就上卷加七记录开关, -2, 74, -2).addGap(0, 0, 32767))).addContainerGap()));
        jPanel20Layout.setVerticalGroup((Group) jPanel20Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel20Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel20Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel49).addComponent((Component) LZJMS.成就还原上卷记录开关, -2, 30, -2)).addGap(6, 6, 6).addGroup((Group) jPanel20Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel50).addComponent((Component) LZJMS.成就上卷加三记录开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel20Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.成就上卷加七记录开关, -2, 30, -2).addComponent((Component) this.jLabel51)).addContainerGap(-1, 32767)));
        this.jPanel37.setBorder((Border) BorderFactory.createTitledBorder("关于金锤子"));
        this.jLabel83.setText("是否可用金锤子");
        LZJMS.金锤子使用开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.金锤子使用开关.setBorderPainted(false);
        LZJMS.金锤子使用开关.setContentAreaFilled(false);
        LZJMS.金锤子使用开关.setFocusPainted(false);
        LZJMS.金锤子使用开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.金锤子使用开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.金锤子使用开关StateChanged(evt);
            }
        });
        LZJMS.金锤子使用开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.金锤子使用开关ActionPerformed(evt);
            }
        });
        this.jLabel85.setText("金锤子使用概率%");
        LZJMS.金锤子使用概率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.金锤子使用概率FocusLost(evt);
            }
        });
        LZJMS.金锤子使用概率.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.金锤子使用概率ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel37Layout = new GroupLayout((Container) this.jPanel37);
        this.jPanel37.setLayout((LayoutManager) jPanel37Layout);
        jPanel37Layout.setHorizontalGroup((Group) jPanel37Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel37Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel37Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel37Layout.createSequentialGroup().addComponent((Component) this.jLabel83).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.金锤子使用开关, -2, 74, -2)).addGroup((Group) jPanel37Layout.createSequentialGroup().addComponent((Component) this.jLabel85).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.金锤子使用概率, -2, 66, -2))).addContainerGap()));
        jPanel37Layout.setVerticalGroup((Group) jPanel37Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel37Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel37Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel83).addComponent((Component) LZJMS.金锤子使用开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel37Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel85).addComponent((Component) LZJMS.金锤子使用概率, -2, -1, -2)).addContainerGap(-1, 32767)));
        this.jPanel52.setBorder((Border) BorderFactory.createTitledBorder("关于GM固伤"));
        this.jLabel163.setText("是否开起GM固伤");
        LZJMS.GM固伤开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.GM固伤开关.setBorderPainted(false);
        LZJMS.GM固伤开关.setContentAreaFilled(false);
        LZJMS.GM固伤开关.setFocusPainted(false);
        LZJMS.GM固伤开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.GM固伤开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.GM固伤开关StateChanged(evt);
            }
        });
        LZJMS.GM固伤开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.GM固伤开关ActionPerformed(evt);
            }
        });
        this.jLabel164.setText("GM固伤数值");
        LZJMS.GM固伤伤害.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.GM固伤伤害FocusLost(evt);
            }
        });
        LZJMS.GM固伤伤害.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.GM固伤伤害ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel52Layout = new GroupLayout((Container) this.jPanel52);
        this.jPanel52.setLayout((LayoutManager) jPanel52Layout);
        jPanel52Layout.setHorizontalGroup((Group) jPanel52Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel52Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel52Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel52Layout.createSequentialGroup().addComponent((Component) this.jLabel163).addGap(18, 18, 18).addComponent((Component) LZJMS.GM固伤开关, -2, 66, -2)).addGroup((Group) jPanel52Layout.createSequentialGroup().addComponent((Component) this.jLabel164).addGap(42, 42, 42).addComponent((Component) LZJMS.GM固伤伤害, -2, 66, -2))).addContainerGap(14, 32767)));
        jPanel52Layout.setVerticalGroup((Group) jPanel52Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel52Layout.createSequentialGroup().addGap(6, 6, 6).addGroup((Group) jPanel52Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel163).addComponent((Component) LZJMS.GM固伤开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel52Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel164).addComponent((Component) LZJMS.GM固伤伤害, -2, -1, -2)).addContainerGap(-1, 32767)));
        this.jPanel55.setBorder((Border) BorderFactory.createTitledBorder("关于金币"));
        this.jLabel30.setText("金币重置");
        LZJMS.金币重置.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.金币重置.setBorderPainted(false);
        LZJMS.金币重置.setContentAreaFilled(false);
        LZJMS.金币重置.setFocusPainted(false);
        LZJMS.金币重置.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.金币重置.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.金币重置StateChanged(evt);
            }
        });
        LZJMS.金币重置.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.金币重置ActionPerformed(evt);
            }
        });
        this.jLabel146.setText("金币全局砍数量");
        LZJMS.金币全局砍数量.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.金币全局砍数量.setBorderPainted(false);
        LZJMS.金币全局砍数量.setContentAreaFilled(false);
        LZJMS.金币全局砍数量.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.金币全局砍数量.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.金币全局砍数量StateChanged(evt);
            }
        });
        LZJMS.金币全局砍数量.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.金币全局砍数量ActionPerformed(evt);
            }
        });
        this.jLabel147.setText("金币砍全局倍率");
        LZJMS.金币砍全局倍率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.金币砍全局倍率FocusLost(evt);
            }
        });
        LZJMS.金币砍全局倍率.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.金币砍全局倍率ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel55Layout = new GroupLayout((Container) this.jPanel55);
        this.jPanel55.setLayout((LayoutManager) jPanel55Layout);
        jPanel55Layout.setHorizontalGroup((Group) jPanel55Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel55Layout.createSequentialGroup().addGroup((Group) jPanel55Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel55Layout.createSequentialGroup().addComponent((Component) this.jLabel30).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.金币重置, -2, 74, -2)).addGroup(Alignment.TRAILING, (Group) jPanel55Layout.createSequentialGroup().addGroup((Group) jPanel55Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel146).addComponent((Component) this.jLabel147)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel55Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.金币砍全局倍率, -2, 66, -2).addComponent((Component) LZJMS.金币全局砍数量, -2, 74, -2)))).addContainerGap()));
        jPanel55Layout.setVerticalGroup((Group) jPanel55Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel55Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel55Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel30).addComponent((Component) LZJMS.金币重置, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel55Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel146).addComponent((Component) LZJMS.金币全局砍数量, -2, 30, -2)).addGap(10, 10, 10).addGroup((Group) jPanel55Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel147).addComponent((Component) LZJMS.金币砍全局倍率, -2, -1, -2)).addContainerGap(-1, 32767)));
        this.jPanel94.setBorder((Border) BorderFactory.createTitledBorder("越级带人系统"));
        this.jLabel200.setText("越级带人开关");
        LZJMS.越级带人开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.越级带人开关.setBorderPainted(false);
        LZJMS.越级带人开关.setContentAreaFilled(false);
        LZJMS.越级带人开关.setFocusPainted(false);
        LZJMS.越级带人开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.越级带人开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.越级带人开关StateChanged(evt);
            }
        });
        LZJMS.越级带人开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.越级带人开关ActionPerformed(evt);
            }
        });
        this.jLabel201.setText("越级带人道具开关");
        LZJMS.越级带人道具开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.越级带人道具开关.setBorderPainted(false);
        LZJMS.越级带人道具开关.setContentAreaFilled(false);
        LZJMS.越级带人道具开关.setFocusPainted(false);
        LZJMS.越级带人道具开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.越级带人道具开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.越级带人道具开关StateChanged(evt);
            }
        });
        LZJMS.越级带人道具开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.越级带人道具开关ActionPerformed(evt);
            }
        });
        this.jLabel202.setText("越级带人道具");
        LZJMS.越级带人道具.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.越级带人道具FocusLost(evt);
            }
        });
        LZJMS.越级带人道具.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.越级带人道具ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel94Layout = new GroupLayout((Container) this.jPanel94);
        this.jPanel94.setLayout((LayoutManager) jPanel94Layout);
        jPanel94Layout.setHorizontalGroup((Group) jPanel94Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel94Layout.createSequentialGroup().addComponent((Component) this.jLabel200).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.越级带人开关, -2, 74, -2)).addGroup((Group) jPanel94Layout.createSequentialGroup().addComponent((Component) this.jLabel201).addGap(18, 18, 18).addComponent((Component) LZJMS.越级带人道具开关, -2, 74, -2).addGap(0, 0, 32767)).addGroup((Group) jPanel94Layout.createSequentialGroup().addComponent((Component) this.jLabel202).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.越级带人道具, -2, 66, -2)));
        jPanel94Layout.setVerticalGroup((Group) jPanel94Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel94Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel94Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel200).addComponent((Component) LZJMS.越级带人开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel94Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.越级带人道具开关, -2, 30, -2).addComponent((Component) this.jLabel201)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel94Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel202).addComponent((Component) LZJMS.越级带人道具, -2, -1, -2)).addContainerGap(-1, 32767)));
        this.jPanel95.setBorder((Border) BorderFactory.createTitledBorder("骑宠功能"));
        this.jLabel42.setText("坐骑回复开关");
        LZJMS.坐骑恢复开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.坐骑恢复开关.setBorderPainted(false);
        LZJMS.坐骑恢复开关.setContentAreaFilled(false);
        LZJMS.坐骑恢复开关.setFocusPainted(false);
        LZJMS.坐骑恢复开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.坐骑恢复开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.坐骑恢复开关StateChanged(evt);
            }
        });
        LZJMS.坐骑恢复开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.坐骑恢复开关ActionPerformed(evt);
            }
        });
        this.jLabel43.setText("坐骑回复频率");
        LZJMS.坐骑恢复频率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.坐骑恢复频率FocusLost(evt);
            }
        });
        this.jLabel44.setText("坐骑回复道具");
        LZJMS.坐骑恢复道具.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.坐骑恢复道具FocusLost(evt);
            }
        });
        this.jLabel206.setText("坐骑不饥饿开关");
        LZJMS.坐骑不饥饿开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.坐骑不饥饿开关.setBorderPainted(false);
        LZJMS.坐骑不饥饿开关.setContentAreaFilled(false);
        LZJMS.坐骑不饥饿开关.setFocusPainted(false);
        LZJMS.坐骑不饥饿开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.坐骑不饥饿开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.坐骑不饥饿开关StateChanged(evt);
            }
        });
        LZJMS.坐骑不饥饿开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.坐骑不饥饿开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel95Layout = new GroupLayout((Container) this.jPanel95);
        this.jPanel95.setLayout((LayoutManager) jPanel95Layout);
        jPanel95Layout.setHorizontalGroup((Group) jPanel95Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel95Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel95Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel95Layout.createSequentialGroup().addComponent((Component) this.jLabel42).addPreferredGap(ComponentPlacement.RELATED, 38, 32767).addComponent((Component) LZJMS.坐骑恢复开关, -2, 74, -2)).addGroup((Group) jPanel95Layout.createSequentialGroup().addComponent((Component) this.jLabel43).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.坐骑恢复频率, -2, 69, -2)).addGroup((Group) jPanel95Layout.createSequentialGroup().addComponent((Component) this.jLabel44).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.坐骑恢复道具, -2, 69, -2)).addGroup((Group) jPanel95Layout.createSequentialGroup().addComponent((Component) this.jLabel206).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.坐骑不饥饿开关, -2, 74, -2))).addContainerGap()));
        jPanel95Layout.setVerticalGroup((Group) jPanel95Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel95Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel95Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel42).addComponent((Component) LZJMS.坐骑恢复开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel95Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel43).addComponent((Component) LZJMS.坐骑恢复频率, -2, -1, -2)).addGap(6, 6, 6).addGroup((Group) jPanel95Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel44).addComponent((Component) LZJMS.坐骑恢复道具, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel95Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel206).addComponent((Component) LZJMS.坐骑不饥饿开关, -2, 30, -2)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel12Layout = new GroupLayout((Container) LZJMS.jPanel12);
        LZJMS.jPanel12.setLayout((LayoutManager) jPanel12Layout);
        jPanel12Layout.setHorizontalGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel12Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component) this.jPanel18, -2, 0, 32767).addComponent((Component) LZJMS.jPanel44, -1, -1, 32767).addComponent((Component) this.jPanel64, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel20, -1, -1, 32767).addComponent((Component) this.jPanel73, -1, -1, 32767).addComponent((Component) this.jPanel53, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel48, -1, -1, 32767).addComponent((Component) this.jPanel6, -1, -1, 32767).addComponent((Component) this.jPanel37, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel49, -1, -1, 32767).addComponent((Component) this.jPanel7, -1, -1, 32767).addComponent((Component) this.jPanel52, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel54, -1, -1, 32767).addComponent((Component) this.jPanel8, -1, -1, 32767).addComponent((Component) this.jPanel55, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel10, -2, 199, -2).addComponent((Component) this.jPanel16, -2, 0, 32767).addComponent((Component) this.jPanel94, -2, 0, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel4, -1, -1, 32767).addComponent((Component) this.jPanel17, -1, -1, 32767).addComponent((Component) this.jPanel95, -1, -1, 32767)).addGap(479, 479, 479)));
        jPanel12Layout.setVerticalGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel12Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel10, -1, -1, 32767).addComponent((Component) this.jPanel54, -1, -1, 32767).addComponent((Component) this.jPanel49, -1, -1, 32767).addComponent((Component) this.jPanel53, -1, -1, 32767).addComponent((Component) this.jPanel48, -1, -1, 32767).addComponent((Component) LZJMS.jPanel44, -1, -1, 32767).addGroup((Group) jPanel12Layout.createSequentialGroup().addComponent((Component) this.jPanel4, -2, -1, -2).addGap(0, 0, 32767))).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel64, -1, -1, 32767).addComponent((Component) this.jPanel73, -1, -1, 32767).addComponent((Component) this.jPanel6, -1, -1, 32767).addComponent((Component) this.jPanel7, -1, -1, 32767).addComponent((Component) this.jPanel17, -1, -1, 32767).addComponent((Component) this.jPanel16, -1, -1, 32767).addComponent((Component) this.jPanel8, -1, -1, 32767)).addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group) jPanel12Layout.createSequentialGroup().addGap(10, 10, 10).addGroup((Group) jPanel12Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel18, -1, -1, 32767).addComponent((Component) this.jPanel20, -1, -1, 32767).addComponent((Component) this.jPanel37, -1, -1, 32767).addComponent((Component) this.jPanel55, -1, -1, 32767).addComponent((Component) this.jPanel52, -1, -1, 32767).addComponent((Component) this.jPanel94, -1, -1, 32767))).addGroup((Group) jPanel12Layout.createSequentialGroup().addGap(18, 18, 18).addComponent((Component) this.jPanel95, -1, -1, 32767))).addGap(175, 175, 175)));
        this.jPanel54.setBorder((Border) BorderFactory.createTitledBorder("服务器倍率[重启生效]"));
        this.jPanel49.setBorder((Border) BorderFactory.createTitledBorder("职业/等级"));
        this.jPanel64.setBorder((Border) BorderFactory.createTitledBorder("多开限制"));
        this.宠吸功能面板.addTab("基础设置", (Component) LZJMS.jPanel12);
        this.jPanel76.setBorder((Border) BorderFactory.createTitledBorder("泡点配置"));
        LZJMS.泡点配置表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"类型", "数值"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane5.setViewportView((Component) LZJMS.泡点配置表);
        this.泡点配置说明.setText("说明");
        this.泡点配置说明.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.泡点配置说明ActionPerformed(evt);
            }
        });
        this.泡点配置修改.setText("修改");
        this.泡点配置修改.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.泡点配置修改ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel76Layout = new GroupLayout((Container) this.jPanel76);
        this.jPanel76.setLayout((LayoutManager) jPanel76Layout);
        jPanel76Layout.setHorizontalGroup((Group) jPanel76Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel76Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel76Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane5, -2, 0, 32767).addGroup((Group) jPanel76Layout.createSequentialGroup().addComponent((Component) this.泡点配置说明, -2, 79, -2).addPreferredGap(ComponentPlacement.RELATED, 60, 32767).addComponent((Component) this.泡点配置修改, -2, 79, -2))).addContainerGap()));
        jPanel76Layout.setVerticalGroup((Group) jPanel76Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel76Layout.createSequentialGroup().addComponent((Component) this.jScrollPane5, -1, 312, 32767).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel76Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.泡点配置说明).addComponent((Component) this.泡点配置修改))));
        this.jPanel77.setBorder((Border) BorderFactory.createTitledBorder("离线泡点"));
        LZJMS.离线泡点配置表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"类型", "数值"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane6.setViewportView((Component) LZJMS.离线泡点配置表);
        this.离线泡点说明.setText("说明");
        this.离线泡点说明.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.离线泡点说明ActionPerformed(evt);
            }
        });
        this.离线泡点修改.setText("修改");
        this.离线泡点修改.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.离线泡点修改ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel77Layout = new GroupLayout((Container) this.jPanel77);
        this.jPanel77.setLayout((LayoutManager) jPanel77Layout);
        jPanel77Layout.setHorizontalGroup((Group) jPanel77Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel77Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel77Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane6, -2, 0, 32767).addGroup(Alignment.TRAILING, (Group) jPanel77Layout.createSequentialGroup().addComponent((Component) this.离线泡点说明, -2, 79, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.离线泡点修改, -2, 79, -2))).addContainerGap()));
        jPanel77Layout.setVerticalGroup((Group) jPanel77Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel77Layout.createSequentialGroup().addComponent((Component) this.jScrollPane6, -1, 312, 32767).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel77Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.离线泡点修改).addComponent((Component) this.离线泡点说明))));
        this.jPanel19.setBorder((Border) BorderFactory.createTitledBorder("泡点配置"));
        this.jLabel52.setText("泡点开关");
        LZJMS.泡点开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.泡点开关.setBorderPainted(false);
        LZJMS.泡点开关.setContentAreaFilled(false);
        LZJMS.泡点开关.setFocusPainted(false);
        LZJMS.泡点开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.泡点开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.泡点开关StateChanged(evt);
            }
        });
        LZJMS.泡点开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.泡点开关ActionPerformed(evt);
            }
        });
        this.jLabel53.setText("跟等级有关");
        LZJMS.泡点等级开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.泡点等级开关.setBorderPainted(false);
        LZJMS.泡点等级开关.setContentAreaFilled(false);
        LZJMS.泡点等级开关.setFocusPainted(false);
        LZJMS.泡点等级开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.泡点等级开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.泡点等级开关StateChanged(evt);
            }
        });
        LZJMS.泡点等级开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.泡点等级开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel19Layout = new GroupLayout((Container) this.jPanel19);
        this.jPanel19.setLayout((LayoutManager) jPanel19Layout);
        jPanel19Layout.setHorizontalGroup((Group) jPanel19Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel19Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel19Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel19Layout.createSequentialGroup().addComponent((Component) this.jLabel52).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.泡点开关, -2, 74, -2)).addGroup((Group) jPanel19Layout.createSequentialGroup().addComponent((Component) this.jLabel53).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.泡点等级开关, -2, 74, -2))).addContainerGap()));
        jPanel19Layout.setVerticalGroup((Group) jPanel19Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel19Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel19Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel52).addComponent((Component) LZJMS.泡点开关, Alignment.LEADING, -2, 30, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel19Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel53).addComponent((Component) LZJMS.泡点等级开关, -2, 30, -2)).addContainerGap(-1, 32767)));
        this.jPanel21.setBorder((Border) BorderFactory.createTitledBorder("离线泡点"));
        this.jLabel54.setText("离线泡点开关");
        LZJMS.离线泡点开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.离线泡点开关.setBorderPainted(false);
        LZJMS.离线泡点开关.setContentAreaFilled(false);
        LZJMS.离线泡点开关.setFocusPainted(false);
        LZJMS.离线泡点开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.离线泡点开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.离线泡点开关StateChanged(evt);
            }
        });
        LZJMS.离线泡点开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.离线泡点开关ActionPerformed(evt);
            }
        });
        this.jLabel55.setText("跟等级有关");
        LZJMS.离线泡点等级开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.离线泡点等级开关.setBorderPainted(false);
        LZJMS.离线泡点等级开关.setContentAreaFilled(false);
        LZJMS.离线泡点等级开关.setFocusPainted(false);
        LZJMS.离线泡点等级开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.离线泡点等级开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.离线泡点等级开关StateChanged(evt);
            }
        });
        LZJMS.离线泡点等级开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.离线泡点等级开关ActionPerformed(evt);
            }
        });
        this.jLabel56.setText("在线时间获取");
        LZJMS.离线给在线时间开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.离线给在线时间开关.setBorderPainted(false);
        LZJMS.离线给在线时间开关.setContentAreaFilled(false);
        LZJMS.离线给在线时间开关.setFocusPainted(false);
        LZJMS.离线给在线时间开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.离线给在线时间开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.离线给在线时间开关StateChanged(evt);
            }
        });
        LZJMS.离线给在线时间开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.离线给在线时间开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel21Layout = new GroupLayout((Container) this.jPanel21);
        this.jPanel21.setLayout((LayoutManager) jPanel21Layout);
        jPanel21Layout.setHorizontalGroup((Group) jPanel21Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel21Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel21Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel21Layout.createSequentialGroup().addComponent((Component) this.jLabel54).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.离线泡点开关, -2, 74, -2)).addGroup((Group) jPanel21Layout.createSequentialGroup().addComponent((Component) this.jLabel55).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.离线泡点等级开关, -2, 74, -2)).addGroup((Group) jPanel21Layout.createSequentialGroup().addComponent((Component) this.jLabel56).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.离线给在线时间开关, -2, 74, -2))).addContainerGap()));
        jPanel21Layout.setVerticalGroup((Group) jPanel21Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel21Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel21Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel54).addComponent((Component) LZJMS.离线泡点开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel21Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel55).addComponent((Component) LZJMS.离线泡点等级开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel21Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel56).addComponent((Component) LZJMS.离线给在线时间开关, -2, 30, -2)).addContainerGap(78, 32767)));
        this.jPanel75.setBorder((Border) BorderFactory.createTitledBorder("不同等级不同经验"));
        LZJMS.阶段经验配置表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"类型", "数值"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane7.setViewportView((Component) LZJMS.阶段经验配置表);
        if (LZJMS.阶段经验配置表.getColumnModel().getColumnCount() > 0) {
            LZJMS.阶段经验配置表.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.阶段经验配置表.getColumnModel().getColumn(1).setPreferredWidth(20);
        }
        this.经验加成配置说明1.setText("说明");
        this.经验加成配置说明1.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.经验加成配置说明1ActionPerformed(evt);
            }
        });
        this.经验加成配置修改1.setText("修改");
        this.经验加成配置修改1.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.经验加成配置修改1ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel75Layout = new GroupLayout((Container) this.jPanel75);
        this.jPanel75.setLayout((LayoutManager) jPanel75Layout);
        jPanel75Layout.setHorizontalGroup((Group) jPanel75Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel75Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel75Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane7, -2, 0, 32767).addGroup(Alignment.TRAILING, (Group) jPanel75Layout.createSequentialGroup().addComponent((Component) this.经验加成配置说明1, -2, 79, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.经验加成配置修改1, -2, 79, -2))).addContainerGap()));
        jPanel75Layout.setVerticalGroup((Group) jPanel75Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel75Layout.createSequentialGroup().addComponent((Component) this.jScrollPane7, -1, 312, 32767).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel75Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.经验加成配置修改1).addComponent((Component) this.经验加成配置说明1))));
        this.jPanel25.setBorder((Border) BorderFactory.createTitledBorder("不同等级不同经验"));
        this.jLabel57.setText("阶段经验开关");
        LZJMS.阶段经验开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.阶段经验开关.setBorderPainted(false);
        LZJMS.阶段经验开关.setContentAreaFilled(false);
        LZJMS.阶段经验开关.setFocusPainted(false);
        LZJMS.阶段经验开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.阶段经验开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.阶段经验开关StateChanged(evt);
            }
        });
        LZJMS.阶段经验开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.阶段经验开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel25Layout = new GroupLayout((Container) this.jPanel25);
        this.jPanel25.setLayout((LayoutManager) jPanel25Layout);
        jPanel25Layout.setHorizontalGroup((Group) jPanel25Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel25Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jLabel57).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.阶段经验开关, -2, 74, -2).addContainerGap()));
        jPanel25Layout.setVerticalGroup((Group) jPanel25Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel25Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel25Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel57).addComponent((Component) LZJMS.阶段经验开关, -2, 30, -2)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel3Layout = new GroupLayout((Container) this.jPanel3);
        this.jPanel3.setLayout((LayoutManager) jPanel3Layout);
        jPanel3Layout.setHorizontalGroup((Group) jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel3Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel3Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel76, -2, -1, -2).addComponent((Component) this.jPanel19, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel3Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel21, -1, -1, 32767).addComponent((Component) this.jPanel77, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel3Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel25, -1, -1, 32767).addComponent((Component) this.jPanel75, -1, -1, 32767)).addGap(657, 657, 657)));
        jPanel3Layout.setVerticalGroup((Group) jPanel3Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel3Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel3Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel75, -1, -1, -2).addComponent((Component) this.jPanel77, Alignment.TRAILING, -1, -1, -2).addComponent((Component) this.jPanel76, Alignment.TRAILING, -1, -1, -2)).addPreferredGap(ComponentPlacement.RELATED, 7, 32767).addGroup((Group) jPanel3Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel19, -1, -1, 32767).addComponent((Component) this.jPanel21, -1, -1, 32767).addComponent((Component) this.jPanel25, -1, -1, 32767)).addContainerGap(94, 32767)));
        this.宠吸功能面板.addTab("配置修改", (Component) this.jPanel3);
        this.jPanel83.setBorder((Border) BorderFactory.createTitledBorder("不参与叠加道具"));
        LZJMS.不参与叠加道具.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"排序", "物品id"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane8.setViewportView((Component) LZJMS.不参与叠加道具);
        if (LZJMS.不参与叠加道具.getColumnModel().getColumnCount() > 0) {
            LZJMS.不参与叠加道具.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.不参与叠加道具.getColumnModel().getColumn(1).setPreferredWidth(20);
        }
        this.不参与叠加道具增加.setText("增加");
        this.不参与叠加道具增加.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.不参与叠加道具增加ActionPerformed(evt);
            }
        });
        this.不参与叠加道具删除.setText("删除");
        this.不参与叠加道具删除.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.不参与叠加道具删除ActionPerformed(evt);
            }
        });
        this.jLabel119.setText("排序:");
        this.jLabel120.setText("物品id:");
        LZJMS.不参与叠加排序.setEnabled(false);
        final GroupLayout jPanel83Layout = new GroupLayout((Container) this.jPanel83);
        this.jPanel83.setLayout((LayoutManager) jPanel83Layout);
        jPanel83Layout.setHorizontalGroup((Group) jPanel83Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel83Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel83Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane8, -2, 0, 32767).addGroup((Group) jPanel83Layout.createSequentialGroup().addGroup((Group) jPanel83Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.不参与叠加道具增加, -2, 79, -2).addComponent((Component) this.jLabel119).addComponent((Component) this.jLabel120)).addPreferredGap(ComponentPlacement.RELATED, 84, 32767).addGroup((Group) jPanel83Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.不参与叠加道具删除, Alignment.TRAILING, -2, 79, -2).addComponent((Component) LZJMS.不参与叠加排序, Alignment.TRAILING, -2, 69, -2).addComponent((Component) LZJMS.不参与道具叠加源代码, Alignment.TRAILING, -2, 69, -2)))).addContainerGap()));
        jPanel83Layout.setVerticalGroup((Group) jPanel83Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel83Layout.createSequentialGroup().addComponent((Component) this.jScrollPane8, -2, 210, -2).addGap(18, 18, 18).addGroup((Group) jPanel83Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel119).addComponent((Component) LZJMS.不参与叠加排序, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel83Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.不参与道具叠加源代码, -2, -1, -2).addComponent((Component) this.jLabel120)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel83Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.不参与叠加道具删除).addComponent((Component) this.不参与叠加道具增加))));
        this.jPanel26.setBorder((Border) BorderFactory.createTitledBorder("不参与叠加道具"));
        this.jLabel59.setText("叠加开关");
        LZJMS.叠加开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.叠加开关.setBorderPainted(false);
        LZJMS.叠加开关.setContentAreaFilled(false);
        LZJMS.叠加开关.setFocusPainted(false);
        LZJMS.叠加开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.叠加开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.叠加开关StateChanged(evt);
            }
        });
        LZJMS.叠加开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.叠加开关ActionPerformed(evt);
            }
        });
        this.jLabel60.setText("叠加上线");
        LZJMS.叠加上线.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.叠加上线FocusLost(evt);
            }
        });
        this.jLabel91.setText("不参与叠加开关");
        LZJMS.不参与叠加开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.不参与叠加开关.setBorderPainted(false);
        LZJMS.不参与叠加开关.setContentAreaFilled(false);
        LZJMS.不参与叠加开关.setFocusPainted(false);
        LZJMS.不参与叠加开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.不参与叠加开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.不参与叠加开关StateChanged(evt);
            }
        });
        LZJMS.不参与叠加开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.不参与叠加开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel26Layout = new GroupLayout((Container) this.jPanel26);
        this.jPanel26.setLayout((LayoutManager) jPanel26Layout);
        jPanel26Layout.setHorizontalGroup((Group) jPanel26Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel26Layout.createSequentialGroup().addGroup((Group) jPanel26Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel26Layout.createSequentialGroup().addGap(10, 10, 10).addComponent((Component) this.jLabel59).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.叠加开关, -2, 74, -2)).addGroup((Group) jPanel26Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jLabel60).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.叠加上线, -2, 69, -2)).addGroup((Group) jPanel26Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jLabel91).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.不参与叠加开关, -2, 74, -2))).addContainerGap()));
        jPanel26Layout.setVerticalGroup((Group) jPanel26Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel26Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel26Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel59).addComponent((Component) LZJMS.叠加开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel26Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel60).addComponent((Component) LZJMS.叠加上线, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel26Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.不参与叠加开关, -2, 30, -2).addComponent((Component) this.jLabel91)).addContainerGap(74, 32767)));
        this.jPanel63.setBorder((Border) BorderFactory.createTitledBorder("道具经验加成"));
        LZJMS.经验加成表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"道具代码", "加成比例"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane3.setViewportView((Component) LZJMS.经验加成表);
        if (LZJMS.经验加成表.getColumnModel().getColumnCount() > 0) {
            LZJMS.经验加成表.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.经验加成表.getColumnModel().getColumn(1).setPreferredWidth(20);
        }
        this.道具经验加成增加.setText("增加");
        this.道具经验加成增加.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.道具经验加成增加ActionPerformed(evt);
            }
        });
        this.道具经验加成修改按钮.setText("修改");
        this.道具经验加成修改按钮.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.道具经验加成修改按钮ActionPerformed(evt);
            }
        });
        this.道具经验加成修改删除.setText("删除");
        this.道具经验加成修改删除.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.道具经验加成修改删除ActionPerformed(evt);
            }
        });
        this.jLabel108.setText("道具代码:");
        this.jLabel110.setText("道具代码:");
        this.jLabel114.setText("加成比例:");
        LZJMS.原道具经验加成代码.setEnabled(false);
        final GroupLayout jPanel63Layout = new GroupLayout((Container) this.jPanel63);
        this.jPanel63.setLayout((LayoutManager) jPanel63Layout);
        jPanel63Layout.setHorizontalGroup((Group) jPanel63Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel63Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel63Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group) jPanel63Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent((Component) this.道具经验加成增加, -2, 79, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.道具经验加成修改删除, -2, 79, -2).addGap(6, 6, 6).addComponent((Component) this.道具经验加成修改按钮, -2, 79, -2)).addGroup(Alignment.TRAILING, (Group) jPanel63Layout.createSequentialGroup().addComponent((Component) this.jScrollPane3, -2, 0, 32767).addGap(2, 2, 2)).addGroup(Alignment.TRAILING, (Group) jPanel63Layout.createSequentialGroup().addGap(9, 9, 9).addGroup((Group) jPanel63Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component) this.jLabel110).addComponent((Component) this.jLabel108).addComponent((Component) this.jLabel114)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel63Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.道具经验加成代码, Alignment.TRAILING, -2, 69, -2).addComponent((Component) LZJMS.道具经验加成比例, Alignment.TRAILING, -2, 69, -2).addComponent((Component) LZJMS.原道具经验加成代码, Alignment.TRAILING, -2, 69, -2)))).addContainerGap()));
        jPanel63Layout.setVerticalGroup((Group) jPanel63Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel63Layout.createSequentialGroup().addComponent((Component) this.jScrollPane3, -2, 210, -2).addGap(18, 18, 18).addGroup((Group) jPanel63Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.原道具经验加成代码, -2, -1, -2).addComponent((Component) this.jLabel108)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel63Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel110).addComponent((Component) LZJMS.道具经验加成代码, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel63Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel114).addComponent((Component) LZJMS.道具经验加成比例, -2, -1, -2)).addGap(26, 26, 26).addGroup((Group) jPanel63Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.道具经验加成修改按钮).addComponent((Component) this.道具经验加成增加).addComponent((Component) this.道具经验加成修改删除))));
        this.jPanel22.setBorder((Border) BorderFactory.createTitledBorder("道具经验加成"));
        this.jLabel58.setText("道具经验开关");
        LZJMS.道具经验开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.道具经验开关.setBorderPainted(false);
        LZJMS.道具经验开关.setContentAreaFilled(false);
        LZJMS.道具经验开关.setFocusPainted(false);
        LZJMS.道具经验开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.道具经验开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.道具经验开关StateChanged(evt);
            }
        });
        LZJMS.道具经验开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.道具经验开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel22Layout = new GroupLayout((Container) this.jPanel22);
        this.jPanel22.setLayout((LayoutManager) jPanel22Layout);
        jPanel22Layout.setHorizontalGroup((Group) jPanel22Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel22Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jLabel58).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.道具经验开关, -2, 74, -2).addContainerGap()));
        jPanel22Layout.setVerticalGroup((Group) jPanel22Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel22Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel22Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.道具经验开关, -2, 30, -2).addComponent((Component) this.jLabel58)).addContainerGap(-1, 32767)));
        this.jPanel84.setBorder((Border) BorderFactory.createTitledBorder("子弹扩充"));
        LZJMS.子弹扩充列表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"排序", "物品id"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane9.setViewportView((Component) LZJMS.子弹扩充列表);
        if (LZJMS.子弹扩充列表.getColumnModel().getColumnCount() > 0) {
            LZJMS.子弹扩充列表.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.子弹扩充列表.getColumnModel().getColumn(1).setPreferredWidth(20);
        }
        this.子弹增加.setText("增加");
        this.子弹增加.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.子弹增加ActionPerformed(evt);
            }
        });
        this.子弹删除.setText("删除");
        this.子弹删除.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.子弹删除ActionPerformed(evt);
            }
        });
        this.jLabel121.setText("排序:");
        LZJMS.子弹代码.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.子弹代码ActionPerformed(evt);
            }
        });
        LZJMS.子弹排序.setEnabled(false);
        this.jLabel122.setText("物品id:");
        final GroupLayout jPanel84Layout = new GroupLayout((Container) this.jPanel84);
        this.jPanel84.setLayout((LayoutManager) jPanel84Layout);
        jPanel84Layout.setHorizontalGroup((Group) jPanel84Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel84Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel84Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane9, -2, 0, 32767).addGroup(Alignment.TRAILING, (Group) jPanel84Layout.createSequentialGroup().addComponent((Component) this.子弹增加, -2, 79, -2).addPreferredGap(ComponentPlacement.RELATED, 84, 32767).addComponent((Component) this.子弹删除, -2, 79, -2)).addGroup((Group) jPanel84Layout.createSequentialGroup().addGroup((Group) jPanel84Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel121).addComponent((Component) this.jLabel122)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel84Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.子弹排序, Alignment.TRAILING, -2, 69, -2).addComponent((Component) LZJMS.子弹代码, Alignment.TRAILING, -2, 69, -2)))).addContainerGap()));
        jPanel84Layout.setVerticalGroup((Group) jPanel84Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel84Layout.createSequentialGroup().addComponent((Component) this.jScrollPane9, -2, 208, -2).addGap(18, 18, 18).addGroup((Group) jPanel84Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel121).addComponent((Component) LZJMS.子弹排序, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel84Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.子弹代码, -2, -1, -2).addComponent((Component) this.jLabel122)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel84Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.子弹删除).addComponent((Component) this.子弹增加))));
        this.jPanel27.setBorder((Border) BorderFactory.createTitledBorder("子弹扩充"));
        this.jLabel125.setText("子弹扩充开关");
        LZJMS.子弹扩充开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.子弹扩充开关.setBorderPainted(false);
        LZJMS.子弹扩充开关.setContentAreaFilled(false);
        LZJMS.子弹扩充开关.setFocusPainted(false);
        LZJMS.子弹扩充开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.子弹扩充开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.子弹扩充开关StateChanged(evt);
            }
        });
        LZJMS.子弹扩充开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.子弹扩充开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel27Layout = new GroupLayout((Container) this.jPanel27);
        this.jPanel27.setLayout((LayoutManager) jPanel27Layout);
        jPanel27Layout.setHorizontalGroup((Group) jPanel27Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel27Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jLabel125).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.子弹扩充开关, -2, 74, -2).addContainerGap()));
        jPanel27Layout.setVerticalGroup((Group) jPanel27Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel27Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel27Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.子弹扩充开关, -2, 30, -2).addComponent((Component) this.jLabel125)).addContainerGap(-1, 32767)));
        this.jPanel85.setBorder((Border) BorderFactory.createTitledBorder("特殊组队经验"));
        LZJMS.特殊组队经验加成表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"排序", "职业id"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane10.setViewportView((Component) LZJMS.特殊组队经验加成表);
        if (LZJMS.特殊组队经验加成表.getColumnModel().getColumnCount() > 0) {
            LZJMS.特殊组队经验加成表.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.特殊组队经验加成表.getColumnModel().getColumn(1).setPreferredWidth(20);
        }
        this.特殊组队增加.setText("增加");
        this.特殊组队增加.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.特殊组队增加ActionPerformed(evt);
            }
        });
        this.特殊组队删除.setText("删除");
        this.特殊组队删除.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.特殊组队删除ActionPerformed(evt);
            }
        });
        this.jLabel123.setText("排序:");
        LZJMS.特殊组队经验排序.setEnabled(false);
        this.jLabel124.setText("职业id:");
        final GroupLayout jPanel85Layout = new GroupLayout((Container) this.jPanel85);
        this.jPanel85.setLayout((LayoutManager) jPanel85Layout);
        jPanel85Layout.setHorizontalGroup((Group) jPanel85Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel85Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel85Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane10, -2, 0, 32767).addGroup(Alignment.TRAILING, (Group) jPanel85Layout.createSequentialGroup().addComponent((Component) this.特殊组队增加, -2, 79, -2).addPreferredGap(ComponentPlacement.RELATED, 85, 32767).addComponent((Component) this.特殊组队删除, -2, 79, -2)).addGroup((Group) jPanel85Layout.createSequentialGroup().addGroup((Group) jPanel85Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel123).addComponent((Component) this.jLabel124)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel85Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.特殊组队经验排序, Alignment.TRAILING, -2, 69, -2).addComponent((Component) LZJMS.特殊组队经验职业, Alignment.TRAILING, -2, 69, -2)))).addContainerGap()));
        jPanel85Layout.setVerticalGroup((Group) jPanel85Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel85Layout.createSequentialGroup().addComponent((Component) this.jScrollPane10, -2, 209, -2).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel85Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel123).addComponent((Component) LZJMS.特殊组队经验排序, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel85Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.特殊组队经验职业, -2, -1, -2).addComponent((Component) this.jLabel124)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel85Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.特殊组队删除).addComponent((Component) this.特殊组队增加))));
        this.jPanel28.setBorder((Border) BorderFactory.createTitledBorder("特殊组队经验"));
        this.jLabel126.setText("特殊组队经验加成");
        LZJMS.特殊组队经验加成.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.特殊组队经验加成.setBorderPainted(false);
        LZJMS.特殊组队经验加成.setContentAreaFilled(false);
        LZJMS.特殊组队经验加成.setFocusPainted(false);
        LZJMS.特殊组队经验加成.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.特殊组队经验加成.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.特殊组队经验加成StateChanged(evt);
            }
        });
        LZJMS.特殊组队经验加成.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.特殊组队经验加成ActionPerformed(evt);
            }
        });
        this.jLabel127.setText("原始组队经验加成");
        this.jLabel128.setText("修正组队经验加成");
        LZJMS.原始组队经验加成.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.原始组队经验加成FocusLost(evt);
            }
        });
        LZJMS.修正组队经验加成.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.修正组队经验加成FocusLost(evt);
            }
        });
        this.jLabel209.setText("修正队员分配经验");
        LZJMS.修正队员分配经验.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.修正队员分配经验FocusLost(evt);
            }
        });
        final GroupLayout jPanel28Layout = new GroupLayout((Container) this.jPanel28);
        this.jPanel28.setLayout((LayoutManager) jPanel28Layout);
        jPanel28Layout.setHorizontalGroup((Group) jPanel28Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel28Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel28Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel28Layout.createSequentialGroup().addComponent((Component) this.jLabel126).addPreferredGap(ComponentPlacement.RELATED, 73, 32767).addComponent((Component) LZJMS.特殊组队经验加成, -2, 74, -2)).addGroup((Group) jPanel28Layout.createSequentialGroup().addComponent((Component) this.jLabel128).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.修正组队经验加成, -2, 69, -2)).addGroup((Group) jPanel28Layout.createSequentialGroup().addComponent((Component) this.jLabel127).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.原始组队经验加成, -2, 69, -2)).addGroup((Group) jPanel28Layout.createSequentialGroup().addComponent((Component) this.jLabel209).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.修正队员分配经验, -2, 69, -2))).addContainerGap()));
        jPanel28Layout.setVerticalGroup((Group) jPanel28Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel28Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel28Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.特殊组队经验加成, -2, 30, -2).addComponent((Component) this.jLabel126)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel28Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel127).addComponent((Component) LZJMS.原始组队经验加成, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel28Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel128).addComponent((Component) LZJMS.修正组队经验加成, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel28Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel209).addComponent((Component) LZJMS.修正队员分配经验, -2, -1, -2)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel23Layout = new GroupLayout((Container) this.jPanel23);
        this.jPanel23.setLayout((LayoutManager) jPanel23Layout);
        jPanel23Layout.setHorizontalGroup((Group) jPanel23Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel23Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel23Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel22, -1, -1, 32767).addComponent((Component) this.jPanel63, -1, -1, 32767)).addGap(10, 10, 10).addGroup((Group) jPanel23Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel26, -1, -1, 32767).addComponent((Component) this.jPanel83, -1, -1, 32767)).addGap(10, 10, 10).addGroup((Group) jPanel23Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel27, -1, -1, 32767).addComponent((Component) this.jPanel84, -1, -1, 32767)).addGap(10, 10, 10).addGroup((Group) jPanel23Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel28, -1, -1, 32767).addComponent((Component) this.jPanel85, -1, -1, 32767)).addGap(346, 346, 346)));
        jPanel23Layout.setVerticalGroup((Group) jPanel23Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel23Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel23Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel63, -1, -1, 32767).addComponent((Component) this.jPanel83, -1, -1, 32767).addComponent((Component) this.jPanel84, -1, -1, 32767).addComponent((Component) this.jPanel85, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel23Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel26, -1, -1, 32767).addComponent((Component) this.jPanel22, -1, -1, 32767).addComponent((Component) this.jPanel27, -1, -1, 32767).addComponent((Component) this.jPanel28, -1, -1, 32767)).addGap(80, 80, 80)));
        this.宠吸功能面板.addTab("配置增删", (Component) this.jPanel23);
        this.jPanel65.setBorder((Border) BorderFactory.createTitledBorder("登录相关"));
        LZJMS.玩家登录.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.玩家登录.setBorderPainted(false);
        LZJMS.玩家登录.setContentAreaFilled(false);
        LZJMS.玩家登录.setFocusPainted(false);
        LZJMS.玩家登录.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.玩家登录.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.玩家登录StateChanged(evt);
            }
        });
        LZJMS.玩家登录.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.玩家登录ActionPerformed(evt);
            }
        });
        this.jLabel71.setText("玩家登录");
        LZJMS.管理员独占登录.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.管理员独占登录.setBorderPainted(false);
        LZJMS.管理员独占登录.setContentAreaFilled(false);
        LZJMS.管理员独占登录.setFocusPainted(false);
        LZJMS.管理员独占登录.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.管理员独占登录.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.管理员独占登录StateChanged(evt);
            }
        });
        LZJMS.管理员独占登录.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.管理员独占登录ActionPerformed(evt);
            }
        });
        this.jLabel72.setText("管理员独占登录");
        LZJMS.记录登录信息.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.记录登录信息.setBorderPainted(false);
        LZJMS.记录登录信息.setContentAreaFilled(false);
        LZJMS.记录登录信息.setFocusPainted(false);
        LZJMS.记录登录信息.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.记录登录信息.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.记录登录信息StateChanged(evt);
            }
        });
        LZJMS.记录登录信息.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.记录登录信息ActionPerformed(evt);
            }
        });
        this.jLabel75.setText("记录登录信息");
        this.jLabel76.setText("欢迎弹窗");
        LZJMS.欢迎弹窗.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.欢迎弹窗.setBorderPainted(false);
        LZJMS.欢迎弹窗.setContentAreaFilled(false);
        LZJMS.欢迎弹窗.setFocusPainted(false);
        LZJMS.欢迎弹窗.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.欢迎弹窗.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.欢迎弹窗StateChanged(evt);
            }
        });
        LZJMS.欢迎弹窗.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.欢迎弹窗ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel65Layout = new GroupLayout((Container) this.jPanel65);
        this.jPanel65.setLayout((LayoutManager) jPanel65Layout);
        jPanel65Layout.setHorizontalGroup((Group) jPanel65Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel65Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel65Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel76, -1, -1, 32767).addComponent((Component) this.jLabel71, Alignment.TRAILING, -1, -1, 32767).addComponent((Component) this.jLabel72, -1, -1, 32767).addComponent((Component) this.jLabel75, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED, 20, 32767).addGroup((Group) jPanel65Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.管理员独占登录, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.记录登录信息, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.玩家登录, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.欢迎弹窗, Alignment.TRAILING, -2, 74, -2)).addContainerGap()));
        jPanel65Layout.setVerticalGroup((Group) jPanel65Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel65Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group) jPanel65Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel71).addComponent((Component) LZJMS.玩家登录, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel65Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel72).addComponent((Component) LZJMS.管理员独占登录, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel65Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel75).addComponent((Component) LZJMS.记录登录信息, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel65Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel76).addComponent((Component) LZJMS.欢迎弹窗, -2, 30, -2)).addContainerGap(-1, 32767)));
        this.jPanel66.setBorder((Border) BorderFactory.createTitledBorder("玩家行为"));
        LZJMS.玩家交易.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.玩家交易.setBorderPainted(false);
        LZJMS.玩家交易.setContentAreaFilled(false);
        LZJMS.玩家交易.setFocusPainted(false);
        LZJMS.玩家交易.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.玩家交易.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.玩家交易StateChanged(evt);
            }
        });
        LZJMS.玩家交易.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.玩家交易ActionPerformed(evt);
            }
        });
        this.jLabel79.setText("玩家交易");
        LZJMS.玩家聊天.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.玩家聊天.setBorderPainted(false);
        LZJMS.玩家聊天.setContentAreaFilled(false);
        LZJMS.玩家聊天.setFocusPainted(false);
        LZJMS.玩家聊天.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.玩家聊天.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.玩家聊天StateChanged(evt);
            }
        });
        LZJMS.玩家聊天.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.玩家聊天ActionPerformed(evt);
            }
        });
        this.jLabel80.setText("玩家聊天");
        LZJMS.丢出金币.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.丢出金币.setBorderPainted(false);
        LZJMS.丢出金币.setContentAreaFilled(false);
        LZJMS.丢出金币.setFocusPainted(false);
        LZJMS.丢出金币.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.丢出金币.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.丢出金币StateChanged(evt);
            }
        });
        LZJMS.丢出金币.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.丢出金币ActionPerformed(evt);
            }
        });
        this.jLabel81.setText("丢出金币");
        this.jLabel82.setText("丢出物品");
        LZJMS.丢出物品.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.丢出物品.setBorderPainted(false);
        LZJMS.丢出物品.setContentAreaFilled(false);
        LZJMS.丢出物品.setFocusPainted(false);
        LZJMS.丢出物品.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.丢出物品.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.丢出物品StateChanged(evt);
            }
        });
        LZJMS.丢出物品.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.丢出物品ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel66Layout = new GroupLayout((Container) this.jPanel66);
        this.jPanel66.setLayout((LayoutManager) jPanel66Layout);
        jPanel66Layout.setHorizontalGroup((Group) jPanel66Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel66Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel66Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel82, -1, 84, 32767).addComponent((Component) this.jLabel79, Alignment.TRAILING, -1, -1, 32767).addComponent((Component) this.jLabel80, -1, -1, 32767).addComponent((Component) this.jLabel81, -1, 84, 32767)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel66Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.玩家聊天, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.丢出金币, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.玩家交易, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.丢出物品, Alignment.TRAILING, -2, 74, -2)).addContainerGap()));
        jPanel66Layout.setVerticalGroup((Group) jPanel66Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel66Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group) jPanel66Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel79).addComponent((Component) LZJMS.玩家交易, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel66Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel80).addComponent((Component) LZJMS.玩家聊天, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel66Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel81).addComponent((Component) LZJMS.丢出金币, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel66Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel82).addComponent((Component) LZJMS.丢出物品, -2, 30, -2)).addContainerGap(-1, 32767)));
        this.jPanel67.setBorder((Border) BorderFactory.createTitledBorder("信息控制"));
        LZJMS.升级提示.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.升级提示.setBorderPainted(false);
        LZJMS.升级提示.setContentAreaFilled(false);
        LZJMS.升级提示.setFocusPainted(false);
        LZJMS.升级提示.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.升级提示.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.升级提示StateChanged(evt);
            }
        });
        LZJMS.升级提示.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.升级提示ActionPerformed(evt);
            }
        });
        this.jLabel84.setText("升级提示");
        this.jLabel86.setText("游戏喇叭");
        LZJMS.游戏喇叭.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.游戏喇叭.setBorderPainted(false);
        LZJMS.游戏喇叭.setContentAreaFilled(false);
        LZJMS.游戏喇叭.setFocusPainted(false);
        LZJMS.游戏喇叭.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.游戏喇叭.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.游戏喇叭StateChanged(evt);
            }
        });
        LZJMS.游戏喇叭.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.游戏喇叭ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel67Layout = new GroupLayout((Container) this.jPanel67);
        this.jPanel67.setLayout((LayoutManager) jPanel67Layout);
        jPanel67Layout.setHorizontalGroup((Group) jPanel67Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel67Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel67Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel86, -1, -1, 32767).addComponent((Component) this.jLabel84, -1, 84, 32767)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel67Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.升级提示, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.游戏喇叭, Alignment.TRAILING, -2, 74, -2)).addContainerGap()));
        jPanel67Layout.setVerticalGroup((Group) jPanel67Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel67Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel67Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel84).addComponent((Component) LZJMS.升级提示, -2, 30, -2)).addGap(10, 10, 10).addGroup((Group) jPanel67Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel86).addComponent((Component) LZJMS.游戏喇叭, -2, 30, -2)).addContainerGap(-1, 32767)));
        this.jPanel68.setBorder((Border) BorderFactory.createTitledBorder("游戏玩法"));
        LZJMS.越级打怪.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.越级打怪.setBorderPainted(false);
        LZJMS.越级打怪.setContentAreaFilled(false);
        LZJMS.越级打怪.setFocusPainted(false);
        LZJMS.越级打怪.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.越级打怪.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.越级打怪StateChanged(evt);
            }
        });
        LZJMS.越级打怪.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.越级打怪ActionPerformed(evt);
            }
        });
        this.jLabel88.setText("越级打怪");
        this.jLabel90.setText("雇佣商人");
        LZJMS.雇佣商人.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.雇佣商人.setBorderPainted(false);
        LZJMS.雇佣商人.setContentAreaFilled(false);
        LZJMS.雇佣商人.setFocusPainted(false);
        LZJMS.雇佣商人.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.雇佣商人.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.雇佣商人StateChanged(evt);
            }
        });
        LZJMS.雇佣商人.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.雇佣商人ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel68Layout = new GroupLayout((Container) this.jPanel68);
        this.jPanel68.setLayout((LayoutManager) jPanel68Layout);
        jPanel68Layout.setHorizontalGroup((Group) jPanel68Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel68Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel68Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel90, -1, -1, 32767).addComponent((Component) this.jLabel88, -1, 84, 32767)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel68Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.越级打怪, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.雇佣商人, Alignment.TRAILING, -2, 74, -2)).addContainerGap()));
        jPanel68Layout.setVerticalGroup((Group) jPanel68Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel68Layout.createSequentialGroup().addGap(10, 10, 10).addGroup((Group) jPanel68Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel88).addComponent((Component) LZJMS.越级打怪, -2, 30, -2)).addGap(6, 6, 6).addGroup((Group) jPanel68Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel90).addComponent((Component) LZJMS.雇佣商人, -2, 30, -2))));
        this.jPanel69.setBorder((Border) BorderFactory.createTitledBorder("杂项功能"));
        LZJMS.地图名称.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.地图名称.setBorderPainted(false);
        LZJMS.地图名称.setContentAreaFilled(false);
        LZJMS.地图名称.setFocusPainted(false);
        LZJMS.地图名称.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.地图名称.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.地图名称StateChanged(evt);
            }
        });
        LZJMS.地图名称.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.地图名称ActionPerformed(evt);
            }
        });
        this.jLabel92.setText("地图名称");
        LZJMS.玩家指令.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.玩家指令.setBorderPainted(false);
        LZJMS.玩家指令.setContentAreaFilled(false);
        LZJMS.玩家指令.setFocusPainted(false);
        LZJMS.玩家指令.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.玩家指令.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.玩家指令StateChanged(evt);
            }
        });
        LZJMS.玩家指令.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.玩家指令ActionPerformed(evt);
            }
        });
        this.jLabel93.setText("玩家指令");
        this.jLabel94.setText("游戏仓库");
        LZJMS.游戏仓库.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.游戏仓库.setBorderPainted(false);
        LZJMS.游戏仓库.setContentAreaFilled(false);
        LZJMS.游戏仓库.setFocusPainted(false);
        LZJMS.游戏仓库.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.游戏仓库.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.游戏仓库StateChanged(evt);
            }
        });
        LZJMS.游戏仓库.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.游戏仓库ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel69Layout = new GroupLayout((Container) this.jPanel69);
        this.jPanel69.setLayout((LayoutManager) jPanel69Layout);
        jPanel69Layout.setHorizontalGroup((Group) jPanel69Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel69Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel69Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel94, -1, 84, 32767).addComponent((Component) this.jLabel92, -1, -1, 32767).addComponent((Component) this.jLabel93, -1, 84, 32767)).addPreferredGap(ComponentPlacement.RELATED, 20, 32767).addGroup((Group) jPanel69Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.地图名称, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.玩家指令, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.游戏仓库, Alignment.TRAILING, -2, 74, -2)).addContainerGap()));
        jPanel69Layout.setVerticalGroup((Group) jPanel69Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel69Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel69Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel92).addComponent((Component) LZJMS.地图名称, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel69Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel93).addComponent((Component) LZJMS.玩家指令, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel69Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel94).addComponent((Component) LZJMS.游戏仓库, -2, 30, -2))));
        this.jPanel70.setBorder((Border) BorderFactory.createTitledBorder("管理员功能"));
        LZJMS.管理员隐身.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.管理员隐身.setBorderPainted(false);
        LZJMS.管理员隐身.setContentAreaFilled(false);
        LZJMS.管理员隐身.setFocusPainted(false);
        LZJMS.管理员隐身.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.管理员隐身.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.管理员隐身StateChanged(evt);
            }
        });
        LZJMS.管理员隐身.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.管理员隐身ActionPerformed(evt);
            }
        });
        this.jLabel96.setText("管理员隐身");
        LZJMS.管理员加速.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.管理员加速.setBorderPainted(false);
        LZJMS.管理员加速.setContentAreaFilled(false);
        LZJMS.管理员加速.setFocusPainted(false);
        LZJMS.管理员加速.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.管理员加速.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.管理员加速StateChanged(evt);
            }
        });
        LZJMS.管理员加速.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.管理员加速ActionPerformed(evt);
            }
        });
        this.jLabel97.setText("管理员加速");
        final GroupLayout jPanel70Layout = new GroupLayout((Container) this.jPanel70);
        this.jPanel70.setLayout((LayoutManager) jPanel70Layout);
        jPanel70Layout.setHorizontalGroup((Group) jPanel70Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel70Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel70Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel96, -1, -1, 32767).addComponent((Component) this.jLabel97, -1, 84, 32767)).addPreferredGap(ComponentPlacement.RELATED, 20, 32767).addGroup((Group) jPanel70Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.管理员隐身, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.管理员加速, Alignment.TRAILING, -2, 74, -2)).addContainerGap()));
        jPanel70Layout.setVerticalGroup((Group) jPanel70Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel70Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group) jPanel70Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel96).addComponent((Component) LZJMS.管理员隐身, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel70Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel97).addComponent((Component) LZJMS.管理员加速, -2, 30, -2)).addGap(36, 36, 36)));
        this.jPanel100.setBorder((Border) BorderFactory.createTitledBorder("克隆系统"));
        this.jLabel216.setText("克隆基础伤害");
        LZJMS.克隆基础伤害.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.克隆基础伤害FocusLost(evt);
            }
        });
        LZJMS.克隆基础伤害.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.克隆基础伤害ActionPerformed(evt);
            }
        });
        this.jLabel217.setText("自动刷钱道具");
        LZJMS.自动刷钱道具.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.自动刷钱道具FocusLost(evt);
            }
        });
        LZJMS.自动刷钱道具.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.自动刷钱道具ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel100Layout = new GroupLayout((Container) this.jPanel100);
        this.jPanel100.setLayout((LayoutManager) jPanel100Layout);
        jPanel100Layout.setHorizontalGroup((Group) jPanel100Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel100Layout.createSequentialGroup().addComponent((Component) this.jLabel216).addPreferredGap(ComponentPlacement.RELATED, 79, 32767).addComponent((Component) LZJMS.克隆基础伤害, -2, 69, -2)).addGroup((Group) jPanel100Layout.createSequentialGroup().addComponent((Component) this.jLabel217).addPreferredGap(ComponentPlacement.RELATED, 79, 32767).addComponent((Component) LZJMS.自动刷钱道具, -2, 69, -2)));
        jPanel100Layout.setVerticalGroup((Group) jPanel100Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel100Layout.createSequentialGroup().addGap(13, 13, 13).addGroup((Group) jPanel100Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel216).addComponent((Component) LZJMS.克隆基础伤害, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel100Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel217).addComponent((Component) LZJMS.自动刷钱道具, -2, -1, -2)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel13Layout = new GroupLayout((Container) this.jPanel13);
        this.jPanel13.setLayout((LayoutManager) jPanel13Layout);
        jPanel13Layout.setHorizontalGroup((Group) jPanel13Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel13Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel13Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel68, -1, -1, 32767).addComponent((Component) this.jPanel65, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel13Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel69, -1, -1, 32767).addComponent((Component) this.jPanel66, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel13Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel67, -1, -1, 32767).addComponent((Component) this.jPanel70, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jPanel100, -2, -1, -2).addContainerGap(600, 32767)));
        jPanel13Layout.setVerticalGroup((Group) jPanel13Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel13Layout.createSequentialGroup().addGroup((Group) jPanel13Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel66, -1, -1, 32767).addComponent((Component) this.jPanel65, -1, -1, 32767).addComponent((Component) this.jPanel67, -1, -1, 32767).addComponent((Component) this.jPanel100, -1, -1, 32767)).addGap(16, 16, 16).addGroup((Group) jPanel13Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel13Layout.createParallelGroup(Alignment.TRAILING, false).addComponent((Component) this.jPanel69, Alignment.LEADING, -1, -1, 32767).addComponent((Component) this.jPanel68, Alignment.LEADING, -1, -1, 32767)).addComponent((Component) this.jPanel70, Alignment.TRAILING, -2, -1, -2)).addGap(360, 360, 360)));
        this.宠吸功能面板.addTab("功能设置", (Component) this.jPanel13);
        this.jPanel71.setBorder((Border) BorderFactory.createTitledBorder("刷怪相关"));
        LZJMS.段数检测.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.段数检测.setBorderPainted(false);
        LZJMS.段数检测.setContentAreaFilled(false);
        LZJMS.段数检测.setFocusPainted(false);
        LZJMS.段数检测.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.段数检测.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.段数检测StateChanged(evt);
            }
        });
        LZJMS.段数检测.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.段数检测ActionPerformed(evt);
            }
        });
        this.jLabel99.setText("段数检测");
        LZJMS.攻速检测.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.攻速检测.setBorderPainted(false);
        LZJMS.攻速检测.setContentAreaFilled(false);
        LZJMS.攻速检测.setFocusPainted(false);
        LZJMS.攻速检测.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.攻速检测.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.攻速检测StateChanged(evt);
            }
        });
        LZJMS.攻速检测.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.攻速检测ActionPerformed(evt);
            }
        });
        this.jLabel100.setText("攻速检测");
        LZJMS.全屏检测.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.全屏检测.setBorderPainted(false);
        LZJMS.全屏检测.setContentAreaFilled(false);
        LZJMS.全屏检测.setFocusPainted(false);
        LZJMS.全屏检测.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.全屏检测.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.全屏检测StateChanged(evt);
            }
        });
        LZJMS.全屏检测.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.全屏检测ActionPerformed(evt);
            }
        });
        this.jLabel101.setText("全屏检测");
        this.jLabel102.setText("吸怪检测");
        LZJMS.吸怪检测.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.吸怪检测.setBorderPainted(false);
        LZJMS.吸怪检测.setContentAreaFilled(false);
        LZJMS.吸怪检测.setFocusPainted(false);
        LZJMS.吸怪检测.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.吸怪检测.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.吸怪检测StateChanged(evt);
            }
        });
        LZJMS.吸怪检测.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.吸怪检测ActionPerformed(evt);
            }
        });
        this.jLabel107.setText("吸物检测");
        LZJMS.吸物检测.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.吸物检测.setBorderPainted(false);
        LZJMS.吸物检测.setContentAreaFilled(false);
        LZJMS.吸物检测.setFocusPainted(false);
        LZJMS.吸物检测.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.吸物检测.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.吸物检测StateChanged(evt);
            }
        });
        LZJMS.吸物检测.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.吸物检测ActionPerformed(evt);
            }
        });
        this.jLabel145.setText("个数检测");
        LZJMS.个数检测.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.个数检测.setBorderPainted(false);
        LZJMS.个数检测.setContentAreaFilled(false);
        LZJMS.个数检测.setFocusPainted(false);
        LZJMS.个数检测.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.个数检测.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.个数检测StateChanged(evt);
            }
        });
        LZJMS.个数检测.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.个数检测ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel71Layout = new GroupLayout((Container) this.jPanel71);
        this.jPanel71.setLayout((LayoutManager) jPanel71Layout);
        jPanel71Layout.setHorizontalGroup((Group) jPanel71Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel71Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel71Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel71Layout.createSequentialGroup().addComponent((Component) this.jLabel99).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.段数检测, -2, 74, -2)).addGroup((Group) jPanel71Layout.createSequentialGroup().addGroup((Group) jPanel71Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel100, -1, -1, 32767).addComponent((Component) this.jLabel101, -1, 84, 32767)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel71Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.攻速检测, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.全屏检测, Alignment.TRAILING, -2, 74, -2))).addGroup((Group) jPanel71Layout.createSequentialGroup().addComponent((Component) this.jLabel102, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.吸怪检测, -2, 74, -2)).addGroup(Alignment.TRAILING, (Group) jPanel71Layout.createSequentialGroup().addComponent((Component) this.jLabel107, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.吸物检测, -2, 74, -2)).addGroup((Group) jPanel71Layout.createSequentialGroup().addComponent((Component) this.jLabel145).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.个数检测, -2, 74, -2))).addContainerGap()));
        jPanel71Layout.setVerticalGroup((Group) jPanel71Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel71Layout.createSequentialGroup().addGroup((Group) jPanel71Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel99).addComponent((Component) LZJMS.段数检测, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel71Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel100).addComponent((Component) LZJMS.攻速检测, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel71Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel101).addComponent((Component) LZJMS.全屏检测, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel71Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel102).addComponent((Component) LZJMS.吸怪检测, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel71Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel107).addComponent((Component) LZJMS.吸物检测, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel71Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.个数检测, -2, 30, -2).addComponent((Component) this.jLabel145)).addContainerGap(-1, 32767)));
        this.jPanel72.setBorder((Border) BorderFactory.createTitledBorder("伤害相关"));
        LZJMS.伤害检测.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.伤害检测.setBorderPainted(false);
        LZJMS.伤害检测.setContentAreaFilled(false);
        LZJMS.伤害检测.setFocusPainted(false);
        LZJMS.伤害检测.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.伤害检测.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.伤害检测StateChanged(evt);
            }
        });
        LZJMS.伤害检测.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.伤害检测ActionPerformed(evt);
            }
        });
        this.jLabel103.setText("伤害检测");
        this.jLabel104.setText("伤害上限");
        this.jLabel106.setText("丢失伤害");
        LZJMS.丢失伤害.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.丢失伤害.setBorderPainted(false);
        LZJMS.丢失伤害.setContentAreaFilled(false);
        LZJMS.丢失伤害.setFocusPainted(false);
        LZJMS.丢失伤害.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.丢失伤害.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.丢失伤害StateChanged(evt);
            }
        });
        LZJMS.丢失伤害.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.丢失伤害ActionPerformed(evt);
            }
        });
        LZJMS.伤害上限.setToolTipText("");
        LZJMS.伤害上限.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.伤害上限FocusLost(evt);
            }
        });
        this.伤害修正1.setText("伤害修正");
        LZJMS.伤害修正开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.伤害修正开关.setBorderPainted(false);
        LZJMS.伤害修正开关.setContentAreaFilled(false);
        LZJMS.伤害修正开关.setFocusPainted(false);
        LZJMS.伤害修正开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.伤害修正开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.伤害修正开关StateChanged(evt);
            }
        });
        LZJMS.伤害修正开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.伤害修正开关ActionPerformed(evt);
            }
        });
        LZJMS.重置技能总范围.setToolTipText("");
        LZJMS.重置技能总范围.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.重置技能总范围FocusLost(evt);
            }
        });
        this.jLabel199.setText("重置技能总范围");
        this.jLabel113.setText("重置技能范围开关");
        LZJMS.重置技能范围开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.重置技能范围开关.setBorderPainted(false);
        LZJMS.重置技能范围开关.setContentAreaFilled(false);
        LZJMS.重置技能范围开关.setFocusPainted(false);
        LZJMS.重置技能范围开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.重置技能范围开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.重置技能范围开关StateChanged(evt);
            }
        });
        LZJMS.重置技能范围开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重置技能范围开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel72Layout = new GroupLayout((Container) this.jPanel72);
        this.jPanel72.setLayout((LayoutManager) jPanel72Layout);
        jPanel72Layout.setHorizontalGroup((Group) jPanel72Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel72Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel72Layout.createSequentialGroup().addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel72Layout.createSequentialGroup().addComponent((Component) this.jLabel106, -1, -1, 32767).addGap(58, 58, 58).addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.重置技能范围开关, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.丢失伤害, Alignment.TRAILING, -2, 74, -2))).addGroup((Group) jPanel72Layout.createSequentialGroup().addComponent((Component) this.伤害修正1, -1, -1, 32767).addGap(58, 58, 58).addComponent((Component) LZJMS.伤害修正开关, -2, 74, -2)).addGroup((Group) jPanel72Layout.createSequentialGroup().addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.TRAILING, false).addComponent((Component) this.jLabel103, -1, 96, 32767).addComponent((Component) this.jLabel104, -1, -1, 32767)).addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel72Layout.createSequentialGroup().addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) LZJMS.伤害上限, -2, 118, -2).addGap(0, 0, 32767)).addGroup(Alignment.TRAILING, (Group) jPanel72Layout.createSequentialGroup().addGap(58, 58, 58).addComponent((Component) LZJMS.伤害检测, -2, 74, -2))))).addGap(6, 6, 6)).addGroup((Group) jPanel72Layout.createSequentialGroup().addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel113, -1, -1, 32767).addComponent((Component) this.jLabel199, -1, -1, 32767)).addGap(10, 10, 10).addComponent((Component) LZJMS.重置技能总范围, -2, 118, -2).addContainerGap(-1, 32767)))));
        jPanel72Layout.setVerticalGroup((Group) jPanel72Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel72Layout.createSequentialGroup().addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel103).addComponent((Component) LZJMS.伤害检测, -2, 30, -2)).addGap(8, 8, 8).addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel104).addComponent((Component) LZJMS.伤害上限, -2, -1, -2)).addGap(8, 8, 8).addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.丢失伤害, -2, 30, -2).addComponent((Component) this.jLabel106)).addGap(8, 8, 8).addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.重置技能范围开关, -2, 30, -2).addComponent((Component) this.jLabel113)).addGap(8, 8, 8).addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group) jPanel72Layout.createSequentialGroup().addGap(36, 36, 36).addComponent((Component) this.伤害修正1)).addGroup(Alignment.TRAILING, (Group) jPanel72Layout.createSequentialGroup().addGroup((Group) jPanel72Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) LZJMS.重置技能总范围, -2, -1, -2).addComponent((Component) this.jLabel199)).addGap(8, 8, 8).addComponent((Component) LZJMS.伤害修正开关, -2, 30, -2))).addContainerGap(-1, 32767)));
        this.jPanel74.setBorder((Border) BorderFactory.createTitledBorder("封停规则"));
        LZJMS.全服通告.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.全服通告.setBorderPainted(false);
        LZJMS.全服通告.setContentAreaFilled(false);
        LZJMS.全服通告.setFocusPainted(false);
        LZJMS.全服通告.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.全服通告.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.全服通告StateChanged(evt);
            }
        });
        LZJMS.全服通告.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.全服通告ActionPerformed(evt);
            }
        });
        this.jLabel112.setText("全服通告");
        this.jLabel116.setText("封停账号");
        LZJMS.封停账号.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.封停账号.setBorderPainted(false);
        LZJMS.封停账号.setContentAreaFilled(false);
        LZJMS.封停账号.setFocusPainted(false);
        LZJMS.封停账号.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.封停账号.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.封停账号StateChanged(evt);
            }
        });
        LZJMS.封停账号.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.封停账号ActionPerformed(evt);
            }
        });
        this.jLabel117.setText("封停IP");
        LZJMS.封停IP.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.封停IP.setBorderPainted(false);
        LZJMS.封停IP.setContentAreaFilled(false);
        LZJMS.封停IP.setFocusPainted(false);
        LZJMS.封停IP.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.封停IP.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.封停IPStateChanged(evt);
            }
        });
        LZJMS.封停IP.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.封停IPActionPerformed(evt);
            }
        });
        this.jLabel118.setText("封停MAC");
        LZJMS.封停MAC.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.封停MAC.setBorderPainted(false);
        LZJMS.封停MAC.setContentAreaFilled(false);
        LZJMS.封停MAC.setFocusPainted(false);
        LZJMS.封停MAC.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.封停MAC.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.封停MACStateChanged(evt);
            }
        });
        LZJMS.封停MAC.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.封停MACActionPerformed(evt);
            }
        });
        final GroupLayout jPanel74Layout = new GroupLayout((Container) this.jPanel74);
        this.jPanel74.setLayout((LayoutManager) jPanel74Layout);
        jPanel74Layout.setHorizontalGroup((Group) jPanel74Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel74Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel74Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel112, -1, -1, 32767).addComponent((Component) this.jLabel118, Alignment.TRAILING, -1, -1, 32767).addComponent((Component) this.jLabel117, Alignment.TRAILING, -1, -1, 32767).addComponent((Component) this.jLabel116, Alignment.TRAILING, -1, -1, 32767)).addGap(51, 51, 51).addGroup((Group) jPanel74Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.封停账号, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.封停IP, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.封停MAC, Alignment.TRAILING, -2, 74, -2).addComponent((Component) LZJMS.全服通告, Alignment.TRAILING, -2, 74, -2)).addContainerGap()));
        jPanel74Layout.setVerticalGroup((Group) jPanel74Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel74Layout.createSequentialGroup().addGroup((Group) jPanel74Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel112).addComponent((Component) LZJMS.全服通告, -2, 30, -2)).addGap(6, 6, 6).addGroup((Group) jPanel74Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel116).addComponent((Component) LZJMS.封停账号, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel74Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel117).addComponent((Component) LZJMS.封停IP, -2, 30, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel74Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel118).addComponent((Component) LZJMS.封停MAC, -2, 30, -2)).addGap(0, 0, 32767)));
        this.jPanel50.setBorder((Border) BorderFactory.createTitledBorder("职业列表"));
        this.职业列表表单.setModel((TableModel) new DefaultTableModel(new Object[0][], (Object[]) new String[]{"职业id", "职业名称"}));
        this.jScrollPane2.setViewportView((Component) this.职业列表表单);
        this.职业技能初始化.setForeground(new Color(250, 0, 0));
        this.职业技能初始化.setText("初始化所有东西");
        this.职业技能初始化.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.职业技能初始化ActionPerformed(evt);
            }
        });
        this.刷新职业列表.setText("刷新职业列表");
        this.刷新职业列表.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.刷新职业列表ActionPerformed(evt);
            }
        });
        this.读取技能.setText("读取技能>>");
        this.读取技能.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.读取技能ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel50Layout = new GroupLayout((Container) this.jPanel50);
        this.jPanel50.setLayout((LayoutManager) jPanel50Layout);
        jPanel50Layout.setHorizontalGroup((Group) jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel50Layout.createSequentialGroup().addGap(35, 35, 35).addGroup((Group) jPanel50Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane2, -2, 382, -2).addGroup((Group) jPanel50Layout.createSequentialGroup().addComponent((Component) this.刷新职业列表, -2, 118, -2).addGap(30, 30, 30).addComponent((Component) this.职业技能初始化).addGap(18, 18, 18).addComponent((Component) this.读取技能, -2, 100, -2))).addContainerGap(34, 32767)));
        jPanel50Layout.setVerticalGroup((Group) jPanel50Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel50Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane2, -2, 514, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel50Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.职业技能初始化).addComponent((Component) this.刷新职业列表).addComponent((Component) this.读取技能)).addContainerGap()));
        this.jPanel51.setBorder((Border) BorderFactory.createTitledBorder("职业技能列表"));
        this.职业技能列表表单.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"技能代码", "技能名称", "技能段数", "击杀怪物个数", "攻击延迟"}) {
            boolean[] canEdit = {false, false, false, false, true};

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane13.setViewportView((Component) this.职业技能列表表单);
        this.jLabel160.setText("技能代码:");
        this.技能代码.setEnabled(false);
        this.技能名.setText("技能名称:");
        this.技能名称.setEnabled(false);
        this.jLabel161.setText("技能段数:");
        this.技能段数.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.技能段数ActionPerformed(evt);
            }
        });
        this.jLabel162.setText("怪物个数:");
        this.修改技能.setText("修改技能");
        this.修改技能.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.修改技能ActionPerformed(evt);
            }
        });
        this.当前职业ID.setText("当前职业ID： 【0】");
        this.当前职业名称.setText("当前职业名称： 【无】");
        final GroupLayout jPanel51Layout = new GroupLayout((Container) this.jPanel51);
        this.jPanel51.setLayout((LayoutManager) jPanel51Layout);
        jPanel51Layout.setHorizontalGroup((Group) jPanel51Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel51Layout.createSequentialGroup().addGap(28, 28, 28).addGroup((Group) jPanel51Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane13, -2, 585, -2).addGroup((Group) jPanel51Layout.createSequentialGroup().addGroup((Group) jPanel51Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel51Layout.createSequentialGroup().addComponent((Component) this.jLabel160).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.技能代码, -2, 88, -2)).addComponent((Component) this.当前职业ID, -2, 127, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel51Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group) jPanel51Layout.createSequentialGroup().addComponent((Component) this.技能名).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.技能名称, -2, 85, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) this.jLabel161)).addComponent((Component) this.当前职业名称, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.技能段数, -2, 85, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) this.jLabel162).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel51Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.修改技能, -2, 85, -2).addComponent((Component) this.怪物个数, -2, 85, -2)))).addContainerGap(19, 32767)));
        jPanel51Layout.setVerticalGroup((Group) jPanel51Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel51Layout.createSequentialGroup().addContainerGap(23, 32767).addComponent((Component) this.jScrollPane13, -2, 515, -2).addGap(15, 15, 15).addGroup((Group) jPanel51Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel160).addComponent((Component) this.技能代码, -2, -1, -2).addComponent((Component) this.技能名).addComponent((Component) this.技能名称, -2, -1, -2).addComponent((Component) this.jLabel161).addComponent((Component) this.技能段数, -2, -1, -2).addComponent((Component) this.jLabel162).addComponent((Component) this.怪物个数, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel51Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.修改技能).addComponent((Component) this.当前职业ID).addComponent((Component) this.当前职业名称)).addGap(8, 8, 8)));
        final GroupLayout jPanel15Layout = new GroupLayout((Container) this.jPanel15);
        this.jPanel15.setLayout((LayoutManager) jPanel15Layout);
        jPanel15Layout.setHorizontalGroup((Group) jPanel15Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group) jPanel15Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel15Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component) this.jPanel72, -1, -1, 32767).addComponent((Component) this.jPanel74, -1, -1, 32767).addComponent((Component) this.jPanel71, -1, -1, 32767)).addGap(46, 46, 46).addComponent((Component) this.jPanel50, -2, -1, -2).addGap(35, 35, 35).addComponent((Component) this.jPanel51, -2, -1, -2).addGap(99, 99, 99)));
        jPanel15Layout.setVerticalGroup((Group) jPanel15Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel15Layout.createSequentialGroup().addGroup((Group) jPanel15Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group) jPanel15Layout.createSequentialGroup().addComponent((Component) this.jPanel71, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jPanel74, -2, 160, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jPanel72, -1, -1, 32767)).addGroup((Group) jPanel15Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel15Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel50, -1, -1, 32767).addComponent((Component) this.jPanel51, -1, -1, 32767)))).addContainerGap(42, 32767)));
        this.宠吸功能面板.addTab("检测设置", (Component) this.jPanel15);
        this.公告信息.setModel((TableModel) new DefaultTableModel(new Object[0][], (Object[]) new String[]{"公告内容[每5分钟随机播放一条]"}));
        this.jScrollPane4.setViewportView((Component) this.公告信息);
        this.jButton16.setText("删除公告");
        this.jButton16.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton16ActionPerformed(evt);
            }
        });
        this.jButton17.setText("增加公告");
        this.jButton17.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton17ActionPerformed(evt);
            }
        });
        this.jButton18.setText("修改公告");
        this.jButton18.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton18ActionPerformed(evt);
            }
        });
        this.jButton19.setText("刷新列表");
        this.jButton19.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton19ActionPerformed(evt);
            }
        });
        LZJMS.公告间隔时间.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.公告间隔时间FocusLost(evt);
            }
        });
        this.jLabel218.setText("公告间隔时间(重启生效)");
        this.重载公告.setText("重载公告");
        this.重载公告.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载公告ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel30Layout = new GroupLayout((Container) this.jPanel30);
        this.jPanel30.setLayout((LayoutManager) jPanel30Layout);
        jPanel30Layout.setHorizontalGroup((Group) jPanel30Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel30Layout.createSequentialGroup().addGroup((Group) jPanel30Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane4, -1, 1480, 32767).addGroup(Alignment.TRAILING, (Group) jPanel30Layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent((Component) this.重载公告, -2, 108, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jButton17).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jButton16).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jButton18, -2, 106, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jButton19, -2, 108, -2)).addGroup(Alignment.TRAILING, (Group) jPanel30Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent((Component) this.jLabel218).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.公告间隔时间, -2, 108, -2))).addContainerGap()));
        jPanel30Layout.linkSize(0, this.jButton16, this.jButton17, this.jButton18, this.jButton19);
        jPanel30Layout.setVerticalGroup((Group) jPanel30Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel30Layout.createSequentialGroup().addComponent((Component) this.jScrollPane4, -2, 556, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel30Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jButton16).addComponent((Component) this.jButton17).addComponent((Component) this.jButton18).addComponent((Component) this.jButton19).addComponent((Component) this.重载公告)).addGap(18, 18, 18).addGroup((Group) jPanel30Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel218).addComponent((Component) LZJMS.公告间隔时间, -2, -1, -2)).addContainerGap()));
        this.宠吸功能面板.addTab("广播设置", (Component) this.jPanel30);
        this.jPanel62.setBorder((Border) BorderFactory.createTitledBorder("区域展示"));
        LZJMS.蓝蜗牛.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.蓝蜗牛.setText("蓝蜗牛");
        LZJMS.蓝蜗牛.setBorderPainted(false);
        LZJMS.蓝蜗牛.setContentAreaFilled(false);
        LZJMS.蓝蜗牛.setFocusPainted(false);
        LZJMS.蓝蜗牛.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.蓝蜗牛.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.蓝蜗牛StateChanged(evt);
            }
        });
        LZJMS.蓝蜗牛.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.蓝蜗牛ActionPerformed(evt);
            }
        });
        LZJMS.蘑菇仔.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.蘑菇仔.setText("蘑菇仔");
        LZJMS.蘑菇仔.setBorderPainted(false);
        LZJMS.蘑菇仔.setContentAreaFilled(false);
        LZJMS.蘑菇仔.setFocusPainted(false);
        LZJMS.蘑菇仔.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.蘑菇仔.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.蘑菇仔StateChanged(evt);
            }
        });
        LZJMS.蘑菇仔.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.蘑菇仔ActionPerformed(evt);
            }
        });
        LZJMS.绿水灵.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.绿水灵.setText("绿水灵");
        LZJMS.绿水灵.setBorderPainted(false);
        LZJMS.绿水灵.setContentAreaFilled(false);
        LZJMS.绿水灵.setFocusPainted(false);
        LZJMS.绿水灵.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.绿水灵.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.绿水灵StateChanged(evt);
            }
        });
        LZJMS.绿水灵.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.绿水灵ActionPerformed(evt);
            }
        });
        LZJMS.胖企鹅.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.胖企鹅.setText("胖企鹅");
        LZJMS.胖企鹅.setBorderPainted(false);
        LZJMS.胖企鹅.setContentAreaFilled(false);
        LZJMS.胖企鹅.setFocusPainted(false);
        LZJMS.胖企鹅.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.胖企鹅.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.胖企鹅StateChanged(evt);
            }
        });
        LZJMS.胖企鹅.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.胖企鹅ActionPerformed(evt);
            }
        });
        LZJMS.星精灵.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.星精灵.setText("星精灵");
        LZJMS.星精灵.setBorderPainted(false);
        LZJMS.星精灵.setContentAreaFilled(false);
        LZJMS.星精灵.setFocusPainted(false);
        LZJMS.星精灵.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.星精灵.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.星精灵StateChanged(evt);
            }
        });
        LZJMS.星精灵.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.星精灵ActionPerformed(evt);
            }
        });
        LZJMS.漂漂猪.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.漂漂猪.setText("漂漂猪");
        LZJMS.漂漂猪.setBorderPainted(false);
        LZJMS.漂漂猪.setContentAreaFilled(false);
        LZJMS.漂漂猪.setFocusPainted(false);
        LZJMS.漂漂猪.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.漂漂猪.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.漂漂猪StateChanged(evt);
            }
        });
        LZJMS.漂漂猪.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.漂漂猪ActionPerformed(evt);
            }
        });
        LZJMS.白雪人.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.白雪人.setText("白雪人");
        LZJMS.白雪人.setBorderPainted(false);
        LZJMS.白雪人.setContentAreaFilled(false);
        LZJMS.白雪人.setFocusPainted(false);
        LZJMS.白雪人.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.白雪人.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.白雪人StateChanged(evt);
            }
        });
        LZJMS.白雪人.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.白雪人ActionPerformed(evt);
            }
        });
        LZJMS.大海龟.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.大海龟.setText("大海龟");
        LZJMS.大海龟.setBorderPainted(false);
        LZJMS.大海龟.setContentAreaFilled(false);
        LZJMS.大海龟.setFocusPainted(false);
        LZJMS.大海龟.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.大海龟.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.大海龟StateChanged(evt);
            }
        });
        LZJMS.大海龟.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.大海龟ActionPerformed(evt);
            }
        });
        LZJMS.章鱼怪.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.章鱼怪.setText("章鱼怪");
        LZJMS.章鱼怪.setBorderPainted(false);
        LZJMS.章鱼怪.setContentAreaFilled(false);
        LZJMS.章鱼怪.setFocusPainted(false);
        LZJMS.章鱼怪.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.章鱼怪.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.章鱼怪StateChanged(evt);
            }
        });
        LZJMS.章鱼怪.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.章鱼怪ActionPerformed(evt);
            }
        });
        LZJMS.顽皮猴.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.顽皮猴.setText("顽皮猴");
        LZJMS.顽皮猴.setBorderPainted(false);
        LZJMS.顽皮猴.setContentAreaFilled(false);
        LZJMS.顽皮猴.setFocusPainted(false);
        LZJMS.顽皮猴.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.顽皮猴.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.顽皮猴StateChanged(evt);
            }
        });
        LZJMS.顽皮猴.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.顽皮猴ActionPerformed(evt);
            }
        });
        LZJMS.大灰狼.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.大灰狼.setText("大灰狼");
        LZJMS.大灰狼.setBorderPainted(false);
        LZJMS.大灰狼.setContentAreaFilled(false);
        LZJMS.大灰狼.setFocusPainted(false);
        LZJMS.大灰狼.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.大灰狼.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.大灰狼StateChanged(evt);
            }
        });
        LZJMS.大灰狼.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.大灰狼ActionPerformed(evt);
            }
        });
        LZJMS.紫色猫.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.紫色猫.setText("紫色猫");
        LZJMS.紫色猫.setBorderPainted(false);
        LZJMS.紫色猫.setContentAreaFilled(false);
        LZJMS.紫色猫.setFocusPainted(false);
        LZJMS.紫色猫.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.紫色猫.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.紫色猫StateChanged(evt);
            }
        });
        LZJMS.紫色猫.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.紫色猫ActionPerformed(evt);
            }
        });
        LZJMS.石头人.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.石头人.setText("石头人");
        LZJMS.石头人.setBorderPainted(false);
        LZJMS.石头人.setContentAreaFilled(false);
        LZJMS.石头人.setFocusPainted(false);
        LZJMS.石头人.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.石头人.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.石头人StateChanged(evt);
            }
        });
        LZJMS.石头人.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.石头人ActionPerformed(evt);
            }
        });
        LZJMS.红螃蟹.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.红螃蟹.setText("红螃蟹");
        LZJMS.红螃蟹.setBorderPainted(false);
        LZJMS.红螃蟹.setContentAreaFilled(false);
        LZJMS.红螃蟹.setFocusPainted(false);
        LZJMS.红螃蟹.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.红螃蟹.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.红螃蟹StateChanged(evt);
            }
        });
        LZJMS.红螃蟹.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.红螃蟹ActionPerformed(evt);
            }
        });
        LZJMS.小青蛇.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.小青蛇.setText("小青蛇");
        LZJMS.小青蛇.setBorderPainted(false);
        LZJMS.小青蛇.setContentAreaFilled(false);
        LZJMS.小青蛇.setFocusPainted(false);
        LZJMS.小青蛇.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.小青蛇.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.小青蛇StateChanged(evt);
            }
        });
        LZJMS.小青蛇.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.小青蛇ActionPerformed(evt);
            }
        });
        LZJMS.青鳄鱼.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.青鳄鱼.setText("青鳄鱼");
        LZJMS.青鳄鱼.setBorderPainted(false);
        LZJMS.青鳄鱼.setContentAreaFilled(false);
        LZJMS.青鳄鱼.setFocusPainted(false);
        LZJMS.青鳄鱼.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.青鳄鱼.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.青鳄鱼StateChanged(evt);
            }
        });
        LZJMS.青鳄鱼.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.青鳄鱼ActionPerformed(evt);
            }
        });
        LZJMS.花蘑菇.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.花蘑菇.setText("花蘑菇");
        LZJMS.花蘑菇.setBorderPainted(false);
        LZJMS.花蘑菇.setContentAreaFilled(false);
        LZJMS.花蘑菇.setFocusPainted(false);
        LZJMS.花蘑菇.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.花蘑菇.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.花蘑菇StateChanged(evt);
            }
        });
        LZJMS.花蘑菇.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.花蘑菇ActionPerformed(evt);
            }
        });
        LZJMS.火野猪.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.火野猪.setText("火野猪");
        LZJMS.火野猪.setBorderPainted(false);
        LZJMS.火野猪.setContentAreaFilled(false);
        LZJMS.火野猪.setFocusPainted(false);
        LZJMS.火野猪.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.火野猪.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.火野猪StateChanged(evt);
            }
        });
        LZJMS.火野猪.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.火野猪ActionPerformed(evt);
            }
        });
        LZJMS.小白兔.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.小白兔.setText("小白兔");
        LZJMS.小白兔.setBorderPainted(false);
        LZJMS.小白兔.setContentAreaFilled(false);
        LZJMS.小白兔.setFocusPainted(false);
        LZJMS.小白兔.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.小白兔.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.小白兔StateChanged(evt);
            }
        });
        LZJMS.小白兔.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.小白兔ActionPerformed(evt);
            }
        });
        LZJMS.喷火龙.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF1.png")));
        LZJMS.喷火龙.setText("喷火龙");
        LZJMS.喷火龙.setBorderPainted(false);
        LZJMS.喷火龙.setContentAreaFilled(false);
        LZJMS.喷火龙.setFocusPainted(false);
        LZJMS.喷火龙.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON1.png")));
        LZJMS.喷火龙.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.喷火龙StateChanged(evt);
            }
        });
        LZJMS.喷火龙.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.喷火龙ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel62Layout = new GroupLayout((Container) this.jPanel62);
        this.jPanel62.setLayout((LayoutManager) jPanel62Layout);
        jPanel62Layout.setHorizontalGroup((Group) jPanel62Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel62Layout.createSequentialGroup().addContainerGap(-1, 32767).addGroup((Group) jPanel62Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) LZJMS.蘑菇仔, -1, -1, 32767).addComponent((Component) LZJMS.绿水灵, -1, -1, 32767).addComponent((Component) LZJMS.胖企鹅, -1, -1, 32767).addComponent((Component) LZJMS.星精灵, -1, -1, 32767).addComponent((Component) LZJMS.白雪人, -1, -1, 32767).addComponent((Component) LZJMS.大海龟, -1, -1, 32767).addComponent((Component) LZJMS.章鱼怪, -1, -1, 32767).addComponent((Component) LZJMS.漂漂猪, -1, -1, 32767).addComponent((Component) LZJMS.顽皮猴, -1, -1, 32767).addComponent((Component) LZJMS.蓝蜗牛, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel62Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.小青蛇).addComponent((Component) LZJMS.红螃蟹).addComponent((Component) LZJMS.石头人).addComponent((Component) LZJMS.大灰狼).addComponent((Component) LZJMS.紫色猫).addComponent((Component) LZJMS.青鳄鱼).addComponent((Component) LZJMS.花蘑菇).addComponent((Component) LZJMS.火野猪).addComponent((Component) LZJMS.喷火龙).addComponent((Component) LZJMS.小白兔)).addContainerGap()));
        jPanel62Layout.setVerticalGroup((Group) jPanel62Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel62Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel62Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group) jPanel62Layout.createSequentialGroup().addComponent((Component) LZJMS.小青蛇, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.红螃蟹, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.石头人, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.紫色猫, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.大灰狼, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.青鳄鱼, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.花蘑菇, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.火野猪, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.小白兔, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.喷火龙, -2, 30, -2)).addGroup((Group) jPanel62Layout.createSequentialGroup().addComponent((Component) LZJMS.蓝蜗牛, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.蘑菇仔, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.绿水灵, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.星精灵, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.胖企鹅, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.白雪人, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.大海龟, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.章鱼怪, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.顽皮猴, -2, 30, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.漂漂猪, -2, 30, -2))).addContainerGap(-1, 32767)));
        final GroupLayout jPanel1Layout = new GroupLayout((Container) this.jPanel1);
        this.jPanel1.setLayout((LayoutManager) jPanel1Layout);
        jPanel1Layout.setHorizontalGroup((Group) jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel1Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel62, -2, -1, -2).addContainerGap(1130, 32767)));
        jPanel1Layout.setVerticalGroup((Group) jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel1Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel62, -2, -1, -2).addContainerGap(286, 32767)));
        this.宠吸功能面板.addTab("大区设定", (Component) this.jPanel1);
        this.jPanel98.setBorder((Border) BorderFactory.createTitledBorder("定制倍率"));
        this.怪物血量表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"怪物ID", "血量倍率"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane15.setViewportView((Component) this.怪物血量表);
        final GroupLayout jPanel98Layout = new GroupLayout((Container) this.jPanel98);
        this.jPanel98.setLayout((LayoutManager) jPanel98Layout);
        jPanel98Layout.setHorizontalGroup((Group) jPanel98Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel98Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane15, -1, 558, 32767).addContainerGap()));
        jPanel98Layout.setVerticalGroup((Group) jPanel98Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel98Layout.createSequentialGroup().addComponent((Component) this.jScrollPane15, -2, 528, -2).addGap(0, 20, 32767)));
        this.jPanel46.setBorder((Border) BorderFactory.createTitledBorder("普通怪物"));
        this.jLabel20.setText("全局开启最低等级:");
        LZJMS.全局血量等级.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.全局血量等级ActionPerformed(evt);
            }
        });
        this.jLabel23.setText("通用血量倍率:");
        LZJMS.全局血量倍率.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.全局血量倍率ActionPerformed(evt);
            }
        });
        this.jButton114.setText("修改全局血量倍率(≥等级生效)");
        this.jButton114.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton114ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel46Layout = new GroupLayout((Container) this.jPanel46);
        this.jPanel46.setLayout((LayoutManager) jPanel46Layout);
        jPanel46Layout.setHorizontalGroup((Group) jPanel46Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel46Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel46Layout.createParallelGroup(Alignment.TRAILING, false).addComponent((Component) this.jButton114, -1, -1, 32767).addGroup(Alignment.LEADING, (Group) jPanel46Layout.createSequentialGroup().addComponent((Component) this.jLabel23, -2, 119, -2).addGap(18, 18, 18).addComponent((Component) LZJMS.全局血量倍率)).addGroup(Alignment.LEADING, (Group) jPanel46Layout.createSequentialGroup().addComponent((Component) this.jLabel20, -2, 119, -2).addGap(18, 18, 18).addComponent((Component) LZJMS.全局血量等级, -2, 80, -2))).addGap(0, 15, 32767)));
        jPanel46Layout.setVerticalGroup((Group) jPanel46Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel46Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel46Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel20, -2, 30, -2).addComponent((Component) LZJMS.全局血量等级, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel46Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel23, -2, 30, -2).addComponent((Component) LZJMS.全局血量倍率, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jButton114).addContainerGap(15, 32767)));
        this.jPanel99.setBorder((Border) BorderFactory.createTitledBorder("倍率调整"));
        this.jButton115.setText("删除血量倍率");
        this.jButton115.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton115ActionPerformed(evt);
            }
        });
        this.jButton116.setText("修改血量倍率");
        this.jButton116.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton116ActionPerformed(evt);
            }
        });
        this.jButton117.setText("新增血量倍率");
        this.jButton117.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton117ActionPerformed(evt);
            }
        });
        this.jButton118.setText("刷新血量倍率");
        this.jButton118.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton118ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel99Layout = new GroupLayout((Container) this.jPanel99);
        this.jPanel99.setLayout((LayoutManager) jPanel99Layout);
        jPanel99Layout.setHorizontalGroup((Group) jPanel99Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel99Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel99Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jButton117, Alignment.TRAILING, -1, -1, 32767).addComponent((Component) this.jButton115, -1, -1, 32767).addComponent((Component) this.jButton116, -1, 220, 32767).addComponent((Component) this.jButton118, -1, -1, 32767)).addContainerGap(-1, 32767)));
        jPanel99Layout.setVerticalGroup((Group) jPanel99Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel99Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jButton117).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jButton115).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jButton116).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.jButton118).addContainerGap()));
        this.jPanel32.setBorder((Border) BorderFactory.createTitledBorder("怪物血量修正"));
        this.jLabel133.setText("怪物增加血量开关");
        LZJMS.怪物减伤开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.怪物减伤开关.setBorderPainted(false);
        LZJMS.怪物减伤开关.setContentAreaFilled(false);
        LZJMS.怪物减伤开关.setFocusPainted(false);
        LZJMS.怪物减伤开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.怪物减伤开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.怪物减伤开关StateChanged(evt);
            }
        });
        LZJMS.怪物减伤开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.怪物减伤开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel32Layout = new GroupLayout((Container) this.jPanel32);
        this.jPanel32.setLayout((LayoutManager) jPanel32Layout);
        jPanel32Layout.setHorizontalGroup((Group) jPanel32Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel32Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jLabel133).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.怪物减伤开关, -2, 74, -2).addGap(19, 19, 19)));
        jPanel32Layout.setVerticalGroup((Group) jPanel32Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel32Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel32Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.怪物减伤开关, -2, 30, -2).addComponent((Component) this.jLabel133)).addContainerGap(122, 32767)));
        final GroupLayout jPanel11Layout = new GroupLayout((Container) this.jPanel11);
        this.jPanel11.setLayout((LayoutManager) jPanel11Layout);
        jPanel11Layout.setHorizontalGroup((Group) jPanel11Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel11Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel98, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel11Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel99, -1, -1, 32767).addComponent((Component) this.jPanel46, -1, -1, 32767).addComponent((Component) this.jPanel32, -1, -1, 32767)).addGap(630, 630, 630)));
        jPanel11Layout.setVerticalGroup((Group) jPanel11Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel11Layout.createSequentialGroup().addGroup((Group) jPanel11Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel98, -2, -1, -2).addGroup((Group) jPanel11Layout.createSequentialGroup().addComponent((Component) this.jPanel46, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jPanel32, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.jPanel99, -2, -1, -2))).addGap(0, 122, 32767)));
        this.宠吸功能面板.addTab("怪物血量", (Component) this.jPanel11);
        this.jPanel29.setBorder((Border) BorderFactory.createTitledBorder("多倍怪物地图列表"));
        LZJMS.多倍怪物地图列表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"排序", "地图代码"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, false};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane11.setViewportView((Component) LZJMS.多倍怪物地图列表);
        if (LZJMS.多倍怪物地图列表.getColumnModel().getColumnCount() > 0) {
            LZJMS.多倍怪物地图列表.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.多倍怪物地图列表.getColumnModel().getColumn(1).setPreferredWidth(20);
        }
        this.jLabel134.setText("排序:");
        LZJMS.多倍地图排序.setEnabled(false);
        this.jLabel135.setText("原地图代码:");
        LZJMS.多倍地图原地图代码.setEnabled(false);
        this.jLabel136.setText("地图代码:");
        this.增加地图.setText("增加地图");
        this.增加地图.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.增加地图ActionPerformed(evt);
            }
        });
        this.修改地图.setText("修改地图");
        this.修改地图.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.修改地图ActionPerformed(evt);
            }
        });
        this.删除地图.setText("删除地图");
        this.删除地图.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.删除地图ActionPerformed(evt);
            }
        });
        this.刷新地图.setText("刷新地图");
        this.刷新地图.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.刷新地图ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel29Layout = new GroupLayout((Container) this.jPanel29);
        this.jPanel29.setLayout((LayoutManager) jPanel29Layout);
        jPanel29Layout.setHorizontalGroup((Group) jPanel29Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel29Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel29Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane11, -2, 556, -2).addGroup((Group) jPanel29Layout.createSequentialGroup().addComponent((Component) this.jLabel134).addGap(18, 18, 18).addComponent((Component) LZJMS.多倍地图排序, -2, 69, -2).addGap(18, 18, 18).addComponent((Component) this.jLabel135).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) LZJMS.多倍地图原地图代码, -2, 120, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) this.jLabel136).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) LZJMS.多倍地图现地图代码, -2, 120, -2)).addGroup((Group) jPanel29Layout.createSequentialGroup().addGap(209, 209, 209).addComponent((Component) this.增加地图).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.修改地图).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.删除地图).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.刷新地图))).addContainerGap(22, 32767)));
        jPanel29Layout.setVerticalGroup((Group) jPanel29Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel29Layout.createSequentialGroup().addComponent((Component) this.jScrollPane11, -2, 513, -2).addGap(18, 18, 18).addGroup((Group) jPanel29Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel134).addComponent((Component) LZJMS.多倍地图排序, -2, -1, -2).addComponent((Component) this.jLabel135).addComponent((Component) LZJMS.多倍地图原地图代码, -2, -1, -2).addComponent((Component) this.jLabel136).addComponent((Component) LZJMS.多倍地图现地图代码, -2, -1, -2)).addGap(18, 18, 18).addGroup((Group) jPanel29Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.删除地图).addComponent((Component) this.增加地图).addComponent((Component) this.修改地图).addComponent((Component) this.刷新地图)).addGap(0, 16, 32767)));
        this.jPanel31.setBorder((Border) BorderFactory.createTitledBorder("多倍怪物功能列表"));
        this.jLabel129.setText("多倍地图开关");
        LZJMS.怪物多倍地图开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.怪物多倍地图开关.setBorderPainted(false);
        LZJMS.怪物多倍地图开关.setContentAreaFilled(false);
        LZJMS.怪物多倍地图开关.setFocusPainted(false);
        LZJMS.怪物多倍地图开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.怪物多倍地图开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.怪物多倍地图开关StateChanged(evt);
            }
        });
        LZJMS.怪物多倍地图开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.怪物多倍地图开关ActionPerformed(evt);
            }
        });
        this.jLabel130.setText("所有地图多倍怪物开关");
        LZJMS.怪物地图多倍怪物开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.怪物地图多倍怪物开关.setBorderPainted(false);
        LZJMS.怪物地图多倍怪物开关.setContentAreaFilled(false);
        LZJMS.怪物地图多倍怪物开关.setFocusPainted(false);
        LZJMS.怪物地图多倍怪物开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.怪物地图多倍怪物开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.怪物地图多倍怪物开关StateChanged(evt);
            }
        });
        LZJMS.怪物地图多倍怪物开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.怪物地图多倍怪物开关ActionPerformed(evt);
            }
        });
        this.jLabel131.setText("多倍地图倍率");
        LZJMS.多倍地图倍率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.多倍地图倍率FocusLost(evt);
            }
        });
        this.jLabel132.setText("怪物刷新线程频率");
        LZJMS.怪物刷新频率设定.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.怪物刷新频率设定FocusLost(evt);
            }
        });
        this.jLabel212.setText("地图怪物刷新间隔");
        LZJMS.地图刷新频率.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.地图刷新频率FocusLost(evt);
            }
        });
        final GroupLayout jPanel31Layout = new GroupLayout((Container) this.jPanel31);
        this.jPanel31.setLayout((LayoutManager) jPanel31Layout);
        jPanel31Layout.setHorizontalGroup((Group) jPanel31Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel31Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel31Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel31Layout.createSequentialGroup().addGroup((Group) jPanel31Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel31Layout.createSequentialGroup().addComponent((Component) this.jLabel129).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.怪物多倍地图开关, -2, 74, -2)).addGroup(Alignment.TRAILING, (Group) jPanel31Layout.createSequentialGroup().addGroup((Group) jPanel31Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel130).addComponent((Component) this.jLabel131)).addPreferredGap(ComponentPlacement.RELATED, 84, 32767).addGroup((Group) jPanel31Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.多倍地图倍率, -2, 69, -2).addComponent((Component) LZJMS.怪物地图多倍怪物开关, -2, 74, -2)))).addContainerGap()).addGroup((Group) jPanel31Layout.createSequentialGroup().addComponent((Component) this.jLabel132).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.怪物刷新频率设定, -2, 69, -2).addGap(15, 15, 15)).addGroup((Group) jPanel31Layout.createSequentialGroup().addComponent((Component) this.jLabel212).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.地图刷新频率, -2, 69, -2).addGap(15, 15, 15)))));
        jPanel31Layout.setVerticalGroup((Group) jPanel31Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel31Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel31Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel129).addComponent((Component) LZJMS.怪物多倍地图开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel31Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.怪物地图多倍怪物开关, -2, 30, -2).addComponent((Component) this.jLabel130)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel31Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.多倍地图倍率, -2, -1, -2).addComponent((Component) this.jLabel131)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel31Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel132).addComponent((Component) LZJMS.怪物刷新频率设定, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel31Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel212).addComponent((Component) LZJMS.地图刷新频率, -2, -1, -2)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel24Layout = new GroupLayout((Container) this.jPanel24);
        this.jPanel24.setLayout((LayoutManager) jPanel24Layout);
        jPanel24Layout.setHorizontalGroup((Group) jPanel24Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel24Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel29, -2, -1, -2).addGap(18, 18, 18).addComponent((Component) this.jPanel31, -2, -1, -2).addContainerGap(552, 32767)));
        jPanel24Layout.setVerticalGroup((Group) jPanel24Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel24Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel24Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel31, -1, -1, 32767).addComponent((Component) this.jPanel29, -1, -1, 32767)).addContainerGap(51, 32767)));
        this.宠吸功能面板.addTab("多倍怪物", (Component) this.jPanel24);
        this.jPanel34.setBorder((Border) BorderFactory.createTitledBorder("野外BOSS刷新"));
        LZJMS.野外BOSS刷新时间.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"野外BOSS", "地图代码", "横坐标", "纵坐标", "说明", "刷新间隔/分"}) {
            boolean[] canEdit = {false, false, false, false, false, false};

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane12.setViewportView((Component) LZJMS.野外BOSS刷新时间);
        if (LZJMS.野外BOSS刷新时间.getColumnModel().getColumnCount() > 0) {
            LZJMS.野外BOSS刷新时间.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.野外BOSS刷新时间.getColumnModel().getColumn(1).setPreferredWidth(20);
            LZJMS.野外BOSS刷新时间.getColumnModel().getColumn(2).setPreferredWidth(20);
            LZJMS.野外BOSS刷新时间.getColumnModel().getColumn(3).setPreferredWidth(20);
            LZJMS.野外BOSS刷新时间.getColumnModel().getColumn(5).setPreferredWidth(20);
        }
        this.jPanel35.setBorder((Border) BorderFactory.createTitledBorder("操作列表"));
        this.jLabel137.setText("原地图:");
        LZJMS.野外BOSS刷新时间原地图.setEnabled(false);
        this.jLabel138.setText("现地图:");
        this.jLabel139.setText("BOSS代码:");
        this.jLabel140.setText("刷新时间/分:");
        this.jLabel141.setText("横坐标:");
        this.jLabel142.setText("纵坐标:");
        this.jLabel143.setText("说明:");
        this.野外BOSS内容增加.setText("增加内容");
        this.野外BOSS内容增加.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.野外BOSS内容增加ActionPerformed(evt);
            }
        });
        this.野外BOSS内容删除.setText("删除内容");
        this.野外BOSS内容删除.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.野外BOSS内容删除ActionPerformed(evt);
            }
        });
        this.野外BOSS内容修改.setText("修改内容");
        this.野外BOSS内容修改.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.野外BOSS内容修改ActionPerformed(evt);
            }
        });
        this.野外BOSS内容刷新.setText("刷新内容");
        this.野外BOSS内容刷新.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.野外BOSS内容刷新ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel35Layout = new GroupLayout((Container) this.jPanel35);
        this.jPanel35.setLayout((LayoutManager) jPanel35Layout);
        jPanel35Layout.setHorizontalGroup((Group) jPanel35Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel35Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel35Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group) jPanel35Layout.createSequentialGroup().addComponent((Component) this.野外BOSS内容增加).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.野外BOSS内容删除).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.野外BOSS内容修改).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.野外BOSS内容刷新)).addGroup((Group) jPanel35Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group) jPanel35Layout.createSequentialGroup().addComponent((Component) this.jLabel137).addGap(18, 18, 18).addComponent((Component) LZJMS.野外BOSS刷新时间原地图, -2, 120, -2).addGap(18, 18, 18).addComponent((Component) this.jLabel138).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.野外BOSS刷新时间现地图, -2, 120, -2).addGap(18, 18, 18).addComponent((Component) this.jLabel139).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.野外BOSS刷新时间boss代码, -2, 120, -2).addGap(18, 18, 18).addComponent((Component) this.jLabel140).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) LZJMS.野外BOSS刷新时间分, -2, 120, -2)).addGroup((Group) jPanel35Layout.createSequentialGroup().addComponent((Component) this.jLabel141).addGap(18, 18, 18).addComponent((Component) LZJMS.野外BOSS刷新时间横坐标, -2, 120, -2).addGap(18, 18, 18).addComponent((Component) this.jLabel142).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.野外BOSS刷新时间纵坐标, -2, 120, -2).addGap(18, 18, 18).addComponent((Component) this.jLabel143).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.野外BOSS刷新时间说明)))).addContainerGap(198, 32767)));
        jPanel35Layout.setVerticalGroup((Group) jPanel35Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel35Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel35Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel137).addComponent((Component) LZJMS.野外BOSS刷新时间原地图, -2, -1, -2).addComponent((Component) this.jLabel138).addComponent((Component) LZJMS.野外BOSS刷新时间现地图, -2, -1, -2).addComponent((Component) this.jLabel139).addComponent((Component) LZJMS.野外BOSS刷新时间boss代码, -2, -1, -2).addComponent((Component) this.jLabel140).addComponent((Component) LZJMS.野外BOSS刷新时间分, -2, -1, -2)).addGap(18, 18, 18).addGroup((Group) jPanel35Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel35Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel141).addComponent((Component) LZJMS.野外BOSS刷新时间横坐标, -2, -1, -2)).addGroup((Group) jPanel35Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel142).addComponent((Component) LZJMS.野外BOSS刷新时间纵坐标, -2, -1, -2).addComponent((Component) this.jLabel143).addComponent((Component) LZJMS.野外BOSS刷新时间说明, -2, -1, -2))).addGap(18, 18, 18).addGroup((Group) jPanel35Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.野外BOSS内容修改).addComponent((Component) this.野外BOSS内容增加).addComponent((Component) this.野外BOSS内容删除).addComponent((Component) this.野外BOSS内容刷新)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel34Layout = new GroupLayout((Container) this.jPanel34);
        this.jPanel34.setLayout((LayoutManager) jPanel34Layout);
        jPanel34Layout.setHorizontalGroup((Group) jPanel34Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel34Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel34Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane12, -1, 1000, 32767).addComponent((Component) this.jPanel35, -1, -1, 32767)).addContainerGap()));
        jPanel34Layout.setVerticalGroup((Group) jPanel34Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel34Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane12, -2, 359, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) this.jPanel35, -1, -1, 32767).addGap(30, 30, 30)));
        final GroupLayout jPanel33Layout = new GroupLayout((Container) this.jPanel33);
        this.jPanel33.setLayout((LayoutManager) jPanel33Layout);
        jPanel33Layout.setHorizontalGroup((Group) jPanel33Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel33Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel34, -2, -1, -2).addContainerGap(448, 32767)));
        jPanel33Layout.setVerticalGroup((Group) jPanel33Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel33Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel34, -2, -1, -2).addContainerGap(106, 32767)));
        this.宠吸功能面板.addTab("boss刷新", (Component) this.jPanel33);
        this.jPanel82.setBorder((Border) BorderFactory.createTitledBorder("自建NPC清理"));
        LZJMS.自建NPC列表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"NPC ID", "所在地图"}) {
            boolean[] canEdit = {false, false};

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        LZJMS.自建NPC列表.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane108.setViewportView((Component) LZJMS.自建NPC列表);
        if (LZJMS.自建NPC列表.getColumnModel().getColumnCount() > 0) {
            LZJMS.自建NPC列表.getColumnModel().getColumn(0).setResizable(false);
            LZJMS.自建NPC列表.getColumnModel().getColumn(1).setResizable(false);
        }
        this.NPC刷新.setText("刷新列表");
        this.NPC刷新.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.NPC刷新ActionPerformed(evt);
            }
        });
        this.NPC删除.setText("删除NPC");
        this.NPC删除.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.NPC删除ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel82Layout = new GroupLayout((Container) this.jPanel82);
        this.jPanel82.setLayout((LayoutManager) jPanel82Layout);
        jPanel82Layout.setHorizontalGroup((Group) jPanel82Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel82Layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent((Component) this.NPC刷新, -2, 93, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.NPC删除, -2, 93, -2).addContainerGap()).addGroup((Group) jPanel82Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane108, -2, 430, -2).addContainerGap(-1, 32767)));
        jPanel82Layout.setVerticalGroup((Group) jPanel82Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group) jPanel82Layout.createSequentialGroup().addComponent((Component) this.jScrollPane108, -2, 0, 32767).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel82Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.NPC刷新).addComponent((Component) this.NPC删除))));
        this.jPanel79.setBorder((Border) BorderFactory.createTitledBorder("怪物经验限制[建议只设置BOSS/小怪会导致服务器卡死]"));
        LZJMS.怪物经验限制表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"怪物ID", "经验限制(次)"}) {
            boolean[] canEdit = {false, true};

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        LZJMS.怪物经验限制表.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane105.setViewportView((Component) LZJMS.怪物经验限制表);
        if (LZJMS.怪物经验限制表.getColumnModel().getColumnCount() > 0) {
            LZJMS.怪物经验限制表.getColumnModel().getColumn(0).setResizable(false);
            LZJMS.怪物经验限制表.getColumnModel().getColumn(1).setResizable(false);
        }
        this.怪物经验限制增加.setText("增加限制");
        this.怪物经验限制增加.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.怪物经验限制增加ActionPerformed(evt);
            }
        });
        this.怪物经验限制删除.setText("删除限制");
        this.怪物经验限制删除.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.怪物经验限制删除ActionPerformed(evt);
            }
        });
        this.怪物经验限制修改.setText("保存修改");
        this.怪物经验限制修改.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.怪物经验限制修改ActionPerformed(evt);
            }
        });
        this.怪物经验限制刷新.setText("刷新列表");
        this.怪物经验限制刷新.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.怪物经验限制刷新ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel79Layout = new GroupLayout((Container) this.jPanel79);
        this.jPanel79.setLayout((LayoutManager) jPanel79Layout);
        jPanel79Layout.setHorizontalGroup((Group) jPanel79Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel79Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel79Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group) jPanel79Layout.createSequentialGroup().addComponent((Component) this.怪物经验限制刷新, -2, 93, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.怪物经验限制删除, -2, 93, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.怪物经验限制增加, -2, 93, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.怪物经验限制修改, -2, 93, -2)).addComponent((Component) this.jScrollPane105, -2, 430, -2)).addContainerGap(-1, 32767)));
        jPanel79Layout.setVerticalGroup((Group) jPanel79Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel79Layout.createSequentialGroup().addComponent((Component) this.jScrollPane105, -2, 234, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel79Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.怪物经验限制刷新).addComponent((Component) this.怪物经验限制删除).addComponent((Component) this.怪物经验限制增加).addComponent((Component) this.怪物经验限制修改))));
        final GroupLayout jPanel36Layout = new GroupLayout((Container) this.jPanel36);
        this.jPanel36.setLayout((LayoutManager) jPanel36Layout);
        jPanel36Layout.setHorizontalGroup((Group) jPanel36Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group) jPanel36Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel82, -2, 450, -2).addGap(18, 18, 18).addComponent((Component) this.jPanel79, -2, -1, -2).addContainerGap(550, 32767)));
        jPanel36Layout.setVerticalGroup((Group) jPanel36Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel36Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel36Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel79, -1, -1, 32767).addComponent((Component) this.jPanel82, -1, -1, 32767)).addContainerGap(397, 32767)));
        this.宠吸功能面板.addTab("特殊操作", (Component) this.jPanel36);
        this.jPanel80.setBorder((Border) BorderFactory.createTitledBorder("封禁IP"));
        LZJMS.封禁IP地址.setModel((TableModel) new DefaultTableModel(new Object[0][], (Object[]) new String[]{"IP地址"}));
        LZJMS.封禁IP地址.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane106.setViewportView((Component) LZJMS.封禁IP地址);
        if (LZJMS.封禁IP地址.getColumnModel().getColumnCount() > 0) {
            LZJMS.封禁IP地址.getColumnModel().getColumn(0).setResizable(false);
        }
        this.jButton49.setText("删除选中IP");
        this.jButton49.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton49ActionPerformed(evt);
            }
        });
        this.jButton50.setText("刷新列表");
        this.jButton50.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton50ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel80Layout = new GroupLayout((Container) this.jPanel80);
        this.jPanel80.setLayout((LayoutManager) jPanel80Layout);
        jPanel80Layout.setHorizontalGroup((Group) jPanel80Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel80Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel80Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel80Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent((Component) this.jButton50, -2, 98, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jButton49, -2, 120, -2)).addComponent((Component) this.jScrollPane106, -1, 402, 32767)).addContainerGap()));
        jPanel80Layout.linkSize(0, this.jButton49, this.jButton50);
        jPanel80Layout.setVerticalGroup((Group) jPanel80Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group) jPanel80Layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent((Component) this.jScrollPane106, -2, 311, -2).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel80Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jButton49).addComponent((Component) this.jButton50))));
        this.jPanel81.setBorder((Border) BorderFactory.createTitledBorder("封禁MAC"));
        LZJMS.封禁MAC地址.setModel((TableModel) new DefaultTableModel(new Object[0][], (Object[]) new String[]{"MAC地址"}));
        LZJMS.封禁MAC地址.getTableHeader().setReorderingAllowed(false);
        this.jScrollPane107.setViewportView((Component) LZJMS.封禁MAC地址);
        if (LZJMS.封禁MAC地址.getColumnModel().getColumnCount() > 0) {
            LZJMS.封禁MAC地址.getColumnModel().getColumn(0).setResizable(false);
        }
        this.jButton52.setText("删除选中MAC");
        this.jButton52.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton52ActionPerformed(evt);
            }
        });
        this.jButton53.setText("刷新列表");
        this.jButton53.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton53ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel81Layout = new GroupLayout((Container) this.jPanel81);
        this.jPanel81.setLayout((LayoutManager) jPanel81Layout);
        jPanel81Layout.setHorizontalGroup((Group) jPanel81Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel81Layout.createSequentialGroup().addGroup((Group) jPanel81Layout.createParallelGroup(Alignment.TRAILING, false).addGroup(Alignment.LEADING, (Group) jPanel81Layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent((Component) this.jButton53, -2, 98, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jButton52, -2, 120, -2)).addGroup(Alignment.LEADING, (Group) jPanel81Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane107, -2, 392, -2))).addContainerGap(20, 32767)));
        jPanel81Layout.linkSize(0, this.jButton52, this.jButton53);
        jPanel81Layout.setVerticalGroup((Group) jPanel81Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group) jPanel81Layout.createSequentialGroup().addGap(14, 14, 14).addComponent((Component) this.jScrollPane107, -2, 308, -2).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel81Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jButton52).addComponent((Component) this.jButton53))));
        final GroupLayout jPanel47Layout = new GroupLayout((Container) this.jPanel47);
        this.jPanel47.setLayout((LayoutManager) jPanel47Layout);
        jPanel47Layout.setHorizontalGroup((Group) jPanel47Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel47Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel80, -2, -1, -2).addGap(18, 18, 18).addComponent((Component) this.jPanel81, -2, -1, -2).addContainerGap(594, 32767)));
        jPanel47Layout.setVerticalGroup((Group) jPanel47Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel47Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel47Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel80, -1, -1, 32767).addComponent((Component) this.jPanel81, -1, -1, 32767)).addContainerGap(305, 32767)));
        this.宠吸功能面板.addTab("解封账号", (Component) this.jPanel47);
        this.jPanel56.setBorder((Border) BorderFactory.createTitledBorder("宠物吸取道具不参与地图"));
        LZJMS.宠物吸取不参与地图列表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"排序代码", "地图代码"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane16.setViewportView((Component) LZJMS.宠物吸取不参与地图列表);
        if (LZJMS.宠物吸取不参与地图列表.getColumnModel().getColumnCount() > 0) {
            LZJMS.宠物吸取不参与地图列表.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.宠物吸取不参与地图列表.getColumnModel().getColumn(1).setPreferredWidth(20);
        }
        this.jLabel180.setText("排序代码:");
        LZJMS.宠物吸取道具不参与排序.setEnabled(false);
        this.jLabel181.setText("现地图代码:");
        LZJMS.宠物吸取不参与地图代码.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.宠物吸取不参与地图代码ActionPerformed(evt);
            }
        });
        this.不参与叠加道具增加1.setText("增加");
        this.不参与叠加道具增加1.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.不参与叠加道具增加1ActionPerformed(evt);
            }
        });
        this.不参与叠加道具删除1.setText("删除");
        this.不参与叠加道具删除1.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.不参与叠加道具删除1ActionPerformed(evt);
            }
        });
        this.修改地图1.setText("修改");
        this.修改地图1.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.修改地图1ActionPerformed(evt);
            }
        });
        this.宠物不参与地图初始.setText("初始化表单信息");
        this.宠物不参与地图初始.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.宠物不参与地图初始ActionPerformed(evt);
            }
        });
        this.jLabel189.setText("原地图代码:");
        LZJMS.宠物吸取不参与地图代码1.setEnabled(false);
        LZJMS.宠物吸取不参与地图代码1.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.宠物吸取不参与地图代码1ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel56Layout = new GroupLayout((Container) this.jPanel56);
        this.jPanel56.setLayout((LayoutManager) jPanel56Layout);
        jPanel56Layout.setHorizontalGroup((Group) jPanel56Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel56Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel56Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane16, -2, 0, 32767).addGroup((Group) jPanel56Layout.createSequentialGroup().addGroup((Group) jPanel56Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel180).addComponent((Component) this.不参与叠加道具增加1, -2, 79, -2)).addGroup((Group) jPanel56Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group) jPanel56Layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.宠物吸取道具不参与排序, -2, 125, -2)).addGroup(Alignment.LEADING, (Group) jPanel56Layout.createSequentialGroup().addGap(43, 43, 43).addComponent((Component) this.修改地图1, -2, 79, -2).addPreferredGap(ComponentPlacement.RELATED, 43, 32767).addComponent((Component) this.不参与叠加道具删除1, -2, 79, -2)).addGroup(Alignment.LEADING, (Group) jPanel56Layout.createSequentialGroup().addGap(1, 1, 1).addComponent((Component) this.宠物不参与地图初始, -2, 160, -2).addGap(0, 0, 32767)))).addGroup((Group) jPanel56Layout.createSequentialGroup().addComponent((Component) this.jLabel181).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.宠物吸取不参与地图代码, -2, 125, -2)).addGroup((Group) jPanel56Layout.createSequentialGroup().addComponent((Component) this.jLabel189).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.宠物吸取不参与地图代码1, -2, 125, -2))).addContainerGap()));
        jPanel56Layout.setVerticalGroup((Group) jPanel56Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel56Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane16, -2, 344, -2).addGap(18, 18, 18).addGroup((Group) jPanel56Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) LZJMS.宠物吸取道具不参与排序, -2, -1, -2).addComponent((Component) this.jLabel180)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel56Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel189).addComponent((Component) LZJMS.宠物吸取不参与地图代码1, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group) jPanel56Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel181).addComponent((Component) LZJMS.宠物吸取不参与地图代码, -2, -1, -2)).addGap(30, 30, 30).addGroup((Group) jPanel56Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.不参与叠加道具删除1).addComponent((Component) this.不参与叠加道具增加1).addComponent((Component) this.修改地图1)).addGap(18, 18, 18).addComponent((Component) this.宠物不参与地图初始).addGap(31, 31, 31)));
        this.jPanel57.setBorder((Border) BorderFactory.createTitledBorder("功能开关"));
        this.jLabel148.setText("特殊宠物吸取开关");
        LZJMS.特殊宠物吸取开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.特殊宠物吸取开关.setBorderPainted(false);
        LZJMS.特殊宠物吸取开关.setContentAreaFilled(false);
        LZJMS.特殊宠物吸取开关.setFocusPainted(false);
        LZJMS.特殊宠物吸取开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.特殊宠物吸取开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.特殊宠物吸取开关StateChanged(evt);
            }
        });
        LZJMS.特殊宠物吸取开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.特殊宠物吸取开关ActionPerformed(evt);
            }
        });
        this.jLabel165.setText("特殊宠物吸物开关");
        LZJMS.特殊宠物吸物开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.特殊宠物吸物开关.setBorderPainted(false);
        LZJMS.特殊宠物吸物开关.setContentAreaFilled(false);
        LZJMS.特殊宠物吸物开关.setFocusPainted(false);
        LZJMS.特殊宠物吸物开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.特殊宠物吸物开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.特殊宠物吸物开关StateChanged(evt);
            }
        });
        LZJMS.特殊宠物吸物开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.特殊宠物吸物开关ActionPerformed(evt);
            }
        });
        LZJMS.特殊宠物吸金开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.特殊宠物吸金开关.setBorderPainted(false);
        LZJMS.特殊宠物吸金开关.setContentAreaFilled(false);
        LZJMS.特殊宠物吸金开关.setFocusPainted(false);
        LZJMS.特殊宠物吸金开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.特殊宠物吸金开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.特殊宠物吸金开关StateChanged(evt);
            }
        });
        LZJMS.特殊宠物吸金开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.特殊宠物吸金开关ActionPerformed(evt);
            }
        });
        this.jLabel167.setText("特殊宠物吸金开关");
        this.jLabel169.setText("特殊宠物吸物无法使用地图开关");
        LZJMS.特殊宠物吸物无法使用地图开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.特殊宠物吸物无法使用地图开关.setBorderPainted(false);
        LZJMS.特殊宠物吸物无法使用地图开关.setContentAreaFilled(false);
        LZJMS.特殊宠物吸物无法使用地图开关.setFocusPainted(false);
        LZJMS.特殊宠物吸物无法使用地图开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.特殊宠物吸物无法使用地图开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.特殊宠物吸物无法使用地图开关StateChanged(evt);
            }
        });
        LZJMS.特殊宠物吸物无法使用地图开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.特殊宠物吸物无法使用地图开关ActionPerformed(evt);
            }
        });
        this.jLabel187.setText("拥有道具强行玩家可以吸取物品");
        LZJMS.宠吸道具.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.宠吸道具FocusLost(evt);
            }
        });
        this.jLabel188.setText("道具强行宠吸开关");
        LZJMS.道具强行宠吸开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.道具强行宠吸开关.setBorderPainted(false);
        LZJMS.道具强行宠吸开关.setContentAreaFilled(false);
        LZJMS.道具强行宠吸开关.setFocusPainted(false);
        LZJMS.道具强行宠吸开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.道具强行宠吸开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.道具强行宠吸开关StateChanged(evt);
            }
        });
        LZJMS.道具强行宠吸开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.道具强行宠吸开关ActionPerformed(evt);
            }
        });
        this.jLabel207.setText("特殊全宠物吸金开关");
        LZJMS.特殊全宠物吸金开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.特殊全宠物吸金开关.setBorderPainted(false);
        LZJMS.特殊全宠物吸金开关.setContentAreaFilled(false);
        LZJMS.特殊全宠物吸金开关.setFocusPainted(false);
        LZJMS.特殊全宠物吸金开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.特殊全宠物吸金开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.特殊全宠物吸金开关StateChanged(evt);
            }
        });
        LZJMS.特殊全宠物吸金开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.特殊全宠物吸金开关ActionPerformed(evt);
            }
        });
        this.jLabel208.setText("特殊全宠物吸物开关");
        LZJMS.特殊全宠物吸物开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.特殊全宠物吸物开关.setBorderPainted(false);
        LZJMS.特殊全宠物吸物开关.setContentAreaFilled(false);
        LZJMS.特殊全宠物吸物开关.setFocusPainted(false);
        LZJMS.特殊全宠物吸物开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.特殊全宠物吸物开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.特殊全宠物吸物开关StateChanged(evt);
            }
        });
        LZJMS.特殊全宠物吸物开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.特殊全宠物吸物开关ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel57Layout = new GroupLayout((Container) this.jPanel57);
        this.jPanel57.setLayout((LayoutManager) jPanel57Layout);
        jPanel57Layout.setHorizontalGroup((Group) jPanel57Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel57Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel57Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel148).addComponent((Component) this.jLabel165).addComponent((Component) this.jLabel167).addComponent((Component) this.jLabel169).addComponent((Component) this.jLabel187).addComponent((Component) this.jLabel188).addComponent((Component) this.jLabel207).addComponent((Component) this.jLabel208)).addPreferredGap(ComponentPlacement.RELATED, 94, 32767).addGroup((Group) jPanel57Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.特殊宠物吸金开关, -2, 74, -2).addComponent((Component) LZJMS.特殊宠物吸取开关, -2, 74, -2).addComponent((Component) LZJMS.特殊宠物吸物开关, -2, 74, -2).addComponent((Component) LZJMS.特殊宠物吸物无法使用地图开关, -2, 74, -2).addComponent((Component) LZJMS.宠吸道具, -2, 74, -2).addComponent((Component) LZJMS.道具强行宠吸开关, -2, 74, -2).addComponent((Component) LZJMS.特殊全宠物吸金开关, -2, 74, -2).addComponent((Component) LZJMS.特殊全宠物吸物开关, -2, 74, -2)).addGap(24, 24, 24)));
        jPanel57Layout.setVerticalGroup((Group) jPanel57Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel57Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel57Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel148).addComponent((Component) LZJMS.特殊宠物吸取开关, -2, 30, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel57Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel165).addComponent((Component) LZJMS.特殊宠物吸物开关, -2, 30, -2)).addGap(10, 10, 10).addGroup((Group) jPanel57Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel167).addComponent((Component) LZJMS.特殊宠物吸金开关, -2, 30, -2)).addGap(10, 10, 10).addGroup((Group) jPanel57Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.特殊宠物吸物无法使用地图开关, -2, 30, -2).addComponent((Component) this.jLabel169)).addGap(10, 10, 10).addGroup((Group) jPanel57Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.特殊全宠物吸金开关, -2, 30, -2).addComponent((Component) this.jLabel207)).addGroup((Group) jPanel57Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel57Layout.createSequentialGroup().addGap(17, 17, 17).addComponent((Component) this.jLabel208)).addGroup((Group) jPanel57Layout.createSequentialGroup().addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) LZJMS.特殊全宠物吸物开关, -2, 30, -2))).addPreferredGap(ComponentPlacement.RELATED, 10, 32767).addGroup((Group) jPanel57Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.道具强行宠吸开关, -2, 30, -2).addComponent((Component) this.jLabel188)).addGap(10, 10, 10).addGroup((Group) jPanel57Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel187).addComponent((Component) LZJMS.宠吸道具, -2, -1, -2)).addGap(38, 38, 38)));
        this.jPanel88.setBorder((Border) BorderFactory.createTitledBorder("宠物吸取物品宠物代码列表"));
        LZJMS.宠物吸物列表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"排序代码", "宠物代码"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane18.setViewportView((Component) LZJMS.宠物吸物列表);
        if (LZJMS.宠物吸物列表.getColumnModel().getColumnCount() > 0) {
            LZJMS.宠物吸物列表.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.宠物吸物列表.getColumnModel().getColumn(1).setPreferredWidth(20);
        }
        this.jLabel185.setText("排序代码:");
        LZJMS.宠物吸物排序.setEnabled(false);
        this.jLabel186.setText("原宠物代码:");
        this.不参与叠加道具增加3.setText("增加");
        this.不参与叠加道具增加3.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.不参与叠加道具增加3ActionPerformed(evt);
            }
        });
        this.修改地图3.setText("修改");
        this.修改地图3.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.修改地图3ActionPerformed(evt);
            }
        });
        this.不参与叠加道具删除3.setText("删除");
        this.不参与叠加道具删除3.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.不参与叠加道具删除3ActionPerformed(evt);
            }
        });
        this.宠物吸取物品初始化.setText("初始化表单信息");
        this.宠物吸取物品初始化.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.宠物吸取物品初始化ActionPerformed(evt);
            }
        });
        this.jLabel191.setText("现宠物代码:");
        LZJMS.宠物吸物代码1.setEnabled(false);
        final GroupLayout jPanel88Layout = new GroupLayout((Container) this.jPanel88);
        this.jPanel88.setLayout((LayoutManager) jPanel88Layout);
        jPanel88Layout.setHorizontalGroup((Group) jPanel88Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel88Layout.createSequentialGroup().addGap(90, 90, 90).addComponent((Component) this.宠物吸取物品初始化, -2, 160, -2).addContainerGap(-1, 32767)).addGroup((Group) jPanel88Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel88Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel88Layout.createSequentialGroup().addGroup((Group) jPanel88Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel185).addComponent((Component) this.jLabel186)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel88Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.宠物吸物代码, Alignment.TRAILING, -2, 125, -2).addComponent((Component) LZJMS.宠物吸物代码1, Alignment.TRAILING, -2, 125, -2).addComponent((Component) LZJMS.宠物吸物排序, Alignment.TRAILING, -2, 125, -2)).addGap(12, 12, 12)).addGroup((Group) jPanel88Layout.createSequentialGroup().addComponent((Component) this.jScrollPane18, -2, 0, 32767).addContainerGap()).addGroup((Group) jPanel88Layout.createSequentialGroup().addGroup((Group) jPanel88Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel88Layout.createSequentialGroup().addComponent((Component) this.不参与叠加道具增加3, -2, 79, -2).addGap(43, 43, 43).addComponent((Component) this.修改地图3, -2, 79, -2).addGap(43, 43, 43).addComponent((Component) this.不参与叠加道具删除3, -2, 79, -2)).addComponent((Component) this.jLabel191)).addContainerGap(-1, 32767)))));
        jPanel88Layout.setVerticalGroup((Group) jPanel88Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel88Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane18, -2, 341, -2).addGap(18, 18, 18).addGroup((Group) jPanel88Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) LZJMS.宠物吸物排序, -2, -1, -2).addComponent((Component) this.jLabel185)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel88Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel186).addComponent((Component) LZJMS.宠物吸物代码1, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group) jPanel88Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel191).addComponent((Component) LZJMS.宠物吸物代码, -2, -1, -2)).addGap(30, 30, 30).addGroup((Group) jPanel88Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.不参与叠加道具删除3).addComponent((Component) this.不参与叠加道具增加3).addComponent((Component) this.修改地图3)).addGap(18, 18, 18).addComponent((Component) this.宠物吸取物品初始化).addGap(31, 31, 31)));
        this.jPanel89.setBorder((Border) BorderFactory.createTitledBorder("宠物吸取金币宠物代码列表"));
        LZJMS.宠物吸金列表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"排序代码", "宠物代码"}) {
            Class[] types = {String.class, Integer.class};
            boolean[] canEdit = {false, true};

            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane17.setViewportView((Component) LZJMS.宠物吸金列表);
        if (LZJMS.宠物吸金列表.getColumnModel().getColumnCount() > 0) {
            LZJMS.宠物吸金列表.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.宠物吸金列表.getColumnModel().getColumn(1).setPreferredWidth(20);
        }
        this.jLabel182.setText("排序代码:");
        LZJMS.宠物吸金排序.setEnabled(false);
        this.jLabel184.setText("原宠物代码:");
        this.不参与叠加道具增加2.setText("增加");
        this.不参与叠加道具增加2.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.不参与叠加道具增加2ActionPerformed(evt);
            }
        });
        this.修改地图2.setText("修改");
        this.修改地图2.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.修改地图2ActionPerformed(evt);
            }
        });
        this.不参与叠加道具删除2.setText("删除");
        this.不参与叠加道具删除2.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.不参与叠加道具删除2ActionPerformed(evt);
            }
        });
        this.宠物吸取金币初始.setText("初始化表单信息");
        this.宠物吸取金币初始.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.宠物吸取金币初始ActionPerformed(evt);
            }
        });
        this.jLabel190.setText("现宠物代码:");
        LZJMS.宠物吸金代码1.setEnabled(false);
        LZJMS.宠物吸金代码1.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.宠物吸金代码1ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel89Layout = new GroupLayout((Container) this.jPanel89);
        this.jPanel89.setLayout((LayoutManager) jPanel89Layout);
        jPanel89Layout.setHorizontalGroup((Group) jPanel89Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel89Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel89Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane17, -2, 0, 32767).addGroup(Alignment.TRAILING, (Group) jPanel89Layout.createSequentialGroup().addGroup((Group) jPanel89Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.不参与叠加道具增加2, -2, 79, -2).addGroup((Group) jPanel89Layout.createSequentialGroup().addGap(9, 9, 9).addGroup((Group) jPanel89Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel182).addComponent((Component) this.jLabel184).addComponent((Component) this.jLabel190)))).addGroup((Group) jPanel89Layout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING, (Group) jPanel89Layout.createSequentialGroup().addGroup((Group) jPanel89Layout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING, (Group) jPanel89Layout.createSequentialGroup().addGap(43, 43, 43).addComponent((Component) this.修改地图2, -2, 79, -2).addGap(43, 43, 43).addComponent((Component) this.不参与叠加道具删除2, -2, 79, -2)).addGroup(Alignment.LEADING, (Group) jPanel89Layout.createSequentialGroup().addGap(1, 1, 1).addComponent((Component) this.宠物吸取金币初始, -2, 160, -2))).addGap(0, 0, 32767)).addGroup((Group) jPanel89Layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel89Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component) LZJMS.宠物吸金排序, -2, 125, -2).addComponent((Component) LZJMS.宠物吸金代码, -2, 125, -2).addComponent((Component) LZJMS.宠物吸金代码1, -2, 125, -2)))))).addContainerGap()));
        jPanel89Layout.setVerticalGroup((Group) jPanel89Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel89Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane17, -2, 343, -2).addGap(18, 18, 18).addGroup((Group) jPanel89Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) LZJMS.宠物吸金排序, -2, -1, -2).addComponent((Component) this.jLabel182)).addGap(10, 10, 10).addGroup((Group) jPanel89Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel184).addComponent((Component) LZJMS.宠物吸金代码1, -2, -1, -2)).addGap(10, 10, 10).addGroup((Group) jPanel89Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel190).addComponent((Component) LZJMS.宠物吸金代码, -2, -1, -2)).addGap(30, 30, 30).addGroup((Group) jPanel89Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.不参与叠加道具删除2).addComponent((Component) this.不参与叠加道具增加2).addComponent((Component) this.修改地图2)).addGap(18, 18, 18).addComponent((Component) this.宠物吸取金币初始).addGap(31, 31, 31)));
        final GroupLayout jPanel39Layout = new GroupLayout((Container) this.jPanel39);
        this.jPanel39.setLayout((LayoutManager) jPanel39Layout);
        jPanel39Layout.setHorizontalGroup((Group) jPanel39Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel39Layout.createSequentialGroup().addGap(6, 6, 6).addComponent((Component) this.jPanel56, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jPanel89, -2, 350, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jPanel88, -2, 350, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.jPanel57, -2, -1, -2).addContainerGap(29, 32767)));
        jPanel39Layout.setVerticalGroup((Group) jPanel39Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel39Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel39Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel39Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel88, -1, -1, 32767).addComponent((Component) this.jPanel89, -1, -1, 32767).addComponent((Component) this.jPanel56, -1, -1, 32767)).addComponent((Component) this.jPanel57, -2, -1, -2)).addContainerGap(80, 32767)));
        this.宠吸功能面板.addTab("宠吸功能", (Component) this.jPanel39);
        this.jPanel91.setBorder((Border) BorderFactory.createTitledBorder("套装散件比例加成"));
        LZJMS.套装伤害加成表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"道具代码", "道具名字", "加成比例%", "套装编码", "套装名字"}) {
            boolean[] canEdit = {false, false, false, false, false};

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane19.setViewportView((Component) LZJMS.套装伤害加成表);
        if (LZJMS.套装伤害加成表.getColumnModel().getColumnCount() > 0) {
            LZJMS.套装伤害加成表.getColumnModel().getColumn(0).setPreferredWidth(20);
            LZJMS.套装伤害加成表.getColumnModel().getColumn(2).setPreferredWidth(20);
        }
        final GroupLayout jPanel91Layout = new GroupLayout((Container) this.jPanel91);
        this.jPanel91.setLayout((LayoutManager) jPanel91Layout);
        jPanel91Layout.setHorizontalGroup((Group) jPanel91Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel91Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane19, -1, 598, 32767).addGap(12, 12, 12)));
        jPanel91Layout.setVerticalGroup((Group) jPanel91Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel91Layout.createSequentialGroup().addComponent((Component) this.jScrollPane19).addContainerGap()));
        this.jPanel92.setBorder((Border) BorderFactory.createTitledBorder("显示操作"));
        this.jLabel192.setText("道具代码:");
        LZJMS.原套装代码.setEnabled(false);
        this.jLabel193.setText("道具代码:");
        this.jLabel194.setText("加成比例:");
        this.套装道具增加.setText("增加");
        this.套装道具增加.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.套装道具增加ActionPerformed(evt);
            }
        });
        this.套装道具删除.setText("删除");
        this.套装道具删除.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.套装道具删除ActionPerformed(evt);
            }
        });
        this.套装道具修改.setText("修改");
        this.套装道具修改.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.套装道具修改ActionPerformed(evt);
            }
        });
        this.jLabel195.setText("道具名字:");
        LZJMS.套装道具代码.setEnabled(false);
        this.装备加成伤害列表初始化.setText("初始化表单信息");
        this.装备加成伤害列表初始化.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.装备加成伤害列表初始化ActionPerformed(evt);
            }
        });
        this.jLabel196.setText("套装属性加成开关:");
        LZJMS.套装属性加成开关.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/OFF2.png")));
        LZJMS.套装属性加成开关.setBorderPainted(false);
        LZJMS.套装属性加成开关.setContentAreaFilled(false);
        LZJMS.套装属性加成开关.setFocusPainted(false);
        LZJMS.套装属性加成开关.setSelectedIcon((Icon) new ImageIcon(this.getClass().getResource("/image/ON2.png")));
        LZJMS.套装属性加成开关.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent evt) {
                LZJMS.this.套装属性加成开关StateChanged(evt);
            }
        });
        LZJMS.套装属性加成开关.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.套装属性加成开关ActionPerformed(evt);
            }
        });
        this.jPanel93.setBorder((Border) BorderFactory.createTitledBorder("指定套装查询"));
        this.jLabel197.setText("输入套装编码:");
        this.套装查询.setText("查询");
        this.套装查询.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.套装查询ActionPerformed(evt);
            }
        });
        this.jLabel204.setText("输入套装名字:");
        this.套装查询1.setText("查询");
        this.套装查询1.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.套装查询1ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel93Layout = new GroupLayout((Container) this.jPanel93);
        this.jPanel93.setLayout((LayoutManager) jPanel93Layout);
        jPanel93Layout.setHorizontalGroup((Group) jPanel93Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel93Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel93Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel93Layout.createSequentialGroup().addComponent((Component) this.jLabel197).addContainerGap(-1, 32767)).addGroup((Group) jPanel93Layout.createSequentialGroup().addComponent((Component) this.套装排序输入, -2, 100, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.套装查询)).addGroup((Group) jPanel93Layout.createSequentialGroup().addComponent((Component) this.jLabel204).addGap(0, 0, 32767)).addGroup((Group) jPanel93Layout.createSequentialGroup().addComponent((Component) this.套装名字输入, -2, 100, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.套装查询1)))));
        jPanel93Layout.setVerticalGroup((Group) jPanel93Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel93Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jLabel197).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel93Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.套装排序输入, -2, -1, -2).addComponent((Component) this.套装查询)).addGap(10, 10, 10).addComponent((Component) this.jLabel204).addPreferredGap(ComponentPlacement.RELATED, 7, 32767).addGroup((Group) jPanel93Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.套装名字输入, -2, -1, -2).addComponent((Component) this.套装查询1)).addContainerGap()));
        this.jLabel198.setText("套装编码:");
        this.jLabel203.setText("套装名字:");
        this.jLabel205.setText("套装个数:");
        LZJMS.套装个数.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.套装个数FocusLost(evt);
            }
        });
        this.重载套装加成.setText("重载套装加成");
        this.重载套装加成.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载套装加成ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel92Layout = new GroupLayout((Container) this.jPanel92);
        this.jPanel92.setLayout((LayoutManager) jPanel92Layout);
        jPanel92Layout.setHorizontalGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group) jPanel92Layout.createSequentialGroup().addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING, (Group) jPanel92Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel93, -1, -1, 32767)).addGroup(Alignment.LEADING, (Group) jPanel92Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.套装道具增加, -2, 79, -2).addGap(136, 136, 136).addComponent((Component) this.套装道具删除, -2, 79, -2).addGap(137, 137, 137).addComponent((Component) this.套装道具修改, -2, 79, -2)).addGroup((Group) jPanel92Layout.createSequentialGroup().addGap(10, 10, 10).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel195).addComponent((Component) this.jLabel194).addComponent((Component) this.jLabel193).addComponent((Component) this.jLabel192)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) LZJMS.原套装代码, -1, 110, 32767).addComponent((Component) LZJMS.现套装代码).addComponent((Component) LZJMS.套装加成比例).addComponent((Component) LZJMS.套装道具代码)))).addGap(10, 10, 10)).addGroup((Group) jPanel92Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel92Layout.createSequentialGroup().addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel198).addComponent((Component) this.jLabel203)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) LZJMS.套装名字, -1, 110, 32767).addComponent((Component) LZJMS.套装编码))).addGroup((Group) jPanel92Layout.createSequentialGroup().addComponent((Component) this.jLabel205).addGap(346, 346, 346).addComponent((Component) LZJMS.套装个数)).addGroup((Group) jPanel92Layout.createSequentialGroup().addComponent((Component) this.jLabel196).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) LZJMS.套装属性加成开关, -2, 74, -2)).addGroup((Group) jPanel92Layout.createSequentialGroup().addComponent((Component) this.装备加成伤害列表初始化, -2, 160, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.重载套装加成, -2, 160, -2))).addContainerGap()));
        jPanel92Layout.setVerticalGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel92Layout.createSequentialGroup().addGap(18, 18, 18).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.原套装代码, -2, -1, -2).addComponent((Component) this.jLabel192)).addGap(18, 18, 18).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel193).addComponent((Component) LZJMS.现套装代码, -2, -1, -2)).addGap(18, 18, 18).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel194).addComponent((Component) LZJMS.套装加成比例, -2, -1, -2)).addGap(18, 18, 18).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel195).addComponent((Component) LZJMS.套装道具代码, -2, -1, -2)).addGap(18, 18, 18).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) LZJMS.套装编码, -2, -1, -2).addComponent((Component) this.jLabel198)).addGap(18, 18, 18).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel203).addComponent((Component) LZJMS.套装名字, -2, -1, -2)).addGap(18, 18, 18).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.套装道具增加).addComponent((Component) this.套装道具删除).addComponent((Component) this.套装道具修改)).addGap(18, 18, 18).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.装备加成伤害列表初始化).addComponent((Component) this.重载套装加成)).addGap(3, 3, 3).addComponent((Component) this.jPanel93, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel92Layout.createSequentialGroup().addGap(7, 7, 7).addComponent((Component) this.jLabel196)).addComponent((Component) LZJMS.套装属性加成开关, -2, 30, -2)).addGap(18, 18, 18).addGroup((Group) jPanel92Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel205).addComponent((Component) LZJMS.套装个数, -2, -1, -2)).addContainerGap(31, 32767)));
        final GroupLayout jPanel90Layout = new GroupLayout((Container) this.jPanel90);
        this.jPanel90.setLayout((LayoutManager) jPanel90Layout);
        jPanel90Layout.setHorizontalGroup((Group) jPanel90Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel90Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel91, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) this.jPanel92, -2, -1, -2).addContainerGap(296, 32767)));
        jPanel90Layout.setVerticalGroup((Group) jPanel90Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel90Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel90Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel91, -1, -1, 32767).addComponent((Component) this.jPanel92, -1, -1, 32767)).addContainerGap(88, 32767)));
        this.宠吸功能面板.addTab("套装属性", (Component) this.jPanel90);
        this.jPanel59.setBorder((Border) BorderFactory.createTitledBorder("重载功能"));
        this.重载副本.setText("重载副本");
        this.重载副本.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载副本ActionPerformed(evt);
            }
        });
        this.重载爆率.setText("重载爆率");
        this.重载爆率.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载爆率ActionPerformed(evt);
            }
        });
        this.重载反应堆.setText("重载反应堆");
        this.重载反应堆.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载反应堆ActionPerformed(evt);
            }
        });
        this.重载传送门.setText("重载传送门");
        this.重载传送门.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载传送门ActionPerformed(evt);
            }
        });
        this.重载商店.setText("重载商店");
        this.重载商店.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载商店ActionPerformed(evt);
            }
        });
        this.重载商城.setText("重载商城");
        this.重载商城.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载商城ActionPerformed(evt);
            }
        });
        this.重载活动事件.setText("重载活动事件");
        this.重载活动事件.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载活动事件ActionPerformed(evt);
            }
        });
        this.重载BOSS事件.setText("重载BOSS事件");
        this.重载BOSS事件.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载BOSS事件ActionPerformed(evt);
            }
        });
        this.重载自定义事件.setText("重载自定义事件");
        this.重载自定义事件.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载自定义事件ActionPerformed(evt);
            }
        });
        this.重载套装加成1.setText("重载套装加成");
        this.重载套装加成1.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载套装加成1ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel59Layout = new GroupLayout((Container) this.jPanel59);
        this.jPanel59.setLayout((LayoutManager) jPanel59Layout);
        jPanel59Layout.setHorizontalGroup((Group) jPanel59Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel59Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel59Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.重载副本, -2, 117, -2).addComponent((Component) this.重载爆率, -2, 117, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel59Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.重载自定义事件, -2, 117, -2).addComponent((Component) this.重载商店, -2, 117, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel59Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.重载商城, Alignment.TRAILING, -1, -1, 32767).addComponent((Component) this.重载BOSS事件, Alignment.TRAILING, -1, 117, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel59Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.重载活动事件, -2, 117, -2).addComponent((Component) this.重载传送门, -2, 117, -2)).addGap(6, 6, 6).addGroup((Group) jPanel59Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.重载反应堆, -1, 117, 32767).addComponent((Component) this.重载套装加成1, -1, -1, 32767)).addContainerGap(-1, 32767)));
        jPanel59Layout.setVerticalGroup((Group) jPanel59Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel59Layout.createSequentialGroup().addGap(20, 20, 20).addGroup((Group) jPanel59Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.重载爆率).addComponent((Component) this.重载商店).addComponent((Component) this.重载商城).addComponent((Component) this.重载传送门).addComponent((Component) this.重载反应堆)).addGap(27, 27, 27).addGroup((Group) jPanel59Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.重载副本).addComponent((Component) this.重载自定义事件).addComponent((Component) this.重载BOSS事件).addComponent((Component) this.重载活动事件).addComponent((Component) this.重载套装加成1)).addContainerGap(25, 32767)));
        this.jPanel61.setBorder((Border) BorderFactory.createTitledBorder("保存功能"));
        this.保存玩家.setText("保存玩家");
        this.保存玩家.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.保存玩家ActionPerformed(evt);
            }
        });
        this.保存雇佣并关闭.setText("保存雇佣并关闭");
        this.保存雇佣并关闭.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.保存雇佣并关闭ActionPerformed(evt);
            }
        });
        this.一键清空数据库.setText("一键清空数据库");
        this.一键清空数据库.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.一键清空数据库ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel61Layout = new GroupLayout((Container) this.jPanel61);
        this.jPanel61.setLayout((LayoutManager) jPanel61Layout);
        jPanel61Layout.setHorizontalGroup((Group) jPanel61Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel61Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel61Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.保存玩家, -1, -1, 32767).addComponent((Component) this.保存雇佣并关闭, -1, -1, 32767).addComponent((Component) this.一键清空数据库, -1, -1, 32767)).addContainerGap(-1, 32767)));
        jPanel61Layout.setVerticalGroup((Group) jPanel61Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel61Layout.createSequentialGroup().addGap(20, 20, 20).addComponent((Component) this.保存玩家).addGap(18, 18, 18).addComponent((Component) this.保存雇佣并关闭).addGap(16, 16, 16).addComponent((Component) this.一键清空数据库).addContainerGap(16, 32767)));
        final GroupLayout jPanel58Layout = new GroupLayout((Container) this.jPanel58);
        this.jPanel58.setLayout((LayoutManager) jPanel58Layout);
        jPanel58Layout.setHorizontalGroup((Group) jPanel58Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel58Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel58Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel59, -2, 641, 32767).addComponent((Component) this.jPanel61, -1, -1, 32767)).addContainerGap(839, 32767)));
        jPanel58Layout.setVerticalGroup((Group) jPanel58Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel58Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel59, -2, -1, -2).addGap(37, 37, 37).addComponent((Component) this.jPanel61, -2, -1, -2).addContainerGap(381, 32767)));
        this.宠吸功能面板.addTab("重载保存功能", (Component) this.jPanel58);
        final GroupLayout jPanel2Layout = new GroupLayout((Container) this.jPanel2);
        this.jPanel2.setLayout((LayoutManager) jPanel2Layout);
        jPanel2Layout.setHorizontalGroup((Group) jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel2Layout.createSequentialGroup().addComponent((Component) this.宠吸功能面板, -2, 1495, 32767).addContainerGap()));
        jPanel2Layout.setVerticalGroup((Group) jPanel2Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel2Layout.createSequentialGroup().addComponent((Component) this.宠吸功能面板, -2, 723, -2).addGap(0, 19, 32767)));
        LZJMS.主界面菜单.addTab("游戏设置", (Icon) new ImageIcon(this.getClass().getResource("/image/设置.png")), (Component) this.jPanel2);
        this.jPanel38.setBorder((Border) BorderFactory.createTitledBorder("经验金币爆率活动"));
        this.jPanel40.setBorder((Border) BorderFactory.createTitledBorder(""));
        this.jLabel87.setText("倍率设置");
        this.倍率设置.setText("输入数字");
        this.倍率设置.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.倍率设置ActionPerformed(evt);
            }
        });
        this.持续时间时.setText("输入数字");
        this.持续时间时.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.持续时间时ActionPerformed(evt);
            }
        });
        this.jLabel95.setText("持续时间:/时");
        this.jLabel144.setText("持续时间:/分");
        this.持续时间分.setText("输入数字");
        this.jLabel98.setText("启动按钮");
        this.开启倍率活动.setText("开启倍率活动");
        this.开启倍率活动.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.开启倍率活动ActionPerformed(evt);
            }
        });
        this.jLabel157.setText("选择类型");
        this.倍率活动类型.setModel(new DefaultComboBoxModel(new String[]{"经验倍率", "道具爆率", "金币爆率", ""}));
        final GroupLayout jPanel40Layout = new GroupLayout((Container) this.jPanel40);
        this.jPanel40.setLayout((LayoutManager) jPanel40Layout);
        jPanel40Layout.setHorizontalGroup((Group) jPanel40Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel40Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel40Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel87, -1, -1, 32767).addComponent((Component) this.倍率设置, -1, 75, 32767)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel40Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel95, -1, -1, 32767).addComponent((Component) this.持续时间时)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel40Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.持续时间分).addComponent((Component) this.jLabel144, -1, 75, 32767)).addGap(10, 10, 10).addGroup((Group) jPanel40Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.倍率活动类型, 0, -1, 32767).addComponent((Component) this.jLabel157, -1, -1, 32767)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel40Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel40Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent((Component) this.开启倍率活动)).addComponent((Component) this.jLabel98, -1, -1, 32767)).addContainerGap()));
        jPanel40Layout.setVerticalGroup((Group) jPanel40Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel40Layout.createSequentialGroup().addGap(24, 24, 24).addGroup((Group) jPanel40Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel87).addComponent((Component) this.jLabel95).addComponent((Component) this.jLabel144).addComponent((Component) this.jLabel98).addComponent((Component) this.jLabel157)).addGap(18, 18, 18).addGroup((Group) jPanel40Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.倍率设置, -2, -1, -2).addComponent((Component) this.持续时间时, -2, -1, -2).addComponent((Component) this.持续时间分, -2, -1, -2).addComponent((Component) this.开启倍率活动).addComponent((Component) this.倍率活动类型, -2, -1, -2)).addContainerGap(24, 32767)));
        final GroupLayout jPanel38Layout = new GroupLayout((Container) this.jPanel38);
        this.jPanel38.setLayout((LayoutManager) jPanel38Layout);
        jPanel38Layout.setHorizontalGroup((Group) jPanel38Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel38Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel40, -2, 461, -2).addContainerGap(-1, 32767)));
        jPanel38Layout.setVerticalGroup((Group) jPanel38Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel38Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel40, -2, -1, -2).addContainerGap(-1, 32767)));
        this.jPanel41.setBorder((Border) BorderFactory.createTitledBorder("发放道具"));
        this.jPanel42.setBorder((Border) BorderFactory.createTitledBorder(""));
        this.jLabel149.setText("道具代码");
        this.发放道具代码.setText("输入道具代码");
        this.发放道具代码.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.发放道具代码ActionPerformed(evt);
            }
        });
        this.jLabel150.setText("输入数量");
        this.发放道具数量.setText("输入数字");
        this.jLabel151.setText("发放范围");
        this.发放道具发放范围.setModel(new DefaultComboBoxModel(new String[]{"发放全服", "发放个人"}));
        this.jLabel152.setText("玩家名字");
        this.发放个人玩家名字.setText("输入玩家名字");
        this.发放个人玩家名字.setEnabled(false);
        this.jLabel153.setText("点击发放");
        this.发放道具.setText("发放道具");
        this.发放道具.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.发放道具ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel42Layout = new GroupLayout((Container) this.jPanel42);
        this.jPanel42.setLayout((LayoutManager) jPanel42Layout);
        jPanel42Layout.setHorizontalGroup((Group) jPanel42Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel42Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel149, -2, 75, -2).addComponent((Component) this.发放道具代码, -2, -1, -2)).addGroup((Group) jPanel42Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group) jPanel42Layout.createSequentialGroup().addGap(7, 7, 7).addComponent((Component) this.jLabel150, -2, 71, -2)).addGroup((Group) jPanel42Layout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) this.发放道具数量))).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.发放道具发放范围, -2, -1, -2).addComponent((Component) this.jLabel151)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel42Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.发放个人玩家名字, -2, -1, -2).addComponent((Component) this.jLabel152)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel42Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.发放道具, -1, -1, 32767).addComponent((Component) this.jLabel153, -1, -1, 32767)).addContainerGap(-1, 32767)));
        jPanel42Layout.setVerticalGroup((Group) jPanel42Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel42Layout.createSequentialGroup().addGap(24, 24, 24).addGroup((Group) jPanel42Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel149).addComponent((Component) this.jLabel150).addComponent((Component) this.jLabel151).addComponent((Component) this.jLabel152).addComponent((Component) this.jLabel153)).addGap(18, 18, 18).addGroup((Group) jPanel42Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.发放道具代码, -2, -1, -2).addComponent((Component) this.发放道具数量, -2, -1, -2).addComponent((Component) this.发放道具发放范围, -2, -1, -2).addComponent((Component) this.发放个人玩家名字, -2, -1, -2).addComponent((Component) this.发放道具)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel41Layout = new GroupLayout((Container) this.jPanel41);
        this.jPanel41.setLayout((LayoutManager) jPanel41Layout);
        jPanel41Layout.setHorizontalGroup((Group) jPanel41Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel41Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel42, -2, -1, -2).addContainerGap(-1, 32767)));
        jPanel41Layout.setVerticalGroup((Group) jPanel41Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel41Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel42, -1, -1, 32767).addContainerGap()));
        this.jPanel43.setBorder((Border) BorderFactory.createTitledBorder("发放点券抵用金币"));
        this.jPanel45.setBorder((Border) BorderFactory.createTitledBorder(""));
        this.jLabel154.setText("发放数量");
        this.发放其他数量.setText("输入数字");
        this.jLabel155.setText("发放范围");
        this.发放其他范围.setModel(new DefaultComboBoxModel(new String[]{"发放全服", "发放个人"}));
        this.jLabel156.setText("玩家名字");
        this.jLabel158.setText("选择类型");
        this.发放其他类型.setModel(new DefaultComboBoxModel(new String[]{"发放点券", "发放抵用", "发放金币"}));
        this.发放其他玩家.setText("输入玩家名字");
        this.发放其他玩家.setEnabled(false);
        this.jLabel159.setText("点击发放");
        this.发放其他内容.setText("发放内容");
        this.发放其他内容.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.发放其他内容ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel45Layout = new GroupLayout((Container) this.jPanel45);
        this.jPanel45.setLayout((LayoutManager) jPanel45Layout);
        jPanel45Layout.setHorizontalGroup((Group) jPanel45Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel45Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel45Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.发放其他数量, -1, 74, 32767).addComponent((Component) this.jLabel154, -1, -1, 32767)).addGap(10, 10, 10).addGroup((Group) jPanel45Layout.createParallelGroup(Alignment.TRAILING, false).addComponent((Component) this.jLabel158, -1, -1, 32767).addComponent((Component) this.发放其他类型, 0, -1, 32767)).addGap(10, 10, 10).addGroup((Group) jPanel45Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.发放其他范围, Alignment.TRAILING, -2, -1, -2).addComponent((Component) this.jLabel155, Alignment.TRAILING, -2, 74, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel45Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.发放其他玩家).addComponent((Component) this.jLabel156, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel45Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.发放其他内容, -1, -1, 32767).addComponent((Component) this.jLabel159, -1, -1, 32767)).addContainerGap(30, 32767)));
        jPanel45Layout.setVerticalGroup((Group) jPanel45Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel45Layout.createSequentialGroup().addGap(24, 24, 24).addGroup((Group) jPanel45Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.jLabel154).addComponent((Component) this.jLabel155).addComponent((Component) this.jLabel156).addComponent((Component) this.jLabel158).addComponent((Component) this.jLabel159)).addGap(18, 18, 18).addGroup((Group) jPanel45Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.发放其他数量, -2, -1, -2).addComponent((Component) this.发放其他范围, -2, -1, -2).addComponent((Component) this.发放其他类型, -2, -1, -2).addComponent((Component) this.发放其他玩家, -2, -1, -2).addComponent((Component) this.发放其他内容)).addContainerGap(24, 32767)));
        final GroupLayout jPanel43Layout = new GroupLayout((Container) this.jPanel43);
        this.jPanel43.setLayout((LayoutManager) jPanel43Layout);
        jPanel43Layout.setHorizontalGroup((Group) jPanel43Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel43Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel45, -1, -1, 32767).addContainerGap()));
        jPanel43Layout.setVerticalGroup((Group) jPanel43Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel43Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel45, -2, -1, -2).addContainerGap(-1, 32767)));
        final GroupLayout jPanel5Layout = new GroupLayout((Container) this.jPanel5);
        this.jPanel5.setLayout((LayoutManager) jPanel5Layout);
        jPanel5Layout.setHorizontalGroup((Group) jPanel5Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel5Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel38, -2, -1, -2).addGap(10, 10, 10).addComponent((Component) this.jPanel41, -2, -1, -2).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) this.jPanel43, -2, -1, -2).addContainerGap(-1, 32767)));
        jPanel5Layout.setVerticalGroup((Group) jPanel5Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel5Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel5Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel43, -2, -1, -2).addGroup((Group) jPanel5Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel41, -1, -1, 32767).addComponent((Component) this.jPanel38, -1, -1, 32767))).addContainerGap(579, 32767)));
        LZJMS.主界面菜单.addTab("专用功能", (Icon) new ImageIcon(this.getClass().getResource("/image/问题.png")), (Component) this.jPanel5);
        this.jPanel78.setBorder((Border) BorderFactory.createTitledBorder("充值赞助"));
        this.充值赞助列表.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"角色ID", "玩家名字", "每日充值", "累计充值", "剩余积分", "可用点券"}) {
            boolean[] canEdit = {false, false, false, false, false, false};

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane14.setViewportView((Component) this.充值赞助列表);
        this.jPanel86.setBorder((Border) BorderFactory.createTitledBorder("功能操作"));
        this.jButton1.setText("初始化充值赞助列表");
        this.jButton1.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.jButton1ActionPerformed(evt);
            }
        });
        this.jPanel87.setBorder((Border) BorderFactory.createTitledBorder("玩家信息"));
        this.jLabel175.setText("玩家名字:");
        this.jLabel176.setText("角色ID:");
        this.jLabel177.setText("每日充值:");
        this.jLabel178.setText("累计充值:");
        this.jLabel179.setText("剩余积分:");
        this.充值角色ID.setText("           ");
        this.充值角色ID.setEnabled(false);
        this.充值角色ID.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.充值角色IDActionPerformed(evt);
            }
        });
        this.充值玩家名字.setText("           ");
        this.充值玩家名字.setEnabled(false);
        this.现有每日充值.setText("           ");
        this.现有每日充值.setEnabled(false);
        this.现有累计充值.setText("           ");
        this.现有累计充值.setEnabled(false);
        this.现有剩余积分.setText("           ");
        this.现有剩余积分.setEnabled(false);
        this.jLabel183.setText("可用点券:");
        this.现有可用点券.setText("           ");
        this.现有可用点券.setEnabled(false);
        this.现有可用点券.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.现有可用点券ActionPerformed(evt);
            }
        });
        this.jLabel166.setText("每日充值");
        this.jLabel168.setText("累计充值");
        this.jLabel170.setText("剩余积分");
        this.jLabel171.setText("可用点券");
        this.jLabel105.setText("点券比例");
        LZJMS.点券比例.addFocusListener((FocusListener) new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                LZJMS.this.点券比例FocusLost(evt);
            }
        });
        LZJMS.点券比例.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.点券比例ActionPerformed(evt);
            }
        });
        this.jLabel115.setText("发放金额:");
        this.发放充值数量.setText("输入数字");
        this.发放充值数量.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.发放充值数量ActionPerformed(evt);
            }
        });
        this.发放氪金充值.setText("发放内容");
        this.发放氪金充值.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.发放氪金充值ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel87Layout = new GroupLayout((Container) this.jPanel87);
        this.jPanel87.setLayout((LayoutManager) jPanel87Layout);
        jPanel87Layout.setHorizontalGroup((Group) jPanel87Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel87Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel87Layout.createSequentialGroup().addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jLabel175, -1, 60, 32767).addComponent((Component) this.jLabel176, -1, -1, 32767)).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.充值角色ID, Alignment.TRAILING, -2, -1, -2).addComponent((Component) this.充值玩家名字, Alignment.TRAILING, -2, -1, -2))).addGroup((Group) jPanel87Layout.createSequentialGroup().addComponent((Component) this.jLabel179).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.现有剩余积分, -2, -1, -2)).addGroup((Group) jPanel87Layout.createSequentialGroup().addComponent((Component) this.jLabel177).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.现有每日充值, -2, -1, -2)).addGroup((Group) jPanel87Layout.createSequentialGroup().addComponent((Component) this.jLabel178).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.现有累计充值, -2, -1, -2)).addGroup(Alignment.TRAILING, (Group) jPanel87Layout.createSequentialGroup().addGap(0, 0, 32767).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel87Layout.createSequentialGroup().addComponent((Component) this.每日充值, -2, -1, -2).addGap(0, 0, 0).addComponent((Component) this.jLabel166)).addComponent((Component) this.jLabel115)).addGap(18, 18, 18).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.LEADING, false).addGroup((Group) jPanel87Layout.createSequentialGroup().addComponent((Component) this.发放充值数量, -2, 118, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.发放氪金充值, -2, 178, -2)).addGroup((Group) jPanel87Layout.createSequentialGroup().addComponent((Component) this.累计充值, -2, -1, -2).addGap(0, 0, 0).addComponent((Component) this.jLabel168).addGap(18, 18, 18).addComponent((Component) this.剩余余额, -2, -1, -2).addGap(0, 0, 0).addComponent((Component) this.jLabel170).addGap(18, 18, 18).addComponent((Component) this.可用点券, -2, -1, -2).addGap(0, 0, 0).addComponent((Component) this.jLabel171).addGap(18, 18, 18).addComponent((Component) this.jLabel105, -2, 62, -2)))).addGroup(Alignment.TRAILING, (Group) jPanel87Layout.createSequentialGroup().addComponent((Component) this.jLabel183).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.现有可用点券).addComponent((Component) LZJMS.点券比例)))).addContainerGap()));
        jPanel87Layout.setVerticalGroup((Group) jPanel87Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel87Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel176).addComponent((Component) this.充值角色ID, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel175).addComponent((Component) this.充值玩家名字, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel177).addComponent((Component) this.现有每日充值, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel178).addComponent((Component) this.现有累计充值, -2, -1, -2)).addPreferredGap(ComponentPlacement.RELATED).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel179).addComponent((Component) this.现有剩余积分, -2, -1, -2)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.现有可用点券, -2, -1, -2).addComponent((Component) this.jLabel183)).addGap(19, 19, 19).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel171).addComponent((Component) this.可用点券, -2, -1, -2).addComponent((Component) this.jLabel105)).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel170).addComponent((Component) this.剩余余额, -2, -1, -2)).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel166).addComponent((Component) this.每日充值, -2, -1, -2)).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.jLabel168).addComponent((Component) this.累计充值, -2, -1, -2))).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.点券比例, -2, -1, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addGroup((Group) jPanel87Layout.createParallelGroup(Alignment.CENTER).addComponent((Component) this.发放氪金充值).addComponent((Component) this.发放充值数量, -2, -1, -2).addComponent((Component) this.jLabel115)).addContainerGap()));
        final GroupLayout jPanel86Layout = new GroupLayout((Container) this.jPanel86);
        this.jPanel86.setLayout((LayoutManager) jPanel86Layout);
        jPanel86Layout.setHorizontalGroup((Group) jPanel86Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, (Group) jPanel86Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel86Layout.createParallelGroup(Alignment.TRAILING).addComponent((Component) this.jPanel87, -1, -1, 32767).addComponent((Component) this.jButton1, -1, -1, 32767)).addContainerGap()));
        jPanel86Layout.setVerticalGroup((Group) jPanel86Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel86Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jButton1).addPreferredGap(ComponentPlacement.UNRELATED).addComponent((Component) this.jPanel87, -1, -1, 32767).addContainerGap()));
        this.jPanel96.setBorder((Border) BorderFactory.createTitledBorder("搜索功能"));
        this.jLabel213.setText("通过ID查询");
        this.氪金ID输入.setText("输入数字");
        this.氪金ID输入.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.氪金ID输入ActionPerformed(evt);
            }
        });
        this.氪金ID查询.setText("查询");
        this.氪金ID查询.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.氪金ID查询ActionPerformed(evt);
            }
        });
        this.jLabel214.setText("通过名字查询");
        this.氪金名字输入.setText("输入名字");
        this.氪金名字输入.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.氪金名字输入ActionPerformed(evt);
            }
        });
        this.氪金名字查询.setText("查询");
        this.氪金名字查询.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.氪金名字查询ActionPerformed(evt);
            }
        });
        this.氪金机器码查询.setText("查询");
        this.氪金机器码查询.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.氪金机器码查询ActionPerformed(evt);
            }
        });
        this.氪金机器码输入.setText("输入机器码");
        this.氪金机器码输入.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.氪金机器码输入ActionPerformed(evt);
            }
        });
        this.jLabel215.setText("通过机器码查询");
        final GroupLayout jPanel96Layout = new GroupLayout((Container) this.jPanel96);
        this.jPanel96.setLayout((LayoutManager) jPanel96Layout);
        jPanel96Layout.setHorizontalGroup((Group) jPanel96Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel96Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel96Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel96Layout.createSequentialGroup().addGroup((Group) jPanel96Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jLabel213).addComponent((Component) this.jLabel214).addComponent((Component) this.jLabel215)).addContainerGap(-1, 32767)).addGroup(Alignment.TRAILING, (Group) jPanel96Layout.createSequentialGroup().addGroup((Group) jPanel96Layout.createParallelGroup(Alignment.TRAILING).addGroup((Group) jPanel96Layout.createSequentialGroup().addComponent((Component) this.氪金机器码输入, -2, 118, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.氪金机器码查询, -2, 178, -2)).addGroup(Alignment.LEADING, (Group) jPanel96Layout.createSequentialGroup().addComponent((Component) this.氪金ID输入, -2, 118, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.氪金ID查询, -2, 178, -2)).addGroup(Alignment.LEADING, (Group) jPanel96Layout.createSequentialGroup().addComponent((Component) this.氪金名字输入, -2, 118, -2).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent((Component) this.氪金名字查询, -2, 178, -2))).addGap(28, 28, 28)))));
        jPanel96Layout.setVerticalGroup((Group) jPanel96Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel96Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jLabel213).addGap(10, 10, 10).addGroup((Group) jPanel96Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.氪金ID输入, -2, -1, -2).addComponent((Component) this.氪金ID查询)).addGap(10, 10, 10).addComponent((Component) this.jLabel214).addGap(10, 10, 10).addGroup((Group) jPanel96Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.氪金名字输入, -2, -1, -2).addComponent((Component) this.氪金名字查询)).addGap(10, 10, 10).addComponent((Component) this.jLabel215).addGap(10, 10, 10).addGroup((Group) jPanel96Layout.createParallelGroup(Alignment.BASELINE).addComponent((Component) this.氪金机器码输入, -2, -1, -2).addComponent((Component) this.氪金机器码查询)).addContainerGap(-1, 32767)));
        this.jPanel97.setBorder((Border) BorderFactory.createTitledBorder("同账号下其他角色信息"));
        this.充值赞助列表1.setModel((TableModel) new DefaultTableModel(new Object[0][], new String[]{"角色ID", "玩家名字", "每日充值", "累计充值", "剩余积分", "可用点券"}) {
            boolean[] canEdit = {false, false, false, false, false, false};

            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        this.jScrollPane21.setViewportView((Component) this.充值赞助列表1);
        final GroupLayout jPanel97Layout = new GroupLayout((Container) this.jPanel97);
        this.jPanel97.setLayout((LayoutManager) jPanel97Layout);
        jPanel97Layout.setHorizontalGroup((Group) jPanel97Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel97Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane21, -1, 799, 32767).addContainerGap()));
        jPanel97Layout.setVerticalGroup((Group) jPanel97Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane21, -2, 216, -2));
        final GroupLayout jPanel78Layout = new GroupLayout((Container) this.jPanel78);
        this.jPanel78.setLayout((LayoutManager) jPanel78Layout);
        jPanel78Layout.setHorizontalGroup((Group) jPanel78Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel78Layout.createSequentialGroup().addGroup((Group) jPanel78Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel97, -1, -1, 32767).addComponent((Component) this.jScrollPane14)).addGap(18, 18, 18).addGroup((Group) jPanel78Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jPanel96, -1, -1, 32767).addComponent((Component) this.jPanel86, -1, -1, 32767)).addContainerGap()));
        jPanel78Layout.setVerticalGroup((Group) jPanel78Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel78Layout.createSequentialGroup().addGroup((Group) jPanel78Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jScrollPane14).addComponent((Component) this.jPanel86, -1, -1, 32767)).addPreferredGap(ComponentPlacement.UNRELATED).addGroup((Group) jPanel78Layout.createParallelGroup(Alignment.LEADING, false).addComponent((Component) this.jPanel97, -1, -1, 32767).addComponent((Component) this.jPanel96, -1, -1, 32767)).addContainerGap(-1, 32767)));
        final GroupLayout jPanel9Layout = new GroupLayout((Container) this.jPanel9);
        this.jPanel9.setLayout((LayoutManager) jPanel9Layout);
        jPanel9Layout.setHorizontalGroup((Group) jPanel9Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel9Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jPanel78, -2, -1, -2).addContainerGap(130, 32767)));
        jPanel9Layout.setVerticalGroup((Group) jPanel9Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel9Layout.createSequentialGroup().addComponent((Component) this.jPanel78, -2, -1, -2).addGap(0, 30, 32767)));
        LZJMS.主界面菜单.addTab("充值功能", (Icon) new ImageIcon(this.getClass().getResource("/image/管理自定义掉落.png")), (Component) this.jPanel9);
        this.jScrollPane20.setCursor(new Cursor(0));
        this.jTextArea1.setColumns(20);
        this.jTextArea1.setRows(5);
        this.jTextArea1.setText("更新内容:\n===============================================================================\n2021-07-12\n更新 全局怪物血量翻倍修改,修改之后立即生效\n\n===============================================================================\n2021-05-25\n由于前段时间长时间忘记写更新日志所以现在统一补齐\n修改部分封包内容\n调整控制台功能\n删减了部分检测功能 打算花时间重新制作\n添加宠吸功能位于控制台\n添加套装功能位于控制台\n删除了一大堆的配置表修改成控制台操作\n===============================================================================\n2020-01-08\n添加指令 解卡组队 \n===============================================================================\n2020-01-07\n修复bosslog 跟onetimelog记录的时候读取错误\n===============================================================================\n2020-12-30\n修复宠物购买的时候 实际时间跟显示时间不同的bug\n===============================================================================\n2020-12-27\n添加了宠吸功能个别地图无法使用的设定\n===============================================================================\n2020-12-22 \n修复了捉鬼任务击杀之后npc消失提示空值问题\n修复了宠吸功能在事件执行的时候还可以有效果（打boss东西给人吸走了！）\n添加了击杀boss的时候打开npc跟记录次数\n===============================================================================\n2020-12-14\n修复了pkb血条在扣除的时候最后一个阶段秒杀了pkb本身的问题\n修改了金锤子的使用效果，从更换地图改成本身移除在添加\n===============================================================================\n2020-11-30\n添加账号每日跟永久记录表单 及函数 搜getaccountidlog\n调整清理每日表单中添加 账号表单\n添加事件状态下全局爆率\n修复了账号每日记录表单添加次数的时候遇到的bug 读取错误\n添加函数 \ngivePartyAccountidBossLog(bossid)\ngivePartyAccountidLog(bossid)\ngivePartyAccountidBossLog(bossid,number)\ngivePartyAccountidLog(bossid,number)\n修正了报错提示中mode代指具体的位子而不是文件名的bug\n===============================================================================\n2020-11-02更新内容\n消耗代码：2049118\n消耗名称：防爆\n消耗代码：2049035\n消耗名称：还原\n消耗代码：2049117\n消耗名称：枫叶卷轴 枫叶装备全属性+2\n===============================================================================\n2020-10-14更新内容\n宠吸功能完善第三版本\n离线挂机完善第三版本\n===============================================================================\n2020-06-27更新内容\n添加了大量链接数据库没有完全结束的语句 本质上解决了连接池溢出问题\n消耗代码：2049124        这个要修改 为0~+5\n消耗名称：正向混沌卷轴\n消耗代码：2049116\n消耗名称：强化混沌卷轴   -10~10\n消耗代码：2049136\n消耗名称：惊人正义混沌卷轴 20%  +5- +10\n消耗代码：2049129\n消耗名称：正义混沌卷轴 50%  +0 - +10\n修改了部分游戏提示 \n使用技能buf 添加给玩家\nSkillFactory.getSkill(9001002).getEffect(1).applyTo(player);\n===============================================================================\n2020-06-20更新内容\n修复黑龙线程没有效果的问题\n添加自定义扎昆黑龙品克缤入场人数设定 配置表 mob//怪物挑战所需人数.ini\n添加11~20转 转生配置表 转生伤害加成等\n添加基础控制台 lzjms\n修复天空船什么乱七八糟的东西\n添加特殊功能 雇佣商人自动收摊时间\n===============================================================================\n2020-06-08更新内容\n添加捉鬼任务 事件脚本 配置表 捉鬼任务 捉鬼线程\n添加捉鬼线程\n添加自定义箱子\n添加脚本击杀boss之后自动打开npc\n添加gm指令 重载\n===============================================================================\n2020-06-06更新内容\n添加黑龙boss线程配置表 黑龙线程\n===============================================================================\n2020-05-31更新内容\n修复多倍地图怪物问题（由于修复了副本怪物问题导致的地图怪物特别多）\n添加宠物自动嗑粮食问题  配置表 特殊功能ini\n修复了挂机线程问题 \n添加了挂机功能中拒绝操作点击npc整理道具防止bug\n修复了整理背包之后东西消失问题 添加三秒延迟以及提示语句\n修复多倍地图怪物问题 boss也会多倍的问题\n添加拍卖行系统 以及对应函数（尚未添加拍卖行系统的配置表）\n修复武陵道场异常问题\n再一次调整了挂机线程问题\n===============================================================================\n2020-05-30更新内容\n修复挂机打怪系统 吸物系统\n添加自定义外置配置表挂机功能 配置表 挂机功能ini\n修复副本怪物不刷新问题\n===============================================================================\n2020-05-27更新内容\n添加挂机系统\n===============================================================================\n2020-05-26更新内容\n添加转生之后百分比伤害加成 配置表 转生伤害加成ini\n添加自定义泡点点数 配置表 泡点设定ini\n\n===============================================================================\n2020-05-25更新内容\n添加自定义伤害过高封号的数值 配置表 伤害过高设定ini\n喇叭函数cm.喇叭(数字1~4,内容)\n修复系统经验倍率异常问题 无法使用指令提升\n===============================================================================\n2020-05-23更新内容\n修复send封包中的个别封包\n添加自定义技能段数封号判定 配置表 技能封号段数ini\n添加自定义特殊道具不参与叠加 默认子弹飞镖不参与叠加 配置表 特殊配置ini\n修复转生之后不能额外加属性点的问题\n添加正向混沌卷轴 2049124 属性0~5\n添加惊人正义混沌卷轴 2049136 属性5~10\n添加正义混沌 2049129 属性0~10\n添加强化混沌 2049116 属性-10~10\n===============================================================================\n2020-05-22更新内容\n修复sendini中大量的不存在包跟错误包\n修复recvini中大量的不存在包跟错误包\n添加了组队邀请系统但是还有点问题\n===============================================================================\n2020-05-20更新内容\n添加自定义装备添加经验加成 配置表 装备提高经验倍数ini\n删除了源码自带的后门  再一次删除源码自带后门\n修复 !help 内容无法显示\n修复gm隐身术 第一次取消不显影必须要重进地图的问题\n添加自定义地图buf  配置表 地图属性ini\n===============================================================================\n2020-05-19更新内容\n修复兵书封包 修复锻造技能封包 修复时间异常封包\n添加多倍怪物 配置表 多倍怪物ini\n添加宠物自定义buf属性 配置表 宠物属性ini\n添加公告提示 击杀野外boss黄色循环喇叭\n添加转生经验背书 配置表 经验倍数ini\n添加转生之后属性变更 配置表转生属性ini\n添加转生函数 调用函数 cm.getPlayer().转生回归()\n添加扎昆线程 弥补了扎昆笨怪不放技能不造成伤害 配置表 mob\\\\扎昆线程ini\n添加了自定义最大装备叠加个数 配置表特殊功能ini\n添加了爆物上限数量 配置表特殊功能ini\n添加了等级连升开关 配置表特殊功能ini\n添加了破功开关 配置表特殊功能ini 需要配合破功dll否则无法生效\n设置了一级要害最大伤害跟暗杀最大伤害 199999\n修复了丢道具复制bug\n添加了上线喇叭提示 配置表特殊功能ini\n设置了gm等级1的gm指令 \n添加了一堆gm指令跟函数\n修复自定义buf中爆率异常的问题\n修复双倍爆率卡三小时没有效果的问题\n\n\n\n\n\n\n\n\n\n\n\n");
        this.jTextArea1.setEnabled(false);
        this.jScrollPane20.setViewportView((Component) this.jTextArea1);
        final GroupLayout jPanel60Layout = new GroupLayout((Container) this.jPanel60);
        this.jPanel60.setLayout((LayoutManager) jPanel60Layout);
        jPanel60Layout.setHorizontalGroup((Group) jPanel60Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel60Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jScrollPane20, -1, 1485, 32767).addContainerGap()));
        jPanel60Layout.setVerticalGroup((Group) jPanel60Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) this.jScrollPane20, -1, 742, 32767));
        LZJMS.主界面菜单.addTab("更新日志", (Icon) new ImageIcon(this.getClass().getResource("/image/GM工具.png")), (Component) this.jPanel60);
        this.jTextField1.setText("本软件均采集于互联网,仅供学习交流使用，请勿用于商业用途和非法用途，如作他用所造成的一切后果和法律责任一概与我们无关,如侵犯到权益,我们将删除处理.");
        final GroupLayout jPanel101Layout = new GroupLayout((Container) this.jPanel101);
        this.jPanel101.setLayout((LayoutManager) jPanel101Layout);
        jPanel101Layout.setHorizontalGroup((Group) jPanel101Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel101Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jTextField1, -2, 1153, -2).addContainerGap(342, 32767)));
        jPanel101Layout.setVerticalGroup((Group) jPanel101Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel101Layout.createSequentialGroup().addContainerGap().addComponent((Component) this.jTextField1, -2, -1, -2).addContainerGap(711, 32767)));
        LZJMS.主界面菜单.addTab("产品声明", (Component) this.jPanel101);
        this.getContentPane().add((Component) LZJMS.主界面菜单, new AbsoluteConstraints(0, 0, 1510, 780));
        LZJMS.清空日志.setBackground(new Color(255, 255, 255));
        LZJMS.清空日志.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/错误日志.png")));
        LZJMS.清空日志.setText("清空日志");
        LZJMS.清空日志.setToolTipText("");
        LZJMS.清空日志.setActionCommand("");
        LZJMS.清空日志.setEnabled(false);
        LZJMS.清空日志.setIconTextGap(3);
        LZJMS.清空日志.setPreferredSize(new Dimension(100, 30));
        LZJMS.清空日志.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.清空日志ActionPerformed(evt);
            }
        });
        this.getContentPane().add((Component) LZJMS.清空日志, new AbsoluteConstraints(690, 800, 120, 40));
        this.jLabel1.setText("【运行时长】");
        this.getContentPane().add((Component) this.jLabel1, new AbsoluteConstraints(460, 820, -1, -1));
        this.jLabel2.setText("【当前内存】");
        this.getContentPane().add((Component) this.jLabel2, new AbsoluteConstraints(20, 820, -1, -1));
        this.jLabel3.setText("【在线人数】");
        this.getContentPane().add((Component) this.jLabel3, new AbsoluteConstraints(240, 820, -1, -1));
        LZJMS.时长.setMaximum(21000);
        LZJMS.时长.setRequestFocusEnabled(false);
        LZJMS.时长.setString("0天 0时0分0秒");
        LZJMS.时长.setStringPainted(true);
        this.getContentPane().add((Component) LZJMS.时长, new AbsoluteConstraints(530, 820, -1, -1));
        LZJMS.内存.setValue(0);
        LZJMS.内存.setAutoscrolls(true);
        LZJMS.内存.setStringPainted(true);
        LZJMS.内存.setMinimum(0);
        LZJMS.内存.setMaximum(12288);
        LZJMS.内存.setString("0 MB");
        this.getContentPane().add((Component) LZJMS.内存, new AbsoluteConstraints(90, 820, -1, 20));
        LZJMS.人数.setMaximum(300);//服务器上限人数
        LZJMS.人数.setString("0/300");
        LZJMS.人数.setStringPainted(true);
        this.getContentPane().add((Component) LZJMS.人数, new AbsoluteConstraints(310, 820, -1, -1));
        LZJMS.保存数据.setBackground(new Color(255, 255, 255));
        LZJMS.保存数据.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/魔方.png")));
        LZJMS.保存数据.setText("保存数据");
        LZJMS.保存数据.setToolTipText("");
        LZJMS.保存数据.setActionCommand("");
        LZJMS.保存数据.setEnabled(false);
        LZJMS.保存数据.setIconTextGap(3);
        LZJMS.保存数据.setPreferredSize(new Dimension(100, 30));
        LZJMS.保存数据.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.保存数据ActionPerformed(evt);
            }
        });
        this.getContentPane().add((Component) LZJMS.保存数据, new AbsoluteConstraints(1050, 800, 120, 40));
        LZJMS.保存雇佣.setBackground(new Color(255, 255, 255));
        LZJMS.保存雇佣.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/角色管理.png")));
        LZJMS.保存雇佣.setText("重载配置");
        LZJMS.保存雇佣.setToolTipText("");
        LZJMS.保存雇佣.setActionCommand("");
        LZJMS.保存雇佣.setEnabled(false);
        LZJMS.保存雇佣.setIconTextGap(3);
        LZJMS.保存雇佣.setPreferredSize(new Dimension(100, 30));
        LZJMS.保存雇佣.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.保存雇佣ActionPerformed(evt);
            }
        });
        this.getContentPane().add((Component) LZJMS.保存雇佣, new AbsoluteConstraints(930, 800, 120, 40));
        LZJMS.重载全部.setBackground(new Color(255, 255, 255));
        LZJMS.重载全部.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/服务设置.png")));
        LZJMS.重载全部.setText("重载全部");
        LZJMS.重载全部.setToolTipText("");
        LZJMS.重载全部.setActionCommand("");
        LZJMS.重载全部.setEnabled(false);
        LZJMS.重载全部.setIconTextGap(3);
        LZJMS.重载全部.setPreferredSize(new Dimension(100, 30));
        LZJMS.重载全部.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.重载全部ActionPerformed(evt);
            }
        });
        this.getContentPane().add((Component) LZJMS.重载全部, new AbsoluteConstraints(810, 800, 120, 40));
        LZJMS.启动服务端.setBackground(new Color(255, 255, 255));
        LZJMS.启动服务端.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/冒险岛.png")));
        LZJMS.启动服务端.setText("启动服务端");
        LZJMS.启动服务端.setToolTipText("");
        LZJMS.启动服务端.setActionCommand("");
        LZJMS.启动服务端.setIconTextGap(3);
        LZJMS.启动服务端.setPreferredSize(new Dimension(100, 30));
        LZJMS.启动服务端.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.启动服务端ActionPerformed(evt);
            }
        });
        this.getContentPane().add((Component) LZJMS.启动服务端, new AbsoluteConstraints(1380, 800, 130, 40));
        LZJMS.第二控制台.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/后台.png")));
        LZJMS.第二控制台.setText("玩家控制台");
        LZJMS.第二控制台.setEnabled(false);
        LZJMS.第二控制台.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.第二控制台ActionPerformed(evt);
            }
        });
        LZJMS.第三控制台.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/后台.png")));
        LZJMS.第三控制台.setText("特殊控制台");
        LZJMS.第三控制台.setEnabled(false);
        LZJMS.第三控制台.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.第三控制台ActionPerformed(evt);
            }
        });
        final GroupLayout jPanel14Layout = new GroupLayout((Container) this.jPanel14);
        this.jPanel14.setLayout((LayoutManager) jPanel14Layout);
        jPanel14Layout.setHorizontalGroup((Group) jPanel14Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel14Layout.createSequentialGroup().addContainerGap().addGroup((Group) jPanel14Layout.createParallelGroup(Alignment.LEADING).addComponent((Component) LZJMS.第二控制台, Alignment.TRAILING).addComponent((Component) LZJMS.第三控制台, Alignment.TRAILING)).addContainerGap()));
        jPanel14Layout.setVerticalGroup((Group) jPanel14Layout.createParallelGroup(Alignment.LEADING).addGroup((Group) jPanel14Layout.createSequentialGroup().addContainerGap().addComponent((Component) LZJMS.第二控制台, -2, 40, -2).addPreferredGap(ComponentPlacement.RELATED).addComponent((Component) LZJMS.第三控制台, -2, 40, -2).addContainerGap(664, 32767)));
        this.getContentPane().add((Component) this.jPanel14, new AbsoluteConstraints(1510, 0, 140, 760));
        LZJMS.关闭时间显示.setIcon((Icon) new ImageIcon(this.getClass().getResource("/image/信息日志.png")));
        LZJMS.关闭时间显示.setText("关闭时间");
        LZJMS.关闭时间显示.setEnabled(false);
        LZJMS.关闭时间显示.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                LZJMS.this.关闭时间显示ActionPerformed(evt);
            }
        });
        this.getContentPane().add((Component) LZJMS.关闭时间显示, new AbsoluteConstraints(1170, 800, 120, 40));
        LZJMS.关闭时间输入.setText("0");
        LZJMS.关闭时间输入.setEnabled(false);
        this.getContentPane().add((Component) LZJMS.关闭时间输入, new AbsoluteConstraints(1290, 800, 90, 40));
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void 清空日志ActionPerformed(final ActionEvent evt) {
        this.清空文档(LZJMS.outSee);
    }

    private void 保存数据ActionPerformed(final ActionEvent evt) {
        this.保存数据();
    }

    private void 保存雇佣ActionPerformed(final ActionEvent evt) {
        this.重载配置();
    }

    private void 重载全部ActionPerformed(final ActionEvent evt) {
        this.重载全部();
    }

    private void 全局血量等级ActionPerformed(final ActionEvent evt) {
    }

    private void 全局血量倍率ActionPerformed(final ActionEvent evt) {
    }

    private void jButton118ActionPerformed(final ActionEvent evt) {
        this.怪物血量倍率刷新();
    }

    private void jButton117ActionPerformed(final ActionEvent evt) {
        this.怪物血量倍率增加();
    }

    private void jButton115ActionPerformed(final ActionEvent evt) {
        this.怪物血量倍率删除();
    }

    private void jButton116ActionPerformed(final ActionEvent evt) {
        this.怪物血量倍率修改();
    }

    private void jButton114ActionPerformed(final ActionEvent evt) {
        this.全局血量倍率修改();
    }

    private void 启动服务端ActionPerformed(final ActionEvent evt) {
        if (Game.服务端启动中 == 0) {
            LZJMS.启动服务端.setEnabled(false);
            LZJMS.启动服务端.setText("关闭服务端");
            new Thread(new Runnable() {
                public void run() {
                    Start.startServer(null);
                }
            }).start();
        } else {
            this.关闭服务端();
        }
    }

    private void 第二控制台ActionPerformed(final ActionEvent evt) {
        final int n = JOptionPane.showConfirmDialog((Component) this, "你需要打开玩家控制台吗？", "信息", 0);
        if (n == 0) {
            玩家控制台();
        }
    }

    private void jButton19ActionPerformed(final ActionEvent evt) {
        this.公告刷新();
    }

    private void jButton18ActionPerformed(final ActionEvent evt) {
        this.公告修改();
    }

    private void jButton17ActionPerformed(final ActionEvent evt) {
        this.公告增加();
    }

    private void jButton16ActionPerformed(final ActionEvent evt) {
        this.公告删除();
    }

    private void jButton53ActionPerformed(final ActionEvent evt) {
        this.封停MAC刷新();
    }

    private void jButton52ActionPerformed(final ActionEvent evt) {
        this.封停MAC删除();
    }

    private void jButton50ActionPerformed(final ActionEvent evt) {
        this.封停IP刷新();
    }

    private void jButton49ActionPerformed(final ActionEvent evt) {
        this.封停IP删除();
    }

    private void 封停MACActionPerformed(final ActionEvent evt) {
    }

    private void 封停MACStateChanged(final ChangeEvent evt) {
        this.配置更新("封停MAC", (int) (LZJMS.封停MAC.isSelected() ? 1 : 0));
    }

    private void 封停IPActionPerformed(final ActionEvent evt) {
    }

    private void 封停IPStateChanged(final ChangeEvent evt) {
        this.配置更新("封停IP", (int) (LZJMS.封停IP.isSelected() ? 1 : 0));
    }

    private void 封停账号ActionPerformed(final ActionEvent evt) {
    }

    private void 封停账号StateChanged(final ChangeEvent evt) {
        this.配置更新("封停账号", (int) (LZJMS.封停账号.isSelected() ? 1 : 0));
    }

    private void 全服通告ActionPerformed(final ActionEvent evt) {
    }

    private void 全服通告StateChanged(final ChangeEvent evt) {
        this.配置更新("全服通告", (int) (LZJMS.全服通告.isSelected() ? 1 : 0));
    }

    private void 伤害上限FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("伤害上限") != Integer.valueOf(LZJMS.伤害上限.getText())) {
            this.配置更新("伤害上限", Integer.valueOf(LZJMS.伤害上限.getText()).intValue());
        }
    }

    private void 丢失伤害ActionPerformed(final ActionEvent evt) {
    }

    private void 丢失伤害StateChanged(final ChangeEvent evt) {
        this.配置更新("丢失伤害", (int) (LZJMS.丢失伤害.isSelected() ? 1 : 0));
    }

    private void 伤害检测ActionPerformed(final ActionEvent evt) {
    }

    private void 伤害检测StateChanged(final ChangeEvent evt) {
        this.配置更新("伤害检测", (int) (LZJMS.伤害检测.isSelected() ? 1 : 0));
    }

    private void 吸物检测ActionPerformed(final ActionEvent evt) {
    }

    private void 吸物检测StateChanged(final ChangeEvent evt) {
        this.配置更新("吸物检测", (int) (LZJMS.吸物检测.isSelected() ? 1 : 0));
    }

    private void 吸怪检测ActionPerformed(final ActionEvent evt) {
    }

    private void 吸怪检测StateChanged(final ChangeEvent evt) {
        this.配置更新("吸怪检测", (int) (LZJMS.吸怪检测.isSelected() ? 1 : 0));
    }

    private void 全屏检测ActionPerformed(final ActionEvent evt) {
    }

    private void 全屏检测StateChanged(final ChangeEvent evt) {
        this.配置更新("全屏检测", (int) (LZJMS.全屏检测.isSelected() ? 1 : 0));
    }

    private void 攻速检测ActionPerformed(final ActionEvent evt) {
    }

    private void 攻速检测StateChanged(final ChangeEvent evt) {
        this.配置更新("攻速检测", (int) (LZJMS.攻速检测.isSelected() ? 1 : 0));
    }

    private void 段数检测ActionPerformed(final ActionEvent evt) {
    }

    private void 段数检测StateChanged(final ChangeEvent evt) {
        this.配置更新("段数检测", (int) (LZJMS.段数检测.isSelected() ? 1 : 0));
    }

    private void NPC删除ActionPerformed(final ActionEvent evt) {
        this.自建NPC删除();
    }

    private void NPC刷新ActionPerformed(final ActionEvent evt) {
        this.自建NPC刷新();
    }

    private void 怪物经验限制刷新ActionPerformed(final ActionEvent evt) {
        this.怪物经验限制刷新();
    }

    private void 怪物经验限制修改ActionPerformed(final ActionEvent evt) {
        this.怪物经验限制修改();
    }

    private void 怪物经验限制删除ActionPerformed(final ActionEvent evt) {
        this.怪物经验限制删除();
    }

    private void 怪物经验限制增加ActionPerformed(final ActionEvent evt) {
        this.怪物经验限制增加();
    }

    private void 管理员加速ActionPerformed(final ActionEvent evt) {
    }

    private void 管理员加速StateChanged(final ChangeEvent evt) {
        this.配置更新("管理员加速", (int) (LZJMS.管理员加速.isSelected() ? 1 : 0));
    }

    private void 管理员隐身ActionPerformed(final ActionEvent evt) {
    }

    private void 管理员隐身StateChanged(final ChangeEvent evt) {
        this.配置更新("管理员隐身", (int) (LZJMS.管理员隐身.isSelected() ? 1 : 0));
    }

    private void 游戏仓库ActionPerformed(final ActionEvent evt) {
    }

    private void 游戏仓库StateChanged(final ChangeEvent evt) {
        this.配置更新("游戏仓库", (int) (LZJMS.游戏仓库.isSelected() ? 1 : 0));
    }

    private void 玩家指令ActionPerformed(final ActionEvent evt) {
    }

    private void 玩家指令StateChanged(final ChangeEvent evt) {
        this.配置更新("玩家指令", (int) (LZJMS.玩家指令.isSelected() ? 1 : 0));
    }

    private void 地图名称ActionPerformed(final ActionEvent evt) {
    }

    private void 地图名称StateChanged(final ChangeEvent evt) {
        this.配置更新("地图名称", (int) (LZJMS.地图名称.isSelected() ? 1 : 0));
    }

    private void 雇佣商人ActionPerformed(final ActionEvent evt) {
    }

    private void 雇佣商人StateChanged(final ChangeEvent evt) {
        this.配置更新("雇佣商人", (int) (LZJMS.雇佣商人.isSelected() ? 1 : 0));
    }

    private void 越级打怪ActionPerformed(final ActionEvent evt) {
    }

    private void 越级打怪StateChanged(final ChangeEvent evt) {
        this.配置更新("越级打怪", (int) (LZJMS.越级打怪.isSelected() ? 1 : 0));
    }

    private void 游戏喇叭ActionPerformed(final ActionEvent evt) {
    }

    private void 游戏喇叭StateChanged(final ChangeEvent evt) {
        this.配置更新("游戏喇叭", (int) (LZJMS.游戏喇叭.isSelected() ? 1 : 0));
    }

    private void 升级提示ActionPerformed(final ActionEvent evt) {
    }

    private void 升级提示StateChanged(final ChangeEvent evt) {
        this.配置更新("升级提示", (int) (LZJMS.升级提示.isSelected() ? 1 : 0));
    }

    private void 丢出物品ActionPerformed(final ActionEvent evt) {
    }

    private void 丢出物品StateChanged(final ChangeEvent evt) {
        this.配置更新("丢出物品", (int) (LZJMS.丢出物品.isSelected() ? 1 : 0));
    }

    private void 丢出金币ActionPerformed(final ActionEvent evt) {
    }

    private void 丢出金币StateChanged(final ChangeEvent evt) {
        this.配置更新("丢出金币", (int) (LZJMS.丢出金币.isSelected() ? 1 : 0));
    }

    private void 玩家聊天ActionPerformed(final ActionEvent evt) {
    }

    private void 玩家聊天StateChanged(final ChangeEvent evt) {
        this.配置更新("玩家聊天", (int) (LZJMS.玩家聊天.isSelected() ? 1 : 0));
    }

    private void 玩家交易ActionPerformed(final ActionEvent evt) {
    }

    private void 玩家交易StateChanged(final ChangeEvent evt) {
        this.配置更新("玩家交易", (int) (LZJMS.玩家交易.isSelected() ? 1 : 0));
    }

    private void 欢迎弹窗ActionPerformed(final ActionEvent evt) {
    }

    private void 欢迎弹窗StateChanged(final ChangeEvent evt) {
        this.配置更新("欢迎弹窗", (int) (LZJMS.欢迎弹窗.isSelected() ? 1 : 0));
    }

    private void 记录登录信息ActionPerformed(final ActionEvent evt) {
    }

    private void 记录登录信息StateChanged(final ChangeEvent evt) {
        this.配置更新("记录登录信息", (int) (LZJMS.记录登录信息.isSelected() ? 1 : 0));
    }

    private void 管理员独占登录ActionPerformed(final ActionEvent evt) {
    }

    private void 管理员独占登录StateChanged(final ChangeEvent evt) {
        this.配置更新("管理员独占登录", (int) (LZJMS.管理员独占登录.isSelected() ? 1 : 0));
    }

    private void 玩家登录ActionPerformed(final ActionEvent evt) {
    }

    private void 玩家登录StateChanged(final ChangeEvent evt) {
        this.配置更新("玩家登录", (int) (LZJMS.玩家登录.isSelected() ? 1 : 0));
    }

    private void 离线泡点修改ActionPerformed(final ActionEvent evt) {
        this.配置更新("离线泡点金币", Integer.valueOf(LZJMS.离线泡点配置表.getValueAt(0, 1).toString()).intValue());
        this.配置更新("离线泡点经验", Integer.valueOf(LZJMS.离线泡点配置表.getValueAt(1, 1).toString()).intValue());
        this.配置更新("离线泡点抵用", Integer.valueOf(LZJMS.离线泡点配置表.getValueAt(2, 1).toString()).intValue());
        this.配置更新("离线泡点点券", Integer.valueOf(LZJMS.离线泡点配置表.getValueAt(3, 1).toString()).intValue());
        this.配置更新("离线泡点地图", Integer.valueOf(LZJMS.离线泡点配置表.getValueAt(4, 1).toString()).intValue());
        JOptionPane.showMessageDialog(null, "配置更新完毕！", "配置提示", 1);
    }

    private void 离线泡点说明ActionPerformed(final ActionEvent evt) {
        JOptionPane.showMessageDialog(null, "1、雇佣时间调整只有重启后生效。\r\n2、雇佣的奖励填0时不发放，不可以填负数或者其他奇怪的内容。\r\n3、点击修改按钮才会生效并保存。\r\n4、雇佣只支持发放金币和点券。", "雇佣泡点配置说明", 3);
    }

    private void 泡点配置修改ActionPerformed(final ActionEvent evt) {
        this.配置更新("泡点金币", Integer.valueOf(LZJMS.泡点配置表.getValueAt(0, 1).toString()).intValue());
        this.配置更新("泡点经验", Integer.valueOf(LZJMS.泡点配置表.getValueAt(1, 1).toString()).intValue());
        this.配置更新("泡点抵用", Integer.valueOf(LZJMS.泡点配置表.getValueAt(2, 1).toString()).intValue());
        this.配置更新("泡点点券", Integer.valueOf(LZJMS.泡点配置表.getValueAt(3, 1).toString()).intValue());
        this.配置更新("泡点地图", Integer.valueOf(LZJMS.泡点配置表.getValueAt(4, 1).toString()).intValue());
        JOptionPane.showMessageDialog(null, "配置更新完毕！", "配置提示", 1);
    }

    private void 泡点配置说明ActionPerformed(final ActionEvent evt) {
        JOptionPane.showMessageDialog(null, "1、泡点时间调整只有重启后生效。\r\n2、泡点的奖励填0时不发放，不可以填负数或者其他奇怪的内容。\r\n3、点击修改按钮才会生效并保存。", "泡点配置说明", 3);
    }

    private void 雇佣自动回收ActionPerformed(final ActionEvent evt) {
    }

    private void 雇佣自动回收StateChanged(final ChangeEvent evt) {
        this.配置更新("雇佣自动回收", (int) (LZJMS.雇佣自动回收.isSelected() ? 1 : 0));
    }

    private void 雇佣持续时间FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("雇佣持续时间") != Integer.valueOf(LZJMS.雇佣持续时间.getText())) {
            this.配置更新("雇佣持续时间", Integer.valueOf(LZJMS.雇佣持续时间.getText()).intValue());
        }
    }

    private void 同MAC多开ActionPerformed(final ActionEvent evt) {
    }

    private void 同MAC多开StateChanged(final ChangeEvent evt) {
        this.配置更新("同MAC多开", (int) (LZJMS.同MAC多开.isSelected() ? 1 : 0));
    }

    private void 同IP多开ActionPerformed(final ActionEvent evt) {
    }

    private void 同IP多开StateChanged(final ChangeEvent evt) {
        this.配置更新("同IP多开", (int) (LZJMS.同IP多开.isSelected() ? 1 : 0));
    }

    private void IP多开上限FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("IP多开上限") != Integer.valueOf(LZJMS.IP多开上限.getText())) {
            this.配置更新("IP多开上限", Integer.valueOf(LZJMS.IP多开上限.getText()).intValue());
        }
    }

    private void MAC多开上限FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("MAC多开上限") != Integer.valueOf(LZJMS.MAC多开上限.getText())) {
            this.配置更新("MAC多开上限", Integer.valueOf(LZJMS.MAC多开上限.getText()).intValue());
        }
    }

    private void 道具经验加成修改按钮ActionPerformed(final ActionEvent evt) {
        this.道具经验加成调整(2);
    }

    private void 道具经验加成增加ActionPerformed(final ActionEvent evt) {
        this.道具经验加成调整(0);
    }

    private void 喷火龙ActionPerformed(final ActionEvent evt) {
    }

    private void 喷火龙StateChanged(final ChangeEvent evt) {
        this.配置更新("喷火龙", (int) (LZJMS.喷火龙.isSelected() ? 1 : 0));
    }

    private void 小白兔ActionPerformed(final ActionEvent evt) {
    }

    private void 小白兔StateChanged(final ChangeEvent evt) {
        this.配置更新("小白兔", (int) (LZJMS.小白兔.isSelected() ? 1 : 0));
    }

    private void 火野猪ActionPerformed(final ActionEvent evt) {
    }

    private void 火野猪StateChanged(final ChangeEvent evt) {
        this.配置更新("火野猪", (int) (LZJMS.火野猪.isSelected() ? 1 : 0));
    }

    private void 花蘑菇ActionPerformed(final ActionEvent evt) {
    }

    private void 花蘑菇StateChanged(final ChangeEvent evt) {
        this.配置更新("花蘑菇", (int) (LZJMS.花蘑菇.isSelected() ? 1 : 0));
    }

    private void 青鳄鱼ActionPerformed(final ActionEvent evt) {
    }

    private void 青鳄鱼StateChanged(final ChangeEvent evt) {
        this.配置更新("青鳄鱼", (int) (LZJMS.青鳄鱼.isSelected() ? 1 : 0));
    }

    private void 小青蛇ActionPerformed(final ActionEvent evt) {
    }

    private void 小青蛇StateChanged(final ChangeEvent evt) {
        this.配置更新("小青蛇", (int) (LZJMS.小青蛇.isSelected() ? 1 : 0));
    }

    private void 红螃蟹ActionPerformed(final ActionEvent evt) {
    }

    private void 红螃蟹StateChanged(final ChangeEvent evt) {
        this.配置更新("红螃蟹", (int) (LZJMS.红螃蟹.isSelected() ? 1 : 0));
    }

    private void 石头人ActionPerformed(final ActionEvent evt) {
    }

    private void 石头人StateChanged(final ChangeEvent evt) {
        this.配置更新("石头人", (int) (LZJMS.石头人.isSelected() ? 1 : 0));
    }

    private void 紫色猫ActionPerformed(final ActionEvent evt) {
    }

    private void 紫色猫StateChanged(final ChangeEvent evt) {
        this.配置更新("紫色猫", (int) (LZJMS.紫色猫.isSelected() ? 1 : 0));
    }

    private void 大灰狼ActionPerformed(final ActionEvent evt) {
    }

    private void 大灰狼StateChanged(final ChangeEvent evt) {
        this.配置更新("大灰狼", (int) (LZJMS.大灰狼.isSelected() ? 1 : 0));
    }

    private void 顽皮猴ActionPerformed(final ActionEvent evt) {
    }

    private void 顽皮猴StateChanged(final ChangeEvent evt) {
        this.配置更新("顽皮猴", (int) (LZJMS.顽皮猴.isSelected() ? 1 : 0));
    }

    private void 章鱼怪ActionPerformed(final ActionEvent evt) {
    }

    private void 章鱼怪StateChanged(final ChangeEvent evt) {
        this.配置更新("章鱼怪", (int) (LZJMS.章鱼怪.isSelected() ? 1 : 0));
    }

    private void 大海龟ActionPerformed(final ActionEvent evt) {
    }

    private void 大海龟StateChanged(final ChangeEvent evt) {
        this.配置更新("大海龟", (int) (LZJMS.大海龟.isSelected() ? 1 : 0));
    }

    private void 白雪人ActionPerformed(final ActionEvent evt) {
    }

    private void 白雪人StateChanged(final ChangeEvent evt) {
        this.配置更新("白雪人", (int) (LZJMS.白雪人.isSelected() ? 1 : 0));
    }

    private void 漂漂猪ActionPerformed(final ActionEvent evt) {
    }

    private void 漂漂猪StateChanged(final ChangeEvent evt) {
        this.配置更新("漂漂猪", (int) (LZJMS.漂漂猪.isSelected() ? 1 : 0));
    }

    private void 星精灵ActionPerformed(final ActionEvent evt) {
    }

    private void 星精灵StateChanged(final ChangeEvent evt) {
        this.配置更新("星精灵", (int) (LZJMS.星精灵.isSelected() ? 1 : 0));
    }

    private void 胖企鹅ActionPerformed(final ActionEvent evt) {
    }

    private void 胖企鹅StateChanged(final ChangeEvent evt) {
        this.配置更新("胖企鹅", (int) (LZJMS.胖企鹅.isSelected() ? 1 : 0));
    }

    private void 绿水灵ActionPerformed(final ActionEvent evt) {
    }

    private void 绿水灵StateChanged(final ChangeEvent evt) {
        this.配置更新("绿水灵", (int) (LZJMS.绿水灵.isSelected() ? 1 : 0));
    }

    private void 蘑菇仔ActionPerformed(final ActionEvent evt) {
    }

    private void 蘑菇仔StateChanged(final ChangeEvent evt) {
        this.配置更新("蘑菇仔", (int) (LZJMS.蘑菇仔.isSelected() ? 1 : 0));
    }

    private void 蓝蜗牛ActionPerformed(final ActionEvent evt) {
    }

    private void 蓝蜗牛StateChanged(final ChangeEvent evt) {
        this.配置更新("蓝蜗牛", (int) (LZJMS.蓝蜗牛.isSelected() ? 1 : 0));
    }

    private void 等级上限ActionPerformed(final ActionEvent evt) {
    }

    private void 等级上限FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("等级上限") != Integer.valueOf(LZJMS.等级上限.getText())) {
            this.配置更新("等级上限", Integer.valueOf(LZJMS.等级上限.getText()).intValue());
        }
    }

    private void 骑士团开关ActionPerformed(final ActionEvent evt) {
    }

    private void 骑士团开关StateChanged(final ChangeEvent evt) {
        this.配置更新("允许创建骑士团", (int) (LZJMS.骑士团开关.isSelected() ? 1 : 0));
    }

    private void 战神开关ActionPerformed(final ActionEvent evt) {
    }

    private void 战神开关StateChanged(final ChangeEvent evt) {
        this.配置更新("允许创建战神", (int) (LZJMS.战神开关.isSelected() ? 1 : 0));
    }

    private void 冒险家开关ActionPerformed(final ActionEvent evt) {
    }

    private void 冒险家开关StateChanged(final ChangeEvent evt) {
        this.配置更新("允许创建冒险家", (int) (LZJMS.冒险家开关.isSelected() ? 1 : 0));
    }

    private void 服务器名字ActionPerformed(final ActionEvent evt) {
    }

    private void 服务器名字FocusLost(final FocusEvent evt) {
        if (!LZJMS.服务器名字.getText().equals(Game.服务端名字)) {
            this.保存ini配置(9);
        }
    }

    private void 服务器登录端口FocusLost(final FocusEvent evt) {
        if (!LZJMS.服务器登录端口.getText().equals(Short.valueOf(Game.服务端端口))) {
            this.保存ini配置(5);
        }
    }

    private void 服务器频道数量FocusLost(final FocusEvent evt) {
        if (!LZJMS.服务器频道数量.getText().equals(Integer.valueOf(Game.频道个数))) {
            this.保存ini配置(8);
        }
    }

    private void 服务器频道端口FocusLost(final FocusEvent evt) {
        if (!LZJMS.服务器频道端口.getText().equals(Integer.valueOf(Game.频道端口))) {
            this.保存ini配置(7);
        }
    }

    private void 服务器商城端口FocusLost(final FocusEvent evt) {
        if (!LZJMS.服务器商城端口.getText().equals(Short.valueOf(Game.商城端口))) {
            this.保存ini配置(6);
        }
    }

    private void 双倍频道开关ActionPerformed(final ActionEvent evt) {
    }

    private void 双倍频道开关StateChanged(final ChangeEvent evt) {
        this.保存ini配置(14);
    }

    private void 服务器双倍频道FocusLost(final FocusEvent evt) {
        if (!LZJMS.服务器双倍频道.getText().equals(Integer.valueOf(Game.双倍频道))) {
            this.保存ini配置(13);
        }
    }

    private void 服务器经验倍率FocusLost(final FocusEvent evt) {
        if (!LZJMS.服务器经验倍率.getText().equals(String.valueOf(Game.经验倍率))) {
            this.保存ini配置(10);
        }
    }

    private void 服务器掉落倍率FocusLost(final FocusEvent evt) {
        if (!LZJMS.服务器掉落倍率.getText().equals(String.valueOf(Game.物品爆率))) {
            this.保存ini配置(12);
        }
    }

    private void 服务器金币倍率FocusLost(final FocusEvent evt) {
        if (!LZJMS.服务器金币倍率.getText().equals(String.valueOf(Game.金币爆率))) {
            this.保存ini配置(11);
        }
    }

    private void 账号角色上限FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("账号角色上限") != Integer.valueOf(LZJMS.账号角色上限.getText())) {
            this.配置更新("账号角色上限", Integer.valueOf(LZJMS.账号角色上限.getText()).intValue());
        }
    }

    private void MAC注册上限FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("MAC注册上限") != Integer.valueOf(LZJMS.MAC注册上限.getText())) {
            this.配置更新("MAC注册上限", Integer.valueOf(LZJMS.MAC注册上限.getText()).intValue());
        }
    }

    private void 登录自动注册开关ActionPerformed(final ActionEvent evt) {
    }

    private void 登录自动注册开关StateChanged(final ChangeEvent evt) {
        this.配置更新("登录自动注册开关", (int) (LZJMS.登录自动注册开关.isSelected() ? 1 : 0));
    }

    private void 数据库密码FocusLost(final FocusEvent evt) {
        if (!LZJMS.数据库密码.getText().equals(Game.数据库密码)) {
            this.保存ini配置(4);
        }
    }

    private void 数据库名称ActionPerformed(final ActionEvent evt) {
    }

    private void 数据库名称FocusLost(final FocusEvent evt) {
        if (!LZJMS.数据库名称.getText().equals(Game.数据库名字)) {
            this.保存ini配置(0);
        }
    }

    private void 数据库用户名FocusLost(final FocusEvent evt) {
        if (!LZJMS.数据库用户名.getText().equals(Game.数据库用户名)) {
            this.保存ini配置(3);
        }
    }

    private void 数据库端口FocusLost(final FocusEvent evt) {
        if (!LZJMS.数据库端口.getText().equals(Game.数据库端口)) {
            this.保存ini配置(2);
        }
    }

    private void 数据库IP地址FocusLost(final FocusEvent evt) {
        if (!LZJMS.数据库IP地址.getText().equals(Game.数据库IP)) {
            this.保存ini配置(1);
        }
    }

    private void 雇佣经验加成开关StateChanged(final ChangeEvent evt) {
        this.配置更新("雇佣经验加成开关", (int) (LZJMS.雇佣经验加成开关.isSelected() ? 1 : 0));
    }

    private void 雇佣经验加成开关ActionPerformed(final ActionEvent evt) {
    }

    private void 经验加成比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("雇佣经验加成比例") != Integer.valueOf(LZJMS.经验加成比例.getText())) {
            this.配置更新("雇佣经验加成比例", Integer.valueOf(LZJMS.经验加成比例.getText()).intValue());
        }
    }

    private void 机器人开关StateChanged(final ChangeEvent evt) {
        MXDJR.startJR();
    }

    private void 机器人开关ActionPerformed(final ActionEvent evt) {
    }

    private void 升级群消息通知StateChanged(final ChangeEvent evt) {
        this.配置更新("升级群消息通知", (int) (LZJMS.升级群消息通知.isSelected() ? 1 : 0));
    }

    private void 升级群消息通知ActionPerformed(final ActionEvent evt) {
    }

    private void 服务器商城端口ActionPerformed(final ActionEvent evt) {
    }

    private void 服务器登录端口ActionPerformed(final ActionEvent evt) {
    }

    private void 爆物上线开关StateChanged(final ChangeEvent evt) {
        this.配置更新("爆物上线开关", (int) (LZJMS.爆物上线开关.isSelected() ? 1 : 0));
    }

    private void 爆物上线开关ActionPerformed(final ActionEvent evt) {
    }

    private void 自定义箱子代码StateChanged(final ChangeEvent evt) {
        this.配置更新("自定义箱子代码", (int) (LZJMS.自定义箱子代码.isSelected() ? 1 : 0));
    }

    private void 自定义箱子代码ActionPerformed(final ActionEvent evt) {
    }

    private void 上线喇叭StateChanged(final ChangeEvent evt) {
        this.配置更新("上线喇叭", (int) (LZJMS.上线喇叭.isSelected() ? 1 : 0));
    }

    private void 上线喇叭ActionPerformed(final ActionEvent evt) {
    }

    private void 所有显示开关StateChanged(final ChangeEvent evt) {
        this.配置更新("所有显示开关", (int) (LZJMS.所有显示开关.isSelected() ? 1 : 0));
    }

    private void 所有显示开关ActionPerformed(final ActionEvent evt) {
    }

    private void 怪物血量显示开关StateChanged(final ChangeEvent evt) {
        this.配置更新("怪物血量显示开关", (int) (LZJMS.怪物血量显示开关.isSelected() ? 1 : 0));
    }

    private void 怪物血量显示开关ActionPerformed(final ActionEvent evt) {
    }

    private void 野外boss击杀广播StateChanged(final ChangeEvent evt) {
        this.配置更新("野外boss击杀广播", (int) (LZJMS.野外boss击杀广播.isSelected() ? 1 : 0));
    }

    private void 野外boss击杀广播ActionPerformed(final ActionEvent evt) {
    }

    private void boss击杀记录StateChanged(final ChangeEvent evt) {
        this.配置更新("boss击杀记录", (int) (LZJMS.boss击杀记录.isSelected() ? 1 : 0));
    }

    private void boss击杀记录ActionPerformed(final ActionEvent evt) {
    }

    private void 突破上线FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("突破上线") != Integer.valueOf(LZJMS.突破上线.getText())) {
            this.配置更新("突破上线", Integer.valueOf(LZJMS.突破上线.getText()).intValue());
        }
    }

    private void 装备卡破功开关StateChanged(final ChangeEvent evt) {
        this.配置更新("装备卡破功开关", (int) (LZJMS.装备卡破功开关.isSelected() ? 1 : 0));
    }

    private void 装备卡破功开关ActionPerformed(final ActionEvent evt) {
    }

    private void 砍爆率FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("砍爆率") != Integer.valueOf(LZJMS.砍爆率.getText())) {
            this.配置更新("砍爆率", Integer.valueOf(LZJMS.砍爆率.getText()).intValue());
        }
    }

    private void 出装备概率FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("出装备概率") != Integer.valueOf(LZJMS.出装备概率.getText())) {
            this.配置更新("出装备概率", Integer.valueOf(LZJMS.出装备概率.getText()).intValue());
        }
    }

    private void 击杀boss打开npcStateChanged(final ChangeEvent evt) {
        this.配置更新("击杀boss打开npc", (int) (LZJMS.击杀boss打开npc.isSelected() ? 1 : 0));
    }

    private void 击杀boss打开npcActionPerformed(final ActionEvent evt) {
    }

    private void 战力修正StateChanged(final ChangeEvent evt) {
        this.配置更新("战力修正", (int) (LZJMS.战力修正.isSelected() ? 1 : 0));
    }

    private void 战力修正ActionPerformed(final ActionEvent evt) {
    }

    private void 宠物自动吃药开关StateChanged(final ChangeEvent evt) {
        this.配置更新("宠物自动吃药开关", (int) (LZJMS.宠物自动吃药开关.isSelected() ? 1 : 0));
    }

    private void 宠物自动吃药开关ActionPerformed(final ActionEvent evt) {
    }

    private void 自动吃药道具FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自动吃药道具") != Integer.valueOf(LZJMS.自动吃药道具.getText())) {
            this.配置更新("自动吃药道具", Integer.valueOf(LZJMS.自动吃药道具.getText()).intValue());
        }
    }

    private void 坐骑恢复开关StateChanged(final ChangeEvent evt) {
        this.配置更新("坐骑恢复开关", (int) (LZJMS.坐骑恢复开关.isSelected() ? 1 : 0));
    }

    private void 坐骑恢复开关ActionPerformed(final ActionEvent evt) {
    }

    private void 坐骑恢复频率FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("坐骑恢复频率") != Integer.valueOf(LZJMS.坐骑恢复频率.getText())) {
            this.配置更新("坐骑恢复频率", Integer.valueOf(LZJMS.坐骑恢复频率.getText()).intValue());
        }
    }

    private void 坐骑恢复道具FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("坐骑恢复道具") != Integer.valueOf(LZJMS.坐骑恢复道具.getText())) {
            this.配置更新("坐骑恢复道具", Integer.valueOf(LZJMS.坐骑恢复道具.getText()).intValue());
        }
    }

    private void 等级连升开关StateChanged(final ChangeEvent evt) {
        this.配置更新("等级连升开关", (int) (LZJMS.等级连升开关.isSelected() ? 1 : 0));
    }

    private void 等级连升开关ActionPerformed(final ActionEvent evt) {
    }

    private void 世界等级开关StateChanged(final ChangeEvent evt) {
        this.配置更新("世界等级开关", (int) (LZJMS.世界等级开关.isSelected() ? 1 : 0));
    }

    private void 世界等级开关ActionPerformed(final ActionEvent evt) {
    }

    private void 成就还原上卷记录开关StateChanged(final ChangeEvent evt) {
        this.配置更新("成就还原上卷记录开关", (int) (LZJMS.成就还原上卷记录开关.isSelected() ? 1 : 0));
    }

    private void 成就还原上卷记录开关ActionPerformed(final ActionEvent evt) {
    }

    private void 成就上卷加三记录开关StateChanged(final ChangeEvent evt) {
        this.配置更新("成就上卷加三记录开关", (int) (LZJMS.成就上卷加三记录开关.isSelected() ? 1 : 0));
    }

    private void 成就上卷加三记录开关ActionPerformed(final ActionEvent evt) {
    }

    private void 成就上卷加七记录开关StateChanged(final ChangeEvent evt) {
        this.配置更新("成就上卷加七记录开关", (int) (LZJMS.成就上卷加七记录开关.isSelected() ? 1 : 0));
    }

    private void 成就上卷加七记录开关ActionPerformed(final ActionEvent evt) {
    }

    private void 经验加成配置说明1ActionPerformed(final ActionEvent evt) {
    }

    private void 经验加成配置修改1ActionPerformed(final ActionEvent evt) {
        for (int i = 0; i < LZJMS.阶段经验配置表.getRowCount(); ++i) {
            this.配置经验更新(LZJMS.阶段经验配置表.getValueAt(i, 0).toString(), Integer.valueOf(LZJMS.阶段经验配置表.getValueAt(i, 1).toString()).intValue(), i);
        }
        this.重载配置经验列表();
        this.加载配置经验列表();
    }

    private void 不参与叠加道具增加ActionPerformed(final ActionEvent evt) {
        this.不参与增加调整(0);
    }

    private void 不参与叠加道具删除ActionPerformed(final ActionEvent evt) {
        this.不参与增加调整(1);
    }

    private void 泡点开关StateChanged(final ChangeEvent evt) {
        this.配置更新("泡点开关", (int) (LZJMS.泡点开关.isSelected() ? 1 : 0));
    }

    private void 泡点开关ActionPerformed(final ActionEvent evt) {
    }

    private void 泡点等级开关StateChanged(final ChangeEvent evt) {
        this.配置更新("泡点等级开关", (int) (LZJMS.泡点等级开关.isSelected() ? 1 : 0));
    }

    private void 泡点等级开关ActionPerformed(final ActionEvent evt) {
    }

    private void 离线泡点开关StateChanged(final ChangeEvent evt) {
        this.配置更新("离线泡点开关", (int) (LZJMS.离线泡点开关.isSelected() ? 1 : 0));
    }

    private void 离线泡点开关ActionPerformed(final ActionEvent evt) {
    }

    private void 离线泡点等级开关StateChanged(final ChangeEvent evt) {
        this.配置更新("离线泡点等级开关", (int) (LZJMS.离线泡点等级开关.isSelected() ? 1 : 0));
    }

    private void 离线泡点等级开关ActionPerformed(final ActionEvent evt) {
    }

    private void 离线给在线时间开关StateChanged(final ChangeEvent evt) {
        this.配置更新("离线给在线时间开关", (int) (LZJMS.离线给在线时间开关.isSelected() ? 1 : 0));
    }

    private void 离线给在线时间开关ActionPerformed(final ActionEvent evt) {
    }

    private void 阶段经验开关StateChanged(final ChangeEvent evt) {
        this.配置更新("阶段经验开关", (int) (LZJMS.阶段经验开关.isSelected() ? 1 : 0));
    }

    private void 阶段经验开关ActionPerformed(final ActionEvent evt) {
    }

    private void 子弹增加ActionPerformed(final ActionEvent evt) {
        this.子弹调整(0);
    }

    private void 子弹删除ActionPerformed(final ActionEvent evt) {
        this.子弹调整(1);
    }

    private void 特殊组队增加ActionPerformed(final ActionEvent evt) {
        this.特殊职业调整(0);
    }

    private void 特殊组队删除ActionPerformed(final ActionEvent evt) {
        this.特殊职业调整(1);
    }

    private void 道具经验开关StateChanged(final ChangeEvent evt) {
        this.配置更新("道具经验开关", (int) (LZJMS.道具经验开关.isSelected() ? 1 : 0));
    }

    private void 道具经验开关ActionPerformed(final ActionEvent evt) {
    }

    private void 叠加开关StateChanged(final ChangeEvent evt) {
        this.配置更新("叠加开关", (int) (LZJMS.叠加开关.isSelected() ? 1 : 0));
    }

    private void 叠加开关ActionPerformed(final ActionEvent evt) {
    }

    private void 叠加上线FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("叠加上线") != Integer.valueOf(LZJMS.叠加上线.getText())) {
            this.配置更新("叠加上线", Integer.valueOf(LZJMS.叠加上线.getText()).intValue());
        }
    }

    private void 不参与叠加开关StateChanged(final ChangeEvent evt) {
        this.配置更新("不参与叠加开关", (int) (LZJMS.不参与叠加开关.isSelected() ? 1 : 0));
    }

    private void 不参与叠加开关ActionPerformed(final ActionEvent evt) {
    }

    private void 子弹扩充开关StateChanged(final ChangeEvent evt) {
        this.配置更新("子弹扩充开关", (int) (LZJMS.子弹扩充开关.isSelected() ? 1 : 0));
    }

    private void 子弹扩充开关ActionPerformed(final ActionEvent evt) {
    }

    private void 特殊组队经验加成StateChanged(final ChangeEvent evt) {
        this.配置更新("特殊组队经验加成", (int) (LZJMS.特殊组队经验加成.isSelected() ? 1 : 0));
    }

    private void 特殊组队经验加成ActionPerformed(final ActionEvent evt) {
    }

    private void 原始组队经验加成FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("原始组队经验加成") != Integer.valueOf(LZJMS.原始组队经验加成.getText())) {
            this.配置更新("原始组队经验加成", Integer.valueOf(LZJMS.原始组队经验加成.getText()).intValue());
        }
    }

    private void 修正组队经验加成FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("修正组队经验加成") != Integer.valueOf(LZJMS.修正组队经验加成.getText())) {
            this.配置更新("修正组队经验加成", Integer.valueOf(LZJMS.修正组队经验加成.getText()).intValue());
        }
    }

    private void 怪物多倍地图开关StateChanged(final ChangeEvent evt) {
        this.配置更新("怪物多倍地图开关", (int) (LZJMS.怪物多倍地图开关.isSelected() ? 1 : 0));
    }

    private void 怪物多倍地图开关ActionPerformed(final ActionEvent evt) {
    }

    private void 怪物地图多倍怪物开关StateChanged(final ChangeEvent evt) {
        this.配置更新("怪物地图多倍怪物开关", (int) (LZJMS.怪物地图多倍怪物开关.isSelected() ? 1 : 0));
    }

    private void 怪物地图多倍怪物开关ActionPerformed(final ActionEvent evt) {
    }

    private void 多倍地图倍率FocusLost(final FocusEvent evt) {
        this.配置更新("怪物多倍地图倍率", Integer.valueOf(LZJMS.多倍地图倍率.getText()).intValue());
    }

    private void 怪物刷新频率设定FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("怪物刷新频率设定") != Integer.valueOf(LZJMS.怪物刷新频率设定.getText())) {
            this.配置更新("怪物刷新频率设定", Integer.valueOf(LZJMS.怪物刷新频率设定.getText()).intValue());
        }
    }

    private void 怪物减伤开关StateChanged(final ChangeEvent evt) {
        this.配置更新("怪物减伤开关", (int) (LZJMS.怪物减伤开关.isSelected() ? 1 : 0));
    }

    private void 怪物减伤开关ActionPerformed(final ActionEvent evt) {
    }

    private void 伤害突破开关ActionPerformed(final ActionEvent evt) {
    }

    private void 伤害突破开关StateChanged(final ChangeEvent evt) {
        this.配置更新("伤害突破开关", (int) (LZJMS.伤害突破开关.isSelected() ? 1 : 0));
    }

    private void 金币重置ActionPerformed(final ActionEvent evt) {
    }

    private void 金币重置StateChanged(final ChangeEvent evt) {
        this.配置更新("金币重置", (int) (LZJMS.金币重置.isSelected() ? 1 : 0));
    }

    private void 爆物上线数量ActionPerformed(final ActionEvent evt) {
    }

    private void 爆物上线数量FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("爆物上线数量") != Integer.valueOf(LZJMS.爆物上线数量.getText())) {
            this.配置更新("爆物上线数量", Integer.valueOf(LZJMS.爆物上线数量.getText()).intValue());
        }
    }

    private void 道具经验加成修改删除ActionPerformed(final ActionEvent evt) {
        this.道具经验加成调整(1);
    }

    private void 子弹代码ActionPerformed(final ActionEvent evt) {
    }

    private void 增加地图ActionPerformed(final ActionEvent evt) {
        this.怪物地图调整(0);
    }

    private void 修改地图ActionPerformed(final ActionEvent evt) {
        this.怪物地图调整(2);
    }

    private void 删除地图ActionPerformed(final ActionEvent evt) {
        this.怪物地图调整(1);
    }

    private void 刷新地图ActionPerformed(final ActionEvent evt) {
        this.加载多倍怪物列表();
    }

    private void 野外BOSS内容增加ActionPerformed(final ActionEvent evt) {
        this.野外BOSS修改(0);
    }

    private void 野外BOSS内容删除ActionPerformed(final ActionEvent evt) {
        this.野外BOSS修改(1);
    }

    private void 野外BOSS内容修改ActionPerformed(final ActionEvent evt) {
        this.野外BOSS修改(2);
    }

    private void 野外BOSS内容刷新ActionPerformed(final ActionEvent evt) {
        this.重载野外boss刷新列表();
        this.加载野外boss刷新列表();
    }

    private void 世界等级范围ActionPerformed(final ActionEvent evt) {
    }

    private void 世界等级范围FocusLost(final FocusEvent evt) {
        if (Start.世界等级 != Integer.valueOf(LZJMS.世界等级范围.getText()).intValue()) {
            this.世界更新("世界等级", Integer.valueOf(LZJMS.世界等级范围.getText()).intValue());
        }
    }

    private void 金锤子使用开关StateChanged(final ChangeEvent evt) {
        this.配置更新("金锤子使用开关", (int) (LZJMS.金锤子使用开关.isSelected() ? 1 : 0));
    }

    private void 金锤子使用开关ActionPerformed(final ActionEvent evt) {
    }

    private void 金锤子使用概率FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("金锤子使用概率") != Integer.valueOf(LZJMS.金锤子使用概率.getText())) {
            this.配置更新("金锤子使用概率", Integer.valueOf(LZJMS.金锤子使用概率.getText()).intValue());
        }
    }

    private void 金锤子使用概率ActionPerformed(final ActionEvent evt) {
    }

    private void 倍率设置ActionPerformed(final ActionEvent evt) {
    }

    private void 发放道具代码ActionPerformed(final ActionEvent evt) {
    }

    private void 开启倍率活动ActionPerformed(final ActionEvent evt) {
        this.开启倍率活动();
    }

    private void 发放道具ActionPerformed(final ActionEvent evt) {
        this.发放道具();
    }

    private void 持续时间时ActionPerformed(final ActionEvent evt) {
    }

    private void 发放其他内容ActionPerformed(final ActionEvent evt) {
        this.发放其他();
    }

    private void 职业技能初始化ActionPerformed(final ActionEvent evt) {
        this.初始化职业所有技能();
    }

    private void GM固伤开关StateChanged(final ChangeEvent evt) {
        this.配置更新("GM固伤开关", (int) (LZJMS.GM固伤开关.isSelected() ? 1 : 0));
    }

    private void GM固伤开关ActionPerformed(final ActionEvent evt) {
    }

    private void GM固伤伤害FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("GM固伤伤害") != Integer.valueOf(LZJMS.GM固伤伤害.getText())) {
            this.配置更新("GM固伤伤害", Integer.valueOf(LZJMS.GM固伤伤害.getText()).intValue());
        }
    }

    private void GM固伤伤害ActionPerformed(final ActionEvent evt) {
    }

    private void 读取技能ActionPerformed(final ActionEvent evt) {
        this.加载技能列表();
    }

    private void 刷新职业列表ActionPerformed(final ActionEvent evt) {
        this.加载职业列表();
    }

    private void 修改技能ActionPerformed(final ActionEvent evt) {
        this.修改技能();
    }

    private void 个数检测StateChanged(final ChangeEvent evt) {
    }

    private void 个数检测ActionPerformed(final ActionEvent evt) {
        this.配置更新("个数检测", (int) (LZJMS.个数检测.isSelected() ? 1 : 0));
    }

    private void 发放氪金充值ActionPerformed(final ActionEvent evt) {
        this.发放氪金();
    }

    private void 发放充值数量ActionPerformed(final ActionEvent evt) {
    }

    private void 点券比例FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("点券比例") != Integer.valueOf(LZJMS.点券比例.getText())) {
            this.配置更新("点券比例", Integer.valueOf(LZJMS.点券比例.getText()).intValue());
        }
    }

    private void BOSS出装备概率FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("BOSS出装备概率") != Integer.valueOf(LZJMS.BOSS出装备概率.getText())) {
            this.配置更新("BOSS出装备概率", Integer.valueOf(LZJMS.BOSS出装备概率.getText()).intValue());
        }
    }

    private void 技能段数ActionPerformed(final ActionEvent evt) {
    }

    private void 关闭时间显示ActionPerformed(final ActionEvent evt) {
    }

    private void 重载反应堆ActionPerformed(final ActionEvent evt) {
        this.重载设定(6);
    }

    private void 重载副本ActionPerformed(final ActionEvent evt) {
        this.重载设定(1);
    }

    private void 重载爆率ActionPerformed(final ActionEvent evt) {
        this.重载设定(2);
    }

    private void 重载传送门ActionPerformed(final ActionEvent evt) {
        this.重载设定(3);
    }

    private void 重载商店ActionPerformed(final ActionEvent evt) {
        this.重载设定(4);
    }

    private void 重载商城ActionPerformed(final ActionEvent evt) {
        this.重载设定(5);
    }

    private void 重载BOSS事件ActionPerformed(final ActionEvent evt) {
        this.重载设定(8);
    }

    private void 重载自定义事件ActionPerformed(final ActionEvent evt) {
        this.重载设定(7);
    }

    private void 重载活动事件ActionPerformed(final ActionEvent evt) {
        this.重载设定(9);
    }

    private void 保存玩家ActionPerformed(final ActionEvent evt) {
        this.保存功能(1);
    }

    private void 保存雇佣并关闭ActionPerformed(final ActionEvent evt) {
        this.保存功能(2);
    }

    private void 一键清空数据库ActionPerformed(final ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(this, "确定要清空数据库吗？", "警告", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            this.清空数据库();
        }
    }

    private void 充值角色IDActionPerformed(final ActionEvent evt) {
    }

    private void 现有可用点券ActionPerformed(final ActionEvent evt) {
    }

    private void 点券比例ActionPerformed(final ActionEvent evt) {
    }

    private void jButton1ActionPerformed(final ActionEvent evt) {
        this.初始化充值列表();
    }

    private void 清空数据库() {
        Delete("accounts", 1);
        Delete("auctionitems", 2);
        Delete("auctionitems1", 3);
        Delete("auctionpoint", 4);
        Delete("auctionpoint1", 5);
        Delete("bank_item", 6);
        Delete("bank_item1", 7);
        Delete("bank_item2", 8);
        Delete("blocklogin", 9);
        Delete("bosslog", 10);
        Delete("bossrank", 11);
        Delete("bossrank1", 12);
        Delete("bossrank2", 13);
        Delete("bossrank3", 14);
        Delete("bossrank4", 15);
        Delete("bossrank5", 16);
        Delete("bossrank6", 17);
        Delete("bossrank7", 18);
        Delete("bossrank8", 19);
        Delete("bossrank9", 20);
        Delete("buddies", 21);
        Delete("capture_cs", 22);
        Delete("cashshop_limit_sell", 23);
        Delete("character7", 24);
        Delete("charactera", 25);
        Delete("characters", 26);
        Delete("characterz", 27);
        Delete("cheatlog", 28);
        Delete("csequipment", 29);
        Delete("csitems", 30);
        Delete("divine", 31);
        Delete("dueyequipment", 32);
        Delete("dueyitems", 33);
        Delete("dueypackages", 34);
        Delete("eventstats", 35);
        Delete("famelog", 36);
        Delete("families", 37);
        Delete("fengye", 38);
        Delete("forum_reply", 39);
        Delete("forum_section", 40);
        Delete("forum_thread", 41);
        Delete("gmlog", 42);
        Delete("guilds", 43);
        Delete("hiredmerchequipment", 44);
        Delete("hiredmerchitems", 45);
        Delete("hiredmerch", 46);
        Delete("inventoryequipment", 47);
        Delete("inventoryitems", 48);
        Delete("inventorylog", 49);
        Delete("inventoryslot", 50);
        Delete("ipbans", 51);
        Delete("macbans", 52);
        Delete("mapidban", 53);
        Delete("monsterbook", 54);
        Delete("mountdata", 55);
        Delete("mts_cart", 56);
        Delete("mts_items", 57);
        Delete("mtsequipment", 58);
        Delete("mtsitems", 59);
        Delete("mtstransfer", 60);
        Delete("mtstransferequipment", 61);
        Delete("mulungdojo", 62);
        Delete("notes", 63);
        Delete("nxcode", 64);
        Delete("pets", 65);
        Delete("pnpc", 66);
        Delete("qqlog", 67);
        Delete("qqstem", 68);
        Delete("questactions", 69);
        Delete("questinfo", 70);
        Delete("queststatusmobs", 71);
        Delete("regrocklocations", 72);
        Delete("saiji", 73);
        Delete("skillmacros", 74);
        Delete("skills", 75);
        Delete("skills_cooldowns", 76);
        Delete("speedruns", 77);
        Delete("storages", 78);
        Delete("bossrank8", 79);
        Delete("bossrank8", 80);
        Delete("bossrank8", 81);
        Delete("awarp", 82);
        Delete("bank", 83);
        Delete("mail", 84);
        Delete("jiezoudashi", 85);
        Delete("shouce", 100);
        Delete("bosslogA", 86);
    }

    private void Delete(String a, int b) {
        try (Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("Delete from " + a + "");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error/" + a + ":" + e);
        }
    }

    private void 金币砍全局倍率FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("金币砍全局倍率") != Integer.valueOf(LZJMS.金币砍全局倍率.getText())) {
            this.配置更新("金币砍全局倍率", Integer.valueOf(LZJMS.金币砍全局倍率.getText()).intValue());
        }
    }

    private void 金币砍全局倍率ActionPerformed(final ActionEvent evt) {
    }

    private void 金币全局砍数量ActionPerformed(final ActionEvent evt) {
    }

    private void 金币全局砍数量StateChanged(final ChangeEvent evt) {
        this.配置更新("金币全局砍数量", (int) (LZJMS.金币全局砍数量.isSelected() ? 1 : 0));
    }

    private void 特殊宠物吸取开关StateChanged(final ChangeEvent evt) {
        this.配置更新("特殊宠物吸取开关", (int) (LZJMS.特殊宠物吸取开关.isSelected() ? 1 : 0));
    }

    private void 特殊宠物吸取开关ActionPerformed(final ActionEvent evt) {
    }

    private void 特殊宠物吸物开关StateChanged(final ChangeEvent evt) {
        this.配置更新("特殊宠物吸物开关", (int) (LZJMS.特殊宠物吸物开关.isSelected() ? 1 : 0));
    }

    private void 特殊宠物吸物开关ActionPerformed(final ActionEvent evt) {
    }

    private void 特殊宠物吸金开关StateChanged(final ChangeEvent evt) {
        this.配置更新("特殊宠物吸金开关", (int) (LZJMS.特殊宠物吸金开关.isSelected() ? 1 : 0));
    }

    private void 特殊宠物吸金开关ActionPerformed(final ActionEvent evt) {
    }

    private void 特殊宠物吸物无法使用地图开关StateChanged(final ChangeEvent evt) {
        this.配置更新("特殊宠物吸物无法使用地图开关", (int) (LZJMS.特殊宠物吸物无法使用地图开关.isSelected() ? 1 : 0));
    }

    private void 特殊宠物吸物无法使用地图开关ActionPerformed(final ActionEvent evt) {
    }

    private void 不参与叠加道具增加1ActionPerformed(final ActionEvent evt) {
        this.宠物吸取不参与地图调整(0);
    }

    private void 不参与叠加道具删除1ActionPerformed(final ActionEvent evt) {
        this.宠物吸取不参与地图调整(1);
    }

    private void 宠物不参与地图初始ActionPerformed(final ActionEvent evt) {
        this.加载宠物不参与列表();
    }

    private void 不参与叠加道具增加2ActionPerformed(final ActionEvent evt) {
        this.宠物吸金调整(0);
    }

    private void 不参与叠加道具删除2ActionPerformed(final ActionEvent evt) {
        this.宠物吸金调整(1);
    }

    private void 宠物吸取金币初始ActionPerformed(final ActionEvent evt) {
        this.加载宠物吸金列表();
    }

    private void 不参与叠加道具增加3ActionPerformed(final ActionEvent evt) {
        this.宠物吸物调整(0);
    }

    private void 不参与叠加道具删除3ActionPerformed(final ActionEvent evt) {
        this.宠物吸物调整(1);
    }

    private void 宠物吸取物品初始化ActionPerformed(final ActionEvent evt) {
        this.加载宠物吸物列表();
    }

    private void 道具强行宠吸开关StateChanged(final ChangeEvent evt) {
        this.配置更新("道具强行宠吸开关", (int) (LZJMS.道具强行宠吸开关.isSelected() ? 1 : 0));
    }

    private void 道具强行宠吸开关ActionPerformed(final ActionEvent evt) {
    }

    private void 修改地图1ActionPerformed(final ActionEvent evt) {
        this.宠物吸取不参与地图调整(2);
    }

    private void 修改地图2ActionPerformed(final ActionEvent evt) {
        this.宠物吸金调整(2);
    }

    private void 修改地图3ActionPerformed(final ActionEvent evt) {
        this.宠物吸物调整(2);
    }

    private void 宠吸道具FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("宠吸道具") != Integer.valueOf(LZJMS.宠吸道具.getText())) {
            this.配置更新("宠吸道具", Integer.valueOf(LZJMS.宠吸道具.getText()).intValue());
        }
    }

    private void 宠物吸取不参与地图代码1ActionPerformed(final ActionEvent evt) {
    }

    private void 宠物吸金代码1ActionPerformed(final ActionEvent evt) {
    }

    private void 宠物吸取不参与地图代码ActionPerformed(final ActionEvent evt) {
    }

    private void 套装道具增加ActionPerformed(final ActionEvent evt) {
        this.套装伤害加成表调整(0);
    }

    private void 套装道具修改ActionPerformed(final ActionEvent evt) {
        this.套装伤害加成表调整(2);
    }

    private void 套装道具删除ActionPerformed(final ActionEvent evt) {
        this.套装伤害加成表调整(1);
    }

    private void 装备加成伤害列表初始化ActionPerformed(final ActionEvent evt) {
        this.加载套装伤害加成表();
    }

    private void 套装属性加成开关StateChanged(final ChangeEvent evt) {
        this.配置更新("套装属性加成开关", (int) (LZJMS.套装属性加成开关.isSelected() ? 1 : 0));
    }

    private void 套装属性加成开关ActionPerformed(final ActionEvent evt) {
    }

    private void 伤害修正开关StateChanged(final ChangeEvent evt) {
        this.配置更新("伤害修正", (int) (LZJMS.伤害修正开关.isSelected() ? 1 : 0));
    }

    private void 伤害修正开关ActionPerformed(final ActionEvent evt) {
    }

    private void 重置技能总范围FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("重置技能总范围") != Integer.valueOf(LZJMS.重置技能总范围.getText())) {
            this.配置更新("重置技能总范围", Integer.valueOf(LZJMS.重置技能总范围.getText()).intValue());
        }
    }

    private void 重置技能范围开关StateChanged(final ChangeEvent evt) {
        this.配置更新("重置技能范围开关", (int) (LZJMS.重置技能范围开关.isSelected() ? 1 : 0));
    }

    private void 重置技能范围开关ActionPerformed(final ActionEvent evt) {
    }

    private void 越级带人开关StateChanged(final ChangeEvent evt) {
        this.配置更新("越级带人开关", (int) (LZJMS.越级带人开关.isSelected() ? 1 : 0));
    }

    private void 越级带人开关ActionPerformed(final ActionEvent evt) {
    }

    private void 越级带人道具开关StateChanged(final ChangeEvent evt) {
        this.配置更新("越级带人道具开关", (int) (LZJMS.越级带人道具开关.isSelected() ? 1 : 0));
    }

    private void 越级带人道具开关ActionPerformed(final ActionEvent evt) {
    }

    private void 越级带人道具FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("越级带人道具") != Integer.valueOf(LZJMS.越级带人道具.getText())) {
            this.配置更新("越级带人道具", Integer.valueOf(LZJMS.越级带人道具.getText()).intValue());
        }
    }

    private void 越级带人道具ActionPerformed(final ActionEvent evt) {
    }

    private void 套装查询ActionPerformed(final ActionEvent evt) {
        this.查询套装列表(1);
    }

    private void 套装查询1ActionPerformed(final ActionEvent evt) {
        this.查询套装列表(2);
    }

    private void 套装个数FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("套装个数") != Integer.valueOf(LZJMS.套装个数.getText())) {
            this.配置更新("套装个数", Integer.valueOf(LZJMS.套装个数.getText()).intValue());
        }
    }

    private void 重载套装加成ActionPerformed(final ActionEvent evt) {
        final int n = JOptionPane.showConfirmDialog((Component) this, "你需要重载套装加成属性吗？", "信息", 0);
        if (n == 0) {
            tzjc.sr_tz();
            JOptionPane.showMessageDialog(null, "重载完成。");
        }
    }

    private void 重载套装加成1ActionPerformed(final ActionEvent evt) {
        final int n = JOptionPane.showConfirmDialog((Component) this, "你需要重载套装加成属性吗？", "信息", 0);
        if (n == 0) {
            tzjc.sr_tz();
            JOptionPane.showMessageDialog(null, "重载完成。");
        }
    }

    private void 第三控制台ActionPerformed(final ActionEvent evt) {
        final int n = JOptionPane.showConfirmDialog((Component) this, "你需要打开特殊控制台吗？", "信息", 0);
        if (n == 0) {
            特殊控制台();
        }
    }

    private void 宠物不饥饿开关StateChanged(final ChangeEvent evt) {
        this.配置更新("宠物不饥饿开关", (int) (LZJMS.宠物不饥饿开关.isSelected() ? 1 : 0));
    }

    private void 宠物不饥饿开关ActionPerformed(final ActionEvent evt) {
    }

    private void 坐骑不饥饿开关StateChanged(final ChangeEvent evt) {
        this.配置更新("坐骑不饥饿开关", (int) (LZJMS.坐骑不饥饿开关.isSelected() ? 1 : 0));
    }

    private void 坐骑不饥饿开关ActionPerformed(final ActionEvent evt) {
    }

    private void 特殊全宠物吸金开关StateChanged(final ChangeEvent evt) {
        this.配置更新("特殊全宠物吸金开关", (int) (LZJMS.特殊全宠物吸金开关.isSelected() ? 1 : 0));
    }

    private void 特殊全宠物吸金开关ActionPerformed(final ActionEvent evt) {
    }

    private void 特殊全宠物吸物开关StateChanged(final ChangeEvent evt) {
        this.配置更新("特殊全宠物吸物开关", (int) (LZJMS.特殊全宠物吸物开关.isSelected() ? 1 : 0));
    }

    private void 特殊全宠物吸物开关ActionPerformed(final ActionEvent evt) {
    }

    private void 修正队员分配经验FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("修正队员分配经验") != Integer.valueOf(LZJMS.修正队员分配经验.getText())) {
            this.配置更新("修正队员分配经验", Integer.valueOf(LZJMS.修正队员分配经验.getText()).intValue());
        }
    }

    private void 表单卡破功开关StateChanged(final ChangeEvent evt) {
        this.配置更新("表单卡破功开关", (int) (LZJMS.表单卡破功开关.isSelected() ? 1 : 0));
    }

    private void 表单卡破功开关ActionPerformed(final ActionEvent evt) {
    }

    private void 玩家升级喇叭StateChanged(final ChangeEvent evt) {
        this.配置更新("玩家升级喇叭", (int) (LZJMS.玩家升级喇叭.isSelected() ? 1 : 0));
    }

    private void 玩家升级喇叭ActionPerformed(final ActionEvent evt) {
    }

    private void 无限BUFFStateChanged(final ChangeEvent evt) {
        this.配置更新("无限BUFF", (int) (LZJMS.无限BUFF.isSelected() ? 1 : 0));
    }

    private void 无限BUFFActionPerformed(final ActionEvent evt) {
    }

    private void 地图刷新频率FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("地图刷新频率") != Integer.valueOf(LZJMS.地图刷新频率.getText())) {
            this.配置更新("地图刷新频率", Integer.valueOf(LZJMS.地图刷新频率.getText()).intValue());
        }
    }

    private void 氪金ID输入ActionPerformed(final ActionEvent evt) {
    }

    private void 氪金ID查询ActionPerformed(final ActionEvent evt) {
        this.氪金查询(1);
    }

    private void 氪金名字输入ActionPerformed(final ActionEvent evt) {
    }

    private void 氪金名字查询ActionPerformed(final ActionEvent evt) {
        this.氪金查询(2);
    }

    private void 氪金机器码查询ActionPerformed(final ActionEvent evt) {
        this.氪金查询(3);
    }

    private void 氪金机器码输入ActionPerformed(final ActionEvent evt) {
    }

    private void 克隆基础伤害FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("克隆基础伤害") != Integer.valueOf(LZJMS.克隆基础伤害.getText())) {
            this.配置更新("克隆基础伤害", Integer.valueOf(LZJMS.克隆基础伤害.getText()).intValue());
        }
    }

    private void 克隆基础伤害ActionPerformed(final ActionEvent evt) {
    }

    private void 自动刷钱道具FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("自动刷钱道具") != Integer.valueOf(LZJMS.自动刷钱道具.getText())) {
            this.配置更新("自动刷钱道具", Integer.valueOf(LZJMS.自动刷钱道具.getText()).intValue());
        }
    }

    private void 自动刷钱道具ActionPerformed(final ActionEvent evt) {
    }

    private void 公告间隔时间FocusLost(final FocusEvent evt) {
        if (Start.ConfigValuesMap.get("公告间隔时间") != Integer.valueOf(LZJMS.公告间隔时间.getText())) {
            this.配置更新("公告间隔时间", Integer.valueOf(LZJMS.公告间隔时间.getText()).intValue());
        }
    }

    private void 重载公告ActionPerformed(final ActionEvent evt) {
        Start.公告初始化();
        JOptionPane.showMessageDialog(null, "重载公告数据成功！", "提示", 1);
    }

    public void 氪金同账号查询(final int type) {
        for (int i = ((DefaultTableModel) (DefaultTableModel) this.充值赞助列表1.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) this.充值赞助列表1.getModel()).removeRow(i);
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
            ps.setInt(1, type);
            rs = ps.executeQuery();
            while (rs.next()) {
                final int accountid = rs.getInt("accountid");
                PreparedStatement ps2 = null;
                ResultSet rs2 = null;
                ps2 = con.prepareStatement("SELECT * FROM characters WHERE accountid = ?");
                ps2.setInt(1, accountid);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    final int characterid = rs2.getInt("id");
                    PreparedStatement ps3 = null;
                    ResultSet rs3 = null;
                    ps3 = con.prepareStatement("SELECT * FROM paymoney WHERE characterid = ?");
                    ps3.setInt(1, characterid);
                    rs3 = ps3.executeQuery();
                    while (rs3.next()) {
                        ((DefaultTableModel) this.充值赞助列表1.getModel()).insertRow(this.充值赞助列表1.getRowCount(), new Object[]{Integer.valueOf(rs3.getInt("characterid")), NPCConversationManager.角色ID取名字(rs3.getInt("characterid")), Integer.valueOf(NPCConversationManager.getMRJF(rs3.getInt("characterid"))), Integer.valueOf(rs3.getInt("ljjf")), Integer.valueOf(rs3.getInt("syjf")), Integer.valueOf(rs3.getInt("kydj"))});
                    }
                    rs3.close();
                    ps3.close();
                }
                rs2.close();
                ps2.close();
            }
            rs.close();
            ps.close();
            con.close();
            return;
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]加载充值赞助列表出错：" + ex.getMessage() + ex);
            return;
        }
    }

    public void 氪金查询(final int type) {
        for (int i = ((DefaultTableModel) (DefaultTableModel) this.充值赞助列表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) this.充值赞助列表.getModel()).removeRow(i);
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            switch (type) {
                case 1: {
                    ps = con.prepareStatement("SELECT * FROM paymoney WHERE characterid = ?");
                    ps.setInt(1, Integer.valueOf(this.氪金ID输入.getText()).intValue());
                    rs = ps.executeQuery();
                    break;
                }
                case 2: {
                    ps = con.prepareStatement("SELECT * FROM paymoney WHERE characterid = ?");
                    ps.setInt(1, NPCConversationManager.角色名字取Id(this.氪金名字输入.getText()));
                    rs = ps.executeQuery();
                    break;
                }
                case 3: {
                    ps = con.prepareStatement("SELECT * FROM accounts WHERE macs = ?");
                    ps.setString(1, this.氪金机器码输入.getText());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        final int accountid = rs.getInt("id");
                        PreparedStatement ps2 = null;
                        ResultSet rs2 = null;
                        ps2 = con.prepareStatement("SELECT * FROM characters WHERE accountid = ?");
                        ps2.setInt(1, accountid);
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            final int characterid = rs2.getInt("id");
                            PreparedStatement ps3 = null;
                            ResultSet rs3 = null;
                            ps3 = con.prepareStatement("SELECT * FROM paymoney WHERE characterid = ?");
                            ps3.setInt(1, characterid);
                            rs3 = ps3.executeQuery();
                            while (rs3.next()) {
                                ((DefaultTableModel) this.充值赞助列表.getModel()).insertRow(this.充值赞助列表.getRowCount(), new Object[]{Integer.valueOf(rs3.getInt("characterid")), NPCConversationManager.角色ID取名字(rs3.getInt("characterid")), Integer.valueOf(NPCConversationManager.getMRJF(rs3.getInt("characterid"))), Integer.valueOf(rs3.getInt("ljjf")), Integer.valueOf(rs3.getInt("syjf")), Integer.valueOf(rs3.getInt("kydj"))});
                            }
                            rs3.close();
                            ps3.close();
                        }
                        rs2.close();
                        ps2.close();
                    }
                    rs.close();
                    ps.close();
                    con.close();
                    return;
                }
            }
            while (rs.next()) {
                ((DefaultTableModel) this.充值赞助列表.getModel()).insertRow(this.充值赞助列表.getRowCount(), new Object[]{Integer.valueOf(rs.getInt("characterid")), NPCConversationManager.角色ID取名字(rs.getInt("characterid")), Integer.valueOf(NPCConversationManager.getMRJF(rs.getInt("characterid"))), Integer.valueOf(rs.getInt("ljjf")), Integer.valueOf(rs.getInt("syjf")), Integer.valueOf(rs.getInt("kydj"))});
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]加载充值赞助列表出错：" + ex.getMessage() + ex);
        }
    }

    public void 初始化充值列表() {
        for (int i = ((DefaultTableModel) (DefaultTableModel) this.充值赞助列表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) this.充值赞助列表.getModel()).removeRow(i);
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM paymoney");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) this.充值赞助列表.getModel()).insertRow(this.充值赞助列表.getRowCount(), new Object[]{Integer.valueOf(rs.getInt("characterid")), NPCConversationManager.角色ID取名字(rs.getInt("characterid")), Integer.valueOf(NPCConversationManager.getMRJF(rs.getInt("characterid"))), Integer.valueOf(rs.getInt("ljjf")), Integer.valueOf(rs.getInt("syjf")), Integer.valueOf(rs.getInt("kydj"))});
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]加载充值赞助列表出错：" + ex.getMessage());
        }
    }

    public void 查询套装列表(final int a) {
        for (int i = ((DefaultTableModel) (DefaultTableModel) LZJMS.套装伤害加成表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) LZJMS.套装伤害加成表.getModel()).removeRow(i);
        }
        if (a == 1) {
            for (int i = 0; i < Start.套装加成表.size(); ++i) {
                if ((Start.套装加成表.get(i)).getLeft() == Integer.valueOf(this.套装排序输入.getText())) {
                    ((DefaultTableModel) LZJMS.套装伤害加成表.getModel()).insertRow(LZJMS.套装伤害加成表.getRowCount(), new Object[]{(((Start.套装加成表.get(i)).getRight()).getRight()).getLeft(), MapleItemInformationProvider.getInstance().getName(Integer.valueOf((String) (((Start.套装加成表.get(i)).getRight()).getRight()).getLeft()).intValue()), (((Start.套装加成表.get(i)).getRight()).getRight()).getRight(), (Start.套装加成表.get(i)).getLeft(), ((Start.套装加成表.get(i)).getRight()).getLeft()});
                }
            }
        } else {
            for (int i = 0; i < Start.套装加成表.size(); ++i) {
                if (((String) ((Start.套装加成表.get(i)).getRight()).getLeft()).contains((CharSequence) this.套装名字输入.getText())) {
                    ((DefaultTableModel) LZJMS.套装伤害加成表.getModel()).insertRow(LZJMS.套装伤害加成表.getRowCount(), new Object[]{(((Start.套装加成表.get(i)).getRight()).getRight()).getLeft(), MapleItemInformationProvider.getInstance().getName(Integer.valueOf((String) (((Start.套装加成表.get(i)).getRight()).getRight()).getLeft()).intValue()), (((Start.套装加成表.get(i)).getRight()).getRight()).getRight(), (Start.套装加成表.get(i)).getLeft(), ((Start.套装加成表.get(i)).getRight()).getLeft()});
                }
            }
        }
    }

    public void 保存功能(final int a) {
        String 内容 = "";
        switch (a) {
            case 1: {
                内容 = "保存所有玩家数据";
                break;
            }
            case 2: {
                内容 = "保存所有雇佣数据并关闭";
                break;
            }
        }
        final int n = JOptionPane.showConfirmDialog((Component) this, "需要" + 内容 + "吗？", "信息", 0);
        if (n == 0) {
            int p = 0;
            switch (a) {
                case 1: {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                            ++p;
                            chr.saveToDB(true, true);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "保存" + p + "个在线角色成功!");
                    break;
                }
                case 2: {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.closeAllMerchant();
                    }
                    JOptionPane.showMessageDialog(null, "保存雇佣商店成功!");
                    break;
                }
            }
        }
    }

    public void 重载设定(final int a) {
        String 内容 = "";
        switch (a) {
            case 1: {
                内容 = "重载副本";
                break;
            }
            case 2: {
                内容 = "重载爆率";
                break;
            }
            case 3: {
                内容 = "重载传送门脚本";
                break;
            }
            case 4: {
                内容 = "重载商店";
                break;
            }
            case 5: {
                内容 = "重载商城";
                break;
            }
            case 6: {
                内容 = "重载反应堆爆率";
                break;
            }
            case 7: {
                内容 = "重载自定义事件";
                break;
            }
            case 8: {
                内容 = "重载BOSS事件";
                break;
            }
            case 9: {
                内容 = "重载活动事件";
                break;
            }
        }
        final int n = JOptionPane.showConfirmDialog((Component) this, "你需要" + 内容 + "吗？", "信息", 0);
        if (n == 0) {
            switch (a) {
                case 1: {
                    for (final ChannelServer instance1 : ChannelServer.getAllInstances()) {
                        if (instance1 != null) {
                            instance1.reloadEvents();
                        }
                    }
                    break;
                }
                case 2: {
                    MapleMonsterInformationProvider.getInstance().clearDrops();
                    break;
                }
                case 3: {
                    PortalScriptManager.getInstance().clearScripts();
                    break;
                }
                case 4: {
                    MapleShopFactory.getInstance().clear();
                    break;
                }
                case 5: {
                    CashItemFactory.getInstance().clearCashShop();
                    break;
                }
                case 6: {
                    ReactorScriptManager.getInstance().clearDrops();
                    break;
                }
                case 7: {
                    for (final ChannelServer instance1 : ChannelServer.getAllInstances()) {
                        if (instance1 != null) {
                            instance1.reloadEventsc();
                        }
                    }
                    break;
                }
                case 8: {
                    for (final ChannelServer instance1 : ChannelServer.getAllInstances()) {
                        if (instance1 != null) {
                            instance1.reloadEventsb();
                        }
                    }
                    break;
                }
                case 9: {
                    for (final ChannelServer instance1 : ChannelServer.getAllInstances()) {
                        if (instance1 != null) {
                            instance1.reloadEventsa();
                        }
                    }
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "重载完成。");
        }
    }

    public static int getLJJF(final int id) {
        int jf = 0;
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("select * from paymoney where characterid =?");
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jf = rs.getInt("ljjf");
            } else {
                final PreparedStatement psu = con.prepareStatement("insert into paymoney (characterid,syjf,ljjf,kydj) VALUES (?,?,?,?)");
                psu.setInt(1, id);
                psu.setInt(2, 0);
                psu.setInt(3, 0);
                psu.setInt(3, 0);
                psu.executeUpdate();
                psu.close();
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("累计积分读取发生错误: " + ex);
        }
        return jf;
    }

    public static int getSYJF(final int id) {
        int jf = 0;
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("select * from paymoney where characterid =?");
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jf = rs.getInt("syjf");
            } else {
                final PreparedStatement psu = con.prepareStatement("insert into paymoney (characterid,syjf,ljjf,kydj) VALUES (?,?,?,?)");
                psu.setInt(1, id);
                psu.setInt(2, 0);
                psu.setInt(3, 0);
                psu.setInt(3, 0);
                psu.executeUpdate();
                psu.close();
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("剩余积分读取发生错误: " + ex);
        }
        return jf;
    }

    public static int getKYDJ(final int id) {
        int jf = 0;
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("select * from paymoney where characterid =?");
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jf = rs.getInt("kydj");
            } else {
                final PreparedStatement psu = con.prepareStatement("insert into paymoney (characterid,syjf,ljjf,kydj) VALUES (?,?,?,?)");
                psu.setInt(1, id);
                psu.setInt(2, 0);
                psu.setInt(3, 0);
                psu.setInt(3, 0);
                psu.executeUpdate();
                psu.close();
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("可用点券读取发生错误: " + ex);
        }
        return jf;
    }

    public static int getMRJF(final int id) {
        int jf = 0;
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("select * from paymoney1 where characterid =?");
            ps.setInt(1, id);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jf = rs.getInt("mrjf");
            } else {
                final PreparedStatement psu = con.prepareStatement("insert into paymoney1 (characterid,mrjf) VALUES (?,?)");
                psu.setInt(1, id);
                psu.setInt(2, 0);
                psu.executeUpdate();
                psu.close();
            }
            ps.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("每日积分读取发生错误: " + ex);
        }
        return jf;
    }

    public static void setLJJF(final int id, final int slot) {
        final int jf = getLJJF(id);
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE paymoney SET ljjf = ? where characterid = ?");
            ps.setInt(1, jf + slot);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("累计充值加减发生错误: " + ex);
        }
    }

    public static void setSYJF(final int id, final int slot) {
        final int jf = getSYJF(id);
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE paymoney SET syjf = ? where characterid = ?");
            ps.setInt(1, jf + slot);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("剩余充值加减发生错误: " + ex);
        }
    }

    public static void setKYDJ(final int id, final int slot) {
        final int jf = getKYDJ(id);
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE paymoney SET kydj = ? where characterid = ?");
            ps.setInt(1, jf + slot);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("可用点券加减发生错误: " + ex);
        }
    }

    public static void setMRJF(final int id, final int slot) {
        final int jf = getMRJF(id);
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE paymoney1 SET mrjf = ? where characterid = ?");
            ps.setInt(1, jf + slot);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("每日充值加减发生错误: " + ex);
        }
    }

    public void 发放氪金() {
        int 道具数量 = 0;
        if ("输入数字".equals(this.发放充值数量.getText())) {
            道具数量 = 0;
        } else {
            道具数量 = Integer.parseInt(this.发放充值数量.getText());
        }
        final String 名字 = "";
        int 玩家ID = 0;
        if (this.充值角色ID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "找不到氪金玩家！", "充值赞助提示", 1);
        } else {
            玩家ID = Integer.parseInt(this.充值角色ID.getText());
        }
        String 玩家的名字 = "";
        if (this.充值玩家名字.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "找不到氪金玩家！", "充值赞助提示", 1);
        } else {
            玩家的名字 = this.充值玩家名字.getText();
        }
        final int 发放类型 = 0;
        final int 发放类型2 = 0;
        final int 发放类型3 = 0;
        String 发放类型名 = "";
        String 发放类型名2 = "";
        String 发放类型名3 = "";
        String 发放类型名4 = "";
        if (this.每日充值.getState()) {
            发放类型名 = "每日充值";
        }
        if (this.累计充值.getState()) {
            发放类型名2 = "累计充值";
        }
        if (this.剩余余额.getState()) {
            发放类型名3 = "剩余余额";
        }
        if (this.可用点券.getState()) {
            发放类型名4 = "可用点券";
        }
        final int answer = JOptionPane.showConfirmDialog((Component) this, "当前选择的类型是:" + 发放类型名 + " " + 发放类型名2 + " " + 发放类型名3 + " " + 发放类型名4 + "\r\n当前输入数量设置是:" + 道具数量 + "个\r\n当前发放范围设置是:" + 名字 + "\r\n当前你选择的角色名字是:" + 玩家的名字 + "\r\n请问您是否要发放呢?\r\n", "发放点卷抵用金币", 0);
        if (answer != 0) {
            return;
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (this.每日充值.getState()) {
            setMRJF(玩家ID, 道具数量);
        }
        if (this.累计充值.getState()) {
            setLJJF(玩家ID, 道具数量);
        }
        if (this.剩余余额.getState()) {
            setSYJF(玩家ID, 道具数量);
        }
        if (this.可用点券.getState()) {
            setKYDJ(玩家ID, 道具数量 * Integer.parseInt(LZJMS.点券比例.getText()));
        }
        this.初始化充值列表();
        JOptionPane.showMessageDialog(null, "处理完毕！", "充值赞助提示", 1);
    }

    public void 修改技能() {
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE skilltest set mobcount = ? ,attackcount = ? where skillid = ?");
            ps.setInt(1, Integer.valueOf(this.怪物个数.getText()).intValue());
            ps.setInt(2, Integer.valueOf(this.技能段数.getText()).intValue());
            ps.setInt(3, Integer.valueOf(this.技能代码.getText()).intValue());
            ps.execute();
            ps.close();
            this.重载技能列表();
            this.重载职业列表();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "技能检测表单调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]技能检测表单调整出错：" + ex.getMessage());
        }
    }

    private void 重载技能列表() {
        Start.SkillType.clear();
        Start.GetSkillTable();
    }

    public void 重载职业列表() {
        for (int i = ((DefaultTableModel) (DefaultTableModel) this.职业技能列表表单.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) this.职业技能列表表单.getModel()).removeRow(i);
        }
        int i = this.职业列表表单.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
            return;
        }
        this.当前职业ID.setText("当前职业ID: 【" + this.职业列表表单.getValueAt(i, 0).toString() + "】");
        this.当前职业名称.setText("当前职业名称: 【" + this.职业列表表单.getValueAt(i, 1).toString() + "】");
        for (int a = 0; a < Start.SkillType.size(); ++a) {
            if (((SkillType) Start.SkillType.get(a)).getSkillId() / 10000 == Integer.parseInt(this.职业列表表单.getValueAt(i, 0).toString())) {
                ((DefaultTableModel) this.职业技能列表表单.getModel()).insertRow(this.职业技能列表表单.getRowCount(), new Object[]{Integer.valueOf(((SkillType) Start.SkillType.get(a)).getSkillId()), SkillFactory.getName(((SkillType) Start.SkillType.get(a)).getSkillId()), Integer.valueOf(((SkillType) Start.SkillType.get(a)).getAttackCount()), Integer.valueOf(((SkillType) Start.SkillType.get(a)).getMobCount())});
            }
        }
    }

    public void 加载职业列表() {
        for (int i = ((DefaultTableModel) (DefaultTableModel) this.职业列表表单.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) this.职业列表表单.getModel()).removeRow(i);
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = con.prepareStatement("SELECT * FROM job");
            rs = ps.executeQuery();
            while (rs.next()) {
                ((DefaultTableModel) this.职业列表表单.getModel()).insertRow(this.职业列表表单.getRowCount(), new Object[]{Integer.valueOf(rs.getInt("jobid")), MapleJob.getName(MapleJob.getById(rs.getInt("jobid")))});
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]加载职业列表表单出错：" + ex.getMessage());
        }
        JOptionPane.showMessageDialog(null, "处理完毕！", "职业列表表单提示", 1);
    }

    public void 加载技能列表() {
        for (int i = ((DefaultTableModel) (DefaultTableModel) this.职业技能列表表单.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) this.职业技能列表表单.getModel()).removeRow(i);
        }
        int i = this.职业列表表单.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
            return;
        }
        this.当前职业ID.setText("当前职业ID: 【" + this.职业列表表单.getValueAt(i, 0).toString() + "】");
        this.当前职业名称.setText("当前职业名称: 【" + this.职业列表表单.getValueAt(i, 1).toString() + "】");
        for (int a = 0; a < Start.SkillType.size(); ++a) {
            if (((SkillType) Start.SkillType.get(a)).getSkillId() / 10000 == Integer.parseInt(this.职业列表表单.getValueAt(i, 0).toString())) {
                ((DefaultTableModel) this.职业技能列表表单.getModel()).insertRow(this.职业技能列表表单.getRowCount(), new Object[]{Integer.valueOf(((SkillType) Start.SkillType.get(a)).getSkillId()), SkillFactory.getName(((SkillType) Start.SkillType.get(a)).getSkillId()), Integer.valueOf(((SkillType) Start.SkillType.get(a)).getAttackCount()), Integer.valueOf(((SkillType) Start.SkillType.get(a)).getMobCount()), Integer.valueOf(0)});
            }
        }
        JOptionPane.showMessageDialog(null, "处理完毕！", "职业列表表单提示", 1);
    }

    public void 配置经验更新(final String name, final int value, final int id) {
        if (Start.exptable.get(id) != null) {
            Start.exptable.set(id, new Pair(name, Integer.valueOf(value)));
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE exptable SET numb = ? WHERE name = ?");
            ps.setInt(1, value);
            ps.setString(2, name);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }

    private void 重载配置经验列表() {
        Start.exptable.clear();
        Start.GetExpTable();
    }

    private void 加载配置经验列表() {
        for (int i = ((DefaultTableModel) LZJMS.阶段经验配置表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.阶段经验配置表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.exptable.size(); ++i) {
            ((DefaultTableModel) LZJMS.阶段经验配置表.getModel()).insertRow(LZJMS.阶段经验配置表.getRowCount(), new Object[]{Integer.valueOf((String) (Start.exptable.get(i)).getLeft()), (Start.exptable.get(i)).getRight()});
        }
    }

    public void 初始化职业所有技能() {
        final int answer = JOptionPane.showConfirmDialog((Component) this, "点击此处所有技能数据会全部清空重新加载\r\n请问你是否要进行该操作!?", "初始化职业所有技能", 0);
        if (answer != 0) {
            return;
        }
        Start.SkillType.clear();
        this.删除技能表();
        final MapleDataProvider skillWZ = MapleDataProviderFactory.getDataProvider("Skill.wz");
        final MapleDataDirectoryEntry root = skillWZ.getRoot();
        for (final MapleDataFileEntry topDir : root.getFiles()) {
            if (topDir.getName().length() <= 8) {
                for (final MapleData data : skillWZ.getData(topDir.getName())) {
                    if (data.getName().equals("skill")) {
                        for (final MapleData subData : data) {
                            if (subData != null) {
                                final int skillId = Integer.parseInt(subData.getName());
                                final Skill skil = Skill.loadFromData(skillId, subData);
                                if ((skil.getEffect1((int) skil.getMaxLevel()).getDamage() <= 100 || skil.getEffect1((int) skil.getMaxLevel()).getMpCon() <= 0 || skil.getEffect1((int) skil.getMaxLevel()).getDuration() >= 50000) && (skil.getEffect1((int) skil.getMaxLevel()).getDuration() >= 1 || skil.getEffect1((int) skil.getMaxLevel()).getMpCon() <= 0 || skil.getEffect1((int) skil.getMaxLevel()).getRange() >= 1 || skillId == 4111006) && (skil.getEffect1((int) skil.getMaxLevel()).getMatk() <= 0 || skil.getEffect1((int) skil.getMaxLevel()).getDuration() >= 50000) && skillId != 3221007) {
                                    continue;
                                }
                                Start.SkillType.add(new SkillType(skillId, (int) skil.getEffect1((int) skil.getMaxLevel()).getMobCount(), (int) ((skil.getEffect1((int) skil.getMaxLevel()).getBulletCount() > 1) ? skil.getEffect1((int) skil.getMaxLevel()).getBulletCount() : skil.getEffect1((int) skil.getMaxLevel()).getAttackCount())));
                            }
                        }
                    }
                }
            }
        }
        int p = 0;
        for (int a = 0; a < Start.SkillType.size(); ++a) {
            this.初始技能数据进入数据库(((SkillType) Start.SkillType.get(a)).getSkillId(), ((SkillType) Start.SkillType.get(a)).getMobCount(), ((SkillType) Start.SkillType.get(a)).getAttackCount());
            ++p;
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]技能加载初始化:一共加载了" + p + "个技能");
        JOptionPane.showMessageDialog(null, "处理完毕！:一共加载了" + p + "个技能", "初始化技能提示", 1);
    }

    public void 初始技能数据进入数据库(final int skillid, final int mobcount, final int attckcount) {
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = null;
            ps = con.prepareStatement("INSERT INTO skilltest (skillid,mobcount,attackcount) VALUES(?,?,?)");
            ps.setInt(1, skillid);
            ps.setInt(2, mobcount);
            ps.setInt(3, attckcount);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]初始化技能失败：" + ex.getMessage());
        }
    }

    public void 删除技能表() {
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = null;
            ps = con.prepareStatement("DELETE FROM skilltest");
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]清空技能失败：" + ex.getMessage());
        }
    }

    public void 开启倍率活动() {
        int 数量 = 0;
        if ("输入数字".equals(this.倍率设置.getText())) {
            数量 = 1;
        } else {
            数量 = ((Integer.parseInt(this.倍率设置.getText()) < 1) ? 1 : Integer.parseInt(this.倍率设置.getText()));
        }
        int 持续时间HOUR = 0;
        if ("输入数字".equals(this.持续时间时.getText())) {
            持续时间HOUR = 0;
        } else {
            持续时间HOUR = Integer.parseInt(this.持续时间时.getText());
        }
        int 持续时间MIN = 0;
        if ("输入数字".equals(this.持续时间分.getText())) {
            持续时间MIN = 0;
        } else {
            持续时间MIN = Integer.parseInt(this.持续时间分.getText());
        }
        int 选择类型 = 0;
        String 名字 = "";
        if ("输入数字".equals(Integer.valueOf(this.倍率活动类型.getSelectedIndex()))) {
            选择类型 = 0;
        } else {
            选择类型 = this.倍率活动类型.getSelectedIndex();
            switch (选择类型) {
                case 0: {
                    名字 = "经验";
                    break;
                }
                case 1: {
                    名字 = "爆率";
                    break;
                }
                case 2: {
                    名字 = "金币";
                    break;
                }
            }
        }
        final int answer = JOptionPane.showConfirmDialog((Component) this, "当前输入的倍率设置是:" + 数量 + "倍\r\n当前输入的持续小时时间设置是:" + 持续时间HOUR + "小时\r\n当前输入的持续分钟时间设置是:" + 持续时间MIN + "分钟\r\n当前输入的选择的类型是:" + 名字 + "\r\n请问您是否要发放呢?\r\n", "开启系统倍率活动", 0);
        if (answer != 0) {
            return;
        }
        final int time = 持续时间MIN * 60 + 持续时间HOUR * 60 * 60;
        boolean bOk = true;
        if (选择类型 == 0) {
            for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "经验倍率已经成功修改为 " + 数量 + "倍。祝大家游戏开心.经验倍率将在" + 持续时间HOUR + "小时" + 持续时间MIN + "分钟后自动更正！"));
            }
            MapleParty.活动经验倍率 = 数量;
        } else if (选择类型 == 1) {
            for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "爆率倍率已经成功修改为 " + 数量 + "倍。祝大家游戏开心.经验倍率将在" + 持续时间HOUR + "小时" + 持续时间MIN + "分钟后自动更正！！"));
            }
            MapleParty.活动爆率倍率 = 数量;
        } else if (选择类型 == 2) {
            for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "金币倍率已经成功修改为 " + 数量 + "倍。祝大家游戏开心.经验倍率将在" + 持续时间HOUR + "小时" + 持续时间MIN + "分钟后自动更正！！"));
            }
            MapleParty.活动金币倍率 = 数量;
        } else {
            bOk = false;
        }
        if (bOk) {
            World.scheduleRateDelay(名字, (long) time);
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]" + 名字 + "活动已经成功修改为 " + 数量 + "倍将在" + 持续时间HOUR + "小时" + 持续时间MIN + "分钟后自动更正！！");
    }

    public void 发放其他() {
        int 道具数量 = 0;
        if ("输入数字".equals(this.发放其他数量.getText())) {
            道具数量 = 0;
        } else {
            道具数量 = Integer.parseInt(this.发放其他数量.getText());
        }
        int 发放范围 = 0;
        String 名字 = "";
        String 玩家的名字 = "";
        if ("输入数字".equals(Integer.valueOf(this.发放其他范围.getSelectedIndex()))) {
            发放范围 = 0;
        } else {
            发放范围 = this.发放其他范围.getSelectedIndex();
            switch (发放范围) {
                case 0: {
                    名字 = "全服";
                    break;
                }
                case 1: {
                    名字 = "个人";
                    玩家的名字 = this.发放其他玩家.getText();
                    break;
                }
            }
        }
        int 发放类型 = 0;
        String 名字2 = "";
        if ("输入数字".equals(Integer.valueOf(this.发放其他类型.getSelectedIndex()))) {
            发放类型 = 0;
        } else {
            发放类型 = this.发放其他类型.getSelectedIndex();
            switch (发放类型) {
                case 0: {
                    名字2 = "点卷";
                    break;
                }
                case 1: {
                    名字2 = "抵用";
                    break;
                }
                case 2: {
                    名字2 = "金币";
                    break;
                }
            }
        }
        final int answer = JOptionPane.showConfirmDialog((Component) this, "当前选择的类型是:" + 名字2 + "\r\n当前输入数量设置是:" + 道具数量 + "个\r\n当前发放范围设置是:" + 名字 + "" + ((发放范围 == 1) ? ("\r\n当前你选择的角色名字是:" + 玩家的名字 + "\r\n") : "") + "请问您是否要发放呢?\r\n", "发放点卷抵用金币", 0);
        if (answer != 0) {
            return;
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        int 个数 = 0;
        if (发放范围 == 0) {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                    if (发放类型 == 0) {
                        mch.modifyCSPoints(1, 道具数量, true);
                    } else if (发放类型 == 1) {
                        mch.modifyCSPoints(2, 道具数量, true);
                    } else if (发放类型 == 2) {
                        mch.gainMeso(道具数量, true);
                    }
                    mch.startMapEffect("管理员发放礼物" + 道具数量 + "个" + 名字2 + "给在线的所有玩家！祝您玩得开心快乐", 5121009);
                    ++个数;
                }
            }
        } else {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                final MapleCharacter mch2 = ChannelServer.getInstance(cserv.getChannel()).getPlayerStorage().getCharacterByName(玩家的名字);
                if (mch2 != null) {
                    if (发放类型 == 0) {
                        mch2.modifyCSPoints(1, 道具数量, true);
                    } else if (发放类型 == 1) {
                        mch2.modifyCSPoints(2, 道具数量, true);
                    } else if (发放类型 == 2) {
                        mch2.gainMeso(道具数量, true);
                    }
                    mch2.startMapEffect("管理员发放礼物" + 道具数量 + "个" + 名字2 + "单独给你！祝您玩得开心快乐", 5121009);
                    ++个数;
                }
            }
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]发放" + 名字2 + ":一共发给了" + 个数 + "个玩家");
    }

    public void 发放道具() {
        int 道具代码 = 0;
        if ("输入道具代码".equals(this.发放道具代码.getText())) {
            道具代码 = 4000000;
        } else {
            道具代码 = ((Integer.parseInt(this.发放道具代码.getText()) < 1) ? 1 : Integer.parseInt(this.发放道具代码.getText()));
        }
        int 道具数量 = 0;
        if ("输入数字".equals(this.发放道具数量.getText())) {
            道具数量 = 0;
        } else {
            道具数量 = Integer.parseInt(this.发放道具数量.getText());
        }
        int 发放范围 = 0;
        String 名字 = "";
        String 玩家的名字 = "";
        if ("输入数字".equals(Integer.valueOf(this.发放道具发放范围.getSelectedIndex()))) {
            发放范围 = 0;
        } else {
            发放范围 = this.发放道具发放范围.getSelectedIndex();
            switch (发放范围) {
                case 0: {
                    名字 = "全服";
                    break;
                }
                case 1: {
                    名字 = "个人";
                    玩家的名字 = this.发放个人玩家名字.getText();
                    break;
                }
            }
        }
        final int answer = JOptionPane.showConfirmDialog((Component) this, "当前输入道具代码是:" + 道具代码 + "\r\n当前输入道具数量设置是:" + 道具数量 + "个\r\n当前发放范围设置是:" + 名字 + "" + ((发放范围 == 1) ? ("\r\n当前你选择的角色名字是:" + 玩家的名字 + "\r\n") : "") + "请问您是否要开启呢?\r\n", "发放道具", 0);
        if (answer != 0) {
            return;
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        int 个数 = 0;
        if (发放范围 == 0) {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                    mch.gainItem(道具代码, 道具数量);
                    mch.startMapEffect("管理员发放礼物" + 道具数量 + "个" + ii.getName(道具代码) + "给在线的所有玩家！祝您玩得开心快乐", 5121009);
                    ++个数;
                }
            }
        } else {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                final MapleCharacter mch2 = ChannelServer.getInstance(cserv.getChannel()).getPlayerStorage().getCharacterByName(玩家的名字);
                if (mch2 != null) {
                    mch2.gainItem(道具代码, 道具数量);
                    mch2.startMapEffect("管理员发放礼物" + 道具数量 + "个" + ii.getName(道具代码) + "单独给你！祝您玩得开心快乐", 5121009);
                    ++个数;
                }
            }
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]发放道具:一共发给了" + 个数 + "个玩家:" + 道具数量 + "个" + ii.getName(道具代码) + "");
    }

    public void 野外BOSS修改(final int type) {
        if (LZJMS.野外BOSS刷新时间现地图.getText().length() < 5) {
            JOptionPane.showMessageDialog(null, "错误:五位以下的地图无法添加");
            return;
        }
        if (LZJMS.野外BOSS刷新时间boss代码.getText().length() < 6) {
            JOptionPane.showMessageDialog(null, "错误:怪物id位数不对");
            return;
        }
        if (Integer.valueOf(LZJMS.野外BOSS刷新时间boss代码.getText()).intValue() < 1) {
            JOptionPane.showMessageDialog(null, "错误:你加的这个是啥怪物");
            return;
        }
        if (LZJMS.野外BOSS刷新时间分.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:boss刷新时间不对");
            return;
        }
        if (LZJMS.野外BOSS刷新时间横坐标.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:横坐标输入不对");
            return;
        }
        if (LZJMS.野外BOSS刷新时间纵坐标.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:纵坐标输入不对");
            return;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO bossmobinmap (mobid,map,x,y,msg,time) VALUES(?,?,?,?,?,?)");
                    ps.setInt(1, Integer.valueOf(LZJMS.野外BOSS刷新时间boss代码.getText()).intValue());
                    ps.setInt(2, Integer.valueOf(LZJMS.野外BOSS刷新时间现地图.getText()).intValue());
                    ps.setInt(3, Integer.valueOf(LZJMS.野外BOSS刷新时间横坐标.getText()).intValue());
                    ps.setInt(4, Integer.valueOf(LZJMS.野外BOSS刷新时间纵坐标.getText()).intValue());
                    ps.setString(5, LZJMS.野外BOSS刷新时间说明.getText());
                    ps.setInt(6, Integer.valueOf(LZJMS.野外BOSS刷新时间分.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM bossmobinmap where map = ?");
                    ps.setInt(1, Integer.valueOf(LZJMS.野外BOSS刷新时间原地图.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE bossmobinmap set mobid = ? ,map = ? ,x = ?, y = ?, msg = ?, time = ? where map = ?");
                    ps.setInt(1, Integer.valueOf(LZJMS.野外BOSS刷新时间boss代码.getText()).intValue());
                    ps.setInt(2, Integer.valueOf(LZJMS.野外BOSS刷新时间现地图.getText()).intValue());
                    ps.setInt(3, Integer.valueOf(LZJMS.野外BOSS刷新时间横坐标.getText()).intValue());
                    ps.setInt(4, Integer.valueOf(LZJMS.野外BOSS刷新时间纵坐标.getText()).intValue());
                    ps.setString(5, LZJMS.野外BOSS刷新时间说明.getText());
                    ps.setInt(6, Integer.valueOf(LZJMS.野外BOSS刷新时间分.getText()).intValue());
                    ps.setInt(7, Integer.valueOf(LZJMS.野外BOSS刷新时间原地图.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.重载野外boss刷新列表();
            this.加载野外boss刷新列表();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "野外boss刷新调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]野外boss刷新调整出错：" + ex.getMessage());
        }
    }

    private void 重载野外boss刷新列表() {
        Start.野外boss刷新.clear();
        Start.GetMobInMapTable();
    }

    private void 加载野外boss刷新列表() {
        for (int i = ((DefaultTableModel) LZJMS.野外BOSS刷新时间.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.野外BOSS刷新时间.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.野外boss刷新.size(); ++i) {
            ((DefaultTableModel) LZJMS.野外BOSS刷新时间.getModel()).insertRow(LZJMS.野外BOSS刷新时间.getRowCount(), new Object[]{Integer.valueOf(((BossInMap) Start.野外boss刷新.get(i)).getMobid()), Integer.valueOf(((BossInMap) Start.野外boss刷新.get(i)).getMap()), Integer.valueOf(((BossInMap) Start.野外boss刷新.get(i)).getX()), Integer.valueOf(((BossInMap) Start.野外boss刷新.get(i)).getY()), ((BossInMap) Start.野外boss刷新.get(i)).getMsg(), Integer.valueOf(((BossInMap) Start.野外boss刷新.get(i)).getTime())});
        }
    }

    public void 道具经验加成调整(final int type) {
        if (LZJMS.道具经验加成代码.getText().length() != 7) {
            JOptionPane.showMessageDialog(null, "错误:道具代码是七位数哦");
            return;
        }
        if (LZJMS.道具经验加成比例.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:加成经验最少是1位数哦");
            return;
        }
        if (Integer.valueOf(LZJMS.道具经验加成比例.getText()).intValue() < 1) {
            JOptionPane.showMessageDialog(null, "错误:加成经验最少是1哦");
            return;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO itemexptable (name,numb) VALUES(?,?)");
                    ps.setString(1, LZJMS.道具经验加成代码.getText());
                    ps.setInt(2, Integer.valueOf(LZJMS.道具经验加成比例.getText()).intValue());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM itemexptable where name = ?");
                    ps.setString(1, LZJMS.道具经验加成代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE itemexptable set numb = ? ,name = ?where name = ?");
                    ps.setInt(1, Integer.valueOf(LZJMS.道具经验加成比例.getText()).intValue());
                    ps.setString(2, LZJMS.道具经验加成代码.getText());
                    ps.setString(3, LZJMS.原道具经验加成代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.重载道具经验列表();
            this.加载道具经验列表();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "道具经验加成调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]道具经验加成调整出错：" + ex.getMessage());
        }
    }

    public void 不参与增加调整(final int type) {
        if (LZJMS.不参与道具叠加源代码.getText().length() != 7) {
            JOptionPane.showMessageDialog(null, "错误:道具代码是七位数哦");
            return;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO noslotmax (name) VALUES( ? )");
                    ps.setString(1, LZJMS.不参与道具叠加源代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM noslotmax where name = ?");
                    ps.setString(1, LZJMS.不参与道具叠加源代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.重载不参与道具叠加列表();
            this.加载不参与道具叠加列表();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "不参与增加调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]不参与增加调整出错：" + ex.getMessage());
        }
    }

    public void 子弹调整(final int type) {
        if (LZJMS.子弹代码.getText().length() != 7) {
            JOptionPane.showMessageDialog(null, "错误:道具代码是七位数哦");
            return;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO rechargeableItems (name) VALUES(?)");
                    ps.setString(1, LZJMS.子弹代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM rechargeableItems where name = ?");
                    ps.setString(1, LZJMS.子弹代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.重载子弹列表();
            this.加载子弹列表();
            MapleShop.重载商店();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "子弹调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]子弹调整出错：" + ex.getMessage());
        }
    }

    public void 特殊职业调整(final int type) {
        if (LZJMS.特殊组队经验职业.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:职业代码错误");
            return;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO specialjobtable (name) VALUES(?)");
                    ps.setString(1, LZJMS.特殊组队经验职业.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM specialjobtable where name = ?");
                    ps.setString(1, LZJMS.特殊组队经验职业.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.重载特殊职业列表();
            this.加载特殊职业列表();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "特殊职业列表调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]特殊职业列表调整出错：" + ex.getMessage());
        }
    }

    public void 怪物地图调整(final int type) {
        if (LZJMS.多倍地图现地图代码.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:职业代码错误");
            return;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO mobmaptable (name) VALUES( ?)");
                    ps.setString(1, LZJMS.多倍地图现地图代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM mobmaptable where name = ?");
                    ps.setString(1, LZJMS.多倍地图现地图代码.getText());
                    ps.execute();
                    ps.close();
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE mobmaptable set name = ? where name = ?");
                    ps.setString(1, LZJMS.多倍地图现地图代码.getText());
                    ps.setString(2, LZJMS.多倍地图现地图代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.重载多倍怪物列表();
            this.加载多倍怪物列表();
            重载玩家刷怪信息();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "多倍地图列表调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]多倍地图列表调整出错：" + ex.getMessage());
        }
    }

    private void 重载多倍怪物列表() {
        Start.mobmaptable.clear();
        Start.GetMobMapTable();
    }

    public void 套装伤害加成表调整(final int type) {
        if (LZJMS.现套装代码.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:道具代码错误");
            return;
        }
        if (LZJMS.套装加成比例.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:套装加成比例错误");
            return;
        }
        if (LZJMS.套装编码.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:套装编码错误");
            return;
        }
        if (LZJMS.套装名字.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:套装名字错误");
            return;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO suitdamtable (name,numb,proportion,proname) VALUES(?,?,?,?)");
                    ps.setString(1, LZJMS.现套装代码.getText());
                    ps.setInt(2, Integer.valueOf(LZJMS.套装加成比例.getText()).intValue());
                    ps.setInt(3, Integer.valueOf(LZJMS.套装编码.getText()).intValue());
                    ps.setString(4, LZJMS.套装名字.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM suitdamtable where name = ? AND numb = ? AND proportion = ? AND proname = ? ");
                    ps.setString(1, LZJMS.原套装代码.getText());
                    ps.setInt(2, Integer.valueOf(LZJMS.套装加成比例.getText()).intValue());
                    ps.setInt(3, Integer.valueOf(LZJMS.套装编码.getText()).intValue());
                    ps.setString(4, LZJMS.套装名字.getText());
                    ps.execute();
                    ps.close();
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE suitdamtable set name = ? ,numb = ?,proportion = ? ,proname = ? where name = ?");
                    ps.setString(1, LZJMS.现套装代码.getText());
                    ps.setInt(2, Integer.valueOf(LZJMS.套装加成比例.getText()).intValue());
                    ps.setInt(3, Integer.valueOf(LZJMS.套装编码.getText()).intValue());
                    ps.setString(4, LZJMS.套装名字.getText());
                    ps.setString(5, LZJMS.原套装代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.重载套装加成列表();
            this.加载套装伤害加成表();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "套装伤害加成列表调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]套装伤害加成列表调整出错：" + ex.getMessage());
        }
    }

    public void 宠物吸取不参与地图调整(final int type) {
        if (LZJMS.宠物吸取不参与地图代码.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:地图代码错误");
            return;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO petnomaptable (name) VALUES( ?)");
                    ps.setString(1, LZJMS.宠物吸取不参与地图代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM petnomaptable where name = ?");
                    ps.setString(1, LZJMS.宠物吸取不参与地图代码.getText());
                    ps.execute();
                    ps.close();
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE petnomaptable set name = ? where name = ?");
                    ps.setString(1, LZJMS.宠物吸取不参与地图代码.getText());
                    ps.setString(2, LZJMS.宠物吸取不参与地图代码1.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.重载宠物不参与地图列表();
            this.加载宠物不参与列表();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "宠物不参与地图列表调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]宠物不参与地图列表调整出错：" + ex.getMessage());
        }
    }

    public void 宠物吸金调整(final int type) {
        if (LZJMS.宠物吸金代码.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:宠物代码错误");
            return;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO petmesotable (name) VALUES( ?)");
                    ps.setString(1, LZJMS.宠物吸金代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM petmesotable where name = ?");
                    ps.setString(1, LZJMS.宠物吸金代码.getText());
                    ps.execute();
                    ps.close();
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE petmesotable set name = ? where name = ?");
                    ps.setString(1, LZJMS.宠物吸金代码.getText());
                    ps.setString(2, LZJMS.宠物吸金代码1.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.重载宠物吸金代码列表();
            this.加载宠物吸金列表();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "宠物吸金列表调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]宠物吸金列表调整出错：" + ex.getMessage());
        }
    }

    public void 宠物吸物调整(final int type) {
        if (LZJMS.宠物吸物代码.getText().length() < 1) {
            JOptionPane.showMessageDialog(null, "错误:地图代码错误");
            return;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            switch (type) {
                case 0: {
                    ps = con.prepareStatement("INSERT INTO petitemtable (name) VALUES( ?)");
                    ps.setString(1, LZJMS.宠物吸物代码.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
                case 1: {
                    ps = con.prepareStatement("DELETE FROM petitemtable where name = ?");
                    ps.setString(1, LZJMS.宠物吸物代码.getText());
                    ps.execute();
                    ps.close();
                }
                case 2: {
                    ps = con.prepareStatement("UPDATE petitemtable set name = ? where name = ?");
                    ps.setString(1, LZJMS.宠物吸物代码.getText());
                    ps.setString(2, LZJMS.宠物吸物代码1.getText());
                    ps.execute();
                    ps.close();
                    break;
                }
            }
            this.重载宠物吸物代码列表();
            this.加载宠物吸物列表();
            JOptionPane.showMessageDialog(null, "数据处理完毕！", "宠物吸物列表调整提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]宠物吸物列表调整出错：" + ex.getMessage());
        }
    }

    private void 重载宠物不参与地图列表() {
        Start.宠物不参与地图表.clear();
        Start.GetPetNoMapTable();
    }

    private void 重载套装加成列表() {
        Start.套装加成表.clear();
        Start.GetSuitDamTable();
    }

    private void 重载宠物吸金代码列表() {
        Start.宠物吸金表.clear();
        Start.GetPetMesoTable();
    }

    private void 重载宠物吸物代码列表() {
        Start.宠物吸物表.clear();
        Start.GetPetItemTable();
    }

    public static void 重载玩家刷怪信息() {
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr != null) {
                    chr.getMap().respawn(false);
                }
            }
        }
    }

    private void 重载特殊职业列表() {
        Start.特殊组队经验加成表.clear();
        Start.GetSpecialJobTable();
    }

    private void 加载多倍怪物列表() {
        for (int i = ((DefaultTableModel) LZJMS.多倍怪物地图列表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.多倍怪物地图列表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.mobmaptable.size(); ++i) {
            ((DefaultTableModel) LZJMS.多倍怪物地图列表.getModel()).insertRow(LZJMS.多倍怪物地图列表.getRowCount(), new Object[]{Integer.valueOf(i), Integer.valueOf((String) Start.mobmaptable.get(i))});
        }
    }

    private void 加载宠物不参与列表() {
        for (int i = ((DefaultTableModel) LZJMS.宠物吸取不参与地图列表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.宠物吸取不参与地图列表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.宠物不参与地图表.size(); ++i) {
            ((DefaultTableModel) LZJMS.宠物吸取不参与地图列表.getModel()).insertRow(LZJMS.宠物吸取不参与地图列表.getRowCount(), new Object[]{Integer.valueOf(i), Integer.valueOf((String) Start.宠物不参与地图表.get(i))});
        }
    }

    private void 加载套装伤害加成表() {
        for (int i = ((DefaultTableModel) LZJMS.套装伤害加成表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.套装伤害加成表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.套装加成表.size(); ++i) {
            ((DefaultTableModel) LZJMS.套装伤害加成表.getModel()).insertRow(LZJMS.套装伤害加成表.getRowCount(), new Object[]{(((Start.套装加成表.get(i)).getRight()).getRight()).getLeft(), MapleItemInformationProvider.getInstance().getName(Integer.valueOf((String) (((Start.套装加成表.get(i)).getRight()).getRight()).getLeft()).intValue()), (((Start.套装加成表.get(i)).getRight()).getRight()).getRight(), (Start.套装加成表.get(i)).getLeft(), ((Start.套装加成表.get(i)).getRight()).getLeft()});
        }
    }

    private void 加载宠物吸金列表() {
        for (int i = ((DefaultTableModel) LZJMS.宠物吸金列表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.宠物吸金列表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.宠物吸金表.size(); ++i) {
            ((DefaultTableModel) LZJMS.宠物吸金列表.getModel()).insertRow(LZJMS.宠物吸金列表.getRowCount(), new Object[]{Integer.valueOf(i), Integer.valueOf((String) Start.宠物吸金表.get(i))});
        }
    }

    private void 加载宠物吸物列表() {
        for (int i = ((DefaultTableModel) LZJMS.宠物吸物列表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.宠物吸物列表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.宠物吸物表.size(); ++i) {
            ((DefaultTableModel) LZJMS.宠物吸物列表.getModel()).insertRow(LZJMS.宠物吸物列表.getRowCount(), new Object[]{Integer.valueOf(i), Integer.valueOf((String) Start.宠物吸物表.get(i))});
        }
    }

    private void 加载特殊职业列表() {
        for (int i = ((DefaultTableModel) LZJMS.特殊组队经验加成表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.特殊组队经验加成表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.特殊组队经验加成表.size(); ++i) {
            ((DefaultTableModel) LZJMS.特殊组队经验加成表.getModel()).insertRow(LZJMS.特殊组队经验加成表.getRowCount(), new Object[]{Integer.valueOf(i), Integer.valueOf((String) Start.特殊组队经验加成表.get(i))});
        }
    }

    private void 重载子弹列表() {
        Start.子弹列表.clear();
        Start.GetRechargeTable();
    }

    private void 加载子弹列表() {
        for (int i = ((DefaultTableModel) LZJMS.子弹扩充列表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.子弹扩充列表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.子弹列表.size(); ++i) {
            ((DefaultTableModel) LZJMS.子弹扩充列表.getModel()).insertRow(LZJMS.子弹扩充列表.getRowCount(), new Object[]{Integer.valueOf(i), Integer.valueOf((String) Start.子弹列表.get(i))});
        }
    }

    private void 加载不参与道具叠加列表() {
        for (int i = ((DefaultTableModel) LZJMS.不参与叠加道具.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.不参与叠加道具.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.不参与叠加道具.size(); ++i) {
            ((DefaultTableModel) LZJMS.不参与叠加道具.getModel()).insertRow(LZJMS.不参与叠加道具.getRowCount(), new Object[]{Integer.valueOf(i), Integer.valueOf((String) Start.不参与叠加道具.get(i))});
        }
    }

    private void 重载不参与道具叠加列表() {
        Start.不参与叠加道具.clear();
        Start.GetNoSlotMaxTable();
    }

    private void 重载道具经验列表() {
        Start.经验加成表.clear();
        Start.GetItemExpTable();
    }

    private void 加载道具经验列表() {
        for (int i = ((DefaultTableModel) LZJMS.经验加成表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.经验加成表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.经验加成表.size(); ++i) {
            ((DefaultTableModel) LZJMS.经验加成表.getModel()).insertRow(LZJMS.经验加成表.getRowCount(), new Object[]{Integer.valueOf((String) (Start.经验加成表.get(i)).getLeft()), (Start.经验加成表.get(i)).getRight()});
        }
    }

    public static void 玩家控制台() {
        if (LZJMS.玩家控制台 != null) {
            LZJMS.玩家控制台.dispose();
        }
        (LZJMS.玩家控制台 = new 玩家控制台()).setVisible(true);
    }

    public static void 特殊控制台() {
        if (LZJMS.特殊控制台 != null) {
            LZJMS.特殊控制台.dispose();
        }
        (LZJMS.特殊控制台 = new 特殊控制台()).setVisible(true);
    }

    public void 清空文档(final JTextArea mainComponent) {
        final int n = JOptionPane.showConfirmDialog((Component) this, "你确定要清空当前所有的日志消息么？", "信息", 0);
        if (n != 0) {
            return;
        }
        LzjStream.cleanMessage(LZJMS.outSee);
    }

    public void 保存数据() {
        final int n = JOptionPane.showConfirmDialog((Component) this, "需要保存所有在线角色数据吗？", "信息", 0);
        if (n == 0) {
            int p = 0;
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    ++p;
                    chr.saveToDB(true, true);
                }
            }
            JOptionPane.showMessageDialog(null, "保存" + p + "个在线角色和雇佣商人成功!");
        }
    }

    public void 重载全部() {
        final int n = JOptionPane.showConfirmDialog((Component) this, "你需要重载全部配置吗？", "信息", 0);
        if (n == 0) {
            MapleShopFactory.getInstance().clear();
            ReactorScriptManager.getInstance().clearDrops();
            MapleMonsterInformationProvider.getInstance().clearDrops();
            PortalScriptManager.getInstance().clearScripts();
            Start.GetConfigValues();
            for (final ChannelServer instance1 : ChannelServer.getAllInstances()) {
                if (instance1 != null) {
                    instance1.reloadEvents();
                }
            }
            JOptionPane.showMessageDialog(null, "重载完成。");
        }
    }

    public void 保存ini配置(final int type) {
        switch (type) {
            case 0: {
                ServerProperties.setProperty("Lzj.DBName", LZJMS.数据库名称.getText());
                break;
            }
            case 1: {
                ServerProperties.setProperty("Lzj.SQLAddress", LZJMS.数据库IP地址.getText());
                break;
            }
            case 2: {
                ServerProperties.setProperty("Lzj.SQLPort", LZJMS.数据库端口.getText());
                break;
            }
            case 3: {
                ServerProperties.setProperty("Lzj.SQLUser", LZJMS.数据库用户名.getText());
                break;
            }
            case 4: {
                ServerProperties.setProperty("Lzj.SQLPassword", LZJMS.数据库密码.getText());
                break;
            }
            case 5: {
                ServerProperties.setProperty("Lzj.LoginPort", LZJMS.服务器登录端口.getText());
                break;
            }
            case 6: {
                ServerProperties.setProperty("Lzj.CashPort", LZJMS.服务器商城端口.getText());
                break;
            }
            case 7: {
                ServerProperties.setProperty("Lzj.ChannelPort", LZJMS.服务器频道端口.getText());
                break;
            }
            case 8: {
                ServerProperties.setProperty("Lzj.Count", LZJMS.服务器频道数量.getText());
                break;
            }
            case 9: {
                ServerProperties.setProperty("Lzj.ServerName", LZJMS.服务器名字.getText());
                break;
            }
            case 10: {
                ServerProperties.setProperty("Lzj.Exp", LZJMS.服务器经验倍率.getText());
                break;
            }
            case 11: {
                ServerProperties.setProperty("Lzj.Meso", LZJMS.服务器金币倍率.getText());
                break;
            }
            case 12: {
                ServerProperties.setProperty("Lzj.Drop", LZJMS.服务器掉落倍率.getText());
                break;
            }
            case 13: {
                ServerProperties.setProperty("Lzj.DDChannel", LZJMS.服务器双倍频道.getText());
                break;
            }
            case 14: {
                ServerProperties.setProperty("Lzj.DD", LZJMS.双倍频道开关.isSelected() ? "1" : "0");
                break;
            }
        }
    }

    private void 关闭服务端() {
        LZJMS.主界面菜单.setSelectedIndex(0);
        LZJMS.第二控制台.setEnabled(false);
        LZJMS.第三控制台.setEnabled(false);
        LZJMS.清空日志.setEnabled(false);
        LZJMS.重载全部.setEnabled(false);
        LZJMS.关闭时间显示.setEnabled(false);
        LZJMS.关闭时间输入.setEnabled(false);
        LZJMS.保存雇佣.setEnabled(false);
        LZJMS.保存数据.setEnabled(false);
        LZJMS.主界面菜单.setEnabled(false);
        try {
            this.minutesLeft = Integer.parseInt(LZJMS.关闭时间输入.getText());
            if (ts == null && (t == null || !t.isAlive())) {
                t = new Thread(ShutdownServer.getInstance());
                ts = EventTimer.getInstance().register(new Runnable() {
                    @Override
                    public void run() {
                        if (minutesLeft == 0) {
                            ShutdownServer.getInstance();
                            t.start();
                            ts.cancel(false);
                            return;
                        }
                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "本服将在 " + LZJMS.this.minutesLeft + "分钟后关闭. 请尽快回收店铺并且下线."));
                        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:本服将在 " + LZJMS.this.minutesLeft + "分钟后关闭.");
                        minutesLeft--;
                    }
                }, 60000);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + e);
        }
    }

    public void 初始化界面配置() {
        this.发放道具发放范围.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.this.发放道具发放范围.getSelectedIndex();
                if (i == 0) {
                    LZJMS.this.发放个人玩家名字.setEnabled(false);
                } else {
                    LZJMS.this.发放个人玩家名字.setEnabled(true);
                }
            }
        });
        this.发放其他范围.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.this.发放其他范围.getSelectedIndex();
                if (i == 0) {
                    LZJMS.this.发放其他玩家.setEnabled(false);
                } else {
                    LZJMS.this.发放其他玩家.setEnabled(true);
                }
            }
        });
        this.职业技能列表表单.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.this.职业技能列表表单.getSelectedRow();
                LZJMS.this.技能代码.setText(LZJMS.this.职业技能列表表单.getValueAt(i, 0).toString());
                LZJMS.this.技能名称.setText(LZJMS.this.职业技能列表表单.getValueAt(i, 1).toString());
                LZJMS.this.技能段数.setText(LZJMS.this.职业技能列表表单.getValueAt(i, 2).toString());
                LZJMS.this.怪物个数.setText(LZJMS.this.职业技能列表表单.getValueAt(i, 3).toString());
            }
        });
        LZJMS.套装伤害加成表.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.套装伤害加成表.getSelectedRow();
                LZJMS.原套装代码.setText(LZJMS.套装伤害加成表.getValueAt(i, 0).toString());
                LZJMS.现套装代码.setText(LZJMS.套装伤害加成表.getValueAt(i, 0).toString());
                LZJMS.套装道具代码.setText(LZJMS.套装伤害加成表.getValueAt(i, 1).toString());
                LZJMS.套装加成比例.setText(LZJMS.套装伤害加成表.getValueAt(i, 2).toString());
                LZJMS.套装编码.setText(LZJMS.套装伤害加成表.getValueAt(i, 3).toString());
                LZJMS.套装名字.setText(LZJMS.套装伤害加成表.getValueAt(i, 4).toString());
            }
        });
        this.充值赞助列表.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.this.充值赞助列表.getSelectedRow();
                LZJMS.this.充值角色ID.setText(LZJMS.this.充值赞助列表.getValueAt(i, 0).toString());
                LZJMS.this.充值玩家名字.setText(LZJMS.this.充值赞助列表.getValueAt(i, 1).toString());
                LZJMS.this.现有每日充值.setText(LZJMS.this.充值赞助列表.getValueAt(i, 2).toString());
                LZJMS.this.现有累计充值.setText(LZJMS.this.充值赞助列表.getValueAt(i, 3).toString());
                LZJMS.this.现有剩余积分.setText(LZJMS.this.充值赞助列表.getValueAt(i, 4).toString());
                LZJMS.this.现有可用点券.setText(LZJMS.this.充值赞助列表.getValueAt(i, 5).toString());
                LZJMS.this.氪金同账号查询(Integer.valueOf(LZJMS.this.充值赞助列表.getValueAt(i, 0).toString()).intValue());
            }
        });
        LZJMS.多倍怪物地图列表.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.多倍怪物地图列表.getSelectedRow();
                LZJMS.多倍地图排序.setText(LZJMS.多倍怪物地图列表.getValueAt(i, 0).toString());
                LZJMS.多倍地图原地图代码.setText(LZJMS.多倍怪物地图列表.getValueAt(i, 1).toString());
                LZJMS.多倍地图现地图代码.setText(LZJMS.多倍怪物地图列表.getValueAt(i, 1).toString());
            }
        });
        LZJMS.宠物吸取不参与地图列表.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.宠物吸取不参与地图列表.getSelectedRow();
                LZJMS.宠物吸取道具不参与排序.setText(LZJMS.宠物吸取不参与地图列表.getValueAt(i, 0).toString());
                LZJMS.宠物吸取不参与地图代码1.setText(LZJMS.宠物吸取不参与地图列表.getValueAt(i, 1).toString());
                LZJMS.宠物吸取不参与地图代码.setText(LZJMS.宠物吸取不参与地图列表.getValueAt(i, 1).toString());
            }
        });
        LZJMS.宠物吸物列表.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.宠物吸物列表.getSelectedRow();
                LZJMS.宠物吸物排序.setText(LZJMS.宠物吸物列表.getValueAt(i, 0).toString());
                LZJMS.宠物吸物代码1.setText(LZJMS.宠物吸物列表.getValueAt(i, 1).toString());
                LZJMS.宠物吸物代码.setText(LZJMS.宠物吸物列表.getValueAt(i, 1).toString());
            }
        });
        LZJMS.宠物吸金列表.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.宠物吸金列表.getSelectedRow();
                LZJMS.宠物吸金排序.setText(LZJMS.宠物吸金列表.getValueAt(i, 0).toString());
                LZJMS.宠物吸金代码1.setText(LZJMS.宠物吸金列表.getValueAt(i, 1).toString());
                LZJMS.宠物吸金代码.setText(LZJMS.宠物吸金列表.getValueAt(i, 1).toString());
            }
        });
        LZJMS.不参与叠加道具.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.不参与叠加道具.getSelectedRow();
                LZJMS.不参与叠加排序.setText(LZJMS.不参与叠加道具.getValueAt(i, 0).toString());
                LZJMS.不参与道具叠加源代码.setText(LZJMS.不参与叠加道具.getValueAt(i, 1).toString());
            }
        });
        LZJMS.子弹扩充列表.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.子弹扩充列表.getSelectedRow();
                LZJMS.子弹排序.setText(LZJMS.子弹扩充列表.getValueAt(i, 0).toString());
                LZJMS.子弹代码.setText(LZJMS.子弹扩充列表.getValueAt(i, 1).toString());
            }
        });
        LZJMS.特殊组队经验加成表.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.特殊组队经验加成表.getSelectedRow();
                LZJMS.特殊组队经验排序.setText(LZJMS.特殊组队经验加成表.getValueAt(i, 0).toString());
                LZJMS.特殊组队经验职业.setText(LZJMS.特殊组队经验加成表.getValueAt(i, 1).toString());
            }
        });
        LZJMS.经验加成表.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.经验加成表.getSelectedRow();
                LZJMS.原道具经验加成代码.setText(LZJMS.经验加成表.getValueAt(i, 0).toString());
                LZJMS.道具经验加成代码.setText(LZJMS.经验加成表.getValueAt(i, 0).toString());
                LZJMS.道具经验加成比例.setText(LZJMS.经验加成表.getValueAt(i, 1).toString());
            }
        });
        LZJMS.野外BOSS刷新时间.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int i = LZJMS.野外BOSS刷新时间.getSelectedRow();
                LZJMS.野外BOSS刷新时间boss代码.setText(LZJMS.野外BOSS刷新时间.getValueAt(i, 0).toString());
                LZJMS.野外BOSS刷新时间原地图.setText(LZJMS.野外BOSS刷新时间.getValueAt(i, 1).toString());
                LZJMS.野外BOSS刷新时间现地图.setText(LZJMS.野外BOSS刷新时间.getValueAt(i, 1).toString());
                LZJMS.野外BOSS刷新时间横坐标.setText(LZJMS.野外BOSS刷新时间.getValueAt(i, 2).toString());
                LZJMS.野外BOSS刷新时间纵坐标.setText(LZJMS.野外BOSS刷新时间.getValueAt(i, 3).toString());
                LZJMS.野外BOSS刷新时间说明.setText(LZJMS.野外BOSS刷新时间.getValueAt(i, 4).toString());
                LZJMS.野外BOSS刷新时间分.setText(LZJMS.野外BOSS刷新时间.getValueAt(i, 5).toString());
            }
        });
        final ButtonGroup g4 = new ButtonGroup();
        g4.add((AbstractButton) LZJMS.蓝蜗牛);
        g4.add((AbstractButton) LZJMS.蘑菇仔);
        g4.add((AbstractButton) LZJMS.绿水灵);
        g4.add((AbstractButton) LZJMS.漂漂猪);
        g4.add((AbstractButton) LZJMS.小青蛇);
        g4.add((AbstractButton) LZJMS.红螃蟹);
        g4.add((AbstractButton) LZJMS.大海龟);
        g4.add((AbstractButton) LZJMS.章鱼怪);
        g4.add((AbstractButton) LZJMS.顽皮猴);
        g4.add((AbstractButton) LZJMS.星精灵);
        g4.add((AbstractButton) LZJMS.胖企鹅);
        g4.add((AbstractButton) LZJMS.白雪人);
        g4.add((AbstractButton) LZJMS.石头人);
        g4.add((AbstractButton) LZJMS.紫色猫);
        g4.add((AbstractButton) LZJMS.大灰狼);
        g4.add((AbstractButton) LZJMS.小白兔);
        g4.add((AbstractButton) LZJMS.喷火龙);
        g4.add((AbstractButton) LZJMS.火野猪);
        g4.add((AbstractButton) LZJMS.青鳄鱼);
        g4.add((AbstractButton) LZJMS.花蘑菇);
    }

    private static String testConnect(final String path, final String params) {
        InputStream in = null;
        HttpURLConnection conn = null;
        String msg = "";
        try {
            final URL url = new URL(path + params);
            conn = (HttpURLConnection) url.openConnection();
            in = conn.getInputStream();
            final InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
            final BufferedReader br = new BufferedReader((Reader) isr);
            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            isr.close();
            in.close();
            msg = sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                conn.disconnect();
            }
        }
        return msg;
    }

    /*private static boolean checkAuth() throws UnsupportedEncodingException {
        final String ip = getLocalIP() + "";
        final String code = LZJMS.authCode;
        if (code == null || code.equals("")) {
            System.out.println("请先填写启动码......");
            return false;
        }
        final String path = "http://47.99.141.130:7777/auth/checkAuth?";
        final String params = "ip=" + URLEncoder.encode(ip, StandardCharsets.UTF_8.name()) + "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8.name());
        final String s = testConnect(path, params);
        if (s != null) {
            System.out.println(s);
            if (s.contains((CharSequence)"授权有效")) {
                return true;
            }
        }
        return false;
    }*/

    public static String getLocalIP() {
        String ipAddrStr = "";
        byte[] ipAddr;
        try {
            ipAddr = InetAddress.getLocalHost().getAddress();
        } catch (UnknownHostException e) {
            return null;
        }
        for (int i = 0; i < ipAddr.length; ++i) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += (ipAddr[i] & 0xFF);
        }
        return ipAddrStr;
    }

    public static void 配置同步到界面() {
        LZJMS.数据库名称.setText(Game.数据库名字);
        LZJMS.数据库IP地址.setText(Game.数据库IP);
        LZJMS.数据库端口.setText(Game.数据库端口);
        LZJMS.数据库用户名.setText(Game.数据库用户名);
        LZJMS.数据库密码.setText(Game.数据库密码);
        LZJMS.服务器登录端口.setText(String.valueOf((int) Game.服务端端口));
        LZJMS.服务器商城端口.setText(String.valueOf((int) Game.商城端口));
        LZJMS.服务器频道端口.setText(String.valueOf(Game.频道端口));
        LZJMS.服务器频道数量.setText(String.valueOf(Game.频道个数));
        LZJMS.服务器名字.setText(String.valueOf(Game.服务端名字));
        LZJMS.无限BUFF.setSelected(((Integer) Start.ConfigValuesMap.get("无限BUFF")).intValue() > 0);
        LZJMS.登录自动注册开关.setSelected(((Integer) Start.ConfigValuesMap.get("登录自动注册开关")).intValue() > 0);
        LZJMS.冒险家开关.setSelected(((Integer) Start.ConfigValuesMap.get("允许创建冒险家")).intValue() > 0);
        LZJMS.战神开关.setSelected(((Integer) Start.ConfigValuesMap.get("允许创建战神")).intValue() > 0);
        LZJMS.骑士团开关.setSelected(((Integer) Start.ConfigValuesMap.get("允许创建骑士团")).intValue() > 0);
        LZJMS.等级上限.setText(String.valueOf(Start.ConfigValuesMap.get("角色最大等级")));
        LZJMS.BOSS出装备概率.setText(String.valueOf(Start.ConfigValuesMap.get("BOSS出装备概率")));
        LZJMS.世界等级范围.setText(String.valueOf(Start.世界等级));
        LZJMS.点券比例.setText(String.valueOf(Start.ConfigValuesMap.get("点券比例")));
        LZJMS.服务器经验倍率.setText(String.valueOf(Game.经验倍率));
        LZJMS.服务器金币倍率.setText(String.valueOf(Game.金币爆率));
        LZJMS.服务器掉落倍率.setText(String.valueOf(Game.物品爆率));
        LZJMS.双倍频道开关.setSelected(Game.双倍频道开关.booleanValue());
        LZJMS.服务器双倍频道.setText(String.valueOf(Game.双倍频道));
        LZJMS.玩家升级喇叭.setSelected(((Integer) Start.ConfigValuesMap.get("玩家升级喇叭")).intValue() > 0);
        LZJMS.同IP多开.setSelected(((Integer) Start.ConfigValuesMap.get("同IP多开")).intValue() > 0);
        LZJMS.同MAC多开.setSelected(((Integer) Start.ConfigValuesMap.get("同MAC多开")).intValue() > 0);
        LZJMS.IP多开上限.setText(String.valueOf(Start.ConfigValuesMap.get("IP多开上限")));
        LZJMS.MAC多开上限.setText(String.valueOf(Start.ConfigValuesMap.get("MAC多开上限")));
        LZJMS.MAC注册上限.setText(String.valueOf(Start.ConfigValuesMap.get("MAC注册上限")));
        LZJMS.账号角色上限.setText(String.valueOf(Start.ConfigValuesMap.get("账号角色上限")));
        LZJMS.套装个数.setText(String.valueOf(Start.ConfigValuesMap.get("套装个数")));
        LZJMS.雇佣自动回收.setSelected(((Integer) Start.ConfigValuesMap.get("雇佣自动回收")).intValue() > 0);
        LZJMS.雇佣持续时间.setText(String.valueOf(Start.ConfigValuesMap.get("雇佣持续时间")));
        LZJMS.阶段经验开关.setSelected(((Integer) Start.ConfigValuesMap.get("阶段经验开关")).intValue() > 0);
        LZJMS.泡点开关.setSelected(((Integer) Start.ConfigValuesMap.get("泡点开关")).intValue() > 0);
        LZJMS.泡点等级开关.setSelected(((Integer) Start.ConfigValuesMap.get("泡点等级开关")).intValue() > 0);
        LZJMS.离线泡点开关.setSelected(((Integer) Start.ConfigValuesMap.get("离线泡点开关")).intValue() > 0);
        LZJMS.离线泡点等级开关.setSelected(((Integer) Start.ConfigValuesMap.get("离线泡点等级开关")).intValue() > 0);
        LZJMS.离线给在线时间开关.setSelected(((Integer) Start.ConfigValuesMap.get("离线给在线时间开关")).intValue() > 0);
        LZJMS.怪物多倍地图开关.setSelected(((Integer) Start.ConfigValuesMap.get("怪物多倍地图开关")).intValue() > 0);
        LZJMS.怪物地图多倍怪物开关.setSelected(((Integer) Start.ConfigValuesMap.get("怪物地图多倍怪物开关")).intValue() > 0);
        LZJMS.多倍地图倍率.setText(String.valueOf(Start.ConfigValuesMap.get("怪物多倍地图倍率")));
        LZJMS.怪物刷新频率设定.setText(String.valueOf(Start.ConfigValuesMap.get("怪物刷新频率设定")));
        LZJMS.地图刷新频率.setText(String.valueOf(Start.ConfigValuesMap.get("地图刷新频率")));
        LZJMS.爆物上线开关.setSelected(((Integer) Start.ConfigValuesMap.get("爆物上线开关")).intValue() > 0);
        LZJMS.等级连升开关.setSelected(((Integer) Start.ConfigValuesMap.get("等级连升开关")).intValue() > 0);
        LZJMS.boss击杀记录.setSelected(((Integer) Start.ConfigValuesMap.get("boss击杀记录")).intValue() > 0);
        LZJMS.爆物上线数量.setText(String.valueOf(Start.ConfigValuesMap.get("爆物上线数量")));
        LZJMS.等级范围.setText(String.valueOf(Start.ConfigValuesMap.get("等级范围")));
        LZJMS.上线喇叭.setSelected(((Integer) Start.ConfigValuesMap.get("上线喇叭")).intValue() > 0);
        LZJMS.升级群消息通知.setSelected(((Integer) Start.ConfigValuesMap.get("升级群消息通知")).intValue() > 0);
        LZJMS.雇佣自动回收.setSelected(((Integer) Start.ConfigValuesMap.get("雇佣自动回收")).intValue() > 0);
        LZJMS.雇佣持续时间.setText(String.valueOf(Start.ConfigValuesMap.get("雇佣持续时间")));
        LZJMS.公告间隔时间.setText(String.valueOf(Start.ConfigValuesMap.get("公告间隔时间")));
        LZJMS.雇佣经验加成开关.setSelected(((Integer) Start.ConfigValuesMap.get("雇佣经验加成开关")).intValue() > 0);
        LZJMS.经验加成比例.setText(String.valueOf(Start.ConfigValuesMap.get("雇佣经验加成比例")));
        LZJMS.击杀boss打开npc.setSelected(((Integer) Start.ConfigValuesMap.get("击杀boss打开npc")).intValue() > 0);
        LZJMS.自定义箱子代码.setSelected(((Integer) Start.ConfigValuesMap.get("自定义箱子代码")).intValue() > 0);
        LZJMS.所有显示开关.setSelected(((Integer) Start.ConfigValuesMap.get("所有显示开关")).intValue() > 0);
        LZJMS.表单卡破功开关.setSelected(((Integer) Start.ConfigValuesMap.get("表单卡破功开关")).intValue() > 0);
        LZJMS.伤害突破开关.setSelected(((Integer) Start.ConfigValuesMap.get("伤害突破开关")).intValue() > 0);
        LZJMS.装备卡破功开关.setSelected(((Integer) Start.ConfigValuesMap.get("装备卡破功开关")).intValue() > 0);
        LZJMS.怪物血量显示开关.setSelected(((Integer) Start.ConfigValuesMap.get("怪物血量显示开关")).intValue() > 0);
        LZJMS.怪物减伤开关.setSelected(((Integer) Start.ConfigValuesMap.get("怪物减伤开关")).intValue() > 0);
        LZJMS.世界等级开关.setSelected(((Integer) Start.ConfigValuesMap.get("世界等级开关")).intValue() > 0);
        LZJMS.成就还原上卷记录开关.setSelected(((Integer) Start.ConfigValuesMap.get("成就还原上卷记录开关")).intValue() > 0);
        LZJMS.成就上卷加七记录开关.setSelected(((Integer) Start.ConfigValuesMap.get("成就上卷加七记录开关")).intValue() > 0);
        LZJMS.成就上卷加三记录开关.setSelected(((Integer) Start.ConfigValuesMap.get("成就上卷加三记录开关")).intValue() > 0);
        LZJMS.野外boss击杀广播.setSelected(((Integer) Start.ConfigValuesMap.get("野外boss击杀广播")).intValue() > 0);
        LZJMS.金币全局砍数量.setSelected(((Integer) Start.ConfigValuesMap.get("金币全局砍数量")).intValue() > 0);
        LZJMS.金币砍全局倍率.setText(String.valueOf(Start.ConfigValuesMap.get("金币砍全局倍率")));
        LZJMS.特殊宠物吸取开关.setSelected(((Integer) Start.ConfigValuesMap.get("特殊宠物吸取开关")).intValue() > 0);
        LZJMS.特殊宠物吸物开关.setSelected(((Integer) Start.ConfigValuesMap.get("特殊宠物吸物开关")).intValue() > 0);
        LZJMS.特殊宠物吸金开关.setSelected(((Integer) Start.ConfigValuesMap.get("特殊宠物吸金开关")).intValue() > 0);
        LZJMS.特殊宠物吸物无法使用地图开关.setSelected(((Integer) Start.ConfigValuesMap.get("特殊宠物吸物无法使用地图开关")).intValue() > 0);
        LZJMS.道具强行宠吸开关.setSelected(((Integer) Start.ConfigValuesMap.get("道具强行宠吸开关")).intValue() > 0);
        LZJMS.宠吸道具.setText(String.valueOf(Start.ConfigValuesMap.get("宠吸道具")));
        LZJMS.克隆基础伤害.setText(String.valueOf(Start.ConfigValuesMap.get("克隆基础伤害")));
        LZJMS.越级带人开关.setSelected(((Integer) Start.ConfigValuesMap.get("越级带人开关")).intValue() > 0);
        LZJMS.越级带人道具开关.setSelected(((Integer) Start.ConfigValuesMap.get("越级带人道具开关")).intValue() > 0);
        LZJMS.越级带人道具.setText(String.valueOf(Start.ConfigValuesMap.get("越级带人道具")));
        LZJMS.修正队员分配经验.setText(String.valueOf(Start.ConfigValuesMap.get("修正队员分配经验")));
        LZJMS.金币重置.setSelected(((Integer) Start.ConfigValuesMap.get("金币重置")).intValue() > 0);
        LZJMS.战力修正.setSelected(((Integer) Start.ConfigValuesMap.get("战力修正")).intValue() > 0);
        LZJMS.宠物自动吃药开关.setSelected(((Integer) Start.ConfigValuesMap.get("宠物自动吃药开关")).intValue() > 0);
        LZJMS.坐骑恢复开关.setSelected(((Integer) Start.ConfigValuesMap.get("坐骑恢复开关")).intValue() > 0);
        LZJMS.叠加开关.setSelected(((Integer) Start.ConfigValuesMap.get("叠加开关")).intValue() > 0);
        LZJMS.不参与叠加开关.setSelected(((Integer) Start.ConfigValuesMap.get("不参与叠加开关")).intValue() > 0);
        LZJMS.道具经验开关.setSelected(((Integer) Start.ConfigValuesMap.get("道具经验开关")).intValue() > 0);
        LZJMS.特殊组队经验加成.setSelected(((Integer) Start.ConfigValuesMap.get("特殊组队经验加成")).intValue() > 0);
        LZJMS.特殊全宠物吸物开关.setSelected(((Integer) Start.ConfigValuesMap.get("特殊全宠物吸物开关")).intValue() > 0);
        LZJMS.特殊全宠物吸金开关.setSelected(((Integer) Start.ConfigValuesMap.get("特殊全宠物吸金开关")).intValue() > 0);
        LZJMS.宠物不饥饿开关.setSelected(((Integer) Start.ConfigValuesMap.get("宠物不饥饿开关")).intValue() > 0);
        LZJMS.坐骑不饥饿开关.setSelected(((Integer) Start.ConfigValuesMap.get("坐骑不饥饿开关")).intValue() > 0);
        LZJMS.自动刷钱道具.setText(String.valueOf(Start.ConfigValuesMap.get("自动刷钱道具")));
        LZJMS.金锤子使用开关.setSelected(((Integer) Start.ConfigValuesMap.get("金锤子使用开关")).intValue() > 0);
        LZJMS.金锤子使用概率.setText(String.valueOf(Start.ConfigValuesMap.get("金锤子使用概率")));
        LZJMS.GM固伤开关.setSelected(((Integer) Start.ConfigValuesMap.get("GM固伤开关")).intValue() > 0);
        LZJMS.GM固伤伤害.setText(String.valueOf(Start.ConfigValuesMap.get("GM固伤伤害")));
        LZJMS.套装属性加成开关.setSelected(((Integer) Start.ConfigValuesMap.get("套装属性加成开关")).intValue() > 0);
        LZJMS.叠加上线.setText(String.valueOf(Start.ConfigValuesMap.get("叠加上线")));
        LZJMS.突破上线.setText(String.valueOf(Start.ConfigValuesMap.get("突破上线")));
        LZJMS.砍爆率.setText(String.valueOf(Start.ConfigValuesMap.get("砍爆率")));
        LZJMS.出装备概率.setText(String.valueOf(Start.ConfigValuesMap.get("出装备概率")));
        LZJMS.自动吃药道具.setText(String.valueOf(Start.ConfigValuesMap.get("自动吃药道具")));
        LZJMS.坐骑恢复频率.setText(String.valueOf(Start.ConfigValuesMap.get("坐骑恢复频率")));
        LZJMS.坐骑恢复道具.setText(String.valueOf(Start.ConfigValuesMap.get("坐骑恢复道具")));
        LZJMS.原始组队经验加成.setText(String.valueOf(Start.ConfigValuesMap.get("原始组队经验加成")));
        LZJMS.修正组队经验加成.setText(String.valueOf(Start.ConfigValuesMap.get("修正组队经验加成")));
        for (int i = ((DefaultTableModel) LZJMS.阶段经验配置表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.阶段经验配置表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.exptable.size(); ++i) {
            ((DefaultTableModel) LZJMS.阶段经验配置表.getModel()).insertRow(LZJMS.阶段经验配置表.getRowCount(), new Object[]{Integer.valueOf((String) (Start.exptable.get(i)).getLeft()), (Start.exptable.get(i)).getRight()});
        }
        for (int i = ((DefaultTableModel) LZJMS.不参与叠加道具.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.不参与叠加道具.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.不参与叠加道具.size(); ++i) {
            ((DefaultTableModel) LZJMS.不参与叠加道具.getModel()).insertRow(LZJMS.不参与叠加道具.getRowCount(), new Object[]{Integer.valueOf(i), Integer.valueOf((String) Start.不参与叠加道具.get(i))});
        }
        for (int i = ((DefaultTableModel) LZJMS.子弹扩充列表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.子弹扩充列表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.子弹列表.size(); ++i) {
            ((DefaultTableModel) LZJMS.子弹扩充列表.getModel()).insertRow(LZJMS.子弹扩充列表.getRowCount(), new Object[]{Integer.valueOf(i), Integer.valueOf((String) Start.子弹列表.get(i))});
        }
        for (int i = ((DefaultTableModel) LZJMS.特殊组队经验加成表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.特殊组队经验加成表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.特殊组队经验加成表.size(); ++i) {
            ((DefaultTableModel) LZJMS.特殊组队经验加成表.getModel()).insertRow(LZJMS.特殊组队经验加成表.getRowCount(), new Object[]{Integer.valueOf(i), Integer.valueOf((String) Start.特殊组队经验加成表.get(i))});
        }
        for (int i = ((DefaultTableModel) LZJMS.泡点配置表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.泡点配置表.getModel()).removeRow(i);
        }
        ((DefaultTableModel) LZJMS.泡点配置表.getModel()).insertRow(LZJMS.泡点配置表.getRowCount(), new Object[]{"金币", String.valueOf(Start.ConfigValuesMap.get("泡点金币"))});
        ((DefaultTableModel) LZJMS.泡点配置表.getModel()).insertRow(LZJMS.泡点配置表.getRowCount(), new Object[]{"经验", String.valueOf(Start.ConfigValuesMap.get("泡点经验"))});
        ((DefaultTableModel) LZJMS.泡点配置表.getModel()).insertRow(LZJMS.泡点配置表.getRowCount(), new Object[]{"抵用", String.valueOf(Start.ConfigValuesMap.get("泡点抵用"))});
        ((DefaultTableModel) LZJMS.泡点配置表.getModel()).insertRow(LZJMS.泡点配置表.getRowCount(), new Object[]{"点券", String.valueOf(Start.ConfigValuesMap.get("泡点点券"))});
        ((DefaultTableModel) LZJMS.泡点配置表.getModel()).insertRow(LZJMS.泡点配置表.getRowCount(), new Object[]{"泡点地图", String.valueOf(Start.ConfigValuesMap.get("泡点地图"))});
        for (int i = ((DefaultTableModel) LZJMS.离线泡点配置表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.离线泡点配置表.getModel()).removeRow(i);
        }
        ((DefaultTableModel) LZJMS.离线泡点配置表.getModel()).insertRow(LZJMS.离线泡点配置表.getRowCount(), new Object[]{"金币", String.valueOf(Start.ConfigValuesMap.get("离线泡点金币"))});
        ((DefaultTableModel) LZJMS.离线泡点配置表.getModel()).insertRow(LZJMS.离线泡点配置表.getRowCount(), new Object[]{"经验", String.valueOf(Start.ConfigValuesMap.get("离线泡点经验"))});
        ((DefaultTableModel) LZJMS.离线泡点配置表.getModel()).insertRow(LZJMS.离线泡点配置表.getRowCount(), new Object[]{"抵用", String.valueOf(Start.ConfigValuesMap.get("离线泡点抵用"))});
        ((DefaultTableModel) LZJMS.离线泡点配置表.getModel()).insertRow(LZJMS.离线泡点配置表.getRowCount(), new Object[]{"点券", String.valueOf(Start.ConfigValuesMap.get("离线泡点点券"))});
        ((DefaultTableModel) LZJMS.离线泡点配置表.getModel()).insertRow(LZJMS.离线泡点配置表.getRowCount(), new Object[]{"泡点地图", String.valueOf(Start.ConfigValuesMap.get("离线泡点地图"))});
        for (int i = ((DefaultTableModel) LZJMS.经验加成表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) LZJMS.经验加成表.getModel()).removeRow(i);
        }
        for (int i = 0; i < Start.经验加成表.size(); ++i) {
            ((DefaultTableModel) LZJMS.经验加成表.getModel()).insertRow(LZJMS.经验加成表.getRowCount(), new Object[]{Integer.valueOf((String) (Start.经验加成表.get(i)).getLeft()), (Start.经验加成表.get(i)).getRight()});
        }
        LZJMS.蓝蜗牛.setSelected(((Integer) Start.ConfigValuesMap.get("蓝蜗牛")).intValue() > 0);
        LZJMS.蘑菇仔.setSelected(((Integer) Start.ConfigValuesMap.get("蘑菇仔")).intValue() > 0);
        LZJMS.绿水灵.setSelected(((Integer) Start.ConfigValuesMap.get("绿水灵")).intValue() > 0);
        LZJMS.星精灵.setSelected(((Integer) Start.ConfigValuesMap.get("星精灵")).intValue() > 0);
        LZJMS.胖企鹅.setSelected(((Integer) Start.ConfigValuesMap.get("胖企鹅")).intValue() > 0);
        LZJMS.白雪人.setSelected(((Integer) Start.ConfigValuesMap.get("白雪人")).intValue() > 0);
        LZJMS.大海龟.setSelected(((Integer) Start.ConfigValuesMap.get("大海龟")).intValue() > 0);
        LZJMS.章鱼怪.setSelected(((Integer) Start.ConfigValuesMap.get("章鱼怪")).intValue() > 0);
        LZJMS.顽皮猴.setSelected(((Integer) Start.ConfigValuesMap.get("顽皮猴")).intValue() > 0);
        LZJMS.漂漂猪.setSelected(((Integer) Start.ConfigValuesMap.get("漂漂猪")).intValue() > 0);
        LZJMS.小青蛇.setSelected(((Integer) Start.ConfigValuesMap.get("小青蛇")).intValue() > 0);
        LZJMS.红螃蟹.setSelected(((Integer) Start.ConfigValuesMap.get("红螃蟹")).intValue() > 0);
        LZJMS.石头人.setSelected(((Integer) Start.ConfigValuesMap.get("石头人")).intValue() > 0);
        LZJMS.紫色猫.setSelected(((Integer) Start.ConfigValuesMap.get("紫色猫")).intValue() > 0);
        LZJMS.大灰狼.setSelected(((Integer) Start.ConfigValuesMap.get("大灰狼")).intValue() > 0);
        LZJMS.青鳄鱼.setSelected(((Integer) Start.ConfigValuesMap.get("青鳄鱼")).intValue() > 0);
        LZJMS.花蘑菇.setSelected(((Integer) Start.ConfigValuesMap.get("花蘑菇")).intValue() > 0);
        LZJMS.火野猪.setSelected(((Integer) Start.ConfigValuesMap.get("火野猪")).intValue() > 0);
        LZJMS.小白兔.setSelected(((Integer) Start.ConfigValuesMap.get("小白兔")).intValue() > 0);
        LZJMS.喷火龙.setSelected(((Integer) Start.ConfigValuesMap.get("喷火龙")).intValue() > 0);
        LZJMS.玩家登录.setSelected(((Integer) Start.ConfigValuesMap.get("玩家登录")).intValue() > 0);
        LZJMS.管理员独占登录.setSelected(((Integer) Start.ConfigValuesMap.get("管理员独占登录")).intValue() > 0);
        LZJMS.记录登录信息.setSelected(((Integer) Start.ConfigValuesMap.get("记录登录信息")).intValue() > 0);
        LZJMS.欢迎弹窗.setSelected(((Integer) Start.ConfigValuesMap.get("欢迎弹窗")).intValue() > 0);
        LZJMS.玩家交易.setSelected(((Integer) Start.ConfigValuesMap.get("玩家交易")).intValue() > 0);
        LZJMS.玩家聊天.setSelected(((Integer) Start.ConfigValuesMap.get("记录登录信息")).intValue() > 0);
        LZJMS.丢出金币.setSelected(((Integer) Start.ConfigValuesMap.get("丢出金币")).intValue() > 0);
        LZJMS.丢出物品.setSelected(((Integer) Start.ConfigValuesMap.get("丢出物品")).intValue() > 0);
        LZJMS.升级提示.setSelected(((Integer) Start.ConfigValuesMap.get("升级提示")).intValue() > 0);
        LZJMS.游戏喇叭.setSelected(((Integer) Start.ConfigValuesMap.get("游戏喇叭")).intValue() > 0);
        LZJMS.越级打怪.setSelected(((Integer) Start.ConfigValuesMap.get("越级打怪")).intValue() > 0);
        LZJMS.雇佣商人.setSelected(((Integer) Start.ConfigValuesMap.get("雇佣商人")).intValue() > 0);
        LZJMS.地图名称.setSelected(((Integer) Start.ConfigValuesMap.get("地图名称")).intValue() > 0);
        LZJMS.玩家指令.setSelected(((Integer) Start.ConfigValuesMap.get("玩家指令")).intValue() > 0);
        LZJMS.游戏仓库.setSelected(((Integer) Start.ConfigValuesMap.get("游戏仓库")).intValue() > 0);
        LZJMS.子弹扩充开关.setSelected(((Integer) Start.ConfigValuesMap.get("子弹扩充开关")).intValue() > 0);
        LZJMS.管理员隐身.setSelected(((Integer) Start.ConfigValuesMap.get("管理员隐身")).intValue() > 0);
        LZJMS.管理员加速.setSelected(((Integer) Start.ConfigValuesMap.get("管理员加速")).intValue() > 0);
        LZJMS.段数检测.setSelected(((Integer) Start.ConfigValuesMap.get("段数检测")).intValue() > 0);
        LZJMS.个数检测.setSelected(((Integer) Start.ConfigValuesMap.get("个数检测")).intValue() > 0);
        LZJMS.攻速检测.setSelected(((Integer) Start.ConfigValuesMap.get("攻速检测")).intValue() > 0);
        LZJMS.全屏检测.setSelected(((Integer) Start.ConfigValuesMap.get("全屏检测")).intValue() > 0);
        LZJMS.吸怪检测.setSelected(((Integer) Start.ConfigValuesMap.get("吸怪检测")).intValue() > 0);
        LZJMS.吸物检测.setSelected(((Integer) Start.ConfigValuesMap.get("吸物检测")).intValue() > 0);
        LZJMS.伤害检测.setSelected(((Integer) Start.ConfigValuesMap.get("伤害检测")).intValue() > 0);
        LZJMS.丢失伤害.setSelected(((Integer) Start.ConfigValuesMap.get("丢失伤害")).intValue() > 0);
        LZJMS.伤害上限.setText(String.valueOf(Start.ConfigValuesMap.get("伤害上限")));
        LZJMS.伤害修正开关.setSelected(((Integer) Start.ConfigValuesMap.get("伤害修正")).intValue() > 0);
        LZJMS.重置技能范围开关.setSelected(((Integer) Start.ConfigValuesMap.get("重置技能范围开关")).intValue() > 0);
        LZJMS.重置技能总范围.setText(String.valueOf(Start.ConfigValuesMap.get("重置技能总范围")));
        LZJMS.全服通告.setSelected(((Integer) Start.ConfigValuesMap.get("全服通告")).intValue() > 0);
        LZJMS.封停账号.setSelected(((Integer) Start.ConfigValuesMap.get("封停账号")).intValue() > 0);
        LZJMS.封停IP.setSelected(((Integer) Start.ConfigValuesMap.get("封停IP")).intValue() > 0);
        LZJMS.封停MAC.setSelected(((Integer) Start.ConfigValuesMap.get("封停MAC")).intValue() > 0);
        LZJMS.全局血量等级.setText(String.valueOf(Start.ConfigValuesMap.get("全局血量等级")));
        LZJMS.全局血量倍率.setText(String.valueOf(Start.ConfigValuesMap.get("全局血量")));
        LZJMS.启动服务端.setEnabled(true);
        LZJMS.第二控制台.setEnabled(true);
        LZJMS.第三控制台.setEnabled(true);
        LZJMS.清空日志.setEnabled(true);
        LZJMS.重载全部.setEnabled(true);
        LZJMS.保存雇佣.setEnabled(true);
        LZJMS.保存数据.setEnabled(true);
        LZJMS.主界面菜单.setEnabled(true);
        LZJMS.关闭时间显示.setEnabled(true);
        LZJMS.关闭时间输入.setEnabled(true);
    }

    public void 配置更新(final String name, final int value) {
        if (Start.ConfigValuesMap.get(name) != null) {
            Start.ConfigValuesMap.put(name, Integer.valueOf(value));
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE configvalues SET Val = ? WHERE name = ?");
            ps.setInt(1, value);
            ps.setString(2, name);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }

    public void 世界更新(final String name, final int value) {
        if (Start.世界等级 > 0) {
            Start.世界等级 = value;
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE fz9 SET sz1 = ? WHERE log = ?");
            ps.setInt(1, value);
            ps.setString(2, name);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }

    public void 经验限制配置更新(final String name, final int value) {
        if (Start.ConfigValuesMap.get(name) != null) {
            Start.ConfigValuesMap.put(name, Integer.valueOf(value));
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE explimit SET num = ? WHERE name = ?");
            ps.setInt(1, value);
            ps.setString(2, name);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }

    public void 血量倍率配置更新(final int name, final int value) {
        if (DamageParse.MobRedDam.get(Integer.valueOf(name)) != null) {
            DamageParse.MobRedDam.put(Integer.valueOf(name), Integer.valueOf(value));
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE mobreddam SET numb = ? WHERE mobid = ?");
            ps.setInt(1, value);
            ps.setInt(2, name);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }

    public void 血量倍率配置更新1(final String name, final int value) {
        if (Start.ConfigValuesMap.get(name) != null) {
            Start.ConfigValuesMap.put(name, Integer.valueOf(value));
        }
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE ConfigValues SET val = ? WHERE name = ?");
            ps.setInt(1, value);
            ps.setString(2, name);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }

    public void 公告配置更新(final String nameNew, final String nameOld) {
        try {
            final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ps = con.prepareStatement("UPDATE messages SET message = ? WHERE message = ?");
            ps.setString(1, nameNew);
            ps.setString(2, nameOld);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
        }
    }

    public void 重载配置() {
        Start.GetConfigValues();
        Start.公告初始化();
        JOptionPane.showMessageDialog(null, "重载数据库配置成功！", "提示", 1);
    }

    public static void UI信息更新() {
        LZJMS.内存.setValue((int) LZJMS.memoryBean.getHeapMemoryUsage().getUsed() / 1024 / 1024);
        LZJMS.内存.setString(String.valueOf(LZJMS.memoryBean.getHeapMemoryUsage().getUsed() / 1024L / 1024L) + "MB");
        LZJMS.内存.repaint();
        final int 运行秒数 = (int) ((System.currentTimeMillis() - LZJMS.startRunTime) / 1000L);
        LZJMS.时长.setValue(运行秒数 / 60);
        LZJMS.时长.setString(运行秒数 / 86400 + "天 " + 运行秒数 / 3600 % 24 + "时" + 运行秒数 / 60 % 60 + "分" + 运行秒数 % 60 + "秒");
    }

    public static void 人数更新(final int playerOnlineNum) {
        LZJMS.人数.setValue(playerOnlineNum);
        LZJMS.人数.setString(playerOnlineNum + "/300");
    }

    public void 自建NPC刷新() {
        for (int i = ((DefaultTableModel) (DefaultTableModel) LZJMS.自建NPC列表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) LZJMS.自建NPC列表.getModel()).removeRow(i);
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("SELECT dataid, mid FROM wz_customlife")) {
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ((DefaultTableModel) LZJMS.自建NPC列表.getModel()).insertRow(LZJMS.自建NPC列表.getRowCount(), new Object[]{Integer.valueOf(rs.getInt("dataid")), Integer.valueOf(rs.getInt("mid"))});
                }
                rs.close();
            }
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]读取NPC数据库出错：" + ex.getMessage());
        }
    }

    public void 自建NPC删除() {
        final int i = LZJMS.自建NPC列表.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
            return;
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("delete from 游戏NPC管理 where dataid = ? and mid = ?")) {
            ps.setInt(1, Integer.parseInt(LZJMS.自建NPC列表.getValueAt(i, 0).toString()));
            ps.setInt(2, Integer.parseInt(LZJMS.自建NPC列表.getValueAt(i, 1).toString()));
            ps.execute();
            ps.close();
            ((DefaultTableModel) (DefaultTableModel) LZJMS.自建NPC列表.getModel()).removeRow(i);
            JOptionPane.showMessageDialog(null, "选中NPC删除完毕！", "配置提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]删除NPC出错：" + ex.getMessage());
        }
    }

    public void 封停IP刷新() {
        for (int i = ((DefaultTableModel) (DefaultTableModel) LZJMS.封禁IP地址.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) LZJMS.封禁IP地址.getModel()).removeRow(i);
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("SELECT ip FROM ipbans")) {
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ((DefaultTableModel) LZJMS.封禁IP地址.getModel()).insertRow(LZJMS.封禁IP地址.getRowCount(), new Object[]{rs.getString("ip")});
                }
                rs.close();
            }
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]读取BANIP数据库出错：" + ex.getMessage());
        }
    }

    public void 封停IP删除() {
        final int i = LZJMS.封禁IP地址.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
            return;
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("delete from ipbans where  ip = ?")) {
            ps.setString(1, LZJMS.封禁IP地址.getValueAt(i, 0).toString());
            ps.execute();
            ps.close();
            ((DefaultTableModel) (DefaultTableModel) LZJMS.封禁IP地址.getModel()).removeRow(i);
            JOptionPane.showMessageDialog(null, "选中封禁IP删除完毕！", "配置提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]删除NPC出错：" + ex.getMessage());
        }
    }

    public void 封停MAC刷新() {
        for (int i = ((DefaultTableModel) (DefaultTableModel) LZJMS.封禁MAC地址.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) LZJMS.封禁MAC地址.getModel()).removeRow(i);
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("SELECT mac FROM macbans")) {
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ((DefaultTableModel) LZJMS.封禁MAC地址.getModel()).insertRow(LZJMS.封禁MAC地址.getRowCount(), new Object[]{rs.getString("mac")});
                }
                rs.close();
            }
            ps.close();
            con.close();
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]读取BANMAC数据库出错：" + ex.getMessage());
        }
    }

    public void 封停MAC删除() {
        final int i = LZJMS.封禁MAC地址.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
            return;
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("delete from macbans where mac = ?")) {
            ps.setString(1, LZJMS.封禁MAC地址.getValueAt(i, 0).toString());
            ps.execute();
            ps.close();
            ((DefaultTableModel) (DefaultTableModel) LZJMS.封禁MAC地址.getModel()).removeRow(i);
            JOptionPane.showMessageDialog(null, "选中封禁MAC删除完毕！", "配置提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]删除NPC出错：" + ex.getMessage());
        }
    }

    public void 怪物经验限制刷新() {
        for (int i = ((DefaultTableModel) (DefaultTableModel) LZJMS.怪物经验限制表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) LZJMS.怪物经验限制表.getModel()).removeRow(i);
        }
        for (final String key : Start.ConfigValuesMap.keySet()) {
            if (key.matches("[0-9]+经验")) {
                ((DefaultTableModel) LZJMS.怪物经验限制表.getModel()).insertRow(LZJMS.怪物经验限制表.getRowCount(), new Object[]{key.substring(0, key.length() - 2), String.valueOf(Start.ConfigValuesMap.get(key))});
            }
        }
    }

    public void 怪物经验限制删除() {
        final int i = LZJMS.怪物经验限制表.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
            return;
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("delete from explimit where name = ? and num = ?")) {
            ps.setInt(1, Integer.parseInt(LZJMS.怪物经验限制表.getValueAt(i, 0).toString()));
            ps.setInt(2, Integer.parseInt(LZJMS.怪物经验限制表.getValueAt(i, 1).toString()));
            ps.execute();
            ps.close();
            Start.ConfigValuesMap.remove(LZJMS.怪物经验限制表.getValueAt(i, 0).toString() + "经验");
            ((DefaultTableModel) (DefaultTableModel) LZJMS.怪物经验限制表.getModel()).removeRow(i);
            JOptionPane.showMessageDialog(null, "选中怪物经验限制删除完毕！", "配置提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]删除怪物经验限制出错：" + ex.getMessage());
        }
    }

    public void 怪物经验限制增加() {
        final String 限制 = JOptionPane.showInputDialog(null, "请输入要限制经验的怪物ID和次数：\n格式[怪物ID,次数]\n", "增加经验限制", 1);
        if (限制.matches("^[0-9]*[,][0-9]*")) {
            try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
                 final PreparedStatement ps = con.prepareStatement("insert into explimit (name,num) values (?,?)")) {
                ps.setString(1, 限制.substring(0, 限制.indexOf(",")) + "经验");
                ps.setInt(2, Integer.valueOf(限制.substring(限制.indexOf(",") + 1, 限制.length())).intValue());
                ps.execute();
                ps.close();
                ((DefaultTableModel) LZJMS.怪物经验限制表.getModel()).insertRow(LZJMS.怪物经验限制表.getRowCount(), new Object[]{限制.substring(0, 限制.indexOf(",")), Integer.valueOf(限制.substring(限制.indexOf(",") + 1, 限制.length()))});
                Start.ConfigValuesMap.put(限制.substring(0, 限制.indexOf(",")) + "经验", Integer.valueOf(限制.substring(限制.indexOf(",") + 1, 限制.length())));
                JOptionPane.showMessageDialog(null, "增加经验限制成功！", "配置提示", 1);
            } catch (SQLException ex) {
                System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]增加怪物经验限制出错：" + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "增加经验限制失败！", "配置提示", 1);
        }
    }

    public void 怪物经验限制修改() {
        for (int i = 0; i < ((DefaultTableModel) (DefaultTableModel) LZJMS.怪物经验限制表.getModel()).getRowCount(); ++i) {
            if (Start.ConfigValuesMap.get(LZJMS.怪物经验限制表.getValueAt(i, 0).toString() + "经验") != Integer.valueOf(LZJMS.怪物经验限制表.getValueAt(i, 1).toString())) {
                this.经验限制配置更新(LZJMS.怪物经验限制表.getValueAt(i, 0).toString() + "经验", Integer.valueOf(LZJMS.怪物经验限制表.getValueAt(i, 1).toString()).intValue());
            }
        }
        JOptionPane.showMessageDialog(null, "配置更新完毕！", "配置提示", 1);
    }

    public void 公告刷新() {
        for (int i = ((DefaultTableModel) (DefaultTableModel) this.公告信息.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) this.公告信息.getModel()).removeRow(i);
        }
        for (final String key : Start.公告列表) {
            ((DefaultTableModel) this.公告信息.getModel()).insertRow(this.公告信息.getRowCount(), new Object[]{key});
        }
    }

    public void 公告删除() {
        final int i = this.公告信息.getSelectedRow();
        int deleteRow = -1;
        if (i < 0) {
            JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
            return;
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("delete from messages where message = ?")) {
            ps.setString(1, this.公告信息.getValueAt(i, 0).toString());
            ps.execute();
            ps.close();
            for (int j = 0; j < Start.公告列表.size(); ++j) {
                if (((String) Start.公告列表.get(j)).contains((CharSequence) this.公告信息.getValueAt(i, 0).toString())) {
                    deleteRow = j;
                    break;
                }
            }
            Start.公告列表.remove(deleteRow);
            ((DefaultTableModel) (DefaultTableModel) this.公告信息.getModel()).removeRow(i);
            JOptionPane.showMessageDialog(null, "选中公告删除完毕！", "配置提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]删除公告出错：" + ex.getMessage());
        }
    }

    public void 公告增加() {
        final String 新公告 = JOptionPane.showInputDialog(null, "请输入要发布的公告内容：", "增加公告", 1);
        if (新公告 == null || 新公告.length() < 1) {
            return;
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("insert into messages(message) values (?)")) {
            ps.setString(1, 新公告);
            ps.execute();
            ps.close();
            ((DefaultTableModel) this.公告信息.getModel()).insertRow(this.公告信息.getRowCount(), new Object[]{新公告});
            Start.公告列表.add(新公告);
            JOptionPane.showMessageDialog(null, "增加公告成功！", "配置提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]增加公告出错：" + ex.getMessage());
        }
    }

    public void 公告修改() {
        for (int i = 0; i < ((DefaultTableModel) (DefaultTableModel) this.公告信息.getModel()).getRowCount(); ++i) {
            if (!((String) Start.公告列表.get(i)).equals(this.公告信息.getValueAt(i, 0).toString())) {
                this.公告配置更新(this.公告信息.getValueAt(i, 0).toString(), (String) Start.公告列表.get(i));
                Start.公告列表.set(i, this.公告信息.getValueAt(i, 0).toString());
            }
        }
        JOptionPane.showMessageDialog(null, "公告更新完毕！", "配置提示", 1);
    }

    public void 怪物血量倍率刷新() {
        for (int i = ((DefaultTableModel) (DefaultTableModel) this.怪物血量表.getModel()).getRowCount() - 1; i >= 0; --i) {
            ((DefaultTableModel) (DefaultTableModel) this.怪物血量表.getModel()).removeRow(i);
        }
        DamageParse.readMobRedDam();
        final Iterator<Integer> iterator = DamageParse.MobRedDam.keySet().iterator();
        while (iterator.hasNext()) {
            final int key = ((Integer) iterator.next()).intValue();
            ((DefaultTableModel) this.怪物血量表.getModel()).insertRow(this.怪物血量表.getRowCount(), new Object[]{Integer.valueOf(key), String.valueOf(DamageParse.MobRedDam.get(Integer.valueOf(key)))});
        }
    }

    public void 怪物血量倍率删除() {
        final int i = this.怪物血量表.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
            return;
        }
        try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("delete from mobreddam where mobid = ? and numb = ?")) {
            ps.setInt(1, Integer.parseInt(this.怪物血量表.getValueAt(i, 0).toString()));
            ps.setInt(2, Integer.parseInt(this.怪物血量表.getValueAt(i, 1).toString()));
            ps.execute();
            ps.close();
            DamageParse.MobRedDam.remove(Integer.valueOf(this.怪物血量表.getValueAt(i, 0).toString()));
            ((DefaultTableModel) (DefaultTableModel) this.怪物血量表.getModel()).removeRow(i);
            JOptionPane.showMessageDialog(null, "选中怪物血量倍率删除完毕！", "配置提示", 1);
        } catch (SQLException ex) {
            System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]删除怪物血量倍率限制出错：" + ex.getMessage());
        }
    }

    public void 怪物血量倍率增加() {
        final String 倍率 = JOptionPane.showInputDialog(null, "请输入要增加的怪物ID和血量倍率：\n格式[怪物ID,倍率]\n", "增加血量倍率", 1);
        if (倍率 == null) {
            return;
        }
        if (倍率.matches("^[0-9]*[,][0-9]*")) {
            try (final Connection con = (Connection) DBConPool.getInstance().getDataSource().getConnection();
                 final PreparedStatement ps = con.prepareStatement("insert into mobreddam (mobid,numb) values (?,?)")) {
                ps.setInt(1, Integer.valueOf(倍率.substring(0, 倍率.indexOf(","))).intValue());
                ps.setInt(2, Integer.valueOf(倍率.substring(倍率.indexOf(",") + 1, 倍率.length())).intValue());
                ps.execute();
                ps.close();
                ((DefaultTableModel) this.怪物血量表.getModel()).insertRow(this.怪物血量表.getRowCount(), new Object[]{倍率.substring(0, 倍率.indexOf(",")), Integer.valueOf(倍率.substring(倍率.indexOf(",") + 1, 倍率.length()))});
                DamageParse.MobRedDam.put(Integer.valueOf(倍率.substring(0, 倍率.indexOf(","))), Integer.valueOf(倍率.substring(倍率.indexOf(",") + 1, 倍率.length())));
                JOptionPane.showMessageDialog(null, "增加血量倍率成功！", "配置提示", 1);
            } catch (SQLException ex) {
                System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]增加怪物血量倍率出错：" + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "增加血量倍率失败！", "配置提示", 1);
        }
    }

    public void 怪物血量倍率修改() {
        for (int i = 0; i < ((DefaultTableModel) (DefaultTableModel) this.怪物血量表.getModel()).getRowCount(); ++i) {
            if (DamageParse.MobRedDam.get(Integer.valueOf(this.怪物血量表.getValueAt(i, 0).toString())) != Integer.valueOf(this.怪物血量表.getValueAt(i, 1).toString())) {
                this.血量倍率配置更新(Integer.valueOf(this.怪物血量表.getValueAt(i, 0).toString()).intValue(), Integer.valueOf(this.怪物血量表.getValueAt(i, 1).toString()).intValue());
            }
        }
        JOptionPane.showMessageDialog(null, "配置更新完毕！", "配置提示", 1);
    }

    public void 全局血量倍率修改() {
        if (Start.ConfigValuesMap.get("全局血量等级") != Integer.valueOf(LZJMS.全局血量等级.getText())) {
            this.血量倍率配置更新1("全局血量等级", Integer.valueOf(LZJMS.全局血量等级.getText()).intValue());
        }
        if (Start.ConfigValuesMap.get("全局血量") != Integer.valueOf(LZJMS.全局血量倍率.getText())) {
            this.血量倍率配置更新1("全局血量", Integer.valueOf(LZJMS.全局血量倍率.getText()).intValue());
        }
        MapleLifeFactory.getMonsterStats().clear();
        JOptionPane.showMessageDialog(null, "配置更新完毕！", "配置提示", 1);
    }
}
