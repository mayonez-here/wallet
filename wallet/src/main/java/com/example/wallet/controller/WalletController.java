package com.example.wallet.controller;

import com.example.wallet.dto.WalletRequest;
import com.example.wallet.entity.Wallet;
import com.example.wallet.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @PostMapping("/wallet")
    public Wallet operate(@RequestBody WalletRequest request) {
        return service.operate(request);
    }

    @GetMapping("/wallets/{id}")
    public Wallet get(@PathVariable UUID id) {
        return service.get(id);
    }
}
