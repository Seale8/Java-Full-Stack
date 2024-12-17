import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import ComponentOne from './Components/ComponentOne/ComponentOne';

function App() {
  return (
    <div className="App">
      <ComponentOne/>
      <EventsDemo/>
    </div>
  );
}

export default App;
