
<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">

        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <div class="col-sm-8">
            <h1 class="mainTitle">Tasks</h1>
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
                <!-- - tasks tab                                         -->
                <!-- ================================================== --> 
                <uib-tab heading="Tasks" >
                    <div ng-include="'taskflow/fragments/tasks_tasks'"></div>
                </uib-tab>
         
                <!-- ================================================== -->
                <!-- - task allocation tab                                         -->
                <!-- ================================================== --> 
                <uib-tab heading="Allocation" >
                    <div ng-include="'taskflow/fragments/tasks_allocation'"></div>
                </uib-tab>

            </uib-tabset>

        </div>
    </div>
</div>
<!-- ================================================== -->
<!-- - end page body                                    -->
<!-- ================================================== -->
