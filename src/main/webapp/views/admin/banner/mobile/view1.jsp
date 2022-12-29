<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<jsp:include page="/views/common/header.jsp"></jsp:include>--%>
<html>
    <header>
        <title>미리보기</title>
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
        <link rel="stylesheet" type="text/css" href="/css/m.common.css">
        <link rel="stylesheet"  type="text/css" href="/css/m.board.css">
        <link rel="stylesheet" type="text/css" href="/css/m.base.css">

    </header>

    <body>
        <div class="header-top">
            <div class="header-logo-font"><span>헬타이</span></div>
                <div class="header-info two">
                    <div>로그아웃</div>
                    <div class="menu">메뉴</div>
                </div>
            </div>

            <div class="header-category">
                <div class="category-log" style="background: url(&quot;/img/thai.png&quot;) 0% 0% / contain no-repeat transparent;"></div>
                <div class="category-name">미리보기</div>
                <div class="category-btn">
                    <button class="category-btn-register">글쓰기</button>
                </div>
            </div>

            <ul class="header-gnb">
                <li><a id="gnb-all">전체</a></li>
                <li><a id="gnb-best">인기글</a></li>
                <li><a id="gnb-board">여행</a></li>
                <li><a id="gnb-news">뉴스</a></li>
                <li><a id="gnb-etc">기타</a></li>
            </ul>


        <div class="container">
            <div class="board-list">
                <c:forEach begin="0" end="20" varStatus="status">
                    <div class="board">
                        <div class="content">
                            <div class="title">${status.count}번째 제목입니다.</div>
                            <div class="info">
                                <div><b>유저-${status.count}</b></div>
                                <div>12.23</div>
                                <div class="view-count">${status.count}</div>
                                <div class="likes-count">${status.count}</div>
                            </div>
                        </div>
                        <div class="reply">${status.count}</div>
                    </div>
                </c:forEach>
                <c:if test="${dto.topBanner.show eq true}">
                <div class="board listBanner" style="background: url(/banner/${dto.topBanner.fileName}) no-repeat; background-size: contain; background-position: center;" onclick="clickBanner('${dto.topBanner.link}')">
                </c:if>
                </div>
                <c:forEach begin="0" end="10" varStatus="status">
                    <div class="board">
                        <div class="content">
                            <div class="title">${status.count}번째 제목입니다.</div>
                            <div class="info">
                                <div><b>유저-${status.count}</b></div>
                                <div>12.23</div>
                                <div class="view-count">${status.count}</div>
                                <div class="likes-count">${status.count}</div>
                            </div>
                        </div>
                        <div class="reply">${status.count}</div>
                    </div>
                </c:forEach>
            </div>
            <div class="board-paging">
                <div class="page-prev">&lt;</div>
                <div class="page-number">
                    <div>1</div>
                    <div>2</div>
                    <div class="on">3</div>
                    <div>4</div>
                    <div>5</div>
                </div>
                <div class="page-next">&gt;</div>
                <div class="page-move">&gt;&gt;</div>
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

            <c:if test="${dto.bottomBanner.show eq true}">
                <div class="bottomBanner"><div class="bannerImg" style="background: url(/banner/${dto.bottomBanner.fileName}) no-repeat; background-size: contain; background-position: center;" onclick="clickBanner('${dto.bottomBanner.link}')"></div></div>
            </c:if>
        </div>
    </body>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
</body>
</html>