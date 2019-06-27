package process;

import java.util.LinkedList;

public class EventQueue {
    private LinkedList<Event> dataEventsList;
    private static final int DEFAULT_EVENT_QUEUE_SIZE = 10;
    private final int maxEventQueueSize;

    public EventQueue(int maxEventQueueSize){
        this.dataEventsList = new LinkedList<>();
        this.maxEventQueueSize = maxEventQueueSize;
    }

    public EventQueue(){
        this(DEFAULT_EVENT_QUEUE_SIZE);
    }


    public Event takeEvent(){
        synchronized(dataEventsList){
            if(dataEventsList.isEmpty()){
                try {
                    dataEventsList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Event event = dataEventsList.removeFirst();
        this.dataEventsList.notify();
        return event;
    }

    public void addEvent(Event event){
        synchronized (dataEventsList){
            if(isDataEventsListFull()){
                try {
                    this.dataEventsList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.dataEventsList.add(event);
        event.notify();
    }

    private  boolean isDataEventsListFull(){
        if(this.dataEventsList.size() >= maxEventQueueSize){
            return true;
        }
        return false;
    }
}
