document.addEventListener("DOMContentLoaded", function() {
    var signupBtn = document.getElementById("signupBtn");

    signupBtn.addEventListener("click", function() {
        var username = document.getElementById("username").value;
        var phoneNumber = document.getElementById("phoneNumber").value;
        var nickname = document.getElementById("nickname").value;
        var password = document.getElementById("password").value;
        var email = document.getElementById("email").value;
        var age = document.getElementById("age").value;
        var gender = document.querySelector('input[name="gender"]:checked').value;
        var region = document.getElementById("region").value;
        var favoriteFood = document.getElementById("favoriteFood").value;

        // 여기에서 서버로 회원가입 데이터를 전송하는 로직을 추가할 수 있습니다.
        // 서버로 데이터를 전송하기 위해 AJAX 요청을 보내거나, Thymeleaf의 th:action을 사용하여 컨트롤러의 URL로 이동하는 등의 방법을 사용할 수 있습니다.
    });
});
