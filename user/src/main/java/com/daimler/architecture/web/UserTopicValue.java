package com.daimler.architecture.web;

import com.daimler.architecture.model.User;

class UserTopicValue {

    private String action;
    private User content;

    private Long targetId;
    private Long followerId;

    public UserTopicValue(String action, User content) {
        this.action = action;
        this.content = content;
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

    public User getContent() {
        return content;
    }

    public void setContent(User content) {
        this.content = content;
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
