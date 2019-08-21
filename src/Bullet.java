import javax.swing.*;

public class Bullet{
    ImageIcon iconBullet = new ImageIcon(getClass().getResource("bullet.png"));
    JLabel lBullet = new JLabel(iconBullet);
    int posX = 0, posY = 0;

    public JLabel getlBullet() {
        return lBullet;
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
