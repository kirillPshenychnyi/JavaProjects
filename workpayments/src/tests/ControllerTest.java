import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ControllerTest {

    private Controller controller;
    private double delta = 0.00001;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public ControllerTest(){
        controller = new Controller();
    }

    @After
    public void cleanUp() {
        controller.clean();
    }

    @Test
    public void test_createHourlyEmployee() {
        controller.createHourlyEmployee( "Winston Smith", 5.0 );

        assertEquals( 0.0, controller.getPayment( "Winston Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_createSeveralHourlyEmployees(){
        controller.createHourlyEmployee( "Winston Smith", 5.0 );

        assertEquals( 0.0, controller.getPayment( "Winston Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.createHourlyEmployee( "Emanuel Goldstein", 10.25 );

        assertEquals( 0.0, controller.getPayment( "Emanuel Goldstein" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_createHourlyEmployeeEmptyName(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.EmptyName );

        controller.createHourlyEmployee( "", 5.0 );
    }

    @Test
    public void test_createHourlyEmployeeNegativeHourRate(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.NonPositivePayment );

        controller.createHourlyEmployee( "James Jameson", 0.0 );
    }

    @Test
    public void test_createHourlyEmployeeDuplicatedName(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.NonUniqueName );

        controller.createHourlyEmployee( "James Jameson", 1.0 );
        controller.createHourlyEmployee( "James Jameson", 1.0 );
    }

    @Test
    public void test_createSalaryEmployee(){
        controller.createSalaryEmployee( "James Jameson", 700.0 );

        assertEquals( 700.0, controller.getPayment( "James Jameson" ), delta );
        assertEquals( 700.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_createCoupleSalaryEmployees(){
        controller.createSalaryEmployee( "James Jameson", 700.0 );
        controller.createSalaryEmployee( "Sarah Smith", 900.0 );

        assertEquals( 700.0, controller.getPayment( "James Jameson" ), delta );
        assertEquals( 900.0, controller.getPayment( "Sarah Smith" ), delta );
        assertEquals( 1600.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_createSalaryEmployeeEmptyName(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.EmptyName );

        controller.createSalaryEmployee( "", 5.0 );
    }

    @Test
    public void test_salaryEmployeeNegativeSalary(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.NonPositivePayment );

        controller.createSalaryEmployee( "James Jameson", -10.0 );
    }

    @Test
    public void test_createEmployeeDuplicatedName(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.NonUniqueName );

        controller.createSalaryEmployee( "James Jameson", 1.0 );
        controller.createSalaryEmployee( "James Jameson", 1.0 );
    }

    @Test
    public void test_createInternEmployee(){
        controller.createInternEmployee( "Winston Smith", 80.0 );

        assertEquals( 0.0, controller.getPayment( "Winston Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_createSeveralInterns(){
        controller.createInternEmployee( "Winston Smith", 100.0 );
        controller.createInternEmployee( "Sarah Smith", 80.0 );

        assertEquals( 0.0, controller.getPayment( "Winston Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        assertEquals( 0.0, controller.getPayment( "Sarah Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_createInternEmployeeEmptyName(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.EmptyName );

        controller.createInternEmployee( "", 5.0 );
    }

    @Test
    public void test_createInternEmployeeNegativeSalary(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.NonPositiveHoursLimit );

        controller.createInternEmployee( "James Jameson", -10.0 );
    }

    @Test
    public void test_createInternEmployeeDuplicatedName(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.NonUniqueName );

        controller.createInternEmployee( "James Jameson", 1.0 );
        controller.createInternEmployee( "James Jameson", 1.0 );
    }

    @Test
    public void test_trackWorkSeveralHourlyEmployess(){
        String emp1 = "James Jameson";
        String emp2 = "Sarah Smith";
        String emp3 = "Julia";

        controller.createHourlyEmployee( emp1, 5.0 );

        assertEquals( 0.0, controller.getPayment( emp1 ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.trackWork( emp1, 25.0 );

        assertEquals( 125.0, controller.getPayment( emp1 ), delta );
        assertEquals( 125.0, controller.getTotalPayment(), delta );

        controller.createHourlyEmployee( emp2, 50.0 );

        assertEquals( 0.0, controller.getPayment( emp2 ), delta );
        assertEquals( 125.0, controller.getTotalPayment(), delta );

        controller.trackWork( emp2, 60.0 );

        assertEquals( 3000.0, controller.getPayment( emp2 ), delta );
        assertEquals( 3125.0, controller.getTotalPayment(), delta );

        controller.createHourlyEmployee( emp3, 10.0 );

        assertEquals( 0.0, controller.getPayment( emp3 ), delta );
        assertEquals( 3125.0, controller.getTotalPayment(), delta );

        controller.trackWork( emp3, 160.0 );

        assertEquals( 1600.0, controller.getPayment( emp3 ), delta );
        assertEquals( 4725.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_trackWorkSeveralSalaryEmployees(){
        String emp1 = "James Jameson";
        String emp2 = "Sarah Smith";
        String emp3 = "Julia";

        controller.createSalaryEmployee( emp1, 500.0 );
        controller.trackWork( emp1, 25.0 );

        assertEquals( 500.0, controller.getPayment( emp1 ), delta );
        assertEquals( 500.0, controller.getTotalPayment(), delta );

        controller.createSalaryEmployee( emp2, 500.0 );
        controller.trackWork( emp2, 25.0 );

        assertEquals( 500.0, controller.getPayment( emp2 ), delta );
        assertEquals( 1000.0, controller.getTotalPayment(), delta );

        controller.createSalaryEmployee( emp3, 1500.0 );
        controller.trackWork( emp3, 160.0 );

        assertEquals( 1500.0, controller.getPayment( emp3 ), delta );
        assertEquals( 2500.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_trackWorkSeveralInternEmployees(){
        String emp1 = "James Jameson";
        String emp2 = "Sarah Smith";

        controller.createInternEmployee( emp1, 80.0 );

        assertEquals( 0.0, controller.getPayment( emp1 ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.trackWork( emp1, 25.0 );

        assertEquals( 0.0, controller.getPayment( emp1 ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.createInternEmployee( emp2, 80.0 );

        assertEquals( 0.0, controller.getPayment( emp2 ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.trackWork( emp2, 25.0 );

        assertEquals( 0.0, controller.getPayment( emp2 ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_trackWorkMixedEmployees(){
        String emp1 = "James Jameson";
        String emp2 = "Sarah Smith";
        String emp3 = "Julia";

        controller.createSalaryEmployee( emp1, 500.0 );

        assertEquals( 500.0, controller.getPayment( emp1 ), delta );
        assertEquals( 500.0, controller.getTotalPayment(), delta );

        controller.createHourlyEmployee( emp2, 25.0 );

        assertEquals( 500.0, controller.getPayment( emp1 ), delta );
        assertEquals( 500.0, controller.getTotalPayment(), delta );

        controller.trackWork( emp2, 25.0 );

        assertEquals( 625.0, controller.getPayment( emp2 ), delta );
        assertEquals( 1125.0, controller.getTotalPayment(), delta );

        controller.createInternEmployee( emp3, 100.0 );
        controller.trackWork( emp3, 90.0 );

        assertEquals( 0.0, controller.getPayment( emp3 ), delta );
        assertEquals( 1125.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_trackEmptyWorkerName(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.EmptyName );

        controller.createHourlyEmployee( "Sarah Smith", 500.0 );
        controller.trackWork( "", 10.0 );

        assertEquals( 0.0, controller.getPayment( "Sarah Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_trackUnknownEmployee(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.UnknownEmployeeName );

        controller.createHourlyEmployee( "Sarah Smith", 500.0 );
        controller.trackWork( "John Smith", 10.0 );

        assertEquals( 0.0, controller.getPayment( "Sarah Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_trackWorkNegativeHours(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.NegativeWorkHours );

        controller.createSalaryEmployee( "Sarah Smith", 500.0 );
        controller.trackWork( "Sarah Smith", -10.0 );

        assertEquals( 0.0, controller.getPayment( "Sarah Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_trackInternExactHours(){
        controller.createInternEmployee( "Sarah Smith", 180.0 );
        controller.trackWork( "Sarah Smith", 80.0 );

        assertEquals( 0.0, controller.getPayment( "Sarah Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.trackWork( "Sarah Smith", 80.0 );

        assertEquals( 0.0, controller.getPayment( "Sarah Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_trackInternExceedingHours(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.OverloadedIntern );

        controller.createInternEmployee( "Sarah Smith", 180.0 );
        controller.trackWork( "Sarah Smith", 80.0 );

        assertEquals( 0.0, controller.getPayment( "Sarah Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.trackWork( "Sarah Smith", 80.0 );

        assertEquals( 0.0, controller.getPayment( "Sarah Smith" ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.trackWork( "Sarah Smith", 80.0 );
    }

    @Test
    public void test_addBonusHourEmployee(){
        String emp = "Sarah Smith";

        controller.createHourlyEmployee( emp, 20.0 );

        assertEquals( 0.0, controller.getPayment( emp ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.trackWork( emp, 80.0 );

        assertEquals( 1600.0, controller.getPayment( emp ), delta );
        assertEquals( 1600.0, controller.getTotalPayment(), delta );

        controller.giveBonus( emp, 80 );

        assertEquals( 1680.0, controller.getPayment( emp ), delta );
        assertEquals( 1680.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_addBonusSalaryEmployee(){
        String emp = "Sarah Smith";

        controller.createSalaryEmployee( emp, 2000.0 );

        assertEquals( 2000.0, controller.getPayment( emp ), delta );
        assertEquals( 2000.0, controller.getTotalPayment(), delta );

        controller.trackWork( emp, 80.0 );

        assertEquals( 2000.0, controller.getPayment( emp ), delta );
        assertEquals( 2000.0, controller.getTotalPayment(), delta );

        controller.giveBonus( emp, 500 );

        assertEquals( 2500.0, controller.getPayment( emp ), delta );
        assertEquals( 2500.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_addBonusInternEmployee(){
        String emp = "Sarah Smith";

        controller.createInternEmployee( emp, 200.0 );

        assertEquals( 0.0, controller.getPayment( emp ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.trackWork( emp, 80.0 );

        assertEquals( 0.0, controller.getPayment( emp ), delta );
        assertEquals( 0.0, controller.getTotalPayment(), delta );

        controller.giveBonus( emp, 500 );

        assertEquals( 500.0, controller.getPayment( emp ), delta );
        assertEquals( 500.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_addBonusMixedEmployees(){
        String emp1 = "Sarah Smith";
        String emp2 = "John Smith";
        String emp3 = "Sarah James";

        controller.createSalaryEmployee( emp1, 2000.0 );
        controller.createHourlyEmployee( emp2, 200.0 );
        controller.createInternEmployee( emp3, 160.0 );

        controller.trackWork( emp1, 160 );
        controller.trackWork( emp2, 10 );
        controller.trackWork( emp3, 100 );

        controller.giveBonus( emp1, 200 );
        controller.giveBonus( emp2, 20 );
        controller.giveBonus( emp3, 200 );

        assertEquals( 2200.0, controller.getPayment( emp1 ), delta );
        assertEquals( 2020.0, controller.getPayment( emp2 ), delta );
        assertEquals( 200.0, controller.getPayment( emp3 ), delta );
        assertEquals( 4420.0, controller.getTotalPayment(), delta );
    }

    @Test
    public void test_addBonusEmptyName(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.EmptyName );

        controller.giveBonus( "", 10.0 );
    }

    @Test
    public void test_addBonusNegativeAmount(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.NonPositivePayment );

        controller.createHourlyEmployee( "John Smith", 100 );

        controller.giveBonus( "John Smith", -10.0 );
    }

    @Test
    public void test_addBonusUnknownEmployee(){
        thrown.expect( IllegalArgumentException.class );
        thrown.expectMessage( Messages.UnknownEmployeeName );

        controller.createHourlyEmployee( "John Smith", 100 );
        controller.giveBonus( "John Johnson", 10.0 );
    }
}