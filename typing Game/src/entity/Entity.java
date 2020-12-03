package entity;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public abstract class Entity
{
	protected ImageIcon img = null;
	
	protected Vector2D dir;
	
	protected float x, y;
	
	public Entity()
	{
		this.dir = new Vector2D();
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setDirection(float x, float y)
	{
		this.dir.set(x, y);
		this.dir.normalize();
	}
	
	public void setDirection(float angle)
	{
		this.dir.set(angle);
	}
	
	public void setDirection(Vector2D vector)
	{
		this.dir = vector;
	}
	
	public void setImage(ImageIcon img)
	{
		this.img = img;
	}
	
	public abstract void draw(Graphics g);
	
	public abstract void update(int deltaTime);
}