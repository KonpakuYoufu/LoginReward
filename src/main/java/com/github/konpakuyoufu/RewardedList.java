package com.github.konpakuyoufu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class RewardedList {
    public Calendar calendar;
    public List<Integer> list = new ArrayList();

    public RewardedList(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        this.calendar = calendar;
    }
}
