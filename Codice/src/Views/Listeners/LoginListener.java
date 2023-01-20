package Views.Listeners;

import Views.LoginFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginListener implements ActionListener {
    private LoginFrame fr;
    public LoginListener(LoginFrame fr){
        this.fr = fr;
    }

    public void actionPerformed(ActionEvent e){
        fr.showJLabel();
    }
}
