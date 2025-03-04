package main;

public class Queen extends Entity{

    public Queen(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;

        getNPCImage();
        setDialog();
        speak();
    }

    public void getNPCImage(){

        down1 = setup("/NPC/Queen");
        down2 = setup("/NPC/Queen");
    }

    public void setDialog(){
        dialogs[0] = "This Kingdom has been ravaged and destroyed\nYou are the chosen one that can help us\nLook for the Old Man and prove yourself!";
        dialogs[1] = "He is inside a room somewhere in\nthis kingom";
        dialogs[2] = "You might as well pick some keys";
        dialogs[3] = "Goodluck our future hero";
    }

    public void speak() {
        super.speak();
    }
}
