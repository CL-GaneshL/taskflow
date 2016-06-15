'use strict';
/** 
 * controller for User Profile Example
 */
app.controller(
        'metricsStatusCtrl',
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

                var CONTROLLER_NAME = 'metricsStatusCtrl';

                // ==================================================
                // - initialization
                // ==================================================

                $scope.projects_status = null;
                $scope.filter_projects_status = null;

                $scope.data = null;
                $scope.labels = null;
                $scope.datasets = null;
                $scope.options = null;

                // ==================================================
                // - project status filter
                // ==================================================
                var getFilterProjectStatus = function () {
                    return $scope.filter_projects_status;
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

                            $scope.projects_status = response.projects;

                            // --------------------------------------------------------
                            // $log.debug(CONTROLLER_NAME + " : $scope.projects_status = " + JSON.stringify($scope.projects_status));
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
                // - Project Status filter
                // ==================================================
                $scope.changeProjectStatus = function () {

                    var project = getFilterProjectStatus();

                    // --------------------------------------------------------
                    // $log.debug(CONTROLLER_NAME + " : project = " + JSON.stringify(project));
                    // $log.debug(CONTROLLER_NAME + " : change project status  .... ");
                    // --------------------------------------------------------

                    if (project !== null && project !== undefined)
                    {
                        var project_id = project.id;

                        var metricsPromise = metricsSrvc.getProjectStatus(project_id);
                        metricsPromise.then(
                                function (response) {

                                    // --------------------------------------------------------
                                    // $log.debug(CONTROLLER_NAME + " : response = " + JSON.stringify(response));
                                    // --------------------------------------------------------

                                    var labels = response.labels;
                                    var HP_data = response.HP;
                                    var HC_data = response.HC;
                                    var PP_data = response.PP;
                                    var PC_data = response.PC;

                                    if (HP_data.length !== 0 && labels.length !== 0) {
                                        $scope.HP_data_set = {
                                            labels: labels,
                                            datasets: [
                                                {
                                                    label: 'Hours Planned (HP)',
                                                    fillColor: 'rgba(220,220,220,0.5)',
                                                    strokeColor: 'rgba(220,220,220,0.8)',
                                                    highlightFill: 'rgba(220,220,220,0.75)',
                                                    highlightStroke: 'rgba(220,220,220,1)',
                                                    data: HP_data
                                                },
                                                {
                                                    label: 'Hours Consumed (HC)',
                                                    fillColor: 'rgba(151,187,205,0.5)',
                                                    strokeColor: 'rgba(151,187,205,0.8)',
                                                    highlightFill: 'rgba(151,187,205,0.75)',
                                                    highlightStroke: 'rgba(151,187,205,1)',
                                                    data: HC_data
                                                }
                                            ]
                                        };
                                    }

                                    if (PP_data.length !== 0 && labels.length !== 0) {
                                        $scope.PP_data_set = {
                                            labels: labels,
                                            datasets: [
                                                {
                                                    label: 'Products Planned (PP)',
                                                    fillColor: 'rgba(220,220,220,0.5)',
                                                    strokeColor: 'rgba(220,220,220,0.8)',
                                                    highlightFill: 'rgba(220,220,220,0.75)',
                                                    highlightStroke: 'rgba(220,220,220,1)',
                                                    data: PP_data
                                                },
                                                {
                                                    label: 'Products Consumed (PC)',
                                                    fillColor: 'rgba(151,187,205,0.5)',
                                                    strokeColor: 'rgba(151,187,205,0.8)',
                                                    highlightFill: 'rgba(151,187,205,0.75)',
                                                    highlightStroke: 'rgba(151,187,205,1)',
                                                    data: PC_data
                                                }
                                            ]
                                        };
                                    }

                                },
                                function (response) {

                                    // --------------------------------------------------------
                                    // $log.debug(CONTROLLER_NAME + " : error response = " + JSON.stringify(response));
                                    // --------------------------------------------------------

                                    var status = response.status;
                                    var message = response.statusText;
                                    modalSrvc.showErrorMessageModal3(CONTROLLER_NAME, status, message);

                                }
                        );
                    }
                };

            }]);

