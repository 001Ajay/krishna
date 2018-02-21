package org.dev.krishna.controller;

import org.dev.krishna.model.Transaction;
import org.dev.krishna.service.CrudService;
import org.dev.krishna.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction")
public class TransactionController extends CrudController<Transaction>{

    @Autowired
    private TransactionService service;

    @Override
    protected CrudService<Transaction> getService() {
        return service;
    }
}
