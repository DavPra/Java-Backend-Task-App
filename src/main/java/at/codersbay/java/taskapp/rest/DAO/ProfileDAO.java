package at.codersbay.java.taskapp.rest.DAO;

import at.codersbay.java.taskapp.rest.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDAO extends JpaRepository<Profile, Long>{

    @Query("SELECT p FROM Profile p WHERE p.id = :id")
    Profile findByUserId(Long id);
}
