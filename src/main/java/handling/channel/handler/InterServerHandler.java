package handling.channel.handler;

import server.maps.FieldLimitType;
import tools.data.LittleEndianAccessor;
import handling.world.guild.MapleGuild;
import handling.world.MapleMessenger;
import handling.world.CharacterIdChannelPair;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import constants.tzjc;
import client.BuddyEntry;
import client.MapleQuestStatus;
import tools.FilePrinter;
import tools.packet.FamilyPacket;
import handling.world.World.Family;
import handling.world.World.Alliance;
import handling.world.World.Guild;
import handling.world.World.Find;
import handling.world.World.Party;
import handling.world.MaplePartyCharacter;
import handling.world.PartyOperation;
import handling.world.World.Buddy;
import client.SkillFactory;
import gui.Start;
import handling.login.LoginServer;
import handling.world.World.Broadcast;
import handling.cashshop.CashShopServer;
import handling.world.CharacterTransfer;
import handling.world.PlayerBuffStorage;
import handling.world.World.Messenger;
import handling.world.MapleMessengerCharacter;
import handling.channel.ChannelServer;
import tools.FileoutputUtil;
import constants.WorldConstants;
import tools.MaplePacketCreator;
import handling.world.World;
import client.MapleCharacter;
import client.MapleClient;

