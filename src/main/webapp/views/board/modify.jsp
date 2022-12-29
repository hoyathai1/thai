<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/views/common/header.jsp"/>
<link rel="stylesheet"  type="text/css" href="/css/m.board.css">

<div class="container">
    <div class="register">
        <div class="subject">
            <h4>글쓰기</h4>
            <button id="btn-register" onclick="btnModify();">수정</button>
        </div>
        <div class="title">
            <%--<span class="board-type-name">${board.typeName}</span>--%>
            <select class="register-select" name="s-type">
                <c:forEach items="${boardType}" var="bt">
                    <option value="${bt.type}" <c:if test="${bt.type eq board.type}">selected</c:if>>
                            ${bt.name}
                    </option>
                </c:forEach>
            </select>
            <input class="ipt" type="text" maxlength="40" id="title" name="title" autocomplete="off" placeholder="제목" value="${board.title}">
        </div>
        <div class="user-info">
            <div class="name">
                <input class="ipt" type="text" maxlength="10" id="name" name="name" placeholder="닉네임" value="${board.author}" disabled>
            </div>
            <div class="password">
            </div>
        </div>
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
            <input id="img-selector" type="file" accept="image/*" multiple />
            <button class="btn" id="btn-img">
                이미지
            </button>
        </div>
        <div class="content">
            <div class="text-box" id="editor" contenteditable="true" placeholder="내용">${board.contents}</div>
        </div>

        <div class="info">
            <ul>
                <li>
                    음란물, 차별, 비하, 혐오 및 초상권, 저작권 침해 게시물은 민, 형사상의 책임을 질 수 있습니다.
                </li>
                <li>불법촬영물 등을 게시할 경우 <a href="https://www.law.go.kr/법령/전기통신사업법/(20211019,18477,20211019)/제22조의5" class="wrt-notice-lnk" target="_blank">[전기통신사업법 제22조의5 제1항]</a>에 따라 게시물 삭제 등의 조치가 취해질 수 있으며, 관련 법률에 의거하여 처벌받을 수 있습니다.</li>
            </ul>
        </div>

        <div class="btn-area between">
            <button class="btn small" onclick="goBack();">취소</button>
            <button class="btn small" onclick="btnModify();">수정</button>
        </div>
    </div>

    <c:if test="${banner.bottomBanner.show eq true}">
        <div class="bottomBanner"><div class="bannerImg" style="background: url(/banner/${banner.bottomBanner.fileName}) no-repeat; background-size: contain; background-position: center;" onclick="clickBanner('${banner.bottomBanner.link}')"></div></div>
    </c:if>
</div>

<input type="hidden" name="boardNum" value="${search.boardNum}">
<input type="hidden" name="password" value="${search.password}">
<input type="hidden" name="pageNum" value="${search.pageNum}">
<input type="hidden" name="pageSize" value="${search.pageSize}">
<input type="hidden" name="keyword" value="${search.keyword}">
<input type="hidden" name="content" value="${search.content}">

<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="/js/board/m.modify.js"></script>
<script type="text/javascript" src="/js/m.base.js"></script>
<script src="https://www.google.com/recaptcha/api.js?render=6LeIHAIjAAAAAAmC25HnQn-fNgwQy-z0S4bpP9Nd"></script>
<script type="text/javascript" src="/js/common/captcha.js"></script>

<jsp:include page="/views/common/footer.jsp"/>
