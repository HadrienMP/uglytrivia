package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.lib.log.InMemoryLog;
import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.trivia.runner.turns.Turn;
import com.adaptionsoft.games.trivia.runner.turns.RandomTurns;
import com.adaptionsoft.games.trivia.runner.turns.TurnsSnapshot;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.LegacyGame;
import com.adaptionsoft.games.uglytrivia.RefactoredGame;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AntiRegressionTest {

    @Test
	public void the_refactored_game_produces_the_same_log_as_the_legacy_one() throws Exception {
        TurnsSnapshot snapshot = new TurnsSnapshot(new RandomTurns());
        InMemoryLog refactoredGameLog = refactoredMessages(snapshot);

        InMemoryLog legacyGameLog = legacyMessages(snapshot.turns);

        assertThat(refactoredGameLog.messages()).isEqualTo(legacyGameLog.messages());
    }

    private InMemoryLog legacyMessages(List<Turn> turns) {
        InMemoryLog legacyGameLog = new InMemoryLog();
        Game legacyGame = new LegacyGame(legacyGameLog);
        GameRunner.replay(legacyGame, turns);
        return legacyGameLog;
    }

    private InMemoryLog refactoredMessages(TurnsSnapshot snapshot) {
        InMemoryLog refactoredGameLog = new InMemoryLog();
        Game refactoredGame = new RefactoredGame(refactoredGameLog);
        GameRunner.runSimulation(refactoredGame, snapshot);
        return refactoredGameLog;
    }
}
