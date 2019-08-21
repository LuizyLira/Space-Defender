import javax.swing.*;

public class Alien {
    ImageIcon iconAlien1 = new ImageIcon(getClass().getResource("alien.gif"));
    JLabel lAlien1 = new JLabel(iconAlien1);
    int posX = 0, posY = 0;

    public Alien(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
    }

    public JLabel getlAlien1() {
        return lAlien1;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
