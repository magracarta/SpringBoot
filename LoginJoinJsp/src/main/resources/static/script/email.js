
let time = 180;
let timer;
//이메일 체크
function sendVerificationCode(){
    let email = document.join.email.value;
    let form = new FormData();
    if(document.join.userid.value == "" || document.join.userid.value != document.join.idcheck.value){
        alert("id작성 및 중복확인 후 이메일인증을 부탁드립니다.");
        document.join.userid.focus();
        return false;
    }
    if(email ==""){
        alert("이메일을 먼저 작성해주세요.")
        document.join.email.focus();
        return false;
    }
    form.append("email",email)
    fetch("mailSend",{
        method:"POST",
        body:form
    }).then(response =>{
        if(response.ok)return response.text();
    }).then(data=>{
        if(data){
            alert(data);
            emailcheckedBox();
        }else{
            alert("이메일 형식을 다시 확인해주세요.");
            document.join.email.focus();
        }
    }).catch(date=>{
        alert("이메일 전송에 실패했습니다.");
        console.error(date);
    });
}

function emailcheckedBox(){

    document.querySelector("#verification-section").style.display="block";
    document.querySelector(".button-wrap button").classList.add("off");

    //타이머 제작
    let timerbox = document.createElement("div");
    timerbox.classList.add("timerbox");
    timerbox.innerHTML="인증 유효 시간 : &nbsp;<p id='remaining__min'></p>:<p id='remaining__sec'></p>";

    document.querySelector("#verification-section .button-wrap").prepend(timerbox);
    takeTarget();
}


const takeTarget = () => {
    const remainingMin = document.getElementById("remaining__min");
    const remainingSec = document.getElementById("remaining__sec");
    timer =  setInterval(function () {
        if (time > 0) { // >= 0 으로하면 -1까지 출력된다.
            time = time - 1; // 여기서 빼줘야 3분에서 3분 또 출력되지 않고, 바로 2분 59초로 넘어간다.
            let min = Math.floor(time / 60);
            let sec = String(time % 60).padStart(2, "0");
            remainingMin.innerText = min;
            remainingSec.innerText = sec;
            // time = time - 1
        } else {
            clearInterval(timer);
        }
    }, 1000);
};
function verifyCode(){
    let userid =document.join.userid.value;
    let useridcheck = document.join.idcheck.value;
    let vcode = document.join.vcode.value;
    let form = new FormData();
    form.append("userid",userid);
    form.append("vcode",vcode);
    fetch("/mailCheck",{
        method:"POST",
        body:form
    }).then(reponse=>{
        if(reponse.ok) return reponse.text();
    }).then(data =>{
        if(data == -1){
            alert("이메일 인증에 실패했습니다.다시 입력해주세요.");
        }else{
            alert("이메일 인증에 성공했습니다.");
            document.querySelector("#verification-section .button-wrap button").classList.add("off");
            clearInterval(timer);
            document.querySelector(".timerbox").style.display="none";
        }
    }).catch(date=>{
        console.error(date);
    });
}