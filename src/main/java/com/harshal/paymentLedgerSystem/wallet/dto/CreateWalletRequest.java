package com.harshal.paymentLedgerSystem.wallet.dto;

import com.harshal.paymentLedgerSystem.common.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateWalletRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Currency is required")
    private Currency currency;
}
