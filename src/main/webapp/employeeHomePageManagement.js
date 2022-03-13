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

                            let p = document.createElement("p");
                            let b = document.createElement("b");
                            p.textContent = "Available services:";
                            b.appendChild(p);
                            spForm.appendChild(b);

                            t_ser.forEach(ts => buildForm_SP_services(spForm, ts));
                            spForm.appendChild(document.createElement("br"));

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

                            let p = document.createElement("p");
                            let b = document.createElement("b");
                            p.textContent = "Available optional products:";
                            b.appendChild(p);
                            spForm.appendChild(b);

                            opt_prod.forEach(op => buildForm_SP_optionalProducts(spForm, op));
                            spForm.appendChild(document.createElement("br"));

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

                            let p = document.createElement("p");
                            let b = document.createElement("b");
                            p.textContent = "Available validity periods:";
                            b.appendChild(p);
                            spForm.appendChild(b);

                            val_per.forEach(vp => buildForm_SP_validityPeriods(spForm, vp));
                            spForm.appendChild(document.createElement("br"));

                            buttonCreation(spForm);

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
        swapAndHide(document.getElementById("outerDiv").firstElementChild, document.getElementById("outerDiv").firstElementChild.nextElementSibling, 0);
    })

    document.getElementById("createOP").addEventListener("click", () => {
        swapAndHide(document.getElementById("outerDiv").firstElementChild, document.getElementById("outerDiv").firstElementChild.nextElementSibling, 1);
    })



})();


function buildForm_SP_services(sp, ts) {
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
    sp.appendChild(checkDiv);
}

function buildForm_SP_optionalProducts(sp, op) {
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
    sp.appendChild(checkDiv);
}

function buildForm_SP_validityPeriods(sp, vp) {
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
    sp.appendChild(checkDiv);
}

function buttonCreation(form) {
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
    form.appendChild(but_class);
}

function swapAndHide(obj1, obj2, n) {
    if(n === 0) {
        obj1.hidden = false;
        obj2.hidden = true;
    }
    else {
        obj1.hidden = true;
        obj2.hidden = false;
    }

}
