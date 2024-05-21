import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class TaskReader {
    private static String filePath = CSVFileWriter.filePath + "\\task.csv"; // 默认路径

    public static void setFilePath(String newPath) {
        filePath = newPath;
    }
    public static ArrayList<task> getTaskInfoAll(int familyId) {
        ArrayList<task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) { // Ensure the line has at least 7 columns
                    try {
                        int taskId = Integer.parseInt(parts[0].trim());
                        String taskName = parts[1].trim();
                        int bonus = Integer.parseInt(parts[2].trim());
                        int taskFamilyId = Integer.parseInt(parts[3].trim());
                        boolean isSubmitted = Boolean.parseBoolean(parts[4].trim());
                        String childName = parts[5].trim();
                        boolean isDone = Boolean.parseBoolean(parts[6].trim());

                        if (taskFamilyId == familyId) {
                            task task = new task(taskId, taskName, bonus, taskFamilyId, isSubmitted, childName, isDone);
                            tasks.add(task);
                        }
                    } catch (NumberFormatException e) {
                        // Skip line if any integer value cannot be parsed
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static ArrayList<task> getTaskInfoPersonal(int familyId, String childName) {
        ArrayList<task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) { // Ensure the line has at least 7 columns
                    try {
                        int taskId = Integer.parseInt(parts[0].trim());
                        String taskName = parts[1].trim();
                        int bonus = Integer.parseInt(parts[2].trim());
                        int taskFamilyId = Integer.parseInt(parts[3].trim());
                        boolean isSubmitted = Boolean.parseBoolean(parts[4].trim());
                        String taskChildName = parts[5].trim();
                        boolean isDone = Boolean.parseBoolean(parts[6].trim());

                        if (taskFamilyId == familyId && taskChildName.equals(childName)) {
                            task task = new task(taskId, taskName, bonus, taskFamilyId, isSubmitted, taskChildName, isDone);
                            tasks.add(task);
                        }
                    } catch (NumberFormatException e) {
                        // Skip line if any numeric value cannot be parsed
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static ArrayList<task> getTaskInfo() {
        ArrayList<task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) { // Ensure the line has at least 7 columns
                    try {
                        int taskId = Integer.parseInt(parts[0].trim());
                        String taskName = parts[1].trim();
                        int bonus = Integer.parseInt(parts[2].trim());
                        int familyId = Integer.parseInt(parts[3].trim());
                        boolean isSubmitted = Boolean.parseBoolean(parts[4].trim());
                        String childName = parts[5].trim();
                        boolean isDone = Boolean.parseBoolean(parts[6].trim());

                        task task = new task(taskId, taskName, bonus, familyId, isSubmitted, childName, isDone);
                        tasks.add(task);
                    } catch (NumberFormatException e) {
                        // Skip line if any numeric value cannot be parsed
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
