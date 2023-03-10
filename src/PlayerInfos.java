import java.util.ArrayList;

public class PlayerInfos {
    private String name;
    private int money = 1000;
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

    public void payMoney(int rent, boolean skipPayment){
        this.money -= rent;
        if (!skipPayment) {

            System.out.printf("Player: %s: %d\n", name, money);
        }
        if (money<0) {
            stillPlaying = false;
        }
    }

    public void earnMoney(int rent, boolean skipPayment){
        this.money += rent;
        if (!skipPayment) {

            System.out.printf("Player: %s: %d\n", name, money);
        }
    }

    public boolean isStillPlaying(){
        return stillPlaying;
    }

    public int getMoney(){
        return money;
    }

}
