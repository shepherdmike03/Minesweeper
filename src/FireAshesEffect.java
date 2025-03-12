import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class FireAshesEffect extends JPanel {

    private static final int FPS = 30;
    private static final int DELAY = 1000 / FPS;

    private final List<Particle> particles = new ArrayList<>();
    private final Random random = new Random();
    private Timer timer;

    public FireAshesEffect() {
        setOpaque(false);

        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateParticles();
                repaint();
            }
        });
    }

    public void startAnimation() {
        timer.start();
    }

    public void stopAnimation() {
        timer.stop();
    }

    private void updateParticles() {
        spawnNewParticles();

        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.update();
            if (p.life <= 0) {
                it.remove();
            }
        }
    }

    private void spawnNewParticles() {
        // 2-4 uj lang minden kepkockanal
        int spawnCount = 2 + random.nextInt(3);
        int w = getWidth();
        int h = getHeight();

        for (int i = 0; i < spawnCount; i++) {
            float x = random.nextFloat() * w;
            float y = h - 5;
            float size = 5 + random.nextFloat() * 5;
            float speedY = 1f + random.nextFloat() * 1.5f;
            float speedX = (random.nextFloat() - 0.5f) * 0.5f;
            int life = 100 + random.nextInt(60);
            particles.add(new Particle(x, y, size, speedX, speedY, life));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {


        Graphics2D g2d = (Graphics2D) g.create();
        for (Particle p : particles) {
            g2d.setColor(p.getCurrentColor());
            int drawX = (int) (p.x - p.size / 2);
            int drawY = (int) (p.y - p.size / 2);
            int drawSize = (int) p.size;
            g2d.fillOval(drawX, drawY, drawSize, drawSize);
        }

        g2d.dispose();
    }

    private class Particle {
        float x, y;
        float size;
        float speedX, speedY;
        int life;
        final Color startColor = new Color(255, 140, 0);
        final Color endColor   = Color.BLACK;
        final int initialLife;

        Particle(float x, float y, float size,
                 float speedX, float speedY, int life) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speedX = speedX;
            this.speedY = speedY;
            this.life = life;
            this.initialLife = life;
        }

        void update() {
            y -= speedY;
            x += speedX;
            size -= 0.05f;
            if (size < 1f) size = 1f;
            life--;
        }

        Color getCurrentColor() {
            float ratio = (float) life / (float) initialLife;
            float r = endColor.getRed()
                    + ratio * (startColor.getRed() - endColor.getRed());
            float g = endColor.getGreen()
                    + ratio * (startColor.getGreen() - endColor.getGreen());
            float b = endColor.getBlue()
                    + ratio * (startColor.getBlue() - endColor.getBlue());
            return new Color((int) r, (int) g, (int) b);
        }
    }
}
