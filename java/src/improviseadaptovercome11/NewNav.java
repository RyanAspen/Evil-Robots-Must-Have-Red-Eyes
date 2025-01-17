package improviseadaptovercome11;

import battlecode.common.*;

public class NewNav extends Globals {

    static MapLocation prevDest = null;
    static FastSet line = null;
    static int obstacleStartDist = 0;
    static MapLocation obstacleStartLoc = null;
    static int obstacleStartRound = 0;
    static boolean isTracing = false;
    static Direction tracingDir= null;

    static boolean careful = false; //Minimize damage from tiles
    static boolean aggro = false; //Ignore paint (overrides careful)
    static final double ROBOT_WALL = 0.6;
    static boolean areRobotsWalls = false;
    static int turnsSinceMovement = 0;
    static int turnsSinceGottenCloser = 0;
    static int minDistToTarget = 100000;
    static int turnsOfAggroMovement = 0;
    static int minPaintLoss = 100000;


    public static void updateMinPaintLoss() throws GameActionException {
        for (int i = 0; i < allDirections.length; i++)
        {
            int paintLoss = AdjacencyManager.getPaintLoss(allDirections[i]);
            if (paintLoss < minPaintLoss) minPaintLoss = paintLoss;
        }
    }

    public static void bug2(MapLocation target) throws GameActionException {
        if (!rc.isMovementReady() || !canMove) return;
        updateMinPaintLoss();
        MapLocation currLoc = rc.getLocation();



        areRobotsWalls = rng.nextInt(10) <= ROBOT_WALL*10;

        boolean dirWall;
        boolean dirWait;

        if(!target.equals(prevDest)) {
            isTracing = false;
            obstacleStartRound = 0;
            obstacleStartLoc = null;
            obstacleStartDist = 0;
            prevDest = target;
            line = createLine(rc.getLocation(), target);
            turnsOfAggroMovement = 0;
            turnsSinceMovement = 0;
            minDistToTarget = 100000;
        }

        if (rc.getLocation().distanceSquaredTo(target) < minDistToTarget) {
            minDistToTarget = rc.getLocation().distanceSquaredTo(target);
            turnsSinceGottenCloser = 0;
        }
        else
        {
            turnsSinceGottenCloser++;
        }

        //If we haven't gotten closer to our target in a while, reset the algorithm
        if (turnsSinceGottenCloser > 20)
        {
            isTracing = false;
            obstacleStartRound = 0;
            obstacleStartLoc = null;
            obstacleStartDist = 0;
            prevDest = target;
            line = createLine(rc.getLocation(), target);
            turnsOfAggroMovement = 0;
            turnsSinceMovement = 0;
            minDistToTarget = 100000;
        }

        careful = rc.getPaint() < 30;//|| rc.senseNearbyRobots(-1,opponentTeam).length > 0;

        //These conditions are problematic (mainly the close-to-target one), since they cause bots to take unnecessary risks
        if (((rc.getRoundNum() < 200 && !careful) || turnsSinceMovement > 5  || (currLoc.isWithinDistanceSquared(target, GameConstants.VISION_RADIUS_SQUARED) && turnsSinceGottenCloser > 3)) && turnsOfAggroMovement == 0)
        {
            turnsOfAggroMovement = 5;
        }
        aggro = turnsOfAggroMovement > 0;
        if (aggro)
        {
            turnsOfAggroMovement--;
        }


        MapLocation[] lineLocs = line.getAllLoc();
        for (int i = 0; i < lineLocs.length; i++) {
            rc.setIndicatorDot(lineLocs[i], 255, 0, 0);
        }
        //System.out.println("------");




        if (isTracing) {
            if (line.contains(rc.getLocation()) && rc.getLocation().distanceSquaredTo(target) < obstacleStartDist) {
                isTracing = false;
                obstacleStartRound = 0;
                obstacleStartLoc = null;
                obstacleStartDist = 0;
                //Logger.addToIndicatorString("Broke loop");
            }
        }


        if(!isTracing) {
            Direction dir = rc.getLocation().directionTo(target);
            tracingDir = dir;
            dirWall = isWall(dir);


            if(!dirWall){
                dirWait = shouldWait(dir);
                if (!dirWait)
                {
                    rc.move(dir);
                }
                else
                {
                    tryToClearWay(dir);
                }
            } else {
                isTracing = true;
                obstacleStartDist = rc.getLocation().distanceSquaredTo(target);
                obstacleStartLoc = rc.getLocation();
                obstacleStartRound = rc.getRoundNum();

            }
        }
        if(isTracing){
            for (int i = 0; i < 9; i++) {
                dirWall = isWall(tracingDir);
                //Logger.addToIndicatorString(tracingDir.toString() + dirWall);
                if (!dirWall) {
                    dirWait = shouldWait(tracingDir);
                    if (!dirWait) {
                        rc.move(tracingDir);
                        tracingDir = tracingDir.rotateRight();
                        tracingDir = tracingDir.rotateRight();
                    } else {
                        tryToClearWay(tracingDir);
                    }
                    //Logger.addToIndicatorString(String.valueOf(i));
                    break;
                } else {
                    tracingDir = tracingDir.rotateLeft();
                }
            }
        }
        //Logger.addToIndicatorString("L:" + dirWall + " " + dirWait + " " + areRobotsWalls + " " + isTracing + " " + tracingDir + " " + obstacleStartDist + " " + obstacleStartLoc + " T = " + target);

        if (rc.getLocation().equals(currLoc))
        {
            turnsSinceMovement++;
        }
        else
        {
            turnsSinceMovement = 0;
        }
        canMove = false;
    }

