# 명세서

잘 이해가 안되거나 이상한거 있으면 말해주세요~

- 24/10/07
  - 화면 구성 추가
    - /edit/{boardId}, /signup

- 24/10/05
  - POST /logout api 추가
  - /signup, /login 요청 데이터 제약사항 추가

- 24/10/01 최초 작성

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
- res: 없음

### POST /login

로그인

- req
  - loginId: string
    - 최소 5글자, 최대 30글자
  - password: string
    - 최소 5글자, 최대 30글자
- res
  - 세션 쿠키
    - 이름: JSESSIONID
    - 서버에서 이걸로 사용자 식별
    - **이후 모든 요청에 포함, 명세서에 없어도 항상 있어야함**
  - memberId 쿠키
    - 이름: memberId
    - 로그인한 사용자의 식별 id

### POST /logout

로그아웃

- req: 없음
- res: 없음

### GET /boards

홈화면에 표시할 게시글 정보

- req: 없음

- res
  - boardId: int
    - 게시글 id
  - memberId: int
    - 게시글 작성자 식별 id
  - writer: string
    - 게시글 작성자 닉네임
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

### GET /board/{boardId}

게시글 하나의 정보

- req: 없음

- res
  - boardId: int
  - memberId: int
  - writer: string
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
  - 리스트 형태

### POST /board

게시글 등록

- req

  - title: string
  - url: string
  - itemName: string
  - price: int
  - deliveryPrice: int
  - category: string
  - content: string
- res: 없음

### PUT /board/{boardId}

게시글 수정

- req
  - title: string
  - url: string
  - itemName: string
  - price: int
  - deliveryPrice: int
  - category: string
  - content: string
- res: 없음

### DELETE /board/{boardId}

게시글 삭제

- req, res 둘다 없음

### GET /comments

댓글 목록 가져오기

- req
  - boardId: int
    - 가져올 댓글이 있는 게시글 id
- res
  - commentId: int
    - 댓글 id
  - boardId: int
    - 댓글이 달려있는 게시글 id
  - memberId: int
    - 댓글 작성자 식별 id
  - writer: string
    - 댓글 작성자 닉네임
  - parendComment: int
    - 대댓글인 경우 부모 댓글의 id, 대댓글 아니면 null
  - date: string
    - 댓글 생성 날짜
  - content:string
    - 댓글 내용
  - 리스트 형태

### POST /comment

댓글 등록

- req
  - boardId: int
    - 댓글 쓰는 게시글 id
  - parentComment: int
    - 대댓글인 경우 부모 댓글의 id, 대댓글 아니면 null 
  - content: string
    - 댓글 내용
- res: 없음

### DELETE /comment/{commentId}

댓글 삭제

- req, res 둘다 없음

### POST /thumbsup

따봉 누르기

- req
  - boardId: string
    - 따봉 누를 게시글 id
- res: 없음

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
    foreign key(member_id) references member(member_id)
);

create table comment(
	comment_id 		int primary key not null,
    board_id		int not null,
    member_id		int not null,
    parent_comment	int,
    content			varchar(500) not null,
    date			datetime not null,
    foreign key(board_id) references board(board_id),
    foreign key(member_id) references member(member_id),
	foreign key(parent_comment) references comment(comment_id)
);

create table thumbsup_table (
	board_id		int not null,
    member_id 		int not null,
    primary key(board_id, member_id),
  	foreign key(board_id) references board(board_id),
    foreign key(member_id) references member(member_id)
);
```

