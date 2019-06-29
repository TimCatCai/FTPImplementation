package server.commands;

public class Reply {
    private int stateCode;
    private String description;
    private final String SPLITER = "|";
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(stateCode);
        builder.append(SPLITER);
        builder.append(description);
        return builder.toString();
    }
}
