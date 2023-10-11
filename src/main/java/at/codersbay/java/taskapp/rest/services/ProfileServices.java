package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.ProfileDAO;
import at.codersbay.java.taskapp.rest.entities.Profile;
import at.codersbay.java.taskapp.rest.exceptions.ProfileNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileServices {

    private final ProfileDAO profileDAO;

    @Autowired
    public ProfileServices(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    public Profile createProfile(Profile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Profile is null");
        }
        if (profile.getId() != null) {
            throw new IllegalStateException("Profile already exists");
        }
        return profileDAO.save(profile);
    }

    public List<Profile> getAllProfiles() {
        return profileDAO.findAll();
    }

    public Profile getProfileByID(Long id) throws ProfileNotFoundException {
        if (id == null) {
            throw new ProfileNotFoundException("Id is null");
        }
        return profileDAO.findById(id).orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
    }

    public Profile getProfileByUserID(Long id) throws ProfileNotFoundException {
        if (id == null) {
            throw new ProfileNotFoundException("Id is null");
        }
        return Optional.ofNullable(profileDAO.findByUserId(id))
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
    }

    public Profile updateProfileByUserID(Long userId, Profile profile) throws ProfileNotFoundException {
        if (profile == null) {
            throw new IllegalArgumentException("Profile is null");
        }
        Profile existingProfile = getProfileByUserID(userId);

        existingProfile.setBio(profile.getBio());
        existingProfile.setImage(profile.getImage());
        existingProfile.setUser(profile.getUser());

        return profileDAO.save(existingProfile);
    }

    public Profile deleteProfileByID(Long id) throws ProfileNotFoundException {
        Profile profile = getProfileByID(id);
        profileDAO.delete(profile);
        return profile;
    }

    public Profile deleteProfileByUserID(Long userId) throws ProfileNotFoundException {
        Profile profile = getProfileByUserID(userId);
        profileDAO.delete(profile);
        return profile;
    }

    public Profile linkProfileIDtoUserID(Long id, Profile profile) throws ProfileNotFoundException {
        if (profile == null) {
            throw new IllegalArgumentException("Profile is null");
        }
        Profile existingProfile = getProfileByID(id);

        existingProfile.setUser(profile.getUser());

        return profileDAO.save(existingProfile);
    }
}
