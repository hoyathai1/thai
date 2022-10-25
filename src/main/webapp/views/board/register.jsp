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
                <button id="btn-register" onclick="btnRegister()">등록</button>
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
                <input id="img-selector" type="file" accept="image/*"/>
                <button class="btn" id="btn-img">
                    이미지
                </button>
            </div>
            <div class="content">
                <div class="text-box" id="editor" contenteditable="true" placeholder="내용"></div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/board/register.js"></script>
</body>
</html>
