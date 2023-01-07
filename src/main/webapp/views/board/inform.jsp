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
            <div class="title">
                ${board.title}
            </div>
            <div class="title-sub">
                <div class="author">${board.username}</div>
                <div class="createDate">${board.createDate}</div>
            </div>
        </div>

        <div class="view-body">
            <div class="board-content">
                ${board.contents}
            </div>
        </div>
        <div class="btn-area">
            <button class="btn two" onclick="goList()">목록보기</button>
        </div>
    </div>

    <div class="div-border"></div>
    <div class="board-list">
    </div>

    <c:if test="${banner.bottomBanner.show eq true}">
        <div class="bottomBanner"><div class="bannerImg" style="background: url(/banner/${banner.bottomBanner.fileName}) no-repeat; background-size: contain; background-position: center;" onclick="clickBanner('${banner.bottomBanner.link}', '${banner.bottomBanner.board}')"></div></div>
    </c:if>
</div>

<input type="hidden" name="boardNum" value="${search.boardNum}">
<input type="hidden" name="pageNum" value="${search.pageNum}">
<input type="hidden" name="pageSize" value="${search.pageSize}">
<input type="hidden" name="keyword" value="${search.keyword}">
<input type="hidden" name="content" value="${search.content}">

<input type="hidden" name="rightBannerShow" value="${banner.rightBanner.show}">
<input type="hidden" name="rightBannerUrl" value="/banner/${banner.rightBanner.fileName}">
<input type="hidden" name="rightBannerLink" value="${banner.rightBanner.link}">
<input type="hidden" name="rightBannerBoard" value="${banner.rightBanner.board}">

<input type="hidden" name="loginId" value="${principal.userId}">
<input type="hidden" name="isLogin" value="${principal != null}">

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/board/m.inform.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>

<jsp:include page="/views/common/footer.jsp"></jsp:include>
