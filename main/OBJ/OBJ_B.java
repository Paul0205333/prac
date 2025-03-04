package main.OBJ;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_B extends SuperObject{

    GamePanel gp;

    public OBJ_B(GamePanel gp){

        this.gp = gp;

        name = "B";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/object/b.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
