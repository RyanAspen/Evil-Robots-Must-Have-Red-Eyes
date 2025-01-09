package microscopic3;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.Message;
import battlecode.common.RobotInfo;

public class Comms extends Globals
{

    public final static int ID_LENGTH = 14;
    public final static int X_LENGTH = 6;
    public final static int Y_LENGTH = 6;
    public final static int LOC_LENGTH = X_LENGTH+Y_LENGTH;

    public enum MessageType {
        PAINT_REQUEST,
        DROUGHT_SIGNAL,
        POSSIBLETOWERLOCSIGNAL,
        CONFIRMEDTOWERLOCSIGNAL,
        FLOODSIGNAL,
        RUINLOCSIGNAL;
    }

    public static class CleanMessage
    {
        MessageType mType;
        MapLocation loc;
        int id;

        public CleanMessage(MessageType mType, MapLocation loc, int id)
        {
            this.mType = mType;
            this.loc = loc;
            this.id = id;
        }
    }

    public static int createMessage(int opCode, int[] fields, int[] fieldLengths)
    {
        if (fields == null)
        {
            return opCode;
        }
        int message = fields[0];
        for (int i = 1; i < fields.length; i++)
        {
            message = message << fieldLengths[i-1];
            message += fields[i];
        }
        message = message << 3;
        message += opCode;
        return message;
    }

    public static boolean sendPaintRequest(MapLocation sendLoc, int id) throws GameActionException {
        if (!canSendMessagesToLoc(sendLoc)) return false;
        int[] fields = new int[]{id};
        int[] fieldLengths = new int[]{ID_LENGTH};
        int message = createMessage(0, fields, fieldLengths);
        rc.sendMessage(sendLoc, message);
        return true;
    }

    public static boolean sendDroughtSignal(MapLocation sendLoc) throws GameActionException {
        if (!canSendMessagesToLoc(sendLoc)) return false;
        int message = createMessage(1, null, null);
        rc.sendMessage(sendLoc, message);
        return true;
    }

    public static boolean sendPossibleTowerLocSignal(MapLocation sendLoc, MapLocation loc) throws GameActionException {
        if (!canSendMessagesToLoc(sendLoc)) return false;
        int[] fields = new int[]{getIntFromLocation(loc)};
        int[] fieldLengths = new int[]{LOC_LENGTH};
        int message = createMessage(2, fields, fieldLengths);
        rc.sendMessage(sendLoc, message);
        return true;
    }

    public static boolean sendConfirmedTowerLocSignal(MapLocation sendLoc, MapLocation loc) throws GameActionException {
        if (!canSendMessagesToLoc(sendLoc)) return false;
        int[] fields = new int[]{getIntFromLocation(loc)};
        int[] fieldLengths = new int[]{LOC_LENGTH};
        int message = createMessage(3, fields, fieldLengths);
        rc.sendMessage(sendLoc, message);
        return true;
    }

    public static boolean sendFloodSignal(MapLocation sendLoc) throws GameActionException {
        if (!canSendMessagesToLoc(sendLoc)) return false;
        int message = createMessage(4, null, null);
        rc.sendMessage(sendLoc, message);
        return true;
    }

    public static boolean sendRuinLocSignal(MapLocation sendLoc, MapLocation loc) throws GameActionException {
        if (!canSendMessagesToLoc(sendLoc)) return false;
        int[] fields = new int[]{getIntFromLocation(loc)};
        int[] fieldLengths = new int[]{LOC_LENGTH};
        int message = createMessage(5, fields, fieldLengths);
        rc.sendMessage(sendLoc, message);
        return true;
    }

    public static void sendMessageBot(MapLocation sendLoc) throws GameActionException {
        //Try ConfirmedTowerLocSignal first
        if (towerLocs.size() > 0)
        {
            sendConfirmedTowerLocSignal(sendLoc, towerLocs.getLoc(rng.nextInt(towerLocs.size())));
            return;
        }

        //Then try RuinLocSignal
        if (ruinLocs.size() > 0)
        {
            sendRuinLocSignal(sendLoc, ruinLocs.getLoc(rng.nextInt(ruinLocs.size())));
            return;
        }

        //Lastly, try PaintRequest
        if (rc.getPaint() < rc.getType().paintCapacity * 0.3)
        {
            sendPaintRequest(sendLoc, myId);
            return;
        }
    }

    public static boolean inDrought()
    {
        if (cleanMessages.length < 4) return false;
        // If at least 60% of messages are paint requests, we are in a drought
        int requests = 0;
        for (int i = 0; i < cleanMessages.length; i++)
        {
            if (cleanMessages[i].mType == MessageType.PAINT_REQUEST) requests++;

        }
        double frac = (double) requests / cleanMessages.length;
        return frac >= 0.6;
    }

