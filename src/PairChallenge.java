import java.util.List;

public class PairChallenge implements Challenge {


    @Override
    public String getChallengeDescription() {
        return "Get at least two dice with the same amount.";
    }

    @Override
    public boolean isComplete(List<Dice> diceList, int roundScore) {

        List<Integer> sortedList= Dice.sortDiceList(diceList);

        for (int i = 0; i < sortedList.size() - 1; i++) {
            if (sortedList.get(i) == sortedList.get(i + 1)) {
                return true;
            }
        }
        return false;
    }
}
