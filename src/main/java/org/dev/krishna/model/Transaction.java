package org.dev.krishna.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String id;
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
    private String details;
    private String time;
}
