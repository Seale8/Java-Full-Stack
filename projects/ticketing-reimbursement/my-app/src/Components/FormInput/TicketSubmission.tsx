import React, { useState, FormEvent, useContext } from "react";
import axios from "axios";
import { AuthContext } from "../ReducerUserContext";

interface SubmitTicketProps {
  onTicketSubmitted: () => void;
}

export default function SubmitTicket({ onTicketSubmitted }: SubmitTicketProps) {
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [error, setError] = useState("");
  const [alert, setAlert] = useState<{ message: string; type: string } | null>(null);
  const [success, setSuccess] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false); // To manage loading state

  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("AuthContext must be used within an AuthProvider");
  }

  const { state } = context;

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();

    // Clear previous messages
    setError("");
    setSuccess("");
    setAlert(null);

    // Validation
    if (!amount || isNaN(Number(amount)) || Number(amount) <= 0) {
      setError("Please enter a valid amount greater than 0.");
      return;
    }

    if (!description.trim()) {
      setError("Description is required.");
      return;
    }

    setIsSubmitting(true); // Set submitting to true while API call is in progress

    try {
        console.log(state);
      const response = await axios.post("http://localhost:8080/submit-ticket", {
        amount: parseFloat(amount),
        description,
        status: "Pending",
        postedBy: state.user?.accountId,
      });

      if (response.status === 200) {
        setSuccess("Ticket submitted successfully!");
        setAmount("");
        setDescription("");

        onTicketSubmitted();
        setAlert({ message: "Ticket submitted successfully", type: "success" });

        setTimeout(() => {
          setAlert(null);
        }, 3000);
      }
    } catch (error: any) {
      console.error("Error submitting ticket:", error);
      setError(error.response.data);
      setAlert({ message: "Failed to submit the ticket. Please try again.", type: "danger" });

      // Hide alert after 3 seconds
      setTimeout(() => {
        setAlert(null);
      }, 3000);
    } finally {
      setIsSubmitting(false); // Reset submitting state after API call completes
    }
  }

  return (
    <div className="container mt-5">
      <h3>Submit Reimbursement Ticket</h3>
      {alert && (
        <div className={`alert alert-${alert.type}`} role="alert">
          {alert.message}
        </div>
      )}
      {!alert&&error && <div className="alert alert-danger">{error}</div>}
      {!alert &&success && <div className="alert alert-success">{success}</div>}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="amount" className="form-label">
            Amount:
          </label>
          <input
            type="number"
            id="amount"
            className="form-control"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            placeholder="Enter amount"
          />
        </div>
        <div className="mb-3">
          <label htmlFor="description" className="form-label">
            Description:
          </label>
          <textarea
            id="description"
            className="form-control"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Enter description"
            rows={3}
          ></textarea>
        </div>
        <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
          {isSubmitting ? "Submitting..." : "Submit Ticket"}
        </button>
      </form>
    </div>
  );
}
