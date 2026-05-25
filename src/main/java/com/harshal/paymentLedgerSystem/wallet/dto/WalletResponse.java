package com.harshal.paymentLedgerSystem.wallet.dto;

import com.harshal.paymentLedgerSystem.common.enums.Currency;
import com.harshal.paymentLedgerSystem.wallet.entity.WalletStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Builder
public class WalletResponse {
    private final Long walletId;
    private final Long userId;
    private final BigDecimal balance;
    private final Currency currency;
    private final WalletStatus status;
    private final LocalDateTime createdAt;
}
