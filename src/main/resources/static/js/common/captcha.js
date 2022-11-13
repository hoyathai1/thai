function checkBot() {
    var captcha_token = "";
    grecaptcha.ready(function() {
        grecaptcha.execute('6LeIHAIjAAAAAAmC25HnQn-fNgwQy-z0S4bpP9Nd', {action: 'submit'}).then(function(token) {
            $.ajax({
                type : 'post',
                url : '/captcha',
                dataType : 'json',
                data : {
                    token : token
                },
                success : function (data) {
                    if (data.success == false) {
                        alert("비정상적인 접근입니다.")
                        return;
                    }
                },
                error : function () {
                    alert("비정상적인 접근입니다.")
                    return;
                }
            });
        });
    });
}