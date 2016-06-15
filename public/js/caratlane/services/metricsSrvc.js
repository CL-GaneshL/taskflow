'use strict';

app.factory("metricsSrvc", function ($log, $http) {

    var FACTORY_NAME = 'metricsSrvc';

// --------------------------------------------------------
    // - get the project's metrics
    // --------------------------------------------------------
    var getProjectMetrics = function (project_id) {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/metrics/projects/' + project_id
                }
        ).then(function (response) {

            return {
                labels: response.data.data[0],
                PV: response.data.data[1],
                EV: response.data.data[2],
                CPI: response.data.data[3],
                SPI: response.data.data[4],
                indicators: response.data.data[5]
            };
        });
    };

    // --------------------------------------------------------
    // - get the employee's metrics
    // --------------------------------------------------------
    var getEmployeeMetrics = function (employee_id, start_date, end_date) {

        return $http(
                {
                    method: "POST",
                    url: '/taskflow/apis/v1/metrics/employees/',
                    data: {
                        id: employee_id,
                        start_date: start_date,
                        end_date: end_date
                    }
                }
        ).then(function (response) {

            return {
                labels: response.data.data[0],
                PV: response.data.data[1],
                EV: response.data.data[2]
            };
        });
    };

// --------------------------------------------------------
    // - get the project's status
    // --------------------------------------------------------
    var getProjectStatus = function (project_id) {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/metrics/status/' + project_id
                }
        ).then(function (response) {

            return {
                labels: response.data.data[0],
                HP: response.data.data[1],
                HC: response.data.data[2],
                PP: response.data.data[3],
                PC: response.data.data[4]
            };
        });
    };


    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------

    return {
        getProjectMetrics: getProjectMetrics,
        getEmployeeMetrics: getEmployeeMetrics,
        getProjectStatus: getProjectStatus
    };
    // ==================================================
});