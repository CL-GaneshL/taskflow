'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'metricsEmployeesCtrl',
        [
            '$log',
            '$scope',
            '$uibModal',
            'metricsSrvc',
            'modalSrvc',
            'employeesSrvc',
            'usSpinnerService',
            function (
                    $log,
                    $scope,
                    $uibModal,
                    metricsSrvc,
                    modalSrvc,
                    employeesSrvc,
                    usSpinnerService
                    ) {

                var CONTROLLER_NAME = 'metricsEmployeesCtrl';

                // ==================================================
                // - initialization
                // ==================================================

                $scope.employees = null;

                // ==================================================
                // - find a row in the employee table
                // ==================================================
                var findEmployee = function (id) {

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
                // - format a JS date to a MySQL date
                // ==================================================
                function  formatJS2MySQLDate(js_date) {

                    var year = js_date.getFullYear();
                    var month = js_date.getMonth() + 1;
                    var day = js_date.getDate();

                    var monthStr = month;
                    if (month < 10) {
                        monthStr = '0' + monthStr;
                    }

                    var dayStr = day;
                    if (day < 10) {
                        dayStr = '0' + dayStr;
                    }

                    return year + '-' + monthStr + '-' + dayStr;
                }

                // ==================================================
                // - retrieve all employees
                // ==================================================

                var employeesPromise = employeesSrvc.getAllEmployees();
                employeesPromise.then(
                        function (response) {

                            $scope.employees = response.employees;

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : $scope.employees = " + JSON.stringify($scope.employees));
                            // --------------------------------------------------------
                        },
                        function (response) {

                            // ==================================================
                            // - retrieving projects failed
                            // ==================================================

                            var status = response.status;
                            var message = response.statusText;
                            modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                        });

                // ==================================================
                // - display Metrics fro that employee
                // ==================================================
                $scope.displayMetrics = function (employee_id) {

                    var obj = findEmployee(employee_id);
                    var employee = obj.fullName;

                    // we initialize the metrics periode to 1 week
                    // starting to the 1st day of the current week
                    // and finishing the last day 
                    var curr = new Date();
                    var day = curr.getDay();
                    var firstday = new Date(curr.getTime() - 60 * 60 * 24 * day * 1000); // will return firstday (i.e. Sunday) of the week
                    var lastday = new Date(curr.getTime() + 60 * 60 * 24 * 6 * 1000); // adding (60*60*6*24*1000) means adding six days to the firstday which results in lastday (Saturday) of the week

                    var start_date = formatJS2MySQLDate(firstday);
                    var end_date = formatJS2MySQLDate(lastday);

                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : employee = " + JSON.stringify(employee));
                    $log.debug(CONTROLLER_NAME + " : start_date = " + JSON.stringify(start_date));
                    $log.debug(CONTROLLER_NAME + " : end_date = " + JSON.stringify(end_date));
                    // --------------------------------------------------------

                    $uibModal.open({
                        animation: $scope.animationsEnabled,
                        templateUrl: 'taskflow/fragments/modal_employee_metrics',
                        placement: 'center',
                        size: 'lg',
                        controller: function ($scope, $uibModalInstance) {
                            $scope.$modalInstance = $uibModalInstance;
                            $scope.employee = employee;
                            $scope.start_date = start_date;
                            $scope.end_date = end_date;
                            $scope.cancel = function () {
                                $uibModalInstance.dismiss('cancel');
                            };

                            $scope.data = null;
                            $scope.labels = null;
                            $scope.datasets = null;
                            $scope.options = null;
                            $scope.PV_data_set = null;

                            // ==================================================
                            // - charts options
                            // ==================================================
                            $scope.options = {
                                maintainAspectRatio: false,
                                // Sets the chart to be responsive
                                responsive: true,
                                ///Boolean - Whether grid lines are shown across the chart
                                scaleShowGridLines: true,
                                //String - Colour of the grid lines
                                scaleGridLineColor: 'rgba(0,0,0,.05)',
                                //Number - Width of the grid lines
                                scaleGridLineWidth: 1,
                                //Boolean - Whether the line is curved between points
                                bezierCurve: false,
                                //Number - Tension of the bezier curve between points
                                bezierCurveTension: 0.4,
                                //Boolean - Whether to show a dot for each point
                                pointDot: true,
                                //Number - Radius of each point dot in pixels
                                pointDotRadius: 4,
                                //Number - Pixel width of point dot stroke
                                pointDotStrokeWidth: 1,
                                //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
                                pointHitDetectionRadius: 20,
                                //Boolean - Whether to show a stroke for datasets
                                datasetStroke: true,
                                //Number - Pixel width of dataset stroke
                                datasetStrokeWidth: 2,
                                //Boolean - Whether to fill the dataset with a colour
                                datasetFill: true,
                                // Function - on animation progress
                                onAnimationProgress: function () { },
                                // Function - on animation complete
                                onAnimationComplete: function () { },
                                //String - A legend template
                                legendTemplate: '<ul class="tc-chart-js-legend"><% for (var i=0; i<datasets.length; i++){%><li><span style="background-color:<%=datasets[i].strokeColor%>"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>'
                            };

                            var metricsPromise = metricsSrvc.getEmployeeMetrics(employee_id, start_date, end_date);
                            metricsPromise.then(
                                    function (response) {

                                        var labels = response.labels;
                                        var PV_data = response.PV;
                                        var EV_data = response.EV;

                                        if (PV_data.length !== 0 && labels.length !== 0) {
                                            $scope.PV_data_set = {
                                                labels: labels,
                                                datasets: [
                                                    {
                                                        label: 'Planned Value (PV)',
                                                        fillColor: 'rgba(220,220,220,0.5)',
                                                        strokeColor: 'rgba(220,220,220,0.8)',
                                                        highlightFill: 'rgba(220,220,220,0.75)',
                                                        highlightStroke: 'rgba(220,220,220,1)',
                                                        data: PV_data
                                                    },
                                                    {
                                                        label: 'Earned Value (EV)',
                                                        fillColor: 'rgba(151,187,205,0.5)',
                                                        strokeColor: 'rgba(151,187,205,0.8)',
                                                        highlightFill: 'rgba(151,187,205,0.75)',
                                                        highlightStroke: 'rgba(151,187,205,1)',
                                                        data: EV_data
                                                    }
                                                ]
                                            };
                                        }

                                        // --------------------------------------------------------
                                       $log.debug(CONTROLLER_NAME + " : $scope.PV_data_set = " + JSON.stringify($scope.PV_data_set));
                                        // --------------------------------------------------------
                                    },
                                    function (response) {

                                        // ==================================================
                                        // - retrieving projects failed
                                        // ==================================================

                                        var status = response.status;
                                        var message = response.statusText;
                                        modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
                                    });


                        }
                    });
                };

            }]);

