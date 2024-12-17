import React from 'react'

type UserInputProps = 
{username: string, setUsername: React.Dispatch<React.SetStateAction<string>>, password: string, setPassword: React.Dispatch<React.SetStateAction<string>>, handleSubmit: any };
export default function UserInput({username, setUsername, password, setPassword, handleSubmit}: UserInputProps) {
  return (
    <form onSubmit={handleSubmit}>
        <label>
        Username:
            <input
            type="text" 
            value={username} 
            onChange={(e: any) => setUsername(e.target.value)}
            placeholder="Enter username"
            />
        </label>
        <br/>
        <label>
        Password:
            <input 
            type="password" 
            value={password} 
            onChange={(e: any) => setPassword(e.target.value)}
            placeholder="Enter password"
            />
        </label>
        <br/>
        <button type="submit">Submit</button>
    </form>
  )
}
