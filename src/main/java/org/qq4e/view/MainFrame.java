package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;

import javax.swing.*;

public class MainFrame extends JFrame {
    private final Bot self;

    public MainFrame(Bot bot) {
        this.self = bot;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        bot.getEventChannel().subscribeAlways(MessageEvent.class, e -> {
            // TODO Notice user
        });

        this.setVisible(true);
    }
}
