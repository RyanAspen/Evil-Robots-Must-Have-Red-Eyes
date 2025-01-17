package improviseadaptovercome11;

import battlecode.common.*;

public class MovementMicro{

    static MicroInfoMopper[] microInfoMoppers = null;
    static MicroInfoSoldier[] microInfoSoldiers = null;
    static MicroInfoSplasher[] microInfoSplashers = null;
    static RobotController rc;

    public static Direction[] allDirections = Direction.values();
    static final int MAX_MICRO_BYTECODE_REMAINING = 5000;

    public MovementMicro(RobotController rc)
    {
        MovementMicro.rc = rc;
    }

    public boolean microMoveMopper(boolean canMove) throws GameActionException {
        if (!rc.isMovementReady() || !canMove) return false;
        preprocessMicroMopper();
        MicroInfoMopper bestMove = microInfoMoppers[0];
        for (int i = 1; i < microInfoMoppers.length; i++)
        {
            if (microInfoMoppers[i].isBetterMoveThan(bestMove)) bestMove = microInfoMoppers[i];
        }
        if (bestMove.dir != Direction.CENTER && rc.canMove(bestMove.dir))
        {
            Logger.addToIndicatorString("Mic-mov " + bestMove.loc);
            rc.move(bestMove.dir);
            return false;
        }
        else if (bestMove.dir == Direction.CENTER) //TODO: Results in no movement sometimes
        {
            Logger.addToIndicatorString("Mic-nomov " + bestMove.loc);
            Logger.addToIndicatorString(String.valueOf(bestMove.safe()));
            return false;
        }
        Logger.addToIndicatorString("Mic-nomove");
        return true;
    }

    public void preprocessMicroMopper() throws GameActionException {
        microInfoMoppers = new MicroInfoMopper[9];
        boolean lowHealth = rc.getHealth() < rc.getType().health * 0.3;
        for (int i = 0; i < allDirections.length; i++)
        {
            MapLocation loc = rc.getLocation().add(allDirections[i]);
            boolean canMoveM = rc.canMove(allDirections[i]) || allDirections[i] == Direction.CENTER;
            microInfoMoppers[i] = new MicroInfoMopper(loc, allDirections[i], AdjacencyManager.getPaintLoss(allDirections[i]), AdjacencyManager.getNumEnemiesAdjacent(allDirections[i]), lowHealth,canMoveM);
        }

        RobotInfo[] allEnemyBots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());

