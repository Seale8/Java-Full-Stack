import React, { useEffect, useState } from "react";
import axios from "axios";

interface Ticket {
  ticketId: number;
  amount: number;
  description: string;
  status: string;
  postedBy: string; // Assuming this is the username or ID of the employee
}

export default function PendingTickets() {
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [error, setError] = useState("");
  const [alert, setAlert] = useState<{ message: string; type: string } | null>(null);


  // Fetch all pending tickets
  useEffect(() => {
    async function fetchPendingTickets() {
      try {
        const response = await axios.get("http://localhost:8080/tickets/pending");
        setTickets(response.data);
      } catch (error: any) {
        console.error("Error fetching pending tickets:", error);
        setError(error.response.data);
      }
    }

    fetchPendingTickets();
  }, []);

  // Handle ticket approval or denial
  async function handleStatusChange(ticketId: number, newStatus: "Approved" | "Denied") {
    try {
      await axios.put(`http://localhost:8080/tickets/${ticketId}/status`, { status: newStatus });
      // Show alert
      setAlert({
        message: `Ticket ${ticketId} has been ${newStatus.toLowerCase()}!`,
        type: newStatus === "Approved" ? "success" : "danger",
      });

      // Hide the alert after 3 seconds
      setTimeout(() => setAlert(null), 3000);
      setTickets((prevTickets) =>
        prevTickets.filter((ticket) => ticket.ticketId !== ticketId)
      ); // Remove the ticket from the list after updating
    } catch (error: any) {
      console.error(`Error updating ticket ${ticketId} status:`, error);
      setError(error.response.data);
    }
  }

  return (
    
    <div className="container mt-5">
      <h3>Pending Tickets</h3>
      {alert && (
        <div className={`alert alert-${alert.type} alert-dismissible fade show`} role="alert">
          {alert.message}
          <button type="button" className="btn-close" onClick={() => setAlert(null)}></button>
        </div>
      )}
      {error && <div className="alert alert-danger">{error}</div>}
      {tickets.length === 0 ? (
        <p>No pending tickets to review.</p>
      ) : (
        <table className="table table-bordered">
          <thead>
            <tr>
              <th>Ticket ID</th>
              <th>Amount</th>
              <th>Description</th>
              <th>Employee</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {tickets.map((ticket) => (
              <tr key={ticket.ticketId}>
                <td>{ticket.ticketId}</td>
                <td>${ticket.amount.toFixed(2)}</td>
                <td>{ticket.description}</td>
                <td>{ticket.postedBy}</td>
                <td>
                  <button
                    className="btn btn-success btn-sm me-2"
                    onClick={() => handleStatusChange(ticket.ticketId, "Approved")}
                  >
                    Approve
                  </button>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => handleStatusChange(ticket.ticketId, "Denied")}
                  >
                    Deny
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
