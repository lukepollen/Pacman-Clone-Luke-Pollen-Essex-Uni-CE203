package ce203AssignmentTwo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// SidePanel for buttons to select all time and last twenty four hour scores with an area to display them in.
public class SidePanel extends JPanel {

    // Text Area to display scores on.
    public static JTextArea displayBox;
    // Buttons for retrieving the scores.
    public static JButton lastTwentyFour, allTime;
    // ArrayLists which will hold the names and the scores for displaying at end of round, or when requested.
    static ArrayList<Integer> theScores = new ArrayList<Integer>();
    // Time boundary to distinguish scores further back in time than the current system time minus 86400 seconds (one day)
    static long lowestTimeBoundary;
    // Boolean type which is updated on button press to determine whether the lowestTimeBoundary is set to either zero for all time or the last day.
    static boolean isTwentyFour;

    // Instatiate the side panel.
    public SidePanel() {

        // Instantiating the text area and setting word wrap.
        displayBox = new JTextArea();
        displayBox.setWrapStyleWord(true);
        displayBox.setLineWrap(true);

        // Instantiating and adding listeners to the buttons.
        lastTwentyFour = new JButton("Scores: Last Day");
        allTime = new JButton("Scores: All Time");
        lastTwentyFour.addActionListener(new SideButtonHandler(this));
        allTime.addActionListener(new SideButtonHandler(this));
        lastTwentyFour.setPreferredSize(new Dimension(200, 100));
        allTime.setPreferredSize(new Dimension(200, 100));

        //adds the score to the display box.
        displayBox.append("Current Score: " + Integer.toString(TheGame.theScore));
        displayBox.setPreferredSize(new Dimension(200, 400));

        // Adding each element to the frame with updates to the GridBagConstraints to ensure correct positioning.
        setLayout(new GridBagLayout());
        setSize(new Dimension(200, 600));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(allTime, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(lastTwentyFour, gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(displayBox, gbc);
    }

    // ActionListener for the score buttons implemented in the sidepanel.
    public class SideButtonHandler implements ActionListener {

        // Set to refer to the SidePanel when the instance is passed to SideButtonHandler's constructor via this.
        SidePanel theApp;

        // Setting theApp to the instance of SidePanel passed by the keyword this.
        public SideButtonHandler(SidePanel app) {

            this.theApp = app;
        }

        // Implementation of actionPerformed for the buttons to make them responsive.
        public void actionPerformed(ActionEvent e) {

            // Call displayScores() with the appropriate lower time boundary for score retrieval, given the button pressed, and display top ten scores.
            if (e.getSource() == theApp.lastTwentyFour) {
                isTwentyFour = true;
                TrackScores.displayScores();
            }
            // Call displayScores() and display the top then all time scores.
            if (e.getSource() == theApp.allTime) {
                isTwentyFour = false;
                TrackScores.displayScores();
            }
        }
    }
}

