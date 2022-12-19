package server.maps;

import provider.MapleDataProviderFactory;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import tools.FileoutputUtil;
import database.DBConPool;
import java.awt.Rectangle;
import server.BossInMap;
import tools.StringUtil;
import server.life.MapleNPC;
import server.life.MapleLifeFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import gui.Start;
import server.life.MapleMonster;
import java.awt.Point;
import java.util.LinkedList;
import server.PortalFactory;
import provider.MapleDataTool;
import java.util.HashMap;
import server.life.AbstractLoadedMapleLife;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;

public class MapleMapFactory{
    private static final MapleDataProvider source = MapleDataProviderFactory.getDataProvider("Map.wz");
    private static final MapleData nameData = MapleDataProviderFactory.getDataProvider("String.wz").getData("Map.img");
    private final Map<Integer, MapleMap> maps;
    private final Map<Integer, MapleMap> instanceMap;
    private static final Map<Integer, MapleNodes> mapInfos = new HashMap();
    private final ReentrantLock lock;
    private static final Map<Integer, List<AbstractLoadedMapleLife>> customLife = new HashMap();
    private static final Map<Integer, List<AbstractLoadedMapleLife>> RemovecustomLife = new HashMap();
    private final Map<Integer, Integer> DeStorymaps;
    private int channel;
    private static boolean changed = false;
    
    public MapleMapFactory() {
        this.maps = (Map<Integer, MapleMap>)new HashMap();
        this.instanceMap = (Map<Integer, MapleMap>)new HashMap();
        this.lock = new ReentrantLock(true);
        this.DeStorymaps = (Map<Integer, Integer>)new HashMap();
    }
    
    public final MapleMap getMap(final int mapid) {
        return this.getMap(mapid, true, true, true);
    }
    
    public final MapleMap getMap(final int mapid, final boolean respawns, final boolean npcs) {
        return this.getMap(mapid, respawns, npcs, true);
    }
    
