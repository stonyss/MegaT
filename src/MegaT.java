import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MegaT {

    private JFrame frame;
    private JPanel panel;

    // Optionally set the look and feel.
    private static boolean useSystemLookAndFeel = true;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    MegaT window = new MegaT();
		    window.frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public MegaT() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

	if (useSystemLookAndFeel) {
	    try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {
		System.err.println("Couldn't use system look and feel.");
	    }
	}

	//JFrame.setDefaultLookAndFeelDecorated(true);
	frame = new JFrame("Mega Template");
	panel = new StaticContentPane();
	//Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension d = new Dimension(1280, 800);
	frame.add(panel);
	frame.setSize(d);
	// frame.pack();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
