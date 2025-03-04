package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;
    public int intellect = 1;


    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent(){
        if(hit(26, 15, "right")){damagePit(gp.dialogState);}
        if(hit(22, 7, "up")){healingPool(gp.dialogState);}
        if(hit(22, 20, "left")){a(gp.dialogState);}
        if(hit(22, 22, "left")){b(gp.dialogState);}
        if(hit(24, 20, "left")){c(gp.dialogState);}
        if(hit(24, 22, "left")){d(gp.dialogState);}
        if(hit(40, 10, "down")){damagePit(gp.dialogState);}

        if(intellect == 4){
            if(hit(26, 21, "right")){teleport(gp.dialogState);}
        }
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection){

        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect.x = eventCol*gp.tileSize + eventRect.x;
        eventRect.y = eventRow*gp.tileSize + eventRect.y;

        if(gp.player.solidArea.intersects(eventRect)){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    public void damagePit(int gameState){

        gp.gameState = gameState;
        gp.ui.currentDialog = "You fall into a pit";
        gp.player.life -= 1;

        if(gp.monster[0] != null) {
            gp.monster[0].life -= 1;
        }
    }

    public void healingPool(int gameState){

        if(gp.keyH.spacePressed){
            gp.gameState = gameState;
            gp.ui.currentDialog = "You drink the water.\nYour life has been recovered.";
            gp.player.life = gp.player.maxLife;
        }
    }

    public void teleport(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialog = "Want to proceed to next round?\nI will teleport you!\nPress space then press D";

        if (gp.keyH.spacePressed) {
            gp.ui.currentDialog = "Welcome to Mobile Legends";
            gp.player.worldX = gp.tileSize * 36;
            gp.player.worldY = gp.tileSize * 11;
        }
    }


    public void a(int gameState) {
        gp.gameState = gameState;

        if (intellect == 1) {
            gp.ui.currentDialog = "Number 1\nA. English\n Press Space then press A to lock in\n Press space only to step out";

            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else if (intellect == 2){
            gp.ui.currentDialog = "Number 2\nA. 1 and 5\n Press Space then press A to lock in\n Press space only to step out";

            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else if (intellect == 3){
            gp.ui.currentDialog = "Number 3\nA. By converting to binary\n Press Space then press A to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Correct!";
                gp.player.life = gp.player.maxLife;
                intellect++;
                System.out.println(intellect);
            }
        }
    }

    public void b(int gameState) {

        gp.gameState = gameState;

        if(intellect == 1) {
            gp.ui.currentDialog = "Number 1\nB. Filipino\n Press Space then press A to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else if (intellect == 2){
            gp.ui.currentDialog = "Number 2\nB. 0 and 1\n Press Space then press A to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Correct!";
                gp.player.life = gp.player.maxLife;
                intellect++;
                System.out.println(intellect);
            }
        } else if (intellect == 3){
            gp.ui.currentDialog = "Number 3\nB. By converting to decimal\n Press Space then press A to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect!";
                gp.player.life -= 1;
            }
        }
    }

    public void c(int gameState) {
        gp.gameState = gameState;

        if(intellect == 1) {
            gp.ui.currentDialog = "Number 1\nC. Binary\n Press Space then press A to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Correct!";
                gp.player.life = gp.player.maxLife;
                intellect++;
                System.out.println(intellect);
            }
        } else if (intellect ==  2){
            gp.ui.currentDialog = "Number 2\nC. 1 and 9\n Press Space then press A to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Inorrect!";
                gp.player.life -= 1;
            }
        } else if (intellect == 3){
            gp.ui.currentDialog = "Number 3\nC. By converting to fraction\n Press Space then press A to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Inorrect!";
                gp.player.life -= 1;
            }
        }
    }

    public void d(int gameState) {

        gp.gameState = gameState;

        if (intellect == 1) {
            gp.ui.currentDialog = "Number 1\nD. Spanish\n Press Space then press A to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else if (intellect == 2){
            gp.ui.currentDialog = "Number 2\nD. 10 and 11\n Press Space then press A to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else if (intellect == 3){
            gp.ui.currentDialog = "Number 3\nThere is no letter D\n Press Space then press A to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        }
    }

}
