package Snake;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        Frame frame = new Frame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(710, 490);
        frame.setLocation(300,140);
        frame.setResizable(false);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
class Frame extends JFrame
{
    public Frame()
    {
        Panel panel = new Panel();
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New");
        JMenuItem pause = new JMenuItem("Pause");
        JMenuItem exit = new JMenuItem("Exit");
        newGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panel.snake.snakeBlocks = new ArrayList<>();
                for (int i = 10; i <= 90; i+=20)
                {
                    panel.snake.snakeBlocks.add(new SnakeBlock(i, 10));
                }
                panel.snake.dX = SnakeBlock.SNAKEBLOCKSIZE;
                panel.snake.dY = 0;
                panel.snake.snakeFood = panel.snake.newSnakeFood();
                panel.MAINTIMER.start();
                panel.LEVELLABEL.setText("");
                panel.snake.levelTimer.start();
            }
        });
        pause.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (panel.MAINTIMER.isRunning() == true)
                {
                    pause.setText("Continue");
                    panel.MAINTIMER.stop();
                    if (panel.snake.levelTimer.isRunning() == true)
                    panel.snake.levelTimer.stop();
                } else
                {
                    pause.setText("Pause");
                    panel.MAINTIMER.start();
                    if (panel.snake.levelTimer.isRunning() == false)
                        panel.snake.levelTimer.start();
                }

            }
        });
        exit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        gameMenu.add(newGame);
        gameMenu.add(pause);
        gameMenu.addSeparator();
        gameMenu.add(exit);
        menuBar.add(gameMenu);
        this.setJMenuBar(menuBar);
        Container pane = getContentPane();
        pane.add(panel);
        pack();
    }
}
class Panel extends JPanel implements ActionListener
{
    public static int DELAY;
    public static Timer MAINTIMER;
    MySnake snake;
    private int screenHeight;
    private int screenWidth;
    SnakeKeyAdapter keyAdapter;
    public static JLabel LEVELLABEL;
    public Panel()
    {
        DELAY = 250;
        MAINTIMER = new Timer(DELAY, this);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;
        snake = new MySnake();
        keyAdapter = new SnakeKeyAdapter();
        addKeyListener(keyAdapter);
        Font font = new Font("Arial", Font.BOLD, 26);
        LEVELLABEL = new JLabel("Press Enter to start new game");
        LEVELLABEL.setFont(font);
        LEVELLABEL.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, LEVELLABEL);
        setFocusable(true);
    }
    public void paintComponent(Graphics g)
    {
        g.setColor(new Color(0xC4D6E1));
        g.fillRect(0,0, 710, 470);
        g.setColor(Color.BLACK);
        g.drawRect(10,10, 680, 420);
        g.setColor(new Color(0x01AF00));
        for (int i = 0; i < snake.snakeBlocks.size(); i++)
        {
            SnakeBlock block = snake.snakeBlocks.get(i);
            g.fillOval(block.getX_BLOCK(), block.getY_BLOCK(), SnakeBlock.SNAKEBLOCKSIZE, SnakeBlock.SNAKEBLOCKSIZE);
        }
        if (snake.snakeFood != null)
        {
            g.setColor(new Color(0xE00007));
            g.fillOval(snake.snakeFood.getX_BLOCK(), snake.snakeFood.getY_BLOCK(), SnakeBlock.SNAKEBLOCKSIZE, SnakeBlock.SNAKEBLOCKSIZE);
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        snake.eat();
        snake.move();
        crash();
        repaint();
        addKeyListener(keyAdapter);
    }
    private class SnakeKeyAdapter extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            snake.keyPressed(e);

            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                Panel.MAINTIMER.start();
                Panel.LEVELLABEL.setText("");
                snake.levelTimer.start();
            }
            removeKeyListener(this);
        }

    }

    public int getScreenHeight()
    {
        return screenHeight;
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public void crash()
    {
        SnakeBlock headBlock = snake.snakeBlocks.get(snake.snakeBlocks.size()-1);
        if (headBlock.getX_BLOCK() < 0 || headBlock.getX_BLOCK() >= 690 ||
                headBlock.getY_BLOCK() < 0 || headBlock.getY_BLOCK() >= 430)
        {
            endGame();
        }
        for (int i = 0; i < snake.snakeBlocks.size()-1; i++)
        {
            SnakeBlock mainBlock = snake.snakeBlocks.get(snake.snakeBlocks.size()-1);
            SnakeBlock currentBlock = snake.snakeBlocks.get(i);
            if (mainBlock.getX_BLOCK() == currentBlock.getX_BLOCK() && mainBlock.getY_BLOCK() == currentBlock.getY_BLOCK())
            {
                endGame();
            }
        }
    }
    public void endGame()
    {
        JOptionPane.showMessageDialog(null,"Game Over. Your score is " + snake.POINTS);
        System.exit(0);
    }
}
