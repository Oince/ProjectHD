import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

function Edit() {
  const { state } = useLocation();
  const navigate = useNavigate();
  const [post, setPost] = useState(state?.post || {
    title: '',
    itemName: '',
    price: '',
    deliveryPrice: '',
    category: '',
    content: ''
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.put(`https://oince.kro.kr/boards/${state?.post.boardId}`, post, { withCredentials: true });
      if (response.status === 201) {
        alert("게시글이 수정되었습니다.");
        navigate(`/boards/${state?.post.boardId}`);
      }
    } catch (error) {
      alert("수정 실패. 다시 시도해주세요.");
    }
  };

  return (
    <div>
      <h2>게시글 수정</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>제목</label>
          <input
            type="text"
            value={post.title}
            onChange={(e) => setPost({ ...post, title: e.target.value })}
          />
        </div>
        <div>
          <label>상품명</label>
          <input
            type="text"
            value={post.itemName}
            onChange={(e) => setPost({ ...post, itemName: e.target.value })}
          />
        </div>
        <div>
          <label>가격</label>
          <input
            type="number"
            value={post.price}
            onChange={(e) => setPost({ ...post, price: e.target.value })}
          />
        </div>
        <div>
          <label>배송비</label>
          <input
            type="number"
            value={post.deliveryPrice}
            onChange={(e) => setPost({ ...post, deliveryPrice: e.target.value })}
          />
        </div>
        <div>
          <label>카테고리</label>
          <input
            type="text"
            value={post.category}
            onChange={(e) => setPost({ ...post, category: e.target.value })}
          />
        </div>
        <div>
          <label>내용</label>
          <textarea
            value={post.content}
            onChange={(e) => setPost({ ...post, content: e.target.value })}
          />
        </div>
        <button type="submit">수정 완료</button>
      </form>
    </div>
  );
}

export default Edit;
