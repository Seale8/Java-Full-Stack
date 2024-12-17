import React, { useContext } from 'react'
import { AuthContext } from '../ReducerUserContext';

export default function UserInfo() {
    const context = useContext(AuthContext);

    if(!context){
        throw new Error("UserInfo must be used within a user provider");
    }

    const {state, dispatch} = context;
  return (
    <>
     {
        state.user && (
            <div>
                <p>Welcome, {state.user.username}</p>
                <button onClick={() => dispatch({type: "LOGOUT"})}>Logout</button>
            </div>
        )
    }
    </>
  )
}
