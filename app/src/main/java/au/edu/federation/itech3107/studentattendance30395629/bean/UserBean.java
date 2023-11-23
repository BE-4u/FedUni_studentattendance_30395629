package au.edu.federation.itech3107.studentattendance30395629.bean;

public class UserBean {

    private String type;//1是管理员2是教师3是学生  1 is the administrator 2 is the teacher 3 is the student
    private String account;
    private String password;
    private String school_num;
    private String other;
    private String standby;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchool_num() {
        return school_num;
    }

    public void setSchool_num(String school_num) {
        this.school_num = school_num;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getStandby() {
        return standby;
    }

    public void setStandby(String standby) {
        this.standby = standby;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "type='" + type + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", school_num='" + school_num + '\'' +
                ", other='" + other + '\'' +
                ", standby='" + standby + '\'' +
                '}';
    }
}
