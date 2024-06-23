<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<div class="wrapper">
    <h2>Login</h2>
    <form method="post" action="login" id="login-form">
        <input type="text" name="userid" placeholder="Email">
        <input type="password" name="pass" placeholder="Password">

        <input type="submit" value="Login">
    </form>
    <a class="joinbutton" href="joinForm">회원가입</a>

    <div class="sns-login">
        <a href="" class="naverLogin" ><img src="/images/naver-sign-in.png"></a>
        <a href="" class="kakaoLogin" ><img src="/images/kakao-sign-in.png"></a>
    </div>
</div>
</body>
</html>
