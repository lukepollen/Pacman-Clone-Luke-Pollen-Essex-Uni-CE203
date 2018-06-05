package ce203AssignmentTwo;

import java.io.*;
import java.util.Collections;

// Class with methods for adding and displaying scores read from the text file.
public class TrackScores {

    // Method for adding the current score to the text file. Called when player loses, or when the player wins.
    public static void addScore() {

        // Otherwise, convert score and currentTime to strings and then write a final string to the text file.
        String currentTime = Long.toString(System.currentTimeMillis());
        String writeString = System.getProperty("line.separator") + Integer.toString(TheGame.theScore) + " " + currentTime;
        // Attempt to write the current name, score and currentTime as a new row in scorex.txt, displaying warnings if file not found or on IOException
        try {
            File targetFile = new File("scores.txt");
            FileWriter theWriter = new FileWriter(targetFile, true);
            BufferedWriter theBufferedWriter = new BufferedWriter(theWriter);
            theBufferedWriter.write(writeString);
            theBufferedWriter.close();
            SidePanel.displayBox.setText("Added new score!");
        } catch (FileNotFoundException fnfe) {
            SidePanel.displayBox.setText("File not found! Please ensure scores file is in correct directory.");
        } catch (IOException ioe) {
            SidePanel.displayBox.setText("Could not write your score to the file!");
        }
    }

    // Displays the historic or last twenty four hour scores.
    public static void displayScores() {
        // If isTwentyFour is true, display scores for upto the last twenty four hours, else retrieve scores for all time.
        if (SidePanel.isTwentyFour) {
            SidePanel.displayBox.setText("Top Ten Scores - Last Day: ");
            SidePanel.lowestTimeBoundary = System.currentTimeMillis() - 86400000;
            readFile();
        }
        // Otherwise, display scores for all time.
        else {
            SidePanel.displayBox.setText("Top Ten Scores - All Time: ");
            SidePanel.lowestTimeBoundary = 0;
            readFile();
        }
        // Order the list of scores that was returned from file read.
        Collections.sort(SidePanel.theScores);
        Collections.reverse(SidePanel.theScores);
        // Try to update the main display box with ten scores or as many scores as found in the text file.
        int count = 0;
        try {
            // Prints a total number of names or scores up to the names in the score file or ten, whichever is lowest.
            while (count < SidePanel.theScores.size() && count < 10) {
                //String tempName = theNames.get(count);
                Integer tempScore = SidePanel.theScores.get(count);
                SidePanel.displayBox.append("\n" + (count + 1) + " : " + Integer.toString(tempScore));
                count++;
            }
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.out.println("Attempted to get " + count);
        }
        SidePanel.displayBox.append("\n" + "\n" + "Your Score: " + TheGame.theScore);
    }

    // Reads all scores in to memory, regardless of when they were recorded.
    public static void readFile() {

        // Empty the name and score lists for this current read of the scores text file.
        SidePanel.theScores.clear();

        // Taking the location as a String argument and trying to read the file to update arrays used in dynamic SQL string generation.
        try {
            File targetFile = new File("C:\\Users\\Cortica\\IdeaProjects\\ce203AssignmentTwo\\src\\ce203AssignmentTwo\\scores.txt");
            FileReader theReader = new FileReader(targetFile);
            BufferedReader theBufferedReader = new BufferedReader(theReader);
            String fileLine = theBufferedReader.readLine();
            // Keep adding strings to score ArrayList until the end of the file is each reached
            while ( fileLine != null) {
                // Split the string on one or more whitespace and then update each array with the fragments of the string.
                // Proceeds only if the score is not further in the past than the lowestTimeBoundary, which is zero for all time.
                String[] lineFragments = fileLine.split(" ");
                // Taking player score and the time of the score from the current line of the text file.
                Integer playerScore = Integer.parseInt(lineFragments[0]);
                long gameTime = Long.parseLong(lineFragments[1]);
                // If whenever the score was generated is greater than or equal to the cutoff point in the past, include the score.
                if (gameTime >= SidePanel.lowestTimeBoundary) {
                    SidePanel.theScores.add(playerScore);
                }
                fileLine = theBufferedReader.readLine();
            }
        }
        // Printing warnings for the user if the file was not found or an error occured reading the file.
        catch (FileNotFoundException fnfe) {
            SidePanel.displayBox.setText("Could not find file! Please check the scores file exists.");
        } catch (IOException ioe) {
            SidePanel.displayBox.setText("Could not read file! Was the scores file in .txt format?");
        }
    }

}
