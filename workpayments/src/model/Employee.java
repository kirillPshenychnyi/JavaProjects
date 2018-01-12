public abstract class Employee {

    private String fullName;
    private double bonus;

    public Employee( String name ){
        fullName = name;
        bonus = 0.0;
    }

    public String getName(){
        return fullName;
    }

    public double getBonus(){
        return bonus;
    }

    public void setBonus( double value ){
        bonus = value;
    }

    public abstract void accept( EmployeeVisitor employeeVisitor );

    public abstract double getWorkedOutHours();

    public abstract double getTotalSalary();

    public void trackWork( double hours ) { }
}
