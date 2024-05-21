import java.util.ArrayList;

public class parent extends user{

    private int familyId;
    private String name;
    private String identity; // "parent" or "child"
    private String password;

    public static ArrayList<task> viewTaskList(int familyId){
        return task.viewTasks(familyId);
    }

    public  ArrayList<task> findPermitTask(){
        int familyID = this.familyId;
        ArrayList<task> familyTask = task.viewTasks(familyID);
        ArrayList<task> PermitTasks = new ArrayList<>();
        for (task t : familyTask) {
            if(t.isSubmitted()){
                PermitTasks.add(t);
            }
        }
        return  PermitTasks;
    }

    public void PermitTask(int taskID){
        int familyId = this.familyId;
        task.permit(taskID);
        ArrayList<task> familyTask = task.viewTasks(familyId);
        for (task t : familyTask) {
            if (t.getTaskId() == taskID) {
                task finishedTask = t;
                int newbalance = account.save(finishedTask.getFamilyId(),finishedTask.getChildName(),finishedTask.getBonus());
                String newDescription = "Task: " + finishedTask.getTaskName()+".";
                CSVFileWriter.writeRecord(finishedTask.getFamilyId(),finishedTask.getChildName(),newDescription,finishedTask.getBonus());
            }
        }
    }

    public  ArrayList<Record> transaction(){
        int familyId = this.familyId;
        ArrayList<Record> familyRecord = RecordReader.getRecordInfoAll(familyId);
        return familyRecord;
    }

    public void releaseTask(String taskName, int bonus, String childName) {
        int familyId = this.familyId;
        task.newTask( familyId,childName,  taskName, bonus);
    }

    public parent(int familyId, String name, String identity, String password) {
        super();
        this.familyId = familyId;
        this.name = name;
        this.identity = identity;
        this.password = password;
    }

    public parent(int familyId1, String name1,String identity) {
        super(familyId1, name1, identity);
        this.familyId = familyId1;
        this.name = name1;
        this.identity = identity;
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
}
