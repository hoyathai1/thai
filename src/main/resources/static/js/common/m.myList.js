$(document).ready(function () {

});

function showDetail(id) {
    var body_id = "#body-" + id;
    var isDisplay = $(body_id).css("display");

    if (isDisplay == 'block') {
        $(body_id).css("display", "none");
    } else {
        $(".board-body").css("display", "none");
        $(body_id).css("display", "block");

        // 상세가져오기
        $.ajax({
            type : 'post',
            url : '/menu/myList/detail',
            headers : {
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : 'json',
            data : JSON.stringify({
                boardNum : id
            }),
            success : function (result) {
                $(body_id).html(result.contents);
            }
        });
    }
}

function movePage(page) {
    location.href = encodeURI("/menu/myList?page=" + page + "&pageSize=15");
}

function moveBoard(boardNum, category) {
    location.href = encodeURI("/board/view?boardNum=" + boardNum + "&type=all&best=&category=" + category + "&pageNum=0");
}

