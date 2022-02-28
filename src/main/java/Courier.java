public class Courier {

    private String login;
    private String password;
    private String firstName;
    private String id;
    public static final String DEFAULT_LOGIN = "ninja";
    public static final String DEFAULT_PASSWORD = "12345";
    public static final int DEFAULT_ID = 70;

    public Courier (String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Courier (String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
