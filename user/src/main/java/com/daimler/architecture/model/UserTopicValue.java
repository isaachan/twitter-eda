package com.daimler.architecture.model;

class UserTopicValue {

    private String action;
    private User content;

    public UserTopicValue(String action, User content) {
        this.action = action;
        this.content = content;
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
    
}
