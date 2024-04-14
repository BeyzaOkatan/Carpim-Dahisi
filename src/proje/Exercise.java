package proje;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import javax.swing.JOptionPane;


public class Exercise implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int a, b, N;
	private ArrayList<ArrayList<Integer>> questions;

	public Exercise() {
		this.questions = new ArrayList<>();
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public ArrayList<ArrayList<Integer>> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<ArrayList<Integer>> questions) {
		this.questions = questions;
	}
	
	public void writeFile() {   // a b n değerlerini serileştirme ile dosyaya yazar 
		String filePath = "src/assets/settings.dat";
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream writer = new ObjectOutputStream(fileOut);
			writer.writeInt(a);
			writer.writeInt(b);
			writer.writeInt(N);
			writer.close();
			
		} catch (IOException e) {	
			JOptionPane.showMessageDialog(null,"Sistem gerekli ayarlara ulaşamadı.");
			e.printStackTrace();
		}
		
	}
	
	
	public void exercises() {  // a b n değerlerini deserialization ile okuyarak alıştırma oluşturur
		
		String filePath = "src/assets/settings.dat";
		  try {			  
			  ObjectInputStream reader = new ObjectInputStream( new FileInputStream(filePath));
			  a = (int)reader.readInt();
			  b = (int)reader.readInt();
			  N = (int)reader.readInt();
			  reader.close();
			  
		   	} catch (IOException e) {	
		   		JOptionPane.showMessageDialog(null,"Sistem sorulara ulaşamadı.");
		   		e.printStackTrace();
		   	}		
		
		Random random = new Random();
		int number;
		
		if(questions != null) {
			questions.clear();
		}

			for (int i = 0; i < 3; i++) {
				
	            ArrayList<Integer> row = new ArrayList<>();
	            for (int j = 0; j < N; j++) {
	                row.add(0); 
	            }
	           questions.add(row); 
	        }
			for (int i = 0; i < 2; i++) {
		            ArrayList<Integer> row = questions.get(i);
		            for (int j = 0; j < N; j++) {
		            	if(i==0) {
		            		number = a;
		            	}else {
		            		number = b;
		            	}
		                row.set(j, random.nextInt(number)+1);
		            }
		        }
			for(int i = 0; i< N ; i++) {
				int product = questions.get(0).get(i) * questions.get(1).get(i);
				questions.get(2).set(i, product);
			}
						
		
	}
	
	public void allExercises() {
		
		Calendar specificDate = Calendar.getInstance();
		
		File file = new File("src/assets/exercises.txt");
		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			fr = new FileWriter(file, true);
			br = new BufferedWriter(fr);
			br.write(specificDate.getTime().toString());
			for (int i = 0; i < questions.get(0).size(); i++) {
				br.newLine();
				String result = String.valueOf(questions.get(0).get(i)) +" x "+ String.valueOf(questions.get(1).get(i));
				br.write(result);
			}
			br.newLine();
			br.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
