import React, { useContext, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { AuthContext } from "./ReducerUserContext";

export default function NavBar() {
  const context = useContext(AuthContext);
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const location = useLocation();

  const navigate = useNavigate();
  if (!context) {
    throw new Error("AuthContext must be used within an AuthProvider");
  }

  const { state, dispatch } = context;
  if (!state || !state.user) {
    return null; // Return null if no user is logged in
  }

  const isManager = state.user.role === "manager";
  const isEmployee = state.user.role === "employee";
  

  const handleLogout = () => {
    dispatch({ type: "LOGOUT" }); // Dispatch the 'LOGOUT' action to clear the user from the context
    navigate("/login"); // Redirect to the login page after logout
  };
  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen); // Toggle dropdown visibility
  };
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/">
          Reimbursement App
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto">
            {isEmployee &&location.pathname !== "/ticket-history" && (
              <li className="nav-item">
                <Link className="nav-link" to="/ticket-history">
                  Ticket History
                </Link>
              </li>
            )}
            {isManager &&location.pathname !== "/manager-ticket-history" && (
              <li className="nav-item">
                <Link className="nav-link" to="/manager-ticket-history">
                  Ticket History
                </Link>
              </li>

            )}
            {isManager &&location.pathname !== "/employees" && (
              <li className="nav-item">
                <Link className="nav-link" to="/employees">
                  Employee List
                </Link>
              </li>

              
            )}
            <li className="nav-item dropdown">
              <span
                className="nav-link dropdown-toggle"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded={dropdownOpen}
                onClick={toggleDropdown} // Toggle dropdown on click
              >
                {state.user.username} {/* Assuming the username is in state.user.username */}
              </span>
              <ul className={`dropdown-menu ${dropdownOpen ? "show" : ""}`} aria-labelledby="navbarDropdown">
                <li>
                  <Link className="dropdown-item" to="/settings">
                    Settings
                  </Link>
                </li>
                <li>
                  <hr className="dropdown-divider" />
                </li>
                <li>
                  <button className="dropdown-item" onClick={handleLogout}>
                    Logout
                  </button>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
