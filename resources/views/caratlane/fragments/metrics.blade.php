
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
<div class="container-fluid container-fullw bg-white">
    <div class="row">
        <div class="col-md-12">

            <uib-tabset class="tabbable">

                <!-- ================================================== -->
                <!-- - Indicators tab                                   -->
                <!-- ================================================== -->   
                <uib-tab heading="Indicators">
                    <div ng-include="'taskflow/fragments/metrics_indicators'"></div>
                </uib-tab>

                <!-- ================================================== -->
                <!-- - Status table                                     -->
                <!-- ================================================== -->   
                <uib-tab heading="Status">
                    <div ng-include="'taskflow/fragments/metrics_status'"></div>
                </uib-tab>

                <!-- ================================================== -->
                <!-- - Employees table                                  -->
                <!-- ================================================== -->   
                <uib-tab heading="Employees">
                    <div ng-include="'taskflow/fragments/metrics_employees'"></div>
                </uib-tab>

            </uib-tabset>            

            <!--</div>-->
        </div>
    </div>

</div>


<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->


