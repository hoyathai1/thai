<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
        <div class="header-logo-font"><span>헬타이</span></div>
            <div class="header-info">
                <div class="close" onclick="goClose()">
                    <div class="close-ico"></div>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${empty principal}">
    <div class="menu-container">
        <div class="user-menu">
            <div class="area signUp-area" onclick="goSignUp()">
                <div class="signUP-ico"></div>
                <div class="menu-font">회원가입</div>
            </div>
            <div class="area" onclick="goLogin()">
                <div class="login-ico"></div>
                <div class="menu-font">로그인</div>
            </div>

        </div>
    </div>
    </c:if>

    <c:if test="${not empty principal}">
    <div class="container">
        <div class="user-menu">
            <c:if test="${principal.userAuth eq 'ROLE_ADMIN'}">
                <div class="area" onclick="javascript:location.href='/admin/main'">
                    <div class="admin-ico"></div>
                    <div class="menu-font">관리자</div>
                </div>
            </c:if>
            <div class="area" onclick="goNotice()">
                <div class="notice-ico"></div>
                <div class="menu-font">공지사항</div>
            </div>
            <div class="area" onclick="goAccount()">
                <div class="mod-user-ico"></div>
                <div class="menu-font">내 프로필</div>
            </div>
            <div class="area" onclick="goMyNoti()">
                <div class="notification-ico"></div>
                <div class="menu-font">알림 리스트</div>
            </div>
            <div class="area" onclick="goMyList()">
                <div class="note-ico"></div>
                <div class="menu-font">내가쓴글</div>
            </div>
            <div class="area" onclick="goComment()">
                <div class="note-ico"></div>
                <div class="menu-font">내댓글</div>
            </div>
            <div class="area" onclick="goBookMark()">
                <div class="favorite-ico"></div>
                <div class="menu-font">저장글</div>
            </div>
            <div class="area" onclick="goLogout()">
                <div class="logout-ico"></div>
                <div class="menu-font">로그아웃</div>
            </div>
        </div>
    </div>
    </c:if>

</body>
<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/common/m.menu.js"></script>

<footer class="footer">
    <span>Copyright 2022 hellowthai. All rights reserved.</span>
</footer>

</body>
</html>