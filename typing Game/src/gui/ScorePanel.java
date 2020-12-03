package gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.GameData;

public class ScorePanel extends JPanel
{
	private JLabel score, life;
	
	private GameData data;
	
	public ScorePanel(GameData data)
	{
		this.data = data;
		
		this.setLayout(null);
		this.setBackground(Color.YELLOW);
		
		this.score = new JLabel("����: 0");
		this.score.setSize(100, 30);
		this.score.setLocation(0, 0);
		this.add(this.score);
		
		this.life = new JLabel("���� ��ȸ: 0");
		this.life.setSize(100, 30);
		this.life.setLocation(0, 80);
		this.add(this.life);
		
		RefreshThread thread = new RefreshThread();
		thread.start();
	}
	
	class RefreshThread extends Thread //������ ���� ��ȸ�� �����ϴ� ������
	{
		@Override
		public void run()
		{
			while(true) {
				try {
					score.setText("����: " + data.getScore());
					life.setText("���� ��ȸ: " + data.getLife());
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}
	}
}