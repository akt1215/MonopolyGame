import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, PlayerInfos> playerInfos = new HashMap<>();
        int playerNum;
        int playerLoc;
        int rents;
        int tradeMoney;
        int tradingPropertyPayment;
        boolean didPlayerSaidNoTrading = false;
        ArrayList<Integer> playerID = new ArrayList<>();
        ArrayList<Integer> traderID = new ArrayList<>();

        //Player infos
        System.out.print("How many players: ");
        playerNum = sc.nextInt();
        sc.nextLine();

        Game game = new Game(playerInfos);
        ArrayList<PropertyMono> propertyMonoHash = new ArrayList<>();
        propertyMonoHash = game.propertyList;

        for (int i = 0; i < playerNum; i++) {
            System.out.printf("Player%d type your name: ", i + 1);
            playerInfos.put(i, new PlayerInfos(sc.nextLine(), i, propertyMonoHash));
            playerID.add(i);
        }

        while (playerInfos.size()>=2) {
            for (int i = 0; i < playerID.size(); i++) {
                if (playerInfos.size()>=2) {
                    traderID.clear();
                    //when player stops at the property
                    System.out.println();
                    System.out.printf("%s's turn\n", playerInfos.get(playerID.get(i)).getName());

                    //initializing trading
                    System.out.print("Do you want to trade: ");
                    if (sc.nextInt() == 1) {
                        while (!didPlayerSaidNoTrading) {

                            for (int t = 0; t < playerID.size(); t++) {
                                if (playerInfos.get(playerID.get(i)) != playerInfos.get(playerID.get(t))){
                                    traderID.add(t);
                                    System.out.printf("%d: %s\n", playerID.get(t), playerInfos.get(playerID.get(t)).getName());
                                }
                            }
                            System.out.print("Who do you want to trade with? (type -1 if you don't want to trade): ");
                            int tradingPlayer = sc.nextInt();
                            if (tradingPlayer == -1) {
                                break;
                            }else if (!traderID.contains(tradingPlayer)) {
                                System.out.printf("%d does not exist\n", tradingPlayer);
                            } else {
                                ArrayList<PropertyMono> traderOwned = new ArrayList<>();
                                traderOwned = playerInfos.get(playerID.get(tradingPlayer)).getOwned();
                                for (int n = 0; n < traderOwned.size(); n++) {
                                    System.out.printf("%d: %s\n", n, traderOwned.get(n).getInfo());
                                }
                                System.out.println();

                                boolean keepTrading = true;
                                while (keepTrading) {
                                    int whatToTradeWith = 0;
                                    System.out.print("Which one do you want to trade (type -1 if you don't want to trade): ");
                                    int tradingPropertyID = sc.nextInt();
                                    if (tradingPropertyID == -1) {
                                        break;
                                    } else if (tradingPropertyID >= traderOwned.size()) {
                                        System.out.println("Out of range, try again");

                                    } else{
                                        boolean agreeToTrade = false;
                                        while (!agreeToTrade) {

                                            System.out.println("Chose what to trade with (type -1 to go back)");
                                            System.out.println("0: money, 1: property, 2: money & property");
                                            whatToTradeWith = sc.nextInt();
                                            if (whatToTradeWith == -1) {
                                                break;
                                            }

                                            switch (whatToTradeWith) {
                                                case 0:
                                                    System.out.print("How much do you pay for: ");
                                                    tradeMoney = sc.nextInt();
                                                    agreeToTrade = playerInfos.get(tradingPlayer).doTrading();
                                                    break;

                                                case 1:
                                                    System.out.println("Which property do you trade with");
                                                    ArrayList<PropertyMono> tradingWithOwned = new ArrayList<>();
                                                    tradingWithOwned = playerInfos.get(playerID.get(i)).getOwned();

                                                    for (int m = 0; m < tradingWithOwned.size(); m++) {
                                                        System.out.printf("%d: %s\n", m, tradingWithOwned.get(m).getInfo());
                                                    }
                                                    System.out.println();

                                                    tradingPropertyPayment = sc.nextInt();

                                                    while (true) {

                                                        if (tradingPropertyPayment >= tradingWithOwned.size()) {
                                                            System.out.print("Out of range. Which property do you trade with: ");
                                                            tradingPropertyPayment = sc.nextInt();
                                                        } else {
                                                            agreeToTrade = playerInfos.get(tradingPlayer).doTrading();

                                                            if (agreeToTrade) {

                                                                //when the other player agrees to the trading
                                                                PropertyMono playerTradingProperty = (PropertyMono) playerInfos.get(playerID.get(i)).getOwned().get(tradingPropertyPayment);

                                                                //updating the owner
                                                                System.out.println("Before");
                                                                System.out.println(playerTradingProperty.getInfo());
                                                                System.out.println(traderOwned.get(tradingPropertyID).getInfo());
                                                                System.out.println();

                                                                //have to work on here

                                                                playerTradingProperty.updateOwner(tradingPlayer, true);
                                                                traderOwned.get(tradingPropertyID).updateOwner(playerID.get(i), true);

                                                                playerInfos.get(playerID.get(i)).updateOwner(playerID.get(i), true);
                                                                playerInfos.get(tradingPlayer).updateOwner(tradingPlayer, true);
                                                                System.out.println("After");
                                                                System.out.println(playerTradingProperty.getInfo());
                                                                System.out.println(traderOwned.get(tradingPropertyID).getInfo());
                                                            }
                                                            break;
                                                        }
                                                    }
                                                    break;

                                                case 2:
                                                    System.out.println("Yay");
                                                    break;

                                                default:
                                                    System.out.println("Default");
                                                    break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    playerLoc = playerInfos.get(playerID.get(i)).getLocation();
                    game.getLocInfo(playerLoc, playerID.get(i));
                    rents = game.getRents(playerLoc);
                    playerInfos.get(playerID.get(i)).payMoney(rents, false);

                    if (!playerInfos.get(playerID.get(i)).isStillPlaying()) {
                        playerInfos.remove(playerID.get(i));
                        playerID.remove(i);
                        i--;
                    }
                }
            }
        }sc.close();

    }
}
