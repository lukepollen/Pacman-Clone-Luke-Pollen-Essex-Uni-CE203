package ce203AssignmentTwo;

import java.awt.*;

// The abstract class which all Shapes extend.
public abstract class Shape extends Component {

    // integer values for the x and y position.
    int xPosition, yPosition;

    // Declaring a default colour and a paintComponent method which must be instantiated for all shapes to allow their painting.
    Color defaultColour = new Color(0, 0, 255);

    // Abstract methods which insists that shapes must implement a method for painting and rotation.
    public abstract void paintComponent(Graphics g);
    public abstract void rotate(double degrees);

}
