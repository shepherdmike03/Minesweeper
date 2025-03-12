/****************************************************************************/
/*           Author: Pasztor Miklos                                         */
/*                   Left - Right - KABOOM!                                 */
/*                          PMKM0005                                        */
/****************************************************************************/

public enum Difficulty {
    EASY("Hungary", 8, 8, 6),
    MEDIUM("Germany", 12, 12, 10),
    HARD("SwitzerLand", 16, 16, 15),
    EXTREME("Russia", 24, 24, 25),
    HOLLY_MOLLY("Ukraine", 30, 30, 70),
    HELL("Hell On Earth", 50, 50, 500);


    private final String displayName;
    private final int rows;
    private final int cols;
    private final int bombs;


    Difficulty(String displayName, int rows, int cols, int bombs) {
        this.displayName = displayName;
        this.rows = rows;
        this.cols = cols;
        this.bombs = bombs;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getBombs() {
        return bombs;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
