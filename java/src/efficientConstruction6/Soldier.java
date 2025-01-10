package efficientConstruction6;


/*
Soldiers have a basic attack that can paint a tile or damage a tower. It outranges the mining towers, so it
is a good strategy to linger outside of tower ranges and destroy them, leaving the enemy without a stable source of paint/chips.

Soldiers CANNOT attack other bots, so their main job is to paint territory and stop enemy towers. They also seem like the best option
for creating patterns for towers, as they are the only ones that can paint precisely

 */

import battlecode.common.*;

public class Soldier extends Globals {

    public static void run() throws GameActionException {
        canMove = true;
        Micro.tryCompleteTowerPattern();
        Micro.refuel();
        paintRuins();
        Micro.soldierMicro();




        //Explore randomly
        if (canMove) {
            //Nav.explore();
            Nav.explore();
        }
    }

    public static void paintRuins() throws GameActionException {
        if (rc.getPaint() < 5 || rc.getNumberTowers() >= GameConstants.MAX_NUMBER_OF_TOWERS)
        {
            return;
        }
        MapLocation[] ruins = rc.senseNearbyRuins(-1);
        if (ruins.length == 0)
        {
            return;
        }

        // Go towards ruins that can be completed
        int minDist = 9999;
        MapLocation targetRuin = null;
        for (int i = 0; i < ruins.length; i++)
        {
            RobotInfo r = rc.senseRobotAtLocation(ruins[i]);
            if (r != null && r.getType().isTowerType() && r.getTeam().isPlayer()) continue;
            int dist = ruins[i].distanceSquaredTo(rc.getLocation());
            if (dist < minDist)
            {
                minDist = dist;
                targetRuin = ruins[i];
            }
        }
        if (targetRuin != null)
        {
            //Move towards the ruin
            Nav.moveToward(targetRuin);
            //Nav.moveTo(targetRuin);
            if (rc.getMoney() < 3000 && rc.canCompleteTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER, targetRuin))
            {
                rc.completeTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER, targetRuin);
            }
            else if (rc.canCompleteTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetRuin))
            {
                rc.completeTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetRuin);
            }
            else if (rc.canCompleteTowerPattern(UnitType.LEVEL_ONE_DEFENSE_TOWER, targetRuin))
            {
                rc.completeTowerPattern(UnitType.LEVEL_ONE_DEFENSE_TOWER, targetRuin);
            }
        }

        MapInfo[] infos = rc.senseNearbyMapInfos(-1);
        MapLocation targetMark = null;
        minDist = 9999;
        boolean secondary = false;
        if (targetRuin != null) {
            for (int i = 0; i < infos.length; i++) {
                int x_diff = Math.abs(infos[i].getMapLocation().x - targetRuin.x);
                int y_diff = Math.abs(infos[i].getMapLocation().y - targetRuin.y);
                if (x_diff <= 2 && y_diff <= 2)
                {
                    if (infos[i].getMark().isAlly() && !infos[i].getPaint().equals(infos[i].getMark()) && !infos[i].getPaint().isEnemy()) {
                        int dist = rc.getLocation().distanceSquaredTo(infos[i].getMapLocation());
                        if (dist < minDist) {
                            minDist = dist;
                            secondary = infos[i].getMark().isSecondary();
                            targetMark = infos[i].getMapLocation();
                        }
                    }
                }
            }
        }

        if (targetMark != null)
        {
            if (rc.canAttack(targetMark))
            {
                rc.attack(targetMark, secondary);
                return;
            }
            else if (canMove)
            {
                Nav.moveToward(targetMark);
                //Nav.moveTo(targetMark);
                if (rc.canAttack(targetMark))
                {
                    rc.attack(targetMark, secondary);
                    return;
                }
            }
            return;
        }


        if (rc.getPaint() >= GameConstants.MARK_PATTERN_PAINT_COST+10 && targetRuin != null)
        {
            MapInfo[] near = rc.senseNearbyMapInfos(targetRuin, 2);
            for (int i = 0; i < near.length; i++)
            {
                if (near[i].getMark().isAlly()) {
                    return;
                }
            }

            if (rc.senseNearbyRobots(-1, opponentTeam).length > 0 && rc.getNumberTowers() > 10)
            {
                if (rc.canMarkTowerPattern(UnitType.LEVEL_ONE_DEFENSE_TOWER, targetRuin)) {
                    rc.markTowerPattern(UnitType.LEVEL_ONE_DEFENSE_TOWER, targetRuin);
                    return;
                }
            }


            double paintProb = 0.7;
            double moneyProb = 0.3;
            if (rc.getRoundNum() < 100)
            {
                paintProb = 0.3;
                moneyProb = 0.7;
            }
            if (rng.nextInt() % 10 < moneyProb*10 && rc.getMoney() < 3000)
            {
                if (rc.canMarkTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER, targetRuin)) {
                    rc.markTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER, targetRuin);
                    return;
                }
            }
            else
            {
                if (rc.canMarkTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetRuin)) {
                    rc.markTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetRuin);
                    return;
                }
            }
        }
    }


}
