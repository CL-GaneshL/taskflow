
<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">

        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <div class="col-sm-8">
            <h1 class="mainTitle">Settings</h1>
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
                <!-- - non working days tab                             -->
                <!-- ================================================== --> 
                <uib-tab heading="Non-Working Days" >
                    <div ng-include="'taskflow/fragments/settings_non_working_days'"></div>
                </uib-tab>

                <!-- ================================================== -->
                <!-- - hourly cost tab                             -->
                <!-- ================================================== --> 
                <uib-tab heading="Hourly Cost" >
                    <div ng-include="'taskflow/fragments/settings_hourly_cost'"></div>
                </uib-tab>

                <!-- ================================================== -->
                <!-- - task generator                                   -->
                <!-- ================================================== --> 
                <uib-tab heading="Task Generator" >
                    <div ng-include="'taskflow/fragments/settings_task_generator'"></div>
                </uib-tab>

                <!-- ================================================== -->
                <!-- - php info                                         -->
                <!-- ================================================== --> 
                <uib-tab heading="Php Info" >
                    <div ng-include="'taskflow/fragments/settings_php_info'"></div>
                </uib-tab>

                <!-- ================================================== -->
                <!-- - laravel log file                                 -->
                <!-- ================================================== --> 
                <uib-tab heading="Laravel logs" >
                    <div ng-include="'taskflow/fragments/settings_laravel_log'"></div>
                </uib-tab>

            </uib-tabset>

        </div>
    </div>
</div>
<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->



