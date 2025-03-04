package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;



    public TileManager (GamePanel gp){
        this.gp = gp;

        tile = new Tile[100];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        
        getTileImage();
        loadMap("C:\\Users\\lored\\IdeaProjects\\Project\\Prac\\src\\res\\maps\\mapaNew.txt");
    }

    public void getTileImage(){

        setup(0, "0001", true);
        setup(1, "0002", true);
        setup(2, "0003", true);
        setup(3, "0004", true);
        setup(4, "0005", false);
        setup(5, "0006", true);
        setup(6, "0007", true);
        setup(7, "0008", false);
        setup(8, "0009", true);
        setup(9, "0010", true);
        setup(10, "0011", true);
        setup(11, "0012", true);
        setup(12, "0013", false);
        setup(13, "0014", false);
        setup(14, "0015", false);
        setup(15, "0016", false);
        setup(16, "0017", true);
        setup(17, "0018", true);
        setup(18, "0019", true);
        setup(19, "0020", true);
        setup(20, "0021", true);
        setup(21, "0022", true);
        setup(22, "0023", true);
        setup(23, "0024", true);
        setup(24, "0025", true);
        setup(25, "0026", true);
        setup(26, "0027", false);
        setup(27, "0028", false);
        setup(28, "0029", false);
        setup(29, "0030", true);
        setup(30, "0031", true);
        setup(31, "0032", false);
        setup(32, "0033", true);
        setup(33, "0034", true);
        setup(34, "0035", true);
        setup(35, "0036", true);
        setup(36, "0037", true);
        setup(37, "0038", false);
        setup(38, "0039", true);
        setup(39, "0040", true);
        setup(40, "0041", true);
        setup(41, "0042", false);
        setup(42, "0043", false);
        setup(43, "0044", false);
        setup(44, "0045", true);
        setup(45, "0046", true);
        setup(46, "0047", true);
        setup(47, "0048", false);
        setup(48, "0049", false);
        setup(49, "0050", false);
        setup(50, "0051", false);
        setup(51, "0052", true);
        setup(52, "0053", true);
        setup(53, "0054", true);
        setup(54, "0055", true);
        setup(55, "0056", true);
        setup(56, "0057", true);
        setup(57, "0058", false);
        setup(58, "0059", false);
        setup(59, "0060", false);
        setup(60, "0061", false);
        setup(61, "0062", false);
        setup(62, "0063", false);
        setup(63, "0064", false);
        setup(64, "0065", false);
        setup(65, "0066", false);
        setup(66, "0067", true);
        setup(67, "0068", true);
        setup(68, "0069", false);
        setup(69, "0070", false);
        setup(70, "0071", false);
        setup(71, "0072", false);
        setup(72, "0073", false);
        setup(73, "0074", false);
        setup(74, "0075", false);
        setup(75, "0076", false);
        setup(76, "0077", false);
        setup(77, "0078", false);
        setup(78, "0079", true);
        setup(79, "0080", true);
        setup(80, "0081", false);
        setup(81, "0082", true);
        setup(82, "0083", true);
        setup(83, "0084", false);
        setup(84, "0085", true);
        setup(85, "0086", false);
        setup(86, "0087", false);

    }

    public void setup(int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/Tile/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try{

            BufferedReader br = new BufferedReader(new FileReader(filePath));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine();

                while (col < gp.maxWorldCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e){

        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol< gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;


            if (worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;

            }
        }
    }
}
