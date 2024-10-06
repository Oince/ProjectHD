import React from 'react';

function Header() {
  return (
    <header className="header">
      <div className="left">
        <h1>핫딜사이트</h1> {/* 사이트 이름, 홈 버튼 */}
      </div>
      <div className="right"> {/* 로그인 및 회원가입을 오른쪽으로 정렬 */}
        <input type="text" placeholder="아이디" className="input-field" />
        <input type="password" placeholder="비밀번호" className="input-field" />
        <button className="login-button">로그인</button>
        <button className="signup-button">회원가입</button>
      </div>
    </header>
  );
}

export default Header;
