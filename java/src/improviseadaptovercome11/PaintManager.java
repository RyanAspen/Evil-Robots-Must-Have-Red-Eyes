package improviseadaptovercome11;

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


    public static int getTilesUntilSRPComplete(MapLocation loc, MapLocation paintLoc, boolean isSecondary) throws GameActionException {
        int xDiff = loc.x - paintLoc.x;
        int yDiff = loc.y - paintLoc.y;
        boolean[][] resourcePattern = rc.getResourcePattern();
        int tilesLeft = 25;
        for (int y = 0; y < 5; y++)
        {
            for (int x = 0; x < 5; x++)
            {
                if (x == xDiff+2 && y == yDiff+2)
                {
                    if (resourcePattern[y][x] == isSecondary) tilesLeft--;
                    continue;
                }
                MapLocation currLoc = new MapLocation(loc.x+x-2,loc.y-y+2);
                if (!rc.canSenseLocation(currLoc)) continue;
                MapInfo currLocInfo = rc.senseMapInfo(currLoc);
                if (currLocInfo.getPaint().isAlly() && resourcePattern[y][x] == currLocInfo.getPaint().isSecondary()) tilesLeft--;
            }
        }
        return tilesLeft;
    }

    //Takes really long (5+ rounds), don't bother for now
    /*
    public static boolean shouldBeSecondary(MapLocation loc) throws GameActionException {
        int test = Clock.getBytecodeNum();
        if (rc.canSenseLocation(loc))
        {
            PaintType mark = rc.senseMapInfo(loc).getMark();
            if (mark.isAlly())
            {
                return mark.isSecondary();
            }
        }
        int primaryMinComplete = 100000;
        int secondaryMinComplete = 100000;
        for (int y = 0; y < 5; y++)
        {
            for (int x = 0; x < 5; x++)
            {
                MapLocation currLoc = new MapLocation(loc.x+x-2,loc.y-y+2);
                int primaryComplete = getTilesUntilSRPComplete(currLoc, loc, false);
                int secondaryComplete = getTilesUntilSRPComplete(currLoc, loc, false);
                if (primaryComplete < primaryMinComplete) primaryMinComplete = primaryComplete;
                if (secondaryComplete < secondaryMinComplete) secondaryMinComplete = secondaryComplete;
            }
        }
        Logger.addToIndicatorString("C:" + (Clock.getBytecodeNum()-test));
        return primaryMinComplete >= secondaryMinComplete;
    }
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
        int rx = loc.x % 5;
        int ry = loc.y % 5;
        return resourcePattern[ry][rx];
    }



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
