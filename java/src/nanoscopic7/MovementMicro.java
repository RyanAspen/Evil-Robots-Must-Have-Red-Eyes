package nanoscopic7;

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
        Logger.addToIndicatorString(String.valueOf(bestMove.isEnemyPaintNear));
        if (bestMove.dir != Direction.CENTER && rc.canMove(bestMove.dir))
        {
            Logger.addToIndicatorString("Micro: Moving towards" + bestMove.loc);
            rc.move(bestMove.dir);
            return false;
        }
        else if (bestMove.dir == Direction.CENTER) //TODO: Results in no movement sometimes
        {
            Logger.addToIndicatorString("Micro: Stay Put" + bestMove.loc);
            Logger.addToIndicatorString(String.valueOf(bestMove.safe()));
            return false;
        }
        Logger.addToIndicatorString("Micro: Not Moving");
        return true;
    }

    public void preprocessMicroMopper() throws GameActionException {
        microInfoMoppers = new MicroInfoMopper[9];
        boolean lowHealth = rc.getHealth() < rc.getType().health * 0.3;
        boolean[][] isEnemyPaint = new boolean[5][5];
        boolean[][] isEnemyPaintWithMark = new boolean[5][5];
        MapLocation currLoc = rc.getLocation();
        for (int y = 0; y < 5; y++)
        {
            for (int x = 0; x < 5; x++)
            {
                MapLocation newLoc = new MapLocation(currLoc.x-2+x, currLoc.y+2-y);
                if (!rc.canSenseLocation(newLoc)) continue;
                MapInfo info = rc.senseMapInfo(newLoc);
                isEnemyPaint[y][x] = info.getPaint().isEnemy();
                if (isEnemyPaint[y][x]) isEnemyPaintWithMark[y][x] = info.getMark().isAlly();
            }
        }
        boolean[] nearEnemyPaint = new boolean[9];
        boolean[] nearEnemyPaintWithMark = new boolean[9];
        //NORTH
        nearEnemyPaint[0] = isEnemyPaint[0][1] || isEnemyPaint[0][2] || isEnemyPaint[0][3] || isEnemyPaint[1][1] || isEnemyPaint[1][3] || isEnemyPaint[2][1] || isEnemyPaint[2][2] || isEnemyPaint[2][3];

        //NORTHEAST
        nearEnemyPaint[1] = isEnemyPaint[0][2] || isEnemyPaint[0][3] || isEnemyPaint[0][4] || isEnemyPaint[1][2] || isEnemyPaint[1][4] || isEnemyPaint[2][2] || isEnemyPaint[2][3] || isEnemyPaint[2][4];

        //EAST
        nearEnemyPaint[2] = isEnemyPaint[1][2] || isEnemyPaint[1][3] || isEnemyPaint[1][4] || isEnemyPaint[2][2] || isEnemyPaint[2][3] || isEnemyPaint[3][2] || isEnemyPaint[3][3] || isEnemyPaint[3][4];

        //SOUTHEAST
        nearEnemyPaint[3] = isEnemyPaint[2][2] || isEnemyPaint[2][3] || isEnemyPaint[2][4] || isEnemyPaint[3][2] || isEnemyPaint[3][4] || isEnemyPaint[4][2] || isEnemyPaint[4][3] || isEnemyPaint[4][4];

        //SOUTH
        nearEnemyPaint[4] = isEnemyPaint[2][1] || isEnemyPaint[2][2] || isEnemyPaint[2][3] || isEnemyPaint[3][1] || isEnemyPaint[3][3] || isEnemyPaint[4][1] || isEnemyPaint[4][2] || isEnemyPaint[4][3];

        //SOUTHWEST
        nearEnemyPaint[5] = isEnemyPaint[2][0] || isEnemyPaint[2][1] || isEnemyPaint[2][2] || isEnemyPaint[3][0] || isEnemyPaint[3][2] || isEnemyPaint[4][0] || isEnemyPaint[4][1] || isEnemyPaint[4][2];

        //WEST
        nearEnemyPaint[6] = isEnemyPaint[1][0] || isEnemyPaint[1][1] || isEnemyPaint[1][2] || isEnemyPaint[2][0] || isEnemyPaint[2][2] || isEnemyPaint[3][0] || isEnemyPaint[3][1] || isEnemyPaint[3][2];

        //NORTHWEST
        nearEnemyPaint[7] = isEnemyPaint[0][0] || isEnemyPaint[0][1] || isEnemyPaint[0][2] || isEnemyPaint[1][0] || isEnemyPaint[1][2] || isEnemyPaint[2][0] || isEnemyPaint[2][1] || isEnemyPaint[2][2];

        //CENTER
        nearEnemyPaint[8] = isEnemyPaint[1][1] || isEnemyPaint[1][2] || isEnemyPaint[1][3] || isEnemyPaint[2][1] || isEnemyPaint[2][3] || isEnemyPaint[3][1] || isEnemyPaint[3][2] || isEnemyPaint[3][3];

        //NORTH
        nearEnemyPaintWithMark[0] = isEnemyPaintWithMark[0][1] || isEnemyPaintWithMark[0][2] || isEnemyPaintWithMark[0][3] || isEnemyPaintWithMark[1][1] || isEnemyPaintWithMark[1][3] || isEnemyPaintWithMark[2][1] || isEnemyPaintWithMark[2][2] || isEnemyPaintWithMark[2][3];

        //NORTHEAST
        nearEnemyPaintWithMark[1] = isEnemyPaintWithMark[0][2] || isEnemyPaintWithMark[0][3] || isEnemyPaintWithMark[0][4] || isEnemyPaintWithMark[1][2] || isEnemyPaintWithMark[1][4] || isEnemyPaintWithMark[2][2] || isEnemyPaintWithMark[2][3] || isEnemyPaintWithMark[2][4];

        //EAST
        nearEnemyPaintWithMark[2] = isEnemyPaintWithMark[1][2] || isEnemyPaintWithMark[1][3] || isEnemyPaintWithMark[1][4] || isEnemyPaintWithMark[2][2] || isEnemyPaintWithMark[2][3] || isEnemyPaintWithMark[3][2] || isEnemyPaintWithMark[3][3] || isEnemyPaintWithMark[3][4];

        //SOUTHEAST
        nearEnemyPaintWithMark[3] = isEnemyPaintWithMark[2][2] || isEnemyPaintWithMark[2][3] || isEnemyPaintWithMark[2][4] || isEnemyPaintWithMark[3][2] || isEnemyPaintWithMark[3][4] || isEnemyPaintWithMark[4][2] || isEnemyPaintWithMark[4][3] || isEnemyPaintWithMark[4][4];

        //SOUTH
        nearEnemyPaintWithMark[4] = isEnemyPaintWithMark[2][1] || isEnemyPaintWithMark[2][2] || isEnemyPaintWithMark[2][3] || isEnemyPaintWithMark[3][1] || isEnemyPaintWithMark[3][3] || isEnemyPaintWithMark[4][1] || isEnemyPaintWithMark[4][2] || isEnemyPaintWithMark[4][3];

        //SOUTHWEST
        nearEnemyPaintWithMark[5] = isEnemyPaintWithMark[2][0] || isEnemyPaintWithMark[2][1] || isEnemyPaintWithMark[2][2] || isEnemyPaintWithMark[3][0] || isEnemyPaintWithMark[3][2] || isEnemyPaintWithMark[4][0] || isEnemyPaintWithMark[4][1] || isEnemyPaintWithMark[4][2];

        //WEST
        nearEnemyPaintWithMark[6] = isEnemyPaintWithMark[1][0] || isEnemyPaintWithMark[1][1] || isEnemyPaintWithMark[1][2] || isEnemyPaintWithMark[2][0] || isEnemyPaintWithMark[2][2] || isEnemyPaintWithMark[3][0] || isEnemyPaintWithMark[3][1] || isEnemyPaintWithMark[3][2];

        //NORTHWEST
        nearEnemyPaintWithMark[7] = isEnemyPaintWithMark[0][0] || isEnemyPaintWithMark[0][1] || isEnemyPaintWithMark[0][2] || isEnemyPaintWithMark[1][0] || isEnemyPaintWithMark[1][2] || isEnemyPaintWithMark[2][0] || isEnemyPaintWithMark[2][1] || isEnemyPaintWithMark[2][2];

        //CENTER
        nearEnemyPaintWithMark[8] = isEnemyPaintWithMark[1][1] || isEnemyPaintWithMark[1][2] || isEnemyPaintWithMark[1][3] || isEnemyPaintWithMark[2][1] || isEnemyPaintWithMark[2][3] || isEnemyPaintWithMark[3][1] || isEnemyPaintWithMark[3][2] || isEnemyPaintWithMark[3][3];


        for (int i = 0; i < allDirections.length; i++)
        {
            MapLocation loc = rc.getLocation().add(allDirections[i]);
            MapInfo info = null;
            if (rc.canSenseLocation(loc)) info = rc.senseMapInfo(loc);
            boolean canMoveM = rc.canMove(allDirections[i]) || allDirections[i] == Direction.CENTER;
            microInfoMoppers[i] = new MicroInfoMopper(loc, allDirections[i],info,lowHealth,nearEnemyPaint[i],nearEnemyPaintWithMark[i],canMoveM);
        }

        RobotInfo[] allBots = rc.senseNearbyRobots(-1, rc.getTeam());

        for (int i = 0; i < allBots.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) break;
            microInfoMoppers[0].updateAlly(allBots[i]);
            microInfoMoppers[1].updateAlly(allBots[i]);
            microInfoMoppers[2].updateAlly(allBots[i]);
            microInfoMoppers[3].updateAlly(allBots[i]);
            microInfoMoppers[4].updateAlly(allBots[i]);
            microInfoMoppers[5].updateAlly(allBots[i]);
            microInfoMoppers[6].updateAlly(allBots[i]);
            microInfoMoppers[7].updateAlly(allBots[i]);
            microInfoMoppers[8].updateAlly(allBots[i]);
        }

        for (int i = 0; i < microInfoMoppers.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) break;
            microInfoMoppers[i].updatePaint();
        }
    }

    public boolean microMoveSoldier(boolean canMove) throws GameActionException {
        if (!rc.isMovementReady() || !canMove) return false;
        preprocessMicroSoldier();
        MicroInfoSoldier bestMove = microInfoSoldiers[0];
        for (int i = 1; i < microInfoSoldiers.length; i++)
        {
            if (microInfoSoldiers[i].isBetterMoveThan(bestMove)) bestMove = microInfoSoldiers[i];
        }
        if (bestMove != null && bestMove.dir != Direction.CENTER && rc.canMove(bestMove.dir))
        {
            Logger.addToIndicatorString("Micro: Moving towards" + bestMove.loc);
            Logger.addToIndicatorString(String.valueOf(bestMove.paintLossForBot));
            rc.move(bestMove.dir);
            return false;
        }
        else if (bestMove != null && bestMove.dir == Direction.CENTER) //TODO: Results in no movement sometimes
        {
            Logger.addToIndicatorString("Micro: Stay Put" + bestMove.loc);
            Logger.addToIndicatorString(String.valueOf(bestMove.safe()));
            return false;
        }
        Logger.addToIndicatorString("Micro: Not Moving");
        return true;
    }

    public void preprocessMicroSoldier() throws GameActionException {
        microInfoSoldiers = new MicroInfoSoldier[9];
        boolean lowHealth = rc.getHealth() < rc.getType().health * 0.2;
        for (int i = 0; i < allDirections.length; i++)
        {
            MapLocation loc = rc.getLocation().add(allDirections[i]);
            MapInfo info = null;
            if (rc.canSenseLocation(loc)) info = rc.senseMapInfo(loc);
            boolean canMoveM = rc.canMove(allDirections[i]) || allDirections[i] == Direction.CENTER;
            microInfoSoldiers[i] = new MicroInfoSoldier(loc, allDirections[i],info,lowHealth,canMoveM);
        }

        RobotInfo[] allBots = rc.senseNearbyRobots();

        for (int i = 0; i < allBots.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) break;
            if (allBots[i].getTeam().isPlayer())
            {
                microInfoSoldiers[0].updateAlly(allBots[i]);
                microInfoSoldiers[1].updateAlly(allBots[i]);
                microInfoSoldiers[2].updateAlly(allBots[i]);
                microInfoSoldiers[3].updateAlly(allBots[i]);
                microInfoSoldiers[4].updateAlly(allBots[i]);
                microInfoSoldiers[5].updateAlly(allBots[i]);
                microInfoSoldiers[6].updateAlly(allBots[i]);
                microInfoSoldiers[7].updateAlly(allBots[i]);
                microInfoSoldiers[8].updateAlly(allBots[i]);
            }
            else
            {
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
        for (int i = 0; i < microInfoSoldiers.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) break;
            microInfoSoldiers[i].updatePaint();
            microInfoSoldiers[i].calcPaintDomination();
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

        if (bestMove != null && bestMove.dir != Direction.CENTER && rc.canMove(bestMove.dir))
        {
            Logger.addToIndicatorString("Micro: Moving towards" + bestMove.loc);
            rc.move(bestMove.dir);
            return false;
        }
        else if (bestMove != null && bestMove.dir == Direction.CENTER) //TODO: Results in no movement sometimes
        {
            Logger.addToIndicatorString("Micro: Stay Put" + bestMove.loc);
            Logger.addToIndicatorString(String.valueOf(bestMove.safe()));
            return false;
        }
        Logger.addToIndicatorString("Micro: Not Moving");
        return true;
    }

    public void preprocessMicroSplasher() throws GameActionException {
        microInfoSplashers = new MicroInfoSplasher[9];
        boolean lowHealth = rc.getHealth() < rc.getType().health * 0.4;
        for (int i = 0; i < allDirections.length; i++)
        {
            MapLocation loc = rc.getLocation().add(allDirections[i]);
            MapInfo info = null;
            if (rc.canSenseLocation(loc)) info = rc.senseMapInfo(loc);
            microInfoSplashers[i] = new MicroInfoSplasher(loc, allDirections[i],info,lowHealth,rc.canMove(allDirections[i]));
        }

        RobotInfo[] allBots = rc.senseNearbyRobots();

        for (int i = 0; i < allBots.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) break;
            if (allBots[i].getTeam().isPlayer())
            {
                microInfoSplashers[0].updateAlly(allBots[i]);
                microInfoSplashers[1].updateAlly(allBots[i]);
                microInfoSplashers[2].updateAlly(allBots[i]);
                microInfoSplashers[3].updateAlly(allBots[i]);
                microInfoSplashers[4].updateAlly(allBots[i]);
                microInfoSplashers[5].updateAlly(allBots[i]);
                microInfoSplashers[6].updateAlly(allBots[i]);
                microInfoSplashers[7].updateAlly(allBots[i]);
                microInfoSplashers[8].updateAlly(allBots[i]);
            }
            else
            {
                microInfoSplashers[0].updateEnemy(allBots[i]);
                microInfoSplashers[1].updateEnemy(allBots[i]);
                microInfoSplashers[2].updateEnemy(allBots[i]);
                microInfoSplashers[3].updateEnemy(allBots[i]);
                microInfoSplashers[4].updateEnemy(allBots[i]);
                microInfoSplashers[5].updateEnemy(allBots[i]);
                microInfoSplashers[6].updateEnemy(allBots[i]);
                microInfoSplashers[7].updateEnemy(allBots[i]);
                microInfoSplashers[8].updateEnemy(allBots[i]);
            }
        }
        for (int i = 0; i < microInfoSplashers.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) break;
            microInfoSplashers[i].updatePaint();
            microInfoSplashers[i].calcPaintDomination();
        }
    }

}
