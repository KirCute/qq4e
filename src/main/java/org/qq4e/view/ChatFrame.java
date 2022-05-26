package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public abstract class ChatFrame extends JFrame {
    protected final JTextArea txtMessageArea;
    protected final JScrollPane txtMessageAreaScrollPane;
    protected final JPanel chatPanel;

    public ChatFrame(Contact contact, Bot bot) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        var simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        this.chatPanel = new JPanel(new GridBagLayout());

        this.txtMessageArea = new JTextArea();
        this.txtMessageArea.setEditable(false);
        this.txtMessageAreaScrollPane = new JScrollPane(this.txtMessageArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.chatPanel.add(this.txtMessageArea, new GridBagConstraints(0, 0, 5, 36, 1, 0.75,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        var txtSendingArea = new JTextArea();
        this.chatPanel.add(new JScrollPane(txtSendingArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
                new GridBagConstraints(0, 36, 5, 12, 1, 0.25,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        var btnSend = new JButton("发送");
        btnSend.setSize(800, 50);
        btnSend.addActionListener(e -> {
            try {
                contact.sendMessage(new MessageChainBuilder()
                        .append(txtSendingArea.getText())
                        .build()
                );
            } catch (IllegalArgumentException ex) {
                // TODO Message is empty, do nothing or notice user
            }
            this.txtMessageArea.append(String.format("%s %s\n", bot.getNick(), simpleDateFormat.format(new Date(System.currentTimeMillis()))));
            this.txtMessageArea.append(txtSendingArea.getText());
            this.txtMessageArea.append("\n");
            txtSendingArea.setText("");
        });
        this.chatPanel.add(btnSend, new GridBagConstraints(4, 48, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        this.setVisible(true);
    }
}
