package com.jonathanwaters.termmanager;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PopulateDB {
    //Create terms
    private static Date springStart;

    static {
        try {
            springStart = new SimpleDateFormat("MM/dd/yyy").parse("01/01/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date springEnd;

    static {
        try {
            springEnd = new SimpleDateFormat("MM/dd/yyy").parse("06/30/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Term spring2021 = new Term("Spring 2021", springStart, springEnd);

    private static Date fallStart;

    static {
        try {
            fallStart = new SimpleDateFormat("MM/dd/yyyy").parse("07/01/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date fallEnd;

    static {
        try {
            fallEnd = new SimpleDateFormat("MM/dd/yyyy").parse("12/31/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Term fall2021 = new Term("Fall 2021", fallStart, fallEnd);

    //Create courses
    private static Date c195Start;

    static {
        try {
            c195Start = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date c195End;

    static {
        try {
            c195End = new SimpleDateFormat("MM/dd/yyyy").parse("02/28/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date c195StartAlarm;

    static {
        try {
            c195StartAlarm = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1970");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date c195DueAlarm;

    static {
        try {
            c195DueAlarm = new SimpleDateFormat("MM/dd/yyyy").parse("02/20/1970");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Course c195 = new Course("C195", c195Start, c195End, "Completed",
            "Carolyn Sher-DeCusatis", "385-428-7192",
            "carolyn.sher@wgu.edu", c195StartAlarm, c195DueAlarm, "Sample notes",
            1, 1, 2);

    private static Date c196Start;

    static {
        try {
            c196Start = new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date c196End;

    static {
        try {
            c196End = new SimpleDateFormat("MM/dd/yyyy").parse("04/30/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date c196StartAlarm;

    static {
        try {
            c196StartAlarm = new SimpleDateFormat("MM/dd/yyyy").parse("03/01/1970");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date c196DueAlarm;

    static {
        try {
            c196DueAlarm = new SimpleDateFormat("MM/dd/yyyy").parse("04/20/1970");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Course c196 = new Course("C196", c196Start, c196End, "In Progress",
            "Carolyn Sher-DeCusatis", "385-428-7192",
            "carolyn.sher@wgu.edu", c196StartAlarm, c196DueAlarm, "Sample notes",
            2, 3, 4);

    //Create assessments
    private static Date c195AssessmentDue;

    static {
        try {
            c195AssessmentDue = new SimpleDateFormat("MM/dd/yyyy").parse("02/20/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date c195AssessmentAlarm;

    static {
        try {
            c195AssessmentAlarm = new SimpleDateFormat("MM/dd/yyyy").parse("02/15/1970");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date c195AssessmentDueAlarm;

    static {
        try {
            c195AssessmentDueAlarm = new SimpleDateFormat("MM/dd/yyyy").parse("02/20/1970");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Assessment c195Assessment = new Assessment("C195 Assessment", "Performance",
            "Assessment title", c195AssessmentDue, "Assessment info", c195AssessmentAlarm,
            "Passed", 1, 5, c195AssessmentDueAlarm, 6);

    private static Date c196AssessmentDue;

    static {
        try {
            c196AssessmentDue = new SimpleDateFormat("MM/dd/yyyy").parse("04/20/2021");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date c196AssessmentAlarm;

    static {
        try {
            c196AssessmentAlarm = new SimpleDateFormat("MM/dd/yyyy").parse("04/15/1970");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Date c196AssessmentDueAlarm;

    static {
        try {
            c196AssessmentDueAlarm = new SimpleDateFormat("MM/dd/yyyy").parse("04/20/1970");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Assessment c196Assessment = new Assessment("C196 Assessment", "Performance",
            "Assessment title", c196AssessmentDue, "Assessment info", c196AssessmentAlarm,
            "Pending", 2, 7, c196AssessmentDueAlarm, 8);

    public PopulateDB() throws ParseException {
    }

    public static void populate(Context context) {
        Database db = Database.getInstance(context);
        if (db.termDAO().count() == 0 &&
            db.courseDAO().count() == 0 &&
            db.assessmentDAO().count() == 0) {
            db.termDAO().insert(spring2021);
            db.termDAO().insert(fall2021);
            db.courseDAO().insert(c195);
            db.courseDAO().insert(c196);
            db.assessmentDAO().insert(c195Assessment);
            db.assessmentDAO().insert(c196Assessment);
        }
    }
}
