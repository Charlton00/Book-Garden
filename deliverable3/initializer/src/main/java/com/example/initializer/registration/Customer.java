package com.example.initializer.registration;

import com.example.initializer.cart.UserCart;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpSession;

@Entity
@Table(name = "customer")
@DynamicUpdate
public class Customer {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	private String email;
    @Column(name = "hashed_password")
	private String hashedPassword;
    @Column(name = "user_status")
	private String userStatus;
    @Column(name = "is_admin")
	private int isAdmin;
    private int subscribed;
    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "password_token")
    private String passwordToken;

    private List<UserCart> userCarts;

	public String getPasswordToken() {
        return passwordToken;
    }

    public void setPasswordToken(String passwordToken) {
        this.passwordToken = passwordToken;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public int getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(int subscribed) {
        this.subscribed = subscribed;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isSessionActive(HttpSession session, Customer loggedUser) {
		Customer isLoggedIn = null;

		if (session == null) {
			return false;
		}

		isLoggedIn = (Customer) session.getAttribute(("loggedInUser"));

		if (isLoggedIn == null) {
			return false;
		}

		if (!loggedUser.getEmail().matches(isLoggedIn.getEmail())) {
			return false;
		}

		return true;
	}

    public List<UserCart> getUserCarts() {
        return userCarts;
    }

    public void setUserCarts(List<UserCart> userCarts) {
        this.userCarts = userCarts;
    }
}
