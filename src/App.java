import MemoryGame.*;
import javax.swing.*;

/**
 * Created by zoloe on 2016. 11. 25..
 */
public class App {
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Turn off metal's use of bold fonts
//                UIManager.put("swing.boldMetal", Boolean.FALSE);

                new MemoryGameFrame();
            }
        });
    }
}
