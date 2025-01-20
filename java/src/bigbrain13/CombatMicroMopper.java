package bigbrain13;

import battlecode.common.*;

public class CombatMicroMopper extends Globals
{
    public static void mopperAttack() throws GameActionException {
        if (!rc.isActionReady()) return;
        attemptMopSweep();
        if (!rc.isActionReady()) return;
        MapInfo target = null;
        MapInfo[] possibleOptions = rc.senseNearbyMapInfos(rc.getType().actionRadiusSquared);
        for (MapInfo option : possibleOptions)
        {
            target = mopperCompare(target, option);
        }
        if (target == null) return;
        if (rc.canAttack(target.getMapLocation()))
        {
            boolean shouldAttack = target.getPaint().isEnemy() || (rc.senseRobotAtLocation(target.getMapLocation()) != null && rc.senseRobotAtLocation(target.getMapLocation()).getTeam() == opponentTeam);
            if (shouldAttack)
            {
                Logger.addToIndicatorString("Micro: Mopping " + target.getMapLocation());
                rc.attack(target.getMapLocation());
                return;
            }
        }
        Logger.addToIndicatorString("Micro: Cannot Mop " + target.getMapLocation());
    }

    public static void attemptMopSweep() throws GameActionException {
        if (!rc.isActionReady()) return;

        int maxNumTargets = 0;
        Direction bestDir = null;
        MapLocation currLoc = rc.getLocation();
        RobotInfo currBot = null;
        MapLocation target1, target2, target3, target4, target5, target6;

        //NORTH
        int numTargetsNorth = 0;
        target1 = currLoc.add(Direction.NORTH);
        target2 = currLoc.add(Direction.NORTHEAST);
        target3 = currLoc.add(Direction.NORTHWEST);
        target4 = target1.add(Direction.NORTH);
        target5 = target1.add(Direction.NORTHEAST);
        target6 = target1.add(Direction.NORTHWEST);
        if (rc.canSenseLocation(target1))
        {
            currBot = rc.senseRobotAtLocation(target1);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsNorth++;
        }
        if (rc.canSenseLocation(target2))
        {
            currBot = rc.senseRobotAtLocation(target2);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsNorth++;
        }
        if (rc.canSenseLocation(target3))
        {
            currBot = rc.senseRobotAtLocation(target3);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsNorth++;
        }
        if (rc.canSenseLocation(target4))
        {
            currBot = rc.senseRobotAtLocation(target4);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsNorth++;
        }
        if (rc.canSenseLocation(target5))
        {
            currBot = rc.senseRobotAtLocation(target5);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsNorth++;
        }
        if (rc.canSenseLocation(target6))
        {
            currBot = rc.senseRobotAtLocation(target6);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsNorth++;
        }

        if (numTargetsNorth > maxNumTargets)
        {
            bestDir = Direction.NORTH;
            maxNumTargets = numTargetsNorth;
        }

        //SOUTH
        int numTargetsSouth = 0;
        target1 = currLoc.add(Direction.SOUTH);
        target2 = currLoc.add(Direction.SOUTHEAST);
        target3 = currLoc.add(Direction.SOUTHWEST);
        target4 = target1.add(Direction.SOUTH);
        target5 = target1.add(Direction.SOUTHEAST);
        target6 = target1.add(Direction.SOUTHWEST);
        if (rc.canSenseLocation(target1))
        {
            currBot = rc.senseRobotAtLocation(target1);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsSouth++;
        }
        if (rc.canSenseLocation(target2))
        {
            currBot = rc.senseRobotAtLocation(target2);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsSouth++;
        }
        if (rc.canSenseLocation(target3))
        {
            currBot = rc.senseRobotAtLocation(target3);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsSouth++;
        }
        if (rc.canSenseLocation(target4))
        {
            currBot = rc.senseRobotAtLocation(target4);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsSouth++;
        }
        if (rc.canSenseLocation(target5))
        {
            currBot = rc.senseRobotAtLocation(target5);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsSouth++;
        }
        if (rc.canSenseLocation(target6))
        {
            currBot = rc.senseRobotAtLocation(target6);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsSouth++;
        }

        if (numTargetsSouth > maxNumTargets)
        {
            bestDir = Direction.SOUTH;
            maxNumTargets = numTargetsSouth;
        }

        //EAST
        int numTargetsEast = 0;
        target1 = currLoc.add(Direction.EAST);
        target2 = currLoc.add(Direction.SOUTHEAST);
        target3 = currLoc.add(Direction.NORTHEAST);
        target4 = target1.add(Direction.EAST);
        target5 = target1.add(Direction.SOUTHEAST);
        target6 = target1.add(Direction.NORTHEAST);
        if (rc.canSenseLocation(target1))
        {
            currBot = rc.senseRobotAtLocation(target1);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsEast++;
        }
        if (rc.canSenseLocation(target2))
        {
            currBot = rc.senseRobotAtLocation(target2);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsEast++;
        }
        if (rc.canSenseLocation(target3))
        {
            currBot = rc.senseRobotAtLocation(target3);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsEast++;
        }
        if (rc.canSenseLocation(target4))
        {
            currBot = rc.senseRobotAtLocation(target4);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsEast++;
        }
        if (rc.canSenseLocation(target5))
        {
            currBot = rc.senseRobotAtLocation(target5);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsEast++;
        }
        if (rc.canSenseLocation(target6))
        {
            currBot = rc.senseRobotAtLocation(target6);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsEast++;
        }

        if (numTargetsEast > maxNumTargets)
        {
            bestDir = Direction.EAST;
            maxNumTargets = numTargetsEast;
        }

        //WEST
        int numTargetsWest = 0;
        target1 = currLoc.add(Direction.WEST);
        target2 = currLoc.add(Direction.SOUTHWEST);
        target3 = currLoc.add(Direction.NORTHWEST);
        target4 = target1.add(Direction.WEST);
        target5 = target1.add(Direction.SOUTHWEST);
        target6 = target1.add(Direction.NORTHWEST);
        if (rc.canSenseLocation(target1))
        {
            currBot = rc.senseRobotAtLocation(target1);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsWest++;
        }
        if (rc.canSenseLocation(target2))
        {
            currBot = rc.senseRobotAtLocation(target2);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsWest++;
        }
        if (rc.canSenseLocation(target3))
        {
            currBot = rc.senseRobotAtLocation(target3);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsWest++;
        }
        if (rc.canSenseLocation(target4))
        {
            currBot = rc.senseRobotAtLocation(target4);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsWest++;
        }
        if (rc.canSenseLocation(target5))
        {
            currBot = rc.senseRobotAtLocation(target5);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsWest++;
        }
        if (rc.canSenseLocation(target6))
        {
            currBot = rc.senseRobotAtLocation(target6);
            if (currBot != null && currBot.getTeam() == opponentTeam) numTargetsWest++;
        }

        if (numTargetsWest > maxNumTargets)
        {
            bestDir = Direction.WEST;
            maxNumTargets = numTargetsWest;
        }

        if (maxNumTargets > 2)
        {
            Logger.addToIndicatorString("MopSwing" + bestDir);
            System.out.println(rc.getLocation() + " Mop Swing " + bestDir);
            rc.mopSwing(bestDir);
        }
    }

