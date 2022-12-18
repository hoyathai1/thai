
function goCategory() {
    location.href="/admin/category/category";
}

function categorySessionInit() {

    $.ajax({
        type : 'post',
        url : '/admin/category/category/init',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
        }),
        success : function () {
            alert("카테고리 세션 초기화 됬습니다.");
        }, error : function () {
        }
    });

}

function typeSessionInit() {

    $.ajax({
        type : 'post',
        url : '/admin/category/type/init',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
        }),
        success : function () {
            alert("타입 세션 초기화 됬습니다.");
        }, error : function () {
        }
    });

}