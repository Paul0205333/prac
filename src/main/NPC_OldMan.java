package main;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 0;

        getNPCImage();
        setDialog();
        speak();
    }

    public void getNPCImage(){
        down1 = setup("/NPC/tanda");
        down2 = setup("/NPC/tanda");
    }

    public void setDialog(){
        dialogs[1] = "To defeat me and reclaim knowledge,\n you have to answer these 3 questions\n correctly! There are letters you have to step \n on";
        dialogs[2] = "When you step on a letter, it will remind\nyou which number you are currently answering\nNow let's begin";
        dialogs[3] = "1. What is the language of a computer?\nA. English       B. Filipino\nC. Binary        D. Spanish";
        dialogs[4] = "2. What two numbers are used in binary?\n A. 1 and 5       B. 0 and 1\nC. 1 and 9        D. 10 and 11";
        dialogs[5] = "3. How computers understand letters?\nA. By converting to binary\nB. By converting to decimal\nC. By converting to fraction";
        dialogs[0] = "Once you reach intellect 4\nYou will be able to teleport!";

    }

    public void speak() {
        super.speak();
    }

}
