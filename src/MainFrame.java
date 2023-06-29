import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Game");
        setResizable(false);

//        add(new StartPanel(this));
        add(new GamePanel());
        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }

}

