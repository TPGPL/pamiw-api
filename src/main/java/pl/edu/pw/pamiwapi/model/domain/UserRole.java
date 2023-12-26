package pl.edu.pw.pamiwapi.model.domain;

public enum UserRole {
    User("ROLE_USER"),
    Admin("ROLE_ADMIN");

    public final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }
}
