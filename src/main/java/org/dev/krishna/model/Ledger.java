package org.dev.krishna.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class Ledger {
    private BigDecimal debitAmount;
    private List<LedgerEntry> debit;
    private BigDecimal creditAmount;
    private List<LedgerEntry> credit;
}
