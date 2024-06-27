
let kakaologin;
fetch("kakaoLogin?"+location.href.split("kakaoLogin.html?")[1])
.then(response=>{
    return response.text();
}).then(data =>{
    kakaologin = data;
    if(kakaologin == 1){
        alert("sns 회원가입이 완료되었습니다.")
    }
    if(kakaologin!=-0)location.href="memberlist.html"
    else {
        alert("sns로그인 실패");
        location.href="/";
    }
}).catch(error=>{console.error(error)});