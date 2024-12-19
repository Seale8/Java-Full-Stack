import React, { useState, FormEvent } from "react";
import axios from "axios";

export default function SubmitTicket() {
  const [amount, setAmount] = useState("");
  const [description, setDescription] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();

    // Clear previous messages
    setError("");
    setSuccess("");

    // Validation
    if (!amount || isNaN(Number(amount)) || Number(amount) <= 0) {
      setError("Please enter a valid amount greater than 0.");
      return;
    }

    if (!description.trim()) {
      setError("Description is required.");
      return;
    }

    try {
      const response = await axios.post("http://localhost:8080/submit-ticket", {
        amount: parseFloat(amount),
        description,
        status: "Pending"
      });

      if (response.status === 200) {
        setSuccess("Ticket submitted successfully!");
        setAmount("");
        setDescription("");
      }
    } catch (err: any) {
      console.error("Error submitting ticket:", err);
      setError("Failed to submit ticket. Please try again later.");
    }
  }

  return (
    <div className="container mt-5">
      <h3>Submit Reimbursement Ticket</h3>
      {error && <div className="alert alert-danger">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}
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
        <button type="submit" className="btn btn-primary">
          Submit Ticket
        </button>
      </form>
    </div>
  );
}
