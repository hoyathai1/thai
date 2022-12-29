<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/menu/myComment.css">
<%--<link rel="stylesheet"  type="text/css" href="/css/pc/board/base.css">--%>
<html>
<head>
    <title></title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

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

<div class="container">
    <div class="category">
        <div class="list">
            <c:forEach items="${allCategory}" var="category">
                <div class="name" onclick="goCategory('${category.id}')">${category.name}</div>
            </c:forEach>
        </div>
    </div>

    <div class="subject">
        <div class="subject-area">
            <h1>내 댓글</h1>
        </div>
    </div>

    <div class="filter">
        <div class="search-filter">
            <label>검색조건:</label>
            <select class="search-select" id="keyword">
                <option value="all" <c:if test ="${search.keyword eq 'all'}">selected="selected"</c:if>>
                    제목+내용
                </option>
                <option value="title" <c:if test ="${search.keyword eq 'title'}">selected="selected"</c:if>>
                    제목
                </option>
                <option value="content" <c:if test ="${search.keyword eq 'content'}">selected="selected"</c:if>>
                    내용
                </option>
            </select>
        </div>
        <div class="search-area">
            <label>검색어 :</label>
            <input class="search-input" placeholder="검색어를 입력하세요." value="${search.content}">
        </div>
    </div>
    <div class="search-btn-area">
        <button class="search-btn" onclick="initSearch()">초기화</button>
        <button class="search-btn" onclick="search()">검색</button>
    </div>

    <div class="myComment">
        <c:forEach items="${list.content}" var="comment">
            <fmt:parseDate value="${comment.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
            <fmt:formatDate pattern="yyyy.MM.dd" value="${ parsedDateTime }" var="commentDate"/>
            <div class="comment" onclick="goComment('${comment.id}', '${comment.boardId}', '${comment.category}')">
                <div class="title">
                    <span>게시글: </span><div>${comment.title}</div>
                </div>
                <div class="content">
                    <span>내용: </span><div>${comment.content}</div>
                </div>
                <div class="createDate">
                    ${commentDate}
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty list.content}">
            <div class="empty">
                검색결과가 없습니다.
            </div>
        </c:if>
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
                    <li class="page-next" onclick="movePage('${pageDto.endPage}')">&raquo;</li>
                </c:if>
            </c:if>
        </ul>
    </div>
</div>

<input type="hidden" name="pageNum" value="${search.pageNum}">
<input type="hidden" name="pageSize" value="${search.pageSize}">
<input type="hidden" name="keyword" value="${search.keyword}">
<input type="hidden" name="content" value="${search.content}">

<input type="hidden" name="likes" value="${search.likes}">
<input type="hidden" name="comment" value="${search.comment}">

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/pc/base.js"></script>
<script type="text/javascript" src="/js/pc/menu/myComment.js"></script>
</body>
</html>
