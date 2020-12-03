package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class GameData
{
	public static final int STOP = 0, PLAY = 1, PAUSE = 2;
	
	private int state = STOP;
	private int score;
	private int life;
	private File wordFile;
	private Vector<String> wordList;
	
	public GameData()
	{
		this.wordList = new Vector<String>();
	}
	
	public void setState(int state)
	{
		this.state = state;
	}
	
	public int getState()
	{
		return this.state;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public synchronized void setLife(int life)
	{
		this.life = life;
	}
	
	public synchronized int getLife()
	{
		return this.life;
	}
	
	public void setWordFile()
	{
		this.wordFile = this.getFileByFileChooser();
		this.readFile(this.wordFile, this.wordList);
	}
	
	public String getWordFileName()
	{
		if(this.wordFile== null)
			return null;
		return this.wordFile.getName();
	}
	
	public Vector<String> getWordList()
	{
		return this.wordList;
	}
	
	public void refreshWordList()
	{
		if(this.wordFile== null)
			return;
		this.readFile(this.wordFile, this.wordList);
	}
	
	public boolean addTextToWordFile(String str) //�ܾ����Ͽ� �ܾ���� ���������� �����ϸ� true ��ȯ
	{
		if(this.wordFile == null) {
			JOptionPane.showMessageDialog(null, "������ ������ �������� �ʽ��ϴ�.");
			return false;
		}
		if(str.length() == 0) {
			JOptionPane.showMessageDialog(null, "������ �ܾ �����ϴ�.");
			return false;
		}
		
		String[] words = str.split("\n");
		FileWriter fw = null;
		BufferedWriter writer = null;
		try {
			fw = new FileWriter(this.wordFile, true);//FileWriter 2��° �μ��� true�� �̾ ���� false�� ���
			writer = new BufferedWriter(fw);
			if(this.wordFile.isFile() && this.wordFile.canWrite()) {
				for(int a = 0; a < words.length; a ++) {
					writer.append(words[a]);
					writer.newLine();
				}
			}
			
			JOptionPane.showMessageDialog(null, "���� ����");
		} catch(FileNotFoundException err) {
			JOptionPane.showMessageDialog(null, "������ ������ �������� �ʽ��ϴ�.");
			return false;
		} catch(IOException err) {
			
		} finally {
			try {
				writer.close();
			} catch(IOException err) {}
			try {
				fw.close();
			} catch(IOException err) {}
		}
		return true;
	}
	
	private File getFileByFileChooser()
	{
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); //���� Ž������ ���� ��θ� ����ȭ������ ����
        chooser.setAcceptAllFileFilterUsed(false); //���� ��Ͽ��� '�������' ����
        chooser.setDialogTitle("�ܾ� ���� ����"); //â�� ����
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //���� ���� ���
        chooser.setFileFilter(new FileNameExtensionFilter("�ؽ�Ʈ ����", "txt")); //���� ��Ͽ� txt �߰�(Ȯ���ڰ� txt�� ���ϸ� ������)
        
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) //������ ���� ������
        	return chooser.getSelectedFile();
        
		return null; //������ ���õ��� �ʾ����� null ����
	}
	
	private void readFile(File f, Vector<String> list) //���� f�� ���پ� �о ���� list�� �������
	{
		if(f == null || list == null)
			return;
		String line = null;
		FileReader fr = null;
		BufferedReader reader = null;
		try {
			fr = new FileReader(f);
			reader = new BufferedReader(fr);
			if(f.isFile() && f.canRead()) {
				list.clear();
				while((line = reader.readLine()) != null)
					list.add(line);
			}
		} catch(FileNotFoundException err) {
			
		} catch(IOException err) {
			
		} finally {
			try {
				reader.close();
			} catch(IOException err) {}
			try {
				fr.close();
			} catch(IOException err) {}
		}
	}
}