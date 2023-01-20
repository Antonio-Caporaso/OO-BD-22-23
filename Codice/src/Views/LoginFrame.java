package Views;

import Controller.Driver;
import Views.Listeners.LoginListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

public class LoginFrame extends JFrame {
    private Driver controller;
    private JPanel loginPanel;
    private JPanelBackground backgroundPanel;
    private JTextField username;
    private JPasswordField password;
    private ImageIcon conferenceBackground;
    private ImageIcon winIcon;
    private JButton okButton;
    private JTextField textField;
    private JLabel errorLabel;

    public LoginFrame(Driver d) {
        super("Login");
        Container c = this.getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controller = d;
        setSize(1280,720);
        setResizable(false);
        getContentPane().setLayout(null);

        winIcon = new ImageIcon(this.getClass().getResource("/Images/Logo.jpg"));
        setIconImage(winIcon.getImage());

        conferenceBackground = new ImageIcon(getClass().getResource("/Images/Background.png"));
        backgroundPanel = new JPanelBackground(conferenceBackground.getImage());
        c.add(backgroundPanel);
        backgroundPanel.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 520, 710);
        backgroundPanel.add(panel);
        panel.setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Benvenuto!", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD,18), new Color(51, 51, 51)));
        panel_1.setBounds(56, 240, 408, 240);
        panel.add(panel_1);
        panel_1.setLayout(null);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(12, 28, 384, 151);
        panel_1.add(panel_2);
        panel_2.setLayout(null);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 18));
        lblUsername.setBounds(12, 58, 112, 15);
        panel_2.add(lblUsername);

        username = new JTextField();
        username.setBounds(142, 53, 230, 25);
        username.setFont(new Font("Arial", Font.PLAIN, 18));
        panel_2.add(username);
        username.setColumns(10);

        errorLabel = new JLabel("");
        errorLabel.setBounds(142, 0, 230, 27);
        panel_2.add(errorLabel);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 18));
        lblPassword.setBounds(12, 119, 360, 15);
        panel_2.add(lblPassword);


        okButton = new JButton("Ok");
        okButton.setBounds(145, 206, 117, 25);
        okButton.setFont(new Font("Arial", Font.PLAIN, 18));
        ActionListener listener = new LoginListener(this);
        okButton.addActionListener(listener);
        panel_1.add(okButton);

        password = new JPasswordField();
        password.setBounds(143,114, 229, 25);
        panel_2.add(password);
    }
    public void showJLabel(){
        if(username.getText().equals("")){
            errorLabel.setFont(new Font("Arial",Font.PLAIN, 14));
            errorLabel.setForeground(Color.red);
            errorLabel.setText("Inserire username e password");
        }
        else
            errorLabel.setText("");
    }
}
