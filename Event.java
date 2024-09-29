import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The event class that puts together the attributes of an Event
 * @author Team Summer: Tawni Pizzagoni, Adrian Chan, David Truong
 * 07/29/19
 */
public class Event 
{
	private String eventName;
	private String year;
	private String startMonth;
	private String endMonth;
	private String startHour;
	private String endHour;
	private String startDay;
	private String endDay;
	private ArrayList<DayOfWeek> days;
	
	public Event(String eventName, String year, String startDay, String startMonth, String endMonth, String startHour, String endHour, ArrayList<DayOfWeek> days)
	{
		this.eventName = eventName;
		this.year = year;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startHour = startHour;
		this.endHour = endHour;
		this.days = days;
		this.startDay = startDay;
		this.endDay = endDay;
	}
	public int getStartDay()
	{
		if(startDay.equals("1"))
		{
			return 1;
		}
		return Integer.parseInt(startDay);
	}
	public int getEndDay()
	{
		return Integer.parseInt(endDay);
	}
	public int getYear()
	{
		return Integer.parseInt(year);
	}
	
	public int startMonth()
	{
		return Integer.parseInt(startMonth);
	}
	
	public int endMonth()
	{
		return Integer.parseInt(endMonth);
	}
	
	public int startHour()
	{
		return Integer.parseInt(startHour);
	}
	
	public int endHour()
	{
		return Integer.parseInt(endHour);
	}
	
	public String eventName()
	{
		return eventName;
	}
	
	public ArrayList<DayOfWeek> daysOfTheWeek() {
		return days;
	}
	
	public LocalDate date()
	{
		if(getStartDay() == 1)
		{
			return LocalDate.of(getYear(), startMonth(), 1);
		}
		return LocalDate.of(getYear(), startMonth(), getStartDay());
	}
}