package at.codersbay.java.taskapp.rest.DAO;

import at.codersbay.java.taskapp.rest.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDAO extends JpaRepository<Task, Long>  {
}

// interface for the task repository
