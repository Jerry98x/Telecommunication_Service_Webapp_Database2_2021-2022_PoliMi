/**
 *
 */

(function() { // avoid variables ending up in the global scope
    window.addEventListener("load", (e) => {
            makeCall("GET", "GetAggregatedData", null,
                function(req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        let message = req.responseText;
                        switch (req.status) {
                            case 200:
                                let reportData = JSON.parse(message);
                                let divs = [];
                                divs[0] = document.getElementById("purchPerSp");
                                divs[1] = document.getElementById("purchPerSpVp");
                                divs[2] = document.getElementById("valuePerSp");
                                divs[3] = document.getElementById("valuePerSpWithOp");
                                divs[4] = document.getElementById("avgNumOpPerSp");
                                divs[5] = document.getElementById("bestSellerOp");
                                divs[6] = document.getElementById("insolventUsers");
                                divs[7] = document.getElementById("rejectedOrders");
                                divs[8] = document.getElementById("alerts");
                                for(let i=0; i<reportData.length; i++){
                                    expandData(divs[i], reportData[i]);
                                }
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

    window.addEventListener("load", () => {
        if(sessionStorage.getItem("loggedEmployee") != null) {
            let employee = JSON.parse(sessionStorage.getItem("loggedEmployee"));
            let employeeInfo = document.createElement("h6");
            employeeInfo.innerHTML = "Logged in as <b>" + employee.employeeId + "</b>";
            document.getElementById("employee_login").appendChild(employeeInfo);

            document.getElementById("anchor_logout").hidden = false;
        }

    });

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

function expandData(div, arrayToExpand){
    if(arrayToExpand.size !== 0) {
        arrayToExpand.forEach(row => showRow(div, row));
    } else {
        showRow(div, "No element to show.");
    }
}

function showRow(div, row){
    let rowElem = document.createElement("h6");
    rowElem.innerHTML = row;
    div.appendChild(rowElem);
}