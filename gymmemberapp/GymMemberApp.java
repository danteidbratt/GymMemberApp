package gymmemberapp;

import static gymmemberapp.Capsule.State.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GymMemberApp {

    Frame frame;
    Repository repository;
    Capsule capsule;
    ActionHandler ah;

    public GymMemberApp() {
        this.frame = new Frame();
        this.repository = new Repository();
        this.capsule = new Capsule();
        this.ah = new ActionHandler();
    }

    public void startApp() {
        frame.allPanels.forEach((p) -> {
            p.setupPanel();
            p.setActionListeners(ah);
        });
        frame.setupFrame();
        frame.updateFrame(capsule);
    }

    public static void main(String[] args) {
        GymMemberApp gma = new GymMemberApp();
        gma.startApp();
    }

    public class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == frame.loginPanel.getSignInButton() || e.getSource() == frame.loginPanel.getTextField()) {
                logIn();
            }
            else if (e.getSource() == frame.bookOrUnbookPanel.getSignOutButton()) {
                capsule.setMember(null);
                capsule.setState(LOGIN);
            }
            else if (e.getSource() == frame.bookOrUnbookPanel.getBookButton()) {
                capsule.setState(GROUP_OR_INDIVIDUAL);
            }
            else if (e.getSource() == frame.groupOrIndividualPanel.getGroupButton()) {
                bookGroup();
            }
            else if (e.getSource() == frame.groupOrIndividualPanel.getIndividualButton()) {
                capsule.setState(INDIVIDUAL);
            }
            else if (e.getSource() == frame.groupOrIndividualPanel.getBackButton()) {
                capsule.setState(BOOK_OR_UNBOOK);
            }
            else if (e.getSource() == frame.groupPanel.getBackbButton()
                  || e.getSource() == frame.individualPanel.getBackbButton()){
                capsule.setState(GROUP_OR_INDIVIDUAL);
            }
            else if (e.getSource() == frame.loginPanel.getExitButton()) {
                System.exit(0);
            }
            frame.updateFrame(capsule);
        }
    }

    private void logIn() {
        String input = frame.loginPanel.getTextField().getText();
        if (input.length() > 0) {
            try {
                capsule.setMember(repository.getMembers(input).get(0));
                capsule.setState(BOOK_OR_UNBOOK);
                frame.loginPanel.getTextField().setText("");
                frame.loginPanel.getInfoText().setText("");
            } catch (IndexOutOfBoundsException e) {
                frame.loginPanel.getTextField().setText("");
                frame.loginPanel.getInfoText().setText("Unregistered name, please try again");
            }
        }
    }
    
    private void bookGroup(){
        capsule.setState(GROUP);
        capsule.setGroupSessions(repository.getGroupSessions().stream()
                .filter(s -> s.getTimeScheduled().isAfter(LocalDateTime.now()) 
                          && s.getTimeScheduled().isBefore(LocalDateTime.now().plusDays(14))
                          && s.getCapacity() > s.getParticipants().size())
                .collect(Collectors.toList()));
    }
}