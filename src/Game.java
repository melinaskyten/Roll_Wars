import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private Rules rules;
    private List<Dice> diceList;
    private Player player;
    private HighScore highScore;
    private Scanner scanner = new Scanner(System.in);
    private static int bonus_value = 50;
    private Feedback feedback;
    private Random random = new Random();

    public Game() {

        highScore = HighScore.getInstance();
        feedback = Feedback.getInstance();
        player = new Player();
        System.out.print("Welcome! \nUsername: ");
        String username = scanner.nextLine();
        player.setName(username);
        showColorOptions();
        int colorOption = scanner.nextInt();
        player.setColor(colorOption);

        while (true) {
            showMenu();
            int menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1: //Play
                    player.setScore(0);
                    throwDice();
                    break;
                case 2: //Highscore
                    highScore.printHighScore();
                    break;
                case 3: //Rules
                    if (rules == null) {
                        rules = Rules.getInstance();
                    }
                    rules.printRules();
                    break;
                case 4: //Feedback
                    feedback.getFeedback();
                    break;
                case 5: //Exit
                    System.out.println("Thank you for playing!");
                    System.exit(0);
            }
        }
    }

    private void showMenu() {
        System.out.println("""
                Menu:
                1.Play
                2.Highscore
                3.Rules
                4.Feedback
                5.Exit""");
    }

    private void showColorOptions() {
        System.out.println("""
                Please choose a color:
                1. Red
                2. Green
                3. Blue""");
    }

    private void throwDice() {

        String challengeType = (random.nextBoolean()) ? "Score" : "Pair";
        Challenge challenge;

        if (challengeType.equals("Score")) {
            challenge = ChallengeFactory.createChallenge(challengeType, 20);
        } else {
            challenge = ChallengeFactory.createChallenge(challengeType, 0);
        }

        System.out.println("An extra challenge for you: " + challenge.getChallengeDescription());

        AudioPlayer.playDiceRollSound();
        if (diceList == null) {
            diceList = getDice(5);
        }

        //Loopar 3 gånger för 3 kast
        for (int i = 0; i < 3; i++) {
            System.out.println("Throw " + (i + 1) + ":");
            //Ställer om round score till 0 efter varje kast
            int roundScore = 0;

            //Loopar genom listan med tärningar, "rullar dem" och skriver ut resultat
            Dice.reRollAll(diceList);
            System.out.println("Dice rolled: ");
            for (int j = 0; j < diceList.size(); j++) {
                System.out.println(diceList.get(j).getPips() + " ");
                roundScore += diceList.get(j).getPips();
            }

            //Skriver ut poängställning
            player.addScore(roundScore);
            System.out.println("Round score: " + roundScore + "\n");

            if (challenge.isComplete(diceList, roundScore)) {
                System.out.println("Congrats! You completed the challenge + 20 point.");
                player.addScore(20);
            }

        }

        int bonus = bonusAmount(diceList);
        player.addScore(bonus);
        System.out.println("Final score: " + player.getScore() + "\n");

        if (bonus > 0) {
            System.out.println("Congratulations on five of a kind! You got a bonus of " + bonus + ". Points");
        }

        this.highScore.addScore(player);
    }

    public static List<Dice> getDice(int amountOfDice) {
        List<Dice> dice = new ArrayList<>();
        for (int i = 0; i < amountOfDice; i++) {
            dice.add(new Dice());
        }
        return dice;
    }

    private static int bonusAmount(List<Dice> diceList) {
        if (isBonusWon(diceList)) {
            return bonus_value;
        } else {
            return 0;
        }
    }

    private static boolean isBonusWon(List<Dice> diceList) {

        int firstDice = diceList.get(0).getPips();
        for (Dice d : diceList) {
            if (d.getPips() != firstDice) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        new Game();
    }

}
