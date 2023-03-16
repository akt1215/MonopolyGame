import java.util.*;

public class Game {
    ArrayList<PropertyMono> propertyList = new ArrayList<>();
    HashMap<Integer, PlayerInfos> playerInfos = new HashMap<>();
    int playerNum;

    //making the 2d array for the prices of each property
    int propertyPrice[][] = {
            //rent, house1, 2, 3, 4, hotel, printedPrice, housecost
            {2, 10, 30, 90, 160, 250, 60, 50},
            {4, 20, 60, 180, 320, 450, 60, 50},
            {6, 30, 90, 270, 400, 550, 100, 50},
            {6, 30, 90, 270, 400, 550, 100, 50},
            {8, 40, 100, 300, 450, 600, 100, 50},
            {10, 50, 150, 450, 625, 750, 140, 100},
            {10, 50, 150, 450, 625, 750, 140, 100},
            {12, 60, 180, 500, 700, 900, 160, 100},
            {14, 70, 200, 550, 750, 950, 180, 100},
            {14, 70, 200, 550, 750, 950, 180, 100},
            {16, 80, 220, 600, 800, 1000, 200, 100},
            {18, 90, 250, 700, 875, 1050, 220, 150},
            {18, 90, 250, 700, 875, 1050, 220, 150},
            {20, 100, 300, 750, 925, 1100, 240, 150},
            {22, 110, 330, 800, 975, 1150, 260, 150},
            {22, 110, 330, 800, 975, 1150, 260, 150},
            {24, 120, 360, 850, 1025, 1200, 280, 150},
            {26, 130, 390, 900, 1100, 1275, 300, 200},
            {26, 130, 390, 900, 1100, 1275, 300, 200},
            {28, 150, 450, 1000, 1200, 1400, 320, 200},
            {35, 175, 500, 1100, 1300, 1500, 350, 200},
            {50, 200, 600, 1400, 1700, 2000, 400, 200}};

    //making the list of the names of the property
    List<String> namesOfProperty = Arrays.asList(
            "Mediterranean Avenue",
            "Baltic Avenue",
            "Oriental Avenue",
            "Vermont Avenue",
            "Connecticut Avenue",
            "St. Charles Place",
            "States Avenue",
            "Virginia Avenue",
            "St. James Place",
            "Tennessee Avenue",
            "New York Avenue",
            "Kentucky Avenue",
            "Indiana Avenue",
            "Illinois Avenue",
            "Atlantic Avenue",
            "Ventnor Avenue",
            "Marvin Gardens",
            "Pacific Avenue",
            "North Carolina Avenue",
            "Pennsylvania Avenue",
            "Park Place",
            "Boardwalk");

    //making the list of the colors of the property
    List<String> colorsOfProperty = Arrays.asList(
            "Brown", "Brown",
            "Light Blue", "Light Blue", "Light Blue",
            "Pink", "Pink", "Pink",
            "Orange", "Orange", "Orange",
            "Red", "Red", "Red",
            "Yellow", "Yellow", "Yellow",
            "Green", "Green", "Green",
            "Dark Blue", "Dark Blue");

    //making the hashmap for the number of properties for each color
    HashMap<String, Integer> colorNumOfProperty = new HashMap<>();

    Game(HashMap<Integer, PlayerInfos> playerInfos) {
        //defining the property class for each property and store the name in the propertyList
        this.playerInfos = playerInfos;
        for (int i = 0; i < namesOfProperty.size(); i++) {
            propertyList.add(new PropertyMono(colorsOfProperty.get(i), namesOfProperty.get(i), i,
                    propertyPrice[i][0], propertyPrice[i][1], propertyPrice[i][2], propertyPrice[i][3],
                    propertyPrice[i][4], propertyPrice[i][5], propertyPrice[i][6], propertyPrice[i][7], -1, playerInfos));
        }
        colorNumOfProperty.put("Brown", 2);
        colorNumOfProperty.put("Light Blue", 3);
        colorNumOfProperty.put("Pink", 3);
        colorNumOfProperty.put("Orange", 3);
        colorNumOfProperty.put("Red", 3);
        colorNumOfProperty.put("Yellow", 3);
        colorNumOfProperty.put("Green", 3);
        colorNumOfProperty.put("Dark Blue", 2);

    }

    public void getLocInfo(int playerLoc, int playerID){
        System.out.println(propertyList.get(playerLoc).getInfo());

        if (propertyList.get(playerLoc).getOwner() == -1) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(playerInfos.get(playerID).getCurrentColorNumOfProperty());
            System.out.printf("The cost is %d, and you have %d\n", propertyList.get(playerLoc).getPrintedPrice(), playerInfos.get(playerID).getMoney());
            System.out.print("Do you want to buy?: ");
            if (scanner.nextInt() == 1) {
                playerInfos.get(playerID).payMoney(propertyList.get(playerLoc).getPrintedPrice(), true);
                propertyList.get(playerLoc).updateOwner(playerID, true);
                playerInfos.get(playerID).updateOwned(propertyList.get(playerLoc), true, true, false);
            }
        }
    }

    public int getRents (int playerLoc) {
        return propertyList.get(playerLoc).getRents();
    }
    public HashMap getColorNumOfProperty(){
        return colorNumOfProperty;
    }
    public ArrayList getPropertyList() {
        return propertyList;
    }
}

