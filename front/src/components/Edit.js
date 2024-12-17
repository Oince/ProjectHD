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
  const [file, setFile] = useState(null); // 이미지 파일 상태
  const [imagePreview, setImagePreview] = useState(null); // 이미지 미리 보기 상태
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

    // 이미지 미리 보기 URL 생성
    if (selectedFile) {
      const reader = new FileReader();
      reader.onloadend = () => setImagePreview(reader.result);
      reader.readAsDataURL(selectedFile);
    }
  };

  // 이미지 업로드
  const uploadFile = async (boardId) => {
    const formData = new FormData();
    formData.append('boardId', boardId);
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
        return true;
      }
    } catch (error) {
      console.error('File upload error:', error);
      setMessage('파일 업로드에 실패했습니다.');
      return false;
    }
  };

  // 게시글 수정 요청
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // 게시글 데이터 수정
      const response = await axios.put(
        `https://oince.kro.kr/boards/${post.boardId}`,
        post,
        { withCredentials: true }
      );

      if (response.status === 201) {
        setMessage('게시글이 수정되었습니다!');

        // 이미지 파일이 있을 경우 업로드
        if (file) {
          const success = await uploadFile(post.boardId);
          if (!success) return; // 파일 업로드 실패 시 중단
        }

        // 수정 완료 후 상세 페이지로 이동
        navigate(`/boards/${post.boardId}`);
      }
    } catch (error) {
      console.error('Edit error:', error);
      setMessage('수정 실패. 다시 시도해주세요.');
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
          {/* 이미지 미리 보기 */}
          {imagePreview && (
            <div style={{ margin: '10px 0' }}>
              <p>이미지 미리 보기:</p>
              <img
                src={imagePreview}
                alt="미리 보기"
                style={{ maxWidth: '100%', height: 'auto', borderRadius: '8px' }}
              />
            </div>
          )}
          <SubmitButton type="submit">수정 완료</SubmitButton>
          {message && <Message>{message}</Message>}
        </Form>
      </WriteContainer>
    </Container>
  );
}

export default Edit;
