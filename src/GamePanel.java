import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class GamePanel extends JPanel implements Runnable {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 1000;
    static final int LEFT_RIGHT_SCREEN_GAP = 25;
    static final int GREEN_PART_WIDTH = 25;
    static final int GREY_PART_WIDTH = 500;
    final int ROAD_LEFT_BORDER_X = LEFT_RIGHT_SCREEN_GAP + GREEN_PART_WIDTH;
    final int ROAD_RIGHT_BORDER_X = LEFT_RIGHT_SCREEN_GAP + GREEN_PART_WIDTH + GREY_PART_WIDTH;
    static final int whiteLineX = GREEN_PART_WIDTH + GREEN_PART_WIDTH + 150;
    static final int CAR_WIDTH = 75;
    static final int CAR_HEIGHT = 150;
    int crashCarSpeed = 5;
    int totalMillSec;
    int millSec = 25;
    int whiteLineY = 0;
    int whiteLineYMultiplier = 0;
    boolean gameStarted = false;
    Thread gameThread;
    Car car;
    Car crashCar;
    public GamePanel(){

        setFocusable(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        addKeyListener(new CarHandler(this));

        gameStart();

    }

    public void createCrashCar(){
        crashCar = new Car(this, "random");
        add(crashCar);
    }
    public void createNormalCar(){
        car = new Car(this, "main");
        add(car);
    }

    private void gameStart(){
        gameStarted = true;

        createNormalCar();
        createCrashCar();

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRoad(g);
    }

    private void drawRoad(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //g2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(new Color(0x39C520));
        g2d.fillRect(LEFT_RIGHT_SCREEN_GAP,0, GREEN_PART_WIDTH, SCREEN_HEIGHT);
        g2d.fillRect(SCREEN_WIDTH-(GREEN_PART_WIDTH + LEFT_RIGHT_SCREEN_GAP),0, GREEN_PART_WIDTH, SCREEN_HEIGHT);

        g2d.setColor(new Color(0xFF444242, true));
        g2d.fillRect(LEFT_RIGHT_SCREEN_GAP*2, 0, GREY_PART_WIDTH, SCREEN_HEIGHT);
        drawRoadLines(g);

        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLUE);
        //g2d.drawLine(carX+CAR_WIDTH/4, carY,carX+(CAR_WIDTH/2)+(CAR_WIDTH/4) , carY);

    }

    private void drawRoadLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(0xFFFFFF));
        while(whiteLineY < SCREEN_HEIGHT){
            if(whiteLineYMultiplier-70 >= 0){
                g2d.drawLine(whiteLineX, 0, whiteLineX, whiteLineYMultiplier-50);
                g2d.drawLine(whiteLineX*2, 0, whiteLineX*2, whiteLineYMultiplier-50);
            }
            g2d.drawLine(whiteLineX, whiteLineY, whiteLineX, whiteLineY+30);
            g2d.drawLine(whiteLineX*2, whiteLineY, whiteLineX*2, whiteLineY+30);
            whiteLineY+=80;
        }
        whiteLineY = 0;

    }

    public void moveRoadLines(){
        whiteLineYMultiplier+= 3;
        if(whiteLineYMultiplier > 80){
            whiteLineYMultiplier = 0;
        }
        whiteLineY = whiteLineYMultiplier;
    }

    public void update(){
        crashCar.setCarY( crashCar.getCarY() + crashCarSpeed );
        crashCar.setLocation(crashCar.getCarX(), crashCar.getCarY());

        car.setLocation(car.getCarX(), car.getCarY());

        //System.out.println("crashCar Y: " + crashCar.getCarY() );
        if(crashCar.getCarY() >= SCREEN_HEIGHT){
            String carType = crashCar.getRandomCarType();
            ImageIcon carImage = new ImageIcon(new ImageIcon(carType).getImage().getScaledInstance(CAR_WIDTH, CAR_HEIGHT, Image.SCALE_DEFAULT));
            crashCar.setIcon(carImage);
            int randomX = crashCar.getRandomX();
            crashCar.setCarX(randomX ,"random");
            crashCar.setCarY(-CAR_HEIGHT);
            crashCar.setLocation(randomX, crashCar.getCarY());
        }
    }

    public void checkCollision(){
        if(crashCar == null)
            return;
        Rectangle rectB = crashCar.getBounds();

        Rectangle result = SwingUtilities.computeIntersection(car.getX(), car.getY(), car.getWidth(), car.getHeight(), rectB);

        if (result.getWidth() > 0 && result.getHeight() > 0){
            gameStarted = false;
        }
    }

    public void millSecCalculator(){
        if (millSec <= 10)
            return;
        totalMillSec += millSec;
        if(totalMillSec >= 1000){
            totalMillSec = 0;
            millSec--;
            if (millSec == 23 || millSec == 20 || millSec == 17 || millSec == 14 || millSec == 11)
                crashCarSpeed +=1;
            System.out.println(millSec);
        }
    }
    @Override
    public void run() {
        while(gameStarted){
            try {
                sleep(millSec);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            millSecCalculator();
            moveRoadLines();
            checkCollision();
            update();

            repaint();
        }
    }


}
