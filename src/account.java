import java.util.ArrayList;

public class account{
    int familyId;
    String childName;
    int SavingAccount;
    int CurrentAccount;
    int goal;

    public account( int familyId, String childName, int SavingAccout, int CurrentAccout, int goal){
        this.familyId = familyId;
        this.childName = childName;
        this.SavingAccount = SavingAccout;
        this.CurrentAccount = CurrentAccout;
        this.goal = goal;
    }
    public static int[] withdraw(int familyId,String name,int money){
        ArrayList<account> account = AccountReader.showAccount(familyId,name);
        for (account t : account) {
            if (t.getFamilyId() == familyId) {
                System.out.println(1);
                if(t.getSavingAccount()<money){
                    int[] Balance = {t.getSavingAccount(),t.getCurrentAccount()};
                    return Balance;
                }else{
                    t.setSavingAccount(t.getSavingAccount()-money);
                    t.setCurrentAccount(t.getCurrentAccount()+money);
                    changeAccountDetail.changeSavingAccount(familyId,name,t.getSavingAccount());
                    changeAccountDetail.changeCurrentAccount(familyId,name,t.getCurrentAccount());
                    int[] Balance = {t.getSavingAccount(),t.getCurrentAccount()};
                    return Balance;
                }
            }
        }
        return new int[0];
    }

    public static int consumption(int familyId,String name,int money){

        ArrayList<account> account = AccountReader.showAccount(familyId,name);
        for (account t : account) {
            if (t.getFamilyId() == familyId) {
                if(t.getSavingAccount()<money){

                }else{
                    t.setCurrentAccount(t.getCurrentAccount()-money);
                    changeAccountDetail.changeCurrentAccount(familyId,name,t.getCurrentAccount());
                    int Balance = t.getCurrentAccount();
                    return Balance;
                }
            }
        }
        return 0;
    }
    public static int save(int familyId,String name,int money){
        ArrayList<account> account = AccountReader.showAccount(familyId,name);
        for (account t : account) {
            if (t.getFamilyId() == familyId) {
                t.setSavingAccount(t.getSavingAccount()+money);
                changeAccountDetail.changeSavingAccount(familyId,name,t.getSavingAccount());
                return t.getSavingAccount();
            }
        }
        return 0;
    }


    public static void changeGoal(int familyId,String name,int newgoal){
        changeAccountDetail.changeGoal(familyId,name,newgoal);
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public int getSavingAccount() {
        return SavingAccount;
    }

    public void setSavingAccount(int savingAccount) {
        SavingAccount = savingAccount;
    }

    public int getCurrentAccount() {
        return CurrentAccount;
    }

    public void setCurrentAccount(int currentAccount) {
        CurrentAccount = currentAccount;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public account() {
    }
}