    /*
        Moppers choose a place to attack as follows:
        - Enemy Paint AND Enemy Unit
        - Enemy Paint AND Allied Unit
        - Enemy Unit
        - Enemy Paint
        - Ignore all other places
     */
    public static MapInfo mopperCompare(MapInfo A, MapInfo B) throws GameActionException {
        if (A == null) return B;
        if (B == null) return A;
        if (!rc.canAttack(B.getMapLocation())) return A;
        if (!rc.canAttack(A.getMapLocation())) return B;

        RobotInfo botA = rc.senseRobotAtLocation(A.getMapLocation());
        RobotInfo botB = rc.senseRobotAtLocation(B.getMapLocation());

        boolean isEnemyPaintA = A.getPaint().isEnemy();
        boolean isEnemyPaintB = B.getPaint().isEnemy();

        boolean markA = A.getMark().isAlly() && A.getPaint().isEnemy();
        boolean markB = B.getMark().isAlly() && B.getPaint().isEnemy();

        if (markA && !markB) return A;
        if (markB && !markA) return B;

        boolean isEnemyPaintAndEnemyUnitA = isEnemyPaintA && botA != null && botA.getTeam() == opponentTeam;
        boolean isEnemyPaintAndEnemyUnitB = isEnemyPaintB && botB != null && botB.getTeam() == opponentTeam;
        if (isEnemyPaintAndEnemyUnitA) return A;
        if (isEnemyPaintAndEnemyUnitB) return B;

        boolean isEnemyPaintAndAllyUnitA = isEnemyPaintA && botA != null && botA.getTeam() == myTeam;
        boolean isEnemyPaintAndAllyUnitB = isEnemyPaintB && botB != null && botB.getTeam() == myTeam;
        if (isEnemyPaintAndAllyUnitA) return A;
        if (isEnemyPaintAndAllyUnitB) return B;

        if (botA != null && botA.getTeam() == opponentTeam) return A;
        if (botB != null && botB.getTeam() == opponentTeam) return B;

        if (isEnemyPaintA) return A;
        if (isEnemyPaintB) return B;

        return A;
    }
}
