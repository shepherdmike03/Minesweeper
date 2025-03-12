/****************************************************************************/
/*           Author: Pasztor Miklos                                         */
/*                   Left - Right - KABOOM!                                 */
/*                          PMKM0005                                        */
/****************************************************************************/

import javax.swing.*;
import java.awt.*;

public class RulesPanel extends JPanel {

    private MinesweeperFrame parentFrame;

    public RulesPanel(MinesweeperFrame frame) {
        this.parentFrame = frame;
        setLayout(new BorderLayout());
        setOpaque(true);

        JLabel titleLabel = new JLabel("Rules & How to Play", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 32)); // Retro font
        add(titleLabel, BorderLayout.NORTH);

        String htmlText =
                "<html>" +
                        "<div style='text-align:center; color:#FFA500; font-family:Courier New;'>" +
                        "<h2>Welcome to Volcanic Minesweeper!</h2>" +
                        "<p><strong>Vulcanic Retro Style</strong> – Enter at your own risk!</p>" +
                        "<hr/>" +

                        "<h3>Controls</h3>" +
                        "<ul>" +
                        "<li><b>Left-Click</b> to open a cell</li>" +
                        "<li><b>Right-Click</b> to place a Flag</li>" +
                        "<li><b>Once a Flag is placed, it cannot be removed!</b></li>" +
                        "</ul>" +

                        "<hr/>" +

                        "<h3>Goal</h3>" +
                        "<ul>" +
                        "<li>Uncover all safe cells</li>" +
                        "<li>Avoid detonating the bombs</li>" +
                        "<li>Use your wits (and a bit of luck) to survive!</li>" +
                        "</ul>" +

                        "<hr/>" +

                        "<h3>Difficulty Levels</h3>" +
                        "<ul>" +
                        "<li><b>EASY (Hungary):</b> 8×8, 6 bombs – Great for warming up</li>" +
                        "<li><b>MEDIUM (Germany):</b> 12×12, 10 bombs – Not too hot, not too cold</li>" +
                        "<li><b>HARD (SwitcerLand):</b> 16×16, 15 bombs – A real challenge</li>" +
                        "<li><b>EXTREME (Russia):</b> 24×24, 25 bombs – You asked for it!</li>" +
                        "<li><b>HOLLY_MOLLY (Ukraine):</b> 24×24, 70 bombs – Very punishing</li>" +
                        "<li><b>HELL (Hell On Earth):</b> 50×50, 500 bombs – <em>Impossible!</em> " +
                        "<br/>No numbers, total chaos. Good luck (you’ll need it)!</li>" +
                        "</ul>" +

                        "<hr/>" +

                        "<h3>Points</h3>" +
                        "<ul>" +
                        "<li>Correctly flagged bombs give points</li>" +
                        "<li>Safely opened cells give points</li>" +
                        "</ul>" +
                        "</div>" +
                        "</html>";

        JLabel rulesLabel = new JLabel(htmlText);
        rulesLabel.setForeground(Color.WHITE);
        rulesLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
        rulesLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JScrollPane scrollPane = new JScrollPane(rulesLabel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);

        JButton backButton = createMenuButton("Back to Menu");
        backButton.addActionListener(e -> parentFrame.showMenuPanel());
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(
                0, 0, Color.BLACK,
                w, h, new Color(139, 0, 0) // Dark red
        );
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        g2d.dispose();
    }
}