    public final MapleMap getMap(final int mapid, final boolean respawns, final boolean npcs, final boolean reactors) {
        final Integer omapid = Integer.valueOf(mapid);
        MapleMap map = (MapleMap)this.maps.get(omapid);
        if (map == null) {
            this.lock.lock();
            try {
                if (this.DeStorymaps.get(omapid) != null) {
                    return null;
                }
                map = (MapleMap)this.maps.get(omapid);
                if (map != null) {
                    return map;
                }
                MapleData mapData = MapleMapFactory.source.getData(this.getMapName(mapid));
                final MapleData link = mapData.getChildByPath("info/link");
                if (link != null) {
                    mapData = MapleMapFactory.source.getData(this.getMapName(MapleDataTool.getIntConvert("info/link", mapData)));
                }
                float monsterRate = 0.0f;
                if (respawns) {
                    final MapleData mobRate = mapData.getChildByPath("info/mobRate");
                    if (mobRate != null) {
                        monsterRate = ((Float)mobRate.getData()).floatValue();
                    }
                }
                map = new MapleMap(mapid, this.channel, MapleDataTool.getInt("info/returnMap", mapData), monsterRate);
                final PortalFactory portalFactory = new PortalFactory();
                for (final MapleData portal : mapData.getChildByPath("portal")) {
                    map.addPortal(portalFactory.makePortal(MapleDataTool.getInt(portal.getChildByPath("pt")), portal));
                }
                map.setTop(MapleDataTool.getInt(mapData.getChildByPath("info/VRTop"), 0));
                map.setLeft(MapleDataTool.getInt(mapData.getChildByPath("info/VRLeft"), 0));
                map.setBottom(MapleDataTool.getInt(mapData.getChildByPath("info/VRBottom"), 0));
                map.setRight(MapleDataTool.getInt(mapData.getChildByPath("info/VRRight"), 0));
                final List<MapleFoothold> allFootholds = (List<MapleFoothold>)new LinkedList();
                final Point lBound = new Point();
                final Point uBound = new Point();
                for (final MapleData footRoot : mapData.getChildByPath("foothold")) {
                    for (final MapleData footCat : footRoot) {
                        for (final MapleData footHold : footCat) {
                            final MapleFoothold fh = new MapleFoothold(new Point(MapleDataTool.getInt(footHold.getChildByPath("x1")), MapleDataTool.getInt(footHold.getChildByPath("y1"))), new Point(MapleDataTool.getInt(footHold.getChildByPath("x2")), MapleDataTool.getInt(footHold.getChildByPath("y2"))), Integer.parseInt(footHold.getName()));
                            fh.setPrev((short)MapleDataTool.getInt(footHold.getChildByPath("prev")));
                            fh.setNext((short)MapleDataTool.getInt(footHold.getChildByPath("next")));
                            if (fh.getX1() < lBound.x) {
                                lBound.x = fh.getX1();
                            }
                            if (fh.getX2() > uBound.x) {
                                uBound.x = fh.getX2();
                            }
                            if (fh.getY1() < lBound.y) {
                                lBound.y = fh.getY1();
                            }
                            if (fh.getY2() > uBound.y) {
                                uBound.y = fh.getY2();
                            }
                            allFootholds.add(fh);
                        }
                    }
                }
                final MapleFootholdTree fTree = new MapleFootholdTree(lBound, uBound);
                for (final MapleFoothold foothold : allFootholds) {
                    fTree.insert(foothold);
                }
                map.setFootholds(fTree);
                if (map.getTop() == 0) {
                    map.setTop(lBound.y);
                }
                if (map.getBottom() == 0) {
                    map.setBottom(uBound.y);
                }
                if (map.getLeft() == 0) {
                    map.setLeft(lBound.x);
                }
                if (map.getRight() == 0) {
                    map.setRight(uBound.x);
                }
                int bossid = -1;
                String msg = null;
                if (mapData.getChildByPath("info/timeMob") != null) {
                    bossid = MapleDataTool.getInt(mapData.getChildByPath("info/timeMob/id"), 0);
                    msg = MapleDataTool.getString(mapData.getChildByPath("info/timeMob/message"), null);
                }
                for (final MapleData life : mapData.getChildByPath("life")) {
                    final String type = MapleDataTool.getString(life.getChildByPath("type"));
                    if (npcs || !type.equals("n")) {
                        final AbstractLoadedMapleLife myLife = this.loadLife(life, MapleDataTool.getString(life.getChildByPath("id")), type);
                        if (myLife instanceof MapleMonster) {
                            final MapleMonster mob = (MapleMonster)myLife;
                            final byte team = (byte)MapleDataTool.getInt("team", life, -1);
                            final int mobTime = MapleDataTool.getInt("mobTime", life, 0);
                            map.addMonsterSpawn(mob, MapleDataTool.getInt("mobTime", life, 0), (byte)MapleDataTool.getInt("team", life, -1), (mob.getId() == bossid) ? msg : null);
                        }
                        else {
                            if (myLife == null) {
                                continue;
                            }
                            map.addMapObject((MapleMapObject)myLife);
                        }
                    }
                }
                final List<AbstractLoadedMapleLife> custom = MapleMapFactory.customLife.get(Integer.valueOf(mapid));
                if (custom != null) {
                    for (final AbstractLoadedMapleLife n : custom) {
                        final String cType = n.getCType();
                        switch (cType) {
                            case "n": {
                                map.addMapObject((MapleMapObject)n);
                                continue;
                            }
                        }
                    }
                }
                this.addAreaBossSpawn(map);
                map.setCreateMobInterval((short)MapleDataTool.getInt(mapData.getChildByPath("info/createMobInterval"), (int)Short.parseShort(String.valueOf(Start.ConfigValuesMap.get("地图刷新频率")))));
                map.loadMonsterRate(true);
                map.setNodes(this.loadNodes(mapid, mapData));
                if (reactors && mapData.getChildByPath("reactor") != null) {
                    for (final MapleData reactor : mapData.getChildByPath("reactor")) {
                        final String id = MapleDataTool.getString(reactor.getChildByPath("id"));
                        if (id != null) {
                            map.spawnReactor(this.loadReactor(reactor, id, (byte)MapleDataTool.getInt(reactor.getChildByPath("f"), 0)));
                        }
                    }
                }
                try {
                    map.setMapName(MapleDataTool.getString("mapName", MapleMapFactory.nameData.getChildByPath(this.getMapStringName(omapid.intValue())), ""));
                    map.setStreetName(MapleDataTool.getString("streetName", MapleMapFactory.nameData.getChildByPath(this.getMapStringName(omapid.intValue())), ""));
                }
                catch (Exception e) {
                    map.setMapName("");
                    map.setStreetName("");
                }
                map.setClock(mapData.getChildByPath("clock") != null);
                map.setEverlast(MapleDataTool.getInt(mapData.getChildByPath("info/everlast"), 0) > 0);
                map.setTown(MapleDataTool.getInt(mapData.getChildByPath("info/town"), 0) > 0);
                map.setSoaring(MapleDataTool.getInt(mapData.getChildByPath("info/needSkillForFly"), 0) > 0);
                map.setPersonalShop(MapleDataTool.getInt(mapData.getChildByPath("info/personalShop"), 0) > 0);
                map.setForceMove(MapleDataTool.getInt(mapData.getChildByPath("info/lvForceMove"), 0));
                map.setHPDec(MapleDataTool.getInt(mapData.getChildByPath("info/decHP"), 0));
                map.setHPDecInterval(MapleDataTool.getInt(mapData.getChildByPath("info/decHPInterval"), 10000));
                map.setHPDecProtect(MapleDataTool.getInt(mapData.getChildByPath("info/protectItem"), 0));
                map.setBoat(mapData.getChildByPath("shipObj") != null);
                map.setForcedReturnMap(MapleDataTool.getInt(mapData.getChildByPath("info/forcedReturn"), 999999999));
                map.setTimeLimit(MapleDataTool.getInt(mapData.getChildByPath("info/timeLimit"), -1));
                map.setFieldLimit(MapleDataTool.getInt(mapData.getChildByPath("info/fieldLimit"), 0));
                map.setFirstUserEnter(MapleDataTool.getString(mapData.getChildByPath("info/onFirstUserEnter"), ""));
                map.setUserEnter(MapleDataTool.getString(mapData.getChildByPath("info/onUserEnter"), ""));
                map.setRecoveryRate(MapleDataTool.getFloat(mapData.getChildByPath("info/recovery"), 1.0f));
                map.setFixedMob(MapleDataTool.getInt(mapData.getChildByPath("info/fixedMobCapacity"), 0));
                map.setConsumeItemCoolTime(MapleDataTool.getInt(mapData.getChildByPath("info/consumeItemCoolTime"), 0));
                this.maps.put(omapid, map);
            }
            finally {
                this.lock.unlock();
            }
        }
        else if (MapleMapFactory.changed) {
            final List<AbstractLoadedMapleLife> custom2 = MapleMapFactory.customLife.get(Integer.valueOf(mapid));
            if (custom2 != null) {
                for (final AbstractLoadedMapleLife n2 : custom2) {
                    final String cType2 = n2.getCType();
                    switch (cType2) {
                        case "n": {
                            map.addMapObject((MapleMapObject)n2);
                            continue;
                        }
                    }
                }
            }
        }
        return map;
    }
    
