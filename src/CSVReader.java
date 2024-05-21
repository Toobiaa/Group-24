import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

public class CSVReader {
    private static String filePath = CSVFileWriter.filePath + "//user.csv" ;
    public static void setFilePath(String newPath) {
        filePath = newPath;
    }


    public static Boolean getFamilyId(int familyId) {
        Boolean check = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { // Ensure the line has at least 4 columns
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        if (id == familyId) {
                            check=true;
                            break;
                        }
                    } catch (NumberFormatException e) {
                        // Skip line if familyId is not a valid integer
                        continue;
                    }
                }
            }
            if (check == false) {
                throw new UserNotFoundException("FamilyId not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return check;
    }
    public static user getUserDetails(int familyId, String name) {
        user userDetails = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) { // Ensure the line has at least 4 columns
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
                        // Skip line if familyId is not a valid integer
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

    public static void main(String[] args) {
        // Example usage
        user ur = getUserDetails(123, "John Doe");
        if (ur != null) {
            System.out.println("User details:");
            System.out.println("Family ID: " + ur.getFamilyId());
            System.out.println("Name: " + ur.getName());
            System.out.println("Identity: " + ur.getIdentity());
            System.out.println("Password: " + ur.getPassword());
        }
    }
}
