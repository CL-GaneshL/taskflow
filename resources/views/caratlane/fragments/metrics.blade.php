
<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">

        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <div class="col-sm-8">
            <h1 class="mainTitle">Metrics</h1>
        </div>

    </div>
</section>

<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->

<div class="container-fluid container-fullw bg-white no-border">

    <div class="row">

        <div class="col-md-10 col-md-offset-1">

            <!-- ================================================== -->
            <!-- - spinner                                          -->
            <!-- ================================================== -->
            <span 
                us-spinner="{radius:30, width:8, length: 16}" 
                spinner-key="taskSpinner"
                spinner-start-active="false">

            </span> 

            <div ng-controller="metricsCtrl">

                <form class="row form-horizontal padding-bottom-10" role="form">

                    <fieldset>

                        <legend>
                            Project :
                        </legend>

                        <div class="form-group padding-left-10">

                            <!-- ================================================== -->
                            <!-- - projects dropdown menu                           -->
                            <!-- ================================================== --> 
                            <div class="col-md-6"> 

                                <select class="form-control cs-select cs-skin-elastic" 
                                        ng-init="filter_project = projects[0]" 
                                        ng-model="filter_project"
                                        ng-change="changeProject()"
                                        ng-options="project.reference for project in projects" 
                                        >
                                    <option value="" disabled="">Select a project ...</option>
                                </select>

                            </div> 

                            <!-- ================================================== -->
                            <!-- - warning messages                                 -->
                            <!-- ================================================== --> 
                            <div class="padding-left-20"> 
                                <p>warning message</p>
                            </div> 

                        </div> 

                    </fieldset>

                </form> 

                <!-- ================================================== -->
                <!-- - Earned Value and Planned Value                   -->
                <!-- ================================================== -->
                <div class="row col-md-12 padding-left-0 padding-right-0">
                    <div class="panel panel-white no-radius" id="metrics1">
                        <div class="panel-heading border-light">
                            <h5 class="panel-title"> Earned Value and Planned Value. </h5>
                            <ul class="panel-heading-tabs border-light">                                
                                <li class="panel-tools">
                                <ct-paneltool tool-refresh="load1"></ct-paneltool>
                                </li>
                            </ul>
                        </div>
                        <div uib-collapse="visits" ng-init="visits = false" class="panel-wrapper">
                            <div class="panel-body">
                                <div class="height-350">
                                    <canvas class="tc-chart" tc-chartjs-line chart-options="options" chart-data="data" chart-legend="chart1" width="100%"></canvas>
                                    <div class="margin-top-20">
                                        <div tc-chartjs-legend chart-legend="chart1" class="inline pull-left"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- ================================================== -->
                <!-- - Cost Performance Index (CPI)                     -->
                <!-- ================================================== -->
                <div class="row col-md-12 padding-left-0 padding-right-0">
                    <div class="panel panel-white no-radius" id="metrics2">
                        <div class="panel-heading border-light">
                            <h4 class="panel-title"> Cost Performance Index (CPI). </h4>
                            <ul class="panel-heading-tabs border-light">                                
                                <li class="panel-tools">
                                <ct-paneltool tool-refresh="load1"></ct-paneltool>
                                </li>
                            </ul>
                        </div>
                        <div uib-collapse="visits" ng-init="visits = false" class="panel-wrapper">
                            <div class="panel-body">
                                <div class="height-350">
                                    <canvas class="tc-chart" tc-chartjs-line chart-options="options" chart-data="data" chart-legend="chart1" width="100%"></canvas>
                                    <div class="margin-top-20">
                                        <div tc-chartjs-legend chart-legend="chart1" class="inline pull-left"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- ================================================== -->
                <!-- - Schedule Performance Index (SPI)                 -->
                <!-- ================================================== -->
                <div class="row col-md-12 padding-left-0 padding-right-0">
                    <div class="panel panel-white no-radius" id="metrics3">
                        <div class="panel-heading border-light">
                            <h4 class="panel-title"> Schedule Performance Index (SPI). </h4>
                            <ul class="panel-heading-tabs border-light">                                
                                <li class="panel-tools">
                                <ct-paneltool tool-refresh="load1"></ct-paneltool>
                                </li>
                            </ul>
                        </div>
                        <div uib-collapse="visits" ng-init="visits = false" class="panel-wrapper">
                            <div class="panel-body">
                                <div class="height-350">
                                    <canvas class="tc-chart" tc-chartjs-line chart-options="options" chart-data="data" chart-legend="chart1" width="100%"></canvas>
                                    <div class="margin-top-20">
                                        <div tc-chartjs-legend chart-legend="chart1" class="inline pull-left"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- ================================================== -->

            </div>

        </div>

    </div>

</div>

</div>

<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->


