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
        String ownerId = account.getOwner();
        return new DtoAccount(account.getId(), account.getBalance(), account.getTransactions(), ownerId);
    }

    /**
     * Converts a {@code DtoAccount} to an {@code Account} entity.
     *
     * @param dtoAccount the DTO representation of an account
     * @return the corresponding {@code Account} entity
     */
    public Account toAccount(DtoAccount dtoAccount) {
        String ownerId = dtoAccount.owner();
        return new Account(dtoAccount.id(), dtoAccount.balance(), dtoAccount.transactions(), ownerId);
    }

    /**
     * Converts a {@code DtoUser} to a {@code User} entity.
     *
     * @param dtoUser the DTO representation of a user
     * @return the corresponding {@code User} entity
     */
    public User toUser(DtoUser dtoUser) {
        return new User(dtoUser.login(), dtoUser.name(), dtoUser.age(), dtoUser.gender(), dtoUser.hairColor(), dtoUser.friends());
    }


    /**
     * Converts a {@code User} entity to a {@code DtoUser}.
     *
     * @param user the user entity to convert
     * @return the corresponding {@code DtoUser}
     */
    public DtoUser toDtoUser(User user) {
        return new DtoUser(user.getLogin(), user.getName(), user.getAge(), user.getGender(), user.getHairColor(), user.getFriends());
    }
}
