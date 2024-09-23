
const getChapterTestable = async () => {
    try {

        const chaptersData = JSON.parse(localStorage.getItem("chapters"));
        // console.log(chaptersData);
        if (!chaptersData)
            return;

        const chapters_testable = chaptersData.map(chapter => chapter.testable);
        return chapters_testable;
    }
    catch (err) {
         console.error(err); 
    }
};

export default getChapterTestable;
  