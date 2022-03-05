/**
 * Landing page management
 */

//TODO impedire che un utente loggato possa tornare sulla LandingPage o che comunque
//TODO un eventuale secondo login/registrazione diano problemi
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
                                if(JSON.parse(message)[1] === true) {
                                    let user = JSON.parse(message)[0];
                                    sessionStorage.setItem('loggedUser', JSON.stringify(user));
                                    if(user.isInsolvent){
                                        sessionStorage.setItem('rejectedOrders', JSON.stringify(user.rejectedOrders));
                                    }
                                    //No need of JSON.parse because just null check
                                    if(sessionStorage.getItem('pendingOrder') != null) {
                                        window.location.href = "ConfirmationPage.html";
                                    }
                                    else {
                                        window.location.href = "HomePage.html";
                                    }
                                }
                                else {
                                    let employee = JSON.parse(message)[0];
                                    sessionStorage.setItem('loggedEmployee', JSON.stringify(employee));
                                    window.location.href = "EmployeeHomePage.html";
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