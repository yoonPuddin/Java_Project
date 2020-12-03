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
	private JTextArea wordsArea; //�Է��� �ܾ���� ���� ������Ʈ
	
	private GameData data;
	
	public EditPanel(GameData data)
	{
		this.data = data; //�Ѱܹ��� GameData ��ü�� ���۷����� ����
		
		this.setLayout(new BorderLayout());
		
		this.fileName = new JLabel("������ �����ϼ���");
		this.add(this.fileName, BorderLayout.NORTH);
		
		this.wordsArea = new JTextArea();
		this.wordsArea.setEditable(false); //wordsArea�� �ؽ�Ʈ�� ����ڰ� ������ �� ���� ����
		this.wordsArea.setFocusable(false);
		
		//��ũ������ ���� ��ũ������ ������Ʈ�� wordsArea, �������� �̵��ϴ� ��ũ�ѹٴ� wordsArea�� ������ �������� ���̰� �������� �̵��ϴ� ��ũ�ѹٴ� �ƿ� ������ �ʰ� ����
		JScrollPane scrollPane = new JScrollPane(this.wordsArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.CENTER);
		
		//�ܾ �Է��� ������Ʈ���� ���� �����̳�
		JPanel inputPart = new JPanel();
		this.editField = new JTextField(12);
		this.editField.addKeyListener(new EditKeyListener());
		inputPart.add(this.editField);
		
		this.addButton = new JButton("add");
		this.addButton.setMargin(new Insets(0, 0, 0, 0));
		this.addButton.setPreferredSize(new Dimension(40, 25)); //��ư�� ũ�� ����, setSize�δ� FlowLayout���� ũ�⸦ ������ �� ����
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
	
	public void addText() //editField �ȿ� �ִ� �ؽ�Ʈ�� wordsArea�� ����
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