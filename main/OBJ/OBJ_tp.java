package main.OBJ;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_tp extends SuperObject{

    GamePanel gp;

    public OBJ_tp(GamePanel gp){

        this.gp = gp;

        name = "TP";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/object/t.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
