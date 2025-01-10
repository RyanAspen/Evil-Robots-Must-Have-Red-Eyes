package pushthefrontline5;

/*
    Keep track of what color to paint a tile
 */

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.PaintType;

public class PaintManager extends Globals {
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
