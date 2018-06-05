package ce203AssignmentTwo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

// A.I. game pieces. Enemies of player avatar, PacMan.
public class Ghost extends Circle {

    // Boolean value used to determine whether PacMan can eat Ghosts.
    public static boolean isEdible = false;
    // The probability that the Ghost will make a random move.
    double randomProbability;
    // Colour instance variables for Ghost body on instantiation and during and after panic mode.
    Color defaultBodyColor;
    Color currentBodyColor;
    // Storing the initial positions supplied as default positions to revert to on successful kill of PacMan, or on death.
    int defaultX;
    int defaultY;

    // Instantiate a Ghost.
    public Ghost(int posX, int posY, Color bodyColor, double randomProbability) {

        super(posX, posY);
        this.defaultX = posX;
        this.defaultY = posY;
        this.defaultBodyColor = bodyColor;
        this.randomProbability = randomProbability;
    }

    // Generates a random colour.
    public void randColour() {

        // Randomly generating a colour for the squares in the square grid.
        Color randomColour = new Color((int) (Math.random() * 255),
                (int) (Math.random() * 255),
                (int) (Math.random() * 255));

        currentBodyColor = randomColour;
    }

    // Whilst not edible, paint the ghost with the default colour supplied, elsewise paint with a randomly generated colours for duration of powerup.
    public void paintComponent(Graphics g) {
        if (isEdible) {
            paintComponent(g, xPosition, yPosition, currentBodyColor);
        }
        if (!isEdible) {
            paintComponent(g, xPosition, yPosition, defaultBodyColor);
        }
    }

    // Method for painting Ghosts. Woo!
    public void paintComponent(Graphics g, int xPos, int yPos, Color theColor) {

        g.setColor(theColor);
        g.fillOval(xPos * 30, yPos * 30, 15, 15);
        g.setColor(Color.WHITE);
        g.fillOval(xPos * 30, yPos * 30 -2, 6, 6);
        g.fillOval(xPos * 30 + 4, yPos * 30 -2, 6, 6);
        g.setColor(Color.BLACK);
        g.fillOval(xPos * 30, yPos * 30 -2, 2, 2);
        g.fillOval(xPos * 30 + 4, yPos * 30 -2, 2, 2);
    }

    // Returns the sum of X and Y distance from the player
    public double xYDistance() {

        double theDistance = Math.abs(this.xPosition - FillGrid.thePlayer.xPos) + Math.abs(this.yPosition - FillGrid.thePlayer.yPos);
        return theDistance;
    }

    // Choses a move for the Ghosts.
    public void choseAMove() {

        // Maximum and minimum distance to be used in comparison.
        double maximumDistance = 39;
        double minimumDistance = 0;
        // ArrayLists for holding either possible moves, or moves which bring the Ghost closer to Pacman, where these int[] are iteratively updated and added.
        int[] possibleMove = {yPosition, xPosition};
        int[] possibleCloserMove = {yPosition, xPosition};
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        ArrayList<int[]> possibleCloserMoves = new ArrayList<int[]>();
        Random boundGenerator = new Random();

        // Checks all adjacent squares and updates the list of valid moves and moves which bring the ghost closer to PacMan.
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    // If adjacent spaces are not a wall, add this position to the list of valid moves
                    if (!FillGrid.wallValues[yPosition + j][xPosition + i]) {
                        xPosition = xPosition + i;
                        yPosition = yPosition + j;
                        possibleMoves.add(possibleMove);
                        double tempDistance = xYDistance();
                        // Move closer or further away from the player, depending on whether the player has an active powerUp.
                        if ( ((PacMan) TheGame.theShapes.get("player").get(0)).afraidOfEctoplasm == true ) {
                            // Empty list of possible closer moves if a better move is found.
                            if ( tempDistance < maximumDistance && ( i != 0 || j != 0 ) ) {
                                possibleCloserMoves.clear();
                                maximumDistance = tempDistance;
                                possibleCloserMove[0] = yPosition;
                                possibleCloserMove[1] = xPosition;
                                possibleCloserMoves.add(possibleCloserMove);
                            }
                            // Add moves to the list that are equally viable in terms of their distance to the player.
                            if (tempDistance == maximumDistance && ( i != 0 || j != 0 )) {
                                possibleCloserMove[0] = yPosition;
                                possibleCloserMove[1] = xPosition;
                                possibleCloserMoves.add(possibleCloserMove);
                            }
                        }
                        else {
                            // As above, however attempt to move away from the player if the player has a powerUp active.
                            if ( tempDistance > minimumDistance && ( i != 0 || j != 0 ) ) {
                                possibleCloserMoves.clear();
                                minimumDistance = tempDistance;
                                possibleCloserMove[0] = yPosition;
                                possibleCloserMove[1] = xPosition;
                                possibleCloserMoves.add(possibleCloserMove);
                            }
                            // Add moves to the list that are equally viable in terms of their distance to the player.
                            if (tempDistance >= minimumDistance && ( i != 0 || j != 0 )) {
                                possibleCloserMove[0] = yPosition;
                                possibleCloserMove[1] = xPosition;
                                possibleCloserMoves.add(possibleCloserMove);
                            }
                        }

                        // Reset back to the original square.
                        xPosition = xPosition - i;
                        yPosition = yPosition - j;
                    }
                // Ignore moves which would take the Ghost off the board.
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    // Continue
                }
            }
        }

        // If movement does not bring the ghost closer to PacMan or if Math.random() is <= probability of moving randomly, then make a random move.
        double moveChance = Math.random();
        if ( moveChance <= randomProbability) {
            // Selects a valid move from the available moves, up to an index size equal to the size of the valid moves.
            Integer upperBound = boundGenerator.nextInt(possibleMoves.size());
            possibleMove = possibleMoves.get(upperBound);
            // Update Ghost position using the returned array.
            yPosition = possibleMove[0];
            xPosition = possibleMove[1];
        }
        // Otherwise, close square whose sum of X and Y distance is smallest.
        else {
            // Selects a valid move from the available moves, up to an index size equal to the size of the valid moves.
            Integer upperBound = boundGenerator.nextInt(possibleCloserMoves.size());
            possibleCloserMove = possibleCloserMoves.get(upperBound);
            //Move into any adjacent squares sharing the smallest manhattan distance
            yPosition = possibleCloserMove[0];
            xPosition = possibleCloserMove[1];
        }

        // Check whether there is a collision with the player and then repaint.
        FillGrid.thePlayer.checkCollision();
        repaint();

    }

    // Resets the Ghost's position to default. Called on successful kill of or death from collision with PacMan
    public void resetPosition() {
        xPosition = defaultX;
        yPosition = defaultY;
    }
}
