package sweeper;

import java.util.List;

public class Game {

    private BombMap bombMap;
    private FlagMap flagMap;
    private GameState state;

    public Game(int cols, int rows, int bombs) {
        bombMap = new BombMap(cols, rows, bombs);
        flagMap = new FlagMap(cols, rows);
        state = GameState.INITIAL;
    }

    public void startNewGame() {
        bombMap.init();
        flagMap.init();
        state = GameState.LAUNCHED;
    }

    public GameState getState() {
        return state;
    }

    public Cell getCell(Coords coords) {
        Cell cell = flagMap.get(coords);
        return cell == Cell.OPENED ? bombMap.get(coords) : cell;
    }

    public List<Coords> getAllCoordsList() {
        return bombMap.getAllCoordsList();
    }

    public void onLeftButton(Coords coords) {
        if (state == GameState.LAUNCHED) {
            openCell(coords);
            if (flagMap.getTotalClosed() == bombMap.getTotalBombs()) {
                state = GameState.VICTORY;
                flagMap.flagLastClosedCells();
            }
        }
    }

    public void onRightButton(Coords coords) {
        if (state == GameState.LAUNCHED) {
            flagMap.toggleFlagInCell(coords);
        }
    }

    private void openCell(Coords coords) {

        switch (flagMap.get(coords)) {
            case OPENED:
                if (bombMap.get(coords).ordinal() == flagMap.countFlagsAround(coords)) {
                    for (Coords neighborCoords : flagMap.getCoordsListAround(coords))
                        if (flagMap.get(neighborCoords) == Cell.CLOSED)
                            openCell(neighborCoords);
                }
                break;

            case CLOSED:
                flagMap.setCellOpened(coords);

                switch (bombMap.get(coords)) {

                    case ZERO:
                        for (Coords neighborCoords : flagMap.getCoordsListAround(coords))
                            openCell(neighborCoords);
                        break;

                    case BOMB:
                        flagMap.setCellBombed(coords);
                        for (Coords coords1 : bombMap.getAllCoordsList()) {
                            if (bombMap.get(coords1) == Cell.BOMB) {
                                if (flagMap.get(coords1) == Cell.CLOSED)
                                    flagMap.set(coords1, Cell.OPENED);
                            }
                            else if (flagMap.get(coords1) == Cell.FLAGGED)
                                flagMap.set(coords1, Cell.NOBOMB);
                        }
                        state = GameState.FAILURE;
                        break;
                }
                break;
        }
    }
}
