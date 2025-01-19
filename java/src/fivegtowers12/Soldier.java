package fivegtowers12;


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
        // Go towards ruins that can be completed
        int minDist = 9999;
        MapLocation targetRuin = null;
        boolean isTargetRuinMarkable = true;
        for (int i = 0; i < ruins.length; i++)
        {
            RobotInfo r = rc.senseRobotAtLocation(ruins[i]);

            int dist = ruins[i].distanceSquaredTo(rc.getLocation());
            if (dist < minDist)
            {
                minDist = dist;
                targetRuin = ruins[i];

                //EXPERIMENT TODO
                if (r != null && r.getType().isTowerType() )//&& r.getTeam() == myTeam)
                {
                    isTargetRuinMarkable = false;
                    continue;
                }
                MapInfo[] nearMapInfos = rc.senseNearbyMapInfos(targetRuin, 2);
                for (int j = 0; j < nearMapInfos.length; j++)
                {
                    if (nearMapInfos[j].getMark().isAlly())
                    {
                        isTargetRuinMarkable = false;
                        break;
                    }
                }
                /*
                    A ruin is markable IF
                    - There is no mark near the ruin

                 */
            }
        }
        if (targetRuin != null && rc.senseRobotAtLocation(targetRuin) == null) //
        {
            UnitType towerType = Micro.towerPatternToComplete(targetRuin, true);
            if (towerType != null) {
                if (rc.getMoney() >= towerType.moneyCost) {
                    if (towerType != UnitType.LEVEL_ONE_MONEY_TOWER || rc.getMoney() < 3000) {
                        if (targetRuin.isAdjacentTo(rc.getLocation())) {
                            if (rc.canCompleteTowerPattern(towerType, targetRuin)) {
                                rc.completeTowerPattern(towerType, targetRuin);
                                Logger.addToIndicatorString(towerType + " " + targetRuin);
                            }
                        } else if (targetRuin.isWithinDistanceSquared(rc.getLocation(), 8)) {
                            //Nav.moveToward(targetRuin);
                            Nav.move(targetRuin);
                            if (targetRuin.isAdjacentTo(rc.getLocation())) {
                                if (rc.canCompleteTowerPattern(towerType, targetRuin)) {
                                    rc.completeTowerPattern(towerType, targetRuin);
                                    Logger.addToIndicatorString(towerType + " " + targetRuin);
                                }
                            }
                        }
                    }
                }
                else
                {
                    //If there are no allied soldiers near the ruin, stay near the ruin
                    RobotInfo[] nearAllies = rc.senseNearbyRobots(targetRuin, 8, myTeam);
                    boolean soldierNear = false;
                    for (int i = 0; i < nearAllies.length; i++)
                    {
                        if (nearAllies[i].getType() == UnitType.SOLDIER)
                        {
                            soldierNear = true;
                            break;
                        }
                    }
                    if (!soldierNear && rc.getLocation().isWithinDistanceSquared(targetRuin, 10))
                    {
                        //Nav.moveToward(targetRuin);
                        Nav.move(targetRuin);
                    }
                    canMove = false;
                }
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
        else
        {
            for (int i = 0; i < infos.length; i++) {
                int dist = rc.getLocation().distanceSquaredTo(infos[i].getMapLocation());
                if (dist < minDist)
                {
                    if (infos[i].getMark().isAlly() && !infos[i].getPaint().equals(infos[i].getMark()) && !infos[i].getPaint().isEnemy())
                    {
                        minDist = dist;
                        secondary = infos[i].getMark().isSecondary();
                        targetMark = infos[i].getMapLocation();
                    }
                }
            }
        }

        if (targetMark != null)
        {
            if (rc.canAttack(targetMark))
            {
                Logger.addToIndicatorString("Marking " + targetMark);
                canMove = false;
                rc.attack(targetMark, secondary);
                return;
            }
            else if (canMove)
            {
                Nav.move(targetMark);
                //Nav.moveToward(targetMark);
                if (rc.canAttack(targetMark))
                {
                    Logger.addToIndicatorString("Marking " + targetMark);
                    rc.attack(targetMark, secondary);
                    return;
                }
            }
            Logger.addToIndicatorString("Will Mark " + targetMark);
            return;
        }

        // If there is a ruin that can be marked and we can mark it, go towards it and attempt to mark it
        if (rc.getPaint() >= GameConstants.MARK_PATTERN_PAINT_COST+10 && targetRuin != null && isTargetRuinMarkable)
        {


            RobotInfo bot = rc.senseRobotAtLocation(targetRuin);
            Nav.move(targetRuin);
            //Nav.moveToward(targetRuin);
            if (bot != null)
            {
                if (rc.canMarkTowerPattern(bot.getType(), targetRuin))
                {
                    Logger.addToIndicatorString("Marking Start " + targetRuin);
                    rc.markTowerPattern(bot.getType(), targetRuin);
                    return;
                }
            }


            if (rc.senseNearbyRobots(-1, opponentTeam).length > 0 && rc.getNumberTowers() > 10)
            {
                if (rc.canMarkTowerPattern(UnitType.LEVEL_ONE_DEFENSE_TOWER, targetRuin)) {
                    Logger.addToIndicatorString("Marking TP " + targetRuin);
                    rc.markTowerPattern(UnitType.LEVEL_ONE_DEFENSE_TOWER, targetRuin);
                    return;
                }
            }

            if (rc.getRoundNum() < 60)
            {
                if (rc.canMarkTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER, targetRuin)) {
                    Logger.addToIndicatorString("Marking TP " + targetRuin);
                    rc.markTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER, targetRuin);

                }
                return;
            }


            double paintProb = 0.5;
            double moneyProb = 0.5;
            if (rng.nextDouble(1) < moneyProb) //&& rc.getMoney() < 3000)
            {
                if (rc.canMarkTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER, targetRuin)) {
                    Logger.addToIndicatorString("Marking TP " + targetRuin);
                    rc.markTowerPattern(UnitType.LEVEL_ONE_MONEY_TOWER, targetRuin);
                    return;
                }
            }
            else
            {
                if (rc.canMarkTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetRuin)) {
                    Logger.addToIndicatorString("Marking TP " + targetRuin);
                    rc.markTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetRuin);
                    return;
                }
            }
        }
    }


}
