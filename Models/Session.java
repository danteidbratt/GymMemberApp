package Models;

import java.time.*;

public class Session {
    
    private LocalDateTime starts;
    private Trainer trainer;
    private Duration duration;
    private Hall hall;

    public LocalDateTime getScheduled() {
        return starts;
    }

    public void setScheduled(LocalDateTime scheduled) {
        this.starts = scheduled;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Models.Duration getDuration() {
        return duration;
    }

    public void setDuration(Models.Duration duration) {
        this.duration = duration;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

}