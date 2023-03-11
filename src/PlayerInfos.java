import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PlayerInfos {
    private String name;
    private int money = 1000;
    private int location = 0;
    private ArrayList<PropertyMono> owned = new ArrayList<>();
    int playerID;
    int colorCount;
    private boolean stillPlaying = true;
    private HashMap<String, Integer> currentColorNumOfProperty = new HashMap<>();
    private HashMap<String, Integer> colorNumOfProperty = new HashMap<>();
    private HashMap<String, String> colorPropertyName = new HashMap<>();
    private String colorNameProperty[][] = {
            //0: brown, 1: light blue, 2: pink, 3: orange, 4: red, 5: yellow, 6: green, 7: dark blue
            {"Mediterranean Avenue", "Baltic Avenue"},
            {"Oriental Avenue", "Vermont Avenue", "Connecticut Avenue"},
            {"St. Charles Place", "States Avenue", "Virginia Avenue"},
            {"St. James Place", "Tennessee Avenue", "New York Avenue"},
            {"Kentucky Avenue", "Indiana Avenue", "Illinois Avenue"},
            {"Atlantic Avenue", "Ventnor Avenue", "Marvin Gardens"},
            {"Pacific Avenue", "North Carolina Avenue", "Pennsylvania Avenue"},
            {"Park Place", "Boardwalk"}
    };
    private ArrayList<PropertyMono> propertyMonoInfo = new ArrayList<>();
    private int colorCode;
    private int propertyID = 0;

    PlayerInfos(String name, int playerID, ArrayList propertyMono){
        this.name = name;
        this.playerID = playerID;
        this.propertyMonoInfo = propertyMono;
        currentColorNumOfProperty.put("Brown", 0);
        currentColorNumOfProperty.put("Light Blue", 0);
        currentColorNumOfProperty.put("Pink", 0);
        currentColorNumOfProperty.put("Orange", 0);
        currentColorNumOfProperty.put("Red", 0);
        currentColorNumOfProperty.put("Yellow", 0);
        currentColorNumOfProperty.put("Green", 0);
        currentColorNumOfProperty.put("Dark Blue", 0);

        colorNumOfProperty.put("Brown", 2);
        colorNumOfProperty.put("Light Blue", 3);
        colorNumOfProperty.put("Pink", 3);
        colorNumOfProperty.put("Orange", 3);
        colorNumOfProperty.put("Red", 3);
        colorNumOfProperty.put("Yellow", 3);
        colorNumOfProperty.put("Green", 3);
        colorNumOfProperty.put("Dark Blue", 2);


    }

    public int getLocation(){
        Dice dice = new Dice();
        location += dice.getDice();

        if (location>21) {
            location -= 21;
            money+=1000;
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
            for (int i = 0; i < owned.size(); i++) {
                owned.get(i).updateOwner(playerID, false);
            }
            owned.clear();
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

    public void updateOwned(PropertyMono propertyID, boolean isBuying) {
        if (isBuying) {
            //adding properties to the list when the player bought the property
            owned.add(propertyID);

            //updating the color count
            colorCount = currentColorNumOfProperty.get(propertyID.getColor());
            currentColorNumOfProperty.replace(propertyID.getColor(), colorCount+1);

            System.out.println(currentColorNumOfProperty);
            checkOwnedProperty();
        } else {
            //removing a properties when selling the property
            owned.remove(propertyID);
        }
    }

    public void checkOwnedProperty(){
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < owned.size(); i++) {
            //check whether the player has all the properties of the same color
            if (currentColorNumOfProperty.get(owned.get(i).getColor()) == colorNumOfProperty.get(owned.get(i).getColor())) {

                //some kind of error here
                System.out.printf("Building house at %s\n", owned.get(i).getColor());
                if (scanner.nextInt() == 1) {

                    switch (owned.get(i).getColor()) {
                        case "Brown":
                            colorCode = 0;
                            propertyID = 0;
                            break;
                        case "Light Blue":
                            colorCode = 1;
                            propertyID = 2;
                            break;
                        case "Pink":
                            colorCode = 2;
                            propertyID = 5;
                            break;
                        case "Orange":
                            colorCode = 3;
                            propertyID = 8;
                            break;
                        case "Red":
                            colorCode = 4;
                            propertyID = 11;
                            break;
                        case "Yellow":
                            colorCode = 5;
                            propertyID = 14;
                            break;
                        case "Green":
                            colorCode = 6;
                            propertyID = 17;
                            break;
                        case "Dark Blue":
                            colorCode = 7;
                            propertyID = 20;
                            break;
                        default:
                            colorCode = -1;
                            propertyID = 0;
                            break;
                    }

                    System.out.println("Type the number corresponding to the name of the property you want to build a house");
                    for (int t = 0; t < colorNameProperty[colorCode].length; t++) {

                        System.out.printf("%d: %s", t, colorNameProperty[colorCode][t]);
                    }

                    int addPropertyID = scanner.nextInt();
                    System.out.println(propertyMonoInfo.get(propertyID+addPropertyID).getPropertyHouseInfo());
                    System.out.print("Do you want to proceed?: ");
                    if (scanner.nextInt() == 1){
                        propertyMonoInfo.get(propertyID+addPropertyID).updateHousing();
                    }
                }
                scanner.close();
            }
        }
    }

    public String getName(){
        return name;
    }
}
