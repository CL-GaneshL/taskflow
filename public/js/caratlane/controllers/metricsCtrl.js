'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'metricsCtrl',
        [
            '$log',
            '$scope',
            '$uibModal',
            'projectsSrvc',
            'metricsSrvc',
            'modalSrvc',
            'usSpinnerService',
            function (
                    $log,
                    $scope,
                    $uibModal,
                    projectsSrvc,
                    metricsSrvc,
                    modalSrvc,
                    usSpinnerService
                    ) {

                var CONTROLLER_NAME = 'metricsCtrl';

                // ==================================================
                // - initialization
                // ==================================================

                $scope.projects = null;
                $scope.filter_project = null;

                $scope.data = null;
                $scope.labels = null;
                $scope.datasets = null;
                $scope.options = null;

                // ==================================================
                // - spinner
                // ==================================================
                $scope.startSpin = function () {
                    usSpinnerService.spin('taskSpinner');
                };

                $scope.stopSpin = function () {
                    usSpinnerService.stop('taskSpinner');
                };

                // ==================================================
                // - project filter
                // ==================================================
                var getFilterProject = function () {
                    return $scope.filter_project;
                };

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

                // ==================================================
                // - retrieve all projects
                // ==================================================

                var projectsPromise = projectsSrvc.getAllProjects();
                projectsPromise.then(
                        function (response) {

                            $scope.projects = response.projects;

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : $scope.projects = " + JSON.stringify($scope.projects));
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
                // - project filter
                // ==================================================
                $scope.changeProject = function () {

                    var project = getFilterProject();

                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : project = " + JSON.stringify(project));
                    // --------------------------------------------------------

                    if (project !== null && project !== undefined)
                    {
                        var project_id = project.id;

                        // --------------------------------------------------------
                        $log.debug(CONTROLLER_NAME + " : project id = " + JSON.stringify(project_id));
                        // --------------------------------------------------------

                        $scope.startSpin();

                        var metricsPromise = metricsSrvc.getProjectMetrics(project_id);
                        metricsPromise.then(
                                function (response) {

                                    var labels = response.labels;
                                    var PV_data = response.PV;
                                    var EV_data = response.EV;
                                    var CPI_data = response.CPI;
                                    var SPI_data = response.SPI;

                                    // --------------------------------------------------------
                                    $log.debug(CONTROLLER_NAME + " : response = " + JSON.stringify(response));
                                    // --------------------------------------------------------

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
                                   
                                    $scope.CPI_data_set = {
                                        labels: labels,
                                        datasets: [
                                            {
                                                label: 'Cost Performance Index (CPI)',
                                                fillColor: 'rgba(220,220,220,0.5)',
                                                strokeColor: 'rgba(220,220,220,0.8)',
                                                highlightFill: 'rgba(220,220,220,0.75)',
                                                highlightStroke: 'rgba(220,220,220,1)',
                                                data: CPI_data
                                            }
                                        ]
                                    };

                                    $scope.SPI_data_set = {
                                        labels: labels,
                                        datasets: [
                                            {
                                                label: 'Schedule Performance Index (SPI)',
                                                fillColor: 'rgba(220,220,220,0.5)',
                                                strokeColor: 'rgba(220,220,220,0.8)',
                                                highlightFill: 'rgba(220,220,220,0.75)',
                                                highlightStroke: 'rgba(220,220,220,1)',
                                                data: SPI_data
                                            }
                                        ]
                                    };

                                    $scope.stopSpin();
                                },
                                function (response) {

                                    // --------------------------------------------------------
                                    // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                    // --------------------------------------------------------

                                    // ==================================================
                                    // - refresh failed
                                    // ==================================================

                                    $scope.stopSpin();

                                    var status = response.status;
                                    var message = response.statusText;
                                    modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);

                                }
                        );

                    }

                };


                // ==================================================
                // - refresh graphics
                // ==================================================
                $scope.refresh = function () {

                    // --------------------------------------------------------
                    $log.debug(CONTROLLER_NAME + " : refresh ..... ");
                    // --------------------------------------------------------

//                    var allocatePromise = taskAllocationSrvc.reset();
//                    allocatePromise.then(
//                            function (response) {
//
//                                // --------------------------------------------------------
//                                // $log.debug(CONTROLLER_NAME + " : xxxxxxx = " + JSON.stringify(xxxxxxx));
//                                $log.debug(CONTROLLER_NAME + " : reset done ! ");
//                                // --------------------------------------------------------
//
//
//                            },
//                            function (response) {
//
//                                // --------------------------------------------------------
//                                // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
//                                // --------------------------------------------------------
//
//                                // ==================================================
//                                // - refresh failed
//                                // ==================================================
//
//                                var status = response.status;
//                                var message = response.statusText;
//                                modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);
//
//                            }
//                    );
                };

            }]);

