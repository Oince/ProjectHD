import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Write() {
  const [title, setTitle] = useState('');
  const [url, setUrl] = useState('');
  const [itemName, setItemName] = useState('');
  const [price, setPrice] = useState('');
  const [deliveryPrice, setDeliveryPrice] = useState('');
  const [category, setCategory] = useState('');
  const [content, setContent] = useState('');
  const navigate = useNavigate();

  // 글 작성 요청 핸들러
  const handleSubmit = async (e) => {
    e.preventDefault();

    // 데이터 유효성 검증
    if (title.length > 100 || url.length > 200 || itemName.length > 100 || content.length > 1000) {
      alert('입력된 데이터의 길이가 너무 깁니다. 다시 확인해 주세요.');
      return;
    }

    try {
      // 서버에 글 작성 요청 보내기
      const response = await axios.post('http://172.20.10.3:8080/write', {
        title,
        url,
        itemName,
        price: parseInt(price), // 가격은 숫자로 변환
        deliveryPrice: parseInt(deliveryPrice), // 배송비는 숫자로 변환
        category,
        content
      });

      if (response.status === 201) {
        // 글 작성 성공 시 서버에서 받은 Location URL로 이동
        alert('글 작성이 완료되었습니다.');
        const postUrl = response.headers.location;
        navigate(postUrl); // 생성된 게시글의 URL로 리다이렉트
      }
    } catch (error) {
      if (error.response.status === 400) {
        alert('잘못된 요청입니다. 입력 데이터를 확인해주세요.');
      } else if (error.response.status === 401) {
        alert('로그인이 필요합니다.');
        navigate('/login'); // 로그인 페이지로 이동
      } else {
        alert('서버 오류가 발생했습니다.');
        console.error(error);
      }
    }
  };

  return (
    <div className="write-container">
      <h2>글 작성</h2>
      <form onSubmit={handleSubmit}>
        {/* 제목 입력 */}
        <div>
          <label htmlFor="title">제목</label>
          <input
            type="text"
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            maxLength="100"
            required
          />
        </div>

        {/* URL 입력 */}
        <div>
          <label htmlFor="url">URL</label>
          <input
            type="text"
            id="url"
            value={url}
            onChange={(e) => setUrl(e.target.value)}
            maxLength="200"
            required
          />
        </div>

        {/* 상품 이름 입력 */}
        <div>
          <label htmlFor="itemName">상품 이름</label>
          <input
            type="text"
            id="itemName"
            value={itemName}
            onChange={(e) => setItemName(e.target.value)}
            maxLength="100"
            required
          />
        </div>

        {/* 가격 입력 */}
        <div>
          <label htmlFor="price">가격</label>
          <input
            type="number"
            id="price"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            required
          />
        </div>

        {/* 배송비 입력 */}
        <div>
          <label htmlFor="deliveryPrice">배송비</label>
          <input
            type="number"
            id="deliveryPrice"
            value={deliveryPrice}
            onChange={(e) => setDeliveryPrice(e.target.value)}
            required
          />
        </div>

        {/* 카테고리 선택 */}
        <div>
          <label htmlFor="category">카테고리</label>
          <select
            id="category"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            required
          >
            <option value="">카테고리 선택</option>
            <option value="먹거리">먹거리</option>
            <option value="SW/게임">SW/게임</option>
            <option value="PC제품">PC제품</option>
            <option value="가전제품">가전제품</option>
            <option value="생활용품">생활용품</option>
            <option value="의류">의류</option>
            <option value="세일정보">세일정보</option>
            <option value="화장품">화장품</option>
            <option value="모바일/상품권">모바일/상품권</option>
            <option value="패키지/이용권">패키지/이용권</option>
            <option value="기타">기타</option>
          </select>
        </div>

        {/* 내용 입력 */}
        <div>
          <label htmlFor="content">내용</label>
          <textarea
            id="content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            maxLength="1000"
            required
          ></textarea>
        </div>

        {/* 제출 버튼 */}
        <button type="submit">글 작성</button>
      </form>
    </div>
  );
}

export default Write;
