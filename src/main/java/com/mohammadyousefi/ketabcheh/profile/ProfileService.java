package com.mohammadyousefi.ketabcheh.profile;

public interface ProfileService {
    Profile findByUserId(Long userId);
    String chargeWallet(Long userId, WalletDto walletDto);
}
