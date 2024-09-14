import axiosInstance from './axiosInstance'

const URL = "http://3.37.159.243:8080"

const getChapterPass = async () => {
    try {
        const rootid = localStorage.getItem('rootid');
        const userid = localStorage.getItem('userid');
        const datacalldto = {
            userId: userid,
            curriculumId: rootid,
          };
          const res = await axiosInstance.post('/curriculum/chapters', datacalldto)
          const chaptersData = res.data.list;
          const chapterPassed = chaptersData.map(chapter => chapter.passed);

          return chapterPassed;
    }
    catch (err) {
         console.error(err); 
    }
};

export default getChapterPass;
  