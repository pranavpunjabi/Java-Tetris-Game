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