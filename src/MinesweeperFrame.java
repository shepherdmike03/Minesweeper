/****************************************************************************/
/*           Author: Pasztor Miklos                                         */
/*                   Left - Right - KABOOM!                                 */
/*                          PMKM0005                                        */
/****************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MinesweeperFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private HighScorePanel highScorePanel;

    private ScoreManager scoreManager;
    private SoundManager soundManager;
    private RulesPanel rulesPanel;

    private FireAshesEffect ashesEffect;

    public MinesweeperFrame() {
        super("left - right - KABOOOOM!");          // Title on the widget
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(1300, 900);
        setLocationRelativeTo(null);

        scoreManager = new ScoreManager("highscores.txt");

        soundManager = new SoundManager();

        soundManager.playBackgroundMusic("music/MainTheme.wav");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        menuPanel = new MenuPanel(this);

        gamePanel = new GamePanel(this, Difficulty.EASY);

        highScorePanel = new HighScorePanel(this);

        rulesPanel = new RulesPanel(this);


        cardPanel.add(menuPanel, "Menu");

        cardPanel.add(gamePanel, "Game");

        cardPanel.add(highScorePanel, "HighScore");

        cardPanel.add(rulesPanel, "Rules");

        getContentPane().add(cardPanel);

        ashesEffect = new FireAshesEffect();
        getLayeredPane().add(ashesEffect, 999);
        ashesEffect.setBounds(0, 0, getWidth(), getHeight());
        ashesEffect.startAnimation();


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ashesEffect.setBounds(0, 0, getWidth(), getHeight());
            }
        });


        showMenuPanel();        // Show Menu By Default!!!
    }


    public void showMenuPanel() {
        menuPanel.refreshTopScores();
        cardLayout.show(cardPanel, "Menu");
    }


    public void showGamePanel() {
        Difficulty chosen = askDifficulty();        // calls the custom dialog aka DIFFICULTY SELECT
        if (chosen == null) {
            return;
        }
        gamePanel = new GamePanel(this, chosen);
        cardPanel.add(gamePanel, "Game");
        cardLayout.show(cardPanel, "Game");
    }



    public void showHighScorePanel() {
        highScorePanel.refreshScores();
        cardLayout.show(cardPanel, "HighScore");
    }


    public void showRulesPanel() {
        cardLayout.show(cardPanel, "Rules");
    }


    public void gameFinished(int score) {
        NameDialog dialog = new NameDialog(this, score);
        dialog.setVisible(true);

        String playerName = dialog.getPlayerName();

        if (playerName != null && !playerName.trim().isEmpty()) {
            scoreManager.saveScore(playerName.trim(), score);
        }
        showMenuPanel();
    }

    private Difficulty askDifficulty() {
        DifficultyDialog dialog = new DifficultyDialog(this);
        dialog.setVisible(true);
        return dialog.getChosenDifficulty();
    }


    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }



                    /*RUN THE GAME*/
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MinesweeperFrame frame = new MinesweeperFrame();
            frame.setVisible(true);
        });
    }
}
