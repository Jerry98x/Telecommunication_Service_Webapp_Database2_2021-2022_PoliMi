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

    document.getElementById("sp").hidden = true;
    document.getElementById("op").hidden = true;

    //let spForm = document.getElementById("spCreationForm");
    //let opForm = document.getElementById("opCreationForm");

    const S_array = [];
    const OP_array = [];
    const VP_array = [];

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

                            t_ser.forEach(ts => buildForm_SP_services(formContainer_serv, ts, S_array));
                            formContainer_serv.appendChild(document.createElement("br"));

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

                            opt_prod.forEach(op => buildForm_SP_optionalProducts(formContainer_optprod, op, OP_array));
                            formContainer_optprod.appendChild(document.createElement("br"));

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
        makeCall("GET", "GetValidityPeriods", null,
            function (req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    let message = req.responseText;
                    switch (req.status) {
                        case 200:
                            let val_per = JSON.parse(message);
                            let formContainer_valper = document.getElementById("fc_valper");

                            let p = document.createElement("p");
                            let b = document.createElement("b");
                            p.textContent = "Available validity periods:";
                            b.appendChild(p);
                            formContainer_valper.appendChild(b);

                            val_per.forEach(vp => buildForm_SP_validityPeriods(formContainer_valper, vp, VP_array));
                            formContainer_valper.appendChild(document.createElement("br"));

                            // buttonCreation(formContainer_but);

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

    document.getElementById("createSP").addEventListener("click", () => {
        hideForm(document.getElementById("outerDiv").firstElementChild, document.getElementById("outerDiv").firstElementChild.nextElementSibling, 0);
    })

    document.getElementById("createOP").addEventListener("click", () => {
        hideForm(document.getElementById("outerDiv").firstElementChild, document.getElementById("outerDiv").firstElementChild.nextElementSibling, 1);
    })


    document.getElementById("but_sp").addEventListener("click", (e) => {
        e.preventDefault();

        let SPproductForm = document.createElement("form");
        createProduct(SPproductForm, 0, S_array, OP_array, VP_array);

        if (SPproductForm.checkValidity()) {
            makeCall("POST", "CreateServicePackage", SPproductForm,
                function (req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        let status = req.status;
                        let message = req.responseText;
                        switch (status) {
                            case 200:
                                modalSetting(message);
                                break;
                            case 401:
                                window.location.href = "LandingPage.html";
                                break;
                            default:
                                alertClientsideErrorSetting(message);
                                break;
                        }
                        //alertCleaning(status, message);
                    }
                }
            );
        }
        else {
            SPproductForm.reportValidity();
        }
    });

    document.getElementById("but_op").addEventListener("click", (e) => {
        e.preventDefault();

        let OPproductForm = document.createElement("form");
        createProduct(OPproductForm, 1, null, null, null);

        if (OPproductForm.checkValidity()) {
            makeCall("POST", "CreateOptionalProduct", OPproductForm,
                function (req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        let status = req.status;
                        let message = req.responseText;
                        switch (status) {
                            case 200:
                                modalSetting(message);
                                break;
                            case 401:
                                window.location.href = "LandingPage.html";
                                break;
                            default:
                                alertClientsideErrorSetting(message);
                                break;
                        }
                        //alertCleaning(status, message);
                    }
                }
            );
        }
        else {
            OPproductForm.reportValidity();
        }
    });

})();


function buildForm_SP_services(fc, ts, array) {
    let checkDiv = document.createElement("div");
    checkDiv.classList.add("form-check");
    let checkInput = document.createElement("input");
    checkInput.classList.add("form-check-input");
    checkInput.type = "checkbox";
    checkInput.id = "ts" + ts.serviceId;

    checkInput.addEventListener("change", (e) => {
        e.preventDefault();
        if(checkInput.checked) {
            array[ts.serviceId] = ts.serviceId;
        }
        else {
            array[ts.serviceId] = null;
        }
    });

    let checkLabel = document.createElement("label");
    checkLabel.classList.add("form-check-label");
    checkLabel.for = "ts" + ts.serviceId;
    checkLabel.innerHTML = ts.description;

    checkDiv.appendChild(checkInput);
    checkDiv.appendChild(checkLabel);
    fc.appendChild(checkDiv);
}

function buildForm_SP_optionalProducts(fc, op, array) {
    let checkDiv = document.createElement("div");
    checkDiv.classList.add("form-check");
    let checkInput = document.createElement("input");
    checkInput.classList.add("form-check-input");
    checkInput.type = "checkbox";
    checkInput.id = "op" + op.optionalProductId;

    checkInput.addEventListener("change", (e) => {
        e.preventDefault();
        if(checkInput.checked) {
            array[op.optionalProductId] = op.optionalProductId;
        }
        else {
            array[p.optionalProductId] = null;
        }
    });

    let checkLabel = document.createElement("label");
    checkLabel.classList.add("form-check-label");
    checkLabel.for = "op" + op.optionalProductId;
    checkLabel.innerHTML = op.name + ": " + op.monthlyFee_euro + "€/month";

    checkDiv.appendChild(checkInput);
    checkDiv.appendChild(checkLabel);
    fc.appendChild(checkDiv);
}

