package Models;

import java.time.LocalDateTime;


public class TimeSpan {
    
    LocalDateTime start;
    LocalDateTime finish;

    public TimeSpan(LocalDateTime start, int minutes) {
        this.start = start;
        this.finish = start.plusMinutes(minutes);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

}