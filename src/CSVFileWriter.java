import java.io.*;

public class CSVFileWriter {
    public static String filePath = "C://Users//1//Desktop//kidsbank24//src";
    public static void setFilePath(String newPath) {
        filePath = newPath;
    }
    public static void writeUser(int familyId, String name, String identity, String password) {
        try (FileWriter writer = new FileWriter(filePath + "//user.csv", true);
            BufferedWriter bw = new BufferedWriter(writer);
            PrintWriter out = new PrintWriter(bw)) {

            if(!identity.equals("child")&&!identity.equals("parent")){
                throw new InvalidIdentityException("Invalid identity:" + identity );
            }

            if (!(Integer.class.isInstance(familyId)) || !(String.class.isInstance(name)) || !(String.class.isInstance(identity)) || !(String.class.isInstance(password))) {
                throw new InvalidDataTypeException("Invalid data type.");
            }
            
            // Write data to file
            out.println(familyId + "," + name + "," + identity + "," + password);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidIdentityException | InvalidDataTypeException e) {
            System.err.println("Error:" + e.getMessage());
        }
    }

     public static void writeTask(int taskId, String taskName, int bonus, int familyId, boolean isSubmitted, String childName, boolean isDone){
        try (FileWriter writer = new FileWriter(filePath + "\\task.csv", true);
            BufferedWriter bw = new BufferedWriter(writer);
            PrintWriter out = new PrintWriter(bw)) {
            
//            if (!(Integer.class.isInstance(taskId)) || !(String.class.isInstance(taskName)) || !(Integer.class.isInstance(bonus)) || !(Integer.class.isInstance(familyId))
//            || !(Boolean.class.isInstance(isSubmitted))|| !(String.class.isInstance(childName))|| !(Boolean.class.isInstance(isDone))) {
//                throw new InvalidDataTypeException("Invalid data type.");
//            }
            
            out.println(taskId + "," + taskName + "," + bonus + "," + familyId + "," + isSubmitted + "," +childName + "," + isDone);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InvalidDataTypeException e){
//            System.err.println("Error:" + e.getMessage());
//        }
    }

    public static void writeRecord(int familyId, String childName, String description, int change) {
        try (FileWriter writer = new FileWriter(filePath + "\\record.csv", true);
            BufferedWriter bw =  new BufferedWriter(writer);
            PrintWriter out = new PrintWriter(bw)) {
            
//            if (!(Integer.class.isInstance(familyId)) || !(String.class.isInstance(childName)) || !(String.class.isInstance(description)) || !(Integer.class.isInstance(change))) {
//                throw new InvalidDataTypeException("Invalid data type.");
//            }

            out.println(familyId + "," + childName + "," + description + "," + change);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InvalidDataTypeException e){
//            System.err.println("Error:" + e.getMessage());
//        }
    }

    public static void writeAccount(int familyId, String name, int SavingAccout, int CurrentAccout, int goal) {
        try (FileWriter writer = new FileWriter(filePath + "\\account.csv", true);
            BufferedWriter bw =  new BufferedWriter(writer);
            PrintWriter out = new PrintWriter(bw)) {
            
            if (!(Integer.class.isInstance(familyId)) || !(String.class.isInstance(name)) || !(Integer.class.isInstance(SavingAccout)) || !(Integer.class.isInstance(CurrentAccout)) || !(Integer.class.isInstance(goal))) {
                throw new InvalidDataTypeException("Invalid data type.");
            }
            
            out.println(familyId + "," + name + "," + SavingAccout + "," + CurrentAccout + "," + goal);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidDataTypeException e){
            System.err.println("Error:" + e.getMessage());
        }
    }
}

class InvalidIdentityException extends Exception {
    public InvalidIdentityException(String message) {
        super(message);
    }
}

class InvalidDataTypeException extends Exception {
    public InvalidDataTypeException(String message) {
        super(message);
    }
}