package com.magneticstudio.transience.game;

import com.magneticstudio.transience.ui.Game;
import com.magneticstudio.transience.util.ArrayList2D;
import com.magneticstudio.transience.util.IntPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will handle the map generation
 * portion of the game.
 *
 * @author Max
 */
public class TileSetGenerator {

    public static final int ROOM_CLUSTER_COMPLEX = 1; // Complex room cluster.
    public static final int ROOM_CLUSTER_MODERATE = 3; // A moderate cluster.
    public static final int ROOM_CLUSTER_SIMPLE = 5; // A simple room cluster.

    private List<Room> generatedRooms = new ArrayList<>(); // The list of generated rooms.
    private static final int ABSOLUTE_MIN_ROOM_WIDTH = 3; // Absolute minimum room width.
    private static final int ABSOLUTE_MIN_ROOM_HEIGHT = 3; // Absolute minimum room height.

    private int tsTransitionTime = 85; // Time it takes to complete a movement on the tile set.
    private int tsRoomClusterSize = 3; // The intricacy of the room clusters.
    private int roomMinWidth = 4; // The minimum width of a room.
    private int roomMaxWidth = 12; // The maximum width of a room.
    private int roomMinHeight = 4; // The minimum height of a room.
    private int roomMaxHeight = 12; // The maximum height of a room.

    private boolean autoPlayerSpawn = true; // Automatically spawn the player.

    /**
     * Creates a new instance of the TileSetGenerator
     * class.
     */
    public TileSetGenerator() {

    }

    /**
     * Sets the transition time of the generated tile set.
     * @param tTime The new transition time for the generated tile set.22222222222
     */
    public void setTransitionTime(int tTime) {
        tsTransitionTime = tTime;
    }

    /**
     * Sets the minimum room width.
     * @param roomMinWidth New minimum room width.
     */
    public void setRoomMinWidth(int roomMinWidth) {
        this.roomMinWidth = roomMinWidth < ABSOLUTE_MIN_ROOM_WIDTH ? ABSOLUTE_MIN_ROOM_WIDTH
                : (roomMinWidth >= roomMaxWidth ? roomMaxWidth - 1 : roomMinWidth);
    }

    /**
     * Sets the maximum room with.
     * @param roomMaxWidth New maximum room width.
     */
    public void setRoomMaxWidth(int roomMaxWidth) {
        this.roomMaxWidth = roomMaxWidth <= roomMinWidth ? roomMinWidth + 1 : roomMaxWidth;
    }

    /**
     * Sets the minimum room height.
     * @param roomMinHeight New minimum room height.
     */
    public void setRoomMinHeight(int roomMinHeight) {
        this.roomMinHeight = roomMinHeight < ABSOLUTE_MIN_ROOM_HEIGHT ? ABSOLUTE_MIN_ROOM_HEIGHT
                : (roomMinHeight >= roomMaxHeight ? roomMaxHeight - 1 : roomMinHeight);
    }

    /**
     * Sets the maximum room height.
     * @param roomMaxHeight New maximum room height.
     */
    public void setRoomMaxHeight(int roomMaxHeight) {
        this.roomMaxHeight = roomMaxHeight <= roomMinHeight ? roomMinHeight + 1 : roomMaxHeight;
    }

    /**
     * Sets the room cluster size.
     * @param roomClusterSize New room cluster size.
     */
    public void setRoomClusterSize(int roomClusterSize) {
        tsRoomClusterSize = roomClusterSize < ROOM_CLUSTER_COMPLEX ? ROOM_CLUSTER_COMPLEX : roomClusterSize;
    }

    /**
     * Sets whether the player should be automatically spawned on the tile set.
     * @param v True to spawn the player automatically; False to not.
     */
    public void setAutoPlayerSpawn(boolean v) {
        autoPlayerSpawn = v;
    }

    /**
     * Generates a new tile set.
     * @param width The width of the tile set.
     * @param height The height of the tile set.
     * @return A new tile set.
     */
    public TileSet generate(int width, int height) {
        if(width < roomMaxWidth)
            roomMaxWidth = width;

        if(height < roomMaxHeight)
            roomMaxHeight = height;

        TileSet tileSet = new TileSet(width, height);
        tileSet.getPosition().setTransitionTime(tsTransitionTime);

        final int generationAttempts = 250_000; // Number of attempts to create non-overlapping rooms.
        for(int i = 0; i < generationAttempts; i++) {
            generateRoom(tileSet);
        }

        if(generatedRooms.size() >= 2) {
            for(int i = 0; i < generatedRooms.size() - 1; i++) {
                Room toLinkWithOthers = generatedRooms.get(i);
                for(int j = i + 1; j < generatedRooms.size(); j += tsRoomClusterSize) {
                    Room next = generatedRooms.get(j);
                    linkRooms(tileSet, toLinkWithOthers, next);
                }
            }
        }

        if(autoPlayerSpawn)
            tileSet.getEntities().spawnPlayer();

        tileSet.adjustGraphicalElements();

        return tileSet;
    }

