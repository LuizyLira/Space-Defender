import javax.swing.*;
import java.awt.event.*;

public class Defender {
    private ImageIcon iconDefender = new ImageIcon(getClass().getResource("defender.gif"));
    private JLabel lDefender = new JLabel(iconDefender);
    int posX = 250, posY = 600;




    public JLabel getlDefender() {
        return lDefender;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX += posX;
    }

    public void setPosY(int posY) {
        this.posY += posY;
    }
}
