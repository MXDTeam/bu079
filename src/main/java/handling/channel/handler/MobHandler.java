package handling.channel.handler;

import server.maps.MapleNodes.MapleNodeInfo;
import java.awt.geom.Point2D;
import tools.MaplePacketCreator;
import server.MapleInventoryManipulator;
import client.inventory.MapleInventoryType;
import java.util.Iterator;
import handling.channel.ChannelServer;
import tools.StringUtil;
import tools.FileoutputUtil;
import server.movement.LifeMovement;
import server.movement.AbstractLifeMovement;
import server.maps.MapleMap;
import server.movement.LifeMovementFragment;
import java.util.List;
import java.awt.Point;
import server.life.MobSkill;
import server.life.MapleMonster;
import client.MapleCharacter;
import server.maps.AnimatedMapleMapObject;
import tools.packet.MobPacket;
import gui.Start;
import server.life.MobSkillFactory;
import server.Randomizer;
import tools.Pair;
import client.MapleClient;
import tools.data.LittleEndianAccessor;

public class MobHandler
{
    public static final void MoveMonster(final LittleEndianAccessor slea, final MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        if (chr == null || chr.getMap() == null) {
            return;
        }        
        final int objectId = slea.readInt();
        final MapleMonster monster = chr.getMap().getMonsterByOid(objectId);
        if (monster == null) {
            return;
        }
        final short moveid = slea.readShort();
        final boolean useSkill = slea.readByte() > 0;
        final byte skill = slea.readByte();
        final int unk2 = slea.readInt();
        int realskill = 0;
        int level = 0;
        if (useSkill) {
            final byte size = monster.getNoSkills();
            boolean used = false;
            if (size > 0) {
                final Pair<Integer, Integer> skillToUse = monster.getSkills().get((int)(byte)Randomizer.nextInt((int)size));
                realskill = ((Integer)skillToUse.getLeft()).intValue();
                level = ((Integer)skillToUse.getRight()).intValue();
                final MobSkill mobSkill = MobSkillFactory.getMobSkill(realskill, level);
                if (mobSkill != null && !mobSkill.checkCurrentBuff(chr, monster)) {
                    final long now = System.currentTimeMillis();
                    final long ls = monster.getLastSkillUsed(realskill);
                    if (ls == 0L || (now - ls > mobSkill.getCoolTime() && !mobSkill.onlyOnce())) {
                        monster.setLastSkillUsed(realskill, now, mobSkill.getCoolTime());
                        final int reqHp = (int)((float)monster.getHp() / (float)monster.getMobMaxHp() * 100.0f);
                        if (reqHp <= mobSkill.getHP()) {
                            used = true;
                            mobSkill.applyEffect(chr, monster, true);
                        }
                    }
                }
            }
            if (!used) {
                realskill = 0;
                level = 0;
            }
        }
        slea.readByte();
        final int unk3 = slea.readInt();
        slea.readInt();
        slea.readInt();
        final Point startPos = slea.readPos();
        List<LifeMovementFragment> res;
        try {
            res = MovementParse.parseMovement(slea, 2);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        final MapleCharacter controller = monster.getController();
        final MapleMap map = chr.getMap();
        if (((Integer)Start.ConfigValuesMap.get("怪物多倍地图倍率")).intValue() < 1) {
            try {
                CheckMobVac(c, monster, res, startPos);
            }
            catch (Exception ex) {}
        }
        if (controller != c.getPlayer()) {
            if (monster.isAttackedBy(c.getPlayer())) {
                monster.switchController(c.getPlayer(), true);
            }
        }
        else if (skill == -1 && monster.isControllerKnowsAboutAggro() && !monster.getStats().getMobile() && !monster.isFirstAttack()) {
            monster.setControllerHasAggro(false);
            monster.setControllerKnowsAboutAggro(false);
        }
        final boolean aggro = monster.isControllerHasAggro();
        if (aggro) {
            monster.setControllerKnowsAboutAggro(true);
        }
        if (res != null && res.size() > 0) {
            c.sendPacket(MobPacket.moveMonsterResponse(monster.getObjectId(), moveid, monster.getMp(), monster.isControllerHasAggro(), realskill, level));
            if (slea.available() != 8L) {
                return;
            }
            MovementParse.updatePosition(res, (AnimatedMapleMapObject)monster, -1);
            map.moveMonster(monster, monster.getPosition());
            map.broadcastMessage(chr, MobPacket.moveMonster(useSkill, (int)skill, unk2, monster.getObjectId(), startPos, monster.getPosition(), res), monster.getPosition());
        }
    }
    
    public static void CheckMobVac(final MapleClient c, final MapleMonster monster, final List<LifeMovementFragment> res, final Point startPos) {
        final MapleCharacter chr = c.getPlayer();
        try {
            final boolean fly = monster.getStats().getFly();
            Point endPos = null;
            int reduce_x = 0;
            int reduce_y = 0;
            for (final LifeMovementFragment move : res) {
                if (move instanceof AbstractLifeMovement) {
                    endPos = ((LifeMovement)move).getPosition();
                    try {
                        reduce_x = Math.abs(startPos.x - endPos.x);
                        reduce_y = Math.abs(startPos.y - endPos.y);
                    }
                    catch (Exception ex) {}
                }
            }
            if (!fly) {
                int GeneallyDistance_y = 150;
                int GeneallyDistance_x = 200;
                int Check_x = 250;
                int max_x = 450;
                switch (chr.getMapId()) {
                    case 100040001:
                    case 926013500: {
                        GeneallyDistance_y = 200;
                        break;
                    }
                    case 200010300: {
                        GeneallyDistance_x = 1000;
                        GeneallyDistance_y = 500;
                        break;
                    }
                    case 220010600:
                    case 926013300: {
                        GeneallyDistance_x = 200;
                        break;
                    }
                    case 211040001: {
                        GeneallyDistance_x = 220;
                        break;
                    }
                    case 101030105: {
                        GeneallyDistance_x = 250;
                        break;
                    }
                    case 541020500: {
                        Check_x = 300;
                        break;
                    }
                }
                switch (monster.getId()) {
                    case 4230100: {
                        GeneallyDistance_y = 200;
                        break;
                    }
                    case 9410066: {
                        Check_x = 1000;
                        break;
                    }
                }
                if (GeneallyDistance_x > max_x) {
                    max_x = GeneallyDistance_x;
                }
                if (((reduce_x > GeneallyDistance_x || reduce_y > GeneallyDistance_y) && reduce_y != 0) || (reduce_x > Check_x && reduce_y == 0) || reduce_x > max_x) {
                    chr.add吸怪();
                    if (c.getPlayer().get吸怪() % 50 == 0 || reduce_x > max_x) {
                        final StringBuilder sb = new StringBuilder();
                        sb.append("\r\n");
                        sb.append(FileoutputUtil.NowTime());
                        sb.append(" 玩家: ");
                        sb.append(StringUtil.getRightPaddedStr(c.getPlayer().getName(), ' ', 13));
                        sb.append("(编号:");
                        sb.append(StringUtil.getRightPaddedStr(String.valueOf(c.getPlayer().getId()), ' ', 5));
                        sb.append(" )怪物: ");
                        sb.append(StringUtil.getRightPaddedStr(String.valueOf(monster.getId()), ' ', 7));
                        sb.append("(");
                        sb.append(StringUtil.getRightPaddedStr(String.valueOf(monster.getObjectId()), ' ', 6));
                        sb.append(")");
                        sb.append(" 地图: ");
                        sb.append(StringUtil.getRightPaddedStr(String.valueOf(c.getPlayer().getMapId()), ' ', 9));
                        sb.append(" 初始座标:");
                        sb.append(StringUtil.getRightPaddedStr(String.valueOf(startPos.x), ' ', 4));
                        sb.append(",");
                        sb.append(StringUtil.getRightPaddedStr(String.valueOf(startPos.y), ' ', 4));
                        sb.append(" 移动座标:");
                        sb.append(StringUtil.getRightPaddedStr(String.valueOf(endPos.x), ' ', 4));
                        sb.append(",");
                        sb.append(StringUtil.getRightPaddedStr(String.valueOf(endPos.y), ' ', 4));
                        sb.append(" 相差座标:");
                        sb.append(StringUtil.getRightPaddedStr(String.valueOf(reduce_x), ' ', 4));
                        sb.append(",");
                        sb.append(StringUtil.getRightPaddedStr(String.valueOf(reduce_y), ' ', 4));
                        if (!chr.hasGmLevel(1)) {
                            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                                for (final MapleCharacter chr_ : cserv.getPlayerStorage().getAllCharactersThreadSafe()) {
                                    if (chr_.getAuto吸怪()) {
                                        chr_.warpAuto吸怪(chr);
                                    }
                                }
                            }
                        }
                        else {
                            c.getPlayer().dropMessage("触发吸怪 --  x: " + reduce_x + ", y: " + reduce_y);
                        }
                    }
                }
            }
        }
        catch (Exception ex2) {}
    }
    
