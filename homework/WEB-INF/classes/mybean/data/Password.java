package mybean.data;

public class Password {
    String oldPassword;
    String newPassword;
    String backNews="";
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getBackNews() {
        return backNews;
    }
    public void setBackNews(String backNews) {
        this.backNews = backNews;
    }
}
