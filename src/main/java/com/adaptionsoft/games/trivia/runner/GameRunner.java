
package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.trivia.runner.turns.RandomTurns;
import com.adaptionsoft.games.trivia.runner.turns.Turn;
import com.adaptionsoft.games.trivia.runner.turns.TurnsSnapshot;
import com.adaptionsoft.games.trivia.runner.turns.Turns;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.lib.log.SystemOutLog;
import com.adaptionsoft.games.uglytrivia.RefactoredGame;

import java.util.List;


public class GameRunner {

    // fails :
    //     - trop de changements sans tests
    //     - passage à java 8 sans tests

    public static void main(String[] args) {
        Game game = new RefactoredGame(new SystemOutLog());
        Turns turns = new TurnsSnapshot(new RandomTurns());
        runSimulation(game, turns);
    }

    public static void runSimulation(Game game, Turns turns) {
        addPlayers(game);
        boolean notAWinner;
        do {
            notAWinner = simulate(game, turns.turn());
        } while (notAWinner);
    }

    public static void replay(Game game, List<Turn> turns) {
        addPlayers(game);
        turns.forEach(turn -> simulate(game, turn));
    }

    private static void addPlayers(Game game) {
        game.add("Chet");
        game.add("Pat");
        game.add("Sue");
    }

    private static boolean simulate(Game game, Turn turn) {
        // FIXME potentielle dépendance temporelle si on ne peut pas roll sans joeurs
        game.roll(turn.roll);
        if (turn.answersCorrectly) {
            // FIXME inverser la logique à cet endroit
            return game.wrongAnswer();
        } else {
            return game.wasCorrectlyAnswered();
        }
    }

    // TODO faire une meilleure api
    /*
     *
     * - Un objet player
     * - Le roll prend le player et lance une exception si ce n'est pas son tour
     * - hasEnded comme méthode pour savoir si on a un gagnant
     * - Peut-être une autre classe genre WonGame pour avoir le gagnant ?
     *
     */
}
