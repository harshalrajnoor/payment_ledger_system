package com.harshal.paymentLedgerSystem.wallet.controller;

import com.harshal.paymentLedgerSystem.common.response.ApiResponse;
import com.harshal.paymentLedgerSystem.wallet.dto.CreateWalletRequest;
import com.harshal.paymentLedgerSystem.wallet.dto.DepositWalletRequest;
import com.harshal.paymentLedgerSystem.wallet.dto.WalletResponse;
import com.harshal.paymentLedgerSystem.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<ApiResponse<WalletResponse>> createWallet(@RequestBody @Valid CreateWalletRequest request) {
        WalletResponse response = walletService.createWallet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @GetMapping(path = "/{wallet_id}")
    public ResponseEntity<ApiResponse<WalletResponse>> getWallet(@PathVariable(name = "wallet_id") Long walletId) {
        WalletResponse response = walletService.getWallet(walletId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping(path = "/{wallet_id}/deposit")
    public ResponseEntity<ApiResponse<WalletResponse>> deposit(@PathVariable Long walletId, @RequestBody @Valid DepositWalletRequest request) {
        WalletResponse response = walletService.deposit(walletId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
