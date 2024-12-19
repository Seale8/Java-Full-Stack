import React from 'react'
import SubmitTicket from './FormInput/TicketSubmission'
import NavBar from './NavBar';

export default function EmployeeDashboard() {
  return (
    <div>
      <NavBar /> {/* Add NavBar */}
      <div className="container mt-5">
        <h1>Employee Dashboard</h1>
        <div className="card shadow-lg p-4">
          <SubmitTicket />
        </div>

        {/* Placeholder for other features */}
        <div className="card shadow-lg p-4 mt-4">
          <h3>Reimbursement History</h3>
          <p>Coming soon...</p>
        </div>
      </div>
    </div>
  );
}
