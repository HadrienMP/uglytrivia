package com.adaptionsoft.games.lib.log;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLog implements Log {

    private List<String> messages = new ArrayList<String>();

    @Override
    public void add(String message) {
        messages.add(message);
    }

    public List<String> messages() {
        return new ArrayList<String>(messages);
    }
}
