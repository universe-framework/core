package eu.lpinto.universe.api.services;

import eu.lpinto.universe.api.dto.Employee;
import eu.lpinto.universe.api.dts.EmployeeDTS;
import eu.lpinto.universe.controllers.EmployeeController;
import javax.ejb.EJB;
import javax.ws.rs.Path;

/**
 * REST CRUD service for Employee.
 *
 * @author Luis Pinto <code>- luis.pinto@petuniversal.com</code>
 */
@Path("Employees")
public class EmployeeService extends AbstractCRUDService<eu.lpinto.universe.persistence.entities.Employee, Employee, EmployeeController, EmployeeDTS> {

    @EJB
    private EmployeeController controller;

    public EmployeeService() {
        super(EmployeeDTS.T);
    }

    @Override
    protected EmployeeController getController() {
        return controller;
    }
}
