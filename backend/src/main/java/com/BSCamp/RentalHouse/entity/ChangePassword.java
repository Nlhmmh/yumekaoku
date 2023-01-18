package com.BSCamp.RentalHouse.entity;

public class ChangePassword {
	String currentPassword;
	
	String newPassword;
	
	String confirmPassword;
	
	int ori_id;
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public int getOri_id() {
		return ori_id;
	}
	public void setOri_id(int ori_id) {
		this.ori_id = ori_id;
	}
	@Override
	public String toString() {
		return "ChangePassword [currentPassword=" + currentPassword + ", newPassword=" + newPassword
				+ ", confirmPassword=" + confirmPassword + ", ori_id=" + ori_id + "]";
	}
	
}