    public MapleMap getInstanceMap(final int instanceid) {
        return (MapleMap)this.instanceMap.get(Integer.valueOf(instanceid));
    }
    
    public void removeInstanceMap(final int instanceid) {
        if (this.isInstanceMapLoaded(instanceid)) {
            this.getInstanceMap(instanceid).checkStates("");
            this.instanceMap.remove(Integer.valueOf(instanceid));
        }
    }
    
    public void removeMap(final int instanceid) {
        if (this.isMapLoaded(instanceid)) {
            this.getMap(instanceid).checkStates("");
            this.maps.remove(Integer.valueOf(instanceid));
        }
    }
    
    public MapleMap CreateInstanceMap(final int mapid, final boolean respawns, final boolean npcs, final boolean reactors, final int instanceid) {
        if (this.isInstanceMapLoaded(instanceid)) {
            return this.getInstanceMap(instanceid);
        }
        MapleData mapData = MapleMapFactory.source.getData(this.getMapName(mapid));
        final MapleData link = mapData.getChildByPath("info/link");
        if (link != null) {
            mapData = MapleMapFactory.source.getData(this.getMapName(MapleDataTool.getIntConvert("info/link", mapData)));
        }
        float monsterRate = 0.0f;
        if (respawns) {
            final MapleData mobRate = mapData.getChildByPath("info/mobRate");
            if (mobRate != null) {
                monsterRate = ((Float)mobRate.getData()).floatValue();
            }
        }
        final MapleMap map = new MapleMap(mapid, this.channel, MapleDataTool.getInt("info/returnMap", mapData), monsterRate);
        final PortalFactory portalFactory = new PortalFactory();
        for (final MapleData portal : mapData.getChildByPath("portal")) {
            map.addPortal(portalFactory.makePortal(MapleDataTool.getInt(portal.getChildByPath("pt")), portal));
        }
        map.setTop(MapleDataTool.getInt(mapData.getChildByPath("info/VRTop"), 0));
        map.setLeft(MapleDataTool.getInt(mapData.getChildByPath("info/VRLeft"), 0));
        map.setBottom(MapleDataTool.getInt(mapData.getChildByPath("info/VRBottom"), 0));
        map.setRight(MapleDataTool.getInt(mapData.getChildByPath("info/VRRight"), 0));
        final List<MapleFoothold> allFootholds = (List<MapleFoothold>)new LinkedList();
        final Point lBound = new Point();
        final Point uBound = new Point();
        for (final MapleData footRoot : mapData.getChildByPath("foothold")) {
            for (final MapleData footCat : footRoot) {
                for (final MapleData footHold : footCat) {
                    final MapleFoothold fh = new MapleFoothold(new Point(MapleDataTool.getInt(footHold.getChildByPath("x1")), MapleDataTool.getInt(footHold.getChildByPath("y1"))), new Point(MapleDataTool.getInt(footHold.getChildByPath("x2")), MapleDataTool.getInt(footHold.getChildByPath("y2"))), Integer.parseInt(footHold.getName()));
                    fh.setPrev((short)MapleDataTool.getInt(footHold.getChildByPath("prev")));
                    fh.setNext((short)MapleDataTool.getInt(footHold.getChildByPath("next")));
                    if (fh.getX1() < lBound.x) {
                        lBound.x = fh.getX1();
                    }
                    if (fh.getX2() > uBound.x) {
                        uBound.x = fh.getX2();
                    }
                    if (fh.getY1() < lBound.y) {
                        lBound.y = fh.getY1();
                    }
                    if (fh.getY2() > uBound.y) {
                        uBound.y = fh.getY2();
                    }
                    allFootholds.add(fh);
                }
            }
        }
        final MapleFootholdTree fTree = new MapleFootholdTree(lBound, uBound);
        for (final MapleFoothold fh2 : allFootholds) {
            fTree.insert(fh2);
        }
        map.setFootholds(fTree);
        int bossid = -1;
        String msg = null;
        if (mapData.getChildByPath("info/timeMob") != null) {
            bossid = MapleDataTool.getInt(mapData.getChildByPath("info/timeMob/id"), 0);
            msg = MapleDataTool.getString(mapData.getChildByPath("info/timeMob/message"), null);
        }
        for (final MapleData life : mapData.getChildByPath("life")) {
            final String type = MapleDataTool.getString(life.getChildByPath("type"));
            if (npcs || !type.equals("n")) {
                final AbstractLoadedMapleLife myLife = this.loadLife(life, MapleDataTool.getString(life.getChildByPath("id")), type);
                if (myLife instanceof MapleMonster) {
                    final MapleMonster mob = (MapleMonster)myLife;
                    map.addMonsterSpawn(mob, MapleDataTool.getInt("mobTime", life, 0), (byte)MapleDataTool.getInt("team", life, -1), (mob.getId() == bossid) ? msg : null);
                }
                else {
                    map.addMapObject((MapleMapObject)myLife);
                }
            }
        }
        this.addAreaBossSpawn(map);
        map.setCreateMobInterval((short)MapleDataTool.getInt(mapData.getChildByPath("info/createMobInterval"), (int)Short.parseShort(String.valueOf(Start.ConfigValuesMap.get("地图刷新频率")))));
        map.loadMonsterRate(true);
        map.setNodes(this.loadNodes(mapid, mapData));
        if (reactors && mapData.getChildByPath("reactor") != null) {
            for (final MapleData reactor : mapData.getChildByPath("reactor")) {
                final String id = MapleDataTool.getString(reactor.getChildByPath("id"));
                if (id != null) {
                    map.spawnReactor(this.loadReactor(reactor, id, (byte)MapleDataTool.getInt(reactor.getChildByPath("f"), 0)));
                }
            }
        }
        try {
            map.setMapName(MapleDataTool.getString("mapName", MapleMapFactory.nameData.getChildByPath(this.getMapStringName(mapid)), ""));
            map.setStreetName(MapleDataTool.getString("streetName", MapleMapFactory.nameData.getChildByPath(this.getMapStringName(mapid)), ""));
        }
        catch (Exception e) {
            map.setMapName("");
            map.setStreetName("");
        }
        map.setClock(MapleDataTool.getInt(mapData.getChildByPath("info/clock"), 0) > 0);
        map.setEverlast(MapleDataTool.getInt(mapData.getChildByPath("info/everlast"), 0) > 0);
        map.setTown(MapleDataTool.getInt(mapData.getChildByPath("info/town"), 0) > 0);
        map.setSoaring(MapleDataTool.getInt(mapData.getChildByPath("info/needSkillForFly"), 0) > 0);
        map.setForceMove(MapleDataTool.getInt(mapData.getChildByPath("info/lvForceMove"), 0));
        map.setHPDec(MapleDataTool.getInt(mapData.getChildByPath("info/decHP"), 0));
        map.setHPDecInterval(MapleDataTool.getInt(mapData.getChildByPath("info/decHPInterval"), 10000));
        map.setHPDecProtect(MapleDataTool.getInt(mapData.getChildByPath("info/protectItem"), 0));
        map.setForcedReturnMap(MapleDataTool.getInt(mapData.getChildByPath("info/forcedReturn"), 999999999));
        map.setTimeLimit(MapleDataTool.getInt(mapData.getChildByPath("info/timeLimit"), -1));
        map.setFieldLimit(MapleDataTool.getInt(mapData.getChildByPath("info/fieldLimit"), 0));
        map.setFirstUserEnter(MapleDataTool.getString(mapData.getChildByPath("info/onFirstUserEnter"), ""));
        map.setUserEnter(MapleDataTool.getString(mapData.getChildByPath("info/onUserEnter"), ""));
        map.setRecoveryRate(MapleDataTool.getFloat(mapData.getChildByPath("info/recovery"), 1.0f));
        map.setFixedMob(MapleDataTool.getInt(mapData.getChildByPath("info/fixedMobCapacity"), 0));
        map.setConsumeItemCoolTime(MapleDataTool.getInt(mapData.getChildByPath("info/consumeItemCoolTime"), 0));
        this.instanceMap.put(Integer.valueOf(instanceid), map);
        return map;
    }
    
