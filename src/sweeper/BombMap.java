package sweeper;

import java.util.Random;

public class BombMap extends CellMatrix {

    private int totalBombs;

    BombMap(int width, int height, int totalBombs) {
        super(width, height, Cell.ZERO);
        int maxBombs = (width * height) / 5;
        this.totalBombs = totalBombs > maxBombs ? maxBombs : totalBombs;
    }

    @Override
    public void init() {

        fill(Cell.ZERO);

        Random rand = new Random();

        for (int i = 0; i < totalBombs; i++) {
            Coords coords;
            do {
                coords = new Coords(rand.nextInt(getWidth()), rand.nextInt(getHeight()));
            }
            while (get(coords) == Cell.BOMB);

            set(coords, Cell.BOMB);

            for (Coords neighborCoords : getCoordsListAround(coords))
                set(neighborCoords, get(neighborCoords).getNextNumCell());
        }
    }

    int getTotalBombs() {
        return totalBombs;
    }
}
