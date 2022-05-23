package org.qq4e.main;

import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public final class Main {
    public static void main(String[] args) {
        final long RECEIVE_ID = 0L;
        final long ID = 0L;
        final String PASSWORD = "123456";

        BotConfiguration.getDefault().setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);
        var bot = BotFactory.INSTANCE.newBot(ID, PASSWORD);
        bot.login();

        bot.getEventChannel().subscribeAlways(MessageEvent.class, e -> {
            for (var friend : bot.getFriends()) {
                if (friend.getId() == RECEIVE_ID) {
                    try {
                        friend.sendMessage(new MessageChainBuilder()
                                .append(new String(new String("来自".getBytes(), StandardCharsets.UTF_8).getBytes("GBK")))
                                .append(e.getSenderName())
                                .append(":\n")
                                .append(e.getMessage())
                                .build());
                    } catch (UnsupportedEncodingException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
    }
}
