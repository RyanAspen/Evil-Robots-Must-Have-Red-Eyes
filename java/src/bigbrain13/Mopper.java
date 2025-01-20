package bigbrain13;

/*
Moppers are frail and cheap in paint but expensive in chips. They're main purpose is to steal paint from the enemy, whether it is
on bots or tiles. They seem important for anti-economy strats. Without towers nearby, moppers can freely "attack" enemy units and cause issues relatively
independently since their actions don't require any paint. They also can supply other units with paint, which is a necessity.

 */

import battlecode.common.*;

public class Mopper extends Globals
{
    public static void run() throws GameActionException {
        canMove = true;
        Micro.tryCompleteTowerPattern();
        Micro.refuel();
        Micro.mopperMicro();
        resupplyAllies();
        mopTiles();

        //Explore randomly
        Nav.explore();
    }

    public static void mopTiles() throws GameActionException {
        MapInfo[] nearTiles = rc.senseNearbyMapInfos(-1);
        int minDist = 1000;
        MapLocation target = null;
        boolean isMarked = false;
        for (int i = 0; i < nearTiles.length; i++)
        {
            int dist = rc.getLocation().distanceSquaredTo(nearTiles[i].getMapLocation());
            boolean marked = rc.senseMapInfo(nearTiles[i].getMapLocation()).getMark().isAlly();
            if (nearTiles[i].getPaint().isEnemy())
            {
                if (marked && !isMarked)
                {
                    target = nearTiles[i].getMapLocation();
                    minDist = dist;
                    isMarked = true;
                }
                else if (marked && dist < minDist)
                {
                    target = nearTiles[i].getMapLocation();
                    minDist = dist;
                }
                else if (!isMarked && dist < minDist)
                {
                    target = nearTiles[i].getMapLocation();
                    minDist = dist;
                }
            }
        }
        if (target != null)
        {
            Logger.addToIndicatorString("M-" + isMarked);
            if (rc.canAttack(target))
            {
                canMove = false; //TODO
                Logger.addToIndicatorString("Mopped Tile");
                rc.attack(target);
            }
            else if (canMove)
            {
                Nav.move(target);
                //Nav.moveToward(target);
                if (rc.canAttack(target))
                {
                    Logger.addToIndicatorString("Mopped Tile w move");
                    rc.attack(target);
                }
                else
                {
                    Logger.addToIndicatorString("Moved towards tile");
                }
            }
        }
    }

    public static void resupplyAllies() throws GameActionException {
        if (rc.getPaint() < UnitType.MOPPER.paintCapacity*0.5) return;
        int paintToGive = (int) (rc.getPaint() - (UnitType.MOPPER.paintCapacity*0.5));
        if (paintToGive < 10) return;

        RobotInfo[] allies = rc.senseNearbyRobots(-1, myTeam);
        double minPaintFrac = 0.5;
        int paintMissing = 0;
        MapLocation target = null;
        for (int i = 0; i < allies.length; i++)
        {
            if (allies[i].getType() == UnitType.MOPPER || allies[i].getType().isTowerType()) continue;
            double paintFrac = (double) allies[i].getPaintAmount() / allies[i].getType().paintCapacity;
            if (paintFrac < minPaintFrac)
            {
                minPaintFrac = paintFrac;
                paintMissing = allies[i].getType().paintCapacity - allies[i].getPaintAmount();

                target = allies[i].getLocation();
            }
        }
        if (target != null)
        {
            Logger.addToIndicatorString("Target = " + target + " " + paintToGive + " " + paintMissing);
            int amountToTransfer = Math.min(paintToGive, paintMissing);
            if (rc.canTransferPaint(target, amountToTransfer))
            {
                Logger.addToIndicatorString("Resupplied Allies");
                rc.transferPaint(target, paintToGive);
            }
            else if (canMove)
            {
                Nav.move(target);
                //Nav.moveToward(target);
                if (rc.canTransferPaint(target, amountToTransfer))
                {
                    Logger.addToIndicatorString("Resupplied Allies");
                    rc.transferPaint(target, paintToGive);
                }
            }
        }
    }
}
