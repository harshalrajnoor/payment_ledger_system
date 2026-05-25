package com.harshal.paymentLedgerSystem.wallet.entity;

import com.harshal.paymentLedgerSystem.common.enums.Currency;
import com.harshal.paymentLedgerSystem.common.enums.ErrorCode;
import com.harshal.paymentLedgerSystem.common.exception.BusinessException;
import com.harshal.paymentLedgerSystem.common.exception.InsufficientBalanceException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private Long userId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalletStatus status;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static Wallet createNew(Long userId, Currency currency) {

        Wallet wallet = new Wallet();
        wallet.userId = userId;
        wallet.balance = BigDecimal.ZERO;
        wallet.currency = currency;
        wallet.status = WalletStatus.ACTIVE;

        return wallet;
    }

    public void credit(BigDecimal amount) {
        validateActiveStatus();
        validatePositiveBalance(amount);
        this.balance = this.balance.add(amount);
    }

    public void debit(BigDecimal amount) {
        validateActiveStatus();
        validatePositiveBalance(amount);
        validateSufficientBalance(amount);
        this.balance = this.balance.subtract(amount);
    }

    public boolean isActive() {
        return this.status == WalletStatus.ACTIVE;
    }

    private void validateActiveStatus() {
        if (!isActive()) {
            throw new BusinessException(
                    ErrorCode.WALLET_INACTIVE,
                    "Wallet is not active"
            );
        }
    }

    private void validatePositiveBalance(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(
                    ErrorCode.VALIDATION_ERROR,
                    "Amount must be greater than 0"
            );
        }
    }

    private void validateSufficientBalance(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException(
                    "Insufficient Balance, Available: " + this.balance
            );
        }
    }
}
