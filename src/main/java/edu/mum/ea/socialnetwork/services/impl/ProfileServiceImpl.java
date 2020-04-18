package edu.mum.ea.socialnetwork.services.impl;

import edu.mum.ea.socialnetwork.domain.Profile;
import edu.mum.ea.socialnetwork.repository.ProfileRepository;
import edu.mum.ea.socialnetwork.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Profile findById(Long id) {

        return profileRepository.findById(id).orElse(null);
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    // this function used in Following Controller to search all the users by first name
    @Override
    public List<Profile> searchProfiles(Long id, String name) {
        return profileRepository.searchProfiles(id,name);
    }

    @Override
    public List<Profile> unfollowedUsers(Long id) {
        return profileRepository.unfollowedUsers(id);
    }

    @Override
    public List<Profile> findTop5(Long id) {
        return profileRepository.Top5unfollowedUsers(id);
    }

    @Override
    public Profile findByEmail(String email) {
        Profile profile = profileRepository.findOneByEmail(email);
        return profile;
    }

}
