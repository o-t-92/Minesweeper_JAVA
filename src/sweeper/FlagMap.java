package sweeper;

public class FlagMap extends CellMatrix {

    private int totalFlags;
    private int totalClosed;

    FlagMap(int width, int height) {
        super(width, height, Cell.CLOSED);
        init();
    }

    @Override
    public void init() {
        fill(Cell.CLOSED);
        totalFlags = 0;
        totalClosed = getWidth() * getHeight();
    }

    public int getTotalFlags() {
        return totalFlags;
    }

    int getTotalClosed() {
        return totalClosed;
    }

    void setCellOpened(Coords coords) {
        set(coords, Cell.OPENED);
        totalClosed--;
    }

    void setCellClosed(Coords coords) {
        set(coords, Cell.CLOSED);
        totalFlags--;
    }

    void setCellFlagged(Coords coords) {
        set(coords, Cell.FLAGGED);
        totalFlags++;
    }

    void setCellBombed(Coords coords) {
        set(coords, Cell.BOMBED);
    }

    public void setCellNoBomb(Coords coords) {
        set(coords, Cell.NOBOMB);
    }

    void toggleFlagInCell(Coords coords) {
        switch (get(coords)) {
            case CLOSED:
                setCellFlagged(coords);
                break;

            case FLAGGED:
                setCellClosed(coords);
                break;
        }
    }

    void flagLastClosedCells() {
        for (Coords coords : getAllCoordsList()) {
            if (get(coords) == Cell.CLOSED)
                setCellFlagged(coords);
        }
    }

    int countFlagsAround(Coords coords) {
        int count = 0;
        for (Coords neighborCoords : getCoordsListAround(coords))
            if (get(neighborCoords) == Cell.FLAGGED)
                count++;
        return count;
    }
}
