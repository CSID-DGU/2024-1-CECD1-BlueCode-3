import BCODE from '../../logo_w.png';
import { remove } from '../../remove';
import styled from 'styled-components';
import getUserInfo from '../../getUserInfo';
import SectionBarJsx from '../../SectionBar';
import axiosInstance from '../../axiosInstance';
import getChapterPass from '../../getChapterPass';
import React, { useState, useEffect } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';


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
  const [processPass, setProcessPass] = useState(0);
  const color = {color : "#008BFF"};
  const textDeco = { textDecoration : "none" };

  const [testValid, setTestValid] = useState(false);

  const [missionDaily, setMissionDaily] = useState([]);
  const [missionWeekly, setMissionWeekly] = useState([]);
  const [challenge, setChallenge] = useState([]);

  


  useEffect(() => {

    getChapterPass()
    .then(data => {
        // 데이터 가져오기 성공 시 상태 업데이트
        setProcess(data.length);
        setProcessPass(data.filter(element => element === true).length);
    })
      .catch(error => {
        // 데이터 가져오기 실패 시 에러 처리
      console.error('Error fetching data:', error);
    });

    
    const getMissionInfo = async () => {
      try {
       const userid = localStorage.getItem('userid');
    
       const UserMissionDataCallDto = {
        'userId' : userid
        };

        const res = await axiosInstance.post('/mission/find', UserMissionDataCallDto);
        
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
        const res = await axiosInstance.get(`/curriculum/${rootid}`);
        localStorage.setItem("chapters", JSON.stringify(res.data.list));
      }
      catch (err){
        console.error(err); 
      }
    }

    // 현재 포인트 불러오기
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
      <SectionBarJsx />
      <Content>
        <NavSection height={height}>
          <Static>
            <NavLink style={textDeco} to="/chatbot"><Nav> ㅇ 챗봇에 질문하기 </Nav></NavLink>
            <NavLink style={textDeco} to="/mypage/todo"><Nav style={color}> ㅇ 마이페이지 </Nav></NavLink>
            <NavLink style={textDeco} to="/"><Nav onClick={remove}> ㅇ 로그아웃 </Nav></NavLink>
          </Static>
          <Info>
            <InfoNav> ㅇ 현재 진행률 <p> {isNaN(Math.round(processPass / process * 100))?"- %":Math.round(processPass / process * 100) + " %"} </p> </InfoNav>
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
                <CircleCur strokeDasharray={processPass?`${2 * Math.PI * 75 * processPass / process} ${2 * Math.PI * 75 * (process - processPass) / process}`:"0 1"}
                           transform={`rotate(-90, 100, 100)`}>
                </CircleCur>
              </svg>
              <Percentage> {isNaN(Math.round(processPass / process * 100))?"":Math.round(processPass / process * 100) + "%"} </Percentage>
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
              <Term> 일간 </Term>
              <MissionContent>
                {missionDaily.map(item => (
                <SubMissionContent>
                  <SubMission key={item.id}> {item.text} </SubMission>
                  <SubMissionCount key={item.id}>{item.currentCount} / {item.missionCount} </SubMissionCount>
                </SubMissionContent>
                ))}
              </MissionContent>
            </Mission>
            <Mission style={{backgroundColor : "#00CFEE"}}>
              <Term> 주간 </Term>
              <MissionContent>
                {missionWeekly.map(item => (
                <SubMissionContent>
                  <SubMission key={item.id}> {item.text}  </SubMission>
                  <SubMissionCount key={item.id}>{item.currentCount} / {item.missionCount} </SubMissionCount>
                </SubMissionContent>
                ))}
              </MissionContent>
            </Mission>
            <Mission style={{backgroundColor : "#00B2FF"}}>
              <Term> 업적 </Term>
              <MissionContent>
                {challenge.map(item => (item.missionStatus === "COMPLETED" && (
                  <SubMission_ key={item.id} style={{color:"black"}}> {item.text} </SubMission_>
                )))}
              </MissionContent>
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
  width : 72.5rem;
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
  cursor : pointer;
  font-weight : bold;
  margin : 0.25rem 1.75rem;
  color : rgba(0, 0, 0, 0.75);
`

const Mission = styled.div`
  width : 21.25rem;
  display : flex;
  color : #FFFFFF;
  height : 12.5rem;
  font-weight : bold;
  margin : 1rem auto 0rem;
  flex-direction : column;
  padding : 0rem 1rem 1rem;
  border-radius : 1.25rem 0rem 1.25rem 0rem;
`

const Term = styled.p`
  font-size : 1.25rem;
  margin-bottom : 1rem;
`

const MissionContent = styled.div`
  height : 10rem;
  display : flex;
  width : 23.75rem;
  overflow : scroll;
  flex-direction : column;

  &::-webkit-scrollbar {
    display : none;
  }
`

const SubMissionContent = styled.div`
  display : flex;
  width : 21.25rem;
  margin : 0.15rem 0rem;
`

const SubMission = styled.div`
  width : 18.5rem;
`

const SubMission_ = styled.div`
  width : 21.25rem;
  margin : 0.15rem 0rem;
`

const SubMissionCount = styled.div`
  width : 2.75rem;
  text-align : right;
`