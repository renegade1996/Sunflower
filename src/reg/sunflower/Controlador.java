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

	int counterStoryLine = 0;
	int counterCoffee	 = 0;
	int counterSpoons 	 = 5;
	int counterWater 	 = 0;
	int counterMusic 	 = 0;

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

			//click on shuffle
			if((counterStoryLine>0)&&(x>=801 && x<=928)&&(y>=572 && y<=600))
			{
				counterSpoons--;
				vista.newOption = vista.allOptions[random.nextInt(4)+2];
				updateSpoons();
			}

			//Click on Next 
			if((x>=536 && x<=659)&&(y>=602 && y<=649))
			{
				if(counterStoryLine > 0)
				{
					vista.btnReshuffle = vista.herramienta.getImage("images\\reshuffle.png");
					//sonidito ganancia
				}
				else
				{
					vista.btnReshuffle = vista.herramienta.getImage("");
				}

				//if any option selected assign from options box
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

					//cases per option selected depending on story line
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
							vista.txtEvent.setText("You do nothing. Time goes by. You feel desperately lonely (-2 spoons).");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 2)
						{
							vista.txtEvent.setText("You do nothing. Time goes by. You feel desperately bored and lonely (-2 spoons).");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 3 || counterStoryLine == 4 || counterStoryLine == 5)
						{
							vista.txtEvent.setText("You do nothing. You seem paralised. The public sees (-2 spoons). Do something?");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 6)
						{
							counterSpoons = 1;

							vista.txtEvent.setText("You do nothing. You're suddenly extremely anxious and disoriented. You NEED to be home RIGHT NOW.");
							vista.txtOptions.setText("ATTENTION: YOU ARE AT YOUR LIMIT.\n\nMOVE UP AND DOWN TO DODGE ALL STIMULI AND MAKE YOUR WAY HOME.");
							//start game
						}
						break;

					case "Wake up":
						vista.you = vista.herramienta.getImage("images\\awake.png");

						if(counterStoryLine == 0)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You wake up. It's too warm. You feel dizzy (-1 spoon). You have committed to attend a social event today.");
							counterSpoons--;
						}
						else if(counterStoryLine == 1)
						{
							counterStoryLine++;
							counterCoffee++;

							vista.txtEvent.setText("You wash your face and get food and coffee. It wakes me up too (+1 spoon). You see your cat.");
							counterSpoons++;
						}
						else if(counterStoryLine == 2)
						{
							counterStoryLine++;

							vista.txtEvent.setText("You wake up your cat. Your cat loves you but is disappointed at you for not respecting their space (-2 spoons). You leave the house.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;
							counterCoffee++;

							if(counterCoffee == 1)
							{
								vista.txtEvent.setText("You get coffee to go so I can wake up too (+1 spoon). On the way to the social event we mentally rehearse your performance in every possible scenario. You get to the event.");
								counterSpoons++;
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("On the way to the social event you get more coffee to go. Your heart is racing and you can't even rehearse your social performance for the event (-2 spoons). This is your brain speaking: one more coffee and I quit.");
								counterSpoons-=2;
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
								vista.txtEvent.setText("You're exhausted. You leave the social event early. People seemed annoyed at you (-2 spoons). You head to the park to relax and get some coffee to wake me up.");
								counterSpoons-=2;
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("You're exhausted. You leave the social event early. People seemed annoyed (-2 spoons). You head to the park to relax and get some more coffee. This is your brain speaking: one more cup and I quit.");
								counterSpoons-=2;
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
								vista.txtEvent.setText("The park is crowded. You're overstimulated and extremely tired. You get a coffee to go to wake me up and head home. Has the way back home always been this long and blurry?");
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("The park is crowded. You're overstimulated and extremely tired. You buy some more caffeine and head home. This is your brain speaking: one more cup and I quit. Has the way back home always been this long and blurry?");
							}
							else if(counterCoffee == 3)
							{
								gameOver();
								vista.message1 = "I warned you???????";
							}
						}
						else if(counterStoryLine == 6)
						{
							counterCoffee++;
							counterSpoons = 1;

							if(counterCoffee == 1)
							{
								vista.txtEvent.setText("You stop for coffee to wake me up. You're overstimulated, anxious and disoriented. The barista calls your name 4 times before you hear it. GO HOME RIGHT NOW.");
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("You stop for more coffee. You're overstimulated, anxious and disoriented. The barista calls your name 4 times before you hear it. GO HOME RIGHT NOW.");
							}
							else if(counterCoffee >= 3)
							{
								gameOver();
								vista.message1 = "I warned you???????";
							}

							//mini game
							//if(!vista.gameOver)
							//{
								//vista.txtOptions.setText("ATTENTION: YOU ARE AT YOUR LIMIT.\n\nMOVE UP AND DOWN TO DODGE ALL STIMULI AND MAKE YOUR WAY HOME.");
								//start game
							//}
						}
						break;

					case "Interact":
						if(counterStoryLine == 1)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You decide to interact with the world. You get up (-1 spoon). You see your cat.");
							counterSpoons--;
						}
						else if(counterStoryLine == 2)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You pet your cat. It purrs. It restores your spoons. You go outside.");
							counterSpoons = 5;
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;
							vista.txtEvent.setText("On the way to the social event you find an acquaintance and you interact with them. You were not ready for this. It dysregulates you (-3 spoons). You get to the social event.");
							counterSpoons-=3;
						}
						else if(counterStoryLine == 4)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You understand half of what the people at the event are saying. You try to interact but you make a fool of yourself. You feel like a social failure (-2 spoons). You head to the park afterwards to process and relax.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 5)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You try to make your senses interact with nature but it's crowded, warm and noisy (-1 spoon). You head home. Has the way back home always been this long and blurry?");
							counterSpoons--;
						}
						else if(counterStoryLine == 6)
						{
							counterSpoons = 1;
							
							vista.txtEvent.setText("Someone asks for directions on your way home. You didn't prepare for this. You make an effort to interact but words don't come out right. You're extremely anxious and disoriented. GO HOME RIGHT NOW.");
							//vista.txtOptions.setText("ATTENTION: YOU ARE AT YOUR LIMIT.\n\nMOVE UP AND DOWN TO DODGE ALL STIMULI AND MAKE YOUR WAY HOME.");
							//start game
						}
						break;

					case "Ignore":
						
						if(counterStoryLine == 1)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You can't ignore your commitments. You get up and see your cat.");
						}
						else if(counterStoryLine == 2)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You ignore your cat. Your cat probably hates you for that (-1 spoon). We don't deserve them. You go outside.");
							counterSpoons--;
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;
							vista.txtEvent.setText("On the way to the social event you find an acquaintance. You were not ready for this. You freeze and ignore them. It dysregulates you (-2 spoons). You get to the social event.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 4)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You struggle to keep up at the event. You give up and dissociate. You feel like a social failure (-2 spoons). You head to the park afterwards to process and relax.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 5)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You try to enjoy nature but you're dissociated. You ignore it (-1 spoon). You head home. Has the way back home always been this long and blurry?");
							counterSpoons--;
						}
						else if(counterStoryLine == 6)
						{
							counterSpoons = 1;

							vista.txtEvent.setText("Someone asks for directions on your way home. You didn't prepare for this. You make an effort to interact but words don't come out right. You're extremely anxious and disoriented. GO HOME RIGHT NOW.");
							vista.txtOptions.setText("ATTENTION: YOU ARE AT YOUR LIMIT.\n\nMOVE UP AND DOWN TO DODGE ALL STIMULI AND MAKE YOUR WAY HOME.");
							//start game
						}
						break;

					case "Drink water":
						
						counterWater++;
						
						if(counterStoryLine == 1)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You drink some water. That made you feel much better (+1 spoon). You see your cat.");
							counterSpoons++;
						}
						else if(counterStoryLine == 2)
						{
							counterStoryLine++;
							
							if(counterWater < 2)
							{
								vista.txtEvent.setText("You drink water in front of your cat. Your cat doesn't care. You leave the house.");
							}
							else
							{
								vista.txtEvent.setText("You keep drinking water in front of your cat. Your cat doesn't care. You leave the house.");
							}
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;
							
							if(counterWater == 1)
							{
								vista.txtEvent.setText("You drink water on the way to the social event. It makes you feel good (+1 spoon). You mentally rehearse your social performance for the event. You get to the event.");
								counterSpoons++;
							}
							else if(counterWater == 2)
							{
							
								vista.txtEvent.setText("You drink more water on the way to the social event. It's always good to stay hydrated (+1 spoon). You mentally rehearse your social performance for the event. You get to the event.");
								counterSpoons++;
							}
							else if(counterWater > 2)
							{
								vista.txtEvent.setText("You drink more water on the way to the social event. Well, now you need to find a toilet. The unplanned deviation dysregulates you (-1 spoon). You finally get to the event.");
								counterSpoons--;
							}
						}
						else if(counterStoryLine == 4)
						{
							counterStoryLine++;
							
							if(counterWater < 3)
							{
								vista.txtEvent.setText("You drink water at the event. The lack of alcohol as a social lubricant makes the event unbearable. You feel like a social failure (-2 spoons). You head to the park afterwards to process and relax.");
								counterSpoons-=2;
							}
							else 
							{
								vista.txtEvent.setText("You drink more water at the event. Well, now you need to pee. You don't know when to interrupt to say it (-1 spoon). You feel very awkward. You leave early and go to the park to process and relax.");
								counterSpoons--;
							}
						}
						else if(counterStoryLine == 5)
						{
							
							counterStoryLine++;
							
							if(counterWater < 3)
							{
								vista.txtEvent.setText("You drink water at the park. It hydrates you, but it's still warm and noisy. The park overstimulated you, so you head home. Has the way back home always been this long and blurry?");
							}
							else
							{
								vista.txtEvent.setText("You drink more water at the park. Well, now you need to pee. You can't sit and enjoy nature (-1 spoon). You are stressed. You head home. Has the way back home always been this long and blurry?");
								counterSpoons--;
							}
						}
						else if(counterStoryLine == 6)
						{
							counterSpoons = 1;

							if(counterWater > 3)
							{
								vista.txtEvent.setText("You drink water on your way home. Your hands are suddenly shaking and you drop the bottle. There's a lot of traffic. You are overstimulated and disoriented.");
							}
							else
							{
								vista.txtEvent.setText("You drink more water on your way home. Now you need to pee. There's a lot of traffic. You are overstimulated, stressed and disoriented.");
							}
							vista.txtOptions.setText("ATTENTION: YOU ARE AT YOUR LIMIT.\n\nMOVE UP AND DOWN TO DODGE ALL STIMULI AND MAKE YOUR WAY HOME.");
							//start game
						}
						break;

					case "Wear headphones":

						if(counterStoryLine == 1)
						{
							counterStoryLine++;
							
							vista.txtEvent.setText("You listen to some music before getting up. It tickles your brain and travels around your whole body (+1 spoon). You get up and see your cat.");
							//play music
							counterSpoons++;
							counterMusic++;	
						}
						else if(counterStoryLine == 2)
						{
							counterStoryLine++;
							counterMusic++;	

							if(counterMusic == 1)
							{
								vista.txtEvent.setText("You listen to some music. It tickles your brain and it makes you jump around (+1 spoon). Your cat enters crazy mode and runs across the room. Haha. You go outside.");
								counterSpoons++;
							}
							else if(counterMusic == 2)
							{
								vista.txtEvent.setText("You keep listening to the same song. It makes you jump around. Your cat enters crazy mode and runs across the room. Meh. You go outside.");
							}
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;
							counterMusic++;	
							
							if(counterMusic == 1)
							{
								vista.txtEvent.setText("You listen to music on the way to the social event. It protects you from any external stimuli (+1 spoon). You mentally rehearse your social performance for the event. You get to the event.");
								counterSpoons++;
							}
							else if(counterMusic == 2)
							{
								vista.txtEvent.setText("You listen to the same song on your way to the social event. It protects you from any external stimuli (+1 spoon). You mentally rehearse your social performance for the event. You get to the event.");
								counterSpoons++;
							}
							else if(counterMusic == 3)
							{
								vista.txtEvent.setText("You keep your song on loop on your way to the social event. It protects you from external stimuli and it makes you feel safe (+1 spoon). You mentally rehearse your social performance for the event. You get to the event.");
								counterSpoons++;
							}
							else if(counterMusic > 3)
							{
								counterMusic = 3;
							}
						}
						else if(counterStoryLine == 4)
						{
							counterStoryLine++;
							vista.txtEvent.setText("It's very noisy, so you wear your headphones at the event. People look stranged and upset. You feel like a social failure (-2 spoons). You head to the park afterwards to process and relax.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 5)
						{
							counterStoryLine++;
							
							vista.txtEvent.setText("The park is noisy, warm and crowded. You wear your headphones but it doesn't help. You're tired and overstimulated. You head home. Has the way back home always been this long and blurry?");
						}
						else if(counterStoryLine == 6)
						{
							counterSpoons = 1;

							counterStoryLine++;
							
							vista.txtEvent.setText("You wear your headphones on your way home but it doesn't help. It's too warm, there's a lot of traffic and your senses are confused. You are extremely overstimulated and disoriented.");
							vista.txtOptions.setText("ATTENTION: YOU ARE AT YOUR LIMIT.\n\nMOVE UP AND DOWN TO DODGE ALL STIMULI AND MAKE YOUR WAY HOME.");
						}
						break;
					}
					
					updateSpoons();
					vista.repaint();
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
				if(!(opSelected.equals("...")) && (op1 || op2 || op3))
				{
					//reset selected
					op1 = false; op2 = false; op3 = false;

					if(!vista.gameOver)
					{
						vista.newOption = vista.allOptions[random.nextInt(4)+2];

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
				vista.spoons  = vista.herramienta.getImage("Images\\spoons5.png");
				counterSpoons = 5;
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
