package com.harshal.paymentLedgerSystem.wallet.service;

import com.harshal.paymentLedgerSystem.common.enums.ErrorCode;
import com.harshal.paymentLedgerSystem.common.exception.BusinessException;
import com.harshal.paymentLedgerSystem.common.exception.ResourceNotFoundException;
import com.harshal.paymentLedgerSystem.wallet.dto.CreateWalletRequest;
import com.harshal.paymentLedgerSystem.wallet.dto.DepositWalletRequest;
import com.harshal.paymentLedgerSystem.wallet.dto.WalletResponse;
import com.harshal.paymentLedgerSystem.wallet.entity.Wallet;
import com.harshal.paymentLedgerSystem.wallet.mapper.WalletMapper;
import com.harshal.paymentLedgerSystem.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request) {
        validateUserDoesNotHaveWallet(request.getUserId());
        Wallet wallet = Wallet.createNew(request.getUserId(), request.getCurrency());
        Wallet savedWallet = walletRepository.save(wallet);
        return walletMapper.toResponse(savedWallet);
    }

    @Transactional(readOnly = true)
    public WalletResponse getWallet(Long walletId) {
        Wallet wallet = findWalletById(walletId);
        return walletMapper.toResponse(wallet);
    }

    @Transactional
    public WalletResponse deposit(Long walletId, DepositWalletRequest request) {
        Wallet wallet = findWalletById(walletId);
        validateCurrencyMatch(wallet, request);
        wallet.credit(request.getAmount());
        Wallet savedWallet = walletRepository.save(wallet);
        return walletMapper.toResponse(savedWallet);
    }

    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: " + walletId));
    }

    private void validateCurrencyMatch(Wallet wallet, DepositWalletRequest request) {
        if (!wallet.getCurrency().equals(request.getCurrency())) {
            throw new BusinessException(ErrorCode.CURRENCY_MISMATCH, "Currency mismatch, Wallet currency is " + wallet.getCurrency());
        }
    }

    private void validateUserDoesNotHaveWallet(Long userId) {
        if (walletRepository.existsByUserId(userId)) {
            throw new BusinessException(ErrorCode.DUPLICATE_REQUEST, "Wallet already exists for user: " + userId);
        }
    }
}
