public class Record{
    private int familyId;
    private String name;
    private String type;
    private int changeMoney;
    public Record(int familyId,String name,String type,int changeMoney){
        this.familyId=familyId;
        this.name=name;
        this.type=type;
        this.changeMoney=changeMoney;
    }
    public Record(int familyId,String type,int changeMoney){
        this.familyId=familyId;
        this.type=type;
        this.changeMoney=changeMoney;
    }

    public static void newRecord(int familyId, String childName, String description, int change){
        CSVFileWriter.writeRecord(familyId,childName,description,change);
    }

    public void setFamilyId(int familyId){
        this.familyId=familyId;
    }
    public int getFamilyId(){
        return familyId;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
    public void setChangeMoney(int changeMoney){
        this.changeMoney=changeMoney;
    }
    public int getChangeMoney(){
        return changeMoney;
    }
}