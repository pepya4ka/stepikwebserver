package account.impl;

import account.AccountServer;

public class AccountServerImpl implements AccountServer {

    private int usersLimit;

    public AccountServerImpl() {
        usersLimit = 10;
    }

    public AccountServerImpl(int usersLimit) {
        this.usersLimit = usersLimit;
    }

    @Override
    public int getUsersLimit() {
        return usersLimit;
    }

    @Override
    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit;
    }
}
