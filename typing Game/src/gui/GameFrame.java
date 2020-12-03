package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import data.GameData;

public class GameFrame extends JFrame
{
	private GamePanel gamePanel;
	private ScorePanel scorePanel;
	private EditPanel editPanel;
	
	private GameData data;
	
	public GameFrame()
	{
		this.data = new GameData();
		
		this.setTitle("타이핑 게임");
		this.setSize(900, 800);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setJMenuBar(this.makeMenuBar());
		
		Container container = this.getContentPane();
		
		container.add(this.makeToolbar(), BorderLayout.NORTH);
		
		JSplitPane hPane = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(900 / 3 * 2);
		hPane.setEnabled(false);
		hPane.addMouseListener(new MousePressedListener());
		
		this.gamePanel = new GamePanel(this.data);
		
		JSplitPane vPane = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setDividerLocation(200);
		vPane.addMouseListener(new MousePressedListener());
		
		this.scorePanel = new ScorePanel(this.data);
		this.editPanel = new EditPanel(this.data);
		vPane.setTopComponent(this.scorePanel);
		vPane.setBottomComponent(this.editPanel);
		
		hPane.setLeftComponent(this.gamePanel);
		hPane.setRightComponent(vPane);
		
		container.add(hPane, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public JToolBar makeToolbar()
	{
		/*ImageIcon icon = new ImageIcon(this.getClass().getResource("../open.png"));
		Image img = icon.getImage();
		img = img.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		icon.setImage(img); //이미지 버튼 테스트*/
		
		JToolBar tb = new JToolBar();
		JButton bu = new JButton("Start");
		bu.setActionCommand("start");
		bu.setToolTipText("Start");
		bu.addActionListener(new ToolbarActionListener());
		tb.add(bu);
		bu = new JButton("Pause");
		bu.setActionCommand("pause");
		bu.setToolTipText("Pause");
		bu.addActionListener(new ToolbarActionListener());
		tb.add(bu);
		bu = new JButton("Stop");
		bu.setActionCommand("stop");
		bu.setToolTipText("Stop");
		bu.addActionListener(new ToolbarActionListener());
		tb.add(bu);
		
		return tb;
	}
	
	public JMenuBar makeMenuBar()
	{
		JMenuBar m = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem itemOpen = new JMenuItem("open");
		itemOpen.addActionListener(new FileMenuActionListener()); //File 메뉴 안에 open 메뉴 아이템에 리스너 설정
		fileMenu.add(itemOpen);
		m.add(fileMenu);
		
		return m;
	}
	
	class ToolbarActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			switch(e.getActionCommand()) {
			case "start" :
				gamePanel.start();
				break;
			case "stop" :
				gamePanel.stop();
				break;
			case "pause" :
				gamePanel.pause();
			}
		}
	}
	
	class FileMenuActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			switch(e.getActionCommand()) {
			case "open" :
				data.setWordFile();
				editPanel.refresh();
				break;
			}
		}
	}
	
	class MousePressedListener extends MouseAdapter
	{
		@Override
		public void mousePressed(MouseEvent e)
		{
			if(data.getState() == GameData.PLAY)
				gamePanel.setFocus();
		}
	}
}