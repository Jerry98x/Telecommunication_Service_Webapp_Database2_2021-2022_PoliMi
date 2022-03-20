

(function () {
    if(sessionStorage.getItem("loggedEmployee") != null) {
        let employee = JSON.parse(sessionStorage.getItem("loggedEmployee"));
        let employeeInfo = document.createElement("h6");
        employeeInfo.innerHTML = "Logged in as <b>" + employee.employeeId + "</b>";
        document.getElementById("employee_login").appendChild(employeeInfo);
    }



})();