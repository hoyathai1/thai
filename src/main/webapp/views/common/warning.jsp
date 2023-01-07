<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta property="og:title" content="헬타이">
    <meta property="og:url" content="http://hellowthai.com/">
    <meta property="og:image" content="/img/logo.png">
    <meta property="og:description" content="태국정보를 공유하는 커뮤님티입니다.">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <title>헬타이</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
    <link rel="stylesheet"  type="text/css" href="/css/common.css">
</head>
<body>
    <div class="container">
        <div class="warning">
            <div class="warn-ico">
            </div>
            <div class="warn1">
                잘못된접근입니다.
            </div>
            <div class="warn2">
                잠시 후 게시판 리스트로 이동합니다.
            </div>
        </div>
    </div>
    <script>
        setTimeout(function () {
            location.href = "/";
        }, 2000);
    </script>
</body>
</html>
