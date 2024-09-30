import styled from 'styled-components';
import SectionBarJsx from '../SectionBar';
import getUserInfo from '../../getUserInfo';
import PrivateInfoJsx from '../PrivateInfo';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../axiosInstance';
import getChapterPass from '../../getChapterPass';
import React, { useState, useEffect } from 'react';
import MypageNavSectionJsx from '../MypageNavSection';



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

  const [point, setPoint] = useState(0);
  const [process, setProcess] = useState(0);
  const [processPass, setProcessPass] = useState(0);

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

        const res = await axiosInstance.post('/mission/mission/find', UserMissionDataCallDto);
        console.log(res.data);
        
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

    // 현재 포인트 불러오기
    getUserInfo()
    .then(data => {
        // 데이터 가져오기 성공 시 상태 업데이트
      console.log(data);
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

  const navigate = useNavigate();
  const enterExam = () => {
    navigate('/test');
  }



  return (
    <TestSection>
      <PrivateInfoJsx />
      <SectionBarJsx />
      <Content>
        <MypageNavSectionJsx height={height} l1={true} l2={false} l3={false} />
        <ContentSection width={width}>
          <SubContentSection>
            <SubContentTopLeft>
              <ProgressName> 교육 과정 진행 상황 </ProgressName>
              <CurrentProgress>
                <ProgressImg>
                  <svg viewBox="0 0 200 200">
                    <Circle></Circle>
                    <CircleCur strokeDasharray={processPass?`${2 * Math.PI * 87.5 * processPass / process} ${2 * Math.PI * 87.5 * (process - processPass) / process}`:"0 1"}
                               transform={`rotate(-90, 100, 100)`}>
                    </CircleCur>
                  </svg>
                  <Progress>
                    {!testValid && <>
                      <Lecture> 초기 테스트 미응시 </Lecture>
                      <SubLecture onClick={enterExam}> 초기 테스트 바로가기 </SubLecture>
                    </>}
                    {testValid  && <Percentage> {isNaN(Math.round(processPass / process * 100))?"":Math.round(processPass / process * 100) + "%"} </Percentage>}
                  </Progress>
                </ProgressImg>
              </CurrentProgress>
            </SubContentTopLeft>
            <SubContentBottomLeft>
              <Mission>
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
            </SubContentBottomLeft>
          </SubContentSection>
          <SubContentSection>
            <SubContentTopRight>
              <Mission>
                <Term> 업적 </Term>
                <MissionContent height={height}>
                {challenge.map(item => (<>
                  <SubMission_ key={item.id}> {item.title} </SubMission_>
                  <SubMissionContent>
                    <SubMission key={item.id}> {item.text}  </SubMission>
                    <SubMissionCount key={item.id}>{item.currentCount} / {item.missionCount} </SubMissionCount>
                  </SubMissionContent>
                  </>))}
                </MissionContent>
              </Mission>
            </SubContentTopRight>
            <SubContentBottomRight>
              <Mission>
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
            </SubContentBottomRight>
          </SubContentSection>
        </ContentSection>
      </Content>
    </TestSection>
  );
}
/*item.missionStatus === "COMPLETED" && */
export default Study_theory;



const TestSection = styled.div`
  height : 100vh;
`

const Content = styled.div`
  display : flex;
`

const ContentSection = styled.div`
  display : flex;
  padding : 1rem;
  flex-wrap : wrap;
  margin : 1rem 0rem 1rem 1rem;
  border : 0.125rem solid #008BFF;
  border-radius : 1rem 0rem 0rem 1rem;
  border-right-style : none;
  width : ${(props) => `${(props.width - 291.5) / 16}rem`};
`

const SubContentSection = styled.div`
  width : 50%;  
  display : flex;
  flex-direction : column;
`

const SubContentTopLeft = styled.div`
  height : 50%;
  width : 97.5%;
  display : flex;
  border-radius : 1rem;
  flex-direction : column;
  margin : 0rem 0.5rem 0.5rem 0rem;
  border-left : 0.25rem solid #008BFF;
  border-right : 0.25rem solid #008BFF;
`

const SubContentBottomLeft = styled.div`
  height : 50%;
  width : 97.5%;
  border-radius : 1rem;
  margin : 0.5rem 0.5rem 0rem 0rem;
  border-left : 0.25rem solid #008BFF;
  border-right : 0.25rem solid #008BFF;
`

const SubContentTopRight = styled.div`
  height : 50%;
  width : 97.5%;
  border-radius : 1rem;
  margin : 0rem 0rem 0.5rem 0.5rem;
  border-left : 0.25rem solid #008BFF;
  border-right : 0.25rem solid #008BFF;
`

const SubContentBottomRight = styled.div`
  height : 50%;
  width : 97.5%;
  border-radius : 1rem;
  margin : 0.5rem 0rem 0rem 0.5rem;
  border-left : 0.25rem solid #008BFF;
  border-right : 0.25rem solid #008BFF;
`

const ProgressName = styled.p`
  font-weight : bold;
  font-size : 1.05rem;
  text-align : center;
  margin : 0rem 0rem 0.5rem;
  color : rgba(0, 0, 0, 0.5);
`

const CurrentProgress = styled.div`
  width : 100%;
  height : 100%;
  display : flex;
`

const ProgressImg = styled.div`
  height : 93.775%;
  aspect-ratio: 1 / 1;
  margin : 0.5rem auto;
`

const Circle = styled.circle`
  r : 87.5;
  cx : 100;
  cy : 100;
  fill : none;
  stroke-width : 25;
  stroke : rgba(0, 0, 0, 0.125);
`

const CircleCur = styled.circle`
  r : 87.5;
  cx : 100;
  cy : 100;
  fill : none;
  stroke : #008BFF;
  stroke-width : 25; 
`

const Percentage = styled.p`
  color : #008BFF;
  font-weight : bold;
  font-size : 1.25rem;
  text-align : center;
`

const Progress = styled.div`
  top : -9.625rem;
  color : #008BFF;
  font-weight : bold;
  position : relative;
  font-size : 1.25rem;
`

const Lecture = styled.p`
  margin : 0rem;
  font-weight : bold;
  text-align : center;
  color : rgba(0, 0, 0, 0.5);
`

const SubLecture = styled.p`
  margin : 0rem;
  cursor : pointer;
  font-weight : bold;
  text-align : center;
  color : rgba(0, 0, 0, 0.75);
`

const Mission = styled.div`
  width : 93.5%;
  display : flex;
  padding : 1rem;
  height : 88.75%;
  font-weight : bold;
  flex-direction : column;
`

const Term = styled.p`
  margin : 0rem 0rem 1rem;
  color : #008BFF;
  font-size : 1.25rem;
`
// height : ${(props) => `${(props.height - 400) / 16}rem`};
const MissionContent = styled.div`
  width : 100%;
  display : flex;
  overflow : scroll;
  flex-direction : column;
  height : ${(props) => `${(props.height - 400) / 16}rem`};

  &::-webkit-scrollbar {
    display : none;
  }
`

const SubMissionContent = styled.div`
  width : 100%;
  display : flex;
  margin : 0.25rem 0rem;
`

const SubMission = styled.div`
  width : 90%;
`

const SubMission_ = styled.div`
  width : 100%;
  margin : 0.25rem 0rem;
`

const SubMissionCount = styled.div`
  width : 11.25%;
  text-align : right;
`