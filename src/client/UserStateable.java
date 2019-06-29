package client;

import reposity.file.AccessPrivilege;

public interface UserStateable {

   String  getUserName();
   void setUserName(String userName);
   void  setUserPrivilege(AccessPrivilege accessPrivilege);

}
