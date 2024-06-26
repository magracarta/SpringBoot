let joinform = document.join;
let joindata;

joinform.addEventListener("submit",function (e){
    e.preventDefault();
    sendJoinPetch(this);
});

function sendJoinPetch(form){
    let formdata = new FormData(form);
    sendJoinFormData(formdata).then(response => {
        joindata = response;
        submitClick();
    }).catch(error=>{
        console.error('Error:', error);
    });
}


function sendJoinFormData(data){
    return new Promise((resolve, reject)=>{
        fetch("/join", {
            method : "POST",
            body: data
        }).then(resoponse =>{
            if(!resoponse.ok) throw new Error("네트 워크 오류");
            return resoponse.json();
        }).then(data=>{
            resolve(data);
            if(data.joinst){
                alert("회원가입에 성공했습니다.");
                location.href="/";
            }
        }).catch(error=>{
            reject(error);
        });
    });

}

//이미지 버튼 클릭
document.querySelector(".imageinsert").addEventListener('click',(e)=>{
    document.querySelector(".proflie").click();
});

function submitClick(){
    if(joindata.message){
        let msg =  document.querySelector(".msg");
        msg.innerHTML="*"+joindata.message;
        msg.style.display="block";
    }
}




function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.querySelector('.img-box img').src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        document.querySelector('.img-box img').src = "/images/noimage.jpg";
    }
}
document.querySelector(".imagedelete").addEventListener('click',(e)=>{
    document.querySelector(".proflie").value="";
    document.querySelector('.img-box img').src = "/images/noimage.jpg";
});



