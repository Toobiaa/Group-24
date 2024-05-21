/* user.java  1st edited by Chongwen Liu */

public class user {
    private int familyId;
    private String name;
    private String identity; // "parent" or "child"
    private String password;



    public user(int familyId, String name, String Identity) {
        this.familyId = familyId;
        this.name = name;
        this.identity = identity;
    }

    public user(int familyId, String name, String identity, String password) {
        this.familyId = familyId;
        this.name = name;
        this.identity = identity;
        this.password = password;
    }

    public user(){

    }



    public int getFamilyId() { return this.familyId; }
    public  String getName() { return this.name; }
    public  String getIdentity() { return this.identity; }
    public  String getPassword() { return this.password; }

    public  void setFamilyId(int familyId) { this.familyId = familyId; }
    public  void setName(String name) { this.name = name; }
    public  void setIdentity(String identity) { this.identity = identity; }
    public  void setPassword(String password) { this.password = password; }

}
