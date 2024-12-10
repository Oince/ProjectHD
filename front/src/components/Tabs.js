import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

// Styled Components
const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const CategoryMenu = styled.div`
  display: flex;
  gap: 10px;
  margin: 20px 0;
  flex-wrap: wrap;
`;

const CategoryButton = styled.button`
  background-color: ${({ active }) => (active ? '#f85f6a' : '#f0f0f0')};
  color: ${({ active }) => (active ? '#fff' : '#333')};
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 1rem;
  &:hover {
    background-color: ${({ active }) => (active ? '#e04b56' : '#ddd')};
  }
`;

const PostList = styled.div`
  width: 100%;
  max-width: 600px;
  margin-top: 20px;
`;

const PostItem = styled.div`
  background: #fff;
  padding: 15px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
`;

const PostTitle = styled.h3`
  margin: 0;
  font-size: 1.2rem;
  color: #333;
`;

const PostDetails = styled.div`
  font-size: 0.9rem;
  color: #666;
  margin-top: 5px;
`;

const Tabs = () => {
  const [activeCategory, setActiveCategory] = useState('');
  const [postsByCategory, setPostsByCategory] = useState({});
  const [posts, setPosts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await axios.get('https://oince.kro.kr/boards');
        if (response.status === 200) {
          const groupedPosts = response.data.reduce((acc, post) => {
            if (!acc[post.category]) {
              acc[post.category] = [];
            }
            acc[post.category].push(post);
            return acc;
          }, {});
          setPostsByCategory(groupedPosts);
          setPosts(groupedPosts[activeCategory] || Object.values(groupedPosts).flat());
        }
      } catch (error) {
        console.error('게시글을 불러오는데 실패했습니다:', error);
      }
    };

    fetchPosts();
  }, [activeCategory]);

  const categories = Object.keys(postsByCategory);

  const handleCategoryClick = (category) => {
    setActiveCategory(category === activeCategory ? '' : category);
    setPosts(category === activeCategory ? Object.values(postsByCategory).flat() : postsByCategory[category]);
  };

  const handlePostClick = (postId) => {
    navigate(`/boards/${postId}`);  // 상세화면으로 이동
  };

  return (
    <Container>
      <CategoryMenu>
        {categories.map((category) => (
          <CategoryButton
            key={category}
            active={category === activeCategory}
            onClick={() => handleCategoryClick(category)}
          >
            {category}
          </CategoryButton>
        ))}
      </CategoryMenu>

      <PostList>
        {posts.map((post) => (
          <PostItem key={post.boardId} onClick={() => handlePostClick(post.boardId)}>
            <PostTitle>{post.title}</PostTitle>
            <PostDetails>
              작성자: {post.name} | 댓글: {post.numberOfComment} | 가격: {post.price}원 | 배달비: {post.deliveryPrice}원
              <br />
              날짜: {new Date(post.date).toLocaleDateString()} | 좋아요: {post.thumbsup} | 조회수: {post.views}
            </PostDetails>
          </PostItem>
        ))}
      </PostList>
    </Container>
  );
};

export default Tabs;
