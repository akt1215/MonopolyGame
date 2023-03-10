import java.util.ArrayList;
import java.util.HashMap;

public class PropertyMono {
    private String color;
    private String name;
    private int location;
    private int rent;
    private int house1;
    private int house2;
    private int house3;
    private int house4;
    private int hotel;
    private int level;
    private int printedPrice;
    private int houseCost;
    private int owner;
    private HashMap<Integer, PlayerInfos> playerInfos;


    PropertyMono(String color, String name, int location, int rent, int house1, int house2,
                 int house3, int house4, int hotel, int printedPrice, int houseCost, int owner, HashMap playerInfos) {
        this.color = color;
        this.name = name;
        this.location = location;
        this.rent = rent;
        this.house1 = house1;
        this.house2 = house2;
        this.house3 = house3;
        this.house4 = house4;
        this.hotel = hotel;
        this.printedPrice = printedPrice;
        this.houseCost = houseCost;
        this.owner = owner;
        this.playerInfos = playerInfos;
    }

    public String getInfo(){
        return String.format("%s (%s), %d...%d->%d", this.name, this.color, this.location, this.rent, this.hotel);
    }

    public int getRents() {
        if (owner != -1) {
            level = 5;
            switch (level) {
                case 0:
                    playerInfos.get(owner).earnMoney(rent, false);
                    return rent;

                case 1:
                    playerInfos.get(owner).earnMoney(house1, false);
                    return house1;

                case 2:
                    playerInfos.get(owner).earnMoney(house2, false);
                    return house2;

                case 3:
                    playerInfos.get(owner).earnMoney(house3, false);
                    return house3;

                case 4:
                    playerInfos.get(owner).earnMoney(house4, false);
                    return house4;

                case 5:
                    playerInfos.get(owner).earnMoney(hotel, false);
                    return hotel;

                default:
                    return 0;
            }
        }
        return 0;
    }

    public int getOwner(){
        return owner;
    }

    public int getPrintedPrice() {
        return printedPrice;
    }

    public void updateOwner(int playerID) {
        this.owner = playerID;
    }
}
