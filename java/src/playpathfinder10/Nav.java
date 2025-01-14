package playpathfinder10;

import battlecode.common.*;

public class Nav extends Globals {

    static int turnCount = 0;

    static PaintType currentPaint = PaintType.EMPTY;

    static void randomMove() throws GameActionException {
        int starting_i = rng.nextInt(adjacentDirections.length);
        for (int i = starting_i; i < starting_i + 8; i++) {
            Direction dir = adjacentDirections[i % 8];
            if (rc.canMove(dir)) rc.move(dir);
        }
    }

    // new path finding code from Ray
    private static final int PRV_LENGTH = 60;
    private static Direction[] prv = new Direction[PRV_LENGTH];
    private static int pathingCnt = 0;
    private static MapLocation lastPathingTarget = null;
    private static MapLocation lastLocation = null;
    private static int stuckCnt = 0;
    private static int lastPathingTurn = 0;
    private static int currentTurnDir = rng.nextInt(2);
    public static int disableTurnDirRound = 0;

    private static Direction[] prv_ = new Direction[PRV_LENGTH];
    private static int pathingCnt_ = 0;
    static int MAX_DEPTH = 15;

    public static void explore() throws GameActionException {
        if (lastPathingTarget != null && rc.getLocation().isWithinDistanceSquared(lastPathingTarget, 4))
        {
            lastPathingTarget = null;
        }
        if (lastPathingTarget == null)
        {
            int exploreX = rng.nextInt(mapWidth);
            int exploreY = rng.nextInt(mapHeight);
            lastPathingTarget = new MapLocation(exploreX, exploreY);
        }
        Logger.addToIndicatorString("E " + lastPathingTarget);
        //moveToward(lastPathingTarget);
        move(lastPathingTarget);
    }

    public static void basicMoveTo(MapLocation target) throws GameActionException {
        if (!rc.isMovementReady()) return;
        if (!target.isWithinDistanceSquared(rc.getLocation(), 10)) return;
        Logger.addToIndicatorString("Basic move " + target);
        //Ignore paint for this version
        Direction forward = rc.getLocation().directionTo(target);
        Direction left = forward.rotateLeft();
        Direction right = forward.rotateRight();
        if (rc.canMove(forward) && !rc.senseMapInfo(rc.getLocation().add(forward)).getPaint().isEnemy()) {
            rc.move(forward);
            return;
        }
        if (rng.nextBoolean())
        {
            if (rc.canMove(left) && !rc.senseMapInfo(rc.getLocation().add(left)).getPaint().isEnemy()) rc.move(left);
            else if (rc.canMove(right) && !rc.senseMapInfo(rc.getLocation().add(right)).getPaint().isEnemy()) rc.move(right);
            else randomMove();
        }
        else
        {
            if (rc.canMove(right) && !rc.senseMapInfo(rc.getLocation().add(right)).getPaint().isEnemy()) rc.move(right);
            else if (rc.canMove(left) && !rc.senseMapInfo(rc.getLocation().add(left)).getPaint().isEnemy()) rc.move(left);
            else randomMove();
        }
    }

    //Outperforms
    static void move(MapLocation loc) throws GameActionException {
        NewNav.bug2(loc);
    }

