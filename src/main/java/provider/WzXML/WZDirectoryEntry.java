package provider.WzXML;

import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import provider.MapleDataEntity;
import provider.MapleDataEntry;
import java.util.Map;
import provider.MapleDataFileEntry;
import java.util.List;
import provider.MapleDataDirectoryEntry;

public class WZDirectoryEntry extends WZEntry implements MapleDataDirectoryEntry
{
    private List<MapleDataDirectoryEntry> subdirs;
    private List<MapleDataFileEntry> files;
    private Map<String, MapleDataEntry> entries;
    
    public WZDirectoryEntry(final String name, final int size, final int checksum, final MapleDataEntity parent) {
        super(name, size, checksum, parent);
        this.subdirs = (List<MapleDataDirectoryEntry>)new ArrayList();
        this.files = (List<MapleDataFileEntry>)new ArrayList();
        this.entries = (Map<String, MapleDataEntry>)new HashMap();
    }
    
    public WZDirectoryEntry() {
        super(null, 0, 0, null);
        this.subdirs = (List<MapleDataDirectoryEntry>)new ArrayList();
        this.files = (List<MapleDataFileEntry>)new ArrayList();
        this.entries = (Map<String, MapleDataEntry>)new HashMap();
    }
    
    public void addDirectory(final MapleDataDirectoryEntry dir) {
        this.subdirs.add(dir);
        this.entries.put(dir.getName(), dir);
    }
    
    public void addFile(final MapleDataFileEntry fileEntry) {
        this.files.add(fileEntry);
        this.entries.put(fileEntry.getName(), fileEntry);
    }
    
    @Override
    public List<MapleDataDirectoryEntry> getSubdirectories() {
        return Collections.unmodifiableList(this.subdirs);
    }
    
    @Override
    public List<MapleDataFileEntry> getFiles() {
        return Collections.unmodifiableList(this.files);
    }
    
    @Override
    public MapleDataEntry getEntry(final String name) {
        return (MapleDataEntry)this.entries.get(name);
    }
}
