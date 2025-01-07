package andthentherewasbot1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.Team;

import java.util.Random;

/*
    Communication is very different from previous competitions. The only means of communication are messages and markers. Messages are restricted
    to 4 bytes (32 bits) and can only be sent to


 */

public class Globals {
    public static RobotController rc;

    public static int mapWidth;
    public static int mapHeight;

    public static int myId;
    public static Team myTeam;
    public static Team opponentTeam;

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

    public static void init(RobotController robotController) throws GameActionException {
        rc = robotController;

        mapWidth = rc.getMapWidth();
        mapHeight = rc.getMapHeight();

        myId = rc.getID();
        rng = new Random(myId);
        myTeam = rc.getTeam();
        opponentTeam = myTeam.opponent();
    }
}
