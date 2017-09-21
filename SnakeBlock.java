package Snake;

/**
 * Created by Nikolaichik on 17.08.2017.
 */
public class SnakeBlock
{
    public static int SNAKEBLOCKSIZE = 20;
    private int X_BLOCK;
    private int Y_BLOCK;

    public SnakeBlock(int X, int Y)
    {
        X_BLOCK = X;
        Y_BLOCK = Y;
    }
    public void setXYBlocks(int X, int Y)
    {
        X_BLOCK = X;
        Y_BLOCK = Y;
    }
    public int getX_BLOCK()
    {
        return X_BLOCK;
    }
    public int getY_BLOCK() { return Y_BLOCK; }
}
