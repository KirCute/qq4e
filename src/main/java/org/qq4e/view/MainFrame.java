package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(Bot bot) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 900);
        this.setTitle("QQ for E");

        var chatTabs = new JTabbedPane();
        var friendList = new JPanel();
        friendList.setLayout(new BoxLayout(friendList, BoxLayout.PAGE_AXIS));
        for (var friend : bot.getFriends()) {
            var entrance = new JButton(friend.getNick());
            entrance.setSize(300, 50);
            entrance.addActionListener(e -> {
                new FriendChatFrame(friend, bot);
            });
            friendList.add(entrance);
        }
        chatTabs.addTab("好友", new JScrollPane(friendList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        var groupList = new JPanel();
        groupList.setLayout(new BoxLayout(groupList, BoxLayout.PAGE_AXIS));
        for (var group : bot.getGroups()) {
            var entrance = new JButton(group.getName());
            entrance.setSize(300, 50);
            entrance.addActionListener(e -> {
                new GroupChatFrame(group, bot);
            });
            groupList.add(entrance);
        }
        chatTabs.addTab("群", new JScrollPane(groupList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        this.add(chatTabs);

        bot.getEventChannel().subscribeAlways(MessageEvent.class, e -> {
            // TODO Notice User
            System.out.println(e.getMessage().get(0).contentToString());
        });

        this.setVisible(true);
    }
}
