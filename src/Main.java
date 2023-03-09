import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<PlayerInfos> playerInfos = new ArrayList<>();
        int playerNum;
        int playerLoc;
        int rents;
        ArrayList<Integer> loser = new ArrayList<>();

        //Player infos
        System.out.print("How many players: ");
        playerNum = sc.nextInt();
        sc.nextLine();

        Game game = new Game(playerInfos);

        for (int i = 0; i < playerNum; i++) {
            System.out.printf("Player%d type your name: ", i + 1);
            playerInfos.add(new PlayerInfos(sc.nextLine()));
        }

        while (playerInfos.size()>=2) {
            for (int i = 0; i < playerNum; i++) {
                if (playerInfos.size()>=2) {
                    //when player stops at the property
                    playerLoc = playerInfos.get(i).getLocation();
                    game.getLocInfo(playerLoc);
                    rents = game.getRents(playerLoc);
                    playerInfos.get(i).payMoney(rents, i);

                    if (!playerInfos.get(i).isStillPlaying()) {
                        loser.add(i);
                    }
                }
            }
            for (int t = 0; t < loser.size(); t++) {
                playerInfos.remove(t);
            }
        }
        sc.close();
    }
}