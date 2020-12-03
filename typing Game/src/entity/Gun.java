package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Gun extends Entity
{
	private float size = 100;
	
	@Override
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(30, BasicStroke.CAP_SQUARE, 0));
		g.drawLine((int)this.x, (int)this.y, (int)(this.x + (this.dir.getX() * this.size)), (int)(this.y - (this.dir.getY() * this.size)));
	}
	
	@Override
	public void update(int deltaTime)
	{
		
	}
}