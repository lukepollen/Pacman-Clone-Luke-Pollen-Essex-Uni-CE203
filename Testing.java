package ce203AssignmentTwo;

import javax.swing.*;
import java.awt.*;

public class Testing extends JFrame {

    public Testing() {

        setLayout(new BorderLayout());

        TestPanel test = new TestPanel();
        add(test);

        // Setting size and visibility.
        setSize(1200, 1000);
        setVisible(true);
        repaint();

    }
}






