<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<header>
    <title></title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/css/m.base.css">
    <link rel="stylesheet" type="text/css" href="/css/m.common.css">

</header>

<body>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<div class="div_load_image">
    <img src="/img/loading.gif" style="width:100px; height:100px;">
</div>

<div class="header-top">
    <div class="header-logo-font"><span>헬타이</span></div>
    <c:if test="${empty principal}">
    <div class="header-info">
        <div onclick="goSignUp()">회원가입</div>
        <div class="header-login" onclick="goLogin()">로그인</div>
    </c:if>
    <c:if test="${not empty principal}">
    <div class="header-info two">
        <div onclick="goLogout()">로그아웃</div>
        </c:if>
        <div class="menu" onclick="goMenu()">메뉴</div>
    </div>
</div>

<div class="header-category">
    <div class="category-log" onclick="goMain('${boardCategory.id}')"></div>
    <div class="category-name" onclick="goMain('${boardCategory.id}')">${boardCategory.name}</div>
    <div class="category-btn">
        <button onclick="goRegister()" class="category-btn-register">글쓰기</button>
    </div>
</div>

<ul class="header-gnb">
    <li><a id="gnb-all" href="/board/list?type=all&best=&category=${search.category}&pageNum=0">전체</a></li>
    <li><a id="gnb-best" href="/board/list?type=board&best=Y&category=${search.category}&pageNum=0">인기글</a></li>
    <c:forEach items="${boardType}" var="bt">
        <c:if test="${bt.type ne 'board'}">
            <li><a id="gnb-${bt.type}" href="/board/list?type=${bt.type}&best=&category=${search.category}&pageNum=0">${bt.name}</a></li>
        </c:if>
    </c:forEach>
</ul>
