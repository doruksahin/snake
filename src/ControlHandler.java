import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.*;


public class ControlHandler {
    private Board board;

    private JLabel endText;
    private JLabel timePassed;
    private JLabel currentSpeed;

    Dimension npD, npE, npF;

    //
    public ControlHandler(Board board) {
        this.board = board;

        endText = new JLabel("NO PATH");
        endText.setName("endText");
        endText.setForeground(Color.white);
        endText.setBackground(Color.white);
        Font bigTextFont = new Font("arial", Font.BOLD, 72);
        endText.setFont(bigTextFont);
        npD = endText.getPreferredSize();
        endText.setVisible(false);

        timePassed = new JLabel("aaa");
        timePassed.setName("timePassed");
        timePassed.setForeground(Color.gray);
        Font timePassedFont = new Font("arial", Font.BOLD, 50);
        timePassed.setFont(timePassedFont);
        npE = timePassed.getPreferredSize();
        timePassed.setVisible(false);

        currentSpeed = new JLabel("Speed: ");
        currentSpeed.setName("currentSpeed");
        currentSpeed.setForeground(Color.white);
        npF = currentSpeed.getPreferredSize();
        currentSpeed.setVisible(true);

    }


    public JLabel getEndText(){
        return endText;
    }
    public JLabel getTimePassed(){
        return timePassed;
    }
    public JLabel getCurrentSpeed(){
        return currentSpeed;
    }


    



    // Tum pozisyonlar ayarlandi.
    // That doesnt work
    public void position() {
        timePassed.setBounds((int)((board.getWidth()/2 - 50)-(npE.getWidth()/2)),
                (int)((board.getHeight()/2)+200),
                400, (int)npE.getHeight());

        endText.setBounds((int)((board.getWidth()/2)-(npD.getWidth()/2)),
                (int)((board.getHeight()/2)-70),
                (int)npD.getWidth(), (int)npD.getHeight());

        currentSpeed.setBounds((int)(board.getWidth() - npF.getWidth() / 2), board.getHeight(), 10, 10);
    }

    // Tum itemlar board'e eklenmeli.
    public void addAll() {
        board.add(endText);
        board.add(timePassed);
        board.add(currentSpeed);
    }

}