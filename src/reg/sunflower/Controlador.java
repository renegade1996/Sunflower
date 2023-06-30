package reg.sunflower;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Timer;

public class Controlador implements WindowListener, MouseListener
{
	Modelo modelo;
	Vista vista;

	//variables opciones seleccionadas
	Boolean op1 = false, op2 = false, op3 = false;
	Timer hoverTimer;

	public Controlador(Modelo m, Vista v)
	{
		this.modelo = m;
		this.vista = v;

		//listeners
		v.addWindowListener(this);
		v.addMouseListener(this);
	}
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(vista.isActive())
		{
			int x = e.getX();
			int y = e.getY();

			//Click en opción a
			if((x>=320 && x<=474)&&(y>=201 && y<=250))
			{
				vista.btnSelectA = vista.herramienta.getImage("images\\A_Clicked.png");
				vista.btnSelectB = vista.herramienta.getImage("images\\selectB.png");
				vista.btnSelectC = vista.herramienta.getImage("images\\selectC.png");
				op1 = true;
				op2 = false;
				op3 = false;

				if(vista.optionA.equals("Wake up"))
				{
					vista.you = vista.herramienta.getImage("images\\awake.png");
				}
				//else if todas las demás opciones y sus consecuencias (spoons1/2/3/4/5)
				vista.repaint();
			}

			//Click en opción b
			if((x>=520 && x<=674)&&(y>=201 && y<=250))
			{
				vista.btnSelectB = vista.herramienta.getImage("images\\B_Clicked.png");
				vista.btnSelectA = vista.herramienta.getImage("images\\selectA.png");
				vista.btnSelectC = vista.herramienta.getImage("images\\selectC.png");
				op2 = true;
				op1 = false;
				op3 = false;

				if(vista.optionB.equals("Wake up"))
				{
					vista.you = vista.herramienta.getImage("images\\awake.png");
				}
				//else if todas las demás opciones y sus consecuencias (spoons1/2/3/4/5)
				vista.repaint();
			}

			//Click en opción c
			if((x>=720 && x<=874)&&(y>=201 && y<=250))
			{
				vista.btnSelectC = vista.herramienta.getImage("images\\C_Clicked.png");
				vista.btnSelectA = vista.herramienta.getImage("images\\selectA.png");
				vista.btnSelectB = vista.herramienta.getImage("images\\selectB.png");
				op3 = true;
				op1 = false;
				op2 = false;

				if(vista.optionC.equals("Wake up"))
				{
					vista.you = vista.herramienta.getImage("images\\awake.png");
				}
				//else if todas las demás opciones y sus consecuencias (spoons1/2/3/4/5)
				vista.repaint();
			}

			//Click en btn Next 
			if((x>=536 && x<=659)&&(y>=602 && y<=649))
			{
				op1 = false;
				op2 = false;
				op3 = false;

				vista.btnSelectA = vista.herramienta.getImage("images\\selectA.png");
				vista.btnSelectB = vista.herramienta.getImage("images\\selectB.png");
				vista.btnSelectC = vista.herramienta.getImage("images\\selectC.png");
				
				vista.btnNext = vista.herramienta.getImage("images\\next_Hover.png");
			
				vista.repaint();
			}
			else
			{
				//resetear cuando clic en cualquier otra cosa
				vista.btnNext = vista.herramienta.getImage("images\\next.png");
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}
	@Override
	public void windowOpened(WindowEvent e){}
	@Override
	public void windowClosing(WindowEvent e)
	{
		if(vista.isActive())
		{
			System.exit(0);
		}
	}
	@Override
	public void windowClosed(WindowEvent e){}
	@Override
	public void windowIconified(WindowEvent e){}
	@Override
	public void windowDeiconified(WindowEvent e){}
	@Override
	public void windowActivated(WindowEvent e){}
	@Override
	public void windowDeactivated(WindowEvent e){}
}
