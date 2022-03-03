/**
 * Landing page management
 */

(function() { // avoid variables ending up in the global scope
    //sessionStorage.setItem('logged_user', null);
    document.getElementById("submitLogin").addEventListener('click', (e) => {
        var form = e.target.closest("form");
        if (form.checkValidity()) {
            makeCall("POST", "CheckLogin", e.target.closest("form"),
                function(req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        var message = req.responseText;
                        switch (req.status) {
                            case 200:
                                let user = JSON.parse(message);
                                sessionStorage.setItem('loggedUser', JSON.stringify(user));//TODO single stringify of rejected orders
                                if(user.isInsolvent){
                                    sessionStorage.setItem('rejectedOrders', JSON.stringify(user.rejectedOrders));//TODO single stringify of optionalProducts
                                }
                                //No need of JSON.parse because just null check
                                if(sessionStorage.getItem('pendingOrder') != null) {
                                    window.location.href = "ConfirmationPage.html";
                                }
                                else {
                                    window.location.href = "HomePage.html";
                                }
                                break;
                            default:
                                alertCleaning(message);
                                break;
                        }
                    }
                }
            );
        } else {
            form.reportValidity();
        }
    });


    document.getElementById("submitSignUp").addEventListener('click', (e) => {
        var form = e.target.closest("form");
        if (form.checkValidity()) {
            makeCall("POST", "Registration", e.target.closest("form"),
                function(req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        let message = req.responseText;
                        alertCleaning(message);
                    }
                }
            );
        } else {
            form.reportValidity();
        }
    });

    document.getElementById("continueAsGuest").addEventListener('click', (e) => {
        sessionStorage.removeItem('loggedUser');
        sessionStorage.removeItem('rejectedOrders');
        window.location.href = "HomePage.html";
    });
})();

function alertCleaning(message) {
    let alert = document.getElementById("registration_alert");
    alert.innerHTML = "";

    if(alert.classList.contains("alert-success")) {
        alert.classList.remove("alert-success");
        alert.classList.add("alert-danger");
    }
    else if(alert.classList.contains("alert-danger")) {
        alert.classList.remove("alert-danger");
        alert.classList.add("alert-success");
    }

    let msg = document.createTextNode(message);
    alert.appendChild(msg);
    alert.style.visibility = "visible";
}