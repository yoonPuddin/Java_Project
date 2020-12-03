package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import data.GameData;

public class EditPanel extends JPanel
{
	private JLabel fileName;
	private JTextField editField;
	private JButton addButton, saveButton;
	private JTextArea wordsArea; //입력한 단어들이 보일 컴포넌트
	
	private GameData data;
	
	public EditPanel(GameData data)
	{
		this.data = data; //넘겨받은 GameData 객체의 레퍼런스를 저장
		
		this.setLayout(new BorderLayout());
		
		this.fileName = new JLabel("파일을 설정하세요");
		this.add(this.fileName, BorderLayout.NORTH);
		
		this.wordsArea = new JTextArea();
		this.wordsArea.setEditable(false); //wordsArea의 텍스트를 사용자가 수정할 수 없게 설정
		this.wordsArea.setFocusable(false);
		
		//스크롤팬을 설정 스크롤팬의 컴포넌트는 wordsArea, 수직으로 이동하는 스크롤바는 wordsArea의 내용이 가려지면 보이고 수평으로 이동하는 스크롤바는 아예 보이지 않게 설정
		JScrollPane scrollPane = new JScrollPane(this.wordsArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.CENTER);
		
		//단어를 입력할 컴포넌트들을 넣을 컨테이너
		JPanel inputPart = new JPanel();
		this.editField = new JTextField(12);
		this.editField.addKeyListener(new EditKeyListener());
		inputPart.add(this.editField);
		
		this.addButton = new JButton("add");
		this.addButton.setMargin(new Insets(0, 0, 0, 0));
		this.addButton.setPreferredSize(new Dimension(40, 25)); //버튼의 크기 설정, setSize로는 FlowLayout에서 크기를 변경할 수 없음
		this.addButton.addActionListener(new ButtonActionListener());
		inputPart.add(this.addButton);
		
		this.saveButton = new JButton("save");
		this.saveButton.setMargin(new Insets(0, 0, 0, 0));
		this.saveButton.setPreferredSize(new Dimension(40, 25));
		this.saveButton.addActionListener(new ButtonActionListener());
		inputPart.add(this.saveButton);
		
		this.add(inputPart, BorderLayout.SOUTH);
	}
	
	public void refresh()
	{
		String name = this.data.getWordFileName();
		if(name == null)
			return;
		this.fileName.setText(name);
	}
	
	public void addText() //editField 안에 있는 텍스트를 wordsArea에 넣음
	{
		String input = this.editField.getText();
		if(input.length() > 0) {
			this.wordsArea.setText(this.wordsArea.getText() + input + "\n");
			this.editField.setText("");
		}
		this.editField.requestFocus();
	}
	
	class ButtonActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object c = e.getSource();
			if(c == addButton)
				addText();
			else if(c == saveButton) {
				if(data.addTextToWordFile(wordsArea.getText()))
					wordsArea.setText("");
			}
		}
	}
	
	class EditKeyListener extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				addText();
		}
	}
}