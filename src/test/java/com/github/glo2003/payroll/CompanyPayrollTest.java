package com.github.glo2003.payroll;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class CompanyPayrollTest {

    public static final float HOURLY_RATE = 10;
    public static final float HOURLY_AMOUNT = 25;
    public static final String HOURLY_NAME = "William";
    public static final String SALARIED_NAME = "Xavier";
    public static final float BIWEEKLY_AMOUNT = 10_000;
    public static final float RAISE = 10;
    public static final float ANOTHER_MONTHLY_AMOUNT = 20_000;
    public static final int VACATION_DAYS = 12;
    public static final int NUMBER_OF_EMPLOYEES = 5;
    CompanyPayroll company;
    Employee vicePresident;
    Employee engineer;
    Employee manager;
    Employee intern1;
    Employee intern2;
    HourlyEmployee hourlyEmployee;
    SalariedEmployee salariedEmployee;
    SalariedEmployee anotherSalariedEmployee;

    @BeforeEach
    void setUp() {
        company = new CompanyPayroll();
        vicePresident = new HourlyEmployee("Alice", "vp", 25, 100, 35.5f * 2);
        engineer = new SalariedEmployee("Bob", "engineer", 4, 1500);
        manager = new SalariedEmployee("Charlie", "manager", 10, 2000);
        intern1 = new HourlyEmployee("Ernest", "intern", 10, 5, 50 * 2);
        intern2 = new HourlyEmployee("Fred", "intern", 10, 5, 50 * 2);

        hourlyEmployee = new HourlyEmployee(HOURLY_NAME, "engineer", VACATION_DAYS, HOURLY_RATE, HOURLY_AMOUNT);
        salariedEmployee = new SalariedEmployee(SALARIED_NAME, "engineer", VACATION_DAYS, BIWEEKLY_AMOUNT);
        anotherSalariedEmployee = new SalariedEmployee("Yan", "manager", VACATION_DAYS, ANOTHER_MONTHLY_AMOUNT);
    }

    @Test
    void createPendingsCreatesCorrectHourlyPaycheck() {
        company.addEmployee(hourlyEmployee);

        company.createPendingPaychecks();

        Paycheck paycheck = company.getPendingPaychecks().get(0);
        assertThat(paycheck.getTo()).isEqualTo(HOURLY_NAME);
        assertThat(paycheck.getAmount()).isEqualTo(HOURLY_RATE * HOURLY_AMOUNT);
    }

    @Test
    void createPendingsCreatesCorrectSalariedPaycheck() {
        company.addEmployee(salariedEmployee);

        company.createPendingPaychecks();

        Paycheck paycheck = company.getPendingPaychecks().get(0);
        assertThat(paycheck.getTo()).isEqualTo(SALARIED_NAME);
        assertThat(paycheck.getAmount()).isEqualTo(BIWEEKLY_AMOUNT);
    }

    @Test
    void processPending_shouldRemovePendingPaychecks() {
        company.addEmployee(vicePresident);
        company.addEmployee(engineer);
        company.addEmployee(manager);
        company.addEmployee(intern1);
        company.addEmployee(intern2);
        company.createPendingPaychecks();

        company.processPendingPaychecks();

        assertThat(company.getPendingPaychecks().size()).isEqualTo(0);
    }

    @Test
    void findSoftwareEngineer_shouldReturnSoftwareEngineers() {
        company.addEmployee(engineer);

        List<Employee> employee = company.findSoftwareEngineer();
        assertThat(employee).containsExactly(engineer);
    }

    @Test
    void findManagersShouldReturnManagers() {
        company.addEmployee(manager);

        List<Employee> employee = company.findManagers();
        assertThat(employee).containsExactly(manager);
    }

    @Test
    void findVicePresidentsShouldReturnVicePresidents() {
        company.addEmployee(vicePresident);

        List<Employee> employee = company.findVicePresidents();
        assertThat(employee).containsExactly(vicePresident);
    }

    @Test
    void find_interns_shouldReturnInterns() {
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        List<Employee> employee = company.findInterns();
        assertThat(employee).containsExactly(intern1, intern2);
    }

    @Test
    void createPending_shouldCreatePendingPaycheck() {
        company.addEmployee(vicePresident);
        company.addEmployee(engineer);
        company.addEmployee(manager);
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        company.createPendingPaychecks();

        assertThat(company.getPendingPaychecks().size()).isEqualTo(5);
    }

    @Test
    void hourlyEmployee() {
        company.addEmployee(vicePresident);
        company.addEmployee(engineer);
        company.addEmployee(manager);
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        company.createPendingPaychecks();

        assertThat(company.getPendingPaychecks().size()).isEqualTo(NUMBER_OF_EMPLOYEES);
    }

    @Test
    void hourlyRaiseShouldRaiseHourlySalary() {
        company.addEmployee(hourlyEmployee);

        company.salaryRaise(hourlyEmployee, RAISE);

        company.createPendingPaychecks();
        Paycheck paycheck = company.getPendingPaychecks().get(0);
        assertThat(paycheck.getAmount()).isEqualTo((HOURLY_RATE + RAISE) * HOURLY_AMOUNT);
    }

    @Test
    void salariedRaiseShouldRaiseMonthlySalary() {
        company.addEmployee(salariedEmployee);

        company.salaryRaise(salariedEmployee, RAISE);

        company.createPendingPaychecks();
        Paycheck paycheck = company.getPendingPaychecks().get(0);
        assertThat(paycheck.getAmount()).isEqualTo(BIWEEKLY_AMOUNT + RAISE);
    }

    @Test
    void negativeRaiseShouldThrow() {
        company.addEmployee(engineer);

        Assert.assertThrows(RuntimeException.class, () -> company.salaryRaise(engineer, -1));
    }

    @Test
    void cannotGiveRaiseIfNotInCompany() {
        Assert.assertThrows(RuntimeException.class, () -> company.salaryRaise(engineer, 10));
    }

    @Test
    void avgPayCehck_pending() {
        company.addEmployee(salariedEmployee);
        company.addEmployee(anotherSalariedEmployee);
        company.createPendingPaychecks();

        float avg = company.avgPayCehck_pending();

        assertThat(avg).isEqualTo((BIWEEKLY_AMOUNT + ANOTHER_MONTHLY_AMOUNT) / 2);
    }

    @Test
    void getTotalmoney() {
        company.addEmployee(salariedEmployee);
        company.addEmployee(anotherSalariedEmployee);
        company.createPendingPaychecks();

        float t = company.getTotalmoney();

        assertThat(t).isEqualTo(BIWEEKLY_AMOUNT + ANOTHER_MONTHLY_AMOUNT);
    }
}