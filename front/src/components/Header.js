import React from 'react';
import { Link } from 'react-router-dom';  // React Router의 Link 컴포넌트 사용

function Header() {
  return (
    <header className="header">
      <div className="left">
        <h1>핫딜사이트</h1> {/* 사이트 이름, 홈 버튼 */}
      </div>
      <div className="right">
        <input type="text" placeholder="아이디" className="input-field" />
        <input type="password" placeholder="비밀번호" className="input-field" />
        <button className="login-button">로그인</button>
        <Link to="/signup">
          <button className="signup-button">회원가입</button> {/* 회원가입 버튼 */}
        </Link>
      </div>
    </header>
  );
}

export default Header;
