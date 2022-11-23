package ex11;

public class AdminVO {
	private String adminId;
	private String adminPwd;
	
	public void info() {
		System.out.printf("adminId:%s%n adminPwd:%s%n",this.getAdminId(),this.getAdminPwd());
	}
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminPwd() {
		return adminPwd;
	}
	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}
	
	
}
