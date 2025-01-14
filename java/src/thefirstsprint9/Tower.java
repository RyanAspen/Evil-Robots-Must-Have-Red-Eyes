package thefirstsprint9;
import battlecode.common.*;
/*
Towers are stationary. They can do 2 things:
- Attack enemies in their radius
    * Attack radius is sqrt(9) for non-defense towers, sqrt(20) for defense towers
    * AOE attack and regular attack once per turn
- Build units
    * Requires paint and chips

This is the only means of getting chips and the only consistent way of getting paint, so they should be preserved at all costs

 */

public class Tower extends Globals
{
    public static MapLocation location = null;
    public static MapLocation[] spawnLocs = null;

    public static MapLocation[] attackableLocs = null;

    public static int numSoldiersBuilt = 0;
    public static int numMoppersBuilt = 0;
    public static int numSplashersBuilt = 0;
    public static final double IDEAL_SOLDIERS_RATIO = 0.4;
    public static final double IDEAL_MOPPERS_RATIO = 0.5;
    public static final double IDEAL_SPLASHERS_RATIO = 0.1;

    public static final int PAINT_TO_SAVE_DEFEND = UnitType.MOPPER.paintCost;
    public static final int MONEY_TO_SAVE_DEFEND = UnitType.MOPPER.moneyCost * 2;

    public static final int PAINT_TO_SAVE_BUILD = UnitType.LEVEL_ONE_PAINT_TOWER.paintCost;
    public static final int MONEY_TO_SAVE_BUILD = UnitType.LEVEL_ONE_PAINT_TOWER.moneyCost;

    public static void replaceWith(UnitType type) throws GameActionException {

        if (rc.senseNearbyRobots(-1,opponentTeam).length > 0) return;
        if (type == null) {
            rc.disintegrate();
            return;
        }

        //This if statement is always false. Add comms to make this plausible TODO
        if (rc.canMarkTowerPattern(type, location)) rc.markTowerPattern(type, location);
        Logger.addToIndicatorString("A R " + type);

        if (rc.senseNearbyRobots(-1,myTeam).length == 0) return;
        if (rc.getMoney() < getMoneyToSave() + type.moneyCost) return;
        UnitType patternAroundTower = Micro.towerPatternToComplete(location, true);
        if (patternAroundTower != type) return;
        rc.disintegrate();
    }

    public static void paintForGold() throws GameActionException {
        if (rc.getMoney() < 3000) return;
        if (rc.senseNearbyRobots(-1, opponentTeam).length > 0) return;
        UnitType patternAroundTower = Micro.towerPatternToComplete(location, true);
        if (patternAroundTower == null) return;
        if (rc.getPaint() >= UnitType.SOLDIER.paintCost && rc.getMoney() >= UnitType.SOLDIER.moneyCost)
        {
            attemptSpawn(UnitType.SOLDIER);
        }
        else if (rc.getPaint() < UnitType.SOLDIER.paintCost)
        {
            rc.disintegrate();
        }
    }

    public static void init(RobotController rc) throws GameActionException {
        Globals.init(rc);
        location = rc.getLocation();
        spawnLocs = new MapLocation[12];
        for (int i = 0; i < adjacentDirections.length; i++)
        {
            spawnLocs[i] = location.add(adjacentDirections[i]);
        }
        for (int i = 0; i < cardinalDirections.length; i++)
        {
            spawnLocs[adjacentDirections.length+i] = location.add(cardinalDirections[i]).add(cardinalDirections[i]);
        }

        int i = 0;
        MapLocation[] tempAttackableLocs = null;
        if (rc.getType() == UnitType.LEVEL_ONE_DEFENSE_TOWER)
        {
            //Radius = sqrt(20)
            tempAttackableLocs = new MapLocation[69];

            for (int x = -4; x < 5; x++){
                for (int y = -4; y < 5; y++)
                {
                    if (x*x + y*y <= 20)
                    {
                        MapLocation loc = new MapLocation(location.x + x, location.y + y);
                        if (rc.onTheMap(loc) && !rc.senseMapInfo(loc).isWall())
                        {
                            tempAttackableLocs[i] = loc;
                            i++;
                        }
                    }
                }
            }
        }
        else
        {
            //Radius = sqrt(9)
            tempAttackableLocs = new MapLocation[29];
            for (int x = -3; x < 4; x++){
                for (int y = -3; y < 4; y++)
                {
                    if (x*x + y*y <= 9)
                    {
                        MapLocation loc = new MapLocation(location.x + x, location.y + y);
                        if (rc.onTheMap(loc) && !rc.senseMapInfo(loc).isWall())
                        {
                            tempAttackableLocs[i] = loc;
                            i++;
                        }
                    }
                }
            }
        }
        attackableLocs = new MapLocation[i];
        System.arraycopy(tempAttackableLocs, 0, attackableLocs, 0, i);
    }

