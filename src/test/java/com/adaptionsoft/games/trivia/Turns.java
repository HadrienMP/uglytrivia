package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.runner.turns.Turn;

import java.util.List;

public class Turns {
    public final List<Turn> turns;

    public Turns(List<Turn> turns) {
        this.turns = turns;
    }
}
