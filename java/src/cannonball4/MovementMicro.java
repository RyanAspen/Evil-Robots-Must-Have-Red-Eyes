package cannonball4;

import battlecode.common.*;

public class MovementMicro{
    static MicroInfo[] microInfos = null;
    static RobotController rc;

    public static Direction[] allDirections = Direction.values();
    static final int MAX_MICRO_BYTECODE_REMAINING = 5000;

    public MovementMicro(RobotController rc)
    {
        this.rc = rc;
    }

    /*
    public void micro() throws GameActionException {
        if (rc.senseNearbyRobots(-1, rc.getTeam().opponent()).length == 0) return;
        preprocessMicro();
        MapLocation bestTarget = null;
        if (rc.isActionReady())
        {
            bestTarget = getTarget();
        }
        if (bestTarget != null)
        {
            PaintManager.soldierAttack(bestTarget);
        }
        Direction bestDir = null;
        if (rc.isMovementReady())
        {
            bestDir = getMovement();
        }
        if (bestDir == null || bestDir == Direction.CENTER) return;
        rc.move(bestDir);
        if (rc.isActionReady())
        {
            bestTarget = getTarget();
        }
        if (bestTarget != null)
        {
            PaintManager.soldierAttack(bestTarget);
        }
    }

    public MapLocation getTarget() throws GameActionException {
        MicroInfo bestMicro = microInfos[0];
        for (int i = 1; i < microInfos.length; i++)
        {
            if (microInfos[i].isBetterTarget(bestMicro)) bestMicro = microInfos[i];
        }
        if (bestMicro.canAttack()) return bestMicro.loc;
        return null;
    }

    public Direction getMovement()
    {
        MicroInfo bestMicro = microInfos[0];
        for (int i = 1; i < microInfos.length; i++)
        {
            if (microInfos[i].isBetterMove(bestMicro)) bestMicro = microInfos[i];
        }
        if (bestMicro.canMoveM) return bestMicro.dir;
        return null;
    }
     */
    public boolean microMove(boolean canMove) throws GameActionException {
        if (!rc.isMovementReady() || !canMove) return false;
        preprocessMicro();
        MicroInfo bestMove = microInfos[0];
        for (int i = 1; i < microInfos.length; i++)
        {
            if (microInfos[i].isBetterMove(bestMove)) bestMove = microInfos[i];
        }
        if (bestMove != null && bestMove.dir != Direction.CENTER && rc.canMove(bestMove.dir))
        {
            Logger.addToIndicatorString("Micro: Moving towards" + bestMove.loc);
            rc.move(bestMove.dir);
            return false;
        }
        else if (bestMove != null && bestMove.dir == Direction.CENTER) //TODO: Results in no movement sometimes
        {
            return false;
        }
        Logger.addToIndicatorString("Micro: Not Moving");
        return true;
    }

