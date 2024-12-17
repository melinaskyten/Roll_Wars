public  class ChallengeFactory {

    public static Challenge createChallenge (String challengeType, int targetScore) {
        switch (challengeType) {
            case "Score":
                return new ScoreChallenge(targetScore);
            case "Pair":
                return new PairChallenge();
            default:
                throw new IllegalArgumentException("Invalid challenge type");
        }
    }
}
