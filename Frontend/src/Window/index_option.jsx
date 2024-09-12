import React from 'react';
import styled from 'styled-components';


const OptionJsx = ({ message, onOption1, onOption2, onOption3 }) => {
    return (
        <OptionSection>
            <OptionContent>
                <Message>{message}</Message>
                <ButtonSection>
                    <Button onClick={onOption1}> 입문자 </Button>
                    <Button onClick={onOption2}> 초급자 </Button>                    
                    {null?{}:<Button onClick={onOption3}> 중급자 </Button>}
                </ButtonSection>
            </OptionContent>
        </OptionSection>
    );
};
  
export default OptionJsx;



const OptionSection = styled.div`
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

const OptionContent = styled.div`
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