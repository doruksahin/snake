import java.awt.EventQueue;
import javax.swing.JFrame;

public class SnakeGame extends JFrame {


    public SnakeGame(String difficulty){
        initUI(difficulty);
    }

    private void initUI(String difficulty) {
        add(new Board(difficulty));
        setResizable(true);
        pack();
        setTitle("SnakeGame");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new SnakeGame();
            ex.setVisible(true);
        });
    }
    */
}