package reg.sunflower;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

public class Controlador implements WindowListener, MouseListener, KeyListener, ActionListener
{
	Modelo modelo;
	Vista  vista;
	Menu   menu;

	//Variables
	Boolean op1 = false, op2 = false, op3 = false;
	Boolean up  = false;

	Boolean hasSpoon = false;
	Boolean theEnd 	 = false;

	String  opSelected = "";
	Random  random	   = new Random();

	Clip ol;

	int counterStoryLine = 0;
	int counterCoffee	 = 0;
	int counterSpoons 	 = 5;
	int counterWater 	 = 0;
	int counterMusic 	 = 0;

	public Controlador(Modelo m, Vista v, Menu mn)
	{
		this.modelo = m;
		this.vista  = v;
		this.menu 	= mn;

		//listeners
		v.addWindowListener(this);
		v.addMouseListener(this);

		mn.addWindowListener(this);
		mn.addMouseListener(this);
		mn.dlgHelp.addWindowListener(this);
		mn.btnBack.addActionListener(this);
	}
	@Override
	public void mouseClicked(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();

		//System.out.println(x+"-"+y);
		if(menu.isActive())
		{
			//btn help
			if((x>=146 && x<=297)&&(y>=102 && y<=162))
			{
				menu.dlgHelp.setLayout(new FlowLayout());
				menu.dlgHelp.setSize(200,100);
				menu.dlgHelp.setTitle("Help?");
				menu.dlgHelp.add(menu.lblHelp);
				menu.dlgHelp.add(menu.btnBack);
				menu.dlgHelp.setResizable(false);
				menu.dlgHelp.setLocationRelativeTo(null);

				menu.dlgHelp.setVisible(true);
			}
			//btn play
			else if((x>=146 && x<=297)&&(y>=253 && y<=313))
			{
				vista.setVisible(true);
			}
		}
		else if(vista.isActive()&&!(vista.gameOver))
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
			if((counterStoryLine>0)&&(x>=801 && x<=928)&&(y>=572 && y<=600) && counterStoryLine > 1)
			{
				counterSpoons-=2;
				vista.newOption = vista.allOptions[random.nextInt(4)+2];
				updateImages();
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
						else if(counterStoryLine == 3 || counterStoryLine == 4 || counterStoryLine == 5 || counterStoryLine == 6)
						{
							vista.txtEvent.setText("You do nothing. You seem paralised. The public sees.\nDo something?");
							counterSpoons-=2;
						}
						break;

					case "Wake up":
						up = true;
						
						if(counterStoryLine == 0)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You wake up. It's too warm. You feel dizzy (-1 spoon).\nYou have committed to attend a social event today.");
							counterSpoons--;
						}
						else if(counterStoryLine == 1)
						{
							counterStoryLine++;
							counterCoffee++;

							vista.txtEvent.setText("You wash your face and get food and coffee. It wakes me up too (+1 spoon).\nYou see your cat.");
							counterSpoons++;
						}
						else if(counterStoryLine == 2)
						{
							counterStoryLine++;

							vista.txtEvent.setText("You wake up your cat. Your cat loves you but they're disappointed at you for not respecting their space (-2 spoons).\nYou leave the house.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;
							counterCoffee++;

							if(counterCoffee == 1)
							{
								vista.txtEvent.setText("You get coffee to go so I can wake up too. On the way to the social event we mentally rehearse your performance in every possible scenario (-1 spoon).\nYou get to the event.");
								counterSpoons--;
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("On the way to the social event you get more coffee to go. Your heart is racing and you can't even rehearse your social performance for the event (-2 spoons).\nThis is your brain speaking: one more coffee and I quit.");
								counterSpoons-=2;
							}
						}
						else if(counterStoryLine == 4)
						{
							counterStoryLine++;
							counterCoffee++;

							if(counterCoffee == 1)
							{
								vista.txtEvent.setText("You drink coffee at the event. The lack of alcohol as a social lubricant makes the event unbearable. You feel like a social failure (-2 spoons).\nYou head to the park to process and relax.");
								counterSpoons-=2;
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("You drink more coffee at the event. The lack of alcohol as a social lubricant makes the event unbearable. Your heart is racing (-2 spoons).\nYou head to the park to process and relax. This is your brain speaking: one more cup and I quit.");
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
								vista.txtEvent.setText("The park is crowded. You're overstimulated and extremely tired. You get a coffee to wake me up but it gives you anxiety.\nHas the way back home always been this long and blurry?");
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("The park is crowded. You're extremely tired. You buy some more caffeine and head home. This is your brain speaking: one more cup and I quit.\nHas the way back home always been this long and blurry?");
							}
							else if(counterCoffee == 3)
							{
								gameOver();
								vista.message1 = "I warned you???????";
							}

							counterSpoons  = 1;

						}
						else if(counterStoryLine == 6)
						{
							counterCoffee++;

							if(counterCoffee == 1)
							{
								vista.txtEvent.setText("You stop for coffee to wake me up. You're overstimulated, anxious and disoriented. The barista calls your name 4 times before you hear it.\nGO HOME RIGHT NOW.");
							}
							else if(counterCoffee == 2)
							{
								vista.txtEvent.setText("You stop for more coffee. You're overstimulated, anxious and disoriented. The barista calls your name 4 times before you hear it.\nGO HOME RIGHT NOW.");
							}
							else if(counterCoffee >= 3)
							{
								gameOver();
								vista.message1 = "I warned you???????";
							}

							if(!vista.gameOver)
							{
								miniGame();
							}
						}
						break;

					case "Interact":
						if(counterStoryLine == 1)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You decide to interact with the world. You get up (-1 spoon).\nYou see your cat.");
							counterSpoons--;
						}
						else if(counterStoryLine == 2)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You pet your cat. It purrs. It restores your spoons.\nYou go outside.");
							counterSpoons = 5;
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;
							vista.txtEvent.setText("On the way to the social event you find an acquaintance and you interact with them. You were not ready for this. It dysregulates you (-3 spoons).\nYou get to the social event.");
							counterSpoons-=3;
						}
						else if(counterStoryLine == 4)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You understand half of what the people at the event are saying. You try to interact but you make a fool of yourself (-2 spoons).\nYou head to the park to process and relax.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 5)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You try to make your senses interact with nature but it's crowded, warm and noisy.\nYou head home. Has the way back home always been this long and blurry?");
							counterSpoons  = 1;
						}
						else if(counterStoryLine == 6)
						{
							vista.txtEvent.setText("Someone asks for directions on your way home. You didn't prepare for this. You make an effort to interact but words don't come out right.\nYou're extremely anxious and disoriented. GO HOME RIGHT NOW.");
							miniGame();
						}
						break;

					case "Ignore":

						if(counterStoryLine == 1)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You can't ignore your commitments.\nYou get up and see your cat.");
						}
						else if(counterStoryLine == 2)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You ignore your cat. Your cat probably hates you for that (-2 spoons).\nWe don't deserve them. You go outside.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;
							vista.txtEvent.setText("On the way to the social event you find an acquaintance. You are not ready for this. You freeze and ignore them. It dysregulates you (-2 spoons).\nYou get to the social event.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 4)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You struggle to keep up at the event. You feel out of place. You give up and dissociate. You feel like a social failure (-2 spoons).\nYou head to the park to process and relax.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 5)
						{
							counterStoryLine++;
							vista.txtEvent.setText("You try to enjoy nature but you're dissociated. You ignore nature.\nYou head home. Has the way back home always been this long and blurry?");
							counterSpoons = 1;

						}
						else if(counterStoryLine == 6)
						{
							vista.txtEvent.setText("Someone asks for directions on your way home. You didn't prepare for this. You make an effort to interact but words don't come out right.\nYou're extremely anxious and disoriented. GO HOME RIGHT NOW.");
							miniGame();
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

							if(counterWater == 1)
							{
								vista.txtEvent.setText("You drink water in front of your cat. Your cat is not impressed. You leave the house.");
							}
							else if(counterWater == 2)
							{
								vista.txtEvent.setText("You keep drinking water in front of your cat. Your cat is not impressed. You leave the house.");
							}
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;

							if(counterWater == 1 && counterCoffee < 2)
							{
								vista.txtEvent.setText("You drink water on the way to the social event. It makes you feel good. Temporarily. You mentally rehearse your social performance for the event (-1 spoon).\nYou get to the event.");
								counterSpoons--;
							}
							else if(counterWater == 2 && counterCoffee == 0)
							{

								vista.txtEvent.setText("You drink more water on the way to the social event. Ok. You mentally rehearse your social performance for the event (-1 spoon).\nYou get to the event.");
								counterSpoons--;
							}
							else if((counterWater > 2) || (counterWater == 2 && counterCoffee > 0) || (counterWater == 1 && counterCoffee > 1))
							{
								vista.txtEvent.setText("You drink water on the way to the social event. Well, now you need to find a toilet. The unplanned deviation dysregulates you (-1 spoon).\nYou finally get to the event.");
								counterSpoons--;
							}
						}
						else if(counterStoryLine == 4)
						{
							counterStoryLine++;

							if((counterWater == 1 && counterCoffee < 2) || (counterWater == 2 && counterCoffee == 0))
							{
								vista.txtEvent.setText("You drink water at the event. The lack of alcohol as a social lubricant makes the event unbearable. You feel like a social failure (-2 spoons).\nYou head to the park to process and relax.");
								counterSpoons-=2;
							}
							else if((counterWater > 2) || (counterWater == 2 && counterCoffee > 0) || (counterWater == 1 && counterCoffee > 1))
							{
								vista.txtEvent.setText("You drink water at the event. Well, now you need to pee. You don't know when to interrupt to say it (-1 spoon). You feel very awkward.\nYou leave early and go to the park to process and relax.");
								counterSpoons--;
							}
						}
						else if(counterStoryLine == 5)
						{

							counterStoryLine++;

							if((counterWater == 1 && counterCoffee < 2) || (counterWater == 2 && counterCoffee == 0))
							{
								vista.txtEvent.setText("You drink water at the park. It hydrates you, but it's still warm and noisy. The park overstimulates you, so you head home.\nHas the way back home always been this long and blurry?");
							}
							else if((counterWater > 2) || (counterWater == 2 && counterCoffee > 0) || (counterWater == 1 && counterCoffee > 1))
							{
								vista.txtEvent.setText("You drink water at the park. Well, now you need to pee. You can't sit and enjoy nature. You are stressed.\nYou head home. Has the way back home always been this long and blurry?");
							}
							counterSpoons  = 1;
						}
						else if(counterStoryLine == 6)
						{
							if((counterWater == 1 && counterCoffee < 2) || (counterWater == 2 && counterCoffee == 0))
							{
								vista.txtEvent.setText("You drink water on your way home. Your hands are suddenly shaking and you drop the bottle.\nThere's a lot of traffic. You are overstimulated and disoriented.");
							}
							else if((counterWater > 2) || (counterWater == 2 && counterCoffee > 0) || (counterWater == 1 && counterCoffee > 1))
							{
								vista.txtEvent.setText("You drink more water on your way home. Now you need to pee. There's a lot of traffic.\nYou are overstimulated, stressed and disoriented.");
							}
							miniGame();
						}
						break;

					case "Wear headphones":

						if(counterStoryLine == 1)
						{
							counterStoryLine++;

							vista.txtEvent.setText("You listen to some music before getting up. It tickles your brain and travels around your whole body (+1 spoon).\nYou get up and see your cat.");
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
								vista.txtEvent.setText("You listen to some music. It tickles your brain and it makes you jump around (+1 spoon).\nYour cat enters crazy mode and runs across the room. Haha. You go outside.");
								counterSpoons++;
							}
							else if(counterMusic == 2)
							{
								vista.txtEvent.setText("You keep listening to the same song. It makes you jump around (+1 spoon).\nYour cat enters crazy mode and runs across the room. Haha. You go outside.");
								counterSpoons++;
							}
						}
						else if(counterStoryLine == 3)
						{
							counterStoryLine++;
							counterMusic++;	

							if(counterMusic == 1)
							{
								vista.txtEvent.setText("You listen to music on the way to the social event. It protects you from any external stimuli and it quiets down your thoughts.\nYou get to the event.");
							}
							else if(counterMusic == 2)
							{
								vista.txtEvent.setText("You listen to the same song on your way to the social event. It protects you from any external stimuli and it quiets down your thoughts.\nYou get to the event.");
							}
							else if(counterMusic == 3)
							{
								vista.txtEvent.setText("You keep your song on loop on your way to the social event. It protects you from external stimuli and it quiets down your thoughts.\nYou get to the event.");
							}
							else if(counterMusic > 3)
							{
								counterMusic = 3;
							}
						}
						else if(counterStoryLine == 4)
						{
							counterStoryLine++;
							vista.txtEvent.setText("It's very noisy, so you wear your headphones at the event. People look puzzled and upset. You feel like a social failure (-2 spoons).\nYou head to the park to process and relax.");
							counterSpoons-=2;
						}
						else if(counterStoryLine == 5)
						{
							counterStoryLine++;

							vista.txtEvent.setText("The park is noisy, warm and crowded. You wear your headphones but it doesn't help. You're tired and overstimulated.\nYou head home. Has the way back home always been this long and blurry?");
							counterSpoons  = 1;
						}
						else if(counterStoryLine == 6)
						{
							vista.txtEvent.setText("You wear your headphones on your way home but it doesn't help. It's too warm, there's a lot of traffic and your senses are confused.\nYou are extremely overstimulated and disoriented.");
							miniGame();
						}
						break;
					}

					updateImages();
					vista.repaint();
				}
				//else if no options selected
				else
				{
					try
					{
						vista.affE = AudioSystem.getAudioFileFormat(vista.errorSoundFile);
						vista.aisE = AudioSystem.getAudioInputStream(vista.errorSoundFile);

						AudioFormat af = vista.affE.getFormat();
						DataLine.Info info = new DataLine.Info(Clip.class, vista.aisE.getFormat(), ((int) vista.aisE.getFrameLength() * af.getFrameSize()));
						Clip ol = (Clip) AudioSystem.getLine(info);
						ol.open(vista.aisE);
						ol.loop(1); //()number of reproductions or (Clip.LOOP_CONTINUOUSLY)
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

	//updates images
	private void updateImages()
	{
		if(!vista.gameOver)
		{	
			//cat purrs face
			if(counterSpoons >= 5)
			{
				if(opSelected.equals("Interact")&&counterStoryLine == 3)
				{
					vista.you = vista.herramienta.getImage("Images\\catPurrs.png");
				}
				else
				{
					if(up)
					{
						vista.you = vista.herramienta.getImage("Images\\neutral.png");
					}
				}
				vista.spoons  = vista.herramienta.getImage("Images\\spoons5.png");
				counterSpoons = 5;
			}
			//spoons
			else if(counterSpoons == 4)
			{
				vista.spoons = vista.herramienta.getImage("Images\\spoons4.png");
				vista.you 	 = vista.herramienta.getImage("Images\\neutral.png");
			}
			else if(counterSpoons == 3)
			{
				vista.spoons = vista.herramienta.getImage("Images\\spoons3.png");
				vista.you 	 = vista.herramienta.getImage("Images\\neutral.png");
			}
			else if(counterSpoons == 2)
			{
				vista.spoons = vista.herramienta.getImage("Images\\spoons2.png");
				vista.you 	 = vista.herramienta.getImage("Images\\awake.png");
			}
			else if(counterSpoons == 1)
			{
				vista.spoons = vista.herramienta.getImage("Images\\spoons1.png");
				vista.you 	 = vista.herramienta.getImage("Images\\awake.png");
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

	//getting home mini game
	private void miniGame()
	{
		//window
		vista.txtOptions.setText("ATTENTION: You are at your limit.\nUse the KEYBOARD to move,\nGRAB your last spoon with INTRO,\nand run home.");

		vista.gameOver = true; //so it doesn't paint the rect

		vista.removeMouseListener(this);
		vista.addKeyListener(this);

		vista.miniGame = true;

		vista.btnSelectA = vista.herramienta.getImage("");
		vista.btnSelectB = vista.herramienta.getImage("");
		vista.btnSelectC = vista.herramienta.getImage("");

		vista.btnReplaceA = vista.herramienta.getImage("");
		vista.btnReplaceB = vista.herramienta.getImage("");
		vista.btnReplaceC = vista.herramienta.getImage("");

		vista.btnNext 	   = vista.herramienta.getImage("");
		vista.btnReshuffle = vista.herramienta.getImage("");

		vista.newOptionTitle = "";
		vista.newOption		 = "";

		vista.repaint();
		vista.requestFocusInWindow();

		//background music
		try
		{
			vista.affM = AudioSystem.getAudioFileFormat(vista.backgroundMusic);
			vista.aisM = AudioSystem.getAudioInputStream(vista.backgroundMusic);

			AudioFormat af 	   = vista.affM.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, vista.aisM.getFormat(), ((int) vista.aisM.getFrameLength() * af.getFrameSize()));

			ol = (Clip) AudioSystem.getLine(info);
			ol.open(vista.aisM);
			ol.loop(Clip.LOOP_CONTINUOUSLY); //()number of reproductions or (Clip.LOOP_CONTINUOUSLY)
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
	}
	private void stopPrevMusic()
	{
		ol.stop();
		ol.flush();
		ol.close();
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
		if(menu.dlgHelp.isActive())
		{
			menu.dlgHelp.setVisible(false);
		}
		else if(vista.isActive() || menu.isActive())
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
	@Override
	public void keyTyped(KeyEvent e){}
	@Override
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();

		//your movements
		if(keyCode == KeyEvent.VK_LEFT)
		{
			if(vista.posYouX > 50)
			{
				vista.posYouX-=8;
				vista.wallDown+=3;
			}
		}
		if(keyCode == KeyEvent.VK_RIGHT)
		{
			if(vista.posYouX < 700)
			{
				vista.posYouX+=8;
				vista.wallDown+=3;
			}
			if(hasSpoon && (vista.posYouX > 650 && vista.posYouX < 701))
			{
				theEnd = true;
				stopPrevMusic();

				//messages
				vista.posXMsg2 = 190;
				vista.posYMsg1 = 300;
				vista.posYMsg2 = 350;
				vista.message1 = "Oh, did that red line make you rush? It doesn't really do anything...";
				vista.message2 = "There's only one way to win this game. Go to bed, it's time to rest (SPACE key to get in).";

				//restore window
				vista.miniGame = false;
				vista.bed 	   = vista.herramienta.getImage("Images\\bed.png");

				vista.txtOptions.setText("A) "+vista.optionA+"\nB) "+vista.optionB+"\nC) "+vista.optionC);
				vista.txtEvent.setText("DO YOU SURVIVE ANOTHER DAY?");

				//end music
				try
				{
					vista.affTE = AudioSystem.getAudioFileFormat(vista.theEnd);
					vista.aisTE = AudioSystem.getAudioInputStream(vista.theEnd);

					AudioFormat af 	   = vista.affTE.getFormat();
					DataLine.Info info = new DataLine.Info(Clip.class, vista.aisTE.getFormat(), ((int) vista.aisTE.getFrameLength() * af.getFrameSize()));

					ol = (Clip) AudioSystem.getLine(info);
					ol.open(vista.aisTE);
					ol.loop(Clip.LOOP_CONTINUOUSLY); //()number of reproductions or (Clip.LOOP_CONTINUOUSLY)
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

				counterStoryLine++;
			}
		}
		if(keyCode == KeyEvent.VK_UP)
		{
			if(vista.posYouY > 250)
			{
				vista.posYouY-=8;
				vista.wallDown+=3;
			}
		}
		if(keyCode == KeyEvent.VK_DOWN)
		{
			if(vista.posYouY < 451)
			{
				vista.posYouY+=8;
				vista.wallDown+=3;
			}
		}
		if((keyCode == KeyEvent.VK_ENTER) && (vista.posYouX > 10 && vista.posYouX < 200)&&(vista.posYouY > 350 && vista.posYouY < 680))
		{
			vista.spoons = vista.herramienta.getImage("");
			vista.you 	 = vista.herramienta.getImage("Images\\youWSpoon.png");

			hasSpoon = true;
		}
		if(keyCode == KeyEvent.VK_SPACE && (vista.posYouX > 96 && vista.posYouX < 240)&&(vista.posYouY > 314 && vista.posYouY < 434)) 
		{
			vista.bed = vista.herramienta.getImage("images\\bedIn.png");
			vista.you = vista.herramienta.getImage("");

			if(vista.txtOptions.getText().contains("Wake up"))
			{
				vista.message1 = "You still had the option to wake up the next day";
				vista.message2 = "THE END.";
				vista.repaint();

				stopPrevMusic();

				try
				{
					vista.affS = AudioSystem.getAudioFileFormat(vista.success);
					vista.aisS = AudioSystem.getAudioInputStream(vista.success);

					AudioFormat af = vista.affS.getFormat();
					DataLine.Info info = new DataLine.Info(Clip.class, vista.aisS.getFormat(), ((int) vista.aisS.getFrameLength() * af.getFrameSize()));
					Clip ol = (Clip) AudioSystem.getLine(info);
					ol.open(vista.aisS);
					ol.loop(0); //()number of reproductions or (Clip.LOOP_CONTINUOUSLY)
					Thread.sleep(2000);
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
			else
			{
				vista.message1 = "You did not have the option to wake up the next day";
				vista.message2 = "THE END.";

				stopPrevMusic();

				try
				{
					vista.affF = AudioSystem.getAudioFileFormat(vista.wahwah);
					vista.aisF = AudioSystem.getAudioInputStream(vista.wahwah);

					AudioFormat af = vista.affF.getFormat();
					DataLine.Info info = new DataLine.Info(Clip.class, vista.aisF.getFormat(), ((int) vista.aisF.getFrameLength() * af.getFrameSize()));
					Clip ol = (Clip) AudioSystem.getLine(info);
					ol.open(vista.aisF);
					ol.loop(0); //()number of reproductions or (Clip.LOOP_CONTINUOUSLY)
					Thread.sleep(5000);
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
		}
		vista.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e){}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(menu.btnBack))
		{
			menu.dlgHelp.setVisible(false);
		}
	}
}

