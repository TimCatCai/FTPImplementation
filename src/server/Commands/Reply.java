package server.Commands;

public class Reply {
    private String stateCode;
    private String description;

    public Reply(String stateCode, String description) {
        this.stateCode = stateCode;
        this.description = description;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getDescription() {
        return description;
    }
}
