package microscopic3;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

public class Nav extends Globals {
    private static MapLocation currentTarget;

    private static int minDistanceToTarget;
    private static int roundsSinceMovingCloserToTarget;

    public static void moveTo(MapLocation target) throws GameActionException {
        if (rc.getPaint() == 0) return;
        MapLocation myLocation = rc.getLocation();
        //rc.setIndicatorLine(rc.getLocation(), target, 255, 0, 0);

        if (myLocation.equals(target)) {
            currentTarget = null;
            return;
        }

        if (currentTarget == null || !currentTarget.equals(target)) {
            reset();
        }

        currentTarget = target;

        MapLocation nextLocation = myLocation.add(myLocation.directionTo(target));
        if (rc.canSenseLocation(nextLocation) && (rc.senseMapInfo(nextLocation).isWall() || rc.senseMapInfo(nextLocation).hasRuin())) {
            currentTarget = null;
            return;
        }

        int distanceToTarget = myLocation.distanceSquaredTo(target);
        if (distanceToTarget < minDistanceToTarget) {
            minDistanceToTarget = distanceToTarget;
            roundsSinceMovingCloserToTarget = 0;
        } else {
            roundsSinceMovingCloserToTarget++;
        }
        /*
        if (roundsSinceMovingCloserToTarget < 3) {
            System.out.println(Clock.getBytecodesLeft());
            System.out.println(Clock.getBytecodesLeft());
            Direction bellmanFordDirection = BellmanFordNav.getBestDirection(target);
            System.out.println(Clock.getBytecodesLeft());
            if (bellmanFordDirection != null) {
                MapLocation bellmanFordLocation = rc.adjacentLocation(bellmanFordDirection);
                if (!rc.canMove(bellmanFordDirection)) return;
                switch (rc.getType())
                {
                    case SOLDIER:
                    case SPLASHER:
                        if (!rc.senseMapInfo(bellmanFordLocation).getPaint().isAlly())
                        {
                            if (rc.canAttack(bellmanFordLocation))
                            {
                                rc.attack(bellmanFordLocation);
                                rc.move(bellmanFordDirection);
                            }
                            else
                            {
                                return;
                            }
                        }
                        else
                        {
                            rc.move(bellmanFordDirection);
                        }
                        break;
                    case MOPPER:
                        if (rc.senseMapInfo(bellmanFordLocation).getPaint() != PaintType.EMPTY && !rc.senseMapInfo(bellmanFordLocation).getPaint().isAlly())
                        {
                            return;
                        }
                        else
                        {
                            rc.move(bellmanFordDirection);
                        }
                        break;
                }

                return;
            }
        }

         */

        if (!rc.isMovementReady()) {
            return;
        }

        BugNav.moveTo(target);

    }

    public static void reset() {
        currentTarget = null;

        minDistanceToTarget = Integer.MAX_VALUE;
        roundsSinceMovingCloserToTarget = 0;

        BugNav.reset();
    }

    public static void explore() throws GameActionException {
        if (currentTarget != null && rc.getLocation().isWithinDistanceSquared(currentTarget, 4))
        {
            currentTarget = null;
        }
        if (currentTarget == null)
        {
            int exploreX = rng.nextInt(mapWidth);
            int exploreY = rng.nextInt(mapHeight);
            currentTarget = new MapLocation(exploreX, exploreY);
        }
        Logger.addToIndicatorString("E " + currentTarget);
        moveTo(currentTarget);


    }
}