    public int getLoadedMaps() {
        return this.maps.size();
    }
    
    public boolean isMapLoaded(final int mapId) {
        return this.maps.containsKey(Integer.valueOf(mapId));
    }
    
    public boolean isInstanceMapLoaded(final int instanceid) {
        return this.instanceMap.containsKey(Integer.valueOf(instanceid));
    }
    
    public void clearLoadedMap() {
        this.maps.clear();
    }
    
    public Collection<MapleMap> getAllMaps() {
        return this.maps.values();
    }
    
    public Collection<MapleMap> getAllMapThreadSafe() {
        final Collection<MapleMap> ret = (Collection<MapleMap>)new ArrayList();
        ret.addAll(this.maps.values());
        return ret;
    }
    
    public Collection<MapleMap> getAllInstanceMaps() {
        return this.instanceMap.values();
    }
    
    private AbstractLoadedMapleLife loadLife(final MapleData life, final String id, final String type) {
        final AbstractLoadedMapleLife myLife = MapleLifeFactory.getLife(Integer.parseInt(id), type);
        if (myLife == null) {
            return null;
        }
        myLife.setCy(MapleDataTool.getInt(life.getChildByPath("cy")));
        final MapleData dF = life.getChildByPath("f");
        if (dF != null) {
            myLife.setF(MapleDataTool.getInt(dF));
        }
        myLife.setFh(MapleDataTool.getInt(life.getChildByPath("fh")));
        myLife.setRx0(MapleDataTool.getInt(life.getChildByPath("rx0")));
        myLife.setRx1(MapleDataTool.getInt(life.getChildByPath("rx1")));
        myLife.setPosition(new Point(MapleDataTool.getInt(life.getChildByPath("x")), MapleDataTool.getInt(life.getChildByPath("y"))));
        if (MapleDataTool.getInt("hide", life, 0) == 1 && myLife instanceof MapleNPC) {
            myLife.setHide(true);
        }
        return myLife;
    }
    
