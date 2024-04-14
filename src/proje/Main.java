package proje;

public class Main {
	
	public static void main(String[] args) {		

		UserDatabase userDatabase = new UserDatabase();
		userDatabase.addUser();
		userDatabase.serializeUserDatabase("src/assets/userdatabase.dat"); 
		
		Pages app = new Pages();
		app.login();
	}
	
}
