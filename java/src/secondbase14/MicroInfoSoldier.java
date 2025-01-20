package secondbase14;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;
import battlecode.common.UnitType;

/*
Soldiers should move as follows:
- If we are just outside enemy tower attack range, move toward enemy tower attack range
- If we are just inside enemy tower attack range, move out of enemy tower attack range
- Move to minimize # of adjacent allied units
- Move to prefer more friendly paint tiles
 */

public class MicroInfoSoldier {
    Direction dir;
    MapLocation loc;
    int minDistToTower = 100000;
    int minDistToEnemy = 100000;
    int minDistToMopper = 100000;
    int paintLoss;
    int adjacentEnemies;
    boolean canMoveM;
    boolean alreadyInTowerRange;
    boolean canAttack;

    public MicroInfoSoldier(MapLocation loc, Direction dir, int paintLoss, int adjacentEnemies, boolean alreadyInTowerRange, boolean canAttack, boolean canMoveM) {
        this.dir = dir;
        this.loc = loc;
        this.paintLoss = paintLoss;
        this.canMoveM = canMoveM;
        this.alreadyInTowerRange = alreadyInTowerRange;
        this.canAttack = canAttack;
        this.adjacentEnemies = adjacentEnemies;
    }

    void updateEnemy(RobotInfo unit)
    {
        if (loc == null) return;
        int dist = unit.getLocation().distanceSquaredTo(loc);
        if (dist < minDistToEnemy) {
            minDistToEnemy = dist;
        }
        if (unit.getType() == UnitType.MOPPER && dist < minDistToMopper) minDistToMopper = dist;
        if (unit.getType().isTowerType() && dist < minDistToTower) minDistToTower = dist;
    }

    int safe()
    {
        if (!canMoveM) return -9999;
        if (alreadyInTowerRange && minDistToTower <= UnitType.LEVEL_ONE_MONEY_TOWER.actionRadiusSquared) return -1;
        if (!canAttack && minDistToTower <= UnitType.LEVEL_ONE_MONEY_TOWER.actionRadiusSquared) return -1;
        return 0;
    }

    boolean isBetterMoveThan(MicroInfoSoldier M)
    {
        if (safe() < M.safe()) return false;
        if (safe() > M.safe()) return true;

        if (canAttack && !alreadyInTowerRange)
        {
            if (minDistToTower < M.minDistToTower) return true;
            if (minDistToTower > M.minDistToTower) return false;
        }

        if (paintLoss < M.paintLoss) return true;
        if (paintLoss > M.paintLoss) return false;

        //We don't want to be next to moppers if possible.
        if (minDistToMopper <= 2) return false;
        if (M.minDistToMopper <= 2) return true;
        //if (adjacentEnemies < M.adjacentEnemies) return true;
        //if (adjacentEnemies > M.adjacentEnemies) return false;

        //But we do want to be near enemies in general
        if (minDistToEnemy < M.minDistToEnemy) return true;
        if (minDistToEnemy > M.minDistToEnemy) return false;

        if (dir == Direction.CENTER) return false;
        if (M.dir == Direction.CENTER) return true;

        return false;
    }
}
