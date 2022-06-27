import java.util.ArrayList;
import java.util.Scanner;



public class App {

    public static int getTotalTeam2Score (ArrayList<PlayerDetails> playerDetailsList1){
        int team2TotalScore = 0;

        for (int i = 0; i < playerDetailsList1.size(); i++) {
            team2TotalScore = team2TotalScore+playerDetailsList1.get(i).getTotalRuns();
        }

        return team2TotalScore;
    }

    public static int [] getCurrentStrikePlayer(ArrayList<PlayerDetails> playerDetails){
        PlayerDetails onStrikePlayer;
        int onStrikerIndex = 0;
        int onFieldRemaingPlayer = 0;
        for (int i = 0; i < playerDetails.size(); i++) {
            onStrikePlayer = playerDetails.get(i);
            if(onStrikePlayer.isOnStrike() == true && onStrikePlayer.isWicketDown() == false){
                onStrikerIndex = i;
            }
            if(onStrikePlayer.isOnStrike() == false && onStrikePlayer.isOnField() == true && onStrikePlayer.isWicketDown() == false){
                onFieldRemaingPlayer = i;
            }
        }
        return new int[] {onStrikerIndex, onFieldRemaingPlayer};
    }

    public static void printPlayerDetails(ArrayList<PlayerDetails> playerDetails, int overNumber, String team){
        System.out.println("Scorecard for "+ team);
        String header = "PlayerName  |  Score  |  4s  |  6s  |  Balls  ";
        System.out.println(header+"\n");
        PlayerDetails currPlayer;
        String scoreboard = "";
        int totalRun = 0;
        int totalWicket = 0;
        for (int i = 0; i < playerDetails.size(); i++) {

            currPlayer = playerDetails.get(i);
            totalWicket = totalWicket + (currPlayer.isWicketDown() ? 1: 0);
            totalRun = totalRun + currPlayer.getTotalRuns();
            scoreboard = currPlayer.getPlayerName() + "  |  "+currPlayer.getTotalRuns() + "  |  "+currPlayer.getFourHits() + "  |  "+currPlayer.getSixHits() + "  |  "+currPlayer.getBallsPlayed() ;
            System.out.println(scoreboard+"\n");
        }
        System.out.println("Overs: "+ String.valueOf(overNumber+1)+"\n");
        System.out.println("Total: "+ String.valueOf(totalRun)+" / "+String.valueOf(totalWicket));
    }

