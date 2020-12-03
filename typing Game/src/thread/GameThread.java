package thread;

import data.GameData;
import entity.Boar;
import gui.GameGroundPanel;

public class GameThread extends Thread //단어를 떨어트리는 스레드
{
	private int delay;
	private String text = null;
	private GameGroundPanel panel;
	private Boar boar;
	
	private GameData data;
	private GameThreadManager manager;
	
	public GameThread(GameGroundPanel panel, Boar e, int delay, GameThreadManager manager, GameData data)
	{
		this.panel = panel;
		this.boar = e;
		this.delay = delay;
		this.manager = manager;
		this.data = data;
	}
	
	public void setText(String str)
	{
		this.text = str;
		this.boar.setText(str);
	}
	
	public boolean isCorrect(String str)
	{
		if(this.text != null)
			return this.text.equals(str);
		return false;
	}
	
	public boolean isReady()
	{
		return this.boar.getState() == Boar.DEAD;
	}
	
	public void wakeup()
	{
		this.boar.setState(Boar.ALIVE);
		float rx = (float)(Math.random() * (this.panel.getWidth() - this.boar.getWidth())) + (this.boar.getWidth() / 2);
		this.boar.setPosition(rx, 0);
	}
	
	public void kill()
	{
		this.boar.setState(Boar.DIE);
	}
	
	public void reset()
	{
		this.text = null;
		this.boar.setState(Boar.DEAD);
		if(!this.manager.contains(this))
			this.manager.addQueue(this);
	}
	
	@Override
	public void run()
	{
		while(true) {
			try {
				if(this.data.getState() == GameData.PLAY && this.text != null) { //현재 게임의 상태가 플레이 중이고 단어를 가지고 있다면
					this.boar.update(this.delay);
					if(this.boar.getY() + this.boar.getHeight() >= this.panel.getHeight()) {
						this.boar.setState(Boar.DEAD);
						int life = this.data.getLife();
						this.data.setLife(life > 0 ? life - 1 : 0);
						if(life - 1 <= 0) {
							this.data.setState(GameData.STOP);
							this.manager.reset();
						}
					}
					if(this.boar.getState() == Boar.DEAD)
						this.interrupt();
				}
				Thread.sleep(this.delay);
			} catch(InterruptedException e) {
				this.reset();
			}
		}
	}
}