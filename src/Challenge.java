import java.util.List;

public interface Challenge {
    String getChallengeDescription();
    boolean isComplete(List<Dice> diceList, int roundScore);
}
