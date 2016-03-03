'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'employeesCtrl',
        [
            '$state',
            '$log',
            '$scope',
            '$http',
            "$uibModal",
            'pageSrvc',
            'employeesSrvc',
            'modalSrvc',
            function ($state, $log, $scope, $http, $uibModal, pageSrvc, employeesSrvc, modalSrvc) {

                // the Employees page is being displayed
                pageSrvc.setEmployeePage(true);

                $scope.obj = new Flow();

                // ==================================================
                // - initialize the list of employees
                // ==================================================

                // --------------------------------------------------------
                // - API : get list all employees
                // --------------------------------------------------------
                // - Method = 
                // - URI = taskflow/apis/v1/employees
                // - Controller = employees.index
                // --------------------------------------------------------
                $http.get('/taskflow/apis/v1/employees')
                        // --------------------------------------------------------
                        // - success, modify the list by appending the new skill
                        // --------------------------------------------------------
                        .success(function (response) {

                            $scope.employees = response.data;

                            // --------------------------------------------------------
                            // $log.debug("employeesCtrl : Initialization : $scope.employees = " + JSON.stringify($scope.employees));
                            // --------------------------------------------------------
                        })
                        // --------------------------------------------------------
                        // - error
                        // --------------------------------------------------------
                        .error(function (data, status, headers, config) {

                            // --------------------------------------------------------
                            // $log.error("employeesCtrl : Initialization : error status = " + JSON.stringify(status));
                            // $log.error("employeesCtrl : Initialization : error data = " + JSON.stringify(data));
                            // --------------------------------------------------------

                            var msg = 'employeesCtrl : Internal server error status = 500!';
                            modalSrvc.showErrorMessageModal(msg);
                        });

                // ==================================================
                // - delete event modal
                // ==================================================
                function showDeleteEventModal(employee) {
                    var modalInstance = $uibModal.open({
                        templateUrl: 'taskflow/fragments/employee_delete',
                        placement: 'center',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.employee = employee;
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.deleteEmployee = function () {
                                $uibModalInstance.close($scope.employee);
                            };

                        }
                    });

                    modalInstance.result.then(function (employee, action) {

                        var employeeId = employee.id;

                        var employeePromise = employeesSrvc.deleteEmployee(employeeId);
                        employeePromise.then(
                                function (response) {

                                    var employeeId = employee.id;
                                    $scope.employees = $scope.employees.filter(function (employee) {
                                        return employee.id !== employeeId;
                                    });

                                    var msg = 'Employee successfully deleted !';
                                    modalSrvc.showSuccessMessageModal(msg);

                                },
                                function (response) {
                                    var msg = 'employeesCtrl : Internal server error status = 500!';
                                    modalSrvc.showErrorMessageModal(msg);
                                }
                        );

                    });
                }

                // ==================================================
                // - new event modal
                // ==================================================
                function showNewEventModal(employee) {

                    var modalInstance = $uibModal.open({
                        templateUrl: 'taskflow/fragments/employee_new',
                        size: 'lg',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.employee = employee;
                            $scope.manager = 'teamMember';
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };
                            $scope.createEmployee = function () {

                                if ($scope.manager === 'teamLeader') {
                                    $scope.employee.isTeamLeader = 1;
                                    $scope.employee.isProjectManager = 0;
                                }
                                else if ($scope.manager === 'projectManager') {
                                    $scope.employee.isTeamLeader = 0;
                                    $scope.employee.isProjectManager = 1;
                                }
                                else {
                                    $scope.employee.isTeamLeader = 0;
                                    $scope.employee.isProjectManager = 0;
                                }

                                $uibModalInstance.close($scope.employee);
                            };
                        }
                    });

                    modalInstance.result.then(function (employee, action) {

                        // --------------------------------------------------------
                        $log.debug("new employee = " + JSON.stringify(employee));
                        // --------------------------------------------------------

                        var employeePromise = employeesSrvc.createEmployee(employee);
                        employeePromise.then(
                                function (response) {

                                    var employee = response.employee;
                                    $scope.employees.push(employee);

                                    var msg = 'Employee successfully created !';
                                    modalSrvc.showSuccessMessageModal(msg);

                                },
                                function (response) {
                                    var msg = 'employeesCtrl : Internal server error status = 500!';
                                    modalSrvc.showErrorMessageModal(msg);
                                }
                        );


                    });
                }

                // ==================================================
                // - show the relevant modal
                // ==================================================
                function showModal(action, employee) {

                    switch (action) {

                        case 'New' :
                            showNewEventModal(employee);
                            break;

                        case 'Deleted' :
                            showDeleteEventModal(employee);
                            break;
                    }
                }

                // ==================================================
                // - when user click on the employee's selection bar
                // ==================================================
                $scope.showEmployee = function (id) {

                    employeesSrvc.setEmployeeId(id);
                    $state.go('app.employee');
                };

                // ==================================================
                // - find a row in the employee table
                // ==================================================
                $scope.findEmployee = function (id) {

                    var employee = null;

                    var employees = $scope.employees.filter(function (employee) {
                        return employee.id === id;
                    });

                    if (employees.length === 1) {
                        employee = employees[0];
                    }

                    return employee;
                };

                // ==================================================
                // - when user click on the employee's delete icon
                // ==================================================
                $scope.deleteEmployee = function (id) {

                    var employee = $scope.findEmployee(id);
                    showModal('Deleted', employee);
                };

                // ==================================================
                // - when user click on the new employee button
                // ==================================================
                $scope.newEmployee = function () {

                    var newEmployee = {
                        firstName: '',
                        lastName: '',
                        fullName: '',
                        location: '',
                        employement: '',
                        email: '',
                        phone: '',
                        avatar: '../../img/caratlane/avatar-50.png',
                        isTeamLeader: 0,
                        isProjectManager: 0
                    };

                    showModal('New', newEmployee);
                };


            }

            // ==================================================
        ]
        );