package finalProject;


import javax.swing.*;

public class main {

    public static void main(String[] args) {
        
    
       
       
       SwingUtilities.invokeLater(() -> {
           LoginModel model = new LoginModel();
           LoginView view = new LoginView();
           new LoginController(model, view);

           JFrame Frame = new JFrame("Login");
           Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           Frame.setSize(800, 800);
         //  Frame.add(view);
           Frame.setVisible(true);
           
            Signup signup=new Signup();
            Frame.add(signup);
       });
     

       
  
        
    }
}