import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {
    Image img;
    public Background(){
        img = Toolkit.getDefaultToolkit().createImage("background.gif");
    }
    public void paintComponent(Graphics g)
    {
        g.drawImage(img,0,0,null);
    }
}
