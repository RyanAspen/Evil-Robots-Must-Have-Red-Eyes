package nanoscopic7;

import battlecode.common.*;

/*
Moppers should move as follows:
- If low on health, prefer being further from enemy towers
- Stay off of enemy tiles if possible, especially when there's adjacent allies
- Stay off of neutral tiles if possible
- Get close to enemies if we have a numbers advantage. Otherwise, don't prioritize it.

Info we need:
- Bot health
- Closest Enemy Tower
- Paint type of tile
- Closest enemy tile
- Closest Bot Enemy
- Paint Domination (all enemies and allies)

 */

public class MicroInfoSplasher {
    Direction dir;
    MapLocation loc;

    int minDistToEnemy = 100000;
    int minDistToTower = 100000;
    int numAllies = 1;
    int numEnemies = 0;
    int numMoppersEnemy = 0;
    int numSoldiersEnemy = 0;
    int numSplashersEnemy = 0;
    int numMoppersAlly = 0;
    int numSoldiersAlly = 0;
    int numSplashersAlly = 1;
    int paintLossForBot = 0;
    int numAdjacentAllies = 1;
    boolean canMoveM;
    PaintType paintType;
    int paintDomination = 0;
    MapInfo info;
    boolean lowHealth;

    public MicroInfoSplasher(MapLocation loc, Direction dir, MapInfo info, boolean isLowHealth, boolean canMoveM) {
        this.dir = dir;
        this.loc = loc;
        this.info = info;
        this.lowHealth = isLowHealth;
        if (info == null)
        {
            this.paintType = null;
        }
        else
        {
            this.paintType = info.getPaint();
        }
        this.canMoveM = canMoveM;
    }

    void updateAlly(RobotInfo unit)
    {
        if (loc == null) return;
        int dist = unit.getLocation().distanceSquaredTo(loc);
        if (dist <= 2) numAdjacentAllies++;
        if (unit.getPaintAmount() < 10) return; // Ignore units that are super low on paint
        switch (unit.getType())
        {
            case MOPPER:
                if (dist <= UnitType.MOPPER.actionRadiusSquared) numMoppersAlly++;
                break;
            case SOLDIER:
                if (dist <= UnitType.SOLDIER.actionRadiusSquared) numSoldiersAlly++;
                break;
            case SPLASHER:
                if (dist <= UnitType.SPLASHER.actionRadiusSquared) numSplashersAlly++;
                break;
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
            if (dist < minDistToTower) minDistToTower = dist;
        }
        numEnemies++;
    }

    void updatePaint() {
        if (loc == null) return;
        if (info == null) return;
        switch (paintType)
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
        if (!canMoveM) return -9999;
        if (!paintType.isAlly()) return -paintLossForBot;

        return 1;
    }

    void calcPaintDomination()
    {
        int d1 = Math.min(numSoldiersAlly, numMoppersAlly) + numSplashersAlly;
        int d2 = Math.min(numSoldiersEnemy, numMoppersEnemy) + numSplashersEnemy;
        paintDomination = d1 - d2;
    }


    boolean isBetterMove(MicroInfoSplasher M)
    {
        if (safe() < M.safe()) return false;
        if (safe() > M.safe()) return true;

        if (lowHealth)
        {
            if (minDistToTower < M.minDistToTower) return false;
            if (minDistToTower > M.minDistToTower) return true;
        }

        if (dir == Direction.CENTER) return false;
        if (M.dir == Direction.CENTER) return true;

        if (minDistToEnemy <= 18) return true;
        if (M.minDistToEnemy <= 18) return false;
        return false;
    }
}
