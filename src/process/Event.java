package process;

/**
 * @author Timcat Cai
 * @version 2019/06/27
 */
public class Event  {
    private String data;
    private  DataDirection direction;
    public Event(String data,DataDirection direction){
        this.data = data;
        this.direction = direction;
    }

    public String getData() {
        return data;
    }

    public DataDirection getDirection() {
        return direction;
    }

    public void setDirection(DataDirection direction) {
        this.direction = direction;
    }

    public void setData(String data) {
        this.data = data;
    }
}
