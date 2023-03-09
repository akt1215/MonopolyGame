import java.util.ArrayList;

public class PlayerInfos {
    private String name;
    private int money = 1500;
    private int location = 0;
    private ArrayList<PropertyMono> owned = new ArrayList<>();
    private boolean stillPlaying = true;
    PlayerInfos(String name){
        this.name = name;

    }

    public int getLocation(){
        Dice dice = new Dice();
        location += dice.getDice();

        if (location>21) {
            location -= 21;
        }
        return location;
    }

    public void payMoney(int rent, int player){
        this.money -= rent;
        System.out.printf("Player%d: %d\n", player+1, money);
        if (money<0) {
            stillPlaying = false;
        }
    }

    public void earnMoney(int rent, int player){
        this.money += rent;
        System.out.printf("Player%d: %d\n", player+1, money);
    }

    public boolean isStillPlaying(){
        return stillPlaying;
    }
}
