package finishingTouches15;

import battlecode.common.*;

/*
    This file is meant to store all functions referring to determining how many adjacent bots there are
    to tiles near the robot. This is important for calculating paint loss.
 */
public class AdjacencyManager extends Globals {



    public static int[] numEnemiesAdjacent = null;
    public static int[] numAlliesAdjacent = null;

    private static final int N = 0;
    private static final int NE = 1;
    private static final int E = 2;
    private static final int SE = 3;
    private static final int S = 4;
    private static final int SW = 5;
    private static final int W = 6;
    private static final int NW = 7;
    private static final int C = 8;

    public static void calculateRobotPresenceMatrices() throws GameActionException {
        if (rc.getType().isTowerType()) return;
        MapLocation myLoc = rc.getLocation();
        MapLocation newLoc;
        RobotInfo bot;
        numEnemiesAdjacent = new int[allDirections.length];
        numAlliesAdjacent = new int[allDirections.length];

        // dx = -2, dy = -2 (SW)
        newLoc = myLoc.translate(-2,-2);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SW]++;
                }
                else
                {
                    numEnemiesAdjacent[SW]++;
                }
            }

        }

        // dx = -1, dy = -2 (SW,S)
        newLoc = myLoc.translate(-1,-2);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SW]++;
                    numAlliesAdjacent[S]++;
                }
                else
                {
                    numEnemiesAdjacent[SW]++;
                    numEnemiesAdjacent[S]++;
                }
            }
        }

        // dx = 0, dy = -2 (SW,S,SE)
        newLoc = myLoc.translate(0,-2);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SW]++;
                    numAlliesAdjacent[S]++;
                    numAlliesAdjacent[SE]++;
                }
                else
                {
                    numEnemiesAdjacent[SW]++;
                    numEnemiesAdjacent[S]++;
                    numEnemiesAdjacent[SE]++;
                }
            }
        }

        // dx = 1, dy = -2 (S,SE)
        newLoc = myLoc.translate(1,-2);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[S]++;
                    numAlliesAdjacent[SE]++;
                }
                else
                {
                    numEnemiesAdjacent[S]++;
                    numEnemiesAdjacent[SE]++;
                }
            }
        }

        // dx = 2, dy = -2 (SE)
        newLoc = myLoc.translate(2,-2);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SE]++;
                }
                else
                {
                    numEnemiesAdjacent[SE]++;
                }
            }
        }

        // dx = -2, dy = -1 (SW,W)
        newLoc = myLoc.translate(-2,-1);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SW]++;
                    numAlliesAdjacent[W]++;
                }
                else
                {
                    numEnemiesAdjacent[SW]++;
                    numEnemiesAdjacent[W]++;
                }
            }
        }

        // dx = -1, dy = -1 (S,W,C)
        newLoc = myLoc.translate(-1,-1);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[S]++;
                    numAlliesAdjacent[W]++;
                    numAlliesAdjacent[C]++;
                }
                else
                {
                    numEnemiesAdjacent[S]++;
                    numEnemiesAdjacent[W]++;
                    numEnemiesAdjacent[C]++;
                }
            }
        }

        // dx = 0, dy = -1 (SW,W,SE,E,C)
        newLoc = myLoc.translate(0,-1);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SW]++;
                    numAlliesAdjacent[W]++;
                    numAlliesAdjacent[SE]++;
                    numAlliesAdjacent[E]++;
                    numAlliesAdjacent[C]++;
                }
                else
                {
                    numEnemiesAdjacent[SW]++;
                    numEnemiesAdjacent[W]++;
                    numEnemiesAdjacent[SE]++;
                    numEnemiesAdjacent[E]++;
                    numEnemiesAdjacent[C]++;
                }
            }
        }

        // dx = 1, dy = -1 (S,E,C)
        newLoc = myLoc.translate(1,-1);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[S]++;
                    numAlliesAdjacent[E]++;
                    numAlliesAdjacent[C]++;
                }
                else
                {
                    numEnemiesAdjacent[S]++;
                    numEnemiesAdjacent[E]++;
                    numEnemiesAdjacent[C]++;
                }
            }
        }

        // dx = 2, dy = -1 (SE,E)
        newLoc = myLoc.translate(2,-1);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SE]++;
                    numAlliesAdjacent[E]++;
                }
                else
                {
                    numEnemiesAdjacent[SE]++;
                    numEnemiesAdjacent[E]++;
                }
            }
        }

        // dx = -2, dy = 0 (SW,W,NW)
        newLoc = myLoc.translate(-2,0);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SW]++;
                    numAlliesAdjacent[W]++;
                    numAlliesAdjacent[NW]++;
                }
                else
                {
                    numEnemiesAdjacent[SW]++;
                    numEnemiesAdjacent[W]++;
                    numEnemiesAdjacent[NW]++;
                }
            }
        }

        // dx = -1, dy = 0 (SW,S,NW,N,C)
        newLoc = myLoc.translate(-1,0);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SW]++;
                    numAlliesAdjacent[S]++;
                    numAlliesAdjacent[NW]++;
                    numAlliesAdjacent[N]++;
                    numAlliesAdjacent[C]++;
                }
                else
                {
                    numEnemiesAdjacent[SW]++;
                    numEnemiesAdjacent[S]++;
                    numEnemiesAdjacent[NW]++;
                    numEnemiesAdjacent[N]++;
                    numEnemiesAdjacent[C]++;
                }
            }
        }

        // dx = 1, dy = 0 (SE,S,NE,N,C)
        newLoc = myLoc.translate(1,0);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SE]++;
                    numAlliesAdjacent[S]++;
                    numAlliesAdjacent[NE]++;
                    numAlliesAdjacent[N]++;
                    numAlliesAdjacent[C]++;
                }
                else
                {
                    numEnemiesAdjacent[SE]++;
                    numEnemiesAdjacent[S]++;
                    numEnemiesAdjacent[NE]++;
                    numEnemiesAdjacent[N]++;
                    numEnemiesAdjacent[C]++;
                }
            }
        }

        // dx = 2, dy = 0 (SE,E,NE)
        newLoc = myLoc.translate(2,0);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[SE]++;
                    numAlliesAdjacent[E]++;
                    numAlliesAdjacent[NE]++;
                }
                else
                {
                    numEnemiesAdjacent[SE]++;
                    numEnemiesAdjacent[E]++;
                    numEnemiesAdjacent[NE]++;
                }
            }
        }

        // dx = -2, dy = 1 (NW,W)
        newLoc = myLoc.translate(-2,1);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[NW]++;
                    numAlliesAdjacent[W]++;
                }
                else
                {
                    numEnemiesAdjacent[NW]++;
                    numEnemiesAdjacent[W]++;
                }
            }
        }

        // dx = -1, dy = 1 (N,W,C)
        newLoc = myLoc.translate(-1,1);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[N]++;
                    numAlliesAdjacent[W]++;
                    numAlliesAdjacent[C]++;
                }
                else
                {
                    numEnemiesAdjacent[N]++;
                    numEnemiesAdjacent[W]++;
                    numEnemiesAdjacent[C]++;
                }
            }
        }

        // dx = 0, dy = 1 (NE,E,NW,W,C)
        newLoc = myLoc.translate(0,1);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[NE]++;
                    numAlliesAdjacent[E]++;
                    numAlliesAdjacent[NW]++;
                    numAlliesAdjacent[W]++;
                    numAlliesAdjacent[C]++;
                }
                else
                {
                    numEnemiesAdjacent[NE]++;
                    numEnemiesAdjacent[E]++;
                    numEnemiesAdjacent[NW]++;
                    numEnemiesAdjacent[W]++;
                    numEnemiesAdjacent[C]++;
                }
            }
        }

        // dx = 1, dy = 1 (N,E,C)
        newLoc = myLoc.translate(1,1);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[N]++;
                    numAlliesAdjacent[E]++;
                    numAlliesAdjacent[C]++;
                }
                else
                {
                    numEnemiesAdjacent[N]++;
                    numEnemiesAdjacent[E]++;
                    numEnemiesAdjacent[C]++;
                }
            }
        }

        // dx = 2, dy = 1 (N,E,C)
        newLoc = myLoc.translate(2,1);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[NE]++;
                    numAlliesAdjacent[E]++;
                }
                else
                {
                    numEnemiesAdjacent[NE]++;
                    numEnemiesAdjacent[E]++;
                }
            }
        }

        // dx = -2, dy = 2 (NW)
        newLoc = myLoc.translate(-2,2);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[NW]++;
                }
                else
                {
                    numEnemiesAdjacent[NW]++;
                }
            }
        }

        // dx = -1, dy = 2 (NW,N)
        newLoc = myLoc.translate(-1,2);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[NW]++;
                    numAlliesAdjacent[N]++;
                }
                else
                {
                    numEnemiesAdjacent[NW]++;
                    numEnemiesAdjacent[N]++;
                }
            }
        }

        // dx = 0, dy = 2 (NW,N,NE)
        newLoc = myLoc.translate(0,2);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[NW]++;
                    numAlliesAdjacent[N]++;
                    numAlliesAdjacent[NE]++;
                }
                else
                {
                    numEnemiesAdjacent[NW]++;
                    numEnemiesAdjacent[N]++;
                    numEnemiesAdjacent[NE]++;
                }
            }
        }

        // dx = 1, dy = 2 (N,NE)
        newLoc = myLoc.translate(1,2);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[N]++;
                    numAlliesAdjacent[NE]++;
                }
                else
                {
                    numEnemiesAdjacent[N]++;
                    numEnemiesAdjacent[NE]++;
                }
            }
        }

        // dx = 2, dy = 2 (NE)
        newLoc = myLoc.translate(2,2);
        if (rc.canSenseLocation(newLoc))
        {
            bot = rc.senseRobotAtLocation(newLoc);
            if (bot != null)
            {
                if (bot.getTeam() == myTeam)
                {
                    numAlliesAdjacent[NE]++;
                }
                else
                {
                    numEnemiesAdjacent[NE]++;
                }
            }
        }

    }

    //Call calculateRobotPresenceMatrices() before using this method
    public static int getPaintLoss(Direction dir) throws GameActionException {
        MapLocation loc = rc.getLocation().add(dir);
        if (!rc.canSenseLocation(loc)) return -9999;
        PaintType paint = rc.senseMapInfo(loc).getPaint();
        int numAlliesA = 0;
        switch (dir)
        {
            case NORTH:
                numAlliesA = numAlliesAdjacent[N];
                break;
            case NORTHWEST:
                numAlliesA = numAlliesAdjacent[NW];
                break;
            case WEST:
                numAlliesA = numAlliesAdjacent[W];
                break;
            case SOUTHWEST:
                numAlliesA = numAlliesAdjacent[SW];
                break;
            case SOUTH:
                numAlliesA = numAlliesAdjacent[S];
                break;
            case SOUTHEAST:
                numAlliesA = numAlliesAdjacent[SE];
                break;
            case EAST:
                numAlliesA = numAlliesAdjacent[E];
                break;
            case NORTHEAST:
                numAlliesA = numAlliesAdjacent[NE];
                break;
            case CENTER:
                numAlliesA = numAlliesAdjacent[C];
                break;
        }
        int paintLoss = 0;
        int mult = (rc.getType()==UnitType.MOPPER) ? GameConstants.MOPPER_PAINT_PENALTY_MULTIPLIER : 1;
        switch (paint)
        {
            case EMPTY:
                paintLoss = mult;
            case ALLY_PRIMARY:
            case ALLY_SECONDARY:
                paintLoss += numAlliesA;
                break;
            default:
                paintLoss = 2*mult +numAlliesA;
        }
        return paintLoss;
    }

    //Call calculateRobotPresenceMatrices() before using this method
    public static int getNumEnemiesAdjacent(Direction dir) {
        int numEnemiesA = 0;
        switch (dir)
        {
            case NORTH:
                numEnemiesA = numEnemiesAdjacent[N];
                break;
            case NORTHWEST:
                numEnemiesA = numEnemiesAdjacent[NW];
                break;
            case WEST:
                numEnemiesA = numEnemiesAdjacent[W];
                break;
            case SOUTHWEST:
                numEnemiesA = numEnemiesAdjacent[SW];
                break;
            case SOUTH:
                numEnemiesA = numEnemiesAdjacent[S];
                break;
            case SOUTHEAST:
                numEnemiesA = numEnemiesAdjacent[SE];
                break;
            case EAST:
                numEnemiesA = numEnemiesAdjacent[E];
                break;
            case NORTHEAST:
                numEnemiesA = numEnemiesAdjacent[NE];
                break;
            case CENTER:
                numEnemiesA = numEnemiesAdjacent[C];
                break;
        }
        return numEnemiesA;
    }
}
