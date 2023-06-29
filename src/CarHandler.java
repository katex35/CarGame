import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CarHandler implements KeyListener {
    GamePanel panel;

    public CarHandler(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            panel.car.setCarX(15);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            panel.car.setCarX(-15);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
