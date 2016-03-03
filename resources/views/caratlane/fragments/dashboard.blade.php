
<!-- start: DASHBOARD TITLE -->
<section id="page-title" class="padding-top-15 padding-bottom-15">
    <div class="row">
        <div class="col-sm-7">
            <h1 class="mainTitle">Dashboard</h1>
            <span class="mainDescription">overview &amp; stats </span>
        </div>
    </div>
</section><!-- end: DASHBOARD TITLE -->

<!-- start: FIRST SECTION -->
<div class="container-fluid container-fullw padding-bottom-10">
    <div class="row">
        <div class="col-sm-12">
            <div class="row">
                <div class="col-md-7 col-lg-8">
                    <div class="panel panel-white no-radius" id="visits">
                        <div class="panel-heading border-light">
                            <h4 class="panel-title"> Some Project Metrics </h4>
                            <ul class="panel-heading-tabs border-light">
                                <li class="panel-tools">
                                <ct-paneltool tool-refresh="load1"></ct-paneltool>
                                </li>
                            </ul>
                        </div>
                        <div uib-collapse="visits" ng-init="visits = false" class="panel-wrapper">
                            <div class="panel-body">
                                <!-- /// controller:  'VisitsCtrl' -  localtion: assets/js/controllers/dashboardCtrl.js /// -->
                                <div ng-controller="VisitsCtrl" class="height-350">
                                    <canvas class="tc-chart" tc-chartjs-line chart-options="options" chart-data="data" chart-legend="chart1" width="100%"></canvas>
                                    <div class="margin-top-20">
                                        <div tc-chartjs-legend chart-legend="chart1" class="inline pull-left"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-5 col-lg-4">
                    <div class="panel panel-white no-radius">
                        <div class="panel-heading border-light">
                            <h4 class="panel-title"> Project 10 Task Completion </h4>
                        </div>
                        <div class="panel-body">
                            <h3 class="inline-block no-margin">26</h3> Overall
                            <uib-progressbar value="26" class="progress-xs no-radius" type="success"></uib-progressbar>
                            <div class="row">
                                <div class="col-sm-4">
                                    <h4 class="no-margin">15%</h4>
                                    <uib-progressbar value="15" class="progress-xs no-radius no-margin" type="danger"></uib-progressbar>
                                    Task 1
                                </div>
                                <div class="col-sm-4">
                                    <h4 class="no-margin">7%</h4>
                                    <uib-progressbar value="7" class="progress-xs no-radius no-margin" type="info"></uib-progressbar>
                                    Task 2
                                </div>
                                <div class="col-sm-4">
                                    <h4 class="no-margin">64%</h4>
                                    <uib-progressbar value="64" class="progress-xs no-radius no-margin" type="warning"></uib-progressbar>
                                    Task 3
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="panel panel-white no-radius">
                        <div class="panel-heading border-light">
                            <h4 class="panel-title"> Project 8 Task Completion </h4>
                        </div>
                        <div class="panel-body">
                            <h3 class="inline-block no-margin">55</h3> Overall
                            <uib-progressbar value="55" class="progress-xs no-radius" type="success"></uib-progressbar>
                            <div class="row">
                                <div class="col-sm-4">
                                    <h4 class="no-margin">35%</h4>
                                    <uib-progressbar value="35" class="progress-xs no-radius no-margin" type="info"></uib-progressbar>
                                    Task 1
                                </div>
                                <div class="col-sm-4">
                                    <h4 class="no-margin">87%</h4>
                                    <uib-progressbar value="87" class="progress-xs no-radius no-margin" type="info"></uib-progressbar>
                                    Task 2
                                </div>
                                <div class="col-sm-4">
                                    <h4 class="no-margin">12%</h4>
                                    <uib-progressbar value="12" class="progress-xs no-radius no-margin" type="danger"></uib-progressbar>
                                    Task 3
                                </div>
                            </div>
                        </div>                        
                    </div>

                </div>
            </div>
        </div>
    </div>
    <!-- end: FIRST SECTION -->

    <!-- start: SECOND SECTION -->
    <div class="container-fluid container-fullw bg-white">
        <div class="row">
            <div class="col-sm-8">
                <div class="panel panel-white no-radius">
                    <div class="panel-body">
                        <div class="partition-light-grey padding-15 text-center margin-bottom-20">
                            <h4 class="no-margin">Monthly Statistics</h4>
                            <span class="text-light">based on some metrics</span>
                        </div>
                        <v-accordion class="vAccordion--default">
                            <!-- add expanded attribute to open first section -->
                            <v-pane expanded>
                                <v-pane-header>
                                    This Month
                                </v-pane-header>
                                <v-pane-content>
                                    <table class="table margin-bottom-0">
                                        <tbody>
                                            <tr>
                                                <td class="center">1</td>
                                                <td>Project 10</td>
                                                <td class="center">0.62%</td>
                                                <td><i class="fa fa-caret-down text-red"></i></td>
                                            </tr>
                                            <tr>
                                                <td class="center">2</td>
                                                <td>Project 8</td>
                                                <td class="center">0.45%</td>
                                                <td><i class="fa fa-caret-up text-green"></i></td>
                                            </tr>
                                            <tr>
                                                <td class="center">3</td>
                                                <td>Project 7</td>
                                                <td class="center">0.56%</td>
                                                <td><i class="fa fa-caret-up text-green"></i></td>
                                            </tr>
                                            <tr>
                                                <td class="center">4</td>
                                                <td>Project 4</td>
                                                <td class="center">0.72%</td>
                                                <td><i class="fa fa-caret-down text-red"></i></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </v-pane-content>
                            </v-pane>
                            <v-pane>
                                <v-pane-header>
                                    Last Month
                                </v-pane-header>
                                <v-pane-content>
                                    <table class="table margin-bottom-0">
                                        <tbody>
                                            <tr>
                                                <td class="center">1</td>
                                                <td>Project 9</td>
                                                <td class="center">0.20%</td>
                                                <td><i class="fa fa-caret-up text-green"></i></td>
                                            </tr>
                                            <tr>
                                                <td class="center">2</td>
                                                <td>Project 8</td>
                                                <td class="center">0.34%</td>
                                                <td><i class="fa fa-caret-up text-green"></i></td>
                                            </tr>
                                            <tr>
                                                <td class="center">3</td>
                                                <td>Project 7</td>
                                                <td class="center">0.76%</td>
                                                <td><i class="fa fa-caret-up text-green"></i></td>
                                            </tr>
                                            <tr>
                                                <td class="center">4</td>
                                                <td>Project 4</td>
                                                <td class="center">0.80%</td>
                                                <td><i class="fa fa-caret-down text-red"></i></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </v-pane-content>
                            </v-pane>
                        </v-accordion>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="panel panel-white no-radius">
                    <div class="panel-heading border-bottom">
                        <h4 class="panel-title">Some Title</h4>
                    </div>
                    <div class="panel-body">
                        <!-- /// controller:  'OnotherCtrl' -  localtion: assets/js/controllers/dashboardCtrl.js /// -->
                    </div>
                    <div class="panel-footer">
                        <div class="clearfix padding-5 space5">
                            <div class="col-xs-4 text-center no-padding">
                                <div class="border-right border-dark">
                                    <span class="text-bold block text-extra-large">24%</span>
                                    <span class="text-light">xxxxxxxx</span>
                                </div>
                            </div>
                            <div class="col-xs-4 text-center no-padding">
                                <div class="border-right border-dark">
                                    <span class="text-bold block text-extra-large">28%</span>
                                    <span class="text-light">yyyyyyy</span>
                                </div>
                            </div>
                            <div class="col-xs-4 text-center no-padding">
                                <span class="text-bold block text-extra-large">98%</span>
                                <span class="text-light">zzzzzzz</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- end: SECOND SECTION -->

