package com.mohammadyousefi.ketabcheh.profile;

import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile findByUserId(Long userId) {
        Optional<Profile> optionalProfile = profileRepository.findByUser_Id(userId);
        if (optionalProfile.isEmpty())
            throw new NotFoundException(ErrorMessages.notFound("user"));
        return optionalProfile.get();
    }

    @Override
    public String chargeWallet(Long userId, WalletDto walletDto) {
        Profile profile = findByUserId(userId);
        profile.setWallet(profile.getWallet() + walletDto.getWallet());
        profileRepository.save(profile);
        return "successfully charged";
    }
}
