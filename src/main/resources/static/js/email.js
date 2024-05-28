document.addEventListener("DOMContentLoaded", function() {
    var sendVerificationCodeBtn = document.getElementById("sendVerificationCodeBtn");
    var verifyCodeBtn = document.getElementById("verifyCodeBtn");

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
        // 이메일을 서버로 보내고 서버에서 인증 코드를 생성하여 전송하는 로직을 추가합니다.
        // 여기에 AJAX 요청을 보내거나, Thymeleaf의 th:action을 사용하여 컨트롤러의 URL로 이동하는 등의 방법으로 서버로 요청을 보낼 수 있습니다.
        // EmailService를 사용하여 이메일을 보낼 수 있습니다.

        // 임시로 인증번호를 생성
        var verificationCode = Math.floor(100000 + Math.random() * 900000); // 6자리 랜덤 숫자 생성

        // 이메일을 서버로 전송하는 로직이 구현되면, 아래의 코드는 삭제하고 대신 서버에서 생성한 인증번호를 사용합니다.
        // var verificationCode = 서버에서 생성한 인증번호;

        // 버튼 텍스트 변경
        sendVerificationCodeBtn.innerText = "인증번호 확인";
        // 버튼 클릭 이벤트 추가
        sendVerificationCodeBtn.removeEventListener("click", arguments.callee);
        sendVerificationCodeBtn.addEventListener("click", function() {
            var enteredCode = document.getElementById("verificationCode").value;
            if (enteredCode === verificationCode.toString()) {
                alert("인증에 성공했습니다.");
                // 여기에 회원가입 등의 로직을 추가할 수 있습니다.
            } else {
                alert("인증번호가 올바르지 않습니다.");
            }
        });
        // "인증번호" 입력 필드와 "인증번호 확인" 버튼을 표시
        document.getElementById("verificationCode").style.display = "block";
        verifyCodeBtn.style.display = "inline-block";

        // 이메일로 인증번호를 보내는 로직을 추가할 수 있습니다.
        // 여기에 서버로 요청을 보내는 코드를 추가하여 이메일을 전송할 수 있습니다.
    }
});
