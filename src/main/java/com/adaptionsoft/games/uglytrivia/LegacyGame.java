package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.lib.log.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LegacyGame implements Game {
    private static final String SCIENCE_CATEGORY = "Science";
    private static final String SPORTS_CATEGORY = "Sports";
    private static final String POP_CATEGORY = "Pop";
    private static final String ROCK_CATEGORY = "Rock";

    private List<String> players = new ArrayList<String>();
    private int[] places = new int[6];
    private int[] purses  = new int[6];
    private boolean[] inPenaltyBox  = new boolean[6];

    private LinkedList<String> popQuestions = new LinkedList<String>();
    private LinkedList<String> scienceQuestions = new LinkedList<String>();
    private LinkedList<String> sportsQuestions = new LinkedList<String>();
    private LinkedList<String> rockQuestions = new LinkedList<String>();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    private Log log;

    public LegacyGame(Log log) {
		this.log = log;
		// fixme Cette initialisation n'a peut être pas sa place ici
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	private String createRockQuestion(int index){
		return "Rock Question " + index;
	}

    @Override
    public void add(String playerName) {

	    players.add(playerName);
	    places[howManyPlayers()] = 0;
	    purses[howManyPlayers()] = 0;
	    inPenaltyBox[howManyPlayers()] = false;

        log.add(playerName + " was added");
        log.add("They are player number " + players.size());
    }
	
	private int howManyPlayers() {
		return players.size();
	}

	@Override
    public void roll(int roll) {
        log.add(players.get(currentPlayer) + " is the current player");
        log.add("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

                log.add(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
				if (places[currentPlayer] > 11) {
                    places[currentPlayer] = places[currentPlayer] - 12;
                }

                log.add(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                log.add("The category is " + currentCategory());
				askQuestion();
			} else {
                log.add(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
			
			log.add(players.get(currentPlayer)
					+ "'s new location is " 
					+ places[currentPlayer]);
			log.add("The category is " + currentCategory());
			askQuestion();
		}
		
	}

	private void askQuestion() {
		if (currentCategory().equals(POP_CATEGORY))
			log.add(popQuestions.removeFirst());
		if (currentCategory().equals(SCIENCE_CATEGORY))
			log.add(scienceQuestions.removeFirst());
		if (currentCategory().equals(SPORTS_CATEGORY))
			log.add(sportsQuestions.removeFirst());
		if (currentCategory().equals(ROCK_CATEGORY))
			log.add(rockQuestions.removeFirst());
	}
	
	
	private String currentCategory() {
		if (places[currentPlayer] == 0) return POP_CATEGORY;
		if (places[currentPlayer] == 4) return POP_CATEGORY;
		if (places[currentPlayer] == 8) return POP_CATEGORY;
		if (places[currentPlayer] == 1) return SCIENCE_CATEGORY;
		if (places[currentPlayer] == 5) return SCIENCE_CATEGORY;
		if (places[currentPlayer] == 9) return SCIENCE_CATEGORY;
		if (places[currentPlayer] == 2) return SPORTS_CATEGORY;
		if (places[currentPlayer] == 6) return SPORTS_CATEGORY;
		if (places[currentPlayer] == 10) return SPORTS_CATEGORY;
		return ROCK_CATEGORY;
	}

	@Override
    public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				log.add("Answer was correct!!!!");
				purses[currentPlayer]++;
				log.add(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {

            log.add("Answer was corrent!!!!");
            purses[currentPlayer]++;
			log.add(players.get(currentPlayer)
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}

	@Override
    public boolean wrongAnswer() {
		log.add("Question was incorrectly answered");
		log.add(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) {
            currentPlayer = 0;
        }
		return true;
	}


	private boolean didPlayerWin() {
		return purses[currentPlayer] != 6;
	}
}
