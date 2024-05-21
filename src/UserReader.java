import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserReader {
    private static String filePath = CSVFileWriter.filePath + "\\user.csv";

    public static void setFilePath(String newPath) {
        filePath = newPath;
    }

    public static user getUserDetails(int familyId, String name) {
        user userDetails = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { 
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String userName = parts[1].trim();
                        String identity = parts[2].trim();
                        String password = parts[3].trim();
                        if (id == familyId && userName.equals(name)) {
                            userDetails = new user(id, userName, identity, password);
                            break;
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
            if (userDetails == null) {
                throw new UserNotFoundException("User not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return userDetails;
    }

    public static int[] getAllFamilyIds() {
        List<Integer> familyIds = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1) { 
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        if (!familyIds.contains(id)) {
                            familyIds.add(id);
                        }
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Convert List<Integer> to int[]
        int[] result = new int[familyIds.size()];
        for (int i = 0; i < familyIds.size(); i++) {
            result[i] = familyIds.get(i);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] allFamilyIds = getAllFamilyIds();
        System.out.println("All familyIds:");
        for (int id : allFamilyIds) {
            System.out.println(id);
        }
    }
}