    public void preprocessMicro() throws GameActionException {
        microInfos = new MicroInfo[9];
        for (int i = 0; i < allDirections.length; i++)
        {
            MapLocation loc = rc.getLocation().add(allDirections[i]);
            if (!rc.canSenseLocation(loc))
            {
                microInfos[i] = new MicroInfo();
            }
            else
            {
                microInfos[i] = new MicroInfo(allDirections[i]);
            }
        }
        RobotInfo[] allBots = rc.senseNearbyRobots();
        for (int i = 0; i < allBots.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) break;
            if (allBots[i].getTeam().isPlayer())
            {
                microInfos[0].updateAlly(allBots[i]);
                microInfos[1].updateAlly(allBots[i]);
                microInfos[2].updateAlly(allBots[i]);
                microInfos[3].updateAlly(allBots[i]);
                microInfos[4].updateAlly(allBots[i]);
                microInfos[5].updateAlly(allBots[i]);
                microInfos[6].updateAlly(allBots[i]);
                microInfos[7].updateAlly(allBots[i]);
                microInfos[8].updateAlly(allBots[i]);
            }
            else
            {
                microInfos[0].updateEnemy(allBots[i]);
                microInfos[1].updateEnemy(allBots[i]);
                microInfos[2].updateEnemy(allBots[i]);
                microInfos[3].updateEnemy(allBots[i]);
                microInfos[4].updateEnemy(allBots[i]);
                microInfos[5].updateEnemy(allBots[i]);
                microInfos[6].updateEnemy(allBots[i]);
                microInfos[7].updateEnemy(allBots[i]);
                microInfos[8].updateEnemy(allBots[i]);
            }
        }
        for (int i = 0; i < microInfos.length; i++)
        {
            if (Clock.getBytecodesLeft() < MAX_MICRO_BYTECODE_REMAINING) break;
            microInfos[i].updatePaint();
            microInfos[i].calcPaintDomination();
        }
    }

    class MicroInfo
    {
        Direction dir;
        MapLocation loc;

        int minDistToEnemy = 100000;
        int numAllies = 1;
        int numEnemies = 0;
        int numMoppersEnemy = 0;
        int numSoldiersEnemy = 0;
        int numSplashersEnemy = 0;
        int numMoppersAlly = rc.getType() == UnitType.MOPPER ? 1 : 0;
        int numSoldiersAlly = rc.getType() == UnitType.SOLDIER ? 1 : 0;
        int numSplashersAlly = rc.getType() == UnitType.SPLASHER ? 1 : 0;
        double DPS = 0;
        double paintLossForBot = 0;
        int numAdjacentAllies = 1;
        boolean canMoveM = true;
        PaintType startingPaint;
        int paintDomination = 0;
        boolean containsEnemy;

        public MicroInfo()
        {
            this.dir = Direction.CENTER;
            this.loc = null;
            canMoveM = false;
        }
        public MicroInfo(Direction dir) throws GameActionException {
            this.dir = dir;
            this.loc = rc.getLocation().add(dir);
            this.startingPaint = rc.senseMapInfo(this.loc).getPaint();
            if (dir != Direction.CENTER || !rc.canMove(dir)) this.canMoveM = false;
            RobotInfo bot = rc.senseRobotAtLocation(loc);
            this.containsEnemy = (bot != null) && !bot.getTeam().isPlayer();
        }

        void updateAlly(RobotInfo unit)
        {
            if (loc == null) return;
            int dist = unit.getLocation().distanceSquaredTo(loc);
            if (dist <= 2) numAdjacentAllies++;
            if (unit.getPaintAmount() < 10) return;
            if (unit.getType() == UnitType.MOPPER && dist <= UnitType.MOPPER.actionRadiusSquared)
            {
                numMoppersAlly++;
            }
            if (unit.getType() == UnitType.SOLDIER && dist <= UnitType.SOLDIER.actionRadiusSquared)
            {
                numSoldiersAlly++;
            }
            if (unit.getType() == UnitType.SPLASHER && dist <= 18)
            {
                numSplashersAlly++;
            }
            numAllies++;
        }

        void updateEnemy(RobotInfo unit)
        {
            if (loc == null) return;
            int dist = unit.getLocation().distanceSquaredTo(loc);
            if (dist < minDistToEnemy) minDistToEnemy = dist;
            if (unit.getPaintAmount() < 10) return;
            if (unit.getType() == UnitType.MOPPER && dist <= UnitType.MOPPER.actionRadiusSquared)
            {
                numMoppersEnemy++;
            }
            if (unit.getType() == UnitType.SOLDIER && dist <= UnitType.SOLDIER.actionRadiusSquared)
            {
                numSoldiersEnemy++;
            }
            if (unit.getType() == UnitType.SPLASHER && dist <= 18)
            {
                numSplashersEnemy++;
            }
            if (unit.getType().isTowerType() && dist <= unit.getType().actionRadiusSquared)
            {
                double paintPercentageLeft = (double) unit.getPaintAmount() / unit.getType().paintCapacity;
                double d = (double) (10 * unit.getType().attackStrength) / unit.getType().actionCooldown;
                if (paintPercentageLeft < 0.5) d *= 1+(1-2*paintPercentageLeft);
                DPS += d;
            }
            numEnemies++;
        }

        void updatePaint() throws GameActionException {
            if (loc == null) return;
            if (!rc.canSenseLocation(loc)) return;
            MapInfo mapInfo = rc.senseMapInfo(loc);
            switch (mapInfo.getPaint())
            {
                case ALLY_PRIMARY:
                case ALLY_SECONDARY:
                    paintLossForBot = 0;
                    break;
                case EMPTY:
                    paintLossForBot = GameConstants.PENALTY_NEUTRAL_TERRITORY;
                    break;
                default:
                    paintLossForBot = GameConstants.PENALTY_ENEMY_TERRITORY*numAdjacentAllies;
                    break;
            }
        }

        int safe()
        {
            if (!canMoveM) return -1;

            //Very unsafe
            if (paintLossForBot > 2) return 0;
            if (4*DPS > rc.getHealth()) return 0;

            //Moderately unsafe
            if (paintLossForBot > 0) return 1;
            if (numEnemies > numAllies) return 1;
            if (DPS > 0) return 1;

            //Relatively safe
            return 2;
        }

        void calcPaintDomination()
        {
            int d1 = Math.min(numSoldiersAlly, numMoppersAlly) + numSplashersAlly;
            int d2 = Math.min(numSoldiersEnemy, numMoppersEnemy) + numSplashersEnemy;
            paintDomination = d1 - d2;
        }


        boolean isBetterMove(MicroInfo M)
        {
            if (safe() > M.safe()) return true;
            if (safe() < M.safe()) return false;

            if (paintLossForBot < M.paintLossForBot) return true;
            if (paintLossForBot > M.paintLossForBot) return false;

            if (paintDomination > M.paintDomination) return true;
            if (paintDomination < M.paintDomination) return false;

            if (numEnemies < M.numEnemies) return true;
            if (numEnemies > M.numEnemies) return false;

            if (minDistToEnemy < M.minDistToEnemy) return true;
            if (minDistToEnemy > M.minDistToEnemy) return false;

            if (dir == Direction.CENTER) return true;
            if (M.dir == Direction.CENTER) return false;

            return true;
        }
    }
}