    private MapleReactor loadReactor(final MapleData reactor, final String id, final byte FacingDirection) {
        final MapleReactorStats stats = MapleReactorFactory.getReactor(Integer.parseInt(id));
        final MapleReactor myReactor = new MapleReactor(stats, Integer.parseInt(id));
        stats.setFacingDirection(FacingDirection);
        myReactor.setPosition(new Point(MapleDataTool.getInt(reactor.getChildByPath("x")), MapleDataTool.getInt(reactor.getChildByPath("y"))));
        myReactor.setDelay(MapleDataTool.getInt(reactor.getChildByPath("reactorTime")) * 1000);
        myReactor.setState((byte)0);
        myReactor.setName(MapleDataTool.getString(reactor.getChildByPath("name"), ""));
        return myReactor;
    }
    
    private String getMapName(final int mapid) {
        String mapName = StringUtil.getLeftPaddedStr(Integer.toString(mapid), '0', 9);
        final StringBuilder builder = new StringBuilder("Map/Map");
        builder.append(mapid / 100000000);
        builder.append("/");
        builder.append(mapName);
        builder.append(".img");
        mapName = builder.toString();
        return mapName;
    }
    
    private String getMapStringName(final int mapid) {
        final StringBuilder builder = new StringBuilder();
        if (mapid < 100000000) {
            builder.append("maple");
        }
        else if ((mapid >= 100000000 && mapid < 200000000) || mapid / 100000 == 5540) {
            builder.append("victoria");
        }
        else if (mapid >= 200000000 && mapid < 300000000) {
            builder.append("ossyria");
        }
        else if (mapid >= 300000000 && mapid < 400000000) {
            builder.append("elin");
        }
        else if (mapid >= 500000000 && mapid < 510000000) {
            builder.append("thai");
        }
        else if (mapid >= 540000000 && mapid < 600000000) {
            builder.append("SG");
        }
        else if (mapid >= 600000000 && mapid < 620000000) {
            builder.append("MasteriaGL");
        }
        else if ((mapid >= 670000000 && mapid < 677000000) || (mapid >= 678000000 && mapid < 682000000)) {
            builder.append("global");
        }
        else if (mapid >= 677000000 && mapid < 678000000) {
            builder.append("Episode1GL");
        }
        else if (mapid >= 682000000 && mapid < 683000000) {
            builder.append("HalloweenGL");
        }
        else if (mapid >= 683000000 && mapid < 684000000) {
            builder.append("event");
        }
        else if (mapid >= 684000000 && mapid < 685000000) {
            builder.append("event_5th");
        }
        else if (mapid >= 700000000 && mapid < 700000300) {
            builder.append("wedding");
        }
        else if (mapid >= 800000000 && mapid < 900000000) {
            builder.append("jp");
        }
        else if (mapid >= 700000000 && mapid < 782000002) {
            builder.append("chinese");
        }
        else {
            builder.append("etc");
        }
        builder.append("/");
        builder.append(mapid);
        return builder.toString();
    }
    
