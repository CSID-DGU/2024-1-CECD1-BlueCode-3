import axiosInstance from './axiosInstance'


const getChapterPass = async () => {
    try {
        const rootid = localStorage.getItem('rootid');
        const userid = localStorage.getItem('userid');
        const datacalldto = {
            userId: userid,
            curriculumId: rootid,
          };
          const res = await axiosInstance.post('/curriculum/curriculum/chapters', datacalldto)
          const chaptersData = res.data.list;
          const chapterPassed = chaptersData.map(chapter => chapter.passed);

          return chapterPassed;
    }
    catch (err) {
         console.error(err); 
    }
};

export default getChapterPass;
  