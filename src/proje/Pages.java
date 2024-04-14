package proje;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import javax.swing.table.DefaultTableModel;
import java.util.Scanner;
import javax.swing.BorderFactory;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Desktop;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class Pages {
	
	Person user;
	Exercise exercise;
	ExerciseDatabase exerciseDatabase;
	
	public Pages() {
		user = new Person();
		exercise = new Exercise();
		exerciseDatabase = new ExerciseDatabase();
	}

	public void login() {	// login sayfası
		exerciseDatabase.deserializeExerciseDatabase("src/assets/exercisedatabase.dat");
		JFrame jf = new JFrame();
		jf.setTitle("ÇarpımDahisi");
		jf.setSize(800, 500);
		jf.setLocation(400,150);
		
		JPanel mainPanel = new JPanel(new GridLayout(1,2));
	
		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBackground(new Color(255,253,183));
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBackground(Color.white);

		mainPanel.add(panel1);
		mainPanel.add(panel2);
		jf.getContentPane().add(mainPanel);
		
		ImageIcon originalIcon = new ImageIcon("src/assets/carpmaicon.png");
		Image originalImage = originalIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(originalImage.getWidth(null)/6, originalImage.getHeight(null)/6, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
	    JLabel img = new JLabel(resizedIcon);
		img.setBounds(80, 110, 200, 200);
		panel1.add(img);
		
		JLabel appName = new JLabel("ÇarpımDahisi");
		appName.setFont(new Font("Arial", Font.BOLD, 18));
		appName.setSize(200,100);
		appName.setLocation(120, 250);
		panel1.add(appName);
		
		JLabel welcometext = new JLabel("HOŞGELDİNİZ");
		welcometext.setFont(new Font("Arial", Font.BOLD, 20));
		welcometext.setSize(150,100);
		welcometext.setLocation(130, 30);
		panel2.add(welcometext);

		JLabel label1 = new JLabel("Kullanıcı Adı");
		label1.setBounds(50,140,80,25);
		panel2.add(label1);
		
		JTextField text1 = new JTextField(50);
		text1.setBounds(50,165, 300, 30);
		Border underlineBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
		text1.setBorder(underlineBorder);
		panel2.add(text1);	
		
		JLabel label2 = new JLabel("Şifre");
		label2.setBounds(50,220,80,25);
		panel2.add(label2);
		
		JPasswordField text2 = new JPasswordField(20);;
		text2.setBounds(50,245, 300, 30);
		text2.setBorder(underlineBorder);
		panel2.add(text2);
		
		JCheckBox showHideCheckBox = new JCheckBox("Şifreyi göster");
        showHideCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                text2.setEchoChar(checkBox.isSelected() ? '\u0000' : '*');
            }
        });
        
        showHideCheckBox.setBackground(Color.white);
        showHideCheckBox.setBounds(50,280, 300, 30);
		panel2.add(showHideCheckBox);		
		
		JButton button = new JButton("Giriş Yap");
		button.setBounds(150, 340, 100, 35);
		button.setBackground(new Color(174, 244, 164));
		
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String username = text1.getText();
				String password = new String(text2.getPassword());
				
				File file = new File("src/assets/login.txt");
				Scanner inputBuffer = null;
				
				try {
					inputBuffer = new Scanner(file);
					boolean found = false;
					while(inputBuffer.hasNext() && !found) {
						String line = inputBuffer.nextLine();
						String[] values = line.split("\t");
						
						if(values[0].equals(username)) {
							found = true;
							if(!values[1].equals(password) &&!password.equals("")) {
								JOptionPane.showMessageDialog(null,"Hatalı şifre");
							}else if(values[1].equals(password)){
								exercise.exercises();
								user.setUsername(values[0]);
								user.setPassword(values[1]);
								if(user.getUsername().equals("admin")) {
									user.setAdmin(true);
								}else {
									user.setAdmin(false);
								}
								jf.dispose();
								menu();
							}
						}
					}
					if(username.equals("") || password.equals("")) {
						JOptionPane.showMessageDialog(null,"Kullanıcı adı ve şifre boş bırakılamaz.");
					}else if(!found) {
						JOptionPane.showMessageDialog(null,"Kullanıcı bulunamadı.");
					}					
					
				}catch (FileNotFoundException fe) {
					JOptionPane.showMessageDialog(null,"Sistem kullanıcı kayıtlarına ulaşamadı.");
				}finally {
					if (inputBuffer != null) {
				        inputBuffer.close();
				    }
				}
				
			}
		});
		panel2.add(button);
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setResizable(false);		

	}
	
	
	public void menu() { // menu ve loginden sonraki frame

		JFrame jf = new JFrame();
		jf.setTitle("ÇarpımDahisi");
		jf.setSize(800, 500);
		jf.setLocation(400,150);
	
		JPanel panel1 = new JPanel();
		panel1.setLayout(null);
		panel1.setBackground(new Color(255,253,183));
		panel1.setSize(new Dimension(250,500));
		JPanel panel_mainscreen = mainScreen();

		CardLayout cardLayout = new CardLayout();
        Container contentPane = new JPanel(cardLayout);
        contentPane.setBounds(250, 0, 550, 500);	
        contentPane.add(panel_mainscreen,"1");
        jf.getContentPane().add(contentPane);
		jf.add(panel1);
		
		ImageIcon originalIcon = new ImageIcon("src/assets/carpmaicon.png");
		Image originalImage = originalIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(originalImage.getWidth(null)/12, originalImage.getHeight(null)/12, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
	    JLabel img = new JLabel(resizedIcon);
		img.setBounds(80, 0, 100, 100);
		panel1.add(img);
		
		JLabel appName = new JLabel("ÇarpımDahisi");
		appName.setFont(new Font("Arial", Font.BOLD, 10));
		appName.setSize(200,100);
		appName.setLocation(95, 45);
		panel1.add(appName);
		
		JButton mainscreen = new JButton("Ana Ekran");
		mainscreen.setBackground(new Color(236, 242, 255));
		mainscreen.setBounds(25,130,200,50);
		mainscreen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JPanel panel_mainscreen = mainScreen();
            	contentPane.add(panel_mainscreen,"1");
            	cardLayout.show(contentPane, "1");
            }
        });
	
		panel1.add(mainscreen);
		
		JButton button_exercise = new JButton("Alıştırma Yap");
		button_exercise.setBackground(new Color(174, 244, 164));
		button_exercise.setBounds(25,200,200,50);	
		button_exercise.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	JPanel panelExercise = doExerciseStartPanel(cardLayout,contentPane);
        		contentPane.add(panelExercise,"2");
            	cardLayout.show(contentPane, "2");
            }
        });
		panel1.add(button_exercise);
		
		if(user.isAdmin()) {			
			
			JPanel panel_createexercise = createExercisePanel();
			contentPane.add(panel_createexercise,"5");
			JButton button_createexercise = new JButton("Alıştırma Oluştur");
			button_createexercise.setBackground(new Color(121, 184, 209));
			button_createexercise.setBounds(25,270,200,50);
			button_createexercise.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	cardLayout.show(contentPane, "5");
	            }
	        });
			
			JPanel record_panel = recordPanel(cardLayout,contentPane);
			contentPane.add(record_panel,"6");
			JButton button_records = new JButton("Rapor");
			button_records.setBackground(new Color(227, 100, 136));
			button_records.setBounds(25,340,200,50);
			button_records.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	cardLayout.show(contentPane, "6");
	            }
	        });
			
			panel1.add(button_createexercise);
			panel1.add(button_records);			
		}
		
		JButton button_logout = new JButton("Çıkış Yap");
		button_logout.setBackground(new Color(240, 170, 245));
		button_logout.setBounds(70,410,110,35);
		panel1.add(button_logout);
		button_logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	login();
            	jf.dispose();
            }
        });
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setResizable(false);		
	}
	
	public JPanel createExercisePanel() { // alıştırma oluşturma paneli
	
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.white);
		
		JLabel title = new JLabel("ALIŞTIRMA OLUŞTURMA");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		title.setSize(250,100);
		title.setLocation(155, 0);
		panel.add(title);
		
		JLabel label1 = new JLabel("a değeri: ");
		label1.setBounds(180,120,80,25);
		panel.add(label1);
		
		JTextField text1 = new JTextField(50);
		text1.setBounds(250,120, 100, 30);
		Border underlineBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
		text1.setBorder(underlineBorder);
		panel.add(text1);	
		
		JLabel label2 = new JLabel("b değeri: ");
		label2.setBounds(180,190,80,25);
		panel.add(label2);
		
		JTextField text2 = new JTextField(50);
		text2.setBounds(250,190, 100, 30);
		text2.setBorder(underlineBorder);
		panel.add(text2);	
		
		JLabel label3 = new JLabel("N değeri: ");
		label3.setBounds(180,260,80,25);
		panel.add(label3);
		
		JTextField text3 = new JTextField(50);
		text3.setBounds(250,260, 100, 30);
		text3.setBorder(underlineBorder);
		panel.add(text3);	
		
		JButton button = new JButton("Oluştur");
		button.setBounds(230, 340, 100, 35);
		button.setBackground(new Color(147, 118, 224));
		
		button.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {		
				try {
					String a_text = text1.getText();
					int a = Integer.parseInt(a_text);
					String b_text = text2.getText();
					int b = Integer.parseInt(b_text);
					String n_text = text3.getText();
					int n = Integer.parseInt(n_text);
					if(a<1 || a>10 || b<1 || b>10) {
						JOptionPane.showMessageDialog(null,"a ve b için 1-10 arasında bir değer giriniz.");
					}else if(n<0) {
						JOptionPane.showMessageDialog(null,"n için uygun bir değer giriniz.");
					}else {
						exercise.setA(a);
						exercise.setB(b);
						exercise.setN(n);
						exercise.writeFile();
				
						JOptionPane.showMessageDialog(null,"Alıştırmalar başarıyla oluşturuldu.");
					}
				}
				catch (Exception error) {
					JOptionPane.showMessageDialog(null,"Tüm değerleri giriniz.");
				}				
			}
		});
		panel.add(button);		
		
		return panel;
	}
	
	public JPanel doExerciseStartPanel(CardLayout cardLayout, Container contentPane) { // alıştırma yapmada başlangıç paneli
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.white);
		
		JLabel title = new JLabel("ALIŞTIRMA YAPMA");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		title.setSize(250,100);
		title.setLocation(180, 0);
		panel.add(title);
		
		int number = exercise.getN();
		JLabel text = new JLabel();
		if(number == 0) {
			text.setText("Sistemde tanımlanmış soru bulunmamaktadır.");
			text.setBounds(140, 150, 300, 50);
		}else {
			text.setText(number+ " adet alıştırma bulunmaktadır.");			
			text.setBounds(180, 150, 300, 50);
		}
		text.setFont(new Font("Arial", Font.BOLD, 13));
		panel.add(text);		
		
		JButton button = new JButton("Başla");
		button.setBounds(230, 250, 100, 35);
		button.setBackground(new Color(147, 118, 224));			 
		button.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {				
				try {
					if(number != 0) {
						exercise.exercises();
						JPanel exercisePanel = doExercisePanel(cardLayout,contentPane);
						contentPane.add(exercisePanel,"3");
						cardLayout.show(contentPane, "3");
					}					
				}catch(Exception a) {
					a.printStackTrace();
				}				
			}
		});		
		panel.add(button);		
		
		return panel;
	}
	
	public JPanel doExercisePanel(CardLayout cardLayout, Container contentPane) { // alıştırma yapma paneli
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.white);			
		
		JButton button = new JButton("Onayla");
		button.setBounds(230, 350, 100, 35);
		button.setBackground(new Color(147, 118, 224));
		panel.add(button);	
		
		JLabel questions = new JLabel("  "+exercise.getQuestions().get(0).get(0)+" x "+exercise.getQuestions().get(1).get(0)+"  ");
		questions.setFont(new Font("Arial", Font.BOLD, 45));
		questions.setSize(180,100);
		questions.setLocation(180, 130);
		questions.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panel.add(questions);
		
		JLabel text = new JLabel("Cevap: ");
		text.setFont(new Font("Arial", Font.BOLD, 30));
		text.setSize(300,150);
		text.setLocation(150, 200);
		panel.add(text);
		
		JTextField answer = new JTextField(15);
		answer.setSize(100,50);
		answer.setLocation(260, 250);
		Border underlineBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
		answer.setBorder(underlineBorder);
		answer.setFont(new Font("Arial", Font.BOLD, 30));
		panel.add(answer);
		
		user.setAskedQuestions(exercise.getQuestions());
		exercise.allExercises();
		exerciseDatabase.addExercise(exercise);
		exerciseDatabase.serializeExerciseDatabase("src/assets/exercisedatabase.dat");
		exerciseDatabase.deserializeExerciseDatabase("src/assets/exercisedatabase.dat");
		
		JLabel t = new JLabel("SÜRE: ");
		t.setFont(new Font("Arial", Font.BOLD, 30));
		t.setSize(300,120);
		t.setLocation(180, 2);
		panel.add(t);		
		
		JLabel timer = new JLabel("0");
		timer.setFont(new Font("Arial", Font.BOLD, 40));
		timer.setSize(300,120);
		timer.setLocation(290, 0);
		panel.add(timer);
		Timer tm = new Timer(1000, new ActionListener() {
			int i = 1;
			public void actionPerformed(ActionEvent e) {
			   if (i > 60) {
				   int minutes = i / 60; 
				   int seconds = i % 60;
			       timer.setText(String.format("%d:%02d", minutes, seconds));
			       } else {
			    	   timer.setText(Integer.toString(i));
			       }
			       i++;	 
			   }
			});
		tm.start();		
		
		ArrayList<String> startTime = new ArrayList<>();
		ArrayList<String> finishTime = new ArrayList<>();
		ArrayList<String> net = new ArrayList<>();
		ArrayList<String> time = new ArrayList<>();
		
		Calendar calendar = Calendar.getInstance();	
		LocalTime currentTime = LocalTime.now();
		String result = currentTime.toString();
		
		startTime.add(calendar.getTime().toString());
		startTime.add(result);
		
		button.addActionListener(new ActionListener() {		
			int tr = 0, f= 0;
			int a,b,i = 1;
			
			public void actionPerformed(ActionEvent e) {				
				try {
					String c = answer.getText();
					int cevap = Integer.parseInt(c);
					
					LocalTime currentTime2 = LocalTime.now();
					String r = currentTime2.toString();
					finishTime.add(r);
					time.add(timer.getText());
					if(cevap == exercise.getQuestions().get(2).get(i-1)) {
						net.add("Dogru");
						tr++;
					}else {
						net.add("Yanlis");
						f++;
					}
					if(i<exercise.getN()) {						
						startTime.add(r);
						answer.setText("");
						a = exercise.getQuestions().get(0).get(i);
						b = exercise.getQuestions().get(1).get(i);
						questions.setText("  "+a + " x " +b+"  ");
						i++;						
					}
					else {
						tm.stop();
						user.setTimeandNet(startTime, finishTime,time, net);
				
						JPanel score_Panel = scorePanel(tr, f, timer.getText());
						contentPane.add(score_Panel,"4");
						cardLayout.show(contentPane, "4");
					}											
				}catch (Exception fe) {
					JOptionPane.showMessageDialog(null,"Geçerli bir değer giriniz.");
				}
		}});
		
		return panel;
	}
	
	public JPanel recordPanel(CardLayout cardLayout, Container contentPane) { // rapor paneli
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.white);
		JLabel title = new JLabel("RAPORLAR");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		title.setSize(250,100);
		title.setLocation(200, 0);
		panel.add(title);
		
		File file = new File("src/assets/login.txt");
		Scanner inputBuffer = null;
		ArrayList<String> usernames = new ArrayList<>();
		try {
			inputBuffer = new Scanner(file);
			boolean found = false;
			while(inputBuffer.hasNext() && !found) {
				String line = inputBuffer.nextLine();
				String[] values = line.split("\t");
				usernames.add(values[0]);					
			}				
		}catch (FileNotFoundException fe) {
			JOptionPane.showMessageDialog(null,"Sistem kullanıcı kayıtlarına ulaşamadı.");
		}finally {
			if (inputBuffer != null) {
		        inputBuffer.close();
		    }
		}
		
        int y = 100;
        for (int i = 1; i<usernames.size(); i++) {
            JButton button = new JButton(usernames.get(i));
            button.setBounds(205,y,100,50); 
            button.setBackground(new Color(223, 255, 216));
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {                	
                	JPanel reportPanel = reportPanel(button.getText());
					contentPane.add(reportPanel,"10");
					cardLayout.show(contentPane, "10");                	
                }
            });
            panel.add(button); 
            y +=70;
        }
