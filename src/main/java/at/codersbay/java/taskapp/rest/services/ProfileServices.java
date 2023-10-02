package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.*;
import at.codersbay.java.taskapp.rest.entities.*;
import at.codersbay.java.taskapp.rest.exceptions.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class ProfileServices {

    public ProfileServices() {
    }

    @Autowired
    ProfileDAO profileDAO;

    public Profile createProfile (Profile profile) throws ProfileNotFoundException {
        if (profile == null) {
            throw new ProfileNotFoundException("Profile is null");
        }
        if (profile.getId() != null) {
            throw new ProfileNotFoundException("Profile already exists");
        }
        try {
            profileDAO.save(profile);
        } catch (Exception e) {
            throw new ProfileNotFoundException("Profile could not be created");
        }
        return profile;
    }

    public List<Profile> getAllProfiles() {

        return profileDAO.findAll();
    }

    public Profile getProfileByID (Long id) throws ProfileNotFoundException {
        if (id == null) {
            throw new ProfileNotFoundException("Id is null");
        }
        Optional<Profile> profile = profileDAO.findById(id);
        if (!profile.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        return profile.get();
    }

    public Profile getProfileByUserID (Long id) throws ProfileNotFoundException {
        if (id == null) {
            throw new ProfileNotFoundException("Id is null");
        }
        Optional<Profile> profile = Optional.ofNullable(profileDAO.findByUserId(id));
        if (!profile.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        return profile.get();
    }

    public Profile updateProfileByProfileId (Long id, Profile profile) throws ProfileNotFoundException {
        if (id == null) {
            throw new ProfileNotFoundException("Id is null");
        }
        if (profile == null) {
            throw new ProfileNotFoundException("Profile is null");
        }
        Optional<Profile> profileOptional = profileDAO.findById(id);
        if (!profileOptional.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        profile.setId(id);
        try {
            profileDAO.save(profile);
        } catch (Exception e) {
            throw new ProfileNotFoundException("Profile could not be updated");
        }
        return profile;
    }

    public Profile updateProfileByUserID (Long id, Profile profile) throws ProfileNotFoundException {
        if (id == null) {
            throw new ProfileNotFoundException("Id is null");
        }
        if (profile == null) {
            throw new ProfileNotFoundException("Profile is null");
        }
        Optional<Profile> profileOptional = Optional.ofNullable(profileDAO.findByUserId(id));
        if (!profileOptional.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        profile.setId(profileOptional.get().getId());
        try {
            profileDAO.save(profile);
        } catch (Exception e) {
            throw new ProfileNotFoundException("Profile could not be updated");
        }
        return profile;
    }

    public Profile deleteProfileByID (Long id) throws ProfileNotFoundException {
        if (id == null) {
            throw new ProfileNotFoundException("Id is null");
        }
        Optional<Profile> profile = profileDAO.findById(id);
        if (!profile.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        try {
            profileDAO.delete(profile.get());
        } catch (Exception e) {
            throw new ProfileNotFoundException("Profile could not be deleted");
        }
        return profile.get();
    }

    public Profile deleteProfileByUserID (Long id) throws ProfileNotFoundException {
        if (id == null) {
            throw new ProfileNotFoundException("Id is null");
        }
        Optional<Profile> profile = Optional.ofNullable(profileDAO.findByUserId(id));
        if (!profile.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        try {
            profileDAO.delete(profile.get());
        } catch (Exception e) {
            throw new ProfileNotFoundException("Profile could not be deleted");
        }
        return profile.get();
    }

    public Profile linkProfileIDtoUserID (Long id, Profile profile) throws ProfileNotFoundException {
        if (id == null) {
            throw new ProfileNotFoundException("Id is null");
        }
        if (profile == null) {
            throw new ProfileNotFoundException("Profile is null");
        }
        Optional<Profile> profileOptional = profileDAO.findById(id);
        if (!profileOptional.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        profile.setId(id);
        try {
            profileDAO.save(profile);
        } catch (Exception e) {
            throw new ProfileNotFoundException("Profile could not be updated");
        }
        return profile;
    }


}
