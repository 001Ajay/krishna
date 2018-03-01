package org.dev.krishna.controller;

import org.dev.krishna.model.Account;
import org.dev.krishna.model.ResponseEntity;
import org.dev.krishna.service.AccountService;
import org.dev.krishna.service.CrudService;
import org.dev.krishna.service.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
	
    @Override
    protected Account preAddHook(Account account) {
        account.setId(service.getId());
        return account;
    }

    @PostMapping("/addAll")
    public @ResponseBody
    ResponseEntity addAll(@RequestBody List<Account> accounts){
        Timer timer = new Timer();
        accounts.forEach(acc -> add(acc));
        System.out.println(timer.readTimer());
        return success("Added all accounts");
    }

}