    public void setChannel(final int channel) {
        this.channel = channel;
    }
    
    private void addAreaBossSpawn(final MapleMap map) {
        int monsterid = -1;
        int mobtime = -1;
        String msg = null;
        Point pos1 = null;
        Point pos2 = null;
        Point pos3 = null;
        for (int a = 0; a < Start.野外boss刷新.size(); ++a) {
            if (map.getId() == ((BossInMap)Start.野外boss刷新.get(a)).getMap()) {
                mobtime = ((BossInMap)Start.野外boss刷新.get(a)).getTime() * 60;
                monsterid = ((BossInMap)Start.野外boss刷新.get(a)).getMobid();
                msg = ((BossInMap)Start.野外boss刷新.get(a)).getMsg();
                pos1 = new Point(((BossInMap)Start.野外boss刷新.get(a)).getX(), ((BossInMap)Start.野外boss刷新.get(a)).getY());
                pos2 = new Point(((BossInMap)Start.野外boss刷新.get(a)).getX(), ((BossInMap)Start.野外boss刷新.get(a)).getY());
                pos3 = new Point(((BossInMap)Start.野外boss刷新.get(a)).getX(), ((BossInMap)Start.野外boss刷新.get(a)).getY());
                break;
            }
        }
        if (monsterid > 0) {
            map.addAreaMonsterSpawn(MapleLifeFactory.getMonster(monsterid), pos1, pos2, pos3, mobtime, msg);
        }
    }
    
