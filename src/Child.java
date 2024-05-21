import java.util.ArrayList;
public class Child extends user  {
        private int familyId;
        private String name;
        private String identity; // "parent" or "child"
        private String password;
        public static ArrayList<task> viewTaskList(int familyId, String childName){
            return task.viewTasks(familyId, childName); }

    public void submittask(int taskId) {// 孩子提交任务, 修改isSubmitted为true
         task.submit(taskId);
        }
    public void withdraw(int money) {
        int newbalance[] = account.withdraw(familyId, name, money);
        String newDescription = "withdraw " ;
        money = -1*money;
        CSVFileWriter.writeRecord(familyId, name, newDescription, money);
    }

    public void consumption(int money, String good) {
        int newbalance = account.consumption(familyId, name, money);
        String newDescription = "Purchase: " + good;
        money = -1*money;
        CSVFileWriter.writeRecord(familyId, name, newDescription, money);
    }

    public  ArrayList<Record> transaction(){
        int familyId = this.familyId;
        ArrayList<Record> familyRecord = RecordReader.getRecordInfoPersonal(familyId,name);
        return familyRecord;
    }
    public void changegoal(int goal){
        account.changeGoal(getFamilyId(),getName(),goal);
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Child(int familyId1, String name1, String Identity) {
        super(familyId1, name1, Identity);
        this.familyId = familyId1;
        this.name = name1;
    }

    public static void main(String[] args) {

    }






}

