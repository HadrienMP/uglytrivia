package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.lib.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefactoredGame implements Game {

    private List<Player> players = new ArrayList<>();
    private Map<String, Integer> positions = new HashMap<>();
    private Map<String, Boolean> inPenaltyBox = new HashMap<>();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    private Log log;

    private Questions questions = new Questions();

    public RefactoredGame(Log log) {
        this.log = log;
    }

    @Override
    public void add(String playerName) {
        players.add(new Player(playerName));

        positions.put(playerName, 0);
        inPenaltyBox.put(playerName, false);

        log.add(playerName + " was added");
        log.add("They are player number " + players.size());
    }

    @Override
    public void roll(int roll) {
        log.add(currentPlayer().name() + " is the current player");
        log.add("They have rolled a " + roll);

        if (inPenaltyBox.get(currentPlayer().name())) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                log.add(currentPlayer().name() + " is getting out of the penalty box");

                movePlayerBy(roll);
                log.add("The category is " + currentCategory());
                askQuestion();
            } else {

                log.add(currentPlayer().name() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            movePlayerBy(roll);
            log.add("The category is " + currentCategory());
            askQuestion();
        }

    }

    private Player currentPlayer() {
        return players.get(currentPlayer);
    }

    private void movePlayerBy(int roll) {
        String playerName = currentPlayer().name();
        positions.put(playerName, positions.get(playerName) + roll);


        if (positions.get(playerName) > 11) {
            positions.put(playerName, positions.get(playerName) - 12);
        }

        log.add(playerName
                + "'s new location is "
                + positions.get(playerName));
    }

    private void askQuestion() {
        log.add(questions.popQuestion(currentCategory()));
    }

    private Category currentCategory() {
        Integer currentPlayerPosition = positions.get(currentPlayer().name());
        return Category.fromPosition(currentPlayerPosition);
    }

    @Override
    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox.get(currentPlayer().name()) && !isGettingOutOfPenaltyBox) {
            nextPlayer();
            return true;
        }

        log.add("Answer was correct!!!!");
        incrementPlayerGold();
        nextPlayer();
        return didPlayerWin();
    }

    private void incrementPlayerGold() {
        currentPlayer().winsACoin();
        log.add(currentPlayer().name()
                + " now has "
                + currentPlayer().coins()
                + " Gold Coins.");
    }

    private void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    @Override
    public boolean wrongAnswer() {
        log.add("Question was incorrectly answered");
        log.add(currentPlayer().name() + " was sent to the penalty box");
        inPenaltyBox.put(currentPlayer().name(), true);

        nextPlayer();
        return true;
    }


    private boolean didPlayerWin() {
        return currentPlayer().coins() != 6;
    }
}