    public static void sendMessagesTower(MapLocation[] sendLocs) throws GameActionException {
        //First, try PossibleTowerLocSignal
        if (possibleTowerLocs.size() > 0)
        {
            for (int i = 0; i < sendLocs.length; i++) {
                if (!sendConfirmedTowerLocSignal(sendLocs[i], possibleTowerLocs.getLoc(rng.nextInt(possibleTowerLocs.size())))) return;
            }
        }

        //Next, try ConfirmedTowerLocSignal
        if (towerLocs.size() > 0)
        {
            for (int i = 0; i < sendLocs.length; i++) {
                if (!sendConfirmedTowerLocSignal(sendLocs[i], towerLocs.getLoc(rng.nextInt(towerLocs.size())))) return;
            }
        }

        //Then try RuinLocSignal
        if (ruinLocs.size() > 0)
        {
            for (int i = 0; i < sendLocs.length; i++) {
                if (!sendConfirmedTowerLocSignal(sendLocs[i], ruinLocs.getLoc(rng.nextInt(ruinLocs.size())))) return;
            }
        }

        //Then try DroughtSignal
        if (inDrought())
        {
            for (int i = 0; i < sendLocs.length; i++) {
                if (!sendDroughtSignal(sendLocs[i])) return;
            }
        }

    }

    public static CleanMessage decodeMessage(int message)
    {
        int opCode = message % 8;
        MessageType mType = null;
        int locI = -1;
        int id = -1;
        MapLocation loc = null;
        switch (opCode)
        {
            case 0:
                mType = MessageType.PAINT_REQUEST;
                message = message >> 3;
                id = message % 2^ID_LENGTH;
                break;
            case 1:
                mType = MessageType.DROUGHT_SIGNAL;
                break;
            case 2:
                mType = MessageType.POSSIBLETOWERLOCSIGNAL;
                message = message >> 3;
                locI = message % 2^LOC_LENGTH;
                loc = getLocationFromInt(locI);
                break;
            case 3:
                mType = MessageType.CONFIRMEDTOWERLOCSIGNAL;
                message = message >> 3;
                locI = message % 2^LOC_LENGTH;
                loc = getLocationFromInt(locI);
                break;
            case 4:
                mType = MessageType.FLOODSIGNAL;
                break;
            case 5:
                mType = MessageType.RUINLOCSIGNAL;
                message = message >> 3;
                locI = message % 2^LOC_LENGTH;
                loc = getLocationFromInt(locI);
                break;
        }
        return new CleanMessage(mType, loc, id);
    }

    public static void updateCleanMessages()
    {
        Message[] m = rc.readMessages(-1);
        CleanMessage[] c = new CleanMessage[m.length];
        for (int i = 0; i < m.length; i++)
        {
            c[i] = decodeMessage(m[i].getBytes());
        }
        cleanMessages = c;
    }

    public static void sendMessages() throws GameActionException {
        RobotInfo[] allies = rc.senseNearbyRobots(-1, myTeam);
        if (allies.length == 0) return;
        if (rc.getType().isTowerType())
        {
            //If we're a tower, get all bots suitable to get info
            MapLocation[] possibleTargets = new MapLocation[allies.length];
            int c = 0;
            for (int i = 0; i < allies.length; i++)
            {
                if (!allies[i].getType().isRobotType()) continue;
                if (!canSendMessagesToLoc(allies[i].getLocation())) continue;
                possibleTargets[c] = allies[i].getLocation();
                c++;
            }
            if (c == 0) return;
            MapLocation[] targets = new MapLocation[c];
            System.arraycopy(possibleTargets, 0, targets, 0, c);
            sendMessagesTower(targets);
        }
        else
        {
            //If we're a bot, send messages to nearest tower

            int minDist = 1000;
            MapLocation target = null;
            for (int i = 0; i < allies.length; i++)
            {
                if (!allies[i].getType().isTowerType()) continue;
                if (!canSendMessagesToLoc(allies[i].getLocation())) continue;
                int dist = rc.getLocation().distanceSquaredTo(allies[i].getLocation());
                if (dist < minDist)
                {
                    minDist = dist;
                    target = allies[i].getLocation();
                }
            }
            if (target != null)
            {
                //Send a message to target
                sendMessageBot(target);
            }
        }
    }

    public static void processMessages()
    {
        for (int i = 0; i < cleanMessages.length; i++)
        {
            CleanMessage m = cleanMessages[i];
            switch (m.mType)
            {
                case PAINT_REQUEST:
                    idsThatNeedPaint.add(m.id);
                    break;
                case DROUGHT_SIGNAL:
                    isDrought = true;
                    isFlood = false;
                    break;
                case POSSIBLETOWERLOCSIGNAL:
                    possibleTowerLocs.add(m.loc);
                    break;
                case CONFIRMEDTOWERLOCSIGNAL:
                    towerLocs.add(m.loc);
                    break;
                case FLOODSIGNAL:
                    isDrought = false;
                    isFlood = true;
                    break;
                case RUINLOCSIGNAL:
                    ruinLocs.add(m.loc);
                    break;
            }
        }
    }

    public static MapLocation getLocationFromInt(int i)
    {
        int x = i >> Y_LENGTH;
        int y = i % Y_LENGTH^6;
        return new MapLocation(x,y);
    }

    public static int getIntFromLocation(MapLocation m)
    {
        return (m.x << X_LENGTH) + m.y;
    }

    public static boolean canSendMessagesToLoc(MapLocation loc)
    {
        return rc.canSendMessage(loc, 0);
    }
}
