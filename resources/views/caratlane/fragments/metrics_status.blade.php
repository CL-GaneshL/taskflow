

<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->

<div class="container-fluid container-fullw bg-white no-border">

    <div class="row col-md-11 col-md-offset-1">

        <div ng-controller="metricsStatusCtrl">

            <form role="form" 
                  class="row col-md-12 padding-bottom-10 padding-left-0 padding-right-0">

                <div class="col-md-5 padding-left-0">

                    <fieldset>

                        <legend>
                            Project :
                        </legend>

                        <!-- ================================================== -->
                        <!-- - projects dropdown menu                          -->
                        <!-- ================================================== --> 
                        <div class="col-md-12 form-group margin-bottom-0 padding-left-10">

                            <select class="form-control cs-select cs-skin-elastic" 
                                    ng-model="filter_projects_status"
                                    ng-change="changeProjectStatus()"
                                    ng-options="project.reference for project in projects_status">
                                <option value="" disabled="">Select a Project ...</option>
                            </select>

                        </div>

                    </fieldset>

                </div>               

            </form>         

            <!-- ================================================== -->
            <!-- - number of hours planned against time             -->
            <!-- - number of hours consumed against time            -->
            <!-- ================================================== -->
            <div class="row col-md-12 padding-left-0 padding-right-0">
                <div class="panel panel-white no-radius" id="metrics1">
                    <div class="panel-heading border-light">
                        <h5 class="panel-title"> Hours Planned and Hours Consumed. </h5>
                        <ul class="panel-heading-tabs border-light">                                
                            <li class="panel-tools">
                            <ct-paneltool tool-refresh="load1"></ct-paneltool>
                            </li>
                        </ul>
                    </div>
                    <div uib-collapse="visits" ng-init="visits = false" class="panel-wrapper">
                        <div class="panel-body">
                            <div class="height-350">
                                <canvas class="tc-chart" 
                                        tc-chartjs-line 
                                        chart-options="options" 
                                        chart-data="HP_data_set"
                                        chart-legend="chart_HP"
                                        width="100%" >

                                </canvas>

                                <div class="margin-top-20">
                                    <div tc-chartjs-legend 
                                         chart-legend="chart_HP" 
                                         class="inline pull-left">                                               
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- ================================================== -->
            <!-- - number of products completed against time        -->
            <!-- - number of products planned against time          -->
            <!-- ================================================== -->
            <div class="row col-md-12 padding-left-0 padding-right-0">
                <div class="panel panel-white no-radius" id="metrics2">
                    <div class="panel-heading border-light">
                        <h4 class="panel-title"> Products Completed and Products Planned. </h4>
                        <ul class="panel-heading-tabs border-light">                                
                            <li class="panel-tools">
                            <ct-paneltool tool-refresh="load1"></ct-paneltool>
                            </li>
                        </ul>
                    </div>
                    <div uib-collapse="visits" ng-init="visits = false" class="panel-wrapper">
                        <div class="panel-body">
                            <div class="height-350">
                                <canvas class="tc-chart" 
                                        tc-chartjs-line 
                                        chart-options="options" 
                                        chart-data="PP_data_set"
                                        chart-legend="chart_PP"
                                        width="100%" >                                              
                                </canvas>

                                <div class="margin-top-20">
                                    <div tc-chartjs-legend 
                                         chart-legend="chart_PP" 
                                         class="inline pull-left">                                               
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>

<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->


