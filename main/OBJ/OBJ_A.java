package main.OBJ;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_A extends SuperObject{

    GamePanel gp;

    public OBJ_A(GamePanel gp){

        this.gp = gp;

        name = "A";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/res/object/a.png"));
            assert image != null;
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
