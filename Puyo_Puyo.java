import java.util.Random;
import javax.swing.*;	//JFrame,JComponent,Timer
import java.awt.*;		//Dimension,Image,Toolkit,Graphics,Container,Color,Graphics2D
						//RenderingHints,GradientPaint,Font,Rectangle,AlphaComposite
import java.awt.event.*;//ActionEvent,ActionListener,KeyAdapter,KeyEvent
import java.applet.*;	//Applet,AudioClip
import java.net.*;		//URL,MalformedURLException

public class Puyo_Puyo extends JFrame
{
	GamePane gp;			//GamePane is subclass of JComponent on which the puyos are moved		
	int width,height;
	int rows,cols;		
	int puyo_len;			//holds the length of the each puyo(because it is a square piece)
	Dimension screenSize;	//holds the dimension of the screen interms of resolution
	public Puyo_Puyo() 
	{
		super("Puyo_Puyo");
		cols=6;			// Can be set to any value, game rows*cols depends on this value	
		rows=cols*2;
		screenSize= Toolkit.getDefaultToolkit().getScreenSize();
		width=screenSize.width;
		height=screenSize.height;
		//Width and Height holds the screen resolution.
		//Different computers may have set with different resolutions.
		//If the Window size is static then it is differ to see from one computer to another.
		//To place the window at the middle of the screen at any resolution and 
		//to adjust the window size and puyo size some calculations are taken here.
		//Normally resolution(for pc) is windth*height format with 8:6 ratio.
		//For puyo game(rows=12,colums=6) window take 1:2(wd*ht) ratio in screen resolution.
		//So in 8 parts of width(of screen) 2 parts is assigned to window widht and
		//and in 6 parts of height(of screen) 4 parts is assigned to window height to place
		//window at the middle of the screen.
		//for puyo(cell size) width and height are same
		//puyo size for 12 rows 6 colums puyo game
		puyo_len=(width/8)*2/cols;			//or (height/6)*4/12 
		gp=new GamePane(puyo_len,rows,cols);
		Container c=getContentPane();
		c.add(gp);
		setResizable(false);
		//placing window(added score board size) at the middle of the screen
		//3 puyos width is added to the window for score board display
		setBounds ((width/8)*3-puyo_len*3/2,(height/6)*1-puyo_len,(width/8)*2+puyo_len*3+6,(height/6)*4+25+puyo_len);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String args[]) {
		System.out.println("Starting Puyo_Puyo...");
		JFrame.setDefaultLookAndFeelDecorated(true); //to set the look and feel for frame as defined int Java
		new Puyo_Puyo();
	}
}

class GamePane extends JComponent implements ActionListener
{
	static int rows,cols;	
	static int scr[][];			//scr or(screen) array holds the information about puyos to display
	Node tetris;				//Formation of Tetris is checked using this object
	Timer timer,timer1,timer2,anim_timer;	//different timers used for animation of puyos
	Image img[]=new Image[4];	//holds 4 puyo images
	Image fpipe,bpipe;			//holds the front and back part of pipe image
								//2 images of pipe used to get the experience of puyos coming out from pipe
	String files[]={"intro.mid","enter.wav","sound735.wav","blip.wav","sound65.wav","sound136.wav","tada.wav","sound713.wav"};
	AudioClip clips[]=new AudioClip[8];//Array of audio clips to load the sound files
	Toolkit tk;			//used to load the images		
	Random rand;		//this object used to generate puyos randomly in color.
	int rot;			//used for the rotation of the puyos
	int len;			//length of the puyo ie. width and height
	boolean reached;	//generated puyo reached the bottom or in movement
	int count;			//count of puyos when formed tetris to delete
	boolean started;	//Game is started or not
	boolean gameOver,paused; 
	int a,b;			//The two puyos generate to be next are stored in a and b
	int level,score,pieces,removed_puyos;//pieces is number of joint puyos(single piece) generated
					//number of removed puyos by forming tetris
	int minscore;	
	int anim;		//to build the pixel by pixel animation (movement of generated puyos)
	float alpha,alpha1;
	boolean levelflag;

