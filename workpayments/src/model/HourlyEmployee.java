public class HourlyEmployee extends Employee {

    private double baseSalary;
    private double workedOutHours;

    public HourlyEmployee( String name, double salaryPerHour ){
        super( name );
        baseSalary = salaryPerHour;
        workedOutHours = 0.0;
    }

    public double getBaseSalary(){
        return baseSalary;
    }

    @Override
    public double getWorkedOutHours(){
        return workedOutHours;
    }

    @Override
    public double getTotalSalary(){
        return baseSalary * getWorkedOutHours() + getBonus();
    }

    @Override
    public void trackWork( double hours ){
        workedOutHours += hours;
    }

    @Override
    public void accept( EmployeeVisitor employeeVisitor ){
        employeeVisitor.visit( this );
    }
}
