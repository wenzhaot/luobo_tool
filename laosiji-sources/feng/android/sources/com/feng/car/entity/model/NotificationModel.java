package com.feng.car.entity.model;

public class NotificationModel {
    public Commont commont;
    public String message = "";
    public String publishdate = "";
    public Thread thread;
    public String title = "";
    public int typeid;
    public User user;

    public class Commont {
        public int commontid;
        public String content;
    }

    public class Thread {
        public int threadid;
        public String title;
    }

    public class User {
        public String name;
        public int userid;
    }
}
