import React from 'react';
import Header from './components/Header';
import Tabs from './components/Tabs';
import PostList from './components/PostList';
import Signup from './components/Signup';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import './styles.css';

function App() {
  return (
    <Router>
      <div>
        <Header />
        <Routes>
          <Route path="/" element={<>
            <Tabs />
            <PostList />
          </>} />
          <Route path="/signup" element={<Signup />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
