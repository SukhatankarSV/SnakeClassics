package Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.*;
import java.util.*;

public class GamePlay extends JPanel implements KeyListener,ActionListener, Serializable
{
    ImageIcon titleImage;
    
    int[] snakeXLength=new int[750];	//For X-axis length of snake
    int[] snakeYLength=new int[750];  //For Y-axis length of snake
    int length=3;					//Initial length
    int level=1;
    int score=0;					//no doubt, your score!
    boolean flgLevel = false;
	//These boolean fields are taken in order to identify face direction of our snake
    boolean left=false;
    boolean right=false;
    boolean up=false;
    boolean down=false;
	ImageIcon obstacle;
    Integer enemyXPosArr[]={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};

    Integer enemyYPosArr[]={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
	LinkedList<Integer> enemyXPos = new LinkedList<Integer>(Arrays.asList(enemyXPosArr));
	LinkedList<Integer> enemyYPos = new LinkedList<Integer>(Arrays.asList(enemyYPosArr));
	LinkedList<Integer> obstacleXList = new LinkedList<Integer>();
	LinkedList<Integer> obstacleYList = new LinkedList<Integer>();
	//enemyXPos and enemyYPos are arrays used for providing cheese to our snake. (x,y) co-ordinates of cheese are determined through these values. 
    
    ImageIcon enemy;					//For cheese image
    Random random=new Random();		//random no. generator
    boolean flg=true;				//To check our snake is alive or not
    
    int moves=0;						//absolutely right as u might guessed, it's no. of moves
    
    ImageIcon leftmouth,rightmouth,upmouth,downmouth;
	//These're few ImageIcons for making our snake's mouth according to direction
    
    Timer timer ;					//Used for scheduling the game
    int delay=100;
    
    int xpos=random.nextInt(34);		//for getting array-index of enemyXPos array
    int ypos=random.nextInt(23);		////for getting array-index of enemyYPos array
    
    ImageIcon snakeimage;
    
    public GamePlay()
    {
        addKeyListener(this);	
        setFocusable(true);	
        setFocusTraversalKeysEnabled(false);
        
        timer=new Timer(delay,this);
        timer.start();
		System.out.println("GAME BEGIN");
    }
	
	//getters and setters
	
	Timer getTimer()
	{
		return timer;
	}
	
	void setTimer(Timer t)
	{
		timer = t;
	}
	
	ImageIcon getLeftMouth()
	{
		return leftmouth;
	}
	
	void setLeftMouth(ImageIcon i)
	{
		leftmouth = i;
	}
	
	ImageIcon getRightMouth()
	{
		return rightmouth;
	}
	
	void setRightMouth(ImageIcon i)
	{
		rightmouth = i;
	}
	
	ImageIcon getUpMouth()
	{
		return upmouth;
	}
	
	void setUpMouth(ImageIcon i)
	{
		upmouth = i;
	}
	
	ImageIcon getDownMouth()
	{
		return downmouth;
	}
	
	void setDownMouth(ImageIcon i)
	{
		downmouth = i;
	}
	
	ImageIcon getSnakeImage()
	{
		return snakeimage;
	}
	
	void setSnakeImage(ImageIcon i)
	{
		snakeimage = i;
	}
	
	ImageIcon getTitleImage()
	{
		return titleImage;
	}
	
	void setTitleImage(ImageIcon i)
	{
		titleImage = i;
	}
	
	int[] getSnakeXLength()
	{
		return snakeXLength;
	}
	
	void setSnakeXLength(int[] arr)
	{
		snakeXLength = arr;
	}
	
	int[] getSnakeYLength()
	{
		return snakeYLength;
	}
	
	void setLength(int l)
	{
		length = l;
	}
	
	int getLength()
	{
		return length;
	}
	
	int getLevel()
	{
		return level;
	}
	
	void setLevel(int a)
	{
		level = a;
	}
	
	int getScore()
	{
		return score;
	}
	
	void setScore(int s)
	{
		score = s;
	}
	
	boolean getLeft()
	{
		return left;
	}
	
	boolean getRight()
	{
		return right;
	}
	
	boolean getUp()
	{
		return up;
	}
	
	boolean getDown()
	{
		return down;
	}
	
	void setLeft(boolean b)
	{
		left = b;
	}
	
	void setRight(boolean b)
	{
		right = b;
	}
	
	void setUp(boolean b)
	{
		up = b;
	}
	
	void setDown(boolean b)
	{
		down = b;
	}
	

    
    public void paint(Graphics g)
    {
		System.out.println("GAME IN PAINT");
        if(moves==0)			//What to do if game just begins
        {			
            snakeXLength[2]=50;
            snakeXLength[1]=75;
            snakeXLength[0]=100;
            
            snakeYLength[2]=100;
            snakeYLength[1]=100;
            snakeYLength[0]=100;
            
        }
        //draw titleImage border
        titleImage=new ImageIcon("snaketitle.jpg");         //draw the titleImage
        titleImage.paintIcon(this, g, 25, 11);
        
        //Now draw border for gaming area
        g.setColor(Color.WHITE);
        g.drawRect(24, 74, 851, 557);
        
        //let's set background for gaming area 
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);
        
		//draw scores you triumphed
        g.setColor(Color.white);    
        g.setFont(new Font("arial",Font.BOLD,14));
        g.drawString("SCORE/S:"+score, 780, 30);
        
		//draw length of our snake
        g.setColor(Color.white);    
        g.setFont(new Font("arial",Font.BOLD,14));
        g.drawString("LENGTH:"+length, 780, 50);
        
		//Initial setting of mouth of snake (towards right)
        rightmouth=new ImageIcon("rightmouth.png");
        rightmouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);
        
