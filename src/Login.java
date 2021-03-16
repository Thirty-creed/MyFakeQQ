import javax.swing.*;

public class Login {
    public static void main(String[] args) {
        Login login=new Login();
        login.UI();
    }

    public void UI(){
        JFrame frame=new JFrame();
        frame.setSize(450,350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
