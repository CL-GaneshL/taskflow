
<div class="container-fluid container-fullw bg-white no-border">

    <!--------------------------------------------------------->
    <!-- modal header                                        -->
    <!--------------------------------------------------------->
    <div class="modal-header col-md-12">
        <h3 class="modal-title">Validation</h3>
    </div>

    <!--------------------------------------------------------->
    <!-- modal body                                          -->
    <!--------------------------------------------------------->
    <div class="modal-body col-md-12">

        <!-- ================================================== -->
        <!-- - new skill form                                   -->
        <!-- ================================================== -->
        <form class="form-horizontal padding-bottom-20" 
              role="form"
              name="skillForm" >

            <fieldset>

                <legend>
                    Create the following Skill ?
                </legend>

                <div class="form-group">

                    <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                        Reference :
                    </label>

                    <div class="col-md-8">

                        <input type="text"
                               name="reference" 
                               id="reference"
                               ng-model="newSkillReference"
                               value="<% newSkillReference %>"
                               ng-readonly="true"
                               disabled="" />

                    </div>
                </div>

                <div class="form-group">

                    <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                        Designation :
                    </label>

                    <div class="col-md-8">

                        <input type="text" 
                               name="designation"
                               id="designation"
                               ng-model="newSkillDesignation"
                               value="<% newSkillDesignation %>"
                               ng-readonly="true"
                               disabled="" />

                    </div>

                </div>

                <div class="form-group">

                    <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                        Duration :
                    </label>

                    <div class="col-md-8">

                        <input type="text" 
                               name="duration"
                               id="duration"
                               ng-model="newSkillDuration"
                               value="<% newSkillDuration %>"
                               ng-readonly="true"
                               disabled="" />

                    </div>

                </div>

            </fieldset>
            <!-- ================================================== -->

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group col-md-12">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-primary btn-o" ng-click="create()">
            Create
        </button>

    </div>

</div>