    public static final void handleFriendlyDamage(final LittleEndianAccessor slea, final MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        final MapleMap map = chr.getMap();
        final MapleMonster mobfrom = map.getMonsterByOid(slea.readInt());
        slea.skip(4);
        final MapleMonster mobto = map.getMonsterByOid(slea.readInt());
        if (mobfrom != null && mobto != null && mobto.getStats().isFriendly()) {
            final int damage = mobto.getStats().getLevel() * Randomizer.nextInt(99) / 2;
            mobto.damage(chr, (long)damage, true);
            checkShammos(chr, mobto, map);
        }
    }
    
    public static final void checkShammos(final MapleCharacter chr, final MapleMonster mobto, final MapleMap map) {
        if (!mobto.isAlive() && mobto.getId() == 9300275) {
            for (final MapleCharacter chrz : map.getCharactersThreadsafe()) {
                if (chrz.getParty() != null && chrz.getParty().getLeader().getId() == chrz.getId()) {
                    if (chrz.haveItem(2022698)) {
                        MapleInventoryManipulator.removeById(chrz.getClient(), MapleInventoryType.USE, 2022698, 1, false, true);
                        mobto.heal((int)mobto.getMobMaxHp(), mobto.getMobMaxMp(), true);
                        return;
                    }
                    break;
                }
            }
            map.broadcastMessage(MaplePacketCreator.serverNotice(6, "Your party has failed to protect the monster."));
            final MapleMap mapp = chr.getClient().getChannelServer().getMapFactory().getMap(921120001);
            for (final MapleCharacter chrz2 : map.getCharactersThreadsafe()) {
                chrz2.changeMap(mapp, mapp.getPortal(0));
            }
        }
        else if (mobto.getId() == 9300275 && mobto.getEventInstance() != null) {
            mobto.getEventInstance().setProperty("HP", String.valueOf(mobto.getHp()));
        }
    }
    
