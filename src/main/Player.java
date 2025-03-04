package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int hasKey = 0;
    EventHandler eHandler;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage(){

        up1 = setup("/player/1W");
        up2 = setup("/player/2W");
        down1 = setup("/player/1S");
        down2 = setup("/player/2S");
        left1 = setup("/player/1A");
        left2 = setup("/player/2A");
        right1 = setup("/player/1D");
        right2 = setup("/player/2D");

    }

    public void update(){

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
            } else if(keyH.downPressed){
                direction = "down";
            } else if (keyH.leftPressed){
                direction = "left";
            } else if (keyH.rightPressed){
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            int glitchIndex = gp.cChecker.checkEntity(this, gp.monster);
            interactGlitch(glitchIndex);

            int qIndex = gp.cChecker.checkEntity(this, gp.queen);
            interactQueen(qIndex);


            gp.eHandler.checkEvent();

            if(!collisionOn && !gp.keyH.spacePressed){
                switch (direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;

                }
            }

            gp.keyH.spacePressed = false;

            spriteCounter++;
            if(spriteCounter>12){
                if(spriteNum == 1){
                    spriteNum = 2;
                } else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i){
        if (i != 999){
            String objectName = gp.obj[i].name;

            switch(objectName){
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    System.out.println("Key: " +hasKey);
                    break;
                case "Door":
                    if(hasKey>0){
                        gp.obj[i] = null;
                        hasKey--;
                        System.out.println("Key: " +hasKey);
                    }
                    break;
            }

        }
    }

    public void interactNPC(int i){
        if (i != 999){

            if(gp.keyH.spacePressed) {
                gp.gameState = gp.dialogState;
                gp.npc[i].speak(); //susi para mag-usap
            }
        }
    }

    public void interactGlitch(int i){
        if (i != 999){

            if(gp.keyH.spacePressed) {
                gp.gameState = gp.dialogState;
                gp.monster[i].speak();
            }
            if (gp.monster[i].life <= 0){
                gp.monster[i].dying = true;
            }
        }
    }

    public void interactQueen(int i){
        if (i != 999){

            if(gp.keyH.spacePressed) {
                gp.gameState = gp.dialogState;
                gp.queen[i].speak();
            }
        }
    }


    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY,null);
    }

}