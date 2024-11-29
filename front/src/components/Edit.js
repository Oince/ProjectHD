import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import {
  Container,
  WriteContainer,
  CategoryContainer,
  CategoryButton,
  Form,
  Input,
  TextArea,
  SubmitButton,
  Message
} from './StyledComponents';

function Edit() {
  const { state } = useLocation();
  const navigate = useNavigate();
  const [post, setPost] = useState(state?.post || {
    category: '',
    title: '',
    url: '',
    itemName: '',
    price: '',
    deliveryPrice: '',
    content: '',
  });
  const [message, setMessage] = useState('');

  const categories = [
    "FOOD", "SW", "PC", "ELECTRONIC", "CLOTHES",
    "SALES", "BEAUTY", "MOBILE", "PACKAGE", "ETC"
  ];

  const handleCategoryClick = (category) => {
    setPost({ ...post, category });
  };

  console.log(post.boardId);

  //게시글 수정
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.put(`https://oince.kro.kr/boards/${post.boardId}`, post, { withCredentials: true });
      if (response.status === 201) {
        setMessage("게시글이 수정되었습니다!");
        navigate(`/boards/${post.boardId}`);
      }
    } catch (error) {
      setMessage("수정 실패. 다시 시도해주세요.");
      console.error(error);
    }
  };

  return (
    <Container>
      <WriteContainer>
        <CategoryContainer>
          {categories.map((category) => (
            <CategoryButton
              key={category}
              active={post.category === category}
              onClick={() => handleCategoryClick(category)}
            >
              {category}
            </CategoryButton>
          ))}
        </CategoryContainer>
        <Form onSubmit={handleSubmit}>
          <Input
            type="text"
            placeholder="제목을 입력하세요"
            value={post.title}
            onChange={(e) => setPost({ ...post, title: e.target.value })}
          />
          <Input
            type="text"
            placeholder="URL을 입력해주세요."
            value={post.url}
            onChange={(e) => setPost({ ...post, url: e.target.value })}
          />
          <Input
            type="text"
            placeholder="상품명을 입력하세요"
            value={post.itemName}
            onChange={(e) => setPost({ ...post, itemName: e.target.value })}
          />
          <Input
            type="number"
            placeholder="가격을 입력하세요"
            value={post.price}
            onChange={(e) => setPost({ ...post, price: e.target.value })}
          />
          <Input
            type="number"
            placeholder="배송비를 입력하세요"
            value={post.deliveryPrice}
            onChange={(e) => setPost({ ...post, deliveryPrice: e.target.value })}
          />
          <TextArea
            placeholder="내용을 입력하세요"
            value={post.content}
            onChange={(e) => setPost({ ...post, content: e.target.value })}
          ></TextArea>
          <SubmitButton type="submit">수정 완료</SubmitButton>
          {message && <Message>{message}</Message>}
        </Form>
      </WriteContainer>
    </Container>
  );
}

export default Edit;
