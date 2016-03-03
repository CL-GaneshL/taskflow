
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
                    Close the following Skill ?
                </legend>

                <div class="form-group col-md-12">

                    <label class="col-md-3 control-label text-right padding-left-0 padding-right-5">
                        Reference :
                    </label>

                    <div class="col-md-8">

                        <input type="text"
                               name="reference" 
                               id="reference"
                               placeholder="<% toCloseSkillReference %>" 
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
                               placeholder="<% toCloseSkillDesignation %>"
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
                               placeholder="<% toCloseSkillDuration %>"
                               ng-readonly="true"
                               disabled="" />

                    </div>

                </div>

            </fieldset>
            <!-- ================================================== -->
        </form>
    </div>

    <!--------------------------------------------------------->
    <!-- modal footer                                        -->
    <!--------------------------------------------------------->
    <div class="modal-footer row form-group col-md-12">

        <button class="btn btn-primary btn-o" ng-click="cancel()">
            Cancel
        </button>

        <button class="btn btn-primary btn-o" ng-click="closeSkill()">
            Close
        </button>

    </div>

</div>