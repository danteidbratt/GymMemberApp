package gymmemberapp;

import Models.TimeSpan;
import static gymmemberapp.Capsule.State.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class GymMemberApp {

    private final Frame frame;
    private final Repository repository;
    private final Capsule capsule;
    private final ActionHandler ah;
    private final DateTimeFormatter formatter;
    private String chosenType;
    private String chosenDate;
    private String chosenTime;
    private String chosenHall;
    private String chosenTrainer;
    private String chosenGroupSessionID;
    private String chosenIndividualSessionID;
    private LocalDateTime chosenDateTime;

    public GymMemberApp() {
        this.frame = new Frame();
        this.repository = new Repository();
        this.capsule = new Capsule();
        this.ah = new ActionHandler();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            else if (e.getSource() == frame.bookOrUnbookPanel.getViewReservationsButton()) {
                viewReservations();
            }
            else if (e.getSource() == frame.groupOrIndividualPanel.getGroupButton()) {
                bookGroup();
            }
            else if (e.getSource() == frame.groupOrIndividualPanel.getIndividualButton()) {
                bookIndividual();
            }
            else if (e.getSource() == frame.groupOrIndividualPanel.getBackButton()) {
                capsule.setState(BOOK_OR_UNBOOK);
            }
            else if (e.getSource() == frame.groupPanel.getTypeSlide()) {
                filterGroupByType();
            }
            else if (e.getSource() == frame.groupPanel.getDateSlide()) {
                filterGroupByDate();
            }
            else if (e.getSource() == frame.groupPanel.getSessionSlide()) {
                filterGroupBySession();
            }
            else if (e.getSource() == frame.groupPanel.getConfirmButton()) {
                confirmGroupReservation();
            }
            else if (e.getSource() == frame.individualPanel.getDateSlide()) {
                filterIndividualByDate();
            }
            else if (e.getSource() == frame.individualPanel.getSessionSlide()) {
                filterIndividualBySession();
            }
            else if (e.getSource() == frame.individualPanel.getConfirmButton()) {
                confirmIndividualReservation();
            }
            else if (e.getSource() == frame.groupPanel.getBackbButton()) {
                backFromGroup();
            }
            else if (e.getSource() == frame.individualPanel.getBackbButton()) {
                backFromIndividual();
            }
            else if (e.getSource() == frame.viewReservationsPanel.getReservedGroupSessionSlide()) {
                selectReservedGroupSession();
            }
            else if (e.getSource() == frame.viewReservationsPanel.getReservedIndividualSessionSlide()) {
                selectReservedIndividualSession();
            }
            else if (e.getSource() == frame.viewReservationsPanel.getRemoveGroupButton()) {
                executeGroupSessionRemoval();
            }
            else if (e.getSource() == frame.viewReservationsPanel.getRemoveIndividualButton()) {
                executeIndividualSessionRemoval();
            }
            else if (e.getSource() == frame.viewReservationsPanel.getBackbButton()) {
                backFromViewReservations();
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
                capsule.getMember().setGroupSessions(repository.getGroupSessionsInMember(String.valueOf(capsule.getMember().getID())));
                capsule.getMember().setIndividualSessions(repository.getIndividualSessionsInMember(String.valueOf(capsule.getMember().getID())));
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
        capsule.setGroupSessions(repository.getGroupSessions("").stream()
                .filter(s -> s.getTimeSpan().getStart().isAfter(LocalDateTime.now()) 
                          && s.getTimeSpan().getStart().isBefore(LocalDateTime.now().plusDays(14))
                          && s.getCapacity() > s.getParticipants().size()
                          && !capsule.getMember().getGroupSessions().stream()
                                  .map(a -> a.getGroupSessionID())
                                  .collect(Collectors.toList())
                                  .contains(s.getGroupSessionID())
                          && !areSimultaneous(capsule.getMember().getGroupSessions().stream()
                                              .map(b -> b.getTimeSpan())
                                              .collect(Collectors.toList()), s.getTimeSpan())
                          && !areSimultaneous(capsule.getMember().getIndividualSessions().stream()
                                              .map(b -> b.getTimeSpan())
                                              .collect(Collectors.toList()), s.getTimeSpan()))
                .collect(Collectors.toList()));
        frame.groupPanel.setTypeSlide(capsule.getGroupSessions().stream()
                        .map(s -> s.getExerciseType().getName())
                        .collect(Collectors.toSet())
                        .stream()
                        .collect(Collectors.toList()), ah);
        frame.groupPanel.resetDateSlide();
        frame.groupPanel.resetSessionSlide();
    }
    
    private void bookIndividual(){
        capsule.setState(INDIVIDUAL);
        capsule.setIndividualSessions(repository.getIndividualSessions("").stream()
                .filter(a -> a.getTimeSpan().getStart().isAfter(LocalDateTime.now())
                          && a.getTimeSpan().getStart().isBefore(LocalDateTime.now().plusDays(14))
                          && a.getMember() == null
                          && !areSimultaneous(capsule.getMember().getGroupSessions().stream()
                                              .map(b -> b.getTimeSpan())
                                              .collect(Collectors.toList()), a.getTimeSpan())
                          && !areSimultaneous(capsule.getMember().getIndividualSessions().stream()
                                              .map(b -> b.getTimeSpan())
                                              .collect(Collectors.toList()), a.getTimeSpan()))
                .collect(Collectors.toList()));
        frame.individualPanel.setDateSlide(capsule.getIndividualSessions().stream()
                .map(a -> a.getTimeSpan().getStart().toLocalDate().toString())
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList()), ah);
    }
    
    private void filterGroupByType(){
        if (frame.groupPanel.getTypeSlide().getSelectedIndex() > 0){
            chosenType = frame.groupPanel.getTypeSlide().getSelectedItem().toString();
            frame.groupPanel.setDateSlide(capsule.getGroupSessions().stream()
                    .filter(s -> s.getExerciseType().getName().equalsIgnoreCase(chosenType))
                    .map(m -> m.getTimeSpan().getStart().toLocalDate().toString())
                    .collect(Collectors.toSet())
                    .stream()
                    .collect(Collectors.toList()), ah);
        }
        else {
            frame.groupPanel.resetDateSlide();
        }
        frame.groupPanel.resetSessionSlide();
        frame.groupPanel.hideChoice();
    }
    
    private void filterGroupByDate(){
        if (frame.groupPanel.getDateSlide().getSelectedIndex() > 0){
            chosenDate = frame.groupPanel.getDateSlide().getSelectedItem().toString();
            frame.groupPanel.setSessionSlide(capsule.getGroupSessions().stream()
                    .filter(s -> s.getExerciseType().getName().equalsIgnoreCase(chosenType))
                    .filter(t -> t.getTimeSpan().getStart().toLocalDate().toString().equalsIgnoreCase(chosenDate))
                    .map(r -> r.getTimeSpan().getStart().toLocalTime().toString() + " / " +
                              r.getHall().getName() + " / " +
                              r.getTrainer().getName())
                    .collect(Collectors.toList()), ah);
        }
        else {
            frame.groupPanel.resetSessionSlide();
        }
        frame.groupPanel.hideConfirmButton();
        frame.groupPanel.hideChoice();
    }
    
    private void filterGroupBySession(){
        if (frame.groupPanel.getSessionSlide().getSelectedIndex() > 0) {
            String[] inputs = frame.groupPanel.getSessionSlide().getSelectedItem().toString().split("\\s/\\s");
            chosenTime = inputs[0].trim();
            chosenHall = inputs[1].trim();
            chosenTrainer = inputs[2].trim();
            chosenDateTime = LocalDateTime.parse(chosenDate + " " + chosenTime + ":00", formatter);
            chosenGroupSessionID = String.valueOf(capsule.getGroupSessions().stream()
                    .filter(a -> a.getExerciseType().getName().equalsIgnoreCase(chosenType)
                            && a.getHall().getName().equalsIgnoreCase(chosenHall)
                            && a.getTimeSpan().getStart().equals(chosenDateTime)
                            && a.getTrainer().getName().equalsIgnoreCase(chosenTrainer))
                    .collect(Collectors.toList())
                    .get(0).getGroupSessionID());
            frame.groupPanel.showConfirmButton();
            frame.groupPanel.displayChoice();
        }
    }
    
    private void confirmGroupReservation(){
        capsule.setState(BOOK_OR_UNBOOK);
        frame.updateFrame(capsule);
        if (repository.makeGroupSessionReservation(String.valueOf(capsule.getMember().getID()), chosenGroupSessionID) == 1) {
            JOptionPane.showMessageDialog(null, "Reservation Confirmed!");
        }
        else {
            JOptionPane.showMessageDialog(null, "Reservation Failed\nPlease try again");
        }
        capsule.getMember().setGroupSessions(repository.getGroupSessionsInMember(String.valueOf(capsule.getMember().getID())));
        frame.groupPanel.resetDateSlide();
        frame.groupPanel.resetSessionSlide();
        frame.groupPanel.hideChoice();
        frame.groupPanel.hideConfirmButton();
    }
    
    private void filterIndividualByDate() {
        if (frame.individualPanel.getDateSlide().getSelectedIndex() > 0){
            chosenDate = frame.individualPanel.getDateSlide().getSelectedItem().toString();
            frame.individualPanel.setSessionSlide(capsule.getIndividualSessions().stream()
                    .filter(t -> t.getTimeSpan().getStart().toLocalDate().toString().equalsIgnoreCase(chosenDate))
                    .map(r -> r.getTimeSpan().getStart().toLocalTime().toString() + " / " +
                              r.getHall().getName() + " / " +
                              r.getTrainer().getName() + " / " +
                              r.getTimeSpan().getMinutes() + "min")
                    .collect(Collectors.toList()), ah);
        }
        else {
            frame.individualPanel.resetSessionSlide();
        }
        frame.individualPanel.hideConfirmButton();
        frame.individualPanel.hideChoice();
    }
    
    private void filterIndividualBySession() {
        if (frame.individualPanel.getSessionSlide().getSelectedIndex() > 0) {
            String[] inputs = frame.individualPanel.getSessionSlide().getSelectedItem().toString().split("\\s/\\s");
            chosenDateTime = LocalDateTime.parse(chosenDate + " " + inputs[0].trim() + ":00", formatter);
            chosenHall = inputs[1].trim();
            chosenTrainer = inputs[2].trim();
            chosenIndividualSessionID = String.valueOf(capsule.getIndividualSessions().stream()
                    .filter(a -> a.getHall().getName().equalsIgnoreCase(chosenHall)
                              && a.getTrainer().getName().equalsIgnoreCase(chosenTrainer)
                              && a.getTimeSpan().getStart().equals(chosenDateTime))
                    .collect(Collectors.toList())
                    .get(0).getIndividualSessionID());
            frame.individualPanel.displayChoice();
            frame.individualPanel.showConfirmButton();
        }
    }
    
    private void confirmIndividualReservation() {
        capsule.setState(BOOK_OR_UNBOOK);
        frame.updateFrame(capsule);
        if (repository.makeIndividualSessionReservation(String.valueOf(capsule.getMember().getID()), chosenIndividualSessionID) == 1) {
            JOptionPane.showMessageDialog(null, "Reservation Confirmed!");
        }
        else {
            JOptionPane.showMessageDialog(null, "Reservation Failed\nPlease try again");
        }
        capsule.getMember().setIndividualSessions(repository.getIndividualSessionsInMember(String.valueOf(capsule.getMember().getID())));
        frame.individualPanel.hideChoice();
        frame.individualPanel.hideConfirmButton();
        frame.individualPanel.resetSessionSlide();
    }
    
    private void viewReservations(){
        capsule.setState(VIEW_RESERVATIONS);
        frame.viewReservationsPanel.setReservedGroupSessionSlide(capsule.getMember().getGroupSessions().stream()
                .filter(a -> a.getTimeSpan().getStart().isAfter(LocalDateTime.now()))
                .map(b -> b.getExerciseType().getName() + " / "
                        + b.getTimeSpan().getStart().toString().replace('T', ' ') + " / "
                        + b.getHall().getName() + " / "
                        + b.getTrainer().getName()).collect(Collectors.toList()), ah);
        frame.viewReservationsPanel.setReservedIndividualSessionSlide(capsule.getMember().getIndividualSessions().stream()
                .filter(a -> a.getTimeSpan().getStart().isAfter(LocalDateTime.now()))
                .map(b -> b.getTimeSpan().getStart().toString().replace('T', ' ') + " / "
                        + b.getHall().getName() + " / "
                        + b.getTrainer().getName() + " / "
                        + b.getTimeSpan().getMinutes() + "min").collect(Collectors.toList()), ah);
    }
    
    private boolean areSimultaneous(List<TimeSpan> x1, TimeSpan x2){
        for (TimeSpan t : x1) {
            if((t.getStart().isAfter(x2.getStart()) && t.getStart().isBefore(x2.getFinish()))
            || (t.getFinish().isAfter(x2.getStart()) && t.getFinish().isBefore(x2.getFinish())))
                return true;
        }
        return false;
    }
    
    private void selectReservedGroupSession(){
        if (frame.viewReservationsPanel.getReservedGroupSessionSlide().getSelectedIndex() > 0){
            String[] inputs = frame.viewReservationsPanel.getReservedGroupSessionSlide().getSelectedItem().toString().split("\\s/\\s");
            chosenType = inputs[0].trim();
            chosenDateTime = LocalDateTime.parse(inputs[1].trim() + ":00", formatter);
            chosenHall = inputs[2].trim();
            chosenTrainer = inputs[3].trim();
            chosenGroupSessionID = String.valueOf(capsule.getMember().getGroupSessions().stream()
                    .filter(a -> a.getExerciseType().getName().equalsIgnoreCase(chosenType)
                              && a.getTimeSpan().getStart().equals(chosenDateTime)
                              && a.getHall().getName().equalsIgnoreCase(chosenHall)
                              && a.getTrainer().getName().equalsIgnoreCase(chosenTrainer))
                    .collect(Collectors.toList())
                    .get(0).getGroupSessionID());
            frame.viewReservationsPanel.displayGroupChoice();
            frame.viewReservationsPanel.showRemoveGroupButton();
        }
        else {
            frame.viewReservationsPanel.hideRemoveButtons();
            frame.viewReservationsPanel.hideChoice();
        }
        frame.viewReservationsPanel.resetReservedIndividualSessionSlide(ah);
    }
    
    private void selectReservedIndividualSession(){
        if (frame.viewReservationsPanel.getReservedIndividualSessionSlide().getSelectedIndex() > 0) {
            String[] inputs = frame.viewReservationsPanel.getReservedIndividualSessionSlide().getSelectedItem().toString().split("\\s/\\s");
            chosenDateTime = LocalDateTime.parse(inputs[0].trim() + ":00", formatter);
            chosenHall = inputs[1].trim();
            chosenTrainer = inputs[2].trim();
            chosenIndividualSessionID = String.valueOf(capsule.getMember().getIndividualSessions().stream()
                    .filter(a -> a.getTimeSpan().getStart().equals(chosenDateTime)
                              && a.getHall().getName().equalsIgnoreCase(chosenHall)
                              && a.getTrainer().getName().equalsIgnoreCase(chosenTrainer))
                    .collect(Collectors.toList())
                    .get(0).getIndividualSessionID());
            frame.viewReservationsPanel.showRemoveIndividualButton();
            frame.viewReservationsPanel.displayIndividualChoice();
        }
        else {
            frame.viewReservationsPanel.hideRemoveButtons();
            frame.viewReservationsPanel.hideChoice();
        }
        frame.viewReservationsPanel.resetReservedGroupSessionSlide(ah);
    }
    
    private void executeGroupSessionRemoval(){
        capsule.setState(BOOK_OR_UNBOOK);
        frame.updateFrame(capsule);
        if (repository.removeGroupSession(String.valueOf(capsule.getMember().getID()), chosenGroupSessionID) == 1) {
            JOptionPane.showMessageDialog(null, "Reservation Deleted");
        }
        else {
            JOptionPane.showMessageDialog(null, "Unable to delete reservation. Please try again");
        }
        frame.viewReservationsPanel.resetReservedGroupSessionSlide(ah);
        frame.viewReservationsPanel.resetReservedIndividualSessionSlide(ah);
        frame.viewReservationsPanel.hideRemoveButtons();
        frame.viewReservationsPanel.hideChoice();
        capsule.getMember().setGroupSessions(repository.getGroupSessionsInMember(String.valueOf(capsule.getMember().getID())));
    }
    
    private void executeIndividualSessionRemoval(){
        capsule.setState(BOOK_OR_UNBOOK);
        frame.updateFrame(capsule);
        if (repository.removeIndividualSession(chosenIndividualSessionID) == 1) {
            JOptionPane.showMessageDialog(null, "Reservation Deleted");
        }
        else {
            JOptionPane.showMessageDialog(null, "Unable to delete reservation. Please try again");
        }
        frame.viewReservationsPanel.resetReservedGroupSessionSlide(ah);
        frame.viewReservationsPanel.resetReservedIndividualSessionSlide(ah);
        frame.viewReservationsPanel.hideRemoveButtons();
        frame.viewReservationsPanel.hideChoice();
        capsule.getMember().setIndividualSessions(repository.getIndividualSessionsInMember(String.valueOf(capsule.getMember().getID())));
    }
    
    private void backFromGroup(){
        capsule.setState(GROUP_OR_INDIVIDUAL);
        frame.groupPanel.resetDateSlide();
        frame.groupPanel.resetSessionSlide();
        frame.groupPanel.hideChoice();
        frame.groupPanel.hideConfirmButton();
    }
    
    private void backFromIndividual(){
        capsule.setState(GROUP_OR_INDIVIDUAL);
        frame.individualPanel.hideChoice();
        frame.individualPanel.hideConfirmButton();
        frame.individualPanel.resetSessionSlide();
    }
    
    private void backFromViewReservations(){
        capsule.setState(BOOK_OR_UNBOOK);
        frame.viewReservationsPanel.resetReservedGroupSessionSlide(ah);
        frame.viewReservationsPanel.resetReservedIndividualSessionSlide(ah);
        frame.viewReservationsPanel.hideRemoveButtons();
        frame.viewReservationsPanel.hideChoice();
    }
}