function buildForm_SP_validityPeriods(fc, vp, array) {
    let checkDiv = document.createElement("div");
    checkDiv.classList.add("form-check");
    let checkInput = document.createElement("input");
    checkInput.classList.add("form-check-input");
    checkInput.type = "checkbox";
    checkInput.id = "vp" + vp.validityPeriodId;

    checkInput.addEventListener("change", (e) => {
        e.preventDefault();
        if(checkInput.checked) {
            array[vp.validityPeriodId] = vp.validityPeriodId;
        }
        else {
            array[vp.validityPeriodId] = null;
        }
    });

    let checkLabel = document.createElement("label");
    checkLabel.classList.add("form-check-label");
    checkLabel.for = "vp" + vp.validityPeriodId;
    checkLabel.innerHTML = vp.monthsOfValidity + " months at " + vp.monthlyFee_euro + "€/month";

    checkDiv.appendChild(checkInput);
    checkDiv.appendChild(checkLabel);
    fc.appendChild(checkDiv);
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

function alertCleaning(status, message) {
    let alert = document.getElementById("creation_alert");
    alert.innerHTML = "";

    if(alert.classList.contains("alert-success") && status === 406) {
        alert.classList.remove("alert-success");
        alert.classList.add("alert-danger");
    }
    else if(alert.classList.contains("alert-danger") && status === 200) {
        alert.classList.remove("alert-danger");
        alert.classList.add("alert-success");
    }

    let msg = document.createTextNode(message);
    alert.appendChild(msg);
    alert.style.visibility = "visible";
}

function alertClientsideErrorSetting(message) {
    let alert = document.getElementById("creation_alert");
    alert.innerHTML = "";

    if(alert.classList.contains("alert-success")) {
        alert.classList.remove("alert-success");
        alert.classList.add("alert-danger");
    }

    let msg = document.createTextNode(message);
    alert.appendChild(msg);
    alert.style.visibility = "visible";
}

function modalSetting(message) {
    document.getElementById("modal_text").innerHTML = message;
    let myModal = new bootstrap.Modal(document.getElementById('myModal'), {});
    myModal.show();

}


function createProduct(productForm, productType, s_array, op_array, vp_array) {
    if(productType === 0) {
        buildSPformCreation(productForm, s_array, op_array, vp_array);
    }
    else {
        buildOPformCreation(productForm);
    }

}

function buildSPformCreation(form, s_array, op_array, vp_array) {
    let inputName = document.createElement("input");
    inputName.name = "sp_name";
    if(document.getElementById("sp_name").value !== null) {
        inputName.value = document.getElementById("sp_name").value;
    }
    else {
        alertClientsideErrorSetting("All input fields need to be compiled.");
        return;
    }
    form.appendChild(inputName);

    let listServices = document.createElement("input");
    listServices.name = "inputServices";
    s_array.forEach(el => {
        if(el != null) {
            listServices.value += el + "$";
        }
    })
    form.appendChild(listServices);

    let listOptionalProducts = document.createElement("input");
    listOptionalProducts.name = "inputOptionalProducts";
    op_array.forEach(el => {
        if(el != null) {
            listOptionalProducts.value += el + "$";
        }
    })
    form.appendChild(listOptionalProducts);

    let listValidityPeriods = document.createElement("input");
    listValidityPeriods.name = "inputValidityPeriods";
    vp_array.forEach(el => {
        if(el != null) {
            listValidityPeriods.value += el + "$";
        }
    })
    form.appendChild(listValidityPeriods);

}

function buildOPformCreation(form) {
    let inputName = document.createElement("input");
    inputName.name = "op_name";
    if(document.getElementById("op_name").value !== null) {
        inputName.value = document.getElementById("op_name").value;
        form.appendChild(inputName);
    }

    let inputMonthlyFee = document.createElement("input");
    inputMonthlyFee.name = "op_fee";
    if(document.getElementById("op_fee").value !== null) {
        inputMonthlyFee.value = document.getElementById("op_fee").value;
        form.appendChild(inputMonthlyFee);
    }
}

function formCleaning() {
    //clean alert
    let alert = document.getElementById("creation_alert");
    alert.innerHTML = "";
    if(!alert.hidden) {
        alert.hidden = true;
    }

    //clean optional product form
    document.getElementById("op_name").value = "";
    document.getElementById("op_fee").value = "";

    //clean service package form

    let checkboxes = document.getElementsByClassName("form-check-input");
    for(let i = 0; i < checkboxes.length; i++) {
        if(checkboxes[i].checked) {
            checkboxes[i].checked = false;
        }
    }

    document.getElementById("sp_name").value = "";
    let sp = document.getElementById("sp");
    let op = document.getElementById("op");
    if(!sp.hidden) {
        sp.hidden = true;
    }
    if(!op.hidden) {
        op.hidden = true;
    }

    window.location.href = "EmployeeHomePage.html";
}