package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.TaskDAO;
import at.codersbay.java.taskapp.rest.DAO.UserDAO;
import at.codersbay.java.taskapp.rest.entities.Task;
import at.codersbay.java.taskapp.rest.entities.User;
import at.codersbay.java.taskapp.rest.DTO.*;
import at.codersbay.java.taskapp.rest.exceptions.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class TaskServices {

    private final TaskDAO taskDAO;
    private final UserDAO userDAO;  // Added UserDAO as a dependency

    @Autowired
    public TaskServices(TaskDAO taskDAO, UserDAO userDAO) { // Modified constructor to take UserDAO
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
    }

    public Task createTask(TaskCreationRequest taskRequest) throws UserNotFoundException {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        task.setDone(taskRequest.isDone());

        Set<Long> userIds = taskRequest.getUserIds();
        List<User> users = new ArrayList<>();
        for (Long userId : userIds) {
            users.add(userDAO.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userId)));
        }

        task.setUsers(new HashSet<>(users));

        return taskDAO.save(task);
    }

    public Task getTaskByTaskID(Long id) throws TaskNotFoundException {
        if (id == null) {
            throw new TaskNotFoundException("Id is null");
        }
        return taskDAO.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public Task deleteTaskByTaskID(Long id) throws TaskNotFoundException {
        Task task = getTaskByTaskID(id);
        taskDAO.delete(task);
        return task;
    }

    public Task updateTaskByTaskID(Long id, Task task) throws TaskNotFoundException {
        if (task == null) {
            throw new IllegalArgumentException("Task is null");
        }
        Task existingTask = getTaskByTaskID(id);

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setDone(task.isDone());

        return taskDAO.save(existingTask);
    }

    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }
}
