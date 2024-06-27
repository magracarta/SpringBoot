
//본인 정보 뿌리기
fetchSessionData().then(data=>{
    userinfo();
}).catch(error=>{
    console.error("데이터를 전송받지 못했습니다.")
});



document.querySelector(".logout").addEventListener("click",()=>{
    if(confirm("정말로 로그아웃 하시겠습니까?")){
        fetch("/logout").then(reponse=>{
            return reponse.text();
        }).then(data=>{
            location.href="index.html";
        }).catch(date=>{
            console.error(date);
        });
    }
});

let memberlist;
fetch("/memberlist").then(reponse =>{
    return reponse.json();
}).then(data =>{
    memberlist = data;
    console.log("멤버리스트 호출 완료~");
    memberList();
}).catch(data=>{
    console.error("서버오류~ memberlist확인해보세요~");
});

//멤버리스트 뿌리기
function memberList(){
    let div = document.querySelector(".memberlist-wrap ul");
    memberlist.memberList.forEach((elem)=>{
        div.innerHTML+=`<li>
                        <div class="img">${elem.saveimage == null ? '<img src="/images/noimage.jpg">' : 
            '<img src="/images/'+elem.saveimage+'">'}</div>
                        <div class="userid">${elem.userid}</div>
                        <div class="name">${elem.name}</div>
                        <div class="loginrole">${elem.provider == null ? "일반" : elem.provider}</div>
                        <div class="email">${elem.email == null ? "SNS 로그인" : elem.email}</div>
                </li>`;
    });

}

function userinfo(){
    document.querySelector(".username").innerHTML=loginuser.loginUser.name;
    if(loginuser.loginUser.saveimage != null)document.querySelector(".userInfo img").src="/images/"+loginuser.loginUser.saveimage;

}
//회원 삭제
function deleteFn(){
    let userid= loginuser.loginUser.userid;
    if(confirm("정말로 탈퇴하시겠습니까?")){
        fetch("/delete?userid="+userid).then(reponse=>{
            return reponse.text();
        }).then(data=>{

        }).catch()
    }
}