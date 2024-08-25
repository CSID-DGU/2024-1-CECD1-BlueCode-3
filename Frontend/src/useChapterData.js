import React, { useState, useEffect } from 'react';
import axiosInstance from './axiosInstance'


const useChapterData = () => {
    const [chapter, setChapter] = useState([]);
    const [chaptersid, setChaptersid] = useState([]);
    const [chapterLevel, setChapterLevel] = useState([]);
    const [chapterPass, setChapterPass] = useState([]);

    const [subChapter, setSubChapter] = useState([]);
    const [subChapterId, setSubChapterId] = useState([]);
    const [currentChapter, setCurrentChapter] = useState([]);
  
    useEffect(() => {
      if (chapter.length > 0) {
        // console.log(chapter);
      }
    }, [chapter]);

    useEffect(() => {
      if (chaptersid.length > 0) {
        console.log(chaptersid);
      }
    }, [chaptersid]);

    useEffect(() => {
      if (chapterLevel.length > 0) {
        //console.log(chapterLevel);
      }
    }, [chapterLevel]);

    
    useEffect(() => {
      if (chapterPass.length > 0) {
        console.log(chapterPass);
      }
    }, [chapterPass]);
    
    useEffect(() => {
      if (subChapter.length > 0) {
        // console.log(subChapter);
      }
    }, [subChapter]);
  
    useEffect(() => {
      if (subChapterId.length > 0) {
        //console.log(subChapterId);
      }
    }, [subChapterId]);

    useEffect(() => {  
      if (currentChapter.length > 0) {
        //console.log(currentChapter);
      }
    }, [currentChapter]);
  
    useEffect(() => {
      const getChapters = () => {
        const chaptersData = JSON.parse(localStorage.getItem("chapters"));
        // console.log(chaptersData);
        if (!chaptersData)
            return;
  
        const chapters = chaptersData.map(chapter => chapter.text);
        const chaptersid = chaptersData.map(chapter => chapter.curriculumId);
        setChapter(chapters);
        setChaptersid(chaptersid);

        chaptersData.forEach((chapter) => {
          const subChapters = chapter.subChapters.map(subChapter => subChapter.text);
          const subChapterIds = chapter.subChapters.map(subChapterId => subChapterId.curriculumId);
          setSubChapter(prev => [...prev, subChapters]);
          setSubChapterId(pre => [...pre, subChapterIds]);
        });
  
        // console.log(subChapter);
      };
  
      const getCurrentChapters = async () => {
        try {
          const rootid = localStorage.getItem('rootid');
          const userid = localStorage.getItem('userid');
  
          const datacalldto = {
            userId: userid,
            curriculumId: rootid,
          };
  
          const res = await axiosInstance.post('/curriculum/curriculum/chapters', datacalldto);
          const chaptersData = res.data.list;
          console.log(chaptersData);
          
          if (!chaptersData)
            return;

          const chapterLevels = chaptersData.map(chapter => chapter.level);
          const chapterPassed = chaptersData.map(chapter => chapter.passed);
          setChapterLevel(chapterLevels);
          setChapterPass(chapterPassed);


          chaptersData.forEach((chapter) => {
            const currentChapters = chapter.subChapters.map(subChapter => subChapter.passed);
            setCurrentChapter(prev => [...prev, currentChapters]);
          });
        
        } catch (err) {
          console.error(err);
        }
      };
  
      getChapters();
      getCurrentChapters();
    }, []);
  
    return {
      chapter,
      chaptersid,
      chapterLevel,
      chapterPass,
      subChapter,
      subChapterId,
      currentChapter,
    };
  };
  
  export default useChapterData;