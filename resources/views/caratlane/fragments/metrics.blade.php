
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

    <div class="row col-md-11 col-md-offset-1">

        <!-- ================================================== -->
        <!-- - spinner                                          -->
        <!-- ================================================== -->
        <span 
            us-spinner="{radius:30, width:8, length: 16}" 
            spinner-key="taskSpinner"
            spinner-start-active="false">

        </span> 

        <div ng-controller="metricsCtrl">

            <form role="form" 
                  class="row col-md-12 padding-bottom-10 padding-left-0 padding-right-0">

                <div class="col-md-5 padding-left-0">

                    <fieldset>

                        <legend>
                            Project :
                        </legend>

                        <!-- ================================================== -->
                        <!-- - projects dropdown menu                           -->
                        <!-- ================================================== --> 
                        <div class="col-md-12 form-group margin-bottom-0 padding-left-10">

                            <select class="form-control cs-select cs-skin-elastic" 
                                    ng-init="filter_project = projects[0]" 
                                    ng-model="filter_project"
                                    ng-change="changeProject()"
                                    ng-options="project.reference for project in projects">
                                <option value="" disabled="">Select a project ...</option>
                            </select>

                        </div>

                    </fieldset>

                </div>

                <div class="col-md-3 padding-right-0">

                    <fieldset>

                        <legend>
                            Hourly Cost
                        </legend>

                        <!-- ================================================== -->
                        <!-- - hourly cost                                      -->
                        <!-- ================================================== -->
                        <div class="col-md-12 form-group margin-bottom-0">
                            <span class="input-icon">

                                <i class="fa fa-rupee"></i>

                                <input type="text" 
                                       class="form-control"
                                       name="hourlyCost"
                                       ng-value ="hourlyCost"
                                       ng-readonly="true"
                                       disabled=""
                                       />
                            </span> 
                        </div>

                    </fieldset>

                </div>                

            </form> 

            <form role="form" 
                  class="row col-md-12 padding-bottom-10 padding-left-0 padding-right-0">


                <div class="col-md-12 padding-left-0 padding-right-0">

                    <fieldset>

                        <legend>
                            Project Indicators ( <% today %> )
                        </legend>

                        <!-- ================================================== -->
                        <!-- - PV                                               -->
                        <!-- ================================================== -->
                        <div class="row col-md-3 margin-bottom-0">

                            <p class="col-md-3 text-center padding-top-5 padding-left-0 padding-right-0">PV :</p>

                            <input type="text"
                                   class="col-md-8 push-left"
                                   ng-value ="indicators.PV"
                                   ng-readonly="true"
                                   disabled=""
                                   />                
                        </div>

                        <!-- ================================================== -->
                        <!-- - EV                                               -->
                        <!-- ================================================== -->
                        <div class="row col-md-3 margin-bottom-0">

                            <p class="col-md-3 text-center padding-top-5 padding-left-0 padding-right-0">EV :</p>

                            <input type="text"
                                   class="col-md-8 push-left"
                                   ng-value ="indicators.EV"
                                   ng-readonly="true"
                                   disabled=""
                                   />                
                        </div>

                        <!-- ================================================== -->
                        <!-- - CPI                                              -->
                        <!-- ================================================== -->
                        <div class="row col-md-3 margin-bottom-0">

                            <p class="col-md-3 text-center padding-top-5 padding-left-0 padding-right-0">CPI :</p>

                            <input type="text"
                                   class="col-md-8 push-left"
                                   ng-value ="indicators.CPI"
                                   ng-readonly="true"
                                   disabled=""
                                   />                
                        </div>

                        <!-- ================================================== -->
                        <!-- - SPI                                               -->
                        <!-- ================================================== -->
                        <div class="row col-md-3 margin-bottom-0">

                            <p class="col-md-3 text-center padding-top-5 padding-left-0 padding-right-0">SPI :</p>

                            <input type="text"
                                   class="col-md-8 push-left"
                                   ng-value ="indicators.SPI"
                                   ng-readonly="true"
                                   disabled=""
                                   />                
                        </div>


                    </fieldset>

                </div>

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
                                <canvas class="tc-chart" 
                                        tc-chartjs-line 
                                        chart-options="options" 
                                        chart-data="PV_data_set"
                                        chart-legend="chart-EV"
                                        width="100%" >

                                </canvas>

                                <div class="margin-top-20">
                                    <div tc-chartjs-legend 
                                         chart-legend="chart-EV" 
                                         class="inline pull-left">                                               
                                    </div>
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
                                <canvas class="tc-chart" 
                                        tc-chartjs-line 
                                        chart-options="options" 
                                        chart-data="CPI_data_set" 
                                        width="100%"
                                        chart-legend="chart-CPI">                                              
                                </canvas>

                                <div class="margin-top-20">
                                    <div tc-chartjs-legend 
                                         chart-legend="chart-CPI" 
                                         class="inline pull-left">                                               
                                    </div>
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
                                <canvas class="tc-chart"
                                        tc-chartjs-line
                                        chart-options="options"
                                        chart-data="SPI_data_set"
                                        width="100%"
                                        chart-legend="chart-SPI">
                                </canvas>

                                <div class="margin-top-20">
                                    <div tc-chartjs-legend 
                                         chart-legend="chart-SPI" 
                                         class="inline pull-left">                                               
                                    </div>
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

<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->