		//Now we've to change direction of mouth as time proceeds 
        for(int a=0;a<length;a++)
        {
            if(a==0&&right)
            {
                rightmouth=new ImageIcon("rightmouth.png");
                rightmouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
            
            if(a==0&&left)
            {
                leftmouth=new ImageIcon("leftmouth.png");
                leftmouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
            
            if(a==0&&up)
            {
                upmouth=new ImageIcon("upmouth.png");
                upmouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
            
            if(a==0&&down)
            {
                downmouth=new ImageIcon("downmouth.png");
                downmouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
            
            if(a!=0)
            {
                snakeimage=new ImageIcon("snakeimage.png");
                snakeimage.paintIcon(this, g, snakeXLength[a], snakeYLength[a]);
            }
        }
        
		//Now let's draw cheese of our slippy snake!!!
        enemy=new ImageIcon("enemy.png");
        
		//Now this is the condition when our snake eats yummy cheese and increase it's length by 1

        if(enemyXPos.get(xpos)==snakeXLength[0] && enemyYPos.get(ypos)==snakeYLength[0])
        {	
            length++;
            score++;
            flgLevel = true;
            xpos=random.nextInt(34);	//we've to generate next cheese's co-ordinates
            ypos=random.nextInt(23);
        }
        
        enemy.paintIcon(this, g, enemyXPos.get(xpos), enemyYPos.get(ypos));
		
		obstacle = new ImageIcon("F:\\Projects\\SnakeGame\\src\\Main\\obstacle.png");
		obstacle.paintIcon(this, g, 850, 75);
		//System.out.println("HELLO");
		if(score%5==0&&flgLevel)
		{
			System.out.println("HELLO if");
			level++;
			flgLevel = false;
			obstacleXList.clear();
			obstacleYList.clear();
			
			for(int i=0;i<level*2;i++)
			{
				int x = random.nextInt(34);
				int y = random.nextInt(23);
				if(x==xpos && y==ypos)
				{
					i--;
					continue;
				}
				obstacle.paintIcon(this, g, enemyXPos.get(x), enemyYPos.get(y));
				obstacleXList.add(x);
				obstacleYList.add(y);
			}
		}
		
		for(int i=0;i<level*2;i++)
			{
				System.out.println(obstacleXList.get(i)+","+obstacleYList.get(i));
			}
		
        
		//We've to identify when our snake dies... :(
        for(int b=1;b<length;b++)
        {
            if(snakeXLength[b]==snakeXLength[0]&&snakeYLength[0]==snakeYLength[b])
            {
                right=left=up=down=false;
                flg=true;
                g.setColor(Color.red);
                g.setFont(new Font("arial",Font.BOLD,50));
                g.drawString("GAME OVER!",300,300);
                
                
                g.setColor(Color.WHITE);
                g.setFont(new Font("arial",Font.BOLD,25));
				
				setLevel(0);
				
                g.drawString("PRESS KEY(OR SPACE) TO RESTART",260,400);
            }
        }
		
		for(int i=0;i<obstacleXList.size();i++)
		{
			if(snakeXLength[0] == obstacleXList.get(i) && snakeYLength[0] == obstacleYList.get(i))
			{
					right=left=up=down=false;
					flg=true;
					g.setColor(Color.red);
					g.setFont(new Font("arial",Font.BOLD,50));
					g.drawString("GAME OVER!",300,300);
					
					
					g.setColor(Color.WHITE);
					g.setFont(new Font("arial",Font.BOLD,25));
					
					setLevel(0);
					
					g.drawString("PRESS KEY(OR SPACE) TO RESTART",260,400);
			}
		}
		g.dispose();			//dispose the graphics
    }
	
	@Override
	public void keyTyped(KeyEvent e)  {}

    @Override
    public void keyPressed(KeyEvent e) 
    {
        try
        {
            if(e.getKeyCode()==KeyEvent.VK_SPACE)
            {
			// if one presses spacebar, we've to restart game
                moves=score=0;
                length=3;
                repaint();
            }
            
            if(!left&&!right&&!up&&!down&&flg)
            {
                moves=score=0;
                length=3;
                repaint();
            }
            
            //else				yes u read it right! else is comment out
            {
            
                if(e.getKeyCode()== KeyEvent.VK_RIGHT)
                {
					//if right (►) arrow key is pressed, move snake up
                    moves++;
                    right=true;		//since we've to move right

                    if(!left)
                    {
                        right=true;
                    }

                    else
                    {
                        left=true;
                        right=false;
                    }

                    up=down=false;
                }
				
				//same code snippet as right arrow key
                if(e.getKeyCode()== KeyEvent.VK_LEFT)
                {
                    moves++;
                    left=true;

                    if(!right)
                    {
                        left=true;
                    }

                    else
                    {
                        right=true;
                        left=false;
                    }

                    up=down=false;
                }

                if(e.getKeyCode()== KeyEvent.VK_UP)
                {
                    moves++;
                    up=true;

                    if(!down)
                    {
                        up=true;
                    }

                    else
                    {
                        down=true;
                        up=false;
                    }

                    left=right=false;
                }

                if(e.getKeyCode()== KeyEvent.VK_DOWN)
                {
                    moves++;
                    down=true;

                    if(!up)
                    {
                        down=true;
                    }

                    else
                    {
                        up=true;
                        down=false;
                    }

                    left=right=false;
                }
            }
        }
        catch(Exception exp){}
    }

    @Override
	//cheers we're reaching to the end!!!
	//Empty implementation
    public void keyReleased(KeyEvent e)	{} 


    @Override
	//this is tricky part, I'll explain in discussion below
    public void actionPerformed(ActionEvent e) 
    {
        try
        {
            timer.start();
            
            if(right)
            {
                for(int r=length-1;r>=0;r--)
                {
                    snakeYLength[r+1]=snakeYLength[r];
                }
                
                for(int r=length;r>=0;r--)
                {
                    if(r==0)
                    {
                        snakeXLength[r]+=25;
                    }
                    else
                    {
                        snakeXLength[r]=snakeXLength[r-1];
                    }
                    
                    if(snakeXLength[r]>850)
                    {
                        snakeXLength[r]=25;
                    }
                    
                    repaint();
                }
            }
            
            if(up)
            {
                for(int r=length-1;r>=0;r--)
                {
                    snakeXLength[r+1]=snakeXLength[r];
                }
                
                for(int r=length;r>=0;r--)
                {
                    if(r==0)
                    {
                        snakeYLength[r]-=25;
                    }
                    else
                    {
                        snakeYLength[r]=snakeYLength[r-1];
                    }
                    
                    if(snakeYLength[r]<75)
                    {
                        snakeYLength[r]=625;
                    }
                    
                    repaint();
                }
            }
            
            if(down)
            {
                for(int r=length-1;r>=0;r--)
                {
                    snakeXLength[r+1]=snakeXLength[r];
                }
                
                for(int r=length;r>=0;r--)
                {
                    if(r==0)
                    {
                        snakeYLength[r]+=25;
                    }
                    else
                    {
                        snakeYLength[r]=snakeYLength[r-1];
                    }
                    
                    if(snakeYLength[r]>625)
                    {
                        snakeYLength[r]=75;
                    }
                    
                    repaint();
                }
            }
            
            if(left)
            {
                for(int r=length-1;r>=0;r--)
                {
                    snakeYLength[r+1]=snakeYLength[r];
                }
                
                for(int r=length;r>=0;r--)
                {
                    if(r==0)
                    {
                        snakeXLength[r]-=25;
                    }
                    else
                    {
                        snakeXLength[r]=snakeXLength[r-1];
                    }
                    
                    if(snakeXLength[r]<25)
                    {
                        snakeXLength[r]=850;
                    }
                    
                    repaint();
                }
            }
			if(score%5==0)
				repaint();
        }
        catch(Exception exp)
        {
            
        }
    }
}

//YEEEAAAH END!!!
