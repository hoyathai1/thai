<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <div class="btn-area">
            <button class="btn four">수정</button>
            <button class="btn four">삭제</button>
            <button class="btn four on">글쓰기</button>
            <button class="btn four" onclick="goList()">목록보기</button>
        </div>

        <div class="comment">
            <div class="comment-info">
                <div class="fnt-bold">전체 댓글</div>
                <div class="comment-cnt">[0]</div>
                <button class="refresh-btn" onclick="getCommentList(0)">
                    <div class="refresh-ico"></div>
                </button>
            </div>
            <div class="comment-list">
            </div>
            <div class="comment-form">
                <div class="user-info">
                    <input type="text" class="ipt" name="nickname" placeholder="닉네임">
                    <input type="password" class="ipt" name="commentPassword" placeholder="비밀번호">
                </div>
                <div class="comment-content">
                    <textarea name="commentContent" maxlength="200" placeholder="내용"></textarea>
                </div>
                <div class="btn-area">
                    <button class="btn on small" onclick="registerComment()">등록</button>
                </div>
            </div>
        </div>
    </div>
</div>

<input type="hidden" name="boardNum" value="${search.boardNum}">
<input type="hidden" name="pageNum" value="${search.pageNum}">
<input type="hidden" name="pageSize" value="${search.pageSize}">
<input type="hidden" name="keyword" value="${search.keyword}">
<input type="hidden" name="content" value="${search.content}">

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/board/view.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>
</body>
</html>
