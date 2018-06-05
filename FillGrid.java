package ce203AssignmentTwo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

// Contains the logic for populating and updating the grid of squares.
public class FillGrid {

    // Two dimensional boolean grid for the gameboard walls, where true equals a filled wall block and false an empty space.
    public static boolean[][] wallValues = {
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {false, true, true, true, false, true, true, true, true, true, true, true, true, true, true, false, true, true, true, false},
            {false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false},
            {false, true, true, true, false, true, true, false, false, true, true, false, false, true, true, false, true, true, true, false},
            {false, true, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, true, false},
            {false, true, false, true, true, false, true, true, true, true, true, true, true, true, false, true, true, false, true, false},
            {false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false},
            {true, true, true, false, true, false, true, true, true, false, false, true, true, true, false, true, false, true, true, true},
            {false, false, false, false, false, false, true, true, true, false, false, true, true, true, false, false, false, false, false, false},
            {false, true, true, true, true, true, true, true, true, false, false, true, true, true, true, true, true, true, true, false},
            // Point of sytmmetry
            {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {false, true, true, true, false, true, true, true, true, true, true, true, true, true, true, false, true, true, true, false},
            {false, false, false, false, false, true, false, false, false, false, false, false, false, false, true, false, false, false, false, false},
            {false, true, true, true, false, true, true, false, false, true, true, false, false, true, true, false, true, true, true, false},
            {false, true, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, true, false},
            {false, true, false, true, true, false, true, true, true, true, true, true, true, true, false, true, true, false, true, false},
            {false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false},
            {true, true, true, false, true, false, true, true, true, true, true, true, true, true, false, true, false, true, true, true},
            {false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false},
            {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false}
    };

    // Creates the player at the top left hand corner of the screen and Ghost objects at other corners with a 20, 40 or 60% chance of making random moves.
    public static PacMan thePlayer = new PacMan(0, 0, false);
    public static Ghost wooMagenta = new Ghost(0, 19, Color.MAGENTA, 0.0);
    public static Ghost wooGreen = new Ghost(19, 0, Color.GREEN, 0.15);
    public static Ghost wooBlue = new Ghost(19, 19, Color.BLUE, 0.3);

    // Creates an excluded list of locations to avoid painting berries in and a list of remaining berries that may be eaten by PacMan.
    public static ArrayList<int[]> exclusionArray = new ArrayList<int[]>();
    ArrayList<Shape> Berries = new ArrayList<Shape>();

    public FillGrid() {

        // Create arrays to hold player PacMan and the Ghosts in the shape HashMap.
        ArrayList<Shape> player = new ArrayList<Shape>();
        player.add(thePlayer);
        TheGame.theShapes.put("player", player);
        ArrayList<Shape> ghosts = new ArrayList<Shape>();
        ghosts.add(wooMagenta);
        ghosts.add(wooGreen);
        ghosts.add(wooBlue);
        TheGame.theShapes.put("ghosts", ghosts);

        // Adding player and ghost positions to the exclusion list, which is used for painting berries in non wall, non excluded locations.
        int[] playerPos = {0, 0};
        int[] ghostOnePos = {0, 19};
        int[] ghostTwoPos = {19, 0};
        int[] ghostThreePos = {19, 19};
        exclusionArray.add(playerPos);
        exclusionArray.add(ghostOnePos);
        exclusionArray.add(ghostTwoPos);
        exclusionArray.add(ghostThreePos);

        // Creating new Circle objects (a subclass of Shape) to be added to the main game collection.
        ArrayList<Shape> powerUps = new ArrayList<>();
        Circle pillOne = new Circle(6, 2);
        Circle pillTwo = new Circle(13, 2);
        Circle pillThree = new Circle(6, 12);
        Circle pillFour = new Circle(13, 12);
        powerUps.add(pillOne);
        powerUps.add(pillTwo);
        powerUps.add(pillThree);
        powerUps.add(pillFour);
        TheGame.theShapes.put("powerUps", powerUps);

    }

    // Checkes whether player shares same coordinates as the berries.
    public static void updateEdibles(PacMan updatedPlayer) {

        // Retrieves berry list and checks whether the moved PacMan occupies the same position.
        for (int i = 0; i < TheGame.theShapes.get("berries").size(); i++) {
            ArrayList<Shape> checkBerries = TheGame.theShapes.get("berries");
            //System.out.println("Berry list size is: "  + TheGame.theShapes.get("berries").size());
            Shape eachBerry = checkBerries.get(i);
            // If the locations are the same, remove the current berry from the berries array so it will not be redrawn and update the score.
            if (eachBerry.xPosition == updatedPlayer.xPos && eachBerry.yPosition == updatedPlayer.yPos) {
                checkBerries.remove(checkBerries.indexOf(eachBerry));
                int[] exclusion = {updatedPlayer.yPos, updatedPlayer.xPos};
                // Add psoition to exclusion so the eaten berry is not wtihdrawn and put the new array back into the main collection.
                FillGrid.exclusionArray.add(exclusion);
                TheGame.theShapes.get("berries").clear();
                TheGame.theShapes.put("berries", checkBerries);
                // Update score if the game is not over
                if ( !TheGame.isGameOver ) {
                    TheGame.theScore += 25;
                }
                SidePanel.displayBox.setText("The Score:" + Integer.toString(TheGame.theScore));
            }
        }

        // Check and remove any powerUps whose locations are equal to that of PacMan's
        for (int i = 0; i < TheGame.theShapes.get("powerUps").size(); i++) {
            ArrayList<Shape> checkPowerUps = TheGame.theShapes.get("powerUps");
            Shape eachPowerUp = checkPowerUps.get(i);
            if (eachPowerUp.xPosition == updatedPlayer.xPos && eachPowerUp.yPosition == updatedPlayer.yPos) {
                checkPowerUps.remove(checkPowerUps.indexOf(eachPowerUp));
                TheGame.theShapes.put("powerUps", checkPowerUps);
                if ( !TheGame.isGameOver ) {
                    TheGame.theScore += 25;
                }
                SidePanel.displayBox.setText("The Score: " + Integer.toString(TheGame.theScore));
            }
        }
    }

    // Updates the position of PacMan in the desired direction along either x or y axis and sets PacMan's angle for drawing.
    public static void movePacMan(char axis, int increment, String moveType) {

        // Retrieving the player from the container.
        ArrayList<Shape> playerArray = TheGame.theShapes.get("player");
        PacMan updatedPlayer = (PacMan) playerArray.get(0);

        // Player updates if key pressed successfully along the target axis
        if ('x' == axis) {
            updatedPlayer.xPos += increment;
        } else {
            updatedPlayer.yPos += increment;
        }

        // Set angle based on type of move
        if (moveType.equals("right")) {
            updatedPlayer.startAngle = -45;
        } else if (moveType.equals("left")) {
            updatedPlayer.startAngle = 135;
        } else if (moveType.equals("up")) {
            updatedPlayer.startAngle = 45;
        } else {
            updatedPlayer.startAngle = -135;
        }

        // Updates player, pill, or Ghost state if a collision is found.
        updatedPlayer.checkCollision();

        // Passes the new player coordinates to updateEdibles and if player co-ordinate matches that of an edible, edible is removed.
        updateEdibles(updatedPlayer);

        // Placing the updated player object back into the shape collection and checking whether the level is complete.
        playerArray.set(0, updatedPlayer);

    }

    // Implementation of movePacMan wherein no new draw angle is specified. This method only ever is called on right mouse click.
    public static void movePacMan() {

        // Retrieving the player from the container.
        ArrayList<Shape> pieceArray = TheGame.theShapes.get("player");
        PacMan updatedPlayer = (PacMan) pieceArray.get(0);

        // Checks the startAngle of PAcMan, given that moving in this direction will not encounter a wall (from logic check in calling method).
        if (updatedPlayer.startAngle == -45) {
            updatedPlayer.xPos += 1;
        } else if (updatedPlayer.startAngle == 45) {
            updatedPlayer.yPos += -1;
        } else if (updatedPlayer.startAngle == -135) {
            updatedPlayer.yPos += 1;
        } else {
            updatedPlayer.xPos -= 1;
        }

        // Updates player, pill, or Ghost state if a collision is found.
        updatedPlayer.checkCollision();

        // Passes the new player coordinates to updateEdibles and if player co-ordinate matches that of an edible, edible is removed.
        updateEdibles(updatedPlayer);

        // Placing the updated player object back into the shape collection and checking whether the level is complete.
        pieceArray.set(0, updatedPlayer);
        //isLevelComplete();

    }

    public void populateMaze() {

        // If not a wall and iteration does not equal character or ghost, place a berry
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                // Testing location and whether current cell is not a wall and not equal to an excluded position.
                boolean wasFound = false;
                int[] currentCell = {i, j};
                // If the current cell is not a wall, then check the current cell is not in the excluded list.
                if ((!wallValues[i][j])) {
                    for (int k = 0; k < exclusionArray.size(); k++) {
                        int[] tempArray = exclusionArray.get(k);
                        if (Arrays.equals(tempArray, currentCell)) {
                            wasFound = true;
                        }
                    }
                }
                //If not a wall and not a reserved space for player or ghosts, add a new edible berry.;
                if ((!wallValues[i][j]) && !wasFound) {
                    Circle berry = new Circle(j, i);
                    Berries.add(berry);
                }
            }
        }

        // Remove and replace list of berries to be redrawn in the main collection.
        TheGame.theShapes.remove("berries");
        TheGame.theShapes.put("berries", Berries);

    }
}
