package com.example.backend.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.backend.model.Notification;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, ObjectId> {
    List<Notification> findByUserId(String userId);

    List<Notification> findByIsAdmin(boolean isAdmin);

    @Query("{'userId': ?0, 'postId': ?1, 'content': ?2}")
    Notification getNotifExistedPost(String userId, String postId, String content);
}
