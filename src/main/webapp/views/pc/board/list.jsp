<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/board/list.css">
<link rel="stylesheet"  type="text/css" href="/css/pc/board/base.css">
<html>
<head>
    <meta property="og:title" content="헬타이">
    <meta property="og:url" content="http://hellowthai.com">
    <meta property="og:image" content="/img/logo.png">
    <meta property="og:description" content="태국정보를 공유하는 커뮤님티입니다.">
    <title>헬타이</title>
</head>
<body>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="principal"/>
    </sec:authorize>

    <jsp:useBean id="now" class="java.util.Date" />
    <fmt:formatDate pattern="yyyyMMdd" value="${ now }" var="today" />

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

            <table class="list">
                <thead>
                    <tr>
                        <th class="type">타입</th>
                        <th class="title">제목</th>
                        <th class="author">글쓴이</th>
                        <th class="createDate">날짜</th>
                        <th class="view">조회</th>
                        <th class="likes">추천</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${inform}" var="inform">
                        <fmt:parseDate value="${inform.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                        <fmt:formatDate pattern="yyyy.MM.dd" value="${ parsedDateTime }" var="informDate"/>
                        <tr class="inform">
                            <td class="type">공지</td>
                            <td class="title" onclick="goinform('${inform.id}')">${inform.title}</td>
                            <td class="author">${inform.username}</td>
                            <td class="createDate">${informDate}</td>
                            <td class="view"></td>
                            <td class="likes"></td>
                        </tr>
                    </c:forEach>

                    <c:forEach items="${list.content}" var="board">
                        <fmt:parseDate value="${board.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                        <fmt:formatDate pattern="yyyy.MM.dd" value="${ parsedDateTime }" var="boardDate"/>
                        <tr>
                            <td class="type">${board.typeName}</td>
                            <td class="title" onclick="goView('${board.id}')">
                                    ${board.title}
                                    <c:if test="${board.img}"><div class="img-ico"></div></c:if>
                                    <div class="comment-cnt">${board.commentCount}</div>
                            </td>

                            <c:if test="${board.user eq true}">
                                <td class="author isUser" onclick="goUserModal('${board.id}', '${board.author}')" id="${board.id}">${board.username}</td>
                            </c:if>
                            <c:if test="${board.user eq false}">
                                <td class="author">${board.author}(${board.ip})</td>
                            </c:if>

                            <td class="createDate">${boardDate}</td>
                            <td class="view">${board.view}</td>
                            <td class="likes">${board.likes}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty list.content}">
                        <tr>
                            <td colspan="6" class="empty">검색결과가 없습니다.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
            <div class="area">
                <div class="search-area">
                    <select class="search-select register-select">
                        <option value="all" <c:if test ="${search.keyword eq 'all'}">selected="selected"</c:if>>
                            전체
                        </option>
                        <option value="title" <c:if test ="${search.keyword eq 'title'}">selected="selected"</c:if>>
                            제목
                        </option>
                        <option value="content" <c:if test ="${search.keyword eq 'content'}">selected="selected"</c:if>>
                            내용
                        </option>
                        <option value="author" <c:if test ="${search.keyword eq 'author'}">selected="selected"</c:if>>
                            작성자
                        </option>
                    </select>
                    <input class="search-input" placeholder="검색어를 입력하세요." id="bottomSearch" value="${search.content}">
                    <button class="search-btn" onclick="searchForKeyword()">검색</button>
                </div>
                <div class="btn-area">
                    <button onclick="goRegister()">글쓰기</button>
                </div>
            </div>
            <div class="paging">
                <ul>
                    <c:if test="${not empty list.content}">
                        <c:if test="${pageDto.startPage eq 0}">
                            <li class="page-prev" onclick="movePage('${pageDto.startPage}')">&laquo;</li>
                        </c:if>
                        <c:if test="${pageDto.startPage ne 0}">
                            <li class="page-prev" onclick="movePage('${pageDto.startPage - 1}')">&laquo;</li>
                        </c:if>

                        <c:forEach var="i" begin="${pageDto.startPage}" end="${pageDto.endPage}" step="1">
                            <c:if test="${pageDto.curPage eq i}">
                                <li class="page active">${i+1}</li>
                            </c:if>
                            <c:if test="${pageDto.curPage ne i}">
                                <li class="page" onclick="movePage('${i}')">${i+1}</li>
                            </c:if>
                        </c:forEach>

                        <c:if test="${pageDto.endPage eq 0}">
                            <li class="page-next" onclick="movePage('${pageDto.endPage}')">&raquo;</li>
                        </c:if>
                        <c:if test="${pageDto.endPage ne 0}">
                            <li class="page-next" onclick="movePage('${pageDto.endPage+1}')">&raquo;</li>
                        </c:if>
                    </c:if>
                </ul>
            </div>

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

    <div class='user-modal'>
        <div class='user-modal-display'>
            <div class="user-modal-info">
                <div class="title"><strong>유저정보</strong></div>
                <div class="x-btn" onclick="closeUserModal()"><div class="x-ico"></div></div>
            </div>
            <div class='user-modal-content' data-id="">
                <div onclick="authorSearch()">작성 글 보기</div>
            </div>
        </div>
    </div>

    <input type="hidden" name="pageNum" value="${search.pageNum}">
    <input type="hidden" name="pageSize" value="${search.pageSize}">

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/pc/board/list.js"></script>
    <script type="text/javascript" src="/js/pc/base.js"></script>
</body>
</html>
