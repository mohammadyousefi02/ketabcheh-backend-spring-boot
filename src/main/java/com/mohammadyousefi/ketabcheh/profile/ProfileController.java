package com.mohammadyousefi.ketabcheh.profile;

import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/charge-wallet")
    @Authorization
    public Response<String> chargeWallet(HttpServletRequest request, @RequestBody WalletDto walletDto) {
        Long userId = (Long) request.getAttribute("userId");
        return new Response<>(profileService.chargeWallet(userId, walletDto));
    }
}
