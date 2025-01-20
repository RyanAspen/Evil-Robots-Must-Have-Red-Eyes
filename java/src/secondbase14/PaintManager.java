package secondbase14;

/*
    Keep track of what color to paint a tile
 */

import battlecode.common.GameActionException;
import battlecode.common.MapInfo;
import battlecode.common.MapLocation;
import battlecode.common.PaintType;

public class PaintManager extends Globals {

    /*
        Choose the paint color that causes the most complete patterns.


     */

    public static boolean shouldBeSecondary(MapLocation loc) throws GameActionException {
        if (rc.canSenseLocation(loc))
        {
            PaintType mark = rc.senseMapInfo(loc).getMark();
            if (mark.isAlly())
            {
                return mark.isSecondary();
            }
        }

        boolean[][] resourcePattern = rc.getResourcePattern();
        int rx = loc.x % 4;
        int ry = loc.y % 4;
        return resourcePattern[ry][rx];


        //return false;
    }



    public static boolean isValidSRPLoc(MapLocation loc)
    {
        if (!rc.canSenseLocation(loc)) return false;
        if (loc.x < 2 || loc.x > mapWidth - 3 || loc.y < 2 || loc.y > mapHeight - 3) return false;
        //Each tile must be paintable and unmarked
        MapInfo[] infos = rc.senseNearbyMapInfos();
        for (int i = 0; i < infos.length; i++)
        {
            if (infos[i].getMark().isAlly()) return false;
            else if (infos[i].isWall() || infos[i].hasRuin()) return false;
        }
        return true;
    }


    /*
    public static void attemptResourcePatternCompletion() throws GameActionException {
        if (rc.getRoundNum() < 300) return;
        if (isValidSRPLoc(rc.getLocation()))
        {
            //If it isn't marked yet, do that.
            if (!rc.senseMapInfo(rc.getLocation()).getMark().isAlly())
            {
                if (rc.canMarkResourcePattern(rc.getLocation()))
                {
                    rc.markResourcePattern(rc.getLocation());
                }
            }
        }
        if (rc.canCompleteResourcePattern(rc.getLocation()))
        {
            rc.completeResourcePattern(rc.getLocation());
        }
    }

     */


    public static void soldierAttack(MapLocation loc) throws GameActionException {
        if (rc.canAttack(loc))
        {
            rc.attack(loc, shouldBeSecondary(loc));
        }
    }


    public static void attemptResourcePatternCompletion() throws GameActionException {
        for (int i = 0; i < allDirections.length; i++)
        {
            if (rc.canCompleteResourcePattern(rc.getLocation().add(allDirections[i])))
            {
                rc.completeResourcePattern(rc.getLocation().add(allDirections[i]));
            }
        }
    }


}
