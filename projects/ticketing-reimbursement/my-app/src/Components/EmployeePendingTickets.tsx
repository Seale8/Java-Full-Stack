import React, { useEffect, useState, useContext } from "react";
import axios from "axios";
import { AuthContext } from "./ReducerUserContext";

interface Ticket {
  ticketId: number;
  amount: number;
  description: string;
  status: string;
}

export default function EmployeePendingTickets() {
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [error, setError] = useState("");
  const context = useContext(AuthContext);
  const [alert, setAlert] = useState<{ message: string; type: string } | null>(
    null
  );

  const [sortConfig, setSortConfig] = useState<{
    key: keyof Ticket;
    direction: "asc" | "desc";
  } | null>(null);

  if (!context) {
    throw new Error("AuthContext must be used within an AuthProvider");
  }

  const { state } = context;

  useEffect(() => {
    async function fetchTickets() {
      try {
        if (!state.user) {
          setError("No user found.");
          return;
        }

        const response = await axios.get(
          `http://localhost:8080/tickets/${state.user.accountId}/Pending`
        );
        console.log(response.data);
        const ticketsData = Array.isArray(response.data) ? response.data : [];

        setTickets(ticketsData);
      } catch (err: any) {
        console.error("Error fetching tickets:", err);
        setError(err.response.data);
      }
    }

    fetchTickets();
  }, [state.user]);

  async function handleDelete(ticketId: number) {
    try {
      const response = await axios.delete(
        `http://localhost:8080/tickets/${ticketId}`
      );

      if (response.status === 200) {
        setTickets((prevTickets) =>
          prevTickets.filter((ticket) => ticket.ticketId !== ticketId)
        );

        setAlert({ message: "Ticket deleted successfully", type: "success" });

        setTimeout(() => {
          setAlert(null); // Hide the alert after 3 seconds
        }, 3000);
      }
    } catch (err: any) {
      console.error("Error deleting ticket:", err);
      setError("Failed to delete the ticket. Please try again later.");
    }
  }

  const getStatusColor = (status: string) => {
    switch (status.toLowerCase()) {
      case "pending":
        return "yellow";
      case "approved":
        return "green";
      case "denied":
        return "red";
      default:
        return "gray"; // Default color if the status is unknown
    }
  };

  const handleSort = (key: keyof Ticket) => {
    let direction: "asc" | "desc" = "asc";

    if (
      sortConfig &&
      sortConfig.key === key &&
      sortConfig.direction === "asc"
    ) {
      direction = "desc";
    }

    setSortConfig({ key, direction });

    const sortedTickets = [...tickets].sort((a, b) => {
      if (a[key] < b[key]) return direction === "asc" ? -1 : 1;
      if (a[key] > b[key]) return direction === "asc" ? 1 : -1;
      return 0;
    });

    setTickets(sortedTickets);
  };

  return (
    <div className="container mt-5">
      <h4>Your Tickets</h4>
       {/* Show success or error alert */}
       {alert && (
        <div className={`alert alert-${alert.type}`} role="alert">
          {alert.message}
        </div>
      )}
      {error && <div className="alert alert-danger">{error}</div>}
      {tickets.length === 0 && <p>No tickets submitted yet.</p>}
      <table className="table table-bordered mt-3">
        <thead>
          <tr>
            <th
              onClick={() => handleSort("ticketId")}
              style={{ cursor: "pointer" }}
            >
              Ticket ID{" "}
              {sortConfig?.key === "ticketId"
                ? sortConfig.direction === "asc"
                  ? "↑"
                  : "↓"
                : ""}
            </th>
            <th
              onClick={() => handleSort("amount")}
              style={{ cursor: "pointer" }}
            >
              Amount{" "}
              {sortConfig?.key === "amount"
                ? sortConfig.direction === "asc"
                  ? "↑"
                  : "↓"
                : ""}
            </th>
            <th
              onClick={() => handleSort("description")}
              style={{ cursor: "pointer" }}
            >
              Description{" "}
              {sortConfig?.key === "description"
                ? sortConfig.direction === "asc"
                  ? "↑"
                  : "↓"
                : ""}
            </th>
            <th
              onClick={() => handleSort("status")}
              style={{ cursor: "pointer" }}
            >
              Status{" "}
              {sortConfig?.key === "status"
                ? sortConfig.direction === "asc"
                  ? "↑"
                  : "↓"
                : ""}
            </th>
            <th>Actions</th>
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
              <td className="no-border">
                <div className="d-flex justify-content-center">
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => handleDelete(ticket.ticketId)}
                    disabled={ticket.status !== "Pending"}
                  >
                    Delete
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
