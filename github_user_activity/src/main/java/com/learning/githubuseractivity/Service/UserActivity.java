package com.learning.githubuseractivity.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.learning.githubuseractivity.Entity.Event;
import com.learning.githubuseractivity.Enums.EventType;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class UserActivity {

    private String username;
    private Gson gson;
    private List<Event> eventList;
    private String API_URL;

    public UserActivity(String username) {
        this.username = username;
        this.gson = new Gson();
        this.eventList = new LinkedList<>();
        this.API_URL = "https://api.github.com/users/" + username + "/events";
        fetchActivity();
    }

    private void fetchActivity() {
        HttpResponse<String> response = sendRequest();
        if (response == null) {
            System.out.println("Error fetching activity");
            return;
        }
        processRequest(response);
    }

    private HttpResponse<String> sendRequest() {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL))
                    .build();
        } catch (URISyntaxException e) {
            System.out.println("Invalid URI: " + API_URL);
        }

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("Error sending request: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Request interrupted: " + e.getMessage());
        }

        return response;
    }

    private void processRequest(HttpResponse<String> response) {
        int responseCode = response.statusCode();

        if (responseCode == 404) {
            System.out.println("Username " + this.username + " not found");
            return;
        }

        String responseBody = response.body();
        JsonArray eventsJson = gson.fromJson(responseBody, JsonArray.class);

        for (int i = 0; i < eventsJson.size(); i++) {
            JsonObject event = eventsJson.get(i).getAsJsonObject();
            String id = event.get("id").getAsString();
            String type = event.get("type").getAsString();

            EventType eventType = Arrays.stream(EventType.values())
                    .filter(e -> e.name().equals(type))
                    .findFirst()
                    .orElse(EventType.Unknown);

            String createdAt = event.get("created_at").getAsString();
            LocalDateTime createdAtDate = parseDate(createdAt);

            JsonObject repo = event.get("repo").getAsJsonObject();
            String repoName = repo.get("name").getAsString();

            JsonObject payload = event.get("payload").getAsJsonObject();

            String action = "null";
            if (payload.has("action"))
                action = payload.get("action").getAsString();

            Event e = new Event(id, eventType, repoName, createdAtDate, action);
            this.eventList.add(e);
        }
    }

    public void showActivity() {
        Map<EventType, List<Event>> eventsByType = eventList.stream().collect(Collectors.groupingBy(Event::type));

        eventsByType.forEach((key, value) -> {
            System.out.println("- " + key.getMessage());
            Map<String, List<Event>> eventsByRepoName = value.stream().collect(Collectors.groupingBy(Event::repoName));
            eventsByRepoName.forEach((repoName, events) -> {
                System.out.println("  Repo: " + repoName);
                events.forEach(evt -> System.out.println("    " +
                        "{id: " + evt.id() + ", " +
                        "createdAt: " + evt.createdAt() +
                        (evt.action().equals("null") ? "" : ", action: " + evt.action()) + "}"));
            });
            System.out.println("");
        });
    }

    private LocalDateTime parseDate(String date) {
        return ZonedDateTime.parse(date).toLocalDateTime();
    }
}