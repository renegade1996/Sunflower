package reg.sunflower;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;


public class Menu  extends Frame
{
	private static final long serialVersionUID = 1L;
	
	Image play, help;
	Toolkit herramienta;
	
	Dialog dlgHelp   = new Dialog(this, "", true);
	Label  lblHelp   = new Label("xd you're on your own.");
	Button btnBack	 = new Button("Back to Menu");

	public Menu()
	{
		setTitle("Sunflower Brain");
		setSize(450,400);
		setLayout(null);
		setBackground(Color.BLACK);
		setResizable(false);
		this.setLocationRelativeTo(null);

		herramienta = getToolkit();

		play = herramienta.getImage("images\\play.png");
		help = herramienta.getImage("images\\help.png");
		
		setVisible(true);
	}
	//Método para "dibujar" las imágenes
	public void paint(Graphics g)
	{
		g.drawImage(help, 145, 100, this);
		g.drawImage(play, 145, 250, this);
	}
	
}
