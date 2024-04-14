package test;
import proje.Person;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class PersonTest {

    @Test
    public void testGetUsername() {
        Person person = new Person();
        person.setUsername("beyza");
        String result = person.getUsername();
        assertEquals("beyza", result);
    }

    @Test
    public void testIsAdmin() {
        Person person = new Person();
        boolean result = person.isAdmin();
        assertFalse(result);
    }

    @Test
    public void testSetAdmin() {
        Person person = new Person();
        person.setAdmin(true);
        boolean result = person.isAdmin();
        assertTrue(result);
    }
    
    @Test
    public void testSetAskedQuestions() {

        Person person = new Person();

        ArrayList<ArrayList<Integer>> questions = new ArrayList<>();
        ArrayList<Integer> question1 = new ArrayList<>();
        ArrayList<Integer> question2 = new ArrayList<>();
        question1.add(2);
        question1.add(3);
        question1.add(4);
        question2.add(5);
        question2.add(6);
        question2.add(7);
        questions.add(question1);
        questions.add(question2);

        person.setAskedQuestions(questions);

        assertNotNull(person.getInfoTable());
        assertEquals(5, person.getInfoTable().size());

        ArrayList<String> firstRow = person.getInfoTable().get(0);
        assertEquals("2 x 5", firstRow.get(0));
        assertEquals("3 x 6", firstRow.get(1));
        assertEquals("4 x 7", firstRow.get(2));
    }
    
    @Test
    public void testWriteInfo() {

        Person person = new Person();

        ArrayList<ArrayList<String>> infoTable = new ArrayList<>();
        ArrayList<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("2");
        row1.add("3");
        ArrayList<String> row2 = new ArrayList<>();
        row2.add("4");
        row2.add("5");
        row2.add("6");
        infoTable.add(row1);
        infoTable.add(row2);

        String result = person.writeInfo(infoTable);
        String expected = "1,4,\n2,5,\n3,6,\n";
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testWriteInfoNullTable() {    	
        Person person = new Person();
        ArrayList<ArrayList<String>> infoTable = null;
        String result = person.writeInfo(infoTable);
        assertNull(result);
    }
    
    
    @Test
    public void testWriteInfoEmptyRows() {
    	
        ArrayList<ArrayList<String>> infoTable = new ArrayList<>();
        infoTable.add(new ArrayList<>());
        infoTable.add(new ArrayList<>());
        Person person = new Person();
        String result = person.writeInfo(infoTable);
        assertEquals("", result);
    }
    
    @Test
    public void testWriteInfoBlankValues() {
    	
        ArrayList<ArrayList<String>> infoTable = new ArrayList<>();
        ArrayList<String> row1 = new ArrayList<>();
        row1.add("1");
        row1.add("");
        row1.add("1");
        infoTable.add(row1);
        ArrayList<String> row2 = new ArrayList<>();
        row2.add("");
        row2.add("2");
        row2.add("");
        infoTable.add(row2);

        Person person = new Person();
        String result = person.writeInfo(infoTable);

        String expected = "1,,\n,2,\n1,,\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testWriteScore() {

        ArrayList<ArrayList<String>> infoTable = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {			
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                row.add(""); 
            }
            infoTable.add(row); 
        }

        ArrayList<String> row1 = new ArrayList<>();        
        row1.add("4");
        row1.add("3");
        row1.add("2");
        row1.add("1");
        infoTable.add(row1);
        
        ArrayList<String> row2 = new ArrayList<>();
        row2.add("Dogru");
        row2.add("Yanlis");
        row2.add("Dogru");
        row2.add("Dogru");
        infoTable.add(row2);

        Person person = new Person();
        String result = person.writeScore(infoTable);

        String expected = "Soru Sayisi,Harcanan sure,Dogru Sayisi,Yanlis Sayisi,\n4,10.0,3,1,\n";
        assertEquals(expected, result);
    }
    
    @Test
    public void testWriteScoreNullTable() {
        ArrayList<ArrayList<String>> infoTable = null;
        Person person = new Person();
        String expected = null;
        String result = person.writeScore(infoTable);
        assertEquals(expected, result);
    }
    
    
    
}




