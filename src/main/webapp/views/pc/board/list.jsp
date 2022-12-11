<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/board/list.css">
<html>
<head>
    <title></title>
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
                        <td class="title" onclick="goView('${board.id}')">${board.title} <div class="comment-cnt">${board.commentCount}</div></td>

                        <c:if test="${board.user eq true}">
                            <td class="author">${board.username}</td>
                        </c:if>
                        <c:if test="${board.user eq false}">
                            <td class="author">${board.author}(${board.ip})</td>
                        </c:if>

                        <td class="createDate">${boardDate}</td>
                        <td class="view">${board.view}</td>
                        <td class="likes">${board.likes}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="area">
            <div class="search-area">
                <select class="search-select register-select">
                    <option value="all" <c:if test ="${search.keyword eq 'all'}">selected="selected"</c:if>>
                        제목+내용
                    </option>
                    <option value="title" <c:if test ="${search.keyword eq 'title'}">selected="selected"</c:if>>
                        제목
                    </option>
                    <option value="content" <c:if test ="${search.keyword eq 'content'}">selected="selected"</c:if>>
                        내용
                    </option>
                    <input class="search-input" placeholder="검색어를 입력하세요." id="bottomSearch" value="${search.content}">
                    <button class="search-btn" onclick="searchForKeyword()">검색</button>
                </select>
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
    </div>

    <input type="hidden" name="pageNum" value="${search.pageNum}">
    <input type="hidden" name="pageSize" value="${search.pageSize}">

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/pc/board/list.js"></script>
</body>
</html>
