package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.*;
import at.codersbay.java.taskapp.rest.entities.*;
import at.codersbay.java.taskapp.rest.exceptions.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class TaskServices {

    public TaskServices() {
    }

    @Autowired
    TaskDAO taskDAO;

    public Task createTask (Task task) throws TaskNotFoundException {
        if (task == null) {
            throw new TaskNotFoundException("Task is null");
        }
        if (task.getId() != null) {
            throw new TaskNotFoundException("Task already exists");
        }
        try {
            taskDAO.save(task);
        } catch (Exception e) {
            throw new TaskNotFoundException("Task could not be created");
        }
        return task;
    }

    public Task getTaskByTaskID(Long id) throws TaskNotFoundException {
        if (id == null) {
            throw new TaskNotFoundException("Id is null");
        }
        Optional<Task> task = taskDAO.findById(id);
        if (!task.isPresent()) {
            throw new TaskNotFoundException("Task not found");
        }
        return task.get();
    }

    public Task deleteTaskByTaskID(Long id) throws TaskNotFoundException {
        if (id == null) {
            throw new TaskNotFoundException("Id is null");
        }
        Optional<Task> task = taskDAO.findById(id);
        if (!task.isPresent()) {
            throw new TaskNotFoundException("Task not found");
        }
        try {
            taskDAO.delete(task.get());
        } catch (Exception e) {
            throw new TaskNotFoundException("Task could not be deleted");
        }
        return task.get();
    }

    public Task updateTaskByTaskID(Long id, Task task) throws TaskNotFoundException {
        if (id == null) {
            throw new TaskNotFoundException("Id is null");
        }
        Optional<Task> taskToUpdate = taskDAO.findById(id);
        if (!taskToUpdate.isPresent()) {
            throw new TaskNotFoundException("Task not found");
        }
        if (task == null) {
            throw new TaskNotFoundException("Task is null");
        }
        if (task.getId() != null) {
            throw new TaskNotFoundException("Task already exists");
        }
        try {
            taskDAO.save(task);
        } catch (Exception e) {
            throw new TaskNotFoundException("Task could not be updated");
        }
        return task;
    }

    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }
}
