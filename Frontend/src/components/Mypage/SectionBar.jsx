import React from 'react';
import BCODE from '../../logo_w.png';
import styled from 'styled-components';

function SectionBarJsx() {
    
    return (
        <SectionBar>
            <Logo>
                <img src={BCODE} alt="Logo"></img>
            </Logo>
        </SectionBar>
    );
}

export default SectionBarJsx;



const SectionBar = styled.div`
  width : 100vw;
  display : flex;
  background : #008BFF;
`

const Logo = styled.div`
  img {
    height : 2rem;
    width : 7.82rem;
    margin : 1rem 4rem;
  }
`