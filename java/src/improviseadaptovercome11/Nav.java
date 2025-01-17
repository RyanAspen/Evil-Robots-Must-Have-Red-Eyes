package improviseadaptovercome11;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

public class Nav extends Globals {

    private static MapLocation lastPathingTarget = null;

    public static void explore() throws GameActionException {
        if (lastPathingTarget != null && rc.getLocation().isWithinDistanceSquared(lastPathingTarget, 4))
        {
            lastPathingTarget = null;
        }
        if (lastPathingTarget == null)
        {
            int exploreX = rng.nextInt(mapWidth);
            int exploreY = rng.nextInt(mapHeight);
            lastPathingTarget = new MapLocation(exploreX, exploreY);
        }
        Logger.addToIndicatorString("E " + lastPathingTarget);
        //moveToward(lastPathingTarget);
        move(lastPathingTarget);
    }

    //Outperforms
    static void move(MapLocation loc) throws GameActionException {
        NewNav.bug2(loc);
    }
}
