package thefirstsprint9;

import battlecode.common.GameActionException;
import battlecode.common.MapInfo;
import battlecode.common.PaintType;
import battlecode.common.RobotInfo;

public class CombatMicroSoldier extends Globals {
    public static void soldierAttack() throws GameActionException {
        if (!rc.isActionReady()) return;
        MapInfo target = null;
        MapInfo[] possibleOptions = rc.senseNearbyMapInfos(rc.getType().actionRadiusSquared);
        for (MapInfo option : possibleOptions)
        {
            target = soldierCompare(target, option);
        }
        if (target == null) return;
        if (rc.canAttack(target.getMapLocation()))
        {
            PaintType targetPaint = target.getPaint();
            RobotInfo targetBot = rc.senseRobotAtLocation(target.getMapLocation());
            boolean isRuinOrAllyTower = target.hasRuin() && (targetBot == null || targetBot.getTeam() == myTeam);
            boolean shouldAttack = !targetPaint.isEnemy() && ((target.getPaint() == PaintType.EMPTY && !isRuinOrAllyTower) || (targetPaint.isAlly() && PaintManager.shouldBeSecondary(target.getMapLocation()) != targetPaint.isSecondary()));
            if (shouldAttack)
            {
                Logger.addToIndicatorString("Micro: Attacking " + target.getMapLocation());
                PaintManager.soldierAttack(target.getMapLocation());
                return;
            }
        }
        Logger.addToIndicatorString("Micro: Cannot Attack " + target.getMapLocation());
    }

    /*
        Soldiers choose a place to attack as follows:
        - Enemy tower if pattern is disrupted
        - Enemy bot on marked tile
        - Ally bot on marked tile
        - Neutral Paint on marked tile
        - Enemy bot
        - Ally bot
        - Neutral Paint
     */
    public static MapInfo soldierCompare(MapInfo A, MapInfo B) throws GameActionException
    {
        if (A == null) return B;
        if (B == null) return A;
        if (!rc.canAttack(B.getMapLocation())) return A;
        if (!rc.canAttack(A.getMapLocation())) return B;

        PaintType paintA = A.getPaint();
        PaintType paintB = B.getPaint();
        PaintType markA = A.getMark();
        PaintType markB = B.getMark();
        RobotInfo botA = rc.senseRobotAtLocation(A.getMapLocation());
        RobotInfo botB = rc.senseRobotAtLocation(B.getMapLocation());

        boolean isRuinOrAllyTowerA = A.hasRuin() && (botA == null || botA.getTeam() == myTeam);
        boolean isRuinOrAllyTowerB = B.hasRuin() && (botB == null || botB.getTeam() == myTeam);

        boolean canPaintA = !paintA.isEnemy() && ((A.getPaint() == PaintType.EMPTY && !isRuinOrAllyTowerA) || (A.getPaint().isAlly() && PaintManager.shouldBeSecondary(A.getMapLocation()) != paintA.isSecondary()));
        boolean canPaintB = !paintB.isEnemy() && ((B.getPaint() == PaintType.EMPTY && !isRuinOrAllyTowerB) || (B.getPaint().isAlly() && PaintManager.shouldBeSecondary(B.getMapLocation()) != paintB.isSecondary()));

        if (!canPaintA) return B;
        if (!canPaintB) return A;



        boolean isEnemyA = botA != null && botA.getTeam() == opponentTeam;
        boolean isEnemyB = botB != null && botB.getTeam() == opponentTeam;

        if (isEnemyA && botA.getType().isTowerType())
        {
            //Check for pattern disruption
            if (Micro.towerPatternToComplete(A.getMapLocation(), false) == null)
            {
                return A;
            }
        }

        if (isEnemyB && botB.getType().isTowerType())
        {
            //Check for pattern disruption
            if (Micro.towerPatternToComplete(B.getMapLocation(), false) == null)
            {
                return B;
            }
        }

        boolean isMarkA = markA.isAlly() && paintA != A.getMark() && !paintA.isEnemy();
        boolean isMarkB = markB.isAlly() && paintB != B.getMark() && !paintB.isEnemy();

        if (isMarkA && !isMarkB) return A;
        if (isMarkB && !isMarkA) return B;

        if (isEnemyA) return A;
        if (isEnemyB) return B;

        boolean isAllyA = botA != null && botA.getTeam() == myTeam;
        boolean isAllyB = botB != null && botB.getTeam() == myTeam;

        if (isAllyA) return A;
        if (isAllyB) return B;

        return A;
    }
}
