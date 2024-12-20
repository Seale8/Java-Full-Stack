import React, { useContext, useEffect, useState } from "react";
import axios from "axios";
import NavBar from "./NavBar";
import { AuthContext } from "./ReducerUserContext";

interface Ticket {
  ticketId: number;
  amount: number;
  description: string;
  status: string;
}

export default function EmployeeTicketHistory() {
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [error, setError] = useState("");
  const context = useContext(AuthContext);

  const [sortConfig, setSortConfig] = useState<{
    key: keyof Ticket;
    direction: "asc" | "desc";
  } | null>(null);

  if (!context) {
    throw new Error("AuthContext must be used within an AuthProvider");
  }

  const { state } = context;

  useEffect(() => {
    async function fetchNonPendingTickets() {
      try {
        if (!state.user) {
          setError("No user found.");
          return;
        }
        const response = await axios.get(
          `http://localhost:8080/tickets/${state.user.accountId}/history`
        );
        const ticketsData = Array.isArray(response.data) ? response.data : [];
        setTickets(ticketsData);
      } catch (err: any) {
        console.error("Error fetching previous tickets:", err);
        setError(err.response.data);
      }
    }

    fetchNonPendingTickets();
  }, [state.user]);

  const getStatusColor = (status: string) => {
    switch (status.toLowerCase()) {
      case "approved":
        return "green";
      case "denied":
        return "red";
      default:
        return "gray"; // Default color if the status is unknown
    }
  };

  return (
    <>
      <NavBar />
      <div className="container mt-5">
        <h4>Ticket History</h4>
        {error && <div className="alert alert-danger">{error}</div>}
        {tickets.length === 0 && <p>No previous tickets found.</p>}
        <table className="table table-bordered mt-3">
          <thead>
            <tr>
              <th>Ticket ID</th>
              <th>Amount</th>
              <th>Description</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {tickets.map((ticket) => (
              <tr key={ticket.ticketId}>
                <td>{ticket.ticketId}</td>
                <td>${ticket.amount.toFixed(2)}</td>
                <td>{ticket.description}</td>
                <td>
                  <span
                    className={`status-light ${getStatusColor(ticket.status)}`}
                  ></span>
                  {ticket.status}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}
