import React, { useEffect, useState } from "react";
import axios from "axios";
import NavBar from "./NavBar";

interface Employee {
  accountId: number;
  name: string;
  role: string;
  username: string;
}

export default function ManagerEmployeeList() {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [error, setError] = useState<string>("");
  const [success, setSuccess] = useState<string>("");

  useEffect(() => {
    async function fetchEmployees() {
      try {
        const response = await axios.get(
          "http://localhost:8080/accounts/employee"
        ); // Fetch all employees
        console.log(response.data);
        setEmployees(response.data);
      } catch (error: any) {
        setError(error.response.data);
      }
    }

    fetchEmployees();
  }, []);

  const promoteEmployee = async (employeeId: number) => {
    try {
      const response = await axios.put(
        `http://localhost:8080/promote/${employeeId}`
      );
      if (response.status === 200) {
        setSuccess("Employee promoted to Manager successfully!");

        // Update the employee's role in the local state
        setEmployees((prevEmployees) =>
          prevEmployees.map((employee) =>
            employee.accountId === employeeId
              ? { ...employee, role: "manager" }
              : employee
          )
        );
        console.log(
          `Employee ${employeeId} promoted successfully! Updated state:`,
          employees
        );
        setTimeout(() => setSuccess(""), 3000);
      }
    } catch (error: any) {
      setError(error.response.data);
    }
  };

  return (
    <>
      <NavBar />
      <div className="container mt-5">
        <h3>Employees</h3>
        {error && <div className="alert alert-danger">{error}</div>}
        {success && <div className="alert alert-success">{success}</div>}

        <table className="table table-bordered mt-3">
          <thead>
            <tr>
              <th>Name</th>
              <th>Role</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {employees.map(
              (employee) =>
                employee.role !== "manager" && (
                  <tr key={employee.accountId}>
                    <td>{employee.username}</td>
                    <td>{employee.role}</td>
                    <td>
                      <button
                        className="btn btn-primary"
                        onClick={() => promoteEmployee(employee.accountId)}
                      >
                        Promote to Manager
                      </button>
                    </td>
                  </tr>
                )
            )}
          </tbody>
        </table>
      </div>
    </>
  );
}
