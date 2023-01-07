package com.travel.thai.common.session;

import com.travel.thai.common.service.DayStatService;
import com.travel.thai.common.service.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Slf4j
@WebListener
public class UserSessionCounter implements HttpSessionListener {

    // 총 접속자 수
    public static int count;

    public static int getCount() {
        return count;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        count ++;

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
        //등록되어있는 빈을 사용할수 있도록 설정해준다
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //request를 파라미터에 넣지 않고도 사용할수 있도록 설정
        VisitService visitService = (VisitService)wac.getBean("visitService");
        visitService.save(req);

        log.info("\n\tSESSION CREATED : {}, TOTAL ACCESSER : {}", session.getId(), count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // 세션이 소멸될 때
        count--;
        if( count < 0 ) count = 0;

        HttpSession session = se.getSession();
        log.info("\n\tSESSION DESTROYED : {}, TOTAL ACCESSER : {}", session.getId(), count);
    }


}
