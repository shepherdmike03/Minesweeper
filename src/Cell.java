/****************************************************************************/
/*           Author: Pasztor Miklos                                         */
/*                   Left - Right - KABOOM!                                 */
/*                          PMKM0005                                        */
/****************************************************************************/


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cell extends JButton {

    private int row;
    private int col;
    private boolean mine;
    private int neighborMines;
    private boolean revealed;
    private boolean flagged;

    private Board board;

    private static final Color VOLCANO_RED    = new Color(178, 34, 34);
    private static final Color VOLCANO_ORANGE = new Color(255, 140, 0);
    private static final Color VOLCANO_YELLOW = new Color(255, 215, 0);
    private static final Color VOLCANO_BLACK  = new Color(0, 0, 0);

    public Cell(int row, int col, Board board) {
        this.row = row;
        this.col = col;
        this.board = board;
        this.mine = false;
        this.neighborMines = 0;
        this.revealed = false;
        this.flagged = false;

        // kissebb beosztas nagyobb tablkakhoz
        setPreferredSize(new Dimension(30, 30));

        setBackground(VOLCANO_RED);
        setText("");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(board.isGameOver()) {
                    return;
                }
                if(SwingUtilities.isLeftMouseButton(e)) {
                    board.revealCell(row, col);
                } else if(SwingUtilities.isRightMouseButton(e)) {
                    toggleFlag();
                }
            }
        });
    }

    public void toggleFlag() {
        if(!revealed) {
            if(!flagged) {
                int usedFlags = board.getUsedFlags();
                int maxFlags = board.getMineCount();
                if (usedFlags < maxFlags) {
                    flagged = true;
                    setBackground(VOLCANO_YELLOW);
                    setText("F");
                    board.getParentFrame().getSoundManager().playSoundEffect("music/laserShoot.wav");
                }
            } else {
                flagged = false;
                setBackground(VOLCANO_RED);
                setText("");
            }
            board.updateCurrentStatus();
        }
    }

    public void revealAsSafe() {
        revealed = true;
        setBackground(VOLCANO_ORANGE);
        if(neighborMines > 0) {
            setText(String.valueOf(neighborMines));
            setForeground(Color.BLACK);
        } else {
            setText("");
        }
    }

    public void revealAsBomb() {
        revealed = true;
        setBackground(VOLCANO_BLACK);
        setText("X");
        setForeground(Color.RED);
    }


    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getNeighborMines() {
        return neighborMines;
    }

    public void setNeighborMines(int neighborMines) {
        this.neighborMines = neighborMines;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isFlagged() {
        return flagged;
    }
}
