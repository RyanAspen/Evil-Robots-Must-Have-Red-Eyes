package nanoscopic7;

import battlecode.common.*;

/*
Moppers should move as follows:
- If low on health, prefer being further from enemy towers
- Stay off of enemy tiles if possible, especially when there's adjacent allies
- Stay off of neutral tiles unless an enemy tile that we can attack is close by
- Get close to enemies if we have a numbers advantage. Otherwise, don't prioritize it.

Info we need:
- Bot health
- Closest Enemy Tower
- Paint type of tile
- Closest enemy tile
- Closest Bot Enemy
- Paint Domination (all enemies and allies)

 */

public class MicroInfoMopper {
    Direction dir;
    MapLocation loc;
    int minDistToTower = 100000;
    double DPS = 0;
    int paintLossForBot = 0;
    int numAdjacentAllies = 1;
    boolean canMoveM;
    PaintType paintType;
    MapInfo info;
    boolean lowHealth;

    public boolean isEnemyPaintNear, isEnemyPaintWithMarkNear;

    public MicroInfoMopper(MapLocation loc, Direction dir, MapInfo info, boolean isLowHealth, boolean isEnemyPaintNear, boolean isEnemyPaintWithMarkNear, boolean canMoveM) {
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
        this.isEnemyPaintNear = isEnemyPaintNear;
        this.isEnemyPaintWithMarkNear = isEnemyPaintWithMarkNear;
    }

    void updateAlly(RobotInfo unit)
    {
        if (loc == null) return;
        int dist = unit.getLocation().distanceSquaredTo(loc);
        if (dist <= 2) numAdjacentAllies++;
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

        if (paintType.isEnemy()) return -paintLossForBot;
        if (!isEnemyPaintNear && paintType == PaintType.EMPTY) return 0;

        return 1;
    }
    boolean isBetterMoveThan(MicroInfoMopper M)
    {
        if (safe() < M.safe()) return false;
        if (safe() > M.safe()) return true;

        if (lowHealth)
        {
            if (minDistToTower < M.minDistToTower) return false;
            if (minDistToTower > M.minDistToTower) return true;
        }

        if (!isEnemyPaintNear && M.isEnemyPaintNear) return false;
        if (!M.isEnemyPaintNear && isEnemyPaintNear) return true;

        if (!isEnemyPaintWithMarkNear && M.isEnemyPaintWithMarkNear) return false;
        if (!M.isEnemyPaintWithMarkNear && isEnemyPaintWithMarkNear) return true;
        
        if (dir == Direction.CENTER) return false;
        if (M.dir == Direction.CENTER) return true;

        return false;
    }
}
