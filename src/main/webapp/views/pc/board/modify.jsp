<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/board/modify.css">
<html>
<head>
    <title></title>
</head>
<body>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="principal"/>
    </sec:authorize>

    <div class="div_load_image">
        <img src="/img/loading.gif" style="width:100px; height:100px;">
    </div>

    <div class="top-gnb">
        <div class="description">
            <h6>헬타이 - 태국 종합 커뮤니티</h6>
        </div>
        <div class="menu">
            <nav>
                <ul>
                    <li>프로필</li>
                    <li>내가쓴글</li>
                    <li>저장글</li>
                    <c:if test="${empty principal}">
                        <li onclick="goSignUp()">회원가입</li>
                        <li onclick="goLogin()">로그인</li>
                    </c:if>
                    <c:if test="${not empty principal}">
                        <li onclick="goLogout()">로그아웃</li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>

    <div class="container">
        <div class="category">
            <div class="list">
                <c:forEach items="${allCategory}" var="category">
                    <div class="name" onclick="goCategory('${category.id}')">${category.name}</div>
                </c:forEach>
            </div>
        </div>

        <div class="subject">
            <div class="subject-area" onclick="goCategory('${boardCategory.id}')">
                <div class="ico-thai"></div>
                <h1>${boardCategory.name}</h1>
            </div>

            <div class="search-area">
                <input class="search-input" placeholder="검색어를 입력하세요." value="${search.content}">
                <button class="search-btn" onclick="search()">검색</button>
            </div>
        </div>

        <div class="types">
            <div class="list">
                <div class="type" id="all" onclick="goType('all')">전체</div>
                <div class="type" id="best" onclick="goBest()">인기</div>
                <c:forEach items="${boardType}" var="type">
                    <div class="type" id="${type.type}" onclick="goType('${type.type}')">${type.name}</div>
                </c:forEach>
            </div>
        </div>

        <div class="modify">
            <div class="title">
                <select class="register-select" name="s-type">
                    <c:forEach items="${boardType}" var="bt">
                        <option value="${bt.type}" <c:if test="${bt.type eq board.type}">selected</c:if>>
                                ${bt.name}
                        </option>
                    </c:forEach>
                </select>
                <input class="ipt" type="text" maxlength="40" name="title" autocomplete="off" placeholder="제목" value="${board.title}">
            </div>
            <c:if test="${empty principal}">
            <div class="info">
                <input class="ipt name" type="text" maxlength="10" name="name" placeholder="닉네임" value="${board.author}" disabled>
                <%--<input class="ipt password" type="password" maxlength="10" name="password" placeholder="패스워드">--%>
            </div>
            </c:if>
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
                <input id="img-selector" type="file" accept="image/*" multiple style="display: none"/>
                <button class="btn" id="btn-img">
                    이미지
                </button>
            </div>
            <div class="content">
                <div class="text-box" id="editor" contenteditable="true" placeholder="내용">${board.contents}</div>
            </div>
            <div class="modify-info">
                <ul>
                    <li>
                        음란물, 차별, 비하, 혐오 및 초상권, 저작권 침해 게시물은 민, 형사상의 책임을 질 수 있습니다.
                    </li>
                    <li>불법촬영물 등을 게시할 경우 <a href="https://www.law.go.kr/법령/전기통신사업법/(20211019,18477,20211019)/제22조의5" class="wrt-notice-lnk" target="_blank">[전기통신사업법 제22조의5 제1항]</a>에 따라 게시물 삭제 등의 조치가 취해질 수 있으며, 관련 법률에 의거하여 처벌받을 수 있습니다.</li>
                </ul>
            </div>
            <div class="modify-btn">
                <button onclick="goBack()">취소</button>
                <button onclick="btnModify()">수정</button>
            </div>
        </div> <%--.content--%>
    </div>

    <form id="uploadForm" method="post" enctype="multipart/form-data" style="display: none;">
        <input id="uploadFile" name="uploadFile" type="file" accept="image/*" multiple />
    </form>

    <input type="hidden" name="boardNum" value="${search.boardNum}">
    <input type="hidden" name="password" value="${search.password}">
    <input type="hidden" name="pageNum" value="${search.pageNum}">
    <input type="hidden" name="pageSize" value="${search.pageSize}">
    <input type="hidden" name="keyword" value="${search.keyword}">
    <input type="hidden" name="content" value="${search.content}">

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/pc/base.js"></script>
    <script type="text/javascript" src="/js/pc/board/modify.js"></script>
</body>
</html>
