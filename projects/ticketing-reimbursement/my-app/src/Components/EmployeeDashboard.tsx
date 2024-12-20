import React, { useState } from 'react'
import SubmitTicket from './FormInput/TicketSubmission'
import NavBar from './NavBar';
import TicketHistory from './EmployeePendingTickets';
import EmployeePendingTickets from './EmployeePendingTickets';

export default function EmployeeDashboard() {
  const [refreshKey, setRefreshKey] = useState(0);

  const handleTicketSubmitted = () => {
    // Increment the key to force a refresh of TicketHistory
    setRefreshKey((prevKey) => prevKey + 1);
  };
  return (
    <div>
      <NavBar />
      <div className="container mt-5">
        <h1>Employee Dashboard</h1>
        <div className="card shadow-lg p-4">
          <SubmitTicket onTicketSubmitted={handleTicketSubmitted} />
        </div>

        {/* Placeholder for other features */}
        <div className="card shadow-lg p-4 mt-4">
          <h3>Reimbursement History</h3>
          <EmployeePendingTickets key={refreshKey}/>
        </div>
      </div>
    </div>
  );
}
