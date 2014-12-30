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