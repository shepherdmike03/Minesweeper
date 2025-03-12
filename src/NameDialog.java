/****************************************************************************/
/*           Author: Pasztor Miklos                                         */
/*                   Left - Right - KABOOM!                                 */
/*                          PMKM0005                                        */
/****************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NameDialog extends JDialog {

    private JTextField nameField;
    private String playerName;

    public NameDialog(JFrame parent, int score) {

        super(parent, "Game Over!", true);

        setUndecorated(true);
        setSize(400, 300);
        setLocationRelativeTo(parent);


        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
        add(mainPanel);




        JLabel titleLabel = new JLabel("GAME OVER!", SwingConstants.CENTER);
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        mainPanel.add(titleLabel, BorderLayout.NORTH);




        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centerPanel, BorderLayout.CENTER);


        JLabel scoreLabel = new JLabel("Your score: " + score, SwingConstants.CENTER);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setFont(new Font("Courier New", Font.BOLD, 20));
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(scoreLabel);
        centerPanel.add(Box.createVerticalStrut(20));


        JLabel namePrompt = new JLabel("Enter your name:", SwingConstants.CENTER);
        namePrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePrompt.setForeground(Color.WHITE);
        namePrompt.setFont(new Font("Courier New", Font.PLAIN, 18));
        centerPanel.add(namePrompt);
        centerPanel.add(Box.createVerticalStrut(10));


        nameField = new JTextField(20);
        nameField.setBackground(Color.BLACK);
        nameField.setForeground(Color.GREEN);
        nameField.setFont(new Font("Courier New", Font.BOLD, 16));
        nameField.setCaretColor(Color.GREEN);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        nameField.setMaximumSize(new Dimension(200, 30));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(nameField);
        centerPanel.add(Box.createVerticalStrut(20));


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        buttonPanel.setOpaque(false);


        JButton okButton = createMenuButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = nameField.getText();
                dispose();
            }
        });
        buttonPanel.add(okButton);


        JButton cancelButton = createMenuButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = null;
                dispose();
            }
        });
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }


    public String getPlayerName() {
        return playerName;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(255, 140, 0)); // volcanic orange
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(30));
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

    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(
                    0, 0, Color.BLACK,
                    w, h, new Color(139, 0, 0) // dark red
            );
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
            g2d.dispose();
        }
    }
}
