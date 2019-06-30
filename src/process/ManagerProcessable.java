package process;

import process.event.Event;

/**
 * the interfaces of user protocol interpreter process for communication with DTP
 * @author TimcatCai
 * @version 2019/06/27
 */
public interface ManagerProcessable {
    /**
     *
     * @param response
     */
     void replyOperationResult(Event response);


}
