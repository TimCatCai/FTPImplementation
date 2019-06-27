package reposity.file;

/**
 *  Users' access privileges that access controls defined to the use of a
 * system, and to the files in that system.
 * @author TimCatCai
 * @version 2019/06/26
 */

public enum AccessPrivilege {
    /**
     * super administrator privilege access
     * User who has privilege access can edit the file, include moving deleting files or directories
     * and modifying the name of files or directories.
     */
    ADMIN("#"),
    /**
     * ordinary user privilege access
     * User can only read files.
     */
    ORDINARY("$");

    private String privilege;
    private AccessPrivilege(String privilege){
        this.privilege = privilege;
    }
    public String getPrivilege(){
        return this.privilege;
    }
}
