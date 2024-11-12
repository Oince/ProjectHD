// WriteStyles.js
import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f8f9fa;
`;

export const WriteContainer = styled.div`
  width: 90%;
  max-width: 600px;
  padding: 20px;
  background-color: #ffffff;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const Title = styled.h2`
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 20px;
  text-align: center;
`;

export const CategoryContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
  justify-content: center;
`;

export const CategoryButton = styled.button`
  background-color: ${(props) => (props.active ? '#ff5c5c' : '#f2f2f2')};
  color: ${(props) => (props.active ? '#ffffff' : '#333333')};
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  &:hover {
    background-color: #ff5c5c;
    color: #ffffff;
  }
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;

export const Input = styled.input`
  width: 100%;
  max-width: 500px;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
  outline: none;
  box-sizing: border-box;
  &:focus {
    border-color: #ff5c5c;
  }
`;

export const TextArea = styled.textarea`
  width: 100%;
  max-width: 500px;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
  outline: none;
  resize: vertical;
  min-height: 100px;
  box-sizing: border-box;
  &:focus {
    border-color: #ff5c5c;
  }
`;

export const SubmitButton = styled.button`
  background-color: #ff5c5c;
  color: #ffffff;
  border: none;
  padding: 12px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  width: 100%;
  max-width: 500px;
  &:hover {
    background-color: #e04a4a;
  }
`;

export const Message = styled.p`
  margin-top: 10px;
  font-size: 14px;
  color: #333;
`;


export const PostContainer = styled.div`
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
`;

export const PostTitle = styled.h1`
  font-size: 2rem;
  color: #333;
  margin-bottom: 16px;
  text-align: center;
`;


export const Info = styled.p`
  font-size: 1rem;
  color: #555;
  margin: 4px 0;
`;

export const Content = styled.div`
  font-size: 1.1rem;
  color: #333;
  line-height: 1.6;
  background: #fff;
  padding: 15px;
  border-radius: 8px;
  margin-top: 20px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
`;

export const UrlLink = styled.a`
  color: #1e90ff;
  text-decoration: underline;
  &:hover {
    color: #0056b3;
  }
`;

export const BackButton = styled.button`
  margin-top: 20px;
  padding: 10px 15px;
  font-size: 1rem;
  color: #fff;
  background-color: #1e90ff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  &:hover {
    background-color: #0056b3;
  }
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 10px;
  margin-top: 20px;
`;

export const ActionButton = styled.button`
  padding: 10px 15px;
  font-size: 1rem;
  color: #fff;
  background-color: ${props => (props.delete ? '#ff4d4f' : '#1e90ff')};
  border: none;
  border-radius: 5px;
  cursor: pointer;
  &:hover {
    background-color: ${props => (props.delete ? '#cc0000' : '#0056b3')};
  }
`;