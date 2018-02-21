package org.dev.krishna.service;

import org.dev.krishna.model.Account;
import org.dev.krishna.model.Ledger;
import org.dev.krishna.model.LedgerEntry;
import org.dev.krishna.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    public Ledger getLedger(String accountId){
        Account account = accountService.find(accountId).get();
        String accountType = account.getType();
        if(accountType.equalsIgnoreCase("Personal"))
            return createLedgerForPersonalAccount(accountId);
        else if(accountType.equalsIgnoreCase("Real"))
            return createLedgerForRealAccount(accountId);
        else if(accountType.equalsIgnoreCase("Nominal"))
            return createLedgerForNominalAccount(accountId);
        else
            return new Ledger();
    }

    // Personal Account : Receiver -> Debit, Giver -> Credit
    // Personal account: These are the accounts with which a business does dealings.
    // These accounts are pertained to a firm, an individual etc. Examples are
    // Prepaid accounts, debtor account, XYZ ltd account etc.
    private Ledger createLedgerForPersonalAccount(String accountId){
        Ledger ledger = new Ledger();
        List<LedgerEntry> debitTransactions = getFromAccountTransactions(accountId);
        calculateDebit(debitTransactions, ledger);
        List<LedgerEntry> creditTransactions = getToAccountTransactions(accountId);
        calculateCredit(creditTransactions, ledger);
        return ledger;
    }

    // Real Account : Coming In -> Debit, Going Out -> Credit
    // Real account: These are the accounts of tangible and intangible assets.
    // Tangible: Land, building, furniture etc
    // Intangible: Good will, copyrights, patents etc
    private Ledger createLedgerForRealAccount(String accountId){
        Ledger ledger = new Ledger();
        List<LedgerEntry> debitTransactions = getToAccountTransactions(accountId);
        calculateDebit(debitTransactions, ledger);
        List<LedgerEntry> creditTransactions = getFromAccountTransactions(accountId);
        calculateCredit(creditTransactions, ledger);
        return ledger;
    }

    // Nominal Account :  Expenses and losses -> Debit, Incomes and gains -> Credit
    // Nominal account: Nominal account is an account that relates to
    // business expenses, loss, income and gains. Nominal accounts
    // doesn’t exist physically. Examples are purchase a/c, sales a/c, rent a/c, salary a/c.
    private Ledger createLedgerForNominalAccount(String accountId){
        Ledger ledger = new Ledger();
        List<LedgerEntry> debitTransactions = getFromAccountTransactions(accountId);
        calculateDebit(debitTransactions, ledger);
        List<LedgerEntry> creditTransactions = getToAccountTransactions(accountId);
        calculateCredit(creditTransactions, ledger);
        return ledger;
    }

    private void calculateDebit(List<LedgerEntry> entries, Ledger ledger){
        ledger.setDebitAmount(BigDecimal.ZERO);
        entries.forEach(txn -> {
            BigDecimal amount = ledger.getDebitAmount();
            ledger.setDebitAmount(amount.add(txn.getAmount()));
        });
    }

    private void calculateCredit(List<LedgerEntry> entries, Ledger ledger){
        ledger.setCreditAmount(BigDecimal.ZERO);
        entries.forEach(txn -> {
            BigDecimal amount = ledger.getCreditAmount();
            ledger.setCreditAmount(amount.add(txn.getAmount()));
        });
    }

    private List<LedgerEntry> getFromAccountTransactions(String accountId){
        return transactionService
                .findAll()
                .stream()
                .filter(txn -> txn.getFromAccountId().equals(accountId))
                .map(txn -> transactionLedgerEntryMapper(txn))
                .collect(Collectors.toList());
    }

    private List<LedgerEntry> getToAccountTransactions(String accountId){
        return transactionService
                .findAll()
                .stream()
                .filter(txn -> txn.getToAccountId().equals(accountId))
                .map(txn -> transactionLedgerEntryMapper(txn))
                .collect(Collectors.toList());
    }

    private LedgerEntry transactionLedgerEntryMapper(Transaction txn) {
        String cpAccountId = txn.getToAccountId();
        LedgerEntry entry = new LedgerEntry();
        entry.setCounterParty(accountService.find(cpAccountId).get());
        entry.setAmount(txn.getAmount());
        entry.setDate(txn.getTime());
        entry.setDetails(txn.getDetails());
        return entry;
    }
}