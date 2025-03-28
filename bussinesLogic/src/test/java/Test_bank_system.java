import com.github.Lama591divine.DTO.DtoAccount;
import com.github.Lama591divine.repositoryes.InMemoryAccountRepository;
import com.github.Lama591divine.repositoryes.InMemoryUserRepository;
import com.github.Lama591divine.systems.AccountSystem;
import com.github.Lama591divine.systems.UserSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

import org.mockito.ArgumentCaptor;


import java.util.ArrayList;

public class Test_bank_system {
    AccountSystem accountSystem;
    UserSystem userSystem;
    InMemoryUserRepository userRepository;
    InMemoryAccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        this.userRepository = Mockito.mock(InMemoryUserRepository.class);
        this.accountRepository = Mockito.mock(InMemoryAccountRepository.class);
        accountSystem = new AccountSystem(userRepository, accountRepository);
        userSystem = new UserSystem(userRepository);
    }

    @Test
    void deposit() {
        DtoAccount dtoAccount = new DtoAccount("1", 5000, new ArrayList<>(), "1");
        when(accountRepository.getObject("1")).thenReturn(dtoAccount);

        accountSystem.depositMoney("1", 2000);

        ArgumentCaptor<DtoAccount> accountCaptor = ArgumentCaptor.forClass(DtoAccount.class);
        verify(accountRepository).add(accountCaptor.capture());

        DtoAccount savedAccount = accountCaptor.getValue();
        assertEquals(7000, savedAccount.balance());
    }

    @Test
    void withdraw() {
        DtoAccount dtoAccount = new DtoAccount("1", 5000, new ArrayList<>(), "1");
        when(accountRepository.getObject("1")).thenReturn(dtoAccount);

        accountSystem.withdrawMoney("1", 2000);

        ArgumentCaptor<DtoAccount> accountCaptor = ArgumentCaptor.forClass(DtoAccount.class);
        verify(accountRepository).add(accountCaptor.capture());

        DtoAccount savedAccount = accountCaptor.getValue();
        assertEquals(3000, savedAccount.balance());
    }
    @Test
    void NotEnoughFunds() {
        DtoAccount dtoAccount = new DtoAccount("1", 1000, new ArrayList<>(), "1");
        when(accountRepository.getObject("1")).thenReturn(dtoAccount);

        accountSystem.withdrawMoney("1", 2000);

        assertEquals(1000, dtoAccount.balance());
    }
}
