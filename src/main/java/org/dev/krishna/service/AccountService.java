package org.dev.krishna.service;

import org.dev.krishna.model.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends DbFileService<Account> implements CrudService<Account>{

    @Override
    protected DB_FILE dBFileType() {
        return DB_FILE.ACCOUNT;
    }

    @Override
    protected Class<Account> getFileTypeClass() {
        return Account.class;
    }

    @Override
    protected String getId() { return "A"+System.currentTimeMillis(); }
}