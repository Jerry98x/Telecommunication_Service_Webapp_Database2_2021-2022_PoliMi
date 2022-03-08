/**
 *
 */
function makeCall(method, url, formElement, cback, reset = true) {
    var req = new XMLHttpRequest(); // visible by closure
    req.onreadystatechange = function() {
        cback(req)
    }; // closure
    req.open(method, url);
    if (formElement == null) {
        req.send();
    } else {
        req.send(new FormData(formElement));
    }
    if (formElement !== null && reset === true) {
        formElement.reset();
    }
}

(function () {
    window.addEventListener("load", () => {
        makeCall("GET", "HomePageLoading", null,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    var message = req.responseText;
                    switch (req.status) {
                        case 200:
                            // let sps = JSON.parse(message);
                            //
                            // let anchor = document.createDocumentFragment();
                            // anchor.appendChild(document.createElement("br"));
                            // sps.forEach(sp => showServicePackage(sp, anchor));
                            // if(sessionStorage.getItem('loggedUser') != null){
                            //     let user = JSON.parse(sessionStorage.getItem('loggedUser'));
                            //     let userInfo = document.createElement("h6");
                            //     userInfo.innerHTML = "Logged in as <b>" + user.username + "</b>";
                            //     document.getElementById("user_login").appendChild(userInfo);
                            //     if(sessionStorage.getItem('rejectedOrders') != null){
                            //         let roText = document.createElement("h6");
                            //         roText.innerHTML = "Rejected orders";
                            //         anchor.appendChild(roText)
                            //         JSON.parse(sessionStorage.getItem('rejectedOrders')).forEach(ro => showRejectedOrder(ro, anchor));
                            //     }
                            // }
                            //
                            // document.getElementById("sp_column").appendChild(anchor);
                            break;
                        case 400: // bad request
                            document.getElementById("errormessage").textContent = message;
                            break;
                        case 401: // unauthorized
                            document.getElementById("errormessage").textContent = message;
                            break;
                        case 500: // server error
                            document.getElementById("errormessage").textContent = message;
                            break;
                    }
                }
            }
        );
    });

})();