public class InterServerHandler
{
    public static final void EnterCashShop(final MapleClient c, final MapleCharacter chr, final boolean mts) {
        if (c.getCloseSession()) {
            return;
        }
        if (World.isShutDown && !chr.isGM()) {
            c.sendPacket(MaplePacketCreator.serverBlocked(2));
            c.sendPacket(MaplePacketCreator.enableActions());
            return;
        }
        if ((!WorldConstants.CS_ENABLE && !chr.isGM()) || mts) {
            c.sendPacket(MaplePacketCreator.serverBlocked(2));
            c.sendPacket(MaplePacketCreator.enableActions());
            return;
        }
        if (chr == null || chr.getMap() == null || chr.getEventInstance() != null || c.getChannelServer() == null) {
            c.getSession().write(MaplePacketCreator.serverBlocked(2));
            c.getSession().write(MaplePacketCreator.enableActions());
            return;
        }
        if (chr.getAntiMacro().inProgress()) {
            c.getPlayer().dropMessage(1, "被使用测谎仪时无法操作。");
            c.sendPacket(MaplePacketCreator.enableActions());
            return;
        }
        try {
            chr.saveToDB(false, false);
        }
        catch (Exception ex) {
            FileoutputUtil.logToFile("logs/进入商城保存数据异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + chr.getName() + " 角色ID " + chr.getId());
            FileoutputUtil.outError("logs/进入商城保存数据异常.txt", (Throwable)ex);
        }
        final ChannelServer ch = ChannelServer.getInstance(c.getChannel());
        chr.dispelBuff();
        chr.changeRemoval();
        if (chr.getMessenger() != null) {
            final MapleMessengerCharacter messengerplayer = new MapleMessengerCharacter(chr);
            Messenger.leaveMessenger(chr.getMessenger().getId(), messengerplayer);
        }
        PlayerBuffStorage.addBuffsToStorage(chr.getId(), chr.getAllBuffs());
        PlayerBuffStorage.addCooldownsToStorage(chr.getId(), chr.getCooldowns());
        PlayerBuffStorage.addDiseaseToStorage(chr.getId(), chr.getAllDiseases());
        World.channelChangeData(new CharacterTransfer(chr), chr.getId(), mts ? -20 : -10);
        ch.removePlayer(chr);
        c.updateLoginState(6, c.getSessionIPAddress());
        chr.getMap().removePlayer(chr);
        c.sendPacket(MaplePacketCreator.getChannelChange(c, Integer.parseInt(CashShopServer.getIP().split(":")[1])));
        c.getPlayer().expirationTask(true, false);
        c.setPlayer(null);
        c.setReceiving(false);
    }
    
    public static final void LoggedIn(final int playerid, final MapleClient c) {
        if (c.getCloseSession()) {
            return;
        }
        final ChannelServer channelServer = c.getChannelServer();
        final CharacterTransfer transfer = channelServer.getPlayerStorage().getPendingCharacter(playerid);
        MapleCharacter player;
        if (transfer == null) {
            final List<String> charNamesa = c.loadCharacterNamesByCharId(playerid);
            for (final ChannelServer cs : ChannelServer.getAllInstances()) {
                for (final String name : charNamesa) {
                    if (cs.getPlayerStorage().getCharacterByName(name) != null) {
                        FileoutputUtil.logToFile("logs/Data/非法登录.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + "登录1");
                        Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
                        c.getSession().close();
                        return;
                    }
                }
            }
            for (final String name2 : charNamesa) {
                if (CashShopServer.getPlayerStorage().getCharacterByName(name2) != null) {
                    FileoutputUtil.logToFile("logs/Data/非法登录.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + "登录1");
                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
                    c.getSession().close();
                    return;
                }
            }
            final List<String> charNames = c.loadCharacterNamesByCharId(playerid);
            for (final ChannelServer cs2 : ChannelServer.getAllInstances()) {
                for (final String name3 : charNames) {
                    final MapleCharacter character = cs2.getPlayerStorage().getCharacterByName(name3);
                    if (character != null) {
                        FileoutputUtil.logToFile("logs/Data/非法登录.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + "登录3");
                        Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
                        c.getSession().close();
                        character.getClient().getSession().close();
                    }
                }
            }
            for (final String name4 : charNames) {
                final MapleCharacter charactercs = CashShopServer.getPlayerStorage().getCharacterByName(name4);
                if (charactercs != null) {
                    FileoutputUtil.logToFile("logs/Data/非法登录.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + "登录4");
                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
                    c.getSession().close();
                    charactercs.getClient().getSession().close();
                }
            }
            if (System.getProperty(String.valueOf(playerid)) == null || !System.getProperty(String.valueOf(playerid)).equals("1")) {
                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
                FileoutputUtil.logToFile("logs/Data/非法登录.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName());
                c.getSession().close();
                return;
            }
            System.setProperty(String.valueOf(playerid), String.valueOf(0));
            LoginServer.removeClient(c);
            player = MapleCharacter.loadCharFromDB(playerid, c, true);
            LoginServer.addEnterGameAgainTime(c.getAccID());
            player.setMrqdTime(System.currentTimeMillis());
        }
        else {
            player = MapleCharacter.ReconstructChr(transfer, c, true);
        }
        if (!LoginServer.CanLoginKey(player.getLoginKey(), player.getAccountID()) || (LoginServer.getLoginKey(player.getAccountID()) == null && !player.getLoginKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(player.getAccountID()) + " 服务端key：" + player.getLoginKey() + " 进入游戏1");
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (!LoginServer.CanServerKey(player.getServerKey(), player.getAccountID()) || (LoginServer.getServerKey(player.getAccountID()) == null && !player.getServerKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端频道KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getServerKey(player.getAccountID()) + " 服务端key：" + player.getServerKey() + " 进入游戏2");
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (!LoginServer.CanClientKey(player.getClientKey(), player.getAccountID()) || (LoginServer.getClientKey(player.getAccountID()) == null && !player.getClientKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端进入KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getClientKey(player.getAccountID()) + " 服务端key：" + player.getClientKey() + " 进入游戏3");
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (((Integer)Start.ConfigValuesMap.get("离线泡点开关")).intValue() > 0) {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                cserv.getMapFactory().getMap(((Integer)Start.ConfigValuesMap.get("离线泡点地图")).intValue()).removePlayer3(player);
            }
        }
        c.setLastLoginTime(LoginServer.getEnterGameAgainTime(c.getAccID()));
        LoginServer.forceRemoveClient(c, false);
        ChannelServer.forceRemovePlayerByAccId(c, c.getAccID());
        c.setPlayer(player);
        c.setAccID(player.getAccountID());
        c.setSecondPassword(player.getAccountSecondPassword());
        final int state = c.getLoginState();
        boolean allowLogin = false;
        if (state == 1 || state == 6 || state == 0) {
            allowLogin = !World.isCharacterListConnected(c.loadCharacterNames(c.getWorld()));
        }
        if (!allowLogin) {
            c.setPlayer(null);
            c.getSession().close();
            FileoutputUtil.logToFile("logs/Data/进入游戏掉线.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号: " + c.getAccountName() + " allowLogin");
            return;
        }
        c.updateLoginState(2, c.getSessionIPAddress());
        channelServer.addPlayer(player);
        c.loadVip(player.getAccountID());
        c.sendPacket(MaplePacketCreator.getCharInfo(player));
        if (MapleCharacter.getCharacterNameById2(playerid) == null) {
            FileoutputUtil.logToFile("logs/Data/角色不存在.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + "登录");
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录不存在角色 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (!LoginServer.CanLoginKey(player.getLoginKey(), player.getAccountID()) || (LoginServer.getLoginKey(player.getAccountID()) == null && !player.getLoginKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(player.getAccountID()) + " 服务端key：" + player.getLoginKey() + " 进入游戏4");
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (!LoginServer.CanServerKey(player.getServerKey(), player.getAccountID()) || (LoginServer.getServerKey(player.getAccountID()) == null && !player.getServerKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端频道KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getServerKey(player.getAccountID()) + " 服务端key：" + player.getServerKey() + " 进入游戏5");
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (!LoginServer.CanClientKey(player.getClientKey(), player.getAccountID()) || (LoginServer.getClientKey(player.getAccountID()) == null && !player.getClientKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端进入KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getClientKey(player.getAccountID()) + " 服务端key：" + player.getClientKey() + " 进入游戏6");
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (player.isGM()) {
            if (((Integer)Start.ConfigValuesMap.get("管理员隐身")).intValue() > 0) {
                SkillFactory.getSkill(9001004).getEffect(1).applyTo(player);
            }
            if (((Integer)Start.ConfigValuesMap.get("管理员加速")).intValue() > 0) {
                SkillFactory.getSkill(9001001).getEffect(1).applyTo(player);
            }
        }
        c.sendPacket(MaplePacketCreator.temporaryStats_Reset());
        c.sendPacket(MaplePacketCreator.showCharCash(player));
        player.getMap().addPlayer(player);
        try {
            player.silentGiveBuffs(PlayerBuffStorage.getBuffsFromStorage(player.getId()));
            player.giveCoolDowns(PlayerBuffStorage.getCooldownsFromStorage(player.getId()));
            player.giveSilentDebuff(PlayerBuffStorage.getDiseaseFromStorage(player.getId()));
            if (player.getBossLogS("离线挂机") > 0) {
                player.deleteBossLogps("离线挂机");
                if (LoginServer.getChrPos() != null && LoginServer.getChrPos().containsKey(Integer.valueOf(player.getId()))) {
                    LoginServer.RemoveChrPos(player.getId());
                }
            }
            final Collection<Integer> buddyIds = player.getBuddylist().getBuddiesIds();
            Buddy.loggedOn(player.getName(), player.getId(), c.getChannel(), buddyIds, player.getGMLevel(), player.isHidden());
            if (player.getParty() != null) {
                Party.updateParty(player.getParty().getId(), PartyOperation.LOG_ONOFF, new MaplePartyCharacter(player));
            }
            final CharacterIdChannelPair[] multiBuddyFind;
            final CharacterIdChannelPair[] onlineBuddies = multiBuddyFind = Find.multiBuddyFind(player.getId(), buddyIds);
            for (final CharacterIdChannelPair onlineBuddy : multiBuddyFind) {
                final BuddyEntry ble = player.getBuddylist().get(onlineBuddy.getCharacterId());
                ble.setChannel(onlineBuddy.getChannel());
                player.getBuddylist().put(ble);
            }
            c.sendPacket(MaplePacketCreator.updateBuddylist(player.getBuddylist().getBuddies()));
            final MapleMessenger messenger = player.getMessenger();
            if (messenger != null) {
                Messenger.silentJoinMessenger(messenger.getId(), new MapleMessengerCharacter(c.getPlayer()));
                Messenger.updateMessenger(messenger.getId(), c.getPlayer().getName(), c.getChannel());
            }
            if (player.getGuildId() > 0) {
                Guild.setGuildMemberOnline(player.getMGC(), true, c.getChannel());
                c.sendPacket(MaplePacketCreator.showGuildInfo(player));
                final MapleGuild gs = Guild.getGuild(player.getGuildId());
                if (gs != null) {
                    final List<byte[]> packetList = Alliance.getAllianceInfo(gs.getAllianceId(), true);
                    if (packetList != null) {
                        for (final byte[] pack : packetList) {
                            if (pack != null) {
                                c.sendPacket(pack);
                            }
                        }
                    }
                }
                else {
                    player.setGuildId(0);
                    player.setGuildRank((byte)5);
                    player.setAllianceRank((byte)5);
                    player.saveGuildStatus();
                }
            }else {
                c.getSession().write(MaplePacketCreator.fuckGuildInfo(c.getPlayer()));//自定义的显示名字
                //c.sendPacket(MaplePacketCreator.勋章(player));
            }
            if (player.getFamilyId() > 0) {
                Family.setFamilyMemberOnline(player.getMFC(), true, c.getChannel());
            }
            c.sendPacket(FamilyPacket.getFamilyInfo(player));
        }
        catch (Exception e) {
            FilePrinter.printError("LoginError.txt", (Throwable)e);
        }
        c.sendPacket(FamilyPacket.getFamilyData());
        player.sendMacros();
        player.showNote();
        player.updatePartyMemberHP();
        player.startFairySchedule(false);
        player.baseSkills();
        c.sendPacket(MaplePacketCreator.getKeymap(player.getKeyLayout()));
        player.updatePetAuto();
        for (final MapleQuestStatus status : player.getStartedQuests()) {
            if (status.hasMobKills()) {
                c.sendPacket(MaplePacketCreator.updateQuestMobKills(status));
            }
        }
        final BuddyEntry pendingBuddyRequest = player.getBuddylist().pollPendingRequest();
        if (pendingBuddyRequest != null) {
            player.getBuddylist().put(new BuddyEntry(pendingBuddyRequest.getName(), pendingBuddyRequest.getCharacterId(), "ETC", -1, false, pendingBuddyRequest.getLevel(), pendingBuddyRequest.getJob()));
            c.sendPacket(MaplePacketCreator.requestBuddylistAdd(pendingBuddyRequest.getCharacterId(), pendingBuddyRequest.getName(), pendingBuddyRequest.getLevel(), pendingBuddyRequest.getJob()));
        }
        if (player.getJob() == 132) {
            player.checkBerserk();
        }
        if (((Integer)Start.ConfigValuesMap.get("离线泡点开关")).intValue() > 0 && player.isPlayer() && player.getMapId() == ((Integer)Start.ConfigValuesMap.get("离线泡点地图")).intValue()) {
            final long nowTimestamp = System.currentTimeMillis();
            final long 奖励时间 = nowTimestamp - player.getLastOfflineTime();
            if (奖励时间 >= 60000L) {
                int 离线时间 = (int)奖励时间 / 60000;
                if (离线时间 >= 1440) {
                    离线时间 = 1440;
                    c.getPlayer().dropMessage(5, "您的离线时间超过24小时,离线奖励按照一天算。");
                }
                int 经验 = 0;
                int 点卷数量 = 0;
                int 抵用卷数量 = 0;
                int 金币 = 0;
                if (((Integer)Start.ConfigValuesMap.get("离线泡点开关")).intValue() > 0) {
                    经验 = ((((Integer)Start.ConfigValuesMap.get("离线泡点等级开关")).intValue() < 1) ? (((Integer)Start.ConfigValuesMap.get("离线泡点经验")).intValue() * 离线时间) : (c.getPlayer().getLevel() * ((Integer)Start.ConfigValuesMap.get("离线泡点经验")).intValue() * 离线时间));
                    点卷数量 = ((((Integer)Start.ConfigValuesMap.get("离线泡点等级开关")).intValue() < 1) ? (((Integer)Start.ConfigValuesMap.get("离线泡点点券")).intValue() * 离线时间) : (c.getPlayer().getLevel() * ((Integer)Start.ConfigValuesMap.get("离线泡点点券")).intValue() * 离线时间));
                    抵用卷数量 = ((((Integer)Start.ConfigValuesMap.get("离线泡点等级开关")).intValue() < 1) ? (((Integer)Start.ConfigValuesMap.get("离线泡点抵用")).intValue() * 离线时间) : (c.getPlayer().getLevel() * ((Integer)Start.ConfigValuesMap.get("离线泡点抵用")).intValue() * 离线时间));
                    金币 = ((((Integer)Start.ConfigValuesMap.get("离线泡点等级开关")).intValue() < 1) ? (((Integer)Start.ConfigValuesMap.get("离线泡点金币")).intValue() * 离线时间) : (c.getPlayer().getLevel() * ((Integer)Start.ConfigValuesMap.get("离线泡点金币")).intValue() * 离线时间));
                }
                player.gainExp(经验, true, false, true);
                player.gainMeso(金币, true, false, true);
                player.modifyCSPoints(2, 抵用卷数量, true);
                player.modifyCSPoints(1, 点卷数量, true);
                if (((Integer)Start.ConfigValuesMap.get("离线给在线时间开关")).intValue() > 0) {
                    player.gainGamePoints(离线时间);
                }
                c.getPlayer().dropMessage(5, "您的离线时间" + 离线时间 + "分钟,离线获得[" + 经验 + "] 经验 [" + 抵用卷数量 + "] 抵用卷 [" + 点卷数量 + "] 点卷  [" + 金币 + "] 金币 !");
                player.updateOfflineTime1();
            }
        }
        player.set套装伤害加成(tzjc.check_tz(player));
        if (((Integer)Start.ConfigValuesMap.get("表单卡破功开关")).intValue() > 0) {}
        player.spawnSavedPets();
        final boolean ChrdangerousIp = player.chrdangerousIp(c.getSession().remoteAddress().toString());
        if (ChrdangerousIp) {
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 危险IP上线 IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 危险IP上线 IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 危险IP上线 IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
            FileoutputUtil.logToFile("logs/Data/危险IP登录.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId());
        }
        final boolean ChrdangerousName = player.ChrDangerousAcc(player.getClient().getAccountName());
        if (ChrdangerousName) {
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 危险角色上线 IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 危险角色上线 IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] 危险角色上线 IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
            FileoutputUtil.logToFile("logs/Data/危险账号登录.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSession().remoteAddress().toString().split(":")[0] + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId());
        }
        if (player.getGMLevel() == 0 && ((Integer)Start.ConfigValuesMap.get("上线喇叭")).intValue() > 0) {
            player.openNpc(9900004, "上线喇叭");
        }
    }
    
    public static final void ChangeChannel(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (c.getCloseSession()) {
            return;
        }
        if (chr.hasBlockedInventory(true) || chr.getEventInstance() != null || chr.getMap() == null || FieldLimitType.ChannelSwitch.check(chr.getMap().getFieldLimit())) {
            c.sendPacket(MaplePacketCreator.enableActions());
            return;
        }
        if (chr.getAntiMacro().inProgress()) {
            chr.dropMessage(5, "被使用测谎仪时无法操作。");
            c.sendPacket(MaplePacketCreator.enableActions());
            return;
        }
        chr.changeChannel(slea.readByte() + 1);
    }
}
