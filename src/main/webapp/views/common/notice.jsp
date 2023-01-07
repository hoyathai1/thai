<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<header>
    <meta property="og:title" content="헬타이">
    <meta property="og:url" content="http://hellowthai.com/">
    <meta property="og:image" content="/img/logo.png">
    <meta property="og:description" content="태국정보를 공유하는 커뮤님티입니다.">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <title>헬타이</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/css/m.base.css">
    <link rel="stylesheet" type="text/css" href="/css/m.common.css">
    <link rel="stylesheet"  type="text/css" href="/css/m.menu.css">
</header>
<body>

    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="principal"/>
    </sec:authorize>

    <div class="menu-header-top">
        <div class="prev" onclick="goBack()">
            <div class="prev-ico"></div>
        </div>
        <div class="header-logo-font"><span>헬타이</span></div>
            <div class="header-info">
                <div class="close" onclick="goClose()">
                    <div class="close-ico"></div>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="notice">
            <div class="title">
                공지사항
            </div>
            <div class="content">
                <div class="notice-list">
                    <c:forEach items="${list}" var="noti">
                        <fmt:parseDate value="${noti.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                        <div class="notice" onclick="showDetail('${noti.id}')" id="noti-${noti.id}">
                            <div class="notice-type noti-${noti.type}">
                                <%--<div class="not-${noti.type}-ico"></div>--%>
                                <c:if test="${noti.type eq 'EVENT'}">이벤트</c:if>
                                <c:if test="${noti.type eq 'NOTICE'}">공지</c:if>
                            </div>
                            <div class="notice-title">${noti.title}</div>
                            <div class="notice-date"><fmt:formatDate pattern="yyyy.MM.dd. HH:mm" value="${ parsedDateTime }" /></div>
                            <div class="notice-body" id="body-${noti.id}">${noti.contents}</div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

</body>
<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>
<script type="text/javascript" src="/js/common/m.menu.js"></script>
<script type="text/javascript" src="/js/common/m.notice.js"></script>

<jsp:include page="/views/common/footer.jsp"></jsp:include>