        for (int i = 0; i < allEnemyBots.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) {
                break;
            }
            microInfoMoppers[0].updateEnemy(allEnemyBots[i]);
            microInfoMoppers[1].updateEnemy(allEnemyBots[i]);
            microInfoMoppers[2].updateEnemy(allEnemyBots[i]);
            microInfoMoppers[3].updateEnemy(allEnemyBots[i]);
            microInfoMoppers[4].updateEnemy(allEnemyBots[i]);
            microInfoMoppers[5].updateEnemy(allEnemyBots[i]);
            microInfoMoppers[6].updateEnemy(allEnemyBots[i]);
            microInfoMoppers[7].updateEnemy(allEnemyBots[i]);
            microInfoMoppers[8].updateEnemy(allEnemyBots[i]);
        }
    }

    public boolean microMoveSoldier(boolean canMove) throws GameActionException {
        if (!rc.isMovementReady() || !canMove) return false;
        if (rc.getID() == 10564) System.out.println("1->" + Clock.getBytecodeNum());
        preprocessMicroSoldier();
        if (rc.getID() == 10564) System.out.println("2->" + Clock.getBytecodeNum());
        MicroInfoSoldier bestMove = microInfoSoldiers[0];
        for (int i = 1; i < microInfoSoldiers.length; i++)
        {
            if (microInfoSoldiers[i].isBetterMoveThan(bestMove)) bestMove = microInfoSoldiers[i];
        }
        if (bestMove.dir != Direction.CENTER && rc.canMove(bestMove.dir))
        {
            Logger.addToIndicatorString("Mic-mov " + bestMove.loc);
            rc.move(bestMove.dir);
            return false;
        }
        else if (bestMove.dir == Direction.CENTER) //TODO: Results in no movement sometimes
        {
            Logger.addToIndicatorString("Mic-nomov " + bestMove.loc);
            Logger.addToIndicatorString(String.valueOf(bestMove.safe()));
            return false;
        }
        Logger.addToIndicatorString("Mic-nomove");
        return true;
    }

    public void preprocessMicroSoldier() throws GameActionException {
        microInfoSoldiers = new MicroInfoSoldier[9];
        boolean canAttack = rc.isActionReady() && rc.getPaint() > rc.getType().attackCost;

        RobotInfo[] allBots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        boolean inTowerRange = false;
        for (int i = 0; i < allBots.length; i++)
        {
            if (allBots[i].getType().isTowerType() && allBots[i].getLocation().isWithinDistanceSquared(rc.getLocation(), allBots[i].getType().actionRadiusSquared))
            {
                inTowerRange = true;
                break;
            }
        }

        for (int i = 0; i < allDirections.length; i++)
        {
            MapLocation loc = rc.getLocation().add(allDirections[i]);
            boolean canMoveM = rc.canMove(allDirections[i]) || allDirections[i] == Direction.CENTER;
            microInfoSoldiers[i] = new MicroInfoSoldier(loc, allDirections[i],AdjacencyManager.getPaintLoss(allDirections[i]),AdjacencyManager.getNumEnemiesAdjacent(allDirections[i]),inTowerRange,canAttack,canMoveM);
        }

        for (int i = 0; i < allBots.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) {
                break;
            }
            microInfoSoldiers[0].updateEnemy(allBots[i]);
            microInfoSoldiers[1].updateEnemy(allBots[i]);
            microInfoSoldiers[2].updateEnemy(allBots[i]);
            microInfoSoldiers[3].updateEnemy(allBots[i]);
            microInfoSoldiers[4].updateEnemy(allBots[i]);
            microInfoSoldiers[5].updateEnemy(allBots[i]);
            microInfoSoldiers[6].updateEnemy(allBots[i]);
            microInfoSoldiers[7].updateEnemy(allBots[i]);
            microInfoSoldiers[8].updateEnemy(allBots[i]);
        }
    }

    public boolean microMoveSplasher(boolean canMove) throws GameActionException {
        if (!rc.isMovementReady() || !canMove) return false;
        preprocessMicroSplasher();
        MicroInfoSplasher bestMove = microInfoSplashers[0];
        for (int i = 1; i < microInfoSplashers.length; i++)
        {
            if (microInfoSplashers[i].isBetterMove(bestMove)) bestMove = microInfoSplashers[i];
        }

        if (bestMove.dir != Direction.CENTER && rc.canMove(bestMove.dir))
        {
            Logger.addToIndicatorString("Mic-mov " + bestMove.loc);
            rc.move(bestMove.dir);
            return false;
        }
        else if (bestMove.dir == Direction.CENTER) //TODO: Results in no movement sometimes
        {
            Logger.addToIndicatorString("Mic-nomov " + bestMove.loc);
            Logger.addToIndicatorString(String.valueOf(bestMove.safe()));
            return false;
        }
        Logger.addToIndicatorString("Mic-nomove");
        return true;
    }

    public void preprocessMicroSplasher() throws GameActionException {
        microInfoSplashers = new MicroInfoSplasher[9];
        for (int i = 0; i < allDirections.length; i++)
        {
            MapLocation loc = rc.getLocation().add(allDirections[i]);
            microInfoSplashers[i] = new MicroInfoSplasher(loc, allDirections[i],AdjacencyManager.getPaintLoss(allDirections[i]),rc.canMove(allDirections[i]));
        }

        RobotInfo[] allEnemyBots = rc.senseNearbyRobots(-1, rc.getTeam().opponent());

        for (int i = 0; i < allEnemyBots.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) break;
            microInfoSplashers[0].updateEnemy(allEnemyBots[i]);
            microInfoSplashers[1].updateEnemy(allEnemyBots[i]);
            microInfoSplashers[2].updateEnemy(allEnemyBots[i]);
            microInfoSplashers[3].updateEnemy(allEnemyBots[i]);
            microInfoSplashers[4].updateEnemy(allEnemyBots[i]);
            microInfoSplashers[5].updateEnemy(allEnemyBots[i]);
            microInfoSplashers[6].updateEnemy(allEnemyBots[i]);
            microInfoSplashers[7].updateEnemy(allEnemyBots[i]);
            microInfoSplashers[8].updateEnemy(allEnemyBots[i]);
        }
    }

}
