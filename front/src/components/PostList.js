import React from 'react';

function PostList() {
  const posts = [
    {
      title: '캘리포니아뉴트리션 락토비프 프로바이오틱스 300억유산균 60캡슐 x 3개',
      shop: '옥션',
      price: '44,390원',
      shipping: '무료',
      category: '먹거리',
      time: '16:29',
      author: '마타',
    },
    // 다른 게시글도 추가 가능
  ];

  return (
    <div className="post-list">
      {posts.map((post, index) => (
        <div key={index} className="post-item">
          <img src="https://via.placeholder.com/100" alt={post.title} />
          <div className="post-details">
            <h3>{post.title}</h3>
            <p>쇼핑몰: {post.shop} | 가격: {post.price} | 배송: {post.shipping}</p>
            <p>{post.category} | {post.time} | {post.author}</p>
          </div>
        </div>
      ))}
    </div>
  );
}

export default PostList;
