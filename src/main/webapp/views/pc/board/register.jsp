<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/board/register.css">
<link rel="stylesheet"  type="text/css" href="/css/pc/board/base.css">
<html>
<head>
    <meta property="og:title" content="헬타이">
    <meta property="og:url" content="http://hellowthai.com/">
    <meta property="og:image" content="/img/logo.png">
    <meta property="og:description" content="태국정보를 공유하는 커뮤님티입니다.">
    <title>헬타이</title>
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
                    <li onclick="goNotice()">공지사항</li>
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
                <div class="right-logo" style="background: url(/banner/${banner.rightBanner.fileName}) no-repeat;" onclick="clickBanner('${banner.rightBanner.link}', '${banner.rightBanner.board}')"></div>
            </c:if>
            <c:if test="${banner.rightBanner.show eq false}">
                <div class="right-logo"></div>
            </c:if>
        </div>

        <div class="left-sidebar">
            <c:if test="${banner.leftBanner.show eq true}">
                <div class="left-logo" style="background: url(/banner/${banner.leftBanner.fileName}) no-repeat;" onclick="clickBanner('${banner.leftBanner.link}', '${banner.leftBanner.board}')"></div>
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
                <div class="top-logo" style="background: url(/banner/${banner.topBanner.fileName}) no-repeat;" onclick="clickBanner('${banner.topBanner.link}', '${banner.topBanner.board}')"></div>
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

            <div class="register">
                <div class="title">
                    <select class="register-select" name="s-type">
                        <c:forEach items="${boardType}" var="bt">
                            <option value="${bt.type}">
                                    ${bt.name}
                            </option>
                        </c:forEach>
                    </select>
                    <input class="ipt" type="text" maxlength="40" name="title" autocomplete="off" placeholder="제목">
                </div>
                <c:if test="${empty principal}">
                <div class="info">
                    <input class="ipt name" type="text" maxlength="10" name="name" placeholder="닉네임">
                    <input class="ipt password" type="password" maxlength="10" name="password" placeholder="패스워드">
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
                    <div class="text-box" id="editor" contenteditable="true" placeholder="내용"></div>
                </div>
                <div class="register-info">
                    <ul>
                        <li>
                            음란물, 차별, 비하, 혐오 및 초상권, 저작권 침해 게시물은 민, 형사상의 책임을 질 수 있습니다.
                        </li>
                        <li>불법촬영물 등을 게시할 경우 <a href="https://www.law.go.kr/법령/전기통신사업법/(20211019,18477,20211019)/제22조의5" class="wrt-notice-lnk" target="_blank">[전기통신사업법 제22조의5 제1항]</a>에 따라 게시물 삭제 등의 조치가 취해질 수 있으며, 관련 법률에 의거하여 처벌받을 수 있습니다.</li>
                    </ul>
                </div>
                <div class="register-btn">
                    <button onclick="goList()">취소</button>
                    <c:if test="${empty principal}">
                        <button onclick="btnRegister()">등록</button>
                    </c:if>
                    <c:if test="${not empty principal}">
                        <button onclick="btnRegisterLogin()">등록</button>
                    </c:if>
                </div>
            </div> <%--.content--%>

            <c:if test="${banner.bottomBanner.show eq true}">
                <div class="bottom-logo" style="background: url(/banner/${banner.bottomBanner.fileName}) no-repeat;" onclick="clickBanner('${banner.bottomBanner.link}', '${banner.bottomBanner.board}')"></div>
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

    <form id="uploadForm" method="post" enctype="multipart/form-data" style="display: none;">
        <input id="uploadFile" name="uploadFile" type="file" accept="image/*" multiple />
    </form>

    <input type="hidden" name="pageNum" value="${search.pageNum}">
    <input type="hidden" name="pageSize" value="${search.pageSize}">
    <input type="hidden" name="keyword" value="${search.keyword}">
    <input type="hidden" name="content" value="${search.content}">

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/pc/base.js"></script>
    <script type="text/javascript" src="/js/pc/board/register.js"></script>
</body>
</html>
