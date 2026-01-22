package com.example.wallet.service;

import com.example.wallet.dto.OperationType;
import com.example.wallet.dto.WalletRequest;
import com.example.wallet.entity.Wallet;
import com.example.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository repo;

    public WalletService(WalletRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Wallet operate(WalletRequest req) {
        Wallet wallet = repo.findByIdForUpdate(req.getWalletId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (req.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance().compareTo(req.getAmount()) < 0) {
                throw new RuntimeException("Insufficient funds");
            }
            wallet.setBalance(wallet.getBalance().subtract(req.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().add(req.getAmount()));
        }

        return repo.save(wallet);
    }

    public Wallet get(UUID id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }
}
