import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import {
  PostContainer,
  PostTitle,
  Info,
  Content,
  UrlLink,
  ButtonContainer,
  ActionButton,
  CommentContainer,
  CommentInput,
  CommentButton
} from './StyledComponents';

function PostDetail() {
  const { boardId } = useParams();
  const navigate = useNavigate();
  
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState("");
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

  // Fetch comments

  useEffect(() => {
    const fetchComments = async () => {
      try {
        const response = await axios.get(`https://oince.kro.kr/comments?boardId=${boardId}`, { withCredentials: true });
        if (response.status === 200) {
          setComments(response.data);
        }
      } catch (err) {
        if (err.response) {
          // 서버 응답이 있을 경우 상태 코드에 따라 에러 메시지 출력
          switch (err.response.status) {
            case 400:
              alert("요청 데이터가 잘못되었습니다. 댓글을 불러오는 데 실패했습니다.");
              break;
            case 404:
              alert("해당 게시글의 댓글을 찾을 수 없습니다.");
              break;
            default:
              alert("서버 오류가 발생했습니다.");
          }
        } else {
          // 서버 응답이 없는 경우
          alert("네트워크 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
        console.error(err); // 상세 오류 내용 콘솔 출력
      }
    };
  
    fetchComments();
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

  // Handle comment submission
  const handleCommentSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        'https://oince.kro.kr/comments',
        {
          boardId: boardId,
          parentComment: null, // 기본 댓글로 설정, 대댓글 기능 추가 시 수정
          content: newComment,
        },
        { withCredentials: true }
      );

      if (response.status === 200) {
        alert("댓글 입력에 성공했습니다.");
        setComments([...comments, response.data]); // 새 댓글 추가
        setNewComment(""); // 입력창 초기화
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
          default:
            alert("서버 오류가 발생했습니다.");
        }
      }
    }
  };

    // Handle comment deletion
  const handleCommentDelete = async (commentId) => {
    try {
      const response = await axios.delete(`https://oince.kro.kr/comments/${commentId}`, { withCredentials: true });
      if (response.status === 200) {
        setComments(comments.filter(comment => comment.commentId !== commentId));
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
            alert("삭제 권한이 없습니다.");
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
      <ButtonContainer>
        <ActionButton onClick={handleEdit}>수정</ActionButton>
        <ActionButton onClick={handleDelete}>삭제</ActionButton>
      </ButtonContainer>
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

      <CommentContainer>
        <h3>댓글</h3>
        {comments.map((comment) => (
          <div key={comment.commentId} style={{ marginBottom: '8px' }}>
            <p>{comment.content}</p>
            <small>{comment.date}</small>
            <ActionButton onClick={() => handleCommentDelete(comment.commentId)}>삭제</ActionButton>
          </div>
        ))}

        <form onSubmit={handleCommentSubmit} style={{ display: 'flex', flexDirection: 'column', marginTop: '16px' }}>
          <CommentInput
            type="text"
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            placeholder="댓글을 입력하세요"
          />
          <CommentButton type="submit">댓글 등록</CommentButton>
        </form>
      </CommentContainer>
    </PostContainer>
  );
}

export default PostDetail;