package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.GameData;
import thread.GameThreadManager;

public class GamePanel extends JPanel
{
	private GameGroundPanel ground;
	private InputPanel input;
	
	private GameData data;
	
	private GameThreadManager threadManager;
	
	public GamePanel(GameData data)
	{
		this.data = data;
		this.threadManager = new GameThreadManager(data);
		
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.setBackground(Color.LIGHT_GRAY);
		this.ground = new GameGroundPanel(data, this.threadManager);
		this.input = new InputPanel();
		this.input.getInputField().addKeyListener(new InputKeyListener());
		this.add(this.ground, BorderLayout.CENTER);
		this.add(this.input, BorderLayout.SOUTH);
	}
	
	public void start()
	{
		if(this.data.getState() == GameData.PLAY) {
			JOptionPane.showMessageDialog(null, "이미 게임이 진행 중 입니다.");
			return;
		}
		else if(this.data.getState() == GameData.STOP) {
			this.data.refreshWordList();
			this.data.setScore(0);
			this.data.setLife(3);
		}
		if(this.data.getWordList().size() == 0) {
			JOptionPane.showMessageDialog(null, "단어 목록이 없습니다.");
			return;
		}
		
		this.data.setState(GameData.PLAY);
		this.input.getInputField().setText("");
		this.setFocus();
		this.threadManager.interrupt();
	}
	
	public void stop()
	{
		if(this.data.getState() == GameData.STOP)
			return;
		
		this.data.setState(GameData.STOP);
		this.data.setScore(0);
		this.threadManager.reset();
	}
	
	public void pause()
	{
		if(this.data.getState() == GameData.PLAY)
			this.data.setState(GameData.PAUSE);
	}
	
	public void setFocus()
	{
		this.input.getInputField().requestFocus();
	}
	
	class InputKeyListener extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			JTextField tf = (JTextField)e.getSource();
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(data.getState() == GameData.PLAY)
					threadManager.checkText(tf.getText());
				tf.setText("");
			}
		}
	}
}