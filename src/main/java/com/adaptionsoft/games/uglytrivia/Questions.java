package com.adaptionsoft.games.uglytrivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.adaptionsoft.games.uglytrivia.Category.*;

public class Questions {

    private Map<Category, LinkedList<String>> questions = new HashMap<Category, LinkedList<String>>() {{
        put(POP, new LinkedList<>());
        put(SCIENCE, new LinkedList<>());
        put(SPORTS, new LinkedList<>());
        put(ROCK, new LinkedList<>());
    }};

    public Questions() {
        // fixme Cette initialisation n'a peut être pas sa place ici
        for (int i = 0; i < 50; i++) {
            questions.get(POP).addLast("Pop Question " + i);
            questions.get(SCIENCE).addLast(("Science Question " + i));
            questions.get(SPORTS).addLast(("Sports Question " + i));
            questions.get(ROCK).addLast("Rock Question " + i);
        }
    }

    public String popQuestion(Category category) {
        return questions.get(category).removeFirst();
    }
}
