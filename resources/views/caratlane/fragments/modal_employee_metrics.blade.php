
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header">
        <h3 class="modal-title">Metrics : <%employee%></h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body">

        <form class="form-horizontal padding-bottom-0" role="form">

            <fieldset>

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
                                            chart-legend="chart_EV"
                                            width="100%" >

                                    </canvas>

                                    <div class="margin-top-20">
                                        <div tc-chartjs-legend 
                                             chart-legend="chart_EV" 
                                             class="inline pull-left">                                               
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>

            </fieldset>

        </form>
        <!-- ================================================== -->

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Ok
        </button>

    </div>

</div>