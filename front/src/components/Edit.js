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
  Message,
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
  const [file, setFile] = useState(null);
  const [message, setMessage] = useState('');

  const categories = [
    'FOOD', 'SW', 'PC', 'ELECTRONIC', 'CLOTHES',
    'SALES', 'BEAUTY', 'MOBILE', 'PACKAGE', 'ETC',
  ];

  // 카테고리 클릭 이벤트
  const handleCategoryClick = (category) => {
    setPost({ ...post, category });
  };

  // 이미지 파일 선택 이벤트 핸들러
  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    setFile(selectedFile);
  };

  // 이미지 업로드
  const uploadFile = async () => {
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

  // 게시글 수정 요청
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // 이미지 파일 업로드 후 URL 생성
      let imageTag = '';
      if (file) {
        const uploadedImageUrl = await uploadFile();
        if (uploadedImageUrl) {
          imageTag = `<img src="${uploadedImageUrl}" alt="Uploaded Image" style="max-width:100%; height:auto; border-radius:8px;" />`;
        }
      }

      // 이미지 태그를 본문에 추가
      const updatedPost = {
        ...post,
        content: `${post.content}\n${imageTag}`.trim(),
      };

      // 게시글 데이터 수정
      const response = await axios.put(
        `https://oince.kro.kr/boards/${post.boardId}`,
        updatedPost,
        { withCredentials: true }
      );

      if (response.status === 201) {
        setMessage('게시글이 수정되었습니다!');
        navigate(`/boards/${post.boardId}`);
      }
    } catch (error) {
      if(error.response.status === 400){
        setMessage('없는 게시글 이거나 빈 파일 입니다.');
      }
      else if(error.response.status === 401){
        setMessage('로그인 해주시기 바랍니다.');
      }
      else if(error.response.status === 403){
        setMessage('수정권한이 없습니다.');
      }
      else{
        setMessage('수정 실패. 다시 시도해주세요.');
      }
      console.error('Edit error:', error);
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
          {/* 이미지 파일 업로드 */}
          <Input type="file" accept="image/*" onChange={handleFileChange} />
          <SubmitButton type="submit">수정 완료</SubmitButton>
          {message && <Message>{message}</Message>}
        </Form>
      </WriteContainer>
    </Container>
  );
}

export default Edit;
