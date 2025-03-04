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

        up1 = setup("/NPC/oldman_up1");
        up2 = setup("/NPC/oldman_up2");
        down1 = setup("/NPC/oldman_down1");
        down2 = setup("/NPC/oldman_down2");
        left1 = setup("/NPC/oldman_left1");
        left2 = setup("/NPC/oldman_left2");
        right1 = setup("/NPC/oldman_right1");
        right2 = setup("/NPC/oldman_right2");
    }

    public void setDialog(){
        dialogs[0] = "To defeat me and reclaim knowledge,\n you have to answer these 3 questions\n correctly! There are letters you have to step \n on";
        dialogs[1] = "When you step on a letter, it will remind\nyou which number you are currently answering\nNow let's begin";
        dialogs[2] = "1. What is the language of a computer?\nA. English       B. Filipino\nC. Binary        D. Spanish";
        dialogs[3] = "2. What two numbers are used in binary?\n A. 1 and 5       B. 0 and 1\nC. 1 and 9        D. 10 and 11";
        dialogs[4] = "3. How computers understand letters?\nA. By converting to binary\nB. By converting to decimal\nC. By converting to fraction";
        dialogs[5] = "Once you reach intellect 4\nYou will be able to teleport!";
    }

    public void speak() {
        super.speak();
    }
}
