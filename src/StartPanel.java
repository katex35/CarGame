import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 1000;
    JButton startButton;
    JFrame frame;

    public StartPanel(JFrame frame) {
        this.frame = frame;

        //setFocusable(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(new Color(0x4D4D65));

        startButton = new JButton("Start the game");

        startButton.setBorder(new EtchedBorder());
        startButton.setBackground(new Color(0xAF4E4E));
        startButton.setForeground(new Color(0xCBC5C5));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });


        add(startButton);


        //addKeyListener(new CarHandler(this));

    }

    public void delete() {
        revalidate();
        frame.remove(this);
        frame.add(new GamePanel());
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        startButton.setBounds(225, 350, 150, 30);
    }

    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setFont(new Font("Ariel Rounded MT", Font.BOLD, 60));
        FontMetrics fm = getFontMetrics(g2d.getFont());
        g2d.drawString("Car Game", (SCREEN_WIDTH - fm.stringWidth("Car Game")) / 2, g.getFont().getSize() + 140);

        g2d.setFont(new Font("Ariel Rounded MT", Font.ITALIC, 10));
        fm = getFontMetrics(g2d.getFont());
        g2d.drawString("by Gökay", (SCREEN_WIDTH - fm.stringWidth("by Gökay")) / 2, g.getFont().getSize() + 210);
    }
}
