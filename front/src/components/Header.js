import React, { useState, useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { UserContext } from './UserContext';

function Header() {
  const [loginId, setLoginId] = useState(''); // 아이디 입력 상태
  const [password, setPassword] = useState(''); // 비밀번호 입력 상태
  const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태 관리
  const { user, setUser } = useContext(UserContext);
  const navigate = useNavigate();

  // 로그인 요청 핸들러
  const handleLogin = async () => {
    // 데이터 유효성 검사
    if (loginId.length < 5 || loginId.length > 30 || password.length < 5 || password.length > 30) {
      alert('아이디와 비밀번호는 각각 최소 5자, 최대 30자여야 합니다.');
      return;
    }

    try {
      const response = await axios.post(
        'https://oince.kro.kr/login',
        { loginId, password },
        { withCredentials: true } // 서버로부터 세션 쿠키를 받기 위해 withCredentials 설정
      );

      if (response.status === 200) {
        // 로그인 성공 시
        alert('로그인 성공');
        setIsLoggedIn(true);
        
        const memberId = response.data.memberId; // 서버로부터 받은 memberId
        
        try {
          const nicknameResponse = await axios.get(`https://oince.kro.kr/nickname?memberId=${memberId}`, {withCredentials: true});
          if (nicknameResponse.status === 200) {
            const nickname = nicknameResponse.data;
            setUser({ memberId, nickname }); // 사용자 정보를 Context에 저장
          }
        } catch (error) {
          if(error.response.status === 404){
            alert("멤버 ID가 없습니다.");
          }
          alert('닉네임을 불러오는 데 실패했습니다.');
        }

        navigate('/'); // 홈 화면으로 리다이렉트
      }
    } catch (error) {
      if (error.response && error.response.status === 400) {
        alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
      } else {
        alert('서버 오류가 발생했습니다.');
        console.error(error);
      }
    }
  };

  // 로그아웃 요청 핸들러
  const handleLogout = async () => {
    try {
      const response = await axios.post('https://oince.kro.kr/logout', {}, { withCredentials: true });
      
      if (response.status === 200) {
        // 로그아웃 성공 시
        alert('로그아웃 되었습니다.');
        setIsLoggedIn(false); // 로그인 상태 해제
        setUser({ memberId: null, nickname: '' }); // 사용자 정보 초기화
        navigate('/'); // 홈 화면으로 리다이렉트
      }
    } catch (error) {
      if(error.response.status === 400){
        alert("세션이 없습니다.")
        console.error(error);
      }
      else{
        alert('로그아웃에 실패했습니다.');
        console.error(error);
      }
    }
  };

  return (
    <header className="header">
      <div className="left">
        <Link to="/">
          <h1>핫딜사이트</h1> {/* 사이트 이름, 홈 버튼 */}
        </Link>
      </div>
      <div className="right">
        {isLoggedIn ? (
          <>
             <span>{user.nickname}님 환영합니다!</span> {/* 로그인 시 닉네임 표시 */}
            <button className="logout-button" onClick={handleLogout}>로그아웃</button> {/* 로그아웃 버튼 */}
            <Link to="/write">
              <button className="write-button">글 작성</button> {/* 글 작성 버튼 */}
            </Link>
          </>
        ) : (
          <>
            <input
              type="text"
              placeholder="아이디"
              className="input-field"
              value={loginId}
              onChange={(e) => setLoginId(e.target.value)}
            />
            <input
              type="password"
              placeholder="비밀번호"
              className="input-field"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <button className="login-button" onClick={handleLogin}>로그인</button> {/* 로그인 버튼 */}
            <Link to="/signup">
              <button className="signup-button">회원가입</button> {/* 회원가입 버튼 */}
            </Link>
          </>
        )}
      </div>
    </header>
  );
}

export default Header;
