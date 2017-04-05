package newGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Serialize {

    private Path path;
    private List<Entry> entries;

    public Serialize(String ipath) {
        entries = new ArrayList<>();
        if(!ipath.endsWith(".srl"))
            path = Paths.get(ipath + ".srl");
        else
            path = Paths.get(ipath);

        if(!Files.exists(path))
            createFile();
        else
            loadFile();
    }

    private void createFile() {
        try {
            Files.createDirectories(Paths.get(path.toString().replace(path.getFileName().toString(), "")));
            Files.createFile(path);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFile() {
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while((line = reader.readLine()) != null)
                entries.add(new Entry(line));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Path getPath() {
        return this.path;
    }

    public List<Entry> getEntries() {
        return this.entries;
    }

    public List<Entry> getGroup(String group) {
        final List<Entry> grouped = new ArrayList<>();
        for(Entry entry : entries)
            if(entry.Group.equalsIgnoreCase(group))
                grouped.add(entry);
        return grouped;
    }

    public String getString(String key) {
        for(Entry entry : entries)
            if(entry.Key.equalsIgnoreCase(key))
                return entry.Value;
        return null;
    }

    public byte getByte(String key) {
        String toParse = getString(key);
        return Byte.parseByte(toParse == null ? "-1" : toParse);
    }

    public short getShort(String key) {
        String toParse = getString(key);
        return Short.parseShort(toParse == null ? "-1" : toParse);
    }

    public int getInt(String key) {
        String toParse = getString(key);
        return Integer.parseInt(toParse == null ? "-1" : toParse);
    }

    public float getFloat(String key) {
        String toParse = getString(key);
        return Float.parseFloat(toParse == null ? "-1" : toParse);
    }

    public double getDouble(String key) {
        String toParse = getString(key);
        return Double.parseDouble(toParse == null ? "-1" : toParse);
    }

    public void setString(String key, Object value) {
        entries.add(new Entry(key, value));
    }

    public void setByte(String key, byte value) {
        entries.add(new Entry(key, value));
    }

    public void setShort(String key, short value) {
        entries.add(new Entry(key, value));
    }

    public void setInt(String key, int value) {
        entries.add(new Entry(key, value));
    }

    public void setFloat(String key, float value) {
        entries.add(new Entry(key, value));
    }

    public void setDouble(String key, double value) {
        entries.add(new Entry(key, value));
    }

    public void editString(String key, String value) {
        for(Entry entry : entries)
            if(entry.Key.equalsIgnoreCase(key))
                entry.Value = value;
    }

    public void editByte(String key, byte value) {
        for(Entry entry : entries)
            if(entry.Key.equalsIgnoreCase(key))
                entry.Value = Byte.toString(value);
    }

    public void editShort(String key, short value) {
        for(Entry entry : entries)
            if(entry.Key.equalsIgnoreCase(key))
                entry.Value = Short.toString(value);
    }

    public void editInt(String key, int value) {
        for(Entry entry : entries)
            if(entry.Key.equalsIgnoreCase(key))
                entry.Value = Integer.toString(value);
    }

    public void editFloat(String key, float value) {
        for(Entry entry : entries)
            if(entry.Key.equalsIgnoreCase(key))
                entry.Value = Float.toString(value);
    }

    public void editDouble(String key, double value) {
        for(Entry entry : entries)
            if(entry.Key.equalsIgnoreCase(key))
                entry.Value = Double.toString(value);
    }

    public boolean containsKey(String key) {
        for(Entry entry : entries)
            if(entry.Key.equalsIgnoreCase(key))
                return true;
        return false;
    }

    public boolean containsGroup(String group) {
        for(Entry entry : entries)
            if(entry.Group.equalsIgnoreCase(group))
                return true;
        return false;
    }

    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for(Entry entry : entries) {
                writer.write(entry.toString());
                writer.newLine();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            Files.delete(path);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static class Entry {
        public String Group;
        public String Key;
        public String Value;
        public Entry(String line) {
            String[] split = line.split("=");
            Group = split[0].contains("\\.") ? split[0].split("\\.")[0] : "";
            Key = split[0];
            Value = split[1];
        }

        public Entry(String key, Object value) {
            Group = key.contains("\\.") ? key.split("\\.")[0] : "";
            Key = key;
            Value = value.toString();
        }

        @Override
        public String toString() {
            return Key + "=" + Value;
        }
    }

}
