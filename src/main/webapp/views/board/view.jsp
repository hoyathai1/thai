<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/views/common/header.jsp"></jsp:include>
<link rel="stylesheet"  type="text/css" href="/css/m.board.css">

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal" />
</sec:authorize>

<div class="container">
    <div class="view">
        <div class="view-header">
            <c:if test="${not empty principal}">
                <c:if test="${isBookMark eq true}"><div class="bookmark-ico on" onclick="setBookMark()"></div></c:if>
                <c:if test="${isBookMark eq false}"><div class="bookmark-ico off" onclick="setBookMark()"></div></c:if>
            </c:if>
            <div class="title">
                ${board.title}
            </div>
            <div class="title-sub">
                <c:if test="${board.user eq true}">
                    <div class="author">${board.author}</div>
                </c:if>
                <c:if test="${board.user eq false}">
                    <div class="author">${board.author} (${board.ip})</div>
                </c:if>
                <div class="createDate">${board.createDate}</div>
            </div>
        </div>
        <c:set value="${board.likes}" var="likesCnt"></c:set>
        <c:if test="${board.likes eq null}">
            <c:set value="0" var="likesCnt"></c:set>
        </c:if>

        <div class="view-body">
            <div class="board-info">
                <div class="view-info-left">
                    <div class="view-count">${board.view}</div>
                    <div class="view-recom"><c:out value="${likesCnt}" /></div>
                    <div class="view-replay-cnt">0</div>
                </div>
                <div class="view-info-right">
                    <div class="share-ico" onclick="shareBtn()"></div>
                    <div class="share" onclick="shareBtn()">공유</div>
                </div>
            </div>
            <div class="board-content">
                ${board.contents}
            </div>

            <div class="likes">
                <button class="likes-btn" onclick="likesBtn()">

                    <div class="likes-cnt"><c:out value="${likesCnt}" /></div>
                    <c:if test="${isLikes eq true}">
                        <div class="like-ico on"></div>
                    </c:if>
                    <c:if test="${isLikes eq false}">
                        <div class="like-ico"></div>
                    </c:if>
                </button>
            </div>
        </div>
        <div class="btn-area">
            <c:if test="${empty principal}">
                <c:if test="${board.user}">
                    <button class="btn two on" onclick="goRegister()">글쓰기</button>
                    <button class="btn two" onclick="goList()">목록보기</button>
                </c:if>
                <c:if test="${not board.user}">
                    <button class="btn four" onclick="modifyBtn()">수정</button>
                    <button class="btn four" onclick="deleteBtn()">삭제</button>
                    <button class="btn four on" onclick="goRegister()">글쓰기</button>
                    <button class="btn four" onclick="goList()">목록보기</button>
                </c:if>
            </c:if>

            <c:if test="${not empty principal}">
                <c:if test="${principal.username eq board.userId}">
                    <button class="btn four" onclick="modifyBtnByOwner()">수정</button>
                    <button class="btn four" onclick="deleteBtnByOwner()">삭제</button>
                    <button class="btn four on" onclick="goRegister()">글쓰기</button>
                    <button class="btn four" onclick="goList()">목록보기</button>
                </c:if>
                <c:if test="${principal.username ne board.userId}">
                    <button class="btn two on" onclick="goRegister()">글쓰기</button>
                    <button class="btn two" onclick="goList()">목록보기</button>
                </c:if>
            </c:if>
        </div>

        <div class="comment">
            <div class="comment-info">
                <div class="fnt-bold">전체 댓글</div>
                <div class="comment-cnt">[0]</div>
                <button class="refresh-btn" onclick="getCommentList(0)">
                    <div class="refresh-ico"></div>
                </button>
            </div>
            <div class="comment-list">
            </div>
            <div class="comment-form">
                <c:if test="${empty principal}">
                <div class="user-info">
                    <input type="text" class="ipt" name="nickname" placeholder="닉네임">
                    <input type="password" class="ipt" name="commentPassword" placeholder="비밀번호">
                </div>
                </c:if>
                <div class="comment-content">
                    <textarea name="commentContent" maxlength="200" placeholder="내용"></textarea>
                </div>
                <div class="btn-area">
                    <c:if test="${empty principal}">
                    <button class="btn on small" onclick="registerComment()">등록</button>
                    </c:if>
                    <c:if test="${not empty principal}">
                    <button class="btn on small" onclick="registerCommentByOwner()">등록</button>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <div class="div-border"></div>
    <div class="board-list">
    </div>
</div>

<input type="hidden" name="boardNum" value="${search.boardNum}">
<input type="hidden" name="pageNum" value="${search.pageNum}">
<input type="hidden" name="pageSize" value="${search.pageSize}">
<input type="hidden" name="keyword" value="${search.keyword}">
<input type="hidden" name="content" value="${search.content}">

<input type="hidden" name="loginId" value="${principal.userId}">
<input type="hidden" name="isLogin" value="${principal != null}">

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/board/m.view.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>

<jsp:include page="/views/common/footer.jsp"></jsp:include>
