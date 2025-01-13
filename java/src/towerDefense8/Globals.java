package towerDefense8;

import battlecode.common.*;

import java.util.Random;

/*
    Communication is very different from previous competitions. The only means of communication are messages and markers. Messages are restricted
    to 4 bytes (32 bits) and can only be sent to


 */

public class Globals {
    public static RobotController rc;

    public static boolean canMove = true;
    public static int mapWidth;
    public static int mapHeight;

    public static int myId;
    public static Team myTeam;
    public static Team opponentTeam;

    public static Comms.CleanMessage[] cleanMessages;

    public static boolean isDrought = false;
    public static boolean isFlood = false;
    public static FastSet idsThatNeedPaint;
    public static FastSet ruinLocs;
    public static FastSet towerLocs;
    public static FastSet possibleTowerLocs;

    static Random rng = null;
    public static Direction[] allDirections = Direction.values();
    public static Direction[] adjacentDirections = {
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST,
            Direction.NORTHEAST,
            Direction.SOUTHEAST,
            Direction.SOUTHWEST,
            Direction.NORTHWEST
    };

    public static Direction[] cardinalDirections = {
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST
    };

    public static MapLocation spawnLoc = null;
    public static boolean isSpawnPaint = false;
    public static MovementMicro soldierMicro = null;

    public static void init(RobotController robotController) throws GameActionException {
        rc = robotController;
        spawnLoc = rc.getLocation();

        if (rc.getType() == UnitType.SOLDIER) soldierMicro = new MovementMicro(rc);

        RobotInfo[] close = rc.senseNearbyRobots(4);
        for (int i = 0; i < close.length; i++)
        {
            if (close[i].getType().isRobotType()) continue;
            if (close[i].getType().equals(UnitType.LEVEL_ONE_PAINT_TOWER) || close[i].getType().equals(UnitType.LEVEL_TWO_PAINT_TOWER) || close[i].getType().equals(UnitType.LEVEL_THREE_PAINT_TOWER))
            {
                isSpawnPaint = true;
                break;
            }
        }

        mapWidth = rc.getMapWidth();
        mapHeight = rc.getMapHeight();

        myId = rc.getID();
        rng = new Random(myId);
        myTeam = rc.getTeam();
        opponentTeam = myTeam.opponent();

        cleanMessages = new Comms.CleanMessage[]{};

        ruinLocs = new FastSet();
        possibleTowerLocs = new FastSet();
        towerLocs = new FastSet();
        idsThatNeedPaint = new FastSet();
    }
}
