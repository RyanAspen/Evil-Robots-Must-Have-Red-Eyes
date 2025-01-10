package pushthefrontline5;

/*
Splashers are more expensive than soldiers but are much more efficient at painting tiles than soldiers in ideal circumstances. This
does cost more paint, however, so they should really only be used once we've secured an economy advantage

 */

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapInfo;

public class Splasher extends Globals {

    public static int paintAttackHeuristic(MapInfo info) throws GameActionException {
        int h = 0;
        for (int i = 0; i < allDirections.length; i++)
        {
            if (!rc.canSenseLocation(info.getMapLocation().add(allDirections[i]))) continue;
            MapInfo currInfo = rc.senseMapInfo(info.getMapLocation().add(allDirections[i]));
            if (currInfo.isWall()) continue;
            switch (currInfo.getPaint())
            {
                case ALLY_PRIMARY:
                case ALLY_SECONDARY:
                    h -= 1;
                    break;
                case EMPTY:
                    h += 1;
                    break;
                default:
                    if (currInfo.getMapLocation().isWithinDistanceSquared(info.getMapLocation(),1))
                        h += 3;
                    break;
            }
        }
        return h;
    }

    //Used when not in combat
    public static void paint() throws GameActionException {
        if (!rc.isActionReady() || rc.getPaint() <= rc.getType().attackCost + 50) return;
        MapInfo[] mapInfos = rc.senseNearbyMapInfos(rc.getType().actionRadiusSquared);
        MapInfo target = null;
        int bestVal = -1000000;
        for (int i = 0; i < mapInfos.length; i++)
        {
            int h = paintAttackHeuristic(mapInfos[i]);
            if (h > bestVal)
            {
                bestVal = h;
                target = mapInfos[i];
            }
        }
        if (target != null && bestVal > 0 && rc.canAttack(target.getMapLocation()))
        {
            rc.attack(target.getMapLocation());
        }
    }

    public static void run() throws GameActionException {
        canMove = true;
        Micro.tryCompleteTowerPattern();
        Micro.refuel();
        Logger.addToIndicatorString(String.valueOf(Clock.getBytecodeNum()));
        Micro.splasherMicro();
        Logger.addToIndicatorString(String.valueOf(Clock.getBytecodeNum()));
        paint();
        Logger.addToIndicatorString(String.valueOf(Clock.getBytecodeNum()));
        if (canMove)
        {
            Nav.explore();
        }
    }
}
