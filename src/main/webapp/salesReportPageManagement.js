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
        makeCall("GET", "GetAggregatedData", null,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    let message = req.responseText;
                    switch (req.status) {
                        case 200:
                            let json = JSON.parse(message);
                            let salesData = JSON.parse(json)[0];
                            let UOAData = JSON.parse(json)[1];

                            let pur_sp_div = document.getElementById("pur_package_list");

                            salesData[0].forEach(sp => buildList(pur_sp_div, sp))

                            break;
                        case 401:
                            window.location.href = "LandingPage.html";
                            break;
                        default:
                            document.getElementById("errormessage").textContent += message;
                            break;
                    }
                }
            }
        );
    });

    // window.addEventListener("load", () => {
    //     makeCall("GET", "GetTotalPurchasesPerSpAndVp", null,
    //         function (req) {
    //             if (req.readyState === XMLHttpRequest.DONE) {
    //                 let message = req.responseText;
    //
    //             }
    //         }
    //     );
    // });
    //
    // window.addEventListener("load", () => {
    //     makeCall("GET", "GetTotalValuePerSp", null,
    //         function (req) {
    //             if (req.readyState === XMLHttpRequest.DONE) {
    //                 let message = req.responseText;
    //
    //             }
    //         }
    //     );
    // });
    //
    // window.addEventListener("load", () => {
    //     makeCall("GET", "GetTotalValuePerSpWithOp", null,
    //         function (req) {
    //             if (req.readyState === XMLHttpRequest.DONE) {
    //                 let message = req.responseText;
    //
    //             }
    //         }
    //     );
    // });
    //
    // window.addEventListener("load", () => {
    //     makeCall("GET", "GetAvgAmountOpPerSp", null,
    //         function (req) {
    //             if (req.readyState === XMLHttpRequest.DONE) {
    //                 let message = req.responseText;
    //
    //             }
    //         }
    //     );
    // });
    //
    // window.addEventListener("load", () => {
    //     makeCall("GET", "GetTotalPurchasesPerOp", null,
    //         function (req) {
    //             if (req.readyState === XMLHttpRequest.DONE) {
    //                 let message = req.responseText;
    //
    //             }
    //         }
    //     );
    // });

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


function buildList(div, sp) {
    let list_container = document.createElement("ul")
    list_container.classList.add("list-group");
    list_container.classList.add("list-group-horizontal");


    for (let prop in sp) {
        if (Object.prototype.hasOwnProperty.call(sp, prop)) {
            let li = document.createElement("li");
            li.classList.add("list-group-item");
            li.innerHTML = String(prop) + ": ";

            list_container.appendChild(li);
        }
    }

    div.appendChild(list_container);
}
