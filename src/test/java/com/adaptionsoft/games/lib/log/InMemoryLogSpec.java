package com.adaptionsoft.games.lib.log;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryLogSpec {

    // todo doit être immutable

    @Test
    public void should_be_empty_at_start() throws Exception {
        InMemoryLog log = new InMemoryLog();
        List<String> messages = log.messages();
        assertThat(messages).isEmpty();
    }

    @Test
    public void should_remember_messages_sent() throws Exception {
        InMemoryLog log = new InMemoryLog();
        String message = "coucou";

        log.add(message);

        assertThat(log.messages()).containsOnly(message);
    }

    @Test
    public void its_messages_should_not_be_deletable() throws Exception {
        InMemoryLog log = new InMemoryLog();
        String message = "coucou";
        log.add(message);
        List<String> messages = log.messages();

        messages.clear();

        assertThat(log.messages()).containsOnly(message);
    }
}