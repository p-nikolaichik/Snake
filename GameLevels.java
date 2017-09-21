package Snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class GameLevels implements ActionListener
{
    public static int POINTS = 0;
    public static int LEVEL = 1;
    public static int count = 0;
    Timer levelTimer = new Timer(2000, this);

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (count == 1)
        {
            Panel.LEVELLABEL.setText("");
            count = 0;
            levelTimer.stop();
        } else{
            Panel.LEVELLABEL.setText("LEVEL " + LEVEL);
            count++;
        }
    }
}
