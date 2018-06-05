package ce203AssignmentTwo;

import javax.swing.*;
import java.awt.*;

public class TestPanel extends JPanel {

    // Shape objects to be created, rotated and displayed.
    Rectangle rectangle;
    Square square;
    Triangle triangle;

    // Instantiation of the TestPanel. Updates Shape variables with instances.
    public TestPanel() {

        rectangle = new Rectangle(200, 200, 200, 200);
        square = new Square(500, 500, 50);
        triangle = new Triangle(800, 800, 60);
        setSize(1200, 1000);
        rectangle();
        square();
        triangle();
    }

    // Prints the co-ordinates of the original rectangle, then rotates the shape, then paints the rotated shape.
    public void rectangle(){
        System.out.println("Rectangle Co-ordinates: ");
        System.out.println(rectangle.firstPoint.x + " : " + rectangle.firstPoint.y);
        System.out.println(rectangle.secondPoint.x + " : " + rectangle.secondPoint.y);
        System.out.println(rectangle.thirdPoint.x + " : " + rectangle.thirdPoint.y);
        System.out.println(rectangle.fourthPoint.x + " : " + rectangle.fourthPoint.y);
        System.out.println("After rotation by 90 degrees: ");
        rectangle.rotate(90);
        System.out.println(rectangle.firstPoint.x + " : " + rectangle.firstPoint.y);
        System.out.println(rectangle.secondPoint.x + " : " + rectangle.secondPoint.y);
        System.out.println(rectangle.thirdPoint.x + " : " + rectangle.thirdPoint.y);
        System.out.println(rectangle.fourthPoint.x + " : " + rectangle.fourthPoint.y);
    }

    // Prints the co-ordinates of the original square, then rotates the shape, then paints the rotated shape.
    public void square(){
        System.out.println("Aquare Co-ordinates: ");
        System.out.println(square.firstPoint.x + " : " + square.firstPoint.y);
        System.out.println(square.secondPoint.x + " : " + square.secondPoint.y);
        System.out.println(square.thirdPoint.x + " : " + square.thirdPoint.y);
        System.out.println(square.fourthPoint.x + " : " + square.fourthPoint.y);
        System.out.println("After rotation by 171 degrees: ");
        square.rotate(171);
        System.out.println(square.firstPoint.x + " : " + square.firstPoint.y);
        System.out.println(square.secondPoint.x + " : " + square.secondPoint.y);
        System.out.println(square.thirdPoint.x + " : " + square.thirdPoint.y);
        System.out.println(square.fourthPoint.x + " : " + square.fourthPoint.y);
    }

    // Prints the co-ordinates of the original triangle, then rotates the shape, then paints the rotated shape.
    public void triangle(){
        System.out.println("Triangle Co-ordinates: ");
        System.out.println(triangle.firstPoint.x + " : " + triangle.firstPoint.y);
        System.out.println(triangle.secondPoint.x + " : " + triangle.secondPoint.y);
        System.out.println(triangle.thirdPoint.x + " : " + triangle.thirdPoint.y);
        System.out.println("After rotation by 225 degrees: ");
        triangle.rotate(225);
        System.out.println(triangle.firstPoint.x + " : " + triangle.firstPoint.y);
        System.out.println(triangle.secondPoint.x + " : " + triangle.secondPoint.y);
        System.out.println(triangle.thirdPoint.x + " : " + triangle.thirdPoint.y);
    }

    // Paint the individual shapes to the Panel.
    public void paintComponent(Graphics g) {
        square.paintComponent(g);
        rectangle.paintComponent(g);
        triangle.paintComponent(g);
    }
}

