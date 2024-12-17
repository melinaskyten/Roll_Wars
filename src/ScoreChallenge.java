import java.util.List;

public class ScoreChallenge implements Challenge{

    private int targetScore;

    public ScoreChallenge(int targetScore) {
        this.targetScore = targetScore;
    }

    @Override
    public String getChallengeDescription() {
        return "Throw exactly " + targetScore + " points in one round.";
    }

    @Override
    public boolean isComplete(List<Dice> diceList, int roundScore) {
        return roundScore == targetScore;
    }
}
