import React from 'react';

const categories = [
  "먹거리", "SW/게임", "PC제품", "가전제품", "생활용품", "의류", "세일정보", "화장품", 
  "모바일/상품권", "패키지/이용권", "기타", "공지사항"
];

function Tabs() {
    return (
      <div className="tabs">
        {categories.map((category, index) => (
          <span key={index} className="tab-item">{category}</span>
        ))}
      </div>
    );
  }

export default Tabs;
