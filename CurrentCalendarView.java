import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CurrentCalendarView extends JFrame 
{
	final GregorianCalendar cal = new GregorianCalendar();
	LocalDate today = LocalDate.now();
	LocalDate viewDate = LocalDate.now();
	CalendarModel calModel;
	ArrayList<Event> events;
	static int setter = 1;
	public CurrentCalendarView(CalendarModel c)
	{
		calModel = c;
		
		//setting the layout for the view and adding the today, left, and right arrow buttons
		setLayout(new BorderLayout());
		JButton todayButton = new JButton("Today ");
		
	
		JButton leftArrow = new JButton("<");
		JButton rightArrow = new JButton(">");
		
		//setting up the north panel
		JPanel northPanel = new JPanel();
		
		northPanel.add(todayButton);
		northPanel.add(leftArrow);
		northPanel.add(rightArrow);
		northPanel.setLayout(new FlowLayout());
		add(northPanel, BorderLayout.NORTH);
		
		//setting up the center panel with the Create button
		JButton create = new JButton("CREATE");
		create.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						JFrame createButtonFrame = new JFrame();
						createButtonFrame.setLayout(new GridLayout(6,2));
						
						//Components are added to datePanel
						JPanel datePanel = new JPanel();
						JLabel label_day = new JLabel("            Enter event Day(#):");
						final int Event = 12;
						JTextField eventDayTextField = new JTextField(Event);
						datePanel.add(label_day);
						datePanel.add(eventDayTextField);
						
						JPanel MonthPanel = new JPanel();
						JLabel label_Month = new JLabel("Enter event Month (1-12):");
						final int Events = 12;
						JTextField eventMonthTextField = new JTextField(Events);
						MonthPanel.add(label_Month);
						MonthPanel.add(eventMonthTextField);
						
						//Components are added to eventNamePanel
						JPanel eventNamePanel = new JPanel();
						JLabel eventNameLabel = new JLabel("             Enter event Name:");
						JTextField eventNameTextField = new JTextField(12);
						eventNamePanel.add(eventNameLabel);
						eventNamePanel.add(eventNameTextField);
						
						//Components are added to yearPanel
						JPanel yearPanel = new JPanel();
						JLabel yearLabel = new JLabel("               Enter event Year:");
						final int YEAR = 12;
						JTextField yearTextField = new JTextField(YEAR);
						yearPanel.add(yearLabel);
						yearPanel.add(yearTextField);
						
						//Components are added to the hourPanel
						JPanel hourPanel = new JPanel();
						JLabel startTimeLabel = new JLabel("Enter Start Time:");
						final int START = 2;
						JTextField startHourTextField = new JTextField(START);						
						JLabel endTimeLabel = new JLabel("Enter End Time:");
						final int END = 2;
						hourPanel.add(startTimeLabel);
						hourPanel.add(startHourTextField);
						JTextField endHourTextField = new JTextField(END);
						hourPanel.add(endTimeLabel);
						hourPanel.add(endHourTextField);
						
						//JButton with action Listener
						JButton create = new JButton("Create Event");
						create.addActionListener(new
								ActionListener()
								{
									public void actionPerformed(ActionEvent e)
									{
										//model.update("Test");
										int counter = 0;
										System.out.println(yearTextField.getText().length());
										if(yearTextField.getText().length() != 4 || yearTextField.getText().length() == 0)
										{
											yearTextField.setText("Invalid Year");
											counter++;
										}
										if(startHourTextField.getText().length() > 2 || startHourTextField.getText().length() == 0)
										{
											startHourTextField.setText("N/A");
											counter++;
										}
										if(endHourTextField.getText().length() > 2 || endHourTextField.getText().length() == 0)
										{

											endHourTextField.setText("N/A");
											counter++;
										}
										System.out.println(eventMonthTextField.getText().length());
										if(eventMonthTextField.getText().length() == 0 ||eventMonthTextField.getText().length() > 2 )
										{
											eventMonthTextField.setText("Invalid Month");
											counter++;
										}
										if(eventNameTextField.getText().length() == 0)
										{
											eventNameTextField.setText("Please enter event");
											counter++;
										}
										if(eventDayTextField.getText().length() > 2 ||eventDayTextField.getText().length()==0)
										{
											eventDayTextField.setText("Please follow format");
											counter++;
										}
										if(counter > 0)
										{
											//System.out.println(counter);
											return;									
										}
										else
										{
											
											//model.update("TE");//new Event("S", 1, 2, 3, 4, 5));
											String event = eventNameTextField.getText();
											String year = yearTextField.getText();
											String startHour = startHourTextField.getText();
											String endHour = endHourTextField.getText();
											String eventDay = eventDayTextField.getText();
											String eventMonth = eventMonthTextField.getText();
											//model.update(new Event(event, year, eventDay, null, startHour, endHour));
											calModel.update(new Event(event, year, eventDay, eventMonth, null, startHour, endHour, null));

										}
									}
								});
						//Add panel to the frame
						createButtonFrame.add(eventNamePanel);
						createButtonFrame.add(MonthPanel);
						createButtonFrame.add(datePanel);					
						createButtonFrame.add(yearPanel);
						createButtonFrame.add(hourPanel);
						//Add button to the frame
						createButtonFrame.add(create);
						
						createButtonFrame.setLocation(300, 0);				
						createButtonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						createButtonFrame.pack();
						createButtonFrame.setVisible(true);					
					}
				});
		
		JPanel centerPanel = new JPanel();
		centerPanel.add(create);
		centerPanel.setLayout(new FlowLayout());
		add(centerPanel, BorderLayout.CENTER);
		
		//setting up the south panel for the month, year, days of the week
		JPanel southPanel = new JPanel();
		
		southPanel.setLayout(new BorderLayout());
		JPanel month = new JPanel();
		month.setLayout(new FlowLayout());
		
		JTextField theMonth = new JTextField(today.getMonth().toString(), 7);
		
		String years = Integer.toString(today.getYear());
		JTextField theYear = new JTextField(years, 4);
		
		theMonth.setEditable(false);
		theYear.setEditable(false);
		month.add(theMonth);
		month.add(theYear);
		
		JButton monthLeftArrow = new JButton("<");
		JButton monthRightArrow = new JButton(">");
		
		JPanel theDays = new JPanel();
		theDays.setLayout(new BorderLayout());
		JPanel weekdays = new JPanel();
		weekdays.setLayout(new GridLayout(0,7));
		
		String week = "SMTWTFS";
		
		for(int i = 0; i < 7; i++) {
			JTextField days = new JTextField(week.substring(i, i+1));
			weekdays.add(days);
			days.setEditable(false);			
		}
		
		theDays.add(weekdays, BorderLayout.NORTH);
		
		JPanel days = new JPanel();
		days.setLayout(new GridLayout(0,7));
		
		LocalDate firstDay = LocalDate.of(today.getYear(), today.getMonth(), 1);
		int currentDay = today.getDayOfMonth();
		int daysInMonth = today.getMonth().maxLength();
		ArrayList<JTextField> f = new ArrayList<JTextField>();
		int count = 0;

		if(firstDay.getDayOfWeek().getValue() != 7) 
		{
			for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++)
			{
				JTextField numbers = new JTextField(null);
				numbers.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e){
						boolean enable = false;
						String text = numbers.getText();
						Color color = numbers.getForeground();
						System.out.println(color.toString());
						if((text.contains("1") || text.contains("2") || text.contains("3") || text.contains("4") || text.contains("5") || text.contains("6") || text.contains("7") || text.contains("8") || text.contains("9") || text.contains("0"))
								&& color != Color.GRAY)
						{
							enable = true;
						}
						if(enable == true)
						{
							//Gets the current day of month
							int start = today.getDayOfMonth();
							//Get day of week value
							LocalDate firstDayOfViewDate = LocalDate.of(viewDate.getYear(), viewDate.getMonth(), 1);
							int blankDays = firstDayOfViewDate.getDayOfWeek().getValue();
							//If week value equals to seven set blank days to zero
							if(blankDays == 7)
							{
								blankDays = 0;
							}
							//F: start + blankDays - 1
							int F = start + blankDays - 1;
							String dayOfMonth = numbers.getText();
							int dayOfTheMonth = Integer.parseInt(dayOfMonth);
							
							System.out.println("F = start + blankDays - 1 is " + F + " = " + start + " + " + blankDays + " - 1");
							System.out.println("dayOfTheMonth is " + dayOfTheMonth);
							
							int difference = dayOfTheMonth - start;
							System.out.println("difference is " + difference);
							
							int startingMonth = today.getMonthValue();
							int endingMonth = viewDate.getMonthValue();
							int monthDifference = endingMonth - startingMonth;
							System.out.println("monthDifference is " + monthDifference);
							
							if(monthDifference == 0)
							{
								if(difference > 0)
								{
									today = today.plusDays(difference);
									SelectedView.changeDay(today);
									f.get(F).setForeground(Color.BLACK);
									
								}
								else if(difference < 0)
								{
									today = today.minusDays(Math.abs(difference));
									SelectedView.changeDay(today);
									f.get(F).setForeground(Color.BLACK);
									
								}
								else
								{
									System.out.println("Already there.");
								}
								
								numbers.setForeground(Color.RED);
							}
							else if(monthDifference > 0)
							{
								if(monthDifference == 1)
								{
									difference = today.lengthOfMonth() - start + dayOfTheMonth;
									System.out.println("new difference is " + difference);
									
									today = today.plusDays(difference);
									SelectedView.changeDay(today);
									f.get(F).setForeground(Color.BLACK);
									
									numbers.setForeground(Color.RED);
								}
							}
							else if(monthDifference < 0)
							{
								if(Math.abs(monthDifference) == 1)
								{
									difference = viewDate.lengthOfMonth() - dayOfTheMonth + start;
									System.out.println("new difference is " + difference);
									
									today = today.minusDays(difference);
									SelectedView.changeDay(today);
									f.get(F).setForeground(Color.BLACK);
									
									numbers.setForeground(Color.RED);
								}
							}
							else
							{
								System.out.println("How did you get here?");
							}
						}
					}	
				});
				days.add(numbers);
				f.add(numbers);
				numbers.setEditable(false);
				count++;
			}
		}
		
		for(int day = 1; day <= daysInMonth; day++) 
		{
			JTextField numbers = new JTextField();
			numbers.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					boolean enable = false;
					String text = numbers.getText();
					Color color = numbers.getForeground();
					System.out.println(color.toString());
					if((text.contains("1") || text.contains("2") || text.contains("3") || text.contains("4") || text.contains("5") || text.contains("6") || text.contains("7") || text.contains("8") || text.contains("9") || text.contains("0"))
							&& color != Color.GRAY)
					{
						enable = true;
					}
					if(enable == true)
					{
						//Gets the current day of month
						int start = today.getDayOfMonth();
						//Get day of week value
						LocalDate firstDayOfViewDate = LocalDate.of(viewDate.getYear(), viewDate.getMonth(), 1);
						int blankDays = firstDayOfViewDate.getDayOfWeek().getValue();
						//If week value equals to seven set blank days to zero
						if(blankDays == 7)
						{
							blankDays = 0;
						}
						//F: start + blankDays - 1
						int F = start + blankDays - 1;
						String dayOfMonth = numbers.getText();
						int dayOfTheMonth = Integer.parseInt(dayOfMonth);
						
						System.out.println("F = start + blankDays - 1 is " + F + " = " + start + " + " + blankDays + " - 1");
						System.out.println("dayOfTheMonth is " + dayOfTheMonth);
						
						int difference = dayOfTheMonth - start;
						System.out.println("difference is " + difference);
						
						int startingMonth = today.getMonthValue();
						int endingMonth = viewDate.getMonthValue();
						int monthDifference = endingMonth - startingMonth;
						System.out.println("monthDifference is " + monthDifference);
						
						if(monthDifference == 0)
						{
							if(difference > 0)
							{
								today = today.plusDays(difference);
								SelectedView.changeDay(today);
								f.get(F).setForeground(Color.BLACK);
								
							}
							else if(difference < 0)
							{
								today = today.minusDays(Math.abs(difference));
								SelectedView.changeDay(today);
								f.get(F).setForeground(Color.BLACK);
								
							}
							else
							{
								System.out.println("Already there.");
							}
							
							numbers.setForeground(Color.RED);
						}
						else if(monthDifference > 0)
						{
							if(monthDifference == 1)
							{
								difference = today.lengthOfMonth() - start + dayOfTheMonth;
								System.out.println("new difference is " + difference);
								
								today = today.plusDays(difference);
								SelectedView.changeDay(today);
								f.get(F).setForeground(Color.BLACK);
								
								numbers.setForeground(Color.RED);
							}
						}
						else if(monthDifference < 0)
						{
							if(Math.abs(monthDifference) == 1)
							{
								difference = viewDate.lengthOfMonth() - dayOfTheMonth + start;
								System.out.println("new difference is " + difference);
								
								today = today.minusDays(difference);
								SelectedView.changeDay(today);
								f.get(F).setForeground(Color.BLACK);
								
								numbers.setForeground(Color.RED);
							}
						}
						else
						{
							System.out.println("How did you get here?");
						}
					}
				}	
			});
			if(day == currentDay) {
				numbers.setForeground(Color.RED);
			}
			numbers.setText("" + day);
			days.add(numbers);
			f.add(numbers);
			numbers.setEditable(false);
			count++;
		}
		//  //
		
		LocalDate lastDay = LocalDate.of(today.getYear(), today.getMonth(), firstDay.lengthOfMonth()); //last day of the current month
		
		for(int i = 0; i <= 12 - lastDay.getDayOfWeek().getValue(); i++)
		{
			JTextField numbers = new JTextField();
			numbers.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e){
					boolean enable = false;
					String text = numbers.getText();
					Color color = numbers.getForeground();
					System.out.println(color.toString());
					if((text.contains("1") || text.contains("2") || text.contains("3") || text.contains("4") || text.contains("5") || text.contains("6") || text.contains("7") || text.contains("8") || text.contains("9") || text.contains("0"))
							&& color != Color.GRAY)
					{
						enable = true;
					}
					if(enable == true)
					{
						//Gets the current day of month
						int start = today.getDayOfMonth();
						//Get day of week value
						LocalDate firstDayOfViewDate = LocalDate.of(viewDate.getYear(), viewDate.getMonth(), 1);
						int blankDays = firstDayOfViewDate.getDayOfWeek().getValue();
						//If week value equals to seven set blank days to zero
						if(blankDays == 7)
						{
							blankDays = 0;
						}
						//F: start + blankDays - 1
						int F = start + blankDays - 1;
						String dayOfMonth = numbers.getText();
						int dayOfTheMonth = Integer.parseInt(dayOfMonth);
						
						System.out.println("F = start + blankDays - 1 is " + F + " = " + start + " + " + blankDays + " - 1");
						System.out.println("dayOfTheMonth is " + dayOfTheMonth);
						
						int difference = dayOfTheMonth - start;
						System.out.println("difference is " + difference);
						
						int startingMonth = today.getMonthValue();
						int endingMonth = viewDate.getMonthValue();
						int monthDifference = endingMonth - startingMonth;
						System.out.println("monthDifference is " + monthDifference);
						
						if(monthDifference == 0)
						{
							if(difference > 0)
							{
								today = today.plusDays(difference);
								SelectedView.changeDay(today);
								f.get(F).setForeground(Color.BLACK);
								
							}
							else if(difference < 0)
							{
								today = today.minusDays(Math.abs(difference));
								SelectedView.changeDay(today);
								f.get(F).setForeground(Color.BLACK);
								
							}
							else
							{
								System.out.println("Already there.");
							}
							
							numbers.setForeground(Color.RED);
						}
						else if(monthDifference > 0)
						{
							if(monthDifference == 1)
							{
								difference = today.lengthOfMonth() - start + dayOfTheMonth;
								System.out.println("new difference is " + difference);
								
								today = today.plusDays(difference);
								SelectedView.changeDay(today);
								f.get(F).setForeground(Color.BLACK);
								
								numbers.setForeground(Color.RED);
							}
						}
						else if(monthDifference < 0)
						{
							if(Math.abs(monthDifference) == 1)
							{
								difference = viewDate.lengthOfMonth() - dayOfTheMonth + start;
								System.out.println("new difference is " + difference);
								
								today = today.minusDays(difference);
								SelectedView.changeDay(today);
								f.get(F).setForeground(Color.BLACK);
								
								numbers.setForeground(Color.RED);
							}
						}
						else
						{
							System.out.println("How did you get here?");
						}
					}
				}	
			});
			numbers.setForeground(Color.GRAY);
			numbers.setText("" + (i + 1));
			days.add(numbers);
			f.add(numbers);
			numbers.setEditable(false);
			count++;
		}
		
		//  //
		
		theDays.add(days, BorderLayout.SOUTH);
		////////////////////////////////////////////////////////////////////////////////////////
		leftArrow.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						SelectedView.changeDay(today);
						if(setter ==  1)
						{
							today = today.minusDays(1);	
							SelectedView.changeDay(today);
												
						}
						if(setter == 2)
						{
							int current = today.getMonthValue();
							today = today.minusDays(7);
							SelectedView.changeDay(today);
							if(today.getMonthValue() != current)
							{
								theMonth.setText(today.getMonth().toString());
								theYear.setText(Integer.toString(today.getYear()));								
								LocalDate firstDay = LocalDate.of(today.getYear(), today.getMonth(), 1);								
								int daysInMonth = today.getMonth().maxLength();								
								int count = 0;								
								if(firstDay.getDayOfWeek().getValue() != 7) {
									for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++) {
										f.get(count).setText(null);
										count++;
									}
								}								
								for(int day = 1; day <= daysInMonth; day++) {
									if(!today.equals(LocalDate.now()))
										f.get(count).setForeground(Color.BLACK);
									else if(today.equals(LocalDate.now()) && day == currentDay)
										f.get(count).setForeground(Color.RED);
									f.get(count).setText("" + day);
									count++;
								}
								LocalDate lastDay = LocalDate.of(today.getYear(), today.getMonth(), firstDay.lengthOfMonth()); //last day of the current month
								
								for(int i = 0; i <= 12 - lastDay.getDayOfWeek().getValue(); i++)
								{
									if(count <= 41)
									{
										f.get(count).setForeground(Color.GRAY);
										f.get(count).setText("" + (i+1));
										count++;
									}
									
								}
							}
						//If the weekly view changes month, then the view from the initial calendar changes as well					
						}
						if(setter == 3)
						{
							today = today.minusMonths(1);
							SelectedView.changeDay(today);		
							
							theMonth.setText(today.getMonth().toString());
							theYear.setText(Integer.toString(today.getYear()));							
							LocalDate firstDay = LocalDate.of(today.getYear(), today.getMonth(), 1);						
							int daysInMonth = today.getMonth().maxLength();							
							int count = 0;							
							if(firstDay.getDayOfWeek().getValue() != 7) {
								for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++) {
									f.get(count).setText(null);
									count++;
								}
							}							
							for(int day = 1; day <= daysInMonth; day++) {
								if(!today.equals(LocalDate.now()))
									f.get(count).setForeground(Color.black);
								else if(today.equals(LocalDate.now()) && day == currentDay)
									f.get(count).setForeground(Color.red);
								f.get(count).setText("" + day);
								count++;
							}							
							//  //							
							LocalDate lastDay = LocalDate.of(today.getYear(), today.getMonth(), firstDay.lengthOfMonth()); //last day of the current month							
							for(int i = 0; i <= 12 - lastDay.getDayOfWeek().getValue(); i++)
							{
								if(count <= 41)
								{
									f.get(count).setForeground(Color.GRAY);
									f.get(count).setText("" + (i+1));
									count++;
								}					
							}	
						}
						
						//CURRENT_CALENDAR_VIEW
						int counter = 0;
						if(today.getDayOfMonth() == today.lengthOfMonth())
						{
							//Sets the label to current month and year 
							theMonth.setText(today.getMonth().toString());
							theYear.setText(Integer.toString(today.getYear()));	
							//Gets the first day of month
							LocalDate firstDay = LocalDate.of(today.getYear(), today.getMonth(), 1);
							int daysInMonth = today.getMonth().maxLength();
							//Counter set to zero 
							int count = 0;
							//Set the layout of the first week of new month
							if(firstDay.getDayOfWeek().getValue() != 7) 
							{
								for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++) 
								{
									f.get(count).setText(null);
									count++;
								}
							}		
							//Set the number of days
							for(int day = 1; day <= daysInMonth; day++)
							{
								f.get(count).setText("" + day);
								count++;
							}												
							LocalDate lastDay = LocalDate.of(today.getYear(), today.getMonth(), firstDay.lengthOfMonth()); //last day of the current month
							//Previews the months
							for(int i = 0; i <= 12 - lastDay.getDayOfWeek().getValue(); i++)
							{
								if(count <= 41)
								{
									f.get(count).setForeground(Color.GRAY);
									f.get(count).setText("" + (i+1));
									count++;
								}						
							}
						}	
						int numberOfTextFieldsBeforeFirstDay = 0;
						while(counter < LocalDate.of(today.getYear(), today.getMonth(), 1).getDayOfWeek().getValue())
						{
							counter++;	
							numberOfTextFieldsBeforeFirstDay++;
						}	
						//If first day of week starts on Sunday then this code will run
						if(counter == 7)
						{
							counter = 0;
							numberOfTextFieldsBeforeFirstDay = 0;
						}
						
						int dayNumbers = 1;
						while(dayNumbers < today.getDayOfMonth())
						{
							f.get(counter).setText("" + dayNumbers);
							f.get(counter).setForeground(Color.BLACK);
							dayNumbers++;
							counter++;
							
						}
						f.get(counter).setForeground(Color.RED);
						counter++;						
						while(counter < today.lengthOfMonth() + numberOfTextFieldsBeforeFirstDay) 
						{
							f.get(counter).setForeground(Color.BLACK);
							counter++;
						}
					}
				});
		rightArrow.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						SelectedView.changeDay(today);
						if(setter ==  1)
						{
							today = today.plusDays(1);	
							SelectedView.changeDay(today);
												
						}
						if(setter == 2)
						{
							int current = today.getMonthValue();
							today = today.plusDays(7);
							SelectedView.changeDay(today);
							if(today.getMonthValue() != current)
							{
								theMonth.setText(today.getMonth().toString());
								theYear.setText(Integer.toString(today.getYear()));								
								LocalDate firstDay = LocalDate.of(today.getYear(), today.getMonth(), 1);								
								int daysInMonth = today.getMonth().maxLength();								
								int count = 0;								
								if(firstDay.getDayOfWeek().getValue() != 7) {
									for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++) {
										f.get(count).setText(null);
										count++;
									}
								}								
								for(int day = 1; day <= daysInMonth; day++) {
									if(!today.equals(LocalDate.now()))
										f.get(count).setForeground(Color.BLACK);
									else if(today.equals(LocalDate.now()) && day == currentDay)
										f.get(count).setForeground(Color.RED);
									f.get(count).setText("" + day);
									count++;
								}
								LocalDate lastDay = LocalDate.of(today.getYear(), today.getMonth(), firstDay.lengthOfMonth()); //last day of the current month
								
								for(int i = 0; i <= 12 - lastDay.getDayOfWeek().getValue(); i++)
								{
									if(count <= 41)
									{
										f.get(count).setForeground(Color.GRAY);
										f.get(count).setText("" + (i+1));
										count++;
									}
									
								}
							}
						//If the weekly view changes month, then the view from the initial calendar changes as well					
						}
						if(setter == 3)
						{
							today = today.plusMonths(1);
							SelectedView.changeDay(today);		
							
							theMonth.setText(today.getMonth().toString());
							theYear.setText(Integer.toString(today.getYear()));							
							LocalDate firstDay = LocalDate.of(today.getYear(), today.getMonth(), 1);						
							int daysInMonth = today.getMonth().maxLength();							
							int count = 0;							
							if(firstDay.getDayOfWeek().getValue() != 7) {
								for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++) {
									f.get(count).setText(null);
									count++;
								}
							}							
							for(int day = 1; day <= daysInMonth; day++) {
								if(!today.equals(LocalDate.now()))
									f.get(count).setForeground(Color.BLACK);
								else if(today.equals(LocalDate.now()) && day == currentDay)
									f.get(count).setForeground(Color.RED);
								f.get(count).setText("" + day);
								count++;
							}							
							//  //							
							LocalDate lastDay = LocalDate.of(today.getYear(), today.getMonth(), firstDay.lengthOfMonth()); //last day of the current month							
							for(int i = 0; i <= 12 - lastDay.getDayOfWeek().getValue(); i++)
							{
								if(count <= 41)
								{
									f.get(count).setForeground(Color.GRAY);
									f.get(count).setText("" + (i+1));
									count++;
								}					
							}	
						}
						int counter = 0;
						if(today.getDayOfMonth() == 1)
						{
							theMonth.setText(today.getMonth().toString());
							theYear.setText(Integer.toString(today.getYear()));						
							LocalDate firstDay = LocalDate.of(today.getYear(), today.getMonth(), 1);
							int daysInMonth = today.getMonth().maxLength();
							//Counter set to zero 
							int count = 0;
							//sets the layout of the first week of new month
							if(firstDay.getDayOfWeek().getValue() != 7) 
							{
								for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++) 
								{
									f.get(count).setText(null);
									count++;
								}
							}		
							//Sets the number of days
							for(int day = 1; day <= daysInMonth; day++)
							{
								f.get(count).setText("" + day);
								count++;
							}												
							LocalDate lastDay = LocalDate.of(today.getYear(), today.getMonth(), firstDay.lengthOfMonth()); //last day of the current month
							//Previews the months
							for(int i = 0; i <= 12 - lastDay.getDayOfWeek().getValue(); i++)
							{
								if(count <= 41)
								{
									f.get(count).setForeground(Color.GRAY);
									f.get(count).setText("" + (i+1));
									count++;
								}						
							}
							}						
							//count == 0	
							int numberOfTextFieldsBeforeFirstDay = 0;
							while(counter < LocalDate.of(today.getYear(), today.getMonth(), 1).getDayOfWeek().getValue())
							{
								counter++;	
								numberOfTextFieldsBeforeFirstDay++;
							}	
							//If first day of week starts on Sunday then this code will run
							if(counter == 7)
							{
								counter = 0;
								numberOfTextFieldsBeforeFirstDay = 0;
							}
							int dayNumbers = 1;
							while(dayNumbers < today.getDayOfMonth())
							{
								f.get(counter).setText("" + dayNumbers);
								f.get(counter).setForeground(Color.BLACK);
								dayNumbers++;
								counter++;
								
							}
							f.get(counter).setForeground(Color.RED);
							counter++;						
							while(counter < today.lengthOfMonth() + numberOfTextFieldsBeforeFirstDay) 
							{
								f.get(counter).setForeground(Color.BLACK);
								counter++;
							}
						
					}				
								
						
					
				});
		/////
		
		monthLeftArrow.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						viewDate = viewDate.minusMonths(1);
						System.out.println(viewDate);
						System.out.println(today);
						theMonth.setText(viewDate.getMonth().toString());
						theYear.setText(Integer.toString(viewDate.getYear()));
						
						LocalDate firstDay = LocalDate.of(viewDate.getYear(), viewDate.getMonth(), 1);
						
						int daysInMonth = viewDate.getMonth().maxLength();
						
						int count = 0;
						
						if(firstDay.getDayOfWeek().getValue() != 7)
						{
							for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++) {
								f.get(count).setText(null);
								count++;
							}
						}
						
						for(int day = 1; day <= daysInMonth; day++) {
							if(!viewDate.equals(LocalDate.now()))
								f.get(count).setForeground(Color.BLACK);
							else if(viewDate.equals(LocalDate.now()) && day == currentDay)
								f.get(count).setForeground(Color.RED);
							f.get(count).setText("" + day);
							count++;
						}	
						
						//  //
						
						LocalDate lastDay = LocalDate.of(viewDate.getYear(), viewDate.getMonth(), firstDay.lengthOfMonth()); //last day of the current month
						
						for(int i = 0; i <= 12 - lastDay.getDayOfWeek().getValue(); i++)
						{
							if(count <= 41)
							{
								f.get(count).setForeground(Color.GRAY);
								f.get(count).setText("" + (i+1));
								count++;
							}
						}
						
						//  //
						
						
					}
				});
		
		monthRightArrow.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						viewDate = viewDate.plusMonths(1);
						System.out.println(viewDate);
						System.out.println(today);
						theMonth.setText(viewDate.getMonth().toString());
						theYear.setText(Integer.toString(viewDate.getYear()));
						
						LocalDate firstDay = LocalDate.of(viewDate.getYear(), viewDate.getMonth(), 1);
						
						int daysInMonth = viewDate.getMonth().maxLength();
						
						int count = 0;
						
						if(firstDay.getDayOfWeek().getValue() != 7) {
							for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++) {
								f.get(count).setText(null);
								count++;
							}
						}
						
						for(int day = 1; day <= daysInMonth; day++) {
							if(!viewDate.equals(LocalDate.now()))
								f.get(count).setForeground(Color.black);
							else if(viewDate.equals(LocalDate.now()) && day == currentDay)
								f.get(count).setForeground(Color.red);
							f.get(count).setText("" + day);
							count++;
						}
						
						//  //
						
						LocalDate lastDay = LocalDate.of(viewDate.getYear(), viewDate.getMonth(), firstDay.lengthOfMonth()); //last day of the current month
						
						for(int i = 0; i <= 12 - lastDay.getDayOfWeek().getValue(); i++)
						{
							if(count <= 41)
							{
								f.get(count).setForeground(Color.GRAY);
								f.get(count).setText("" + (i+1));
								count++;
							}
							
						}
						
						//  //
						
						
					}
				});
		todayButton.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						LocalDate todayF = LocalDate.now();
						today = LocalDate.now();
						SelectedView.changeDay(today);
						
						theMonth.setText(todayF.getMonth().toString());
						theYear.setText(Integer.toString(todayF.getYear()));
						
						LocalDate firstDay = LocalDate.of(todayF.getYear(), todayF.getMonth(), 1);
						
						int daysInMonth = todayF.getMonth().maxLength();
						
						int count = 0;
						
						if(firstDay.getDayOfWeek().getValue() != 7)
						{
							for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++)
							{
								f.get(count).setText(null);
								count++;
							}
						}					
						for(int day = 1; day <= daysInMonth; day++)
						{
							if(day != currentDay)
							{
								f.get(count).setForeground(Color.BLACK);
							}
							else if( day == currentDay)
							{
								f.get(count).setForeground(Color.RED);
							}
							f.get(count).setText("" + day);
							count++;
						}
						LocalDate lastDay = LocalDate.of(todayF.getYear(), todayF.getMonth(), firstDay.lengthOfMonth()); //last day of the current month
						
						for(int i = 0; i <= 12 - lastDay.getDayOfWeek().getValue(); i++)
						{
							if(count <= 41)
							{
								f.get(count).setForeground(Color.GRAY);
								f.get(count).setText("" + (i+1));
								count++;
							}
							
						}
					}
				});
		month.add(monthLeftArrow);
		month.add(monthRightArrow);
		southPanel.add(theDays, BorderLayout.SOUTH);		
		southPanel.add(month, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	/*
	 * 
	 */
	public static void currentMonth()
	{
		setter = 3;
		System.out.println(setter);
	}
	/*
	 * 
	 */
	public static void currentWeek()
	{
		setter = 2;
		System.out.println(setter);
	}
	/*
	 * 
	 */
	public static void currentDay()
	{
		setter = 1;
		System.out.println(setter);
	}
}
	