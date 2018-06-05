package ce203AssignmentTwo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TheGame extends JFrame {

    // ArrayList of Shapes which holds all objects in the game, which are iteratively painted over the board.
    public static HashMap<String, ArrayList<Shape>> theShapes;
    // Static variables for score updates on successful consumption of berries, power ups or enemy ghosts, determining game over and tracking current time.
    public static int theScore;
    // Boolean values relating to whether the game is paused or whether the game is over.
    public static boolean isGameOver, isPaused;
    public static long currentTime, startTime, powerWindow;
    // Static sidePanel whose score display methods can be accessed throughout the program.
    public static SidePanel sidePanel;

    // Instationation of TheGame().
    public TheGame() {

        // Set title with desired information and set score and start states of the game.
        setTitle("Pacman");
        theScore = 0;
        isGameOver = false;
        isPaused = false;
        startTime = System.currentTimeMillis();

        //Instantiating the main collection plus the game board and adding the board to the JFrame.
        theShapes = new HashMap<String, ArrayList<Shape>>();
        setLayout(new BorderLayout());
        GamePanel gameBoard = new GamePanel();
        add(gameBoard, BorderLayout.WEST);

        // Instantiating a new side panel for displaying and adding scores
        sidePanel = new SidePanel();
        add(sidePanel, BorderLayout.EAST);

        // Setting size and visibility.
        setSize(1100, 800);
        setVisible(true);
    }

    // Checks whether or not the win condition has been achieved and updates score based on time taken and remaining lives.
    public static void winCondition(){

        try {
            if ( ((PacMan) theShapes.get("player").get(0)).lives <= 0 ||
                    (theShapes.get("berries").size() == 0 && theShapes.get("powerUps").size() == 0 )) {
                System.out.println("True");
                TheGame.isGameOver = true;
                // Add remaining life bonus to the score, minus modifier for the number of seconds taken to reach end game state.
                TheGame.theScore += (TheGame.startTime - System.currentTimeMillis())% 10;
                TheGame.theScore += (FillGrid.thePlayer.lives * 1000);
                System.out.println(FillGrid.thePlayer.lives);
                // Remove focus from player and add new score plus display all time scores.
                SidePanel.displayBox.requestFocus();
                SidePanel.isTwentyFour = false;
                // Add this game's end state score to the score text file and update the main display area with all time scores.
                TrackScores.addScore();
                TrackScores.displayScores();
            }
        }
        catch (Exception NullPointerException) {
            return;
        }
    }

}
