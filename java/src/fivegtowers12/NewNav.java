package fivegtowers12;

import battlecode.common.*;

/*
    TODO
    Do normal bug nav2 with the following modifications:
    - ROBOT_WALL chance of treating bots as walls.
    - High paint loss positions are walls if aggro = false.
    - If careful = true, treat all positions that have more paint than
    the minimum are walls.
    - A position is not a wall but should be waited for IF
        * There's a robot at that position and is not ROBOT_WALL
        * The next tile can be painted over
    - If we haven't moved towards the goal in a large number of rounds,
    reset bug nav
    - If we haven't moved in 5 rounds, set aggro=true for a few rounds.
 */

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
    static boolean clockwise = rng.nextBoolean();
    static FastSet loopChecker = null;
    static boolean waiting = false;


    public static void updateMinPaintLoss() throws GameActionException {
        minPaintLoss = 100000;
        for (int i = 0; i < allDirections.length; i++)
        {
            int paintLoss = AdjacencyManager.getPaintLoss(allDirections[i]);
            if (paintLoss == -9999) continue;
            if (paintLoss < minPaintLoss) minPaintLoss = paintLoss;
        }
    }

    public static void reset(MapLocation target)
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
        clockwise = !clockwise;
        waiting = false;
        loopChecker = null;
    }

    public static Direction getBestLineFollowDir() throws GameActionException {
        Direction dir = null;
        int bestDist = rc.getLocation().distanceSquaredTo(prevDest);
        for (int i = 0; i < adjacentDirections.length; i++)
        {
            if (line.contains(rc.getLocation().add(adjacentDirections[i])))
            {
                if (isWall(adjacentDirections[i])) continue;
                int dist = rc.getLocation().add(adjacentDirections[i]).distanceSquaredTo(prevDest);
                if (dist < bestDist)
                {
                    bestDist = dist;
                    dir = adjacentDirections[i];
                }
            }
        }
        return dir;
    }

    public static void bug2(MapLocation target) throws GameActionException {
        if (!rc.isMovementReady() || !canMove) return;
        updateMinPaintLoss();
        Logger.addToIndicatorString("MPL= " + minPaintLoss);
        MapLocation currLoc = rc.getLocation();

        areRobotsWalls = rng.nextDouble() <= ROBOT_WALL;

        boolean dirWall;
        boolean dirWait;

        //If we have a new target, reset bug nav
        if(!target.equals(prevDest)) {
            reset(target);
        }

        //Check if we've gotten closer to our target or not
        if (rc.getLocation().distanceSquaredTo(target) < minDistToTarget) {
            minDistToTarget = rc.getLocation().distanceSquaredTo(target);
            turnsSinceGottenCloser = 0;
        }
        else
        {
            turnsSinceGottenCloser++;
        }

        /*
        //If we haven't gotten closer to our target in a while, reset the algorithm
        if (turnsSinceGottenCloser > 20)
        {
            reset(target);
        }

         */

        careful = rc.getPaint() < 30 && prevDest != null && !prevDest.isWithinDistanceSquared(rc.getLocation(), 8);

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


        if (isTracing && !waiting && loopChecker != null && loopChecker.contains(rc.getLocation()))
        {
            reset(target);
        }
        else if (isTracing && !waiting && loopChecker != null)
        {
            loopChecker.add(rc.getLocation());
        }

        if (isTracing) {

            //If we were tracing and we've gotten closer to our target since we've started while staying on the line, break out
            if (line.contains(rc.getLocation()) && rc.getLocation().distanceSquaredTo(target) < obstacleStartDist) {
                isTracing = false;
                obstacleStartRound = 0;
                obstacleStartLoc = null;
                obstacleStartDist = 0;
                clockwise = !clockwise;
                loopChecker = null;
                //Logger.addToIndicatorString("Broke loop");
            }
        }

        //If we aren't tracing, follow the line.
        if(!isTracing) {
            Direction dir = getBestLineFollowDir();


            if(dir != null){
                dirWait = shouldWait(dir);
                if (!dirWait)
                {
                    waiting = false;
                    rc.move(dir);
                }
                else
                {
                    tryToClearWay(dir);
                }
            } else {
                tracingDir = rc.getLocation().directionTo(prevDest);
                isTracing = true;
                loopChecker = new FastSet();
                obstacleStartDist = rc.getLocation().distanceSquaredTo(target);
                obstacleStartLoc = rc.getLocation();
                obstacleStartRound = rc.getRoundNum();

                //Sim
                clockwise = !clockwise;
                /*
                Direction tempDir = tracingDir;
                for (int i = 0; i < 5; i++)
                {
                    if (!isWall(tempDir)) {
                        clockwise = false;
                        break;
                    }
                    tempDir = tempDir.rotateRight();
                }

                 */
            }
        }

        //If we are tracing, trace around the obstacle
        if(isTracing){
            for (int i = 0; i < 9; i++) {
                if (rc.onTheMap(rc.getLocation().add(tracingDir))) rc.setIndicatorLine(rc.getLocation(),rc.getLocation().add(tracingDir),0,0,255);
                dirWall = isWall(tracingDir);
                //Logger.addToIndicatorString(tracingDir.toString() + dirWall);
                if (!dirWall) {
                    dirWait = shouldWait(tracingDir);
                    if (!dirWait) {
                        rc.move(tracingDir);
                    } else {
                        tryToClearWay(tracingDir);
                    }
                    if (clockwise) {
                        tracingDir = tracingDir.rotateRight();
                        tracingDir = tracingDir.rotateRight();
                    }
                    else
                    {
                        tracingDir = tracingDir.rotateLeft();
                        tracingDir = tracingDir.rotateLeft();
                    }
                    //Logger.addToIndicatorString(String.valueOf(i));
                    break;
                } else {
                    if (clockwise) tracingDir = tracingDir.rotateLeft();
                    else tracingDir = tracingDir.rotateRight();
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
                    if (!shouldWait(d))
                    {
                        waiting = false;
                        rc.move(d);
                    }
                    else
                    {
                        waiting = true;
                    }
                }
                break;
            case MOPPER:
                if (rc.canAttack(loc) && paint.isEnemy())
                {
                    rc.attack(loc);
                    if (!shouldWait(d))
                    {
                        waiting = false;
                        rc.move(d);
                    }
                    else
                    {
                        waiting = true;
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
            if (paintLoss > minPaintLoss && paintLoss > 2) return true;
        }


        return false;
    }

    //Moppers appear to move directly onto
    public static boolean shouldWait(Direction d) throws GameActionException {
        MapLocation loc = rc.getLocation().add(d);
        if (!rc.canSenseLocation(loc)) return true;
        RobotInfo bot = rc.senseRobotAtLocation(loc);

        if (!areRobotsWalls && bot != null) return true;
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
