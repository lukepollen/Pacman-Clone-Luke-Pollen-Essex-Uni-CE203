package ce203AssignmentTwo;

import java.awt.*;
import java.util.ArrayList;

// Character avatar
public class PacMan extends Pie {

    // Boolean value determining whether PacMan is drawn as a Pie shape or with his mouth closed (as a Circle via g.drawOval)
    boolean restingState;
    // Starting position for PacMan.
    static int xPos, yPos;
    static int powerUpCount = 0;
    // Current state for fear, or lack thereof, of Ghosts, which switches to false when powerUp is consumed.
    static boolean afraidOfEctoplasm = true;
    // Remaining lives.
    static int lives = 3;

    // Instantiate a PacMan.
    public PacMan(int xPos, int yPos, boolean restingState) {

        // Calling the parent class constructor with the arguments passed to this constructor and assigning this instance the starting coordinates.
        super(xPos, yPos, 15, 15, 135, -135);
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = 15;
        this.height = 15;
        this.restingState = restingState;
        // Setting variables inherited from parent class, Pie, to draw PacMan with.
        this.startAngle = 135;
        this.arcAngle = -270;
    }

    // Paint a closed circle if pacman is in restingState, otherwise use the paint logic of the parent class, Pie.
    public void paintComponent(Graphics g) {

        //System.out.println("PacMan paint.");
        // If PacMan is not resting, paint as Pie shape, or open mouth PacMan. Otherwise, draw him resting, as a circle.
        if (!restingState) {
            super.paintComponent(g, xPos, yPos);
        }
        if (restingState) {
            g.setColor(Color.YELLOW);
            g.fillOval(xPos * 30, yPos * 30, 15, 15);
        }
    }

    // Checks Collisions with objects in
    public void checkCollision() {

        //
        ArrayList<Shape> powerUps = TheGame.theShapes.get("powerUps");
        ArrayList<Shape> ghosts = TheGame.theShapes.get("ghosts");

        for (int i = 0; i < powerUps.size(); i++) {
            Circle powerUp = (Circle) powerUps.get(i);
            // Set PacMan's fear of Ghosts to false when under the effects of the powerUp and make each Ghost edible.
            if (powerUp.xPosition == xPos && powerUp.yPosition == yPos) {
                // Take the current time and set a point fifteen seconds in the future, between which PacMan is invicible and can consume ghosts.
                TheGame.currentTime = System.currentTimeMillis();
                TheGame.powerWindow = TheGame.currentTime + 7500;
                afraidOfEctoplasm = false;
                // Set each ghost state to edible.
                for (int j = 0; j < ghosts.size(); j++) {
                    Ghost eachGhost = (Ghost) ghosts.get(j);
                    eachGhost.isEdible = true;
                    TheGame.theShapes.get("ghosts").set(j, eachGhost);
                }
            }
        }

        // Iterate through the Ghosts array and retrieve ghosts to detect collisions.
        for (int i = 0; i < ghosts.size(); i++) {
            Ghost enemy = (Ghost) ghosts.get(i);
            // If the Ghost occupies the same position as PacMan, kill PacMan or the kill the Ghost, depending on whether power up has been recently consumed.
            if (enemy.xPosition == xPos && enemy.yPosition == yPos) {
                if (afraidOfEctoplasm) {
                    lives -= 1;
                    xPos = 0;
                    yPos = 0;
                } else {
                    enemy.resetPosition();
                    TheGame.theScore += 1;
                    SidePanel.displayBox.setText(Integer.toString(TheGame.theScore));
                }
            }
        }
    }
}
