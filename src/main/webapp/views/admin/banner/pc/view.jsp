<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"  type="text/css" href="/css/pc/board/list.css">
<link rel="stylesheet"  type="text/css" href="/css/pc/board/base.css">
<html>
<head>
    <title></title>
</head>
<body>
    <div class="top-gnb">
        <div class="description">
            <h6>헬타이 - 태국 종합 커뮤니티</h6>
        </div>
        <div class="menu">
            <nav>
                <ul>
                    <li>프로필</li>
                    <li>내가쓴글</li>
                    <li>내 댓글</li>
                    <li>저장글</li>
                    <li>로그아웃</li>
                </ul>
            </nav>
        </div>
    </div>

    <div class="wrapper">
        <div class="right-sidebar">
            <div class="alert">
                <div class="user-info" onclick="goMyAccount()">
                    <strong>미리보기</strong>님
                </div>
                <div class="user-alert">
                    <div class="top">
                        <button>프로필</button>
                        <button>내가쓴글</button>
                        <button>내 댓글</button>
                    </div>
                    <div class="bottom">
                        <button>저장글</button>
                        <button>
                            <div class="alert-ico" id="alert-ico-id"></div>알림
                        </button>
                    </div>
                </div>
            </div>

            <c:if test="${dto.rightBanner.show eq true}">
                <div class="right-logo" style="background: url(/banner/${dto.rightBanner.fileName}) no-repeat;" onclick="clickBanner('${dto.rightBanner.link}')"></div>
            </c:if>
            <c:if test="${dto.rightBanner.show eq false}">
                <div class="right-logo"></div>
            </c:if>
        </div>

        <div class="left-sidebar">
            <c:if test="${dto.leftBanner.show eq true}">
                <div class="left-logo" style="background: url(/banner/${dto.leftBanner.fileName}) no-repeat;" onclick="clickBanner('${dto.leftBanner.link}')"></div>
            </c:if>
            <c:if test="${dto.leftBanner.show eq false}">
                <div class="left-logo"></div>
            </c:if>
        </div>

        <div class="container">
            <div class="category">
                <div class="list">
                    <div class="name">태국</div>
                    <div class="name">한국</div>
                    <div class="name">일본</div>
                </div>
            </div>

            <c:if test="${dto.topBanner.show eq true}">
                <div class="top-logo" style="background: url(/banner/${dto.topBanner.fileName}) no-repeat;" onclick="clickBanner('${dto.topBanner.link}')"></div>
            </c:if>

            <div class="subject">
                <div class="subject-area">
                    <div class="ico-thai"></div>
                    <h1>미리보기</h1>
                </div>

                <div class="search-area">
                    <input class="search-input" placeholder="검색어를 입력하세요.">
                    <button class="search-btn">검색</button>
                </div>
            </div>

            <div class="types">
                <div class="list">
                    <div class="type">전체</div>
                    <div class="type">인기</div>
                    <div class="type">여행</div>
                    <div class="type">뉴스</div>
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
                    <tr class="inform">
                        <td class="type">공지</td>
                        <td class="title">공지 제목 - 1</td>
                        <td class="author">관리자</td>
                        <td class="createDate">2022.01.01</td>
                        <td class="view"></td>
                        <td class="likes"></td>
                    </tr>
                    <tr class="inform">
                        <td class="type">공지</td>
                        <td class="title">공지 제목 - 2</td>
                        <td class="author">관리자</td>
                        <td class="createDate">2022.01.01</td>
                        <td class="view"></td>
                        <td class="likes"></td>
                    </tr>
                    <tr class="inform">
                        <td class="type">공지</td>
                        <td class="title">공지 제목 - 3</td>
                        <td class="author">관리자</td>
                        <td class="createDate">2022.01.01</td>
                        <td class="view"></td>
                        <td class="likes"></td>
                    </tr>
                    <tr class="inform">
                        <td class="type">공지</td>
                        <td class="title">공지 제목 - 4</td>
                        <td class="author">관리자</td>
                        <td class="createDate">2022.01.01</td>
                        <td class="view"></td>
                        <td class="likes"></td>
                    </tr>

                    <c:forEach begin="0" end="30" varStatus="status">
                        <tr>
                            <td class="type">여행</td>
                            <td class="title">${status.count} 번째 게시글 제목 입니다 <div class="comment-cnt">${status.count}</div></td>
                            <td class="author">유저-${status.count}</td>
                            <td class="createDate">2022.01.01</td>
                            <td class="view">${status.count}</td>
                            <td class="likes">${status.count}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="area">
                <div class="search-area">
                    <select class="search-select register-select">
                        <option value="all">
                            전체
                        </option>
                        <option value="title">
                            제목
                        </option>
                        <option value="content">
                            내용
                        </option>
                        <option value="author">
                            작성자
                        </option>
                    </select>
                    <input class="search-input" placeholder="검색어를 입력하세요." id="bottomSearch" value="">
                    <button class="search-btn">검색</button>
                </div>
                <div class="btn-area">
                    <button>글쓰기</button>
                </div>
            </div>
            <div class="paging">
                <ul>
                    <li class="page-prev">&laquo;</li>
                    <li class="page">1</li>
                    <li class="page">2</li>
                    <li class="page">3</li>
                    <li class="page active">4</li>
                    <li class="page">5</li>
                    <li class="page">6</li>
                    <li class="page">7</li>
                    <li class="page-next">&raquo;</li>
                </ul>
            </div>

            <c:if test="${dto.bottomBanner.show eq true}">
                <div class="bottom-logo" style="background: url(/banner/${dto.bottomBanner.fileName}) no-repeat;" onclick="clickBanner('${dto.bottomBanner.link}')"></div>
            </c:if>
        </div>
    </div> <%--.wrapper--%>

    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="/js/pc/board/list.js"></script>
    <script type="text/javascript" src="/js/pc/base.js"></script>
</body>
</html>
