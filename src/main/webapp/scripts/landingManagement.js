/**
 * Landing page management
 */

(function() { // avoid variables ending up in the global scope

    document.getElementById("LoginPageButton").addEventListener('click', (e) => {
            makeCall("GET", 'RedirectToLoginPage', null,
                function(req) {
                    if (req.readyState == XMLHttpRequest.DONE) {
                        var message = req.responseText;
                        switch (req.status) {
                            case 200:
                                window.location.href = "LoginPage.html";
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