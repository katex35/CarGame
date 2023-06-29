import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Car extends JLabel {
    final int CAR_WIDTH = 75;
    final int CAR_HEIGHT = 150;
    int carX;
    int carY;
    ImageIcon carImage;
    GamePanel panel;

    public Car(GamePanel panel, String type) {
        if (type.equals("main")) {
            this.carX = 265;
            this.carY = 800;
            carImage = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/Audi.png"))).getImage().getScaledInstance(CAR_WIDTH, CAR_HEIGHT, Image.SCALE_DEFAULT));

        }
        if (type.equals("random")) {
            this.carX = getRandomX();
            this.carY = -CAR_HEIGHT;

            String carType = getRandomCarType();

            carImage = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(carType))).getImage().getScaledInstance(CAR_WIDTH, CAR_HEIGHT, Image.SCALE_DEFAULT));
        }
        this.panel = panel;
        setIcon(carImage);
        setLocation(carX, carY);
    }

    public int getRandomX() {
        Random r = new Random();
        int randomNumber = (int) (r.nextFloat() * 3 + 1);
        int xCord;
        if (randomNumber == 1) {
            xCord = 85;
        } else if (randomNumber == 2) {
            xCord = 265;
        } else {
            xCord = 435;
        }
        return xCord;
    }

    public String getRandomCarType() {
        Random r = new Random();
        int randomNumber = (int) (r.nextFloat() * 3 + 1);
        String carType;
        if (randomNumber == 1)
            carType = "resources/Black_viper.png";
        else if (randomNumber == 2)
            carType = "resources/Mini_van.png";
        else
            carType = "resources/truck.png";
        return carType;
    }

    public void setCarX(int carX) {
        // car right border = carX+CAR_WIDTH, carY
        // car left border = carX,carY
        if (this.carX + CAR_WIDTH + carX > panel.ROAD_RIGHT_BORDER_X) {
            return;
        }
        if (this.carX + carX < panel.ROAD_LEFT_BORDER_X) {
            System.out.println(this.carX);
            System.out.println(panel.ROAD_LEFT_BORDER_X);
            return;
        }
        this.carX += carX;
    }

    public void setCarX(int carX, String type) {
        if (type.equals("random"))
            this.carX = carX;
    }

    public int getCarX() {
        return carX;
    }

    public int getCarY() {
        return carY;
    }

    public void setCarY(int carY) {
        this.carY = carY;
    }


}