	public GamePane(int l,int r,int c)
	{
		len=l;		//length of puyos is set by the Puyo_Puyo class where it is calculated and sent here
		rows=r+1;	//number of rows is taken 12+1 for the easyness of generating puyos from pipe.
					//extra one row is occupied by pipe at the top. Only 12 rows are used for puyos.
		cols=c;	    //6 columns
		init();		//initializing game data
		//puyo images are selected depending on the length of puyos
		//length of puyo is calculated depending on the resolution of screen
		//In this game i used two types of puyos for 
		//(i)800*600 and below resolutions
		//(ii)1024*768 and above resolutions
		//So images are loaded ofter length of puyos is calculated
		loadImages();
		loadSounds();		
		generatePuyos();				//to start the generating puyos
		clips[0].loop();
		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					//if game is not started then start the game by starting timer when enter is pressed
					//ig game is over then initialize the variables and start generating puyos also
					if(!started)		
					{
						clips[0].stop();
						clips[1].play();
						setDelays();
						timer.start();
						started=true;
					}
					if(gameOver)		
					{
						init();
						generatePuyos();
						clips[0].loop();
						started=false;
					}
					if(paused)
					{
						init();
						generatePuyos();
						clips[0].loop();
						started=false;
					}
					repaint();
				}
				else
				if(e.getKeyCode()==KeyEvent.VK_LEFT && !reached && !paused)//move puyos left if each puyo not reached to ground
				{
					clips[3].play();
					moveLeft();
				}
				else
				if(e.getKeyCode()==KeyEvent.VK_RIGHT && !reached && !paused)
				{
					clips[3].play();
					moveRight();
				}
				else
				if(e.getKeyCode()==KeyEvent.VK_UP && !paused )
				{
					clips[3].play();
					if(!reached)
					rotate();
					if(!started && level<19)	//Before strting the game 
						level++;
				}
				else
				if(e.getKeyCode()==KeyEvent.VK_DOWN && !paused)
				{
					clips[3].play();
					moveDown();
					if(!started && level>0) 	//Before strting the game 
					level--;
				}
				else
				if(e.getKeyCode()==KeyEvent.VK_P && started && !gameOver)
				{
					//if game is already paused then resume it, other wise pause the game
					if(paused)
					{
						clips[1].play();
						paused=false;
						alpha1=0.0f;
						timer.start();		//game is resumed
					}
					else
					{
						clips[2].play();
						timer.stop();
						paused=true;		//game is paused
					}
				}
				else
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
				{
					clips[2].play();
					//if game is already paused then exit the game, other wise pause the game
					if( started && !gameOver)
					{
						if(paused)
						System.exit(0);		//exit the game
						else
						{
							timer.stop();
							paused=true;		//game is paused
						}
					}
					else
					System.exit(0);
				}
			}
		});
		setFocusable(true);			//to set the keyboard focus on this game pane
	}

	public void init()//all variables declared above are initialized here
	{
		scr=new int[rows][cols];
		rot=1;
		reached=true;
		count=0;
		started=false;
		gameOver=false;
		paused=false;
		a=0;
		b=0;
		level=0;
		score=0;
		pieces=-1;
		removed_puyos=0;
		minscore=50;
		anim=0;
		alpha=0.0f;
		alpha1=0.0f;
		levelflag=true;
		tk= Toolkit.getDefaultToolkit();
		rand=new Random();
		timer=new Timer(1000,this);	//generates action event for each 1075 milli seconds when timer is started
		timer.setInitialDelay(0);	//generates first event ofter 0 ms when timer starts
		timer1=new Timer(1000,this);
		timer2=new Timer(500,this);
		anim_timer=new Timer(50,this);
		anim_timer.start();			//starting the timer
	}
	public void loadImages()//loading images into the image array and pipe objects
	{
		String s="";
		if(len>=42)
		s="_";
		for(int i=0;i<img.length;i++)
		img[i]=tk.getImage("images\\puyo_"+s+(i+1)+".png");
		fpipe=tk.getImage("images\\pipe"+s+"1.png");
		bpipe=tk.getImage("images\\pipe"+s+".png");
	}
	public void loadSounds()//loading souds into the AudioClip array
	{
		try{
			for(int i=0;i<clips.length;i++)	//Loading all the sound clips
			clips[i]=Applet.newAudioClip(new URL("file:"+System.getProperty("user.dir")+"\\sounds\\"+files[i]));
			
		}
		catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
    }