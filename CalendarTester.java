import java.util.ArrayList;

public class CalendarTester 
{
	public static void main(String[] args)
	{
		ArrayList<Event> e = new ArrayList<Event>();
		CalendarModel model = new CalendarModel(e);
		
		CurrentCalendarView current = new CurrentCalendarView(model);
		SelectedView view = new SelectedView(model);
	}
}