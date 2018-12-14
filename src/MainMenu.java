import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    JButton easyGame = new JButton("Easy");
    JButton mediumGame = new JButton("Medium");
    JButton hardGame = new JButton("Hard");
    // Panel yerine MainMenu'ye addleyince sadece Hard gorunuyor.
    JPanel panel = new JPanel();

    public MainMenu(){
        initMainMenu();
    }

    private void initMainMenu(){
        addAll();
        setActionListeners();

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);


    }

    // Zorluk butonlarina basinca gerceklesen olaylar.
    private void setActionListeners(){
        easyGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame easySnake = new Snake("Easy");
                easySnake.setVisible(true);
                // MainMenu ekranini oyun baslayinca gizle.
                setVisible(false);
            }
        });

        mediumGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame mediumSnake = new Snake("Medium");
                mediumSnake.setVisible(true);
                setVisible(false);
            }
        });

        hardGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame hardSnake = new Snake("Hard");
                hardSnake.setVisible(true);
                setVisible(false);
            }
        });
    }


    // Tum componentleri MainMenu'ye ekle.
    private void addAll(){
        panel.add(easyGame);
        panel.add(mediumGame);
        panel.add(hardGame);
        add(panel);
    }


    public static void main(String[] args){
        MainMenu mainMenu = new MainMenu();
    }



}
