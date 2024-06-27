let icheckText;
let popupp= document.querySelector(".icheckpopup p");
let popupbutton= document.querySelector(".icheckpopup .checkedok");

document.querySelector(".icheckpopup .close").addEventListener('click', popupClose);
document.querySelector(".idcheck").addEventListener('click', popupOpen);
popupbutton.addEventListener("click",()=>{
    document.join.idcheck.value =document.join.userid.value;
    popupClose();
});

function popupOpen(){
    if(document.join.userid.value==""){
        alert("아이디를 먼저 입력해주세요.");
        document.join.userid.focus();
        return;
    }
    document.querySelector(".icheckpopup").style.display="flex";
    idcheck();
}

function popupClose(){
    document.querySelector(".icheckpopup").style.display="none";
}

function idcheck(){
    let idString = document.join.userid.value;
    let formData = new FormData();
    formData.append("userid", idString);
    fetch("idcheck",{
        method:"POST",
        body:formData
    }).then(response => {
        return response.text();
    }).then(data=>{
        icheckText = data;

        if(icheckText != 1){
            popupp.innerText=idString+"은 이미 사용중인 아이디입니다.";
            popupbutton.style.display="none";
        }else{
            popupp.innerText=idString+"은 사용가능한 아이디 입니다.";
            popupbutton.style.display="block";
        }
    }).catch(error=>{
        console.error(error);
    })
}