import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Stage extends Engine{

    JFrame frame = new JFrame();
    JLabel lFont = new JLabel();

    public Stage()
    {
        editWindow();
        showInit();
        phase();
    }
    //atualiza o frame principal para mostrar a quantidade de aliens eliminados e o menu
    public void scoreFrame(){
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("PixelFont.TTF"));
            lFont.setFont(font.deriveFont(Font.BOLD, 20f));
        }catch (Exception e){}
        frame.add(lFont);
        frame.add(lMenu);
        frame.add(lBackground);
        lFont.setForeground(Color.WHITE);
        lFont.setBounds(100,200,width,100);
        lMenu.setBounds(50,300,400,320);
        lFont.setText("Parabens, voce eliminou: " + eliminations + " alienigenas.");
        lFont.setVisible(true);
        lMenu.setVisible(true);

    }

    //gerencia o que tem de aparecer no frame e o que acontece na partida
    public void phase()
    {

        while(true)
        {
            if (!runningGame) {
                showInit();
            }
            else {
                clearWindow();
                while (true) {
                    if(eliminations % 5 == 0) {
                        alienSpeed--;
                    }

                    if (lineHitFlag || eliminations == 100) {
                        clearWindow();
                        runningGame = false;
                        scoreFrameB = true;
                        alienSpeed = 22;
                        scoreFrame();
                        while (scoreFrameB)
                        {
                            System.out.println("");
                            if(!scoreFrameB)
                            {
                                lFont.setVisible(false);
                                lMenu.setVisible(false);
                                lineHitFlag = false;
                                scoreFrameB = false;
                            }
                        }
                        hashtableOfAliens.clear();
                        hashtableOfAliens = createMatrixOfAliens(100);
                        addAliens(frame);
                    }
                    else
                    {
                        AlienMove alienMove = new AlienMove();
                        alienMove.run();
                    }
                }

            }
        }
        }

    //mostra a tela inicial do jogo
    public void showInit()
    {
//        hashtableOfAliens.clear();

        eliminations = 0;
        hashtableOfAliens = createMatrixOfAliens(100);
        addAliens(frame);
        frame.add(lBackground);
        lMenu.setVisible(true);
        lLogo.setVisible(true);

        lLogo.setBounds(0,0, 408,132);
        lMenu.setBounds(50,200, 400,320);
    }

    //adiciona os elementos no frame
    public void editWindow()
    {

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.add(defender.getlDefender());
        defender.getlDefender().setBounds(defender.getPosX(), defender.getPosY(), 70, 70);
        addBullets(frame);
//        addAliens(frame);
        frame.add(lLine);
        lLine.setBounds(0, height - 100, width, 5);
        frame.add(lGg);
        frame.add(lLogo);
        frame.add(lMenu);
        frame.add(lBackground);
        defenderControl(frame);
        lBackground.setBounds(0,0,width,height);


    }

    public static void main(String[] args) {

        new Stage();

    }
}
