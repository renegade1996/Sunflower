package reg.sunflower;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;

public class Vista extends Frame
{
	private static final long serialVersionUID = 1L;
	
	TextField txtEvent = new TextField(85);
	TextArea txtOptions = new TextArea(5,50);
	
	Image btnReplaceA, btnReplaceB, btnReplaceC;
	Image btnSelectA, btnSelectB, btnSelectC;
	Image btnNext, btnExit, btnReshuffle;
	Image spoons;
	Image you;
	
	File errorSoundFile = new File("sounds\\error.wav");
	AudioFileFormat aff;
	AudioInputStream ais;
	
	String allOptions[]   = {"...", "Wake up", "Interact", "Ignore", "Drink water", "Leave", "Wear headphones", "Take a painkiller", "Stim"};
	String newOptionTitle = "N E W  O P T I O N  A V A I L A B L E";
	
	//initial values
	String optionA      = allOptions[1], optionB = allOptions[1], optionC = allOptions[1];
	String newOption    = allOptions[0];
	
	Boolean gameOver = false;
	
	String message1 = "";
	String message2 = "";
	
	Toolkit herramienta;
	
	public Vista()
	{
		setTitle("Sunflower");
		setLayout(new FlowLayout(FlowLayout.CENTER,380,20));
		setSize(1200,760);
		setBackground(Color.black);
		setResizable(false);
		setLocationRelativeTo(null);
		
		txtEvent.setEditable(false);
		txtEvent.setBackground(Color.yellow);
		txtEvent.setText("You are sleeping.");
		add(txtEvent);
		
		txtOptions.setEditable(false);
		txtOptions.setText("A) "+optionA+"\nB) "+optionB+"\nC) "+optionC);
		add(txtOptions);
		
		herramienta = getToolkit();
		you 		= herramienta.getImage("images\\asleep.png");
		
		btnSelectA = herramienta.getImage("images\\selectA.png");
		btnSelectB = herramienta.getImage("images\\selectB.png");
		btnSelectC = herramienta.getImage("images\\selectC.png");
		
		btnReplaceA = herramienta.getImage("images\\replaceA.png");
		btnReplaceB = herramienta.getImage("images\\replaceB.png");
		btnReplaceC = herramienta.getImage("images\\replaceC.png");
		
		btnNext  = herramienta.getImage("images\\next.png");
		spoons   = herramienta.getImage("images\\spoons5.png");
		
		//eliminar cuando hagas menú
		setVisible(true);
	}
	public void paint(Graphics g)
	{
		g.drawImage(you, 400, 250, this);
		
		g.drawImage(btnSelectA, 320, 200, this);
		g.drawImage(btnSelectB, 520, 200, this);
		g.drawImage(btnSelectC, 720, 200, this);
		
		g.drawImage(btnNext, 535, 600, this);
		g.drawImage(btnExit, 530, 550, this);
		
		g.drawImage(spoons, 140, 557, this);
		
		g.drawImage(btnReplaceA, 990, 530, this);
		g.drawImage(btnReplaceB, 990, 560, this);
		g.drawImage(btnReplaceC, 990, 590, this);
		
		g.drawImage(btnReshuffle, 800, 570, this);
		
		Font calibriS = new Font("Calibri", Font.BOLD, 12);
		Font calibriB = new Font("Calibri", Font.BOLD, 18);
		Font calibriG = new Font("Calibri", Font.BOLD, 25);

		g.setFont(calibriS);
		g.setColor(Color.white);
		g.drawString(newOptionTitle, 800, 470);
		
		if(!gameOver)
		{
			g.drawRect(800, 480, 300, 80);
		}
		
		g.setFont(calibriB);
		g.setColor(Color.yellow);
		g.drawString(newOption, 830, 525);
		
		g.setFont(calibriG);
		
		g.setColor(Color.white);
		g.drawString(message1, 190, 250);
		g.setColor(Color.red);
		g.drawString(message2, 540, 320);
	}
}
