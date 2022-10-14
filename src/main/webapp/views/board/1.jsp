<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=2.0, minimum-scale=1.0, user-scalable=no">
</head>
<body>
<link rel="stylesheet"  type="text/css" href="/css/board.css">
<style>
    .header {

    }

    .container {
        padding-left: 2px;
        overflow: hidden;
    }
    .board-list {

    }
    /* 게시판 정보 (제목, 작성자, 날짜, 뷰) */
    .board-list > .board {
        display: flex;
        border-bottom: 1px solid #bcbcbc;
        padding: 4px;
    }
    .board-list > .board > .content {
        width: 90%
    }
    .board-list > .board > .content > .title {
        font-size: 14px;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }
    .board-list > .board > .content > .info {
        font-size: 12px;
        color: #7c7874;
        padding-top: 4px;
    }
    .board-list > .board > .content > .info > div {
        float: left;
        margin-right: 2%;
    }
    .board-list > .board > .content > .info > div.view-count:before {
        content: '조회 ';
    }
    /* 댓글 정보 ( 댓글 개수) */
    .board-list > .board > .reply {
        width: 10%;
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #f9f9f6;
        font-size: 8px;
        color: #b62713;
    }
    .more-btn {
    }
    .more-btn > .btn_img {
        background: url("/img/arrow-down.png") no-repeat;
        background-size: contain;
        width: 32px;
        height: 64px;
        margin:0 auto;  /*중앙정렬*/
    }
</style>
    <div class="container">
        <div class="board-list">
            <div class="board">
                <div class="content">
                    <div class="title">
                        여행을 마무리하며
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">3</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        빠따야 전립선 마사지 지대로 하는곳 없냐?
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">3</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        방콕 초보의 첫 후기
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">15</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        오늘 클럽 한다고 하네영 정보추
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">2</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        오늘 아고고 밤10시에 연다니까
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">3</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        arte hotel 추천한 형님들때매 쑥쓰럽습니다
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">5</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        파타야 피우팬 두번째 도전자 입니다
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">24</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        오늘 01시에 법사비즈니스 11일 좌석2개 남아서 이거 남을줄 알았음
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">0</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        푸잉한테 조련당하는 느낌입니다..
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">0</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        테메 특파원 보고드립니다
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">12</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        아고고 탐방 갈건데 ㅊㅊ점여
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">2</div>
            </div>
            <div class="board">
                <div class="content">
                    <div class="title">
                        형틀 방타이할때 숙소
                    </div>
                    <div class="info">
                        <div>박푸박</div>
                        <div>22:00</div>
                        <div class="view-count">49</div>
                    </div>
                </div>
                <div class="reply">7</div>
            </div>
        </div>
        <%--더보기 버튼--%>
        <div class="more-btn">
            <div class="btn_img"></div>
        </div>

    </div><%--.container--%>

</body>
</html>
