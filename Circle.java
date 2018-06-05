package ce203AssignmentTwo;

import java.awt.*;

// Shape extension. A Circle.
public class Circle extends Shape {

    // Sets a default colour for the circles.
    public Color defaultColour = new Color(255, 0, 0);

    // Assigns a value to the variables xPosition and yPosition.
    public Circle(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    // Default paint component.

    public void paintComponent(Graphics g){
        g.setColor(defaultColour);
        g.fillOval(xPosition * 30, yPosition * 30, 5, 5);
    }

    // Paint component where circle of any size can be specified.
    public void paintComponent(Graphics g, int size){
        g.setColor(defaultColour);
        g.fillOval(xPosition * 30, yPosition * 30, size, size);
    }

    // Alternative paint method with different name due to same types of arguments.
    public void paintOffSetComponent(Graphics g, int offSet){
        g.setColor(defaultColour);
        g.fillOval(xPosition * 30 + offSet, yPosition * 30 + offSet, 5 , 5);
    }

    // Empty rotate method ingerited from parent.
    public void rotate(double degrees){
        // Method stub. Blank as a rotated circle is always equal to the original circle.
    }

}
