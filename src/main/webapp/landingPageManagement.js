/**
 * Landing page management
 */

//TODO impedire che un utente loggato possa tornare sulla LandingPage o che comunque
//TODO un eventuale secondo login/registrazione diano problemi
(function() { // avoid variables ending up in the global scope
    // sessionStorage.removeItem("loggedUserId");
    // sessionStorage.removeItem("loggedUser");
    // sessionStorage.removeItem("rejectedOrders");
    document.getElementById("submitLogin").addEventListener("click", (e) => {
        let form = e.target.closest("form");
        if (form.checkValidity()) {
            makeCall("POST", "CheckLogin", e.target.closest("form"),
                async function(req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        let message = req.responseText;
                        switch (req.status) {
                            case 200:
                                if(JSON.parse(message)[1] === true) {
                                    let user = JSON.parse(message)[0];
                                    sessionStorage.setItem("loggedUserId", user.userId);
                                    await (sessionStorage.getItem("loggedUserId") != null);
                                    //No need of JSON.parse because just null check
                                    if(sessionStorage.getItem("pendingOrder") != null) {
                                        let pendingOrder = JSON.parse(sessionStorage.getItem("pendingOrder"));
                                        pendingOrder.userId = sessionStorage.getItem("loggedUserId");
                                        sessionStorage.setItem("pendingOrder", JSON.stringify(pendingOrder));
                                        window.location.href = "ConfirmationPage.html";
                                    }
                                    else {
                                        window.location.href = "HomePage.html";
                                    }
                                }
                                else {
                                    let employee = JSON.parse(message)[0];
                                    sessionStorage.setItem("loggedEmployee", JSON.stringify(employee));
                                    window.location.href = "EmployeeHomePage.html";
                                }
                                break;
                            default:
                                alertCleaning(message, 1);
                                break;
                        }
                    }
                }
            );
        } else {
            form.reportValidity();
        }
    });


    document.getElementById("submitSignUp").addEventListener("click", (e) => {
        let form = e.target.closest("form");
        if (form.checkValidity()) {
            makeCall("POST", "RegisterUser", e.target.closest("form"),
                function(req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        let message = req.responseText;
                        switch(req.status) {
                            case 200:
                                alertCleaning(message, 0);
                                break;
                            default:
                                alertCleaning(message, 1);
                                break;
                        }
                    }
                }
            );
        }
        else {
            form.reportValidity();
        }
    });

    document.getElementById("continueAsGuest").addEventListener("click", (e) => {
        e.preventDefault();
        sessionStorage.removeItem("loggedUserId");
        sessionStorage.removeItem("loggedUser");
        sessionStorage.removeItem("rejectedOrders");
        window.location.href = "HomePage.html";
    });
})();

function alertCleaning(message, type) {
    let alert = document.getElementById("registration_alert");
    alert.innerHTML = "";

    if(alert.classList.contains("alert-success") && type === 1) {
        alert.classList.remove("alert-success");
        alert.classList.add("alert-danger");
    }
    else if(alert.classList.contains("alert-danger") && type === 0) {
        alert.classList.remove("alert-danger");
        alert.classList.add("alert-success");
    }

    let msg = document.createTextNode(message);
    alert.appendChild(msg);
    alert.style.visibility = "visible";
}
