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
        if(hit(6, 18, "up")){a(gp.dialogState);}
        if(hit(8, 18, "up")){b(gp.dialogState);}
        if(hit(6, 20, "up")){c(gp.dialogState);}
        if(hit(8, 20, "up")){d(gp.dialogState);}
        if(hit(40, 10, "down")){damagePit(gp.dialogState);}
        if(hit(10, 2, "right")){gA(gp.dialogState);}
        if(hit(10, 4, "right")){gB(gp.dialogState);}
        if(hit(8, 3, "right")){gC(gp.dialogState);}

        if(intellect == 4){
            if(hit(7, 21, "down")){teleport(gp.dialogState);}
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
        gp.ui.currentDialog = "Want to proceed to next round?\nI will teleport you!\nPress space then press S";

        if (gp.keyH.spacePressed) {
            gp.ui.currentDialog = "Beware: Glitch is here";
            gp.player.worldX = gp.tileSize * 5;
            gp.player.worldY = gp.tileSize * 3;
        }
    }


    public void a(int gameState) {
        gp.gameState = gameState;

        if (intellect == 1) {
            gp.ui.currentDialog = "Number 1\nA. English\n Press Space then press W to lock in\n Press space only to step out";

            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else if (intellect == 2){
            gp.ui.currentDialog = "Number 2\nA. 1 and 5\n Press Space then press W to lock in\n Press space only to step out";

            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else if (intellect == 3){
            gp.ui.currentDialog = "Number 3\nA. By converting to binary\n Press Space then press W to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Correct!";
                gp.player.life = gp.player.maxLife;
                intellect++;
                System.out.println(intellect);
            }
        } else {
            gp.ui.currentDialog = "Done";
        }
    }

    public void b(int gameState) {

        gp.gameState = gameState;

        if(intellect == 1) {
            gp.ui.currentDialog = "Number 1\nB. Filipino\n Press Space then press W to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else if (intellect == 2){
            gp.ui.currentDialog = "Number 2\nB. 0 and 1\n Press Space then press W to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Correct!";
                gp.player.life = gp.player.maxLife;
                intellect++;
                System.out.println(intellect);
            }
        } else if (intellect == 3){
            gp.ui.currentDialog = "Number 3\nB. By converting to decimal\n Press Space then press W to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect!";
                gp.player.life -= 1;
            }
        } else {
            gp.ui.currentDialog = "Done";
        }
    }

    public void c(int gameState) {
        gp.gameState = gameState;

        if(intellect == 1) {
            gp.ui.currentDialog = "Number 1\nC. Binary\n Press Space then press W to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Correct!";
                gp.player.life = gp.player.maxLife;
                intellect++;
                System.out.println(intellect);
            }
        } else if (intellect ==  2){
            gp.ui.currentDialog = "Number 2\nC. 1 and 9\n Press Space then press W to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Inorrect!";
                gp.player.life -= 1;
            }
        } else if (intellect == 3){
            gp.ui.currentDialog = "Number 3\nC. By converting to fraction\n Press Space then press W to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Inorrect!";
                gp.player.life -= 1;
            }
        } else {
            gp.ui.currentDialog = "Done";
        }
    }

    public void d(int gameState) {

        gp.gameState = gameState;

        if (intellect == 1) {
            gp.ui.currentDialog = "Number 1\nD. Spanish\n Press Space then press W to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else if (intellect == 2){
            gp.ui.currentDialog = "Number 2\nD. 10 and 11\n Press Space then press W to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else if (intellect == 3){
            gp.ui.currentDialog = "Number 3\nThere is no letter D\n Press Space then press W to lock in\n Press space only to step out";
            if (gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Incorrect";
                gp.player.life -= 1;
            }
        } else {
            gp.ui.currentDialog = "Done";
        }
    }

    public void gA(int gameState) {
        gp.gameState = gameState;

        if(intellect == 4){
            gp.ui.currentDialog = "Number 1\nA. It's perfectly safe to share your home\n address with strangers online if they seem\nfriendly.";
        } else if (intellect == 5){
            gp.ui.currentDialog = "Number 2\nA. Report the content to the platform and \nconsider blocking the user.";
            if(gp.keyH.spacePressed) {
                gp.ui.currentDialog = "Correct";
                intellect++;
                if (gp.monster[0] != null) {
                    gp.monster[0].life -= 1;
                }
            }
        } else if (intellect == 6){
            gp.ui.currentDialog = "Number 3\nA. Yes, using the same password for all\n accounts makes it easier to remember.";
        } else if (intellect == 7){
            gp.ui.currentDialog = "Number 4\nA. It depends on whether the link is\n from a known website or not.";
        } else if (intellect == 8){
            gp.ui.currentDialog = "Number 5\nA. No, it’s probably a scam trying to\n steal your personal information.";
            if(gp.keyH.spacePressed) {
                intellect++;
                if (gp.monster[0] != null) {
                    gp.monster[0].life -= 1;
                }
            }
        }
    }

    public void gB(int gameState) {
        gp.gameState = gameState;

        if(intellect == 4){
            gp.ui.currentDialog = "Number 1\nB. You should never share your home address\n with strangers online, as it can lead to privacy\n breaches or security risks.";
            if(gp.keyH.spacePressed) {
                intellect++;
                if (gp.monster[0] != null) {
                    gp.monster[0].life -= 1;
                }
            }
        } else if (intellect == 5){
            gp.ui.currentDialog = "Number 2\nB. Share the content with your friends \nto get their opinion.";
        } else if (intellect == 6){
            gp.ui.currentDialog = "Number 3\nB. Only use the same password for accounts\n that don’t contain sensitive information.";
        } else if (intellect == 7){
            gp.ui.currentDialog = "Number 4\nB. No, clicking random links can expose you\n to malware or phishing attempts.";
            if(gp.keyH.spacePressed) {
                intellect++;
                if (gp.monster[0] != null) {
                    gp.monster[0].life -= 1;
                }
            }
        } else if (intellect == 8){
            gp.ui.currentDialog = "Number 5\nB. Yes, if it looks legitimate, it’s likely\n true.";
        }
    }

    public void gC(int gameState){
        gp.gameState = gameState;

        if(intellect == 4){
            gp.ui.currentDialog = "Number 1\nC. It's okay to share your home address\nonline if you're purchasing somethingn\nfrom a reputable website.";
        }  else if (intellect == 5){
            gp.ui.currentDialog = "Number 2\nC. Ignore it and continue browsing; it’ll\n probably go away soon.";
        } else if (intellect == 6){
            gp.ui.currentDialog = "Number 3\nC. No, it's important to have unique passwords\n for each account to improve security.";
            if(gp.keyH.spacePressed) {
                intellect++;
                if (gp.monster[0] != null) {
                    gp.monster[0].life -= 1;
                }
            }
        } else if (intellect == 7){
            gp.ui.currentDialog = "Number 4\nC. Yes, clicking random links can lead\n to interesting websites and offers.";
        } else if (intellect == 8){
            gp.ui.currentDialog = "Number 5\nC. Only if you remember buying a lottery\n ticket recently.";
        }
    }




}