    public static final void handleMonsterBomb(final LittleEndianAccessor slea, final MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        final MapleMonster monster = chr.getMap().getMonsterByOid(slea.readInt());
        if (monster == null || !chr.isAlive() || chr.isHidden() || (chr.getJob() != 421 && chr.getJob() != 422)) {
            return;
        }
        final byte selfd = monster.getStats().getSelfD();
        if (selfd != -1) {
            chr.getMap().killMonster(monster, chr, false, false, selfd);
        }
    }
    
    public static final void handleAutoAggro(final LittleEndianAccessor slea, final MapleClient c) {
        if (c.getPlayer() == null || c.getPlayer().getMap() == null || c.getPlayer().isHidden()) {
            return;
        }
        final MapleCharacter chr = c.getPlayer();
        final MapleMonster monster = chr.getMap().getMonsterByOid(slea.readInt());
        if (monster != null && chr.getPosition().distanceSq((Point2D)monster.getPosition()) < 200000.0) {
            if (monster.getController() != null) {
                if (chr.getMap().getCharacterById(monster.getController().getId()) == null) {
                    monster.switchController(chr, true);
                }
                else {
                    monster.switchController(monster.getController(), true);
                }
            }
            else {
                monster.switchController(chr, true);
            }
        }
    }
    
    public static final void HypnotizeDmg(final LittleEndianAccessor slea, final MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        final MapleMonster mob_from = chr.getMap().getMonsterByOid(slea.readInt());
        slea.skip(4);
        final int to = slea.readInt();
        slea.skip(1);
        final int damage = slea.readInt();
        final MapleMonster mob_to = chr.getMap().getMonsterByOid(to);
        if (mob_from != null && mob_to != null && mob_to.getStats().isFriendly()) {
            if (damage > 30000) {
                return;
            }
            mob_to.damage(chr, (long)damage, true);
            checkShammos(chr, mob_to, chr.getMap());
        }
    }
    
    public static final void handleDisplayNode(final LittleEndianAccessor slea, final MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        final MapleMonster mobFrom = chr.getMap().getMonsterByOid(slea.readInt());
        if (mobFrom != null) {
            chr.getClient().sendPacket(MaplePacketCreator.getNodeProperties(mobFrom, chr.getMap()));
        }
    }
    
    public static final void handleMobNode(final LittleEndianAccessor slea, final MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        final MapleMonster mob_from = chr.getMap().getMonsterByOid(slea.readInt());
        final int newNode = slea.readInt();
        final int nodeSize = chr.getMap().getNodes().size();
        if (mob_from != null && nodeSize > 0 && nodeSize >= newNode) {
            final MapleNodeInfo mni = chr.getMap().getNode(newNode);
            if (mni == null) {
                return;
            }
            if (mni.attr == 2) {
                chr.getMap().talkMonster("Please escort me carefully.", 5120035, mob_from.getObjectId());
            }
            if (mob_from.getLastNode() >= newNode) {
                return;
            }
            mob_from.setLastNode(newNode);
            if (nodeSize == newNode) {
                int newMap = -1;
                switch (chr.getMapId() / 100) {
                    case 9211200: {
                        newMap = 921120100;
                        break;
                    }
                    case 9211201: {
                        newMap = 921120200;
                        break;
                    }
                    case 9211202: {
                        newMap = 921120300;
                        break;
                    }
                    case 9211203: {
                        newMap = 921120400;
                        break;
                    }
                    case 9211204: {
                        chr.getMap().removeMonster(mob_from);
                        break;
                    }
                }
                if (newMap > 0) {
                    chr.getMap().broadcastMessage(MaplePacketCreator.serverNotice(5, "Proceed to the next stage."));
                    chr.getMap().removeMonster(mob_from);
                }
            }
        }
    }
}
