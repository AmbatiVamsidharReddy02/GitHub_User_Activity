package com.learning.githubuseractivity.Entity;

import java.time.LocalDateTime;

import com.learning.githubuseractivity.Enums.EventType;

public record Event(String id, EventType type, String repoName, LocalDateTime createdAt, String action) { }
