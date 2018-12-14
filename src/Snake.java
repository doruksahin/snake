import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snake extends JFrame {

    public Snake(String difficulty){
        initUI(difficulty);
    }

    private void initUI(String difficulty) {
        add(new Board(difficulty));
        setResizable(false);
        pack();
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake();
            ex.setVisible(true);
        });
    }
    */
}