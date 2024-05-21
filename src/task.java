import java.util.*;

/**
 * task[EN], 任务[中]
 * 家长视角：【家长】针对同一家庭下的一个【孩子】发布或审批任务
 * 孩子视角：提交任务
 * 构成元素：taskName, taskId, familyId, bonus, childName, isSubmitted, isDone
 * 每一个新建的taskId等于当前的task数量 + 1
 */
public class task {
    private int taskId;
    private String taskName;
    private int bonus;
    private int familyId;
    private boolean isSubmitted;
    private boolean isDone;
    private String childName;

    public static void submit(int taskId) {
        // 【孩子】提交任务, 修改isSubmitted为true
        changeTaskDetail.changeSubState(taskId, true);
        System.out.println("successfully submit");
        return;
    }

    public static  void permit(int taskId) {
        // 【家长】审批任务, 修改isDone为ture
        changeTaskDetail.changeDoneState(taskId, true);
        System.out.println("successfully permit");
        return;
    }

    // 查看家庭下某个小孩的任务列表【小孩功能】
    // static方法的原因：参数含有家庭和小孩信息，和具体的task对象无关
    // 如果不使用static方法，本方法应该在家长/小孩对应的类实现
    public static ArrayList<task> viewTasks(int familyId, String childName) {
        // 调用IO类的方法，检索出符合上述要求的所有任务，然后显示
        ArrayList<task> childTasks = TaskReader.getTaskInfoPersonal(familyId, childName);
        System.out.println(childTasks);
        return childTasks;
    }

    // 查看家庭下的所有小孩的任务列表 【家长功能】
    public static ArrayList<task> viewTasks(int familyId) {
        // 调用IO类的方法，检索符合上述要求的所有任务，然后显示
        ArrayList<task> familyTasks = TaskReader.getTaskInfoAll(familyId);
        System.out.println(familyTasks);
        return familyTasks;
    }

    public static void newTask(int familyId, String chindName, String taskName, int bonus) {
        // 印发新任务【家长功能】；在方法中提供任务的所有描述信息，然后针对某小孩发布
        // taskId为目前的task条数
        String name = chindName;
        int newTaskId = (TaskReader.getTaskInfo()).size() + 1;
        CSVFileWriter.writeTask(newTaskId, taskName, bonus, familyId, false, name, false);
        System.out.println("New task established!");
    }

    public static void main(String[] args) {
        task.newTask(1,"rr","uu",1);
    }

    public task(int taskId, String taskName, int bonus, int familyId, 
                boolean isSubmitted, String childName, boolean isDone) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.bonus = bonus;
        this.familyId = familyId;
        this.isSubmitted = isSubmitted;
        this.isDone = isDone;
        this.childName = childName;
    }


    // getters and setters
    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public int getBonus() { return bonus; }
    public void setBonus(int bonus) { this.bonus = bonus; }
    public int getFamilyId() { return familyId; }
    public void setFamilyId(int familyId) { this.familyId = familyId; }
    public boolean isSubmitted() { return isSubmitted; }
    public void setSubmitted(boolean isSubmitted) { this.isSubmitted = isSubmitted; }
    public boolean isDone() { return isDone; }
    public void setDone(boolean isDone) { this.isDone = isDone; }
    public String getChildName() { return childName; }
    public void setChildName(String childName) { this.childName = childName; }

}