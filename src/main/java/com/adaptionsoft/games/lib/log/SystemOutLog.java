package com.adaptionsoft.games.lib.log;

public class SystemOutLog implements Log {
    @Override
    public void add(String message) {
        System.out.println(message);
    }
}
