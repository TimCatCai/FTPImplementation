package process;

/**
 * @author Timcat Cai
 * @version 2019/06/27
 */
public class Event  {
    private String data;
    private final DataDirection direction;
    public Event(String data,DataDirection direction){
        this.data = data;
        this.direction = direction;
    }

    protected String getData() {
        return data;
    }

    protected DataDirection getDirection() {
        return direction;
    }
}
