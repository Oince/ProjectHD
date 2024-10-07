//회원가입 화면

import React, { useState } from 'react';
import axios from 'axios';

function Signup() {
  const [name, setname] = useState('');
  const [loginId, setloginId] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [successMessage , setSuccessMessage] = useState('');

  const handleSignup = async (e) => {
    e.preventDefault();

     // 입력 값 유효성 검사
     if (loginId.length < 5 || loginId.length > 30) {
      setError('아이디는 최소 5글자에서 최대 30글자여야 합니다.');
      setSuccessMessage('');
      return;
    }

    if (password.length < 5 || password.length > 30) {
      setError('비밀번호는 최소 5글자에서 최대 30글자여야 합니다.');
      setSuccessMessage('');
      return;
    }

    if (name.length < 2 || name.length > 20) {
      setError('닉네임은 최소 2글자에서 최대 20글자여야 합니다.');
      setSuccessMessage('');
      return;
    }

    try {
      const response = await axios.post('http://172.20.10.3:8080/signup', {
        name,
        loginId,
        password,
      });
      

        // 요청 성공 시
      setSuccessMessage('회원가입 성공!');
      setError('');  // 에러 메시지 초기화

      // 입력 필드 초기화
      setloginId('');
      setPassword('');
      setname('');
    
    } catch (error) {
      if (error.response && error.response.status === 400) {
        setError('회원가입 실패: 중복된 아이디가 있습니다.');
      } else {
        setError('회원가입 중 오류가 발생했습니다.');
      }
      setSuccessMessage('');
    }
  };

  return (
    <div className="signup-container">
      <h2>회원가입</h2>
      <form onSubmit={handleSignup}>
        <input
          type="text"
          placeholder="닉네임"
          value={name}
          onChange={(e) => setname(e.target.value)}
          required
        />
        <input
          type="text"
          placeholder="아이디"
          value={loginId}
          onChange={(e) => setloginId(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit" className="signup-submit">회원가입</button>
      </form>
      {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
}

export default Signup;
