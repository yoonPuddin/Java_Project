package entity;

public class Vector2D
{
	private static final float radian = 0.01745328f;
	
	private float x, y, length;
	
	public Vector2D() {}
	
	public Vector2D(float x, float y)
	{
		this.set(x, y);
	}
	
	public Vector2D(float angle)
	{
		this.set(angle);
	}
	
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.length = (float)Math.sqrt((x * x) + (y * y));
	}
	
	public void set(float angle)
	{
		this.x = (float)Math.cos(this.radian * angle);
		this.y = (float)Math.sin(this.radian * angle);
		this.length = 1;
	}
	
	public void normalize()
	{
		if(this.length > 1) {
			this.x /= this.length;
			this.y /= this.length;
			this.length = 1;
		}
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	public Vector2D copy()
	{
		return new Vector2D(this.x, this.y);
	}
}