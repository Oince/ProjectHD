import React, { useState } from 'react';
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
  Message,
} from './StyledComponents';
import { useNavigate } from 'react-router-dom';

function Write() {
  const [activeCategory, setActiveCategory] = useState('');
  const [title, setTitle] = useState('');
  const [url, setUrl] = useState('');
  const [itemName, setItemName] = useState('');
  const [price, setPrice] = useState('');
  const [deliveryPrice, setDeliveryPrice] = useState('');
  const [content, setContent] = useState('');
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const categories = [
    'FOOD',
    'SW',
    'PC',
    'ELECTRONIC',
    'CLOTHES',
    'SALES',
    'BEAUTY',
    'MOBILE',
    'PACKAGE',
    'ETC',
  ];

  const handleCategoryClick = (category) => {
    setActiveCategory(category);
  };

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    setFile(selectedFile);
  };

  const uploadFile = async () => {
    if (!file) return null;

    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await axios.post('https://oince.kro.kr/files', formData, {
        withCredentials: true,
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      if (response.status === 201) {
        console.log('File uploaded successfully:', response.headers.location);
        return response.headers.location; // 업로드된 파일의 URL 반환
      }
    } catch (error) {
      console.error('File upload error:', error);
      setMessage('이미지 업로드에 실패했습니다.');
      return null;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      let imageTag = '';
      if (file) {
        const uploadedImageUrl = await uploadFile();
        if (uploadedImageUrl) {
          imageTag = `<img src="${uploadedImageUrl}" alt="Uploaded Image" style="max-width:100%; height:auto; border-radius:8px;" />`;
        }
      }

      const postData = {
        category: activeCategory,
        title,
        url,
        itemName,
        price,
        deliveryPrice,
        content: `${content}\n${imageTag}`.trim(),
      };

      const response = await axios.post(
        'https://oince.kro.kr/boards', postData, {
        withCredentials: true,
      });

      if (response.status === 201) {
        setMessage('글 작성이 완료되었습니다!');
        const postUrl = response.headers.location;
        navigate(postUrl);
      }
    } catch (error) {
      console.error('Write error:', error);
      if (error.response) {
        if (error.response.status === 400) {
          setMessage('요청 데이터가 잘못되었습니다. 입력값을 확인하세요.');
        } else if (error.response.status === 401) {
          setMessage('로그인이 필요합니다.');
        } else {
          setMessage('서버 오류가 발생했습니다. 다시 시도해주세요.');
        }
      } else {
        setMessage('서버와의 연결에 실패했습니다. 다시 시도해주세요.');
      }
    }
  };

  return (
    <Container>
      <WriteContainer>
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
          {/* 이미지 파일 업로드 */}
          <Input type="file" accept="image/*" onChange={handleFileChange} />
          <SubmitButton type="submit">작성</SubmitButton>
          {message && <Message>{message}</Message>}
        </Form>
      </WriteContainer>
    </Container>
  );
}

export default Write;
