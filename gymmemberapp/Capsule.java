package gymmemberapp;

import Models.*;
import static gymmemberapp.Capsule.State.*;
import java.util.List;

public class Capsule {
    
    private Member member;
    private State state;
    private List<String> types;
    private List<String> dates;

    public Capsule() {
        state = LOGIN;
    }
    
    protected enum State {
        LOGIN, BOOK_OR_UNBOOK, GROUP_OR_INDIVIDUAL, GROUP, INDIVIDUAL,
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

}
