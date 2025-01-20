package secondbase14;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;
import battlecode.common.UnitType;

/*
    This file contains all important global information known
    by the bot. This can be updated with communication or observation
    of the map.
 */
public class KnowledgeBase extends Globals {

    //ID, Location, type, and team of all known towers
    //Location of all ruins

    public static int[] towerIDs = new int[50];
    public static MapLocation[] towerLocs = new MapLocation[50];
    public static UnitType[] towerTypes = new UnitType[50];

    //true = ally
    public static boolean[] towerTeams = new boolean[50];
    public static int[] towerRoundUpdate = new int[50];

    public static FastSet ruinLocs = new FastSet();
    public static int id = 0;

    public static void updateInfo() throws GameActionException {
        MapLocation[] locs = rc.senseNearbyRuins(-1);
        for (int i = 0; i < locs.length; i++)
        {
            if (rc.senseRobotAtLocation(locs[i]) == null)
            {
                ruinLocs.add(locs[i]);
                for (int j = 0; j < 50; j++)
                {
                    if (locs[i] == towerLocs[j])
                    {
                        towerIDs[j] = 0;
                        towerLocs[j] = null;
                        towerTypes[j] = null;
                        towerTeams[j] = false;
                        break;
                    }
                }
            }
            else
            {
                ruinLocs.remove(locs[i]);
            }
        }

        RobotInfo[] bots = rc.senseNearbyRobots();
        for (int i = 0; i < bots.length; i++)
        {
            if (!bots[i].getType().isTowerType()) continue;
            boolean found = false;
            int open = -1;
            for (int j = 0; j < towerIDs.length; j++)
            {
                if (towerIDs[j] == bots[i].getID())
                {
                    towerLocs[j] = bots[i].getLocation();
                    towerTypes[j] = bots[i].getType();
                    towerTeams[j] = rc.getTeam() == myTeam;
                    towerRoundUpdate[j] = rc.getRoundNum();
                    found = true;
                    break;
                }
                else if (towerIDs[j] == 0) open = j;
            }
            if (!found)
            {
                if (open > -1)
                {
                    towerIDs[open] = bots[i].getID();
                    towerLocs[open] = bots[i].getLocation();
                    towerTypes[open] = bots[i].getType();
                    towerTeams[open] = bots[i].getTeam() == myTeam;
                    towerRoundUpdate[open] = rc.getRoundNum();
                }
                else
                {
                    towerIDs[id] = bots[i].getID();
                    towerLocs[id] = bots[i].getLocation();
                    towerTypes[id] = bots[i].getType();
                    towerTeams[id] = bots[i].getTeam() == myTeam;
                    towerRoundUpdate[id] = rc.getRoundNum();
                    id = (id + 1) % 50;
                }
            }
        }


    }

    public static MapLocation getClosestRuin()
    {
        MapLocation[] ruins = ruinLocs.getAllLoc();
        MapLocation bestLoc = null;
        int closestDist = 9999;
        for (int i = 0; i < ruins.length; i++)
        {
            int dist = rc.getLocation().distanceSquaredTo(ruins[i]);
            if (dist < closestDist)
            {
                closestDist = dist;
                bestLoc = ruins[i];
            }
        }
        return bestLoc;
    }

    public static MapLocation[] getDefenseTowersToAvoid()
    {
        int count = 0;
        MapLocation[] locs = new MapLocation[25];
        for (int i = 0; i < 50; i++)
        {
            if (towerTypes[i] != null && towerTypes[i].getBaseType() == UnitType.LEVEL_ONE_DEFENSE_TOWER && !towerTeams[i] && rc.getRoundNum() - towerRoundUpdate[i] < 100)
            {
                locs[count] = towerLocs[i];
                count++;
            }
        }
        MapLocation[] defenseLocs = new MapLocation[count];
        System.arraycopy(locs, 0, defenseLocs, 0, count);
        return defenseLocs;
    }

    public static FastSet getLocsToAvoid() throws GameActionException {
        FastSet locSet = new FastSet();

        MapLocation[] defenseLocs = getDefenseTowersToAvoid();
        for (int i = 0; i < defenseLocs.length; i++)
        {
            MapLocation[] locs = rc.getAllLocationsWithinRadiusSquared(defenseLocs[i], UnitType.LEVEL_ONE_DEFENSE_TOWER.actionRadiusSquared);
            for (int j = 0; j < locs.length; j++)
            {
                locSet.add(locs[j]);
            }
        }
        return locSet;
    }

    public static MapLocation getClosestAllyPaintTower()
    {
        MapLocation closestLoc = null;
        int minDist = 99999;
        for (int i = 0; i < 50; i++)
        {
            if (towerTypes[i] != null && towerTypes[i].getBaseType() == UnitType.LEVEL_ONE_PAINT_TOWER && towerTeams[i])
            {
                int dist = rc.getLocation().distanceSquaredTo(towerLocs[i]);
                if (dist < minDist)
                {
                    minDist = dist;
                    closestLoc = towerLocs[i];
                }
            }
        }
        return closestLoc;
    }

    public static MapLocation getClosestNonDefenseEnemyTower()
    {
        MapLocation closestLoc = null;
        int minDist = 99999;
        for (int i = 0; i < 50; i++)
        {
            if (towerTypes[i] != null && towerTypes[i].getBaseType() != UnitType.LEVEL_ONE_DEFENSE_TOWER && !towerTeams[i])
            {
                int dist = rc.getLocation().distanceSquaredTo(towerLocs[i]);
                if (dist < minDist)
                {
                    minDist = dist;
                    closestLoc = towerLocs[i];
                }
            }
        }
        return closestLoc;
    }
}
