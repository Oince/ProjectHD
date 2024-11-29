import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { UserContext } from './UserContext';
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
  CommentButton,
  ThumbsupButton
} from './StyledComponents';

function PostDetail() {
  const { boardId } = useParams();
  const navigate = useNavigate();
  const { user } = useContext(UserContext); // 로그인한 사용자 정보 가져오기
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState("");
  const [error, setError] = useState(null);

  //게시글 입력 상태, 댓글 상태 리턴
  useEffect(() => {
    const fetchPostAndComments = async () => {
      try {
        //게시글 상태 리턴
        const postResponse = await axios.get(`https://oince.kro.kr/boards/${boardId}`);
        
        if (postResponse.status === 200) {
          setPost(postResponse.data);
        }
        
        //댓글들 리턴
        const commentsResponse = await axios.get(`https://oince.kro.kr/comments?boardId=${boardId}`, {
          withCredentials: true,
        });
        //각 댓글 들에서 닉네임 가져오기
        if (commentsResponse.status === 200) {
          const commentsWithNicknames = await Promise.all(
            commentsResponse.data.map(async (comment) => {
              try {
                const nicknameResponse = await axios.get(
                  `https://oince.kro.kr/nickname?memberId=${comment.memberId}`,
                  { withCredentials: true }
                );
                return {
                  ...comment,
                  nickname: nicknameResponse.data || '알 수 없음', // 닉네임 설정
                };
              } catch (error) {
                console.error(`Failed to fetch nickname for memberId ${comment.memberId}:`, error);
                return {
                  ...comment,
                  nickname: '알 수 없음', // 실패 시 기본 닉네임 설정
                };
              }
            })
          );
          setComments(commentsWithNicknames);
        }
        
      } catch (err) {
        if (err.response && err.response.status === 404) {
          setError("게시글을 찾을 수 없습니다.");
        } else {
          setError("서버 오류가 발생했습니다.");
        }
      }
    };

    fetchPostAndComments();
  }, [boardId]);

  //게시글 삭제
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

  //게시글 수정
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
        boardId: boardId, // 현재 게시글의 boardId 추가
      };
  
      // PUT 요청을 호출하지 않고 데이터만 전달
      navigate('/edit', { state: { post: updatedData } });
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

  const handleCommentSubmit = async (e) => {
    e.preventDefault();
  
    if (!newComment.trim()) {
      alert("댓글 내용을 입력해주세요.");
      return;
    }
  
    try {
      // 댓글 등록 요청
      const response = await axios.post(
        'https://oince.kro.kr/comments',
        {
          boardId: boardId,
          parentComment: null, // 기본 댓글
          content: newComment,
        },
        { withCredentials: true }
      );
  
      if (response.status === 201) {
        // 성공적으로 등록되었을 경우
        alert("댓글 입력에 성공했습니다.");
        const commentUrl = response.headers.location; // Location 헤더에서 URL 가져오기
        
        console.log(commentUrl);
        // 새로 등록된 댓글 가져오기
        const commentResponse = await axios.get(`https://oince.kro.kr${commentUrl}`, { withCredentials: true });
  
        if (commentResponse.status === 200) {
          setComments([...comments, commentResponse.data]); // 댓글 상태에 추가
          setNewComment(""); // 입력창 초기화
        }
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
      } else {
        alert("댓글 등록에 실패했습니다.");
      }
    }
  };
  

  //댓글 삭제
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

  //좋아요 올라가기
  const handleThumbsup = async () => {
    try {
      const response = await axios.post(`https://oince.kro.kr/boards/${boardId}/thumbsup`, null, {
        withCredentials: true,
      });
      if (response.status === 200) {
        setPost((prev) => ({ ...prev, thumbsup: prev.thumbsup + 1 })); // 따봉 값 증가
      }
    } catch (error) {
      if (error.response) {
        switch (error.response.status) {
          case 400:
            alert('요청 데이터가 잘못되었습니다.');
            break;
          case 401:
            alert('로그인이 필요합니다.');
            break;
          default:
            alert('서버 오류가 발생했습니다.');
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
      {user.memberId === post.memberId && (
        <ButtonContainer>
        <ActionButton onClick={handleEdit}>수정</ActionButton>
        <ActionButton onClick={handleDelete}>삭제</ActionButton>
      </ButtonContainer>
      )}
      <PostTitle>{post.title}</PostTitle>
      <Info><strong>상품명:</strong> {post.itemName}</Info>
      <Info><strong>가격:</strong> {post.price}원</Info>
      <Info><strong>배송비:</strong> {post.deliveryPrice}원</Info>
      <Info><strong>카테고리:</strong> {post.category}</Info>
      <Info><strong>작성일:</strong> {post.date}</Info>
      <Info><strong>조회수:</strong> {post.views}</Info>
      <Info><strong>URL:</strong> <UrlLink href={post.url} target="_blank" rel="noopener noreferrer">{post.url}</UrlLink></Info>
      <Content>
        <strong>내용:</strong>
        <p>{post.content}</p>
      </Content>

      {/* Thumbsup Button */}
      <div style={{ textAlign: 'center', margin: '16px 0', display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '8px' }}>
        <ThumbsupButton onClick={handleThumbsup}>👍 따봉</ThumbsupButton>
        <span style={{ fontSize: '16px', fontWeight: 'bold' }}>{post.thumbsup}</span>
      </div>

      <CommentContainer>
      <h3>댓글</h3>
      {comments.map((comment) => {
        // UTC 시간을 한국 표준시로 변환
        const convertToKST = (utcDate) => {
          const date = new Date(utcDate); // UTC 시간 기준으로 Date 객체 생성
          date.setHours(date.getHours() + 9); // UTC에 9시간 더해서 KST로 변환
          return date.toLocaleString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
          });
        };

        return (
          <div
            key={comment.commentId}
            style={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'space-between',
              padding: '8px 16px',
              borderBottom: '1px solid #ddd',
              marginBottom: '4px',
              borderRadius: '4px',
              backgroundColor: '#f9f9f9',
            }}
          >
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
              <p style={{ margin: 0, fontWeight: 'bold' }}>{comment.nickname}</p>
              <small style={{ margin: 0 }}>{comment.content}</small>
            </div>
            <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
              <small style={{ color: '#888' }}>{convertToKST(comment.date)}</small>
              {user.memberId === comment.memberId && (
                <ActionButton
                style={{
                  backgroundColor: '#ff4d4f',
                  color: 'white',
                  border: 'none',
                  padding: '4px 8px',
                  borderRadius: '4px',
                  cursor: 'pointer',
                }}
                onClick={() => handleCommentDelete(comment.commentId)}
              >
                삭제
              </ActionButton>
              )}
            </div>
          </div>
        );
      })}



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