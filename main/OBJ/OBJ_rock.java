package main.OBJ;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_rock extends SuperObject{

    GamePanel gp;

    public OBJ_rock(GamePanel gp){

        this.gp = gp;

        name = "rock";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/object/bato.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
