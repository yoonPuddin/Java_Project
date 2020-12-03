package thread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import data.GameData;
import entity.Boar;
import entity.Entity;
import gui.GameGroundPanel;

public class GameThreadManager extends Thread //�ܾ���� ����Ʈ���� ��������� ������ ������
{
	private ArrayList<GameThread> threadList;
	private Queue<GameThread> waitingQueue;
	
	private GameData data;
	
	public GameThreadManager(GameData data)
	{
		this.data = data;
		
		this.threadList = new ArrayList<GameThread>();
		
		this.waitingQueue = new LinkedList<GameThread>();
	}
	
	public int runningCount()
	{
		return this.threadList.size() - this.waitingQueue.size();
	}
	
	public void checkText(String str) //���� �۵����� ������ �� ��ġ�ϴ� �ܾ ���� �����带 �߰��ϸ� �ش� �����忡 ���ͷ�Ʈ�� ��
	{
		for(int a = 0; a < this.threadList.size(); a ++) {
			GameThread thread = this.threadList.get(a);
			if(thread.isCorrect(str)) {
				thread.kill();
				this.data.setScore(this.data.getScore() + 10); //������ 10�� �ø�
				return;
			}
		}
	}
	
	public boolean contains(GameThread thread)
	{
		return this.waitingQueue.contains(thread);
	}
	
	public void addQueue(GameThread thread)
	{
		this.waitingQueue.add(thread); //�����带 ���ť�� �������
	}
	
	public void reset() //��������� ����
	{
		this.interrupt();
		for(int a = 0; a < this.threadList.size(); a ++)
			this.threadList.get(a).interrupt();
	}
	
	public void start(GameGroundPanel panel, Boar boars[], int delays[])
	{
		super.start();
		for(int a = 0; a < boars.length; a ++) {
			GameThread thread = new GameThread(panel, boars[a], delays[a], this, this.data);
			this.threadList.add(thread);
			this.waitingQueue.add(thread);
			thread.start();
		}	
	}
	
	@Override
	public void run()
	{
		while(true) {
			try {
				if(this.data.getState() == GameData.PLAY) {
					if(!this.waitingQueue.isEmpty()) { //���ť�� ������� �ʴٸ�
						if(this.data.getWordList().size() > 0) { //�ܾ��Ͽ� �ܾ �����ִ� ���
							if(this.runningCount() < (this.data.getScore() / 100) + 1) {
								if(this.waitingQueue.peek() != null && this.waitingQueue.peek().isReady()) {
									Vector<String> list = this.data.getWordList();
									int randomIndex = (int)(Math.random() * list.size()); //�ܾ����� ���� �ε���
								
									GameThread thread = this.waitingQueue.remove(); //���ť�� ù��° �����带 �������� ���ť���� ����
									thread.setText(list.get(randomIndex)); //������ �����忡 �ܾ��
									thread.wakeup();
									
									list.remove(randomIndex); //�ܾ��Ͽ��� �ܾ� ����
								}
								else if(this.waitingQueue.peek() != null)
									this.waitingQueue.add(this.waitingQueue.remove());
							}
						}
					}
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}
}