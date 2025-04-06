package com.learning.githubuseractivity.Enums;

public enum EventType {
    CommitCommentEvent("A commit comment is created."),
    CreateEvent("A Git branch or tag is created."),
    DeleteEvent("A Git branch or tag is deleted."),
    ForkEvent("A user forks a repository."),
    GollumEvent("A wiki page is created or updated."),
    IssueCommentEvent("Activity related to an issue or pull request comment."),
    IssuesEvent("Activity related to an issue."),
    MemberEvent("Activity related to repository collaborators."),
    PublicEvent("When a private repository is made public."),
    PullRequestEvent("Activity related to pull requests."),
    PullRequestReviewEvent("Activity related to pull request reviews."),
    PullRequestReviewCommentEvent("Activity related to pull request review comments in the pull request's unified diff."),
    PullRequestReviewThreadEvent("Activity related to a comment thread on a pull request being marked as resolved or unresolved."),
    PushEvent("One or more commits are pushed to a repository branch or tag."),
    ReleaseEvent("Activity related to a release."),
    SponsorshipEvent("Activity related to a sponsorship listing."),
    WatchEvent("When someone stars a repository."),
    Unknown("Unknown event type.");

    private String message;

    EventType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}