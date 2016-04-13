'use strict';

app.factory("settingsSrvc", function ($log, $http) {

    var FACTORY_NAME = 'settingsSrvc';

    // --------------------------------------------------------
    // - 
    // --------------------------------------------------------
    var getTaskflowConfiguration = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/settings/taskflow-configuration/'
                }
        ).then(function (response) {

            // --------------------------------------------------------
            $log.debug(FACTORY_NAME + " : response = " + JSON.stringify(response));
            // --------------------------------------------------------

            var data = response.data.data;

            return {
                data: data
            };
        });
    };

    // --------------------------------------------------------
    // - 
    // --------------------------------------------------------
    var getJavaConfiguration = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/settings/java-configuration/'
                }
        ).then(function (response) {

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response = " + JSON.stringify(response));
            // --------------------------------------------------------

            var data = response.data.data;

            return {
                data: data
            };
        });
    };

    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------

    return {
        getTaskflowConfiguration: getTaskflowConfiguration,
        getJavaConfiguration: getJavaConfiguration
    };
    // ==================================================
});