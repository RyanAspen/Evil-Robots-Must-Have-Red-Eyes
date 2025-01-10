package pushthefrontline5;

/*
    Micro methods that apply to any bot should be included here. This includes
    - Target Prioritization
    - Kiting
    - Regaining Paint
    - etc.
 */

import battlecode.common.*;

public class Micro extends Globals {

    private static MovementMicro movementMicro = new MovementMicro(rc);

    public static void soldierMicro() throws GameActionException {
        declutter();
        if (rc.senseNearbyRobots(-1, opponentTeam).length == 0) return;
        CombatMicro.soldierAttack();
        canMove = movementMicro.microMove(canMove);
        CombatMicro.soldierAttack();
    }

    public static void mopperMicro() throws GameActionException {
        declutter();
        if (rc.senseNearbyRobots(-1, opponentTeam).length == 0) return;
        CombatMicro.mopperAttack();
        canMove = movementMicro.microMove(canMove);
        CombatMicro.mopperAttack();
    }

    public static void splasherMicro() throws GameActionException {
        declutter();
        if (rc.senseNearbyRobots(-1, opponentTeam).length == 0) return;
        canMove = movementMicro.microMove(canMove);
    }

    public static void tryCompleteTowerPattern() throws GameActionException {
        if (rc.getMoney() < UnitType.LEVEL_ONE_DEFENSE_TOWER.moneyCost || rc.getNumberTowers() >= GameConstants.MAX_NUMBER_OF_TOWERS) return;
        MapLocation[] ruins = rc.senseNearbyRuins(4);
        if (ruins.length == 0) return;
        for (int i = 0; i < ruins.length; i++)
        {
            RobotInfo r = rc.senseRobotAtLocation(ruins[i]);
            if (r != null && r.getType().isTowerType() && r.getTeam().isPlayer()) continue;
            if (rc.canCompleteTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER, ruins[i]))
            {
                rc.completeTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER, ruins[i]);
                return;
            }
            else if (rc.canCompleteTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, ruins[i]))
            {
                rc.completeTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, ruins[i]);
                return;
            }
            else if (rc.canCompleteTowerPattern(UnitType.LEVEL_ONE_DEFENSE_TOWER, ruins[i]))
            {
                rc.completeTowerPattern(UnitType.LEVEL_ONE_DEFENSE_TOWER, ruins[i]);
                return;
            }
        }

    }

    public static void refuel() throws GameActionException {
        if (rc.getPaint() >= rc.getType().paintCapacity * 0.2 && rc.getType() != UnitType.SPLASHER) return;
        if (rc.getPaint() >= rc.getType().attackCost + 50 && rc.getType() == UnitType.SPLASHER) return;
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

    public static void declutter() throws GameActionException {
        //If we are low on paint, there are many allies nearby, and we haven't moved recently, destroy the bot
        return;
        /*
        if (rc.getType().isTowerType()) return;
        if (rc.getPaint() > rc.getType().attackCost) return;
        if (rc.senseNearbyRobots(2, myTeam).length < 7) return;
        rc.disintegrate();

         */
    }
}
