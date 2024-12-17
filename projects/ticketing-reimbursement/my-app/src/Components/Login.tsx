import React, { FormEvent, useContext, useState } from 'react'
import { AuthContext } from './ReducerUserContext';
import UserInput from './FormInput/UserInput';
import UserInfo from './FormInput/UserInfo';
import axios from "axios";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("AuthContext must be used within an AuthProvider");
    }

    const {dispatch } = context;

    async function handleSubmit(event: FormEvent){
        event.preventDefault();
        if(!username || !password){
            setError("Username and password are required");
            return;
        }

        try {
            const response = await axios.post("http://localhost:8080/login", {
              username,
              password,
            });

              const account = response.data; // Account object returned from Spring
              console.log("Logged in:", account);
  
          dispatch({type: 'LOGIN', payload: {username, password}});
          setError("");
            }catch (error) {
                setError("Invalid credentials. Please try again.");
                console.log(error);
              }
      
           
        
    }



  return (
    <div>
     <UserInfo/>
     <UserInput
     username={username}
     setUsername={setUsername}
     password={password}
     setPassword={setPassword}
     handleSubmit={handleSubmit}/>
    </div>
  )
}
