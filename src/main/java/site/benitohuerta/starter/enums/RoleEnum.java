package site.benitohuerta.starter.enums;

public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
