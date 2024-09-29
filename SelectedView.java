import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The SelectedView displays the frame that holds all of the events
 * loaded from the text file the user wishes to upload
 * It has a day, week, month, and agenda button to show the views
 * as well as a from file button to upload the selected file
 * @author Team Summer: Tawni Pizzagoni, Adrian Chan, David Truong
 * 07/29/19
 */

public class SelectedView extends JFrame 
{
	CalendarModel calModel;
	ArrayList<Event> events;
	static LocalDate currentDay = LocalDate.now();
	static int currentView = 1; //1 for day view, 2 for week view, 3 for month view, 4 for agenda view
	static JButton day = new JButton("Day");
	static JButton week = new JButton("Week");
	static JButton month = new JButton("Month");
	static JButton agenda = new JButton("Agenda");
	ArrayList<JTextField> dayView =  new ArrayList<JTextField>();
	ArrayList<JTextField> f = new ArrayList<JTextField>();
	JPanel centerPanel = new JPanel();
	JPanel southPanel = new JPanel();
	ArrayList<JTextField> hours = new ArrayList<JTextField>(24);
	ArrayList<JTextField> designCol = new ArrayList<JTextField>(24);
	ArrayList<JTextField> firstColEvents = new ArrayList<JTextField>(24);
	ArrayList<JTextField> secondColEvents = new ArrayList<JTextField>(24);
	ArrayList<JTextField> thirdColEvents = new ArrayList<JTextField>(24);
	ArrayList<JTextField> fourthColEvents = new ArrayList<JTextField>(24);
	ArrayList<JTextField> fifthColEvents = new ArrayList<JTextField>(24);
	ArrayList<JTextField> sixthColEvents = new ArrayList<JTextField>(24);
	ArrayList<JTextField> seventhColEvents = new ArrayList<JTextField>(24);
	JTextField monthTextField = new JTextField(10);
	boolean numberOfDaysInBetween = true;
	public SelectedView(CalendarModel c) {
		calModel = c;
		events = new ArrayList<Event>();
		
		//initial view is the Day view
		setLocation(300, 0);
		this.setPreferredSize(new Dimension(675, 420));
		setLayout(new BorderLayout());
		JPanel northPanel = new JPanel();
		
		JPanel hourPanel = new JPanel();
		JPanel eventPanel = new JPanel();
		ArrayList<JTextField> hours = new ArrayList<JTextField>(24);
		
		JTextField date = new JTextField();
		date.setEditable(false);
		
		date.setText(currentDay.getDayOfWeek().toString() + " " + currentDay.getDayOfMonth());
		centerPanel.add(date);
		add(centerPanel, BorderLayout.CENTER);
		
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		hourPanel.setLayout(new BoxLayout(hourPanel, BoxLayout.Y_AXIS));
		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		
		//created an ArrayList of the hours that are shared by the day, week, and agenda view
		JTextField firstHour = new JTextField("12 AM", 6);
		firstHour.setEditable(false);
		firstHour.setMaximumSize(new Dimension(80,20));
		hourPanel.add(firstHour);
		hours.add(firstHour);
		for(int i = 1; i < 12; i++) {
			JTextField hour = new JTextField(i + " AM", 6);
			hour.setEditable(false);
			hour.setMaximumSize(new Dimension(80,20));
			hours.add(hour);
			hourPanel.add(hour);
		}
		JTextField noon = new JTextField("12 PM", 6);
		noon.setEditable(false);
		noon.setMaximumSize(new Dimension(80, 20));
		hours.add(noon);
		hourPanel.add(noon);
		for(int i = 1; i < 12; i++) {
			JTextField hour = new JTextField(i + " PM",6);
			hour.setEditable(false);
			hour.setMaximumSize(new Dimension(80,20));
			hours.add(hour);
			hourPanel.add(hour);
		}
		//
		ArrayList<JTextField> jEvents = new ArrayList<>(24);
		for(int i = 0; i < 24; i++) {
			JTextField jEvent = new JTextField("");
			jEvent.setEditable(false);
			jEvents.add(jEvent);
			eventPanel.add(jEvent);
		}
		//
		southPanel.add(hourPanel);
		southPanel.add(eventPanel);
		JScrollPane sp = new JScrollPane(southPanel);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setPreferredSize(new Dimension(50, 300));
		add(sp, BorderLayout.SOUTH);
		
		//setting up the view for when the Day button is pressed
		day.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						centerPanel.removeAll();
						southPanel.removeAll();
						
						for(int i = 0; i < jEvents.size(); i++){
							jEvents.get(i).setText("");
							jEvents.get(i).setBackground(null);
						}
						
						date.setText(currentDay.getDayOfWeek().toString() + " " + currentDay.getDayOfMonth());
						centerPanel.setLayout(new FlowLayout());
						centerPanel.add(date);
						
						centerPanel.revalidate();
						centerPanel.repaint();
						
						southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
						//System.out.println(events.size());
						//System.out.println("J" + jEvents.size());
						Event todaysEvent;
						for(int i = 0; i < events.size(); i++) 
						{
							if(events.get(i).startMonth() == currentDay.getMonthValue() && events.get(i).date().equals(currentDay)) {
								todaysEvent = events.get(i);
								for(int j = 0; j < jEvents.size(); j++) {
									if(todaysEvent.startHour() == j) {
										int end = todaysEvent.endHour();
										String timeOfDay = "AM";
										if(end > 12) {
											end = todaysEvent.endHour() - 12;
											timeOfDay = "PM";
										}
										jEvents.get(j).setText(todaysEvent.eventName() + ": " + todaysEvent.startHour() + " - " + end + " " + timeOfDay);
										jEvents.get(j).setBackground(Color.BLUE);
										if(todaysEvent.startHour() < todaysEvent.endHour()) {
											while(j+1 <= todaysEvent.endHour()) {
												jEvents.get(j+1).setBackground(Color.BLUE);
												j++;
											}
										}
									}
								}
							}
						}
						
						for(int i = 0; i < 24; i++) {
							hourPanel.add(hours.get(i));
							eventPanel.add(jEvents.get(i));
						}
						
						currentView = 1;
						southPanel.add(hourPanel);
						southPanel.add(eventPanel);
						
						southPanel.revalidate();
						southPanel.repaint();
					}
				});
		
		//setting up the week view when the week button is pressed
		week.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						centerPanel.removeAll();
						southPanel.removeAll();
						
						centerPanel.setLayout(new GridLayout(2,1));
						
						JPanel firstRow = new JPanel();
						JPanel secondRow = new JPanel();
						firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.X_AXIS));
						secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));
						
						String weekDays = "SUNMONTUEWEDTHUFRISAT";
						int first = 0;
						int second = 3;
						JTextField firstBlank = new JTextField("", 9);
						firstBlank.setEditable(false);
						firstRow.add(firstBlank);
						firstBlank.setMaximumSize(new Dimension(83,20));
						for(int i = 0; i < 7; i++) {
							JTextField week = new JTextField(weekDays.substring(first, second), 9);
							week.setEditable(false);
							week.setMaximumSize(new Dimension(83,20));
							firstRow.add(week);
							first = second;
							second = second + 3;
						}
						
						JTextField secondBlank = new JTextField("", 9);
						secondBlank.setEditable(false);
						secondBlank.setMaximumSize(new Dimension(82,20));
						secondRow.add(secondBlank);
						
						ArrayList<JTextField> weekdays = new ArrayList<JTextField>();
						for(int i = 0; i < 7; i++) {
							JTextField j = new JTextField("", 9);
							j.setMaximumSize(new Dimension(85,20));
							j.setEditable(false);
							weekdays.add(j);
						}
						
						int today = currentDay.getDayOfMonth();
						int daysInLastMonth = currentDay.minusMonths(1).lengthOfMonth();
						
						//if the current day falls on a Sunday
						if(currentDay.getDayOfWeek().getValue() == 7) {
							for(int i = 0; i < 7; i++) {
								if(today > currentDay.lengthOfMonth())
									today = 1;
								weekdays.get(i).setText("" + today);
								today++;
							}
						}
						
						//if the current day falls on a Monday
						else if(currentDay.getDayOfWeek().getValue() == 1) {
							if(today == 1)
								weekdays.get(0).setText("" + daysInLastMonth);
							else
								weekdays.get(0).setText("" + (today - 1));
							for(int i = 1; i < 7; i++) {
								if(today > currentDay.lengthOfMonth())
									today = 1;
								weekdays.get(i).setText("" + today);
								today++;
							}
						}
						
						//if the current day falls on a Tuesday
						else if(currentDay.getDayOfWeek().getValue() == 2) {
							if(today == 1) {
								weekdays.get(0).setText("" + (daysInLastMonth - 1));
								weekdays.get(1).setText("" + (daysInLastMonth));
							}
							else if(today == 2) {
								weekdays.get(0).setText("" + (daysInLastMonth));
								weekdays.get(1).setText("" + (today - 1));
							}
							else {
								weekdays.get(0).setText("" + (today - 2));
								weekdays.get(1).setText("" + (today - 1));
							}
							for(int i = 2; i < 7; i++) {
								if(today > currentDay.lengthOfMonth())
									today = 1;
								weekdays.get(i).setText("" + today);
								today++;
							}
						}
						
						//if the current day falls on a Wednesday
						else if(currentDay.getDayOfWeek().getValue() == 3) {
							if(today == 1) {
								weekdays.get(0).setText("" + (daysInLastMonth - 2));
								weekdays.get(1).setText("" + (daysInLastMonth - 1));
								weekdays.get(2).setText("" + daysInLastMonth);
							}
							else if(today == 2) {
								weekdays.get(0).setText("" + (daysInLastMonth - 1));
								weekdays.get(1).setText("" + (daysInLastMonth));
								weekdays.get(2).setText("" + (today - 1));
							}
							else if(today == 3) {
								weekdays.get(0).setText("" + (daysInLastMonth));
								weekdays.get(1).setText("" + (today - 2));
								weekdays.get(2).setText("" + (today - 1));
							}
							else {
								weekdays.get(0).setText("" + (today - 3));
								weekdays.get(1).setText("" + (today - 2));
								weekdays.get(2).setText("" + (today - 1));
							}
							for(int i = 3; i < 7; i++) {
								if(today > currentDay.lengthOfMonth())
									today = 1;
								weekdays.get(i).setText("" + today);
								today++;
							}
						}
						
						//if the current day falls on a Thursday
						else if(currentDay.getDayOfWeek().getValue() == 4) {
							if(today == 1) {
								weekdays.get(0).setText("" + (daysInLastMonth - 3));
								weekdays.get(1).setText("" + (daysInLastMonth - 2));
								weekdays.get(2).setText("" + (daysInLastMonth - 1));
								weekdays.get(3).setText("" + (daysInLastMonth));
							}
							else if(today == 2) {
								weekdays.get(0).setText("" + (daysInLastMonth - 2));
								weekdays.get(1).setText("" + (daysInLastMonth - 1));
								weekdays.get(2).setText("" + daysInLastMonth);
								weekdays.get(3).setText("" + (today - 1));
							}
							else if(today == 3) {
								weekdays.get(0).setText("" + (daysInLastMonth - 1));
								weekdays.get(1).setText("" + daysInLastMonth);
								weekdays.get(2).setText("" + (today - 2));
								weekdays.get(3).setText("" + (today - 1));
							}
							else if(today == 4) {
								weekdays.get(0).setText("" + (daysInLastMonth));
								weekdays.get(1).setText("" + (today - 3));
								weekdays.get(2).setText("" + (today - 2));
								weekdays.get(3).setText("" + (today - 1));
							}
							else {
								weekdays.get(0).setText("" + (today - 4));
								weekdays.get(1).setText("" + (today - 3));
								weekdays.get(2).setText("" + (today - 2));
								weekdays.get(3).setText("" + (today - 1));
							}
							for(int i = 4; i < 7; i++) {
								if(today > currentDay.lengthOfMonth())
									today = 1;
								weekdays.get(i).setText("" + today);
								today++;
							}
						}
						
						//if the current day falls on a Friday
						else if(currentDay.getDayOfWeek().getValue() == 5) {
							if(today == 1) {
								weekdays.get(0).setText("" + (daysInLastMonth - 4));
								weekdays.get(1).setText("" + (daysInLastMonth - 3));
								weekdays.get(2).setText("" + (daysInLastMonth - 2));
								weekdays.get(3).setText("" + (daysInLastMonth - 1));
								weekdays.get(4).setText("" + (daysInLastMonth));
							}
							else if(today == 2) {
								weekdays.get(0).setText("" + (daysInLastMonth - 3));
								weekdays.get(1).setText("" + (daysInLastMonth - 2));
								weekdays.get(2).setText("" + (daysInLastMonth - 1));
								weekdays.get(3).setText("" + (daysInLastMonth));
								weekdays.get(4).setText("" +  (today - 1));
							}
							else if(today == 3) {
								weekdays.get(0).setText("" + (daysInLastMonth - 2));
								weekdays.get(1).setText("" + (daysInLastMonth - 1));
								weekdays.get(2).setText("" + daysInLastMonth);
								weekdays.get(3).setText("" + (today - 2));
								weekdays.get(4).setText("" + (today - 1));
							}
							else if(today == 4) {
								weekdays.get(0).setText("" + (daysInLastMonth - 1));
								weekdays.get(1).setText("" + (daysInLastMonth));
								weekdays.get(2).setText("" + (today - 3));
								weekdays.get(3).setText("" + (today - 2));
								weekdays.get(4).setText("" + (today - 1));
							}
							else if (today == 5) {
								weekdays.get(0).setText("" + (daysInLastMonth));
								weekdays.get(1).setText("" + (today - 4));
								weekdays.get(2).setText("" + (today - 3));
								weekdays.get(3).setText("" + (today - 2));
								weekdays.get(4).setText("" + (today - 1));
							}
							else {
								weekdays.get(0).setText("" + (today - 5));
								weekdays.get(1).setText("" + (today - 4));
								weekdays.get(2).setText("" + (today - 3));
								weekdays.get(3).setText("" + (today - 2));
								weekdays.get(4).setText("" + (today - 1));
							}
							for(int i = 5; i < 7; i++) {
								if(today > currentDay.lengthOfMonth())
									today = 1;
								weekdays.get(i).setText("" + today);
								today++;
							}
						}
						
						//if the current day falls on a Saturday
						else {
							if(today == 1) {
								weekdays.get(0).setText("" + (daysInLastMonth - 5));
								weekdays.get(1).setText("" + (daysInLastMonth - 4));
								weekdays.get(2).setText("" + (daysInLastMonth - 3));
								weekdays.get(3).setText("" + (daysInLastMonth - 2));
								weekdays.get(4).setText("" + (daysInLastMonth - 1));
								weekdays.get(5).setText("" + (daysInLastMonth));
							}
							else if(today == 2) {
								weekdays.get(0).setText("" + (daysInLastMonth - 4));
								weekdays.get(1).setText("" + (daysInLastMonth - 3));
								weekdays.get(2).setText("" + (daysInLastMonth - 2));
								weekdays.get(3).setText("" + (daysInLastMonth - 1));
								weekdays.get(4).setText("" +  (daysInLastMonth));
								weekdays.get(5).setText("" + (today - 1));
							}
							else if(today == 3) {
								weekdays.get(0).setText("" + (daysInLastMonth - 3));
								weekdays.get(1).setText("" + (daysInLastMonth - 2));
								weekdays.get(2).setText("" + (daysInLastMonth - 1));
								weekdays.get(3).setText("" + (daysInLastMonth));
								weekdays.get(4).setText("" +  (today - 2));
								weekdays.get(5).setText("" + (today - 1));
							}
							else if(today == 4) {
								weekdays.get(0).setText("" + (daysInLastMonth - 2));
								weekdays.get(1).setText("" + (daysInLastMonth - 1));
								weekdays.get(2).setText("" + (daysInLastMonth));
								weekdays.get(3).setText("" + (today - 3));
								weekdays.get(4).setText("" +  (today - 2));
								weekdays.get(5).setText("" + (today - 1));
							}
							else if (today == 5) {
								weekdays.get(0).setText("" + (daysInLastMonth - 1));
								weekdays.get(1).setText("" + (daysInLastMonth));
								weekdays.get(2).setText("" + (today - 4));
								weekdays.get(3).setText("" + (today - 3));
								weekdays.get(4).setText("" +  (today - 2));
								weekdays.get(5).setText("" + (today - 1));
							}
							else if (today == 6) {
								weekdays.get(0).setText("" + (daysInLastMonth));
								weekdays.get(1).setText("" + (today - 5));
								weekdays.get(2).setText("" + (today - 4));
								weekdays.get(3).setText("" + (today - 3));
								weekdays.get(4).setText("" +  (today - 2));
								weekdays.get(5).setText("" + (today - 1));
							}
							else {
								weekdays.get(0).setText("" + (today - 6));
								weekdays.get(1).setText("" + (today - 5));
								weekdays.get(2).setText("" + (today - 4));
								weekdays.get(3).setText("" + (today - 3));
								weekdays.get(4).setText("" + (today - 2));
								weekdays.get(5).setText("" + (today - 1));
							}
							weekdays.get(6).setText("" + today);
						}

						for(int i = 0; i < weekdays.size(); i++) {
							secondRow.add(weekdays.get(i));
						}
						
						centerPanel.add(firstRow);
						centerPanel.add(secondRow);
						centerPanel.revalidate();
						centerPanel.repaint();
						add(centerPanel,BorderLayout.CENTER);
						
						southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
						
						for(int i = 0; i < 24; i++) {
							hourPanel.add(hours.get(i));
						}
						
						southPanel.add(hourPanel);
						
						JPanel firstCol = new JPanel();
						firstCol.setLayout(new BoxLayout(firstCol, BoxLayout.Y_AXIS));
						JPanel secondCol = new JPanel();
						secondCol.setLayout(new BoxLayout(secondCol, BoxLayout.Y_AXIS));
						JPanel thirdCol = new JPanel();
						thirdCol.setLayout(new BoxLayout(thirdCol, BoxLayout.Y_AXIS));
						JPanel fourthCol = new JPanel();
						fourthCol.setLayout(new BoxLayout(fourthCol, BoxLayout.Y_AXIS));
						JPanel fifthCol = new JPanel();
						fifthCol.setLayout(new BoxLayout(fifthCol, BoxLayout.Y_AXIS));
						JPanel sixthCol = new JPanel();
						sixthCol.setLayout(new BoxLayout(sixthCol, BoxLayout.Y_AXIS));
						JPanel seventhCol = new JPanel();
						seventhCol.setLayout(new BoxLayout(seventhCol, BoxLayout.Y_AXIS));
						
						
				
						
						for(int i = 0; i < 24; i++) {
							JTextField jField = new JTextField("", 7);
							jField.setMaximumSize(new Dimension(82,20));
							jField.setEditable(false);
							firstColEvents.add(jField);
							firstCol.add(jField);
						}
						for(int i = 0; i < 24; i++) {
							JTextField jField = new JTextField("", 7);
							jField.setMaximumSize(new Dimension(82,20));
							jField.setEditable(false);
							secondColEvents.add(jField);
							secondCol.add(jField);
						}
						for(int i = 0; i < 24; i++) {
							JTextField jField = new JTextField("", 7);
							jField.setMaximumSize(new Dimension(82,20));
							jField.setEditable(false);
							thirdColEvents.add(jField);
							thirdCol.add(jField);
						}
						for(int i = 0; i < 24; i++) {
							JTextField jField = new JTextField("", 7);
							jField.setMaximumSize(new Dimension(82,20));
							jField.setEditable(false);
							fourthColEvents.add(jField);
							fourthCol.add(jField);
						}
						for(int i = 0; i < 24; i++) {
							JTextField jField = new JTextField("", 7);
							jField.setMaximumSize(new Dimension(82,20));
							jField.setEditable(false);
							fifthColEvents.add(jField);
							fifthCol.add(jField);
						}
						for(int i = 0; i < 24; i++) {
							JTextField jField = new JTextField("", 7);
							jField.setMaximumSize(new Dimension(82,20));
							jField.setEditable(false);
							sixthColEvents.add(jField);
							sixthCol.add(jField);
						}
						for(int i = 0; i < 24; i++) {
							JTextField jField = new JTextField("", 7);
							jField.setMaximumSize(new Dimension(82,20));
							jField.setEditable(false);
							seventhColEvents.add(jField);
							seventhCol.add(jField);
						}

						currentView = 2;
						southPanel.add(seventhCol);
						southPanel.add(firstCol);				
						southPanel.add(secondCol);
						southPanel.add(thirdCol);
						southPanel.add(fourthCol);
						southPanel.add(fifthCol);
						southPanel.add(sixthCol);
						
						sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						getWeeklyEvents();
						southPanel.revalidate();
						southPanel.repaint();
					}
				});
		
		//setting up the month view when the month button is pressed
		month.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				centerPanel.removeAll();
				southPanel.removeAll();
				
				JTextField monthTextField = new JTextField(currentDay.getMonth().toString() + " " + currentDay.getYear());
				monthTextField.setEditable(false);
				centerPanel.add(monthTextField);
				centerPanel.setLayout(new FlowLayout());
				centerPanel.revalidate();
				centerPanel.repaint();
				
				southPanel.setLayout(new GridLayout(7,6));

				String weekDays = "SUNMONTUEWEDTHUFRISAT";
				int first = 0;
				int second = 3;
				for(int i = 0; i < 7; i++) {
					JTextField week = new JTextField(weekDays.substring(first, second));
					week.setEditable(false);
					southPanel.add(week);
					first = second;
					second = second + 3;
				}
				
				LocalDate firstDay = LocalDate.of(currentDay.getYear(), currentDay.getMonth(), 1);
				
				final int cd = currentDay.getDayOfMonth();
				int daysInMonth = currentDay.getMonth().maxLength();
				
				int count = 0;

				if(firstDay.getDayOfWeek().getValue() != 7) {
					for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++) {
						JTextField numbers = new JTextField(null);
						southPanel.add(numbers);
						f.add(numbers);
						numbers.setEditable(false);
						count++;
					}
				}
				
				for(int day = 1; day <= daysInMonth; day++) {
					JTextField numbers = new JTextField();
					if(day == cd) {
						numbers.setForeground(Color.red);
					}
					numbers.setText("" + day);
					southPanel.add(numbers);
					f.add(numbers);
					numbers.setEditable(false);
					count++;
				}
				for(int i = count; i < 38; i++) {
					JTextField numbers = new JTextField(null);
					southPanel.add(numbers);
					f.add(numbers);
					numbers.setEditable(false);
				}
				for(int i = 0; i < f.size(); i++)
				{
					String calendarDisplay = null;
					int counterdd = 0;
					String textFieldDayOfMonth = f.get(i).getText();
					for(int j=0; j < events.size(); j++)
					{
						int dayOfMonth = calModel.getData().get(j).date().getDayOfMonth();
						String dayOfMonthString = Integer.toString(dayOfMonth);
						System.out.println("MODEL" + calModel.getData().size());
						if(dayOfMonthString.equals(textFieldDayOfMonth) && calModel.getData().get(j).date().getMonthValue() == currentDay.getMonthValue())
						{
							if(counterdd == 0)
							{										
								calendarDisplay = f.get(i).getText() + " " + calModel.getData().get(j).eventName();
								f.get(i).setText(calendarDisplay);
							}
							counterdd++;
						}
						if(counterdd >= 2)
						{
							f.get(i).setText(calendarDisplay + "+" +(counterdd - 1)  + "..");
						}
					}
				}
				currentView = 3;
				southPanel.revalidate();
				southPanel.repaint();
			}
			
		});
		
		//setting up the agenda view when the agenda button is pressed
		agenda.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						JFrame agendaFrame = new JFrame();
						agendaFrame.setLayout(new BorderLayout());
						JTextField enterPeriod = new JTextField("Please enter a time period");
						enterPeriod.setEditable(false);
						JTextField start = new JTextField("Start date: ", 6);
						start.setEditable(false);
						JTextField end = new JTextField("End date: ", 6);
						end.setEditable(false);
						JTextField startMonth = new JTextField("", 6);
						JTextField endMonth = new JTextField("", 6);
						JTextField startYear = new JTextField("", 6);
						JTextField endYear = new JTextField("", 6);
						String startM = startMonth.getText();
						String endM = endMonth.getText();
						String startY = startYear.getText();
						String endY = endYear.getText();
						JPanel dates = new JPanel();
						dates.setLayout(new BorderLayout());
						
						agendaFrame.add(enterPeriod, BorderLayout.NORTH);
						JPanel beginning = new JPanel();
						beginning.add(start);
						beginning.add(startMonth);
						beginning.add(startYear);
						JPanel ending = new JPanel();
						ending.add(end);
						ending.add(endMonth);
						ending.add(endYear);
						dates.add(beginning, BorderLayout.NORTH);
						dates.add(ending, BorderLayout.SOUTH);
						agendaFrame.add(dates, BorderLayout.CENTER);
						JButton enter = new JButton("Enter");
						enter.addActionListener(new 
								ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										agendaFrame.dispose();
										centerPanel.removeAll();
										southPanel.removeAll();
										LocalDate before = LocalDate.of(Integer.parseInt(startY), Integer.parseInt(startM), 1);
										LocalDate after = LocalDate.of(Integer.parseInt(startY), Integer.parseInt(endM), 1);
										JTextField period = new JTextField(startMonth.getText() + " " + startYear.getText() + " - "
												+ endMonth.getText() + " " + endYear.getText());
										period.setEditable(false);
										centerPanel.add(period);
										for(int i = 0; i < calModel.getData().size(); i++)
										{
											if(calModel.getData().get(i).date().isAfter(before))
											{
												JTextField event = new JTextField(calModel.getData().get(i).eventName());
												southPanel.add(event);
											}
										}
										{
											JTextField empty = new JTextField("Nothing to show");
											empty.setEditable(false);
											southPanel.add(empty);
										}
										centerPanel.revalidate();
										centerPanel.repaint();
										southPanel.revalidate();
										southPanel.repaint();
									}
						});

						currentView = 4;
						agendaFrame.add(enter, BorderLayout.SOUTH);
						agendaFrame.setLocation(300, 0);				
						agendaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						agendaFrame.pack();
						agendaFrame.setVisible(true);
					}
			
				});
		
		//Reading the file when the From File button is pressed
		JButton file = new JButton("From File");
		file.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						centerPanel.removeAll();
						southPanel.removeAll();
						JFileChooser j = new JFileChooser();
						int r = j.showSaveDialog(null);
						String l = null;
						if(r == JFileChooser.APPROVE_OPTION) {
							l = j.getSelectedFile().getAbsolutePath();
						}
						JTextField fileRead = new JTextField();
						fileRead.setEditable(false);
						try {
							BufferedReader in = new BufferedReader(new FileReader(l));
							String theEvent = null;
							String eventName = null;
							String startTime = null;
							String endTime = null;
							String weekdays = null;
							while((theEvent = in.readLine()) != null){
								int i = 0;
								while(theEvent.charAt(i) != ';') {
									i++;
								}
								eventName = theEvent.substring(0,i);
								String year = theEvent.substring(i+1,i+5);
								String startMonth;
								if(theEvent.charAt(i+7) == ';') {
									startMonth = theEvent.substring(i+6,i+7);
									i = i+8;
								}
								else {
									startMonth = theEvent.substring(i+6,i+8);
									i = i+9;
								}
								String endMonth;
								int k = 0;
								if(theEvent.charAt(i+1) == ';') {
									endMonth = theEvent.substring(i,i+1);
									i = i+2;
									k = i;
								}
								else {
									endMonth = theEvent.substring(i, i+2);
									i = i+3;
									k = i;
								}
								
								while(theEvent.charAt(k) != ';') {
									k++;
								}
								weekdays = theEvent.substring(i,k);
								ArrayList<DayOfWeek> dow = new ArrayList<>();
								if(weekdays.contains("S"))
									dow.add(DayOfWeek.SUNDAY);
								if(weekdays.contains("M"))
									dow.add(DayOfWeek.MONDAY);
								if(weekdays.contains("T"))
									dow.add(DayOfWeek.TUESDAY);
								if(weekdays.contains("W"))
									dow.add(DayOfWeek.WEDNESDAY);
								if(weekdays.contains("R"))
									dow.add(DayOfWeek.THURSDAY);
								if(weekdays.contains("F"))
									dow.add(DayOfWeek.FRIDAY);
								if(weekdays.contains("A"))
									dow.add(DayOfWeek.SATURDAY);
								if(theEvent.charAt(k+2) == ';') {
									startTime = theEvent.substring(k+1,k+2);
									k = k+3;
								}
								else {
									startTime = theEvent.substring(k+1,k+3);
									k = k+4;
								}
								if(theEvent.charAt(k+1) == ';')
									endTime = theEvent.substring(k,k+1);
								else
									endTime = theEvent.substring(k,k+2);
	
								calModel.update(new Event(eventName, year,"1", startMonth, endMonth, startTime, endTime, dow));
							
							}
							
							fileRead.setText("File upload successful");
							southPanel.add(fileRead);
							in.close();

							centerPanel.revalidate();
							centerPanel.repaint();
							southPanel.revalidate();
							southPanel.repaint();
						}
							catch(IOException i) {
								fileRead.setText("File upload unsuccessful");
							}
						}
				});
		
		calModel.attach(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				events = calModel.getData();
			}
		});
		
		northPanel.setLayout(new FlowLayout());
		northPanel.add(day);
		northPanel.add(week);
		northPanel.add(month);
		northPanel.add(agenda);
		northPanel.add(file);

		add(northPanel, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
			
			southPanel.add(hourPanel);
			southPanel.add(eventPanel);
			
			southPanel.revalidate();
			southPanel.repaint();
		}
		
	public static void changeDay(LocalDate d) {
		currentDay = d;
		if(currentView == 1)
		{
			day.doClick();
			CurrentCalendarView.currentDay();	
		}
		else if (currentView == 2)
		{
			CurrentCalendarView.currentWeek();
			week.doClick();
			
		}
		else if(currentView == 3)
		{
			month.doClick();
			CurrentCalendarView.currentMonth();
		}
		else
		{
			agenda.doClick();
		}
	}
	
	public void getWeeklyEvents()
	{
		if(currentDay.getDayOfWeek().getValue() == 7)
		{
			System.out.println(currentDay.getDayOfWeek());
			for(int i = 0; i < events.size(); i++)
			{
				if(currentDay.getDayOfMonth() == events.get(i).date().getDayOfMonth() && currentDay.getYear() == events.get(i).date().getYear()&& currentDay.getMonthValue() == events.get(i).date().getMonthValue())
				{
					for(int j = 0; j < seventhColEvents.size(); j++)
					{
						if(events.get(i).startHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(events.get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < events.get(i).endHour())
						{
							seventhColEvents.get(j).setBackground(Color.BLUE);
						}
						if(events.get(i).endHour() == (j % 24))
						{
							seventhColEvents.get(j).setBackground(Color.BLUE);
							seventhColEvents.get(j).setText(events.get(i).eventName());
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 1 == events.get(i).date().getDayOfMonth() && currentDay.getYear() == events.get(i).date().getYear()&& currentDay.getMonthValue() == events.get(i).date().getMonthValue())
				{     
					for(int j = 0; j < firstColEvents.size(); j++)
					{
						if(events.get(i).startHour() == (j % 24))
						{
							firstColEvents.get(j).setText(events.get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < events.get(i).endHour())
						{
							firstColEvents.get(j).setBackground(Color.BLUE);
						}
						if(events.get(i).endHour() == (j % 24))
						{
							firstColEvents.get(j).setText(events.get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < secondColEvents.size(); j++)
					{
						if(events.get(i).startHour() == (j % 24))
						{
							secondColEvents.get(j).setText(events.get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							secondColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							secondColEvents.get(j).setText(events.get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 3 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < thirdColEvents.size(); j++)
					{
						if(events.get(i).startHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(events.get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							thirdColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 4 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fourthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							fourthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fifthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							fifthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 3 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < sixthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							sixthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							sixthColEvents.get(j).setBackground(Color.BLUE);
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							numberOfDaysInBetween = true;
						}
					}
				}								
			}
		}
		else if(currentDay.getDayOfWeek().getValue() == 1)
		{
			for(int i = 0; i < events.size(); i++)
			{
				if(currentDay.getDayOfMonth() == events.get(i).date().getDayOfMonth() && currentDay.getYear() == events.get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < firstColEvents.size(); j++)
					{
						if(events.get(i).startHour() == (j % 24))
						{
							firstColEvents.get(j).setText(events.get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < events.get(i).endHour())
						{
							firstColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							firstColEvents.get(j).setText(events.get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < seventhColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							seventhColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() +1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < secondColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							secondColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < thirdColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							thirdColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 3 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fourthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							fourthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 4 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fifthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24)){
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour()){
							fifthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24)){
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}}}
				else if(currentDay.getDayOfMonth() + 5 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < sixthColEvents.size(); j++){
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							sixthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}								
			}
		}
		else if(currentDay.getDayOfWeek().getValue() == 2)
		{
			for(int i = 0; i < events.size(); i++){
				if(currentDay.getDayOfMonth() == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < secondColEvents.size(); j++){
						if(calModel.getData().get(i).startHour() == (j % 24)){
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour()){
							secondColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24)){
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < firstColEvents.size(); j++){
						if(calModel.getData().get(i).startHour() == (j % 24)){
							firstColEvents.get(j).setText(calModel.getData().get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour()){
							firstColEvents.get(j).setText(calModel.getData().get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24)){
							firstColEvents.get(j).setText(calModel.getData().get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < seventhColEvents.size(); j++){
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							seventhColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < thirdColEvents.size(); j++){
						if(calModel.getData().get(i).startHour() == (j % 24)){
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour()){
							thirdColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24)){
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fourthColEvents.size(); j++){
						if(calModel.getData().get(i).startHour() == (j % 24)){
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour()){
							fourthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24)){
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 3 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fifthColEvents.size(); j++){
						if(calModel.getData().get(i).startHour() == (j % 24)){
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour()){
							fifthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24)){
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 4 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < sixthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							sixthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}								
			}
		}
		else if(currentDay.getDayOfWeek().getValue() == 3)
		{
			for(int i = 0; i < events.size(); i++)
			{
				if(currentDay.getDayOfMonth() == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < thirdColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							thirdColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < secondColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							secondColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < firstColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							firstColEvents.get(j).setText(calModel.getData().get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							firstColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							firstColEvents.get(j).setText(calModel.getData().get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() -3 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < seventhColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							seventhColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fourthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							fourthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fifthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							fifthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 3 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear() && currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < sixthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							sixthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}								
			}
		}
		else if(currentDay.getDayOfWeek().getValue() == 4)
		{
			for(int i = 0; i < events.size(); i++)
			{
				if(currentDay.getDayOfMonth() == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear() && currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fourthColEvents.size(); j++)
					{
						
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							fourthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < thirdColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							thirdColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < secondColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							secondColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 3 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < firstColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							firstColEvents.get(j).setText(calModel.getData().get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							firstColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							firstColEvents.get(j).setText(calModel.getData().get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 4 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < seventhColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							seventhColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fifthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							fifthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < sixthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							sixthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}								
			}			
		}
		else if(currentDay.getDayOfWeek().getValue() == 5)
		{
			for(int i = 0; i < events.size(); i++)
			{
				if(currentDay.getDayOfMonth() == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fifthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							fifthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fourthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							fourthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					
					for(int j = 0; j < thirdColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							thirdColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 3 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j <secondColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							secondColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 4 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < firstColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							firstColEvents.get(j).setText(calModel.getData().get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							firstColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							firstColEvents.get(j).setText(calModel.getData().get(i).eventName());
							firstColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 5 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < seventhColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							seventhColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() + 1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < sixthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							sixthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}								
			}
		}		
		else if(currentDay.getDayOfWeek().getValue() == 6)
		{
			for(int i = 0; i < events.size(); i++)
			{
				if(currentDay.getDayOfMonth() == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < sixthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							sixthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							sixthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							sixthColEvents.get(j).setBackground(Color.BLUE);
							sixthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 1 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fifthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fifthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							fifthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fifthColEvents.get(j).setText(calModel.getData().get(i).startHour() + " " + calModel.getData().get(i).endHour() );
							fifthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 2 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < fourthColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).eventName());
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{			
							fourthColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							fourthColEvents.get(j).setText(calModel.getData().get(i).startHour() + " " + calModel.getData().get(i).endHour() );
							fourthColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 3 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < thirdColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							thirdColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 4 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < secondColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							secondColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							secondColEvents.get(j).setText(calModel.getData().get(i).eventName());
							secondColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 5 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < firstColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							thirdColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							thirdColEvents.get(j).setText(calModel.getData().get(i).eventName());
							thirdColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}
				else if(currentDay.getDayOfMonth() - 6 == calModel.getData().get(i).date().getDayOfMonth() && currentDay.getYear() == calModel.getData().get(i).date().getYear()&& currentDay.getMonthValue() == calModel.getData().get(i).date().getMonthValue())
				{
					for(int j = 0; j < seventhColEvents.size(); j++)
					{
						if(calModel.getData().get(i).startHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = false;
						}	
						if(numberOfDaysInBetween == false && j%24 < calModel.getData().get(i).endHour())
						{
							seventhColEvents.get(j).setBackground(Color.BLUE);
						}
						if(calModel.getData().get(i).endHour() == (j % 24))
						{
							seventhColEvents.get(j).setText(calModel.getData().get(i).eventName());
							seventhColEvents.get(j).setBackground(Color.BLUE);
							numberOfDaysInBetween = true;
						}
					}
				}								
			}
		}
	}
	/*
	@Override
	public void stateChanged(ChangeEvent e) {
		boolean numberOfHoursInBetween = true;
		for(int j = 0; j < dayView.size(); j++)
		{
			
			for(int i = 0; i < calModel.getData().size(); i++)
			{
				if(calModel.getData().get(i).date().getDayOfMonth()==currentDay.getDayOfMonth() &&calModel.getData().get(i).date().getYear() == currentDay.getYear())
				{							
					if(calModel.getData().get(i).startHour() == j)
					{
						System.out.println("START: " + j);
						dayView.get(j).setText(calModel.getData().get(i).eventName());
						numberOfHoursInBetween = false;
					}
					if(numberOfHoursInBetween == false && j < calModel.getData().get(i).endHour())
					{
						System.out.println("BETWEEN: " + j);
						dayView.get(j).setText(calModel.getData().get(i).eventName());
					}
					if(calModel.getData().get(i).endHour() == j)
					{
						System.out.println("END: " + j);
						dayView.get(j).setText(calModel.getData().get(i).eventName());
						numberOfHoursInBetween = true;
					}
				}
			}
		}
		if(currentView == 3)
		{
			centerPanel.removeAll();
			southPanel.removeAll();
			
			JTextField monthTextField = new JTextField(currentDay.getMonth().toString() + " " + currentDay.getYear());
			monthTextField.setEditable(false);
			centerPanel.add(monthTextField);
			centerPanel.setLayout(new FlowLayout());
			centerPanel.revalidate();
			centerPanel.repaint();
			
			southPanel.setLayout(new GridLayout(7,6));
	
			String weekDays = "SUNMONTUEWEDTHUFRISAT";
			int first = 0;
			int second = 3;
			for(int i = 0; i < 7; i++) {
				JTextField week = new JTextField(weekDays.substring(first, second));
				week.setEditable(false);
				southPanel.add(week);
				first = second;
				second = second + 3;
			}
			
			LocalDate firstDay = LocalDate.of(currentDay.getYear(), currentDay.getMonth(), 1);
			
			final int cd = currentDay.getDayOfMonth();
			int daysInMonth = currentDay.getMonth().maxLength();
			
			int count = 0;
	
			if(firstDay.getDayOfWeek().getValue() != 7) {
				for(int day = 0; day < firstDay.getDayOfWeek().getValue(); day++) {
					JTextField numbers = new JTextField(null);
					southPanel.add(numbers);
					f.add(numbers);
					numbers.setEditable(false);
					count++;
				}
			}
			
			for(int day = 1; day <= daysInMonth; day++) {
				JTextField numbers = new JTextField();
				if(day == cd) {
					numbers.setForeground(Color.red);
				}
				numbers.setText("" + day);
				southPanel.add(numbers);
				f.add(numbers);
				numbers.setEditable(false);
				count++;
			}
			for(int i = count; i < 38; i++) {
				JTextField numbers = new JTextField(null);
				southPanel.add(numbers);
				f.add(numbers);
				numbers.setEditable(false);
			}
			for(int i = 0; i < f.size(); i++)
			{
				String calendarDisplay = null;
				int counterdd = 0;
				String textFieldDayOfMonth = f.get(i).getText();
				for(int j=0; j < events.size(); j++)
				{
					int dayOfMonth = calModel.getData().get(j).date().getDayOfMonth();
					String dayOfMonthString = Integer.toString(dayOfMonth);
					System.out.println("MODEL" + calModel.getData().size());
					if(dayOfMonthString.equals(textFieldDayOfMonth) && calModel.getData().get(j).date().getMonthValue() == currentDay.getMonthValue())
					{
						if(counterdd == 0)
						{										
							calendarDisplay = f.get(i).getText() + " " + calModel.getData().get(j).eventName();
							f.get(i).setText(calendarDisplay);
						}
						counterdd++;
					}
					if(counterdd >= 2)
					{
						f.get(i).setText(calendarDisplay + "+" +(counterdd - 1)  + "..");
					}
				}
			}
			southPanel.revalidate();
			southPanel.repaint();
		}
	}
	*/
}