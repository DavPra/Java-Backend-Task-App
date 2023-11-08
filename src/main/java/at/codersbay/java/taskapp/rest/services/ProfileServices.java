package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.ProfileDAO;
import at.codersbay.java.taskapp.rest.DAO.UserDAO;
import at.codersbay.java.taskapp.rest.entities.Profile;
import at.codersbay.java.taskapp.rest.entities.User;
import at.codersbay.java.taskapp.rest.exceptions.*;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

//Services to handle the profiles of Users.

@Service
public class ProfileServices {

    private final ProfileDAO profileDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    public ProfileServices(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    public Profile createAndLinkProfileToUser(Long userId, Profile profile) throws PrimaryIdNullOrEmptyException, ProfileNotFoundException, UserNotFoundException, ProfileAlreadyExistsException, IOException {
        if (profile == null) {
            throw new PrimaryIdNullOrEmptyException("Profile is null");
        }

        if (profile.getId() != null) {
            throw new ProfileAlreadyExistsException("Profile already exists");
        }

        User user = userDAO.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userId));

        profile.setUser(user);
        user.setProfile(profile);
        userDAO.save(user);
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

    public Profile linkUserToProfile(Long id, Profile profile) throws ProfileNotFoundException {
        if (profile == null) {
            throw new IllegalArgumentException("Profile is null");
        }
        Profile existingProfile = getProfileByID(id);
        existingProfile.setUser(profile.getUser());

        return profileDAO.save(existingProfile);
    }
}
