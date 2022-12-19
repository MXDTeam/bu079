package scripting;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import server.quest.MapleQuest;
import javax.script.Invocable;
import java.util.concurrent.locks.Lock;
import tools.FilePrinter;
import javax.script.ScriptException;
import tools.MaplePacketCreator;
import javax.script.ScriptEngine;
import java.util.WeakHashMap;
import client.MapleClient;
import java.util.Map;

public class NPCScriptManager extends AbstractScriptManager
{
    private final Map<MapleClient, NPCConversationManager> cms;
    private static final NPCScriptManager instance = new NPCScriptManager();
    
    public NPCScriptManager() {
        this.cms = (Map<MapleClient, NPCConversationManager>)new WeakHashMap();
    }
    
    public static final NPCScriptManager getInstance() {
        return NPCScriptManager.instance;
    }
    
    public final void start(final MapleClient c, final int npc) {
        this.start(c, npc, null);
    }
    
    public final void start(final MapleClient c, final int npc, final String script) {
        this.start(c, npc, 0, script);
    }
    
    public final void start(final MapleClient c, final int npc, final int mode, final String script) {
        final Lock lock = c.getNPCLock();
        lock.lock();
        try {
            if (c.getPlayer().getMobVac() || c.getPlayer().getItemVac()) {
                c.getPlayer().dropMessage(1, "开着挂机的时候不能操作");
                return;
            }
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系统提示]您已经建立与NPC:" + npc + ((script == null) ? "" : ("(" + script + ")")) + ((mode == 0) ? "" : ("型号: " + mode)) + "的对话。");
            }
            if (!this.cms.containsKey(c) && c.canClickNPC()) {
                if (c.getPlayer() != null && c.getPlayer().getDebugMessage()) {
                    c.getPlayer().dropMessage("start - !cms.containsKey(c) && c.canClickNPC()");
                }
                if (c.getPlayer() != null && c.getPlayer().getDebugMessage()) {
                    c.getPlayer().dropMessage("start - setInvocable");
                }
                Invocable iv;
                if (script == null) {
                    if (mode != 0) {
                        iv = this.getInvocable("npc/" + npc + "_" + mode + ".js", c, true);
                    }
                    else {
                        iv = this.getInvocable("npc/" + npc + ".js", c, true);
                    }
                }
                else {
                    iv = this.getInvocable("special/" + script + ".js", c, true);
                }
                if (iv == null) {
                    iv = this.getInvocable("special/notcoded.js", c, true);
                    if (iv == null) {
                        this.dispose(c);
                        return;
                    }
                }
                final ScriptEngine scriptengine = (ScriptEngine)iv;
                if (c.getPlayer() != null && c.getPlayer().getDebugMessage()) {
                    c.getPlayer().dropMessage("start - iv");
                }
                final NPCConversationManager cm = new NPCConversationManager(c, npc, -1, mode, script, (byte)(-1), iv);
                if (c.getPlayer() != null && c.getPlayer().getDebugMessage()) {
                    c.getPlayer().dropMessage("start - cm");
                }
                if (getInstance() == null) {
                    if (c.getPlayer() != null && c.getPlayer().getDebugMessage()) {
                        c.getPlayer().dropMessage("start = null");
                    }
                    this.dispose(c);
                    return;
                }
                this.cms.put(c, cm);
                if (c.getPlayer() != null && c.getPlayer().getDebugMessage()) {
                    c.getPlayer().dropMessage("start - cms");
                }
                scriptengine.put("cm", cm);
                if (c.getPlayer() != null) {
                    c.getPlayer().setConversation(1);
                }
                if (c.getPlayer() != null && c.getPlayer().getDebugMessage()) {
                    c.getPlayer().dropMessage("start - setConversation");
                }
                c.setClickedNPC();
                if (c.getPlayer() != null && c.getPlayer().getDebugMessage()) {
                    c.getPlayer().dropMessage("start - setClickNPC");
                }
                try {
                    iv.invokeFunction("start", new Object[0]);
                    if (c.getPlayer() != null && c.getPlayer().getDebugMessage()) {
                        c.getPlayer().dropMessage("start - cms");
                    }
                }
                catch (NoSuchMethodException nsme) {
                    iv.invokeFunction("action", Byte.valueOf((byte)1), Byte.valueOf((byte)0), Integer.valueOf(0));
                }
            }
            else if (c.getPlayer() != null) {
                getInstance().dispose(c);
                c.sendPacket(MaplePacketCreator.enableActions());
            }
        }
        catch (ScriptException ex) {}
        catch (NoSuchMethodException e) {
            System.err.println("NPC 脚本错误1, 它ID为 : " + npc + "_" + ((script == null) ? "" : ("(" + script + ")")) + ((mode == 0) ? "" : ("型号: " + mode)) + "." + e);
            if (c.getPlayer() != null && c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系统提示] NPC " + npc + "_" + ((script == null) ? "" : ("(" + script + ")")) + ((mode == 0) ? "" : ("型号: " + mode)) + "脚本错误 " + e + "");
            }
            FilePrinter.printError("NPCScriptManager1.txt", "Error executing NPC script, NPC ID : " + npc + "." + e);
            this.dispose(c);
        }
        finally {
            lock.unlock();
        }
    }
    
    public final void action(final MapleClient c, final byte mode, final byte type, final int selection) {
        if (mode != -1) {
            final NPCConversationManager cm = (NPCConversationManager)this.cms.get(c);
            if (cm == null || cm.getLastMsg() > -1) {
                return;
            }
            final Lock lock = c.getNPCLock();
            lock.lock();
            try {
                if (cm.pendingDisposal) {
                    this.dispose(c);
                }
                else {
                    c.setClickedNPC();
                    cm.getIv().invokeFunction("action", Byte.valueOf(mode), Byte.valueOf(type), Integer.valueOf(selection));
                }
            }
            catch (ScriptException ex) {}
            catch (NoSuchMethodException e) {
                if (c.getPlayer() != null && c.getPlayer().isGM()) {
                    c.getPlayer().dropMessage("[系统提示] NPC " + cm.getNpc() + "脚本错误 " + e + "");
                }
                System.err.println("NPC 脚本错误2, 它ID为 : " + cm.getNpc() + "_" + ((cm.getScript() == null) ? "" : ("(" + cm.getScript() + ")")) + ((cm.getMode() == 0) ? "" : ("型号: " + cm.getMode())) + "." + e);
                FilePrinter.printError("NPCScriptManager2.txt", "Error executing NPC script, NPC ID : " + cm.getNpc() + "_" + ((cm.getScript() == null) ? "" : ("(" + cm.getScript() + ")")) + ((cm.getMode() == 0) ? "" : ("型号: " + cm.getMode())) + "." + e);
                this.dispose(c);
            }
            finally {
                lock.unlock();
            }
        }
    }
    
    public final void startQuest(final MapleClient c, final int npc, final int quest) {
        if (!MapleQuest.getInstance(quest).canStart(c.getPlayer(), null)) {
            return;
        }
        final Lock lock = c.getNPCLock();
        lock.lock();
        try {
            if (!this.cms.containsKey(c) && c.canClickNPC()) {
                final Invocable iv = this.getInvocable("quest/" + quest + ".js", c, true);
                if (iv == null) {
                    c.getPlayer().dropMessage(1, "此任务尚未建置，请通知管理员。\r\n任务编号: " + quest);
                    this.dispose(c);
                    return;
                }
                final ScriptEngine scriptengine = (ScriptEngine)iv;
                final NPCConversationManager cm = new NPCConversationManager(c, npc, quest, 0, null, (byte)0, iv);
                this.cms.put(c, cm);
                scriptengine.put("qm", cm);
                c.getPlayer().setConversation(1);
                c.setClickedNPC();
                if (c.getPlayer().isGM()) {
                    c.getPlayer().dropMessage("[系统提示]您已经建立与任务脚本:" + quest + "的往来。");
                }
                iv.invokeFunction("start", Byte.valueOf((byte)1), Byte.valueOf((byte)0), Integer.valueOf(0));
            }
        }
        catch (ScriptException ex) {}
        catch (NoSuchMethodException e) {
            System.err.println("Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            FilePrinter.printError("NPCScriptManager.txt", "Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            this.dispose(c);
        }
        finally {
            lock.unlock();
        }
    }
    
    public final void startQuest(final MapleClient c, final byte mode, final byte type, final int selection) {
        final Lock lock = c.getNPCLock();
        final NPCConversationManager cm = (NPCConversationManager)this.cms.get(c);
        if (cm == null || cm.getLastMsg() > -1) {
            return;
        }
        lock.lock();
        try {
            if (cm.pendingDisposal) {
                this.dispose(c);
            }
            else {
                c.setClickedNPC();
                cm.getIv().invokeFunction("start", Byte.valueOf(mode), Byte.valueOf(type), Integer.valueOf(selection));
            }
        }
        catch (ScriptException ex) {}
        catch (NoSuchMethodException e) {
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系统提示]任务脚本:" + cm.getQuest() + "错误...NPC: " + cm.getNpc() + ":" + e);
            }
            System.err.println("Error executing Quest script. (" + cm.getQuest() + ")...NPC: " + cm.getNpc() + ":" + e);
            FilePrinter.printError("NPCScriptManager.txt", "Error executing Quest script. (" + cm.getQuest() + ")..NPCID: " + cm.getNpc() + ":" + e);
            this.dispose(c);
        }
        finally {
            lock.unlock();
        }
    }
    
    public final void endQuest(final MapleClient c, final int npc, final int quest, final boolean customEnd) {
        if (!customEnd && !MapleQuest.getInstance(quest).canComplete(c.getPlayer(), null)) {
            return;
        }
        final Lock lock = c.getNPCLock();
        lock.lock();
        try {
            if (!this.cms.containsKey(c) && c.canClickNPC()) {
                final Invocable iv = this.getInvocable("quest/" + quest + ".js", c, true);
                if (iv == null) {
                    this.dispose(c);
                    return;
                }
                final ScriptEngine scriptengine = (ScriptEngine)iv;
                final NPCConversationManager cm = new NPCConversationManager(c, npc, quest, 0, null, (byte)1, iv);
                this.cms.put(c, cm);
                scriptengine.put("qm", cm);
                c.getPlayer().setConversation(1);
                c.setClickedNPC();
                iv.invokeFunction("end", Byte.valueOf((byte)1), Byte.valueOf((byte)0), Integer.valueOf(0));
            }
        }
        catch (ScriptException ex) {}
        catch (NoSuchMethodException e) {
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系统提示]任务脚本:" + quest + "错误...NPC: " + quest + ":" + e);
            }
            System.err.println("Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            FilePrinter.printError("NPCScriptManager.txt", "Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            this.dispose(c);
        }
        finally {
            lock.unlock();
        }
    }
    
    public final void endQuest(final MapleClient c, final byte mode, final byte type, final int selection) {
        final Lock lock = c.getNPCLock();
        final NPCConversationManager cm = (NPCConversationManager)this.cms.get(c);
        if (cm == null || cm.getLastMsg() > -1) {
            return;
        }
        lock.lock();
        try {
            if (cm.pendingDisposal) {
                this.dispose(c);
            }
            else {
                c.setClickedNPC();
                cm.getIv().invokeFunction("end", Byte.valueOf(mode), Byte.valueOf(type), Integer.valueOf(selection));
            }
        }
        catch (ScriptException ex) {}
        catch (NoSuchMethodException e) {
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系统提示]任务脚本:" + cm.getQuest() + "错误...NPC: " + cm.getNpc() + ":" + e);
            }
            System.err.println("Error executing Quest script. (" + cm.getQuest() + ")...NPC: " + cm.getNpc() + ":" + e);
            FilePrinter.printError("NPCScriptManager.txt", "Error executing Quest script. (" + cm.getQuest() + ")..NPCID: " + cm.getNpc() + ":" + e);
            this.dispose(c);
        }
        finally {
            lock.unlock();
        }
    }
    
    public final void dispose(final MapleClient c) {
        final NPCConversationManager npccm = (NPCConversationManager)this.cms.get(c);
        if (npccm != null) {
            this.cms.remove(c);
            if (npccm.getType() == -1) {
                c.removeScriptEngine("scripts/npc/" + npccm.getNpc() + ".js");
                if (npccm.getMode() != 0) {
                    c.removeScriptEngine("scripts/npc/" + npccm.getNpc() + "_" + npccm.getMode() + ".js");
                }
                c.removeScriptEngine("scripts/special/" + npccm.getScript() + ".js");
                c.removeScriptEngine("scripts/special/notcoded.js");
            }
            else {
                c.removeScriptEngine("scripts/quest/" + npccm.getQuest() + ".js");
            }
        }
        if (c.getPlayer() != null && c.getPlayer().getConversation() == 1) {
            c.getPlayer().setConversation(0);
        }
    }
    
    public final NPCConversationManager getCM(final MapleClient c) {
        return (NPCConversationManager)this.cms.get(c);
    }
    
    public final void onUserEnter(final MapleClient c, final String scriptname) {
        try {
            if (c.getPlayer().isAdmin()) {
                c.getPlayer().dropMessage(5, "[地图脚本] 执行onUserEnter脚本：" + scriptname + " 地图：" + c.getPlayer().getMap().getMapName());
            }
            final Invocable iv = this.getInvocable("map/onUserEnter/" + scriptname + ".js", c, true);
            final ScriptEngine scriptEngine = (ScriptEngine)iv;
            final NPCConversationManager ms = new NPCConversationManager(c, c.getPlayer().getMap().getId(), -1, 0, scriptname, (byte)(-1), iv);
            if (this.cms.containsValue(ms)) {
                if (c.getPlayer().isAdmin()) {
                    c.getPlayer().dropMessage(5, "无法执行脚本:已有脚本执行 - " + this.cms.containsKey(c));
                }
                this.dispose(c);
                return;
            }
            if (iv == null || getInstance() == null) {
                if (iv == null && c.getPlayer().isAdmin()) {
                    c.getPlayer().dropMessage(5, "[系统提示]:没有找到事件:" + scriptname + "脚本在map/onUserEnter里");
                }
                this.dispose(c);
                return;
            }
            this.cms.put(c, ms);
            scriptEngine.put("ms", ms);
            c.getPlayer().setConversation(1);
            c.setClickedNPC();
            try {
                iv.invokeFunction("start", new Object[0]);
            }
            catch (NoSuchMethodException nsme) {
                iv.invokeFunction("action", Byte.valueOf((byte)1), Byte.valueOf((byte)0), Integer.valueOf(0));
            }
        }
        catch (NoSuchMethodException | ScriptException ex2) {
            FilePrinter.printError("OnUserEnter.txt", "地图脚本 : " + scriptname + ", 型态 : onUserEnter - 地图ID " + c.getPlayer().getMapId());
            System.err.println("地图脚本 : " + scriptname + ", 型态 : onUserEnter - 地图ID " + c.getPlayer().getMapId() + ":" + ex2);
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系统提示]地图脚本:" + scriptname + "型态 : onUserEnter 错误...地图ID: " + c.getPlayer().getMap().getId() + ":" + ex2);
            }
            this.dispose(c);
        }
    }
    
    public final void onFirstUserEnter(final MapleClient c, final String scriptname) {
        try {
            if (c.getPlayer().isAdmin()) {
                c.getPlayer().dropMessage(5, "[地图脚本] 执行onFirstUserEnter脚本：" + scriptname + " 地图：" + c.getPlayer().getMap().getMapName());
            }
            if (this.cms.containsKey(c)) {
                if (c.getPlayer().isAdmin()) {
                    c.getPlayer().dropMessage(5, "无法执行脚本:已有脚本执行 - " + this.cms.containsKey(c));
                }
                this.dispose(c);
                return;
            }
            final Invocable iv = this.getInvocable("map/onFirstUserEnter/" + scriptname + ".js", c, true);
            final ScriptEngine scriptEngine = (ScriptEngine)iv;
            final NPCConversationManager ms = new NPCConversationManager(c, c.getPlayer().getMap().getId(), -1, 0, scriptname, (byte)(-1), iv);
            if (iv == null || getInstance() == null) {
                if (iv != null && c.getPlayer().isAdmin()) {
                    c.getPlayer().dropMessage(5, "[系统提示]:没有找到事件:" + scriptname + "脚本在map/onFirstUserEnter里");
                }
                this.dispose(c);
                return;
            }
            this.cms.put(c, ms);
            scriptEngine.put("ms", ms);
            c.getPlayer().setConversation(1);
            c.setClickedNPC();
            try {
                iv.invokeFunction("start", new Object[0]);
            }
            catch (NoSuchMethodException nsme) {
                iv.invokeFunction("action", Byte.valueOf((byte)1), Byte.valueOf((byte)0), Integer.valueOf(0));
            }
        }
        catch (NoSuchMethodException | ScriptException ex2) {
            FilePrinter.printError("OnFirstUserEnter.txt", "地图脚本 : " + scriptname + ", 型态 : onFirstUserEnter - 地图ID " + c.getPlayer().getMapId() + ex2);
            System.err.println("地图脚本 : " + scriptname + ", 型态 : OnFirstUserEnter - 地图ID " + c.getPlayer().getMapId() + ":" + ex2);
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系统提示]地图脚本:" + scriptname + "型态 : onFirstUserEnter 错误...地图ID: " + c.getPlayer().getMap().getId() + ":" + ex2);
            }
            this.dispose(c);
        }
    }
    
    public final void cleanCMS() {
        final List<MapleClient> clients = (List<MapleClient>)new ArrayList();
        for (final MapleClient c : this.cms.keySet()) {
            if (c == null || c.getSession() == null || !c.getSession().isActive()) {
                clients.add(c);
            }
        }
        for (final MapleClient c : clients) {
            this.cms.remove(c);
        }
    }
}