    private MapleNodes loadNodes(final int mapid, final MapleData mapData) {
        MapleNodes nodeInfo = (MapleNodes)MapleMapFactory.mapInfos.get(Integer.valueOf(mapid));
        if (nodeInfo == null) {
            nodeInfo = new MapleNodes(mapid);
            if (mapData.getChildByPath("nodeInfo") != null) {
                for (final MapleData node : mapData.getChildByPath("nodeInfo")) {
                    try {
                        final String name2 = node.getName();
                        switch (name2) {
                            case "start": {
                                nodeInfo.setNodeStart(MapleDataTool.getInt(node, 0));
                                continue;
                            }
                            case "end": {
                                nodeInfo.setNodeEnd(MapleDataTool.getInt(node, 0));
                                continue;
                            }
                            default: {
                                final List<Integer> edges = (List<Integer>)new ArrayList();
                                if (node.getChildByPath("edge") != null) {
                                    for (final MapleData edge : node.getChildByPath("edge")) {
                                        edges.add(Integer.valueOf(MapleDataTool.getInt(edge, -1)));
                                    }
                                }
                                final server.maps.MapleNodes.MapleNodeInfo mni = new server.maps.MapleNodes.MapleNodeInfo(Integer.parseInt(node.getName()), MapleDataTool.getIntConvert("key", node, 0), MapleDataTool.getIntConvert("x", node, 0), MapleDataTool.getIntConvert("y", node, 0), MapleDataTool.getIntConvert("attr", node, 0), edges);
                                nodeInfo.addNode(mni);
                                continue;
                            }
                        }
                    }
                    catch (NumberFormatException ex) {}
                }
                nodeInfo.sortNodes();
            }
            for (int i = 1; i <= 7; ++i) {
                if (mapData.getChildByPath(String.valueOf(i)) != null && mapData.getChildByPath(i + "/obj") != null) {
                    for (final MapleData node2 : mapData.getChildByPath(i + "/obj")) {
                        final int sn_count = MapleDataTool.getIntConvert("SN_count", node2, 0);
                        final String name = MapleDataTool.getString("name", node2, "");
                        final int speed = MapleDataTool.getIntConvert("speed", node2, 0);
                        if (sn_count > 0 && speed > 0) {
                            if (name.equals("")) {
                                continue;
                            }
                            final List<Integer> SN = (List<Integer>)new ArrayList();
                            for (int x = 0; x < sn_count; ++x) {
                                SN.add(Integer.valueOf(MapleDataTool.getIntConvert("SN" + x, node2, 0)));
                            }
                            final server.maps.MapleNodes.MaplePlatform mni2 = new server.maps.MapleNodes.MaplePlatform(name, MapleDataTool.getIntConvert("start", node2, 2), speed, MapleDataTool.getIntConvert("x1", node2, 0), MapleDataTool.getIntConvert("y1", node2, 0), MapleDataTool.getIntConvert("x2", node2, 0), MapleDataTool.getIntConvert("y2", node2, 0), MapleDataTool.getIntConvert("r", node2, 0), SN);
                            nodeInfo.addPlatform(mni2);
                        }
                    }
                }
            }
            if (mapData.getChildByPath("area") != null) {
                for (final MapleData area : mapData.getChildByPath("area")) {
                    final int x2 = MapleDataTool.getInt(area.getChildByPath("x1"));
                    final int y1 = MapleDataTool.getInt(area.getChildByPath("y1"));
                    final int x3 = MapleDataTool.getInt(area.getChildByPath("x2"));
                    final int y2 = MapleDataTool.getInt(area.getChildByPath("y2"));
                    final Rectangle mapArea = new Rectangle(x2, y1, x3 - x2, y2 - y1);
                    nodeInfo.addMapleArea(mapArea);
                }
            }
            if (mapData.getChildByPath("monsterCarnival") != null) {
                final MapleData mc = mapData.getChildByPath("monsterCarnival");
                if (mc.getChildByPath("mobGenPos") != null) {
                    for (final MapleData area2 : mc.getChildByPath("mobGenPos")) {
                        nodeInfo.addMonsterPoint(MapleDataTool.getInt(area2.getChildByPath("x")), MapleDataTool.getInt(area2.getChildByPath("y")), MapleDataTool.getInt(area2.getChildByPath("fh")), MapleDataTool.getInt(area2.getChildByPath("cy")), MapleDataTool.getInt("team", area2, -1));
                    }
                }
                if (mc.getChildByPath("mob") != null) {
                    for (final MapleData area2 : mc.getChildByPath("mob")) {
                        nodeInfo.addMobSpawn(MapleDataTool.getInt(area2.getChildByPath("id")), MapleDataTool.getInt(area2.getChildByPath("spendCP")));
                    }
                }
                if (mc.getChildByPath("guardianGenPos") != null) {
                    for (final MapleData area2 : mc.getChildByPath("guardianGenPos")) {
                        nodeInfo.addGuardianSpawn(new Point(MapleDataTool.getInt(area2.getChildByPath("x")), MapleDataTool.getInt(area2.getChildByPath("y"))), MapleDataTool.getInt("team", area2, -1));
                    }
                }
                if (mc.getChildByPath("skill") != null) {
                    for (final MapleData area2 : mc.getChildByPath("skill")) {
                        nodeInfo.addSkillId(MapleDataTool.getInt(area2));
                    }
                }
            }
            MapleMapFactory.mapInfos.put(Integer.valueOf(mapid), nodeInfo);
        }
        return nodeInfo;
    }
    
