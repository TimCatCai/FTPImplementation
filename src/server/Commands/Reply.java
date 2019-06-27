package server.Commands;

public class Reply {
    private int stateCode;
    private String description;

    public Reply(int stateCode, String description) {
        this.stateCode = stateCode;
        this.description = description;
    }

    public int getStateCode() {
        return stateCode;
    }

    public String getDescription() {
        return description;
    }
}
