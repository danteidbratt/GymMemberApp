package Models;

public class IndividualSession extends Session{
    
    private int individualSessionID;
    private boolean attendance;
    private Member member;

    public int getIndividualSessionID() {
        return individualSessionID;
    }

    public void setIndividualSessionID(int individualSessionID) {
        this.individualSessionID = individualSessionID;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
}