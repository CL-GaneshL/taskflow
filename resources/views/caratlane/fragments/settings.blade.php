
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
                <!-- - non working days tab                                         -->
                <!-- ================================================== --> 
                <uib-tab heading="Non-Working Days" >
                    <div ng-include="'taskflow/fragments/projects_non_working_days'"></div>
                </uib-tab>

            </uib-tabset>

        </div>
    </div>
</div>
<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->



