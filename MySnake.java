package Snake;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nikolaichik on 17.08.2017.
 */
public class MySnake extends GameLevels
{
    protected ArrayList<SnakeBlock> snakeBlocks;
    protected SnakeBlock snakeFood;
    protected int dX;
    protected int dY;

    public MySnake()
    {
        snakeBlocks = new ArrayList<>();
        for (int i = 10; i <= 90; i+=20)
        {
            snakeBlocks.add(new SnakeBlock(i, 10));
        }
        dX = SnakeBlock.SNAKEBLOCKSIZE;
        dY = 0;
        snakeFood = newSnakeFood();
    }

    public void move()
    {
        for (int i = 0; i < snakeBlocks.size()-1; i++)
        {
            SnakeBlock nextBlock = snakeBlocks.get(i+1);
            snakeBlocks.get(i).setXYBlocks(nextBlock.getX_BLOCK(), nextBlock.getY_BLOCK());
        }
        SnakeBlock mainBlock = snakeBlocks.get(snakeBlocks.size()-1);
        mainBlock.setXYBlocks(mainBlock.getX_BLOCK()+dX, mainBlock.getY_BLOCK()+dY);

    }

    public SnakeBlock newSnakeFood()
    {
        Random random = new Random();
        int X = random.nextInt(34);
        int Y = random.nextInt( 21);
        if (X == 0) X = 1;
        if (Y == 0) Y = 1;
        snakeFood = new SnakeBlock(X * 20-10, Y * 20-10);
        return snakeFood;
    }

    public void eat()
    {
        SnakeBlock mainBlock = snakeBlocks.get(snakeBlocks.size()-1);
        if (mainBlock.getX_BLOCK() == snakeFood.getX_BLOCK() && mainBlock.getY_BLOCK() == snakeFood.getY_BLOCK())
        {
            snakeBlocks.add(0, snakeFood);
            snakeFood = newSnakeFood();
            POINTS++;
            if (POINTS % 5 == 0)
            {
                LEVEL++;
                Panel.DELAY -= 20;
                Panel.MAINTIMER.setDelay(Panel.DELAY);
                levelTimer.start();
            }
        }
    }

    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT)
        {
            if (dX == 0)
            {
                dX = SnakeBlock.SNAKEBLOCKSIZE;
                dY = 0;
            }
        }
        if (key == KeyEvent.VK_DOWN)
        {
            if (dY == 0)
            {
                dY = SnakeBlock.SNAKEBLOCKSIZE;
                dX = 0;
            }
        }
        if (key == KeyEvent.VK_LEFT)
        {
            if (dX == 0)
            {
                dX = -SnakeBlock.SNAKEBLOCKSIZE;
                dY = 0;
            }
        }
        if (key == KeyEvent.VK_UP)
        {
            if (dY == 0)
            {
                dY = -SnakeBlock.SNAKEBLOCKSIZE;
                dX = 0;
            }
        }
    }
}
