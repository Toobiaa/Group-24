import java.io.*;
import java.util.ArrayList;

public class changeTaskDetail{

    public static void changeSubState(int TaskId, boolean newState){
        String filePath = CSVFileWriter.filePath + "//task.csv";
        ArrayList<String> lines = new ArrayList<>();

        // Read and store the first line (column headers)
        String firstLine = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            firstLine = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lines.add(firstLine); // Add the first line to the list

        // Read accounts from file and update balance if familyId and childName match
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip the first line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) { // Ensure the line has at least 3 columns
                    int theTaskId = Integer.parseInt(parts[0].trim());
                    String taskName = parts[1].trim();
                    int bonus = Integer.parseInt(parts[2].trim());
                    int familyId = Integer.parseInt(parts[3].trim());
                    boolean isSubmitted = Boolean.parseBoolean(parts[4].trim());
                    String childName = parts[5].trim();
                    boolean isDone = Boolean.parseBoolean(parts[6].trim());


                    // Check if current line matches specified familyId and childName
                    if (theTaskId == TaskId) {
                        // Update balance for matching line
                        isSubmitted = newState;
                        line = theTaskId + "," + taskName + "," + bonus + "," + familyId + "," + isSubmitted + "," + childName + "," + isDone;
                    }
                }
                lines.add(line); // Add the line to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write updated accounts back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeDoneState(int TaskId, boolean newState){
        String filePath = CSVFileWriter.filePath + "//task.csv";
        ArrayList<String> lines = new ArrayList<>();

        // Read and store the first line (column headers)
        String firstLine = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            firstLine = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lines.add(firstLine); // Add the first line to the list

        // Read accounts from file and update balance if familyId and childName match
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip the first line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) { // Ensure the line has at least 3 columns
                    int theTaskId = Integer.parseInt(parts[0].trim());
                    String taskName = parts[1].trim();
                    int bouns = Integer.parseInt(parts[2].trim());
                    int familyId = Integer.parseInt(parts[3].trim());
                    boolean isSubmitted = Boolean.parseBoolean(parts[4].trim());
                    String childName = parts[5].trim();
                    boolean isDone = Boolean.parseBoolean(parts[6].trim());


                    // Check if current line matches specified familyId and childName
                    if (theTaskId == TaskId) {
                        // Update balance for matching line
                        isDone = newState;
                        line = theTaskId + "," + taskName + "," + bouns + "," + familyId + "," + isSubmitted + "," + childName + "," + isDone;
                    }
                }
                lines.add(line); // Add the line to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write updated accounts back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}