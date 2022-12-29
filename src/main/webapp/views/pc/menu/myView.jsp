<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/menu/myView.css">
<%--<link rel="stylesheet"  type="text/css" href="/css/pc/board/base.css">--%>
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

        <c:set value="${board.likes}" var="likesCnt"></c:set>
        <c:if test="${board.likes eq null}">
            <c:set value="0" var="likesCnt"></c:set>
        </c:if>

        <div class="content">
            <div class="view">
                <div class="title">
                    ${board.title}
                </div>
                <div class="info">
                    <div class="left">
                        <c:if test="${board.user eq true}">
                            <div class="author">${board.author}</div>
                        </c:if>
                        <c:if test="${board.user eq false}">
                            <div class="author">${board.author} (${board.ip})</div>
                        </c:if>
                        <div class="createDate">${board.createDate}</div>
                    </div>
                    <div class="right">
                        <div class="views">${board.view}</div>
                        <c:if test="${likesCnt eq null}">
                            <div class="likes">0</div>
                        </c:if>
                        <c:if test="${likesCnt ne null}">
                            <div class="likes"><c:out value="${likesCnt}" /></div>
                        </c:if>
                        <div class="comment-count">0</div>
                    </div>
                </div> <%--.info--%>
                <div class="contents">
                    ${board.contents}
                </div>

                <div class="comment-area">
                    <div class="comment-title">
                        <div class="left">
                            댓글<div class="comment-cnt">0</div>개
                        </div>
                        <div class="right">
                            <div class="share" onclick="shareBtn()">
                                공유
                            </div>
                        </div>
                    </div>
                    <div class="comment-list">
                    </div>
                    <div class="comment-paging">
                    </div>
                    <div class="comment-form">
                        <div class="ipt">
                            <input class="comment-user" type="text" placeholder="닉네임" name="nickname" readonly value="${principal.name}">
                        </div>
                        <div class="comment-textarea">
                            <textarea class="commentContent" maxlength="200" placeholder="내용" name="commentContent"></textarea>
                        </div>
                        <div class="btn register">
                            <button class="register-btn" onclick="registerCommentByOwner()">등록</button>
                        </div>
                    </div>
                </div> <%-- .comment-area --%>
                <div class="view-btn">
                    <div class="left">
                        <button onclick="goList()">목록</button>
                    </div>
                    <div class="right">
                        <button onclick="modifyBtnByOwner()">수정</button>
                        <button onclick="deleteBtnByOwner()" class="delete">삭제</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <input type="hidden" name="boardNum" value="${search.boardNum}">
    <input type="hidden" name="pageNum" value="${search.pageNum}">
    <input type="hidden" name="pageSize" value="${search.pageSize}">
    <input type="hidden" name="likes" value="${search.likes}">
    <input type="hidden" name="comment" value="${search.comment}">
    <input type="hidden" name="category" value="${search.category}">
    <input type="hidden" name="keyword" value="${search.keyword}">
    <input type="hidden" name="content" value="${search.content}">

    <input type="hidden" name="loginId" value="${principal.userId}">
    <input type="hidden" name="loginName" value="${principal.name}">
    <input type="hidden" name="isLogin" value="${principal != null}">
    <input type="hidden" name="commentPage" value="">
    <input type="hidden" name="subPageNum" value="">

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/pc/menu/myView.js"></script>
    <script type="text/javascript" src="/js/pc/base.js"></script>
</body>
</html>


