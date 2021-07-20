package pers.spring4.day04.n1_jdbcTemplate.bean;

/**
 * @author dinhyu
 * @version v1.0
 * date: 2021-07-20 10:53
 */
public class User {
    private String userId;
    private String username;
    private String ustatus;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUstatus(String ustatus) {
        this.ustatus = ustatus;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getUstatus() {
        return ustatus;
    }
}
