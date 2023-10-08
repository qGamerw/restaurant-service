import React from 'react';
import './App.css';
import RegistrationPage from "./pages/Registration";
import {Route, Routes} from "react-router-dom";
import {NotFoundPage} from "./pages/NotFoundPage";

function App() {
  return (
    <div className="App">
        <Routes>
            <Route index element={<RegistrationPage/>}/>
            {/*<Route path="/" element={<NotFoundPage/>}/>*/}

            <Route path="*" element={<NotFoundPage/>}/>
        </Routes>
    </div>
  );
}

export default App;
