let naverlogin;
fetch("naverLogin?"+location.href.split("naverlogin.html?")[1])
    .then(response=>{
        return response.text();
    }).then(data =>{
    naverlogin = data;
    if(naverlogin == 1){
        alert("sns 회원가입이 완료되었습니다.")
    }
    if(naverlogin!=-1)location.href="memberlist.html"
    else {
        alert("sns로그인 실패");
        location.href="/";
    }
}).catch(error=>{console.error(error)});