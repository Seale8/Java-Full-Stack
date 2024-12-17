import React from "react";

function EventsDemo(){
    function clickEventTrigger(){
        console.log("CLick button triggered");

    }
    return(
        <>
        <button onClick ={clickEventTrigger}>Click event button</button>
        <button onMouseOver={() =>console.log("hover ecent triggered")}>Hover Event</button>
        <input type = "text" 
        </>
    )
}