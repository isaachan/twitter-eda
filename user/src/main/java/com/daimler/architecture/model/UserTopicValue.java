package com.daimler.architecture.model;

public class UserTopicValue {

    private String action;

    private User user;
    private Long targetId;
    private Long followerId;

    public UserTopicValue(String action, User user) {
        this.action = action;
        this.user = user;
    }

    public UserTopicValue(String action, Long targetId, Long followerId) {
        this.action = action;
        this.targetId = targetId;
        this.followerId = followerId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }
}
