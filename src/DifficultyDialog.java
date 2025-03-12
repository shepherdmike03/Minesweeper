import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class DifficultyDialog extends JDialog {

    private Difficulty chosenDifficulty = null;
    private JComboBox<Difficulty> difficultyCombo;


    public DifficultyDialog(Frame owner) {
        super(owner, "Select Difficulty", true);

        setSize(600, 300);
        setLocationRelativeTo(owner);
        setUndecorated(true);   /*Remove TitleBar for better visibility*/


        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;


        JLabel titleLabel = new JLabel("Select Difficulty");
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        gradientPanel.add(titleLabel, gbc);


        gbc.gridy++;
        difficultyCombo = new JComboBox<>(Difficulty.values());
        difficultyCombo.setFont(new Font("Arial", Font.PLAIN, 20));

        gradientPanel.add(difficultyCombo, gbc);


        gbc.gridy++;
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        buttonsPanel.setOpaque(false);

        JButton okButton = createVolcanicButton("OK");
        okButton.addActionListener(e -> {
            chosenDifficulty = (Difficulty) difficultyCombo.getSelectedItem();
            dispose();
        });

        JButton cancelButton = createVolcanicButton("Cancel");
        cancelButton.addActionListener(e -> {
            chosenDifficulty = null;
            dispose();
        });

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        gradientPanel.add(buttonsPanel, gbc);


        setContentPane(gradientPanel);
    }


    public Difficulty getChosenDifficulty() {
        return chosenDifficulty;
    }


    private JButton createVolcanicButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(255, 140, 0)); // Orange
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setBorder(new RoundedBorder(30));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 50));
        return btn;
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
        public void paintBorder(Component c, Graphics g, int x, int y,
                                int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
}
