let loginuser;

function fetchSessionData() {
    return new Promise((resolve, reject)=>{
        fetch("/session",{
        method:'GET'
        }).then(reponse=>{
            if(!reponse.ok) throw new Error("네트워크 오류"+reponse.statusText);
            return reponse.json();
        }).then(data=>{
            resolve(data);
        }).catch(data =>{
            reject(data);
         });
    });
}

alreadLogin();
function alreadLogin(){
    fetchSessionData().then(data=>{
        loginuser = data;
        if(loginuser.loginUser != null && location.href.indexOf("index.html") > 1) {
            alert("이미 로그인 하셨습니다.");
            location.href="memberlist.html";
        }else if(loginuser.loginUser == null && location.href.indexOf("index.html") < 0 && location.href.indexOf("joinForm.html") < 0){
            alert("로그인 후 이용해주세요.");
            location.href="/";
        }
    }).catch(data=>{
        console.error(data);
    })

}
