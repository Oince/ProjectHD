// TabsWithPostList.js
import React, { useState, useEffect } from 'react';
import styled from 'styled-components';

// Sample data (Replace this with your API call in useEffect)
const samplePosts = {
  먹거리: [{ id: 1, title: '맛있는 피자' }, { id: 2, title: '버거 스페셜' }],
  소프트웨어: [{ id: 3, title: '새 소프트웨어 출시' }],
  PC제품: [{ id: 4, title: '게이밍 PC 할인' }],
  가전제품: [{ id: 5, title: '스마트폰 할인' }],
  의류: [{ id: 6, title: '겨울 자켓' }],
  세일정보: [{ id: 7, title: '블랙 프라이데이 딜' }],
  화장품: [{ id: 8, title: '스킨 케어 필수품' }],
  모바일: [{ id: 9, title: '최신 모바일 액세서리' }],
  상품권: [{ id: 10, title: '휴가 패키지' }],
  기타: [{ id: 11, title: '기타 할인 상품' }]
};

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
`;

const PostTitle = styled.h3`
  margin: 0;
  font-size: 1.2rem;
  color: #333;
`;

const Tabs = () => {
  const [activeCategory, setActiveCategory] = useState('');
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    // Fetch posts based on activeCategory
    // Replace this sample data logic with API call if needed
    if (activeCategory === '') {
      // Show all posts if no category is selected
      const allPosts = Object.values(samplePosts).flat();
      setPosts(allPosts);
    } else {
      setPosts(samplePosts[activeCategory] || []);
    }
  }, [activeCategory]);

  const categories = Object.keys(samplePosts);

  return (
    <Container>
      <CategoryMenu>
        {categories.map((category) => (
          <CategoryButton
            key={category}
            active={category === activeCategory}
            onClick={() => setActiveCategory(category === activeCategory ? '' : category)}
          >
            {category}
          </CategoryButton>
        ))}
      </CategoryMenu>

      <PostList>
        {posts.map((post) => (
          <PostItem key={post.id}>
            <PostTitle>{post.title}</PostTitle>
          </PostItem>
        ))}
      </PostList>
    </Container>
  );
};

export default Tabs;
