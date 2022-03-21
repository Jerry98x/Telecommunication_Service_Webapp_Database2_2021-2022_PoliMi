/**
 *
 */

(function () {
    window.addEventListener("load", () => {
        if(sessionStorage.getItem("loggedEmployee") != null) {
            let employee = JSON.parse(sessionStorage.getItem("loggedEmployee"));
            let employeeInfo = document.createElement("h6");
            employeeInfo.innerHTML = "Logged in as <b>" + employee.employeeId + "</b>";
            document.getElementById("employee_login").appendChild(employeeInfo);

            document.getElementById("anchor_logout").hidden = false;
        }

    });

    window.addEventListener("load", () => {
        makeCall("GET", "Get...", null,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    let message = req.responseText;

                }
            }
        );
    });

    // window.addEventListener("load", () => {
    //     makeCall("GET", "Get...", null,
    //         function (req) {
    //             if (req.readyState === XMLHttpRequest.DONE) {
    //                 let message = req.responseText;
    //
    //             }
    //         }
    //     );
    // });

    document.getElementById("anchor_logout").addEventListener("click", () => {
        makeCall("GET", "Logout", null,
            function (req) {
                if(req.readyState === XMLHttpRequest.DONE) {
                    // let message = req.responseText;
                    if(req.status === 200) {
                        document.getElementById("anchor_logout").hidden = true;
                        window.location.href = "LandingPage.html";
                    }
                }
            }
        );
    });

})();