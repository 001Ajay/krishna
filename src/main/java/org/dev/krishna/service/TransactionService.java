package org.dev.krishna.service;


import org.dev.krishna.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends DbFileService<Transaction> implements CrudService<Transaction>{

    @Override
    protected DB_FILE dBFileType() {
        return DB_FILE.TRANSACTION;
    }

    @Override
    protected Class<Transaction> getFileTypeClass() {
        return Transaction.class;
    }

    @Override
    protected String getId() {
        return "T"+System.currentTimeMillis();
    }
}
