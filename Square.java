package ce203AssignmentTwo;

import java.awt.*;

public class Square extends Shape {

    // integer which determines the length of the sides.
    int lengthOfSides;
    // defaultColour to paint with.
    public Color defaultColour = new Color(0, 0, 0);
    // Point objects which will relate X and Y co-ordinates to the polygon to be drawn.
    Point firstPoint, secondPoint, thirdPoint, fourthPoint;
    // int arrays taken by g.fillPolygon as arguments to be utilized in paintComponent for painting.
    int[] polyXCoordinates, polyYCoordinates;
    // The Polygon object which will be painted after the co-ordinate arrays are instantiated with values.
    Polygon thePolygon;

    public Square(int xPosition, int yPosition, int lengthOfSides) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.lengthOfSides = lengthOfSides;

        // Setting the points to draw the rectangle with, based on the position and area arguments.
        this.firstPoint = new Point(xPosition + 0, yPosition + 0);
        this.secondPoint = new Point(xPosition + lengthOfSides, yPosition + 0);
        this.thirdPoint = new Point(xPosition + lengthOfSides, yPosition + lengthOfSides);
        this.fourthPoint = new Point(xPosition, yPosition + lengthOfSides);

        // Populating the x and y co-ordinate arrays based on the Points and then instantiating a new Polygon object which can be painted.
        this.polyXCoordinates = new int[]{(int) firstPoint.getX(), (int) secondPoint.getX(), (int) thirdPoint.getX(), (int) fourthPoint.getX()};
        this.polyYCoordinates = new int[]{(int) firstPoint.getY(), (int) secondPoint.getY(), (int) thirdPoint.getY(), (int) fourthPoint.getY()};
        this.thePolygon = new Polygon(polyXCoordinates, polyYCoordinates, polyXCoordinates.length);

    }

    // Method for painting the Square.
    public void paintComponent(Graphics g){
        super.paint(g);
        g.setColor(defaultColour);
        g.fillPolygon(thePolygon);
    }

    // Method for rotating the Square.
    public void rotate (double degrees) {
        // Find the center of the rectangle so that the rotation around the origin can be performed and translated back to original position.
        Point theCenter = new Point(xPosition + (lengthOfSides / 2), yPosition + (lengthOfSides / 2));

        // Setting to each Point object's X and Y coordinates a transformation around the origin by passing the degrees in radians to Math's cos and sin functions
        firstPoint.setLocation(
                (firstPoint.getX()-theCenter.x) * Math.cos(Math.toRadians(degrees)) -
                        (firstPoint.getY()-theCenter.y) * Math.sin(Math.toRadians(degrees))+theCenter.x,
                (firstPoint.getX()-theCenter.x) * Math.sin(Math.toRadians(degrees)) +
                        (firstPoint.getY()-theCenter.y) * Math.cos(Math.toRadians(degrees))+theCenter.y);

        // Repeat for successive points.
        secondPoint.setLocation((secondPoint.getX()-theCenter.x) * Math.cos(Math.toRadians(degrees)) -
                        (secondPoint.getY()-theCenter.y) * Math.sin(Math.toRadians(degrees))+ theCenter.x,
                (secondPoint.getX()-theCenter.x) * Math.sin(Math.toRadians(degrees)) +
                        (secondPoint.getY()-theCenter.y) * Math.cos(Math.toRadians(degrees))+theCenter.y);

        // Repeat for successive points.
        thirdPoint.setLocation((thirdPoint.getX()-theCenter.x) * Math.cos(Math.toRadians(degrees)) -
                        (thirdPoint.getY()-theCenter.y) * Math.sin(Math.toRadians(degrees))+ theCenter.x,
                (thirdPoint.getX()-theCenter.x) * Math.sin(Math.toRadians(degrees)) +
                        (thirdPoint.getY()-theCenter.y) * Math.cos(Math.toRadians(degrees))+theCenter.y);

        // Repeat for successive points.
        fourthPoint.setLocation((fourthPoint.getX()-theCenter.x) * Math.cos(Math.toRadians(degrees)) -
                        (fourthPoint.getY()-theCenter.y) * Math.sin(Math.toRadians(degrees))+theCenter.x,
                (fourthPoint.getX()-theCenter.x) * Math.sin(Math.toRadians(degrees)) +
                        (fourthPoint.getY()-theCenter.y) * Math.cos(Math.toRadians(degrees))+theCenter.y);


        // Create an array of x and y coordinates for the polygon, based on the new x and y co-ordinates in each Point and repaintint the subsequent shape.
        polyXCoordinates = new int[]{(int) firstPoint.getX(), (int) secondPoint.getX(), (int) thirdPoint.getX(), (int) fourthPoint.getX()};
        polyYCoordinates = new int[]{(int) firstPoint.getY(), (int) secondPoint.getY(), (int) thirdPoint.getY(), (int) fourthPoint.getY()};
        thePolygon = new Polygon(polyXCoordinates, polyYCoordinates, polyXCoordinates.length);
        repaint();
    }

}
