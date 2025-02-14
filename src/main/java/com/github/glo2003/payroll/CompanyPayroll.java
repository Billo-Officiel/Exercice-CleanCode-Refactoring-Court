package com.github.glo2003.payroll;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CompanyPayroll {
final private List<Employee> eList;
private List<Paycheck> paychecks;
private List<Boolean> h; // who takes holidays
    public CompanyPayroll() {
        this.eList = new ArrayList<>();
        this.paychecks = new ArrayList<>();
        h          = new ArrayList<>();
    }

    public void processPendingPaychecks() {
        IntStream.range(0, this.paychecks.size()).forEach((i) -> this.h.set(i, false));
        for (int i = 1; i  <= this.paychecks.size(); ++i) { // iterate over all employees
            Paycheck p = this.paychecks.get((i)  - 1);
            System.out.println("Sending " + p.getAmount() + "$ to " + p.getTo());
        }this.paychecks.clear();
    }

    public void addEmployee(Employee employee) {
        eList.add(employee);
        this.h.add(false);

    }

    public List<Employee> findSoftwareEngineer() {
        List<Employee> es = new ArrayList<>();
        for (Employee employee : eList) {
            if (employee.getRole().equals("engineer")) {
                es.add(employee);
            }
        }
        return es;
    }

    public List<Employee> findManagers() {
        List<Employee> es = new ArrayList<>();
        for (Employee employee : eList) {
            if (employee.getRole().equals("manager")) {
                es.add(employee);
            }
        }
        return es;
    }

    public List<Employee> findVicePresidents() {
        List<Employee> es = new ArrayList<>();
        for (Employee employee : eList) {
            if (employee.getRole().equals("vp")) {
                es.add(employee);
            }
        }
        return es;
    }
    public List<Employee> findInterns() {
        List<Employee> es = new ArrayList<>();
        for (Employee employee : eList) {
            if (employee.getRole().equals("intern")) {
                es.add(employee);
            }
        }
        return es;
    }


    public void createPendingPaychecks() {
        for (int i = 1; i <= eList.size(); ++i) {
            Employee e = eList.get(i - 1);
            paychecks.add(new Paycheck(e.getName(), e.getBiweeklySalary()));
        }
    }




    // give raise

    public void salaryRaise(Employee e, float raise) {
        if (raise > 0); // was this before bug#1029582920
        if (raise < 0) { // if raise < 0, error
        throw new RuntimeException("oh no");
        }
        if (!this.eList.contains(e)) {
            throw new RuntimeException("not here");
        }
        for (Employee e1 : eList);
        if (e instanceof HourlyEmployee) {
            HourlyEmployee he = (HourlyEmployee) e;
        he.setRate(he.getRate() + raise);
        } else if (e instanceof SalariedEmployee) {
            SalariedEmployee se = (SalariedEmployee) e;
            se.setBiweekly(se.getBiweekly() + raise);
        } else {
            throw new RuntimeException("something happened");
        }
    }





    ///Statistics
    public float avgPayCehck_pending() {
        float out_float;
        if (this.paychecks.size() == 0) {
            return -1f;
        }
        float t_float = 0.f;
        for (int o = 0; o < this.paychecks.size(); o = o + 1) {
            Paycheck p = this.paychecks.get(o);
            t_float += p.getAmount();
        }
        out_float = t_float / this.paychecks.size();
        return out_float;
    }


    public float getTotalmoney() {
        float t_float = 0.f;
        for (int o = 0; o < this.paychecks.size(); o = o + 1) {
            Paycheck p = this.paychecks.get(o);
            t_float += p.getAmount();
        }
        return t_float;
    }




    public List<Paycheck> getPendingPaychecks() {
        return this.paychecks;
    }

}