    /**
     * Attempts to generate a new room.
     * @param onWhat The tile set to generate room on.
     */
    private void generateRoom(TileSet onWhat) {
        int x = Game.rng.nextInt(onWhat.getTiles().getWidth() - roomMinWidth);
        int y = Game.rng.nextInt(onWhat.getTiles().getHeight() - roomMinHeight);
        final int maxWidth = onWhat.getTiles().getWidth() - x;
        final int maxHeight = onWhat.getTiles().getHeight() - y;

        if(maxWidth < ABSOLUTE_MIN_ROOM_WIDTH || maxHeight < ABSOLUTE_MIN_ROOM_HEIGHT)
            return;

        int width = roomMinWidth + Game.rng.nextInt(Math.min(maxWidth, roomMaxWidth - roomMinWidth));
        int height = roomMinHeight + Game.rng.nextInt(Math.min(maxHeight, roomMaxHeight - roomMinHeight));

        Room room = new Room(x, y, width, height);

        for(Room r : generatedRooms)
            if(r.doesOverlap(room))
                return;

        boolean wasSuccessful = room.embed(onWhat);
        if(wasSuccessful) {
            generatedRooms.add(room);
        }
    }

    // A predicate to use when setting elements to automatically
    // check if the element is null or the type of void.
    private static final ArrayList2D.If<Tile> wallPlacePredicate = t -> t == null || t.getTileType() == Tile.Type.VOID;
    private static final IntPoint[] pointSurroundings = {
        new IntPoint(-1, -1),
        new IntPoint( 0, -1),
        new IntPoint( 1, -1),
        new IntPoint(-1,  0),
        new IntPoint( 1,  0),
        new IntPoint(-1,  1),
        new IntPoint(-1,  1),
        new IntPoint(-1,  1),
    };

    /**
     * Links two rooms together using a hallway.
     * @param onWhat On what tile set.
     * @param first The first room.
     * @param second The second room.
     */
    private void linkRooms(TileSet onWhat, Room first, Room second) {
        IntPoint start = first.selectRandomLocationInside();
        IntPoint end = second.selectRandomLocationInside();
        final byte changeX = (byte) (start.x < end.x ? 1 : -1);
        final byte changeY = (byte) (start.y < end.y ? 1 : -1);
        ArrayList2D<Tile> tiles = onWhat.getTiles();
        for(int i = start.x + changeX; i != end.x + changeX; i += changeX) {
            tiles.setElement(Tile.createAirTile(onWhat), i, start.y);
            tiles.setElementIf(Tile.createWallTile(onWhat), wallPlacePredicate, i, start.y - 1);
            tiles.setElementIf(Tile.createWallTile(onWhat), wallPlacePredicate, i, start.y + 1);
        }
        for(int i = start.y + changeY; i != end.y + changeY; i += changeY) {
            tiles.setElement(Tile.createAirTile(onWhat), end.x, i);
            tiles.setElementIf(Tile.createWallTile(onWhat), wallPlacePredicate, end.x - 1, i);
            tiles.setElementIf(Tile.createWallTile(onWhat), wallPlacePredicate, end.x + 1, i);
        }

        final int pivotX = end.x;
        final int pivotY = start.y;
        for(IntPoint s : pointSurroundings) {
            tiles.setElementIf(Tile.createWallTile(onWhat), wallPlacePredicate, pivotX + s.x, pivotY + s.y);
        }
    }

    /**
     * This struct keeps track of a generated room.
     */
    public static /*struct*/ class Room {
        private int x;
        private int y;
        private int width;
        private int height;

        /**
         * Creates a new room object.
         * @param x X value of the position of this room.
         * @param y Y value of the position of this room.
         * @param w The width of this room.
         * @param h The height of this room.
         */
        private Room(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
        }

        /**
         * Randomly picks a location on a wall of this room.
         * @return Random location that contains this room's wall.
         */
        private IntPoint selectRandomLocationInside() {
            return new IntPoint(x + Game.rng.nextInt(width - 2) + 1, y + Game.rng.nextInt(height - 2) + 1);
        }

        /**
         * Checks whether another room overlaps this one.
         * @param another A room to check for overlapping.
         * @return Whether the two rooms overlap.
         */
        private boolean doesOverlap(Room another) {
            return !(y + height < another.y || another.y + another.height < y)
                    && !(x + width < another.x || another.x + another.width < x);
        }

        /**
         * Sets this room onto the tile set.
         * @param into The TileSet to place this room in.
         * @return Whether this room was successfully embedded into the tile set.
         */
        private boolean embed(TileSet into) {
            if(x < 0 || y < 0)
                return false;

            ArrayList2D<Tile> tiles = into.getTiles();
            if(x + width >= tiles.getWidth() || y + height >= tiles.getHeight())
                return false;

            for(int i = x; i < x + width; i++) {
                if(tiles.getElement(i, y).getTileType() != Tile.Type.AIR)
                    tiles.setElement(Tile.createWallTile(into), i, y);

                if(tiles.getElement(i, y + height - 1).getTileType() != Tile.Type.AIR)
                    tiles.setElement(Tile.createWallTile(into), i, y + height - 1);
            }

            for(int i = y + 1; i < y + height - 1; i++) {
                if(tiles.getElement(x, i).getTileType() != Tile.Type.AIR)
                    tiles.setElement(Tile.createWallTile(into), x, i);

                if(tiles.getElement(x + width - 1, i).getTileType() != Tile.Type.AIR)
                    tiles.setElement(Tile.createWallTile(into), x + width - 1, i);

                for(int j = x + 1; j < x + width - 1; j++)
                    tiles.setElement(Tile.createAirTile(into), j, i);
            }
            return true;
        }

        /**
         * Gets a string representation of this object.
         * @return String representation of this object.
         */
        @Override
        public String toString() {
            return x + ", " + y + ", " + width + ", " + height;
        }
    }
}
