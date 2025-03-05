import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.interfaces.Repository;
import com.github.Lama591divine.BankSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankSystemTest {
    private BankSystem bankSystem;
    private Repository<User> userRepository;
    private Repository<Account> accountRepository;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(Repository.class);
        accountRepository = Mockito.mock(Repository.class);
        bankSystem = new BankSystem(userRepository, accountRepository);
    }

    @Test
    void withdrawMoney_SuccessfulWithdrawal() {
        User user = new User("user1", "John", 30, null, null);
        Account account = new Account("acc1", user);
        account.addBalance(500); // Начальный баланс 500

        when(accountRepository.getObject("acc1")).thenReturn(account);

        bankSystem.withdrawMoney("acc1", 200);

        assertEquals(300, account.getBalance()); // Проверяем, что баланс уменьшился корректно
    }

    @Test
    void withdrawMoney_InsufficientFunds_ShouldFail() {
        User user = new User("user2", "Jane", 28, null, null);
        Account account = new Account("acc2", user);
        account.addBalance(100); // Баланс 100

        when(accountRepository.getObject("acc2")).thenReturn(account);

        Exception exception = assertThrows(RuntimeException.class, () -> bankSystem.withdrawMoney("acc2", 200));

        assertEquals("Withdrawal error: insufficient funds", exception.getMessage());
    }

    @Test
    void depositMoney_SuccessfulDeposit() {
        User user = new User("user3", "Mike", 35, null, null);
        Account account = new Account("acc3", user);
        account.addBalance(300);

        when(accountRepository.getObject("acc3")).thenReturn(account);

        bankSystem.depositMoney("acc3", 400);

        assertEquals(700, account.getBalance()); // Проверяем, что баланс увеличился корректно
    }
}
