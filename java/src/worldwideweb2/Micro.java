package worldwideweb2;

/*
    Micro methods that apply to any bot should be included here. This includes
    - Target Prioritization
    - Kiting
    - Regaining Paint
    - etc.
 */

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.PaintType;
import battlecode.common.RobotInfo;

public class Micro extends Globals {

    public static void refuel() throws GameActionException {
        if (rc.getPaint() >= rc.getType().paintCapacity * 0.2) return;
        //Search for nearby towers to refuel at
        int amountToRefuel = rc.getType().paintCapacity - rc.getPaint();
        if (amountToRefuel > 100)
        {
            amountToRefuel = 100;
        }
        RobotInfo[] allies = rc.senseNearbyRobots(-1, myTeam);
        int minDist = 1000;
        MapLocation target = null;
        for (int i = 0; i < allies.length; i++)
        {
            if (allies[i].getType().isRobotType()) continue;
            if (allies[i].getPaintAmount() <= 100) continue;
            int dist = rc.getLocation().distanceSquaredTo(allies[i].getLocation());
            if (dist < minDist)
            {
                minDist = dist;
                target = allies[i].getLocation();
            }
        }
        if (target != null)
        {
            if (rc.canTransferPaint(target, -amountToRefuel))
            {
                rc.transferPaint(target, -amountToRefuel);
            }
            else if (canMove)
            {
                Nav.moveTo(target);
                if (rc.canTransferPaint(target, -amountToRefuel))
                {
                    rc.transferPaint(target, -amountToRefuel);
                }
            }
        }
        else if (isSpawnPaint)
        {
            //Try to go back to spawn
            if (canMove && !rc.getLocation().isWithinDistanceSquared(spawnLoc,2))
            {
                Nav.moveTo(spawnLoc);
            }
            else if (rc.getLocation().isWithinDistanceSquared(spawnLoc,2))
            {
                canMove = false;
            }
        }
    }

    public static void kite() throws GameActionException {
        if (!rc.isMovementReady()) return;

        //Avoid the attack radius of towers if possible. Tiebreaker is paint alignment

        RobotInfo[] enemies = rc.senseNearbyRobots(-1,opponentTeam);
        if (enemies.length == 0) return;


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
            if (canMove) {
                rc.move(rc.getLocation().directionTo(target));
            }
        }
        else
        {
            canMove = false;
        }
    }
}
