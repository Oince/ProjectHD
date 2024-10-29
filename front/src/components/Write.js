import React, { useState } from 'react';
import axios from 'axios';
import {
  Container,
  WriteContainer,
  Title,
  CategoryContainer,
  CategoryButton,
  Form,
  Input,
  TextArea,
  SubmitButton,
  Message
} from './StyledComponents';

function Write() {
  const [activeCategory, setActiveCategory] = useState('');
  const [title, setTitle] = useState('');
  const [url, setUrl] = useState('');
  const [itemName, setItemName] = useState('');
  const [price, setPrice] = useState('');
  const [deliveryPrice, setDeliveryPrice] = useState('');
  const [content, setContent] = useState('');
  const [message, setMessage] = useState('');

  const categories = [
    "FOOD", "SW", "PC", "ELECTRONIC", "CLOTHES", 
    "SALES", "BEAUTY", "MOBILE", "PACKAGE", "ETC"
  ];

  const handleCategoryClick = (category) => {
    setActiveCategory(category);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // 서버로 전송할 데이터
    const postData = {
      category: activeCategory,
      title,
      url,
      itemName,
      price,
      deliveryPrice,
      content
    };

    try {
      // POST 요청을 보내기
      const response = await axios.post('https://oince.kro.kr/boards', postData);
      if (response.status === 201) {
        setMessage("글 작성이 완료되었습니다!");
        // 필요한 경우 입력 폼 초기화
        setActiveCategory('');
        setTitle('');
        setUrl('');
        setItemName('');
        setPrice('');
        setDeliveryPrice('');
        setContent('');
        navigator('/');
      } else {
        setMessage("글 작성에 실패했습니다. 다시 시도해주세요.");
      }
    } catch (error) {
      console.error(error);
      setMessage("서버와의 연결에 실패했습니다. 다시 시도해주세요.");
    }
  };

  return (
    <Container>
      <WriteContainer>
        <Title>글 작성</Title>
        <CategoryContainer>
          {categories.map((category) => (
            <CategoryButton
              key={category}
              active={activeCategory === category}
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
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
          <Input
            type="text"
            placeholder="URL을 입력해주세요."
            value={url}
            onChange={(e) => setUrl(e.target.value)}
          />
          <Input
            type="text"
            placeholder="상품명을 입력하세요"
            value={itemName}
            onChange={(e) => setItemName(e.target.value)}
          />
          <Input
            type="number"
            placeholder="가격을 입력하세요"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
          />
          <Input
            type="number"
            placeholder="배송비를 입력하세요"
            value={deliveryPrice}
            onChange={(e) => setDeliveryPrice(e.target.value)}
          />
          <TextArea
            placeholder="내용을 입력하세요"
            value={content}
            onChange={(e) => setContent(e.target.value)}
          ></TextArea>
          <SubmitButton type="submit">작성</SubmitButton>
          {message && <Message>{message}</Message>}
        </Form>
      </WriteContainer>
    </Container>
  );
}

export default Write;