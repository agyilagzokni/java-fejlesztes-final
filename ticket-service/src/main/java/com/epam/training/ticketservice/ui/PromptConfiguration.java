package com.epam.training.ticketservice.ui;

import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

import org.jline.utils.AttributedString;

@Configuration
public class PromptConfiguration implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("Ticket service>");
    }
}
