package bigbrain13;

import battlecode.common.*;

public class CombatMicroSoldier extends Globals {

    public static MapInfo[] attackOptions = new MapInfo[0];
    public static boolean canMoveToEnemyTower = false;

    public static void updateViableAttackTargets() throws GameActionException {
        MapInfo[] possibleOptions = rc.senseNearbyMapInfos();
        MapInfo[] viableOptions = new MapInfo[possibleOptions.length];
        int count = 0;
        canMoveToEnemyTower = false;
        for (int i = 0; i < possibleOptions.length; i++)
        {
            RobotInfo targetBot = rc.senseRobotAtLocation(possibleOptions[i].getMapLocation());
            if (!canMoveToEnemyTower && rc.getRoundNum() < 100) {
                if (targetBot != null && targetBot.getTeam() == opponentTeam && targetBot.getType().isTowerType()) {
                    canMoveToEnemyTower = true;
                }
            }
            if (possibleOptions[i].getMapLocation().distanceSquaredTo(rc.getLocation()) > rc.getType().actionRadiusSquared) continue;

            boolean isRuinOrAllyTower = possibleOptions[i].hasRuin() && (targetBot == null || targetBot.getTeam() == myTeam);
            boolean shouldAttack = !possibleOptions[i].isWall() && !possibleOptions[i].getPaint().isEnemy() && ((possibleOptions[i].getPaint() == PaintType.EMPTY && !isRuinOrAllyTower) || (possibleOptions[i].getPaint().isAlly() && PaintManager.shouldBeSecondary(possibleOptions[i].getMapLocation()) != possibleOptions[i].getPaint().isSecondary()));
            if (shouldAttack)
            {
                viableOptions[count] = possibleOptions[i];
                count++;
            }
        }
        attackOptions = new MapInfo[count];
        System.arraycopy(viableOptions, 0, attackOptions, 0 , count);
    }

    //Uses about 7k bytecode. That is too much, where is that happening?
    public static void soldierAttack() throws GameActionException {
        if (!rc.isActionReady() || rc.getPaint() < UnitType.SOLDIER.attackCost * 3) return;
        if (rc.getID() == 10564) System.out.println("3->" + Clock.getBytecodeNum());
        if (attackOptions.length == 0) return;
        MapInfo target = attackOptions[0];
        //MapInfo[] possibleOptions = rc.senseNearbyMapInfos(rc.getType().actionRadiusSquared);
        for (int i = 1; i < attackOptions.length; i++)
        {
            target = soldierCompare(target, attackOptions[i]);
        }
        if (rc.canAttack(target.getMapLocation()))
        {
            PaintType targetPaint = target.getPaint();
            RobotInfo targetBot = rc.senseRobotAtLocation(target.getMapLocation());
            boolean isRuinOrAllyTower = target.hasRuin() && (targetBot == null || targetBot.getTeam() == myTeam);
            boolean shouldAttack = !targetPaint.isEnemy() && ((target.getPaint() == PaintType.EMPTY && !isRuinOrAllyTower) || (targetPaint.isAlly() && PaintManager.shouldBeSecondary(target.getMapLocation()) != targetPaint.isSecondary()));
            if (shouldAttack)
            {
                PaintManager.soldierAttack(target.getMapLocation());
            }
        }
        if (rc.getID() == 10564) System.out.println("4->" + Clock.getBytecodeNum());
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
//        if (A == null) return B;
//        if (B == null) return A;
//        if (!rc.canAttack(B.getMapLocation())) return A;
//        if (!rc.canAttack(A.getMapLocation())) return B;


        RobotInfo botA = rc.senseRobotAtLocation(A.getMapLocation());
        RobotInfo botB = rc.senseRobotAtLocation(B.getMapLocation());

        //boolean isRuinOrAllyTowerA = A.hasRuin() && (botA == null || botA.getTeam() == myTeam);
        //boolean isRuinOrAllyTowerB = B.hasRuin() && (botB == null || botB.getTeam() == myTeam);

        //boolean canPaintA = !paintA.isEnemy() && ((A.getPaint() == PaintType.EMPTY && !isRuinOrAllyTowerA) || (A.getPaint().isAlly() && PaintManager.shouldBeSecondary(A.getMapLocation()) != paintA.isSecondary()));
        //boolean canPaintB = !paintB.isEnemy() && ((B.getPaint() == PaintType.EMPTY && !isRuinOrAllyTowerB) || (B.getPaint().isAlly() && PaintManager.shouldBeSecondary(B.getMapLocation()) != paintB.isSecondary()));

        //if (!canPaintA) return B;
        //if (!canPaintB) return A;



        boolean isEnemyA = botA != null && botA.getTeam() == opponentTeam;
        boolean isEnemyB = botB != null && botB.getTeam() == opponentTeam;

        if (isEnemyA && botA.getType().isTowerType())
        {
            return A;
            /*
            //Check for pattern disruption
            if (Micro.towerPatternToComplete(A.getMapLocation(), false) == null)
            {
                return A;
            }

             */
        }

        if (isEnemyB && botB.getType().isTowerType())
        {
            return B;
            /*
            //Check for pattern disruption
            if (Micro.towerPatternToComplete(B.getMapLocation(), false) == null)
            {
                return B;
            }

             */
        }

        PaintType paintA = A.getPaint();
        PaintType paintB = B.getPaint();
        PaintType markA = A.getMark();
        PaintType markB = B.getMark();

        boolean isMarkA = markA.isAlly() && paintA != A.getMark() && !paintA.isEnemy();
        boolean isMarkB = markB.isAlly() && paintB != B.getMark() && !paintB.isEnemy();

        if (isMarkA && !isMarkB) return A;
        if (isMarkB && !isMarkA) return B;

        if (isEnemyA) return A;
        if (isEnemyB) return B;

        if (paintA == PaintType.EMPTY && paintB != PaintType.EMPTY) return A;
        if (paintB == PaintType.EMPTY && paintA != PaintType.EMPTY) return B;

        boolean isAllyA = botA != null && botA.getTeam() == myTeam;
        boolean isAllyB = botB != null && botB.getTeam() == myTeam;

        if (isAllyA) return A;
        if (isAllyB) return B;

        return A;
    }
}
