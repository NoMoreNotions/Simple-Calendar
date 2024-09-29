import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CalendarModel 
{
	private ArrayList<Event> event;
	private ArrayList<ChangeListener> listener;
	
	public CalendarModel(ArrayList<Event> e)
	{
		event = e;
		listener = new ArrayList<ChangeListener>();
	}

	public ArrayList<Event> getData()
	{
		return (ArrayList<Event>) (event.clone());
	}
	
	public void attach(ChangeListener l)
	{
		listener.add(l);
	}
	
	public void update(Event e)
	{
		event.add(e);
		for(ChangeListener l : listener)
		{
			l.stateChanged(new ChangeEvent(this));
		}
	}
}