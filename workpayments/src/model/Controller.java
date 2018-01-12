
import java.util.HashMap;

public class Controller {

    private class WorkTracker implements EmployeeVisitor{

        double hours;

        public void track( Employee employee, double toTrack ){
            hours = toTrack;
            employee.accept( this );
        }

        public void visit( SalaryEmployee salaryEmployee ){
            salaryEmployee.trackWork( hours );
        }

        public void visit( InternEmployee internEmployee ){
            double actualWorkedOut
                    =   internEmployee.getWorkedOutHours()
                    +   hours;

            if( actualWorkedOut >= internEmployee.getMaxHoursToWork() ){
                throw new IllegalArgumentException( Messages.OverloadedIntern );
            }

            internEmployee.trackWork( hours );
        }

        public void visit( HourlyEmployee hourlyEmployee ){
            hourlyEmployee.trackWork( hours );
        }

    }

    private HashMap< String, Employee > employees;

    public Controller() {
        employees = new HashMap<>();
    }

    public void createHourlyEmployee( String name, double hourlyRate ) {
        checkNameParams( name );
        checkPositiveValue( hourlyRate, Messages.NonPositivePayment );

        HourlyEmployee employee = new HourlyEmployee( name, hourlyRate );
        employees.put( name, employee );
    }

    public void createSalaryEmployee( String name, double monthPayment ){
        checkNameParams( name );
        checkPositiveValue( monthPayment, Messages.NonPositivePayment );

        SalaryEmployee employee = new SalaryEmployee( name, monthPayment );
        employees.put( name, employee );
    }

    public void createInternEmployee( String name, double workHoursLimit ){
        checkNameParams( name );
        checkPositiveValue( workHoursLimit, Messages.NonPositiveHoursLimit );

        InternEmployee intern = new InternEmployee( name, workHoursLimit );
        employees.put( name, intern );
    }

    private void checkNameParams( String name ){
        if( name.isEmpty() ){
            throw new IllegalArgumentException( Messages.EmptyName );
        }

        if( hasEmployee( name ) ){
            throw new IllegalArgumentException( Messages.NonUniqueName );
        }
    }

    private void checkPositiveValue( double value, String message ){
        if( value <= 0 ){
            throw new IllegalArgumentException( message );
        }
    }

    public void clean(){
        employees.clear();
    }

    public boolean hasEmployee( String name ) {
        return employees.containsKey( name );
    }

    public void trackWork( String name, double hours ) {
        checkPositiveValue( hours, Messages.NegativeWorkHours );

        Employee employee = accessEmployee( name );

        WorkTracker tracker = new WorkTracker();
        tracker.track( employee, hours );
    }

    public void giveBonus( String name, double bonus ){
        checkPositiveValue( bonus, Messages.NonPositivePayment );
        Employee employee = accessEmployee( name );
        employee.setBonus( bonus );
    }

    public double getPayment( String name ){
        Employee employee = accessEmployee( name );
        return employee.getTotalSalary();
    }

    public double getTotalPayment(){
        double total = 0.0;
        for ( Employee e : employees.values() ) {
            total += e.getTotalSalary();
        }

        return  total;
    }

    private Employee accessEmployee( String name ){
        if( name.isEmpty() ){
            throw new IllegalArgumentException( Messages.EmptyName );
        }

        Employee employee = employees.getOrDefault( name, null );

        if( employee == null ){
            throw new IllegalArgumentException( Messages.UnknownEmployeeName );
        }

        return employee;
    }
}
