package nanoscopic7;

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

    public static UnitType towerPatternToComplete(MapLocation loc) throws GameActionException {
        boolean[][] paintPattern = rc.getTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER);
        boolean[][] moneyPattern = rc.getTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER);
        boolean[][] defensePattern = rc.getTowerPattern(UnitType.LEVEL_ONE_DEFENSE_TOWER);
        boolean paintEnemyPossible = true;
        boolean moneyEnemyPossible = true;
        boolean defenseEnemyPossible = true;
        boolean paintAllyPossible = true;
        boolean moneyAllyPossible = true;
        boolean defenseAllyPossible = true;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (i == 2 && j == 2) continue;
                MapLocation newLoc = new MapLocation(loc.x-2+j,loc.y+2-i);
                if (!rc.canSenseLocation(newLoc)) continue;
                PaintType paint = rc.senseMapInfo(newLoc).getPaint();
                if (paint == PaintType.EMPTY) return null;
                if (paint.isAlly())
                {
                    paintEnemyPossible = false;
                    moneyEnemyPossible = false;
                    defenseEnemyPossible = false;
                }
                if (paint.isEnemy())
                {
                    paintAllyPossible = false;
                    moneyAllyPossible = false;
                    defenseAllyPossible = false;
                }
                if (paint.isSecondary() != paintPattern[i][j]) //i=row/y, j=column/x
                {
                    paintEnemyPossible = false;
                    paintAllyPossible = false;
                }
                if (paint.isSecondary() != moneyPattern[i][j])
                {
                    moneyEnemyPossible = false;
                    moneyAllyPossible = false;
                }
                if (paint.isSecondary() != defensePattern[i][j])
                {
                    defenseEnemyPossible = false;
                    defenseAllyPossible = false;
                }
                if (!paintAllyPossible && !paintEnemyPossible && !moneyAllyPossible && !moneyEnemyPossible && !defenseAllyPossible && !defenseEnemyPossible) return null;
            }
        }
        if (paintAllyPossible || paintEnemyPossible) return UnitType.LEVEL_ONE_PAINT_TOWER;
        if (moneyAllyPossible || moneyEnemyPossible) return UnitType.LEVEL_ONE_MONEY_TOWER;
        if (defenseAllyPossible || defenseEnemyPossible) return UnitType.LEVEL_ONE_DEFENSE_TOWER;
        return null;
    }

    public static UnitType towerPatternToComplete(MapLocation loc, boolean isAlly) throws GameActionException {
        boolean[][] paintPattern = rc.getTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER);
        boolean[][] moneyPattern = rc.getTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER);
        boolean[][] defensePattern = rc.getTowerPattern(UnitType.LEVEL_ONE_DEFENSE_TOWER);
        boolean paintEnemyPossible = true;
        boolean moneyEnemyPossible = true;
        boolean defenseEnemyPossible = true;
        boolean paintAllyPossible = true;
        boolean moneyAllyPossible = true;
        boolean defenseAllyPossible = true;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (i == 2 && j == 2) continue;
                MapLocation newLoc = new MapLocation(loc.x-2+j,loc.y+2-i);
                if (!rc.canSenseLocation(newLoc)) continue;
                PaintType paint = rc.senseMapInfo(newLoc).getPaint();
                if (paint == PaintType.EMPTY) return null;
                if (paint.isAlly() && !isAlly) return null;
                if (paint.isEnemy() && isAlly) return null;
                if (paint.isSecondary() != paintPattern[i][j])
                {
                    paintEnemyPossible = false;
                    paintAllyPossible = false;
                }
                if (paint.isSecondary() != moneyPattern[i][j])
                {
                    moneyEnemyPossible = false;
                    moneyAllyPossible = false;
                }
                if (paint.isSecondary() != defensePattern[i][j])
                {
                    defenseEnemyPossible = false;
                    defenseAllyPossible = false;
                }
                if (!paintAllyPossible && !paintEnemyPossible && !moneyAllyPossible && !moneyEnemyPossible && !defenseAllyPossible && !defenseEnemyPossible) return null;
            }
        }
        if (paintAllyPossible || paintEnemyPossible) return UnitType.LEVEL_ONE_PAINT_TOWER;
        if (moneyAllyPossible || moneyEnemyPossible) return UnitType.LEVEL_ONE_MONEY_TOWER;
        if (defenseAllyPossible || defenseEnemyPossible) return UnitType.LEVEL_ONE_DEFENSE_TOWER;
        return null;
    }

    public static void soldierMicro() throws GameActionException {
        declutter();
        if (rc.senseNearbyRobots(-1, opponentTeam).length == 0) return;
        //CombatMicro.soldierAttack();
        CombatMicroSoldier.soldierAttack();
        canMove = movementMicro.microMoveSoldier(canMove);
        //canMove = movementMicro.microMove(canMove);
        //CombatMicro.soldierAttack();
        CombatMicroSoldier.soldierAttack();
    }

    public static void mopperMicro() throws GameActionException {
        declutter();
        if (rc.senseNearbyRobots(8, opponentTeam).length == 0) return; //TODO
        //CombatMicro.mopperAttack();
        CombatMicroMopper.mopperAttack();
        //canMove = movementMicro.microMove(canMove);
        canMove = movementMicro.microMoveMopper(canMove);
        //CombatMicro.mopperAttack();
        CombatMicroMopper.mopperAttack();
    }

    public static void splasherMicro() throws GameActionException {
        declutter();
        if (rc.senseNearbyRobots(-1, opponentTeam).length == 0) return;
        //canMove = movementMicro.microMove(canMove);
        canMove = movementMicro.microMoveSplasher(canMove);
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
                Nav.moveToward(target);
                //Nav.moveTo(target);
                if (rc.canTransferPaint(target, -amountToRefuel))
                {
                    rc.transferPaint(target, -amountToRefuel);
                }
            }
        }

        else if (isSpawnPaint && rc.getType() != UnitType.SOLDIER)
        {
            //Try to go back to spawn
            if (canMove && !rc.getLocation().isWithinDistanceSquared(spawnLoc,2))
            {
                Nav.moveToward(spawnLoc);
                //Nav.moveTo(spawnLoc);
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
