import React, { useState } from 'react';
import { Link } from 'react-router-dom'; // React Router의 Link 컴포넌트 사용
import axios from 'axios';

function Header() {
  const [loginId, setLoginId] = useState(''); // 로그인 아이디 입력 상태
  const [password, setPassword] = useState(''); // 비밀번호 입력 상태
  const [nickname, setNickname] = useState(''); // 로그인 성공 시 닉네임 상태

  // 로그인 요청 핸들러
  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      // 로그인 요청 보내기
      const response = await axios.post('http://172.20.10.3:8080/login', {
        loginId,
        password,
      });

      // 로그인 성공 시 닉네임 설정
      setNickname(response.data.name); // 서버로부터 닉네임을 받아옴
      setLoginId(''); // 로그인 필드 초기화
      setPassword(''); // 비밀번호 필드 초기화
    } catch (error) {
      // 로그인 실패 시 팝업 메시지 표시
      alert('로그인에 실패했습니다.');
      setLoginId(''); // 로그인 필드 초기화
      setPassword(''); // 비밀번호 필드 초기화
    }
  };

  // 로그아웃 핸들러
  const handleLogout = () => {
    setNickname(''); // 닉네임 초기화 -> 비로그인 상태로 되돌림
    window.alert('로그아웃 되었습니다.');
  };

  return (
    <header className="header">
      <div className="left">
        <h1>핫딜사이트</h1> {/* 사이트 이름, 홈 버튼 */}
      </div>

      <div className="right">
        {nickname ? (
          <div>
            {/* 로그인 성공 시 닉네임과 로그아웃 버튼, 글작성 버튼 표시 */}
            <span>안녕하세요, {nickname}님!</span>
            <Link to="/write"> {/* 글작성 버튼 */}
              <button className="write-button">글작성</button>
            </Link>
            <button className="logout-button" onClick={handleLogout}>로그아웃</button>
          </div>
        ) : (
          <form onSubmit={handleLogin}>
            {/* 로그인 폼 */}
            <input
              type="text"
              placeholder="아이디"
              className="input-field"
              value={loginId}
              onChange={(e) => setLoginId(e.target.value)}
              required
            />
            <input
              type="password"
              placeholder="비밀번호"
              className="input-field"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <button className="login-button" type="submit">로그인</button>
          </form>
        )}

        {/* 로그인하지 않은 상태에서만 회원가입 버튼 표시 */}
        {!nickname && (
          <Link to="/Signup">
            <button className="signup-button">회원가입</button>
          </Link>
        )}
      </div>
    </header>
  );
}

export default Header;
