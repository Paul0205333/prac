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

        down1 = setup("/NPC/kween");
        down2 = setup("/NPC/kween");
    }

    public void setDialog(){
        dialogs[1] = "This Kingdom has been destroyed\nYou are the chosen one that can help us\nLook for the Old Man and prove yourself!";
        dialogs[2] = "He is somewhere in this kingom";
        dialogs[3] = "I heard there's a rock that would\nprevent you from meeting him";
        dialogs[4] = "Maybe you should pick the axe";
        dialogs[0] = "Goodluck our future hero";
    }

    public void speak() {
        super.speak();
    }

}
