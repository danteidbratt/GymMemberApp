package Models;

import java.time.*;

public abstract class Session {
    
    private int sessionID;
    private TimeSpan timeSpan;
//    private LocalDateTime timeScheduled;
//    private Duration duration;
    private Trainer trainer;
    private Hall hall;

    public Session() {
        this.trainer = new Trainer();
//        this.duration = new Duration();
        this.hall = new Hall();
    }

    public TimeSpan getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(LocalDateTime start, int minutes) {
        this.timeSpan = new TimeSpan(start, minutes);
    }
    
    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

//    public LocalDateTime getTimeScheduled() {
//        return timeScheduled;
//    }
//
//    public void setTimeScheduled(LocalDateTime timeScheduled) {
//        this.timeScheduled = timeScheduled;
//    }
//
//    public Models.Duration getDuration() {
//        return duration;
//    }
//
//    public void setDuration(Models.Duration duration) {
//        this.duration = duration;
//    }
    
    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

}