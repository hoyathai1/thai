package com.travel.thai.admin;

import com.travel.thai.common.domain.DayStat;
import com.travel.thai.common.domain.DayStatDto;
import com.travel.thai.common.repository.DayStatRepository;
import com.travel.thai.common.session.UserSessionCounter;
import com.travel.thai.common.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private DayStatRepository dayStatRepository;

    @RequestMapping(value = {"/main"}, method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        int userCnt = UserSessionCounter.getCount();    // 지금 방문자 수
        DayStatDto dto = dayStatRepository.dayStatDate(DateUtil.today_date());
        DayStatDto total = dayStatRepository.dayStatTotal();

        model.addAttribute("userCnt", userCnt);
        model.addAttribute("dto", dto);
        model.addAttribute("total", total);

        return "/admin/main";
    }
/*
    @RequestMapping(value = {"/addDate"}, method = RequestMethod.GET)
    public String addDate(HttpServletRequest request, HttpServletResponse response, Model model) {
        int userCnt = UserSessionCounter.getCount();    // 지금 방문자 수

        model.addAttribute("userCnt", userCnt);


        int year = 2024;
        for (int month = 1; month < 13; month++) {
            int maxDay = 0;
            if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
                maxDay = 30;
            } else if (month == 2) {//29일까지인 2월 찾기
                if ((((year % 4) == 0) && (year % 100) != 0) || ((year % 400) == 0)) {
                    //j는 월을 의미하기 때문에 윤년을 확인하기 위해서는 입력한 year으로 비교해야한다.
                    maxDay = 29;
                } else {
                    maxDay = 28;
                }
            } else {//2, 4, 6, 9, 11 이외의 월은 31일까지
                maxDay = 31;
            }

            for (int j = 1; j < maxDay+1; j++) {
                DayStat dayStat = new DayStat();
                dayStat.setNewBoard(0);
                dayStat.setNewComment(0);
                dayStat.setNewLikes(0);
                dayStat.setOutUser(0);
                dayStat.setVisit(0);

                String date = "";
                date = String.valueOf(year);
                if (month < 10) {
                    date += "0" + String.valueOf(month);
                } else {
                    date += String.valueOf(month);
                }

                if (j < 10) {
                    date += "0" + String.valueOf(j);
                } else {
                    date += String.valueOf(j);
                }

                dayStat.setStatDate(date);

                dayStatRepository.save(dayStat);
            }

        }


        return "/admin/main";
    }*/
}
