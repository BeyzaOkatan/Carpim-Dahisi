package proje;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable{
	static final long serialVersionUID = 3L;
	private String username;
	private String password;
	private boolean isAdmin;
	private transient ArrayList<ArrayList<String>>  infoTable;
	
	public Person() {
		this.isAdmin = false;
		infoTable = new ArrayList<>();		
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}	
	
	public ArrayList<ArrayList<String>> getInfoTable() {
		return infoTable;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public void setAskedQuestions(ArrayList<ArrayList<Integer>> questions) { // infotable'a sorulan soruları kaydetme
		
		if(infoTable != null) {
			infoTable.clear();
		}
		
		for (int i = 0; i < 5; i++) {			
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < questions.get(0).size(); j++) {
                row.add(""); 
            }
            infoTable.add(row); 
        }
		
		for (int i = 0; i < questions.get(0).size(); i++) {
			String result = String.valueOf(questions.get(0).get(i)) +" x "+ String.valueOf(questions.get(1).get(i));
			infoTable.get(0).set(i, result);
        }
	}
	
	// infotable'a soruların her birine başlama bitirme zamanı, çözülme süresi, doğru ya da yanlış olduğunu ekleme
	public void setTimeandNet(ArrayList<String> startTime, ArrayList<String> finishTime, ArrayList<String> time,ArrayList<String> net) {
			
		for (int i = 0; i < infoTable.get(0).size(); i++) {
			infoTable.get(1).set(i, startTime.get(i+1));
        }
		for (int i = 0; i < infoTable.get(0).size(); i++) {
			infoTable.get(2).set(i, finishTime.get(i));
        }
		for (int i = 0; i < infoTable.get(0).size(); i++) {
			int a;
			int b;
			if(i==0) {
				infoTable.get(3).set(i, time.get(i));
			}else {
				a = Integer.parseInt(time.get(i));
				b = Integer.parseInt(time.get(i-1));
				a = a-b;
				infoTable.get(3).set(i, String.valueOf(a));
			}			
        }
		for (int i = 0; i < infoTable.get(0).size(); i++) {
			infoTable.get(4).set(i, net.get(i));
        }

		writeReport(startTime.get(0));
	}
	
	// her bir alıştırmadan elde ediilen günceleri csv dosyasına yazdırma 
	public void writeReport(String date) {
				
		File file = new File("src/assets/"+this.getUsername()+".csv");
		FileWriter fr = null;
		BufferedWriter br = null;
		try {
			fr = new FileWriter(file, true);
			br = new BufferedWriter(fr);
			br.write(date+",\n");
			br.write("Soru,Baslangic Zamani,Bitis Zamani, Saniye, Net,\n");
			br.write((writeInfo(this.infoTable)));
			br.newLine();
			br.write(date+",\n");
			br.write(writeScore(infoTable));
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
	// infotabledaki her bir sorunun çözülme zamanı ve neti gibi ayrıntılı bilgileri csv'ye yazdırmak için uygun hale getirme
	public String writeInfo( ArrayList<ArrayList<String>> infoTable ) {
		if(infoTable == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		for(int i = 0; i<infoTable.get(0).size();i++) {
			for(int j = 0; j<infoTable.size();j++) {
				result.append(infoTable.get(j).get(i)).append(",");
			}
			result.append("\n");
		}
		return result.toString();
	}
	
	// hız doğruluk bilgilerini csv'ye yazdırmak için uygun hale getirme
	public String writeScore( ArrayList<ArrayList<String>> infoTable ) {
		
		if(infoTable == null)
			return null;
		
		StringBuilder result = new StringBuilder();
		double sn_sum = 0;
		int net = 0;
		for(int i = 0; i<infoTable.get(0).size();i++) {
			sn_sum += Double.parseDouble(infoTable.get(3).get(i));
		}
		for(int i = 0; i<infoTable.get(0).size();i++) {
			if(infoTable.get(4).get(i).equals("Dogru")) {
				net ++;
			}
		}
		result.append("Soru Sayisi,"+"Harcanan sure,"+"Dogru Sayisi,"+"Yanlis Sayisi,\n");
		result.append(infoTable.get(0).size()+","+sn_sum+","+net+","+(infoTable.get(0).size()-net)+",\n");
		return result.toString();
	}
	
	
	
	
}
