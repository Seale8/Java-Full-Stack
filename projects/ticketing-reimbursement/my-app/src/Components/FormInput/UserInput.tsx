import React from "react";

type UserInputProps = {
  username: string;
  setUsername: React.Dispatch<React.SetStateAction<string>>;
  password: string;
  setPassword: React.Dispatch<React.SetStateAction<string>>;
  handleSubmit: any;
  title: string;
};
export default function UserInput({
  username,
  setUsername,
  password,
  setPassword,
  handleSubmit,
  title,
}: UserInputProps) {
  return (
    <div className="container mt-5 d-flex justify-content-center align-items-center">
    <div className="card shadow-lg p-4" style={{ width: '400px' }}>
      <h2 className="text-center mb-4">{title}</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="username" className="form-label">
            Username:
          </label>
          <input
            type="text"
            id="username"
            className="form-control"
            value={username}
            onChange={(e: any) => setUsername(e.target.value)}
            placeholder="Enter username"
          />
        </div>
        <div className="mb-3">
          <label htmlFor="password" className="form-label">
            Password:
          </label>
          <input
            type="password"
            id="password"
            className="form-control"
            value={password}
            onChange={(e: any) => setPassword(e.target.value)}
            placeholder="Enter password"
          />
        </div>
        <div className="text-center">
          <button type="submit" className="btn btn-primary w-100">
            Submit
          </button>
        </div>
      </form>
    </div>
  </div>
  );
}
