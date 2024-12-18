package com.example.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.request.UpdateTaskRequest;
import com.example.backend.dto.request.CreateTaskRequest;
import com.example.backend.dto.request.UpdateBigTaskRequest;
import com.example.backend.dto.response.BaseResponse;
import com.example.backend.dto.response.TaskInProgressAndEnded;
import com.example.backend.dto.response.TaskProgressResponse;
import com.example.backend.dto.response.TasksInDateResponse;
import com.example.backend.model.Task;
import com.example.backend.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;

  @GetMapping("/task/{id}")
  public ResponseEntity<BaseResponse<Task>> getTaskById(@PathVariable String id) {
    Task task = taskService.getTaskById(id);
    if (task == null) {
      return ResponseEntity.notFound().build();
    }
    BaseResponse<Task> response = new BaseResponse<>(true, "Fetched task successfully", task);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/task/date")
  public ResponseEntity<BaseResponse<TasksInDateResponse>> getTasksInDatetime(@RequestParam String userId,
      @RequestParam String time) {
    return ResponseEntity.ok(taskService.getTasksInDateTime(userId, time));
  }

  @PutMapping("/task/{id}")
  public ResponseEntity<BaseResponse<Task>> updateTask(@PathVariable String id,
      @RequestBody UpdateTaskRequest request) {
    return ResponseEntity.ok(taskService.updateTask(id, request.getTime(), request.getStatus()));
  }

  @PostMapping("/task")
  public ResponseEntity<BaseResponse<Task>> createTask(@RequestBody CreateTaskRequest req) {
    return ResponseEntity.ok(taskService.createTask(req));
  }

  @GetMapping("/task/filter/{userId}")
  public ResponseEntity<BaseResponse<TaskInProgressAndEnded>> getTaskByStatusNow(@PathVariable String userId) {
    return ResponseEntity.ok(taskService.getTaskInProgressAndEnded(userId));
  }

  @GetMapping("/task/in-progress")
  public ResponseEntity<Page<Task>> getInProgressTasks(
      @RequestParam String userId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Task> tasks = taskService.getInProgressTasks(userId, pageable);
    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/task/ended")
  public ResponseEntity<Page<Task>> getEndedTasks(
      @RequestParam String userId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Task> tasks = taskService.getEndedTasks(userId, pageable);
    return ResponseEntity.ok(tasks);
  }

  @PutMapping("/task")
  public ResponseEntity<BaseResponse<Task>> updateBigTask(@RequestBody UpdateBigTaskRequest req) {
    return ResponseEntity.ok(taskService.updateBigTask(req));
  }

  @DeleteMapping("/task/{taskId}")
  public ResponseEntity<BaseResponse<Void>> deleteBigTask(@PathVariable String taskId) {
    return ResponseEntity.ok(taskService.deleteBigTask(taskId));
  }

  @GetMapping("/task/all-progress")
  public ResponseEntity<BaseResponse<List<TaskProgressResponse>>> getTasksProgress(@RequestParam String userId, @RequestParam String time) {
      return ResponseEntity.ok(taskService.getAllTasksByUserId(userId, time));
  }
}
