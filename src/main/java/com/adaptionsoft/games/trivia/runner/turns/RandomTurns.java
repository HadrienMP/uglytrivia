package com.adaptionsoft.games.trivia.runner.turns;

import java.util.Random;

public class RandomTurns implements Turns {

    private static final Random RANDOM = new Random();

    @Override
    public Turn turn() {
        return new Turn(rollDice(), answersCorrectly());
    }

    private static boolean answersCorrectly() {
        return RANDOM.nextInt(9) == 7;
    }

    private static int rollDice() {
        return RANDOM.nextInt(5) + 1;
    }
}
