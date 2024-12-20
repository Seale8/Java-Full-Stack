import React from "react";
import "./App.css";
import { Route, Routes } from "react-router-dom";
import Login from "./Components/Login";
import Register from "./Components/Register";
import { AuthProvider } from "./Components/ReducerUserContext";
import "bootstrap/dist/css/bootstrap.min.css";
import { ProtectedRoute } from "./Components/ProtectedRoute";
import ManagerDashboard from "./Components/ManagerDashboard";
import EmployeeDashboard from "./Components/EmployeeDashboard";
import EmployeeNonPendingTickets from "./Components/EmployeeTicketHistory";
import EmployeeTicketHistory from "./Components/EmployeeTicketHistory";
import EmployeeList from "./Components/EmployeeList";
import ManagerTicketHistory from "./Components/ManagerTicketHistory";

function App() {
  return (
    <div className="App">
      <AuthProvider>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/login" element={<Login />} />

          <Route path="/register" element={<Register />} />

          {/* Employee Dashboard Route */}
          <Route element={<ProtectedRoute allowedRoles={["employee"]} />}>
            <Route path="/employee-dashboard" element={<EmployeeDashboard />} />
            <Route path="/" element={<EmployeeDashboard />} />
            <Route path="/ticket-history" element={<EmployeeTicketHistory />} />
          </Route>

          {/* Manager Dashboard Route */}
          <Route element={<ProtectedRoute allowedRoles={["manager"]} />}>
            <Route path="/manager-dashboard" element={<ManagerDashboard />} />
            <Route path="/" element={<ManagerDashboard />} />
            <Route path="/" element={<ManagerDashboard />} />
            <Route path="/employees" element={<EmployeeList />} />
            <Route path="/manager-ticket-history" element={<ManagerTicketHistory />} />
            
          </Route>
        </Routes>
      </AuthProvider>
    </div>
  );
}

export default App;
