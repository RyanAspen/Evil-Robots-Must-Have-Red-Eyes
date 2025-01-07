package andthentherewasbot1;
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

    public static void run() throws GameActionException {
        //Try to build soldiers
        int paint = rc.getPaint();
        int chips = rc.getMoney();
        if (rng.nextBoolean()) {
            if (paint >= UnitType.SOLDIER.paintCost && chips >= UnitType.SOLDIER.moneyCost) {
                for (int i = 0; i < spawnLocs.length; i++) {
                    if (rc.canBuildRobot(UnitType.SOLDIER, spawnLocs[i])) {
                        rc.buildRobot(UnitType.SOLDIER, spawnLocs[i]);
                        break;
                    }
                }
            }
        }
        else
        {
            if (paint >= UnitType.MOPPER.paintCost && chips >= UnitType.MOPPER.moneyCost) {
                for (int i = 0; i < spawnLocs.length; i++) {
                    if (rc.canBuildRobot(UnitType.MOPPER, spawnLocs[i])) {
                        rc.buildRobot(UnitType.MOPPER, spawnLocs[i]);
                        break;
                    }
                }
            }
        }


        if (rc.isActionReady()) {
            //Attack enemy bots
            RobotInfo[] enemyBots = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, opponentTeam);

            if (enemyBots.length > 2)
            {
                //AOE ATTACK
                rc.attack(null);
                return;
            }

            //REGULAR ATTACK: For now, just attack the weakest enemy
            int lowestHealth = 1000;
            MapLocation target = null;
            for (int i = 0; i < enemyBots.length; i++) {
                int health = enemyBots[i].getHealth();
                if (health < lowestHealth) {
                    lowestHealth = health;
                    target = enemyBots[i].getLocation();
                }
            }
            if (target != null)
            {
                rc.attack(target);
            }
        }
    }
}
