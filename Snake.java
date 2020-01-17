package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener// actionlistener for processing any action... keylistener for direction keys and knowing keystrokes
{
	public static Snake snake;// MADE A CONSTRUCTOR OF CLASS SNAKE
	public JFrame jframe;// DECLARED A NEW JFRAME CALLED jframe
	public RenderPanel renderPanel;// MADE A CONSTRUCTOR OF CLASS RENDERPANEL
	public Timer timer = new Timer(20, this); // DELAY PARAMETER 
	public ArrayList<Point> snakeParts = new ArrayList<Point>();// DECLARE ARRAYLIST
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10; // FINAL VALUES SO THAT THEY CAN'T BE CHANGED THROUGHOUT THE CODE
	public int ticks = 0, direction = DOWN, score, tailLength = 10, time;// DECLARE VALUES
	public Point head, cherry; // DECLARE A POINT
	public Random random;// DECLARES RANDOM CONSTRUCTOR SO THAT IT CAN BE USED TO MAKE RANDOM POSITION OF CHERRY ON THE FRAME
	public boolean over = false, paused;//DECLARE VALUES
	public Dimension dim;// DECLARE DIMENSION

	public Snake()
	{
		dim = Toolkit.getDefaultToolkit().getScreenSize(); 
		jframe = new JFrame("Snake"); // NEW JFRAME CALLED SNAKE
		jframe.setVisible(true); // SO THAT JFRAME IS VISIBLE
		jframe.setSize(800, 700); // DECLARES SIZE OF THE FRAME
		jframe.setResizable(false); // DEFAULT VALUE WHICH WILL BE CHANGED LATER ON DURING IMPLEMENTATION
		jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2); // DECLARE SIZE OF THE LOCATION
		jframe.add(renderPanel = new RenderPanel()); // TO USE THE RENDERPANEL WHICH CONTAINS THE COLOR AND PRESENTATION OF THE PANEL 
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ADVISED TO USE THIS SO NO SECURITY EXCEPTION IS THROWN
		jframe.addKeyListener(this);// FUNCTION IN THE CODE WHICH ALLOWS INPUT THROUGH THE KEY STROKES
		startGame(); // FUNCTION WHICH DECLARES INITIAL VALUES WHEN THE GAME STARTS WHICH KEEP UPDATING AS GAME GOES BY
	}

	public void startGame()// ALL DEFAULT VALUES ARE DECLARED IN THIS FUNCTION -> START POSITION
	{
		over = false;
		paused = false;
		time = 0;
		score = 0;
		tailLength = 10;
		ticks = 0;
		direction = DOWN;// DEFAULT DIRECTION
		head = new Point(0, -1); // STARTING FROM -1 SO THAT IT SEEMS IT IS STARTING FROM BEHIND
		random = new Random();// DECLARING RANDOM FUNCTION
		snakeParts.clear(); // LENGTH HAS NOT CHANGED AS NO CHERRY EATEN YET
		cherry = new Point(random.nextInt(79), random.nextInt(66));// DECLARES NEW CHERRY ON RANDOM POSITION WITHIN THESE LIMITS
		timer.start(); //  TIMER STARTS AND TIME IN SECONDS START
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		renderPanel.repaint();
		ticks++;//RATE/SPEED AT WHICH THE SNAKE MOVES... TICKS%2 -> FAST... TICKS%10 -> SLOW

		if (ticks % 2 == 0 && head != null && !over && !paused) // CONDINTONS SO THAT SNAKE ENTERS IN IT
		{
			time++;// TIME IN SECONDS WILL KEEP INCREASING

			snakeParts.add(new Point(head.x, head.y)); // NEW POINT WHERE IT STARTS

			if (direction == UP) //  CONDITION #1
			{
				if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1)) // CONDITION IF IT HAS TO FOLLOW THAT DIRECTION
				{
					head = new Point(head.x, head.y - 1); // ANOTHER BLOCK IS ADDED TO THE HEAD OF THE SNAKE 
				}
				else
				{
					over = true; // OTHERWISE GAME = OVER

				}
			}

			if (direction == DOWN) // CONDITION #2
			{
				if (head.y + 1 < 67 && noTailAt(head.x, head.y + 1))// CONDITION IF IT HAS TO FOLLOW THAT DIRECTION
				{
					head = new Point(head.x, head.y + 1);// ANOTHER BLOCK IS ADDED TO THE HEAD OF THE SNAKE 
				}
				else
				{
					over = true; // OTHERWISE GAME = OVER
				}
			}

			if (direction == LEFT)// CONDITION #3
			{
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))// CONDITION IF IT HAS TO FOLLOW THAT DIRECTION
				{
					head = new Point(head.x - 1, head.y);// ANOTHER BLOCK IS ADDED TO THE HEAD OF THE SNAKE 
				}
				else
				{
					over = true; // OTHERWISE GAME = OVER
				}
			}

			if (direction == RIGHT)// CONDITION #4
			{
				if (head.x + 1 < 80 && noTailAt(head.x + 1, head.y))// CONDITION IF IT HAS TO FOLLOW THAT DIRECTION
				{
					head = new Point(head.x + 1, head.y);// ANOTHER BLOCK IS ADDED TO THE HEAD OF THE SNAKE
				}
				else
				{
					over = true; // OTHERWISE GAME = OVER
				}
			}

			if (snakeParts.size() > tailLength) // CONDITION TO CHECK IF SNAKE BIT ITSELF AND GAME ENDS 
			{
				snakeParts.remove(0); // PRINTS YOU LOSE

			}

			if (cherry != null) // CHERRY EXISTS ON THE FRAME
			{
				if (head.equals(cherry)) // HEAD OF SNAKE HITS THE CHERRY
				{
					score += 10; // SCORE INCREMENTED
					tailLength++; // LENGTH OF THE TAIL IS INCREASED
					cherry.setLocation(random.nextInt(79), random.nextInt(66)); // limit is 79 and 66. so it has ot between it
				}
			}
		}
	}

	public boolean noTailAt(int x, int y)// to end the movement of the snake
	{
		for (Point point : snakeParts)// run a for each loop till point REACHES snakeParts
		{
			if (point.equals(new Point(x, y))) // IF THE POINT HITS ON THE HEAD OF THE SNAKE.. IT WILL RETURN FALSE
			{
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args)
	{
		snake = new Snake();// CONSTRUCTOR OF THE SNAKE CLASS 
	}

	@Override
	public void keyPressed(KeyEvent e)// take input of key pressed
	{
		int i = e.getKeyCode();// ASSIGNING VALUE OF i THROUGH E.GETKEYCODE AND CHECKING WHAT KEY IS PRESSED

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT) // <- || A
		{
			direction = LEFT; 
		}

		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT) // -> || D
		{
			direction = RIGHT;
		}

		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN) // UP || W
		{
			direction = UP;
		}

		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP) // DOWN || S
		{
			direction = DOWN;
		}

		if (i == KeyEvent.VK_SPACE) // SPACE KEY
		{
			if (over) // IF GAME IS OVER... RESTART AND CALL THE SAME FUNCTION AGAIN WHICH STARTS FROM DEFAULT VALUES
			{
				startGame();
			}
                        else // FOR PAUSING THE GAME IN BETWEEN ANY TIME
			{
				paused = !paused;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

}