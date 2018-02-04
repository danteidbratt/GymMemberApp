package gymmemberapp;

import Models.*;
import static gymmemberapp.Capsule.State.*;
import java.util.List;

public class Capsule {
    
    private Member member;
    private State state;
    private List<GroupSession> groupSessions;
    private List<IndividualSession> individualSessions;

    public Capsule() {
        member = new Member();
        state = LOGIN;
    }
    
    protected enum State {
        LOGIN, BOOK_OR_UNBOOK, GROUP_OR_INDIVIDUAL, GROUP, INDIVIDUAL, VIEW_RESERVATIONS
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

    public List<GroupSession> getGroupSessions() {
        return groupSessions;
    }

    public void setGroupSessions(List<GroupSession> groupSessions) {
        this.groupSessions = groupSessions;
    }

    public List<IndividualSession> getIndividualSessions() {
        return individualSessions;
    }

    public void setIndividualSessions(List<IndividualSession> individualSessions) {
        this.individualSessions = individualSessions;
    }

}