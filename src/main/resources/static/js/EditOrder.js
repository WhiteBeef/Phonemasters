firstName = document.getElementById('firstName')
phoneNumber = document.getElementById('phoneNumber')
deviceModel = document.getElementById('deviceModel')
originalComplaint = document.getElementById('originalComplaint')
realComplaint = document.getElementById('realComplaint')
originalPrice = document.getElementById('originalPrice')
agreedPrice = document.getElementById('agreedPrice')
repairState = document.getElementById('repairState')
paymentState = document.getElementById('paymentState')


firstName.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
phoneNumber.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
deviceModel.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
originalComplaint.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
realComplaint.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
originalPrice.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
agreedPrice.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
repairState.addEventListener("keypress", function (event) {
    onPressEnter(event)
});
paymentState.addEventListener("keypress", function (event) {
    onPressEnter(event)
});

function onPressEnter(event) {
    if (event.key === "Enter") {
        event.preventDefault();
        document.getElementById("submit-button").click();
    }
}

async function onClickSubmit(id) {
    if (!checkPhoneNumber() || !checkPrice(originalPrice) || !checkPrice(agreedPrice)) {
        return
    }

    await fetch(new URL('http://localhost:8080/api/v1/orders/update/' + id), {
        method: "PUT",
        credentials: 'include',
        body: JSON.stringify({
            "name": firstName.value,
            "phoneNumber": phoneNumber.value,
            "deviceModel": deviceModel.value,
            "originalComplaint": originalComplaint.value,
            "realComplaint": realComplaint.value,
            "originalPrice": originalPrice.value,
            "agreedPrice": agreedPrice.value,
            "repairState": repairState.value,
            "paymentState": paymentState.value
        }),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        }
    }).then(res => window.location.replace("http://localhost:8080/admin/orders/1"))
        .catch(err => console.log(err));
}

function checkPhoneNumber() {
    if (isNaN(phoneNumber.value)) {
        phoneNumber.setCustomValidity("Номер телефона должен быть числом")
        return false
    }
    if ((phoneNumber.value + '').length !== 11) {
        phoneNumber.setCustomValidity("Длина номера телефона неверная")
        return false
    }
    if (!(phoneNumber.value + '').startsWith("8")) {
        phoneNumber.setCustomValidity("Телефон должен начинаться с 8")
        return false
    }
    phoneNumber.setCustomValidity("")
    return true
}

function checkPrice(field) {
    if (isNaN(field.value)) {
        field.setCustomValidity("Номер телефона должен быть числом")
        return false
    }
    field.setCustomValidity("");
    return true
}

