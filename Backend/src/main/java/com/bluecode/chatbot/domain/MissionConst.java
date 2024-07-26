package com.bluecode.chatbot.domain;


public class MissionConst {

    private MissionConst() {
    }

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

    public static final String STUDY_COMPLETE = "STUDY_COMPLETE";

    public static final String STUDY_CHAPTER_COMPLETE = "STUDY_CHAPTER_COMPLETE";

    public static final String USER_LOGIN = "USER_LOGIN";

    public static final String USER_STREAK = "USER_STREAK";

    public static final String MISSION_DAILY_COMPLETE = "MISSION_DAILY_COMPLETE";

    public static final String MISSION_WEEKLY_COMPLETE = "MISSION_WEEKLY_COMPLETE";

    public static String createConstByRootAndChapterName(Curriculums root, Curriculums chapter) {
        return "STUDY_" + root.getCurriculumName().toUpperCase() + "_" + chapter.getChapterNum() + "_COMPLETE";
    }

    public static String createConstByRootName(Curriculums root) {
        return "STUDY_" + root.getCurriculumName().toUpperCase() + "_COMPLETE";
    }

}
