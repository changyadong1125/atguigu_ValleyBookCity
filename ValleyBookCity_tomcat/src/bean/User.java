package bean;

/**
 * project:atguigu_ValleyBookCity
 * package:bean
 * class:User
 *
 * @author: smile
 * @create: 2023/3/19-13:58
 * @Version: v1.0
 * @Description:
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String emile;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmile() {
        return emile;
    }

    public void setEmile(String emile) {
        this.emile = emile;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", emile='" + emile + '\'' +
                '}';
    }
}
