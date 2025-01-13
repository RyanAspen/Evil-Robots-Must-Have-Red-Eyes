package towerDefense8;

import battlecode.common.*;

public class CombatMicro extends Globals {

    public static void soldierAttack() throws GameActionException {
        if (!rc.isActionReady()) return;
        MapInfo target = null;
        MapInfo[] possibleOptions = rc.senseNearbyMapInfos(rc.getType().actionRadiusSquared);
        for (MapInfo option : possibleOptions)
        {
            target = soldierCompare(target, option);
        }
        if (target == null) return;
        if (rc.canAttack(target.getMapLocation()))
        {
            Logger.addToIndicatorString("Micro: Attacking " + target.getMapLocation());
            PaintManager.soldierAttack(target.getMapLocation());
        }
        else
        {
            Logger.addToIndicatorString("Micro: Cannot Attack " + target.getMapLocation());
        }
    }

    /*
        Soldiers choose a place to attack as follows:
        - Don't attack unless the location has no paint (RUINS always have no paint)
        - Don't attack a tower unless its pattern has been disrupted. If we can, prioritize the tower.
        - Prefer to attack empty tiles that are under non-mopper enemy bots
        - Prefer to attack tiles that are further from enemy Moppers TODO
     */
    public static MapInfo soldierCompare(MapInfo A, MapInfo B) throws GameActionException {
        if (A == null) return B;
        if (B == null) return A;

        if (A.isWall()) return B;
        if (B.isWall()) return A;

        if (A.getPaint() == PaintType.EMPTY && B.getPaint() != PaintType.EMPTY) return A;
        if (A.getPaint() != PaintType.EMPTY && B.getPaint() == PaintType.EMPTY) return B;



        RobotInfo botA = rc.senseRobotAtLocation(A.getMapLocation());
        RobotInfo botB = rc.senseRobotAtLocation(B.getMapLocation());

        if (botA != null) {
            if (botA.getType().isTowerType() && botA.getTeam() == opponentTeam) {
                if (Micro.towerPatternToComplete(A.getMapLocation(), false) == null) {
                    return A;
                }
                return B;
            } else if (botA.getType().isTowerType() || A.hasRuin()) {
                return B;
            }
        }

        if (botB != null) {
            if (botB.getType().isTowerType() && botB.getTeam() == opponentTeam) {
                if (Micro.towerPatternToComplete(B.getMapLocation(), false) == null) {
                    return B;
                }
                return A;
            } else if (botB.getType().isTowerType() || B.hasRuin()) {
                return A;
            }
        }

        if (botA == null) return B;
        if (botB == null) return A;

        //if (A.getMark().isAlly()) return A;
        //if (B.getMark().isAlly()) return B;

        if (botA.getType() == UnitType.MOPPER) return B;
        if (botB.getType() == UnitType.MOPPER) return A;

        if (botA.getPaintAmount() < botB.getPaintAmount()) return A;
        return B;
    }


    public static void mopperAttack() throws GameActionException {
        if (!rc.isActionReady()) return;
        //attemptMopSweep();
        //if (!rc.isActionReady()) return;
        MapInfo target = null;
        MapInfo[] possibleOptions = rc.senseNearbyMapInfos(rc.getType().actionRadiusSquared);
        for (MapInfo option : possibleOptions)
        {
            target = mopperCompare(target, option);
        }
        if (target == null) return;
        if (rc.canAttack(target.getMapLocation()))
        {
            if ((rc.senseRobotAtLocation(target.getMapLocation()) != null && rc.senseRobotAtLocation(target.getMapLocation()).getTeam() == opponentTeam) || (!target.getPaint().isAlly() && target.getPaint() != PaintType.EMPTY)) {
                Logger.addToIndicatorString("Micro: Attacking " + target.getMapLocation());
                rc.attack(target.getMapLocation());
            }
        }
        else {
            Logger.addToIndicatorString("Micro: Cannot Attack " + target.getMapLocation());
        }
    }

