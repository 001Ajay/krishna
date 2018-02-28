package org.dev.krishna.service;

import org.dev.krishna.model.Account;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String getId() {
        return "AC"+System.currentTimeMillis();
    }

    private Map<String, Account> idEntityMap;
    private Map<String, String> nameIdMap;

    public AccountService(){
        List<Account> allAccounts = findAll();
        idEntityMap = allAccounts.stream()
                .collect(Collectors.toMap(a -> a.getId(), a -> a));
        nameIdMap = allAccounts.stream()
                .collect(Collectors.toMap(Account::getName, Account::getId));
    }

    public Optional<Account> findByName(String accountName){
        String accountId = nameIdMap.get(accountName);
        Account account = idEntityMap.get(accountId);
        if(account != null) return Optional.of(account);
        else return Optional.empty();
    }

    public Optional<String> findIdByName(String accountName){
        String accountId = nameIdMap.get(accountName);
        if(accountId != null) return Optional.of(accountId);
        else return Optional.empty();
    }
}
