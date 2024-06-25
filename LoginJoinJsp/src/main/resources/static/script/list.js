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