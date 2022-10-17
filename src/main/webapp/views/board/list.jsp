<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시판</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <link rel="stylesheet"  type="text/css" href="/css/board.css">
</head>
<body>
    <div class="header">
    </div>
    <div class="container">
        <div class="board-list">
        </div>
        <div class="board-paging">
        </div>
        <div class="search-area">
            <select class="search-select">
                <option value="all">제목+내용</option>
                <option value="title">제목</option>
                <option value="content">내용</option>
            </select>
            <input class="search-input" placeholder="검색어를 입력하세요.">
            <button class="search-btn" onclick="search()">
                <div class="search-ico"></div>
            </button>

        </div>
    </div>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/board/list.js"></script>
</body>
</html>
