package process;

public interface DataTransferProcessable {
    /**
     * sets data through DTP
     * @param event communication info for the DTP
     */

    void sentData(Event event);



    /**
     * sets data frome DTP
     * @param event communication info for the DTP
     */
    void receiveData(Event event);

}
