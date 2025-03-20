const baseURL = "http://localhost:8080";

//delete contact

async function deleteQuestion(questionBankId,id){
    Swal.fire({
        title: "Do you want to delete the Question?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            if (result.isConfirmed) {
                console.log(questionBankId)
                console.log(id)
                const url=`${baseURL}/teacher/QB/`+`${questionBankId}`+`/`+`${id}`+`/delete`;
                window.location.replace(url);
            }
        }
    });
}