;
		return panel;
	}
	
	
	public JPanel mainScreen() { // ana ekran paneli
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.white);
		
		JLabel title2 = new JLabel("ÇarpımDahisi");
		title2.setFont(new Font("Arial", Font.BOLD, 20));
		title2.setSize(250,100);
		title2.setLocation(200, 0);
		panel.add(title2);
		
		JLabel title = new JLabel("MERHABA !");
		title.setFont(new Font("Arial", Font.BOLD, 16));
		title.setSize(250,100);
		title.setLocation(50, 50);
		panel.add(title);
		
		JLabel text = new JLabel("ÇarpımDahisi uygulaması size matematik becerilerinizi");
		text.setBounds(50, 120, 500, 50);
		text.setFont(new Font("Arial", Font.PLAIN, 15));
		panel.add(text);
		
		JLabel text2 = new JLabel("geliştirme fırsatı sunuyor.");
		text2.setBounds(50, 140, 500, 50);
		text2.setFont(new Font("Arial", Font.PLAIN, 15));
		panel.add(text2);
		
		JLabel text3 = new JLabel("Hazır mısınız? Başlayalım!");
		text3.setBounds(50, 190, 500, 50);
		text3.setFont(new Font("Arial", Font.BOLD, 15));
		panel.add(text3);
		
		JLabel text4 = new JLabel("SKOR TABLOSU");
		text4.setBounds(200, 230, 500, 50);
		text4.setFont(new Font("Arial", Font.BOLD, 15));
		panel.add(text4);
		
		JTable table = scoreTable(user.getUsername());		
		JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);            
		scrollPane.setBounds(70,280,400,120);	
		
		return panel;
	}
	
	public JPanel scorePanel(int t, int f, String time) { // alıştırma yaptıktan sonra çıkan skor paneli
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.white);
		JLabel title = new JLabel("SKOR");
		title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setSize(250,100);
		title.setLocation(220, 0);
		panel.add(title);
		
		JLabel text = new JLabel("Soru sayısı: " +exercise.getN());
		text.setFont(new Font("Arial", Font.BOLD, 20));
		text.setBounds(205,150,300,100);
		JLabel text2 = new JLabel("Doğru Sayısı: "+t);
		text2.setFont(new Font("Arial", Font.BOLD, 20));
		text2.setBounds(200,210,300,100);
		JLabel text4 = new JLabel("Yanlış Sayısı: "+f);
		text4.setFont(new Font("Arial", Font.BOLD, 20));
		text4.setBounds(200,270,300,100);
		JLabel text3 = new JLabel("Süre: "+time+" saniye");
		text3.setFont(new Font("Arial", Font.BOLD, 20));
		text3.setBounds(202,90,300,100);
		
		panel.add(text);
		panel.add(text2);
		panel.add(text3);
		panel.add(text4);
		
		
		return panel;
	}
	
	
