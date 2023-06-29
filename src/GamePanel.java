import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;

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
    static final int CAR_WIDTH = 150;
    static final int CAR_HEIGHT = 150;
    int xTemp;
    int milSec = 25;
    int whiteLineY = 0;
    int whiteLineYMultiplier = 0;
    boolean gameStarted = false;
    Thread gameThread;
    Car car;
    Car crashCar;
    public GamePanel(){

        setFocusable(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        car = new Car(this, "main");
        crashCar = new Car(this, "random");

        add(car);
        add(crashCar);
        addKeyListener(new CarHandler(this));

        gameStart();

    }

    public void setCarX(int x){
//        if(carX+(CAR_WIDTH/2)+(CAR_WIDTH/4) > LEFT_RIGHT_SCREEN_GAP + GREEN_PART_WIDTH + GREY_PART_WIDTH){
//            System.out.println("sağa çarpar");
//            //this.carX = x+(CAR_WIDTH/6)+(CAR_WIDTH/6);
//            return;
//        }if(carX+CAR_WIDTH/4 < LEFT_RIGHT_SCREEN_GAP + GREEN_PART_WIDTH){
//            System.out.println("sola çarpar");
//            //this.carX = x+(CAR_WIDTH/6)+(CAR_WIDTH/6);
//            return;
//        }
//        this.carX+= x;
//        this.carX = x;
    }

    public void createCrashCar(){
        crashCar = new Car(this, "random");
    }
    private void gameStart(){
        gameStarted = true;

        gameThread = new Thread(this);
        gameThread.start();

        System.out.println("313131");

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
        if(crashCar != null){
            crashCar.setCarY( crashCar.getCarY() + 5 );
            crashCar.setLocation(crashCar.getCarX(), crashCar.getCarY());
        }

        car.setLocation(car.getCarX(), car.getCarY());

        //System.out.println("crashCar Y: " + crashCar.getCarY() );
        if(crashCar.getCarY() >= SCREEN_HEIGHT){
            crashCar = null;
            crashCar = new Car(this, "random");
            System.out.println("oluşituye");
//            System.out.println(crashCar.getCarY());
        }
    }

    public void checkCollision(){
        if(crashCar == null)
            return;
        Rectangle rectB = crashCar.getBounds();

        Rectangle result = SwingUtilities.computeIntersection(car.getX(), car.getY(), car.getWidth(), car.getHeight(), rectB);

        if (result.getWidth() > 0 && result.getHeight() > 0){
            System.out.print("çarptı");
            gameStarted = false;
        }
    }

    @Override
    public void run() {
        while(gameStarted){
            try {
                //sleep(milSec);
                sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            moveRoadLines();

            checkCollision();
            update();
//            xTemp++;
//            if(xTemp > 50 - milSec){
//                milSec--;
//                xTemp = 0;
//            }
            if(crashCar != null){
                System.out.println("hala var");
            }
            repaint();
        }
        System.out.println("bitti");
    }


}
