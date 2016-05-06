'use strict';

app.factory("metricsSrvc", function ($log, $http) {

    var FACTORY_NAME = 'metricsSrvc';
    // --------------------------------------------------------
    // - allocate tasks
    // --------------------------------------------------------
    var getProjectMetrics = function (project_id) {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/metrics/' + project_id
                }
        ).then(function (response) {

            return {
                labels: response.data.data[0],
                PV: response.data.data[1],
                EV: response.data.data[2],
                CPI: response.data.data[3],
                SPI: response.data.data[4]
            };
        });
    };
    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------

    return {
        getProjectMetrics: getProjectMetrics
    };
    // ==================================================
});