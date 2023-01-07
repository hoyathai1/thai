<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/views/common/header.jsp"></jsp:include>
<link rel="stylesheet"  type="text/css" href="/css/m.board.css">

<div class="container">
    <div class="board-list">
    </div>
    <div class="board-paging">
    </div>
    <div class="btn-area">
        <button class="btn two" id="bestBtn" onclick="goBest()">인기글보기</button>
        <button class="btn two on" onclick="goRegister()">글쓰기</button>
    </div>
    <div class="search-area">
        <select class="search-select">
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
        <input class="search-input" placeholder="검색어를 입력하세요." value="${search.content}">
        <button class="search-btn" onclick="search()">
            <div class="search-ico"></div>
        </button>
    </div>

    <c:if test="${banner.bottomBanner.show eq true}">
        <div class="bottomBanner"><div class="bannerImg" style="background: url(/banner/${banner.bottomBanner.fileName}) no-repeat; background-size: contain; background-position: center;" onclick="clickBanner('${banner.bottomBanner.link}', '${banner.bottomBanner.board}')"></div></div>
    </c:if>
</div>

<input type="hidden" name="topBannerShow" value="${banner.topBanner.show}">
<input type="hidden" name="topBannerUrl" value="/banner/${banner.topBanner.fileName}">
<input type="hidden" name="topBannerLink" value="${banner.topBanner.link}">
<input type="hidden" name="topBannerBoard" value="${banner.topBanner.board}">

<input type="hidden" name="pageNum" value="${search.pageNum}">
<input type="hidden" name="pageSize" value="${search.pageSize}">

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/board/m.list.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>

<jsp:include page="/views/common/footer.jsp"></jsp:include>