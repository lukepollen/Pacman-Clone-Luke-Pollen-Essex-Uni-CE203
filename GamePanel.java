package ce203AssignmentTwo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Main game panel containing important board elements, a timer for animation and another class implementing MouseListener, KeyListener that calls repaint();
public class GamePanel extends JPanel {

    // Instantiates a 2 dimensional array, 20 x 20, of Square objects, which are subclasses of Component and abstract Shape
    Square[][] theSquares;

    // Buttons for resetting or pausing the game.
    JButton newGame, pause;

    // An instance of the FillGrid class, which contains important board elements for the game
    FillGrid gameItems = new FillGrid();

    // Constructor instantiating a grid of squares.
    public GamePanel() {

        // Set a BorderLayout for the JPanel and set the preferred size for the JButtons
        setLayout(new BorderLayout());
        newGame = new JButton("New Game!");
        newGame.addActionListener(new ButtonHandler(this));
        newGame.setPreferredSize(new Dimension(300, 100));
        pause = new JButton("Pause / Unpause");
        pause.addActionListener(new ButtonHandler(this));
        pause.setPreferredSize(new Dimension(300, 100));

        // Remove the ability to foucs the buttons and add the buttons to the panel.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newGame.setFocusable(false);
        pause.setFocusable(false);
        buttonPanel.add(newGame);
        buttonPanel.add(pause);
        add(buttonPanel, BorderLayout.SOUTH);

        // Creates an ActionListener to be used with subsequent instantiation of the Timer class, where actionPerformed updates the animations.
        ActionListener updateDisplay = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                // Whilst the game is not over, update the animations.
                if (TheGame.isPaused == false) {
                    if (!TheGame.isGameOver) {

                        // Draw focus back from the buttons.
                        requestFocusInWindow();

                        // Move the Ghosts and update the variable for PacMan's mouth appearance. Afterwards, call repaint on all components.
                        FillGrid.wooBlue.choseAMove();
                        FillGrid.wooGreen.choseAMove();
                        FillGrid.wooMagenta.choseAMove();
                        FillGrid.thePlayer.restingState = !FillGrid.thePlayer.restingState;

                        // If the player powerUp condition is in the past, then revert Ghosts to chasing PacMan
                        long thisTime = System.currentTimeMillis();
                        if (TheGame.powerWindow < thisTime) {
                            FillGrid.thePlayer.afraidOfEctoplasm = true;
                            FillGrid.wooBlue.isEdible = false;
                            FillGrid.wooGreen.isEdible = false;
                            FillGrid.wooMagenta.isEdible = false;

                        }
                        // Whilst PacMan is under the effects of a powerUp, randomly updates the colours of Ghosts.
                        else {
                            FillGrid.wooBlue.randColour();
                            FillGrid.wooGreen.randColour();
                            FillGrid.wooMagenta.randColour();

                        }

                        // Update the painting and then check whether the game is over.
                        repaint();
                        TheGame.winCondition();
                    }
                }
            }
        };

        // Instantiates the Timer.
        Timer timer = new Timer(375, updateDisplay);
        timer.start();

        // Assigns the grid of squares to this instantiation.
        SquareGrid squareGrid = new SquareGrid();
        this.theSquares = squareGrid.gridOfSquares;
        add(squareGrid, BorderLayout.NORTH);

        // Adds a Keylistener and MouseListener to this class instantiation and requests the focus in this window.
        this.setFocusable(true);
        addKeyListener(new PlayerMovement());
        addMouseListener(new PlayerMovement());
        requestFocusInWindow();

        // Panel visibility settings.
        setSize(1000, 700);
        setVisible(true);

    }

    // Paint the main maze and all the important board pieces (PacMan, Ghosts, berries, powerups)
    public void paintComponent(Graphics g) {

        // For each square in each array of twenty, paint the square.
        for (int i = 0; i < 20; i++) {
            for (Square square : theSquares[i]) {
                square.paintComponent(g);
            }
        }

        // Populate the maze and retrieve each individual list for iterations over components with calls to appropriate painting method.
        gameItems.populateMaze();
        ArrayList<Shape> powerUps = TheGame.theShapes.get("powerUps");
        ArrayList<Shape> berries = TheGame.theShapes.get("berries");
        ArrayList<Shape> ghosts = TheGame.theShapes.get("ghosts");

        // Painting edible berries.
        for (int i = 0; i < berries.size(); i++) {
            Circle tempShape = (Circle) berries.get(i);
            tempShape.paintOffSetComponent(g, 7);
        }
        // Painting the power ups.
        for (int i = 0; i < powerUps.size(); i++) {
            Circle thePowerUp = (Circle) powerUps.get(i);
            thePowerUp.defaultColour = new Color(255, 255, 255);
            thePowerUp.paintComponent(g, 15);
        }
        // Painting the ghosts and the player
        for (int i = 0; i < ghosts.size(); i++) {
            Ghost tempGhost = (Ghost) ghosts.get(i);
            tempGhost.paintComponent(g);
        }
        FillGrid.thePlayer.paintComponent(g);
    }

    // Resets all the game pieces and relevant variables to the start conditions of the game.
    public void resetAll(){
        TheGame.theScore = 0;
        // Reset the exlcusionArray and refill the grid.
        FillGrid.exclusionArray.clear();
        gameItems = new FillGrid();
        // Reinitialize ghosts and the player.
        FillGrid.wooBlue.xPosition = 19;
        FillGrid.wooBlue.yPosition = 19;
        FillGrid.wooGreen.xPosition = 19;
        FillGrid.wooGreen.yPosition = 0;
        FillGrid.wooMagenta.xPosition = 0;
        FillGrid.wooMagenta.yPosition = 19;
        FillGrid.thePlayer.xPos = 0;
        FillGrid.thePlayer.yPos = 0;
        FillGrid.thePlayer.lives = 3;
        // Reset all game time and score variables.
        TheGame.theScore = 0;
        TheGame.isGameOver = false;
        TheGame.isPaused = false;
        TheGame.startTime = System.currentTimeMillis();
        TheGame.powerWindow = 0;
        SidePanel.displayBox.setText("The Score: " + TheGame.theScore);
    }


    // Public class implementing KeyListener and MouseListener, implemented within this class so as to make calls to repaint(); on movement.
    public class PlayerMovement implements KeyListener, MouseListener {

        @Override
        // If the key is pressed, retrieve player pacMan from the global shapes collection, update coordinate, replace in arrayList, then repaint.
        public void keyPressed(KeyEvent e) {

            // Attempt to move player and return if an ArrayIndexOutOfBoundsException is thrown for an illegal move.
            try {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    // If the space the player is trying to move into is not a wall, or false, then update the player position.
                    if (FillGrid.wallValues[FillGrid.thePlayer.yPos][FillGrid.thePlayer.xPos + 1] == false) {

                        // Move PacMan, check collisions, remove any edibles, then call repaint, which repopulates board and paints remaining objects.
                        FillGrid.movePacMan('x', 1, "right");
                        repaint();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (FillGrid.wallValues[FillGrid.thePlayer.yPos][FillGrid.thePlayer.xPos - 1] == false) {

                        FillGrid.movePacMan('x', -1, "left");
                        repaint();

                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (FillGrid.wallValues[FillGrid.thePlayer.yPos + 1][FillGrid.thePlayer.xPos] == false) {

                        FillGrid.movePacMan('y', 1, "down");
                        repaint();

                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (FillGrid.wallValues[FillGrid.thePlayer.yPos - 1][FillGrid.thePlayer.xPos] == false) {

                        FillGrid.movePacMan('y', -1, "up");
                        repaint();

                    }
                }
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                return;
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Method stub
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // Method stub
        }

        // Implementing the the methods of MouseListener interface
        // This method is called after click on the component.
        public void mouseClicked(MouseEvent e) {

            // Rotate PacMan by 90 degrees if the left mouse button is clicked.
            if (SwingUtilities.isLeftMouseButton(e)) {
                FillGrid.thePlayer.rotate(90);
                repaint();
            }

            // Checks orientation of PacMan to determine if PacMan can move into an empty (a non-wall) space
            else if (SwingUtilities.isRightMouseButton(e)) {
                // If an ArrayIndexOutOfBoundsException occurs, do nothing, as player has requested an invalid movement
                try {
                    // Checks PacMan rotation and tried to move in that direction (left, up, right, down).
                    if (FillGrid.thePlayer.startAngle == 135) {
                        if (FillGrid.wallValues[FillGrid.thePlayer.yPos][FillGrid.thePlayer.xPos - 1] == false) {
                            FillGrid.movePacMan();
                            repaint();
                        }
                    }
                    // Try to move up.
                    else if (FillGrid.thePlayer.startAngle == 45) {
                        if (FillGrid.wallValues[FillGrid.thePlayer.yPos - 1][FillGrid.thePlayer.xPos] == false) {
                            FillGrid.movePacMan();
                            repaint();
                        }
                    }
                    // Try to move right.
                    else if (FillGrid.thePlayer.startAngle == -45) {
                        if (FillGrid.wallValues[FillGrid.thePlayer.yPos][FillGrid.thePlayer.xPos + 1] == false) {
                            FillGrid.movePacMan();
                            repaint();
                        }
                    }
                    // Try to move down.
                    else if (FillGrid.thePlayer.startAngle == -135) {
                        if (FillGrid.wallValues[FillGrid.thePlayer.yPos + 1][FillGrid.thePlayer.xPos] == false) {
                            FillGrid.movePacMan();
                            repaint();
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    // Do nothing, because move was invalid.
                }


            }

            // If the middle mouse button is pressed and a power up has been collected, consume powerup and switch ghost : PacMan edible states.
            else if (SwingUtilities.isMiddleMouseButton(e)) {
                if (FillGrid.thePlayer.powerUpCount > 0) {
                    FillGrid.thePlayer.powerUpCount -= 1;
                    // Needs timer logic to reset.
                    Ghost.isEdible = !Ghost.isEdible;
                }
                FillGrid.thePlayer.afraidOfEctoplasm = false;
            }
        }

        // Call when mouse is pressed over component
        public void mousePressed(MouseEvent e) {
            // Method stub

        }

        // Called when the mouse button is released over component windows.
        public void mouseReleased(MouseEvent e) {
            // Method stub
        }

        // Called when mouse enters the current component window
        public void mouseEntered(MouseEvent e) {

        }

        // Called when mouse exits the window of the component
        public void mouseExited(MouseEvent e) {
            // Method stub
        }
    }

    // Implements buttons for resetting or pausing the game state on the main panel.
    public class ButtonHandler implements ActionListener {

        // Type of GamePanel which is set in the constructor to an instance of the GamePanel class via the keyword this.
        GamePanel theApp;

        // Assigning theApp to the GamePanel passed via this.
        public ButtonHandler(GamePanel app) {
            this.theApp = app;
        }

        // Pause or reset the game, depending on which button was pressed.
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == theApp.newGame) {
                // Call to method which resets all pieces to game start conditions.
                resetAll();
            }
            if (e.getSource() == theApp.pause) {
                TheGame.isPaused = !TheGame.isPaused;
            }
        }
    }
}







