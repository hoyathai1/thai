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
            <div class="view">
                <div class="view-header">
                    <div class="bookmark-ico off"></div>
                    <div class="title">
                        미리보기입니다.
                    </div>
                    <div class="title-sub">
                        <div class="author">작성자</div>
                        <div class="createDate">2023.12.12</div>
                    </div>
                </div>

                <div class="view-body">
                    <div class="board-info">
                        <div class="view-info-left">
                            <div class="view-count">67</div>
                            <div class="view-recom">4</div>
                            <div class="view-replay-cnt">0</div>
                        </div>
                        <div class="view-info-right">
                            <div class="share-ico"></div>
                            <div class="share">공유</div>
                        </div>
                    </div>
                    <div class="board-content">
                        <div><br></div><div>안녕하세요</div><div><br></div>
                    </div>

                    <div class="likes">
                        <button class="likes-btn" onclick="likesBtn()">

                            <div class="likes-cnt">7</div>
                            <div class="like-ico"></div>
                        </button>
                    </div>
                </div>
                <div class="btn-area">
                    <button class="btn two on">글쓰기</button>
                    <button class="btn two">목록보기</button>
                </div>

                <div class="comment">
                    <div class="comment-info">
                        <div class="fnt-bold">전체 댓글</div>
                        <div class="comment-cnt">[0]</div>
                        <button class="refresh-btn">
                            <div class="refresh-ico"></div>
                        </button>
                    </div>
                    <div class="comment-list">
                        <c:forEach begin="0" end="5" varStatus="status">
                            <div class="comment">
                                <div class="author">작성자</div>
                                <div class="content">미리보기 댓글</div>
                                <div class="createDate">12.16 23:32</div>
                            </div>
                        </c:forEach>
                        <c:if test="${dto.leftBanner.show eq true}">
                            <div class="comment listBanner" style="background: url(/banner/${dto.leftBanner.fileName}) no-repeat; background-size: contain; background-position: center;" onclick="clickBanner('${dto.leftBanner.link}')"></div>
                        </c:if>
                        <c:forEach begin="0" end="5" varStatus="status">
                            <div class="comment">
                                <div class="author">작성자</div>
                                <div class="content">미리보기 댓글</div>
                                <div class="createDate">12.16 23:32</div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="comment-form">
                        <div class="comment-content">
                            <textarea name="commentContent" maxlength="200" placeholder="내용"></textarea>
                        </div>
                        <div class="btn-area">
                            <button class="btn on small">등록</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="div-border"></div>
            <div class="board-list">
                <div class="board">
                    <div class="content">
                        <div class="title">미리보기 확인</div>
                        <div class="info">
                            <div><b>작성자</b></div>
                            <div>2022.12.18 18:33</div>
                            <div class="view-count">2</div>
                            <div class="likes-count">0</div>
                        </div>
                    </div>
                    <div class="reply">0</div>
                </div>
                <div class="board">
                    <div class="content">
                        <div class="title">미리보기 확인</div>
                        <div class="info">
                            <div><b>작성자</b></div>
                            <div>2022.12.18 18:33</div>
                            <div class="view-count">2</div>
                            <div class="likes-count">0</div>
                        </div>
                    </div>
                    <div class="reply">0</div>
                </div>
                <div class="board">
                    <div class="content">
                        <div class="title">미리보기 확인</div>
                        <div class="info">
                            <div><b>작성자</b></div>
                            <div>2022.12.18 18:33</div>
                            <div class="view-count">2</div>
                            <div class="likes-count">0</div>
                        </div>
                    </div>
                    <div class="reply">0</div>
                </div>
                <div class="board">
                    <div class="content">
                        <div class="title">미리보기 확인</div>
                        <div class="info">
                            <div><b>작성자</b></div>
                            <div>2022.12.18 18:33</div>
                            <div class="view-count">2</div>
                            <div class="likes-count">0</div>
                        </div>
                    </div>
                    <div class="reply">0</div>
                </div>
                <c:if test="${dto.rightBanner.show eq true}">
                    <div class="board listBanner" style="background: url(/banner/${dto.rightBanner.fileName}) no-repeat; background-size: contain; background-position: center;" onclick="clickBanner('${dto.rightBanner.link}')"></div>
                </c:if>
                <div class="board">
                    <div class="content">
                        <div class="title">미리보기 확인</div>
                        <div class="info">
                            <div><b>작성자</b></div>
                            <div>2022.12.18 18:33</div>
                            <div class="view-count">2</div>
                            <div class="likes-count">0</div>
                        </div>
                    </div>
                    <div class="reply">0</div>
                </div>
                <div class="board">
                    <div class="content">
                        <div class="title">미리보기 확인</div>
                        <div class="info">
                            <div><b>작성자</b></div>
                            <div>2022.12.18 18:33</div>
                            <div class="view-count">2</div>
                            <div class="likes-count">0</div>
                        </div>
                    </div>
                    <div class="reply">0</div>
                </div>
                <div class="board">
                    <div class="content">
                        <div class="title">미리보기 확인</div>
                        <div class="info">
                            <div><b>작성자</b></div>
                            <div>2022.12.18 18:33</div>
                            <div class="view-count">2</div>
                            <div class="likes-count">0</div>
                        </div>
                    </div>
                    <div class="reply">0</div>
                </div>
            </div>
            <div class="more-btn"><div class="btn_img"></div></div>

            <c:if test="${dto.bottomBanner.show eq true}">
                <div class="bottomBanner"><div class="bannerImg" style="background: url(/banner/${dto.bottomBanner.fileName}) no-repeat; background-size: contain; background-position: center;" onclick="clickBanner('${dto.bottomBanner.link}')"></div></div>
            </c:if>
        </div>
    </body>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
</body>
</html>