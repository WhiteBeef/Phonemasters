firstName = document.getElementById('firstName')
lastName = document.getElementById('lastName')
email = document.getElementById('email')
password = document.getElementById('password')
userRole = document.getElementById('userRole')

firstName.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
lastName.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
email.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
password.addEventListener("keypress", function (event) {
    onPressEnter(event)
});

function onPressEnter(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        document.getElementById("submit-button").click();
    }
}

async function onClickSubmit() {
    await fetch(new URL("http://localhost:8080/api/v1/users/create"), {
        method: "POST",
        body: JSON.stringify({
            "firstName": firstName.value,
            "lastName": lastName.value,
            "email": email.value,
            "userRole": userRole.value,
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        }
    }).then(res => window.location.replace("http://localhost:8080/admin/users/1"));
}
