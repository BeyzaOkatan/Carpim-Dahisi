package proje;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ExerciseDatabase implements Serializable {
	
	static final long serialVersionUID = 4L;
	private ArrayList<Exercise> exercises;
	
	public ExerciseDatabase() {
		exercises = new ArrayList<>();
	}
	
	public void addExercise(Exercise e) {		
		exercises.add(e);		
    }
	
	public void deserializeExerciseDatabase(String filePath) {
	    
	    try {
	    	if(exercises != null) {
	    		exercises.clear();
	    	}
	    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath)) ;
	    	Integer exerciseCount = (Integer)ois.readObject();
	    	for(int i = 0; i<exerciseCount; i++) {
	    		Exercise exercise = (Exercise) ois.readObject();
	    		exercises.add(exercise);
	    	}
	        System.out.println("UserDatabase deserialized from file: " + filePath);
	        ois.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}
	public void serializeExerciseDatabase(String filePath) {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
	    	oos.writeObject(exercises.size());
	    	for(int i = 0; i< exercises.size(); i++) {
	    		oos.writeObject(exercises.get(i));
	    	}	        
	        System.out.println("UserDatabase serialized and saved to file: " + filePath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	

}