    public static void tryToClearWay(Direction d) throws GameActionException {
        MapLocation loc = rc.getLocation().add(d);
        if (!rc.canSenseLocation(loc)) return;
        MapInfo info = rc.senseMapInfo(loc);
        PaintType paint = info.getPaint();
        switch (rc.getType())
        {
            case SOLDIER:
                if (rc.canAttack(loc) && (paint == PaintType.EMPTY || (paint.isAlly() && paint.isSecondary() != PaintManager.shouldBeSecondary(loc))))
                {
                    PaintManager.soldierAttack(loc);
                    if (rc.canMove(d)) {
                        rc.move(d);
                        tracingDir = tracingDir.rotateRight();
                        tracingDir = tracingDir.rotateRight();
                    }
                }
                break;
            case MOPPER:
                if (rc.canAttack(loc) && paint.isEnemy())
                {
                    rc.attack(loc);
                    if (rc.canMove(d)) {
                        rc.move(d);
                        tracingDir = tracingDir.rotateRight();
                        tracingDir = tracingDir.rotateRight();
                    }
                }
                break;
        }
    }

    public static boolean isWall(Direction d) throws GameActionException {
        MapLocation loc = rc.getLocation().add(d);
        if (!rc.canSenseLocation(loc)) return true;
        MapInfo info = rc.senseMapInfo(loc);
        if (info.isWall() || info.hasRuin()) return true;
        RobotInfo bot = rc.senseRobotAtLocation(loc);

        if (areRobotsWalls && bot != null) return true;
        if (aggro) return false;
        int paintLoss = AdjacencyManager.getPaintLoss(d);
        //if (paintLoss > minPaintLoss) return true;

        if (careful)
        {
            if (paintLoss > minPaintLoss) return true;
        }
        else
        {
            if (paintLoss > minPaintLoss && paintLoss > 3) return true;
        }


        return false;
    }

    //Moppers appear to move directly onto
    public static boolean shouldWait(Direction d) throws GameActionException {
        MapLocation loc = rc.getLocation().add(d);
        if (!rc.canSenseLocation(loc)) return true;
        RobotInfo bot = rc.senseRobotAtLocation(loc);

        if (!areRobotsWalls && bot != null) return true;
        if (aggro) return false;
        if (rc.getPaint() < rc.getType().attackCost) return false; //Don't bother waiting if we have no paint to attack with

        MapInfo info = rc.senseMapInfo(loc);
        PaintType paint = info.getPaint();

        switch (rc.getType()) {
            case SOLDIER:
                if (paint == PaintType.EMPTY) return true;
                break;
            case MOPPER:
                if (paint.isEnemy()) return true;
                break;

        }
        return false;
    }

    public static FastSet createLine(MapLocation a, MapLocation b) {
        FastSet locs = new FastSet();
        int x = a.x, y = a.y;
        int dx = b.x - a.x;
        int dy = b.y - a.y;
        int sx = (int) Math.signum(dx);
        int sy = (int) Math.signum(dy);
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        int d = Math.max(dx,dy);
        int r = d/2;
        if (dx > dy) {
            for (int i = 0; i < d; i++) {

                locs.add(new MapLocation(x, y));
                //if (rc.getID() == 12478) System.out.println(new MapLocation(x,y));
                x += sx;
                r += dy;
                if (r >= dx) {
                    locs.add(new MapLocation(x, y));
                    //if (rc.getID() == 12478) System.out.println(new MapLocation(x,y));
                    y += sy;
                    r -= dx;
                }
            }
        }
        else {
            for (int i = 0; i < d; i++) {
                locs.add(new MapLocation(x, y));
                //if (rc.getID() == 12478) System.out.println(new MapLocation(x,y));
                y += sy;
                r += dx;
                if (r >= dy) {
                    locs.add(new MapLocation(x, y));
                    //if (rc.getID() == 12478) System.out.println(new MapLocation(x,y));
                    x += sx;
                    r -= dy;
                }
            }
        }
        locs.add(new MapLocation(x, y));
        //if (rc.getID() == 12478) System.out.println(new MapLocation(x,y));
        /*
        if (rc.getID() == 12478) {
            System.out.println("--------------------------------------------");
            MapLocation[] allLocs = locs.getAllLoc();
            for (int i = 0; i < allLocs.length; i++) {
                System.out.println(allLocs[i].toString());
            }
        }

         */
        return locs;
    }

}
