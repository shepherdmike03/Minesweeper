/****************************************************************************/
/*           Author: Pasztor Miklos                                         */
/*                   Left - Right - KABOOM!                                 */
/*                          PMKM0005                                        */
/****************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class HighScorePanel extends JPanel {

    private MinesweeperFrame parentFrame;

    private JLabel titleLabel;
    private JLabel scoreListLabel;

    private static final Color VOLCANO_ORANGE = new Color(255, 140, 0);
    private static final Color VOLCANO_BLACK  = new Color(0, 0, 0);

    public HighScorePanel(MinesweeperFrame frame) {
        this.parentFrame = frame;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.YELLOW);
        add(titleLabel, gbc);

        gbc.gridy++;
        scoreListLabel = new JLabel();
        scoreListLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        scoreListLabel.setForeground(Color.WHITE);
        scoreListLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreListLabel, gbc);

        gbc.gridy++;
        JButton backButton = createButton("Back to Menu");
        backButton.addActionListener(e -> parentFrame.showMenuPanel());
        add(backButton, gbc);
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


    public void refreshScores() {
        List<ScoreEntry> entries = parentFrame.getScoreManager().loadAndSortScores();
        StringBuilder sb = new StringBuilder("<html><div style='text-align:center;'>");

        int limit = Math.min(entries.size(), 10);
        if (limit == 0) {
            sb.append("No scores yet!");
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
        scoreListLabel.setText(sb.toString());
    }


    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(VOLCANO_ORANGE);
        button.setForeground(VOLCANO_BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(220, 50));
        button.setBorder(new RoundedBorder(30));
        button.setFocusPainted(false);
        return button;
    }


    private static class RoundedBorder implements javax.swing.border.Border {
        private int radius;
        public RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius+1, radius+1, radius+2, radius);
        }
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
}