    //If we see enemies early on, build a mopper to protect the tower (meant for anti-zerg rush tactics)

    //This isn't good enough. The soldiers destroy the starting towers too quickly. TODO
    public static void defend() throws GameActionException {
        if (rc.getRoundNum() > 100) return;
        if (rc.getMoney() < UnitType.MOPPER.moneyCost || rc.getPaint() < UnitType.MOPPER.paintCost) return;
        RobotInfo[] enemyBots = rc.senseNearbyRobots(-1, opponentTeam);
        if (enemyBots.length == 0) return;
        attemptSpawn(UnitType.MOPPER);
    }

    public static int getPaintToSave()
    {
        int paintToSave = PAINT_TO_SAVE_BUILD;
        if (rc.getRoundNum() < 10 && rc.getType() == UnitType.LEVEL_ONE_PAINT_TOWER)
        {
            return 0;
        }
        if (rc.getRoundNum() < 100)
        {
            paintToSave += PAINT_TO_SAVE_DEFEND;
        }

        return paintToSave;
    }

    public static int getMoneyToSave()
    {
        int moneyToSave = MONEY_TO_SAVE_BUILD;
        if (rc.getRoundNum() < 10)
        {
            return 0;
        }
        else if (rc.getRoundNum() < 100)
        {
            moneyToSave += MONEY_TO_SAVE_DEFEND;
        }
        return moneyToSave;
    }

    public static void attemptSpawn(UnitType type) throws GameActionException {
        RobotInfo[] enemyBots = rc.senseNearbyRobots(-1, opponentTeam);
        if (enemyBots.length == 0)
        {
            for (int i = 0; i < spawnLocs.length; i++) {
                if (rc.canBuildRobot(type, spawnLocs[i])) {
                    rc.buildRobot(type, spawnLocs[i]);
                    switch (type)
                    {
                        case SOLDIER:
                            numSoldiersBuilt++;
                            break;
                        case SPLASHER:
                            numSplashersBuilt++;
                            break;
                        case MOPPER:
                            numMoppersBuilt++;
                            break;
                    }
                    break;
                }
            }
            return;
        }

        RobotInfo closestEnemy = enemyBots[0];
        int minDist = 100000;
        for (int i = 1; i < enemyBots.length; i++)
        {
            int dist = enemyBots[i].getLocation().distanceSquaredTo(location);
            if (dist < minDist)
            {
                minDist = dist;
                closestEnemy = enemyBots[i];
            }
        }

        //Try to spawn as close as possible to the closest enemy
        minDist = 100000;
        MapLocation bestSpawn = null;
        for (int i = 0; i < spawnLocs.length; i++) {
            if (rc.canBuildRobot(type, spawnLocs[i])) {
                int dist = spawnLocs[i].distanceSquaredTo(closestEnemy.getLocation());
                if (dist < minDist)
                {
                    minDist = dist;
                    bestSpawn = spawnLocs[i];

                }
            }
        }
        if (bestSpawn != null)
        {
            rc.buildRobot(type, bestSpawn);
            switch (type)
            {
                case SOLDIER:
                    numSoldiersBuilt++;
                    break;
                case SPLASHER:
                    numSplashersBuilt++;
                    break;
                case MOPPER:
                    numMoppersBuilt++;
                    break;
            }
        }
    }

    public static UnitType chooseBotToBuildNext()
    {
        int totalBotsBuilt = numMoppersBuilt + numSoldiersBuilt + numSplashersBuilt;
        double currentRatioMopper = (double) numMoppersBuilt / totalBotsBuilt;
        double currentRatioSoldier = (double) numSoldiersBuilt / totalBotsBuilt;
        double currentRatioSplasher = (double) numSplashersBuilt / totalBotsBuilt;
        double mopperRatioDiff = IDEAL_MOPPERS_RATIO - currentRatioMopper;
        double soldierRatioDiff = IDEAL_SOLDIERS_RATIO - currentRatioSoldier;
        double splasherRatioDiff = IDEAL_SPLASHERS_RATIO - currentRatioSplasher;
        if (mopperRatioDiff > soldierRatioDiff && mopperRatioDiff > splasherRatioDiff) return UnitType.MOPPER;
        if (soldierRatioDiff > mopperRatioDiff && soldierRatioDiff > splasherRatioDiff) return UnitType.SOLDIER;
        if (splasherRatioDiff > mopperRatioDiff && splasherRatioDiff > soldierRatioDiff && rc.getRoundNum() > 100) return UnitType.SPLASHER;
        return UnitType.SOLDIER;
    }

