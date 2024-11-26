package carsharing.model;

public class Car {
    private int id;
    private final String name;
    private final int companyId;

    public Car(String name, int companyId) {
        this.name = name;
        this.companyId = companyId;
    }
    public Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
