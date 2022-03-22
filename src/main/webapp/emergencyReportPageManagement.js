/**
 *
 */

(function() { // avoid variables ending up in the global scope
    window.addEventListener("load", (e) => {
            makeCall("GET", "EmergencyGetSalesData", null,
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
                                divs[5] = document.getElementById("insolventUsers");
                                divs[6] = document.getElementById("rejectedOrders");
                                divs[7] = document.getElementById("alerts");
                                divs[8] = document.getElementById("bestSellerOp");
                                for(let i=0; i<reportData.length; i++){
                                    expandData(divs[i], reportData[i]);
                                }
                                break;
                            default:
                                document.getElementById("errormessage").textContent += message;
                                break;
                        }
                    }
                }
            );
    });

})();

function expandData(div, arrayToExpand){
    arrayToExpand.forEach(row => showRow(div, row));
}

function showRow(row){
    let rowElem = document.createElement("h4");
    rowElem.innerHTML = row;
    div.appendChild(rowElem);
}