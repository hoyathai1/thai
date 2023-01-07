<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/user/login.css">
<html>
<head>
    <meta property="og:title" content="헬타이">
    <meta property="og:url" content="http://hellowthai.com/">
    <meta property="og:image" content="/img/logo.png">
    <meta property="og:description" content="태국정보를 공유하는 커뮤님티입니다.">
    <title>헬타이</title>
</head>
<body>
    <div class="header">
        <div class="logo-font">헬로우타이.com</div><div class="login-font">로그인</div>
    </div>

    <div class="container">
        <div class="login">
            <form role="form">
                <input class="ipt" type="text" name="username" id="username"  placeholder="아이디" maxlength="20">
                <input class="ipt" type="password" name="password" id="password" placeholder="비밀번호" maxlength="20">

                <button class="btn" onclick="btnLogin()">로그인</button>

                <div class="remember">
                    <input type="checkbox" id="remember-me" name="remember-me" checked>
                    <label for="remember-me">로그인유지</label>
                </div>

                <%--<div class="info" style="display:<c:if test="${error eq true}">block;</c:if><c:if test="${error ne true}">none;</c:if>">--%>
                <div class="info">
                    <c:if test="${errorType eq '1'}">
                        <span class="fnt-red">아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.</span>
                    </c:if>
                    <c:if test="${errorType eq '2'}">
                        <span class="fnt-red">계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.</span>
                    </c:if>
                    <c:if test="${errorType eq '3'}">
                        <span class="fnt-red">내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.</span>
                    </c:if>
                </div>

            </form>

            <div class="user-btn">
                <div class="signUp-btn" onclick="goSignUpBtn()">회원가입</div>
                <div class="find-pwd-btn" onclick="goFindPwd()">비밀번호 찾기</div>
            </div>
        </div>
    </div>




<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/pc/base.js"></script>
<script type="text/javascript" src="/js/pc/user/login.js"></script>
</body>
</html>
