package process;

/**
 * @author Timcat Cai
 * @version 2019/06/27
 */
public class Event  {
    private String data;
    private  DataDirection direction;
    private String original;
    public Event(String data,DataDirection direction,String original){
        this.data = data;
        this.direction = direction;
        this.original = original;
    }

    public String getData() {
        return data;
    }

    public DataDirection getDirection() {
        return direction;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public void setDirection(DataDirection direction) {
        this.direction = direction;
    }

    public void setData(String data) {
        this.data = data;
    }
}
