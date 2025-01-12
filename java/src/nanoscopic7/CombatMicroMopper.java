package nanoscopic7;

import battlecode.common.GameActionException;
import battlecode.common.MapInfo;
import battlecode.common.RobotInfo;

public class CombatMicroMopper extends Globals
{
    public static void mopperAttack() throws GameActionException {
        if (!rc.isActionReady()) return;
        //attemptMopSweep();
        //if (!rc.isActionReady()) return;
        MapInfo target = null;
        MapInfo[] possibleOptions = rc.senseNearbyMapInfos(rc.getType().actionRadiusSquared);
        for (MapInfo option : possibleOptions)
        {
            target = mopperCompare(target, option);
        }
        if (target == null) return;
        if (rc.canAttack(target.getMapLocation()))
        {
            boolean shouldAttack = target.getPaint().isEnemy() || (rc.senseRobotAtLocation(target.getMapLocation()) != null && !rc.senseRobotAtLocation(target.getMapLocation()).getTeam().isPlayer());
            if (shouldAttack)
            {
                Logger.addToIndicatorString("Micro: Mopping " + target.getMapLocation());
                rc.attack(target.getMapLocation());
                return;
            }
        }
        Logger.addToIndicatorString("Micro: Cannot Mop " + target.getMapLocation());
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

        boolean isEnemyPaintAndEnemyUnitA = isEnemyPaintA && botA != null && !botA.getTeam().isPlayer();
        boolean isEnemyPaintAndEnemyUnitB = isEnemyPaintB && botB != null && !botB.getTeam().isPlayer();
        if (isEnemyPaintAndEnemyUnitA) return A;
        if (isEnemyPaintAndEnemyUnitB) return B;

        boolean isEnemyPaintAndAllyUnitA = isEnemyPaintA && botA != null && botA.getTeam().isPlayer();
        boolean isEnemyPaintAndAllyUnitB = isEnemyPaintB && botB != null && botB.getTeam().isPlayer();
        if (isEnemyPaintAndAllyUnitA) return A;
        if (isEnemyPaintAndAllyUnitB) return B;

        if (botA != null && !botA.getTeam().isPlayer()) return A;
        if (botB != null && !botB.getTeam().isPlayer()) return B;

        if (isEnemyPaintA) return A;
        if (isEnemyPaintB) return B;

        return A;
    }
}
