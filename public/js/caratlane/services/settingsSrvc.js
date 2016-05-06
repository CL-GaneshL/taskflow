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
    // - 
    // --------------------------------------------------------
    var getHourlyCost = function () {

        return $http(
                {
                    method: "GET",
                    url: '/taskflow/apis/v1/hourly-cost/'
                }
        ).then(function (response) {

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response = " + JSON.stringify(response));
            // --------------------------------------------------------

            var hourlyCost = response.data.data;

            return {
                hourlyCost: hourlyCost
            };
        });
    };

    // --------------------------------------------------------
    // - 
    // --------------------------------------------------------
    var updateHourlyCost = function (newHourlyCost) {

        // Using id = 0 for now, only 1 hourly cost available
        
        return $http(
                {
                    method: "PUT",
                    url: '/taskflow/apis/v1/hourly-cost/' + "0",
                    data: {
                        'hourly_cost': newHourlyCost
                    }
                }
        ).then(function (response) {

            // --------------------------------------------------------
            // $log.debug(FACTORY_NAME + " : response = " + JSON.stringify(response));
            // --------------------------------------------------------

            var hourlyCost = response.data.data;

            return {
                hourlyCost: hourlyCost
            };
        });
    };

    // --------------------------------------------------------
    // - return functions
    // --------------------------------------------------------

    return {
        getTaskflowConfiguration: getTaskflowConfiguration,
        getJavaConfiguration: getJavaConfiguration,
        updateHourlyCost: updateHourlyCost,
        getHourlyCost: getHourlyCost
    };
    // ==================================================
});