package process;

public interface DataTransferProcessable {
    /**
     * sets data through DTP
     * @param event communication info for the DTP
     */

    void sentData(Event event);



    /**
     * gets data from DTP
     * @param event communication info for the DTP
     */
    void receiveData(Event event);

}
