import static org.junit.jupiter.api.Assertions.*;

import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.entities.Gender;
import com.github.Lama591divine.entities.HairColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountServiceTest {

    private Account account;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "Test Name", 30, Gender.MALE, HairColor.BLACK);
        account = new Account("12345", user);
    }

    @Test
    void withdrawMoney_SufficientBalance_ShouldUpdateBalance() {
        account.addBalance(1000);
        assertEquals(1000, account.getBalance());

        account.withDraw(500);
        assertEquals(500, account.getBalance());

    }

    @Test
    void withdrawMoney_InsufficientBalance_ShouldNotAllowWithdrawal() {
        account.addBalance(300);
        assertEquals(300, account.getBalance());

        account.withDraw(500);
        assertEquals(300, account.getBalance());

    }

    @Test
    void depositMoney_ShouldUpdateBalance() {
        account.addBalance(500);
        assertEquals(500, account.getBalance());

    }
}