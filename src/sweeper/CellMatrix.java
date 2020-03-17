package sweeper;

import java.util.ArrayList;
import java.util.List;

abstract class CellMatrix {

    private Cell[][] matrix;
    private int width, height;
    private List<Coords> allCoords;

    CellMatrix(int width, int height, Cell fillingCell) {

        this.width = width;
        this.height = height;
        matrix = new Cell[width][height];

        allCoords = new ArrayList<>(width * height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                allCoords.add(new Coords(x, y));

        fill(fillingCell);
    }

    void fill(Cell fillingCell) {
        for (Coords coords : allCoords)
            set(coords, fillingCell);
    }

    private boolean inRange(Coords coords) {
        return coords.x >= 0 && coords.x < width &&
                coords.y >= 0 && coords.y < height;
    }

    Cell get(Coords coords) {
        if (inRange(coords))
            return matrix[coords.x][coords.y];
        else
            return null;
    }

    void set(Coords coords, Cell cell) {
        if (inRange(coords))
            matrix[coords.x][coords.y] = cell;
    }

    public abstract void init();

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    List<Coords> getAllCoordsList() {
        return allCoords;
    }

    List<Coords> getCoordsListAround(Coords coords) {
        List<Coords> result = new ArrayList<>();
        Coords neighborCoords;
        for (int x = coords.x - 1; x <= coords.x + 1; x++) {
            for (int y = coords.y - 1; y <= coords.y + 1; y++) {
                neighborCoords = new Coords(x, y);
                if (inRange(neighborCoords) && !coords.equals(neighborCoords))
                    result.add(neighborCoords);
            }
        }
        return result;
    }
}
