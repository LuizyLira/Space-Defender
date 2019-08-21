import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class Engine{

    ArrayList<Bullet> listOfBullet = createListOfBullets();
    boolean lineHitFlag = false, runningGame = false, scoreFrameB = false;
    Defender defender = new Defender();
    Hashtable<Integer, ArrayList<Alien>> hashtableOfAliens = new Hashtable<>();// = createMatrixOfAliens(100);
    int width = 500, height = 700, bulletInUse = 0, eliminations = 0, alienSpeed = 22, qntOfAliens;
    JLabel lBackground = new JLabel(new ImageIcon(getClass().getResource("background.gif")));
    JLabel lLine = new JLabel(new ImageIcon(getClass().getResource("line.png")));
    JLabel lGg = new JLabel(new ImageIcon(getClass().getResource("gameOver.gif")));
    JLabel lMenu = new JLabel(new ImageIcon((getClass().getResource("menu.png"))));
    JLabel lLogo = new JLabel(new ImageIcon((getClass().getResource("logo.png"))));

    //adiciona todas as 15 munições no frame, mas não torna visível
    public void addBullets(JFrame frame)
    {
        Bullet bullet;
        for (int i = 0; i < 15; i++) {
            bullet = listOfBullet.get(i);
            if(bullet == null) System.out.println("eita!");
            frame.add(bullet.getlBullet());
        }
    }
    //adiciona todos os aliens no frame, mas não torna visível
    {
        ArrayList<Alien> list;

        for (int i = 1; i <= 8; ++i)
        {
            list = hashtableOfAliens.get(i);
            for (int j = 0; j < list.size(); j++)
            {
                frame.add(list.get(j).getlAlien1());
            }
        }
        frame.add(lBackground);
    }

    //Controla o movimento do defensor e quando preciso o comando para o menu
    public void defenderControl(JFrame frame){
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode())
                {
                    case 37:
                        if(defender.getPosX() > 0)
                            defender.setPosX(-20);
                        break;
                    case 39:
                        if(defender.getPosX() < width - 70)
                            defender.setPosX(20);
                        break;
                }
                defender.getlDefender().setBounds(defender.getPosX(), defender.getPosY(), 70,70);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println(e.getKeyCode());
                if (e.getKeyCode() == 32){
                    System.out.println(e.getKeyCode());
                    BulletMovement bulletMove = new BulletMovement();
                    bulletMove.start();
                }
                if (e.getKeyCode() == 49 && !runningGame)
                {
                    System.out.println("iniciou");
                    lLogo.setVisible(false);
                    scoreFrameB = false;
                    lineHitFlag = false;
                    runningGame = true;
                    eliminations = 0;
                }
                if (e.getKeyCode() == 50 && !runningGame)
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    //Movimenta todos os aliens gerados e se ele estiver dentro dos limites do frame torna o alien visivel. Tsmbém
    // gerencia a colisão do alien com a linha verde
    public class AlienMove extends Thread
    {
        public void run(){
            ArrayList<Alien> list;
            Alien alien;

            for (int i = 1; i <= 8; i++)
            {
                list = hashtableOfAliens.get(i);
                for (int j = 0; j < list.size(); j++)
                {
                    try{sleep(5);}catch (Exception error){}
                    alien = list.get(j);
                    if(alien.getPosY() != -999)
                        alien.setPosY(alien.getPosY() + 20);
                    if(alien.getPosY() + 38 >= height - 100)
                    {
                        lineHitFlag = true;
                        break;
                    }
                    else if(alien.getPosY() > 0)
                        alien.getlAlien1().setBounds(alien.getPosX(), alien.getPosY(), 38, 38);
                }
            }
        }
    }
    //Movimenta todas as 15 munições e pede a verifição da colisão da munição com os aliens visíveis
    public class BulletMovement extends Thread{
        public void run(){
            if(bulletInUse < 15)
            {
                Bullet bullet = listOfBullet.get(bulletInUse);
                bulletInUse++;
                bullet.lBullet.setVisible(true);
                int x = defender.getPosX();
                bullet.setPosX(x);
                for(int i = height - 70; i > 0; i-=5){
                    try{sleep(5);}catch (Exception error){}
                    bullet.lBullet.setBounds(x + 29, i,10,10);
                    bullet.setPosY(i);
                    if (bulletCollision(bullet) == true)
                        break;
                }
                bulletInUse--;
                bullet.lBullet.setVisible(false);
            }
        }
    }

    //verifica se a colisão da munição com os aliens visíveis
    public boolean bulletCollision(Bullet bullet)
    {
        ArrayList<Alien> aliens;
        Alien alien;
        boolean flag = false;
        for (int i = 1; i <= 8; i++) {
            aliens = hashtableOfAliens.get(i);
            for (int j = 0; j < aliens.size(); j++) {
                alien = aliens.get(j);
                if (bullet.getPosY() <= alien.getPosY() + 38)
                {
                    if ((bullet.getPosX() >= alien.getPosX() - 58 && bullet.getPosX() <= alien.getPosX() + 20) ||
                            (bullet.getPosX() + 10 >= alien.getPosX() - 58 &&
                                    bullet.getPosX() + 10 <= alien.getPosX() + 20))
                    {
                        bullet.getlBullet().setVisible(false);
                        alien.getlAlien1().setVisible(false);
                        System.out.println("Alien atingido em: X = " + alien.getPosX() + ", Y = " + alien.getPosY());
                        alien.setPosX(-999);
                        alien.setPosY(-999);
                        alien.getlAlien1().setBounds(alien.getPosX(), alien.getPosY(), 10, 0);
                        System.out.println("Caso 1");
                        eliminations++;
                        System.out.println("acertos: " + eliminations);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //gera aleatoriamente todos os aliens da partida
    public Hashtable<Integer, ArrayList<Alien>> createMatrixOfAliens(int qntOfAliens)
    {
        int x, y;
        ArrayList<Alien> list;
        Alien alien;
        Random generator = new Random();
        Hashtable<Integer, ArrayList<Alien>> hashtable = new Hashtable<Integer, ArrayList<Alien>>();

        hashtable.put(1, new ArrayList<Alien>());
        hashtable.put(2, new ArrayList<Alien>());
        hashtable.put(3, new ArrayList<Alien>());
        hashtable.put(4, new ArrayList<Alien>());
        hashtable.put(5, new ArrayList<Alien>());
        hashtable.put(6, new ArrayList<Alien>());
        hashtable.put(7, new ArrayList<Alien>());
        hashtable.put(8, new ArrayList<Alien>());

        for (int i = 1; i <= qntOfAliens; ++i) {
            x = generator.nextInt(8) + 1;
            list = hashtable.get(x);
            y = i * -58;
            alien = new Alien((x-1) * 58, y);
            list.add(alien);
            hashtable.put(x, list);
        }
        return hashtable;
    }

    //cria uma lista das munições
    public static ArrayList<Bullet>  createListOfBullets()
    {
        ArrayList<Bullet> listOfBullet = new ArrayList<>();
        for (int i = 0; i < 15; i++)
        {
            listOfBullet.add(new Bullet());
        }
        return listOfBullet;
    }

    //limapa elementos da tela
    public void clearWindow()
    {
        ArrayList<Alien> list;
        Alien alien;
        for (int i = 1; i <= 8; i++)
        {
            list = hashtableOfAliens.get(i);
            for (int j = 0; j < list.size(); j++)
            {
                alien = list.get(j);
                alien.getlAlien1().setBounds(-50,-50, 0, 0);
            }
        }
        lMenu.setVisible(false);
        lLogo.setVisible(false);
    }
}
