import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(){
        /* GamePanel panel = new GamePanel();
        this.add(panel); */
         this.add(new GamePanel()); // create a new Panel
         this.setTitle("SNAKE"); // add a title
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
         this.setResizable(false); //
         this.pack(); // ist going to take our JFrame and fit it snugly around all  the component that we add to the Frame
        this.setVisible(true);
        this.setLocationRelativeTo(null); // when you want that this Window appears in the middle of the computer
    }
}
