import java.util.*;

public class PlayerInfos {
    private String name;
    private int money = 1000;
    private int location = 0;
    private ArrayList<PropertyMono> owned = new ArrayList<>();
    private int payment;
    public int rent;
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
    private ArrayList<Integer> houseBuildingAvailable = new ArrayList<>();
    private boolean didPlayerSaidNo = false;
    private ArrayList<Integer> houseLevels = new ArrayList<>();
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
            money+=100;
        }
        return location;
    }

    public void payMoney(int rent, boolean skipPayment){
        this.rent = rent;

        if (this.rent > money){

            if (aboutToLose()) {
                this.money -= this.rent;
                if (!skipPayment) {

                    System.out.printf("Player: %s: %d\n", name, money);
                }
            } else {
                stillPlaying = false;
                payment = money;
                money = 0;
                for (int i = 0; i < owned.size(); i++) {
                    owned.get(i).updateOwner(playerID, false);
                }
                owned.clear();
            }
        } else{

            this.money -= this.rent;
            if (!skipPayment) {

                System.out.printf("Player: %s: %d\n", name, money);
            }
        }
    }

    public void earnMoney(int payment, boolean skipPayment){
        this.money += payment;
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

    public void updateOwned(PropertyMono propertyID, boolean isBuying, boolean isSelling, boolean extraInfo) {
        if (isBuying) {
            //adding properties to the list when the player bought the property
            owned.add(propertyID);

            //updating the color count
            colorCount = currentColorNumOfProperty.get(propertyID.getColor());
            currentColorNumOfProperty.replace(propertyID.getColor(), colorCount+1);
            if (extraInfo) {

                System.out.println(currentColorNumOfProperty);
            }
            checkOwnedProperty();
        } else {
            owned.remove(propertyID);
            //updating the color count
            colorCount = currentColorNumOfProperty.get(propertyID.getColor());
            currentColorNumOfProperty.replace(propertyID.getColor(), colorCount-1);
            if (extraInfo) {
                System.out.println(currentColorNumOfProperty);
            }

            if (isSelling) {
                money += propertyID.getPrintedPrice() / 2;
            }
        }
    }

    public HashMap getCurrentColorNumOfProperty(){
        return currentColorNumOfProperty;
    }

    /*check whether the player has all the property with the same color
            - use for loop to check the Hashmap of the number of the property they have in
            - compare that number with the total number of the property of each color
            - if the number is the same, do the following
        -> Done
      check whether any of the properties already has a hotel
            - if so, get rid of it from the list
            - if the list is not empty, do the following
      -> Done
      check whether the player wants to build a house
            - use if statement to check whether the player has already said no
            - if player hasn't said no, do the following
      -> Done

      display the info of the properties the house can be built
            - Current level
            - How the rent will change
            - Housing cost
            - whether house can be built -> if the there is a difference in the housing level,
            the player has to build a house in the lower level property before building another house
            - How much money the player has
      -> Done

      ask where the player wants to build a house (while loop)
            - print out "Where you want to build a house (type -1 if you don't want to build a house)
            - use scanner and use that as the index number for the list
            - if the player chose the number that the house can't be built, tell the player that you cannot build a house there
                -> go back and ask where the player wants to build a house
            - if the player chose -1, break the while loop
            - upgrade the level of the housing the player chose
            - subtract the player's money by the cost of building a house */

    /*if the player is about to break and has properties or houses, ask whether the player wants to sell any of the properties or the houses
            - the player has to sell houses before they sell the properties that has houses
            - if selling the houses, if there's a difference in the housing level, the player has to chose the one with a higher level

     */

    public void checkOwnedProperty(){
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < owned.size(); i++) {
            houseBuildingAvailable.clear();
            //check whether the player has all the properties of the same color
            if (currentColorNumOfProperty.get(owned.get(i).getColor()) == colorNumOfProperty.get(owned.get(i).getColor()) && !didPlayerSaidNo) {

                //some kind of error here
                System.out.printf("Building house at %s: ", owned.get(i).getColor());
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

                    for (int t = 0; t < colorNameProperty[colorCode].length; t++) {
                        if (propertyMonoInfo.get(propertyID+t).getLevel() < 5) {
                            houseBuildingAvailable.add(propertyID + t);
                        }
                    }

                    while (!didPlayerSaidNo) {
                        houseLevels.clear();
                        displayHousingInfo();
                        System.out.println("Where do you want to build a house (type -1 if you don't want to buy)");
                        int addPropertyID = scanner.nextInt();
                        if (addPropertyID == -1) {
                            break;
                        }

                        for (int n = 0; n < houseBuildingAvailable.size(); n++) {
                            houseLevels.add(propertyMonoInfo.get(houseBuildingAvailable.get(n)).getLevel());
                        }

                        if (houseLevels.get(addPropertyID) >= 5) {
                            System.out.println("You cannot build more house here (reached max level)");
                            System.out.println();
                        } else if (propertyMonoInfo.get(houseBuildingAvailable.get(addPropertyID)).getLevel() > Collections.min(houseLevels)) {
                            System.out.println("You cannot build more house here (unbalance)");
                            System.out.println();
                        }
                        else{

                            propertyMonoInfo.get(houseBuildingAvailable.get(addPropertyID)).updateHousing();
                            money -= propertyMonoInfo.get(houseBuildingAvailable.get(addPropertyID)).getHouseCost();
                            System.out.println("Do you want to continue: ");
                            if (scanner.nextInt() == 0) {
                                didPlayerSaidNo = true;
                            }
                        }
                    }
                }
            }
        }
    }

    /*Trading feature
        Check whether the player wants to trade the property before rolling the dice
        if yes, do the following
            - ask who the player wants to trade with
                - use the player list and ask to type the number
            - show all the properties the other player who the player wants to trade with (in the list and the number form)
            - ask which one the player wants to trade (if not with that person type -1)

            -> Done

            - chose what to trade with
                - 0: money, 1: property, 2: money and property
                - if 0
                    - type in how much
                - if 1
                    - show the list of the property and let the player choose

                    -> Done

                    -> use while loop until the player types -1
                - if 2
                    - do both 0 and 1
        Ask the trader whether they want to trade with that conditions
            - if yes -> send money/ properties to the trader, and give property
            - if no -> ask the player to continue the process
                - if yes, start again from choosing what to trade with

    */

    public String getName(){
        return name;
    }

    public void displayHousingInfo(){
        if (!houseBuildingAvailable.isEmpty() && !didPlayerSaidNo) {

            for (int t = 0; t < houseBuildingAvailable.size(); t++) {

                System.out.printf("%d: %s, Money: %d\n", t, propertyMonoInfo.get(houseBuildingAvailable.get(t)).getPropertyHouseInfo(), money);
            }
        }
    }

    public ArrayList getOwned(){
        return owned;
    }

    public boolean doTrading(){
        Scanner sc = new Scanner(System.in);
        System.out.printf("%s, do you agree to the trade?: ", name);
        int agreeToTrade = sc.nextInt();

        while (true) {

            switch (agreeToTrade) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    System.out.print("Answer with 0 or 1 (0: No, 1: Yes): ");
                    agreeToTrade = sc.nextInt();
            }
        }

    }

    public boolean aboutToLose() {

        Scanner sc = new Scanner(System.in);

        while (!owned.isEmpty()) {

            while (money < rent) {

                System.out.print("You don't have enough money. Do you want to sell your property: ");
                int doSell = sc.nextInt();

                switch (doSell) {
                    case 1:
                        System.out.println("Chose which property to sell");
                        for (PropertyMono propertyMono : owned) {
                            System.out.printf("%d: %s\n", owned.indexOf(propertyMono), propertyMono.getInfo());
                        }
                        int sellProperty = sc.nextInt();
                        money += owned.get(sellProperty).getPrintedPrice() / 2;
                        updateOwned(owned.get(sellProperty), false, true, true);
                        System.out.printf("You have %d\n", money);
                        break;
                    default:
                        break;

                }
            }
            return true;
        }
        return false;

    }

    /*public void updateOwner(int newOwner, int propertyID, boolean isBuying) {
        //when buying
        owned.get(propertyID).updateOwner(newOwner, isBuying);
    }*/
}
