'use strict';

app.controller(
        "taskAllocationCtrl",
        [
            '$log',
            '$scope',
            '$uibModal',
            'modalSrvc',
            'taskAllocationSrvc',
            function ($log, $scope, $uibModal, modalSrvc, taskAllocationSrvc) {

                var CONTROLLER_NAME = 'taskAllocationCtrl';

                // ==================================================
                // - initialization
                // ==================================================
                $scope.generate_message = '';
                $scope.reset_message = '';

                // ==================================================
                // - kick off task allocation
                // ==================================================
                $scope.allocateTasks = function () {

                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : allocate ..... ");
                    // --------------------------------------------------------

                    $scope.generate_message = 'Allocating tasks ...';
                    $scope.reset_message = '';

                    var allocatePromise = taskAllocationSrvc.allocate();
                    allocatePromise.then(
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : xxxxxxx = " + JSON.stringify(xxxxxxx));
                                $log.debug(CONTROLLER_NAME + " : allocate done ! ");
                                // --------------------------------------------------------

                                $scope.generate_message = '... allocation completed.';
                                $scope.reset_message = '';

                            },
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                // --------------------------------------------------------

                                // ==================================================
                                // - allocation failed
                                // ==================================================

                                var status = response.status;
                                var message = response.statusText;
                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);

                                $scope.generate_message = 'Error occured during Task allocation.';
                                $scope.reset_message = '';
                            }
                    );
                };

                // ==================================================
                // - reset task allocation ( for testing purpose )
                // ==================================================
                $scope.resetTasks = function () {

                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : reset ..... ");
                    // --------------------------------------------------------

                    $scope.generate_message = '';
                    $scope.reset_message = 'Resetting database ...';

                    var allocatePromise = taskAllocationSrvc.reset();
                    allocatePromise.then(
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : xxxxxxx = " + JSON.stringify(xxxxxxx));
                                $log.debug(CONTROLLER_NAME + " : reset done ! ");
                                // --------------------------------------------------------

                                $scope.generate_message = '';
                                $scope.reset_message = '... database reset.';

                            },
                            function (response) {

                                // --------------------------------------------------------
                                // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                // --------------------------------------------------------

                                // ==================================================
                                // - reset failed
                                // ==================================================

                                var status = response.status;
                                var message = response.statusText;
                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);

                                $scope.generate_message = '';
                                $scope.reset_message = 'Error occured during reset of the database.';
                            }
                    );
                };


                // ==================================================
            }]);