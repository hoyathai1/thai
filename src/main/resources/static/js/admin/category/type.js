function goType() {
    var category = $("input[name=category]").val();
    location.href="/admin/category/type?category=" + category;
}

function unuseBtn(id) {
    $.ajax({
        type : 'post',
        url : '/admin/category/type/unuse',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            id : id
        }),
        success : function () {
            goType();
        }, error : function () {
        }
    });
}

function useBtn(id) {
    $.ajax({
        type : 'post',
        url : '/admin/category/type/use',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'text',
        data : JSON.stringify({
            id : id
        }),
        success : function () {
            goType();
        }, error : function () {
        }
    });
}

function modifyBtn() {
    var id = $("#type_mod_id").val();
    var name = $("#type_mod_name").val();
    var type = $("#type_mod_type").val();
    var orderBy = $("#type_mod_order").val();

    $.ajax({
        type : 'post',
        url : '/admin/category/type/modify',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            id : id,
            name : name,
            type : type,
            orderBy : orderBy
        }),
        success : function (data) {
            goType();
        }, error : function () {
        }
    });
}

function registerBtn() {
    var categoryId = $("input[name=category]").val();
    var name = $("#type_name").val();
    var type = $("#type_type").val();
    var orderBy = $("#type_order").val();

    $.ajax({
        type : 'post',
        url : '/admin/category/type/register',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            categoryId : categoryId,
            name : name,
            type : type,
            orderBy : orderBy
        }),
        success : function (data) {
            goType();
        }, error : function () {
        }
    });
}

function closeModal() {
    $(".modal").css("display", "none");
    $(".modal").removeClass("show");
}

function typeModifyModal(id) {
    $.ajax({
        type : 'post',
        url : '/admin/category/type/detail',
        headers : {
            "Content-Type" : "application/json",
            "X-HTTP-Method-Override" : "POST"
        },
        dataType : 'json',
        data : JSON.stringify({
            id : id
        }),
        success : function (data) {
            $("#type_mod_id").val(data.type.id);
            $("#type_mod_type").val(data.type.type);
            $("#type_mod_name").val(data.type.name);
            $("#type_mod_order").val(data.type.orderBy);
        }, error : function () {
        }
    });


    $("#type-mod-modal").data("id", id);
    $("#type-mod-modal").css("display", "block");
    $("#type-mod-modal").addClass("show");
}

function typeRegisterModal() {

    $("#type-register-modal").css("display", "block");
    $("#type-register-modal").addClass("show");
}
