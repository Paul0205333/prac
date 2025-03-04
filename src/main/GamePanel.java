package main;

import main.OBJ.SuperObject;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{


    UI ui;
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[10];
    public Entity queen[] = new Queen[2];



    //Game State
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        ui = new UI(this);
    }

    public void setupGame(){

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setQueen();
        aSetter.setMonster();

        gameState = playState;
    }


    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        double drawInterval = 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta>=1){
                update();
                repaint();
                delta --;
            }
        }
    }

    public void update(){

        if (gameState == playState) {
            player.update();

            for(int i = 0; i< monster.length; i++){
                if(monster[i] != null){
                    if(monster[i].alive && !monster[i].dying){
                        monster[i].update();
                    }
                    if(!monster[i].alive){
                        monster[i] = null;
                    }
                }
            }
        }
        if (gameState == pauseState) {

        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);

        //OBJ drawing
        for(int i = 0; i < obj.length; i++){
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        for (int i = 0; i < queen.length; i++) {
            if (queen[i] != null) {
                queen[i].draw(g2);
            }
        }

        player.draw(g2);
        //NPC drawing
        for(int i = 0; i<npc.length; i++){
            if(npc[i] != null){
                npc[i].draw(g2);
            }
        }

        ui.draw(g2);

        for(int i = 0; i<monster.length; i++){
            if(monster[i] != null){
                monster[i].draw(g2);
            }
        }
        g2.dispose();
    }



}