package ce203AssignmentTwo;

import java.awt.*;

// Extension of the Shape class. A "pie" shape. Part of a circle.
public class Pie extends Shape {

    // Starting location and the width and height of the pie to draw
    int startXPos, startYPos, width, height;
    // Start and end angles for drawing the pie. Doubles as rotation may involve a subtraction which a non integer.
    double startAngle, arcAngle;

    // Instantiate a pie.
    public Pie(int startXPos, int startYPos, int width, int height, Integer startAngle, Integer arcAngle) {

        this.startXPos = startXPos;
        this.startYPos = startYPos;
        this.width = width;
        this.height = height;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;

    }

    // Sets the default colour to yellow and paints two arcs based around the startAngle.
    public void paintComponent(Graphics g) {

        g.setColor(Color.YELLOW);
        g.fillArc(startXPos * 30, startYPos * 30, 15, 15, (int) startAngle, (int) arcAngle);
    }

    // Painting a pie at a specific location.
    public void paintComponent(Graphics g, int xPos, int yPos) {

        g.setColor(Color.YELLOW);
        g.fillArc(xPos * 30, yPos * 30, 15, 15, (int) startAngle, (int) arcAngle);

    }

    // Rotates the pie by the supplied degrees
    public void rotate(double degrees){

        double totalDegrees = startAngle - degrees;

        // If the totalDegrees is less than -180, take the absolute value and return mod 180. e.g. -225 will now use 45 as the starting point
        if ( totalDegrees < -180 ) {
            startAngle = 180 - ((Math.abs(startAngle - degrees)) % 180);
        }
        // If neither of these conditions are true and startAngle is within acceptable range, then use the startAngle plus degrees as new startAngle.
        else {
            startAngle = startAngle - degrees;
        }
        arcAngle = -270;

    }

}


