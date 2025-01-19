package fivegtowers12;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;
import battlecode.common.UnitType;

/*
Moppers should move as follows:
- If low on health, prefer being further from enemy towers
- Move to minimize # of adjacent allied units
- Move to maximize # of adjacent enemy units
- Move to prefer more friendly paint tiles
 */

public class MicroInfoMopper {
    Direction dir;
    MapLocation loc;
    int minDistToTower = 100000;
    int minDistToEnemy = 100000;
    boolean canMoveM;
    boolean lowHealth;
    int paintLoss;
    int numAdjacentEnemies;

    public MicroInfoMopper(MapLocation loc, Direction dir, int paintLoss, int numAdjacentEnemies, boolean isLowHealth, boolean canMoveM) {
        this.dir = dir;
        this.loc = loc;
        this.lowHealth = isLowHealth;
        this.canMoveM = canMoveM;
        this.paintLoss = paintLoss;
        this.numAdjacentEnemies = numAdjacentEnemies;
    }


    void updateEnemy(RobotInfo unit)
    {
        if (loc == null) return;
        int dist = unit.getLocation().distanceSquaredTo(loc);
        if (dist < minDistToEnemy) minDistToEnemy = dist;
        if (unit.getType().isTowerType() && dist < minDistToTower) minDistToTower = dist;
    }

    int safe()
    {
        if (!canMoveM) return -9999;
        if (lowHealth && minDistToTower <= UnitType.LEVEL_ONE_DEFENSE_TOWER.actionRadiusSquared) return 30-minDistToTower;
        return 0;
    }
    boolean isBetterMoveThan(MicroInfoMopper M)
    {
        if (safe() < M.safe()) return false;
        if (safe() > M.safe()) return true;

        if (paintLoss < M.paintLoss) return true;
        if (paintLoss > M.paintLoss) return false;

        if (numAdjacentEnemies < M.numAdjacentEnemies) return false;
        if (numAdjacentEnemies > M.numAdjacentEnemies) return true;

        if (minDistToEnemy < M.minDistToEnemy) return true;
        if (minDistToEnemy > M.minDistToEnemy) return false;

        if (dir == Direction.CENTER) return false;
        if (M.dir == Direction.CENTER) return true;

        return false;
    }
}
