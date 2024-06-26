//통신
let logindate;
const form = document.loginform;
form.addEventListener('submit',(e)=>{
    e.preventDefault();
    loginCheck();
});

function loginCheck(){
    let formDate = new FormData(form);
    let msg = document.querySelector(".msg");
    sendFormData(formDate).then(response =>{
        logindate = response.loginCheck;
        fetchSessionData();
        msg.style.display = "block";
        if(logindate == -2) msg.innerHTML="계정이 없습니다.";
        else if(logindate == -1) msg.innerHTML="비밀번호가 틀립니다.";
        else if(logindate == 1) location.href="memberlist.html";
    }).catch(error=>{
        console.error('Error:', error);
    });
}

function sendFormData(formDate){
    return new Promise((resolve, reject)=>{
       fetch("/login",{
           method:'POST',
           body:formDate
       })
       .then(response =>{
           if(!response.ok) throw new Error('Network response was not ok ' + response.statusText);
           return response.json();
       }).then(data=>{
           resolve(data);
       }).then(error=>{
           reject(error);
       });
    });
}

