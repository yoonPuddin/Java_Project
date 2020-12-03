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
	
	public boolean addTextToWordFile(String str) //단어파일에 단어들을 성공적으로 저장하면 true 반환
	{
		if(this.wordFile == null) {
			JOptionPane.showMessageDialog(null, "저장할 파일이 존재하지 않습니다.");
			return false;
		}
		if(str.length() == 0) {
			JOptionPane.showMessageDialog(null, "저장할 단어가 없습니다.");
			return false;
		}
		
		String[] words = str.split("\n");
		FileWriter fw = null;
		BufferedWriter writer = null;
		try {
			fw = new FileWriter(this.wordFile, true);//FileWriter 2번째 인수에 true면 이어서 쓰고 false면 덮어씀
			writer = new BufferedWriter(fw);
			if(this.wordFile.isFile() && this.wordFile.canWrite()) {
				for(int a = 0; a < words.length; a ++) {
					writer.append(words[a]);
					writer.newLine();
				}
			}
			
			JOptionPane.showMessageDialog(null, "저장 성공");
		} catch(FileNotFoundException err) {
			JOptionPane.showMessageDialog(null, "저장할 파일이 존재하지 않습니다.");
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
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); //파일 탐색기의 시작 경로를 바탕화면으로 설정
        chooser.setAcceptAllFileFilterUsed(false); //필터 목록에서 '모든파일' 없앰
        chooser.setDialogTitle("단어 파일 설정"); //창의 제목
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //파일 선택 모드
        chooser.setFileFilter(new FileNameExtensionFilter("텍스트 파일", "txt")); //필터 목록에 txt 추가(확장자가 txt인 파일만 보여줌)
        
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) //파일이 선택 됐으면
        	return chooser.getSelectedFile();
        
		return null; //파일이 선택되지 않았으면 null 리턴
	}
	
	private void readFile(File f, Vector<String> list) //파일 f를 한줄씩 읽어서 벡터 list에 집어넣음
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