package bigbrain13;

/*
Splashers are more expensive than soldiers but are much more efficient at painting tiles than soldiers in ideal circumstances. This
does cost more paint, however, so they should really only be used once we've secured an economy advantage

 */

/*
    Splashers are best used for offense. Targeting enemies is good.
    Targeting enemy towers or ruins is even better.
 */

import battlecode.common.GameActionException;
import battlecode.common.MapInfo;
import battlecode.common.RobotInfo;

public class Splasher extends Globals {

    /*
        The heuristic works as follows:
        * +1 if empty tile w/o bot
        * +2 if empty tile with bot
        * +3 if enemy tile that can be painted over w/o bot
        * +5 if enemy tile that can be painted over with bot
        * -3 if allied tile
        * +10 if enemy tower

        (NOTE: Maybe include enemy ruins in heuristic. Also, try painting your own tile
        if it isn't a good alignment)
     */
    public static int paintAttackHeuristic(MapInfo info) throws GameActionException {
        int h = 0;
        for (int i = 0; i < allDirections.length; i++)
        {
            if (!rc.canSenseLocation(info.getMapLocation().add(allDirections[i]))) continue;
            MapInfo currInfo = rc.senseMapInfo(info.getMapLocation().add(allDirections[i]));
            if (currInfo.isWall()) continue;
            RobotInfo bot = rc.senseRobotAtLocation(currInfo.getMapLocation());
            boolean hasBot = bot != null;
            boolean hasEnemyTower = hasBot && bot.getType().isTowerType() && bot.getTeam() == opponentTeam;
            boolean hasRuin = currInfo.hasRuin();
            switch (currInfo.getPaint())
            {
                case ALLY_PRIMARY:
                case ALLY_SECONDARY:
                    h -= 3;
                    break;
                case EMPTY:
                    if (hasEnemyTower) h += 10;
                    else if (hasRuin) h += 6;
                    else if (hasBot) h += 2;
                    else h += 1;
                    break;
                default:
                    if (currInfo.getMapLocation().isWithinDistanceSquared(info.getMapLocation(),1)) {
                        if (hasBot) h += 5;
                        else h += 3;
                    }
                    break;
            }
        }
        return h;
    }

    //Used when not in combat
    public static void paint() throws GameActionException {
        if (!rc.isActionReady() || rc.getPaint() < rc.getType().attackCost) return;
        if (rc.senseNearbyRobots(-1, opponentTeam).length == 0 ) return;
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
        if (target != null && bestVal > 6 && rc.canAttack(target.getMapLocation()))
        {
            rc.attack(target.getMapLocation());
        }
    }

    public static void run() throws GameActionException {
        canMove = true;
        Micro.tryCompleteTowerPattern();
        Micro.refuel();
        Micro.splasherMicro();
        paint();
        if (canMove)
        {
            //Nav.explore();
            Nav.explore();
        }
    }
}