    public static void run() throws GameActionException {
        if (rc.getRoundNum() == 1)
        {
            attemptSpawn(UnitType.SOLDIER);
        }
        if (rc.getRoundNum() == 2 && rc.getType() == UnitType.LEVEL_ONE_PAINT_TOWER)
        {
            attemptSpawn(UnitType.MOPPER);
        }
        if (rc.getRoundNum() == 3)
        {
            attemptSpawn(UnitType.SOLDIER);
        }



        //paintForGold(); //PURE GOLD STRAT
        //defend();


        if ((rc.getType() == UnitType.LEVEL_ONE_MONEY_TOWER || rc.getType() == UnitType.LEVEL_TWO_MONEY_TOWER || rc.getType() == UnitType.LEVEL_THREE_MONEY_TOWER) && rc.getMoney() > 3000 && rc.getNumberTowers() > 20 ) replaceWith(null);
        if ((rc.getType() == UnitType.LEVEL_ONE_MONEY_TOWER || rc.getType() == UnitType.LEVEL_TWO_MONEY_TOWER || rc.getType() == UnitType.LEVEL_THREE_MONEY_TOWER) && rc.getMoney() > 5000) replaceWith(UnitType.LEVEL_ONE_PAINT_TOWER);

        //Too overzealous
        //if ((rc.getType() == UnitType.LEVEL_ONE_PAINT_TOWER || rc.getType() == UnitType.LEVEL_TWO_PAINT_TOWER || rc.getType() == UnitType.LEVEL_THREE_PAINT_TOWER) && rc.getNumberTowers() > 20 && rc.senseNearbyRobots(-1).length==0) rc.disintegrate();


        tryUpgrade();

        //Try to build soldiers
        int paintToSpend = rc.getPaint() - getPaintToSave();
        int chipsToSpend = rc.getMoney() - getMoneyToSave();

        if (rc.getRoundNum() >= 50) {
            UnitType botToBuild = chooseBotToBuildNext();
            if (paintToSpend >= botToBuild.paintCost && chipsToSpend >= botToBuild.moneyCost) attemptSpawn(botToBuild);
        }
        if (rc.isActionReady()) {
            //Attack enemy bots
            RobotInfo[] enemyBots = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, opponentTeam);
            if (enemyBots.length == 0) return;

            int lowestHealth = 100000;
            int secondLowestHealth = 100000;
            MapLocation target = null;
            for (int i = 0; i < enemyBots.length; i++) {
                int health = enemyBots[i].getHealth();
                if (health < lowestHealth) {
                    secondLowestHealth = lowestHealth;
                    lowestHealth = health;
                    target = enemyBots[i].getLocation();
                }
                else if (health < secondLowestHealth)
                {
                    secondLowestHealth = health;
                }
            }
            if (target != null)
            {
                //If we can destroy more than 1 unit at once, use AOE
                if (secondLowestHealth <= rc.getType().aoeAttackStrength) rc.attack(null);
                //If we can destroy a unit directly, use normal attack
                else if (lowestHealth <= rc.getType().attackStrength) rc.attack(target);
                //If there are many enemies in range, use AOE
                else if (enemyBots.length > 4) rc.attack(null);
                //Otherwise, regular attack the best target
                else rc.attack(target);
            }
        }
    }

    public static void tryUpgrade() throws GameActionException {
        int extraCash = 2000;
        int currentCash = rc.getMoney();
        boolean canUpgrade;
        switch (rc.getType())
        {
            case UnitType.LEVEL_ONE_PAINT_TOWER:
                canUpgrade = currentCash >= extraCash + UnitType.LEVEL_TWO_PAINT_TOWER.moneyCost;
                break;
            case UnitType.LEVEL_TWO_PAINT_TOWER:
                canUpgrade = currentCash >= extraCash + UnitType.LEVEL_THREE_PAINT_TOWER.moneyCost;
                break;
            case UnitType.LEVEL_ONE_MONEY_TOWER:
                canUpgrade = currentCash >= extraCash + UnitType.LEVEL_TWO_MONEY_TOWER.moneyCost;
                break;
            case UnitType.LEVEL_TWO_MONEY_TOWER:
                canUpgrade = currentCash >= extraCash + UnitType.LEVEL_THREE_MONEY_TOWER.moneyCost;
                break;
            case UnitType.LEVEL_ONE_DEFENSE_TOWER:
                canUpgrade = currentCash >= extraCash + UnitType.LEVEL_TWO_DEFENSE_TOWER.moneyCost;
                break;
            case UnitType.LEVEL_TWO_DEFENSE_TOWER:
                canUpgrade = currentCash >= extraCash + UnitType.LEVEL_THREE_DEFENSE_TOWER.moneyCost;
                break;
            default:
                canUpgrade = false;
        }
        if (canUpgrade)
        {
            if (rc.canUpgradeTower(rc.getLocation()))
            {
                rc.upgradeTower(rc.getLocation());
            }
        }
    }
}
