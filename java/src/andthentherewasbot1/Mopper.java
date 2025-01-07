package andthentherewasbot1;

/*
Moppers are frail and cheap in paint but expensive in chips. They're main purpose is to steal paint from the enemy, whether it is
on bots or tiles. They seem important for anti-economy strats. Without towers nearby, moppers can freely "attack" enemy units and cause issues relatively
independently since their actions don't require any paint. They also can supply other units with paint, which is a necessity.

 */

import battlecode.common.*;

public class Mopper extends Globals
{
    public static void run() throws GameActionException {
        stealPaintFromEnemies();
        resupplyAllies();
        mopTiles();

        boolean canMove = Micro.kite();

        //Explore randomly
        if (canMove) {
            Nav.explore();
        }
    }

    public static void mopTiles() throws GameActionException {
        if (!rc.isActionReady()) return;
        MapInfo[] nearTiles = rc.senseNearbyMapInfos(2);
        for (int i = 0; i < nearTiles.length; i++)
        {
            if (nearTiles[i].getPaint() == PaintType.ENEMY_PRIMARY || nearTiles[i].getPaint() == PaintType.ENEMY_SECONDARY)
            {
                rc.attack(nearTiles[i].getMapLocation());
                Logger.addToIndicatorString("Mopped Tile");
                return;
            }
        }
    }

    public static void attemptMopSweep() throws GameActionException {
        if (!rc.isActionReady()) return;
        MapLocation[] closeLocs = new MapLocation[8];
        boolean[] valid = new boolean[8];
        for (int i = 0; i < adjacentDirections.length; i++)
        {
            closeLocs[i] = rc.getLocation().add(adjacentDirections[i]);
            valid[i] = true;
        }
        RobotInfo sensedRobot;

        for (int i = 0; i < adjacentDirections.length; i++)
        {
            if (!rc.onTheMap(closeLocs[i]))
            {
                valid[i] = false;
            }
            else
            {
                sensedRobot = rc.senseRobotAtLocation(closeLocs[i]);
                if (sensedRobot == null || sensedRobot.getTeam() != opponentTeam) {
                    valid[i] = false;
                }
            }
        }

        //NORTH (0,4,7)
        if (valid[0] && valid[4] && valid[7])
        {
            rc.mopSwing(Direction.NORTH);
            Logger.addToIndicatorString("Mop Swing North");
        }
        //SOUTH (2,5,6)
        else if (valid[2] && valid[5] && valid[6])
        {
            rc.mopSwing(Direction.SOUTH);
            Logger.addToIndicatorString("Mop Swing South");
        }
        //EAST (1,4,5)
        else if (valid[1] && valid[4] && valid[5])
        {
            rc.mopSwing(Direction.EAST);
            Logger.addToIndicatorString("Mop Swing East");
        }
        //WEST (3,6,7)
        else if (valid[3] && valid[6] && valid[7])
        {
            rc.mopSwing(Direction.WEST);
            Logger.addToIndicatorString("Mop Swing West");
        }
    }

    public static void stealPaintFromEnemies() throws GameActionException {
        if (!rc.isActionReady()) return;
        RobotInfo[] closeEnemies = rc.senseNearbyRobots(2, opponentTeam);
        if (closeEnemies.length == 0) return;

        //Try a mop sweep if its ideal
        attemptMopSweep();
        if (!rc.isActionReady()) return;

        //Steal from the enemy with the least paint
        int minPaint = 1000;
        MapLocation target = null;
        for (int i = 0; i < closeEnemies.length; i++)
        {
            int paint = closeEnemies[i].getPaintAmount();
            if (paint < minPaint)
            {
                minPaint = paint;
                target = closeEnemies[i].getLocation();
            }
        }
        if (target != null)
        {
            rc.attack(target);
            Logger.addToIndicatorString("Stole Enemy Paint");
        }
    }

    public static void resupplyAllies() throws GameActionException {
        if (!rc.isActionReady() || rc.getPaint() < UnitType.MOPPER.paintCapacity*0.5) return;
        int paintToGive = (int) (rc.getPaint() - (UnitType.MOPPER.paintCapacity*0.5));
        if (paintToGive < 10) return;

        RobotInfo[] allies = rc.senseNearbyRobots(2, myTeam);
        double minPaintFrac = 1.0;
        int paintMissing = 0;
        MapLocation target = null;
        for (int i = 0; i < allies.length; i++)
        {
            if (allies[i].getType() == UnitType.MOPPER) continue;
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
            if (paintToGive < paintMissing)
            {
                rc.transferPaint(target, paintToGive);
            }
            else
            {
                rc.transferPaint(target, paintMissing);
            }
            Logger.addToIndicatorString("Resupplied Allies");
        }
    }
}
