<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<form action="join" method="post">
  <div class="wrapper">
    <h2>JOIN</h2>
    <form method="post" action="login" id="login-form">
      <label>아이디</label>
      <input type="text" name="userid" placeholder="ID">
      <label>비밀번호</label>
      <input type="password" name="pass" placeholder="Password">
      <input type="password" name="passCheck" placeholder="Password Check">
      <div class="msg">*<p>비밀번호 체크해주세요.</p></div>
      <label>이름</label>
      <input type="text" name="name" placeholder="Name">

      <label>생년월일</label>
      <input type="date" id="birthdate" name="birthdate" required>

      <label>Email</label>
      <input type="text" name="email" placeholder="Email">
     <div class="button-wrap">
       <button type="button" onclick="sendVerificationCode()">인증 코드 전송</button>
     </div>
      <div id="verification-section">
        <label for="verification-code">인증 코드:</label>
        <input type="text" id="verification-code" name="verification-code">
        <div class="button-wrap">
          <button type="button" onclick="verifyCode()">인증 코드 확인</button>
        </div>
      </div>

      <input class="joinButton" type="submit" value="가입하기">
    </form>
    <a class="text-button" href="/">로그인 폼으로 돌아가기</a>
  </div>
</form>
</body>
</html>
