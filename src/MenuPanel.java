/****************************************************************************/
/*           Author: Pasztor Miklos                                         */
/*                   Left - Right - KABOOM!                                 */
/*                          PMKM0005                                        */
/****************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuPanel extends JPanel {

    private MinesweeperFrame parentFrame;
    private JLabel scoresLabel;

    public MenuPanel(MinesweeperFrame frame) {
        this.parentFrame = frame;

        setOpaque(true);
        setLayout(new BorderLayout());

        Image leftRaw = new ImageIcon(MenuPanel.class.getResource("images/zelenski.png")).getImage();
        Image scaledLeft = leftRaw.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        ImageIcon leftIcon = new ImageIcon(scaledLeft);

        JLabel leftLabel = new JLabel(leftIcon);
        leftLabel.setOpaque(false);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.add(leftLabel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);


        Image rightRaw = new ImageIcon(MenuPanel.class.getResource("images/army.png")).getImage();
        Image scaledRight = rightRaw.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        ImageIcon rightIcon = new ImageIcon(scaledRight);

        JLabel rightLabel = new JLabel(rightIcon);
        rightLabel.setOpaque(false);


        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(rightLabel, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.EAST);


        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;


        JLabel titleLabel = new JLabel("Left - Right - KABOOM!");
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        centerPanel.add(titleLabel, gbc);


        gbc.gridy++;
        scoresLabel = new JLabel();
        scoresLabel.setForeground(Color.WHITE);
        scoresLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoresLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoresLabel.setText(buildTopScoresHTML());
        centerPanel.add(scoresLabel, gbc);


        gbc.gridy++;
        JButton newGameButton = createMenuButton("Join Army");
        newGameButton.addActionListener(e -> parentFrame.showGamePanel());
        centerPanel.add(newGameButton, gbc);


        gbc.gridy++;
        JButton highScoreButton = createMenuButton("Heroes");
        highScoreButton.addActionListener(e -> parentFrame.showHighScorePanel());
        centerPanel.add(highScoreButton, gbc);


        gbc.gridy++;
        JButton rulesButton = createMenuButton("Plan");
        rulesButton.addActionListener(e -> parentFrame.showRulesPanel());
        centerPanel.add(rulesButton, gbc);


        gbc.gridy++;
        JButton exitButton = createMenuButton("Traitor");
        exitButton.addActionListener(e -> System.exit(0));
        centerPanel.add(exitButton, gbc);
    }


    public void refreshTopScores() {
        if (scoresLabel != null) {
            scoresLabel.setText(buildTopScoresHTML());
        }
    }


    private String buildTopScoresHTML() {
        List<ScoreEntry> entries = parentFrame.getScoreManager().loadAndSortScores();

        StringBuilder sb = new StringBuilder("<html><div style='text-align:center;'>");
        sb.append("<h3>Top Scores</h3>");

        int limit = Math.min(entries.size(), 3);
        if (limit == 0) {
            sb.append("<p>No scores yet!</p>");
        } else {
            for (int i = 0; i < limit; i++) {
                ScoreEntry se = entries.get(i);
                sb.append(String.format(
                        "%d) %s - %d<br/>",
                        (i + 1), se.getPlayerName(), se.getScore()
                ));
            }
        }
        sb.append("</div></html>");
        return sb.toString();
    }


    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 140, 0));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(220, 50));
        button.setBorder(new RoundedBorder(30));
        button.setFocusPainted(false);
        return button;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(
                0, 0, Color.BLACK,
                w, h, new Color(139, 0, 0)
        );
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        g2d.dispose();
    }


    private static class RoundedBorder implements javax.swing.border.Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
