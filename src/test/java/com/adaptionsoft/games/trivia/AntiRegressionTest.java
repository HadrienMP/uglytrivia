package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.lib.log.InMemoryLog;
import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.trivia.runner.turns.Turn;
import com.adaptionsoft.games.trivia.runner.turns.RandomTurns;
import com.adaptionsoft.games.trivia.runner.turns.TurnsSnapshot;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.LegacyGame;
import com.adaptionsoft.games.uglytrivia.RefactoredGame;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.Test;

import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AntiRegressionTest {

    @Test
	public void the_refactored_game_produces_the_same_log_as_the_legacy_one() throws Exception {
        List<Turn> turns = turns();

        InMemoryLog refactoredGameLog = refactoredMessages(turns);
        InMemoryLog legacyGameLog = legacyMessages(turns);

        assertThat(refactoredGameLog.messages()).isEqualTo(legacyGameLog.messages());
    }

    private List<Turn> turns() throws URISyntaxException, IOException {
        Gson gson = new Gson();
        String jsonTurns = jsonTurns();
        Turns turns = gson.fromJson(jsonTurns, Turns.class);
        return turns.turns;
    }

    private String jsonTurns() throws URISyntaxException, IOException {
        URI turnsUri = this.getClass()
                .getResource("/turns.json")
                .toURI();
        Path path = Paths.get(turnsUri);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
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

    private InMemoryLog refactoredMessages(List<Turn> turns) {
        InMemoryLog refactoredGameLog = new InMemoryLog();
        Game refactoredGame = new RefactoredGame(refactoredGameLog);
        GameRunner.replay(refactoredGame, turns);
        return refactoredGameLog;
    }
}
