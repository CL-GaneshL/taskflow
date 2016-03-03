
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header">
        <h3 class="modal-title">Validation</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body">

        <h5>Create the following Team ?</h5>

        <!-- ================================================== -->
        <!-- - new skill form                                   -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" 
              role="form"
              name="teamForm" >

            <div class="form-group">

                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                    Team Name :
                </label>

                <div class="col-md-8">

                    <input type="text"
                           name="teamName" 
                           id="teamName"
                           ng-model="newTeamName"
                           value="<% newTeamName %>"
                           ng-readonly="true"
                           disabled="" />

                </div>
            </div>

            <div class="form-group">

                <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                    Team Leader :
                </label>

                <div class="col-md-8">

                    <input type="text" 
                           name="teamLeader"
                           id="teamLeader"
                           ng-model="newTeamLeaderFullName"
                           value="<% newTeamLeaderFullName %>"
                           ng-readonly="true"
                           disabled="" />

                </div>

            </div>

            <!-- ================================================== -->


    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-primary btn-o" ng-click="create()">
            Create
        </button>

    </div>

</div>