    /*
        Moppers choose a place to attack as follows:
        - Don't attack unless the location has enemy paint OR there is a non-tower enemy unit on the tile
        - Prefer to attack tiles that are under non-soldier enemy bots, especially splashers
        - Prefer to attack tiles that are further from enemy Soldiers TODO
     */
    public static MapInfo mopperCompare(MapInfo A, MapInfo B) throws GameActionException {
        if (A == null) return B;
        if (B == null) return A;
        if (!rc.canAttack(B.getMapLocation())) return A;
        if (!rc.canAttack(A.getMapLocation())) return B;


        RobotInfo botA = rc.senseRobotAtLocation(A.getMapLocation());
        RobotInfo botB = rc.senseRobotAtLocation(B.getMapLocation());

        boolean canAttackA = rc.canAttack(A.getMapLocation()) && ((A.getPaint() == PaintType.ENEMY_PRIMARY || A.getPaint() == PaintType.ENEMY_SECONDARY) || (botA != null && botA.getType().isRobotType()));
        boolean canAttackB = rc.canAttack(B.getMapLocation()) && ((B.getPaint() == PaintType.ENEMY_PRIMARY || B.getPaint() == PaintType.ENEMY_SECONDARY) || (botB != null && botB.getType().isRobotType()));

        if (!canAttackA) return B;
        if (!canAttackB) return A;

        if (botA == null) return B;
        if (botB == null) return A;

        //if (A.getMark().isAlly()) return A;
        //if (B.getMark().isAlly()) return B;

        int priorityA, priorityB;
        switch (botA.getType())
        {
            case SOLDIER:
                priorityA = 1;
                break;
            case SPLASHER:
                priorityA = 2;
                break;
            default:
                priorityA = 0;
                break;
        }
        switch (botB.getType())
        {
            case SOLDIER:
                priorityB = 1;
                break;
            case SPLASHER:
                priorityB = 2;
                break;
            default:
                priorityB = 0;
                break;
        }
        if (priorityA > priorityB) return A;
        return B;
    }

    public static void attemptMopSweep() throws GameActionException {
        if (!rc.isActionReady()) return;
        MapLocation[] closeLocs = new MapLocation[8];
        boolean[] valid = new boolean[8];
        for (int i = 0; i < adjacentDirections.length; i++)
        {
            closeLocs[i] = rc.getLocation().add(adjacentDirections[i]);
            valid[i] = true;
        }
        RobotInfo sensedRobot;

        for (int i = 0; i < adjacentDirections.length; i++)
        {
            if (!rc.onTheMap(closeLocs[i]))
            {
                valid[i] = false;
            }
            else
            {
                sensedRobot = rc.senseRobotAtLocation(closeLocs[i]);
                if (sensedRobot == null || sensedRobot.getTeam() != opponentTeam) {
                    valid[i] = false;
                }
            }
        }

        //NORTH (0,4,7)
        if (valid[0] && valid[4] && valid[7])
        {
            rc.mopSwing(Direction.NORTH);
            Logger.addToIndicatorString("Mop Swing North");
        }
        //SOUTH (2,5,6)
        else if (valid[2] && valid[5] && valid[6])
        {
            rc.mopSwing(Direction.SOUTH);
            Logger.addToIndicatorString("Mop Swing South");
        }
        //EAST (1,4,5)
        else if (valid[1] && valid[4] && valid[5])
        {
            rc.mopSwing(Direction.EAST);
            Logger.addToIndicatorString("Mop Swing East");
        }
        //WEST (3,6,7)
        else if (valid[3] && valid[6] && valid[7])
        {
            rc.mopSwing(Direction.WEST);
            Logger.addToIndicatorString("Mop Swing West");
        }
    }

    /*
    public static void splasherAttack() throws GameActionException {
        if (!rc.isActionReady()) return;
        MapInfo target = null;
        MapInfo[] possibleOptions = rc.senseNearbyMapInfos(rc.getType().actionRadiusSquared);
        for (MapInfo option : possibleOptions)
        {
            if (!rc.canAttack(option.getMapLocation())) continue;
            target = splasherCompare(target, option);
        }
        if (target == null) return;
        if (rc.canAttack(target.getMapLocation()))
        {
            Logger.addToIndicatorString("Micro: Attacking " + target.getMapLocation());
            rc.attack(target.getMapLocation());
        }
        else
        {
            Logger.addToIndicatorString("Micro: Cannot Attack " + target.getMapLocation());
        }
    }

    public static MapInfo splasherCompare(MapInfo A, MapInfo B)
    {

    }

     */
}
