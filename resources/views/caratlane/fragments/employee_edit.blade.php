
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-xs-12">
        <h3 class="modal-title">Edit Employee</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-xs-12">

        <div class="row">
            <div class="col-md-12">

                <uib-tabset class="tabbable">

                    <!-- ================================================== -->
                    <!-- - overview tab                                     -->
                    <!-- ================================================== -->   
                    <uib-tab heading="Overview">
                        <div data-ng-include="'taskflow/fragments/profile_overview'"></div>
                    </uib-tab>

                    <!-- ================================================== -->
                    <!-- - edit tab                                         -->
                    <!-- ================================================== --> 
                    <uib-tab heading="Edit">
                        <div data-ng-include="'taskflow/fragments/profile_edit'"></div>
                    </uib-tab>

                    <!-- ================================================== -->
                    <!-- - skills tab                                       -->
                    <!-- ================================================== -->   
                    <uib-tab heading="Skills">
                        <div data-ng-include="'taskflow/fragments/profile_skills'"></div>
                    </uib-tab>

                    <!-- ================================================== -->
                    <!-- - tasks table                                  -->
                    <!-- ================================================== -->   
                    <uib-tab heading="Timetable">
                        <div data-ng-include="'taskflow/fragments/profile_tasks'"></div>
                    </uib-tab>

                </uib-tabset>

            </div>
        </div>
    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer col-xs-12">

        <div class="row form-group">

            <button class="btn btn-primary btn-o" ng-click="cancel()">
                Cancel
            </button>

            <button class="btn btn-danger btn-o" ng-click="cancel()">
                Save
            </button>

        </div>

    </div>

</div>

