package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.TaskDAO;
import at.codersbay.java.taskapp.rest.DAO.UserDAO;
import at.codersbay.java.taskapp.rest.entities.Task;
import at.codersbay.java.taskapp.rest.entities.User;
import at.codersbay.java.taskapp.rest.DTO.*;
import at.codersbay.java.taskapp.rest.exceptions.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskServices {

    private final TaskDAO taskDAO;
    private final UserDAO userDAO;

    @Autowired
    public TaskServices(TaskDAO taskDAO, UserDAO userDAO) {
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
    }

    @Transactional
    public Task createTask(TaskCreationDTO taskDTO) throws TaskAlreadyExistsException, UserNotFoundException {
        if (taskDTO == null) {
            throw new IllegalArgumentException("Task data is null");
        }

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setDone(taskDTO.isDone());

        Set<Long> userIds = taskDTO.getUserIDs();

        Set<User> users = new HashSet<>(userDAO.findAllById(userIds));
        if(users.size() != userIds.size()) {
            throw new UserNotFoundException("One or more users not found");
        }

        task.setUsers(users);

        for(User user : users) {
            user.getTasks().add(task);
            userDAO.save(user);
        }

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