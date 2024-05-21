import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AccountReaderTest {

    @Test
    public void testShowAccountWithValidData() {
        // 假设 CSV 文件中有以下数据
        // 8, kkk, 1000, 500, 1500
        // 此测试假设 CSV 文件恰好有这些数据
        ArrayList<account> accounts = AccountReader.showAccount(35327784,"wuboheng");
        assertEquals(1, accounts.size());
        account acc = accounts.get(0);
        assertEquals(35327784, acc.getFamilyId());
        assertEquals("wuboheng", acc.getChildName());
        assertEquals(11311, acc.getSavingAccount());
        assertEquals(862, acc.getCurrentAccount());
        assertEquals(88888888, acc.getGoal());
    }

    @Test
    public void testShowAccountWithNoMatchingEntry() {
        // 假设 CSV 文件中没有 id 为 999 的家庭
        ArrayList<account> accounts = AccountReader.showAccount(22222222, "wangxin");
        assertTrue(accounts.isEmpty());
    }

    @Test
    public void testShowFamilyAccountWithMultipleEntries() {
        // 假设 CSV 文件中有多条数据属于同一家庭ID
        // 8, kkkbbb, 1000, 500, 1500
        // 8, aaabbb, 2000, 1000, 2500
        ArrayList<account> accounts = AccountReader.showFamilyAccount(35327784);
        assertEquals(2, accounts.size());  // 检查是否读取了两个帐户
    }
}
