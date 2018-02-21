package org.dev.krishna.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class LedgerEntry {
    private Account counterParty;
    private String details;
    private String date;
    private BigDecimal amount;
}
