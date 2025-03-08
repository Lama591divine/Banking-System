package com.github.Lama591divine;

import com.github.Lama591divine.DTO.DtoAccount;
import com.github.Lama591divine.DTO.DtoUser;
import com.github.Lama591divine.entities.Account;
import com.github.Lama591divine.entities.User;

public class ConverterDTO {

    public DtoAccount toDtoAccount(Account account) {
        String ownerid = account.getOwner();
        DtoAccount account1 = new DtoAccount(account.getId(), account.getBalance(), account.getTransactions(), ownerid);
        return account1;
    }

    public Account toAccount(DtoAccount dtoAccount) {
        String ownerid = dtoAccount.getOwner();
        Account account = new Account(dtoAccount.getId(), dtoAccount.getBalance(), dtoAccount.getTransactions(), ownerid);
        return account;
    }

    public User toUser(DtoUser dtoUser) {
        User user = new User(dtoUser.getLogin(), dtoUser.getName(), dtoUser.getAge(), dtoUser.getGender(), dtoUser.getHairColor(), dtoUser.getFriends());

        return user;
    }

    public DtoUser toDtoUser(User user) {
        DtoUser dtoUser = new DtoUser(user.getLogin(), user.getName(), user.getAge(), user.getGender(), user.getHairColor(), user.getFriends());

        return dtoUser;
    }
}
