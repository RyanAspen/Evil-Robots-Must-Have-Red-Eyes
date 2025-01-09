//Generated with bellmanford_codegen.py

package cannonball4;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;

public class BellmanFordNav extends Globals {
    public static Direction getBestDirection(MapLocation target) throws GameActionException {
        MapLocation location25 = rc.adjacentLocation(Direction.NORTHWEST);
        boolean canVisit25 = rc.canMove(Direction.NORTHWEST);

        MapLocation location26 = rc.adjacentLocation(Direction.WEST);
        boolean canVisit26 = rc.canMove(Direction.WEST);

        MapLocation location27 = rc.adjacentLocation(Direction.SOUTHWEST);
        boolean canVisit27 = rc.canMove(Direction.SOUTHWEST);

        MapLocation location34 = rc.adjacentLocation(Direction.NORTH);
        boolean canVisit34 = rc.canMove(Direction.NORTH);

        MapLocation location36 = rc.adjacentLocation(Direction.SOUTH);
        boolean canVisit36 = rc.canMove(Direction.SOUTH);

        MapLocation location43 = rc.adjacentLocation(Direction.NORTHEAST);
        boolean canVisit43 = rc.canMove(Direction.NORTHEAST);

        MapLocation location44 = rc.adjacentLocation(Direction.EAST);
        boolean canVisit44 = rc.canMove(Direction.EAST);

        MapLocation location45 = rc.adjacentLocation(Direction.SOUTHEAST);
        boolean canVisit45 = rc.canMove(Direction.EAST);

        if (!canVisit26 && !canVisit27 && !canVisit34 && !canVisit36 && !canVisit43 && !canVisit44 && !canVisit45 && canVisit25) {
            return Direction.NORTHWEST;
        }

        if (!canVisit25 && !canVisit27 && !canVisit34 && !canVisit36 && !canVisit43 && !canVisit44 && !canVisit45 && canVisit26) {
            return Direction.WEST;
        }

        if (!canVisit25 && !canVisit26 && !canVisit34 && !canVisit36 && !canVisit43 && !canVisit44 && !canVisit45 && canVisit27) {
            return Direction.SOUTHWEST;
        }

        if (!canVisit25 && !canVisit26 && !canVisit27 && !canVisit36 && !canVisit43 && !canVisit44 && !canVisit45 && canVisit34) {
            return Direction.NORTH;
        }

        if (!canVisit25 && !canVisit26 && !canVisit27 && !canVisit34 && !canVisit43 && !canVisit44 && !canVisit45 && canVisit36) {
            return Direction.SOUTH;
        }

        if (!canVisit25 && !canVisit26 && !canVisit27 && !canVisit34 && !canVisit36 && !canVisit44 && !canVisit45 && canVisit43) {
            return Direction.NORTHEAST;
        }

        if (!canVisit25 && !canVisit26 && !canVisit27 && !canVisit34 && !canVisit36 && !canVisit43 && !canVisit45 && canVisit44) {
            return Direction.EAST;
        }

        if (!canVisit25 && !canVisit26 && !canVisit27 && !canVisit34 && !canVisit36 && !canVisit43 && !canVisit44 && canVisit45) {
            return Direction.SOUTHEAST;
        }

        boolean checkOpponents = rc.senseNearbyRobots(GameConstants.VISION_RADIUS_SQUARED, opponentTeam).length > 0;

        int distance25 = 1000000;
        Direction direction25 = null;
        int weight25 = canVisit25 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location25, 10, opponentTeam).length : 0) : 1000000;
        int distance26 = 1000000;
        Direction direction26 = null;
        int weight26 = canVisit26 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location26, 10, opponentTeam).length : 0) : 1000000;
        int distance27 = 1000000;
        Direction direction27 = null;
        int weight27 = canVisit27 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location27, 10, opponentTeam).length : 0) : 1000000;
        int distance34 = 1000000;
        Direction direction34 = null;
        int weight34 = canVisit34 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location34, 10, opponentTeam).length : 0) : 1000000;
        int distance36 = 1000000;
        Direction direction36 = null;
        int weight36 = canVisit36 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location36, 10, opponentTeam).length : 0) : 1000000;
        int distance43 = 1000000;
        Direction direction43 = null;
        int weight43 = canVisit43 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location43, 10, opponentTeam).length : 0) : 1000000;
        int distance44 = 1000000;
        Direction direction44 = null;
        int weight44 = canVisit44 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location44, 10, opponentTeam).length : 0) : 1000000;
        int distance45 = 1000000;
        Direction direction45 = null;
        int weight45 = canVisit45 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location45, 10, opponentTeam).length : 0) : 1000000;
        MapLocation location15 = location25.add(Direction.NORTHWEST);
        boolean canVisit15 = rc.canSenseLocation(location15) && (rc.sensePassability(location15));
        int distance15 = 1000000;
        Direction direction15 = null;
        int weight15 = canVisit15 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location15, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location16 = location26.add(Direction.NORTHWEST);
        boolean canVisit16 = rc.canSenseLocation(location16) && (rc.sensePassability(location16));
        int distance16 = 1000000;
        Direction direction16 = null;
        int weight16 = canVisit16 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location16, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location17 = location25.add(Direction.SOUTHWEST);
        boolean canVisit17 = rc.canSenseLocation(location17) && (rc.sensePassability(location17));
        int distance17 = 1000000;
        Direction direction17 = null;
        int weight17 = canVisit17 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location17, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location18 = location26.add(Direction.SOUTHWEST);
        boolean canVisit18 = rc.canSenseLocation(location18) && (rc.sensePassability(location18));
        int distance18 = 1000000;
        Direction direction18 = null;
        int weight18 = canVisit18 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location18, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location19 = location27.add(Direction.SOUTHWEST);
        boolean canVisit19 = rc.canSenseLocation(location19) && (rc.sensePassability(location19));
        int distance19 = 1000000;
        Direction direction19 = null;
        int weight19 = canVisit19 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location19, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location20 = location19.add(Direction.SOUTH);
        boolean canVisit20 = rc.canSenseLocation(location20) && (rc.sensePassability(location20));
        int distance20 = 1000000;
        Direction direction20 = null;
        int weight20 = canVisit20 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location20, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location21 = location20.add(Direction.SOUTH);
        boolean canVisit21 = rc.canSenseLocation(location21) && (rc.sensePassability(location21));
        int distance21 = 1000000;
        Direction direction21 = null;
        int weight21 = canVisit21 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location21, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location23 = location15.add(Direction.NORTHEAST);
        boolean canVisit23 = rc.canSenseLocation(location23) && (rc.sensePassability(location23));
        int distance23 = 1000000;
        Direction direction23 = null;
        int weight23 = canVisit23 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location23, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location24 = location16.add(Direction.NORTHEAST);
        boolean canVisit24 = rc.canSenseLocation(location24) && (rc.sensePassability(location24));
        int distance24 = 1000000;
        Direction direction24 = null;
        int weight24 = canVisit24 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location24, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location28 = location18.add(Direction.SOUTHEAST);
        boolean canVisit28 = rc.canSenseLocation(location28) && (rc.sensePassability(location28));
        int distance28 = 1000000;
        Direction direction28 = null;
        int weight28 = canVisit28 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location28, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location29 = location19.add(Direction.SOUTHEAST);
        boolean canVisit29 = rc.canSenseLocation(location29) && (rc.sensePassability(location29));
        int distance29 = 1000000;
        Direction direction29 = null;
        int weight29 = canVisit29 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location29, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location30 = location20.add(Direction.SOUTHEAST);
        boolean canVisit30 = rc.canSenseLocation(location30) && (rc.sensePassability(location30));
        int distance30 = 1000000;
        Direction direction30 = null;
        int weight30 = canVisit30 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location30, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location31 = location23.add(Direction.NORTHEAST);
        boolean canVisit31 = rc.canSenseLocation(location31) && (rc.sensePassability(location31));
        int distance31 = 1000000;
        Direction direction31 = null;
        int weight31 = canVisit31 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location31, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location32 = location24.add(Direction.NORTHEAST);
        boolean canVisit32 = rc.canSenseLocation(location32) && (rc.sensePassability(location32));
        int distance32 = 1000000;
        Direction direction32 = null;
        int weight32 = canVisit32 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location32, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location33 = location23.add(Direction.SOUTHEAST);
        boolean canVisit33 = rc.canSenseLocation(location33) && (rc.sensePassability(location33));
        int distance33 = 1000000;
        Direction direction33 = null;
        int weight33 = canVisit33 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location33, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location37 = location27.add(Direction.SOUTHEAST);
        boolean canVisit37 = rc.canSenseLocation(location37) && (rc.sensePassability(location37));
        int distance37 = 1000000;
        Direction direction37 = null;
        int weight37 = canVisit37 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location37, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location38 = location28.add(Direction.SOUTHEAST);
        boolean canVisit38 = rc.canSenseLocation(location38) && (rc.sensePassability(location38));
        int distance38 = 1000000;
        Direction direction38 = null;
        int weight38 = canVisit38 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location38, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location39 = location29.add(Direction.SOUTHEAST);
        boolean canVisit39 = rc.canSenseLocation(location39) && (rc.sensePassability(location39));
        int distance39 = 1000000;
        Direction direction39 = null;
        int weight39 = canVisit39 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location39, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location40 = location32.add(Direction.NORTHEAST);
        boolean canVisit40 = rc.canSenseLocation(location40) && (rc.sensePassability(location40));
        int distance40 = 1000000;
        Direction direction40 = null;
        int weight40 = canVisit40 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location40, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location41 = location31.add(Direction.SOUTHEAST);
        boolean canVisit41 = rc.canSenseLocation(location41) && (rc.sensePassability(location41));
        int distance41 = 1000000;
        Direction direction41 = null;
        int weight41 = canVisit41 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location41, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location42 = location32.add(Direction.SOUTHEAST);
        boolean canVisit42 = rc.canSenseLocation(location42) && (rc.sensePassability(location42));
        int distance42 = 1000000;
        Direction direction42 = null;
        int weight42 = canVisit42 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location42, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location46 = location36.add(Direction.SOUTHEAST);
        boolean canVisit46 = rc.canSenseLocation(location46) && (rc.sensePassability(location46));
        int distance46 = 1000000;
        Direction direction46 = null;
        int weight46 = canVisit46 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location46, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location47 = location37.add(Direction.SOUTHEAST);
        boolean canVisit47 = rc.canSenseLocation(location47) && (rc.sensePassability(location47));
        int distance47 = 1000000;
        Direction direction47 = null;
        int weight47 = canVisit47 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location47, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location48 = location38.add(Direction.SOUTHEAST);
        boolean canVisit48 = rc.canSenseLocation(location48) && (rc.sensePassability(location48));
        int distance48 = 1000000;
        Direction direction48 = null;
        int weight48 = canVisit48 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location48, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location49 = location41.add(Direction.NORTHEAST);
        boolean canVisit49 = rc.canSenseLocation(location49) && (rc.sensePassability(location49));
        int distance49 = 1000000;
        Direction direction49 = null;
        int weight49 = canVisit49 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location49, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location50 = location40.add(Direction.SOUTHEAST);
        boolean canVisit50 = rc.canSenseLocation(location50) && (rc.sensePassability(location50));
        int distance50 = 1000000;
        Direction direction50 = null;
        int weight50 = canVisit50 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location50, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location51 = location41.add(Direction.SOUTHEAST);
        boolean canVisit51 = rc.canSenseLocation(location51) && (rc.sensePassability(location51));
        int distance51 = 1000000;
        Direction direction51 = null;
        int weight51 = canVisit51 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location51, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location52 = location42.add(Direction.SOUTHEAST);
        boolean canVisit52 = rc.canSenseLocation(location52) && (rc.sensePassability(location52));
        int distance52 = 1000000;
        Direction direction52 = null;
        int weight52 = canVisit52 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location52, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location53 = location43.add(Direction.SOUTHEAST);
        boolean canVisit53 = rc.canSenseLocation(location53) && (rc.sensePassability(location53));
        int distance53 = 1000000;
        Direction direction53 = null;
        int weight53 = canVisit53 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location53, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location54 = location44.add(Direction.SOUTHEAST);
        boolean canVisit54 = rc.canSenseLocation(location54) && (rc.sensePassability(location54));
        int distance54 = 1000000;
        Direction direction54 = null;
        int weight54 = canVisit54 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location54, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location55 = location45.add(Direction.SOUTHEAST);
        boolean canVisit55 = rc.canSenseLocation(location55) && (rc.sensePassability(location55));
        int distance55 = 1000000;
        Direction direction55 = null;
        int weight55 = canVisit55 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location55, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location56 = location46.add(Direction.SOUTHEAST);
        boolean canVisit56 = rc.canSenseLocation(location56) && (rc.sensePassability(location56));
        int distance56 = 1000000;
        Direction direction56 = null;
        int weight56 = canVisit56 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location56, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location57 = location47.add(Direction.SOUTHEAST);
        boolean canVisit57 = rc.canSenseLocation(location57) && (rc.sensePassability(location57));
        int distance57 = 1000000;
        Direction direction57 = null;
        int weight57 = canVisit57 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location57, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location58 = location49.add(Direction.SOUTHEAST);
        boolean canVisit58 = rc.canSenseLocation(location58) && (rc.sensePassability(location58));
        int distance58 = 1000000;
        Direction direction58 = null;
        int weight58 = canVisit58 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location58, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location59 = location50.add(Direction.SOUTHEAST);
        boolean canVisit59 = rc.canSenseLocation(location59) && (rc.sensePassability(location59));
        int distance59 = 1000000;
        Direction direction59 = null;
        int weight59 = canVisit59 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location59, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location60 = location51.add(Direction.SOUTHEAST);
        boolean canVisit60 = rc.canSenseLocation(location60) && (rc.sensePassability(location60));
        int distance60 = 1000000;
        Direction direction60 = null;
        int weight60 = canVisit60 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location60, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location61 = location52.add(Direction.SOUTHEAST);
        boolean canVisit61 = rc.canSenseLocation(location61) && (rc.sensePassability(location61));
        int distance61 = 1000000;
        Direction direction61 = null;
        int weight61 = canVisit61 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location61, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location62 = location53.add(Direction.SOUTHEAST);
        boolean canVisit62 = rc.canSenseLocation(location62) && (rc.sensePassability(location62));
        int distance62 = 1000000;
        Direction direction62 = null;
        int weight62 = canVisit62 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location62, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location63 = location54.add(Direction.SOUTHEAST);
        boolean canVisit63 = rc.canSenseLocation(location63) && (rc.sensePassability(location63));
        int distance63 = 1000000;
        Direction direction63 = null;
        int weight63 = canVisit63 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location63, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location64 = location55.add(Direction.SOUTHEAST);
        boolean canVisit64 = rc.canSenseLocation(location64) && (rc.sensePassability(location64));
        int distance64 = 1000000;
        Direction direction64 = null;
        int weight64 = canVisit64 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location64, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location65 = location58.add(Direction.SOUTHEAST);
        boolean canVisit65 = rc.canSenseLocation(location65) && (rc.sensePassability(location65));
        int distance65 = 1000000;
        Direction direction65 = null;
        int weight65 = canVisit65 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location65, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location66 = location59.add(Direction.SOUTHEAST);
        boolean canVisit66 = rc.canSenseLocation(location66) && (rc.sensePassability(location66));
        int distance66 = 1000000;
        Direction direction66 = null;
        int weight66 = canVisit66 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location66, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location67 = location60.add(Direction.SOUTHEAST);
        boolean canVisit67 = rc.canSenseLocation(location67) && (rc.sensePassability(location67));
        int distance67 = 1000000;
        Direction direction67 = null;
        int weight67 = canVisit67 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location67, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location68 = location61.add(Direction.SOUTHEAST);
        boolean canVisit68 = rc.canSenseLocation(location68) && (rc.sensePassability(location68));
        int distance68 = 1000000;
        Direction direction68 = null;
        int weight68 = canVisit68 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location68, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location69 = location62.add(Direction.SOUTHEAST);
        boolean canVisit69 = rc.canSenseLocation(location69) && (rc.sensePassability(location69));
        int distance69 = 1000000;
        Direction direction69 = null;
        int weight69 = canVisit69 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location69, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location6 = location15.add(Direction.NORTHWEST);
        boolean canVisit6 = rc.canSenseLocation(location6) && (rc.sensePassability(location6));
        int distance6 = 1000000;
        Direction direction6 = null;
        int weight6 = canVisit6 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location6, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location7 = location16.add(Direction.NORTHWEST);
        boolean canVisit7 = rc.canSenseLocation(location7) && (rc.sensePassability(location7));
        int distance7 = 1000000;
        Direction direction7 = null;
        int weight7 = canVisit7 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location7, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location8 = location15.add(Direction.SOUTHWEST);
        boolean canVisit8 = rc.canSenseLocation(location8) && (rc.sensePassability(location8));
        int distance8 = 1000000;
        Direction direction8 = null;
        int weight8 = canVisit8 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location8, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location9 = location16.add(Direction.SOUTHWEST);
        boolean canVisit9 = rc.canSenseLocation(location9) && (rc.sensePassability(location9));
        int distance9 = 1000000;
        Direction direction9 = null;
        int weight9 = canVisit9 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location9, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location10 = location17.add(Direction.SOUTHWEST);
        boolean canVisit10 = rc.canSenseLocation(location10) && (rc.sensePassability(location10));
        int distance10 = 1000000;
        Direction direction10 = null;
        int weight10 = canVisit10 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location10, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location11 = location18.add(Direction.SOUTHWEST);
        boolean canVisit11 = rc.canSenseLocation(location11) && (rc.sensePassability(location11));
        int distance11 = 1000000;
        Direction direction11 = null;
        int weight11 = canVisit11 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location11, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location12 = location19.add(Direction.SOUTHWEST);
        boolean canVisit12 = rc.canSenseLocation(location12) && (rc.sensePassability(location12));
        int distance12 = 1000000;
        Direction direction12 = null;
        int weight12 = canVisit12 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location12, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location13 = location6.add(Direction.NORTHEAST);
        boolean canVisit13 = rc.canSenseLocation(location13) && (rc.sensePassability(location13));
        int distance13 = 1000000;
        Direction direction13 = null;
        int weight13 = canVisit13 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location13, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location14 = location7.add(Direction.NORTHEAST);
        boolean canVisit14 = rc.canSenseLocation(location14) && (rc.sensePassability(location14));
        int distance14 = 1000000;
        Direction direction14 = null;
        int weight14 = canVisit14 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location14, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location22 = location14.add(Direction.NORTHEAST);
        boolean canVisit22 = rc.canSenseLocation(location22) && (rc.sensePassability(location22));
        int distance22 = 1000000;
        Direction direction22 = null;
        int weight22 = canVisit22 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location22, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location1 = location6.add(Direction.SOUTHWEST);
        boolean canVisit1 = rc.canSenseLocation(location1) && (rc.sensePassability(location1));
        int distance1 = 1000000;
        Direction direction1 = null;
        int weight1 = canVisit1 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location1, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location2 = location7.add(Direction.SOUTHWEST);
        boolean canVisit2 = rc.canSenseLocation(location2) && (rc.sensePassability(location2));
        int distance2 = 1000000;
        Direction direction2 = null;
        int weight2 = canVisit2 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location2, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location3 = location8.add(Direction.SOUTHWEST);
        boolean canVisit3 = rc.canSenseLocation(location3) && (rc.sensePassability(location3));
        int distance3 = 1000000;
        Direction direction3 = null;
        int weight3 = canVisit3 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location3, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location4 = location9.add(Direction.SOUTHWEST);
        boolean canVisit4 = rc.canSenseLocation(location4) && (rc.sensePassability(location4));
        int distance4 = 1000000;
        Direction direction4 = null;
        int weight4 = canVisit4 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location4, 10, opponentTeam).length : 0) : 1000000;

        MapLocation location5 = location10.add(Direction.SOUTHWEST);
        boolean canVisit5 = rc.canSenseLocation(location5) && (rc.sensePassability(location5));
        int distance5 = 1000000;
        Direction direction5 = null;
        int weight5 = canVisit5 ? 1 + (checkOpponents ? rc.senseNearbyRobots(location5, 10, opponentTeam).length : 0) : 1000000;

        if (canVisit25) {
            distance25 = weight25;
            direction25 = Direction.NORTHWEST;
        }

        if (canVisit26) {
            distance26 = weight26;
            direction26 = Direction.WEST;
        }

        if (canVisit27) {
            distance27 = weight27;
            direction27 = Direction.SOUTHWEST;
        }

        if (canVisit34) {
            distance34 = weight34;
            direction34 = Direction.NORTH;
        }

        if (canVisit36) {
            distance36 = weight36;
            direction36 = Direction.SOUTH;
        }

        if (canVisit43) {
            distance43 = weight43;
            direction43 = Direction.NORTHEAST;
        }

        if (canVisit44) {
            distance44 = weight44;
            direction44 = Direction.EAST;
        }

        if (canVisit45) {
            distance45 = weight45;
            direction45 = Direction.SOUTHEAST;
        }

        if (canVisit1) {
            if (distance6 + weight1 < distance1) {
                distance1 = distance6 + weight1;
                direction1 = direction6;
            }

            if (distance8 + weight1 < distance1) {
                distance1 = distance8 + weight1;
                direction1 = direction8;
            }

            if (distance7 + weight1 < distance1) {
                distance1 = distance7 + weight1;
                direction1 = direction7;
            }

            if (distance2 + weight1 < distance1) {
                distance1 = distance2 + weight1;
                direction1 = direction2;
            }

        }

        if (canVisit2) {
            if (distance7 + weight2 < distance2) {
                distance2 = distance7 + weight2;
                direction2 = direction7;
            }

            if (distance9 + weight2 < distance2) {
                distance2 = distance9 + weight2;
                direction2 = direction9;
            }

            if (distance8 + weight2 < distance2) {
                distance2 = distance8 + weight2;
                direction2 = direction8;
            }

            if (distance3 + weight2 < distance2) {
                distance2 = distance3 + weight2;
                direction2 = direction3;
            }

        }

        if (canVisit3) {
            if (distance8 + weight3 < distance3) {
                distance3 = distance8 + weight3;
                direction3 = direction8;
            }

            if (distance10 + weight3 < distance3) {
                distance3 = distance10 + weight3;
                direction3 = direction10;
            }

            if (distance9 + weight3 < distance3) {
                distance3 = distance9 + weight3;
                direction3 = direction9;
            }

        }

        if (canVisit4) {
            if (distance9 + weight4 < distance4) {
                distance4 = distance9 + weight4;
                direction4 = direction9;
            }

            if (distance11 + weight4 < distance4) {
                distance4 = distance11 + weight4;
                direction4 = direction11;
            }

            if (distance10 + weight4 < distance4) {
                distance4 = distance10 + weight4;
                direction4 = direction10;
            }

            if (distance3 + weight4 < distance4) {
                distance4 = distance3 + weight4;
                direction4 = direction3;
            }

        }

        if (canVisit5) {
            if (distance10 + weight5 < distance5) {
                distance5 = distance10 + weight5;
                direction5 = direction10;
            }

            if (distance12 + weight5 < distance5) {
                distance5 = distance12 + weight5;
                direction5 = direction12;
            }

            if (distance11 + weight5 < distance5) {
                distance5 = distance11 + weight5;
                direction5 = direction11;
            }

            if (distance4 + weight5 < distance5) {
                distance5 = distance4 + weight5;
                direction5 = direction4;
            }

        }

        if (canVisit6) {
            if (distance15 + weight6 < distance6) {
                distance6 = distance15 + weight6;
                direction6 = direction15;
            }

            if (distance14 + weight6 < distance6) {
                distance6 = distance14 + weight6;
                direction6 = direction14;
            }

            if (distance7 + weight6 < distance6) {
                distance6 = distance7 + weight6;
                direction6 = direction7;
            }

        }

        if (canVisit7) {
            if (distance16 + weight7 < distance7) {
                distance7 = distance16 + weight7;
                direction7 = direction16;
            }

            if (distance15 + weight7 < distance7) {
                distance7 = distance15 + weight7;
                direction7 = direction15;
            }

            if (distance8 + weight7 < distance7) {
                distance7 = distance8 + weight7;
                direction7 = direction8;
            }

        }

        if (canVisit8) {
            if (distance15 + weight8 < distance8) {
                distance8 = distance15 + weight8;
                direction8 = direction15;
            }

            if (distance17 + weight8 < distance8) {
                distance8 = distance17 + weight8;
                direction8 = direction17;
            }

            if (distance16 + weight8 < distance8) {
                distance8 = distance16 + weight8;
                direction8 = direction16;
            }

            if (distance9 + weight8 < distance8) {
                distance8 = distance9 + weight8;
                direction8 = direction9;
            }

        }

        if (canVisit9) {
            if (distance16 + weight9 < distance9) {
                distance9 = distance16 + weight9;
                direction9 = direction16;
            }

            if (distance18 + weight9 < distance9) {
                distance9 = distance18 + weight9;
                direction9 = direction18;
            }

            if (distance17 + weight9 < distance9) {
                distance9 = distance17 + weight9;
                direction9 = direction17;
            }

        }

        if (canVisit10) {
            if (distance17 + weight10 < distance10) {
                distance10 = distance17 + weight10;
                direction10 = direction17;
            }

            if (distance19 + weight10 < distance10) {
                distance10 = distance19 + weight10;
                direction10 = direction19;
            }

            if (distance18 + weight10 < distance10) {
                distance10 = distance18 + weight10;
                direction10 = direction18;
            }

            if (distance9 + weight10 < distance10) {
                distance10 = distance9 + weight10;
                direction10 = direction9;
            }

        }

        if (canVisit11) {
            if (distance18 + weight11 < distance11) {
                distance11 = distance18 + weight11;
                direction11 = direction18;
            }

            if (distance19 + weight11 < distance11) {
                distance11 = distance19 + weight11;
                direction11 = direction19;
            }

            if (distance10 + weight11 < distance11) {
                distance11 = distance10 + weight11;
                direction11 = direction10;
            }

        }

        if (canVisit12) {
            if (distance19 + weight12 < distance12) {
                distance12 = distance19 + weight12;
                direction12 = direction19;
            }

            if (distance20 + weight12 < distance12) {
                distance12 = distance20 + weight12;
                direction12 = direction20;
            }

            if (distance11 + weight12 < distance12) {
                distance12 = distance11 + weight12;
                direction12 = direction11;
            }

        }

        if (canVisit13) {
            if (distance6 + weight13 < distance13) {
                distance13 = distance6 + weight13;
                direction13 = direction6;
            }

            if (distance23 + weight13 < distance13) {
                distance13 = distance23 + weight13;
                direction13 = direction23;
            }

            if (distance22 + weight13 < distance13) {
                distance13 = distance22 + weight13;
                direction13 = direction22;
            }

            if (distance14 + weight13 < distance13) {
                distance13 = distance14 + weight13;
                direction13 = direction14;
            }

        }

        if (canVisit14) {
            if (distance24 + weight14 < distance14) {
                distance14 = distance24 + weight14;
                direction14 = direction24;
            }

            if (distance23 + weight14 < distance14) {
                distance14 = distance23 + weight14;
                direction14 = direction23;
            }

            if (distance15 + weight14 < distance14) {
                distance14 = distance15 + weight14;
                direction14 = direction15;
            }

        }

        if (canVisit15) {
            if (distance25 + weight15 < distance15) {
                distance15 = distance25 + weight15;
                direction15 = direction25;
            }

            if (distance24 + weight15 < distance15) {
                distance15 = distance24 + weight15;
                direction15 = direction24;
            }

            if (distance16 + weight15 < distance15) {
                distance15 = distance16 + weight15;
                direction15 = direction16;
            }

        }

        if (canVisit16) {
            if (distance26 + weight16 < distance16) {
                distance16 = distance26 + weight16;
                direction16 = direction26;
            }

            if (distance25 + weight16 < distance16) {
                distance16 = distance25 + weight16;
                direction16 = direction25;
            }

            if (distance17 + weight16 < distance16) {
                distance16 = distance17 + weight16;
                direction16 = direction17;
            }

        }

        if (canVisit17) {
            if (distance25 + weight17 < distance17) {
                distance17 = distance25 + weight17;
                direction17 = direction25;
            }

            if (distance27 + weight17 < distance17) {
                distance17 = distance27 + weight17;
                direction17 = direction27;
            }

            if (distance26 + weight17 < distance17) {
                distance17 = distance26 + weight17;
                direction17 = direction26;
            }

        }

        if (canVisit18) {
            if (distance26 + weight18 < distance18) {
                distance18 = distance26 + weight18;
                direction18 = direction26;
            }

            if (distance27 + weight18 < distance18) {
                distance18 = distance27 + weight18;
                direction18 = direction27;
            }

            if (distance17 + weight18 < distance18) {
                distance18 = distance17 + weight18;
                direction18 = direction17;
            }

        }

        if (canVisit19) {
            if (distance27 + weight19 < distance19) {
                distance19 = distance27 + weight19;
                direction19 = direction27;
            }

            if (distance28 + weight19 < distance19) {
                distance19 = distance28 + weight19;
                direction19 = direction28;
            }

            if (distance18 + weight19 < distance19) {
                distance19 = distance18 + weight19;
                direction19 = direction18;
            }

        }

        if (canVisit20) {
            if (distance28 + weight20 < distance20) {
                distance20 = distance28 + weight20;
                direction20 = direction28;
            }

            if (distance29 + weight20 < distance20) {
                distance20 = distance29 + weight20;
                direction20 = direction29;
            }

            if (distance19 + weight20 < distance20) {
                distance20 = distance19 + weight20;
                direction20 = direction19;
            }

        }

        if (canVisit21) {
            if (distance12 + weight21 < distance21) {
                distance21 = distance12 + weight21;
                direction21 = direction12;
            }

            if (distance29 + weight21 < distance21) {
                distance21 = distance29 + weight21;
                direction21 = direction29;
            }

            if (distance30 + weight21 < distance21) {
                distance21 = distance30 + weight21;
                direction21 = direction30;
            }

            if (distance20 + weight21 < distance21) {
                distance21 = distance20 + weight21;
                direction21 = direction20;
            }

        }

        if (canVisit22) {
            if (distance14 + weight22 < distance22) {
                distance22 = distance14 + weight22;
                direction22 = direction14;
            }

            if (distance32 + weight22 < distance22) {
                distance22 = distance32 + weight22;
                direction22 = direction32;
            }

            if (distance31 + weight22 < distance22) {
                distance22 = distance31 + weight22;
                direction22 = direction31;
            }

            if (distance23 + weight22 < distance22) {
                distance22 = distance23 + weight22;
                direction22 = direction23;
            }

        }

        if (canVisit23) {
            if (distance15 + weight23 < distance23) {
                distance23 = distance15 + weight23;
                direction23 = direction15;
            }

            if (distance33 + weight23 < distance23) {
                distance23 = distance33 + weight23;
                direction23 = direction33;
            }

            if (distance32 + weight23 < distance23) {
                distance23 = distance32 + weight23;
                direction23 = direction32;
            }

            if (distance24 + weight23 < distance23) {
                distance23 = distance24 + weight23;
                direction23 = direction24;
            }

        }

        if (canVisit24) {
            if (distance34 + weight24 < distance24) {
                distance24 = distance34 + weight24;
                direction24 = direction34;
            }

            if (distance33 + weight24 < distance24) {
                distance24 = distance33 + weight24;
                direction24 = direction33;
            }

            if (distance25 + weight24 < distance24) {
                distance24 = distance25 + weight24;
                direction24 = direction25;
            }

        }

        if (canVisit28) {
            if (distance36 + weight28 < distance28) {
                distance28 = distance36 + weight28;
                direction28 = direction36;
            }

            if (distance37 + weight28 < distance28) {
                distance28 = distance37 + weight28;
                direction28 = direction37;
            }

            if (distance27 + weight28 < distance28) {
                distance28 = distance27 + weight28;
                direction28 = direction27;
            }

        }

        if (canVisit29) {
            if (distance19 + weight29 < distance29) {
                distance29 = distance19 + weight29;
                direction29 = direction19;
            }

            if (distance37 + weight29 < distance29) {
                distance29 = distance37 + weight29;
                direction29 = direction37;
            }

            if (distance38 + weight29 < distance29) {
                distance29 = distance38 + weight29;
                direction29 = direction38;
            }

            if (distance28 + weight29 < distance29) {
                distance29 = distance28 + weight29;
                direction29 = direction28;
            }

        }

        if (canVisit30) {
            if (distance20 + weight30 < distance30) {
                distance30 = distance20 + weight30;
                direction30 = direction20;
            }

            if (distance38 + weight30 < distance30) {
                distance30 = distance38 + weight30;
                direction30 = direction38;
            }

            if (distance39 + weight30 < distance30) {
                distance30 = distance39 + weight30;
                direction30 = direction39;
            }

            if (distance29 + weight30 < distance30) {
                distance30 = distance29 + weight30;
                direction30 = direction29;
            }

        }

        if (canVisit31) {
            if (distance23 + weight31 < distance31) {
                distance31 = distance23 + weight31;
                direction31 = direction23;
            }

            if (distance41 + weight31 < distance31) {
                distance31 = distance41 + weight31;
                direction31 = direction41;
            }

            if (distance32 + weight31 < distance31) {
                distance31 = distance32 + weight31;
                direction31 = direction32;
            }

        }

        if (canVisit32) {
            if (distance24 + weight32 < distance32) {
                distance32 = distance24 + weight32;
                direction32 = direction24;
            }

            if (distance42 + weight32 < distance32) {
                distance32 = distance42 + weight32;
                direction32 = direction42;
            }

            if (distance33 + weight32 < distance32) {
                distance32 = distance33 + weight32;
                direction32 = direction33;
            }

        }

        if (canVisit33) {
            if (distance25 + weight33 < distance33) {
                distance33 = distance25 + weight33;
                direction33 = direction25;
            }

            if (distance43 + weight33 < distance33) {
                distance33 = distance43 + weight33;
                direction33 = direction43;
            }

            if (distance34 + weight33 < distance33) {
                distance33 = distance34 + weight33;
                direction33 = direction34;
            }

        }

        if (canVisit37) {
            if (distance27 + weight37 < distance37) {
                distance37 = distance27 + weight37;
                direction37 = direction27;
            }

            if (distance45 + weight37 < distance37) {
                distance37 = distance45 + weight37;
                direction37 = direction45;
            }

            if (distance36 + weight37 < distance37) {
                distance37 = distance36 + weight37;
                direction37 = direction36;
            }

        }

        if (canVisit38) {
            if (distance28 + weight38 < distance38) {
                distance38 = distance28 + weight38;
                direction38 = direction28;
            }

            if (distance46 + weight38 < distance38) {
                distance38 = distance46 + weight38;
                direction38 = direction46;
            }

            if (distance37 + weight38 < distance38) {
                distance38 = distance37 + weight38;
                direction38 = direction37;
            }

        }

        if (canVisit39) {
            if (distance29 + weight39 < distance39) {
                distance39 = distance29 + weight39;
                direction39 = direction29;
            }

            if (distance47 + weight39 < distance39) {
                distance39 = distance47 + weight39;
                direction39 = direction47;
            }

            if (distance38 + weight39 < distance39) {
                distance39 = distance38 + weight39;
                direction39 = direction38;
            }

        }

        if (canVisit40) {
            if (distance32 + weight40 < distance40) {
                distance40 = distance32 + weight40;
                direction40 = direction32;
            }

            if (distance31 + weight40 < distance40) {
                distance40 = distance31 + weight40;
                direction40 = direction31;
            }

            if (distance50 + weight40 < distance40) {
                distance40 = distance50 + weight40;
                direction40 = direction50;
            }

            if (distance41 + weight40 < distance40) {
                distance40 = distance41 + weight40;
                direction40 = direction41;
            }

        }

        if (canVisit41) {
            if (distance33 + weight41 < distance41) {
                distance41 = distance33 + weight41;
                direction41 = direction33;
            }

            if (distance32 + weight41 < distance41) {
                distance41 = distance32 + weight41;
                direction41 = direction32;
            }

            if (distance51 + weight41 < distance41) {
                distance41 = distance51 + weight41;
                direction41 = direction51;
            }

            if (distance42 + weight41 < distance41) {
                distance41 = distance42 + weight41;
                direction41 = direction42;
            }

        }

        if (canVisit42) {
            if (distance34 + weight42 < distance42) {
                distance42 = distance34 + weight42;
                direction42 = direction34;
            }

            if (distance33 + weight42 < distance42) {
                distance42 = distance33 + weight42;
                direction42 = direction33;
            }

            if (distance43 + weight42 < distance42) {
                distance42 = distance43 + weight42;
                direction42 = direction43;
            }

        }

        if (canVisit46) {
            if (distance36 + weight46 < distance46) {
                distance46 = distance36 + weight46;
                direction46 = direction36;
            }

            if (distance37 + weight46 < distance46) {
                distance46 = distance37 + weight46;
                direction46 = direction37;
            }

            if (distance45 + weight46 < distance46) {
                distance46 = distance45 + weight46;
                direction46 = direction45;
            }

        }

        if (canVisit47) {
            if (distance37 + weight47 < distance47) {
                distance47 = distance37 + weight47;
                direction47 = direction37;
            }

            if (distance38 + weight47 < distance47) {
                distance47 = distance38 + weight47;
                direction47 = direction38;
            }

            if (distance55 + weight47 < distance47) {
                distance47 = distance55 + weight47;
                direction47 = direction55;
            }

            if (distance46 + weight47 < distance47) {
                distance47 = distance46 + weight47;
                direction47 = direction46;
            }

        }

        if (canVisit48) {
            if (distance38 + weight48 < distance48) {
                distance48 = distance38 + weight48;
                direction48 = direction38;
            }

            if (distance39 + weight48 < distance48) {
                distance48 = distance39 + weight48;
                direction48 = direction39;
            }

            if (distance56 + weight48 < distance48) {
                distance48 = distance56 + weight48;
                direction48 = direction56;
            }

            if (distance47 + weight48 < distance48) {
                distance48 = distance47 + weight48;
                direction48 = direction47;
            }

        }

        if (canVisit49) {
            if (distance41 + weight49 < distance49) {
                distance49 = distance41 + weight49;
                direction49 = direction41;
            }

            if (distance40 + weight49 < distance49) {
                distance49 = distance40 + weight49;
                direction49 = direction40;
            }

            if (distance58 + weight49 < distance49) {
                distance49 = distance58 + weight49;
                direction49 = direction58;
            }

            if (distance50 + weight49 < distance49) {
                distance49 = distance50 + weight49;
                direction49 = direction50;
            }

        }

        if (canVisit50) {
            if (distance42 + weight50 < distance50) {
                distance50 = distance42 + weight50;
                direction50 = direction42;
            }

            if (distance41 + weight50 < distance50) {
                distance50 = distance41 + weight50;
                direction50 = direction41;
            }

            if (distance51 + weight50 < distance50) {
                distance50 = distance51 + weight50;
                direction50 = direction51;
            }

        }

        if (canVisit51) {
            if (distance43 + weight51 < distance51) {
                distance51 = distance43 + weight51;
                direction51 = direction43;
            }

            if (distance42 + weight51 < distance51) {
                distance51 = distance42 + weight51;
                direction51 = direction42;
            }

            if (distance52 + weight51 < distance51) {
                distance51 = distance52 + weight51;
                direction51 = direction52;
            }

        }

        if (canVisit52) {
            if (distance44 + weight52 < distance52) {
                distance52 = distance44 + weight52;
                direction52 = direction44;
            }

            if (distance43 + weight52 < distance52) {
                distance52 = distance43 + weight52;
                direction52 = direction43;
            }

            if (distance53 + weight52 < distance52) {
                distance52 = distance53 + weight52;
                direction52 = direction53;
            }

        }

        if (canVisit53) {
            if (distance43 + weight53 < distance53) {
                distance53 = distance43 + weight53;
                direction53 = direction43;
            }

            if (distance45 + weight53 < distance53) {
                distance53 = distance45 + weight53;
                direction53 = direction45;
            }

            if (distance44 + weight53 < distance53) {
                distance53 = distance44 + weight53;
                direction53 = direction44;
            }

        }

        if (canVisit54) {
            if (distance44 + weight54 < distance54) {
                distance54 = distance44 + weight54;
                direction54 = direction44;
            }

            if (distance45 + weight54 < distance54) {
                distance54 = distance45 + weight54;
                direction54 = direction45;
            }

            if (distance53 + weight54 < distance54) {
                distance54 = distance53 + weight54;
                direction54 = direction53;
            }

        }

        if (canVisit55) {
            if (distance45 + weight55 < distance55) {
                distance55 = distance45 + weight55;
                direction55 = direction45;
            }

            if (distance46 + weight55 < distance55) {
                distance55 = distance46 + weight55;
                direction55 = direction46;
            }

            if (distance54 + weight55 < distance55) {
                distance55 = distance54 + weight55;
                direction55 = direction54;
            }

        }

        if (canVisit56) {
            if (distance46 + weight56 < distance56) {
                distance56 = distance46 + weight56;
                direction56 = direction46;
            }

            if (distance47 + weight56 < distance56) {
                distance56 = distance47 + weight56;
                direction56 = direction47;
            }

            if (distance55 + weight56 < distance56) {
                distance56 = distance55 + weight56;
                direction56 = direction55;
            }

        }

        if (canVisit57) {
            if (distance47 + weight57 < distance57) {
                distance57 = distance47 + weight57;
                direction57 = direction47;
            }

            if (distance48 + weight57 < distance57) {
                distance57 = distance48 + weight57;
                direction57 = direction48;
            }

            if (distance64 + weight57 < distance57) {
                distance57 = distance64 + weight57;
                direction57 = direction64;
            }

            if (distance56 + weight57 < distance57) {
                distance57 = distance56 + weight57;
                direction57 = direction56;
            }

        }

        if (canVisit58) {
            if (distance51 + weight58 < distance58) {
                distance58 = distance51 + weight58;
                direction58 = direction51;
            }

            if (distance50 + weight58 < distance58) {
                distance58 = distance50 + weight58;
                direction58 = direction50;
            }

            if (distance59 + weight58 < distance58) {
                distance58 = distance59 + weight58;
                direction58 = direction59;
            }

        }

        if (canVisit59) {
            if (distance52 + weight59 < distance59) {
                distance59 = distance52 + weight59;
                direction59 = direction52;
            }

            if (distance51 + weight59 < distance59) {
                distance59 = distance51 + weight59;
                direction59 = direction51;
            }

            if (distance60 + weight59 < distance59) {
                distance59 = distance60 + weight59;
                direction59 = direction60;
            }

        }

        if (canVisit60) {
            if (distance51 + weight60 < distance60) {
                distance60 = distance51 + weight60;
                direction60 = direction51;
            }

            if (distance53 + weight60 < distance60) {
                distance60 = distance53 + weight60;
                direction60 = direction53;
            }

            if (distance52 + weight60 < distance60) {
                distance60 = distance52 + weight60;
                direction60 = direction52;
            }

            if (distance61 + weight60 < distance60) {
                distance60 = distance61 + weight60;
                direction60 = direction61;
            }

        }

        if (canVisit61) {
            if (distance52 + weight61 < distance61) {
                distance61 = distance52 + weight61;
                direction61 = direction52;
            }

            if (distance54 + weight61 < distance61) {
                distance61 = distance54 + weight61;
                direction61 = direction54;
            }

            if (distance53 + weight61 < distance61) {
                distance61 = distance53 + weight61;
                direction61 = direction53;
            }

        }

        if (canVisit62) {
            if (distance53 + weight62 < distance62) {
                distance62 = distance53 + weight62;
                direction62 = direction53;
            }

            if (distance55 + weight62 < distance62) {
                distance62 = distance55 + weight62;
                direction62 = direction55;
            }

            if (distance54 + weight62 < distance62) {
                distance62 = distance54 + weight62;
                direction62 = direction54;
            }

            if (distance61 + weight62 < distance62) {
                distance62 = distance61 + weight62;
                direction62 = direction61;
            }

        }

        if (canVisit63) {
            if (distance54 + weight63 < distance63) {
                distance63 = distance54 + weight63;
                direction63 = direction54;
            }

            if (distance55 + weight63 < distance63) {
                distance63 = distance55 + weight63;
                direction63 = direction55;
            }

            if (distance62 + weight63 < distance63) {
                distance63 = distance62 + weight63;
                direction63 = direction62;
            }

        }

        if (canVisit64) {
            if (distance55 + weight64 < distance64) {
                distance64 = distance55 + weight64;
                direction64 = direction55;
            }

            if (distance56 + weight64 < distance64) {
                distance64 = distance56 + weight64;
                direction64 = direction56;
            }

            if (distance63 + weight64 < distance64) {
                distance64 = distance63 + weight64;
                direction64 = direction63;
            }

        }

        if (canVisit65) {
            if (distance58 + weight65 < distance65) {
                distance65 = distance58 + weight65;
                direction65 = direction58;
            }

            if (distance60 + weight65 < distance65) {
                distance65 = distance60 + weight65;
                direction65 = direction60;
            }

            if (distance59 + weight65 < distance65) {
                distance65 = distance59 + weight65;
                direction65 = direction59;
            }

            if (distance66 + weight65 < distance65) {
                distance65 = distance66 + weight65;
                direction65 = direction66;
            }

        }

        if (canVisit66) {
            if (distance59 + weight66 < distance66) {
                distance66 = distance59 + weight66;
                direction66 = direction59;
            }

            if (distance61 + weight66 < distance66) {
                distance66 = distance61 + weight66;
                direction66 = direction61;
            }

            if (distance60 + weight66 < distance66) {
                distance66 = distance60 + weight66;
                direction66 = direction60;
            }

            if (distance67 + weight66 < distance66) {
                distance66 = distance67 + weight66;
                direction66 = direction67;
            }

        }

        if (canVisit67) {
            if (distance60 + weight67 < distance67) {
                distance67 = distance60 + weight67;
                direction67 = direction60;
            }

            if (distance62 + weight67 < distance67) {
                distance67 = distance62 + weight67;
                direction67 = direction62;
            }

            if (distance61 + weight67 < distance67) {
                distance67 = distance61 + weight67;
                direction67 = direction61;
            }

        }

        if (canVisit68) {
            if (distance61 + weight68 < distance68) {
                distance68 = distance61 + weight68;
                direction68 = direction61;
            }

            if (distance63 + weight68 < distance68) {
                distance68 = distance63 + weight68;
                direction68 = direction63;
            }

            if (distance62 + weight68 < distance68) {
                distance68 = distance62 + weight68;
                direction68 = direction62;
            }

            if (distance67 + weight68 < distance68) {
                distance68 = distance67 + weight68;
                direction68 = direction67;
            }

        }

        if (canVisit69) {
            if (distance62 + weight69 < distance69) {
                distance69 = distance62 + weight69;
                direction69 = direction62;
            }

            if (distance64 + weight69 < distance69) {
                distance69 = distance64 + weight69;
                direction69 = direction64;
            }

            if (distance63 + weight69 < distance69) {
                distance69 = distance63 + weight69;
                direction69 = direction63;
            }

            if (distance68 + weight69 < distance69) {
                distance69 = distance68 + weight69;
                direction69 = direction68;
            }

        }

        if (canVisit2) {
            if (distance1 + weight2 < distance2) {
                distance2 = distance1 + weight2;
                direction2 = direction1;
            }

        }

        if (canVisit3) {
            if (distance2 + weight3 < distance3) {
                distance3 = distance2 + weight3;
                direction3 = direction2;
            }

            if (distance4 + weight3 < distance3) {
                distance3 = distance4 + weight3;
                direction3 = direction4;
            }

        }

        if (canVisit4) {
            if (distance5 + weight4 < distance4) {
                distance4 = distance5 + weight4;
                direction4 = direction5;
            }

        }

        if (canVisit6) {
            if (distance1 + weight6 < distance6) {
                distance6 = distance1 + weight6;
                direction6 = direction1;
            }

            if (distance13 + weight6 < distance6) {
                distance6 = distance13 + weight6;
                direction6 = direction13;
            }

        }

        if (canVisit7) {
            if (distance2 + weight7 < distance7) {
                distance7 = distance2 + weight7;
                direction7 = direction2;
            }

            if (distance1 + weight7 < distance7) {
                distance7 = distance1 + weight7;
                direction7 = direction1;
            }

            if (distance14 + weight7 < distance7) {
                distance7 = distance14 + weight7;
                direction7 = direction14;
            }

            if (distance6 + weight7 < distance7) {
                distance7 = distance6 + weight7;
                direction7 = direction6;
            }

        }

        if (canVisit8) {
            if (distance1 + weight8 < distance8) {
                distance8 = distance1 + weight8;
                direction8 = direction1;
            }

            if (distance3 + weight8 < distance8) {
                distance8 = distance3 + weight8;
                direction8 = direction3;
            }

            if (distance2 + weight8 < distance8) {
                distance8 = distance2 + weight8;
                direction8 = direction2;
            }

            if (distance7 + weight8 < distance8) {
                distance8 = distance7 + weight8;
                direction8 = direction7;
            }

        }

        if (canVisit9) {
            if (distance2 + weight9 < distance9) {
                distance9 = distance2 + weight9;
                direction9 = direction2;
            }

            if (distance4 + weight9 < distance9) {
                distance9 = distance4 + weight9;
                direction9 = direction4;
            }

            if (distance3 + weight9 < distance9) {
                distance9 = distance3 + weight9;
                direction9 = direction3;
            }

            if (distance8 + weight9 < distance9) {
                distance9 = distance8 + weight9;
                direction9 = direction8;
            }

            if (distance10 + weight9 < distance9) {
                distance9 = distance10 + weight9;
                direction9 = direction10;
            }

        }

        if (canVisit10) {
            if (distance3 + weight10 < distance10) {
                distance10 = distance3 + weight10;
                direction10 = direction3;
            }

            if (distance5 + weight10 < distance10) {
                distance10 = distance5 + weight10;
                direction10 = direction5;
            }

            if (distance4 + weight10 < distance10) {
                distance10 = distance4 + weight10;
                direction10 = direction4;
            }

            if (distance11 + weight10 < distance10) {
                distance10 = distance11 + weight10;
                direction10 = direction11;
            }

        }

        if (canVisit11) {
            if (distance4 + weight11 < distance11) {
                distance11 = distance4 + weight11;
                direction11 = direction4;
            }

            if (distance5 + weight11 < distance11) {
                distance11 = distance5 + weight11;
                direction11 = direction5;
            }

            if (distance20 + weight11 < distance11) {
                distance11 = distance20 + weight11;
                direction11 = direction20;
            }

            if (distance12 + weight11 < distance11) {
                distance11 = distance12 + weight11;
                direction11 = direction12;
            }

        }

        if (canVisit12) {
            if (distance5 + weight12 < distance12) {
                distance12 = distance5 + weight12;
                direction12 = direction5;
            }

            if (distance21 + weight12 < distance12) {
                distance12 = distance21 + weight12;
                direction12 = direction21;
            }

        }

        if (canVisit14) {
            if (distance7 + weight14 < distance14) {
                distance14 = distance7 + weight14;
                direction14 = direction7;
            }

            if (distance6 + weight14 < distance14) {
                distance14 = distance6 + weight14;
                direction14 = direction6;
            }

            if (distance22 + weight14 < distance14) {
                distance14 = distance22 + weight14;
                direction14 = direction22;
            }

            if (distance13 + weight14 < distance14) {
                distance14 = distance13 + weight14;
                direction14 = direction13;
            }

        }

        if (canVisit15) {
            if (distance6 + weight15 < distance15) {
                distance15 = distance6 + weight15;
                direction15 = direction6;
            }

            if (distance8 + weight15 < distance15) {
                distance15 = distance8 + weight15;
                direction15 = direction8;
            }

            if (distance7 + weight15 < distance15) {
                distance15 = distance7 + weight15;
                direction15 = direction7;
            }

            if (distance23 + weight15 < distance15) {
                distance15 = distance23 + weight15;
                direction15 = direction23;
            }

            if (distance14 + weight15 < distance15) {
                distance15 = distance14 + weight15;
                direction15 = direction14;
            }

        }

        if (canVisit16) {
            if (distance7 + weight16 < distance16) {
                distance16 = distance7 + weight16;
                direction16 = direction7;
            }

            if (distance9 + weight16 < distance16) {
                distance16 = distance9 + weight16;
                direction16 = direction9;
            }

            if (distance8 + weight16 < distance16) {
                distance16 = distance8 + weight16;
                direction16 = direction8;
            }

            if (distance24 + weight16 < distance16) {
                distance16 = distance24 + weight16;
                direction16 = direction24;
            }

            if (distance15 + weight16 < distance16) {
                distance16 = distance15 + weight16;
                direction16 = direction15;
            }

        }

        if (canVisit17) {
            if (distance8 + weight17 < distance17) {
                distance17 = distance8 + weight17;
                direction17 = direction8;
            }

            if (distance10 + weight17 < distance17) {
                distance17 = distance10 + weight17;
                direction17 = direction10;
            }

            if (distance9 + weight17 < distance17) {
                distance17 = distance9 + weight17;
                direction17 = direction9;
            }

            if (distance16 + weight17 < distance17) {
                distance17 = distance16 + weight17;
                direction17 = direction16;
            }

            if (distance18 + weight17 < distance17) {
                distance17 = distance18 + weight17;
                direction17 = direction18;
            }

        }

        if (canVisit18) {
            if (distance9 + weight18 < distance18) {
                distance18 = distance9 + weight18;
                direction18 = direction9;
            }

            if (distance11 + weight18 < distance18) {
                distance18 = distance11 + weight18;
                direction18 = direction11;
            }

            if (distance10 + weight18 < distance18) {
                distance18 = distance10 + weight18;
                direction18 = direction10;
            }

            if (distance28 + weight18 < distance18) {
                distance18 = distance28 + weight18;
                direction18 = direction28;
            }

            if (distance19 + weight18 < distance18) {
                distance18 = distance19 + weight18;
                direction18 = direction19;
            }

        }

        if (canVisit19) {
            if (distance10 + weight19 < distance19) {
                distance19 = distance10 + weight19;
                direction19 = direction10;
            }

            if (distance12 + weight19 < distance19) {
                distance19 = distance12 + weight19;
                direction19 = direction12;
            }

            if (distance11 + weight19 < distance19) {
                distance19 = distance11 + weight19;
                direction19 = direction11;
            }

            if (distance29 + weight19 < distance19) {
                distance19 = distance29 + weight19;
                direction19 = direction29;
            }

            if (distance20 + weight19 < distance19) {
                distance19 = distance20 + weight19;
                direction19 = direction20;
            }

        }

        if (canVisit20) {
            if (distance11 + weight20 < distance20) {
                distance20 = distance11 + weight20;
                direction20 = direction11;
            }

            if (distance12 + weight20 < distance20) {
                distance20 = distance12 + weight20;
                direction20 = direction12;
            }

            if (distance30 + weight20 < distance20) {
                distance20 = distance30 + weight20;
                direction20 = direction30;
            }

            if (distance21 + weight20 < distance20) {
                distance20 = distance21 + weight20;
                direction20 = direction21;
            }

        }

        if (canVisit22) {
            if (distance13 + weight22 < distance22) {
                distance22 = distance13 + weight22;
                direction22 = direction13;
            }

        }

        if (canVisit23) {
            if (distance13 + weight23 < distance23) {
                distance23 = distance13 + weight23;
                direction23 = direction13;
            }

            if (distance14 + weight23 < distance23) {
                distance23 = distance14 + weight23;
                direction23 = direction14;
            }

            if (distance31 + weight23 < distance23) {
                distance23 = distance31 + weight23;
                direction23 = direction31;
            }

            if (distance22 + weight23 < distance23) {
                distance23 = distance22 + weight23;
                direction23 = direction22;
            }

        }

        if (canVisit24) {
            if (distance14 + weight24 < distance24) {
                distance24 = distance14 + weight24;
                direction24 = direction14;
            }

            if (distance16 + weight24 < distance24) {
                distance24 = distance16 + weight24;
                direction24 = direction16;
            }

            if (distance15 + weight24 < distance24) {
                distance24 = distance15 + weight24;
                direction24 = direction15;
            }

            if (distance32 + weight24 < distance24) {
                distance24 = distance32 + weight24;
                direction24 = direction32;
            }

            if (distance23 + weight24 < distance24) {
                distance24 = distance23 + weight24;
                direction24 = direction23;
            }

        }

        if (canVisit28) {
            if (distance18 + weight28 < distance28) {
                distance28 = distance18 + weight28;
                direction28 = direction18;
            }

            if (distance20 + weight28 < distance28) {
                distance28 = distance20 + weight28;
                direction28 = direction20;
            }

            if (distance19 + weight28 < distance28) {
                distance28 = distance19 + weight28;
                direction28 = direction19;
            }

            if (distance38 + weight28 < distance28) {
                distance28 = distance38 + weight28;
                direction28 = direction38;
            }

            if (distance29 + weight28 < distance28) {
                distance28 = distance29 + weight28;
                direction28 = direction29;
            }

        }

        if (canVisit29) {
            if (distance21 + weight29 < distance29) {
                distance29 = distance21 + weight29;
                direction29 = direction21;
            }

            if (distance20 + weight29 < distance29) {
                distance29 = distance20 + weight29;
                direction29 = direction20;
            }

            if (distance39 + weight29 < distance29) {
                distance29 = distance39 + weight29;
                direction29 = direction39;
            }

            if (distance30 + weight29 < distance29) {
                distance29 = distance30 + weight29;
                direction29 = direction30;
            }

        }

        if (canVisit30) {
            if (distance21 + weight30 < distance30) {
                distance30 = distance21 + weight30;
                direction30 = direction21;
            }

        }

        if (canVisit31) {
            if (distance22 + weight31 < distance31) {
                distance31 = distance22 + weight31;
                direction31 = direction22;
            }

            if (distance40 + weight31 < distance31) {
                distance31 = distance40 + weight31;
                direction31 = direction40;
            }

        }

        if (canVisit32) {
            if (distance22 + weight32 < distance32) {
                distance32 = distance22 + weight32;
                direction32 = direction22;
            }

            if (distance23 + weight32 < distance32) {
                distance32 = distance23 + weight32;
                direction32 = direction23;
            }

            if (distance40 + weight32 < distance32) {
                distance32 = distance40 + weight32;
                direction32 = direction40;
            }

            if (distance41 + weight32 < distance32) {
                distance32 = distance41 + weight32;
                direction32 = direction41;
            }

            if (distance31 + weight32 < distance32) {
                distance32 = distance31 + weight32;
                direction32 = direction31;
            }

        }

        if (canVisit33) {
            if (distance23 + weight33 < distance33) {
                distance33 = distance23 + weight33;
                direction33 = direction23;
            }

            if (distance24 + weight33 < distance33) {
                distance33 = distance24 + weight33;
                direction33 = direction24;
            }

            if (distance41 + weight33 < distance33) {
                distance33 = distance41 + weight33;
                direction33 = direction41;
            }

            if (distance42 + weight33 < distance33) {
                distance33 = distance42 + weight33;
                direction33 = direction42;
            }

            if (distance32 + weight33 < distance33) {
                distance33 = distance32 + weight33;
                direction33 = direction32;
            }

        }

        if (canVisit37) {
            if (distance29 + weight37 < distance37) {
                distance37 = distance29 + weight37;
                direction37 = direction29;
            }

            if (distance28 + weight37 < distance37) {
                distance37 = distance28 + weight37;
                direction37 = direction28;
            }

            if (distance47 + weight37 < distance37) {
                distance37 = distance47 + weight37;
                direction37 = direction47;
            }

            if (distance46 + weight37 < distance37) {
                distance37 = distance46 + weight37;
                direction37 = direction46;
            }

            if (distance38 + weight37 < distance37) {
                distance37 = distance38 + weight37;
                direction37 = direction38;
            }

        }

        if (canVisit38) {
            if (distance30 + weight38 < distance38) {
                distance38 = distance30 + weight38;
                direction38 = direction30;
            }

            if (distance29 + weight38 < distance38) {
                distance38 = distance29 + weight38;
                direction38 = direction29;
            }

            if (distance48 + weight38 < distance38) {
                distance38 = distance48 + weight38;
                direction38 = direction48;
            }

            if (distance47 + weight38 < distance38) {
                distance38 = distance47 + weight38;
                direction38 = direction47;
            }

            if (distance39 + weight38 < distance38) {
                distance38 = distance39 + weight38;
                direction38 = direction39;
            }

        }

        if (canVisit39) {
            if (distance30 + weight39 < distance39) {
                distance39 = distance30 + weight39;
                direction39 = direction30;
            }

            if (distance48 + weight39 < distance39) {
                distance39 = distance48 + weight39;
                direction39 = direction48;
            }

        }

        if (canVisit40) {
            if (distance49 + weight40 < distance40) {
                distance40 = distance49 + weight40;
                direction40 = direction49;
            }

        }

        if (canVisit41) {
            if (distance31 + weight41 < distance41) {
                distance41 = distance31 + weight41;
                direction41 = direction31;
            }

            if (distance49 + weight41 < distance41) {
                distance41 = distance49 + weight41;
                direction41 = direction49;
            }

            if (distance50 + weight41 < distance41) {
                distance41 = distance50 + weight41;
                direction41 = direction50;
            }

            if (distance40 + weight41 < distance41) {
                distance41 = distance40 + weight41;
                direction41 = direction40;
            }

        }

        if (canVisit42) {
            if (distance32 + weight42 < distance42) {
                distance42 = distance32 + weight42;
                direction42 = direction32;
            }

            if (distance50 + weight42 < distance42) {
                distance42 = distance50 + weight42;
                direction42 = direction50;
            }

            if (distance52 + weight42 < distance42) {
                distance42 = distance52 + weight42;
                direction42 = direction52;
            }

            if (distance51 + weight42 < distance42) {
                distance42 = distance51 + weight42;
                direction42 = direction51;
            }

            if (distance41 + weight42 < distance42) {
                distance42 = distance41 + weight42;
                direction42 = direction41;
            }

        }

        if (canVisit46) {
            if (distance38 + weight46 < distance46) {
                distance46 = distance38 + weight46;
                direction46 = direction38;
            }

            if (distance54 + weight46 < distance46) {
                distance46 = distance54 + weight46;
                direction46 = direction54;
            }

            if (distance56 + weight46 < distance46) {
                distance46 = distance56 + weight46;
                direction46 = direction56;
            }

            if (distance55 + weight46 < distance46) {
                distance46 = distance55 + weight46;
                direction46 = direction55;
            }

            if (distance47 + weight46 < distance46) {
                distance46 = distance47 + weight46;
                direction46 = direction47;
            }

        }

        if (canVisit47) {
            if (distance39 + weight47 < distance47) {
                distance47 = distance39 + weight47;
                direction47 = direction39;
            }

            if (distance57 + weight47 < distance47) {
                distance47 = distance57 + weight47;
                direction47 = direction57;
            }

            if (distance56 + weight47 < distance47) {
                distance47 = distance56 + weight47;
                direction47 = direction56;
            }

            if (distance48 + weight47 < distance47) {
                distance47 = distance48 + weight47;
                direction47 = direction48;
            }

        }

        if (canVisit48) {
            if (distance57 + weight48 < distance48) {
                distance48 = distance57 + weight48;
                direction48 = direction57;
            }

        }

        if (canVisit50) {
            if (distance40 + weight50 < distance50) {
                distance50 = distance40 + weight50;
                direction50 = direction40;
            }

            if (distance59 + weight50 < distance50) {
                distance50 = distance59 + weight50;
                direction50 = direction59;
            }

            if (distance58 + weight50 < distance50) {
                distance50 = distance58 + weight50;
                direction50 = direction58;
            }

            if (distance49 + weight50 < distance50) {
                distance50 = distance49 + weight50;
                direction50 = direction49;
            }

        }

        if (canVisit51) {
            if (distance41 + weight51 < distance51) {
                distance51 = distance41 + weight51;
                direction51 = direction41;
            }

            if (distance58 + weight51 < distance51) {
                distance51 = distance58 + weight51;
                direction51 = direction58;
            }

            if (distance60 + weight51 < distance51) {
                distance51 = distance60 + weight51;
                direction51 = direction60;
            }

            if (distance59 + weight51 < distance51) {
                distance51 = distance59 + weight51;
                direction51 = direction59;
            }

            if (distance50 + weight51 < distance51) {
                distance51 = distance50 + weight51;
                direction51 = direction50;
            }

        }

        if (canVisit52) {
            if (distance42 + weight52 < distance52) {
                distance52 = distance42 + weight52;
                direction52 = direction42;
            }

            if (distance59 + weight52 < distance52) {
                distance52 = distance59 + weight52;
                direction52 = direction59;
            }

            if (distance61 + weight52 < distance52) {
                distance52 = distance61 + weight52;
                direction52 = direction61;
            }

            if (distance60 + weight52 < distance52) {
                distance52 = distance60 + weight52;
                direction52 = direction60;
            }

            if (distance51 + weight52 < distance52) {
                distance52 = distance51 + weight52;
                direction52 = direction51;
            }

        }

        if (canVisit53) {
            if (distance60 + weight53 < distance53) {
                distance53 = distance60 + weight53;
                direction53 = direction60;
            }

            if (distance62 + weight53 < distance53) {
                distance53 = distance62 + weight53;
                direction53 = direction62;
            }

            if (distance61 + weight53 < distance53) {
                distance53 = distance61 + weight53;
                direction53 = direction61;
            }

            if (distance52 + weight53 < distance53) {
                distance53 = distance52 + weight53;
                direction53 = direction52;
            }

            if (distance54 + weight53 < distance53) {
                distance53 = distance54 + weight53;
                direction53 = direction54;
            }

        }

        if (canVisit54) {
            if (distance46 + weight54 < distance54) {
                distance54 = distance46 + weight54;
                direction54 = direction46;
            }

            if (distance61 + weight54 < distance54) {
                distance54 = distance61 + weight54;
                direction54 = direction61;
            }

            if (distance63 + weight54 < distance54) {
                distance54 = distance63 + weight54;
                direction54 = direction63;
            }

            if (distance62 + weight54 < distance54) {
                distance54 = distance62 + weight54;
                direction54 = direction62;
            }

            if (distance55 + weight54 < distance54) {
                distance54 = distance55 + weight54;
                direction54 = direction55;
            }

        }

        if (canVisit55) {
            if (distance47 + weight55 < distance55) {
                distance55 = distance47 + weight55;
                direction55 = direction47;
            }

            if (distance62 + weight55 < distance55) {
                distance55 = distance62 + weight55;
                direction55 = direction62;
            }

            if (distance64 + weight55 < distance55) {
                distance55 = distance64 + weight55;
                direction55 = direction64;
            }

            if (distance63 + weight55 < distance55) {
                distance55 = distance63 + weight55;
                direction55 = direction63;
            }

            if (distance56 + weight55 < distance55) {
                distance55 = distance56 + weight55;
                direction55 = direction56;
            }

        }

        if (canVisit56) {
            if (distance48 + weight56 < distance56) {
                distance56 = distance48 + weight56;
                direction56 = direction48;
            }

            if (distance63 + weight56 < distance56) {
                distance56 = distance63 + weight56;
                direction56 = direction63;
            }

            if (distance64 + weight56 < distance56) {
                distance56 = distance64 + weight56;
                direction56 = direction64;
            }

            if (distance57 + weight56 < distance56) {
                distance56 = distance57 + weight56;
                direction56 = direction57;
            }

        }

        if (canVisit58) {
            if (distance49 + weight58 < distance58) {
                distance58 = distance49 + weight58;
                direction58 = direction49;
            }

            if (distance65 + weight58 < distance58) {
                distance58 = distance65 + weight58;
                direction58 = direction65;
            }

        }

        if (canVisit59) {
            if (distance50 + weight59 < distance59) {
                distance59 = distance50 + weight59;
                direction59 = direction50;
            }

            if (distance66 + weight59 < distance59) {
                distance59 = distance66 + weight59;
                direction59 = direction66;
            }

            if (distance65 + weight59 < distance59) {
                distance59 = distance65 + weight59;
                direction59 = direction65;
            }

            if (distance58 + weight59 < distance59) {
                distance59 = distance58 + weight59;
                direction59 = direction58;
            }

        }

        if (canVisit60) {
            if (distance65 + weight60 < distance60) {
                distance60 = distance65 + weight60;
                direction60 = direction65;
            }

            if (distance67 + weight60 < distance60) {
                distance60 = distance67 + weight60;
                direction60 = direction67;
            }

            if (distance66 + weight60 < distance60) {
                distance60 = distance66 + weight60;
                direction60 = direction66;
            }

            if (distance59 + weight60 < distance60) {
                distance60 = distance59 + weight60;
                direction60 = direction59;
            }

        }

        if (canVisit61) {
            if (distance66 + weight61 < distance61) {
                distance61 = distance66 + weight61;
                direction61 = direction66;
            }

            if (distance68 + weight61 < distance61) {
                distance61 = distance68 + weight61;
                direction61 = direction68;
            }

            if (distance67 + weight61 < distance61) {
                distance61 = distance67 + weight61;
                direction61 = direction67;
            }

            if (distance60 + weight61 < distance61) {
                distance61 = distance60 + weight61;
                direction61 = direction60;
            }

            if (distance62 + weight61 < distance61) {
                distance61 = distance62 + weight61;
                direction61 = direction62;
            }

        }

        if (canVisit62) {
            if (distance67 + weight62 < distance62) {
                distance62 = distance67 + weight62;
                direction62 = direction67;
            }

            if (distance69 + weight62 < distance62) {
                distance62 = distance69 + weight62;
                direction62 = direction69;
            }

            if (distance68 + weight62 < distance62) {
                distance62 = distance68 + weight62;
                direction62 = direction68;
            }

            if (distance63 + weight62 < distance62) {
                distance62 = distance63 + weight62;
                direction62 = direction63;
            }

        }

        if (canVisit63) {
            if (distance56 + weight63 < distance63) {
                distance63 = distance56 + weight63;
                direction63 = direction56;
            }

            if (distance68 + weight63 < distance63) {
                distance63 = distance68 + weight63;
                direction63 = direction68;
            }

            if (distance69 + weight63 < distance63) {
                distance63 = distance69 + weight63;
                direction63 = direction69;
            }

            if (distance64 + weight63 < distance63) {
                distance63 = distance64 + weight63;
                direction63 = direction64;
            }

        }

        if (canVisit64) {
            if (distance57 + weight64 < distance64) {
                distance64 = distance57 + weight64;
                direction64 = direction57;
            }

            if (distance69 + weight64 < distance64) {
                distance64 = distance69 + weight64;
                direction64 = direction69;
            }

        }

        if (canVisit66) {
            if (distance65 + weight66 < distance66) {
                distance66 = distance65 + weight66;
                direction66 = direction65;
            }

        }

        if (canVisit67) {
            if (distance66 + weight67 < distance67) {
                distance67 = distance66 + weight67;
                direction67 = direction66;
            }

            if (distance68 + weight67 < distance67) {
                distance67 = distance68 + weight67;
                direction67 = direction68;
            }

        }

        if (canVisit68) {
            if (distance69 + weight68 < distance68) {
                distance68 = distance69 + weight68;
                direction68 = direction69;
            }

        }

        if (canVisit1) {
            if (distance6 + weight1 < distance1) {
                distance1 = distance6 + weight1;
                direction1 = direction6;
            }

            if (distance8 + weight1 < distance1) {
                distance1 = distance8 + weight1;
                direction1 = direction8;
            }

            if (distance7 + weight1 < distance1) {
                distance1 = distance7 + weight1;
                direction1 = direction7;
            }

            if (distance2 + weight1 < distance1) {
                distance1 = distance2 + weight1;
                direction1 = direction2;
            }

        }

        if (canVisit2) {
            if (distance7 + weight2 < distance2) {
                distance2 = distance7 + weight2;
                direction2 = direction7;
            }

            if (distance9 + weight2 < distance2) {
                distance2 = distance9 + weight2;
                direction2 = direction9;
            }

            if (distance8 + weight2 < distance2) {
                distance2 = distance8 + weight2;
                direction2 = direction8;
            }

            if (distance3 + weight2 < distance2) {
                distance2 = distance3 + weight2;
                direction2 = direction3;
            }

        }

        if (canVisit3) {
            if (distance8 + weight3 < distance3) {
                distance3 = distance8 + weight3;
                direction3 = direction8;
            }

            if (distance10 + weight3 < distance3) {
                distance3 = distance10 + weight3;
                direction3 = direction10;
            }

            if (distance9 + weight3 < distance3) {
                distance3 = distance9 + weight3;
                direction3 = direction9;
            }

        }

        if (canVisit4) {
            if (distance9 + weight4 < distance4) {
                distance4 = distance9 + weight4;
                direction4 = direction9;
            }

            if (distance11 + weight4 < distance4) {
                distance4 = distance11 + weight4;
                direction4 = direction11;
            }

            if (distance10 + weight4 < distance4) {
                distance4 = distance10 + weight4;
                direction4 = direction10;
            }

            if (distance3 + weight4 < distance4) {
                distance4 = distance3 + weight4;
                direction4 = direction3;
            }

        }

        if (canVisit5) {
            if (distance10 + weight5 < distance5) {
                distance5 = distance10 + weight5;
                direction5 = direction10;
            }

            if (distance12 + weight5 < distance5) {
                distance5 = distance12 + weight5;
                direction5 = direction12;
            }

            if (distance11 + weight5 < distance5) {
                distance5 = distance11 + weight5;
                direction5 = direction11;
            }

            if (distance4 + weight5 < distance5) {
                distance5 = distance4 + weight5;
                direction5 = direction4;
            }

        }

        if (canVisit6) {
            if (distance15 + weight6 < distance6) {
                distance6 = distance15 + weight6;
                direction6 = direction15;
            }

            if (distance14 + weight6 < distance6) {
                distance6 = distance14 + weight6;
                direction6 = direction14;
            }

            if (distance7 + weight6 < distance6) {
                distance6 = distance7 + weight6;
                direction6 = direction7;
            }

        }

        if (canVisit7) {
            if (distance16 + weight7 < distance7) {
                distance7 = distance16 + weight7;
                direction7 = direction16;
            }

            if (distance15 + weight7 < distance7) {
                distance7 = distance15 + weight7;
                direction7 = direction15;
            }

            if (distance8 + weight7 < distance7) {
                distance7 = distance8 + weight7;
                direction7 = direction8;
            }

        }

        if (canVisit8) {
            if (distance15 + weight8 < distance8) {
                distance8 = distance15 + weight8;
                direction8 = direction15;
            }

            if (distance17 + weight8 < distance8) {
                distance8 = distance17 + weight8;
                direction8 = direction17;
            }

            if (distance16 + weight8 < distance8) {
                distance8 = distance16 + weight8;
                direction8 = direction16;
            }

            if (distance9 + weight8 < distance8) {
                distance8 = distance9 + weight8;
                direction8 = direction9;
            }

        }

        if (canVisit9) {
            if (distance16 + weight9 < distance9) {
                distance9 = distance16 + weight9;
                direction9 = direction16;
            }

            if (distance18 + weight9 < distance9) {
                distance9 = distance18 + weight9;
                direction9 = direction18;
            }

            if (distance17 + weight9 < distance9) {
                distance9 = distance17 + weight9;
                direction9 = direction17;
            }

        }

        if (canVisit10) {
            if (distance17 + weight10 < distance10) {
                distance10 = distance17 + weight10;
                direction10 = direction17;
            }

            if (distance19 + weight10 < distance10) {
                distance10 = distance19 + weight10;
                direction10 = direction19;
            }

            if (distance18 + weight10 < distance10) {
                distance10 = distance18 + weight10;
                direction10 = direction18;
            }

            if (distance9 + weight10 < distance10) {
                distance10 = distance9 + weight10;
                direction10 = direction9;
            }

        }

        if (canVisit11) {
            if (distance18 + weight11 < distance11) {
                distance11 = distance18 + weight11;
                direction11 = direction18;
            }

            if (distance19 + weight11 < distance11) {
                distance11 = distance19 + weight11;
                direction11 = direction19;
            }

            if (distance10 + weight11 < distance11) {
                distance11 = distance10 + weight11;
                direction11 = direction10;
            }

        }

        if (canVisit12) {
            if (distance19 + weight12 < distance12) {
                distance12 = distance19 + weight12;
                direction12 = direction19;
            }

            if (distance20 + weight12 < distance12) {
                distance12 = distance20 + weight12;
                direction12 = direction20;
            }

            if (distance11 + weight12 < distance12) {
                distance12 = distance11 + weight12;
                direction12 = direction11;
            }

        }

        if (canVisit13) {
            if (distance6 + weight13 < distance13) {
                distance13 = distance6 + weight13;
                direction13 = direction6;
            }

            if (distance23 + weight13 < distance13) {
                distance13 = distance23 + weight13;
                direction13 = direction23;
            }

            if (distance22 + weight13 < distance13) {
                distance13 = distance22 + weight13;
                direction13 = direction22;
            }

            if (distance14 + weight13 < distance13) {
                distance13 = distance14 + weight13;
                direction13 = direction14;
            }

        }

        if (canVisit14) {
            if (distance24 + weight14 < distance14) {
                distance14 = distance24 + weight14;
                direction14 = direction24;
            }

            if (distance23 + weight14 < distance14) {
                distance14 = distance23 + weight14;
                direction14 = direction23;
            }

            if (distance15 + weight14 < distance14) {
                distance14 = distance15 + weight14;
                direction14 = direction15;
            }

        }

        if (canVisit15) {
            if (distance25 + weight15 < distance15) {
                distance15 = distance25 + weight15;
                direction15 = direction25;
            }

            if (distance24 + weight15 < distance15) {
                distance15 = distance24 + weight15;
                direction15 = direction24;
            }

            if (distance16 + weight15 < distance15) {
                distance15 = distance16 + weight15;
                direction15 = direction16;
            }

        }

        if (canVisit16) {
            if (distance26 + weight16 < distance16) {
                distance16 = distance26 + weight16;
                direction16 = direction26;
            }

            if (distance25 + weight16 < distance16) {
                distance16 = distance25 + weight16;
                direction16 = direction25;
            }

            if (distance17 + weight16 < distance16) {
                distance16 = distance17 + weight16;
                direction16 = direction17;
            }

        }

        if (canVisit17) {
            if (distance25 + weight17 < distance17) {
                distance17 = distance25 + weight17;
                direction17 = direction25;
            }

            if (distance27 + weight17 < distance17) {
                distance17 = distance27 + weight17;
                direction17 = direction27;
            }

            if (distance26 + weight17 < distance17) {
                distance17 = distance26 + weight17;
                direction17 = direction26;
            }

        }

        if (canVisit18) {
            if (distance26 + weight18 < distance18) {
                distance18 = distance26 + weight18;
                direction18 = direction26;
            }

            if (distance27 + weight18 < distance18) {
                distance18 = distance27 + weight18;
                direction18 = direction27;
            }

            if (distance17 + weight18 < distance18) {
                distance18 = distance17 + weight18;
                direction18 = direction17;
            }

        }

        if (canVisit19) {
            if (distance27 + weight19 < distance19) {
                distance19 = distance27 + weight19;
                direction19 = direction27;
            }

            if (distance28 + weight19 < distance19) {
                distance19 = distance28 + weight19;
                direction19 = direction28;
            }

            if (distance18 + weight19 < distance19) {
                distance19 = distance18 + weight19;
                direction19 = direction18;
            }

        }

        if (canVisit20) {
            if (distance28 + weight20 < distance20) {
                distance20 = distance28 + weight20;
                direction20 = direction28;
            }

            if (distance29 + weight20 < distance20) {
                distance20 = distance29 + weight20;
                direction20 = direction29;
            }

            if (distance19 + weight20 < distance20) {
                distance20 = distance19 + weight20;
                direction20 = direction19;
            }

        }

        if (canVisit21) {
            if (distance12 + weight21 < distance21) {
                distance21 = distance12 + weight21;
                direction21 = direction12;
            }

            if (distance29 + weight21 < distance21) {
                distance21 = distance29 + weight21;
                direction21 = direction29;
            }

            if (distance30 + weight21 < distance21) {
                distance21 = distance30 + weight21;
                direction21 = direction30;
            }

            if (distance20 + weight21 < distance21) {
                distance21 = distance20 + weight21;
                direction21 = direction20;
            }

        }

        if (canVisit22) {
            if (distance14 + weight22 < distance22) {
                distance22 = distance14 + weight22;
                direction22 = direction14;
            }

            if (distance32 + weight22 < distance22) {
                distance22 = distance32 + weight22;
                direction22 = direction32;
            }

            if (distance31 + weight22 < distance22) {
                distance22 = distance31 + weight22;
                direction22 = direction31;
            }

            if (distance23 + weight22 < distance22) {
                distance22 = distance23 + weight22;
                direction22 = direction23;
            }

        }

        if (canVisit23) {
            if (distance15 + weight23 < distance23) {
                distance23 = distance15 + weight23;
                direction23 = direction15;
            }

            if (distance33 + weight23 < distance23) {
                distance23 = distance33 + weight23;
                direction23 = direction33;
            }

            if (distance32 + weight23 < distance23) {
                distance23 = distance32 + weight23;
                direction23 = direction32;
            }

            if (distance24 + weight23 < distance23) {
                distance23 = distance24 + weight23;
                direction23 = direction24;
            }

        }

        if (canVisit24) {
            if (distance34 + weight24 < distance24) {
                distance24 = distance34 + weight24;
                direction24 = direction34;
            }

            if (distance33 + weight24 < distance24) {
                distance24 = distance33 + weight24;
                direction24 = direction33;
            }

            if (distance25 + weight24 < distance24) {
                distance24 = distance25 + weight24;
                direction24 = direction25;
            }

        }

        if (canVisit28) {
            if (distance36 + weight28 < distance28) {
                distance28 = distance36 + weight28;
                direction28 = direction36;
            }

            if (distance37 + weight28 < distance28) {
                distance28 = distance37 + weight28;
                direction28 = direction37;
            }

            if (distance27 + weight28 < distance28) {
                distance28 = distance27 + weight28;
                direction28 = direction27;
            }

        }

        if (canVisit29) {
            if (distance19 + weight29 < distance29) {
                distance29 = distance19 + weight29;
                direction29 = direction19;
            }

            if (distance37 + weight29 < distance29) {
                distance29 = distance37 + weight29;
                direction29 = direction37;
            }

            if (distance38 + weight29 < distance29) {
                distance29 = distance38 + weight29;
                direction29 = direction38;
            }

            if (distance28 + weight29 < distance29) {
                distance29 = distance28 + weight29;
                direction29 = direction28;
            }

        }

        if (canVisit30) {
            if (distance20 + weight30 < distance30) {
                distance30 = distance20 + weight30;
                direction30 = direction20;
            }

            if (distance38 + weight30 < distance30) {
                distance30 = distance38 + weight30;
                direction30 = direction38;
            }

            if (distance39 + weight30 < distance30) {
                distance30 = distance39 + weight30;
                direction30 = direction39;
            }

            if (distance29 + weight30 < distance30) {
                distance30 = distance29 + weight30;
                direction30 = direction29;
            }

        }

        if (canVisit31) {
            if (distance23 + weight31 < distance31) {
                distance31 = distance23 + weight31;
                direction31 = direction23;
            }

            if (distance41 + weight31 < distance31) {
                distance31 = distance41 + weight31;
                direction31 = direction41;
            }

            if (distance32 + weight31 < distance31) {
                distance31 = distance32 + weight31;
                direction31 = direction32;
            }

        }

        if (canVisit32) {
            if (distance24 + weight32 < distance32) {
                distance32 = distance24 + weight32;
                direction32 = direction24;
            }

            if (distance42 + weight32 < distance32) {
                distance32 = distance42 + weight32;
                direction32 = direction42;
            }

            if (distance33 + weight32 < distance32) {
                distance32 = distance33 + weight32;
                direction32 = direction33;
            }

        }

        if (canVisit33) {
            if (distance25 + weight33 < distance33) {
                distance33 = distance25 + weight33;
                direction33 = direction25;
            }

            if (distance43 + weight33 < distance33) {
                distance33 = distance43 + weight33;
                direction33 = direction43;
            }

            if (distance34 + weight33 < distance33) {
                distance33 = distance34 + weight33;
                direction33 = direction34;
            }

        }

        if (canVisit37) {
            if (distance27 + weight37 < distance37) {
                distance37 = distance27 + weight37;
                direction37 = direction27;
            }

            if (distance45 + weight37 < distance37) {
                distance37 = distance45 + weight37;
                direction37 = direction45;
            }

            if (distance36 + weight37 < distance37) {
                distance37 = distance36 + weight37;
                direction37 = direction36;
            }

        }

        if (canVisit38) {
            if (distance28 + weight38 < distance38) {
                distance38 = distance28 + weight38;
                direction38 = direction28;
            }

            if (distance46 + weight38 < distance38) {
                distance38 = distance46 + weight38;
                direction38 = direction46;
            }

            if (distance37 + weight38 < distance38) {
                distance38 = distance37 + weight38;
                direction38 = direction37;
            }

        }

        if (canVisit39) {
            if (distance29 + weight39 < distance39) {
                distance39 = distance29 + weight39;
                direction39 = direction29;
            }

            if (distance47 + weight39 < distance39) {
                distance39 = distance47 + weight39;
                direction39 = direction47;
            }

            if (distance38 + weight39 < distance39) {
                distance39 = distance38 + weight39;
                direction39 = direction38;
            }

        }

        if (canVisit40) {
            if (distance32 + weight40 < distance40) {
                distance40 = distance32 + weight40;
                direction40 = direction32;
            }

            if (distance31 + weight40 < distance40) {
                distance40 = distance31 + weight40;
                direction40 = direction31;
            }

            if (distance50 + weight40 < distance40) {
                distance40 = distance50 + weight40;
                direction40 = direction50;
            }

            if (distance41 + weight40 < distance40) {
                distance40 = distance41 + weight40;
                direction40 = direction41;
            }

        }

        if (canVisit41) {
            if (distance33 + weight41 < distance41) {
                distance41 = distance33 + weight41;
                direction41 = direction33;
            }

            if (distance32 + weight41 < distance41) {
                distance41 = distance32 + weight41;
                direction41 = direction32;
            }

            if (distance51 + weight41 < distance41) {
                distance41 = distance51 + weight41;
                direction41 = direction51;
            }

            if (distance42 + weight41 < distance41) {
                distance41 = distance42 + weight41;
                direction41 = direction42;
            }

        }

        if (canVisit42) {
            if (distance34 + weight42 < distance42) {
                distance42 = distance34 + weight42;
                direction42 = direction34;
            }

            if (distance33 + weight42 < distance42) {
                distance42 = distance33 + weight42;
                direction42 = direction33;
            }

            if (distance43 + weight42 < distance42) {
                distance42 = distance43 + weight42;
                direction42 = direction43;
            }

        }

        if (canVisit46) {
            if (distance36 + weight46 < distance46) {
                distance46 = distance36 + weight46;
                direction46 = direction36;
            }

            if (distance37 + weight46 < distance46) {
                distance46 = distance37 + weight46;
                direction46 = direction37;
            }

            if (distance45 + weight46 < distance46) {
                distance46 = distance45 + weight46;
                direction46 = direction45;
            }

        }

        if (canVisit47) {
            if (distance37 + weight47 < distance47) {
                distance47 = distance37 + weight47;
                direction47 = direction37;
            }

            if (distance38 + weight47 < distance47) {
                distance47 = distance38 + weight47;
                direction47 = direction38;
            }

            if (distance55 + weight47 < distance47) {
                distance47 = distance55 + weight47;
                direction47 = direction55;
            }

            if (distance46 + weight47 < distance47) {
                distance47 = distance46 + weight47;
                direction47 = direction46;
            }

        }

        if (canVisit48) {
            if (distance38 + weight48 < distance48) {
                distance48 = distance38 + weight48;
                direction48 = direction38;
            }

            if (distance39 + weight48 < distance48) {
                distance48 = distance39 + weight48;
                direction48 = direction39;
            }

            if (distance56 + weight48 < distance48) {
                distance48 = distance56 + weight48;
                direction48 = direction56;
            }

            if (distance47 + weight48 < distance48) {
                distance48 = distance47 + weight48;
                direction48 = direction47;
            }

        }

        if (canVisit49) {
            if (distance41 + weight49 < distance49) {
                distance49 = distance41 + weight49;
                direction49 = direction41;
            }

            if (distance40 + weight49 < distance49) {
                distance49 = distance40 + weight49;
                direction49 = direction40;
            }

            if (distance58 + weight49 < distance49) {
                distance49 = distance58 + weight49;
                direction49 = direction58;
            }

            if (distance50 + weight49 < distance49) {
                distance49 = distance50 + weight49;
                direction49 = direction50;
            }

        }

        if (canVisit50) {
            if (distance42 + weight50 < distance50) {
                distance50 = distance42 + weight50;
                direction50 = direction42;
            }

            if (distance41 + weight50 < distance50) {
                distance50 = distance41 + weight50;
                direction50 = direction41;
            }

            if (distance51 + weight50 < distance50) {
                distance50 = distance51 + weight50;
                direction50 = direction51;
            }

        }

        if (canVisit51) {
            if (distance43 + weight51 < distance51) {
                distance51 = distance43 + weight51;
                direction51 = direction43;
            }

            if (distance42 + weight51 < distance51) {
                distance51 = distance42 + weight51;
                direction51 = direction42;
            }

            if (distance52 + weight51 < distance51) {
                distance51 = distance52 + weight51;
                direction51 = direction52;
            }

        }

        if (canVisit52) {
            if (distance44 + weight52 < distance52) {
                distance52 = distance44 + weight52;
                direction52 = direction44;
            }

            if (distance43 + weight52 < distance52) {
                distance52 = distance43 + weight52;
                direction52 = direction43;
            }

            if (distance53 + weight52 < distance52) {
                distance52 = distance53 + weight52;
                direction52 = direction53;
            }

        }

        if (canVisit53) {
            if (distance43 + weight53 < distance53) {
                distance53 = distance43 + weight53;
                direction53 = direction43;
            }

            if (distance45 + weight53 < distance53) {
                distance53 = distance45 + weight53;
                direction53 = direction45;
            }

            if (distance44 + weight53 < distance53) {
                distance53 = distance44 + weight53;
                direction53 = direction44;
            }

        }

        if (canVisit54) {
            if (distance44 + weight54 < distance54) {
                distance54 = distance44 + weight54;
                direction54 = direction44;
            }

            if (distance45 + weight54 < distance54) {
                distance54 = distance45 + weight54;
                direction54 = direction45;
            }

            if (distance53 + weight54 < distance54) {
                distance54 = distance53 + weight54;
                direction54 = direction53;
            }

        }

        if (canVisit55) {
            if (distance45 + weight55 < distance55) {
                distance55 = distance45 + weight55;
                direction55 = direction45;
            }

            if (distance46 + weight55 < distance55) {
                distance55 = distance46 + weight55;
                direction55 = direction46;
            }

            if (distance54 + weight55 < distance55) {
                distance55 = distance54 + weight55;
                direction55 = direction54;
            }

        }

        if (canVisit56) {
            if (distance46 + weight56 < distance56) {
                distance56 = distance46 + weight56;
                direction56 = direction46;
            }

            if (distance47 + weight56 < distance56) {
                distance56 = distance47 + weight56;
                direction56 = direction47;
            }

            if (distance55 + weight56 < distance56) {
                distance56 = distance55 + weight56;
                direction56 = direction55;
            }

        }

        if (canVisit57) {
            if (distance47 + weight57 < distance57) {
                distance57 = distance47 + weight57;
                direction57 = direction47;
            }

            if (distance48 + weight57 < distance57) {
                distance57 = distance48 + weight57;
                direction57 = direction48;
            }

            if (distance64 + weight57 < distance57) {
                distance57 = distance64 + weight57;
                direction57 = direction64;
            }

            if (distance56 + weight57 < distance57) {
                distance57 = distance56 + weight57;
                direction57 = direction56;
            }

        }

        if (canVisit58) {
            if (distance51 + weight58 < distance58) {
                distance58 = distance51 + weight58;
                direction58 = direction51;
            }

            if (distance50 + weight58 < distance58) {
                distance58 = distance50 + weight58;
                direction58 = direction50;
            }

            if (distance59 + weight58 < distance58) {
                distance58 = distance59 + weight58;
                direction58 = direction59;
            }

        }

        if (canVisit59) {
            if (distance52 + weight59 < distance59) {
                distance59 = distance52 + weight59;
                direction59 = direction52;
            }

            if (distance51 + weight59 < distance59) {
                distance59 = distance51 + weight59;
                direction59 = direction51;
            }

            if (distance60 + weight59 < distance59) {
                distance59 = distance60 + weight59;
                direction59 = direction60;
            }

        }

        if (canVisit60) {
            if (distance51 + weight60 < distance60) {
                distance60 = distance51 + weight60;
                direction60 = direction51;
            }

            if (distance53 + weight60 < distance60) {
                distance60 = distance53 + weight60;
                direction60 = direction53;
            }

            if (distance52 + weight60 < distance60) {
                distance60 = distance52 + weight60;
                direction60 = direction52;
            }

            if (distance61 + weight60 < distance60) {
                distance60 = distance61 + weight60;
                direction60 = direction61;
            }

        }

        if (canVisit61) {
            if (distance52 + weight61 < distance61) {
                distance61 = distance52 + weight61;
                direction61 = direction52;
            }

            if (distance54 + weight61 < distance61) {
                distance61 = distance54 + weight61;
                direction61 = direction54;
            }

            if (distance53 + weight61 < distance61) {
                distance61 = distance53 + weight61;
                direction61 = direction53;
            }

        }

        if (canVisit62) {
            if (distance53 + weight62 < distance62) {
                distance62 = distance53 + weight62;
                direction62 = direction53;
            }

            if (distance55 + weight62 < distance62) {
                distance62 = distance55 + weight62;
                direction62 = direction55;
            }

            if (distance54 + weight62 < distance62) {
                distance62 = distance54 + weight62;
                direction62 = direction54;
            }

            if (distance61 + weight62 < distance62) {
                distance62 = distance61 + weight62;
                direction62 = direction61;
            }

        }

        if (canVisit63) {
            if (distance54 + weight63 < distance63) {
                distance63 = distance54 + weight63;
                direction63 = direction54;
            }

            if (distance55 + weight63 < distance63) {
                distance63 = distance55 + weight63;
                direction63 = direction55;
            }

            if (distance62 + weight63 < distance63) {
                distance63 = distance62 + weight63;
                direction63 = direction62;
            }

        }

        if (canVisit64) {
            if (distance55 + weight64 < distance64) {
                distance64 = distance55 + weight64;
                direction64 = direction55;
            }

            if (distance56 + weight64 < distance64) {
                distance64 = distance56 + weight64;
                direction64 = direction56;
            }

            if (distance63 + weight64 < distance64) {
                distance64 = distance63 + weight64;
                direction64 = direction63;
            }

        }

        if (canVisit65) {
            if (distance58 + weight65 < distance65) {
                distance65 = distance58 + weight65;
                direction65 = direction58;
            }

            if (distance60 + weight65 < distance65) {
                distance65 = distance60 + weight65;
                direction65 = direction60;
            }

            if (distance59 + weight65 < distance65) {
                distance65 = distance59 + weight65;
                direction65 = direction59;
            }

            if (distance66 + weight65 < distance65) {
                distance65 = distance66 + weight65;
                direction65 = direction66;
            }

        }

        if (canVisit66) {
            if (distance59 + weight66 < distance66) {
                distance66 = distance59 + weight66;
                direction66 = direction59;
            }

            if (distance61 + weight66 < distance66) {
                distance66 = distance61 + weight66;
                direction66 = direction61;
            }

            if (distance60 + weight66 < distance66) {
                distance66 = distance60 + weight66;
                direction66 = direction60;
            }

            if (distance67 + weight66 < distance66) {
                distance66 = distance67 + weight66;
                direction66 = direction67;
            }

        }

        if (canVisit67) {
            if (distance60 + weight67 < distance67) {
                distance67 = distance60 + weight67;
                direction67 = direction60;
            }

            if (distance62 + weight67 < distance67) {
                distance67 = distance62 + weight67;
                direction67 = direction62;
            }

            if (distance61 + weight67 < distance67) {
                distance67 = distance61 + weight67;
                direction67 = direction61;
            }

        }

        if (canVisit68) {
            if (distance61 + weight68 < distance68) {
                distance68 = distance61 + weight68;
                direction68 = direction61;
            }

            if (distance63 + weight68 < distance68) {
                distance68 = distance63 + weight68;
                direction68 = direction63;
            }

            if (distance62 + weight68 < distance68) {
                distance68 = distance62 + weight68;
                direction68 = direction62;
            }

            if (distance67 + weight68 < distance68) {
                distance68 = distance67 + weight68;
                direction68 = direction67;
            }

        }

        if (canVisit69) {
            if (distance62 + weight69 < distance69) {
                distance69 = distance62 + weight69;
                direction69 = direction62;
            }

            if (distance64 + weight69 < distance69) {
                distance69 = distance64 + weight69;
                direction69 = direction64;
            }

            if (distance63 + weight69 < distance69) {
                distance69 = distance63 + weight69;
                direction69 = direction63;
            }

            if (distance68 + weight69 < distance69) {
                distance69 = distance68 + weight69;
                direction69 = direction68;
            }

        }

        Direction bestDirection = null;
        double maxScore = 0;
        int currentDistance = rc.getLocation().distanceSquaredTo(target);

        double score1 = (double) (currentDistance - location1.distanceSquaredTo(target)) / (double) distance1;
        if (score1 > maxScore) {
            bestDirection = direction1;
            maxScore = score1;
        }

        double score2 = (double) (currentDistance - location2.distanceSquaredTo(target)) / (double) distance2;
        if (score2 > maxScore) {
            bestDirection = direction2;
            maxScore = score2;
        }

        double score3 = (double) (currentDistance - location3.distanceSquaredTo(target)) / (double) distance3;
        if (score3 > maxScore) {
            bestDirection = direction3;
            maxScore = score3;
        }

        double score4 = (double) (currentDistance - location4.distanceSquaredTo(target)) / (double) distance4;
        if (score4 > maxScore) {
            bestDirection = direction4;
            maxScore = score4;
        }

        double score5 = (double) (currentDistance - location5.distanceSquaredTo(target)) / (double) distance5;
        if (score5 > maxScore) {
            bestDirection = direction5;
            maxScore = score5;
        }

        double score6 = (double) (currentDistance - location6.distanceSquaredTo(target)) / (double) distance6;
        if (score6 > maxScore) {
            bestDirection = direction6;
            maxScore = score6;
        }

        double score7 = (double) (currentDistance - location7.distanceSquaredTo(target)) / (double) distance7;
        if (score7 > maxScore) {
            bestDirection = direction7;
            maxScore = score7;
        }

        double score11 = (double) (currentDistance - location11.distanceSquaredTo(target)) / (double) distance11;
        if (score11 > maxScore) {
            bestDirection = direction11;
            maxScore = score11;
        }

        double score12 = (double) (currentDistance - location12.distanceSquaredTo(target)) / (double) distance12;
        if (score12 > maxScore) {
            bestDirection = direction12;
            maxScore = score12;
        }

        double score13 = (double) (currentDistance - location13.distanceSquaredTo(target)) / (double) distance13;
        if (score13 > maxScore) {
            bestDirection = direction13;
            maxScore = score13;
        }

        double score14 = (double) (currentDistance - location14.distanceSquaredTo(target)) / (double) distance14;
        if (score14 > maxScore) {
            bestDirection = direction14;
            maxScore = score14;
        }

        double score20 = (double) (currentDistance - location20.distanceSquaredTo(target)) / (double) distance20;
        if (score20 > maxScore) {
            bestDirection = direction20;
            maxScore = score20;
        }

        double score21 = (double) (currentDistance - location21.distanceSquaredTo(target)) / (double) distance21;
        if (score21 > maxScore) {
            bestDirection = direction21;
            maxScore = score21;
        }

        double score22 = (double) (currentDistance - location22.distanceSquaredTo(target)) / (double) distance22;
        if (score22 > maxScore) {
            bestDirection = direction22;
            maxScore = score22;
        }

        double score30 = (double) (currentDistance - location30.distanceSquaredTo(target)) / (double) distance30;
        if (score30 > maxScore) {
            bestDirection = direction30;
            maxScore = score30;
        }

        double score31 = (double) (currentDistance - location31.distanceSquaredTo(target)) / (double) distance31;
        if (score31 > maxScore) {
            bestDirection = direction31;
            maxScore = score31;
        }

        double score39 = (double) (currentDistance - location39.distanceSquaredTo(target)) / (double) distance39;
        if (score39 > maxScore) {
            bestDirection = direction39;
            maxScore = score39;
        }

        double score40 = (double) (currentDistance - location40.distanceSquaredTo(target)) / (double) distance40;
        if (score40 > maxScore) {
            bestDirection = direction40;
            maxScore = score40;
        }

        double score48 = (double) (currentDistance - location48.distanceSquaredTo(target)) / (double) distance48;
        if (score48 > maxScore) {
            bestDirection = direction48;
            maxScore = score48;
        }

        double score49 = (double) (currentDistance - location49.distanceSquaredTo(target)) / (double) distance49;
        if (score49 > maxScore) {
            bestDirection = direction49;
            maxScore = score49;
        }

        double score50 = (double) (currentDistance - location50.distanceSquaredTo(target)) / (double) distance50;
        if (score50 > maxScore) {
            bestDirection = direction50;
            maxScore = score50;
        }

        double score56 = (double) (currentDistance - location56.distanceSquaredTo(target)) / (double) distance56;
        if (score56 > maxScore) {
            bestDirection = direction56;
            maxScore = score56;
        }

        double score57 = (double) (currentDistance - location57.distanceSquaredTo(target)) / (double) distance57;
        if (score57 > maxScore) {
            bestDirection = direction57;
            maxScore = score57;
        }

        double score58 = (double) (currentDistance - location58.distanceSquaredTo(target)) / (double) distance58;
        if (score58 > maxScore) {
            bestDirection = direction58;
            maxScore = score58;
        }

        double score59 = (double) (currentDistance - location59.distanceSquaredTo(target)) / (double) distance59;
        if (score59 > maxScore) {
            bestDirection = direction59;
            maxScore = score59;
        }

        double score63 = (double) (currentDistance - location63.distanceSquaredTo(target)) / (double) distance63;
        if (score63 > maxScore) {
            bestDirection = direction63;
            maxScore = score63;
        }

        double score64 = (double) (currentDistance - location64.distanceSquaredTo(target)) / (double) distance64;
        if (score64 > maxScore) {
            bestDirection = direction64;
            maxScore = score64;
        }

        double score65 = (double) (currentDistance - location65.distanceSquaredTo(target)) / (double) distance65;
        if (score65 > maxScore) {
            bestDirection = direction65;
            maxScore = score65;
        }

        double score66 = (double) (currentDistance - location66.distanceSquaredTo(target)) / (double) distance66;
        if (score66 > maxScore) {
            bestDirection = direction66;
            maxScore = score66;
        }

        double score67 = (double) (currentDistance - location67.distanceSquaredTo(target)) / (double) distance67;
        if (score67 > maxScore) {
            bestDirection = direction67;
            maxScore = score67;
        }

        double score68 = (double) (currentDistance - location68.distanceSquaredTo(target)) / (double) distance68;
        if (score68 > maxScore) {
            bestDirection = direction68;
            maxScore = score68;
        }

        double score69 = (double) (currentDistance - location69.distanceSquaredTo(target)) / (double) distance69;
        if (score69 > maxScore) {
            bestDirection = direction69;
            maxScore = score69;
        }

        return bestDirection;
    }
}