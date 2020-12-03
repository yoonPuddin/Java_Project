package gui;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel
{
	private JTextField inputField;
	
	public InputPanel()
	{
		this.inputField = new JTextField(30);
		this.add(this.inputField);
	}
	
	public JTextField getInputField()
	{
		return this.inputField;
	}
}