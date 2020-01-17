package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

@SuppressWarnings("serial")// does not give warning regarding a missing serializable class/ serialversionuid 

public class RenderPanel extends JPanel
{

	public static final Color GREEN = new Color(1666073); // COLOR CODE OF THIS COLOR

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);// to call parent class method paint component
		
		Snake snake = Snake.snake; // object

		g.setColor(GREEN);// background color
		
		g.fillRect(0, 0, 800, 700);// frame size

		g.setColor(Color.BLACK);// snake color

		for (Point point : snake.snakeParts) // for each loop till snakeParts exist
		{
			g.fillRect(point.x * Snake.SCALE, point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE); // fill this much in this area
		}
		
		g.fillRect(snake.head.x * Snake.SCALE, snake.head.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		
		g.setColor(Color.RED);// cherry color
		
		g.fillRect(snake.cherry.x * Snake.SCALE, snake.cherry.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		
		String string = "SCORE: " + snake.score + ", LENGTH: " + snake.tailLength + ", TIME SINCE STARTED: " + snake.time / 20;// information
		
		g.setColor(Color.white); // color of info
		
		g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10); // marks system

		string = "YOU LOST"; // printing lost statemet

		if (snake.over)//condition
		{
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}

		string = "PAUSE";// printing pause statement

		if (snake.paused && !snake.over)
		{
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}
	}
}