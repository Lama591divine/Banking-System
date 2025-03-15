package com.github.Lama591divine;

import com.github.Lama591divine.DTO.DtoAccount;
import com.github.Lama591divine.DTO.DtoUser;
import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.User;

/**
 * The {@code ConverterDTO} class provides methods for converting between entity objects and DTO objects.
 * It ensures seamless conversion between domain models and data transfer objects.
 */
public class ConverterDTO {

    /**
     * Converts an {@code Account} entity to a {@code DtoAccount}.
     *
     * @param account the account entity to convert
     * @return the corresponding {@code DtoAccount}
     */
    public DtoAccount toDtoAccount(Account account) {
        String ownerid = account.getOwner();
        DtoAccount account1 = new DtoAccount(account.getId(), account.getBalance(), account.getTransactions(), ownerid);
        return account1;
    }

    /**
     * Converts a {@code DtoAccount} to an {@code Account} entity.
     *
     * @param dtoAccount the DTO representation of an account
     * @return the corresponding {@code Account} entity
     */
    public Account toAccount(DtoAccount dtoAccount) {
        String ownerid = dtoAccount.getOwner();
        Account account = new Account(dtoAccount.getId(), dtoAccount.getBalance(), dtoAccount.getTransactions(), ownerid);
        return account;
    }

    /**
     * Converts a {@code DtoUser} to a {@code User} entity.
     *
     * @param dtoUser the DTO representation of a user
     * @return the corresponding {@code User} entity
     */
    public User toUser(DtoUser dtoUser) {
        User user = new User(dtoUser.getLogin(), dtoUser.getName(), dtoUser.getAge(), dtoUser.getGender(), dtoUser.getHairColor(), dtoUser.getFriends());

        return user;
    }


    /**
     * Converts a {@code User} entity to a {@code DtoUser}.
     *
     * @param user the user entity to convert
     * @return the corresponding {@code DtoUser}
     */
    public DtoUser toDtoUser(User user) {
        DtoUser dtoUser = new DtoUser(user.getLogin(), user.getName(), user.getAge(), user.getGender(), user.getHairColor(), user.getFriends());

        return dtoUser;
    }
}
