package org.qq4e.view;

import net.mamoe.mirai.BotFactory;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private final JButton btnLogin;
    private final JTextField txtId;
    private final JTextField txtPwd;

    public LoginFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.btnLogin = new JButton();
        this.txtId = new JTextField();
        this.txtPwd = new JPasswordField();
        this.btnLogin.addActionListener(e -> {
            var bot = BotFactory.INSTANCE.newBot(Long.parseLong(txtId.getText()), txtPwd.getText());
            bot.login();
            // TODO Exceptions catch
            new MainFrame(bot);
            this.dispose();
        });

        this.setVisible(true);
    }
}
