package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Glitch extends Entity {

    public Glitch(GamePanel gp) {
        super(gp);

        name = "Glitch";
        speed = 0; // Set speed to 0 to prevent movement
        maxLife = 5;
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
        dialogs[1] = "This is much more difficult compared to\nhow they prepared you!";
        dialogs[2] = "1. Should you share your home address with\n strangers online?";
        dialogs[3] = "2. What should you do if you see something\n online that makes you uncomfortable?";
        dialogs[4] = "3. Is it okay to use the same password for all\n your accounts?";
        dialogs[5] = "4. Should you click random links?";
        dialogs[6] = "5. If a notification popped up saying you won\n the lottery, would you beliveve it?";
    }

    public void speak() {
        super.speak();
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    if (spriteNum == 3) {
                        image = down3;
                    }
                    if (spriteNum == 4) {
                        image = down4;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            // Draw the image with scaling
            g2.drawImage(image, screenX, screenY, (int)(gp.tileSize * 2), (int)(gp.tileSize * 2), null);

            // Draw the health bar
            drawHealthBar(g2);

            if(dying){
                dyingAnimation(g2);
            }
        }
    }

    private void drawHealthBar(Graphics2D g2) {
        // Draw the health bar
        int healthBarWidth = 64; // Adjusted for the larger size
        int healthBarHeight = 10; // Adjusted for the larger size
        int healthBarX = worldX - gp.player.worldX + gp.player.screenX + (gp.tileSize * 2 - healthBarWidth) / 2;
        int healthBarY = worldY - gp.player.worldY + gp.player.screenY - 15; // Position above the entity

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
