/**
 *
 */

(async function () {
    var today = new Date(Date());
    document.getElementById("startDate").min = today.toISOString().split('T')[0];
    var maxDate = new Date();
    maxDate.setFullYear(today.getFullYear() + 1);
    document.getElementById("startDate").max = maxDate.toISOString().split('T')[0];
    document.getElementById("startDate").addEventListener("change", (event => {
        event.preventDefault();
        if (sessionStorage.getItem("loggedUser") != null) {
            document.getElementById("successfulPaymentBtn").disabled = false;
            document.getElementById("failingPaymentBtn").disabled = false;
        }
    }))
    if (sessionStorage.getItem("loggedUser") == null) {
        document.getElementById("errormessage").innerHTML = "You need to be logged in to complete a payment";
        //disable payment buttons
        document.getElementById("successfulPaymentBtn").disabled = true;
        document.getElementById("failingPaymentBtn").disabled = true;
        //add event listener to login/signup buttons
        document.getElementById("loginBtn").addEventListener("click", (event) => notLoggedRedirect(event));
        document.getElementById("signUpBtn").addEventListener("click", (event) => notLoggedRedirect(event));
        //show login/signup buttons
        document.getElementById("loginBtn").hidden = false;
        document.getElementById("signUpBtn").hidden = false;
    }
    if (sessionStorage.getItem("rejectedOrderId") != null && sessionStorage.getItem("loggedUser") != null) {
        window.addEventListener("load", () => {
            let rejectedOrderForm = document.createElement("form");
            rejectedOrderForm.name = "roForm";
            let input = document.createElement("input");
            input.name = "rejectedOrderId";
            input.value = sessionStorage.getItem("rejectedOrderId");
            rejectedOrderForm.appendChild(input);
            makeCall("POST", "GetRejectedOrderToComplete", rejectedOrderForm,
                async function (req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        var message = req.responseText;
                        switch (req.status) {
                            case 200:
                                let rejectedOrder = JSON.parse(message)[0];
                                let servicePackageToBuy = JSON.parse(message)[1];
                                let pendingOrder = {};
                                pendingOrder.orderId = rejectedOrder.orderId;
                                pendingOrder.totalCost = rejectedOrder.totalCost_euro;
                                pendingOrder.startDate = null;
                                pendingOrder.valid = rejectedOrder.valid;
                                pendingOrder.userId = rejectedOrder.userId;
                                pendingOrder.servicePackageId = servicePackageToBuy.servicePackageId;
                                pendingOrder.servicePackageName = servicePackageToBuy.name;
                                pendingOrder.servicesDescriptions = servicePackageToBuy.servicesDescriptions;
                                pendingOrder.chosenValidityPeriod = rejectedOrder.chosenValidityPeriod;
                                pendingOrder.chosenOptionalProducts = rejectedOrder.chosenOptionalProducts;
                                sessionStorage.setItem("pendingOrder",JSON.stringify(pendingOrder));
                                await (sessionStorage.getItem("pendingOrder") != null);
                                sessionStorage.removeItem("rejectedOrderId");
                                showOrder();
                                break;
                            case 401:
                                window.location.href = "LandingPage.html";
                                break;
                            default:
                                document.getElementById("errormessage").textContent = message;
                        }
                    }
                }
            );
        })
    } else if (sessionStorage.getItem("loggedUser") != null){
        showOrder();
    }
})();

function notLoggedRedirect(event){
    event.preventDefault();
    window.location.href = "LandingPage.html";
}

function showOrder(){
    let spName = JSON.parse(sessionStorage.getItem("pendingOrder")).servicePackageName;
    let sds = JSON.parse(sessionStorage.getItem("pendingOrder")).servicesDescriptions;
    let tot = JSON.parse(sessionStorage.getItem("pendingOrder")).totalCost;
    let cvp = JSON.parse(sessionStorage.getItem("pendingOrder")).chosenValidityPeriod;
    let cops = JSON.parse(sessionStorage.getItem("pendingOrder")).chosenOptionalProducts;

    document.getElementById("packageName").innerHTML = spName;
    sds.forEach(sd => showServiceDescription(sd));
    if (cops != null && cops.length > 0) {
        document.getElementById("optionalProductsDiv").hidden = false;
        cops.forEach(cop => showOptionalProduct(cop));
    }
    document.getElementById("validityPeriodDiv").innerHTML = cvp.monthsOfValidity + " months at " + cvp.monthlyFee_euro + "€/month";
    document.getElementById("totalCost").innerHTML = tot;

    document.getElementById("successfulPaymentBtn").addEventListener("click", (event) => sendPayment(event, true));
    document.getElementById("failingPaymentBtn").addEventListener("click", (event) => sendPayment(event, false));
}

function showServiceDescription(serviceDescription){
    let service = document.createElement("p");
    service.innerHTML = serviceDescription;
    document.getElementById("servicesDiv").appendChild(service);
}

function showOptionalProduct(optionalProduct){
    if(optionalProduct != null) {
        let singleProductDiv = document.createElement("div");
        let opLabel = document.createElement("label");
        opLabel.innerHTML = optionalProduct.name + ": " + optionalProduct.monthlyFee_euro + "€/month";
        singleProductDiv.appendChild(opLabel);
        document.getElementById("optionalProductsDiv").appendChild(singleProductDiv);
    }
}

function sendPayment(event, isSuccessful) {
    event.preventDefault();
    let newOrderForm = document.createElement("form");
    let newOrder = JSON.parse(sessionStorage.getItem("pendingOrder"));
    newOrder.valid = isSuccessful ? 1 : 0;
    if(newOrder.startDate == null) {
        newOrder.startDate = document.getElementById("startDate").value;
    }
    buildInputOrder(newOrderForm, newOrder);
    makeCall("POST", "ManageOrder", newOrderForm,
        function (req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        sessionStorage.removeItem("pendingOrder");
                        window.location.href = "HomePage.html";
                        break;
                    case 401:
                        window.location.href = "LandingPage.html";
                        break;
                    default:
                        document.getElementById("errormessage").textContent += message;
                        break;
                }
            }
        });
}

function buildInputOrder(form, order){
    //orderId
    let orderId = document.createElement("input");
    orderId.name = "orderId";
    if(order.orderId != null) {
        orderId.value = order.orderId;
    } else {
        orderId.value = -1;
    }
    form.appendChild(orderId);
    //tot
    let tot = document.createElement("input");
    tot.name = "totalCost";
    tot.value = order.totalCost;
    form.appendChild(tot);
    //startDate
    let startDate = document.createElement("input");
    startDate.name = "startDate";
    startDate.value = order.startDate;
    form.appendChild(startDate);
    //valid
    let valid = document.createElement("input");
    valid.name = "valid";
    valid.value = order.valid;
    form.appendChild(valid);
    //userId
    let userId = document.createElement("input");
    userId.name = "userId";
    userId.value = order.userId;
    form.appendChild(userId);
    //spId
    let spId = document.createElement("input");
    spId.name = "servicePackageId";
    spId.value = order.servicePackageId;
    form.appendChild(spId);
    //[]chosenOPIds
    let opIds = document.createElement("input");
    opIds.name = "chosenOptionalProductsIds";
    opIds.value = "";
    order.chosenOptionalProducts.forEach(op => {
        if(op != null) {
            opIds.value += op.optionalProductId + "$";
        }
    })
    form.appendChild(opIds);
    //chosenVPId
    let vpId = document.createElement("input");
    vpId.name = "chosenValidityPeriodId";
    vpId.value = order.chosenValidityPeriod.validityPeriodId;
    form.appendChild(vpId);
}