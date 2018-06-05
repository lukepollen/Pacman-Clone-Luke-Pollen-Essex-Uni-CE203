package ce203AssignmentTwo;

import java.awt.*;

// On instantiation, updates a two dimensional array of Squares which can be painted on the GamePanel.
public class SquareGrid extends Component {

    // The two dimensional array of Square shapes.
    public static Square[][] gridOfSquares;

    // Initial x and y position to use. Iteratively updated.
    int xPosition, yPosition = 0;

    // Instantiation of the SquareGrid class, which populates gridOfSquares.
    public SquareGrid() {

        this.gridOfSquares = new Square[20][20];

        // If the value in FillGrid is true, then add square to this location in the array.
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                boolean isSolidBlock = FillGrid.wallValues[i][j];
                Square tempSquare = new Square(xPosition, yPosition, 30);
                if ( isSolidBlock ) {
                    tempSquare.defaultColour = new Color( 0, 0, 255 );
                }
                this.gridOfSquares[i][j] = tempSquare;
                // Update the xPosition for painting along the xAxis first.
                xPosition += 30;
                //System.out.println("Square Coordinates: " + xPosition + " , " + yPosition);
            }
            // Reset xAxis and repeat painting process one row down.
            xPosition = 0;
            yPosition += 30;
        }
    }
}
