import React from 'react';
import Header from './components/Header';
import Tabs from './components/Tabs';
import PostList from './components/PostList';
import './styles.css';

function App() {
  return (
    <div>
      <Header />
      <Tabs />
      <PostList />
    </div>
  );
}

export default App;
