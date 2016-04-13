
'use strict';

angular.module('calendar', ['mwl.calendar', 'ui.bootstrap', 'ngTouch', 'ngAnimate'])

        .controller('settingsTaskGeneratorCtrl',
                [
                    '$log',
                    "$scope",
                    "$uibModal",
                    "taskAllocationSrvc",
                    "modalSrvc",
                    function ($log, $scope, $uibModal, taskAllocationSrvc, modalSrvc) {

                        var CONTROLLER_NAME = 'settingsTaskGeneratorCtrl';

                        $scope.taskGeneratorConf = [];
                        $scope.javaConf = [];

                        // ==================================================
                        // - when user click on the java configuration button
                        // ==================================================
                        $scope.getJavaVersion = function () {

                            $scope.version_logs = [];

                            var msg = {'msg': ""};
                            $scope.version_logs.push(msg);

                            msg = {'msg': "Requesting server ..."};
                            $scope.version_logs.push(msg);

                            var allocationPromise = taskAllocationSrvc.getJavaConfiguration();
                            allocationPromise.then(
                                    function (response) {

                                        // --------------------------------------------------------
                                        $log.debug(CONTROLLER_NAME + " : response = " + JSON.stringify(response));
                                        // --------------------------------------------------------

                                        $scope.version_logs = [];

                                        var index = 0;
                                        var logs = response.data;
                                        for (index = 0; index <= logs.length; index++) {
                                            var data = logs[index];
                                            var msg = {'msg': data};
                                            $scope.version_logs.push(msg);
                                        }

                                        // --------------------------------------------------------
                                        $log.debug(CONTROLLER_NAME + " : $scope.version_logs = " + JSON.stringify($scope.version_logs));
                                        // --------------------------------------------------------

                                    },
                                    function (response) {

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                        // --------------------------------------------------------

                                        var msg = {'msg': "... error occured while trying to query the server."};
                                        $scope.version_logs.push(msg);

                                        var status = response.status;
                                        var message = response.statusText;
                                        modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                    }
                            );
                        };

                        // ==================================================
                        // - when user click on the taskflow configuration button
                        // ==================================================
                        $scope.getTaskflowConfiguration = function () {

                            $scope.configuration_logs = [];

                            var msg = {'msg': ""};
                            $scope.configuration_logs.push(msg);

                            msg = {'msg': "Requesting server ..."};
                            $scope.configuration_logs.push(msg);

                            var allocationPromise = taskAllocationSrvc.getTaskflowConfiguration();
                            allocationPromise.then(
                                    function (response) {

                                        // --------------------------------------------------------
                                        $log.debug(CONTROLLER_NAME + " : response = " + JSON.stringify(response));
                                        // --------------------------------------------------------

                                        $scope.configuration_logs = [];

                                        var index = 0;
                                        var logs = response.data;
                                        for (index = 0; index <= logs.length; index++) {
                                            var data = logs[index];
                                            var msg = {'msg': data};
                                            $scope.configuration_logs.push(msg);
                                        }

                                        // --------------------------------------------------------
                                        $log.debug(CONTROLLER_NAME + " : $scope.version_logs = " + JSON.stringify($scope.version_logs));
                                        // --------------------------------------------------------

                                    },
                                    function (response) {

                                        // --------------------------------------------------------
                                        // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                        // --------------------------------------------------------

                                        var msg = {'msg': "... error occured while trying to query the server."};
                                        $scope.configuration_logs.push(msg);

                                        var status = response.status;
                                        var message = response.statusText;
                                        modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                    }
                            );
                        };

                    }
                ]);

