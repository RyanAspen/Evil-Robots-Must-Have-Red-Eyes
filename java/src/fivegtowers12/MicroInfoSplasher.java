package fivegtowers12;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;
import battlecode.common.UnitType;

/*
Splashers should move as follows:
- Never move within attack range of an enemy tower
- Move to mimimize # of adjacent allied units
- Move to prefer more friendly paint tiles

 */

public class MicroInfoSplasher {
    Direction dir;
    MapLocation loc;
    int minDistToTower = 100000;
    int minDistToEnemy = 100000;
    int minDistToMopper = 100000;
    int paintLoss;
    boolean canMoveM;

    public MicroInfoSplasher(MapLocation loc, Direction dir, int paintLoss, boolean canMoveM) {
        this.dir = dir;
        this.loc = loc;
        this.paintLoss = paintLoss;
        this.canMoveM = canMoveM;
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
        if (minDistToTower <= UnitType.LEVEL_ONE_PAINT_TOWER.actionRadiusSquared) return 30-minDistToTower;
        return 0;
    }


    boolean isBetterMove(MicroInfoSplasher M)
    {
        if (safe() < M.safe()) return false;
        if (safe() > M.safe()) return true;

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