public JPanel reportPanel(String username) { // her bir çocuğun raporlarının gösterildiği panel
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.white);
		JLabel title = new JLabel("RAPOR - "+username);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		title.setSize(250,100);
		title.setLocation(180, 0);
		panel.add(title);

		JTable table = scoreTable(username);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		scrollPane.setBounds(50,100,425,250);

        panel.add(scrollPane);
        
        JButton button = new JButton("Detaylı İnceleme");
        button.setBounds(180,370,150,50);
        button.setBackground(new Color(223, 255, 216));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	String filePath = "src/assets/"+username+".csv"; 
                File file = new File(filePath);

                try {
                    Desktop.getDesktop().open(file);
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(null,"İlgili dosya bulunamadı.");
                }
            }
        });
        panel.add(button);
        
		
		
		return panel;
	}
	
	
	public JTable scoreTable(String username) {		// alıştırma sonuçlarının tutulduğu tablo
		
		String path = "src/assets/"+username+".csv";
        String searchCriteria = "Soru Sayisi";
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> numberOfQuestions = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> trueAnswers = new ArrayList<>();
        ArrayList<String> falseAnswers = new ArrayList<>();
        String[] columnNames = {"Tarih","Soru Sayısı", "Harcanan Süre", "Doğru Sayısı","Yanlış Sayısı"};        
        
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            String prevLine = "";
            while (scanner.hasNextLine()) {      
                String line = scanner.nextLine();                            
                if (line.contains(searchCriteria)) {                	
                	String nextLine = scanner.nextLine();
                	String[] values = nextLine.split(",");
                	String[] values2 = prevLine.split(",");
                	date.add(values2[0]);
                	numberOfQuestions.add(values[0]);    
                	time.add(values[1]);  
                	trueAnswers.add(values[2]);  
                	falseAnswers.add(values[3]);  
                }
                prevLine = line;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        DefaultTableModel model = new DefaultTableModel(columnNames,0);
        for (int i = 0; i<time.size();i++) {
            model.addRow(new Object[]{date.get(i),numberOfQuestions.get(i), time.get(i),trueAnswers.get(i),falseAnswers.get(i)});
        }
        
        JTable table = new JTable(model);
        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setBackground(new Color(255, 215, 154));
		
		return table;
	}
}
