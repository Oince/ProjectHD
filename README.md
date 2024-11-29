# 명세서

잘 이해가 안되거나 이상한거 있으면 말해주세요~



## 문서 수정사항

- 24/11/15
  - GET /comments -> GET /comment?boardId= 로 변경
  - GET /comments/{commentId} 추가
  - POST /comments 응답 데이터에 Location 추가하고 응답 코드 201로 변경
  - GET /nickname/{memberId} -> GET /nickname?memberId=?로 변경
  - 로그인시 memberId 값을 전달하도록 변경

- 24/11/08
  - POST /thumbsup -> POST /boards/{boardId}/thumbsup 으로 이름 변경

- 24/10/31
  - GET /comments 에서 응답 데이터중 wirter 삭제
  - POST /comments 성공시 응답코드 201->200으로 변경

- 24/10/17
  - GET /nickname/{memberId} 추가
  - GET /boards, GET /board{boardId} 에서 writer 항목 삭제

- 24/10/11
  - 요청 데이터 제약사항 추가
  - api 응답 코드 추가
  - /login에서 memberId 쿠키 안보내도록 수정
  - /board -> /boards, /comment -> /comments 로 수정
    - 그냥 복수로 바꿨다는 뜻

- 24/10/07
  - 화면 구성 추가
    - /edit/{boardId}, /signup

- 24/10/05
  - POST /logout api 추가
  - /signup, /login 요청 데이터 제약사항 추가

- 24/10/01 최초 작성



## TODO

- 따봉 기능
- 조회수 2번 찍히는거 수정
- 게시글 작성자 표시
- 수정/삭제 버튼 권한이 있는 사용자에게만 보이게
- 댓글 입력시 새로고침 없이 바로 나오게
- 이미지 처리 API

## 기능

- 로그인/회원가입
- 게시글 crud
- 댓글/대댓글 달기
- 따봉 누르기



시간 남으면 할거 

- 검색
- 카테고리

 

## 화면 구성

- 메인페이지
  - /
  
- 게시글 하나의 페이지
  - /{boardId}

- 게시글 수정 페이지
  - /edit/{boardId}

- 회원가입 페이지
  - /signup


## API

### POST /signup

회원 가입

- req
  - loginId: string
    - 로그인에 사용할 id
    - 최소 5글자, 최대 30글자
  - password: string
    - 비밀번호
    - 최소 5글자, 최대 30글자
  - name: string
    - 닉네임
    - 최소 2글자, 최대 20글자
- res
  - 성공시 200
  - 실패시 400 (loginId가 겹치는 경우)


### POST /login

로그인

- req
  - loginId: string
    - 최소 5글자, 최대 30글자
  - password: string
    - 최소 5글자, 최대 30글자
- res
  - memberId: 로그인한 사람의 memberId값
  - 세션 쿠키
    - 이름: JSESSIONID
    - 서버에서 이걸로 사용자 식별
    - **이후 모든 요청에 포함, 명세서에 없어도 항상 있어야함**
  - 성공시 200, 실패시 400
  

### POST /logout

로그아웃

- req: 없음
- res
  - 성공시 200 리턴
  - 세션이 없으면 400 리턴

### GET /nickname?memberId=

memberId에 해당하는 닉네임 돌려주는 api

- req: 없음
- res
  - nickname: 닉네임
  - 성공시 200
  - memberId가 없는 경우에 404


### GET /boards

홈화면에 표시할 게시글 정보

- req: 없음
- res
  - boardId: int
    - 게시글 id
  - memberId: int
    - 게시글 작성자 식별 id
  - title: string
    - 제목
  - numberOfComment: int
    - 댓글 개수
  - price: int
    - 가격
  - deliveryPrice: int
    - 배달비
  - category: string
    - 카테고리
  - date: string
    - 게시글 생성 날짜
  - thumbsup: int
    - 따봉 개수
  - 리스트 형태
  - 200 코드 리턴

### GET /boards/{boardId}

게시글 하나의 정보

- req: 없음
- res
  - boardId: int
  - memberId: int
  - title: string
  - numberOfComment: int
  - price: int
  - deliveryPrice: int
  - category: string
  - date: string
  - thumbsup: int
  - itemName: string
    - 상품명
  - views: int
    - 조회수
  - url: string
    - 실제 구매할 수 있는 url
  - content: string
    - 본문 내용
  - 성공시 200
  - 실패시 404(게시글이 없는 경우)

### POST /boards

게시글 등록

- req

  - title: string
    - 최대 100글자
  - url: string
    - 최대 200글자
  - itemName: string
    - 최대 100글자
  - price: int
  - deliveryPrice: int
  - category: string
  - content: string
    - 최대 1000글자
