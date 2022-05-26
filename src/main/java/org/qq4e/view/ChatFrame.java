package org.qq4e.view;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public abstract class ChatFrame extends JFrame {
    public static final SimpleDateFormat DATE_FORMAT;

    protected final JPanel chatPanel;
    protected boolean showLatest = true;

    private final JTextArea txtMessageArea;
    private final JScrollPane txtMessageAreaScrollPane;

    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    public ChatFrame(Contact contact, Bot bot) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.chatPanel = new JPanel(new GridBagLayout());

        this.txtMessageArea = new JTextArea();
        this.txtMessageArea.setEditable(false);
        this.txtMessageAreaScrollPane = new JScrollPane(this.txtMessageArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.txtMessageAreaScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
            var vertical = this.txtMessageAreaScrollPane.getVerticalScrollBar();
            this.showLatest = vertical.getVisibleAmount() + e.getValue() >= vertical.getMaximum();
        });
        this.chatPanel.add(this.txtMessageAreaScrollPane, new GridBagConstraints(0, 0, 5, 36, 1, 0.75,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        var txtSendingArea = new JTextArea();
        this.chatPanel.add(new JScrollPane(txtSendingArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
                new GridBagConstraints(0, 36, 5, 12, 1, 0.25,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        var btnSend = new JButton("发送");
        btnSend.setSize(800, 50);
        btnSend.addActionListener(e -> new Thread(() -> {
            try {
                var message = new MessageChainBuilder()
                        .append(txtSendingArea.getText())
                        .build();
                contact.sendMessage(message);
                this.appendMessage(bot.getNick(), message);
                txtSendingArea.setText("");
            } catch (IllegalArgumentException ex) {
                // TODO Message is empty, do nothing or notice user
            }
        }).start());
        this.chatPanel.add(btnSend, new GridBagConstraints(4, 48, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        this.setVisible(true);
    }

    public void appendMessage(String senderName, MessageChain message) {
        var showLatest = this.showLatest;

        this.txtMessageArea.append(String.format("%s %s\n", senderName, DATE_FORMAT.format(new Date(System.currentTimeMillis()))));
        this.txtMessageArea.append(message.contentToString());
        this.txtMessageArea.append("\n");
        this.txtMessageAreaScrollPane.doLayout();

        var vertical = this.txtMessageAreaScrollPane.getVerticalScrollBar();
        if (showLatest) vertical.setValue(vertical.getMaximum());
        this.showLatest = showLatest;
    }
}
