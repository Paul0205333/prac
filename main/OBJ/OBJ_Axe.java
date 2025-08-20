package main.OBJ;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Axe extends SuperObject{

    GamePanel gp;

    public OBJ_Axe(GamePanel gp){

        this.gp = gp;

        name = "Axe";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/object/axe.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
