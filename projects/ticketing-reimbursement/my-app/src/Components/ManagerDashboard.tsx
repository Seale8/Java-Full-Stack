import React from "react";
import PendingTickets from "./PendingTickets";
import NavBar from "./NavBar";

export default function ManagerPage() {
  return (
    <>
      <NavBar />
      <div className="container mt-5">
        <h2>Manager Dashboard</h2>
        <PendingTickets />
      </div>
    </>
  );
}
