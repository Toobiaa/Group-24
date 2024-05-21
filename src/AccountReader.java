import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class AccountReader{


    public static ArrayList<account> showAccount(int familyId, String childName){
        String filePath = CSVFileWriter.filePath + "//account.csv";
        ArrayList<account> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) { // Ensure the line has at least 7 columns
                    try {
                        int accountFamilyId = Integer.parseInt(parts[0].trim());
                        String accountChildName = parts[1].trim();
                        int SavingAccout = Integer.parseInt(parts[2].trim());
                        int CurrentAccout = Integer.parseInt(parts[3].trim());
                        int goal = Integer.parseInt(parts[4].trim());

                        if (accountFamilyId == familyId && accountChildName.equals(childName)) {
                            account account = new account(accountFamilyId, accountChildName, SavingAccout, CurrentAccout, goal);
                            accounts.add(account);
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
        return accounts;
    }

    public static ArrayList<account> showFamilyAccount(int familyId){
        String filePath = CSVFileWriter.filePath + "//account.csv";
        ArrayList<account> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) { // Ensure the line has at least 7 columns
                    try {
                        int accountFamilyId = Integer.parseInt(parts[0].trim());
                        String accountChildName = parts[1].trim();
                        int SavingAccout = Integer.parseInt(parts[2].trim());
                        int CurrentAccout = Integer.parseInt(parts[3].trim());
                        int goal = Integer.parseInt(parts[4].trim());

                        if (accountFamilyId == familyId) {
                            account account = new account(accountFamilyId, accountChildName, SavingAccout, CurrentAccout, goal);
                            accounts.add(account);
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
        return accounts;
    }
}