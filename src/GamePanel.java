/****************************************************************************/
/*           Author: Pasztor Miklos                                         */
/*                   Left - Right - KABOOM!                                 */
/*                          PMKM0005                                        */
/****************************************************************************/

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {

    private MinesweeperFrame parentFrame;
    private Board board;

    private JPanel statusPanel;
    private JLabel scoreLabel;
    private JLabel flagLabel;

    private Difficulty difficulty;

    public GamePanel(MinesweeperFrame frame, Difficulty difficulty) {
        this.parentFrame = frame;
        this.difficulty = difficulty;

        setLayout(new BorderLayout());

        statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        statusPanel.setBackground(Color.BLACK);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));

        flagLabel = new JLabel("Flags: 0/" + difficulty.getBombs());
        flagLabel.setForeground(Color.YELLOW);
        flagLabel.setFont(new Font("Arial", Font.BOLD, 18));

        statusPanel.add(scoreLabel);
        statusPanel.add(flagLabel);

        add(statusPanel, BorderLayout.NORTH);

        board = new Board(
                difficulty.getRows(),
                difficulty.getCols(),
                difficulty.getBombs(),
                parentFrame
        );
        add(board, BorderLayout.CENTER);
    }


    public void updateStatus(int score, int usedFlags) {
        scoreLabel.setText("Score: " + score);
        flagLabel.setText("Flags: " + usedFlags + "/" + difficulty.getBombs());
    }
}
