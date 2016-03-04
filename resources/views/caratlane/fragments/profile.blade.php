
<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">

        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <div class="col-sm-8">
            <h1 class="mainTitle">My profile</h1>
        </div>

    </div>
</section>

<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->
<div class="container-fluid container-fullw bg-white">
    <div class="row">
        <div class="col-md-12">

            <!-- initialize the profile controller -->
            <div ng-controller="profileCtrl">

                <uib-tabset class="tabbable">

                    <!-- ================================================== -->
                    <!-- - overview tab                                     -->
                    <!-- ================================================== -->   
                    <uib-tab heading="Overview">
                        <div ng-include="'taskflow/fragments/profile_overview'"></div>
                    </uib-tab>

                    <!-- ================================================== -->
                    <!-- - timetable table                                  -->
                    <!-- ================================================== -->   
                    <uib-tab heading="Tasks">
                        <div ng-include="'taskflow/fragments/profile_tasks'"></div>
                    </uib-tab>

                    <!-- ================================================== -->
                    <!-- - edit tab                                         -->
                    <!-- ================================================== --> 
                    <uib-tab heading="Edit">
                        <div ng-include="'taskflow/fragments/profile_edit'"></div>
                    </uib-tab>

                    <!-- ================================================== -->
                    <!-- - skills tab                                       -->
                    <!-- ================================================== -->   
                    <uib-tab heading="Skills">
                        <div ng-include="'taskflow/fragments/profile_skills'"></div>
                    </uib-tab>

                    <!-- ================================================== -->
                    <!-- - holidays tab                                       -->
                    <!-- ================================================== -->   
                    <uib-tab heading="Holidays">
                        <div ng-include="'taskflow/fragments/profile_holidays'"></div>
                    </uib-tab>

                </uib-tabset>

            </div>
        </div>
    </div>
</div>
<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->
