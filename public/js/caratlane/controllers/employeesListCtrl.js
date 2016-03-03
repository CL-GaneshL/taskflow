'use strict';
/** 
 * This controller aims at querying the backend to get the list 
 * off all employees.
 */
app.controller(
        "employeesListCtrl", ['$log', '$scope', '$http', '$uibModal',
            function ($log, $scope, $http, $uibModal) {
                // ==================================================
                // - get the list of all employees
                // ==================================================

                // --------------------------------------------------------
                // - API : Retrieve all employees from DB
                // --------------------------------------------------------
                // - Method = GET
                // - URI = taskflow/apis/v1/employees
                // - Controller = empoyees.index
                // --------------------------------------------------------
                $http.get('/taskflow/apis/v1/employees')
                        // --------------------------------------------------------
                        // - success
                        // --------------------------------------------------------
                        .success(function (response) {

                            // list of teams
                            $scope.allEmployees = response.data[0];

                            // --------------------------------------------------------
                            // $log.debug("employeesListCtrl : Initialization : response = " + JSON.stringify(response));
                            // --------------------------------------------------------
                        })
                        // --------------------------------------------------------
                        // - error
                        // --------------------------------------------------------
                        .error(function (data, status, headers, config) {

                            // --------------------------------------------------------
                            // $log.error("employeesListCtrl : Initialization : error status = " + JSON.stringify(status));
                            // $log.error("employeesListCtrl : Initialization : error data = " + JSON.stringify(data));
                            // --------------------------------------------------------

                            showErrorMessageModal('employeesListCtrl : Internal server error status = 500!');
                        });


                // ==================================================
                // - error message modal
                // ==================================================
                function showErrorMessageModal(message) {
                    var modalInstance = $uibModal.open({
                        templateUrl: 'taskflow/fragments/modal_error_message',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.errorMessage = message;
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                        }
                    });
                }

                // ==================================================


            }
        ]
        );