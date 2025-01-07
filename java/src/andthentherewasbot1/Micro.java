package andthentherewasbot1;

/*
    Micro methods that apply to any bot should be included here. This includes
    - Target Prioritization
    - Kiting
    - etc.
 */

import battlecode.common.*;

public class Micro extends Globals{

    public static boolean kite() throws GameActionException {
        if (!rc.isMovementReady()) return false;
        //Avoid the attack radius of towers if possible. Tiebreaker is paint alignment

        RobotInfo[] enemies = rc.senseNearbyRobots(-1,opponentTeam);
        if (enemies.length == 0) return true;

        MapLocation[] tempNearLocs = new MapLocation[9];
        int c = 0;
        for (int i = 0; i < allDirections.length; i++)
        {
            if (rc.canMove(allDirections[i]))
            {
                tempNearLocs[c] = rc.getLocation().add(allDirections[i]);
                c++;
            }
        }
        MapLocation[] nearLocs = new MapLocation[c];
        System.arraycopy(tempNearLocs, 0, nearLocs, 0 , c);

        int minTowers = 1000;
        int paint = -1;
        MapLocation target = null;
        for (int j = 0; j < nearLocs.length; j++)
        {
            int towers = 0;
            for (int i = 0; i < enemies.length; i++)
            {
                if (!enemies[i].getType().isTowerType()) continue;
                if (nearLocs[j].isWithinDistanceSquared(enemies[i].getLocation(), enemies[i].getType().actionRadiusSquared))
                {
                    towers++;
                }
            }
            if (towers < minTowers)
            {
                minTowers = towers;
                target = nearLocs[j];
                PaintType paintType = rc.senseMapInfo(nearLocs[j]).getPaint();
                if (paintType.isAlly())
                {
                    paint = 1;
                }
                else if (paintType == PaintType.EMPTY)
                {
                    paint = 0;
                }
                else
                {
                    paint = -1;
                }
            }
            else if (towers == minTowers)
            {
                PaintType paintType = rc.senseMapInfo(nearLocs[j]).getPaint();
                int paint2;
                if (paintType.isAlly())
                {
                    paint2 = 1;
                }
                else if (paintType == PaintType.EMPTY)
                {
                    paint2 = 0;
                }
                else
                {
                    paint2 = -1;
                }
                if (paint < paint2)
                {
                    paint = paint2;
                    target = nearLocs[j];
                }
            }
        }
        if (target != null && target != rc.getLocation())
        {
            rc.move(rc.getLocation().directionTo(target));
            return true;
        }
        else
        {
            return false;
        }
    }
}
