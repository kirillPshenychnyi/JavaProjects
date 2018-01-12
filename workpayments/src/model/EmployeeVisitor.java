public interface EmployeeVisitor {

    void visit( SalaryEmployee salaryEmployee );

    void visit( InternEmployee internEmployee );

    void visit( HourlyEmployee hourlyEmployee );
}
