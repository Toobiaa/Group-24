import java.io.*;
import java.util.ArrayList;

public class changeAccountDetail {

    public static void changeSavingAccount(int familyId, String childName, int newSaving) {
        String filePath = CSVFileWriter.filePath + "//account.csv" ;
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
                if (parts.length >= 5) { // Ensure the line has at least 3 columns
                    int accountFamilyId = Integer.parseInt(parts[0].trim());
                    String accountChildName = parts[1].trim();
                    int SavingAccount = Integer.parseInt(parts[2].trim());
                    int CurrentAccount = Integer.parseInt(parts[3].trim());
                    int goal = Integer.parseInt(parts[4].trim());

                    // Check if current line matches specified familyId and childName
                    if (accountFamilyId == familyId && accountChildName.equals(childName)) {
                        // Update balance for matching line
                        SavingAccount = newSaving;
                        line = accountFamilyId + "," + accountChildName + "," + SavingAccount + "," + CurrentAccount + "," + goal;
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
    public static void changeCurrentAccount(int familyId, String childName, int newCurrent) {
        String filePath = CSVFileWriter.filePath + "//account.csv";
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
                if (parts.length >= 5) { // Ensure the line has at least 3 columns
                    int accountFamilyId = Integer.parseInt(parts[0].trim());
                    String accountChildName = parts[1].trim();
                    int SavingAccount = Integer.parseInt(parts[2].trim());
                    int CurrentAccount = Integer.parseInt(parts[3].trim());
                    int goal = Integer.parseInt(parts[4].trim());

                    // Check if current line matches specified familyId and childName
                    if (accountFamilyId == familyId && accountChildName.equals(childName)) {
                        // Update balance for matching line
                        CurrentAccount = newCurrent;
                        line = accountFamilyId + "," + accountChildName + "," + SavingAccount + "," + CurrentAccount + "," + goal;
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

    public static void changeGoal(int familyId, String childName, int newGoal) {
        String filePath = CSVFileWriter.filePath + "//account.csv";
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
                if (parts.length >= 5) { // Ensure the line has at least 3 columns
                    int accountFamilyId = Integer.parseInt(parts[0].trim());
                    String accountChildName = parts[1].trim();
                    int SavingAccount = Integer.parseInt(parts[2].trim());
                    int CurrentAccount = Integer.parseInt(parts[3].trim());
                    int goal = Integer.parseInt(parts[4].trim());

                    // Check if current line matches specified familyId and childName
                    if (accountFamilyId == familyId && accountChildName.equals(childName)) {
                        // Update balance for matching line
                        goal = newGoal;
                        line = accountFamilyId + "," + accountChildName + "," + SavingAccount + "," + CurrentAccount + "," + goal;
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

    public static void main(String[] args) {
        changeGoal(123, "Alice", 30);
    }
}
