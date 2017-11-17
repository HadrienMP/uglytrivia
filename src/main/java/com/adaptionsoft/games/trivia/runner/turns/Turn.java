package com.adaptionsoft.games.trivia.runner.turns;

public class Turn {
    public final int roll;
    public final boolean answersCorrectly;
    public Turn(int roll, boolean answersCorrectly) {
        this.roll = roll;
        this.answersCorrectly = answersCorrectly;
    }
}
