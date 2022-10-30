<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <link rel="stylesheet"  type="text/css" href="/css/base.css">
    <link rel="stylesheet"  type="text/css" href="/css/board.css">
</head>
<body>
    <div class="container">
        <div class="register">
            <div class="subject">
                <h4>글쓰기</h4>
                <button id="btn-register" onclick="btnRegister();">등록</button>
            </div>
            <div class="title">
                <input class="ipt" type="text" maxlength="40" id="title" name="title" autocomplete="off" placeholder="제목">
            </div>
            <div class="user-info">
                <div class="name">
                    <input class="ipt" type="text" maxlength="10" id="name" name="name" placeholder="닉네임" value="">
                </div>
                <div class="password">
                    <input class="ipt" type="password" maxlength="10" id="password" name="password" placeholder="패스워드" value="">
                </div>
            </div>
            <div class="editor-menu">
                <button class="btn" id="btn-bold">
                    <b>B</b>
                </button>
                <button class="btn" id="btn-italic">
                    <i>I</i>
                </button>
                <button class="btn" id="btn-underline">
                    <u>U</u>
                </button>
                <button class="btn" id="btn-strike">
                    <s>S</s>
                </button>
                <input id="img-selector" type="file" accept="image/*" multiple />
                <button class="btn" id="btn-img">
                    이미지
                </button>
            </div>
            <div class="content">
                <div class="text-box" id="editor" contenteditable="true" placeholder="내용"></div>
            </div>

            <div class="info">
                <ul>
                    <li>
                        음란물, 차별, 비하, 혐오 및 초상권, 저작권 침해 게시물은 민, 형사상의 책임을 질 수 있습니다.
                    </li>
                    <li>불법촬영물 등을 게시할 경우 <a href="https://www.law.go.kr/법령/전기통신사업법/(20211019,18477,20211019)/제22조의5" class="wrt-notice-lnk" target="_blank">[전기통신사업법 제22조의5 제1항]</a>에 따라 게시물 삭제 등의 조치가 취해질 수 있으며, 관련 법률에 의거하여 처벌받을 수 있습니다.</li>
                </ul>
            </div>

            <div class="btn-area between">
                <button class="btn small" onclick="goList();">취소</button>
                <button class="btn small" onclick="btnRegister();">등록</button>
            </div>
        </div>
    </div>

    <input type="hidden" name="pageNum" value="${search.pageNum}">
    <input type="hidden" name="pageSize" value="${search.pageSize}">
    <input type="hidden" name="keyword" value="${search.keyword}">
    <input type="hidden" name="content" value="${search.content}">

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/board/register.js"></script>
    <script type="text/javascript" src="/js/m.base.js"></script>
</body>
</html>
