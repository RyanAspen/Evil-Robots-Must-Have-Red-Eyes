package bloonsTowerDefense9;

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

public class MicroInfoSoldier {
    Direction dir;
    MapLocation loc;

    int minDistToEnemy = 100000;
    int minDistToTower = 100000;
    int paintLossForBot = 0;
    int numAdjacentAllies = 1;
    boolean canMoveM;
    PaintType paintType;
    MapInfo info;
    boolean lowHealth;

    public MicroInfoSoldier(MapLocation loc, Direction dir, MapInfo info, boolean isLowHealth, boolean canMoveM) {
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
        int dist = unit.getLocation().distanceSquaredTo(loc);
        if (dist <= 2) numAdjacentAllies++;
    }

    void updateEnemy(RobotInfo unit)
    {
        int dist = unit.getLocation().distanceSquaredTo(loc);
        Logger.addToIndicatorString(String.valueOf(dist));
        if (dist < minDistToEnemy) minDistToEnemy = dist;
    }

    void updatePaint() {
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
        return -paintLossForBot;
    }

    boolean isBetterMoveThan(MicroInfoSoldier M)
    {
        //Logger.addToIndicatorString(String.valueOf(M.minDistToEnemy));
        if (safe() < M.safe()) return false;
        if (safe() > M.safe()) return true;

        if (lowHealth)
        {
            if (minDistToTower < M.minDistToTower) return false;
            if (minDistToTower > M.minDistToTower) return true;
        }

        //if (dir == Direction.CENTER) return false;
        //if (M.dir == Direction.CENTER) return true;

        return minDistToEnemy < M.minDistToEnemy;
    }
}
