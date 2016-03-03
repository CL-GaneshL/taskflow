'use strict';

app.factory("projectsTasksSrvc", function ($log, $http) {

    var FACTORY_NAME = 'projectsTasksSrvc';

    // ==================================================
    // - 
    // ==================================================
    var getProjectsEvents = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/projects-tasks'
                }
        ).then(function (response) {

            var tasks = response.data.data[0];
            var holidays = response.data.data[1];
            var non_working_days = response.data.data[2];
            var employees = response.data.data[3];
            var projects = response.data.data[4];

            return {
                tasks: tasks,
                holidays: holidays,
                non_working_days: non_working_days,
                employees: employees,
                projects: projects
            };
        });

    };

    // ==================================================
    // - return functions
    // ==================================================
    return {
        getProjectsEvents: getProjectsEvents
    };

    // ==================================================
});