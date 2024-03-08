public class Person {
    private String name;
    private String sureName;
    private String email;

    public Person(String objName, String objSureName, String objEmail) {
        name = objName;
        sureName = objSureName;
        email = objEmail;
    }

    public void setName(String objName) {
        name = objName;

    }

    public String getName() {
        return name;
    }

    public void setSureName(String objSureName) {
        name = objSureName;

    }

    public String getSureName() {
        return sureName;
    }

    public void setEmail(String email) {
        name = email;

    }

    public String getEmail() {
        return email;
    }

    public void displayPersonInfo() {

        System.out.println("\nName: " + name);
        System.out.println("Surname: " + sureName);
        System.out.println("Email: " + email + "\n");
    }

}
