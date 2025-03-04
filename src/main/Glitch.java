package main;

import java.awt.*;

public class Glitch extends Entity {

    public Glitch(GamePanel gp) {
        super(gp);

        name = "Glitch";
        speed = 0; // Set speed to 0 to prevent movement
        maxLife = 4;
        life = maxLife;
        direction = "down";

        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getImage();
        setDialog();
    }

    public void getImage() {
        down1 = setup("/res/enemy/g1");
        down2 = setup("/res/enemy/g2");
        down3 = setup("/res/enemy/g3");
        down4 = setup("/res/enemy/g4");
    }

    public void update() {
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            } else if (spriteNum == 3) {
                spriteNum = 4;
            } else if (spriteNum == 4) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void setDialog(){
        dialogs[0] = "Hello the chosen one!\nAre you ready to face me?";
        dialogs[1] = "When you step on a letter, it will remind\nyou which number you are currently answering\nNow let's begin";
        dialogs[2] = "1. What is the language of a computer?\nA. English       B. Filipino\nC. Binary        D. Spanish";
        dialogs[3] = "2. What two numbers are used in binary?\n A. 1 and 5       B. 0 and 1\nC. 1 and 9        D. 10 and 11";
        dialogs[4] = "3. How computers understand letters?\nA. By converting to binary\nB. By converting to decimal\nC. By converting to fraction";
        dialogs[5] = "Once you reach intellect 4\nYou will be able to teleport!";
    }

    public void speak() {
        super.speak();
    }

    @Override
    public void draw(Graphics2D g2) {
        // Call the superclass draw method to handle common drawing logic
        super.draw(g2);

        // Draw the health bar
        int healthBarWidth = 32;
        int healthBarHeight = 5;
        int healthBarX = worldX - gp.player.worldX + gp.player.screenX + (gp.tileSize - healthBarWidth) / 2;
        int healthBarY = worldY - gp.player.worldY + gp.player.screenY - 10; // Position above the entity

        // Draw the health bar background (slightly larger for the border effect)
        int borderWidth = 2; // Width of the border
        g2.setColor(new Color(35, 35, 35)); // Background color
        g2.fillRect(healthBarX - borderWidth, healthBarY - borderWidth,
                healthBarWidth + (2 * borderWidth), healthBarHeight + (2 * borderWidth));

// Draw the health bar foreground based on current life
        g2.setColor(new Color(255, 0, 30)); // Foreground color
        double healthPercentage = (double) life / maxLife; // Calculate health percentage
        int currentHealthWidth = (int) (healthBarWidth * healthPercentage); // Calculate current health width
        g2.fillRect(healthBarX, healthBarY, currentHealthWidth, healthBarHeight);
    }
}
