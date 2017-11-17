package com.adaptionsoft.games.trivia.runner.turns;

import java.util.ArrayList;
import java.util.List;

public class TurnsSnapshot implements Turns {

    public final List<Turn> turns = new ArrayList<>();
    private final Turns decorated;

    public TurnsSnapshot(Turns turns) {
        this.decorated = turns;
    }

    @Override
    public Turn turn() {
        Turn turn = decorated.turn();
        turns.add(turn);
        return turn;
    }
}
