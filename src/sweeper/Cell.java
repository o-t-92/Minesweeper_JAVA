package sweeper;

public enum Cell {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,

    OPENED,
    CLOSED,
    FLAGGED,
    BOMBED,
    NOBOMB;

    public Cell getNextNumCell() {
        if (ordinal() < NUM8.ordinal())
            return values()[ordinal() + 1];
        else
            return this;
    }
}
