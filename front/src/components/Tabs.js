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

const Pagination = styled.div`
  display: flex;
  gap: 10px;
  margin-top: 20px;
`;

const PageButton = styled.button`
  background-color: ${({ active }) => (active ? '#f85f6a' : '#f0f0f0')};
  color: ${({ active }) => (active ? '#fff' : '#333')};
  border: none;
  border-radius: 20px;
  padding: 10px;
  cursor: pointer;
  font-size: 1rem;
  &:hover {
    background-color: ${({ active }) => (active ? '#e04b56' : '#ddd')};
  }
`;

const Tabs = () => {
  const [activeCategory, setActiveCategory] = useState('');
  const [posts, setPosts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await axios.get('https://oince.kro.kr/boards');
        if (response.status === 200) {
          const uniqueCategories = [...new Set(response.data.map(post => post.category))];
          setCategories(uniqueCategories);
        }
      } catch (error) {
        console.error('카테고리 불러오기 실패:', error);
      }
    };
    fetchCategories();
  }, []);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await axios.get(
          `https://oince.kro.kr/boards?page=${currentPage}&category=${activeCategory}`
        );
        if (response.status === 200) {
          const postsData = response.data;
          setPosts(postsData);

          // 페이지 수 계산: 예시로 100개 게시글을 가정하여 계산
          const totalPosts = 100;
          const pages = Math.ceil(totalPosts / 20);
          setTotalPages(pages);
        }
      } catch (error) {
        console.error('게시글 불러오기 실패:', error);
      }
    };

    if (activeCategory) {
      fetchPosts();
    }
  }, [currentPage, activeCategory]);

  const handleCategoryClick = (category) => {
    setActiveCategory(category === activeCategory ? '' : category);
    setCurrentPage(1); // 카테고리 변경 시 첫 번째 페이지부터 불러오기
  };

  const handlePostClick = (postId) => {
    navigate(`/boards/${postId}`);
  };

  const handlePageChange = (page) => {
    setCurrentPage(page);
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

      <Pagination>
        {[...Array(totalPages)].map((_, index) => (
          <PageButton
            key={index + 1}
            active={index + 1 === currentPage}
            onClick={() => handlePageChange(index + 1)}
          >
            {index + 1}
          </PageButton>
        ))}
      </Pagination>
    </Container>
  );
};

export default Tabs;
