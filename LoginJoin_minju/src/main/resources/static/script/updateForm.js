fetchSessionData().then(data=>{
    updateForm(data);
}).catch( error =>{
    console.error(error+"데이터를 전송받지 못했습니다.")
});

//정보 확인후 데이터 뿌리기
function updateForm(data){
    console.log(data);
    document.update.userid.value=data.loginUser.userid;
    document.update.name.value=data.loginUser.name;
    document.update.name.value=data.loginUser.name;
    document.update.birthdate.value=data.loginUser.birth ? data.loginUser.birth.split("T")[0]: "";
    document.update.zip_code.value=data.loginUser.zip_code;
    document.update.email.value=data.loginUser.email;
    document.update.address1.value=data.loginUser.address1;
    document.update.address2.value=data.loginUser.address2;
    document.update.address3.value=data.loginUser.address3;
    document.update.beforeImage.value= data.loginUser.saveimage;

    if(data.loginUser.saveimage)document.querySelector(".img-box img").src = "/images/"+data.loginUser.saveimage;
    if(data.loginUser.provider != null) {
        document.update.pass.value = "snslogin";
        document.update.pass.readOnly  =true;
        document.update.passCheck.type = "hidden";
        document.update.passCheck.value = "snslogin";
    }
}

function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
}

document.update.addEventListener("submit",function (e){
    e.preventDefault();
    sendUpdate(this);
});

//조인 펫치
function sendUpdate(form){
    let formdata = new FormData(form);
    updateFormData(formdata).then(response => {
        submitClick(response);
        if(response.upate == 1){
            alert("회원 수정이 성공했습니다.");
            location.href="memberlist.html";
        }
    }).catch(error=>{
        console.error('Error:', error);
    });
}

function updateFormData(data){
    return new Promise((resolve, reject)=>{
        fetch("/update", {
            method : "POST",
            body: data
        }).then(resoponse =>{
            if(!resoponse.ok) throw new Error("네트 워크 오류");
            return resoponse.json();
        }).then(data=>{
            resolve(data);
        }).catch(error=>{
            reject(error);
        });
    });

}

function submitClick(response){
    if(response.message){
        let msg =  document.querySelector(".msg");
        msg.innerHTML="*"+response.message;
        msg.style.display="block";
    }
}

//이미지 버튼 클릭
document.querySelector(".imageinsert").addEventListener('click',(e)=>{
    document.querySelector(".proflie").click();
});



function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.querySelector('.img-box img').src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        if(document.update.beforeImage.value == "") document.querySelector('.img-box img').src = "/images/noimage.jpg";
        else ocument.querySelector('.img-box img').src = "/images/"+document.update.beforeImage.value;
    }
}
document.querySelector(".imagedelete").addEventListener('click',(e)=>{
    document.querySelector(".proflie").value="";
    document.querySelector('.img-box img').src = "/images/noimage.jpg";
});