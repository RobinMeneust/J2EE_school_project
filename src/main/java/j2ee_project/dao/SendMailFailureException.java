package j2ee_project.dao;

public class SendMailFailureException extends Exception {
    public SendMailFailureException(String e) {
        super("The mail could not be send\n"+e);
    }
}
