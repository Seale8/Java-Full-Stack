import React, { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "./ReducerUserContext";

export default function NavBar() {
    const context = useContext(AuthContext);
    

    const navigate = useNavigate();
    if (!context) {
        throw new Error('AuthContext must be used within an AuthProvider');
      }
    
      const { state, dispatch } = context;

    const handleLogout = () => {
        dispatch({ type: "LOGOUT" });  // Dispatch the 'LOGOUT' action to clear the user from the context
        navigate("/login");  // Redirect to the login page after logout
      }
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
          {/* Logout link moved to the right */}
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              
              <button className="nav-link btn" onClick={handleLogout}>
                Logout
              </button>
             
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
