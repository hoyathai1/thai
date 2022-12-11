
function goCategory() {
    location.href="/admin/category/category";
}

function goType(id) {
    location.href="/admin/category/type?category=" + id;
}

function unuseBtn(id) {
    $.ajax({
        type : 'post',
        url : '/admin/category/category/unuse',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            category : id
        }),
        success : function () {
            location.href="/admin/category/category";
        }, error : function () {
        }
    });
}

function useBtn(id) {
    $.ajax({
        type : 'post',
        url : '/admin/category/category/use',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            category : id
        }),
        success : function () {
            location.href="/admin/category/category";
        }, error : function () {
        }
    });
}

function modifyBtn() {
    var id = $("#category_mod_id").val();
    var name = $("#category_mod_name").val();

    $.ajax({
        type : 'post',
        url : '/admin/category/category/modify',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            id : id,
            name : name
        }),
        success : function (data) {
            location.href="/admin/category/category";
        }, error : function () {
        }
    });
}

function registerBtn() {
    var id = $("#category_id").val();
    var name = $("#category_name").val();

    $.ajax({
        type : 'post',
        url : '/admin/category/category/register',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            id : id,
            name : name
        }),
        success : function (data) {
            location.href="/admin/category/category";
        }, error : function () {
        }
    });
}

function closeModal() {
    $(".modal").css("display", "none");
    $(".modal").removeClass("show");
}

function categoryModifyModal(id) {

    $.ajax({
        type : 'post',
        url : '/admin/category/category/detail',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            category : id
        }),
        success : function (data) {
            $("#category_mod_id").val(data.category.id);
            $("#category_mod_name").val(data.category.name);
        }, error : function () {
        }
    });


    $("#category-mod-modal").data("id", id);
    $("#category-mod-modal").css("display", "block");
    $("#category-mod-modal").addClass("show");
}

function categoryRegisterModal() {

    $("#category-register-modal").css("display", "block");
    $("#category-register-modal").addClass("show");
}