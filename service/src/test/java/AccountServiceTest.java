import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.User;
import com.github.Lama591divine.interfaces.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest{
    private Repository<Account> accountRepository;
    private User user;
    private Account account;

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(Repository.class);
        user = new User("testUser", "Test User", 25, null, null);
        account = new Account("12345", user);

        when(accountRepository.getObject("12345")).thenReturn(account);
    }

    @Test
    void testDepositMoney() {
        Account acc = accountRepository.getObject("12345");
        acc.addBalance(500);

        assertEquals(500, acc.getBalance(), "Баланс должен увеличиться на 500");
        verify(accountRepository, never()).remove(acc);
    }

    @Test
    void testDepositNegativeAmount() {
        Account acc = accountRepository.getObject("12345");
        acc.addBalance(-100);

        assertEquals(0, acc.getBalance(), "Баланс не должен измениться при отрицательном пополнении");
        verify(accountRepository, never()).remove(acc);
    }

    @Test
    void testWithdrawWithSufficientBalance() {
        Account acc = accountRepository.getObject("12345");
        acc.addBalance(1000);
        acc.withDraw(500);

        assertEquals(500, acc.getBalance(), "После снятия 500 баланс должен быть 500");
        verify(accountRepository, never()).remove(acc);
    }

    @Test
    void testWithdrawWithInsufficientBalance() {
        Account acc = accountRepository.getObject("12345");
        acc.withDraw(500);

        assertEquals(0, acc.getBalance(), "При недостаточном балансе сумма не должна измениться");
        verify(accountRepository, never()).remove(acc);
    }

    @Test
    void testWithdrawNegativeAmount() {
        Account acc = accountRepository.getObject("12345");
        acc.withDraw(-200);

        assertEquals(0, acc.getBalance(), "Баланс не должен изменяться при попытке снять отрицательную сумму");
        verify(accountRepository, never()).remove(acc);
    }

    @Test
    void testRepositoryAddMethodCalled() {
        accountRepository.add(account);

        verify(accountRepository, times(1)).add(account);
    }

    @Test
    void testRepositoryRemoveMethodCalled() {
        accountRepository.remove(account);

        verify(accountRepository, times(1)).remove(account);
    }
}
