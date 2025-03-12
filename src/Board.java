import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Board extends JPanel {

    private int rows;
    private int cols;
    private int mineCount;
    private Cell[][] cells;

    private boolean gameOver = false;
    private MinesweeperFrame parentFrame;

    public Board(int rows, int cols, int mineCount, MinesweeperFrame frame) {
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        this.parentFrame = frame;

        setLayout(new GridLayout(rows, cols));

        cells = new Cell[rows][cols];
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                cells[r][c] = new Cell(r, c, this);
                add(cells[r][c]);
            }
        }

        placeMines();
        calculateNeighbors();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    /*Getter for Cell.java*/
    public int getMineCount() {
        return mineCount;
    }

    public void revealCell(int row, int col) {
        if(gameOver) return;

        Cell cell = cells[row][col];
        if(cell.isRevealed() || cell.isFlagged()) {
            return;
        }

        cell.setRevealed(true);

        if(cell.isMine()) {
            parentFrame.getSoundManager().playSoundEffect("music/explosion.wav");
            cell.revealAsBomb();
            gameOver();
        } else {
            parentFrame.getSoundManager().playSoundEffect("music/pickupCoin.wav");
            cell.revealAsSafe();
            if(cell.getNeighborMines() == 0) {
                floodFill(row, col);
            }
        }

        if(checkVictory()) {
            gameWin();
        }

        if(!gameOver) {
            updateCurrentStatus();
        }
    }

    private void floodFill(int startRow, int startCol) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startRow, startCol));

        while(!queue.isEmpty()) {
            Point p = queue.remove();
            int r = p.x;
            int c = p.y;

            for(int dr = -1; dr <= 1; dr++) {
                for(int dc = -1; dc <= 1; dc++) {
                    if(dr == 0 && dc == 0) continue;
                    int nr = r + dr;
                    int nc = c + dc;
                    if(nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                        Cell neighbor = cells[nr][nc];
                        if(!neighbor.isRevealed() && !neighbor.isMine() && !neighbor.isFlagged()) {
                            neighbor.setRevealed(true);
                            neighbor.revealAsSafe();
                            if(neighbor.getNeighborMines() == 0) {
                                queue.add(new Point(nr, nc));
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkVictory() {
        int revealedCount = 0;
        int nonMineCells = rows * cols - mineCount;
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                if(cells[r][c].isRevealed() && !cells[r][c].isMine()) {
                    revealedCount++;
                }
            }
        }
        return (revealedCount == nonMineCells);
    }

    public void gameOver() {
        gameOver = true;
        revealAllMines();
        int finalScore = computeFlagScore();
        parentFrame.gameFinished(finalScore);
    }

    public void gameWin() {
        gameOver = true;
        revealAllCells();
        int finalScore = computeFlagScore();
        parentFrame.gameFinished(finalScore);
    }

    private void revealAllMines() {
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                Cell cell = cells[r][c];
                if(cell.isMine() && !cell.isRevealed()) {
                    cell.setRevealed(true);
                    cell.revealAsBomb();
                }
            }
        }
    }

    private void revealAllCells() {
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                Cell cell = cells[r][c];
                if(!cell.isRevealed()) {
                    cell.setRevealed(true);
                    if(cell.isMine()) {
                        cell.revealAsBomb();
                    } else {
                        cell.revealAsSafe();
                    }
                }
            }
        }
    }

    private int computeFlagScore() {
        return computeCurrentScore();
    }

    public int computeCurrentScore() {
        int revealedCorrect = 0;
        int correctFlags = 0;
        int incorrectFlags = 0;

        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                Cell cell = cells[r][c];
                if(cell.isRevealed() && !cell.isMine()) {
                    revealedCorrect++;
                }
                if(cell.isFlagged()) {
                    if(cell.isMine()) {
                        correctFlags++;
                    } else {
                        incorrectFlags++;
                    }
                }
            }
        }
        return revealedCorrect + correctFlags - incorrectFlags;
    }

    public int getUsedFlags() {
        int flaggedCount = 0;
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < cols; c++) {
                if(cells[r][c].isFlagged()) {
                    flaggedCount++;
                }
            }
        }
        return flaggedCount;
    }

    public void updateCurrentStatus() {
        int currentScore = computeCurrentScore();
        int usedFlags = getUsedFlags();
        parentFrame.getGamePanel().updateStatus(currentScore, usedFlags);
    }

    private void placeMines() {
        int placed = 0;
        while(placed < mineCount) {
            int r = (int)(Math.random() * rows);
            int c = (int)(Math.random() * cols);
            if(!cells[r][c].isMine()) {
                cells[r][c].setMine(true);
                placed++;
            }
        }
    }

    private void calculateNeighbors() {
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                if(!cells[r][c].isMine()) {
                    int count = 0;
                    for(int rr = -1; rr <= 1; rr++){
                        for(int cc = -1; cc <= 1; cc++){
                            int nr = r + rr;
                            int nc = c + cc;
                            if(nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                                if(cells[nr][nc].isMine()) {
                                    count++;
                                }
                            }
                        }
                    }
                    cells[r][c].setNeighborMines(count);
                }
            }
        }
    }

    public MinesweeperFrame getParentFrame() {
        return parentFrame;
    }
}