    public static ArrayList<PlayerDetails> playTeam1(int noOfPlayer, int noOfOvers){
        Scanner sc = new Scanner(System.in);
        Constant constant = new Constant();

        System.out.println("Enter the batting order of team 1");
        // Add Player names to a array
        ArrayList<String> team1 = new ArrayList<String>();
        for (int i = 1; i <= noOfPlayer; i++) {
            System.out.println("Enter player name: " + String.valueOf(i));
            team1.add(sc.next());
        }

        // Add Over details
        int currentPlayerOrder = 0; // top two player will play
        boolean isTeamOut = false;
        PlayerDetails player1 = new PlayerDetails();
        PlayerDetails playerX = new PlayerDetails();
        player1.setOnStrike(true);
        player1.setOnField(true);
        player1.setPlayerName(team1.get(0));
        PlayerDetails player2 = new PlayerDetails();
        player2.setOnStrike(false);
        player2.setOnField(true);
        player2.setPlayerName(team1.get(1));
        ArrayList<PlayerDetails> playerDetailsList1 = new ArrayList<PlayerDetails>();
        ArrayList<Integer> wicketDown = new ArrayList<Integer>();

        team1.remove(0);
        team1.remove(0);

        playerDetailsList1.add(player1);
        playerDetailsList1.add(player2);
        for (int i = 1; i <= noOfOvers; i++) {
            if(isTeamOut){
                break;
            }
            int totalOverBalls = 6;
            for (int j = 1; j <= totalOverBalls; j++) {
                System.out.println("Please enter the run scored");
                int run = sc.nextInt();
                System.out.println("Please enter if ball is a valid delivery possible options are ['NORMAL', 'WICKET', 'NO', 'WIDE']");
                String status = sc.next();

                if(status.equals(constant.WICKET)){
                    int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);

                    playerDetailsList1.get(onStrikePlayerIndex[0]).setWicketDown(true);
                    playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                    playerDetailsList1.get(onStrikePlayerIndex[0]).setOnField(false);
                    playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                    wicketDown.add(onStrikePlayerIndex[0]);
                    if(team1.size()==0){
                        isTeamOut = true;
                        break;
                    }
                    playerX.setOnStrike(true);
                    playerX.setOnField(true);
                    playerX.setPlayerName(team1.get(0));
                    team1.remove(0);
                    playerDetailsList1.add(playerX);
                } else if (status.equals(constant.NO_BALL)){
                    totalOverBalls = totalOverBalls +1;
                    if(run == 1){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==2){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);

                    } else if (run ==3){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==4){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setFourHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getFourHits()+1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);

                    } else if (run ==5){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);

                    } else if (run ==6){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setSixHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getSixHits()+1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                    } else {
                        System.out.println("Invalid Runs Scorred. Ignoring");
                    }
                } else if (status.equals(constant.WIDE)){
                    totalOverBalls = totalOverBalls +1;
                    if(run == 1){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==2){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);

                    } else if (run ==3){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==4){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setFourHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getFourHits()+1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);

                    } else if (run ==5){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);

                    } else if (run ==6){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setSixHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getSixHits()+1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                    } else {
                        System.out.println("Invalid Runs Scorred. Ignoring");
                    }

                } else if (status.equals(constant.NORMAL)){
                    if(run == 1){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==2){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);


                    } else if (run ==3){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==4){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setFourHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getFourHits()+1);

                    } else if (run ==5){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);

                    } else if (run ==6){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setSixHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getSixHits()+1);
                    } else {
                        System.out.println("Invalid Runs Scorred. Ignoring");
                    }

                } else {
                    System.out.println("Please enter a valid type of ball. Ending program now");
                    System.exit(0);
                }

            }
            printPlayerDetails(playerDetailsList1, i, "TEAM-1");
        }
        return playerDetailsList1;
    }
    public static ArrayList<PlayerDetails> playTeam2(int noOfPlayer, int noOfOvers, int team1TotalScore){
        Scanner sc = new Scanner(System.in);
        Constant constant = new Constant();

        System.out.println("Enter the batting order of team 2");

        // Add Player names to a array
        ArrayList<String> team1 = new ArrayList<String>();
        for (int i = 1; i <= noOfPlayer; i++) {
            System.out.println("Enter player name: " + String.valueOf(i));
            team1.add(sc.next());
        }
        // Add Over details
        int currentPlayerOrder = 0; // top two player will play
        boolean isTeamOut = false;
        PlayerDetails player1 = new PlayerDetails();
        PlayerDetails playerX = new PlayerDetails();
        player1.setOnStrike(true);
        player1.setOnField(true);
        player1.setPlayerName(team1.get(0));
        PlayerDetails player2 = new PlayerDetails();
        player2.setOnStrike(false);
        player2.setOnField(true);
        player2.setPlayerName(team1.get(1));
        ArrayList<PlayerDetails> playerDetailsList1 = new ArrayList<PlayerDetails>();
        ArrayList<Integer> wicketDown = new ArrayList<Integer>();

        team1.remove(0);
        team1.remove(0);

        playerDetailsList1.add(player1);
        playerDetailsList1.add(player2);
        for (int i = 1; i <= noOfOvers; i++) {
            if(isTeamOut){
                break;
            }
            int totalOverBalls = 6;
            for (int j = 1; j <= totalOverBalls; j++) {
                System.out.println("Please enter the run scored");
                int run = sc.nextInt();
                System.out.println("Please enter if ball is a valid delivery possible options are ['NORMAL', 'WICKET', 'NO', 'WIDE']");
                String status = sc.next();
                if(status.equals(constant.WICKET)){
                    int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);

                    playerDetailsList1.get(onStrikePlayerIndex[0]).setWicketDown(true);
                    playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                    playerDetailsList1.get(onStrikePlayerIndex[0]).setOnField(false);
                    playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                    wicketDown.add(onStrikePlayerIndex[0]);
                    if(team1.size()==0){
                        isTeamOut = true;
                        break;
                    }
                    playerX.setOnStrike(true);
                    playerX.setOnField(true);
                    playerX.setPlayerName(team1.get(0));
                    team1.remove(0);
                    playerDetailsList1.add(playerX);
                } else if (status.equals(constant.NO_BALL)){
                    totalOverBalls = totalOverBalls +1;
                    if(run == 1){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==2){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);

                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);

                    } else if (run ==3){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==4){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);

                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setFourHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getFourHits()+1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);

                    } else if (run ==5){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);

                    } else if (run ==6){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setSixHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getSixHits()+1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                    } else {
                        System.out.println("Invalid Runs Scorred. Ignoring");
                    }
                } else if (status.equals(constant.WIDE)){
                    totalOverBalls = totalOverBalls +1;
                    if(run == 1){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==2){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);

                    } else if (run ==3){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==4){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setFourHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getFourHits()+1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);

                    } else if (run ==5){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);

                    } else if (run ==6){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setSixHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getSixHits()+1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setExtras(playerDetailsList1.get(onStrikePlayerIndex[0]).getExtras()+1);
                    } else {
                        System.out.println("Invalid Runs Scorred. Ignoring");
                    }

                } else if (status.equals(constant.NORMAL)){
                    if(run == 1){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==2){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);


                    } else if (run ==3){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);
                    } else if (run ==4){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setFourHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getFourHits()+1);

                    } else if (run ==5){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                        // Alter the onstrike and store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setOnStrike(false);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        // setting for non playing player
                        int nonStrikerIndex = onStrikePlayerIndex[1];
                        playerDetailsList1.get(nonStrikerIndex).setOnStrike(true);

                    } else if (run ==6){
                        int [] onStrikePlayerIndex = getCurrentStrikePlayer(playerDetailsList1);
                       //store run in the non striker boy
                        // setting for playing player now
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setBallsPlayed(playerDetailsList1.get(onStrikePlayerIndex[0]).getBallsPlayed() + 1);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setTotalRuns(playerDetailsList1.get(onStrikePlayerIndex[0]).getTotalRuns()+run);
                        playerDetailsList1.get(onStrikePlayerIndex[0]).setSixHits(playerDetailsList1.get(onStrikePlayerIndex[0]).getSixHits()+1);
                    } else {
                        System.out.println("Invalid Runs Scorred. Ignoring");
                    }
                    int totalScore = getTotalTeam2Score(playerDetailsList1);

                    if(totalScore > team1TotalScore){
                        System.out.println("Team 2 Won by Team 1 by : "+ String.valueOf(totalScore - team1TotalScore));
                        break;
                    }
                } else {
                    System.out.println("Please enter a valid type of ball. Ending program now");
                    System.exit(0);
                }

            }
            printPlayerDetails(playerDetailsList1, i,"TEAM-2");

        }


        return playerDetailsList1;
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Hello, PHONEPE!");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the no. of player in each team");
        int noOfPlayer = sc.nextInt();
        if(noOfPlayer <2){
            System.out.println("Atleast two player are required on each side");
            System.exit(0);
        }
        System.out.println("Enter the no. of total overs that will be played by each team");
        int noOfOvers = sc.nextInt();
        if(noOfOvers<=0){
            System.out.println("Atleast one over is required to play the match");
            System.exit(0);
        }
        int team1TotalScore = 0;
        int team2TotalScore = 0;


        ArrayList<PlayerDetails> team1 = playTeam1(noOfPlayer, noOfOvers);

        for (int i = 0; i < team1.size(); i++) {
            team1TotalScore = team1TotalScore+team1.get(i).getTotalRuns();
        }


        ArrayList<PlayerDetails> team2 = playTeam2(noOfPlayer, noOfOvers, team1TotalScore);
        for (int i = 0; i < team2.size(); i++) {
            team2TotalScore = team2TotalScore+team2.get(i).getTotalRuns();
        }


        if(team2TotalScore > team1TotalScore){
            System.out.println("Team 2 Won by Team 1 by : "+ String.valueOf(team2TotalScore - team1TotalScore));
        } else if (team2TotalScore == team1TotalScore){
            System.out.println("Team 2 Draw with Team 1");
        }else {
            System.out.println("Team 1 Won by Team 2 by : "+ String.valueOf(team1TotalScore - team2TotalScore));
        }

    }
}
