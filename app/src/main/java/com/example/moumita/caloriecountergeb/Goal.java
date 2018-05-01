package com.example.moumita.caloriecountergeb;

public class Goal {
    private long goal_id;
    private String goal_name, goal_description;
    private long goal_duration, my_goal_streak;
    private String goal_status, goal_image;
    public Goal(long goal_id, String goal_name, String goal_description, long goal_duration,
                long my_goal_streak, String goal_status, String goal_image) {
        this.goal_id = goal_id;
        this.goal_name = goal_name;
        this.goal_description = goal_description;
        this.goal_duration = goal_duration;
        this.my_goal_streak = my_goal_streak;
        this.goal_status = goal_status;
        this.goal_image = goal_image;
    }

    public long getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(long goal_id) {
        this.goal_id = goal_id;
    }

    public String getGoal_name() {
        return goal_name;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }

    public String getGoal_description() {
        return goal_description;
    }

    public void setGoal_description(String goal_description) {
        this.goal_description = goal_description;
    }

    public long getMy_goal_streak() {
        return my_goal_streak;
    }

    public void setMy_goal_streak(long my_goal_streak) {
        this.my_goal_streak = my_goal_streak;
    }

    public long getGoal_duration() {
        return goal_duration;
    }

    public void setGoal_duration(long goal_duration) {
        this.goal_duration = goal_duration;
    }

    public String getGoal_status() {
        return goal_status;
    }

    public void setGoal_status(String goal_status) {
        this.goal_status = goal_status;
    }

    public String getGoal_image() {
        return goal_image;
    }

    public void setGoal_image(String goal_image) {
        this.goal_image = goal_image;
    }
}
