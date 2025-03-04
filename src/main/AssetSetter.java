package main;

import main.OBJ.OBJ_Key;
import main.OBJ.OBJ_door;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        gp.obj[2] = new OBJ_door(gp);
        gp.obj[2].worldX = 10 * gp.tileSize;
        gp.obj[2].worldY = 11 * gp.tileSize;
    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }

    public void setMonster(){
        gp.monster[0] = new Glitch(gp);
        gp.monster[0].worldX = 39 * gp.tileSize;
        gp.monster[0].worldY = 10 * gp.tileSize;
    }

    public void setQueen(){
        gp.queen[0] = new Queen(gp);
        gp.queen[0].worldX = gp.tileSize*24;
        gp.queen[0].worldY = gp.tileSize*7;
    }

}
