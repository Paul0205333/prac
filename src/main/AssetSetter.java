package main;

import main.OBJ.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Axe(gp);
        gp.obj[0].worldX = 19 * gp.tileSize;
        gp.obj[0].worldY = 17 * gp.tileSize;

        gp.obj[1] = new OBJ_A(gp);
        gp.obj[1].worldX = 6 * gp.tileSize;
        gp.obj[1].worldY = 18 * gp.tileSize;

        gp.obj[2] = new OBJ_B(gp);
        gp.obj[2].worldX = 8 * gp.tileSize;
        gp.obj[2].worldY = 18 * gp.tileSize;

        gp.obj[3] = new OBJ_C(gp);
        gp.obj[3].worldX = 6 * gp.tileSize;
        gp.obj[3].worldY = 20 * gp.tileSize;

        gp.obj[4] = new OBJ_D(gp);
        gp.obj[4].worldX = 8 * gp.tileSize;
        gp.obj[4].worldY = 20 * gp.tileSize;

        gp.obj[5] = new OBJ_tp(gp);
        gp.obj[5].worldX = 7 * gp.tileSize;
        gp.obj[5].worldY = 21 * gp.tileSize;

        gp.obj[6] = new OBJ_rock(gp);
        gp.obj[6].worldX = 10 * gp.tileSize;
        gp.obj[6].worldY = 22 * gp.tileSize;

        gp.obj[7] = new OBJ_A(gp);
        gp.obj[7].worldX = 10 * gp.tileSize;
        gp.obj[7].worldY = 2 * gp.tileSize;

        gp.obj[8] = new OBJ_B(gp);
        gp.obj[8].worldX = 10 * gp.tileSize;
        gp.obj[8].worldY = 4 * gp.tileSize;

        gp.obj[9] = new OBJ_C(gp);
        gp.obj[9].worldX = 8 * gp.tileSize;
        gp.obj[9].worldY = 3 * gp.tileSize;

        gp.obj[10] = new OBJ_rock(gp);
        gp.obj[10].worldX = 3 * gp.tileSize;
        gp.obj[10].worldY = 12 * gp.tileSize;

        gp.obj[11] = new OBJ_rock(gp);
        gp.obj[11].worldX = 4 * gp.tileSize;
        gp.obj[11].worldY = 12 * gp.tileSize;
    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*7;
        gp.npc[0].worldY = gp.tileSize*17;
    }

    public void setMonster(){
        gp.monster[0] = new Glitch(gp);
        gp.monster[0].worldX = 14 * gp.tileSize;
        gp.monster[0].worldY = 2 * gp.tileSize;
    }

    public void setQueen(){
        gp.queen[0] = new Queen(gp);
        gp.queen[0].worldX = gp.tileSize*17;
        gp.queen[0].worldY = gp.tileSize*15;
    }

}
