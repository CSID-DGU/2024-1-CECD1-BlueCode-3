import BCODE from '../../logo_w.png';
import { remove } from '../../remove';
import styled from 'styled-components';
import axiosInstance from '../../axiosInstance';
import React, { useState, useEffect } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import getUserInfo from '../../getUserInfo';


function Study_theory() {
  const [width, setWidth] = useState(window.innerWidth);
  const [height, setHeight] = useState(window.innerHeight);

  const handleResize = () => {
    setWidth(window.innerWidth);
    setHeight(window.innerHeight);
  };

  useState(() => {
    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  const [contentWidth, setContentWidth] = useState(width);

  const [point, setPoint] = useState(0);
  const [process, setProcess] = useState(0);
  const [current, setCurrent] = useState(5);
  const color = {color : "#008BFF"};
  const textDeco = { textDecoration : "none" };

  const [testValid, setTestValid] = useState(false);

  const [missionDaily, setMissionDaily] = useState([]);
  const [missionWeekly, setMissionWeekly] = useState([]);
  const [challenge, setChallenge] = useState([]);

  useEffect(() => {
    const getMissionInfo = async () => {
      try {
       const userid = localStorage.getItem('userid');
    
       const UserMissionDataCallDto = {
        'userId' : userid
        };

        const res = await axiosInstance.post('/mission/mission/find', UserMissionDataCallDto);
        
        setMissionDaily(res.data.listDaily);
        setMissionWeekly(res.data.listWeekly);
        setChallenge(res.data.listChallenge);
      }
      catch (err) {
        console.error(err); 
      }
    };

    // 루트 커리큘럼에 대한 챕터들 정보 불러와서 로컬스토리지에 저장
    const getChapters = async () => {
      try {
        const rootid = localStorage.getItem('rootid');
        const res = await axiosInstance.get(`/curriculum/curriculum/${rootid}`);
        localStorage.setItem("chapters", JSON.stringify(res.data.list));
      }
      catch (err){
        console.error(err); 
      }
    }

    
    getUserInfo()
      .then(data => {
        // 데이터 가져오기 성공 시 상태 업데이트
        setTestValid(data.initTest);
        setPoint(data.exp);
      })
      .catch(error => {
        // 데이터 가져오기 실패 시 에러 처리
        console.error('Error fetching data:', error);
      });

    getChapters(); // 챕터 데이터를 불러오는 함수
    getMissionInfo(); // 미션 데이터를 불러오는 함수
  }, []);

// 서버에서 사용자 정보를 가져오는 함수
/*
const getUserInfo = async () => {
  try {
   const userid = localStorage.getItem('userid');
   const UserIdDto = {
     'userId' : userid
   };
   const res = await axiosInstance.post('/checkAuth/checkAuth/getUserInfo', UserIdDto);
   setTestValid(res.data.initTest);   // 이거 빼고
   setPoint(res.data.exp);            // 이거 빼고
   
  }
  catch (err){
   console.error(err); 
  }
 };
*/
  useEffect(()=>{
    //getUserInfo();
    if (testValid) {
      //console.log(testValid);
    }
  }, [testValid]);

  const navigate = useNavigate();
  const enterExam = () => {
    navigate('/test');
  }

  return (
    <TestSection>
      <SectionBar>
        <Logo>
          <img src={BCODE} alt="Logo"></img>
        </Logo>
      </SectionBar>
      <Content>
        <NavSection height={height}>
          <Static>
            <NavLink style={textDeco} to="/chatbot"><Nav> ㅇ 챗봇에 질문하기 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/todo"><Nav style={color}> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={textDeco} to="/"><Nav onClick={remove}> ㅇ 로그아웃 </Nav></NavLink>
          </Static>
          <Info>
            <InfoNav> ㅇ 현재 진행률 <p> {current / 10 * 100} % </p> </InfoNav>
            <InfoNav> ㅇ 현재 포인트 <p> {point} p </p> </InfoNav>
          </Info>
          <Dynamic>
            <NavLink style={textDeco} to="/mypage/todo"><Nav style={color}> ㅇ 내 할일 관련 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/lecture"><Nav> ㅇ 내 강의 정보 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/question"><Nav> ㅇ 내 질문 정보 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/info"><Nav> ㅇ 내 정보 수정 </Nav></NavLink>
            </Dynamic>
        </NavSection>
        <ContentSection width={contentWidth}>
          <ProgressName> ㅇ 교육 과정 진행 상황 </ProgressName>
          <CurrentProgress>
            <ProgressImg>
              <svg viewBox="0 0 200 200">
                <Circle></Circle>
                <CircleCur strokeDasharray={`${2 * Math.PI * 75 * current / 10} ${2 * Math.PI * 75 * (10 - current) / 10}`}
                           transform={`rotate(-90, 100, 100)`}>
                </CircleCur>
              </svg>
              <Percentage> {current / 10 * 100}% </Percentage>
            </ProgressImg>
            {!testValid && <Progress>
              <Lecture> - 초기 테스트 미응시 </Lecture>
              <SubLecture onClick={enterExam}> 초기 테스트 바로가기 </SubLecture>
            </Progress>}
            {testValid && <Progress>
              <Lecture> - 이전 수강 강의 </Lecture>
              <SubLecture> 구체적인 이전 수강 강의 </SubLecture>
              <Lecture> - 현재 수강 강의 </Lecture>
              <SubLecture> 구체적인 현재 수강 강의 </SubLecture>
              <Lecture> - 다음 수강 강의 </Lecture>
              <SubLecture> 구체적인 다음 수강 강의 </SubLecture>
            </Progress>}
          </CurrentProgress>
          <ProgressName> ㅇ 미션/업적 진행 상황 </ProgressName>
          <CurrentProgress>
            <Mission style={{backgroundColor : "#00E5BA"}}>
              <MissionContent>
                <Term> 일간 </Term>
                <SubMissionContent>
                  {missionDaily.map(item => (
                    <SubMission key={item.id}> {item.text} {item.currentCount} / {item.missionCount} </SubMission>
                  ))}
                </SubMissionContent>
              </MissionContent>
            </Mission>
            <Mission style={{backgroundColor : "#00CFEE"}}>
              <MissionContent>
                <Term> 주간 </Term>
                <SubMissionContent>
                  {missionWeekly.map(item => (
                    <SubMission key={item.id}> {item.text} {item.currentCount} / {item.missionCount} </SubMission>
                  ))}
                </SubMissionContent>
              </MissionContent>
            </Mission>
            <Mission style={{backgroundColor : "#00B2FF"}}>
              <MissionContent>
                <Term> 업적 </Term>
                <SubMissionContent>
                  {challenge.map(item => (
                   item.missionStatus === "COMPLETED" && (
                  <SubMission key={item.id} style={{color:"black"}}> {item.text} </SubMission>
                    )
                  ))}
                </SubMissionContent>
              </MissionContent>
              <PointBtn style={{backgroundColor : "#00B2FF"}}> 업적 보기 </PointBtn>
            </Mission>
          </CurrentProgress>
        </ContentSection>
      </Content>
    </TestSection>
  );
}

export default Study_theory;



const TestSection = styled.div`
  height : 100vh;
`

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

const Content = styled.div`
  display : flex;
`

const NavSection = styled.div`
  display : flex;
  min-width : 15rem;
  flex-direction : column;
  border-right : 0.125rem solid rgba(0, 0, 0, 0.125);
  height : ${(props) => `${(props.height - 68) / 16}rem`};
`

const Static = styled.div`
  padding : 0.625rem;
  border-bottom : 0.125rem solid rgba(0, 0, 0, 0.125);
`

const Info = styled.div`
  display : flex;
  padding : 0.625rem;
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);
  border-bottom : 0.125rem solid rgba(0, 0, 0, 0.125);
`

const InfoNav = styled.div`
  display : flex;
  padding : 0.625rem;
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);

  p {
    margin : 0;
    text-align : right;
  }
`

const Nav = styled.div`
  display : flex;
  padding : 0.625rem;
  font-weight : bold;
  align-items : left;
  flex-direction : column;
  justify-content : center;
  color : rgba(0, 0, 0, 0.25);

  &:hover {
    color : #008BFF;
    background : rgba(0, 139, 255, 0.25);
  }
`

const Dynamic = styled.div`
  overflow : scroll;
  padding : 0.625rem;

  &::-webkit-scrollbar {
    display : none;
  }
`

const ContentSection = styled.div`
  margin : 2rem;
  padding : 2rem;
  border-radius : 1rem;
  border : 0.05rem solid rgba(0, 0, 0, 0.5);
  width : ${(props) => `${(props.width - 370) / 16}rem`};
`

const ProgressName = styled.p`
  margin : 0;
  font-weight : bold;
  font-size : 1.05rem;
  color : rgba(0, 0, 0, 0.5);
`

const CurrentProgress = styled.div`
  display : flex;
  margin : 1rem auto 0rem;
`

const ProgressImg = styled.div`
  width : 12.5rem;
  height : 12.5rem;
  padding : 1.25rem;
`

const Circle = styled.circle`
  r : 75;
  cx : 100;
  cy : 100;
  fill : none;
  stroke-width : 37.5;
  stroke : rgba(0, 0, 0, 0.125);
`

const CircleCur = styled.circle`
  r : 75;
  cx : 100;
  cy : 100;
  fill : none;
  stroke : #008BFF;
  stroke-width : 37.5; 
`

const Percentage = styled.div`
  top : -7.25rem;
  left : 5.25rem;
  color : #008BFF;
  font-weight : bold;
  position : relative;
  font-size : 1.25rem;
`

const Progress = styled.div`
`

const Lecture = styled.p`
  font-weight : bold;
  margin : 1.5rem 1rem 0rem;
  color : rgba(0, 0, 0, 0.5);
`

const SubLecture = styled.p`
  font-weight : bold;
  margin : 0.25rem 1.75rem;
  color : rgba(0, 0, 0, 0.75);
`

const Mission = styled.div`
  width : 18rem;
  padding : 1rem;
  display : flex;
  height : 9.75rem;
  margin : 1rem auto 0rem;
  border-radius : 1.25rem 0rem 1.25rem 0rem;
`

const MissionContent = styled.div`
  display : flex;
  color : #FFFFFF;
  width : 12.5rem;
  align-items : left;
  font-weight : bold;
  justify-content : center;
`

const Term = styled.p`
  margin : 0;
  font-size : 1.25rem;
`

const SubMissionContent = styled.div`

`

const SubMission = styled.div`

`

const PointBtn = styled.button`
  border : none;
  color : #FFFFFF;
  width : 5.375rem;
  font-weight : bold;
`