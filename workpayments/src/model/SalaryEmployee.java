public class SalaryEmployee extends Employee {

    private double fixedSalary;
    private final int HOURS_WORKED_OUT = 8;

    public SalaryEmployee( String fullName, double baseSalary ){
        super( fullName );
        fixedSalary = baseSalary;
    }

    public double getFixedSalary() {
        return fixedSalary;
    }

    @Override
    public double getWorkedOutHours(){
        return HOURS_WORKED_OUT;
    }

    @Override
    public double getTotalSalary(){
        return fixedSalary + getBonus();
    }

    @Override
    public void accept( EmployeeVisitor employeeVisitor ){
        employeeVisitor.visit( this );
    }
}
