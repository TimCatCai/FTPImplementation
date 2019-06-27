package process;

/**
 * the interfaces of user protocol interpreter process for communication with DTP
 * @author TimcatCai
 * @version 2019/06/27
 */
public interface ServerProcessable {
    /**
     *
     * @param response
     */
     void replyOperationResult(Event response);
}
