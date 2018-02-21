package org.dev.krishna.controller;

import org.dev.krishna.model.Account;
import org.dev.krishna.service.AccountService;
import org.dev.krishna.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
{
	"id":"",
	"name":"BankBalance",
	"type":"Asset"
}
*/

@Controller
@RequestMapping("/account")
public class AccountController extends CrudController<Account>{

    @Autowired
    private AccountService service;

    @Override
    protected CrudService<Account> getService() {
        return service;
    }
}