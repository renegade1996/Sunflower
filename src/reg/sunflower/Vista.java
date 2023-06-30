package reg.sunflower;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.Toolkit;

public class Vista extends Frame
{
	private static final long serialVersionUID = 1L;
	
	TextArea txtStimuli = new TextArea(7,50);
	
	Image you;
	Image btnSelectA, btnSelectB, btnSelectC, btnNext;
	Image btnReplaceA, btnReplaceB, btnReplaceC;
	Image blurrL1, blurrL2, blurrL3;
	Image spoons;
	
	String optionA   = "Wake up", optionB = "Wake up", optionC = "Wake up";
	String newOption = "...";
	
	Toolkit herramienta;
	
	public Vista()
	{
		setTitle("Sunflower");
		setLayout(new FlowLayout(FlowLayout.CENTER,380,20));
		setSize(1200,760);
		setBackground(Color.black);
		setResizable(false);
		setLocationRelativeTo(null);
		
		txtStimuli.setEditable(false);
		txtStimuli.setText("You are sleeping.\n\n\tA) "+optionA+"\n\tB) "+optionB+"\n\tC) "+optionC);
		add(txtStimuli);
		
		herramienta = getToolkit();
		you 		= herramienta.getImage("images\\asleep.png");
		
		btnSelectA = herramienta.getImage("images\\selectA.png");
		btnSelectB = herramienta.getImage("images\\selectB.png");
		btnSelectC = herramienta.getImage("images\\selectC.png");
		
		btnReplaceA = herramienta.getImage("images\\replaceA.png");
		btnReplaceB = herramienta.getImage("images\\replaceB.png");
		btnReplaceC = herramienta.getImage("images\\replaceC.png");
		
		btnNext = herramienta.getImage("images\\next.png");
		spoons  = herramienta.getImage("images\\spoons5.png");
		
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
		
		g.drawImage(spoons, 140, 557, this);
		
		g.drawImage(btnReplaceA, 990, 530, this);
		g.drawImage(btnReplaceB, 990, 560, this);
		g.drawImage(btnReplaceC, 990, 590, this);
		
		Font calibriS = new Font("Calibri", Font.BOLD, 12);
		Font calibriB = new Font("Calibri", Font.BOLD, 18);
		
		g.setColor(Color.white);
		g.setFont(calibriS);
		g.drawString("N E W  O P T I O N  A V A I L A B L E", 800, 470);
		
		g.drawRect(800, 480, 300, 80);
		
		g.setFont(calibriB);
		g.setColor(Color.yellow);
		g.drawString(newOption, 830, 525);
	}
}
