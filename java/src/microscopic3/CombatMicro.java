package microscopic3;

import battlecode.common.*;

public class CombatMicro extends Globals{

    public static void soldierAttack() throws GameActionException {
        if (!rc.isActionReady()) return;
        MapInfo target = null;
        MapInfo[] possibleOptions = rc.senseNearbyMapInfos(rc.getType().actionRadiusSquared);
        for (MapInfo option : possibleOptions)
        {
            if (!rc.canAttack(option.getMapLocation())) continue;
            target = soldierCompare(target, option);
        }
        if (target == null) return;
        if (rc.canAttack(target.getMapLocation()))
        {
            Logger.addToIndicatorString("Micro: Attacking " + target.getMapLocation());
            PaintManager.soldierAttack(target.getMapLocation());
        }
        else
        {
            Logger.addToIndicatorString("Micro: Cannot Attack " + target.getMapLocation());
        }
    }

    public static MapInfo soldierCompare(MapInfo A, MapInfo B) throws GameActionException {
        if (A == null) return B;
        if (B == null) return A;

        if (A.isWall()) return B;
        if (B.isWall()) return A;

        if (A.getPaint() == PaintType.EMPTY && B.getPaint() != PaintType.EMPTY) return A;
        if (A.getPaint() != PaintType.EMPTY && B.getPaint() == PaintType.EMPTY) return B;

        RobotInfo botA = rc.senseRobotAtLocation(A.getMapLocation());
        RobotInfo botB = rc.senseRobotAtLocation(B.getMapLocation());

        if (botA == null && A.hasRuin()) return B;
        if (botB == null && B.hasRuin()) return A;

        if (botA != null && botA.getTeam().isPlayer() && botA.getType().isTowerType()) return B;
        if (botB != null && botB.getTeam().isPlayer() && botB.getType().isTowerType()) return A;

        if (botA != null && botB == null) return A;
        if (botA == null && botB != null) return B;


        int distA = rc.getLocation().distanceSquaredTo(A.getMapLocation());
        int distB = rc.getLocation().distanceSquaredTo(B.getMapLocation());
        if (botA == null)
        {
            if (distA < distB) return A;
        }
        else
        {
            if (botA.getPaintAmount() < botB.getPaintAmount()) return A;
        }
        return B;
    }

    public static void mopperAttack() throws GameActionException {
        if (!rc.isActionReady()) return;
        attemptMopSweep();
        if (!rc.isActionReady()) return;
        MapInfo target = null;
        MapInfo[] possibleOptions = rc.senseNearbyMapInfos(rc.getType().actionRadiusSquared);
        RobotInfo[] allies = rc.senseNearbyRobots(-1, myTeam);
        for (MapInfo option : possibleOptions)
        {
            if (!rc.canAttack(option.getMapLocation())) continue;
            target = mopperCompare(target, option, allies);
        }
        if (target == null) return;
        if (rc.canAttack(target.getMapLocation()))
        {
            if ((rc.senseRobotAtLocation(target.getMapLocation()) != null && rc.senseRobotAtLocation(target.getMapLocation()).getTeam() == opponentTeam) || (!target.getPaint().isAlly() && target.getPaint() != PaintType.EMPTY)) {
                Logger.addToIndicatorString("Micro: Attacking " + target.getMapLocation());
                rc.attack(target.getMapLocation());
            }
        }
        else {
            Logger.addToIndicatorString("Micro: Cannot Attack " + target.getMapLocation());
        }
    }

    public static MapInfo mopperCompare(MapInfo A, MapInfo B, RobotInfo[] allies) throws GameActionException {
        if (A == null) return B;
        if (B == null) return A;
        if (A.hasRuin() || A.isWall()) return B;
        if (B.hasRuin() || B.isWall()) return A;

        if (!rc.canAttack(B.getMapLocation())) return A;
        if (!rc.canAttack(A.getMapLocation())) return B;
        if (A.getPaint() != PaintType.ENEMY_PRIMARY && A.getPaint() != PaintType.ENEMY_SECONDARY) return B;
        if (B.getPaint() != PaintType.ENEMY_PRIMARY && B.getPaint() != PaintType.ENEMY_SECONDARY) return A;

        RobotInfo botA = rc.senseRobotAtLocation(A.getMapLocation());
        RobotInfo botB = rc.senseRobotAtLocation(B.getMapLocation());
        boolean isEnemyAtA = botA != null && !botA.getTeam().isPlayer();
        boolean isEnemyAtB = botB != null && !botB.getTeam().isPlayer();
        if (isEnemyAtA && !isEnemyAtB) return A;
        if (!isEnemyAtA && isEnemyAtB) return B;



        // Should try to drain locations close to allied soldiers
        int minDistA = 100000;
        int minDistB = 100000;
        for (int i = 0; i < allies.length; i++)
        {
            if (allies[i].getType() != UnitType.SOLDIER) continue;
            int distA = allies[i].getLocation().distanceSquaredTo(A.getMapLocation());
            int distB = allies[i].getLocation().distanceSquaredTo(B.getMapLocation());
            if (distA < minDistA) minDistA = distA;
            if (distB < minDistB) minDistB = distB;
        }
        if (minDistA < minDistB) return A;
        return B;
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
}
