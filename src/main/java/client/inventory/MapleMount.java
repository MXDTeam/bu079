package client.inventory;

import server.Randomizer;
import server.Timer.MapTimer;
import client.MapleBuffStat;
import server.MapleInventoryManipulator;
import tools.MaplePacketCreator;
import constants.GameConstants;
import gui.Start;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import tools.FileoutputUtil;
import tools.FilePrinter;
import java.sql.Connection;
import java.util.concurrent.ScheduledFuture;
import client.MapleCharacter;
import java.lang.ref.WeakReference;
import java.io.Serializable;

public class MapleMount implements Serializable
{
    public byte fatigue;
    public byte level;
    private static final long serialVersionUID = 9179541993413738569L;
    private final int skillid;
    private int itemid;
    private int exp;
    private transient boolean changed;
    private long lastFatigue;
    private final transient WeakReference<MapleCharacter> owner;
    private ScheduledFuture<?> tirednessSchedule;
    private boolean Riding;
    
    public MapleMount(final MapleCharacter owner, final int id, final int skillid, final byte fatigue, final byte level, final int exp) {
        this.changed = false;
        this.lastFatigue = 0L;
        this.Riding = false;
        this.itemid = id;
        this.skillid = skillid;
        this.fatigue = fatigue;
        this.level = level;
        this.exp = exp;
        this.owner = new WeakReference(owner);
    }
    
    public void saveMount(final int charid, final Connection con) {
        if (!this.changed) {
            return;
        }
        try (final PreparedStatement ps = con.prepareStatement("UPDATE mountdata set `Level` = ?, `Exp` = ?, `Fatigue` = ? WHERE characterid = ?")) {
            ps.setByte(1, this.level);
            ps.setInt(2, this.exp);
            ps.setByte(3, this.fatigue);
            ps.setInt(4, charid);
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            FilePrinter.printError("MapleMount.txt", (Throwable)ex, "saveMount");
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
        }
    }
    
    public int getItemId() {
        return this.itemid;
    }
    
    public int getSkillId() {
        return this.skillid;
    }
    
    public byte getFatigue() {
        return this.fatigue;
    }
    
    public int getExp() {
        return this.exp;
    }
    
    public byte getLevel() {
        return this.level;
    }
    
    public void setItemId(final int c) {
        this.changed = true;
        this.itemid = c;
    }
    
    public void setFatigue(final byte amount) {
        this.changed = true;
        this.fatigue += amount;
        if (this.fatigue < 0) {
            this.fatigue = 0;
        }
    }
    
    public void setExp(final int c) {
        this.changed = true;
        this.exp = c;
    }
    
    public void setLevel(final byte c) {
        this.changed = true;
        this.level = c;
    }
    
    public void increaseFatigue() {
        final MapleCharacter chr = (MapleCharacter)this.owner.get();
        this.changed = true;
        ++this.fatigue;
        if (chr != null && ((Integer)Start.ConfigValuesMap.get("坐骑恢复开关")).intValue() > 0) {
            final int itemid = 2260000;
            final int toUse = chr.getItemQuantity(2260000, false);
            final MapleMount mount = chr.getMount();
            boolean pd = false;
            if (chr.getItemQuantity(((Integer)Start.ConfigValuesMap.get("坐骑恢复道具")).intValue(), false) > 0) {
                pd = true;
            }
            if (pd && this.fatigue >= ((Integer)Start.ConfigValuesMap.get("坐骑恢复频率")).intValue() && toUse > 0) {
                final int fatigue = mount.getFatigue();
                boolean levelup = false;
                mount.setFatigue((byte)(-30));
                if (fatigue > 0) {
                    mount.increaseExp();
                    final int level = mount.getLevel();
                    if (level < 30 && mount.getExp() >= GameConstants.getMountExpNeededForLevel(level + 1)) {
                        mount.setLevel((byte)(level + 1));
                        levelup = true;
                    }
                }
                chr.getMap().broadcastMessage(MaplePacketCreator.updateMount(chr, levelup));
                if (chr != null) {
                    MapleInventoryManipulator.removeById(chr.getClient(), MapleInventoryType.USE, 2260000, 1, false, false);
                    chr.getClient().getSession().write(MaplePacketCreator.getShowItemGain(2260000, (short)(-1), true));
                    chr.dropMessage(5, "VIP自动使用骑宠疲劳药水,目前骑宠疲劳:" + fatigue);
                }
            }
        }
        if (chr != null && chr.getMap() != null) {
            chr.getMap().broadcastMessage(MaplePacketCreator.updateMount(chr, false));
        }
        if (((Integer)Start.ConfigValuesMap.get("坐骑不饥饿开关")).intValue() > 0) {
            this.fatigue = 100;
        }
        if (this.fatigue > 99) {
            this.fatigue = 95;
            if (chr != null) {
                chr.cancelEffectFromBuffStat(MapleBuffStat.MONSTER_RIDING);
            }
        }
    }
    
    public final boolean canTire(final long now) {
        return this.lastFatigue > 0L && this.lastFatigue + 30000L < now;
    }
    
    public long getTiredness() {
        return this.lastFatigue;
    }
    
    public void startSchedule() {
        this.changed = true;
        this.tirednessSchedule = MapTimer.getInstance().register((Runnable)new Runnable() {
            @Override
            public void run() {
                if (MapleMount.this.Riding) {
                    MapleMount.this.increaseFatigue();
                    if (MapleMount.this.owner.get() != null) {}
                }
                else {
                    MapleMount.this.Riding = true;
                }
            }
        }, 60000L);
    }
    
    public void cancelSchedule() {
        if (this.tirednessSchedule != null) {
            this.Riding = false;
            this.tirednessSchedule.cancel(false);
        }
    }
    
    public void increaseExp() {
        int e;
        if (this.level >= 1 && this.level <= 7) {
            e = Randomizer.nextInt(10) + 15;
        }
        else if (this.level >= 8 && this.level <= 15) {
            e = Randomizer.nextInt(13) + 7;
        }
        else if (this.level >= 16 && this.level <= 24) {
            e = Randomizer.nextInt(23) + 9;
        }
        else {
            e = Randomizer.nextInt(28) + 12;
        }
        this.setExp(this.exp + e);
    }
}
