package net.n256coding.calendarapp;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.CalendarView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Nishan on 9/25/2017.
 */
public class MainActivityTest {
    @Rule
    ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }

    @Test
    public void testCalendar() throws Exception {
        View view = mainActivity.findViewById(R.id.calendarView);
        assertNotNull(view);
    }

    @Test
    public void testShowToday() throws Exception{
        CalendarView calendarView = (CalendarView) mainActivity.findViewById(R.id.calendarView);
        long nowTime = new Date().getTime();
        calendarView.setDate(nowTime);
        assertEquals(nowTime, calendarView.getDate());
    }

}