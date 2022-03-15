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
    if(sessionStorage.getItem("loggedEmployee") != null) {
        let employee = JSON.parse(sessionStorage.getItem("loggedEmployee"));
        let employeeInfo = document.createElement("h6");
        employeeInfo.innerHTML = "Logged in as <b>" + employee.employeeId + "</b>";
        document.getElementById("employee_login").appendChild(employeeInfo);
    }

    document.getElementById("sp").hidden = true;
    document.getElementById("op").hidden = true;

    let spForm = document.getElementById("spCreationForm");
    //let opForm = document.getElementById("opCreationForm");

    window.addEventListener("load", () => {
        makeCall("GET", "GetServices", null,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    let message = req.responseText;
                    switch (req.status) {
                        case 200:
                            let t_ser = JSON.parse(message);
                            let formContainer_serv = document.getElementById("fc_serv");

                            let p = document.createElement("p");
                            let b = document.createElement("b");
                            p.textContent = "Available services:";
                            b.appendChild(p);
                            formContainer_serv.appendChild(b);

                            t_ser.forEach(ts => buildForm_SP_services(formContainer_serv, ts));
                            formContainer_serv.appendChild(document.createElement("br"));

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
        makeCall("GET", "GetOptionalProducts", null,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    let message = req.responseText;
                    switch (req.status) {
                        case 200:
                            let opt_prod = JSON.parse(message);
                            let formContainer_optprod = document.getElementById("fc_optprod");

                            let p = document.createElement("p");
                            let b = document.createElement("b");
                            p.textContent = "Available optional products:";
                            b.appendChild(p);
                            formContainer_optprod.appendChild(b);

                            opt_prod.forEach(op => buildForm_SP_optionalProducts(formContainer_optprod, op));
                            formContainer_optprod.appendChild(document.createElement("br"));

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
        makeCall("GET", "GetValidityPeriods", null,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    let message = req.responseText;
                    switch (req.status) {
                        case 200:
                            let val_per = JSON.parse(message);
                            let formContainer_valper = document.getElementById("fc_valper");
                            let formContainer_but = document.getElementById("fc_but");

                            let p = document.createElement("p");
                            let b = document.createElement("b");
                            p.textContent = "Available validity periods:";
                            b.appendChild(p);
                            formContainer_valper.appendChild(b);

                            val_per.forEach(vp => buildForm_SP_validityPeriods(formContainer_valper, vp));
                            formContainer_but.appendChild(document.createElement("br"));

                            buttonCreation(formContainer_but);

                            break;
                        default:
                            document.getElementById("errormessage").textContent += message;
                            break;
                    }
                }
            }
        );
    });

    document.getElementById("createSP").addEventListener("click", () => {
        hideForm(document.getElementById("outerDiv").firstElementChild, document.getElementById("outerDiv").firstElementChild.nextElementSibling, 0);
    })

    document.getElementById("createOP").addEventListener("click", () => {
        hideForm(document.getElementById("outerDiv").firstElementChild, document.getElementById("outerDiv").firstElementChild.nextElementSibling, 1);
    })


    document.getElementById("but_sp").addEventListener("click", (e) => {
        createSP(e);
        let form = e.target.closest("form");
        if (form.checkValidity()) {
            makeCall("POST", "CreateOrder", e.target.closest("form"),
                function (req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        let message = req.responseText;
                        alertCleaning(message);
                    }
                }
            );
        }
        else {
            form.reportValidity();
        }
    });


})();


function buildForm_SP_services(fc, ts) {
    let checkDiv = document.createElement("div");
    checkDiv.classList.add("form-check");
    let checkInput = document.createElement("input");
    checkInput.classList.add("form-check-input");
    checkInput.type = "checkbox";
    checkInput.id = "ts" + ts.serviceId;

    let checkLabel = document.createElement("label");
    checkLabel.classList.add("form-check-label");
    checkLabel.for = "ts" + ts.serviceId;
    checkLabel.innerHTML = ts.description;

    checkDiv.appendChild(checkInput);
    checkDiv.appendChild(checkLabel);
    fc.appendChild(checkDiv);
}

function buildForm_SP_optionalProducts(fc, op) {
    let checkDiv = document.createElement("div");
    checkDiv.classList.add("form-check");
    let checkInput = document.createElement("input");
    checkInput.classList.add("form-check-input");
    checkInput.type = "checkbox";
    checkInput.id = "op" + op.optionalProductId;

    let checkLabel = document.createElement("label");
    checkLabel.classList.add("form-check-label");
    checkLabel.for = "op" + op.optionalProductId;
    checkLabel.innerHTML = op.name + ": " + op.monthlyFee_euro + "€/month";

    checkDiv.appendChild(checkInput);
    checkDiv.appendChild(checkLabel);
    fc.appendChild(checkDiv);
}

function buildForm_SP_validityPeriods(fc, vp) {
    let checkDiv = document.createElement("div");
    checkDiv.classList.add("form-check");
    let checkInput = document.createElement("input");
    checkInput.classList.add("form-check-input");
    checkInput.type = "checkbox";
    checkInput.id = "vp" + vp.validityPeriodId;

    let checkLabel = document.createElement("label");
    checkLabel.classList.add("form-check-label");
    checkLabel.for = "vp" + vp.validityPeriodId;
    checkLabel.innerHTML = vp.monthsOfValidity + " months at " + vp.monthlyFee_euro + "€/month";

    checkDiv.appendChild(checkInput);
    checkDiv.appendChild(checkLabel);
    fc.appendChild(checkDiv);
}

function buttonCreation(fc) {
    let but_class = document.createElement("div");
    but_class.classList.add("d-grid");
    but_class.classList.add("gap-2");
    let but = document.createElement("button");
    but.id = "but_sp";
    but.classList.add("btn");
    but.classList.add("btn-primary");
    but.classList.add("btn-lg");
    but.type = "button";
    let b = document.createElement("b");
    b.innerHTML = "CREATE";
    but.appendChild(b);
    but_class.appendChild(but);
    fc.appendChild(but_class);
}

function hideForm(obj1, obj2, n) {
    if(n === 0) {
        obj1.hidden = false;
        obj2.hidden = true;
    }
    else {
        obj1.hidden = true;
        obj2.hidden = false;
    }

}

function alertCleaning(message) {
    let alert = document.getElementById("creation_alert");
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


function createSP(event) {
    event.preventDefault();


}