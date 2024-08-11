package com.bluecode.chatbot.domain;


public class MissionConst {

    private MissionConst() {
    }

    // test 관련
    public static final String TEST_SUBMIT = "TEST_SUBMIT";

    public static final String TEST_PASS = "TEST_PASS";

    public static final String TEST_FAIL = "TEST_FAIL";

    public static final String TEST_EASY_SUBMIT = "TEST_EASY_SUBMIT";

    public static final String TEST_NORMAL_SUBMIT = "TEST_NORMAL_SUBMIT";

    public static final String TEST_HARD_SUBMIT = "TEST_HARD_SUBMIT";

    public static final String TEST_EASY_PASS = "TEST_EASY_PASS";

    public static final String TEST_NORMAL_PASS = "TEST_NORMAL_PASS";

    public static final String TEST_HARD_PASS = "TEST_HARD_PASS";

    public static final String TEST_INIT_COMPLETE = "TEST_INIT_COMPLETE";

    // user 관련
    public static final String USER_LOGIN = "USER_LOGIN";

    public static final String USER_STREAK = "USER_STREAK";

    // mission 관련
    public static final String MISSION_DAILY_COMPLETE = "MISSION_DAILY_COMPLETE";

    public static final String MISSION_WEEKLY_COMPLETE = "MISSION_WEEKLY_COMPLETE";

    // chat 관련

    public static final String CHAT_QUESTION  ="CHAT_QUESTION";

    public static final String CHAT_DEF_QUESTION = "CHAT_DEF_QUESTION";

    public static final String CHAT_CODE_QUESTION = "CHAT_CODE_QUESTION";

    public static final String CHAT_ERRORS_QUESTION = "CHAT_ERRORS_QUESTION";

    public static String createConstByQuestionTypeAndLevel(QuestionType questionType, int level) {
        return "CHAT_" + questionType + "_UNTIL_" + level;
    }

    // study 관련
    public static final String STUDY_COMPLETE = "STUDY_COMPLETE";

    public static String createConstByRootAndSubChapterName(Curriculums root, Curriculums chapter) {
        return "STUDY_" + root.getCurriculumName().toUpperCase() + "_CHAP_" + chapter.getChapterNum() + "_SUB_" + chapter.getSubChapterNum() + "_COMPLETE";
    }

    public static String createConstByRootName(Curriculums root) {
        return "STUDY_" + root.getCurriculumName().toUpperCase() + "_COMPLETE";
    }

}
