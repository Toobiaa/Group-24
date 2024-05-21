import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class RecordReader {
    private static String filePath = CSVFileWriter.filePath + "//record.csv"; // 默认路径
    public static void setFilePath(String newPath) {
        filePath = newPath;
    }
    public static ArrayList<Record> getRecordInfoAll(int familyId) {
        ArrayList<Record> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { // Ensure the line has at least 7 columns
                    try {
                        int recordFamilyId = Integer.parseInt(parts[0].trim());
                        String childName = parts[1].trim();
                        String description = parts[2].trim();
                        int change = Integer.parseInt(parts[3].trim());

                        if (recordFamilyId == familyId) {
                            Record record = new Record(recordFamilyId, childName, description, change);
                            records.add(record);
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
        return records;
    }

    public static ArrayList<Record> getRecordInfoPersonal(int familyId, String childName) {
        ArrayList<Record> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { // Ensure the line has at least 7 columns
                    try {
                        int recordFamilyId = Integer.parseInt(parts[0].trim());
                        String recordChildName = parts[1].trim();
                        String description = parts[2].trim();
                        int change = Integer.parseInt(parts[3].trim());

                        if (recordFamilyId == familyId && recordChildName.equals(childName)) {
                            Record record = new Record(recordFamilyId, childName, description, change);
                            records.add(record);
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
        return records;
    }

    public static void main(String[] args) { 
            ArrayList<Record> results=getRecordInfoPersonal(1, "wxc");

        // 检查结果
        if (results.isEmpty()) {
            System.out.println("No accounts found for the specified family ID and child name.");
        } else {
            System.out.println("Found accounts:");
            for (Record rec : results) {
                System.out.println("Family ID: " + rec.getFamilyId() + ", Child Name: " + rec.getName() +
                     ", Type: " + rec.getType() + ", ChangeMoney: " + rec.getChangeMoney());

            }
        }
       }
}