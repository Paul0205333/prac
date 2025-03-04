package main;

import main.OBJ.OBJ_Heart;
import main.OBJ.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage heartFull, heartHalf,heartBlank;

    public String currentDialog = "";

    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 38);

        SuperObject heart = new OBJ_Heart(gp);
        heartFull = heart.image;
        heartHalf = heart.image2;
        heartBlank = heart.image3;

    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawIntellect();
        }
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawIntellect();
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogState){
            drawPlayerLife();
            drawIntellect();
            drawDialogScreen();
        }
        if(gp.gameState == gp.gameOverState){
            drawPlayerLife();
            drawIntellect();
            drawGameOverScreen();
        }
        if(gp.gameState == gp.accomplishedState){
            drawPlayerLife();
            drawIntellect();
            drawAccomplishedScreen();
            if(gp.keyH.spacePressed){
                gp.gameState = gp.playState;
            }
        }
    }

    public void drawPlayerLife(){

        //gp.player.life = 6;

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        while(i < gp.player.maxLife/2){
            g2.drawImage(heartBlank, x, y, null);
            i++;
            x+=gp.tileSize;
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        while( i < gp.player.life){
            g2.drawImage(heartHalf, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heartFull, x, y, null);
            }
            i++;
            x += gp.tileSize;

        }


    }

    public void drawIntellect(){
        String text = "Intellect: " + gp.eHandler.intellect;
        int x = gp.tileSize/2 + 3;
        float y = (float) (gp.tileSize/2 + gp.tileSize*1.5);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,22F));
        g2.drawString(text, x, y);
    }

    public void drawAccomplishedScreen(){
        String title = "MISSION";
        String subtitle = "ACCOMPLISHED";

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 100F));

        int titleLength = (int) g2.getFontMetrics().getStringBounds(title, g2).getWidth();
        int titleX = gp.screenWidth / 2 - titleLength / 2;
        int titleY = gp.screenHeight / 2 - 20;

        g2.drawString(title, titleX, titleY);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F)); // You can adjust the size if needed

        int subtitleLength = (int) g2.getFontMetrics().getStringBounds(subtitle, g2).getWidth();
        int subtitleX = gp.screenWidth / 2 - subtitleLength / 2;
        int subtitleY = titleY + 100;

        g2.drawString(subtitle, subtitleX, subtitleY);
        g2.dispose();
    }

    public void drawGameOverScreen(){
        String text = "GAME OVER";
        int x;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,100F));
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth/2 - length/2;
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
        g2.dispose();
    }

    public void drawPauseScreen(){

        String text = "PAUSED";
        int x;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth/2 - length/2;
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogScreen(){

        int x = gp.tileSize *2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26F ));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialog.split( "\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
}