    public static int loadCustomLife() {
        return loadCustomLife(false, null);
    }
    
    public static int loadCustomLife(final boolean reload, final MapleMap map) {
        if (reload) {
            final int mid = map.getId();
            final List<AbstractLoadedMapleLife> custom = MapleMapFactory.RemovecustomLife.get(Integer.valueOf(mid));
            if (custom != null) {
                for (final AbstractLoadedMapleLife n : custom) {
                    final String cType = n.getCType();
                    switch (cType) {
                        case "n": {
                            map.removeNpc_(n.getId());
                            continue;
                        }
                    }
                }
            }
            MapleMapFactory.customLife.clear();
            MapleMapFactory.changed = true;
        }
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            try (final PreparedStatement ps = con.prepareStatement("SELECT * FROM `wz_customlife`");
                 final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final int mapid = rs.getInt("mid");
                    final AbstractLoadedMapleLife myLife = loadLife(rs.getInt("dataid"), rs.getInt("f"), rs.getByte("hide") > 0, rs.getInt("fh"), rs.getInt("cy"), rs.getInt("rx0"), rs.getInt("rx1"), rs.getInt("x"), rs.getInt("y"), rs.getString("type"), rs.getInt("mobtime"));
                    if (myLife == null) {
                        continue;
                    }
                    final List<AbstractLoadedMapleLife> entries = MapleMapFactory.customLife.get(Integer.valueOf(mapid));
                    final List<AbstractLoadedMapleLife> collections = (List<AbstractLoadedMapleLife>)new ArrayList();
                    if (entries == null) {
                        collections.add(myLife);
                        MapleMapFactory.RemovecustomLife.put(Integer.valueOf(mapid), collections);
                        MapleMapFactory.customLife.put(Integer.valueOf(mapid), collections);
                    }
                    else {
                        collections.addAll((Collection<? extends AbstractLoadedMapleLife>)entries);
                        collections.add(myLife);
                        MapleMapFactory.RemovecustomLife.put(Integer.valueOf(mapid), collections);
                        MapleMapFactory.customLife.put(Integer.valueOf(mapid), collections);
                    }
                }
                rs.close();
                ps.close();
            }
            con.close();
            return MapleMapFactory.customLife.size();
        }
        catch (SQLException e) {
            System.err.println("Error loading custom life..." + e);
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
            return -1;
        }
    }
    
    private static AbstractLoadedMapleLife loadLife(final int id, final int f, final boolean hide, final int fh, final int cy, final int rx0, final int rx1, final int x, final int y, final String type, final int mtime) {
        final AbstractLoadedMapleLife myLife = MapleLifeFactory.getLife(id, type);
        if (myLife == null) {
            System.err.println("自定 npc " + id + " 异常...");
            return null;
        }
        myLife.setCy(cy);
        myLife.setF(f);
        myLife.setFh(fh);
        myLife.setRx0(rx0);
        myLife.setRx1(rx1);
        myLife.setPosition(new Point(x, y));
        myLife.setHide(hide);
        myLife.setMTime(mtime);
        myLife.setCType(type);
        return myLife;
    }
    
    public boolean destroyMap(final int mapid) {
        return this.destroyMap(mapid, false);
    }
    
    public boolean destroyMap(final int mapid, final boolean Remove) {
        synchronized (this.maps) {
            if (this.maps.containsKey(Integer.valueOf(mapid))) {
                if (Remove) {
                    this.DeStorymaps.put(Integer.valueOf(mapid), Integer.valueOf(0));
                    this.maps.remove(Integer.valueOf(mapid));
                }
                return this.maps.remove(Integer.valueOf(mapid)) != null;
            }
        }
        return false;
    }
    
    public void HealMap(final int mapid) {
        synchronized (this.maps) {
            if (this.DeStorymaps.containsKey(Integer.valueOf(mapid))) {
                this.DeStorymaps.remove(Integer.valueOf(mapid));
            }
        }
    }
}
