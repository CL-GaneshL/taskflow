'use strict';

app.factory("employeesSrvc", function ($http, $log) {

    var FACTORY_NAME = 'employeesSrvc';

    var employeeId = null;

    // ==================================================
    // - retrieve the employee's profile from the DB
    // ==================================================
    var getEmployee = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/employees/' + employeeId
                }
        ).then(function (response) {

            var employee = response.data.data[0][0];
            var team = response.data.data[1][0];
            var recentTasks = response.data.data[2];
            var holidays = response.data.data[3];
            var tasks = response.data.data[4];
            var skills = response.data.data[5];
            var non_working_days = response.data.data[6];

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : recentTasks = " + JSON.stringify(recentTasks));
            // --------------------------------------------------------

            return {
                employee: employee,
                team: team,
                recentTasks: recentTasks,
                holidays: holidays,
                tasks: tasks,
                skills: skills,
                non_working_days: non_working_days

            };
        });
    };

    var updateEmployee = function (employee) {

        var id = employee.id;

        return  $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/employees/' + id,
                    data: employee
                }
        );

    };

    var deleteEmployee = function (employeeId) {

        return  $http(
                {
                    method: "DELETE",
                    url: '/taskflow/apis/v1/employees/' + employeeId
                }
        ).then(function (response) {

            return {
                employee: null
            };
        });

    };

    var createEmployee = function (employee) {

        return  $http(
                {
                    method: "POST",
                    url: '/taskflow/apis/v1/employees',
                    data: employee
                }
        ).then(function (response) {

            var employee = response.data.data;

            return {
                employee: employee
            };
        });

    };

    // ==================================================
    // - return functions
    // ==================================================
    return {
        setEmployeeId: function (id) {
            employeeId = id;
        },
        getEmployeeId: function () {
            return employeeId;
        },
        getEmployee: getEmployee,
        updateEmployee: updateEmployee,
        deleteEmployee: deleteEmployee,
        createEmployee: createEmployee
    };


    // ==================================================
});