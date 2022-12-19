package client;

import java.sql.SQLException;
import java.util.Iterator;
import java.sql.PreparedStatement;
import tools.FileoutputUtil;
import java.util.Map.Entry;
import java.sql.Connection;
import tools.data.MaplePacketLittleEndianWriter;
import java.util.HashMap;
import tools.Pair;
import java.util.Map;
import java.io.Serializable;

public class MapleKeyLayout implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    private boolean changed;
    private final Map<Integer, Pair<Byte, Integer>> keymap;
    
    public MapleKeyLayout() {
        this.changed = false;
        this.keymap = (Map<Integer, Pair<Byte, Integer>>)new HashMap();
    }
    
    public MapleKeyLayout(final Map<Integer, Pair<Byte, Integer>> keys) {
        this.changed = false;
        this.keymap = keys;
    }
    
    public final Map<Integer, Pair<Byte, Integer>> Layout() {
        this.changed = true;
        return this.keymap;
    }
    
    public final void writeData(final MaplePacketLittleEndianWriter mplew) {
        for (int x = 0; x < 90; ++x) {
            final Pair<Byte, Integer> binding = this.keymap.get(Integer.valueOf(x));
            if (binding != null) {
                mplew.write(((Byte)binding.getLeft()).byteValue());
                mplew.writeInt(((Integer)binding.getRight()).intValue());
            }
            else {
                mplew.write(0);
                mplew.writeInt(0);
            }
        }
    }
    
    public final void saveKeys(final int charid, final Connection con) throws SQLException {
        if (!this.changed || this.keymap.isEmpty()) {
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM keymap WHERE characterid = ?");
            ps.setInt(1, charid);
            ps.execute();
            ps.close();
            boolean first = true;
            final StringBuilder query = new StringBuilder();
            for (final Entry<Integer, Pair<Byte, Integer>> keybinding : this.keymap.entrySet()) {
                if (first) {
                    first = false;
                    query.append("INSERT INTO keymap VALUES (");
                }
                else {
                    query.append(",(");
                }
                query.append("DEFAULT,");
                query.append(charid).append(",");
                query.append(((Integer)keybinding.getKey()).intValue()).append(",");
                query.append((int)((Byte)((Pair<Byte, Integer>)keybinding.getValue()).getLeft()).byteValue()).append(",");
                query.append(((Integer)((Pair<Byte, Integer>)keybinding.getValue()).getRight()).intValue()).append(")");
            }
            ps = con.prepareStatement(query.toString());
            ps.execute();
            ps.close();
        }
        catch (Exception se) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)se);
        }
    }
}
