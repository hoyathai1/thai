<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/menu/myList.css">
<link rel="stylesheet"  type="text/css" href="/css/pc/board/base.css">
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
                <li onclick="goMyAccount()">프로필</li>
                <li onclick="goMyList()">내가쓴글</li>
                <li onclick="goMyComment()">댓글</li>
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
        <div class="subject-area">
            <h1>내가쓴글</h1>
        </div>
    </div>

    <div class="filter">
        <div class="category-filter">
            <label>카테고리:</label>
            <select id="category-filter-select">
                <c:forEach items="${allCategory}" var="category">
                    <c:if test="${category.id eq boardCategory.id}">
                        <option value="${category.id}" selected>${category.name}</option>
                    </c:if>
                    <c:if test="${category.id ne boardCategory.id}">
                        <option value="${category.id}">${category.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="likes-filter">
            <label>추천 수:</label>
            <button id="likes10" class="likes <c:if test="${search.likes eq 10}">on</c:if>" onclick="onLikes(10)">10</button>
            <button id="likes20" class="likes <c:if test="${search.likes eq 20}">on</c:if>" onclick="onLikes(20)">20</button>
            <button id="likes30" class="likes <c:if test="${search.likes eq 30}">on</c:if>" onclick="onLikes(30)">30</button>
        </div>
        <div class="comment-filter">
            <label>댓글 수:</label>
            <button id="comment10" class="comment <c:if test="${search.comment eq 10}">on</c:if>" onclick="onComment(10)">10</button>
            <button id="comment20" class="comment <c:if test="${search.comment eq 20}">on</c:if>" onclick="onComment(20)">20</button>
            <button id="comment30" class="comment <c:if test="${search.comment eq 30}">on</c:if>" onclick="onComment(30)">30</button>
        </div>
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

    <table class="list">
        <thead>
        <tr>
            <th class="type">타입</th>
            <th class="title">제목</th>
            <th class="createDate">날짜</th>
            <th class="view">조회</th>
            <th class="likes">추천</th>
            <th class="comment">댓글</th>
            <th class="move">원글이동</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list.content}" var="board" varStatus="status">
            <fmt:parseDate value="${board.createDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
            <fmt:formatDate pattern="yyyy.MM.dd" value="${ parsedDateTime }" var="boardDate"/>
            <tr>
                <td class="type">${board.typeName}</td>
                <td class="title" onclick="goView('${board.id}')">${board.title}</td>
                <td class="createDate">${boardDate}</td>
                <td class="view">${board.view}</td>
                <td class="likes">${board.likes}</td>
                <td class="comment">${board.commentCount}</td>
                <td class="move"><button>이동</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
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
<script type="text/javascript" src="/js/pc/menu/myList.js"></script>
</body>
</html>
