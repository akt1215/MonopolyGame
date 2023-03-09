import java.util.Random;
public class Dice {
    private int firstDice;
    private int secondDice;
    Random rand = new Random();

    Dice(){
        this.firstDice = rand.nextInt(7)+1;
        this.secondDice = rand.nextInt(7)+1;
    }

    public int getDice(){
        return firstDice + secondDice;
    }
}