    static void moveToward(MapLocation location) throws GameActionException {
        canMove = false;
        currentPaint = rc.senseMapInfo(rc.getLocation()).getPaint();

        if (location.isAdjacentTo(rc.getLocation()) && !rc.sensePassability(location)) {
            Logger.addToIndicatorString("Close to " + location);
            return;
        }
        // reset queue when target location changes or there's gap in between calls
        if (!location.equals(lastPathingTarget) || lastPathingTurn < turnCount - 4) {
            pathingCnt = 0;
            stuckCnt = 0;
        }
        basicMoveTo(location);
        if (rc.isMovementReady()) {
            // we increase stuck count only if it's a new turn
            if (rc.getLocation().equals(lastLocation)) {
                if (turnCount != lastPathingTurn) {
                    stuckCnt++;
                }
            } else {
                lastLocation = rc.getLocation();
                stuckCnt = 0;
            }
            lastPathingTarget = location;
            lastPathingTurn = turnCount;
            if (stuckCnt >= 3) {
                Logger.addToIndicatorString("Random Move");
                randomMove();
                pathingCnt = 0;
            }
            if (pathingCnt == 0) {
                //if free of obstacle: try go directly to target
                Direction dir = rc.getLocation().directionTo(location);
                boolean dirCanPass = canPass(dir);
                boolean dirRightCanPass = canPass(dir.rotateRight());
                boolean dirLeftCanPass = canPass(dir.rotateLeft());
                if (dirCanPass || dirRightCanPass || dirLeftCanPass) {
                    Logger.addToIndicatorString("Direct Move");
                    if (dirCanPass && rc.canMove(dir)) {
                        rc.move(dir);
                    } else if (dirRightCanPass && rc.canMove(dir.rotateRight())) {
                        rc.move(dir.rotateRight());
                    } else if (dirLeftCanPass && rc.canMove(dir.rotateLeft())) {
                        rc.move(dir.rotateLeft());
                    }
                } else {
                    //encounters obstacle; run simulation to determine best way to go
                    if (rc.getRoundNum() > disableTurnDirRound) {
                        currentTurnDir = getTurnDir(dir, location);
                    }
                    while (!canPass(dir) && pathingCnt != 8) {
//                        rc.setIndicatorLine(rc.getLocation(), rc.getLocation().add(dir), 0, 0, 255);
                        if (!rc.onTheMap(rc.getLocation().add(dir))) {
                            currentTurnDir ^= 1;
                            pathingCnt = 0;
                            disableTurnDirRound = rc.getRoundNum() + 100;
                            return;
                        }
                        prv[pathingCnt] = dir;
                        pathingCnt++;
                        if (currentTurnDir == 0) dir = dir.rotateLeft();
                        else dir = dir.rotateRight();
                    }
                    if (pathingCnt == 8) {

                    } else if (rc.canMove(dir)) {
                        Logger.addToIndicatorString("Sim Move");
                        rc.move(dir);
                    }
                }
            } else {
                //update stack of past directions, move to next available direction
                if (pathingCnt > 1 && canPass(prv[pathingCnt - 2])) {
                    pathingCnt -= 2;
                }
                while (pathingCnt > 0 && canPass(prv[pathingCnt - 1])) {
//                    rc.setIndicatorLine(rc.getLocation(), rc.getLocation().add(prv[pathingCnt - 1]), 0, 255, 0);
                    pathingCnt--;
                }
                if (pathingCnt == 0) {
                    Direction dir = rc.getLocation().directionTo(location);
                    if (!canPass(dir)) {
                        prv[pathingCnt++] = dir;
                    }
                }
                int pathingCntCutOff = Math.min(PRV_LENGTH, pathingCnt + 8); // if 8 then all dirs blocked
                while (pathingCnt > 0 && !canPass(currentTurnDir == 0?prv[pathingCnt - 1].rotateLeft():prv[pathingCnt - 1].rotateRight())) {
                    prv[pathingCnt] = currentTurnDir == 0?prv[pathingCnt - 1].rotateLeft():prv[pathingCnt - 1].rotateRight();
//                    rc.setIndicatorLine(rc.getLocation(), rc.getLocation().add(prv[pathingCnt]), 255, 0, 0);
                    if (!rc.onTheMap(rc.getLocation().add(prv[pathingCnt]))) {
                        currentTurnDir ^= 1;
                        pathingCnt = 0;
                        disableTurnDirRound = rc.getRoundNum() + 100;
                        return;
                    }
                    pathingCnt++;
                    if (pathingCnt == pathingCntCutOff) {
                        pathingCnt = 0;
                        return;
                    }
                }
                Direction moveDir = pathingCnt == 0? prv[pathingCnt] :
                        (currentTurnDir == 0?prv[pathingCnt - 1].rotateLeft():prv[pathingCnt - 1].rotateRight());
                if (rc.canMove(moveDir)) {
                    Logger.addToIndicatorString("Sim2 Move");
                    rc.move(moveDir);
                } else {
                    Logger.addToIndicatorString("Wall wait" + pathingCnt_ + " " + lastPathingTurn + " " + turnCount);
                    // a robot blocking us while we are following wall, wait
                }
            }
        }
        lastPathingTarget = location;
        lastPathingTurn = turnCount;
    }

    static int getSteps(MapLocation a, MapLocation b) {
        int xdif = a.x - b.x;
        int ydif = a.y - b.y;
        if (xdif < 0) xdif = -xdif;
        if (ydif < 0) ydif = -ydif;
        if (xdif > ydif) return xdif;
        else return ydif;
    }

