import React from 'react';
import Header from './components/Header';
import Tabs from './components/Tabs';
import SignUp from './components/SignUp';
import Write from './components/Write';
import PostDetail from './components/PostDetail';
import Edit from './components/Edit';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { UserProvider } from './components/UserContext';
import './styles.css';

function App() {
  return (
    <UserProvider>
      <Router>
        <div>
          <Header />
          <Routes>
            <Route path="/" element={<>
              <Tabs />
            </>} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/write" element={<Write />} />
            <Route path="/boards/:boardId" element={<PostDetail />} /> {/* Dynamic route for post detail */}
            <Route path="/edit" element={<Edit />} />
          </Routes>
        </div>
      </Router>
    </UserProvider>
  );
}

export default App;
