package handling.channel.handler;

import client.MapleClient;
import tools.data.LittleEndianAccessor;
import tools.packet.MTSCSPacket;

/**
 *
 * @author 小灰灰
 */
public class ViciousHammerHandler {

    public static void VICIOUS_HAMMER(LittleEndianAccessor slea, MapleClient c) {
        int type = slea.readInt();
        if(type == 0x33){
            c.getPlayer().getClient().sendPacket(MTSCSPacket.sendHammerMessage(58));
        }
        if(type == 0x34){
            c.getPlayer().getClient().sendPacket(MTSCSPacket.sendHammerMessage(59));
        }
    }
}
