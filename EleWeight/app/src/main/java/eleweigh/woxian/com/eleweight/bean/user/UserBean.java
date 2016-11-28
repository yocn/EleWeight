package eleweigh.woxian.com.eleweight.bean.user;

/**
 * Created by Yocn on 16.11.28.
 */

public class UserBean {
    private String uid;
    private String name;
    private String access_token;

    public UserBean() {
    }

    public UserBean(String uid, String name, String access_token) {
        this.uid = uid;
        this.name = name;
        this.access_token = access_token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
