public class PlayerDetails {
    private String playerName = "";
    private int totalRuns = 0;
    private int ballsPlayed = 0;
    private int extras = 0;
    private int fourHits = 0;
    private int sixHits = 0;
    private boolean isOnStrike = false;
    private boolean isOnField = false;
    private boolean isWicketDown = false;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(int totalRuns) {
        this.totalRuns = totalRuns;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public int getFourHits() {
        return fourHits;
    }

    public void setFourHits(int fourHits) {
        this.fourHits = fourHits;
    }

    public int getSixHits() {
        return sixHits;
    }

    public void setSixHits(int sixHits) {
        this.sixHits = sixHits;
    }

    public boolean isWicketDown() {
        return isWicketDown;
    }

    public void setWicketDown(boolean wicketDown) {
        isWicketDown = wicketDown;
    }

    public boolean isOnStrike() {
        return isOnStrike;
    }

    public void setOnStrike(boolean onStrike) {
        isOnStrike = onStrike;
    }

    public int getExtras() {
        return extras;
    }

    public void setExtras(int extras) {
        this.extras = extras;
    }

    public boolean isOnField() {
        return isOnField;
    }

    public void setOnField(boolean onField) {
        isOnField = onField;
    }

}