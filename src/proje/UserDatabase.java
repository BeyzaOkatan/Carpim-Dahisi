package proje;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;


import javax.swing.JOptionPane;

public class UserDatabase implements Serializable {
	
	
	static final long serialVersionUID = 2L;
	private ArrayList<Person> users;
	 
	public UserDatabase() {
		users = new ArrayList<>();
	}
	
	
	public ArrayList<Person> getUsers() {
		return users;
	}

	public void addUser() {
	
		File file = new File("src/assets/login.txt");
		Scanner inputBuffer = null;
		
		try {
			inputBuffer = new Scanner(file);
			while(inputBuffer.hasNext()) {
				Person user = new Person();
				String line = inputBuffer.nextLine();
				String[] values = line.split("\t");	
				user.setUsername(values[0]);
				user.setPassword(values[1]);
				if(values[0].equals("admin")) {
					user.setAdmin(true);					
				}
				users.add(user);
				
			}
		}catch (Exception fe) {
			JOptionPane.showMessageDialog(null,"Dosya okuma hatası");
		}finally {
			if (inputBuffer != null) {
		        inputBuffer.close();
		    }
		}
		
    }
	
	public void serializeUserDatabase(String filePath) {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
	    	oos.writeObject(users.size());
	    	for(int i = 0; i< users.size(); i++) {
	    		oos.writeObject(users.get(i));
	    	}	        
	        System.out.println("UserDatabase serialized and saved to file: " + filePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deserializeUserDatabase(String filePath) {
	    
	    try {
	    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath)) ;
	    	Integer userCount = (Integer)ois.readObject();
	    	for(int i = 0; i<userCount; i++) {
	    		Person user = (Person) ois.readObject();
	    		System.out.println(user);
	    	}
	        System.out.println("UserDatabase deserialized from file: " + filePath);
	        ois.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}	
	
}
