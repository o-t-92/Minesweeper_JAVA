package sweeper;

public enum Level {
    EASY(9, 9, 10),
    NORMAL(16, 16, 40),
    HARD(30, 16, 99);

    public final int cols;
    public final int rows;
    public final int bombs;

    Level(int cols, int rows, int bombs) {
        this.cols = cols;
        this.rows = rows;
        this.bombs = bombs;
    }
}
