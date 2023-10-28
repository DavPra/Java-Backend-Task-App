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

    public TaskWithUserIdsDTO convertToDTO(Task task) {
        TaskWithUserIdsDTO dto = new TaskWithUserIdsDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setDone(task.isDone());
        dto.setUserIds(task.getUsers().stream().map(User::getId).collect(Collectors.toSet()));
        return dto;
    }

    public Task convertDtoToTask(TaskWithUserIdsDTO taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setDone(taskDto.isDone());


        Set<User> users = new HashSet<>(userDAO.findAllById(taskDto.getUserIds()));
        task.setUsers(users);

        return task;
    }


    @Transactional
    public TaskWithUserIdsDTO createTask(TaskCreationDTO taskDTO) throws TaskAlreadyExistsException, UserNotFoundException {
        // Validate the input
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

        task = taskDAO.save(task);

        for (User user : users) {
            user.getTasks().add(task);
            userDAO.save(user);
        }

        return convertToDTO(task);
    }


    public TaskWithUserIdsDTO getTaskByTaskID(Long id) throws TaskNotFoundException {
        Task task = taskDAO.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return convertToDTO(task);
    }

    public Task deleteTaskByID(Long taskId) throws TaskNotFoundException {
        Task task = taskDAO.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        for (User user : task.getUsers()) {
            user.getTasks().remove(task);
            userDAO.save(user);
        }
        task.setUsers(null);
        taskDAO.delete(task);
        return task;
    }

    @Transactional
    public Task updateTaskByTaskID(Long id, Task taskUpdates) throws TaskNotFoundException {
        if (taskUpdates == null) {
            throw new IllegalArgumentException("Task is null");
        }

        Task existingTask = taskDAO.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));

        existingTask.setId(taskDAO.findById(id).get().getId());
        existingTask.setTitle(taskUpdates.getTitle());
        existingTask.setDescription(taskUpdates.getDescription());
        existingTask.setDueDate(taskUpdates.getDueDate());
        existingTask.setDone(taskUpdates.isDone());

        return taskDAO.save(existingTask);
    }

    public List<TaskWithUserIdsDTO> getAllTasks() {
        List<Task> tasks = taskDAO.findAll();
        return tasks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