- res
  - 성공시 201 리턴
    - HTTP 헤더의 Location 필드에 생성된 게시글의 url 저장

  - 실패시
    - 요청 데이터가 잘못된 경우: 400
    - 세션이 없을 경우(로그인이 안된 경우): 401


### PUT /boards/{boardId}

게시글 수정

- req
  - title: string
    - 최대 100글자
  - url: string
    - 최대 200글자
  - itemName: string
    - 최대 100글자
  - price: int
  - deliveryPrice: int
  - category: string
  - content: string
    - 최대 1000글자
- res
  - 성공시 201
    - Location 필드에 수정된 게시글의 url 저장
  
  - 실패시 
    - 요청 데이터가 잘못된 경우: 400
    - 세션이 없을 경우: 401
    - 수정 권한이 없을 경우(ex)다른 사람의 글을 수정시): 403
  

### DELETE /boards/{boardId}

게시글 삭제

- req: 없음
- res
  - 성공시 200
  - 실패시
    - 요청 데이터가 잘못된 경우: 400
    - 세션이 없을 경우: 401
    - 삭제 권한이 없을 경우: 403

### POST /boards/{boardId}/thumbsup

따봉 누르기

- req: 없음
- res
  - 성공시 200
  - 실패시
    - 요청 데이터가 잘못된 경우: 400
    - 세션이 없을 경우: 401

### GET /comments/{commentId}

댓글 

- req: 없음

- res

  - commentId: int
    - 댓글 id
  - boardId: int
    - 댓글이 달려있는 게시글 id
  - memberId: int
    - 댓글 작성자 식별 id
  - parendComment: int
    - 대댓글인 경우 부모 댓글의 id, 대댓글 아니면 null
  - date: string
    - 댓글 생성 날짜
  - content:string
    - 댓글 내용

  - 성공시 200
  - commentId에 해당하는 댓글이 없으면 404

### GET /comments?boardId=

댓글 목록 가져오기

- req: 없음
- res
  - commentId: int
    - 댓글 id
  - boardId: int
    - 댓글이 달려있는 게시글 id
  - memberId: int
    - 댓글 작성자 식별 id
  - parendComment: int
    - 대댓글인 경우 부모 댓글의 id, 대댓글 아니면 null
  - date: string
    - 댓글 생성 날짜
  - content:string
    - 댓글 내용
  - 리스트 형태
  - 성공시 200
  - 실패시 400 (요청 데이터가 잘못된 경우)

### POST /comments

댓글 등록

- req
  - boardId: int
    - 댓글 쓰는 게시글 id
  - parentComment: int
    - 대댓글인 경우 부모 댓글의 id, 대댓글 아니면 null 
  - content: string
    - 댓글 내용
    - 최대 500 글자
- res
  - 성공시 201
    - HTTP 헤더의 Location 필드에 생성된 댓글의 url 저장
    
  - 실패시
    - 요청 데이터가 잘못된 경우: 400
    - 세션이 없는 경우: 401


### DELETE /comments/{commentId}

댓글 삭제

- req: 없음
  - res
  - 성공시 200
  - 실패시
    - 요청 데이터가 잘못된 경우: 400
    - 세션이 없을 경우: 401
    - 삭제 권한이 없을 경우: 403



## 테이블


```sql
create table member (
	member_id 		int primary key auto_increment not null,
    login_id 		varchar(30) unique  not null,
    password 		varchar(30) not null,
    name			varchar(20) not null
);

create table board (
	board_id 		int primary key auto_increment not null,
    member_id		int not null,
    title 			varchar(100) not null,
    date 			datetime not null,
    views			int not null,
    thumbsup		int not null,
    url				varchar(200) not null,
    item_name		varchar(100) not null,
    price 			int not null,
    delivery_price	int not null,
    category		varchar(10) not null,
    content			varchar(1000) not null,
    foreign key(member_id) references member(member_id) on delete cascade
);

create table comment(
	comment_id 		int primary key auto_increment not null,
    board_id		int not null,
    member_id		int not null,
    parent_comment	int,
    content			varchar(500) not null,
    date			datetime not null,
    foreign key(board_id) references board(board_id) on delete cascade,
    foreign key(member_id) references member(member_id) on delete cascade,
	foreign key(parent_comment) references comment(comment_id) on delete cascade
);

create table thumbsup_table (
	board_id		int not null,
    member_id 		int not null,
    primary key(board_id, member_id),
  	foreign key(board_id) references board(board_id) on delete cascade,
    foreign key(member_id) references member(member_id) on delete cascade
);

create table file_table {
	board_id 		int not null,
	file_name		varchar(100) not null,
	path			varchar(100) not null,
	

}
```

