import React from 'react';
import styled from 'styled-components';


const ConfirmJsx = ({ message, onConfirm, onCancel }) => {
    return (
        <ConfirmSection>
            <ConfirmContent>
                <Message>{message}</Message>
                <ButtonSection>
                    <Button onClick={onConfirm}> 확인 </Button>
                    <Button onClick={onCancel}> 취소 </Button>
                </ButtonSection>
            </ConfirmContent>
        </ConfirmSection>
    );
};
  
export default ConfirmJsx;



const ConfirmSection = styled.div`
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  position: fixed;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.25);
`

const ConfirmContent = styled.div`
  display : flex;
  padding : 1.25rem;
  border-radius : 1rem;
  flex-direction : column;
  background-color : #FFFFFF;
  border : 0.25rem solid #008BFF;
`

const Message = styled.p`
  font-weight : bold;
  text-align : center;
  color : rgba(0, 0, 0, 0.75);
`

const ButtonSection = styled.div`
  display : flex;
  align-items: center;
  justify-content: center;
`

const Button = styled.button`
  border : none;
  color : #FFFFFF;
  cursor : pointer;
  font-weight : bold;
  padding : 0.5rem 1rem;
  border-radius : 0.5rem;
  margin : 1rem 0.5rem 0rem;
  background-color : #008BFF;
`