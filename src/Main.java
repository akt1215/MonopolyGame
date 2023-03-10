import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, PlayerInfos> playerInfos = new HashMap<>();
        int playerNum;
        int playerLoc;
        int rents;
        ArrayList<Integer> playerID = new ArrayList<>();

        //Player infos
        System.out.print("How many players: ");
        playerNum = sc.nextInt();
        sc.nextLine();

        Game game = new Game(playerInfos);

        for (int i = 0; i < playerNum; i++) {
            System.out.printf("Player%d type your name: ", i + 1);
            playerInfos.put(i, new PlayerInfos(sc.nextLine()));
            playerID.add(i);
        }

        while (playerInfos.size()>=2) {
            for (int i = 0; i < playerID.size(); i++) {
                if (playerInfos.size()>=2) {
                    //when player stops at the property
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
        }
        sc.close();
    }
}