    private static final int BYTECODE_CUTOFF = 3000;
    static int getTurnDir(Direction direction, MapLocation target) throws GameActionException{
        //int ret = getCenterDir(direction);
        MapLocation now = rc.getLocation();
        int moveLeft = 0;
        int moveRight = 0;

        pathingCnt_ = 0;
        Direction dir = direction;
        while (!canPass(now.add(dir)) && pathingCnt_ != 8) {
            prv_[pathingCnt_] = dir;
            pathingCnt_++;
            dir = dir.rotateLeft();
            if (pathingCnt_ > 8) {
                break;
            }
        }
        now = now.add(dir);

        int byteCodeRem = Clock.getBytecodesLeft();
        if (byteCodeRem < BYTECODE_CUTOFF)
            return rng.nextInt(2);
        //simulate turning left
        while (pathingCnt_ > 0) {
            moveLeft++;
            if (moveLeft > MAX_DEPTH) {
                break;
            }
            if (Clock.getBytecodesLeft() < BYTECODE_CUTOFF) {
                moveLeft = -1;
                break;
            }
            while (pathingCnt_ > 0 && canPass(now.add(prv_[pathingCnt_ - 1]))) {
                pathingCnt_--;
            }
            if (pathingCnt_ > 1 && canPass(now.add(prv_[pathingCnt_ - 1]))) {
                pathingCnt_-=2;
            }
            while (pathingCnt_ > 0 && !canPass(now.add(prv_[pathingCnt_ - 1].rotateLeft()))) {
                prv_[pathingCnt_] = prv_[pathingCnt_ - 1].rotateLeft();
                pathingCnt_++;
                if (pathingCnt_ > 8) {
                    moveLeft = -1;
                    break;
                }
            }
            if (pathingCnt_ > 8 || pathingCnt_ == 0) {
                break;
            }
            Direction moveDir = pathingCnt_ == 0? prv_[pathingCnt_] : prv_[pathingCnt_ - 1].rotateLeft();
            now = now.add(moveDir);
        }
        MapLocation leftend = now;
        pathingCnt_ = 0;
        now = rc.getLocation();
        dir = direction;
        //simulate turning right
        while (!canPass(dir) && pathingCnt_ != 8) {
            prv_[pathingCnt_] = dir;
            pathingCnt_++;
            dir = dir.rotateRight();
            if (pathingCnt_ > 8) {
                break;
            }
        }
        now = now.add(dir);

        while (pathingCnt_ > 0) {
            moveRight++;
            if (moveRight > MAX_DEPTH) {
                break;
            }
            if (Clock.getBytecodesLeft() < BYTECODE_CUTOFF) {
                moveRight = -1;
                break;
            }
            while (pathingCnt_ > 0 && canPass(now.add(prv_[pathingCnt_ - 1]))) {
                pathingCnt_--;
            }
            if (pathingCnt_ > 1 && canPass(now.add(prv_[pathingCnt_ - 1]))) {
                pathingCnt_-=2;
            }
            while (pathingCnt_ > 0 && !canPass(now.add(prv_[pathingCnt_ - 1].rotateRight()))) {
                prv_[pathingCnt_] = prv_[pathingCnt_ - 1].rotateRight();
                pathingCnt_++;
                if (pathingCnt_ > 8) {
                    moveRight = -1;
                    break;
                }
            }
            if (pathingCnt_ > 8 || pathingCnt_ == 0) {
                break;
            }
            Direction moveDir = pathingCnt_ == 0? prv_[pathingCnt_] : prv_[pathingCnt_ - 1].rotateRight();
            now = now.add(moveDir);
        }
        MapLocation rightend = now;
        //find best direction
        if (moveLeft == -1 || moveRight == -1) return rng.nextInt(2);
        if (moveLeft + getSteps(leftend, target) <= moveRight + getSteps(rightend, target)) return 0;
        else return 1;

    }

    private static boolean canPass(MapLocation loc) throws GameActionException {
        if (loc.equals(rc.getLocation())) return true;
        if (!rc.canSenseLocation(loc)) return true;
        if (!rc.sensePassability(loc)) return false;
        if (rc.senseRobotAtLocation(loc) != null) return false;
        switch (rc.getType()) {
            case SOLDIER:
                if (currentPaint.isEnemy()) return true;
                return !rc.senseMapInfo(loc).getPaint().isEnemy();
            case SPLASHER:
                if (!currentPaint.isAlly()) return true;
                return rc.senseMapInfo(loc).getPaint().isAlly();
            default:
                return true;
        }
    }

    private static boolean canPass(Direction dir) throws GameActionException {
        return canPass(rc.getLocation().add(dir));
    }
}
