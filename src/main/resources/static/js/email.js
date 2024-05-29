document.addEventListener("DOMContentLoaded", function() {
    var sendVerificationCodeBtn = document.getElementById("sendVerificationCodeBtn");
    var verifyCodeBtn = document.getElementById("verifyCodeBtn");
    var verificationCodeInput = document.getElementById("verificationCode");

    sendVerificationCodeBtn.addEventListener("click", function() {
        var email = document.getElementById("email").value;

        // 이메일 주소 유효성 검사
        if (!email) {
            alert("이메일 주소를 입력하세요.");
            return;
        }

        // 서버로 이메일을 보내는 함수 호출
        sendVerificationCode(email);
    });

    function sendVerificationCode(email) {
        // AJAX 요청을 사용하여 서버로 이메일을 전송합니다.
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/member/sendVerificationCode", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert("이메일로 인증 코드를 전송했습니다. 이메일을 확인하세요.");
                } else {
                    alert("이메일 전송 중 오류가 발생했습니다.");
                }
            }
        };
        xhr.send(JSON.stringify({ email: email }));
    }
});
