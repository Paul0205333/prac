package main.OBJ;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_C extends SuperObject{

    GamePanel gp;

    public OBJ_C(GamePanel gp){

        this.gp = gp;

        name = "C";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/object/c.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
