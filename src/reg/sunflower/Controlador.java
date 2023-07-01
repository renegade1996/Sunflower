package reg.sunflower;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Controlador implements WindowListener, MouseListener
{
	Modelo modelo;
	Vista  vista;

	//Variables
	Boolean op1 = false, op2 = false, op3 = false;

	String  opSelected = "";
	Random  random	   = new Random();

	int counterStoryLine = 0, counterCoffee	= 0, counterSpoons = 5;

	public Controlador(Modelo m, Vista v)
	{
		this.modelo = m;
		this.vista  = v;

		//listeners
		v.addWindowListener(this);
		v.addMouseListener(this);
	}
	@Override
	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();

		if(vista.isActive()&&!(vista.gameOver))
		{
			//Click on option A
			if((x>=320 && x<=474)&&(y>=201 && y<=250))
			{
				vista.btnSelectA = vista.herramienta.getImage("images\\A_Clicked.png");
				vista.btnSelectB = vista.herramienta.getImage("images\\selectB.png");
				vista.btnSelectC = vista.herramienta.getImage("images\\selectC.png");

				op1 = true; op2  = false; op3 = false;
			}
			//Click on option b
			else if((x>=520 && x<=674)&&(y>=201 && y<=250))
			{
				vista.btnSelectA = vista.herramienta.getImage("images\\selectA.png");
				vista.btnSelectB = vista.herramienta.getImage("images\\B_Clicked.png");
				vista.btnSelectC = vista.herramienta.getImage("images\\selectC.png");

				op1 = false; op2 = true; op3 = false;
			}
			//Click on option c
			else if((x>=720 && x<=874)&&(y>=201 && y<=250))
			{
				vista.btnSelectA = vista.herramienta.getImage("images\\selectA.png");
				vista.btnSelectB = vista.herramienta.getImage("images\\selectB.png");
				vista.btnSelectC = vista.herramienta.getImage("images\\C_Clicked.png");

				op1 = false; op2 = false; op3 = true;
			}

			//click on replace A
			if((x>=990 && x<=1088)&&(y>=530 && y<=557))
			{
				vista.btnReplaceA = vista.herramienta.getImage("images\\replaceA_clicked.png");
				vista.btnReplaceB = vista.herramienta.getImage("images\\replaceB.png");
				vista.btnReplaceC = vista.herramienta.getImage("images\\replaceC.png");

				vista.optionA 	  = vista.newOption;
				vista.txtOptions.setText("A) "+vista.optionA+"\nB) "+vista.optionB+"\nC) "+vista.optionC);
			}
			//click on replace B
			else if((x>=990 && x<=1088)&&(y>=565 && y<=585))
			{
				vista.btnReplaceA = vista.herramienta.getImage("images\\replaceA.png");
				vista.btnReplaceB = vista.herramienta.getImage("images\\replaceB_clicked.png");
				vista.btnReplaceC = vista.herramienta.getImage("images\\replaceC.png");

				vista.optionB 	  = vista.newOption;
				vista.txtOptions.setText("A) "+vista.optionA+"\nB) "+vista.optionB+"\nC) "+vista.optionC);
			}
			//click on replace C
			else if((x>=990 && x<=1088)&&(y>=595 && y<=619))
			{
				vista.btnReplaceA = vista.herramienta.getImage("images\\replaceA.png");
				vista.btnReplaceB = vista.herramienta.getImage("images\\replaceB.png");
				vista.btnReplaceC = vista.herramienta.getImage("images\\replaceC_clicked.png");

				vista.optionC 	  = vista.newOption;
				vista.txtOptions.setText("A) "+vista.optionA+"\nB) "+vista.optionB+"\nC) "+vista.optionC);
			}
			else
			{
				vista.btnReplaceA = vista.herramienta.getImage("images\\replaceA.png");
				vista.btnReplaceB = vista.herramienta.getImage("images\\replaceB.png");
				vista.btnReplaceC = vista.herramienta.getImage("images\\replaceC.png");
			}

			//click on re-shuffle
			if((counterStoryLine>0)&&(x>=801 && x<=928)&&(y>=572 && y<=600))
			{
				counterSpoons--;
				vista.newOption = vista.allOptions[random.nextInt(7)+2];
				updateSpoons();
			}

			//Click on Next 
			if((x>=536 && x<=659)&&(y>=602 && y<=649))
			{
				if(counterStoryLine > 0)
				{
					vista.btnReshuffle = vista.herramienta.getImage("images\\reshuffle.png");
				}
				else
				{
					vista.btnReshuffle = vista.herramienta.getImage("");
				}

				//if any option selected
				if(op1 || op2 || op3)
				{
					vista.btnNext = vista.herramienta.getImage("images\\next_Hover.png");	

					if(op1)
					{
						opSelected = vista.optionA;
					}
					else if(op2)
					{
						opSelected = vista.optionB;
					}
					else if(op3)
					{
						opSelected = vista.optionC;
					}

					//cases per option selected
					switch (opSelected)
					{
					case "...":
						if(counterStoryLine == 0)
						{
							if(vista.optionA.equals(vista.allOptions[0]) && vista.optionB.equals(vista.allOptions[0]) && vista.optionC.equals(vista.allOptions[0]))
							{
								gameOver();
								vista.message1 = "It looks like you decided to never wake up again...";
							}
							else
							{
								vista.txtEvent.setText("You continue sleeping. Perhaps it's better this way.");
							}
						}
						else if(counterStoryLine == 1)
						{
							vista.txtEvent.setText("You do nothing. Time goes by. You feel desperately lonely. It takes 1 spoon.");
							counterSpoons--;
						}
						else if(counterStoryLine == 2)
						{
							if(counterCoffee > 1)
							{
								vista.txtEvent.setText("You do nothing. Your heart is still racing. You're so lonely. It takes 1 spoon.");
								counterSpoons--;
							}
							else
							{
								vista.txtEvent.setText("You do nothing. Time goes by. You feel desperately bored and lonely. It takes 1 spoon.");
								counterSpoons--;
							}
						}
						else if(counterStoryLine == 3)
						{
							if(counterCoffee > 1)
							{
								vista.txtEvent.setText("You do nothing. Your heart is still racing. It takes 1 spoon. There's a lot of traffic and it's stressful. Move or something?");
								counterSpoons--;
							}
							else
							{
								vista.txtEvent.setText("You do nothing. There's a lot of traffic and it's stressful. You're paralised. It takes 1 spoon. Move or something?");
								counterSpoons--;
							}
						}
						else if(counterStoryLine == 4)
						{
							if(counterCoffee > 1)
							{
								vista.txtEvent.setText("You do nothing. Your heart is still racing. It takes 1 spoon. What now?");
								counterSpoons--;
							}
							else
							{
								vista.txtEvent.setText("You do nothing. You wonder why you went outside. It takes 1 spoon. What now?");
								counterSpoons--;
							}
						}
						break;

					case "Wake up":
						vista.you = vista.herramienta.getImage("images\\awake.png");

						if(counterStoryLine == 0)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You wake up. It's too warm. You feel dizzy. It takes away 1 spoon.");
							counterSpoons--;
						}
						else if(counterStoryLine == 1)
						{
							counterStoryLine++;
							counterCoffee++;

							vista.txtEvent.setText("You wash your face and get food and coffee. It gives you 1 spoon. You see your cat.");
							counterSpoons++;
						}
						else if(counterStoryLine == 2)
						{
							counterStoryLine++;
							counterCoffee++;

							if(counterCoffee == 1)
							{
								vista.txtEvent.setText("You wash your face and get food and coffee. It gives you 1 spoon. You go outside.");
								counterSpoons++;
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("You have another coffee. Your heart is racing. If you get another coffee I'm quitting. You go outside.");
							}
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;
							counterCoffee++;

							if(counterCoffee == 1)
							{
								vista.txtEvent.setText("You go to a coffee shop for some caffeine to wake up. It's very noisy.");
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("You go to a coffee shop for more caffeine. Your heart is racing. The place is very noisy. DON'T get more caffeine.");
							}
							else if(counterCoffee == 3)
							{
								gameOver();
								vista.message1 = "I warned you???????";
							}
						}
						else if(counterStoryLine == 4)
						{
							counterStoryLine++;
							counterCoffee++;

							if(counterCoffee == 1)
							{
								vista.txtEvent.setText("You're exhausted. It takes 1 spoon. You head to a coffee shop for some caffeine and relaxation.");
								counterSpoons--;
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("You're exhausted. You buy some more caffeine. Your heart is racing. The place is very noisy. DON'T get more caffeine.");
							}
							else if(counterCoffee == 3)
							{
								gameOver();
								vista.message1 = "I warned you???????";
							}
						}
						else if(counterStoryLine == 5)
						{
							counterStoryLine++;
							counterCoffee++;

							if(counterCoffee == 1)
							{
								vista.txtEvent.setText("You're overstimulated. You get a coffee to go (that was a bad idea) and head home.");
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("You're overstimulated. You buy some more caffeine (Why tho?) and head home. DON'T get more caffeine.");
							}
							else if(counterCoffee == 3)
							{
								gameOver();
								vista.message1 = "I warned you???????";
							}
						}
						break;

					case "Interact":
						if(counterStoryLine == 1)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You decide to interact with the world. You get up. It takes away 1 spoon. You see your cat.");
							vista.spoons = vista.herramienta.getImage("images\\spoons4.png");
						}
						else if(counterStoryLine == 2)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You pet your cat. It purrs. It gives you 1 spoon. You go outside.");
							vista.spoons = vista.herramienta.getImage("images\\spoons5.png");
						}
					case "Ignore":
					case "Drink water":
					case "Leave":
					case "Wear headphones":
					case "Take a painkiller":
					}

					updateSpoons();	
				}
				//else if no options selected
				else
				{
					try
					{
						vista.aff = AudioSystem.getAudioFileFormat(vista.errorSoundFile);
						vista.ais = AudioSystem.getAudioInputStream(vista.errorSoundFile);

						AudioFormat af = vista.aff.getFormat();
						DataLine.Info info = new DataLine.Info(Clip.class, vista.ais.getFormat(), ((int) vista.ais.getFrameLength() * af.getFrameSize()));
						Clip ol = (Clip) AudioSystem.getLine(info);
						ol.open(vista.ais);
						ol.loop(1); //()number of reproductions or loop.continously
						Thread.sleep(890);
						ol.close();
					}
					catch(UnsupportedAudioFileException ee)
					{
						System.out.println(ee.getMessage());
					}
					catch(IOException ea)
					{
						System.out.println(ea.getMessage());
					}
					catch(LineUnavailableException LUE)
					{
						System.out.println(LUE.getMessage());
					}
					catch (InterruptedException ie)
					{
						ie.printStackTrace();
					}
				}
				//new random option after updating story line
				if((counterStoryLine > 0) && (op1 || op2 || op3))
				{
					//reset selected
					op1 = false; op2 = false; op3 = false;

					if(!vista.gameOver)
					{
						vista.newOption = vista.allOptions[random.nextInt(7)+2];

						vista.btnSelectA = vista.herramienta.getImage("images\\selectA.png");
						vista.btnSelectB = vista.herramienta.getImage("images\\selectB.png");
						vista.btnSelectC = vista.herramienta.getImage("images\\selectC.png");
					}
				}
			}
			else
			{
				if(!vista.gameOver)
				{
					vista.btnNext = vista.herramienta.getImage("images\\next.png");
				}
			}
		}
		else if(vista.isActive() && vista.gameOver)
		{	
			if((x>=530 && x<=678)&&(y>=551 && y<=615))
			{
				System.exit(0);
			}
		}
		vista.repaint();
	}

	//updates spoons left
	private void updateSpoons()
	{
		if(!vista.gameOver)
		{
			if(counterSpoons >= 5)
			{
				vista.spoons = vista.herramienta.getImage("Images\\spoons5.png");
			}
			else if(counterSpoons == 4)
			{
				vista.spoons = vista.herramienta.getImage("Images\\spoons4.png");
			}
			else if(counterSpoons == 3)
			{
				vista.spoons = vista.herramienta.getImage("Images\\spoons3.png");
			}
			else if(counterSpoons == 2)
			{
				vista.spoons = vista.herramienta.getImage("Images\\spoons2.png");
			}
			else if(counterSpoons == 1)
			{
				vista.spoons = vista.herramienta.getImage("Images\\spoons1.png");
			}
			else if(counterSpoons < 1)
			{
				//new dodge game.
				//if not wake up option available:
				//counterCoffee = 0;
				gameOver();
				vista.message1 = "You are physically incapable of doing anything about this anymore.";
			}
		}
	}

	//wipes out window and adds game-over elements 
	private void gameOver()
	{
		vista.gameOver = true;

		vista.btnSelectA  = vista.herramienta.getImage("");
		vista.btnSelectB  = vista.herramienta.getImage("");
		vista.btnSelectC  = vista.herramienta.getImage("");

		vista.btnReplaceA = vista.herramienta.getImage("");
		vista.btnReplaceB = vista.herramienta.getImage("");
		vista.btnReplaceC = vista.herramienta.getImage("");

		vista.btnNext 	   = vista.herramienta.getImage("");
		vista.spoons	   = vista.herramienta.getImage("");
		vista.btnReshuffle = vista.herramienta.getImage("");

		vista.newOptionTitle = "";
		vista.newOption		 = "";

		vista.remove(vista.txtEvent);
		vista.remove(vista.txtOptions);

		vista.message2 = "GAME 	OVER";
		vista.btnExit  = vista.herramienta.getImage("images\\exit.png");

		vista.repaint();
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
