package fivegtowers12;

import battlecode.common.MapLocation;

public class FastSet {
    private StringBuilder values = new StringBuilder();

    public boolean add(char value) {
        String str = String.valueOf(value);
        if (values.indexOf(str) == -1) {
            values.append(str);
            return true;
        }

        return false;
    }

    public boolean contains(char value) {
        return values.indexOf(String.valueOf(value)) > -1;
    }

    public void remove(MapLocation location)
    {
        char val = encodeLocation(location);
        int index = values.indexOf(String.valueOf(val));
        if (index > -1)
        {
            values.deleteCharAt(index);
        }
    }

    public boolean add(MapLocation location) {
        return add(encodeLocation(location));
    }

    public boolean add(int id) {
        return add((char) id);
    }

    public boolean contains(MapLocation location) {
        return contains(encodeLocation(location));
    }

    public boolean contains(int id)
    {
        return contains((char) id);
    }

    private char encodeLocation(MapLocation location) {
        return (char) ((location.x << 6) | location.y);
    }

    public int size() {
        return values.length();
    }

    private MapLocation decodeLoc(char value)
    {
        int i = (int) value;
        return new MapLocation(i >> 6, i & 0b111111);
    }

    private int decodeId(char value)
    {
        return value;
    }

    public MapLocation getLoc(int idx)
    {
        int i = (int) values.charAt(idx);
        return new MapLocation(i << 6, i % 2^6);
    }

    public MapLocation[] getAllLoc()
    {
        MapLocation[] locs = new MapLocation[values.length()];
        for (int i = 0; i < values.length(); i++)
        {
            locs[i] = decodeLoc(values.charAt(i));
        }
        return locs;
    }

    public int[] getAllIds()
    {
        int[] ids = new int[values.length()];
        for (int i = 0; i < values.length(); i++)
        {
            ids[i] = decodeId(values.charAt(i));
        }
        return ids;
    }
}
