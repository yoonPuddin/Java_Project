package thread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import data.GameData;
import entity.Boar;
import entity.Entity;
import gui.GameGroundPanel;

public class GameThreadManager extends Thread //단어들을 떨어트리는 스레드들을 관리할 스레드
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
	
	public void checkText(String str) //현재 작동중인 스레드 중 일치하는 단어를 가진 스레드를 발견하면 해당 스레드에 인터럽트를 걺
	{
		for(int a = 0; a < this.threadList.size(); a ++) {
			GameThread thread = this.threadList.get(a);
			if(thread.isCorrect(str)) {
				thread.kill();
				this.data.setScore(this.data.getScore() + 10); //점수를 10점 올림
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
		this.waitingQueue.add(thread); //스레드를 대기큐에 집어넣음
	}
	
	public void reset() //스레드들을 리셋
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
					if(!this.waitingQueue.isEmpty()) { //대기큐가 비어있지 않다면
						if(this.data.getWordList().size() > 0) { //단어목록에 단어가 남아있는 경우
							if(this.runningCount() < (this.data.getScore() / 100) + 1) {
								if(this.waitingQueue.peek() != null && this.waitingQueue.peek().isReady()) {
									Vector<String> list = this.data.getWordList();
									int randomIndex = (int)(Math.random() * list.size()); //단어목록의 랜덤 인덱스
								
									GameThread thread = this.waitingQueue.remove(); //대기큐의 첫번째 스레드를 가져오고 대기큐에서 삭제
									thread.setText(list.get(randomIndex)); //가져온 스레드에 단어설정
									thread.wakeup();
									
									list.remove(randomIndex); //단어목록에서 단어 삭제
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