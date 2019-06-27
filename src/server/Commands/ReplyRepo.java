package server.Commands;

import java.util.HashMap;

public class ReplyRepo {
    private HashMap<Integer,Reply> replysList = new HashMap<>();
    private static ReplyRepo sReplyRepo;

    //commands
    public final static int NO_SUCH_COMMAND = 502;
    public final static int PARAMETER_ERROR = 501;

    //login
    public final static int LOGGED_IN_PROCEED = 230;
    private ReplyRepo(){
        replysList.put(502 ,new Reply(
                502 ,
                "Command not implemented"
        ));
        replysList.put(501 ,new Reply(
                501,
                "Syntax error in parameters or arguments"
        ));

        replysList.put(230,new Reply(
                230,
                "User logged in, proceed"
        ));

    }

    public static Reply getReply(int stateCode){
        if(sReplyRepo == null){
            sReplyRepo =  new ReplyRepo();
        }

        return sReplyRepo.getReplyInReplysList(stateCode);
    }

    private Reply getReplyInReplysList(int stateCode){
        return replysList.get(stateCode);
    }

}
