package eu.lpinto.universe.controllers;

import eu.lpinto.universe.persistence.entities.Employee;
import eu.lpinto.universe.persistence.facades.EmployeeFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Controller for Employee entity.
 *
 * @author Luis Pinto <code>- luis.pinto@petuniversal.com</code>
 */
@Stateless
public class EmployeeController extends AbstractCrudController<Employee> {

    @EJB
    private EmployeeFacade facade;

    public EmployeeController() {
        super(Employee.class.getCanonicalName());
    }

    @Override
    protected EmployeeFacade getFacade() {
        return facade;
    }
}
