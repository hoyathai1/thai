<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>게시판</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <link rel="stylesheet"  type="text/css" href="/css/board.css">
    <link rel="stylesheet"  type="text/css" href="/css/base.css">
</head>
<body>
    <div class="header">
    </div>
    <div class="container">
        <div class="board-list">
        </div>
        <div class="board-paging">
        </div>
        <div class="btn-area">
            <button class="btn two">인기글보기</button>
            <button class="btn two on" onclick="goRegister()">글쓰기</button>
        </div>
        <div class="search-area">
            <select class="search-select">
                <option value="all" <c:if test ="${search.keyword eq 'all'}">selected="selected"</c:if>>
                    제목+내용
                </option>
                <option value="title" <c:if test ="${search.keyword eq 'title'}">selected="selected"</c:if>>
                    제목
                </option>
                <option value="content" <c:if test ="${search.keyword eq 'content'}">selected="selected"</c:if>>
                    내용
                </option>
            </select>
            <input class="search-input" placeholder="검색어를 입력하세요." value="${search.content}">
            <button class="search-btn" onclick="search()">
                <div class="search-ico"></div>
            </button>
        </div>
    </div>

    <input type="hidden" name="pageNum" value="${search.pageNum}">
    <input type="hidden" name="pageSize" value="${search.pageSize}">

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/board/list.js"></script>
</body>
</html>
