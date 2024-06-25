let loginuser;
fetchSessionData();
function fetchSessionData() {
    fetch("/session",{
        method:'GET'
    }).then(reponse=>{
        if(!reponse.ok) throw new Error("네트워크 오류"+reponse.statusText);
        return reponse.json();
    }).then(data=>{
        loginuser = data;

    }).catch(data =>{
        console.error(error);
    });

}

setTimeout(()=>{alreadLogin()},500);
function alreadLogin(){
    if(loginuser.loginUser != null && location.href.indexOf("index.html") > 1) {
        alert("이미 로그인 하셨습니다.");
        location.href="memberlist.html";
    }
}
