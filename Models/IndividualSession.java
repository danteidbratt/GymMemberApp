package Models;

public class IndividualSession extends Session{
    
    private int individualSessionID;
    private Member member;

    public int getIndividualSessionID() {
        return individualSessionID;
    }

    public void setIndividualSessionID(int individualSessionID) {
        this.individualSessionID = individualSessionID;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
}