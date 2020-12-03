package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Boar extends Entity
{
	public static final int DEAD = 0, ALIVE = 1, DIE = 2;
	
	private int width = 50, height = 80;
	private int speed = 30;
	public int accumTime = 0;
	
	private int state = DEAD;
	
	private String text = null;
	
	public int getState()
	{
		return this.state;
	}
	
	public void setState(int state)
	{
		this.state = state;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	@Override
	public void draw(Graphics g)
	{
		if(this.state != Boar.DEAD) {
			if(this.state == Boar.ALIVE) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLACK);
			}
			g.fillRect((int)(this.x - (this.width / 2)), (int)(y - (this.height / 2)), this.width, this.height);
			
			g.setFont(new Font(null, Font.PLAIN, 20));
			g.setColor(Color.BLACK);
			g.drawString(this.text, (int)this.x - (this.width / 2), (int)this.y);
		}
	}

	@Override
	public void update(int deltaTime)
	{
		if(this.state == Boar.ALIVE) {
			this.x += this.dir.getX() * this.speed / 1000 * deltaTime;
			this.y -= this.dir.getY() * this.speed / 1000 * deltaTime;
		}
		else if(this.state == Boar.DIE) {
			if(this.accumTime >= 400) {
				this.accumTime = 0;
				this.setState(Boar.DEAD);
				return;
			}
			this.accumTime += deltaTime;
		}
	}
}