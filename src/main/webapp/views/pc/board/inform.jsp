<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/board/view.css">
<link rel="stylesheet"  type="text/css" href="/css/pc/board/base.css">
<html>
<head>
    <title>게시글</title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate pattern="yyyyMMdd" value="${ now }" var="today" />

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
                <c:if test="${empty principal}">
                    <li onclick="goSignUp()">회원가입</li>
                    <li onclick="goLogin()">로그인</li>
                </c:if>
                <c:if test="${not empty principal}">
                    <c:if test="${principal.userAuth eq 'ROLE_ADMIN'}">
                        <li onclick="javascript:location.href='/admin/main'">
                            관리자
                        </li>
                    </c:if>
                    <li onclick="goMyAccount()">프로필</li>
                    <li onclick="goMyList()">내가쓴글</li>
                    <li onclick="goMyComment()">내 댓글</li>
                    <li onclick="goBookmark()">저장글</li>
                    <li onclick="goLogout()">로그아웃</li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>

<div class="wrapper">
    <div class="right-sidebar">
        <c:if test="${not empty principal}">
            <div class="alert">
                <div class="user-info" onclick="goMyAccount()">
                    <strong>${principal.username}</strong>님
                </div>
                <div class="user-alert">
                    <div class="top">
                        <button onclick="goMyAccount()">프로필</button>
                        <button onclick="goMyList()">내가쓴글</button>
                        <button onclick="goMyComment()">내 댓글</button>
                    </div>
                    <div class="bottom">
                        <button onclick="goBookmark()">저장글</button>
                        <button onclick="goNotiModal()">
                            <c:if test="${isHasNoti eq false}">
                                <div class="alert-ico" id="alert-ico-id"></div>알림
                            </c:if>
                            <c:if test="${isHasNoti eq true}">
                                <div class="alert-ico on" id="alert-ico-id"></div>알림
                            </c:if>
                        </button>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${empty principal}">
            <div class="alert off">
            </div>
        </c:if>

        <c:if test="${banner.rightBanner.show eq true}">
            <div class="right-logo" style="background: url(/banner/${banner.rightBanner.fileName}) no-repeat;" onclick="clickBanner('${banner.rightBanner.link}')"></div>
        </c:if>
        <c:if test="${banner.rightBanner.show eq false}">
            <div class="right-logo"></div>
        </c:if>
    </div>

    <div class="left-sidebar">
        <c:if test="${banner.leftBanner.show eq true}">
            <div class="left-logo" style="background: url(/banner/${banner.leftBanner.fileName}) no-repeat;" onclick="clickBanner('${banner.leftBanner.link}')"></div>
        </c:if>
        <c:if test="${banner.leftBanner.show eq false}">
            <div class="left-logo"></div>
        </c:if>
    </div>

    <div class="container">
        <div class="category">
            <div class="list">
                <c:forEach items="${allCategory}" var="category">
                    <div class="name" onclick="goCategory('${category.id}')">${category.name}</div>
                </c:forEach>
            </div>
        </div>

        <c:if test="${banner.topBanner.show eq true}">
            <div class="top-logo" style="background: url(/banner/${banner.topBanner.fileName}) no-repeat;" onclick="clickBanner('${banner.topBanner.link}')"></div>
        </c:if>

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

        <div class="content">
            <div class="view">
                <div class="title">
                    ${board.title}
                </div>
                <div class="info">
                    <div class="left">
                        <div class="author">${board.username}</div>
                        <div class="createDate">${board.createDate}</div>
                    </div>
                    <div class="right">
                    </div>
                </div> <%--.info--%>
                <div class="contents">
                    ${board.contents}
                </div>

                <div class="border-line">
                </div>

                <div class="view-btn">
                    <div class="left">

                    </div>
                    <div class="right">
                        <button onclick="goList()">목록</button>
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${banner.bottomBanner.show eq true}">
            <div class="bottom-logo" style="background: url(/banner/${banner.bottomBanner.fileName}) no-repeat;" onclick="clickBanner('${banner.bottomBanner.link}')"></div>
        </c:if>
    </div>
</div> <%--.wrapper--%>

<div class='alert-modal'>
    <div class='alert-modal-display'>
        <div class="alert-modal-info">
            <div class="title"><strong>알림</strong></div>
            <div class="x-btn" onclick="closeNotiModal()"><div class="x-ico"></div></div>
        </div>
        <div class="alert-modal-option">
            <div class="all-option" onclick="allDeleteNoti()">전체삭제</div>
        </div>
        <div class='alert-modal-content'>
        </div>
    </div>
</div>

<input type="hidden" name="boardNum" value="${search.boardNum}">
<input type="hidden" name="pageNum" value="${search.pageNum}">
<input type="hidden" name="pageSize" value="${search.pageSize}">
<input type="hidden" name="keyword" value="${search.keyword}">
<input type="hidden" name="content" value="${search.content}">

<input type="hidden" name="loginId" value="${principal.userId}">
<input type="hidden" name="loginName" value="${principal.name}">
<input type="hidden" name="isLogin" value="${principal != null}">

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/pc/board/inform.js"></script>
<script type="text/javascript" src="/js/pc/base.js"></script>
</body>
</html>


