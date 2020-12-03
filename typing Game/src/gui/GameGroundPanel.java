package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import data.GameData;
import entity.Boar;
import thread.GameThreadManager;

public class GameGroundPanel extends JPanel
{
	private Boar boars[];
	private GameData data;
	private GameThreadManager threadManager;
	
	public GameGroundPanel(GameData data, GameThreadManager manager)
	{
		this.data = data;
		this.threadManager = manager;
		
		this.setLayout(null);
		
		int delays[] = new int[5];
		this.boars = new Boar[5];
		for(int a = 0; a < this.boars.length; a ++) {
			delays[a] = 200;
			this.boars[a] = new Boar();
			this.boars[a].setDirection(0, -1);
		}
		
		this.threadManager.start(this, this.boars, delays);
		
		this.drawThread.start();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int width = this.getWidth(), height = this.getHeight();
		
		for(int a = 0; a < this.boars.length; a ++) {
			if(this.boars[a].getState() != Boar.DEAD)
				this.boars[a].draw(g);
		}
		
		if(this.data.getState() == GameData.PAUSE) {
			g.setColor(new Color(0, 0, 0, 90));
			g.fillRect(0, 0, width, height);
			g.setColor(Color.WHITE);
			g.setFont(new Font(null, Font.PLAIN, 40));
			g.drawString("PAUSE", width / 2 - 70, height / 2);
		}
	}
	
	private Thread drawThread = new Thread()
	{
		@Override
		public void run()
		{
			while(true) {
				try {
					repaint();
					Thread.sleep(100);
				} catch(InterruptedException err) {}
			}
		}
	};
}
