
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
              name="projectForm" >

            <fieldset>

                <legend>
                    Create the following Project ?
                </legend>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-right-5">
                        Reference :
                    </label>

                    <div class="col-md-8">
                        <input type="text"
                               class="col-md-12"
                               name="projectReference" 
                               id="projectReference"
                               ng-model="projectReference"
                               value="<% projectReference %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>
                </div>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-right-5">
                        Designation :
                    </label>

                    <div class="col-md-8">
                        <input type="text" 
                               class="col-md-12"
                               name="projectDesignation"
                               id="projectDesignation"
                               ng-model="projectDesignation"
                               value="<% projectDesignation %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>

                </div>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-right-5">
                        Start Date :
                    </label>

                    <div class="col-md-8">
                        <input type="text" 
                               class="col-md-12"
                               name="startDate"
                               id="startDate"
                               ng-model="startDate"
                               value="<% startDate %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>
                </div>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-right-5">
                        Start Date :
                    </label>

                    <div class="col-md-8">
                        <input type="text" 
                               class="col-md-12"
                               name="nbProducts"
                               id="nbProducts"
                               ng-model="nbProducts"
                               value="<% nbProducts %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>
                </div>

                <div class="form-group col-md-12">

                    <label class="col-md-4 control-label text-right padding-right-5">
                        Start Date :
                    </label>

                    <div class="col-md-8">
                        <input type="text" 
                               class="col-md-12"
                               name="priority"
                               id="priority"
                               ng-model="priority"
                               value="<% priority %>"
                               ng-readonly="true"
                               disabled="" />
                    </div>
                </div>

            </fieldset>
        </form>
        <!-- ================================================== -->

    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group col-md-12">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-primary btn-o" ng-click="createProject()">
            Create
        </button>

    </div>

</div>