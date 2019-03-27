import javax.swing.JFrame;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View extends JFrame implements ActionListener {
	public static int smallest;
	public static int height = 480;
	public static int width = 640;
	public static MyPanel panel;
	
    private class MyPanel extends JPanel {
        App app;

        MyPanel(App a) {
            app = a;
            addMouseListener(a);
        }

        public void paintComponent(Graphics g) {
            app.update(g);
            revalidate();
        }
    }


    public View(App a) throws Exception{
    	panel = new MyPanel(a);
        setTitle("Threats");
        getContentPane().add(panel);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(a);
        smallest = panel.getHeight();
    }

    public void actionPerformed(ActionEvent evt) {
        repaint();
    }
}