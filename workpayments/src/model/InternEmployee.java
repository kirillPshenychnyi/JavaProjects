public class InternEmployee extends Employee {

    private double hoursWorkedOut;
    private double maxHoursToWork;

    public InternEmployee( String name, double maxHours ){
        super( name );
        maxHoursToWork = maxHours;
        hoursWorkedOut = 0;
    }

    public double getMaxHoursToWork(){
        return maxHoursToWork;
    }

    @Override
    public double getWorkedOutHours() {
        return hoursWorkedOut;
    }

    @Override
    public double getTotalSalary() {
        return getBonus();
    }

    @Override
    public void trackWork( double hours ){
        hoursWorkedOut += hours;
    }

    @Override
    public void accept( EmployeeVisitor employeeVisitor ){
        employeeVisitor.visit( this );
    }
}
