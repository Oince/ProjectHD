import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import {
  PostContainer,
  PostTitle,
  Info,
  Content,
  UrlLink,
  BackButton,
  ButtonContainer,
  ActionButton
} from './StyledComponents';

function PostDetail() {
  const { boardId } = useParams();
  const navigate = useNavigate();
  
  const [post, setPost] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchPostDetails = async () => {
      try {
        const response = await axios.get(`https://oince.kro.kr/boards/${boardId}`);
        
        if (response.status === 200) {
          setPost(response.data);
        }
      } catch (err) {
        if (err.response && err.response.status === 404) {
          setError("게시글을 찾을 수 없습니다.");
        } else {
          setError("서버 오류가 발생했습니다.");
        }
      }
    };

    fetchPostDetails();
  }, [boardId]);

  const handleDelete = async () => {
    try {
      const response = await axios.delete(`https://oince.kro.kr/boards/${boardId}`, { withCredentials: true });

      if (response.status === 200) {
        alert("게시글이 삭제되었습니다.");
        navigate("/"); // 삭제 성공 후 홈 화면으로 이동
      }
    } catch (error) {
      if (error.response) {
        switch (error.response.status) {
          case 400:
            alert("요청 데이터가 잘못되었습니다.");
            break;
          case 401:
            alert("로그인이 필요합니다.");
            break;
          case 403:
            alert("삭제 권한이 없습니다.");
            break;
          default:
            alert("서버 오류가 발생했습니다.");
        }
      }
    }
  };

  const handleEdit = async () => {
    try {
      const updatedData = {
        title: post.title,
        url: post.url,
        itemName: post.itemName,
        price: post.price,
        deliveryPrice: post.deliveryPrice,
        category: post.category,
        content: post.content,
      };

      const response = await axios.put(
        `https://oince.kro.kr/boards/${boardId}`,
        updatedData,
        { withCredentials: true }
      );

      if (response.status === 201) {
        navigate('/edit', { state: { post } });
      }
    } catch (error) {
      if (error.response) {
        switch (error.response.status) {
          case 400:
            alert("요청 데이터가 잘못되었습니다. 입력값을 확인하세요.");
            break;
          case 401:
            alert("로그인이 필요합니다.");
            break;
          case 403:
            alert("수정 권한이 없습니다.");
            break;
          default:
            alert("서버 오류가 발생했습니다.");
        }
      }
    }
  };

  if (error) {
    return <PostContainer>{error}</PostContainer>;
  }

  if (!post) {
    return <PostContainer>로딩 중...</PostContainer>;
  }

  return (
    <PostContainer>
      <PostTitle>{post.title}</PostTitle>
      <Info><strong>상품명:</strong> {post.itemName}</Info>
      <Info><strong>가격:</strong> {post.price}원</Info>
      <Info><strong>배송비:</strong> {post.deliveryPrice}원</Info>
      <Info><strong>카테고리:</strong> {post.category}</Info>
      <Info><strong>작성일:</strong> {post.date}</Info>
      <Info><strong>좋아요:</strong> {post.thumbsup}</Info>
      <Info><strong>조회수:</strong> {post.views}</Info>
      <Info><strong>URL:</strong> <UrlLink href={post.url} target="_blank" rel="noopener noreferrer">{post.url}</UrlLink></Info>
      <Content>
        <strong>내용:</strong>
        <p>{post.content}</p>
      </Content>

      <ButtonContainer>
        <ActionButton onClick={handleEdit}>수정</ActionButton>
        <ActionButton onClick={handleDelete}>삭제</ActionButton>
      </ButtonContainer>

      <BackButton onClick={() => navigate(-1)}>뒤로 가기</BackButton>
    </PostContainer>
  );
}

export default PostDetail;
