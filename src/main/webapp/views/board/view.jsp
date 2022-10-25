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
    <div class="view">
        <div class="view-header">
            <div class="title">
                ${board.title}
            </div>
            <div class="title-sub">
                <div class="author">${board.author}</div>
                <div class="createDate">${board.createDate}</div>
            </div>
        </div>
        <div class="view-body">
            <div class="board-info">
                <div class="view-count">${board.view}</div>
                <div class="view-recom">0</div>
                <div class="view-replay-cnt">0</div>
            </div>
            <div class="board-content">
                ${board.contents}
            </div>
        </div>
</div>

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/board/view.js"></script>
</body>
</html>
