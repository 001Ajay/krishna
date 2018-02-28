package org.dev.krishna.controller;

import org.dev.krishna.model.Transaction;
import org.dev.krishna.service.AccountService;
import org.dev.krishna.service.CrudService;
import org.dev.krishna.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/transaction")
public class TransactionController extends CrudController<Transaction>{

    @Autowired
    private TransactionService service;

    @Autowired
    private AccountService accountService;

    @Override
    protected CrudService<Transaction> getService() {
        return service;
    }

    @Override
    protected Transaction preAddHook(Transaction transaction) {
        transaction.setId(service.getId());
        return transaction;
    }

    @PostMapping("/addEntries")
    public @ResponseBody ResponseEntity addEntries(@RequestBody List<Transaction> transactions){
        transactions.forEach(txn -> {
            Optional<String> fromAccountId = accountService.findIdByName(txn.getFromAccountId());
            if(fromAccountId.isPresent()) txn.setFromAccountId(fromAccountId.get());
            else System.err.println("Expected Account Name in place of fromAccountId.");
            Optional<String> toAccountId = accountService.findIdByName(txn.getToAccountId());
            if(toAccountId.isPresent()) txn.setToAccountId(toAccountId.get());
            else System.err.println("Expected Account Name in place of toAccountId.");
            add(txn);
        });
        return success("All transactions added successfully");
    }
}
